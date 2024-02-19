package com.resendegabriel.investwalletapi.domain;

import com.resendegabriel.investwalletapi.domain.dto.WalletRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(nullable = false, length = 50)
    private String name;

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "wallet")
    private List<Active> actives;

    public Wallet(WalletRequestDTO walletRequestDTO, Customer customer) {
        this.name = walletRequestDTO.name();
        this.customer = customer;
    }
}
