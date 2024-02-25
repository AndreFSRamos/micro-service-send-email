package com.ms.email.consumers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.services.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    private static final Logger logger = LogManager.getLogger(EmailConsumer.class);
    final
    EmailService emailService;
    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.addresses}" )
    public void listen(@Payload EmailDto emailDto) {
        try {
            emailService.sendEmail(emailDto);
        }catch (Exception error){
            logger.error("Error in send email(Consumer): " + error.getMessage());
        }
    }
}
