package com.pamela.hh.chart;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder @Data @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class ChartData {

    @Builder.Default private List<String> labels = new ArrayList<>();
    @Builder.Default private List<Dataset> dataset = new ArrayList<>();

    @Builder @Data @EqualsAndHashCode
    public static class Dataset {
        String label;
        String backgroundColor;
        String borderColor;
        @Builder.Default List<Integer> data = new ArrayList<>();
    }

}
