package com.pamela.hh.patient;


import com.pamela.hh.user.User;

public class PatientNullObject extends Patient {

    public PatientNullObject() {
        this.weight = 0;
        this.height = 0;
        this.gender = Gender.OTHER;
        this.smoker = Smoker.NO;
    }
}
