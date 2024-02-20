package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findAllByCustomer_CustomerId(Long customerId);
}
