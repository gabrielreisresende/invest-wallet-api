package com.resendegabriel.investwalletapi.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ActivesReportDTO {

    private Long activeId;

    private String activeCode;

    private BigDecimal activeTotalValue;

    private BigDecimal activePercent;

    public ActivesReportDTO(Long activeId, String activeCode, BigDecimal activeTotalValue) {
        this.activeId = activeId;
        this.activeCode = activeCode;
        this.activeTotalValue = activeTotalValue;
    }
}
