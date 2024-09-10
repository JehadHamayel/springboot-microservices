package org.ms.user.repository;

import org.ms.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link UserEntity} entities.
 * <p>
 *     This interface extends {@link JpaRepository}, providing CRUD operations and additional
 *     data access methods for {@link UserEntity} objects. Spring Data JPA will automatically
 *     provide implementations for these methods at runtime.
 * </p>
 *

 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
