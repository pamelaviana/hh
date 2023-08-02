package com.pamela.hh.patient.medication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Optional<Medication> getById(String id) {
        return medicationRepository.findById(id);
    }

    public Medication save(Medication medication) {
        return medicationRepository.save(medication);
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

    public void deleteByPatientId(Long id) {
        medicationRepository.deleteByPatientId(id);
    }

    public void deleteByDoctorId(Long id) {
        medicationRepository.deleteByDoctorId(id);
    }

    public Optional<ArrayList<Medication>> getMedicationsByPatientId(Long id) {
        return medicationRepository.findByPatientId(id);
    }

    public Optional<Boolean> existsByDoctorId(Long id) {
        return medicationRepository.existsByDoctorId(id);
    }
}
