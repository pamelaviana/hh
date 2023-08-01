package com.pamela.hh.settings;

import com.pamela.hh.entity.BaseController;
import com.pamela.hh.hospital.device.DeviceToken;
import com.pamela.hh.hospital.device.DeviceTokenService;
import com.pamela.hh.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "settings")
public class SettingsController extends BaseController {

    private final DeviceTokenService deviceTokenService;

    @Autowired
    public SettingsController(DeviceTokenService deviceTokenService) {
        this.deviceTokenService = deviceTokenService;
    }

    @GetMapping
    String getSettings(Model model, @AuthenticationPrincipal User user) {
        DeviceToken deviceToken = deviceTokenService.getFirstActivetedDeviceToken();
        model.addAttribute("deviceToken", deviceToken);
        model.addAttribute("user", user);
        model.addAttribute("pageName", "Settings");
        return "settings";
    }

}
