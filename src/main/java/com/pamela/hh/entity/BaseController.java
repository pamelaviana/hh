package com.pamela.hh.entity;

import com.pamela.hh.alert.ui.Alert;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BaseController {

    protected void addUIAlertToSession(HttpSession session, List<Alert> alerts) {
        session.setAttribute(Alert.ALERT_MESSAGES, alerts);
    }

    protected void flagAllUIAlertsIfAny(Model model,HttpSession session) {
        List<Alert> listAlertMessage = Optional.ofNullable((List <Alert>) session.getAttribute(Alert.ALERT_MESSAGES))
                .orElse(new ArrayList<>())
                        .stream().filter(alert -> !alert.isDismissed())
                        .collect(Collectors.toList());
        Alert.flagAllDismissed(listAlertMessage);
        session.removeAttribute(Alert.ALERT_MESSAGES);
        model.addAttribute(Alert.ALERT_MESSAGES, listAlertMessage);
    }
}
