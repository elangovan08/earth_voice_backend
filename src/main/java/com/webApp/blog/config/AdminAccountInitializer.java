package com.webApp.blog.config;

import com.webApp.blog.model.User;
import com.webApp.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Component
public class AdminAccountInitializer {

    private final UserRepository userRepository;
    private final String adminEmail;
    private final String adminName;

    public AdminAccountInitializer(
            UserRepository userRepository,
            @Value("${app.security.admin.email:elangovandev27@gmail.com}") String adminEmail,
            @Value("${app.security.admin.name:Elangovan}") String adminName
    ) {
        this.userRepository = userRepository;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void enforceSingleAdmin() {
        List<User> matchingUsers = userRepository.findByEmailIgnoreCaseOrderByIdAsc(adminEmail);
        User designatedAdmin = matchingUsers.stream()
                .min(Comparator.comparing(User::getId))
                .orElse(null);

        for (User user : userRepository.findAll()) {
            if (user == designatedAdmin) {
                user.setName(adminName);
                user.setRole("ADMIN");
            } else if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                user.setRole("POSTER");
            }
        }
    }
}