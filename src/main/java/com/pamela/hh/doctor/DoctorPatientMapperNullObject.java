package com.pamela.hh.doctor;

import com.pamela.hh.patient.PatientNullObject;
import com.pamela.hh.user.User;

public class DoctorPatientMapperNullObject extends DoctorPatientMapper {

    public DoctorPatientMapperNullObject() {
        patient = new PatientNullObject();
        doctor = User.builder().id(-1l).build();
    }
}
