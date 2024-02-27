package com.resendegabriel.investwalletapi.domain;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_actives")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Active {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activeId;

    @Column(nullable = false, columnDefinition = "INT")
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "DECIMAL", scale = 5, precision = 2)
    private BigDecimal averageValue;

    @JoinColumn(name = "active_code", nullable = false)
    @ManyToOne
    private ActiveCode activeCode;

    @JoinColumn(name = "wallet_id", nullable = false)
    @ManyToOne
    private Wallet wallet;

    public Active(ActiveRequestDTO activeRequestDTO, Wallet wallet, ActiveCode activeCode) {
        this.quantity = activeRequestDTO.quantity();
        this.averageValue = activeRequestDTO.averageValue();
        this.activeCode = activeCode;
        this.wallet = wallet;
    }

    public void updateData(ActiveUpdateDTO activeUpdateDTO) {
        this.quantity = activeUpdateDTO.quantity() != null ? activeUpdateDTO.quantity() : this.quantity;
        this.averageValue = activeUpdateDTO.averageValue() != null ? activeUpdateDTO.averageValue() : this.averageValue;
    }
}
