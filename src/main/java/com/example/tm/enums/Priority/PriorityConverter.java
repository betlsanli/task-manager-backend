package com.example.tm.enums.Priority;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PriorityConverter implements AttributeConverter<Priority, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Priority priority) {
        if(priority == null)
            return null;
        return priority.getValue();
    }

    @Override
    public Priority convertToEntityAttribute(Integer integer) {
        if(integer == null)
            return null;
        return Stream.of(Priority.values())
                .filter(p -> p.getValue() == integer)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Priority"));
    }
}
