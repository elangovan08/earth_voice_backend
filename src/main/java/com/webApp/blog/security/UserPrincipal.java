package com.webApp.blog.security;

import com.webApp.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String role;
    private final Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(Long id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.authorities = authoritiesFromRole(role);
    }

    public static UserPrincipal fromUser(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );
    }

    private static Collection<? extends GrantedAuthority> authoritiesFromRole(String role) {
        if (role == null || role.isBlank()) {
            return List.of();
        }

        String normalizedRole = role.toUpperCase(Locale.ROOT);
        if (!normalizedRole.startsWith("ROLE_")) {
            normalizedRole = "ROLE_" + normalizedRole;
        }

        return List.of(new SimpleGrantedAuthority(normalizedRole));
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
