package org.ms.post.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for setting up the OpenAPI documentation for the Post API.
 *
 * <p>
 *     This class defines how the OpenAPI documentation is structured, including API metadata
 *     (title, version, description), server information, and contact details.
 * </p>
 */
@Configuration
public class OpenAPIConfiguration {

    /**
     * Defines the OpenAPI bean which sets up the metadata and server information
     * for the Post API.
     *
     * <p>
     *     This method creates a local server configuration, API information (such as title and version),
     *     and sets the contact details of the API owner. This bean will be used by Swagger UI and other
     *     OpenAPI tools to provide API documentation.
     * </p>
     *
     * @return an {@link OpenAPI} object containing API metadata and server information.
     */
    @Bean
    public OpenAPI defineOpenApi() {
        // Define server settings for the API
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development of Post API");

        // Set the contact information for the API
        Contact myContact = new Contact();
        myContact.setName("Jehad Hamayel");
        myContact.setEmail("jehadhamayel95@gmail.com");

        // Provide metadata about the API including title, version, and description
        Info information = new Info()
                .title("Post API")
                .version("1.0")
                .description("This API exposes endpoints for post management")
                .contact(myContact);

        // Create the OpenAPI instance with the defined info and server settings
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
