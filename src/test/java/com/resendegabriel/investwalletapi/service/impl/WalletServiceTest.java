package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.WalletResponseDTO;
import com.resendegabriel.investwalletapi.repository.WalletRepository;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private ICustomerService customerService;

    private static WalletRequestDTO walletRequestDTO;

    private static WalletResponseDTO walletResponseDTO;

    private static Wallet wallet;

    private static Customer customer;

    @BeforeAll
    static void init() {
        walletRequestDTO = WalletRequestDTO.builder()
                .name("Wallet name")
                .customerId(1L)
                .build();

        customer = Customer.builder()
                .customerId(1L)
                .user(User.builder()
                        .userId(1L)
                        .build())
                .wallets(new ArrayList<>())
                .build();

        wallet = Wallet.builder()
                .walletId(1L)
                .customer(customer)
                .name(walletRequestDTO.name())
                .actives(new ArrayList<>())
                .build();

        walletResponseDTO = WalletResponseDTO.builder()
                .walletId(wallet.getWalletId())
                .name(walletRequestDTO.name())
                .customer(new CustomerResponseDTO(customer))
                .actives(new ArrayList<>())
                .build();
    }

    @Test
    void shouldCreateANewWallet() {
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        var response = walletService.create(walletRequestDTO);

        assertEquals(new WalletResponseDTO(wallet), response);
        then(customerService).should().findById(anyLong());
        then(customerService).shouldHaveNoMoreInteractions();
        then(walletRepository).should().save(any(Wallet.class));
        then(walletRepository).shouldHaveNoMoreInteractions();
    }
}