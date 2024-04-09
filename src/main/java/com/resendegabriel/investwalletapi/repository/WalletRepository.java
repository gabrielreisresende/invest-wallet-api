package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findAllByClient_ClientId(Long clientId);

    @Query(value = "SELECT w.client.user.email FROM Wallet w WHERE w.walletId = :walletId")
    String findWalletOwnerMail(Long walletId);
}
