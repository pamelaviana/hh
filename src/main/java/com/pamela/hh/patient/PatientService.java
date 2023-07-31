package com.pamela.hh.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient getById(Long id) {
    	return patientRepository.findById(id).orElse(null);
    }

    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    public void delete(Long id) {
    	patientRepository.deleteById(id);
    }

    public void update(Patient patient) {
    	patientRepository.save(patient);
    }

    public List<Patient> getAll() {
    	return patientRepository.findAll();
    }

}
