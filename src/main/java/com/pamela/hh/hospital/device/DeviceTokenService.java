package com.pamela.hh.hospital.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    @Autowired
    public DeviceTokenService(DeviceTokenRepository deviceTokenRepository) {
        this.deviceTokenRepository = deviceTokenRepository;
    }

    public DeviceToken getById(String id) {
    	return deviceTokenRepository.findById(id).orElse(null);
    }

    public DeviceToken save(DeviceToken deviceToken) {
        return deviceTokenRepository.save(deviceToken);
    }

    public void delete(String id) {
    	deviceTokenRepository.deleteById(id);
    }

    public ResponseEntity<String> update(DeviceToken deviceToken) {
    	deviceTokenRepository.save(deviceToken);
    	return ResponseEntity.ok("Device token updated successfully");
    }

    public List<DeviceToken> getAll() {
    	return deviceTokenRepository.findAll();
    }

    public DeviceToken getFirstActivetedDeviceToken() {
        return deviceTokenRepository.findFirstByActive();
    }
}
