package com.resendegabriel.investwalletapi.service.auth;

import com.resendegabriel.investwalletapi.domain.auth.PasswordResetCode;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.dto.EmailDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.ResetPasswordDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.TwoFactorCodeDTO;
import com.resendegabriel.investwalletapi.repository.auth.PasswordResetCodeRepository;
import com.resendegabriel.investwalletapi.service.IPasswordResetService;
import com.resendegabriel.investwalletapi.service.mail.IMailService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PasswordResetService implements IPasswordResetService {

    @Autowired
    private PasswordResetCodeRepository passwordResetCodeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IMailService mailService;

    @Override
    public void generatePasswordResetCode(EmailDTO emailDTO) {
        var userEmail = emailDTO.email();
        var user = getUserByEmail(userEmail);

        PasswordResetCode passwordResetCode = verifyIfUserAlreadyHasAPasswordResetCode(user.getUserId());

        if (passwordResetCode != null) {
            sendPasswordResetCodeToUserEmail(userEmail, passwordResetCode.getCode());
        } else {
            int code = generateTwoFactorCode();
            sendPasswordResetCodeToUserEmail(userEmail, code);
            passwordResetCodeRepository.save(new PasswordResetCode(code, user));
        }
    }

    @Override
    public void validateTwoFactorCode(TwoFactorCodeDTO twoFactorCodeDTO) {
        var user = getUserByEmail(twoFactorCodeDTO.email());
        if (!passwordResetCodeRepository.existsByCodeAndUser_UserId(twoFactorCodeDTO.code(), user.getUserId()))
            throw new ValidationException("Two factor code is not valid");
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        var user = getUserByEmail(resetPasswordDTO.email());
        userService.updatePassword(resetPasswordDTO.password(), user);
        deleteExpiredCodes(user.getUserId());
    }

    private void deleteExpiredCodes(Long userId) {
        passwordResetCodeRepository.deleteByUser_UserId(userId);
    }

    private int generateTwoFactorCode() {
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }

    private PasswordResetCode verifyIfUserAlreadyHasAPasswordResetCode(Long userId) {
        return passwordResetCodeRepository.findByUser_UserId(userId);
    }

    private void sendPasswordResetCodeToUserEmail(String email, int code) {
        mailService.sendResetPasswordCodeToEmail(email, code);
    }

    private User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }
}
