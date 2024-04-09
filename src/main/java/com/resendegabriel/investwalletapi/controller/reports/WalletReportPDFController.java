package com.resendegabriel.investwalletapi.controller.reports;

import com.resendegabriel.investwalletapi.controller.doc.report.pdf.DownloadReportPDFDoc;
import com.resendegabriel.investwalletapi.controller.doc.report.pdf.SendReportPDFToMailDoc;
import com.resendegabriel.investwalletapi.service.reports.IPDFGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/export/reports/pdf")
@Tag(name = "4. Export Wallet Reports", description = "Endpoints to receive the wallet completed report (download or email).")
public class WalletReportPDFController {

    @Autowired
    private IPDFGenerator pdfGenerator;

    @DownloadReportPDFDoc
    @GetMapping("/download/wallets/{walletId}")
    public void getWalletReportPDF(@PathVariable Long walletId, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=wallet-report-" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        pdfGenerator.export(response, walletId);
    }

    @SendReportPDFToMailDoc
    @GetMapping("/mail/wallets/{walletId}")
    public ResponseEntity<?> sendWalletReportPDFToMail(@PathVariable Long walletId) throws IOException {
        pdfGenerator.exportToMail(walletId);
        return ResponseEntity.ok().body("Relatório de investimentos enviado para o email");
    }
}
