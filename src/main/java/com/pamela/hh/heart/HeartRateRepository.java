package com.pamela.hh.heart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface HeartRateRepository extends JpaRepository<HeartRate, Long> {

    @Query("SELECT h FROM HeartRate h WHERE h.id = ?1")
    Optional<HeartRate> findByUUID(String id);
}
