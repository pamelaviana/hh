package com.pamela.hh.alert.heart;

import com.pamela.hh.entity.BaseEntity;
import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.patient.Patient;
import com.pamela.hh.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "alert_heart_rates")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class AlertHeartRate extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(40)")
    private String id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "heart_rate_id", referencedColumnName = "id", nullable = false)
    private HeartRate heartRate;

    private boolean seen;
}
