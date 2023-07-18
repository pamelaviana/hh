package com.pamela.hh.hospital.policy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PatientPolicyRepository extends JpaRepository<PatientPolicy, Long> {

}
