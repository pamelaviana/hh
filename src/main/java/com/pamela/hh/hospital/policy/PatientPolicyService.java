package com.pamela.hh.hospital.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientPolicyService {

    private final PatientPolicyRepository patientPolicyRepository;

    @Autowired
    public PatientPolicyService(PatientPolicyRepository patientPolicyRepository) {
        this.patientPolicyRepository = patientPolicyRepository;
    }

    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Patient Policy get");
    }

    public ResponseEntity<String> add(PatientPolicy patientPolicy) {
        patientPolicyRepository.save(patientPolicy);
        return ResponseEntity.ok("Patient Policy added");
    }

    public ResponseEntity<String> update(PatientPolicy patientPolicy) {
        patientPolicyRepository.save(patientPolicy);
        return ResponseEntity.ok("Patient Policy updated");
    }

    public ResponseEntity<String> delete(Long id) {
        patientPolicyRepository.deleteById(id);
        return ResponseEntity.ok("Patient Policy deleted");
    }

    public List<PatientPolicy> getAll() {
        return patientPolicyRepository.findAll();
    }

}
