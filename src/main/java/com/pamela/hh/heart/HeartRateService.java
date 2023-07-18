package com.pamela.hh.heart;

import com.pamela.hh.heart.processor.AlertHeartRateGenerator;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public HeartRate add(HeartRate heartRate) {
        return heartRateRepository.save(heartRate);
    }

    public List<HeartRate> getHeartRates() {
        return heartRateRepository.findAll();
    }
}
