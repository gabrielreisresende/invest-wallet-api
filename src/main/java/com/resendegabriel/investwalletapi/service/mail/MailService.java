package com.resendegabriel.investwalletapi.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

        try {
            mailSender.send(message);
            logger.info(String.valueOf(message));
        } catch (MailException e) {
            throw new MailSendException("Failed to send email. " + e.getMessage());
        }
    }

    @Override
    public void sendWelcomeEmail(String email, String firstName) {
        String subject = "Bem vindo ao Invest Wallet!";
        String message = firstName + ", seja bem vindo ao Invest Wallet!\nSeu cadastro foi realizado com sucesso!\nMuito obrigado por confiar em nossa aplicação. Agora, aproveite para gerenciar seus investimentos!";
        sendMail(email, subject, message);
    }

    @Override
    public void sendResetPasswordCodeToEmail(String email, int code) {
        String subject = "Código de verificação";
        String text = "Digite o seguinte código para conseguir redefinir sua senha: " + code;
        sendMail(email, subject, text);
    }

    @SneakyThrows
    @Override
    public void sendMailWithAttachment(String mail, String subject, byte[] attachmentData, String attachmentName) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(mail);
        helper.setSubject(subject);
        helper.setText("Olá, o relatório de investimento de sua carteira está disponível no anexo desse email.");

        InputStreamSource attachment = new ByteArrayResource(attachmentData);
        helper.addAttachment(attachmentName, attachment);

        mailSender.send(message);

    }

    private String formatSubject(String subject) {
        StringBuilder builder = new StringBuilder(subject.substring(0, 1).toUpperCase());
        builder.append(subject.substring(1).toLowerCase());
        return builder.toString();
    }
}