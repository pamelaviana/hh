package com.pamela.hh.heart.stats;

import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.heart.stats.group.Groupable;

import java.util.*;

public class HeartRateStat {

    private final Map<Integer, Node<Node, Node>> description;
    private Map<Integer, List<HeartRate>> grouped;
    private final List<HeartRate> heartRates;

    public HeartRateStat (List<HeartRate> heartRates) {
        description = new HashMap<>();
        grouped = new HashMap<>();
        this.heartRates = heartRates;
    }

    public HeartRateStat sort(Comparator<HeartRate> comparable) {
        Collections.sort(heartRates, comparable);
        return this;
    }

    public HeartRateStat sortGrouped() {
        Map<Integer, List<HeartRate>> sortedMap = new TreeMap<>(grouped);
        grouped = sortedMap;
        return this;
    }

    public HeartRateStat groupBy(Groupable groupable) {
        Map<Integer, List<HeartRate>> grouped = groupable.group(heartRates);
        this.grouped.putAll(grouped);
        return this;
    }

    public Map<Integer, Node<Node, Node>> describe(){

        grouped.forEach((key, value) -> {
            int[] sbp = value.stream().mapToInt(HeartRate::getSbp).toArray();
            int[] dbp = value.stream().mapToInt(HeartRate::getDbp).toArray();

            Node<Integer, Node<Integer, Integer>> nodeSbp = getAvgMinMax(sbp);
            Node<Integer, Node<Integer, Integer>> nodeDbp = getAvgMinMax(dbp);

            description.put(key, new Node<>(nodeSbp, nodeDbp));
        });
        return getImmutableDescription();
    }

    private Node<Integer, Node<Integer, Integer>> getAvgMinMax(int[] array) {

        int min = getMin(array);
        int max = getMax(array);
        int avg = getAverage(array);

        return new Node<>(avg, getNodeMinMax(min, max));
    }

    private Node<Integer, Integer> getNodeMinMax(int min, int max) {
        return new Node<>(min, max);
    }

    private int getMin(int[] array) {
        return Arrays.stream(array).min().orElse(0);
    }

    private int getMax(int[] array) {
        return Arrays.stream(array).max().orElse(0);
    }

    private int getAverage(int[] array) {
        return (int) Arrays.stream(array).average().orElse(0);
    }

    public List<HeartRate> getImmutableHeartRates() {
        return Collections.unmodifiableList(heartRates);
    }

    public Map<Integer, List<HeartRate>> getImmutableGrouped() {
        return grouped;
    }

    public Map<Integer, Node<Node, Node>> getImmutableDescription() {
        return description;
    }

    public void end() {
        System.out.println("end");
    }
}
