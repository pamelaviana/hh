package com.pamela.hh.patient;

import com.pamela.hh.doctor.DoctorPatientMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
