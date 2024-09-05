package org.ms.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ms.post.mappers.impl.PostMapper;
import org.ms.post.model.dto.PostDto;
import org.ms.post.model.entity.PostEntity;
import org.ms.post.service.PostService;
import org.ms.post.service.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;

    @ApiResponse(responseCode = "200", description = "Get all posts",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostEntity.class)) })
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get all posts",
            description = "Get all posts")
    @GetMapping("/posts")
    public List<PostDto> getPosts() {
        log.info("Fetching all posts");
        List<PostEntity> posts = postService.getPosts();
        return posts.stream().map(postMapper::mapTo).collect(Collectors.toList());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @Tag(name = "Post", description = "Post methods of APIs")
    @Operation(summary = "Create a post",
            description = "Create a new post")
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post) {
        log.info("Creating post with userId: {} and body length: {}", post.getUserId(), post.getBody().length());
        if (post.getBody().length() < 10 || post.getBody().length() > 1000) {
            log.warn("Post creation failed due to body length validation. Body length: {}", post.getBody().length());
            return ResponseEntity.badRequest().build();
        }
        boolean exist = userService.getUserById(post.getUserId());
        if (!exist) {
            log.warn("Post creation failed. User with id {} does not exist", post.getUserId());
            return ResponseEntity.badRequest().build();
        }
        PostEntity newPost = postService.createPost(postMapper.mapFrom(post));
        log.info("Post created with id: {}", newPost.getId());
        return ResponseEntity.ok(postMapper.mapTo(newPost));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get post by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content)})
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get post by id",
            description = "Get post by id")
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        log.info("Fetching post with id: {}", id);
        PostEntity post = postService.getPost(id);
        if (post == null) {
            log.error("Post with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postMapper.mapTo(post));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get post by user id and post id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content)})
    @Tag(name = "Get", description = "Get methods of APIs")
    @Operation(summary = "Get post by user id and post id",
            description = "Get post by user id and post id")
    @GetMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity<PostDto> getPostByUserIdAndPostId(@PathVariable Long userId, @PathVariable Long postId) {
        log.info("Fetching post with userId: {} and postId: {}", userId, postId);
        PostEntity post = postService.getPostByUserIdAndPostId(userId, postId);
        if (post == null) {
            log.error("Post with userId {} and postId {} not found", userId, postId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postMapper.mapTo(post));
    }
}