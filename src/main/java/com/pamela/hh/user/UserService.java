package com.pamela.hh.user;

import com.pamela.hh.doctor.DoctorPatientMapperService;
import com.pamela.hh.heart.HeartRateService;
import com.pamela.hh.location.AddressService;
import com.pamela.hh.patient.PatientService;
import com.pamela.hh.patient.medication.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final PatientService patientService;
    private final HeartRateService heartRateService;
    private final MedicationService medicationService;
    private final DoctorPatientMapperService doctorPatientMapperService;
    private final PasswordEncoder passwordEncoder;

    private final String USER_NOT_FOUND_MSG = "User with email %s not found";

    @Autowired
    public UserService(UserRepository userRepository,
                       AddressService addressService,
                       PatientService patientService,
                       HeartRateService heartRateService,
                       MedicationService medicationService,
                       DoctorPatientMapperService doctorPatientMapperService,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.patientService = patientService;
        this.heartRateService = heartRateService;
        this.medicationService = medicationService;
        this.doctorPatientMapperService = doctorPatientMapperService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findUserByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public User save(User user) {
        boolean userExists = userRepository
                .findUserByEmail(user.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("User with id %o doesn't exist", id)));

        if(user.getUserRole().equals(UserRole.PATIENT)){
            patientService.getByUserId(user.getId())
                    .ifPresent(patient -> patientService.delete(patient.getId()));

            heartRateService.existsByUserId(user.getId())
                    .ifPresent(bool -> heartRateService.deleteByUserId(user.getId()));

            addressService.existsByUserId(user.getId())
                    .ifPresent(bool -> addressService.deleteByUserId(user.getId()));

        } else if(user.getUserRole().equals(UserRole.DOCTOR)){

            medicationService.existsByDoctorId(user.getId())
                    .ifPresent(bool -> medicationService.deleteByDoctorId(user.getId()));

            doctorPatientMapperService.existsByDoctorId(user.getId())
                    .ifPresent(bool -> doctorPatientMapperService.deleteByDoctorId(user.getId()));
        }
        userRepository.delete(user);
    }

    @Transactional
    public User update(User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException(
                        String.format("User with id %o doesn't exist", user.getId())));
        return userRepository.save(user);
    }

    @Transactional
    public User updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("User with email %s doesn't exist", email)));
    }

    public User getByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("User with id %o doesn't exist", id)));
    }
}
