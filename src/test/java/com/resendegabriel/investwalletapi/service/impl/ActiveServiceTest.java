package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Active;
import com.resendegabriel.investwalletapi.domain.ActiveCode;
import com.resendegabriel.investwalletapi.domain.ActiveSector;
import com.resendegabriel.investwalletapi.domain.ActiveType;
import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.repository.ActiveRepository;
import com.resendegabriel.investwalletapi.service.IActiveCodeService;
import com.resendegabriel.investwalletapi.service.IWalletService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActiveServiceTest {

    @InjectMocks
    private ActiveService activeService;

    @Mock
    private ActiveRepository activeRepository;

    @Mock
    private IActiveCodeService activeCodeService;

    @Mock
    private IWalletService walletService;

    private static ActiveRequestDTO activeRequestDTO;

    private static Active active;

    private static Wallet wallet;

    private static ActiveCode activeCode;

    private static ActiveUpdateDTO activeUpdateDTO;

    @BeforeAll
    static void init() {
        activeRequestDTO = ActiveRequestDTO.builder()
                .quantity(100)
                .averageValue(new BigDecimal("9.98"))
                .activeCode("MXRF11")
                .walletId(1L)
                .build();

        var customer = Customer.builder()
                .customerId(1L)
                .user(mock(User.class))
                .build();

        wallet = Wallet.builder()
                .walletId(activeRequestDTO.walletId())
                .customer(customer)
                .build();

        activeCode = ActiveCode.builder()
                .activeCode(activeRequestDTO.activeCode())
                .activeType(mock(ActiveType.class))
                .activeSector(mock(ActiveSector.class))
                .build();

        active = Active.builder()
                .activeId(activeRequestDTO.walletId())
                .quantity(activeRequestDTO.quantity())
                .averageValue(activeRequestDTO.averageValue())
                .activeCode(activeCode)
                .wallet(wallet)
                .build();

        activeUpdateDTO = ActiveUpdateDTO.builder()
                .quantity(150)
                .averageValue(new BigDecimal("9.99"))
                .build();
    }

    @Test
    void shouldCreateANewActive() {
        when(walletService.findWalletEntityById(anyLong())).thenReturn(wallet);
        when(activeCodeService.findActiveCodeEntity(anyString())).thenReturn(activeCode);
        when(activeRepository.save(any(Active.class))).thenReturn(active);

        var response = activeService.create(activeRequestDTO);

        assertEquals(new ActiveResponseDTO(active), response);
        then(activeRepository).should().save(any(Active.class));
        then(activeRepository).shouldHaveNoMoreInteractions();
        then(walletService).should().findWalletEntityById(anyLong());
        then(walletService).shouldHaveNoMoreInteractions();
        then(activeCodeService).should().findActiveCodeEntity(anyString());
        then(activeCodeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldUpdateAnActive() {
        when(activeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(active));

        var response = activeService.update(1L, activeUpdateDTO);

        assertEquals(new ActiveResponseDTO(active), response);
        then(activeRepository).should().findById(anyLong());
        then(activeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldDeleteAnActiveById() {
        when(activeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(active));

        activeService.deleteById(1L);

        then(activeRepository).should().findById(anyLong());
        then(activeRepository).should().deleteById(anyLong());
        then(activeRepository).shouldHaveNoMoreInteractions();
    }
}