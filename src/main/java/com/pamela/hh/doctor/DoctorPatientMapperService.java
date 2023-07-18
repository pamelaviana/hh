package com.pamela.hh.doctor;

import com.pamela.hh.patient.Patient;
import com.pamela.hh.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorPatientMapperService {

    private final DoctorPatientMapperRepository doctorPatientMapperRepository;

    @Autowired
    public DoctorPatientMapperService(DoctorPatientMapperRepository doctorPatientMapperRepository) {
        this.doctorPatientMapperRepository = doctorPatientMapperRepository;
    }

    public List<DoctorPatientMapper> getDoctorPatientsById(Long doctorId) {
        return doctorPatientMapperRepository.findByDoctorId(doctorId);
    }

    public List<DoctorPatientMapper> getDoctorsByPatientId(Long patientId) {
        return doctorPatientMapperRepository.findByPatientId(patientId);
    }

    public DoctorPatientMapper add(DoctorPatientMapper doctorPatientMapper) {
        return doctorPatientMapperRepository.save(doctorPatientMapper);
    }

    public void delete(DoctorPatientMapper doctorPatientMapper) {
        doctorPatientMapperRepository.delete(doctorPatientMapper);
    }

    public DoctorPatientMapper update(DoctorPatientMapper doctorPatientMapper) {
        return doctorPatientMapperRepository.save(doctorPatientMapper);
    }

}
