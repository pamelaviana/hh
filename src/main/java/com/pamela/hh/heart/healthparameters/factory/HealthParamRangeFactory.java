package com.pamela.hh.heart.healthparameters.factory;

import com.pamela.hh.heart.healthparameters.HealthParamRange;
import com.pamela.hh.heart.healthparameters.HealthParamRangeNullObject;
import com.pamela.hh.patient.Patient;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class HealthParamRangeFactory {

    private final List<HealthParamRange> healthParamRangeList;

    public HealthParamRangeFactory(List<HealthParamRange> healthParamRangeList) {
        this.healthParamRangeList = healthParamRangeList;
    }

    public HealthParamRange make(Patient patient) {
        for (HealthParamRange healthParamRange: healthParamRangeList) {
            if(checkAgeRange(healthParamRange, patient) && checkGender(healthParamRange, patient)) {
                return healthParamRange;
            }
        }
        return new HealthParamRangeNullObject();
    }

    private boolean checkGender(HealthParamRange healthParamRange, Patient patient) {
        return healthParamRange.isGenderEqual(patient.getGender());
    }

    private boolean checkAgeRange(HealthParamRange healthParamRange, Patient patient) {
        int age = Period.between(patient.getBirthday(), LocalDate.now()).getYears();
        return healthParamRange.isAgeInRange(age);
    }
}
