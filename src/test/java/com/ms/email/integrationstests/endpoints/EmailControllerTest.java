package com.ms.email.integrationstests.endpoints;

import com.ms.email.dtos.EmailDto;
import com.ms.email.integrationstests.configurations.TestConfiguration;
import com.ms.email.integrationstests.dto.ResponseAuth;
import com.ms.email.integrationstests.dto.UserAuth;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailControllerTest extends AbstractIntegrationTest {
    private static final List<String> ENDPOINTS = Arrays.asList(
            "/api/email/v1/send", // #0
            "/api/email/v1/list-all", //#1
            "/api/email/v1/by/1", //#2
            "/api/auth/v1/sign-in"//#3
    );

    private static ResponseAuth token = new ResponseAuth();

    @Test
    @Order(0)
    public void authentication() {
        token = given()
                .basePath(ENDPOINTS.get(3))
                .port(3100)
                .contentType(TestConfiguration.CONTENT_TYPE_JSON)
                .body(new UserAuth("usertest.integration", "123456"))
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ResponseAuth.class);

        System.out.println(token.getAccessToken());
    }

    @Test
    @Order(1)
    public void sendEmail() {
        System.out.println(token.getAccessToken());

        EmailDto emailDto = new EmailDto();
        emailDto.setEmailFrom("email.from.test@gmail.com");
        emailDto.setEmailTo("andre.sis.tec@hotmail.com");
        emailDto.setSubject("Test integration");
        emailDto.setOwnerRef("User Test");
        emailDto.setOrigin("htt://localhost:8889");
        emailDto.setUsername("usertest.integration");
        emailDto.setText("Lorem ipsum dolor sit, amet consectetur adipisicing elit. Sapiente nihil aliquam illo ipsum iusto? Minima nulla aut voluptatem debitis? Iure adipisci quaerat, dicta magnam sit corrupti! Neque possimus dolore quibusdam.");
        emailDto.setJwtToken(token.getAccessToken());

        given()
                .basePath(ENDPOINTS.get(0))
                .port(TestConfiguration.SERVER_PORT)
                .contentType(TestConfiguration.CONTENT_TYPE_JSON)
                .body(emailDto)
                .when()
                .post()
                .then()
                .statusCode(204);
    }

    @Test
    @Order(2)
    public void findAllEmails() {
        System.out.println(token.getAccessToken());

        given()
                .basePath(ENDPOINTS.get(1))
                .port(TestConfiguration.SERVER_PORT)
                .header(TestConfiguration.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .contentType(TestConfiguration.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    public void findEmailsByID() {
        System.out.println(token.getAccessToken());

        given()
                .basePath(ENDPOINTS.get(2))
                .port(TestConfiguration.SERVER_PORT)
                .header(TestConfiguration.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .contentType(TestConfiguration.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }
}
