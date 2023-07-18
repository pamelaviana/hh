package com.pamela.hh.alert.heart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
