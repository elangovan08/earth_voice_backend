package com.webApp.blog.service.impl;

import com.webApp.blog.dto.response.NotificationResponseDTO;
import com.webApp.blog.exception.ResourceNotFoundException;
import com.webApp.blog.model.Notification;
import com.webApp.blog.repository.NotificationRepository;
import com.webApp.blog.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public Notification createNotification(String title, String message, String type,
                                           String username, String userEmail,
                                           Long relatedEntityId, String relatedEntityType) {
        try {
            Notification notification = new Notification(title, message, type, username, userEmail,
                    relatedEntityId, relatedEntityType);
            Notification saved = notificationRepository.save(notification);
            logger.info("Notification created: type={}, title='{}', user='{}'", type, title, username);
            return saved;
        } catch (Exception ex) {
            logger.error("Failed to create notification: type={}, title='{}', error={}", type, title, ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable)
                .map(NotificationResponseDTO::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO> getUnreadNotifications(Pageable pageable) {
        return notificationRepository.findByReadFalse(pageable)
                .map(NotificationResponseDTO::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return notificationRepository.countByReadFalse();
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.markAsRead(id);
        logger.info("Notification marked as read: id={}", id);
    }

    @Override
    @Transactional
    public void markAllAsRead() {
        notificationRepository.markAllAsRead();
        logger.info("All notifications marked as read");
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
        logger.info("Notification deleted: id={}", id);
    }
}
