package org.ms.user;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.ms.user.service.impl.UserServiceGRPC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class UsersApplication {
    public static ApplicationContext context;
    public static void main(String[] args) throws IOException, InterruptedException {
        context = SpringApplication.run(UsersApplication.class, args);
        Server server = ServerBuilder
                .forPort(9090)
                .addService(new UserServiceGRPC()).build();
        server.start();
        server.awaitTermination();
    }

}
