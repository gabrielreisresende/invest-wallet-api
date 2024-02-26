package com.resendegabriel.investwalletapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record WalletResponseDTO(Long walletId,

                                String name,

                                @JsonIgnoreProperties(value = {"wallets"})
                                CustomerResponseDTO customer,

                                List<ActiveResponseDTO> actives) {

    public WalletResponseDTO(Wallet wallet) {
        this(
                wallet.getWalletId(),
                wallet.getName(),
                getCustomerResponse(wallet.getCustomer()),
                getActivesList(wallet.getActives())
        );
    }

    private static CustomerResponseDTO getCustomerResponse(Customer customer) {
        return CustomerResponseDTO.builder()
                .customerId(customer.getCustomerId())
                .cpf(customer.getCpf())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .birthDate(customer.getBirthDate())
                .user(new UserResponseDTO(customer.getUser()))
                .build();
    }

    private static List<ActiveResponseDTO> getActivesList(List<Active> actives) {
        return actives == null ? null :
                actives.stream()
                        .map(ActiveResponseDTO::new).collect(Collectors.toList());
    }
}
