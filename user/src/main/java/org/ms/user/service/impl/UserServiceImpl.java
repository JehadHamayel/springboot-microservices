package org.ms.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.ms.user.model.entity.UserEntity;
import org.ms.user.repository.UserRepository;
import org.ms.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface.
 * <p>
 *     This class provides concrete implementations for managing user data,
 *     including retrieving, creating, and deleting user entities. It interacts
 *     with the {@link UserRepository} to perform CRUD operations on user data.
 * </p>
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Retrieves a list of all users.
     *
     * @return a {@link List} of {@link UserEntity} objects representing all users
     */
    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a new user.
     *
     * @param user the {@link UserEntity} object to be created
     * @return the created {@link UserEntity} object
     */
    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link UserEntity} object with the specified ID, or {@code null} if not found
     */
    @Override
    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return {@code true} if the user was deleted successfully, {@code false} otherwise
     */
    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
