package org.ms.user.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.ms.user.mappers.Mapper;
import org.ms.user.model.dto.UserDto;
import org.ms.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link UserEntity} and {@link UserDto}.
 * <p>
 *     This class uses {@link ModelMapper} to handle the conversion between the entity and DTO.
 *     It implements the {@link Mapper} interface, providing methods to map {@link UserEntity}
 *     to {@link UserDto} and vice versa.
 * </p>
 *
 */
@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    /**
     * Converts a {@link UserEntity} to a {@link UserDto}.
     * <p>
     *     This method uses {@link ModelMapper} to map the properties from the entity to the DTO.
     * </p>
     *
     * @param userEntity the {@link UserEntity} to be converted
     * @return the corresponding {@link UserDto}
     */
    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    /**
     * Converts a {@link UserDto} to a {@link UserEntity}.
     * <p>
     *     This method uses {@link ModelMapper} to map the properties from the DTO to the entity.
     * </p>
     *
     * @param userDto the {@link UserDto} to be converted
     * @return the corresponding {@link UserEntity}
     */
    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
