package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.Wallet;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActivesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActivesReportDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.WalletRepository;
import com.resendegabriel.investwalletapi.service.IActiveService;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private IActiveService activeService;

    private static WalletRequestDTO walletRequestDTO;

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

    @Test
    void shouldGetAllWalletsFromACustomer() {
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(walletRepository.findAllByCustomer_CustomerId(anyLong())).thenReturn(List.of(wallet));

        var response = walletService.getAll(1L);

        assertEquals(List.of(new WalletResponseDTO(wallet)), response);
        then(customerService).should().findById(anyLong());
        then(customerService).shouldHaveNoMoreInteractions();
        then(walletRepository).should().findAllByCustomer_CustomerId(anyLong());
        then(walletRepository).shouldHaveNoMoreInteractions();
    }


    @Test
    void shouldGetAWalletById() {
        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));

        var response = walletService.getById(1L);

        assertEquals(new WalletResponseDTO(wallet), response);
        then(walletRepository).should().findById(anyLong());
        then(walletRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToGetByIdAWalletThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> walletService.getById(1L));
    }

    @Test
    void shouldUpdateAWalletName() {
        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));

        var updateWalletDTO = new UpdateWalletDTO("New wallet name");

        var response = walletService.update(1L, updateWalletDTO);

        assertEquals(updateWalletDTO.walletName(), response.name());
        assertEquals(1L, response.walletId());
        then(walletRepository).should().findById(anyLong());
        then(walletRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToUpdateeByIdAWalletThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> walletService.update(1L, new UpdateWalletDTO("new name")));
    }

    @Test
    void shouldDeleteAWalletById() {
        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));

        walletService.deleteById(1L);

        then(walletRepository).should().deleteById(anyLong());
        then(walletRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToDeleteByIdAWalletThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> walletService.deleteById(1L));
    }

    @Test
    void shouldReturnAWalletActivesReport() {
        var activesReportDTO = ActivesReportDTO.builder()
                .activeId(1L)
                .activeCode("MXRF11")
                .activeValuePercentage(new BigDecimal("100.0"))
                .activeTotalValue(new BigDecimal("100.0"))
                .build();
        var walletReportDTO = WalletActivesReportDTO.builder()
                .wallet(WalletSimpleDTO.builder()
                        .walletId(wallet.getWalletId())
                        .name(wallet.getName())
                        .build())
                .walletTotalValue(new BigDecimal("100.0"))
                .actives(List.of(activesReportDTO))
                .build();

        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
        when(activeService.getActivesReport(anyLong())).thenReturn(List.of(activesReportDTO));
        when(activeService.getWalletTotalValue(anyLong())).thenReturn(new BigDecimal("100.0"));

        var response = walletService.getWalletActivesReport(1L);

        assertEquals(walletReportDTO, response);
        then(activeService).should().getActivesReport(anyLong());
        then(activeService).should().getWalletTotalValue(anyLong());
        then(activeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldReturnAWalletActiveTypesReport() {
        var activeTypesReportDTO = ActiveTypesReportDTO.builder()
                .activeTypeId(1L)
                .activeType("Papel")
                .quantityOfActives(1)
                .quantityPercentage(new BigDecimal("100.0"))
                .monetaryPercentage(new BigDecimal("100.0"))
                .totalValue(new BigDecimal("10.0"))
                .build();

        var walletActiveTypesReportDTO = WalletActiveTypesReportDTO.builder()
                .wallet(WalletSimpleDTO.builder()
                        .walletId(wallet.getWalletId())
                        .name(wallet.getName())
                        .build())
                .distinctActiveTypesQuantity(1)
                .walletTotalValue(new BigDecimal("10.0"))
                .activeTypes(List.of(activeTypesReportDTO))
                .build();

        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
        when(activeService.getActiveTypesReport(anyLong())).thenReturn(List.of(activeTypesReportDTO));
        when(activeService.getDistinctActiveTypesQuantity(anyLong())).thenReturn(1);
        when(activeService.getWalletTotalValue(anyLong())).thenReturn(new BigDecimal("10.0"));

        var response = walletService.getWalletActiveTypesReport(1L);

        assertEquals(walletActiveTypesReportDTO, response);
        then(activeService).should().getActiveTypesReport(anyLong());
        then(activeService).should().getDistinctActiveTypesQuantity(anyLong());
        then(activeService).should().getWalletTotalValue(anyLong());
        then(activeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldReturnAWalletActiveSectorsReport() {
        var activeSectorsReportDTO = ActiveSectorsReportDTO.builder()
                .activeSectorId(1L)
                .activeSector("Hibrido")
                .quantityOfActives(1)
                .quantityPercentage(new BigDecimal("100.0"))
                .monetaryPercentage(new BigDecimal("100.0"))
                .totalValue(new BigDecimal("10.0"))
                .build();
        var walletActiveSectorsReportDTO = WalletActiveSectorsReportDTO.builder()
                .wallet(WalletSimpleDTO.builder()
                        .walletId(wallet.getWalletId())
                        .name(wallet.getName())
                        .build())
                .distinctActiveSectorsQuantity(1)
                .walletTotalValue(new BigDecimal("10.0"))
                .activeSectors(List.of(activeSectorsReportDTO))
                .build();

        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
        when(activeService.getActiveSectorsReport(anyLong())).thenReturn(List.of(activeSectorsReportDTO));
        when(activeService.getDistinctActiveSectorsQuantity(anyLong())).thenReturn(1);
        when(activeService.getWalletTotalValue(anyLong())).thenReturn(new BigDecimal("10.0"));

        var response = walletService.getWalletActiveSectorsReport(1L);

        assertEquals(walletActiveSectorsReportDTO, response);
        then(activeService).should().getActiveSectorsReport(anyLong());
        then(activeService).should().getDistinctActiveSectorsQuantity(anyLong());
        then(activeService).should().getWalletTotalValue(anyLong());
        then(activeService).shouldHaveNoMoreInteractions();
    }
}