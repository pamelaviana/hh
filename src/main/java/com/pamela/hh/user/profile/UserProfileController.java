package com.pamela.hh.user.profile;

import com.pamela.hh.alert.ui.Alert;
import com.pamela.hh.doctor.DoctorPatientMapper;
import com.pamela.hh.doctor.DoctorPatientMapperNullObject;
import com.pamela.hh.doctor.DoctorPatientMapperService;
import com.pamela.hh.entity.BaseController;
import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.hospital.policy.PatientPolicyNullObject;
import com.pamela.hh.hospital.policy.PatientPolicyService;
import com.pamela.hh.hospital.policy.PatientValidator;
import com.pamela.hh.location.Address;
import com.pamela.hh.location.AddressNullObject;
import com.pamela.hh.location.AddressService;
import com.pamela.hh.location.AddressValidator;
import com.pamela.hh.patient.*;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserRole;
import com.pamela.hh.user.UserService;
import com.pamela.hh.user.register.RegistrationValidator;
import com.pamela.hh.util.ObjectUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path = "profile")
public class UserProfileController extends BaseController {

    private final UserService userService;
    private final PatientPolicyService patientPolicyService;
    private final AddressService addressService;
    private final PatientService patientService;
    private final DoctorPatientMapperService doctorPatientMapperService;

    private final EnumSet<Gender> genders;
    private final EnumSet<Smoker> smokers;

    @Autowired
    public UserProfileController(UserService userService,
                                 PatientPolicyService patientPolicyService,
                                 AddressService addressService,
                                 PatientService patientService,
                                 DoctorPatientMapperService doctorPatientMapperService) {
        this.userService = userService;
        this.patientPolicyService = patientPolicyService;
        this.addressService = addressService;
        this.patientService = patientService;
        this.doctorPatientMapperService = doctorPatientMapperService;
        genders = Gender.getGenders();
        smokers = Smoker.getSmokers();
    }

    @GetMapping
    String getProfile(Model model, HttpSession session, @AuthenticationPrincipal User user) {

        if (user == null) return "redirect:/login";

        List<Alert> listAlertMessage = new ArrayList<>();
        flagAllUIAlertsIfAny(model, session);
        try {
            PatientPolicy patientPolicy = getPatientPolicy(user, listAlertMessage);
            model.addAttribute("patientPolicy", patientPolicy);

            Address address = getAddress(user, listAlertMessage);
            model.addAttribute("address", address);

            Patient patient = getPatient(user, listAlertMessage);
            model.addAttribute("patient", patient);

            getAllDoctors(model, user);

        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().warning().message(e.getMessage()).build());
            addUIAlertToSession(session, listAlertMessage);
        }
        model.addAttribute("user", user);
        model.addAttribute("genders", genders);
        model.addAttribute("smokers", smokers);
        model.addAttribute("pageName", "Profile");
        return "profile";
    }

    private void getAllDoctors(Model model, User user) {
        List<User> doctors = userService.getAllDoctors().orElse(new ArrayList<>());
        List<DoctorPatientMapper> doctorPatientMappers = doctorPatientMapperService
                .getAllDoctorsByPatientId(user.getId()).orElse(new ArrayList<>());

        DoctorPatientMapper doctorPatientMapper = doctorPatientMappers.stream().findFirst()
                        .orElse(new DoctorPatientMapperNullObject());

        model.addAttribute("doctors", doctors);
        model.addAttribute("assignedDoctor", doctorPatientMapper);
    }

    private PatientPolicy getPatientPolicy(User user, List<Alert> listAlertMessage) {
        try {
            return patientPolicyService.getPolicyByNameAndEmail(user).get();
        } catch (NoSuchElementException e) {
            listAlertMessage.add(Alert.builder().warning().message("Please add your patient policy").build());
            return new PatientPolicyNullObject();
        }
    }

    private Address getAddress(User user, List<Alert> listAlertMessage) {
        try {
            return addressService.getByUserId(user.getId()).get();
        } catch (NoSuchElementException e) {
            listAlertMessage.add(Alert.builder().warning().message("Please add your address").build());
            return new AddressNullObject();
        }
    }

    private Patient getPatient(User user, List<Alert> listAlertMessage) {
        try {
            return patientService.getByUserId(user.getId()).get();
        } catch (NoSuchElementException e) {
            listAlertMessage.add(Alert.builder().warning().message("Please add your patient").build());
            return new PatientNullObject();
        }
    }

    @PostMapping("/user")
    String putRegister(Model model, User user, HttpSession session,
                                  @AuthenticationPrincipal User userSession) {

        List<Alert> listAlertMessage = new ArrayList<>();
        try {
            if (user.getPassword().isBlank() && user.getPasswordConfirm().isBlank()) {
                user.setPassword(userSession.getPassword());
                user.setPasswordConfirm(userSession.getPassword());
            }
            RegistrationValidator.validateUser(user);
            RegistrationValidator.validateUserRole(user);
            user.setId(userSession.getId());
            ObjectUtil.process(userService.update(user),
                    (User u) -> listAlertMessage.add(Alert.builder().success().message("User updated successfully").build()),
                    () -> new IllegalStateException("User already exists"));
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        return "redirect:/profile";
    }

    @PostMapping("/address")
    String putAddress(Model model, Address address, HttpSession session,
                      @AuthenticationPrincipal User user) {

        List<Alert> listAlertMessage = new ArrayList<>();
        try {
            address.setUser(user);
            AddressValidator.validate(address);
            addressService.checkAddressDuplication(address).ifPresent(a -> {
                throw new IllegalStateException("Address already exists");
            });
            addressService.getByUserId(user.getId())
                    .ifPresentOrElse(a -> {
                        address.setId(a.getId());
                        if (AddressValidator.isValidAddress1(address)) address.setAddress1(a.getAddress1());
                        if (AddressValidator.isValidState(address)) address.setState(a.getState());
                        if (AddressValidator.isValidZip(address)) address.setZip(a.getZip());
                        addressService.update(address);
                    },
                    () -> addressService.save(address));
            listAlertMessage.add(Alert.builder().success().message("Address updated successfully").build());
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        return "redirect:/profile";
    }


    @PostMapping("/patient")
    String putPatient(Model model, Patient patient, HttpSession session,
                      @AuthenticationPrincipal User user) {
        List<Alert> listAlertMessage = new ArrayList<>();
        try {
            patientService.getByUserId(user.getId())
                    .ifPresentOrElse(p -> {
                        patient.setId(p.getId());
                        patient.setPatient(user);
                        PatientValidator.validatePatient(patient);
                        patientService.update(patient);
                    },
                    () -> {
                        patient.setPatient(user);
                        PatientValidator.validatePatient(patient);
                        patientService.save(patient);
                    });
            listAlertMessage.add(Alert.builder().success().message("Patient updated successfully").build());
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        return "redirect:/profile";
    }

    @PutMapping("/doctor")
    ResponseEntity<?> assignDoctor(Model model, @RequestBody User user, HttpSession session,
                                   @AuthenticationPrincipal User userSession) {

        Map<String, Object> response = new HashMap<>();
        List<Alert> listAlertMessage = new ArrayList<>();
        flagAllUIAlertsIfAny(model, session);
        try {
            Patient patient = patientService.getByUserId(userSession.getId())
                    .orElseThrow(() -> new IllegalStateException("You must be a patient to assign a doctor"));
            Optional.ofNullable(userService.getByUserId(user.getId())).ifPresentOrElse(
                    doctor -> {
                        if (doctor.getUserRole().equals(UserRole.DOCTOR)) {
                            doctorPatientMapperService.getAllDoctorsByPatientId(patient.getId()).ifPresentOrElse(
                                dpm -> {
                                    dpm.stream().findFirst().ifPresent(dp -> {
                                        doctorPatientMapperService.deleteByDoctorId(dp.getDoctor().getId());
                                        DoctorPatientMapper dpmNew = DoctorPatientMapper.builder()
                                                .doctor(doctor)
                                                .patient(patient)
                                                .build();
                                        doctorPatientMapperService.save(dpmNew);
                                    });
                                },
                                () -> {
                                    DoctorPatientMapper dpmNew = DoctorPatientMapper.builder()
                                            .doctor(doctor)
                                            .patient(patient)
                                            .build();
                                    doctorPatientMapperService.save(dpmNew);
                                });
                        }
                    },
                    () -> {throw new IllegalStateException("Doctor doesn't exist");});
            listAlertMessage.add(Alert.builder().success().message("Doctor assigned successfully").build());
        } catch (Exception e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }
        addUIAlertToSession(session, listAlertMessage);
        response.put("alerts", listAlertMessage);
        response.put("url", "/profile");
        return ResponseEntity.ok(response);
    }


}
