package com.resendegabriel.investwalletapi.domain;

import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ClientResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "clientId")
@SQLDelete(sql = "UPDATE tb_clients SET deleted = true WHERE client_id=?")
@SQLRestriction("deleted=false")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false, unique = true, updatable = false, length = 14)
    private String cpf;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    @OneToMany(mappedBy = "client")
    private List<Wallet> wallets;

    public Client(ClientRegisterDTO clientRegisterDTO, User user) {
        this.cpf = clientRegisterDTO.cpf();
        this.firstName = clientRegisterDTO.firstName();
        this.lastName = clientRegisterDTO.lastName();
        this.phone = clientRegisterDTO.phone();
        this.birthDate = clientRegisterDTO.birthDate();
        this.user = user;
        this.wallets = new ArrayList<>();
    }

    public void updateData(ClientUpdateDTO clientUpdateDTO) {
        this.cpf = clientUpdateDTO.cpf() != null ? clientUpdateDTO.cpf() : this.cpf;
        this.firstName = clientUpdateDTO.firstName() != null ? clientUpdateDTO.firstName() : this.firstName;
        this.lastName = clientUpdateDTO.lastName() != null ? clientUpdateDTO.lastName() : this.lastName;
        this.phone = clientUpdateDTO.phone() != null ? clientUpdateDTO.phone() : this.phone;
        this.birthDate = clientUpdateDTO.birthDate() != null ? clientUpdateDTO.birthDate() : this.birthDate;
    }

    public ClientResponseDTO toDto() {
        return new ClientResponseDTO(this.clientId, this.cpf, this.firstName, this.lastName,
                this.phone, this.birthDate, this.user.toDto(), getWalletSimpleDtoList());
    }

    private List<WalletSimpleDTO> getWalletSimpleDtoList() {
        return this.wallets.stream()
                .map(WalletSimpleDTO::new)
                .toList();
    }
}
