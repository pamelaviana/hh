package com.pamela.hh.heart.healthparameters;

import com.pamela.hh.entity.BaseEntity;
import com.pamela.hh.patient.Gender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "health_parameter_ranges")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class HealthParamRange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @NonNull Integer lowRangeBpm;
    private @NonNull Integer highRangeBpm;
    private @Enumerated(EnumType.STRING) Gender gender = Gender.OTHER;
    private @NonNull Integer lowRangeAge;
    private @NonNull Integer highRangeAge;

    public boolean isGenderEqual(Gender gender) {
        return this.gender == gender;
    }

    public boolean isBpmInRange(Integer bpm) {
        return bpm >= lowRangeBpm && bpm <= highRangeBpm;
    }

    public boolean isAgeInRange(Integer age) {
        return age >= lowRangeAge && age <= highRangeAge;
    }

}
