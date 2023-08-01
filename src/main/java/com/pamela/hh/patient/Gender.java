package com.pamela.hh.patient;

import java.util.EnumSet;

public enum Gender {
    FEMALE,
    MALE,
    OTHER;

    public static EnumSet<Gender> getGenders() {
        return EnumSet.of(FEMALE, MALE, OTHER);
    }
}
