package com.pamela.hh.security.config;

import com.pamela.hh.user.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains(UserRole.ADMIN.name())) {
            response.sendRedirect("/register/users");
        } else if(roles.contains(UserRole.DOCTOR.name())) {
            response.sendRedirect("/index");
        } else if(roles.contains(UserRole.PATIENT.name())) {
            response.sendRedirect("/index");
        } else {
            response.sendRedirect("/register");
        }
    }
}
