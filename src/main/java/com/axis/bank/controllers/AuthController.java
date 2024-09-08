package com.axis.bank.controllers;
import com.axis.bank.dto.JwtRequest;
import com.axis.bank.dto.JwtResponse;
import com.axis.bank.security.JwtHelper;
import com.axis.bank.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);
        String role = userDetails.getAuthorities().toString();
        if(role.contains("ADMIN")) {
            role = "ADMIN";
        } else if (role.contains("CUSTOMER")) {
            role = "CUSTOMER";
        } else {
            role = "EMPLOYEE";
        }
        JwtResponse response = JwtResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .role(role)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String duplicateUserHandler(){ return "User already exists !!"; }

    @PostMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login";
    }
}

