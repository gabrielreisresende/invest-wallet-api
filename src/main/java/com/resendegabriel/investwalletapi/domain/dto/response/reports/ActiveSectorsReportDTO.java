package com.resendegabriel.investwalletapi.domain.dto.response.reports;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"activeSectorId", "activeSector"})
public class ActiveSectorsReportDTO {

    private Long ActiveSectorId;

    private String ActiveSector;

    private Integer quantityOfActives;

    private BigDecimal quantityPercentage;

    private BigDecimal totalValue;

    private BigDecimal monetaryPercentage;

    public ActiveSectorsReportDTO(Long ActiveSectorId, String ActiveSector, Long quantityOfActives, BigDecimal totalValue) {
        this.ActiveSectorId = ActiveSectorId;
        this.ActiveSector = ActiveSector;
        this.quantityOfActives = Math.toIntExact(quantityOfActives);
        this.totalValue = totalValue;
    }
}
