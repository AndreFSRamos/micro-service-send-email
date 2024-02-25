package com.ms.email.services;

import com.ms.email.dtos.EmailDto;
import com.ms.email.entities.EmailModel;
import com.ms.email.enums.StatusEmail;
import com.ms.email.exceptions.UnauthorizedException;
import com.ms.email.repositories.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.time.LocalDateTime;

@Service
public class EmailService {
    private static final Logger logger = LogManager.getLogger(EmailService.class);
    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;
    private final TokenService tokenService;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender, TokenService tokenService) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
        this.tokenService = tokenService;
    }

    @Transactional
    public ResponseEntity<?> sendEmail(EmailDto emailDto) {
        tokenService.validateToken(emailDto.getJwtToken());
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailModel.setSendDateEmail(LocalDateTime.now());

        try {
            String template = new String(FileCopyUtils.copyToByteArray(new ClassPathResource("templates/email-body.html").getInputStream()));
            template = template
                    .replace("[name]", emailDto.getOwnerRef())
                    .replace("[email]", emailDto.getEmailFrom())
                    .replace("[subject]", emailDto.getSubject())
                    .replace("[content]", emailDto.getText());

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(emailModel.getEmailFrom());
            helper.setTo(emailModel.getEmailTo());
            helper.setSubject(emailModel.getSubject());
            helper.setText(template, true);

            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
            logger.info("Email Sent");
        } catch (Exception e) {
            logger.error("Error in send email(Service): " + e.getMessage());
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }
        emailRepository.save(emailModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public Page<EmailModel> findAll(Pageable pageable, String token) {
        tokenService.validateToken(getTokenHeader(token));
        return emailRepository.findAll(pageable);
    }

    public ResponseEntity<?> findById(Long emailId, String token) {
        tokenService.validateToken(getTokenHeader(token));
        return emailRepository.findById(emailId).map(email -> ResponseEntity.status(HttpStatus.OK).body(email))
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    private String getTokenHeader(String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        } else {
            throw new UnauthorizedException("Invalid token!");
        }
    }
}
