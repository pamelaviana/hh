package com.pamela.hh.heart.healthparameters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface HealthParamRangeRepository extends JpaRepository<HealthParamRange, Long> {

}
