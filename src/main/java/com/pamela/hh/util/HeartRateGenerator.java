package com.pamela.hh.util;

import com.pamela.hh.heart.HeartRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
public class HeartRateGenerator {

    @Builder.Default private int yearMin = LocalDateTime.now().getYear();
    @Builder.Default private int yearMax = LocalDateTime.now().getYear() + 2;

    @Builder.Default private int monthMin = 1;
    @Builder.Default private int monthMax = 13;

    @Builder.Default private int dayMin = 1;
    @Builder.Default private int dayMax = 32;
    @Builder.Default private int hourMin = 1;
    @Builder.Default private int hourMax = 24;
    @Builder.Default private int minuteMin = 1;
    @Builder.Default private int minuteMax = 60;
    @Builder.Default private int secondMin = 1;
    @Builder.Default private int secondMax = 60;
    @Builder.Default private int sbpMin = 110;
    @Builder.Default private int sbpMax = 200;
    @Builder.Default private int dbpMin = 60;
    @Builder.Default private int dbpMax = 100;

    private int getRand(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public List<HeartRate> generateRand(int size) {
        List<HeartRate> heartRates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            heartRates.add(HeartRate.builder()
                    .sbp(getRand(sbpMin, sbpMax))
                    .dbp(getRand(dbpMin, dbpMax))
                    .timestamp(getRandDate())
                    .build());
        }
        return heartRates;
    }

    private LocalDateTime getRandDate() {
        int year = getRand(yearMin, yearMax);
        int month = getRand(monthMin, monthMax);
        int day = getRand(dayMin, dayMax);
        int hour = getRand(hourMin, hourMax);
        int minute = getRand(minuteMin, minuteMax);
        int second = getRand(secondMin, secondMax);
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
}
