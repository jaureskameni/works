package com.example.SecurityTest.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter
public class RoleNameConverter implements AttributeConverter<RoleName, String> {
    @Override
    public String convertToDatabaseColumn(RoleName attribute) {
        return Optional
                .ofNullable(attribute)
                .map(Enum::name)
                .orElseThrow(null);
    }

    @Override
    public RoleName convertToEntityAttribute(String dataBase) {
        return Optional
                .ofNullable(dataBase)
                .map()
                .orElseThrow(null);
    }
}
