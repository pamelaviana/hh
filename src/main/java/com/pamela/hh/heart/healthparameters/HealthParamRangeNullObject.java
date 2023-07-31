package com.pamela.hh.heart.healthparameters;

import com.pamela.hh.patient.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data @EqualsAndHashCode(callSuper = false)
public class HealthParamRangeNullObject extends HealthParamRange{

    private Integer lowRangeSpm;
    private Integer highRangeSpm;
    private Integer lowRangeDpm;
    private Integer highRangeDpm;
    private Gender gender;
    private Integer lowRangeAge;
    private Integer highRangeAge;

    public HealthParamRangeNullObject() {
        this.lowRangeSpm = -1;
        this.highRangeSpm = Integer.MAX_VALUE;
        this.lowRangeDpm = -1;
        this.highRangeDpm = Integer.MAX_VALUE;
        this.gender = Gender.OTHER;
        this.lowRangeAge = -1;
        this.highRangeAge = Integer.MAX_VALUE;
    }
}
