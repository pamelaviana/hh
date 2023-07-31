package com.pamela.hh.alert.ui;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class AlertTest {

    @Test
    public void testAlertType() {
        Alert.Type type = Alert.Type.DANGER;
        String value = type.getValue();
        assertEquals("alert-danger", value);
    }
}
