package com.pamela.hh.heart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeartRateService {

    private final HeartRateRepository heartRateRepository;

    @Autowired
    public HeartRateService(HeartRateRepository heartRateRepository) {
        this.heartRateRepository = heartRateRepository;
    }

    public HeartRate save(HeartRate heartRate) {
        return heartRateRepository.save(heartRate);
    }

    public List<HeartRate> getHeartRates() {
        return heartRateRepository.findAll();
    }

    public void deleteByUserId(Long id) {
        heartRateRepository.deleteByUserId(id);
    }

    public Optional<List<HeartRate>> getHeartRatesByPatientId(Long id) {
        return heartRateRepository.findByPatientId(id);
    }
}
