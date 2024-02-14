package com.resendegabriel.investwalletapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_active_sectors")
@Getter
@Setter
@NoArgsConstructor
public class ActiveSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activeSectorId;

    @Column(nullable = false, length = 50, unique = true)
    private String activeSector;
}
