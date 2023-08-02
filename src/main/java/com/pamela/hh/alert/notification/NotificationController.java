package com.pamela.hh.alert.notification;

import com.pamela.hh.alert.heart.AlertHeartRate;
import com.pamela.hh.alert.heart.AlertHeartRateService;
import com.pamela.hh.alert.ui.Alert;
import com.pamela.hh.alert.ui.Notification;
import com.pamela.hh.entity.BaseController;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserRole;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "check")
public class NotificationController extends BaseController {

    private final AlertHeartRateService alertHeartRateService;

    @Autowired
    public NotificationController(AlertHeartRateService alertHeartRateService) {
        this.alertHeartRateService = alertHeartRateService;
    }

    @GetMapping(value = "notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getNotifications(@AuthenticationPrincipal User user) {

        if(user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        List<Notification> response = new ArrayList<>();
        if (user.getUserRole().equals(UserRole.DOCTOR)) {
            List<AlertHeartRate> alertHeartRates = alertHeartRateService
                    .getAlertHeartRateByDoctor(user.getId())
                    .orElse(new ArrayList<>());

            alertHeartRates.forEach(alertHeartRate -> {

                User patient = alertHeartRate.getPatient().getPatient();

                String tailoredMessage = String.format("Patient %s has a blood pressure of %d / %d",
                        patient.getUsername(), alertHeartRate.getHeartRate().getSbp(),
                        alertHeartRate.getHeartRate().getDbp());

                Notification notification = Notification.builder()
                        .danger()
                        .id(alertHeartRate.getId())
                        .url(String.format("/check/notifications/%d", patient.getId()))
                        .timestamp(alertHeartRate.getHeartRate().getTimestamp())
                        .message(tailoredMessage)
                        .build();
                response.add(notification);
            });
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "notifications/{id}")
    String notificationClicked(Model model, HttpSession httpSession,
                               @AuthenticationPrincipal User user, @PathVariable("id") String id) {

        flagAllUIAlertsIfAny(model, httpSession);
        if (user == null) return "redirect:/login";

        List<Alert> listAlertMessage = new ArrayList<>();
        alertHeartRateService.getAlertHeartRateByPatient(id).ifPresent(alertHeartRate -> {
            User patient = alertHeartRate.getPatient().getPatient();
            String tailoredMessage = String.format("Patient %s has a blood pressure of %d / %d",
                    patient.getUsername(), alertHeartRate.getHeartRate().getSbp(),
                    alertHeartRate.getHeartRate().getDbp());

            Alert alert = Alert.builder()
                    .danger()
                    .message(tailoredMessage)
                    .build();
            listAlertMessage.add(alert);
            alertHeartRateService.deleteByPatientId(Long.parseLong(id));
        });
        alertHeartRateService.getAlertHeartRateByPatient(id)
                .ifPresent(ahr -> System.err.println("Failed to delete alert for patient " + id));
        addUIAlertToSession(httpSession, listAlertMessage);
        return "redirect:/report/patient/" + id;
    }
}
