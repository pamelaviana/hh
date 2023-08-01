package com.pamela.hh.heart.stats.group;

import com.pamela.hh.heart.HeartRate;

import java.util.List;
import java.util.Map;

public interface Groupable {
    Map<Integer, List<HeartRate>> group(List<HeartRate> list);
}
