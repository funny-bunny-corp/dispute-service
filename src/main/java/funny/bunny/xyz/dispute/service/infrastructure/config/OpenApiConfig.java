package funny.bunny.xyz.dispute.service.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Dispute Management API")
                .version("1.0.0")
                .description("API for managing disputes, including dispute creation, escalation, and evidence submission.")
                .contact(new Contact()
                    .name("Dispute Service Team")
                    .email("support@dispute-service.com")))
            .servers(List.of(
                new Server().url("https://api.example.com").description("Production Server"),
                new Server().url("https://api.sandbox.example.com").description("Sandbox Server"),
                new Server().url("http://localhost:8080").description("Local Development Server")
            ));
    }
}