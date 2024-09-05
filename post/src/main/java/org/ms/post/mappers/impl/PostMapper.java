package org.ms.post.mappers.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.ms.post.mappers.Mapper;
import org.ms.post.model.dto.PostDto;
import org.ms.post.model.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper implements Mapper<PostEntity, PostDto> {

    private final ModelMapper modelMapper;

    @Override
    public PostDto mapTo(PostEntity postEntity) {
        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostEntity mapFrom(PostDto postDto) {
        return modelMapper.map(postDto, PostEntity.class);
    }
}
