/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartyflip.modules.model.Tag;

import java.lang.reflect.Method;


@Configuration
public class ServiceConfiguration {
    /**
     * Retrieves an instance of {@link ModelMapper} with specific configurations.
     *
     * @return An instance of {@link ModelMapper} with the following configurations:
     * - Field matching enabled
     * - Field access level set to private
     * - Matching strategy set to strict
     * - Custom converter added for converting instances of {@link Tag} to {@link String}
     * <p>
     * Note: The converter implementation extracts the tag name by invoking the {@code getTagName} method
     * on the given tag instance using reflection.
     * <p>
     * If the {@link Tag} class is not found, the converter will not be added.
     */
    @Bean
    ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        try {
            Class<?> tagClass = Class.forName("smartyflip.modules.model.Tag");
            Converter converter = new AbstractConverter<Object, String>() {
                @Override
                protected String convert(Object source) {
                    try {
                        Method getTagName = source.getClass().getMethod("getTagName");
                        return (String) getTagName.invoke(source);
                    } catch (Exception e) {
                        // Handle reflection exceptions
                        return null;
                    }
                }
            };
            modelMapper.addConverter(converter, tagClass, String.class);
        } catch (ClassNotFoundException e) {
            // Handle the case where the Tag class isn't found
        }
        return modelMapper;
    }
}