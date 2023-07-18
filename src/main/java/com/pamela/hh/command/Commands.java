package com.pamela.hh.command;

import com.pamela.hh.doctor.DoctorPatientMapper;
import com.pamela.hh.doctor.DoctorPatientMapperRepository;
import com.pamela.hh.heart.healthparameters.HealthParamRange;
import com.pamela.hh.heart.healthparameters.HealthParamRangeRepository;
import com.pamela.hh.hospital.device.DeviceToken;
import com.pamela.hh.hospital.device.DeviceTokenRepository;
import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.hospital.policy.PatientPolicyRepository;
import com.pamela.hh.location.Address;
import com.pamela.hh.location.AddressRepository;
import com.pamela.hh.patient.Gender;
import com.pamela.hh.patient.Patient;
import com.pamela.hh.patient.PatientRepository;
import com.pamela.hh.patient.Smoker;
import com.pamela.hh.patient.medication.Medication;
import com.pamela.hh.patient.medication.MedicationRepository;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserRepository;
import com.pamela.hh.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class Commands {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorPatientMapperRepository doctorPatientMapperRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final HealthParamRangeRepository healthParamRangeRepository;
    private final AddressRepository addressRepository;
    private final PatientPolicyRepository patientPolicyRepository;
    private final MedicationRepository medicationRepository;

    @Autowired
    public Commands(
            UserRepository userRepository,
            PatientRepository patientRepository,
            DoctorPatientMapperRepository doctorPatientMapperRepository,
            DeviceTokenRepository deviceTokenRepository,
            HealthParamRangeRepository healthParamRangeRepository,
            AddressRepository addressRepository,
            PatientPolicyRepository patientPolicyRepository,
            MedicationRepository medicationRepository
    ) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorPatientMapperRepository = doctorPatientMapperRepository;
        this.deviceTokenRepository = deviceTokenRepository;
        this.healthParamRangeRepository = healthParamRangeRepository;
        this.addressRepository = addressRepository;
        this.patientPolicyRepository = patientPolicyRepository;
        this.medicationRepository = medicationRepository;
    }

    @Bean
    CommandLineRunner commandConfigPatient() {
        return args -> {

            PatientPolicy patientPolicy = PatientPolicy.builder()
                    .firstName("Pamela")
                    .lastName("Quintanilha")
                    .email("pamela@email.com")
                    .build();
            patientPolicyRepository.save(patientPolicy);

            User pamela = User.builder()
                    .id(1L)
                    .firstName("Pamela")
                    .lastName("Quintanilha")
                    .email("pamela@email.com")
                    .password("1234678")
                    .userRole(UserRole.PATIENT)
                    .build();

            User doctor = User.builder()
                    .id(2L)
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@email.com")
                    .password("1234678")
                    .userRole(UserRole.DOCTOR)
                    .build();
            userRepository.saveAll(List.of(pamela, doctor));

            Patient patient = Patient.builder()
                    .id(1L)
                    .patient(pamela)
                    .birthday(LocalDate.now().minusYears(20))
                    .gender(Gender.FEMALE)
                    .height(1.70f)
                    .weight(60f)
                    .smoker(Smoker.NO)
                    .build();
            patientRepository.save(patient);

            DoctorPatientMapper doctorPatientMapper = DoctorPatientMapper.builder()
                    .patient(patient)
                    .doctor(doctor)
                    .build();
            doctorPatientMapperRepository.save(doctorPatientMapper);

            DeviceToken deviceToken = DeviceToken.builder()
                    .expirationDate(LocalDate.now())
                    .active(true)
                    .neverExpires(true)
                    .build();
            deviceTokenRepository.save(deviceToken);

            HealthParamRange healthParamRange = HealthParamRange.builder()
                    .lowRangeBpm(60)
                    .highRangeBpm(100)
                    .lowRangeAge(18)
                    .highRangeAge(65)
                    .gender(Gender.FEMALE)
                    .build();
            healthParamRangeRepository.save(healthParamRange);

            Address address = Address.builder()
                    .user(pamela)
                    .address1("10 Main Street")
                    .address2("Swords")
                    .city("Dublin")
                    .state("Dublin")
                    .zip("D01")
                    .country("Ireland")
                    .build();
            addressRepository.save(address);

            Medication medication = Medication.builder()
                    .patient(pamela)
                    .doctor(doctor)
                    .name("Lisinopril")
                    .description("Lisinopril is used to treat high blood pressure. " +
                            "Lowering high blood pressure helps prevent strokes, heart attacks, " +
                            "and kidney problems. It is also used to treat heart failure and " +
                            "to improve survival after a heart attack.")
                    .dosage("10mg")
                    .frequency("1 time a day")
                    .duration("1 month")
                    .build();
            medicationRepository.save(medication);
        };
    }
}
