package com.pamela.hh.patient;

import com.pamela.hh.entity.BaseController;
import com.pamela.hh.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "all-patients")
public class PatientController extends BaseController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    String getAllPatient(Model model, HttpSession session,
                         @AuthenticationPrincipal User user) {

        flagAllUIAlertsIfAny(model, session);
        List<Patient> patients = patientService.getAll()
                .orElse(new ArrayList<>());

        model.addAttribute("user", user);
        model.addAttribute("patients", patients);
        model.addAttribute("pageName", "Patients");
        return "patients";
    }

}
