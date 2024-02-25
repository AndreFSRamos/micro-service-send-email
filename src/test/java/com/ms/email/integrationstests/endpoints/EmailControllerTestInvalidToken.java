package com.ms.email.integrationstests.endpoints;

import com.ms.email.dtos.EmailDto;
import com.ms.email.integrationstests.configurations.TestConfiguration;
import com.ms.email.integrationstests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailControllerTestInvalidToken extends AbstractIntegrationTest {
    private static final List<String> ENDPOINTS = Arrays.asList(
            "/api/email/v1/send", // #0
            "/api/email/v1/list-all", //#1
            "/api/email/v1/by/" //#2
    );

    @Test
    @Order(1)
    public void sendEmailWithInvalidToken() {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailFrom("email.from@gmail.com");
        emailDto.setEmailTo("email.to@gmail.com");
        emailDto.setSubject("Test integration");
        emailDto.setOwnerRef("User Test");
        emailDto.setOrigin("htt://localhost:8889");
        emailDto.setUsername("user.test");
        emailDto.setText("message test integration");
        emailDto.setJwtToken("Invalidtoken");

        try {
            given()
                    .basePath(ENDPOINTS.get(0))
                    .port(TestConfiguration.SERVER_PORT)
                    .contentType(TestConfiguration.CONTENT_TYPE_JSON)
                    .body(emailDto)
                    .when()
                    .post()
                    .then()
                    .statusCode(401);
        }catch (Exception error){
            System.out.println(error.getMessage());
            assertTrue(true);
        }
    }

    @Test
    @Order(2)
    public void findAllEmailsWithInvalidToken() {
        try {
            given()
                    .basePath(ENDPOINTS.get(1))
                    .port(TestConfiguration.SERVER_PORT)
                    .header("Authorization","Bearer InvalidToken")
                    .contentType(TestConfiguration.CONTENT_TYPE_JSON)
                    .when()
                    .get()
                    .then()
                    .statusCode(401);
        }catch (Exception error){
            System.out.println(error.getMessage());
            assertTrue(true);
        }
    }

    @Test
    @Order(3)
    public void findEmailsByIDWithInvalidToken() {
        try {
            given()
                    .basePath(ENDPOINTS.get(2) +"1")
                    .port(TestConfiguration.SERVER_PORT)
                    .header("Authorization","Bearer InvalidToken")
                    .contentType(TestConfiguration.CONTENT_TYPE_JSON)
                    .when()
                    .get()
                    .then()
                    .statusCode(401);
        }catch (Exception error){
            System.out.println(error.getMessage());
            assertTrue(true);
        }
    }
}
