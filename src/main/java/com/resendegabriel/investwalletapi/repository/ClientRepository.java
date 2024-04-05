package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUser_UserId(Long userId);
}
