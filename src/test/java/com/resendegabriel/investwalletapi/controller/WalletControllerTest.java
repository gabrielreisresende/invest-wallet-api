package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.WalletResponseDTO;
import com.resendegabriel.investwalletapi.service.IWalletService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WalletControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IWalletService walletService;

    private static WalletRequestDTO walletRequestDTO;

    private static WalletResponseDTO walletResponseDTO;

    @BeforeAll
    static void init() {
        walletRequestDTO = WalletRequestDTO.builder()
                .name("Wallet name")
                .customerId(1L)
                .build();

        walletResponseDTO = WalletResponseDTO.builder()
                .walletId(1L)
                .name("Wallet name")
                .customer(
                        CustomerResponseDTO.builder()
                                .customerId(1L)
                                .build())
                .actives(new ArrayList<>())
                .build();
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode201WhenCreateAWalletWithCustomerRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(walletRequestDTO);

        when(walletService.create(any(WalletRequestDTO.class))).thenReturn(walletResponseDTO);

        mvc.perform(post("/wallets")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/wallets/" + walletResponseDTO.walletId()))
                .andExpect(jsonPath("$.walletId").value(1))
                .andExpect(jsonPath("$.name").value("Wallet name"))
                .andExpect(jsonPath("$.actives").isEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToCreateAWalletWithAdminRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(walletRequestDTO);

        mvc.perform(post("/wallets")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}