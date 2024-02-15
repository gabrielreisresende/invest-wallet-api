package com.resendegabriel.investwalletapi.service.auth;

import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User save(String base64Credentials) {
        var user = getUserData(base64Credentials);
        return userRepository.save(user);
    }

    private User getUserData(String base64Credentials) {
        String[] decryptedCredentials = authenticationService.getBase64Credentials(base64Credentials);
        var userEmail = decryptedCredentials[0];
        var userPassword = decryptedCredentials[1];
        return new User(userEmail, new BCryptPasswordEncoder().encode(userPassword), UserRole.CUSTOMER);
    }
}
