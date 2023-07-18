package com.pamela.hh.heart;

import com.pamela.hh.entity.BaseEntity;
import com.pamela.hh.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "heart_rates")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class HeartRate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(40)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private @NonNull Integer bpm;
    private @NonNull LocalDateTime timestamp;

    public Long getUserId() {
        return user.getId();
    }

}
