package com.example.tm.enums.Role;

import com.example.tm.enums.Status.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        if(role == null)
            return null;
        return role.getValue();
    }

    @Override
    public Role convertToEntityAttribute(Integer integer) {
        if(integer == null)
            return null;
        return Stream.of(Role.values())
                .filter(s -> s.getValue() == integer)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

