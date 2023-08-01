package com.pamela.hh.hospital.policy;

import com.pamela.hh.patient.Patient;

import java.time.LocalDate;

public class PatientValidator {

    public static void validatePatient(Patient patient) {
        if (patient == null) throw new IllegalArgumentException("Patient cannot be null");
        if (patient.getPatient() == null || patient.getPatient().getId() == null)
            throw new IllegalArgumentException("Patient must be associated with a user");
        if (patient.getBirthday() == null || patient.getBirthday().isAfter(LocalDate.now().plusYears(1)))
            throw new IllegalArgumentException("Must be at least 1 year old");
        if (patient.getBirthday().isBefore(LocalDate.now().minusYears(150)))
            throw new IllegalArgumentException("Must be older than 150 years old");
        if (patient.getHeight() < 1) throw new IllegalArgumentException("Height must be greater than 1");
        if (patient.getWeight() < 1) throw new IllegalArgumentException("Weight must be greater than 1");
        if (patient.getGender() == null) throw new IllegalArgumentException("Must have a gender");
        if (patient.getSmoker() == null) throw new IllegalArgumentException("Must have a smoking status");
    }
}
