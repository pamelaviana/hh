package com.pamela.hh.patient.medication;

public class MedicationValidator {

    public static void validateMedication(Medication medication){
        if(medication == null) throw new IllegalStateException("Medication cannot be null");
        if(medication.getPatient() == null)
            throw new IllegalStateException("Must assign medication to a patient");
        if(medication.getDoctor() == null)
            throw new IllegalStateException("Must be prescribed by a doctor");
        if(medication.getName() == null|| medication.getName().isBlank())
            throw new IllegalStateException("Medication name cannot be blank");
        if(medication.getDescription() == null || medication.getDescription().isBlank())
            throw new IllegalStateException("Medication description cannot be blank");
        if(medication.getDosage() == null || medication.getDosage().isBlank())
            throw new IllegalStateException("Medication dosage cannot be blank");
        if(medication.getFrequency() == null || medication.getFrequency().isBlank())
            throw new IllegalStateException("Medication frequency cannot be blank");
        if(medication.getDuration() == null || medication.getDuration().isBlank())
            throw new IllegalStateException("Medication duration cannot be blank");
    }
}
