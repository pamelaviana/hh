package com.pamela.hh.heart;

import com.pamela.hh.heart.processor.HeartRateAlertManager;
import com.pamela.hh.hospital.device.DeviceTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/heartrate")
public class HeartRateController {

    @Autowired
    private ApplicationContext applicationContext;
    private final HeartRateService heartRateService;
    private final DeviceTokenService deviceTokenService;

    @Autowired
    public HeartRateController(HeartRateService heartRateService, DeviceTokenService deviceTokenService) {
        this.heartRateService = heartRateService;
        this.deviceTokenService = deviceTokenService;
    }

    @GetMapping
    public ResponseEntity<String> checkAPI() {
        return ResponseEntity.ok("Heart rate API is working");
    }

    @PostMapping
    public ResponseEntity<String>
    addHeartRate(@RequestHeader("token") String token, @RequestBody HeartRate heartRate) {

        if(Objects.isNull(deviceTokenService.getById(token)))
            return ResponseEntity.badRequest().body("Token is invalid");

        if(deviceTokenService.getById(token).isExpired())
            return ResponseEntity.badRequest().body("Token is expired");


        HeartRate heartRateSaved = heartRateService.add(heartRate);
        HeartRateAlertManager heartRateAlertManager = applicationContext.getBean(HeartRateAlertManager.class);
        heartRateAlertManager.processData(heartRateSaved);

        return ResponseEntity.ok("Heart rate added");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(MissingRequestHeaderException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
