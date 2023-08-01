package com.pamela.hh.command;

import com.pamela.hh.doctor.DoctorPatientMapper;
import com.pamela.hh.doctor.DoctorPatientMapperService;
import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.heart.HeartRateService;
import com.pamela.hh.heart.healthparameters.HealthParamRange;
import com.pamela.hh.heart.healthparameters.HealthParamRangeService;
import com.pamela.hh.hospital.device.DeviceToken;
import com.pamela.hh.hospital.device.DeviceTokenService;
import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.hospital.policy.PatientPolicyService;
import com.pamela.hh.location.Address;
import com.pamela.hh.location.AddressService;
import com.pamela.hh.patient.*;
import com.pamela.hh.patient.medication.Medication;
import com.pamela.hh.patient.medication.MedicationService;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserRole;
import com.pamela.hh.user.UserService;
import com.pamela.hh.util.HeartRateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class Commands {

    private final UserService userService;
    private final PatientService patientService;
    private final DoctorPatientMapperService doctorPatientMapperService;
    private final DeviceTokenService deviceTokenService;
    private final HealthParamRangeService healthParamRangeService;
    private final AddressService addressService;
    private final PatientPolicyService patientPolicyService;
    private final MedicationService medicationService;
    private final HeartRateService heartRateService;

    @Autowired
    public Commands(UserService userService,
                    PatientService patientService,
                    DoctorPatientMapperService doctorPatientMapperService,
                    DeviceTokenService deviceTokenService,
                    HealthParamRangeService healthParamRangeService,
                    AddressService addressService,
                    PatientPolicyService patientPolicyService,
                    MedicationService medicationService, HeartRateService heartRateService) {
        this.userService = userService;
        this.patientService = patientService;
        this.doctorPatientMapperService = doctorPatientMapperService;
        this.deviceTokenService = deviceTokenService;
        this.healthParamRangeService = healthParamRangeService;
        this.addressService = addressService;
        this.patientPolicyService = patientPolicyService;
        this.medicationService = medicationService;
        this.heartRateService = heartRateService;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

            PatientPolicy patientPolicy = PatientPolicy.builder()
                    .firstName("Pamela")
                    .lastName("Quintanilha")
                    .email("pamela@email.com")
                    .build();
            patientPolicyService.save(patientPolicy);

            User pamela = User.builder()
                    .id(1L)
                    .firstName("Pamela")
                    .lastName("Quintanilha")
                    .email("pamela@email.com")
                    .password("12345678")
                    .userRole(UserRole.PATIENT)
                    .build();
            userService.save(pamela);

            User admin = User.builder()
                    .id(2L)
                    .firstName("Admin")
                    .lastName("")
                    .email("admin@email.com")
                    .password("12345678")
                    .userRole(UserRole.ADMIN)
                    .build();
            userService.save(admin);

            User doctor = User.builder()
                    .id(3L)
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@email.com")
                    .password("12345678")
                    .userRole(UserRole.DOCTOR)
                    .build();
            userService.save(doctor);

            Patient patient = Patient.builder()
                    .id(1L)
                    .patient(pamela)
                    .birthday(LocalDate.now().minusYears(20))
                    .gender(Gender.FEMALE)
                    .height(1.70f)
                    .weight(60f)
                    .smoker(Smoker.NO)
                    .build();
            patientService.save(patient);

            DoctorPatientMapper doctorPatientMapper = DoctorPatientMapper.builder()
                    .patient(patient)
                    .doctor(doctor)
                    .build();
            doctorPatientMapperService.save(doctorPatientMapper);

            DeviceToken deviceToken = DeviceToken.builder()
                    .expirationDate(LocalDate.now())
                    .active(true)
                    .neverExpires(true)
                    .build();
            deviceTokenService.save(deviceToken);

            HealthParamRange bpFemaleRange18To39 = HealthParamRange.builder()
                    .lowRangeSbp(110)
                    .highRangeSbp(120)
                    .lowRangeDbp(60)
                    .highRangeDbp(80)
                    .lowRangeAge(18)
                    .highRangeAge(39)
                    .gender(Gender.FEMALE)
                    .build();
            healthParamRangeService.save(bpFemaleRange18To39);

            HealthParamRange bpMaleRange18To39 = HealthParamRange.builder()
                    .lowRangeSbp(100)
                    .highRangeSbp(129)
                    .lowRangeDbp(50)
                    .highRangeDbp(80)
                    .lowRangeAge(18)
                    .highRangeAge(39)
                    .gender(Gender.MALE)
                    .build();
            healthParamRangeService.save(bpMaleRange18To39);

            Address address = Address.builder()
                    .user(pamela)
                    .address1("10 Main Street")
                    .address2("Apartment 36")
                    .city("Dublin")
                    .state("Dublin")
                    .zip("D01")
                    .country("Ireland")
                    .build();
            addressService.save(address);

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
            medicationService.save(medication);

            int currentYear = LocalDate.now().getYear();
            int currentDay = LocalDate.now().getDayOfMonth();
            List<HeartRate> heartRates = HeartRateGenerator.builder()
                    .yearMin(currentYear).yearMax(currentYear + 1)
                    .dayMin(currentDay).dayMax(currentDay + 2)
                    .build().generateRand(25);

            heartRates.forEach(heartRate -> {
                heartRate.setUser(pamela);
                heartRateService.save(heartRate);
            });

        };
    }
}
