package com.ms.email.controllers;


import com.ms.email.dtos.EmailDto;
import com.ms.email.entities.EmailModel;
import com.ms.email.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email/v1")
@Tag(name = "Email", description = "Endpoints to send email.")
public class EmailController {
    final
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Send email.",
            description = "To send the email, you must enter the jwt token in the \"jwtToken\" field in the body.",
            tags = {"Email"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
    }
    )
    @PostMapping("/send")
    public ResponseEntity<?> sendingEmail(@Valid @RequestBody EmailDto emailDto) {
        return emailService.sendEmail(emailDto);
    }

    @Operation(summary = "Get all sent emails.",
            description = "Get all sent emails",
            tags = {"Email"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = EmailModel.class)
                                    )
                            )
                    }
            ),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
    }
    )
    @GetMapping("/list-all")
    public Page<EmailModel> getAllEmails(@RequestHeader(name = "Authorization") String token, @PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable) {
        return emailService.findAll(pageable, token);
    }

    @Operation(summary = "Get sent emails by ID.",
            description = "Get sent emails by ID.",
            tags = {"Email"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmailModel.class)
                            )
                    }
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
    }
    )
    @GetMapping("/by/{id}")
    public ResponseEntity<?> getEmailById(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "id") Long emailId) {
        return emailService.findById(emailId, token);
    }
}
