package com.pamela.hh.hospital.device;

import com.pamela.hh.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "device_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class DeviceToken extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(40)")
    private String id;

    private @Column(nullable=false) LocalDate expirationDate;
    private @Column(nullable=false) boolean active;
    private @Column(nullable=false) boolean neverExpires;

    public boolean isExpired() {
        return !neverExpires && expirationDate.isBefore(LocalDate.now());
    }
}
