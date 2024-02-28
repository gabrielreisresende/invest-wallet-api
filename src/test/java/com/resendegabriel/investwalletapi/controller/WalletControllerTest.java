package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActivesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletActivesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    private static Customer customer;

    private static UpdateWalletDTO updateWalletDTO;

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

        customer = Customer.builder()
                .customerId(1L)
                .build();

        updateWalletDTO = new UpdateWalletDTO("New wallet name");
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

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenGetAllWalletsFromACustomerWithCustomerRole() throws Exception {
        when(walletService.getAll(anyLong())).thenReturn(List.of(walletResponseDTO));

        mvc.perform(get("/wallets/customers/{customerId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].walletId").value(1))
                .andExpect(jsonPath("$[0].name").value("Wallet name"))
                .andExpect(jsonPath("$[0].actives").isEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetAllWalletsFromACustomerWithAdminRole() throws Exception {
        mvc.perform(get("/wallets/customers/{customerId}", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenGetAWalletByIdWithCustomerRole() throws Exception {
        when(walletService.getById(anyLong())).thenReturn(walletResponseDTO);

        mvc.perform(get("/wallets/{walletId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(1))
                .andExpect(jsonPath("$.name").value("Wallet name"))
                .andExpect(jsonPath("$.actives").isEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetAWalletByIdWithAdminRole() throws Exception {
        mvc.perform(get("/wallets/{walletId}", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenUpdateAWalletNameWithCustomerRole() throws Exception {
        var newWalletResponse = WalletResponseDTO.builder()
                .walletId(walletResponseDTO.walletId())
                .name(updateWalletDTO.walletName())
                .customer(walletResponseDTO.customer())
                .actives(walletResponseDTO.actives())
                .build();

        when(walletService.update(anyLong(), any(UpdateWalletDTO.class))).thenReturn(newWalletResponse);

        var json = new ObjectMapper().writeValueAsString(updateWalletDTO);

        mvc.perform(put("/wallets/{walletId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(1))
                .andExpect(jsonPath("$.name").value(updateWalletDTO.walletName()))
                .andExpect(jsonPath("$.customer.customerId").value(walletResponseDTO.customer().customerId()))
                .andExpect(jsonPath("$.actives").isEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToUpdateAWalletNameWithAdminRole() throws Exception {
        var json = new ObjectMapper().writeValueAsString(updateWalletDTO);

        mvc.perform(put("/wallets/{walletId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode204WhenDeleteAWalletWithCustomerRole() throws Exception {
        mvc.perform(delete("/wallets/{walletId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToDeleteAWalletWithAdminRole() throws Exception {
        mvc.perform(delete("/wallets/{walletId}", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenGetAWalletActivesReportWithCustomerRole() throws Exception {
        var activesReportDTO = ActivesReportDTO.builder()
                .activeId(1L)
                .activeCode("MXRF11")
                .activePercent(new BigDecimal("100.0"))
                .activeTotalValue(new BigDecimal("100.0"))
                .build();
        var walletReportDTO = WalletActivesReportDTO.builder()
                .wallet(WalletSimpleDTO.builder()
                        .walletId(1L)
                        .name("wallet name")
                        .build())
                .walletTotalValue(new BigDecimal("100.0"))
                .actives(List.of(activesReportDTO))
                .build();

        when(walletService.getWalletActivesReport(anyLong())).thenReturn(walletReportDTO);

        mvc.perform(get("/wallets/{walletId}/actives/details", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wallet.walletId").value(walletReportDTO.wallet().walletId()))
                .andExpect(jsonPath("$.wallet.name").value(walletReportDTO.wallet().name()))
                .andExpect(jsonPath("$.walletTotalValue").value(walletReportDTO.walletTotalValue()))
                .andExpect(jsonPath("$.actives[0].activeId").value(walletReportDTO.actives().get(0).getActiveId()))
                .andExpect(jsonPath("$.actives[0].activeCode").value(walletReportDTO.actives().get(0).getActiveCode()))
                .andExpect(jsonPath("$.actives[0].activeTotalValue").value(walletReportDTO.actives().get(0).getActiveTotalValue()))
                .andExpect(jsonPath("$.actives[0].activePercent").value(walletReportDTO.actives().get(0).getActivePercent()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetAWalletActivesReportWithAdminRole() throws Exception {
        mvc.perform(get("/wallets/{walletId}/actives/details", 1))
                .andExpect(status().isForbidden());
    }
}