package com.pamela.hh.alert.heart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertHeartRateService {

    private final AlertHeartRateRepository alertHeartRateRepository;

    @Autowired
    public AlertHeartRateService(AlertHeartRateRepository alertHeartRateRepository) {
        this.alertHeartRateRepository = alertHeartRateRepository;
    }

    public AlertHeartRate findById(String id) {
        return alertHeartRateRepository.findById(id).orElse(null);
    }

    public AlertHeartRate add(AlertHeartRate alertHeartRate) {
        return alertHeartRateRepository.save(alertHeartRate);
    }

    public AlertHeartRate update(AlertHeartRate alertHeartRate) {
        return alertHeartRateRepository.save(alertHeartRate);
    }

    public void delete(String id) {
        alertHeartRateRepository.deleteById(id);
    }

    public List<AlertHeartRate> findAll() {
        return alertHeartRateRepository.findAll();
    }

    public void deleteByPatientId(Long id) {
        alertHeartRateRepository.deleteByPatientId(id);
    }


    public Optional<List<AlertHeartRate>> getAlertHeartRateByDoctor(Long id) {
        return alertHeartRateRepository.getAlertHeartRateByDoctor(id);
    }

    public Optional<AlertHeartRate> getAlertHeartRateByPatient(String id) {
        return alertHeartRateRepository.getAlertHeartRateByPatient(id);
    }
}
