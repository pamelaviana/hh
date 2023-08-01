package com.pamela.hh.heart.stats;

import com.pamela.hh.heart.HeartRate;

import java.util.Comparator;

public class HeartRateComparator {

    public static class SBP implements Comparator<HeartRate> {
        @Override
        public int compare(HeartRate o1, HeartRate o2) {
            return o1.getSbp().compareTo(o2.getSbp());
        }
    }

    public static class DBP implements Comparator<HeartRate> {
        @Override
        public int compare(HeartRate o1, HeartRate o2) {
            return o1.getDbp().compareTo(o2.getDbp());
        }
    }

    public static class Timestamp implements Comparator<HeartRate> {
        @Override
        public int compare(HeartRate o1, HeartRate o2) {
            return o1.getTimestamp().compareTo(o2.getTimestamp());
        }
    }
}
