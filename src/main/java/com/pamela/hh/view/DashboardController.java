package com.pamela.hh.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pamela.hh.alert.ui.Alert;
import com.pamela.hh.chart.ChartData;
import com.pamela.hh.entity.BaseController;
import com.pamela.hh.heart.HeartRate;
import com.pamela.hh.heart.HeartRateNullObject;
import com.pamela.hh.heart.HeartRateService;
import com.pamela.hh.heart.stats.HeartRateAvg;
import com.pamela.hh.heart.stats.HeartRateStat;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        List<Alert> listAlertMessage = new ArrayList<>();
        flagAllUIAlertsIfAny(model, httpSession);
        if(user == null) return "redirect:/login";

        Patient patient = patientService.getByUserId(user.getId())
                .orElse(new PatientNullObject());

        User userPatient = Optional.ofNullable(patient.getPatient()).orElse(user);

        List<HeartRate> latestHeartRate = heartRateService.getLatestHeartRateByUserId(userPatient.getId())
                .orElse(new ArrayList<>());
        HeartRate heartRate = latestHeartRate.stream().findFirst().orElse(new HeartRateNullObject());

        String bmi = String.format("%.2f", BMICalculator.calculateBMI(patient));

//        List<HeartRate> dailyHeartRates = HeartRateStat.builder()
//                .year(LocalDate.now().getYear())
//                .month(LocalDate.now().getMonthValue())
//                .build()
//                .getFilteredByDay(latestHeartRate, LocalDate.now().getDayOfMonth());

        Map<String, HeartRateAvg> monthlyHeartRate = HeartRateStat.builder()
                .year(LocalDate.now().getYear()).build()
                .getAverageGroupedByMonth(latestHeartRate);

        ObjectMapper objectMapper = new ObjectMapper();
        ChartData chartData = mapMonthlyHeartRateToChartData(monthlyHeartRate);
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(chartData);
        } catch (JsonProcessingException e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }

        addUIAlertToSession(httpSession, listAlertMessage);
        model.addAttribute("user", user);
        model.addAttribute("userPatient", user);
        model.addAttribute("patient", patient);
        model.addAttribute("heartRate", heartRate);
        model.addAttribute("bmi", bmi);
        model.addAttribute("barChartData", jsonString);
        model.addAttribute("reportUrl", "/report/patient/" + user.getId());
        model.addAttribute("viewUrl", "/view/patient/" + user.getId());
        model.addAttribute("pageName", "Dashboard");
        return "index";
    }

    @GetMapping("/patient/{id}")
    String getPatientDashboard(Model model, HttpSession httpSession, @PathVariable("id") Long id,
                               @AuthenticationPrincipal User user){

        List<Alert> listAlertMessage = new ArrayList<>();
        flagAllUIAlertsIfAny(model, httpSession);
        Patient patient = patientService.getById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User userPatient = patient.getPatient();

        List<HeartRate> latestHeartRate = heartRateService.getLatestHeartRateByUserId(patient.getId())
                .orElse(new ArrayList<>());
        HeartRate heartRate = latestHeartRate.stream().findFirst().orElse(new HeartRateNullObject());

        String bmi = String.format("%.2f", BMICalculator.calculateBMI(patient));
        Long userPatientId = userPatient.getId();

        List<HeartRate> dailyHeartRates = HeartRateStat.builder()
                .year(LocalDate.now().getYear())
                .build()
                .getFilteredByDay(latestHeartRate, LocalDate.now().getDayOfMonth());

        ObjectMapper objectMapper = new ObjectMapper();
        ChartData chartData = mapDailyHeartRateToChartData(dailyHeartRates);
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(chartData);
        } catch (JsonProcessingException e) {
            listAlertMessage.add(Alert.builder().danger().message(e.getMessage()).build());
        }

        addUIAlertToSession(httpSession, listAlertMessage);
        model.addAttribute("user", user);
        model.addAttribute("userPatient", userPatient);
        model.addAttribute("patient", patient);
        model.addAttribute("bmi", bmi);
        model.addAttribute("barChartData", jsonString);
        model.addAttribute("viewUrl", "/view/patient/" + userPatientId);
        model.addAttribute("reportUrl", "/report/patient/" + userPatientId);
        model.addAttribute("heartRate", heartRate);
        return "index";
    }

    private ChartData mapDailyHeartRateToChartData(List<HeartRate> heartRates) {

        ChartData.Dataset datasetSystolic = ChartData.Dataset.builder()
                .label("Systolic").backgroundColor("#4e73df")
                .borderColor("#4e73df").build();

        ChartData.Dataset datasetDiastolic = ChartData.Dataset.builder()
                .label("Diastolic").backgroundColor("#36b9cc")
                .borderColor("#36b9cc").build();

        List<String> labels = new ArrayList<>();

        heartRates.forEach(heartRate -> {
            datasetSystolic.getData().add(heartRate.getSbp());
            datasetDiastolic.getData().add(heartRate.getDbp());
            String hourAndMinute = heartRate.getTimestamp().getHour() + ":" + heartRate.getTimestamp().getMinute();
            labels.add(hourAndMinute);
        });
        return ChartData.builder().labels(labels)
                .dataset(List.of(datasetSystolic, datasetDiastolic)).build();
    }

    private ChartData mapMonthlyHeartRateToChartData(Map<String, HeartRateAvg> monthlyHeartRate) {

            ChartData.Dataset datasetSystolic = ChartData.Dataset.builder()
                    .label("Systolic").backgroundColor("#4e73df")
                    .borderColor("#4e73df").build();

            ChartData.Dataset datasetDiastolic = ChartData.Dataset.builder()
                    .label("Diastolic").backgroundColor("#36b9cc")
                    .borderColor("#36b9cc").build();

            List<String> labels = new ArrayList<>();

            monthlyHeartRate.forEach((month, heartRateAvg) -> {
                datasetSystolic.getData().add(heartRateAvg.getSbpAvg());
                datasetDiastolic.getData().add(heartRateAvg.getDbpAvg());
                labels.add(month);
            });
            return ChartData.builder().labels(labels)
                    .dataset(List.of(datasetSystolic, datasetDiastolic)).build();
    }

}
