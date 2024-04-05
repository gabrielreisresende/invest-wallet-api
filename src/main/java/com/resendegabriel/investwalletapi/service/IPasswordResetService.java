package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.auth.dto.EmailDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.ResetPasswordDTO;
import com.resendegabriel.investwalletapi.domain.auth.dto.TwoFactorCodeDTO;

public interface IPasswordResetService {

    void generatePasswordResetCode(EmailDTO emailDTO);

    void validateTwoFactorCode(TwoFactorCodeDTO twoFactorCodeDTO);

    void resetPassword(ResetPasswordDTO resetPasswordDTO);
}
