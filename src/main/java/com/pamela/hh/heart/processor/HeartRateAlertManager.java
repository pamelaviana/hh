package com.pamela.hh.heart.processor;

import com.pamela.hh.alert.heart.AlertHeartRateRepository;
import com.pamela.hh.doctor.DoctorPatientMapper;
import com.pamela.hh.doctor.DoctorPatientMapperRepository;
import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.heart.healthparameters.HealthParamRange;
import com.pamela.hh.heart.healthparameters.HealthParamRangeRepository;
import com.pamela.hh.heart.healthparameters.factory.HealthParamRangeFactory;
import com.pamela.hh.patient.Patient;
import com.pamela.hh.patient.PatientRepository;
import com.pamela.hh.patient.PatientService;
import com.pamela.hh.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeartRateAlertManager {

    private AlertHeartRateRepository alertHeartRateRepository;
    private PatientService patientService;
    private HealthParamRangeRepository healthParamRangeRepository;
    private DoctorPatientMapperRepository doctorPatientMapperRepository;

    public void processData(HeartRate heartRate){

        List<HealthParamRange> healthParamRanges = healthParamRangeRepository.findAll();

        List<DoctorPatientMapper> doctorPatientMappers = doctorPatientMapperRepository
                .findByPatientId(heartRate.getUserId());

        Patient patient = patientService.getByUserId(heartRate.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        HealthParamRangeFactory healthParamRangeFactory = new HealthParamRangeFactory(healthParamRanges);
        HealthParamRange healthParamRange = healthParamRangeFactory.make(patient);
        heartRate.setUser(patient.getPatient());

        for(DoctorPatientMapper doctorPatientMapper : doctorPatientMappers) {
            AlertHeartRateGenerator.builder(alertHeartRateRepository)
                    .healthParamRange(healthParamRange)
                    .heartRate(heartRate)
                    .patient(patient)
                    .doctor(doctorPatientMapper.getDoctor())
                    .build()
                    .generateAlertIfAny();
        }
    }

    @Autowired
    public void setAlertHeartRateRepository(AlertHeartRateRepository alertHeartRateRepository) {
        this.alertHeartRateRepository = alertHeartRateRepository;
    }

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setHealthParamRangeRepository(HealthParamRangeRepository healthParamRangeRepository) {
        this.healthParamRangeRepository = healthParamRangeRepository;
    }

    @Autowired
    public void setDoctorPatientMapperRepository(DoctorPatientMapperRepository doctorPatientMapperRepository) {
        this.doctorPatientMapperRepository = doctorPatientMapperRepository;
    }
}
