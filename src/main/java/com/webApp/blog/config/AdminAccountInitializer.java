package com.webApp.blog.config;

import com.webApp.blog.model.User;
import com.webApp.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;

@Component
public class AdminAccountInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AdminAccountInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminName;
    private final String adminUsername;
    private final String adminPassword;

    public AdminAccountInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.security.admin.email:elangovandev27@gmail.com}") String adminEmail,
            @Value("${app.security.admin.name:Elangovan}") String adminName,
            @Value("${app.security.admin.username:elangovan}") String adminUsername,
            @Value("${app.security.admin.password:}") String adminPassword
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void enforceSingleAdmin() {
        List<User> matchingUsers = userRepository.findByEmailIgnoreCaseOrderByIdAsc(adminEmail);
        User designatedAdmin = matchingUsers.stream()
                .min(Comparator.comparing(User::getId))
                .orElse(null);

        if (designatedAdmin == null) {
            if (!StringUtils.hasText(adminPassword)) {
                logger.warn("Designated admin '{}' does not exist. Set ADMIN_PASSWORD to provision it.", adminEmail);
            } else {
                designatedAdmin = new User();
                designatedAdmin.setEmail(adminEmail);
                designatedAdmin.setUsername(adminUsername);
                designatedAdmin.setPassword(passwordEncoder.encode(adminPassword));
                userRepository.save(designatedAdmin);
                logger.info("Provisioned designated administrator '{}'.", adminEmail);
            }
        }

        if (designatedAdmin != null) {
            User usernameOwner = userRepository.findByUsername(adminUsername).orElse(null);
            if (usernameOwner != null && usernameOwner != designatedAdmin) {
                throw new IllegalStateException("Administrator username is already used by another account: " + adminUsername);
            }
            designatedAdmin.setUsername(adminUsername);
            designatedAdmin.setName(adminName);
            designatedAdmin.setRole("ADMIN");
            if (StringUtils.hasText(adminPassword)) {
                designatedAdmin.setPassword(passwordEncoder.encode(adminPassword));
            }
        }

        for (User user : userRepository.findAll()) {
            if (user == designatedAdmin) {
                continue;
            } else if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                user.setRole("POSTER");
            }
        }
    }
}