package za.co.learnings.todolistbatch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        var contact = new Contact();
        contact.setName("TODO List Batch ");
        contact.setUrl("https://www.linkedin.com/in/arnold-madisa-b9064446/");
        contact.setEmail("atmadisa1@gmail.com");
        var api = new OpenAPI()
                .tags(null)
                .info(new Info()
                        .title("TODO List Batch")
                        .description("Spring boot batch application")
                        .version("1.0")
                        .contact(contact));
        return api;
    }
}
