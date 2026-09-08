package com.webApp.blog.dto.email;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Immutable input for the shared HTML admin email template.
 */
public record EmailNotification(
        String subject,
        String heading,
        Map<String, String> details,
        String messageLabel,
        String message,
        LocalDateTime occurredAt
) {

    public EmailNotification {
        details = details == null
                ? Map.of()
                : Collections.unmodifiableMap(new LinkedHashMap<>(details));
        occurredAt = occurredAt == null ? LocalDateTime.now() : occurredAt;
    }

    public static EmailNotification detailsOnly(
            String subject,
            String heading,
            Map<String, String> details,
            LocalDateTime occurredAt
    ) {
        return new EmailNotification(subject, heading, details, null, null, occurredAt);
    }

    public static EmailNotification withMessage(
            String subject,
            String heading,
            Map<String, String> details,
            String messageLabel,
            String message,
            LocalDateTime occurredAt
    ) {
        return new EmailNotification(subject, heading, details, messageLabel, message, occurredAt);
    }
}
