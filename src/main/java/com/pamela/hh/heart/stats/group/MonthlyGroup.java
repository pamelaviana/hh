package com.pamela.hh.heart.stats.group;

import com.pamela.hh.heart.HeartRate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthlyGroup implements Groupable {
    @Override
    public Map<Integer, List<HeartRate>> group(List<HeartRate> list) {
        Map<Integer, List<HeartRate>> tempMap = new HashMap<>();
        list.forEach(heartRate -> {
            int month = heartRate.getTimestamp().getMonth().getValue();
            tempMap.computeIfAbsent(month, k -> new ArrayList<>());
            tempMap.get(month).add(heartRate);
        });
        return tempMap;
    }
}
