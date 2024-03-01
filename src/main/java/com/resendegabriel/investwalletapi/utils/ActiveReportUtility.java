package com.resendegabriel.investwalletapi.utils;

import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActivesReportDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class ActiveReportUtility {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.ONE.multiply(BigDecimal.valueOf(100));

    public static void setEachActivePercentage(List<ActivesReportDTO> activesReportDTO, BigDecimal walletTotalValue) {
        activesReportDTO.forEach(active ->
                active.setActiveValuePercentage(
                        calculatePercentage(active.getActiveTotalValue(), walletTotalValue)));
    }

    public static void setEachActiveTypeQuantityPercentage(List<ActiveTypesReportDTO> activeTypesReportDTO) {
        var totalActiveTypesQuantity = getTotalActiveTypesQuantity(activeTypesReportDTO);

        activeTypesReportDTO.forEach(activeType ->
                activeType.setQuantityPercentage
                        (calculatePercentage(
                                BigDecimal.valueOf(activeType.getQuantityOfActives()),
                                BigDecimal.valueOf(totalActiveTypesQuantity))));
    }

    public static void setEachActiveTypeMonetaryPercentage(List<ActiveTypesReportDTO> activeTypesReportDTO, BigDecimal walletTotalValue) {
        activeTypesReportDTO.forEach(activeType ->
                activeType.setMonetaryPercentage(
                        calculatePercentage(activeType.getTotalValue(), walletTotalValue)));
    }

    public static void setEachActiveSectorQuantityPercentage(List<ActiveSectorsReportDTO> activeSectorsReportDTO) {
        var totalActiveSectorsQuantity = getTotalActiveSectorsQuantity(activeSectorsReportDTO);

        activeSectorsReportDTO.forEach(activeSector ->
                activeSector.setQuantityPercentage(
                        calculatePercentage(
                                BigDecimal.valueOf(activeSector.getQuantityOfActives()),
                                BigDecimal.valueOf(totalActiveSectorsQuantity))));
    }

    public static void setEachActiveSectorMonetaryPercentage(List<ActiveSectorsReportDTO> activeSectorsReportDTO, BigDecimal walletTotalValue) {
        activeSectorsReportDTO.forEach(activeSector ->
                activeSector.setMonetaryPercentage(
                        calculatePercentage(activeSector.getTotalValue(), walletTotalValue)));
    }

    private static BigDecimal calculatePercentage(BigDecimal activeTotalValue, BigDecimal walletTotalValue) {
        return isWalletTotalValueZero(walletTotalValue) ? BigDecimal.ZERO
                : activeTotalValue.multiply(ONE_HUNDRED).divide(walletTotalValue, 2, RoundingMode.HALF_UP);
    }

    private static boolean isWalletTotalValueZero(BigDecimal walletTotalValue) {
        return walletTotalValue.compareTo(BigDecimal.ZERO) == 0;
    }

    private static Integer getTotalActiveTypesQuantity(List<ActiveTypesReportDTO> activeTypesReportDTO) {
        return activeTypesReportDTO.stream()
                .mapToInt(ActiveTypesReportDTO::getQuantityOfActives)
                .sum();
    }

    private static Integer getTotalActiveSectorsQuantity(List<ActiveSectorsReportDTO> activeSectorsReportDTO) {
        return activeSectorsReportDTO.stream()
                .mapToInt(ActiveSectorsReportDTO::getQuantityOfActives)
                .sum();
    }
}
