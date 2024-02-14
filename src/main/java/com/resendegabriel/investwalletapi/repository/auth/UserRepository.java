package com.resendegabriel.investwalletapi.repository.auth;

import com.resendegabriel.investwalletapi.domain.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

}
