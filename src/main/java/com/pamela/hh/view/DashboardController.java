package com.pamela.hh.view;

import com.pamela.hh.entity.BaseController;
import com.pamela.hh.patient.PatientService;
import com.pamela.hh.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "index"})
public class DashboardController extends BaseController {

    private final PatientService patientService;

    @Autowired
    public DashboardController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    String getDashboard(Model model, @AuthenticationPrincipal User user) {
        if(user == null) return "redirect:/login";
        model.addAttribute("user", user);
        model.addAttribute("pageName", "dashboard");
        return "index";
    }

}
