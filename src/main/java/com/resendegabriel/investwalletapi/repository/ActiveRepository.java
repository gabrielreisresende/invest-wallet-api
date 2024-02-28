package com.resendegabriel.investwalletapi.repository;

import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.dto.response.ActivesReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActiveRepository extends JpaRepository<Active, Long> {

    @Query("SELECT NEW com.resendegabriel.investwalletapi.domain.dto.response.ActivesReportDTO(" +
            "a.activeId, " +
            "a.activeCode.activeCode, " +
            "CAST(a.quantity AS BIGDECIMAL) * a.averageValue) " +
            "FROM Active a " +
            "WHERE a.wallet.walletId = :walletId")
    List<ActivesReportDTO> getActivesReport(Long walletId);
}
