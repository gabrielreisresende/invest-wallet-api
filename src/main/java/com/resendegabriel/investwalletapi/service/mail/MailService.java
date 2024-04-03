package com.resendegabriel.investwalletapi.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService {

    @Value("${spring.mail.username}")
    private String mailSenderAddress;

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Override
    public void sendMail(String mailAddress, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        var subjectFormatted = formatSubject(subject);

        message.setFrom(mailSenderAddress);
        message.setTo(mailAddress);
        message.setSubject(subjectFormatted);
        message.setText(text);

        logger.info(String.valueOf(message));
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new MailSendException("Failed to send email. " + e.getMessage());
        }
    }

    private String formatSubject(String subject) {
        StringBuilder builder = new StringBuilder(subject.substring(0, 1).toUpperCase());
        builder.append(subject.substring(1).toLowerCase());
        return builder.toString();
    }
}