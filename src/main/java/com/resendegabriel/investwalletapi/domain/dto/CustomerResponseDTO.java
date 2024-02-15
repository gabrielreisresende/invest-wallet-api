package com.resendegabriel.investwalletapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CustomerResponseDTO(Long customerId,

                                  String cpf,

                                  String firstName,

                                  String lastName,

                                  String phone,

                                  LocalDate birthDate,

                                  UserResponseDTO user,

                                  @JsonIgnoreProperties(value = {"customerResponseDTO, actives"})
                                  List<WalletResponseDTO> wallets) {

    public CustomerResponseDTO(Customer customer) {
        this(
                customer.getCustomerId(),
                customer.getCpf(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getBirthDate(),
                new UserResponseDTO(customer.getUser()),
                customer.getWallets().stream().map(WalletResponseDTO::new).toList()
        );
    }
}
