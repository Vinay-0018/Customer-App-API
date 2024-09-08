package com.axis.bank.controllers;

import com.axis.bank.entities.SupportTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.axis.bank.entities.Feedback;
import com.axis.bank.repositories.FeedbackRepository;

import javax.persistence.EntityNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getTicket(@PathVariable Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        return ResponseEntity.ok(feedback);
    }
    @PutMapping("/{id}")
    public Feedback updateFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
        feedback.setId(id);
        return feedbackRepository.save(feedback);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable Long id) {
        feedbackRepository.deleteById(id);
    }
}

