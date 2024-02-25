package com.ms.email.integrationstests.swagger;

import com.ms.email.integrationstests.configurations.TestConfiguration;
import com.ms.email.integrationstests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void shouldDisplaySwaggerUiPage(){
        String content;
        content = given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfiguration.SERVER_PORT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        assertTrue(content.contains("Swagger UI"));
    }
}
