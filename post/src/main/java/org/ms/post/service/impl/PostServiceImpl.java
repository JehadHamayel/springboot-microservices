package org.ms.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.ms.post.model.entity.PostEntity;
import org.ms.post.repository.PostRepository;
import org.ms.post.service.PostService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link PostService} interface.
 * <p>
 *     This class provides the business logic for managing {@link PostEntity} entities,
 *     including retrieving, creating, and deleting posts. It also listens for Kafka messages
 *     to perform operations based on incoming messages.
 * </p>
 *
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    /**
     * Retrieves all posts from the repository.
     *
     * @return a list of {@link PostEntity} objects
     */
    @Override
    public List<PostEntity> getPosts() {
        return postRepository.findAll();
    }

    /**
     * Creates a new post and saves it to the repository.
     *
     * @param post the {@link PostEntity} to be created
     * @return the created {@link PostEntity}
     */
    @Override
    public PostEntity createPost(PostEntity post) {
        return postRepository.save(post);
    }

    /**
     * Retrieves a post by its unique identifier.
     *
     * @param id the unique identifier of the post
     * @return the {@link PostEntity} if found, or {@code null} if not
     */
    @Override
    public PostEntity getPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a post by its unique identifier and the user identifier.
     *
     * @param userId the unique identifier of the user who created the post
     * @param postId the unique identifier of the post
     * @return the {@link PostEntity} if found, or {@code null} if not
     */
    @Override
    public PostEntity getPostByUserIdAndPostId(Long userId, Long postId) {
        Optional<PostEntity> postEntityOptional = postRepository.getPostByUserIdAndPostId(userId, postId);
        return postEntityOptional.orElse(null);
    }

    /**
     * Listens for Kafka messages on the "post_user" topic to delete posts by user identifier.
     * <p>
     *     This method is triggered by incoming Kafka messages. It parses the message to extract
     *     the user identifier and deletes all posts associated with that user.
     * </p>
     *
     * @param message the Kafka message containing the user identifier
     */
    @KafkaListener(topics = "post_user", groupId = "post_user_group")
    public void listen(String message) {
        Long userId = Long.parseLong(message);
        postRepository.deletePostByUserId(userId);
    }

}
