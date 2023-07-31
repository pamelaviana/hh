package com.pamela.hh.heart.healthparameters;

import com.pamela.hh.entity.BaseEntity;
import com.pamela.hh.patient.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "health_parameter_ranges")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class HealthParamRange extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @NonNull Integer lowRangeSbp;
    private @NonNull Integer highRangeSbp;
    private @NonNull Integer lowRangeDbp;
    private @NonNull Integer highRangeDbp;
    private @Enumerated(EnumType.STRING) Gender gender = Gender.OTHER;
    private @NonNull Integer lowRangeAge;
    private @NonNull Integer highRangeAge;

    public boolean isGenderEqual(Gender gender) {
        return this.gender == gender;
    }

    public boolean isSpmInRange(Integer spm) {
        return spm >= lowRangeSbp && spm <= highRangeSbp;
    }
    public boolean isDpmInRange(Integer dpm) {
        return dpm >= lowRangeDbp && dpm <= highRangeDbp;
    }

    public boolean isAgeInRange(Integer age) {
        return age >= lowRangeAge && age <= highRangeAge;
    }



}
