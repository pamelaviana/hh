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

            PatientPolicy patientPolicy2 = PatientPolicy.builder()
                    .firstName("Brian")
                    .lastName("Smith")
                    .email("brain.smith@email.com")
                    .build();
            patientPolicyService.save(patientPolicy2);

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

            User doctorJohn = User.builder()
                    .id(3L)
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@email.com")
                    .password("12345678")
                    .userRole(UserRole.DOCTOR)
                    .build();
            userService.save(doctorJohn);

            User doctorJane = User.builder()
                    .id(4L)
                    .firstName("Jane")
                    .lastName("Smith")
                    .email("jane.smith@email.com")
                    .password("12345678")
                    .userRole(UserRole.DOCTOR)
                    .build();
            userService.save(doctorJane);

            Patient patientPamela = Patient.builder()
                    .id(1L)
                    .patient(pamela)
                    .birthday(LocalDate.now().minusYears(20))
                    .gender(Gender.FEMALE)
                    .height(1.70f)
                    .weight(60f)
                    .smoker(Smoker.NO)
                    .build();
            patientService.save(patientPamela);

            User brian = User.builder()
                    .id(5L)
                    .firstName("Brian")
                    .lastName("Smith")
                    .email("brain.smith@email.com")
                    .password("12345678")
                    .userRole(UserRole.PATIENT)
                    .build();
            userService.save(brian);

            Patient patientBrian = Patient.builder()
                    .id(2L)
                    .patient(brian)
                    .birthday(LocalDate.now().minusYears(32))
                    .gender(Gender.MALE)
                    .height(1.80f)
                    .weight(80f)
                    .smoker(Smoker.MODERATE)
                    .build();
            patientService.save(patientBrian);

            User charles = User.builder()
                    .id(6L)
                    .firstName("Charles")
                    .lastName("Smith")
                    .email("charles@hotmail.com")
                    .password("12345678")
                    .userRole(UserRole.PATIENT)
                    .build();
            userService.save(charles);

            Patient charlesPatient = Patient.builder()
                    .id(3L)
                    .patient(charles)
                    .birthday(LocalDate.now().minusYears(46))
                    .gender(Gender.MALE)
                    .height(1.80f)
                    .weight(75f)
                    .smoker(Smoker.LIGHT)
                    .build();
            patientService.save(charlesPatient);

            User henry = User.builder()
                    .id(7L)
                    .firstName("Henry")
                    .lastName("Smith")
                    .email("henry@email.com")
                    .password("12345678")
                    .userRole(UserRole.DOCTOR)
                    .build();
            userService.save(henry);

            User doctorJessica = User.builder()
                    .id(8L)
                    .firstName("Jessica")
                    .lastName("Smith")
                    .email("jessica@email.com")
                    .password("12345678")
                    .userRole(UserRole.DOCTOR)
                    .build();
            userService.save(doctorJessica);

            User victor = User.builder()
                    .id(9L)
                    .firstName("Victor")
                    .lastName("Smith")
                    .email("victor@email.com")
                    .password("12345678")
                    .userRole(UserRole.PATIENT)
                    .build();
            userService.save(victor);

            Patient patientVictor = Patient.builder()
                    .id(4L)
                    .patient(victor)
                    .birthday(LocalDate.now().minusYears(18))
                    .gender(Gender.MALE)
                    .height(1.78f)
                    .weight(78f)
                    .smoker(Smoker.NO)
                    .build();
            patientService.save(patientVictor);

            DoctorPatientMapper johnToPamela = DoctorPatientMapper.builder()
                    .patient(patientPamela)
                    .doctor(doctorJohn)
                    .build();
            doctorPatientMapperService.save(johnToPamela);

            DoctorPatientMapper janeToBrian = DoctorPatientMapper.builder()
                    .patient(patientBrian)
                    .doctor(doctorJane)
                    .build();
            doctorPatientMapperService.save(janeToBrian);

            DoctorPatientMapper jessicaToCharles = DoctorPatientMapper.builder()
                    .patient(charlesPatient)
                    .doctor(doctorJessica)
                    .build();
            doctorPatientMapperService.save(jessicaToCharles);

            DoctorPatientMapper henryToVictor = DoctorPatientMapper.builder()
                    .patient(patientVictor)
                    .doctor(henry)
                    .build();
            doctorPatientMapperService.save(henryToVictor);

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

            Address address2 = Address.builder()
                    .user(brian)
                    .address1("32 O'Connell Street")
                    .address2("House 2")
                    .city("Dublin")
                    .state("Dublin")
                    .zip("D02")
                    .country("Ireland")
                    .build();
            addressService.save(address2);

            Medication medication = Medication.builder()
                    .patient(pamela)
                    .doctor(doctorJohn)
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
            int currentMonth = LocalDate.now().getMonthValue();
            int currentDay = LocalDate.now().getDayOfMonth();

            List<HeartRate> pamelaHeartRates = HeartRateGenerator.builder()
                    .yearMin(currentYear).yearMax(currentYear + 1)
                    .monthMax(currentMonth).monthMin(currentMonth + 2)
                    .dayMin(currentDay).dayMax(currentDay + 2)
                    .build().generateRand(25);

            pamelaHeartRates.forEach(heartRate -> {
                heartRate.setUser(pamela);
                heartRateService.save(heartRate);
            });

            List<HeartRate> brianHeartRates = HeartRateGenerator.builder()
                    .yearMin(currentYear).yearMax(currentYear + 1)
                    .monthMax(currentMonth).monthMin(currentMonth + 2)
                    .dayMin(currentDay).dayMax(currentDay + 3)
                    .build().generateRand(30);

            brianHeartRates.forEach(heartRate -> {
                heartRate.setUser(brian);
                heartRateService.save(heartRate);
            });

            List<HeartRate> charlesHeartRates = HeartRateGenerator.builder()
                    .yearMin(currentYear).yearMax(currentYear + 1)
                    .monthMax(currentMonth).monthMin(currentMonth + 2)
                    .dayMin(currentDay).dayMax(currentDay + 3)
                    .build().generateRand(30);

            charlesHeartRates.forEach(heartRate -> {
                heartRate.setUser(charles);
                heartRateService.save(heartRate);
            });

            List<HeartRate> victorHeartRates = HeartRateGenerator.builder()
                    .yearMin(currentYear).yearMax(currentYear + 1)
                    .monthMax(currentMonth).monthMin(currentMonth + 2)
                    .dayMin(currentDay).dayMax(currentDay + 3)
                    .build().generateRand(30);

            victorHeartRates.forEach(heartRate -> {
                heartRate.setUser(victor);
                heartRateService.save(heartRate);
            });

        };
    }
}
