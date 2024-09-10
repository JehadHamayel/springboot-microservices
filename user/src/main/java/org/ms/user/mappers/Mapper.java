package org.ms.user.mappers;

/**
 * A generic interface for mapping between two object types.
 * <p>
 *     This interface provides a contract for mapping between two objects, typically an entity
 *     and a DTO (Data Transfer Object). It abstracts the conversion logic, allowing for
 *     cleaner, reusable code across different parts of the application.
 * </p>
 *
 * @param <A> the source type, typically an entity.
 * @param <B> the target type, typically a DTO.
 */
public interface Mapper<A, B> {

    /**
     * Maps the source object of type {@code A} to the target object of type {@code B}.
     *
     * @param a the source object to be mapped, typically an entity.
     * @return the mapped target object, typically a DTO.
     */
    B mapTo(A a);

    /**
     * Maps the source object of type {@code B} to the target object of type {@code A}.
     *
     * @param b the source object to be mapped, typically a DTO.
     * @return the mapped target object, typically an entity.
     */
    A mapFrom(B b);
}

