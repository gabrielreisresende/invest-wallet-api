package com.resendegabriel.investwalletapi.domain.auth.dto;

import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import lombok.Builder;

@Builder
public record UserResponseDTO(Long userId,

                              String email,

                              UserRole userRole) {

    public UserResponseDTO(User user) {
        this(
                user.getUserId(),
                user.getEmail(),
                user.getRole()
        );
    }
}
