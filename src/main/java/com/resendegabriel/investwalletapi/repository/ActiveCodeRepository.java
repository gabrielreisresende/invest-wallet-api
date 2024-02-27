package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.ActiveCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiveCodeRepository extends JpaRepository<ActiveCode, String> {

    Optional<ActiveCode> findByActiveCode(String activeCode);
}
