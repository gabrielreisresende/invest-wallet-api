package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActivesReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ActiveRepository extends JpaRepository<Active, Long> {

    @Query("SELECT NEW com.resendegabriel.investwalletapi.domain.dto.response.reports.ActivesReportDTO(" +
            "a.activeId, " +
            "a.activeCode.activeCode, " +
            "a.quantity, " +
            "a.averageValue, " +
            "CAST(a.quantity AS BIGDECIMAL) * a.averageValue) " +
            "FROM Active a " +
            "WHERE a.wallet.walletId = :walletId")
    List<ActivesReportDTO> getActivesReport(Long walletId);

    @Query("SELECT NEW com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveTypesReportDTO(" +
            "a.activeCode.activeType.activeTypeId, " +
            "a.activeCode.activeType.activeType, " +
            "COUNT(a.activeCode.activeType), " +
            "SUM(CAST(a.quantity AS BIGDECIMAL) * a.averageValue) AS totalValue) " +
            "FROM Active a " +
            "WHERE a.wallet.walletId = :walletId " +
            "GROUP BY a.activeCode.activeType.activeTypeId")
    List<ActiveTypesReportDTO> getActiveTypesReport(Long walletId);

    @Query("SELECT NEW com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveSectorsReportDTO(" +
            "a.activeCode.activeSector.activeSectorId, " +
            "a.activeCode.activeSector.activeSector, " +
            "COUNT(a.activeCode.activeSector), " +
            "SUM(CAST(a.quantity AS BIGDECIMAL) * a.averageValue) AS totalValue) " +
            "FROM Active a " +
            "WHERE a.wallet.walletId = :walletId " +
            "GROUP BY a.activeCode.activeSector.activeSectorId")
    List<ActiveSectorsReportDTO> getActiveSectorsReport(Long walletId);

    @Query("SELECT CAST(SUM(a.quantity * a.averageValue) AS BIGDECIMAL) " +
            "FROM Active a " +
            "WHERE a.wallet.walletId = :walletId")
    BigDecimal getWalletTotalValue(Long walletId);

    @Query("SELECT COUNT(DISTINCT a.activeCode.activeType)" +
            " FROM Active a" +
            " WHERE a.wallet.walletId = :walletId")
    Integer getDistinctActiveTypesQuantity(Long walletId);

    @Query("SELECT COUNT(DISTINCT a.activeCode.activeSector)" +
            " FROM Active a" +
            " WHERE a.wallet.walletId = :walletId")
    Integer getDistinctActiveSectorsQuantity(Long walletId);
}
