package com.user.management.util;

import com.user.management.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuthenticationUtil {
    public static User getUserByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }
}