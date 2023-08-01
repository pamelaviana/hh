package com.pamela.hh.view;

import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.hospital.policy.PatientPolicyService;
import com.pamela.hh.location.Address;
import com.pamela.hh.location.AddressService;
import com.pamela.hh.patient.PatientService;
import com.pamela.hh.patient.medication.Medication;
import com.pamela.hh.patient.medication.MedicationService;
import com.pamela.hh.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/view")
public class ViewInfoController {

    private final PatientService patientService;
    private final PatientPolicyService patientPolicyService;
    private final AddressService addressService;
    private final MedicationService medicationService;

    @Autowired
    public ViewInfoController(
            PatientService patientService,
            PatientPolicyService patientPolicyService,
            AddressService addressService,
            MedicationService medicationService) {
        this.patientService = patientService;
        this.patientPolicyService = patientPolicyService;
        this.addressService = addressService;
        this.medicationService = medicationService;
    }

    @GetMapping(value = "/patient/{id}")
    String getReport(Model model, @AuthenticationPrincipal User user) {

        if(user == null) return "redirect:/login";

        PatientPolicy patientPolicy = patientPolicyService.getPolicyByNameAndEmail(user)
                .orElseThrow(() -> new RuntimeException("Patient policy not found"));

        Address address = addressService.getByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        List<Medication> medication = medicationService.getMedicationsByPatientId(user.getId())
                .orElse(new ArrayList<>());

        model.addAttribute("user", user);
        model.addAttribute("userPatient", user);
        model.addAttribute("patientPolicy", patientPolicy);
        model.addAttribute("address", address);
        model.addAttribute("medications", medication);
        return "view_info";
    }
}
