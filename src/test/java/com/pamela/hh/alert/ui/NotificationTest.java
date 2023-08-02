package com.pamela.hh.alert.ui;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class NotificationTest {

    @Test
    public void testType() {
        Notification notification = Notification.builder()
                .danger()
                .id("1")
                .url("http://localhost:8080/test")
                .timestamp(LocalDateTime.now())
                .message("Alert message")
                .build();
        System.out.println(notification.getType().getLevel());
        System.out.println(notification.getType().getIcon());
        System.out.println(notification);
    }
}
