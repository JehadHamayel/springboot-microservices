package org.ms.post.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.ms.post.mappers.Mapper;
import org.ms.post.model.dto.PostDto;
import org.ms.post.model.entity.PostEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link PostEntity} and {@link PostDto}.
 * <p>
 *     This class uses {@link ModelMapper} to handle the conversion between the entity and DTO.
 *     It implements the {@link Mapper} interface, providing methods to map {@link PostEntity}
 *     to {@link PostDto} and vice versa.
 * </p>
 *
 */
@Component
@RequiredArgsConstructor
public class PostMapper implements Mapper<PostEntity, PostDto> {

    private final ModelMapper modelMapper;

    /**
     * Maps a {@link PostEntity} to a {@link PostDto}.
     * <p>
     *     This method uses the {@link ModelMapper} to convert the entity into a DTO,
     *     which can then be used in the API layer to transfer data.
     * </p>
     *
     * @param postEntity the entity object to be mapped to a DTO.
     * @return the mapped {@link PostDto} object.
     */
    @Override
    public PostDto mapTo(PostEntity postEntity) {
        return modelMapper.map(postEntity, PostDto.class);
    }

    /**
     * Maps a {@link PostDto} to a {@link PostEntity}.
     *<p>
     *     This method uses the {@link ModelMapper} to convert the DTO into an entity,
     *     which can be persisted in the database.
     *</p>
     *
     * @param postDto the data transfer object to be mapped to an entity.
     * @return the mapped {@link PostEntity} object.
     */
    @Override
    public PostEntity mapFrom(PostDto postDto) {
        return modelMapper.map(postDto, PostEntity.class);
    }
}
