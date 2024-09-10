package org.ms.post.service.impl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.ms.grpc_prop.prop.UserRequest;
import org.ms.grpc_prop.prop.UserResponse;
import org.ms.grpc_prop.prop.UserServiceGrpc;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with the gRPC UserService.
 * <p>
 *     This class provides a method to retrieve user information from a gRPC server.
 *     It connects to the gRPC server, sends a request to get user details by ID,
 *     and processes the response to determine if the user exists.
 * </p>
 *
 */
@Service
public class UserService {

    /**
     * Retrieves a user by its unique identifier using gRPC.
     * <p>
     *     This method creates a gRPC channel to the server, sends a request with the user ID,
     *     and checks if the user exists based on the response. The channel is shut down
     *     after the request is processed.
     * </p>
     *
     * @param id the unique identifier of the user
     * @return {@code true} if the user exists, {@code false} otherwise
     */
    public boolean getUserById(Long id) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        UserResponse userResponse = stub.getUserById(UserRequest.newBuilder().setUserId(id).build());
        channel.shutdown();

        return userResponse.getUserId() != -1;
    }
}
