package org.ms.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ms.user.mappers.impl.UserMapper;
import org.ms.user.model.dto.UserDto;
import org.ms.user.model.entity.UserEntity;
import org.ms.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserMapper userMapper;

    @ApiResponse(responseCode = "200", description = "Get all users",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class)) })
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get all users",
            description = "Get all users")
    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Request to get all users");
        List<UserEntity> users = userService.getUsers();
        List<UserDto> userDtos = users.stream().map(userMapper::mapTo).collect(Collectors.toList());
        log.info("Successfully retrieved {} users", userDtos.size());
        return userDtos;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @Tag(name = "Post", description = "Post methods of APIs")
    @Operation(summary = "Create a user",
            description = "Create a new user")
    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "User to add. Cannot be null or empty.",
                    required = true)
            @RequestBody UserDto user
    ) {
        if (user.getName().length() < 4) {
            log.warn("User creation failed due to invalid input: Name length is less than 4 characters");
            return ResponseEntity.badRequest().build();
        }
        log.info("Request to create user with name: {}", user.getName());
        UserEntity userEntity = userMapper.mapFrom(user);
        UserEntity newUser = userService.createUser(userEntity);
        log.info("User created successfully with ID: {}", newUser.getId());
        return ResponseEntity.ok(userMapper.mapTo(newUser));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get user by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get user by id",
            description = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(
            @Parameter(description = "User id", required = true)
            @PathVariable Long id
    ) {
        log.info("Request to get user with ID: {}", id);
        UserEntity user = userService.getUser(id);
        if (user == null) {
            log.error("User with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("User with ID: {} retrieved successfully", id);
        return ResponseEntity.ok(userMapper.mapTo(user));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @Tag(name = "Delete", description = "Delete methods of APIs")
    @Operation(summary = "Delete user by id",
            description = "Delete user by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User id", required = true)
            @PathVariable Long id
    ) {
        log.info("Request to delete user with ID: {}", id);
        this.kafkaTemplate.send("post_user", id.toString());
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            log.error("User with ID: {} not found during deletion", id);
            return ResponseEntity.notFound().build();
        }
        log.info("User with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
