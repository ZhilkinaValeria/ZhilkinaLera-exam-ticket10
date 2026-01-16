package ru.rksp.ZhilkinaLera.dto;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ingest Service API")
                        .version("1.0")
                        .description("Сервис для приема событий платежей и отправки в RabbitMQ")
                        .termsOfService("http://swagger.io/terms/"));
    }
}
