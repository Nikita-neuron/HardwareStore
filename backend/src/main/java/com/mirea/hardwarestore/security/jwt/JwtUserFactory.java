package com.mirea.hardwarestore.security.jwt;

import com.mirea.hardwarestore.model.user.Role;
import com.mirea.hardwarestore.model.user.Status;
import com.mirea.hardwarestore.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday(),
                user.getPhone(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                mapToGrantedAuthority(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
