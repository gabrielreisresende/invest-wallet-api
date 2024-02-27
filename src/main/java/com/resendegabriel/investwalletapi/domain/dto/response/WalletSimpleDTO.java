package com.resendegabriel.investwalletapi.domain.dto.response;

import com.resendegabriel.investwalletapi.domain.Wallet;
import lombok.Builder;

@Builder
public record WalletSimpleDTO(Long walletId,

                              String name) {

    public WalletSimpleDTO(Wallet wallet) {
        this(
                wallet.getWalletId(),
                wallet.getName()
        );
    }
}
