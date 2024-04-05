package com.resendegabriel.investwalletapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.Client;
import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record WalletResponseDTO(Long walletId,

                                String name,

                                @JsonIgnoreProperties(value = {"wallets"})
                                ClientResponseDTO client,

                                @JsonIgnoreProperties(value = {"wallet"})
                                List<ActiveResponseDTO> actives) {

    public WalletResponseDTO(Wallet wallet) {
        this(
                wallet.getWalletId(),
                wallet.getName(),
                getClientResponse(wallet.getClient()),
                getActivesList(wallet.getActives())
        );
    }

    private static ClientResponseDTO getClientResponse(Client client) {
        return ClientResponseDTO.builder()
                .clientId(client.getClientId())
                .cpf(client.getCpf())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .phone(client.getPhone())
                .birthDate(client.getBirthDate())
                .user(new UserResponseDTO(client.getUser()))
                .build();
    }

    private static List<ActiveResponseDTO> getActivesList(List<Active> actives) {
        return actives == null ? null :
                actives.stream()
                        .map(ActiveResponseDTO::new).collect(Collectors.toList());
    }
}
