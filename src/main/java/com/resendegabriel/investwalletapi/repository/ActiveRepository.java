package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.Active;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveRepository extends JpaRepository<Active, Long> {
}
