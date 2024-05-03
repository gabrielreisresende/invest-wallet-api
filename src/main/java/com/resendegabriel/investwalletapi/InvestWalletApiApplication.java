package com.resendegabriel.investwalletapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InvestWalletApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestWalletApiApplication.class, args);
    }

}
