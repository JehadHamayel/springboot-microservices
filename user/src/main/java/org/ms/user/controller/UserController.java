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

/**
 * Controller for handling user-related HTTP requests.
 * <p>
 *     This controller provides endpoints to manage users, including retrieving, creating,
 *     and deleting users. It uses {@link UserService} for business logic and {@link KafkaTemplate}
 *     for sending messages to Kafka topics.
 * </p>
 *
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserMapper userMapper;

    /**
     * Retrieves all users.
     * <p>
     *     This endpoint returns a list of all users. The users are mapped from
     *     {@link UserEntity} to {@link UserDto} for the response.
     * </p>
     *
     * @return a {@link List} of {@link UserDto} representing all users
     */
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

    /**
     * Creates a new user.
     * <p>
     *     This endpoint creates a new user based on the provided {@link UserDto}.
     *     If the user's name is less than 4 characters, it returns a bad request response.
     *     Otherwise, it creates the user and returns the created user in the response.
     * </p>
     *
     * @param user the {@link UserDto} to be created
     * @return a {@link ResponseEntity} containing the created {@link UserDto}
     */
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

    /**
     * Retrieves a user by ID.
     * <p>
     *     This endpoint returns the user with the specified ID. If the user is not found,
     *     it returns a not found response.
     * </p>
     *
     * @param id the unique identifier of the user to retrieve
     * @return a {@link ResponseEntity} containing the {@link UserDto} if found, or a not found response
     */
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

    /**
     * Deletes a user by ID.
     * <p>
     *     This endpoint deletes the user with the specified ID. It also sends a message to a Kafka topic
     *     to notify other services about the deletion to delete related posts. If the user is not found,
     *     it returns a not found response.
     * </p>
     *
     * @param id the unique identifier of the user to delete
     * @return a {@link ResponseEntity} with no content if successfully deleted, or a not found response
     */
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
