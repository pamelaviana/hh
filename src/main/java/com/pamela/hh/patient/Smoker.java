package com.pamela.hh.patient;

import java.util.EnumSet;

public enum Smoker {
    YES,
    NO,
    LIGHT,
    MODERATE,
    HEAVY;

    public static EnumSet<Smoker> getSmokers() {
        return EnumSet.of(YES, NO, LIGHT, MODERATE, HEAVY);
    }
}
