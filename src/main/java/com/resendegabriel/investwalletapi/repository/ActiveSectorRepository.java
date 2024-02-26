package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.ActiveSector;
import com.resendegabriel.investwalletapi.domain.ActiveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveSectorRepository extends JpaRepository<ActiveSector, Long> {
}
