package com.pamela.hh.alert.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder @Data @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class Notification {

    @Transient
    @Setter(AccessLevel.NONE)
    public static final String ALERT_MESSAGES = "notificationMsg";

    @Transient
    public static final LocalDateTime timestamp = LocalDateTime.now();

    protected Notification.Type type;
    protected String id;
    protected String url;
    protected String date;
    protected String time;
    protected String message;

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Type {
        DANGER("bg-danger", "fas fa-exclamation-circle"),
        WARNING("bg-warning", "fas fa-exclamation-triangle"),
        INFO("bg-info", "fas fa-info-circle"),
        SUCCESS("bg-success", "fas fa-check-circle");

        private final String level;
        private final String icon;

        Type(String level, String icon) {
            this.level = level;
            this.icon = icon;
        }
    }

    public static class NotificationBuilder {

        public NotificationBuilder danger() {
            this.type = Type.DANGER;
            return this;
        }

        public NotificationBuilder warning() {
            this.type = Type.WARNING;
            return this;
        }

        public NotificationBuilder info() {
            this.type = Type.INFO;
            return this;
        }

        public NotificationBuilder success() {
            this.type = Type.SUCCESS;
            return this;
        }

        public NotificationBuilder timestamp(LocalDateTime date) {

            String capitalizedMonthName = date.getMonth().name().substring(0, 1).toUpperCase()
                    + date.getMonth().name().substring(1).toLowerCase();

            this.date = String.format("%s %s, %s", capitalizedMonthName,
                    date.getDayOfMonth(), date.getYear());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            this.time = date.format(formatter);
            return this;
        }

    }
}
