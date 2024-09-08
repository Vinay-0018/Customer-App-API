package com.axis.bank.controllers;

import com.axis.bank.entities.Feedback;
import com.axis.bank.repositories.CustomerRepository;
import com.axis.bank.repositories.FeedbackRepository;
import com.axis.bank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import com.axis.bank.entities.SupportTicket;
import com.axis.bank.repositories.SupportTicketRepository;
import javax.persistence.EntityNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tickets")
public class SupportTicketController {
    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @PostMapping
    public ResponseEntity<SupportTicket> createSupportTicket(@RequestBody SupportTicket supportTicket) {
        supportTicket.setStatus("Open");
        supportTicket.setTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
        SupportTicket createdTicket = supportTicketRepository.save(supportTicket);
        Feedback feedback = new Feedback();
        createdTicket.setFeedback(feedback);
        feedbackRepository.save(feedback);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @PutMapping("/{id}")
    public SupportTicket updateTicket(@PathVariable Long id, @RequestBody SupportTicket supportTicket) {
        supportTicket.setId(id);
        return supportTicketRepository.save(supportTicket);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SupportTicket> getTicket(@PathVariable Long id) {
        SupportTicket supportTicket =  supportTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SupportTicket not found"));
        return ResponseEntity.ok(supportTicket);
    }
    @GetMapping
    public ResponseEntity<List<SupportTicket>> getTicketsByStatus(@RequestParam(name = "status", required = false) String status,@RequestParam(name = "accountId", required = false) String accountId
    ,@RequestParam(name = "level", required = false) String level) {
        List<SupportTicket> filteredTickets;

        if(status!=null && accountId!=null){
            filteredTickets = supportTicketRepository.findByStatusAndAccountId(status,accountId);
        } else if (level!=null && status!=null) {
            filteredTickets = supportTicketRepository.findByStatusAndLevel(status,level);
        } else if (level!=null) {
            filteredTickets = supportTicketRepository.findByLevel(level);
        }
        else if (status != null) {
            filteredTickets = supportTicketRepository.findByStatus(status);
        } else if (accountId!=null) {
            filteredTickets = supportTicketRepository.findByAccountId(accountId);
        } else {
            filteredTickets = supportTicketRepository.findAll();
        }
        return ResponseEntity.ok(filteredTickets);
    }
    @DeleteMapping("{id}")
    public void deleteTicket(@PathVariable Long id) {
        SupportTicket supportTicket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SupportTicket not found"));
        supportTicketRepository.deleteById(id);
        feedbackRepository.deleteById(supportTicket.getFeedback().getId());
    }
}

