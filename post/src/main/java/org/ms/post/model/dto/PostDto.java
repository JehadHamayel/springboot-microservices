package org.ms.post.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a post.
 * <p>
 *     This class is used to transfer post data between different layers of the application.
 * </p>
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    /**
     * The unique identifier of the post.
     */
    private Long id;

    /**
     * The unique identifier of the user who created the post.
     */
    private Long userId;

    /**
     * The body content of the post.
     */
    private String body;
}
