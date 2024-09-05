package org.ms.user.mappers.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.ms.user.mappers.Mapper;
import org.ms.user.model.dto.UserDto;
import org.ms.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
