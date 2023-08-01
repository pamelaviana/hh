package com.pamela.hh.location;

import com.pamela.hh.entity.BaseEntity;
import com.pamela.hh.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(40)")
    protected String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    protected User user;

    protected @Column(nullable=false, length=120) String address1;
    protected @Column(length=80) String address2;
    protected @Column(nullable=false, length=90) String city;
    protected @Column(length=60) String state;
    protected @Column(nullable=false, length=15) String zip;
    protected @Column(nullable=false, length=50) String country;
}
