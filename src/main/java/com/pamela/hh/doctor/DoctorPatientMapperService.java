package com.pamela.hh.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorPatientMapperService {

    private final DoctorPatientMapperRepository doctorPatientMapperRepository;

    @Autowired
    public DoctorPatientMapperService(DoctorPatientMapperRepository doctorPatientMapperRepository) {
        this.doctorPatientMapperRepository = doctorPatientMapperRepository;
    }

    public Optional<List<DoctorPatientMapper>> getDoctorPatientsById(Long doctorId) {
        return doctorPatientMapperRepository.findByDoctorId(doctorId);
    }

    public List<DoctorPatientMapper> getDoctorsByPatientId(Long patientId) {
        return doctorPatientMapperRepository.findByPatientId(patientId);
    }

    public DoctorPatientMapper save(DoctorPatientMapper doctorPatientMapper) {
        return doctorPatientMapperRepository.save(doctorPatientMapper);
    }

    public void delete(DoctorPatientMapper doctorPatientMapper) {
        doctorPatientMapperRepository.delete(doctorPatientMapper);
    }

    public DoctorPatientMapper update(DoctorPatientMapper doctorPatientMapper) {
        return doctorPatientMapperRepository.save(doctorPatientMapper);
    }

    public void deleteByPatientId(Long id) {
        doctorPatientMapperRepository.deleteByPatientId(id);
    }

    public void deleteByDoctorId(Long id) {
        doctorPatientMapperRepository.deleteByDoctorId(id);
    }

    public Optional<Boolean> existsByDoctorId(Long id) {
        return doctorPatientMapperRepository.existsByDoctorId(id);
    }

    public Optional<List<DoctorPatientMapper>> getAllDoctorsByPatientId(Long id) {
        return doctorPatientMapperRepository.findAllByPatientId(id);
    }
}
