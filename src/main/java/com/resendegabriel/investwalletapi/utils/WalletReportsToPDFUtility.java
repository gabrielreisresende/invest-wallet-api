package com.resendegabriel.investwalletapi.utils;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.RGBColor;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActivesReportDTO;
import com.resendegabriel.investwalletapi.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

@Component
public class WalletReportsToPDFUtility {

    private WalletActivesReportDTO walletActivesReportDTO;

    private WalletActiveTypesReportDTO walletActiveTypesReportDTO;

    private WalletActiveSectorsReportDTO walletActiveSectorsReportDTO;

    private String currencyCode;

    @Autowired
    public IWalletService walletService;

    public void setAttributes(Long walletId) {
        this.walletActivesReportDTO = walletService.getWalletActivesReport(walletId);
        this.walletActiveTypesReportDTO = walletService.getWalletActiveTypesReport(walletId);
        this.walletActiveSectorsReportDTO = walletService.getWalletActiveSectorsReport(walletId);
        this.currencyCode = Currency.getInstance(Locale.getDefault()).getSymbol();
    }

    public Paragraph getWalletInfo() {
        var walletInfo = getWalletTitle();

        if (walletHasActives()) {
            walletInfo.add(subTitle("\nResumo da Carteira:"));
            walletInfo.add(getWalletTotalValue());
            walletInfo.add(getDistinctActiveTypesQuantity());
            walletInfo.add(getDistinctActiveSectorsQuantity());
            walletInfo.add(subTitle("\nRelatório dos Ativos:"));
            walletInfo.add(getActivesReportData());
            walletInfo.add(subTitle("\nRelatório de Tipos dos Ativos:"));
            walletInfo.add(getActiveTypesReportData());
            walletInfo.add(subTitle("\nRelatório de Setores dos Ativos:"));
            walletInfo.add(getActiveSectorsReportData());
            walletInfo.add(getGeneratedDateInfo());
        }

        return walletInfo;
    }

    private Paragraph getWalletTitle() {
        Font walletTitleFontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        walletTitleFontBold.setSize(13);
        Paragraph walletTitle = new Paragraph("\nCarteira - ", walletTitleFontBold);

        Font walletTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        walletTitleFont.setSize(13);
        walletTitle.add(new Paragraph(walletActivesReportDTO.wallet().name(), walletTitleFont));

        walletTitle.setAlignment(Paragraph.ALIGN_LEFT);
        return walletTitle;
    }

    private Paragraph getWalletTotalValue() {
        Font walletTotalValueFont = FontFactory.getFont(FontFactory.HELVETICA);
        walletTotalValueFont.setSize(12);

        var walletTotalValueFormatted = currencyFormat(walletActivesReportDTO.walletTotalValue());

        return new Paragraph("- Valor total: " + walletTotalValueFormatted, walletTotalValueFont);
    }

    private Paragraph getDistinctActiveTypesQuantity() {
        Font distinctActiveTypesQuantityFont = FontFactory.getFont(FontFactory.HELVETICA);
        distinctActiveTypesQuantityFont.setSize(12);

        return new Paragraph("- Tipos de ativos diferentes: " + walletActiveTypesReportDTO.distinctActiveTypesQuantity(), distinctActiveTypesQuantityFont);
    }

    private Paragraph getDistinctActiveSectorsQuantity() {
        Font distinctActiveSectorsQuantityFont = FontFactory.getFont(FontFactory.HELVETICA);
        distinctActiveSectorsQuantityFont.setSize(12);

        return new Paragraph("- Setores de ativos diferentes: " + walletActiveSectorsReportDTO.distinctActiveSectorsQuantity(), distinctActiveSectorsQuantityFont);
    }

    private PdfPTable getActivesReportData() {
        String[] tableHeaders = {"Código do Ativo", "Valor Total(" + currencyCode + ")", "Porcentagem da Carteira (%)"};
        var table = createTable(tableHeaders);

        walletActivesReportDTO.actives()
                .forEach(active -> {
                    table.addCell(active.getActiveCode());
                    table.addCell(currencyFormat(active.getActiveTotalValue()));
                    table.addCell(active.getActiveValuePercentage() + "%");
                });
        return table;
    }

    private PdfPTable getActiveTypesReportData() {
        String[] tableHeaders = {"Tipo do Ativo", "Quantidade de Ativos", "Valor Total(" + currencyCode + ")", "Porcentagem Monetaria (%)", " Porcentagem de Quantidade (%)"};
        var table = createTable(tableHeaders);

        walletActiveTypesReportDTO.activeTypes()
                .forEach(activeType -> {
                    table.addCell(activeType.getActiveType());
                    table.addCell(String.valueOf(activeType.getQuantityOfActives()));
                    table.addCell(currencyFormat(activeType.getTotalValue()));
                    table.addCell(String.valueOf(activeType.getMonetaryPercentage()) + "%");
                    table.addCell(String.valueOf(activeType.getQuantityPercentage()) + "%");
                });

        return table;
    }

    private PdfPTable getActiveSectorsReportData() {
        String[] tableHeaders = {"Setor do Ativo", "Quantidade de Ativos", "Valor Total(" + currencyCode + ")", "Porcentagem Monetaria (%)", "Porcentagem de Quantidade (%)"};
        var table = createTable(tableHeaders);

        walletActiveSectorsReportDTO.activeSectors()
                .forEach(activeSector -> {
                    table.addCell(activeSector.getActiveSector());
                    table.addCell(String.valueOf(activeSector.getQuantityOfActives()));
                    table.addCell(currencyFormat(activeSector.getTotalValue()));
                    table.addCell(String.valueOf(activeSector.getMonetaryPercentage()) + "%");
                    table.addCell(String.valueOf(activeSector.getQuantityPercentage()) + "%");
                });

        return table;
    }

    private static PdfPTable createTable(String[] tableHeaders) {
        PdfPTable table = new PdfPTable(tableHeaders.length);

        Arrays.stream(tableHeaders)
                .forEach(header -> {
                    PdfPCell cell = new PdfPCell(new Paragraph(header));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(new RGBColor(139, 168, 218));
                    table.addCell(cell);
                });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        return table;
    }

    private Paragraph subTitle(String subTitle) {
        Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        return new Paragraph(subTitle, subTitleFont);
    }

    private Element getGeneratedDateInfo() {
        Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new Paragraph("\n\nRelatório gerado em: " + formatter.format(LocalDateTime.now()), textFont);
    }

    private boolean walletHasActives() {
        return walletActivesReportDTO.walletTotalValue() != null;
    }

    private static String currencyFormat(BigDecimal walletTotalValue) {
        return NumberFormat.getCurrencyInstance().format(walletTotalValue);
    }
}
