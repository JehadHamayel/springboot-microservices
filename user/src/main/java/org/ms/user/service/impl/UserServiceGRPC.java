package org.ms.user.service.impl;


import io.grpc.stub.StreamObserver;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.ms.grpc_prop.prop.UserRequest;
import org.ms.grpc_prop.prop.UserResponse;
import org.ms.grpc_prop.prop.UserServiceGrpc;
import org.ms.user.UsersApplication;
import org.ms.user.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.ms.user.service.UserService;

@Slf4j
@GrpcService
public class UserServiceGRPC extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void getUserById(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserService userService = UsersApplication.context.getBean(UserService.class);
        UserEntity userEntity = null;
        try {
            userEntity = userService.getUser(request.getUserId());
        } catch (Exception e) {
            log.error("The user with id " + request.getUserId() + " does not exist");
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
