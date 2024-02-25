package com.ms.email.controllers;


import com.ms.email.dtos.EmailDto;
import com.ms.email.entities.EmailModel;
import com.ms.email.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController {
    final
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendingEmail(@Valid  @RequestBody EmailDto emailDto) {
        return emailService.sendEmail(emailDto);
    }

    @GetMapping("/list-all")
    public Page<EmailModel> getAllEmails(@RequestHeader(name = "Authorization") String token, @PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable){
        return emailService.findAll(pageable, token);
    }

    @GetMapping("/by/{id}")
    public ResponseEntity<?> getEmailById(@RequestHeader(name = "Authorization") String token, @PathVariable(value="id") Long emailId) {
        return emailService.findById(emailId, token);
    }
}
