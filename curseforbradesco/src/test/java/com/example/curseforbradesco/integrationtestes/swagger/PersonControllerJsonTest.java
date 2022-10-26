package com.example.curseforbradesco.integrationtestes.swagger;

import com.example.curseforbradesco.configs.TestConfigs;
import com.example.curseforbradesco.interationstests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	@Test
	public void showDisplaySwaggerUiPage() {
		var content = given().basePath("swagger-ui/index.html").port(TestConfigs.SERVER_PORT)
				.when().get().then().statusCode(200).extract().body().asString();

		Assertions.assertTrue(content.contains("Swagger-UI"));
	}

}
