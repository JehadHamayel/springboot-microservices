package org.ms.user.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8090");
        server.setDescription("Development of User API");

        Contact myContact = new Contact();
        myContact.setName("Jehad Hamayel");
        myContact.setEmail("jehadhamayel95@gmail.com");

        Info information = new Info()
                .title("User API")
                .version("1.0")
                .description("This API exposes endpoints for user management")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
