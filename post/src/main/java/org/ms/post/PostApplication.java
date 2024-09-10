package org.ms.post;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Post application.
 * <p>
 *     This class is used to bootstrap the Spring Boot application by running the
 * {@link SpringApplication}. It starts the application and initializes the
 * necessary components to handle incoming requests.
 * </p>
 *
 */
@SpringBootApplication
public class PostApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }

}
