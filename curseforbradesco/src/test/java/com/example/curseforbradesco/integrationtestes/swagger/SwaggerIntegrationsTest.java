package com.example.curseforbradesco.integrationtestes.swagger;

import static io.restassured.RestAssured.given;

import com.example.curseforbradesco.configs.TestConfigs;
import com.example.curseforbradesco.interationstests.testcontainers.AbstractIntegrationTest;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationsTest extends AbstractIntegrationTest {

	@Test
	public void showDisplaySwaggerUiPage() {
		var content = given().basePath("swagger-ui/index.html").port(TestConfigs.SERVER_PORT)
				.when().get().then().statusCode(200).extract().body().asString();

		Assertions.assertTrue(content.contains("Swagger-UI"));
	}

}
