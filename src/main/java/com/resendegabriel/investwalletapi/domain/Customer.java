package com.resendegabriel.investwalletapi.domain;

import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.dto.CustomerRegisterDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

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

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "customer")
    private List<Wallet> wallets;

    public Customer(CustomerRegisterDTO customerRegisterDTO, User user){
        this.cpf = customerRegisterDTO.cpf();
        this.firstName = customerRegisterDTO.firstName();
        this.lastName = customerRegisterDTO.lastName();
        this.phone = customerRegisterDTO.phone();
        this.birthDate = customerRegisterDTO.birthDate();
        this.user = user;
        this.wallets = new ArrayList<>();
    }
}
