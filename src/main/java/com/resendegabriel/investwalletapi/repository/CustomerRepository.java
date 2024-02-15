package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
