package com.pamela.hh.heart.stats;

import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.heart.stats.group.MonthlyGroup;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeartRateStatTest {

    @Test
    public void testGroupMonth() {

        List<HeartRate> heartRates = getHeartRates(30);
        HeartRateStat heartRateStat = new HeartRateStat(heartRates);
        heartRateStat.sort(new HeartRateComparator.Timestamp())
                .groupBy(new MonthlyGroup()).sortGrouped().end();

        Map<Integer, List<HeartRate>> grouped = heartRateStat.getImmutableGrouped();

        for(Map.Entry<Integer, List<HeartRate>> entry : grouped.entrySet()) {
            System.out.print(entry.getKey() + "\t");
            for(HeartRate heartRate : entry.getValue()) {
                System.out.print(heartRate.getTimestamp() + "\t");
            }
            System.out.println();
        }
    }

    private List<HeartRate> getHeartRates(int size) {
        List<HeartRate> heartRates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            heartRates.add(HeartRate.builder()
                    .sbp(getRand(110, 200))
                    .dbp(getRand(60, 100))
                    .timestamp(getRandDate(2020, 2021))
                    .build());
        }
        return heartRates;
    }

    private int getRand(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private LocalDateTime getRandDate(int minYear, int maxYear) {
        int year = getRand(minYear, maxYear);
        int month = getRand(1, 12);
        int day = getRand(1, 28);
        int hour = getRand(0, 23);
        int minute = getRand(0, 59);
        int second = getRand(0, 59);
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

}
