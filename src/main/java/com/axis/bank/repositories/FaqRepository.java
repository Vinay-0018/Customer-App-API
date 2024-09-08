package com.axis.bank.repositories;

import com.axis.bank.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
