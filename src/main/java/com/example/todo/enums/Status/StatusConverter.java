package com.example.todo.enums.Status;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, Integer> {


    @Override
    public Integer convertToDatabaseColumn(Status status) {
        if(status == null)
            return null;
        return status.getValue();
    }

    @Override
    public Status convertToEntityAttribute(Integer integer) {
        if(integer == null)
            return null;
        return Stream.of(Status.values())
                .filter(s -> s.getValue() == integer)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
