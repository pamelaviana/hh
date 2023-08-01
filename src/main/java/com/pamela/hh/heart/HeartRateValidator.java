package com.pamela.hh.heart;

public class HeartRateValidator {

    public static void validateHeartRate(HeartRate heartRate) {
        if (heartRate == null)
            throw new IllegalArgumentException("HeartRate must not be null");
        if (heartRate.getTimestamp() == null)
            throw new IllegalArgumentException("Timestamp must not be null");
        if (heartRate.getSbp() == null)
            throw new IllegalArgumentException("SBP must not be null");
        if (heartRate.getDbp() == null)
            throw new IllegalArgumentException("DBP must not be null");
        if (heartRate.getSbp() < 0 || heartRate.getSbp() > 500) {
            throw new IllegalArgumentException("SBP must be between 0 and 500");
        }
        if (heartRate.getDbp() < 0 || heartRate.getDbp() > 500) {
            throw new IllegalArgumentException("DBP must be between 0 and 500");
        }
    }
}
