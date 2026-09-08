package com.webApp.blog.event;

import com.webApp.blog.model.Contact;

public record ContactSubmittedEvent(
        Long id,
        String name,
        String email,
        String subject,
        String message,
        java.time.LocalDateTime submittedAt
) {

    public ContactSubmittedEvent(Long id, String name, String email, String subject, String message) {
        this(id, name, email, subject, message, null);
    }

    public static ContactSubmittedEvent from(Contact contact) {
        return new ContactSubmittedEvent(
                contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getSubject(),
                contact.getMessage(),
                contact.getCreatedAt()
        );
    }
}
