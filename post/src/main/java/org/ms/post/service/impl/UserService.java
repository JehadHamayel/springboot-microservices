package org.ms.post.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import io.grpc.stub.StreamObserver;
import org.ms.grpc_prop.prop.UserRequest;
import org.ms.grpc_prop.prop.UserResponse;
import org.ms.grpc_prop.prop.UserServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public boolean getUserById(Long id) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        UserResponse userResponse = stub.getUserById(UserRequest.newBuilder().setUserId(id).build());
        channel.shutdown();

        return userResponse.getUserId() != -1;

    }
}
