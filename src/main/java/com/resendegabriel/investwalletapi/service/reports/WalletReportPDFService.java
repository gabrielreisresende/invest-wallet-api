package com.resendegabriel.investwalletapi.service.reports;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.resendegabriel.investwalletapi.service.mail.IMailService;
import com.resendegabriel.investwalletapi.utils.WalletReportsToPDFUtility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WalletReportPDFService implements IPDFGenerator {

    @Autowired
    private WalletReportsToPDFUtility walletReportsToPDFUtility;

    @Autowired
    private IMailService mailService;

    private static final String INVEST_WALLET_TITLE = "Invest Wallet Reports";

    @Override
    public void export(HttpServletResponse response, Long walletId) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        setReportPageGenericSettings(document, walletId);

        document.add(walletReportsToPDFUtility.getWalletInfo());

        document.close();
    }

    @Override
    public void exportToMail(Long walletId) throws IOException {
        Document document = new Document(PageSize.A4);
        var outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        setReportPageGenericSettings(document, walletId);

        document.add(walletReportsToPDFUtility.getWalletInfo());

        document.close();

        sendPdfToMail(outputStream.toByteArray());
    }

    private void setReportPageGenericSettings(Document document, Long walletId) throws IOException {
        walletReportsToPDFUtility.setAttributes(walletId);

        Font investWalletFontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        investWalletFontTitle.setSize(18);

        Paragraph investWalletTitle = new Paragraph(INVEST_WALLET_TITLE, investWalletFontTitle);
        investWalletTitle.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(investWalletTitle);
    }

    private void sendPdfToMail(byte[] document) {
        var attachmentName = "wallet-report-" + getFormattedCurrentDate() + ".pdf";
        mailService.sendMailWithAttachment("gabriellreis2005@gmail.com", "Relatório da Carteira de Investimentos", document, attachmentName);
    }

    private static String getFormattedCurrentDate() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.format(new Date());
    }
}
