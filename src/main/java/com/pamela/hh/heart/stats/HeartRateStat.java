package com.pamela.hh.heart.stats;

import com.pamela.hh.heart.HeartRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.*;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
public class HeartRateStat {

    @Builder.Default private int year = -1;
    @Builder.Default private int month = -1;

    public Map<String, HeartRateAvg> getAverageGroupedByDay(List<HeartRate> heartRates){
        heartRates = filterByYear(heartRates);
        heartRates = filterByYearAndMonth(heartRates);
        Map<Integer, List<HeartRate>> tempMap = new TreeMap<>();
        heartRates.forEach(heartRate -> {
            int day = heartRate.getTimestamp().getDayOfMonth();
            tempMap.computeIfAbsent(day, k -> new ArrayList<>());
            tempMap.get(day).add(heartRate);
        });

        Map<String, HeartRateAvg> grouped = new LinkedHashMap<>();
        tempMap.forEach((key, value) -> {
            HeartRateAvg heartRateAvg = getHeartRateAvg(value);
            String day = String.valueOf(key);
            grouped.put(day, heartRateAvg);
        });
        return grouped;
    }

    public Map<String, HeartRateAvg> getAverageGroupedByMonth(List<HeartRate> heartRates){
        heartRates = filterByYear(heartRates);
        heartRates = filterByYearAndMonth(heartRates);
        Map<Integer, List<HeartRate>> tempMap = new TreeMap<>();
        heartRates.forEach(heartRate -> {
            int month = heartRate.getTimestamp().getMonth().getValue();
            tempMap.computeIfAbsent(month, k -> new ArrayList<>());
            tempMap.get(month).add(heartRate);
        });

        Map<String, HeartRateAvg> grouped = new LinkedHashMap<>();
        tempMap.forEach((key, value) -> {
            HeartRateAvg heartRateAvg = getHeartRateAvg(value);
            String month = Month.of(key).name();
            grouped.put(month.toLowerCase(), heartRateAvg);
        });
        return grouped;
    }

    private List<HeartRate> filterByYear(List<HeartRate> heartRates) {
        if (year == -1) {
            return heartRates;
        }
        List<HeartRate> filtered = new ArrayList<>();
        heartRates.forEach(heartRate -> {
            if (heartRate.getTimestamp().getYear() == year) {
                filtered.add(heartRate);
            }
        });
        return filtered;
    }

    public Map<String, HeartRateAvg> getAverageGroupedByYear(List<HeartRate> heartRates){
        Map<Integer, List<HeartRate>> tempMap = new TreeMap<>();
        heartRates.forEach(heartRate -> {
            int year = heartRate.getTimestamp().getYear();
            tempMap.computeIfAbsent(year, k -> new ArrayList<>());
            tempMap.get(year).add(heartRate);
        });

        Map<String, HeartRateAvg> grouped = new LinkedHashMap<>();
        tempMap.forEach((key, value) -> {
            HeartRateAvg heartRateAvg = getHeartRateAvg(value);
            String year = String.valueOf(key);
            grouped.put(year, heartRateAvg);
        });
        return grouped;
    }

    public List<HeartRate> getFilteredByDay(List<HeartRate> heartRates, int day){
        heartRates = filterByYearAndMonth(heartRates);
        return filterByDay(heartRates, day);
    }

    private List<HeartRate> filterByDay(List<HeartRate> heartRates, int day) {
        if (day == -1) {
            return heartRates;
        }
        List<HeartRate> filtered = new ArrayList<>();
        heartRates.forEach(heartRate -> {
            if (heartRate.getTimestamp().getDayOfMonth() == day) {
                filtered.add(heartRate);
            }
        });
        return filtered;
    }

    private List<HeartRate> filterByYearAndMonth(List<HeartRate> heartRates) { // Modified method name
        List<HeartRate> filtered = new ArrayList<>();
        heartRates.forEach(heartRate -> {
            if ((year == -1 || heartRate.getTimestamp().getYear() == year) &&
                    (month == -1 || heartRate.getTimestamp().getMonth().getValue() == month)) {
                filtered.add(heartRate);
            }
        });
        return filtered;
    }

    private HeartRateAvg getHeartRateAvg(List<HeartRate> heartRates) {
        int[] sbp = heartRates.stream().mapToInt(HeartRate::getSbp).toArray();
        int avgSbp = getAverage(sbp);
        int minSbp = getMin(sbp);
        int maxSbp = getMax(sbp);

        int[] dbp = heartRates.stream().mapToInt(HeartRate::getDbp).toArray();
        int avgDbp = getAverage(dbp);
        int minDbp = getMin(dbp);
        int maxDbp = getMax(dbp);

        return HeartRateAvg.builder()
                .sbpAvg(avgSbp)
                .sbpMin(minSbp)
                .sbpMax(maxSbp)
                .dbpAvg(avgDbp)
                .dbpMin(minDbp)
                .dbpMax(maxDbp)
                .build();
    }

    public int getMin(int[] array) {
        return Arrays.stream(array).min().orElse(0);
    }

    public int getMax(int[] array) {
        return Arrays.stream(array).max().orElse(0);
    }

    public int getAverage(int[] array) {
        return (int) Arrays.stream(array).average().orElse(0);
    }

}
