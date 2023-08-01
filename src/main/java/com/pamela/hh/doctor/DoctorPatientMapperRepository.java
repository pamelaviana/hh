package com.pamela.hh.doctor;

import com.pamela.hh.patient.Patient;
import com.pamela.hh.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DoctorPatientMapperRepository extends JpaRepository<DoctorPatientMapper, Long> {

    @Query("SELECT dpm FROM DoctorPatientMapper dpm WHERE dpm.doctor.id = ?1")
    List<DoctorPatientMapper> findByDoctorId(Long doctorId);

    @Query("SELECT dpm FROM DoctorPatientMapper dpm WHERE dpm.patient.id = ?1")
    List<DoctorPatientMapper> findByPatientId(Long patientId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DoctorPatientMapper dpm WHERE dpm.patient.id = ?1")
    void deleteByPatientId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM DoctorPatientMapper dpm WHERE dpm.doctor.id = ?1")
    void deleteByDoctorId(Long id);
}
