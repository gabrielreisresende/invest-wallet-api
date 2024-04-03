package com.resendegabriel.investwalletapi.service.mail;

public interface IMailService {

    void sendMail(String mailAddress, String subject, String text);

    void sendWelcomeEmail(String email, String firstName);
}
