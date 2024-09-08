package com.axis.bank.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.axis.bank.entities.Role;
import com.axis.bank.repositories.RoleRepository;
import com.axis.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axis.bank.entities.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roles = roleRepository.findByName("USER").orElseThrow(()-> new RuntimeException("User Not Found!!"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roles);
        user.setRoles(roleSet);
        return userRepository.save(user);
    }
}
