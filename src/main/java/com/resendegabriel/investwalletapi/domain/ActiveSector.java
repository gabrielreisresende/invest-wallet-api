package com.resendegabriel.investwalletapi.domain;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveSectorRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveSectorResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_active_sectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activeSectorId;

    @Column(nullable = false, length = 50, unique = true)
    private String activeSector;

    public ActiveSector(ActiveSectorRequestDTO activeSectorRequestDTO) {
        this.activeSector = activeSectorRequestDTO.activeSector();
    }

    public ActiveSector(ActiveSectorResponseDTO activeSectorResponseDTO) {
        this.activeSectorId = activeSectorResponseDTO.activeSectorId();
        this.activeSector = activeSectorResponseDTO.activeSector();
    }

    public void updateActiveSectorName(String newActiveSectorName) {
        this.activeSector = newActiveSectorName;
    }

    public ActiveSectorResponseDTO toDto() {
        return new ActiveSectorResponseDTO(this.activeSectorId, this.activeSector);
    }
}
