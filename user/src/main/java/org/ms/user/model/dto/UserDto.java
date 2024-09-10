package org.ms.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a user.
 * <p>
 *     This class is used to transfer user data between different layers of the application,
 *     such as from the service layer to the presentation layer. It includes the user's ID and name.
 * </p>
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The name of the user.
     */
    private String name;
}
