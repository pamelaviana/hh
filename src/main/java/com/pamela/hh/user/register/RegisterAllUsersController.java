package com.pamela.hh.user.register;


import com.pamela.hh.entity.BaseController;
import com.pamela.hh.user.User;
import com.pamela.hh.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "register-all")
public class RegisterAllUsersController extends BaseController {

    private final UserService userService;

    @Autowired
    public RegisterAllUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String getAllUsers(Model model, HttpSession session, @AuthenticationPrincipal User user) {
        flagAllUIAlertsIfAny(model, session);
        List<User> users = userService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("pageName", "register users");
        return "register_users";
    }

}
