package com.webApp.blog.controller;

import com.webApp.blog.dto.response.ApiResponseDTO;
import com.webApp.blog.dto.response.NotificationResponseDTO;
import com.webApp.blog.dto.response.PageResponseDTO;
import com.webApp.blog.service.NotificationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/admin/notifications")
public class AdminNotificationController {

    private static final int MAX_PAGE_SIZE = 50;
    private static final Set<String> SORT_FIELDS = Set.of(
            "id", "title", "type", "username", "userEmail", "relatedEntityId", "createdAt", "read");

    private final NotificationService notificationService;

    public AdminNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<PageResponseDTO<NotificationResponseDTO>>> getAllNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Pageable pageable = pageable(page, size, sortBy, direction);
        PageResponseDTO<NotificationResponseDTO> response = PageResponseDTO.from(
                notificationService.getAllNotifications(pageable));
        return ResponseEntity.ok(ApiResponseDTO.success("Notifications fetched successfully", response));
    }

    @GetMapping("/unread")
    public ResponseEntity<ApiResponseDTO<PageResponseDTO<NotificationResponseDTO>>> getUnreadNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = pageable(page, size, "createdAt", "desc");
        PageResponseDTO<NotificationResponseDTO> response = PageResponseDTO.from(
                notificationService.getUnreadNotifications(pageable));
        return ResponseEntity.ok(ApiResponseDTO.success("Unread notifications fetched successfully", response));
    }

    @GetMapping("/unread/count")
    public ResponseEntity<ApiResponseDTO<Long>> getUnreadCount() {
        return ResponseEntity.ok(ApiResponseDTO.success("Unread count fetched", notificationService.getUnreadCount()));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponseDTO<Void>> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Notification marked as read", null));
    }

    @PutMapping("/read-all")
    public ResponseEntity<ApiResponseDTO<Void>> markAllAsRead() {
        notificationService.markAllAsRead();
        return ResponseEntity.ok(ApiResponseDTO.success("All notifications marked as read", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Notification deleted successfully", null));
    }

    private Pageable pageable(int page, int size, String sortBy, String direction) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        String safeSortBy = SORT_FIELDS.contains(sortBy) ? sortBy : "createdAt";
        Sort.Direction sortDirection = Sort.Direction.fromOptionalString(direction)
                .orElse(Sort.Direction.DESC);
        return PageRequest.of(safePage, safeSize, Sort.by(sortDirection, safeSortBy));
    }
}
