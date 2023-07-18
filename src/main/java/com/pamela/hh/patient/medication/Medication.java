package com.pamela.hh.patient.medication;

import com.pamela.hh.entity.BaseEntity;
import com.pamela.hh.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "patients_medication")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Medication extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(40)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    private @Column(nullable = false) String name;
    private @Column(nullable = false) String description;
    private @Column(nullable = false) String dosage;
    private @Column(nullable = false) String frequency;
    private @Column(nullable = false) String duration;
}
