package com.pamela.hh.heart.stats;

import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.util.HeartRateGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class HeartRateStatTest {

    @Test
    public void testGroupMonth() {
        List<HeartRate> heartRates = HeartRateGenerator.builder()
                .yearMin(2020).yearMax(2024)
                .monthMin(10).monthMax(13)
                .build().generateRand(20);

        Map<String, HeartRateAvg> grouped = HeartRateStat.builder().year(2023)
                .build().getAverageGroupedByMonth(heartRates);

        for(Map.Entry<String, HeartRateAvg> entry : grouped.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    @Test
    public void testGroupDay() {
        List<HeartRate> heartRates = HeartRateGenerator.builder()
                .yearMin(2020).yearMax(2024)
                .build().generateRand(20);

        Map<String, HeartRateAvg> grouped = HeartRateStat.builder().year(2023)
                .build().getAverageGroupedByDay(heartRates);

        for(Map.Entry<String, HeartRateAvg> entry : grouped.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    @Test
    public void testGroupYear() {
        List<HeartRate> heartRates = HeartRateGenerator.builder()
                .yearMin(2020).yearMax(2024)
                .build().generateRand(20);

        Map<String, HeartRateAvg> grouped = HeartRateStat.builder().year(2023)
                .build().getAverageGroupedByYear(heartRates);

        for(Map.Entry<String, HeartRateAvg> entry : grouped.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    @Test
    public void testFilterByDay() {
        List<HeartRate> heartRates = HeartRateGenerator.builder()
                .yearMin(2020).yearMax(2024)
                .dayMin(1).dayMax(5)
                .build().generateRand(20);

        List<HeartRate> filtered = HeartRateStat.builder().year(2023)
                .build().getFilteredByDay(heartRates, 2);

        for(HeartRate heartRate : filtered) {
            System.out.println(heartRate);
        }
    }
}
