package com.pamela.hh.patient.medication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Medication getById(String id) {
        return medicationRepository.findById(id).orElse(null);
    }

    public ResponseEntity<String> add(Medication medication) {
        medicationRepository.save(medication);
        return ResponseEntity.ok("Medication added");
    }

    public void delete(String id) {
        medicationRepository.deleteById(id);
    }

    public ResponseEntity<String> update(Medication medication) {
        medicationRepository.save(medication);
        return ResponseEntity.ok("Medication updated");
    }

    public List<Medication> getAll() {
        return medicationRepository.findAll();
    }
}
