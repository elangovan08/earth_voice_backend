package com.webApp.blog.service.impl;

import com.webApp.blog.config.NotificationProperties;
import com.webApp.blog.dto.email.EmailNotification;
import com.webApp.blog.service.EmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;

import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    private final MailProperties mailProperties;
    private final NotificationProperties notificationProperties;
    private final String siteUrl;

    public EmailServiceImpl(
            ObjectProvider<JavaMailSender> mailSenderProvider,
            ObjectProvider<MailProperties> mailPropertiesProvider,
            NotificationProperties notificationProperties,
            @Value("${app.base-url:http://localhost:3000}") String siteUrl
    ) {
        this.mailSenderProvider = mailSenderProvider;
        this.mailProperties = mailPropertiesProvider.getIfAvailable(MailProperties::new);
        this.notificationProperties = notificationProperties;
        this.siteUrl = siteUrl;
    }

    @PostConstruct
    void logMailConfiguration() {
        logger.info(
                "Email notifications enabled={}, recipient='{}', SMTP username configured={}, SMTP password configured={}",
                notificationProperties.isEnabled(),
                notificationProperties.getRecipient(),
                StringUtils.hasText(mailProperties.getUsername()),
                StringUtils.hasText(mailProperties.getPassword())
        );
    }

    @Override
    @Async("emailTaskExecutor")
    public void sendAdminNotification(EmailNotification notification) {
        if (notification == null) {
            logger.warn("Email notification skipped: notification payload is null.");
            return;
        }

        if (!notificationProperties.isEnabled()) {
            logger.debug("Email notification skipped because notifications are disabled.");
            return;
        }

        if (!StringUtils.hasText(notificationProperties.getRecipient())) {
            logger.warn("Email notification skipped: app.notifications.recipient is empty.");
            return;
        }

        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            logger.warn("Email notification skipped: no JavaMailSender bean available.");
            return;
        }

        String fromAddress = resolveFromAddress();
        if (!StringUtils.hasText(fromAddress)) {
            logger.warn("Email notification skipped: MAIL_USERNAME is empty and no notification from address is configured.");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(notificationProperties.getRecipient().trim());
            if (StringUtils.hasText(notificationProperties.getFromName())) {
                helper.setFrom(new InternetAddress(
                        fromAddress,
                        notificationProperties.getFromName().trim(),
                        StandardCharsets.UTF_8.name()));
            } else {
                helper.setFrom(fromAddress);
            }
            helper.setSubject(notification.subject());
            helper.setText(buildHtmlEmail(notification), true);

            mailSender.send(message);
            logger.info("Email sent successfully to '{}' with subject '{}'.",
                    notificationProperties.getRecipient().trim(), notification.subject());
        } catch (MailException ex) {
            logger.error("Failed to send email with subject '{}': {}", notification.subject(), ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error("Unexpected error sending email with subject '{}': {}",
                    notification.subject(), ex.getMessage(), ex);
        }
    }

    private String buildHtmlEmail(EmailNotification notification) {
        String contentRows = notification.details().entrySet().stream()
            .map(entry -> "<tr><td style=\"width:38%%;padding:12px 16px;border-bottom:1px solid #e5e7eb;color:#64748b;font-weight:600;vertical-align:top;\">%s</td><td style=\"padding:12px 16px;border-bottom:1px solid #e5e7eb;color:#1f2937;vertical-align:top;word-break:break-word;\">%s</td></tr>"
                        .formatted(escape(entry.getKey()), escape(entry.getValue())))
                .collect(Collectors.joining());

        String messageBlock = StringUtils.hasText(notification.message())
                ? """
                        <div style="margin-top:20px;padding:15px;background:#f0fdf4;border-left:4px solid #22c55e;border-radius:4px;">
                            <strong style="color:#166534;">%s:</strong>
                            <p style="color:#333;margin-top:8px;line-height:1.6;white-space:pre-wrap;">%s</p>
                        </div>
                        """.formatted(escape(notification.messageLabel()), escape(notification.message()))
                : "";

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="margin:0;padding:0;background-color:#f4f7f9;font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;">
                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f7f9;padding:30px 0;">
                        <tr>
                            <td align="center">
                                <table width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 2px 8px rgba(0,0,0,0.08);">
                                    <!-- Header -->
                                    <tr>
                                        <td style="background-color:#15803d;padding:25px 30px;text-align:center;">
                                            <h1 style="color:#ffffff;margin:0;font-size:24px;font-weight:700;letter-spacing:0.5px;">EarthVoice</h1>
                                            <p style="color:#dcfce7;margin:5px 0 0;font-size:13px;letter-spacing:0.3px;">Admin Notification</p>
                                        </td>
                                    </tr>
                                    <!-- Body -->
                                    <tr>
                                        <td style="padding:30px;">
                                            <h2 style="color:#166534;margin:0 0 20px;font-size:18px;font-weight:600;border-bottom:2px solid #dcfce7;padding-bottom:10px;">%s</h2>
                                            <table width="100%%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;text-align:left;">
                                                %s
                                            </table>
                                            %s
                                            <div style="margin-top:26px;text-align:center;">
                                                <a href="%s" style="display:inline-block;padding:12px 24px;background:#15803d;color:#ffffff;text-decoration:none;border-radius:6px;font-weight:600;">Visit EarthVoice</a>
                                            </div>
                                        </td>
                                    </tr>
                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color:#f9fafb;padding:15px 30px;text-align:center;border-top:1px solid #e5e7eb;">
                                            <p style="color:#9ca3af;margin:0;font-size:11px;">
                                                This is an automated notification from EarthVoice &bull; %s
                                            </p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(
                escape(notification.heading()),
                contentRows,
                messageBlock,
                escape(siteUrl),
                notification.occurredAt().format(FORMATTER)
        );
    }

    private String resolveFromAddress() {
        if (StringUtils.hasText(notificationProperties.getFrom())) {
            return notificationProperties.getFrom().trim();
        }
        if (StringUtils.hasText(mailProperties.getUsername())) {
            return mailProperties.getUsername().trim();
        }
        return null;
    }

    private String escape(String value) {
        return HtmlUtils.htmlEscape(value == null ? "" : value.trim());
    }
}
