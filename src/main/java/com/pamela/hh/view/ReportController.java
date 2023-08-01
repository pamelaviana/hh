package com.pamela.hh.view;

import com.pamela.hh.doctor.DoctorPatientMapperService;
import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.heart.HeartRateService;
import com.pamela.hh.heart.stats.HeartRateAvg;
import com.pamela.hh.heart.stats.HeartRateStat;
import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.hospital.policy.PatientPolicyNullObject;
import com.pamela.hh.hospital.policy.PatientPolicyService;
import com.pamela.hh.location.Address;
import com.pamela.hh.location.AddressNullObject;
import com.pamela.hh.location.AddressService;
import com.pamela.hh.patient.Patient;
import com.pamela.hh.patient.PatientNullObject;
import com.pamela.hh.patient.PatientService;
import com.pamela.hh.patient.fitness.BMICalculator;
import com.pamela.hh.patient.medication.Medication;
import com.pamela.hh.patient.medication.MedicationService;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserRole;
import com.pamela.hh.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/report")
public class ReportController {

    private final UserService userService;
    private final PatientPolicyService patientPolicyService;
    private final PatientService patientService;
    private final AddressService addressService;
    private final HeartRateService heartRateService;
    private final MedicationService medicationService;
    private final DoctorPatientMapperService doctorPatientMapperService;

    @Autowired
    public ReportController(UserService userService,
                            PatientPolicyService patientPolicyService,
                            PatientService patientService,
                            AddressService addressService,
                            HeartRateService heartRateService,
                            MedicationService medicationService,
                            DoctorPatientMapperService doctorPatientMapperService) {
        this.userService = userService;
        this.patientPolicyService = patientPolicyService;
        this.patientService = patientService;
        this.addressService = addressService;
        this.heartRateService = heartRateService;
        this.medicationService = medicationService;
        this.doctorPatientMapperService = doctorPatientMapperService;
    }


    @GetMapping(value = "/patient/{id}")
    String getPatientReport(Model model, HttpSession session, @AuthenticationPrincipal User userSession,
                            @PathVariable("id") Long id) {

        if(userSession == null) return "redirect:/login";
        if(userSession.getUserRole().equals(UserRole.PATIENT) && !userSession.getId().equals(id)){
            return "redirect:/index";
        }

        User userPatient = userService.getByUserId(id);
        PatientPolicy patientPolicy = patientPolicyService.getPolicyByNameAndEmail(userPatient)
                        .orElse(new PatientPolicyNullObject());
        Patient patient = patientService.getByUserId(id).orElse(new PatientNullObject());
        Address address = addressService.getByUserId(id).orElse(new AddressNullObject());
        List<HeartRate> heartRates = heartRateService.getHeartRatesByPatientId(id)
                        .orElse(new ArrayList<>());
        List<Medication> medications = medicationService.getMedicationsByPatientId(id)
                        .orElse(new ArrayList<>());

        Integer age = getPatientAge(patient);
        String bmi = String.format("%.2f", BMICalculator.calculateBMI(patient));

        List<HeartRate> dailyHeartRates = HeartRateStat.builder()
                        .year(LocalDate.now().getYear()).build()
                        .getFilteredByDay(heartRates, LocalDate.now().getDayOfMonth());

        Map<String, HeartRateAvg> avgHeartRates = HeartRateStat.builder()
                        .year(LocalDate.now().getYear()).build()
                        .getAverageGroupedByMonth(heartRates);

        model.addAttribute("user", userSession);
        model.addAttribute("userPatient", userPatient);
        model.addAttribute("patientPolicy", patientPolicy);
        model.addAttribute("patient", patient);
        model.addAttribute("address", address);
        model.addAttribute("age", age);
        model.addAttribute("bmi", bmi);
        model.addAttribute("medications", medications);
        model.addAttribute("dailyHeartRates", dailyHeartRates);
        model.addAttribute("avgHeartRates", avgHeartRates);
        return "report";
    }

    private int getPatientAge(Patient patient) {
        Period period = Period.between(Optional.ofNullable(patient.getBirthday())
                .orElse(LocalDate.now()), LocalDate.now());
        return period.getYears();
    }

}
