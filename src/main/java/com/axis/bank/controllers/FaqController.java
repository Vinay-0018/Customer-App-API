package com.axis.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.axis.bank.entities.Faq;
import com.axis.bank.repositories.FaqRepository;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/faqs")
public class FaqController {

    @Autowired
    private FaqRepository faqRepository;

    @GetMapping
    public List<Faq> getAllFAQs() {
        return faqRepository.findAll();
    }

    @PostMapping
    public Faq createFAQ(@RequestBody Faq faq) {
        return faqRepository.save(faq);
    }

    @PutMapping("/{id}")
    public Faq updateFAQ(@PathVariable Long id, @RequestBody Faq faq) {
        faq.setId(id);
        return faqRepository.save(faq);
    }

    @DeleteMapping("/{id}")
    public void deleteFAQ(@PathVariable Long id) {
        faqRepository.deleteById(id);
    }
}
