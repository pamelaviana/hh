package com.pamela.hh.user.register;

import com.pamela.hh.hospital.policy.PatientPolicy;
import com.pamela.hh.user.User;

import java.util.Objects;

public class RegistrationValidator {

    public static void validateUser(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        if(user.getFirstName().isBlank()) throw new IllegalStateException("First name cannot be blank");
        if(user.getLastName().isBlank()) throw new IllegalStateException("Last name cannot be blank");
        if(user.getEmail().isBlank()) throw new IllegalStateException("Email cannot be blank");
        if(user.getPassword().isBlank()) throw new IllegalStateException("Password cannot be blank");
        if(user.getPassword().length() < 8) throw new IllegalStateException("Password must be at least 8 characters long");
        if(!user.getPassword().equals(user.getPasswordConfirm())) throw new IllegalStateException("Passwords do not match");
    }

    public static void validateUserRole(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        if(Objects.isNull(user.getUserRole())) throw new IllegalStateException("User role cannot be blank");
    }

    public static void validatePolicy(PatientPolicy patientPolicy, String policyNumber) {
        Objects.requireNonNull(patientPolicy, "Patient policy cannot be null");
        if(!patientPolicy.getId().equals(policyNumber)) throw new IllegalStateException("Policy number does not match");
    }

    public static void validateUserPolicy(User user){
        Objects.requireNonNull(user, "User cannot be null");
        if(user.getFirstName().isBlank()) throw new IllegalStateException("First name cannot be blank");
        if(user.getLastName().isBlank()) throw new IllegalStateException("Last name cannot be blank");
        if(user.getEmail().isBlank()) throw new IllegalStateException("Email cannot be blank");
    }
}
