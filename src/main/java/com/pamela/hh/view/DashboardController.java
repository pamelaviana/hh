package com.pamela.hh.view;

import com.pamela.hh.entity.BaseController;
import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.heart.HeartRateNullObject;
import com.pamela.hh.heart.HeartRateService;
import com.pamela.hh.patient.Patient;
import com.pamela.hh.patient.PatientNullObject;
import com.pamela.hh.patient.PatientService;
import com.pamela.hh.patient.fitness.BMICalculator;
import com.pamela.hh.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/", "index"})
public class DashboardController extends BaseController {

    private final PatientService patientService;
    private final HeartRateService heartRateService;

    @Autowired
    public DashboardController(
            PatientService patientService,
            HeartRateService heartRateService) {
        this.patientService = patientService;
        this.heartRateService = heartRateService;
    }

    @GetMapping
    String getDashboard(Model model, HttpSession httpSession, @AuthenticationPrincipal User user) {

        flagAllUIAlertsIfAny(model, httpSession);
        if(user == null) return "redirect:/login";

        Patient patient = patientService.getByUserId(user.getId())
                .orElse(new PatientNullObject());

        List<HeartRate> latestHeartRate = heartRateService.getLatestHeartRateByUserId(patient.getId())
                .orElse(new ArrayList<>());
        HeartRate heartRate = latestHeartRate.stream().findFirst().orElse(new HeartRateNullObject());

        String bmi = String.format("%.2f", BMICalculator.calculateBMI(patient));

        model.addAttribute("user", user);
        model.addAttribute("userPatient", user);
        model.addAttribute("patient", patient);
        model.addAttribute("heartRate", heartRate);
        model.addAttribute("bmi", bmi);
        model.addAttribute("reportUrl", "/report/patient/" + user.getId());
        model.addAttribute("viewUrl", "/view/patient/" + user.getId());
        model.addAttribute("pageName", "Dashboard");
        return "index";
    }

}
