package com.resendegabriel.investwalletapi.service.reports;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IPDFGenerator {

    void export(HttpServletResponse response, Long walletId) throws IOException;

    void exportToMail(Long walletId) throws IOException;
}
