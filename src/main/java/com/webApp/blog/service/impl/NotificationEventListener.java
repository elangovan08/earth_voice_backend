package com.webApp.blog.service.impl;

import com.webApp.blog.dto.email.EmailNotification;
import com.webApp.blog.event.CommentCreatedEvent;
import com.webApp.blog.event.ContactSubmittedEvent;
import com.webApp.blog.event.PostBookmarkedEvent;
import com.webApp.blog.event.PostCreatedEvent;
import com.webApp.blog.event.PostDeletedEvent;
import com.webApp.blog.event.PostLikedEvent;
import com.webApp.blog.event.PostUpdatedEvent;
import com.webApp.blog.event.UserCreatedEvent;
import com.webApp.blog.event.UserLoggedInEvent;
import com.webApp.blog.service.EmailService;
import com.webApp.blog.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class NotificationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final NotificationService notificationService;
    private final EmailService emailService;

    public NotificationEventListener(NotificationService notificationService, EmailService emailService) {
        this.notificationService = notificationService;
        this.emailService = emailService;
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onUserCreated(UserCreatedEvent event) {
        LocalDateTime occurredAt = eventTime(event.registeredAt());
        notifyAdmin(
                "New User Registered",
                "User '%s' (%s) registered with role '%s'.".formatted(event.username(), event.email(), event.role()),
                "REGISTRATION",
                event.username(),
                event.email(),
                event.id(),
                "USER",
                EmailNotification.detailsOnly(
                        "New User Registered - EarthVoice",
                        "New User Registration",
                        details(
                                "Name", event.name(),
                                "Email", event.email(),
                                "Username", event.username(),
                                "Role", event.role(),
                                "Registration Date & Time", format(occurredAt),
                                "IP Address", valueOrUnavailable(event.ipAddress())
                        ),
                        occurredAt
                )
        );
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onUserLoggedIn(UserLoggedInEvent event) {
        LocalDateTime occurredAt = eventTime(event.loggedInAt());
        notifyAdmin(
                "Existing User Login",
                "User '%s' logged in.".formatted(event.username()),
                "LOGIN",
                event.username(),
                event.email(),
                event.id(),
                "USER",
                EmailNotification.detailsOnly(
                        "Existing User Login - EarthVoice",
                        "Existing User Login",
                        details(
                                "Name", event.name(),
                                "Email", event.email(),
                                "Username", event.username(),
                                "Role", event.role(),
                                "Login Date & Time", format(occurredAt)
                        ),
                        occurredAt
                )
        );
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onContactSubmitted(ContactSubmittedEvent event) {
        LocalDateTime occurredAt = eventTime(event.submittedAt());
        notifyAdmin(
                "New Contact Message",
                "Contact message from '%s' (%s) with subject '%s'.".formatted(event.name(), event.email(), event.subject()),
                "CONTACT",
                event.name(),
                event.email(),
                event.id(),
                "CONTACT",
                EmailNotification.withMessage(
                        "New Contact Message - EarthVoice",
                        "New Contact Message",
                        details(
                                "Name", event.name(),
                                "Email", event.email(),
                                "Subject", event.subject(),
                                "Submitted Time", format(occurredAt)
                        ),
                        "Message",
                        event.message(),
                        occurredAt
                )
        );
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onPostCreated(PostCreatedEvent event) {
        LocalDateTime occurredAt = eventTime(event.createdAt());
        notifyAdmin(
                "New Blog Published",
                "User '%s' published post '%s' in category '%s'.".formatted(
                        event.authorUsername(), event.title(), event.category()),
                "POST_CREATED",
                event.authorUsername(),
                event.authorEmail(),
                event.id(),
                "POST",
                EmailNotification.detailsOnly(
                        "New Blog Published",
                        "New Blog Published",
                        details(
                                "Author Name", event.authorName(),
                                "Author Email", event.authorEmail(),
                                "Post Title", event.title(),
                                "Category", event.category(),
                                "Created Time", format(occurredAt)
                        ),
                        occurredAt
                )
        );
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onPostLiked(PostLikedEvent event) {
        LocalDateTime occurredAt = eventTime(event.likedAt());
        notifyAdmin(
                "Blog Liked",
                "User '%s' liked post '%s' by '%s'.".formatted(
                        event.likerUsername(), event.postTitle(), event.postAuthorName()),
                "POST_LIKED",
                event.likerUsername(),
                event.likerEmail(),
                event.postId(),
                "POST",
                EmailNotification.detailsOnly(
                        "Blog Liked",
                        "Blog Liked",
                        details(
                                "User Name", event.likerUsername(),
                                "User Email", event.likerEmail(),
                                "Blog Title", event.postTitle(),
                                "Blog Author", event.postAuthorName(),
                                "Time", format(occurredAt)
                        ),
                        occurredAt
                )
        );
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onPostBookmarked(PostBookmarkedEvent event) {
        LocalDateTime occurredAt = eventTime(event.bookmarkedAt());
        notifyAdmin(
                "Blog Bookmarked",
                "User '%s' bookmarked post '%s'.".formatted(event.bookmarkUsername(), event.postTitle()),
                "POST_BOOKMARKED",
                event.bookmarkUsername(),
                event.bookmarkEmail(),
                event.postId(),
                "POST",
                EmailNotification.detailsOnly(
                        "Blog Bookmarked",
                        "Blog Bookmarked",
                        details(
                                "User Name", event.bookmarkName(),
                                "User Email", event.bookmarkEmail(),
                                "Blog Title", event.postTitle(),
                                "Time", format(occurredAt)
                        ),
                        occurredAt
                )
        );
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onCommentCreated(CommentCreatedEvent event) {
        LocalDateTime occurredAt = eventTime(event.commentedAt());
        notifyAdmin(
                "New Comment",
                "User '%s' commented on post '%s': %s".formatted(
                        event.commenterUsername(), event.postTitle(), event.commentContent()),
                "COMMENT",
                event.commenterUsername(),
                event.commenterEmail(),
                event.commentId(),
                "COMMENT",
                EmailNotification.withMessage(
                        "New Comment",
                        "New Comment",
                        details(
                                "User Name", event.commenterName(),
                                "User Email", event.commenterEmail(),
                                "Blog Title", event.postTitle(),
                                "Time", format(occurredAt)
                        ),
                        "Comment",
                        event.commentContent(),
                        occurredAt
                )
        );
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onPostUpdated(PostUpdatedEvent event) {
        LocalDateTime occurredAt = eventTime(event.updatedAt());
        String previousTitle = Objects.equals(event.previousTitle(), event.newTitle())
                ? "(unchanged)"
                : valueOrUnavailable(event.previousTitle());
        String titleChange = Objects.equals(event.previousTitle(), event.newTitle())
                ? ""
                : " Previous title: '%s'.".formatted(previousTitle);
        notifyAdmin(
                "Blog Updated",
                "User '%s' updated post '%s'.%s".formatted(
                        event.updaterUsername(), event.newTitle(), titleChange),
                "POST_UPDATED",
                event.updaterUsername(),
                event.updaterEmail(),
                event.postId(),
                "POST",
                EmailNotification.detailsOnly(
                        "Blog Updated",
                        "Blog Updated",
                        details(
                                "User Name", event.updaterName(),
                                "Blog Title", event.newTitle(),
                                "Previous Title", previousTitle,
                                "Updated Time", format(occurredAt)
                        ),
                        occurredAt
                )
        );
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onPostDeleted(PostDeletedEvent event) {
        LocalDateTime occurredAt = eventTime(event.deletedAt());
        notifyAdmin(
                "Blog Deleted",
                "User '%s' deleted post '%s'.".formatted(event.deleterUsername(), event.postTitle()),
                "POST_DELETED",
                event.deleterUsername(),
                event.deleterEmail(),
                event.postId(),
                "POST",
                EmailNotification.detailsOnly(
                        "Blog Deleted",
                        "Blog Deleted",
                        details(
                                "User Name", event.deleterName(),
                                "Blog Title", event.postTitle(),
                                "Deleted Time", format(occurredAt)
                        ),
                        occurredAt
                )
        );
    }

    private void notifyAdmin(
            String title,
            String message,
            String type,
            String username,
            String userEmail,
            Long relatedEntityId,
            String relatedEntityType,
            EmailNotification emailNotification
    ) {
        try {
            notificationService.createNotification(
                    title, limitMessage(message), type, username, userEmail, relatedEntityId, relatedEntityType);
        } catch (Exception ex) {
            logger.error("Notification processing failed for type={}: {}", type, ex.getMessage(), ex);
        }

        try {
            emailService.sendAdminNotification(emailNotification);
        } catch (Exception ex) {
            logger.error("Email dispatch failed for type={}: {}", type, ex.getMessage(), ex);
        }
    }

    private Map<String, String> details(String... values) {
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        for (int i = 0; i + 1 < values.length; i += 2) {
            details.put(values[i], values[i + 1]);
        }
        return details;
    }

    private LocalDateTime eventTime(LocalDateTime value) {
        return value == null ? LocalDateTime.now() : value;
    }

    private String format(LocalDateTime value) {
        return value.format(FORMATTER);
    }

    private String valueOrUnavailable(String value) {
        return value == null || value.isBlank() ? "Unavailable" : value;
    }

    private String limitMessage(String value) {
        if (value == null) {
            return "";
        }
        return value.length() <= 2000 ? value : value.substring(0, 1997) + "...";
    }
}
