package com.resendegabriel.investwalletapi.domain.auth.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public enum UserRole {

    CLIENT {
        @Override
        public Collection<? extends GrantedAuthority> getSimpleGrantedAuthority() {
            return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        }
    },
    ADMIN {
        @Override
        public Collection<? extends GrantedAuthority> getSimpleGrantedAuthority() {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
    };

    public abstract Collection<? extends GrantedAuthority> getSimpleGrantedAuthority();
}
