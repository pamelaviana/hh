package com.pamela.hh.view;

import com.pamela.hh.entity.BaseController;
import com.pamela.hh.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AllowedPages extends BaseController {

    @RequestMapping(value = {"login"})
    @GetMapping
    String login(Model model, @AuthenticationPrincipal User user) {
        SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", user);
        model.addAttribute("pageName", "login");
        return "login";
    }
}
