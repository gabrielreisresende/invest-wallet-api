package com.resendegabriel.investwalletapi.domain;

import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeRequestDTO;
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
@Table(name = "tb_active_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activeTypeId;

    @Column(nullable = false, length = 50, unique = true)
    private String activeType;

    public ActiveType(ActiveTypeRequestDTO activeTypeRequestDTO) {
        this.activeType = activeTypeRequestDTO.activeType();
    }
}
