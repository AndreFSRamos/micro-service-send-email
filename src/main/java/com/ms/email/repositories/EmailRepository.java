package com.ms.email.repositories;

import com.ms.email.entities.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel, Long> {}
