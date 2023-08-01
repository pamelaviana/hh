package com.pamela.hh.patient.fitness;

import com.pamela.hh.patient.Patient;

public class BMICalculator {

    public static double calculateBMI(Patient patient) {
        if(patient.getHeight() == 0) return 0;
        return patient.getWeight() / (patient.getHeight() * patient.getHeight());
    }
}
