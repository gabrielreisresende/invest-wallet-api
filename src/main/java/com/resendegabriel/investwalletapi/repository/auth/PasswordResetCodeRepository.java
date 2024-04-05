package com.resendegabriel.investwalletapi.repository.auth;

import com.resendegabriel.investwalletapi.domain.auth.PasswordResetCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {

    PasswordResetCode findByUser_UserId(Long userId);

    boolean existsByCodeAndUser_UserId(int code, Long userId);

    void deleteByUser_UserId(Long userId);
}
