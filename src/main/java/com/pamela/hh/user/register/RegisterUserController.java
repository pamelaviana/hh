package com.pamela.hh.user.register;

import com.pamela.hh.alert.ui.Alert;
import com.pamela.hh.entity.BaseController;
import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.hospital.policy.PatientPolicyService;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserService;
import com.pamela.hh.util.ObjectUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterUserController extends BaseController {

    private final UserService userService;
    private final PatientPolicyService patientPolicyService;

    @Autowired
    public RegisterUserController(UserService userService, PatientPolicyService patientPolicyService) {
        this.userService = userService;
        this.patientPolicyService = patientPolicyService;
    }

    @GetMapping
    String getRegisterPage(Model model, HttpSession session) {
        flagAllUIAlertsIfAny(model, session);
        model.addAttribute("pageName", "Register");
        return "register";
    }

    @PostMapping
    String postRegister(Model model, User user, HttpSession session,
            @RequestParam(value="policyNumber", required = false, defaultValue = "") String policyNumber) {

        List<Alert> listAlertMessage = new ArrayList<>();
        try {
            RegistrationValidator.validateUser(user);
            PatientPolicy patientPolicy = patientPolicyService.getPolicyByNameAndEmail(user)
                    .orElseThrow(() -> new IllegalStateException("Policy not found or not valid"));

            RegistrationValidator.validatePolicy(patientPolicy, policyNumber);
            User preRegisteredUser = userService.getUserByEmail(user.getEmail());
            ObjectUtil.process(userService.updatePassword(preRegisteredUser, user.getPassword()),
                    (User u) -> listAlertMessage.add(Alert.builder().success().message("User registered successfully").build()),
                    () -> new IllegalStateException("User already exists"));
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        return listAlertMessage.isEmpty() ? "redirect:/login" : "redirect:/register";
    }

}
