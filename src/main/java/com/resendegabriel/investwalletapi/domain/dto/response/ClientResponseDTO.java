package com.resendegabriel.investwalletapi.domain.dto.response;

import com.resendegabriel.investwalletapi.domain.Client;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ClientResponseDTO(Long clientId,

                                String cpf,

                                String firstName,

                                String lastName,

                                String phone,

                                LocalDate birthDate,

                                UserResponseDTO user,

                                List<WalletSimpleDTO> wallets) {

    public ClientResponseDTO(Client client) {
        this(
                client.getClientId(),
                client.getCpf(),
                client.getFirstName(),
                client.getLastName(),
                client.getPhone(),
                client.getBirthDate(),
                new UserResponseDTO(client.getUser()),
                client.getWallets()
                        .stream()
                        .map(WalletSimpleDTO::new)
                        .toList()
        );
    }
}
