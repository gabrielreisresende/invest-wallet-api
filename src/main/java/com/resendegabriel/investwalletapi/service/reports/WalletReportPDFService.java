package com.resendegabriel.investwalletapi.service.reports;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.resendegabriel.investwalletapi.utils.WalletReportsToPDFUtility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WalletReportPDFService implements IPDFGenerator {

    @Autowired
    private WalletReportsToPDFUtility walletReportsToPDFUtility;

    private static final String INVEST_WALLET_TITLE = "Invest Wallet Reports";

    @Override
    public void export(HttpServletResponse response, Long walletId) throws IOException {
        walletReportsToPDFUtility.setAttributes(walletId);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        setReportPageGenericSettings(document);

        document.add(walletReportsToPDFUtility.getWalletInfo());

        document.close();
    }

    private void setReportPageGenericSettings(Document document) throws IOException {
        Font investWalletFontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        investWalletFontTitle.setSize(18);

        Paragraph investWalletTitle = new Paragraph(INVEST_WALLET_TITLE, investWalletFontTitle);
        investWalletTitle.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(investWalletTitle);
    }
}
