package com.pamela.hh.patient;

import com.pamela.hh.alert.heart.AlertHeartRateService;
import com.pamela.hh.doctor.DoctorPatientMapperService;
import com.pamela.hh.patient.medication.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final DoctorPatientMapperService doctorPatientMapperService;
    private final MedicationService medicationService;
    private final AlertHeartRateService alertHeartRateService;

    @Autowired
    public PatientService(
            PatientRepository patientRepository,
            DoctorPatientMapperService doctorPatientMapperService,
            MedicationService medicationService,
            AlertHeartRateService alertHeartRateService
    ) {
        this.patientRepository = patientRepository;
        this.doctorPatientMapperService = doctorPatientMapperService;
        this.medicationService = medicationService;
        this.alertHeartRateService = alertHeartRateService;
    }

    public Optional<Patient> getById(Long id) {
    	return patientRepository.findById(id);
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

    public Optional<List<Patient>> getAll() {
    	return Optional.ofNullable(patientRepository.findAll());
    }

    public void deleteByUserId(Long id) {
        Patient patient = patientRepository.findByUserId(id);
        doctorPatientMapperService.deleteByPatientId(patient.getId());
        medicationService.deleteByPatientId(patient.getId());
        alertHeartRateService.deleteByPatientId(patient.getId());
        patientRepository.deleteByUserId(id);
    }

    public Optional<Patient> getByUserId(Long id) {
        return Optional.ofNullable(patientRepository.findByUserId(id));
    }
}
