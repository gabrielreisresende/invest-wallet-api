package com.resendegabriel.investwalletapi.domain.dto.response;

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

                                  List<WalletSimpleDTO> wallets) {

    public CustomerResponseDTO(Customer customer) {
        this(
                customer.getCustomerId(),
                customer.getCpf(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getBirthDate(),
                new UserResponseDTO(customer.getUser()),
                customer.getWallets()
                        .stream()
                        .map(WalletSimpleDTO::new)
                        .toList()
        );
    }
}
