package com.reply.mobilityondemand.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {

    @JsonProperty("M")
    MALE('M'),

    @JsonProperty("F")
    FEMALE('F');

    private final char code;

    Gender(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    public static Gender fromCode(char code) {
        if (code == 'M') {
            return MALE;
        }
        if (code == 'F') {
            return FEMALE;
        }
        throw new IllegalArgumentException("Unknown gender code " + code);
    }

    //   @JsonCreator
    //   public static Gender fromCode(String code) {
    //       return (code == null || code.length() != 1) ? null : fromCode(code.charAt(0));
    //   }

    @Override
    public String toString() {
        return Character.toString(code);
    }
}