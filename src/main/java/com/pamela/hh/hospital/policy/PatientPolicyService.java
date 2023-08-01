package com.pamela.hh.hospital.policy;

import com.pamela.hh.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public PatientPolicy save(PatientPolicy patientPolicy) {
        return patientPolicyRepository.save(patientPolicy);
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

    public Optional<PatientPolicy> getPolicyByNameAndEmail(User user) {
        return patientPolicyRepository.findByFirstNameAndLastNameAndEmail
                (user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
