package com.webApp.blog.service;

import com.webApp.blog.dto.email.EmailNotification;

public interface EmailService {

    void sendAdminNotification(EmailNotification notification);
}
