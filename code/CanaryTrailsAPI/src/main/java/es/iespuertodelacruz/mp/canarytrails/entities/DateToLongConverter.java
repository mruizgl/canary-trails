package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Date;

@Converter(autoApply = true)
public class DateToLongConverter implements AttributeConverter<Date, Long> {

    @Override
    public Long convertToDatabaseColumn(Date date) {
        return (date == null) ? null : date.getTime();
    }

    @Override
    public Date convertToEntityAttribute(Long timestamp) {
        return (timestamp == null) ? null : new Date(timestamp);
    }



}
