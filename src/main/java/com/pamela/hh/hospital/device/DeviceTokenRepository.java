package com.pamela.hh.hospital.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, String> {

    @Query("SELECT d FROM DeviceToken d WHERE d.active = true")
    DeviceToken findFirstByActive();
}
