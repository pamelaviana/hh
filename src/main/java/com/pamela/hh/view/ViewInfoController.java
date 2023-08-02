package com.pamela.hh.view;

import com.pamela.hh.alert.ui.Alert;
import com.pamela.hh.entity.BaseController;
import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.hospital.policy.PatientPolicyService;
import com.pamela.hh.location.Address;
import com.pamela.hh.location.AddressService;
import com.pamela.hh.patient.Patient;
import com.pamela.hh.patient.PatientService;
import com.pamela.hh.patient.medication.Medication;
import com.pamela.hh.patient.medication.MedicationService;
import com.pamela.hh.patient.medication.MedicationValidator;
import com.pamela.hh.user.User;
import com.pamela.hh.user.register.RegistrationValidator;
import com.pamela.hh.util.ObjectUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/view")
public class ViewInfoController extends BaseController {

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
    String getReport(Model model, HttpSession session, @PathVariable("id") Long id,
                     @AuthenticationPrincipal User user) {

        flagAllUIAlertsIfAny(model, session);
        if(user == null) return "redirect:/login";

        List<Alert> listAlertMessage = new ArrayList<>();

        try {
            Patient patient = patientService.getByUserId(id)
                    .orElseThrow(() -> new RuntimeException("You have to be a patient or become one"));

            User userPatient = patient.getPatient();

            PatientPolicy patientPolicy = patientPolicyService.getPolicyByNameAndEmail(userPatient)
                    .orElseThrow(() -> new RuntimeException("Patient policy not found"));

            Address address = addressService.getByUserId(userPatient.getId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            List<Medication> medication = medicationService.getMedicationsByPatientId(userPatient.getId())
                    .orElse(new ArrayList<>());

            model.addAttribute("user", user);
            model.addAttribute("userPatient", userPatient);
            model.addAttribute("patientPolicy", patientPolicy);
            model.addAttribute("address", address);
            model.addAttribute("medications", medication);
            model.addAttribute("pageName", "View Info");
            return "view_info";
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
            addUIAlertToSession(session, listAlertMessage);
            return "redirect:/index";
        }
    }

    @PostMapping(value = "/patient")
    String postRegister(Model model, Medication medication, HttpSession session,
                        @AuthenticationPrincipal User userSession) {

        List<Alert> listAlertMessage = new ArrayList<>();
        User userPatient = medication.getPatient();
        try {
            medication.setDoctor(userSession);
            MedicationValidator.validateMedication(medication);
            medicationService.save(medication);
            listAlertMessage.add(Alert.builder().success().message("Medication added successfully.").build());
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        return "redirect:/view/patient/" + userPatient.getId();
    }

    @DeleteMapping("/patient/{id}")
    ResponseEntity<?> deletePatient(Model model, HttpSession session, @PathVariable("id") String id,
                         @AuthenticationPrincipal User user) {

        Map<String, String> response = new HashMap<>();
        List<Alert> listAlertMessage = new ArrayList<>();
        flagAllUIAlertsIfAny(model, session);
        try {
            Medication medication = medicationService.getById(id)
                    .orElseThrow(() -> new RuntimeException("Medication not found"));
            medicationService.delete(id);
            listAlertMessage.add(Alert.builder().success().message("Medication removed successfully.").build());
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
            response.put("message", "Error removing medication");
        }
        addUIAlertToSession(session, listAlertMessage);
        response.put("url", "/view/patient/" + user.getId());
        response.put("message", "Medication removed successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/patient/{id}")
    ResponseEntity<?> putRegister(Model model, @RequestBody Medication medication, HttpSession session,
                                  @AuthenticationPrincipal User userSession, @PathVariable("id") Long id) {

        Map<String, String> response = new HashMap<>();
        List<Alert> listAlertMessage = new ArrayList<>();
        flagAllUIAlertsIfAny(model, session);
        try {
            Patient patient = patientService.getByUserId(id)
                    .orElseThrow(() -> new RuntimeException("The patient you want to update the medication doesn't exist"));
            medication.setPatient(patient.getPatient());
            medication.setDoctor(userSession);
            MedicationValidator.validateMedication(medication);
            medicationService.update(medication);
            listAlertMessage.add(Alert.builder().success().message("Medication updated successfully.").build());
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
            response.put("message", "Error updating medication");
        }
        addUIAlertToSession(session, listAlertMessage);
        response.put("url", "/view/patient/" + id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
