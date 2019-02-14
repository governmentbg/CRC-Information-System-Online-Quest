package bg.bulsi.crc.config;

import bg.bulsi.crc.mapping.MappingDefinition;
import bg.bulsi.crc.mapping.converters.LocalDateConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan("bg.bulsi.crc.mapping")
public class ModelMapperConfig {

    @Bean
    public ModelMapper mapper(List<MappingDefinition> definitions) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setFullTypeMatchingRequired(true);

        // Converters
        //mapper.addConverter(new LocalDateConverter(), Object.class, LocalDate.class);
        mapper.getConfiguration().getConverters().add(new LocalDateConverter());

        definitions.forEach(mappingDefinition -> mappingDefinition.mapping(mapper));

        return mapper;
    }
}