package org.ms.user.service;

import org.ms.user.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing user-related operations.
 * <p>
 *     This interface defines the core methods for user management, including
 *     retrieving, creating, and deleting user entities. Implementations of this
 *     interface are responsible for providing the concrete behavior for these
 *     operations, typically interacting with a repository or data source.
 * </p>
 */
@Service
public interface UserService {

    /**
     * Retrieves a list of all users.
     *
     * @return a {@link List} of {@link UserEntity} objects representing all users
     */
    List<UserEntity> getUsers();

    /**
     * Creates a new user.
     *
     * @param user the {@link UserEntity} object to be created
     * @return the created {@link UserEntity} object
     */
    UserEntity createUser(UserEntity user);

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link UserEntity} object with the specified ID, or {@code null} if not found
     */
    UserEntity getUser(Long id);

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return {@code true} if the user was deleted successfully, {@code false} otherwise
     */
    boolean deleteUser(Long id);
}
