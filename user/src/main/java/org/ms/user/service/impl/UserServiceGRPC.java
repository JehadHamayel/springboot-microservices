package org.ms.user.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.ms.grpc_prop.prop.UserRequest;
import org.ms.grpc_prop.prop.UserResponse;
import org.ms.grpc_prop.prop.UserServiceGrpc;
import org.ms.user.UsersApplication;
import org.ms.user.model.entity.UserEntity;
import org.ms.user.service.UserService;

/**
 * gRPC service implementation for managing user-related operations.
 * <p>
 *     This class implements the {@link UserServiceGrpc.UserServiceImplBase} and provides
 *     the gRPC endpoint for retrieving user information by user ID. It interacts with the
 *     {@link UserService} to fetch user details and responds to gRPC requests accordingly.
 * </p>
 *
 */
@Slf4j
@GrpcService
public class UserServiceGRPC extends UserServiceGrpc.UserServiceImplBase {

    /**
     * Retrieves a user by ID and sends the response via gRPC.
     * <p>
     *     This method processes a {@link UserRequest} to fetch a {@link UserEntity} from the
     *     {@link UserService}. It then builds a {@link UserResponse} with the user details or
     *     an error response if the user is not found. The response is sent to the client using
     *     the provided {@link StreamObserver}.
     * </p>
     *
     * @param request the {@link UserRequest} containing the user ID
     * @param responseObserver the {@link StreamObserver} to send the {@link UserResponse}
     */
    @Override
    public void getUserById(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserService userService = UsersApplication.context.getBean(UserService.class);
        UserEntity userEntity = null;
        try {
            userEntity = userService.getUser(request.getUserId());
        } catch (Exception e) {
            log.error("The user with id {} does not exist", request.getUserId());
        }

        if (userEntity == null) {
            responseObserver.onNext(UserResponse.newBuilder().setUserId(-1L).build());
            responseObserver.onCompleted();
        } else {
            UserResponse empResp = UserResponse.newBuilder()
                    .setUserId(userEntity.getId())
                    .setName(userEntity.getName())
                    .build();
            responseObserver.onNext(empResp);

            responseObserver.onCompleted();
        }
    }
}
