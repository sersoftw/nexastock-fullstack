package com.portfolio.nexastock.auth;

import com.portfolio.nexastock.user.Role;

public record AuthResponse(
        String token,
        String email,
        String fullName,
        Role role
) {
}
