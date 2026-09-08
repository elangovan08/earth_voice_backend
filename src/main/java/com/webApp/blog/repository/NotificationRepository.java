package com.webApp.blog.repository;

import com.webApp.blog.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Notification> findByReadFalseOrderByCreatedAtDesc(Pageable pageable);

    Page<Notification> findByReadFalse(Pageable pageable);

    long countByReadFalse();

    @Modifying
    @Query("UPDATE Notification n SET n.read = true WHERE n.id = :id")
    int markAsRead(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Notification n SET n.read = true WHERE n.read = false")
    int markAllAsRead();
}
