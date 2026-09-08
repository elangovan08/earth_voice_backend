package com.webApp.blog.service;

import com.webApp.blog.dto.response.NotificationResponseDTO;
import com.webApp.blog.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Notification createNotification(String title, String message, String type,
                                    String username, String userEmail,
                                    Long relatedEntityId, String relatedEntityType);

    Page<NotificationResponseDTO> getAllNotifications(Pageable pageable);

    Page<NotificationResponseDTO> getUnreadNotifications(Pageable pageable);

    long getUnreadCount();

    void markAsRead(Long id);

    void markAllAsRead();

    void deleteNotification(Long id);
}
