package com.pamela.hh.alert.heart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AlertHeartRateRepository extends JpaRepository<AlertHeartRate, String> {

}
