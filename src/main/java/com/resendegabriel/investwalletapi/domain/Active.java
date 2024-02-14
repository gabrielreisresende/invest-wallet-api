package com.resendegabriel.investwalletapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_actives")
@Getter
@Setter
@NoArgsConstructor
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
}
