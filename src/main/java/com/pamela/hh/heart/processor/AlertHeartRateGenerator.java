package com.pamela.hh.heart.processor;

import com.pamela.hh.alert.heart.AlertHeartRate;
import com.pamela.hh.alert.heart.AlertHeartRateRepository;
import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.heart.healthparameters.HealthParamRange;
import com.pamela.hh.patient.Patient;
import com.pamela.hh.user.User;
import lombok.Builder;
import lombok.Data;

@Builder(builderMethodName = "internalBuilder") @Data
public class AlertHeartRateGenerator {

    private final AlertHeartRateRepository alertHeartRateRepository;
    private HealthParamRange healthParamRange;
    private HeartRate heartRate;
    private Patient patient;
    private User doctor;

    public static AlertHeartRateGeneratorBuilder builder(AlertHeartRateRepository alertHeartRateRepository) {
        return internalBuilder().alertHeartRateRepository(alertHeartRateRepository);
    }

    public void generateAlertIfAny() {
        if (!healthParamRange.isSpmInRange(heartRate.getSbp())
                || !healthParamRange.isDpmInRange(heartRate.getDbp())) {
            AlertHeartRate alertHeartRate = AlertHeartRate.builder()
                    .patient(patient)
                    .doctor(doctor)
                    .heartRate(heartRate)
                    .seen(false)
                    .build();
            alertHeartRateRepository.save(alertHeartRate);
        }
    }
}
