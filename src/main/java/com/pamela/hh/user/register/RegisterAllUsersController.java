package com.pamela.hh.user.register;


import com.pamela.hh.alert.ui.Alert;
import com.pamela.hh.entity.BaseController;
import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.hospital.policy.PatientPolicyService;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserRole;
import com.pamela.hh.user.UserService;
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
@RequestMapping(path = "register-all")
public class RegisterAllUsersController extends BaseController {

    private final UserService userService;
    private final PatientPolicyService patientPolicyService;

    @Autowired
    public RegisterAllUsersController(UserService userService, PatientPolicyService patientPolicyService) {
        this.userService = userService;
        this.patientPolicyService = patientPolicyService;
    }

    @GetMapping
    String getAllUsers(Model model, HttpSession session, @AuthenticationPrincipal User user) {
        flagAllUIAlertsIfAny(model, session);
        List<User> users = userService.getAll();
        List<PatientPolicy> patientPolicies = patientPolicyService.getAll();

        Map<String, String> patientPolicyMap = new HashMap<>();
        patientPolicies.forEach(patientPolicy ->
                patientPolicyMap.put(patientPolicy.getEmail(), patientPolicy.getId()));

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("patientPolicyMap", patientPolicyMap);
        model.addAttribute("pageName", "Register Users");
        return "register_users";
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(Model model, HttpSession session, @PathVariable("id") Long id,
                                 @AuthenticationPrincipal User user) {

        flagAllUIAlertsIfAny(model, session);
        List<Alert> listAlertMessage = new ArrayList<>();
        try {
            if (user != null && user.getId().equals(id)) {
                throw new IllegalStateException("You can't delete yourself");
            }
            userService.delete(id);
            listAlertMessage.add(Alert.builder().success().message("User removed successfully.").build());
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        Map<String, String> response = new HashMap<>();
        response.put("url", "/register-all");
        response.put("message", "User removed successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    String postRegister(Model model, User user, HttpSession session,
                        @AuthenticationPrincipal User userSession) {

        List<Alert> listAlertMessage = new ArrayList<>();
        try {
            if(isUserPatient(user)){
                createUserPolicy(user, listAlertMessage);
            }
            validateUser(user);
            processSaveRequest(user, listAlertMessage);
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        return "redirect:/register-all";
    }

    private void processSaveRequest(User user, List<Alert> listAlertMessage){
        ObjectUtil.process(userService.save(user),
                (User u) -> listAlertMessage.add(Alert.builder().success()
                        .message("User registered successfully").build()),
                () -> new IllegalStateException("User already exists"));
    }

    @PutMapping
    ResponseEntity<?> putRegister(Model model, @RequestBody User user, HttpSession session,
                       @AuthenticationPrincipal User userSession) {

        List<Alert> listAlertMessage = new ArrayList<>();
        try {
            keepPasswordIfChangeNotRequested(user);
            validateUser(user);
            createPatientPolicyIfAbsent(user, listAlertMessage);
            processUpdateRequest(user, listAlertMessage);
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        Map<String, String> response = new HashMap<>();
        response.put("url", "/register-all");
        response.put("message", "User removed successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void processUpdateRequest(User user, List<Alert> listAlertMessage){
        ObjectUtil.process(userService.update(user),
                (User u) -> listAlertMessage.add(Alert.builder().success()
                        .message("User updated successfully").build()),
                () -> new IllegalStateException("User already exists"));
    }

    private void keepPasswordIfChangeNotRequested(User user){
        User userTemp = userService.getByUserId(user.getId());
        if(user.getPassword().isBlank() && user.getPasswordConfirm().isBlank()){
            user.setPassword(userTemp.getPassword());
            user.setPasswordConfirm(userTemp.getPassword());
        }
    }

    private void validateUser(User user){
        RegistrationValidator.validateUser(user);
        RegistrationValidator.validateUserRole(user);
    }

    private void createPatientPolicyIfAbsent(User user, List<Alert> listAlertMessage){
        if(patientPolicyService.getPolicyByNameAndEmail(user).isEmpty()){
            createUserPolicy(user, listAlertMessage);
        }
    }

    private boolean isUserPatient(User user){
        return user.getUserRole().equals(UserRole.PATIENT);
    }

    private void createUserPolicy(User user, List<Alert> listAlertMessage){
        RegistrationValidator.validateUser(user);
        PatientPolicy patientPolicy = PatientPolicy.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
        ObjectUtil.process(patientPolicyService.save(patientPolicy),
                (PatientPolicy p) -> listAlertMessage
                        .add(Alert.builder().success().message("Policy register successfully").build()),
                () -> new IllegalStateException("Policy already exists"));
    }
}
