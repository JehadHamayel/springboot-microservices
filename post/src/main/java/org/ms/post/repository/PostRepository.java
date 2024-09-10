package org.ms.post.repository;

import jakarta.transaction.Transactional;
import org.ms.post.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link PostEntity} entities.
 * <p>
 *     This interface provides methods to perform CRUD operations and custom queries on the "posts" table.
 *     It extends {@link JpaRepository} to leverage common database operations and also defines custom queries.
 * </p>
 */
@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    /**
     * Retrieves a post by its unique identifier and the user identifier.
     *
     * @param userId the unique identifier of the user who created the post
     * @param postId the unique identifier of the post
     * @return an {@link Optional} containing the post if found, or empty if not
     */
    @Query("SELECT p FROM PostEntity p WHERE p.id = :postId AND p.userId = :userId")
    Optional<PostEntity> getPostByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

    /**
     * Deletes posts created by a specific user.
     * <p>
     *     This method performs a bulk delete operation.
     * </p>
     *
     * @param userId the unique identifier of the user whose posts are to be deleted
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PostEntity p WHERE p.userId = :userId")
    void deletePostByUserId(@Param("userId") Long userId);
}
