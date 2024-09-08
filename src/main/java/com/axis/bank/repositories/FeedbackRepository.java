package com.axis.bank.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.axis.bank.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}


