package com.pamela.hh.heart.stats;

import lombok.*;

@Builder @Data @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class HeartRateAvg {

    private int sbpAvg;
    private int sbpMin;
    private int sbpMax;
    private int dbpAvg;
    private int dbpMin;
    private int dbpMax;
}
