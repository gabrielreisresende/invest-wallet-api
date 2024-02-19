package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
