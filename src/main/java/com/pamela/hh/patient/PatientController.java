package com.pamela.hh.patient;

import com.pamela.hh.doctor.DoctorPatientMapper;
import com.pamela.hh.doctor.DoctorPatientMapperService;
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
    private final DoctorPatientMapperService doctorPatientMapperService;

    public PatientController(PatientService patientService,
                             DoctorPatientMapperService doctorPatientMapperService) {
        this.patientService = patientService;
        this.doctorPatientMapperService = doctorPatientMapperService;
    }

    @GetMapping
    String getAllPatient(Model model, HttpSession session,
                         @AuthenticationPrincipal User user) {

        flagAllUIAlertsIfAny(model, session);
        List<DoctorPatientMapper> doctorPatientMappers = doctorPatientMapperService
                .getDoctorPatientsById(user.getId()).orElse(new ArrayList<>());

        List<Patient> patients = doctorPatientMappers.stream()
                .map(DoctorPatientMapper::getPatient)
                .toList();

        int total = patients.size();

        model.addAttribute("user", user);
        model.addAttribute("patients", patients);
        model.addAttribute("pageName", "Patients");
        model.addAttribute("total", total);
        return "patients";
    }

}
