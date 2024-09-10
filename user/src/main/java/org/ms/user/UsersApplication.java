package org.ms.user;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.ms.user.service.impl.UserServiceGRPC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * Entry point for the User service application.
 * <p>
 *     This class sets up and runs a Spring Boot application along with a gRPC server
 *     for handling user-related gRPC requests. It initializes the Spring application context
 *     and starts the gRPC server on port 9090.
 * </p>
 *
 */
@SpringBootApplication
public class UsersApplication {

    /**
     * The Spring application context.
     * <p>
     *     This static field holds the application context, which can be accessed
     *     from other parts of the application.
     * </p>
     */
    public static ApplicationContext context;

    /**
     * Main method to run the application.
     * <p>
     *     This method starts the Spring Boot application and the gRPC server. It
     *     builds and starts a gRPC server listening on port 9090 and adds the
     *     {@link UserServiceGRPC} service to handle gRPC requests. The server runs
     *     until it is terminated.
     * </p>
     *
     * @param args command-line arguments
     * @throws IOException if an I/O error occurs while starting the server
     * @throws InterruptedException if the server is interrupted while waiting
     *         for termination
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        context = SpringApplication.run(UsersApplication.class, args);
        Server server = ServerBuilder
                .forPort(9090)
                .addService(new UserServiceGRPC()).build();
        server.start();
        server.awaitTermination();
    }
}
