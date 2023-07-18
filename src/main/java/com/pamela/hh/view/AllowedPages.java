package com.pamela.hh.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AllowedPages {

    @RequestMapping(value = {"/", "index"})
    @GetMapping
    String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("pageName", "home");
        }
        return "index";
    }
}
