package com.example.curseforbradesco.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {



        @Bean
    public OpenAPI customAPI(){
        return new OpenAPI().info(new Info()
                .title("Restful API with Java 18 and Spring Boot 3").version("v3").description("I'm studie Spring Boot").termsOfService("https://www.google.com/search?q=language+speak+in+mexico&ei=ImhJY_HxHdLZ1sQPi7qSqAY&oq=language+speak+in+me&gs_lcp=Cgdnd3Mtd2l6EAMYADIHCAAQgAQQEzIHCAAQgAQQEzIICAAQFhAeEBMyCAgAEBYQHhATMgoIABAWEB4QDxATMggIABAWEB4QEzoKCAAQ6gIQtAIQQzoXCC4Q1AIQ6gIQtAIQigMQtwMQ1AMQ5QI6CwgAEIAEELEDEIMBOgUILhCABDoLCC4QgAQQsQMQgwE6BwguENQCEEM6CAguELEDEIMBOhEILhCABBCxAxCDARDHARDRAzoECAAQQzoHCAAQgAQQAzoOCC4QgAQQsQMQxwEQ0QM6BwgAELEDEEM6CAguEIAEELEDOggIABCxAxCDAToICAAQgAQQsQM6BQgAEIAEOgsILhCABBDHARCvAToGCAAQFhAeOgkIABCABBAKEBM6CQgAEIAEEA0QEzoLCAAQgAQQDRAKEBM6BwgAEIAEEA1KBAhBGABKBAhGGABQ5gtYp5QBYKSbAWgKcAF4AIABgQGIAewVkgEEMC4yNJgBAKABAbABCsABAQ&sclient=gws-wiz").license(new License().name("Apache 2.0").url("www.google.com")));
    }






}
