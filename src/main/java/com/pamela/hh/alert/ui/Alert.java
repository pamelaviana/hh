package com.pamela.hh.alert.ui;

import jakarta.persistence.Transient;
import lombok.*;

import java.util.Collection;

@Builder @Data @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class Alert {

    @Transient
    @Setter(AccessLevel.NONE)
    public static final String ALERT_MESSAGES = "alertMessages";

    protected Alert.Type type;
    protected String highlight;
    protected String message;
    protected boolean dismissed = false;

    @Getter
    public enum Type {
        DANGER("alert-danger"),
        WARNING("alert-warning"),
        INFO("alert-info"),
        SUCCESS("alert-success");

        private final String value;

        Type(String value) {
            this.value = value;
        }

    }

    public static void flagDismissed(Alert alert) {
        alert.setDismissed(true);
    }

    public static void flagAllDismissed(Collection<Alert> alerts) {
        alerts.forEach(alert -> alert.setDismissed(true));
    }

    public static class AlertBuilder {
        public AlertBuilder danger() {
            this.type = Type.DANGER;
            this.highlight = "Error: ";
            return this;
        }

        public AlertBuilder warning() {
            this.type = Type.WARNING;
            this.highlight = "Warning: ";
            return this;
        }

        public AlertBuilder info() {
            this.type = Type.INFO;
            this.highlight = "Info: ";
            return this;
        }

        public AlertBuilder success() {
            this.type = Type.SUCCESS;
            this.highlight = "Success: ";
            return this;
        }
    }
}
