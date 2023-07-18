package com.pamela.hh.heart.healthparameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HealthParamRangeService {

    private final HealthParamRangeRepository healthParamRangeRepository;

    @Autowired
    public HealthParamRangeService(HealthParamRangeRepository healthParamRangeRepository) {
        this.healthParamRangeRepository = healthParamRangeRepository;
    }

    public ResponseEntity<String> add(HealthParamRange healthParamRange) {
        healthParamRangeRepository.save(healthParamRange);
        return ResponseEntity.ok("Health Parameter Range added successfully");
    }
}
