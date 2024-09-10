package org.ms.post.service;

import org.ms.post.model.entity.PostEntity;

import java.util.List;

/**
 * Service interface for managing {@link PostEntity} entities.
 * <p>
 *     This interface defines the business logic methods for handling post entities,
 *     including retrieving, creating, and finding posts.
 * </p>
 *
 */
public interface PostService {

    /**
     * Retrieves all posts.
     *
     * @return a list of {@link PostEntity} objects
     */
    List<PostEntity> getPosts();

    /**
     * Creates a new post and saves it to the repository.
     *
     * @param post the {@link PostEntity} to be created
     * @return the created {@link PostEntity}
     */
    PostEntity createPost(PostEntity post);

    /**
     * Retrieves a post by its unique identifier.
     *
     * @param id the unique identifier of the post
     * @return the {@link PostEntity} if found, or {@code null} if not
     */
    PostEntity getPost(Long id);

    /**
     * Retrieves a post by its unique identifier and the user identifier.
     *
     * @param userId the unique identifier of the user who created the post
     * @param postId the unique identifier of the post
     * @return the {@link PostEntity} if found, or {@code null} if not
     */
    PostEntity getPostByUserIdAndPostId(Long userId, Long postId);
}
