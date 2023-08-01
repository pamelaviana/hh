package com.pamela.hh.heart;

import com.pamela.hh.user.User;

import java.time.LocalDateTime;

public class HeartRateNullObject extends HeartRate {

    public HeartRateNullObject(){
        user = User.builder().id(-1L).build();
        sbp = 0;
        dbp = 0;
        timestamp = LocalDateTime.now();
    }

}
