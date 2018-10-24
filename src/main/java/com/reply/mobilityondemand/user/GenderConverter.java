package com.reply.mobilityondemand.user;

import javax.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<Gender, Character> {

    @Override
    public Character convertToDatabaseColumn(Gender gender) {
        return (gender == null) ? null : gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(Character code) {
        return (code == null) ? null : Gender.fromCode(code);
    }
}
