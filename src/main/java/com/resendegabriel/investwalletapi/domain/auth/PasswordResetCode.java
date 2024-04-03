package com.resendegabriel.investwalletapi.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_passoword_reset_codes")
@NoArgsConstructor
@Getter
public class PasswordResetCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int code;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public PasswordResetCode(int code, User user) {
        this.code = code;
        this.user = user;
    }
}