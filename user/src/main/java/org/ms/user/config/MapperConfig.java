package org.ms.user.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up a ModelMapper bean.
 * <p>
 *     This class configures the ModelMapper, which is used for mapping objects between
 *     different layers of the application, such as mapping between DTOs (Data Transfer Objects)
 *     and entity models.
 * </p>
 */
@Configuration
public class MapperConfig {

    /**
     * Configures and provides a {@link ModelMapper} bean.
     * <p>
     *     The ModelMapper is set with a loose matching strategy, which means it will map
     *     properties with similar names more flexibly, even if they do not match exactly.
     *     This is particularly useful when mapping between objects with slightly different structures.
     * </p>
     *
     * @return a configured {@link ModelMapper} instance with loose matching strategy.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}

