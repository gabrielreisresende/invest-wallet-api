package com.resendegabriel.investwalletapi.domain;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveCodeRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_active_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveCode {

    @Id
    private String activeCode;

    @JoinColumn(name = "active_type_id", nullable = false)
    @ManyToOne
    private ActiveType activeType;

    @JoinColumn(name = "active_sector_id", nullable = false)
    @ManyToOne
    private ActiveSector activeSector;

    public ActiveCode(ActiveCodeRequestDTO activeCodeRequestDTO) {
        this.activeCode = activeCodeRequestDTO.activeCode();
        this.activeType = new ActiveType(activeCodeRequestDTO.activeType());
        this.activeSector = new ActiveSector(activeCodeRequestDTO.activeSector());
    }
}
