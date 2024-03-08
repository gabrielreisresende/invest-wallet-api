package com.resendegabriel.investwalletapi.domain;

import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
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
import java.util.stream.Collectors;

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

    public void updateName(UpdateWalletDTO updateWalletDTO) {
        this.name = updateWalletDTO.walletName();
    }

    public WalletResponseDTO toWalletResponseDto() {
        return new WalletResponseDTO(this.walletId, this.name, this.customer.toDto(), getActivesResponseDtoList());
    }

    private List<ActiveResponseDTO> getActivesResponseDtoList() {
        return actives == null ? null :
                actives.stream()
                        .map(ActiveResponseDTO::new).collect(Collectors.toList());
    }

    public WalletSimpleDTO toWalletSimpleDto() {
        return new WalletSimpleDTO(this.walletId, this.name);
    }
}
