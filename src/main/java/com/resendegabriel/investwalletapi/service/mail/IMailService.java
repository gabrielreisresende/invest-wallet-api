package com.resendegabriel.investwalletapi.service.mail;

public interface IMailService {

    void sendMail(String mailAddress, String subject, String text);
}
