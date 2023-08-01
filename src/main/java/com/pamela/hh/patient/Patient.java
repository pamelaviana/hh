package com.pamela.hh.patient;

import com.pamela.hh.entity.BaseEntity;
import com.pamela.hh.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Patient extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, unique = true)
    private User patient;

    protected @Column(nullable=false) LocalDate birthday;
    protected @Enumerated(EnumType.STRING) @Column(nullable = false) Gender gender = Gender.OTHER;
    protected @Enumerated(EnumType.STRING) @Column(nullable = false) Smoker smoker = Smoker.NO;
    protected @Column float height;
    protected @Column float weight;
}
