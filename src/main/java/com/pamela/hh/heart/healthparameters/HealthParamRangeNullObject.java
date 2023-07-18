package com.pamela.hh.heart.healthparameters;

import com.pamela.hh.patient.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data @EqualsAndHashCode(callSuper = false)
public class HealthParamRangeNullObject extends HealthParamRange{

    private Integer lowRangeBpm;
    private Integer highRangeBpm;
    private Gender gender;
    private Integer lowRangeAge;
    private Integer highRangeAge;

    public HealthParamRangeNullObject() {
        this.lowRangeBpm = -1;
        this.highRangeBpm = Integer.MAX_VALUE;
        this.gender = Gender.OTHER;
        this.lowRangeAge = -1;
        this.highRangeAge = Integer.MAX_VALUE;
    }
}
