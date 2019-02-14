package bg.bulsi.crc.mapping.converters;

import org.modelmapper.internal.Errors;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

public class LocalDateConverter implements ConditionalConverter<Object, Temporal> {

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return Temporal.class.isAssignableFrom(destinationType)
                && (TemporalAccessor.class.isAssignableFrom(sourceType)
                || sourceType == String.class) ? MatchResult.FULL : MatchResult.NONE;
    }

    @Override
    public Temporal convert(MappingContext<Object, Temporal> context) {
        Object source = context.getSource();

        if (source == null)
            return null;

        Class<?> destinationType = context.getDestinationType();

        if (source instanceof TemporalAccessor) {
            return dateFor((TemporalAccessor) source, destinationType);
        }
        if (source instanceof String) {
            return dateFor((String) source, destinationType);
        }

        throw new Errors().errorMapping(source, destinationType).toMappingException();
    }

    private Temporal dateFor(TemporalAccessor source, Class<?> destinationType) {
        if (destinationType.equals(LocalDate.class))
            return LocalDate.from(source);
        if (destinationType.equals(OffsetDateTime.class) && source.getClass().equals(LocalDate.class)) {
            return OffsetDateTime.of((LocalDate) source, LocalTime.MIN, ZoneOffset.UTC);
        }
        if (destinationType.equals(OffsetDateTime.class) && source.getClass().equals(LocalDateTime.class)) {
            return OffsetDateTime.of((LocalDateTime) source, ZoneOffset.UTC);
        }
        if (destinationType.equals(OffsetDateTime.class) && source.getClass().equals(OffsetDateTime.class)) {
            try {
                return OffsetDateTime.from(source);
            } catch (DateTimeException ex) {
                throw new Errors().errorInstantiatingDestination(destinationType, ex).toMappingException();
            }
        }

        throw new Errors().errorMapping(source, destinationType).toMappingException();
    }

    private LocalDate dateFor(String source, Class<?> destinationType) {

        String sourceString = toString().trim();
        if (sourceString.length() == 0)
            throw new Errors().errorMapping(source, destinationType).toMappingException();

        try {
            return LocalDate.parse(sourceString);
        } catch (DateTimeParseException ex) {
            throw new Errors().addMessage(ex, "String must be in ISO_LOCAL_DATE format to create java.time.LocalDate").toMappingException();
        }
    }
}
