package com.pamela.hh.hospital.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, String> {

}
