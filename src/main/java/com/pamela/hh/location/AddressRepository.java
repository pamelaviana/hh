package com.pamela.hh.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly=true)
public interface AddressRepository extends JpaRepository<Address, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Address a WHERE a.user.id = ?1")
    void deleteByUserId(Long id);

    @Query("SELECT a FROM Address a WHERE a.user.id = ?1")
    Optional<Address> findByUserId(Long id);

    @Query("SELECT a FROM Address a WHERE a.address1 = ?1 AND a.city = ?2 AND a.country = ?3")
    Optional<Address> existsByAddress(String address1, String city, String country);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Address a WHERE a.user.id = ?1")
    Optional<Boolean> existsByUserId(Long id);
}
