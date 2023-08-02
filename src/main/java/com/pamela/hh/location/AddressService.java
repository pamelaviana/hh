package com.pamela.hh.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address getById(String id) {
    	return addressRepository.findById(id).orElse(null);
    }

    public ResponseEntity<String> save(Address address) {
        addressRepository.save(address);
        return ResponseEntity.ok("Address saved");
    }

    public void delete(String id) {
    	addressRepository.deleteById(id);
    }

    public ResponseEntity<String> update(Address address) {
    	addressRepository.save(address);
    	return ResponseEntity.ok("Address updated");
    }

    public List<Address> getAll() {
    	return addressRepository.findAll();
    }

    public void deleteByUserId(Long id) {
        addressRepository.deleteByUserId(id);
    }

    public Optional<Address> getByUserId(Long id) {
        return addressRepository.findByUserId(id);
    }

    public Optional<Address> checkAddressDuplication(Address address) {
        return addressRepository.existsByAddress(
                address.getAddress1(),
                address.getCity(),
                address.getCountry());
    }

    public Optional<Boolean> existsByUserId(Long id) {
        return addressRepository.existsByUserId(id);
    }
}
