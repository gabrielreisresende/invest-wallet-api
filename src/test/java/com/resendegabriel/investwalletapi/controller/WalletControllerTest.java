package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.ActivesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActivesReportDTO;
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
                .activeValuePercentage(new BigDecimal("100.0"))
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
                .andExpect(jsonPath("$.actives[0].activeValuePercentage").value(walletReportDTO.actives().get(0).getActiveValuePercentage()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetAWalletActivesReportWithAdminRole() throws Exception {
        mvc.perform(get("/wallets/{walletId}/actives/details", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenGetAWalletActiveTypesReportWithCustomerRole() throws Exception {
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
                        .walletId(1L)
                        .name("Wallet name")
                        .build())
                .distinctActiveTypesQuantity(1)
                .walletTotalValue(new BigDecimal("10.0"))
                .activeTypes(List.of(activeTypesReportDTO))
                .build();

        when(walletService.getWalletActiveTypesReport(anyLong())).thenReturn(walletActiveTypesReportDTO);

        mvc.perform(get("/wallets/{walletId}/active-types/details", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wallet.walletId").value(walletActiveTypesReportDTO.wallet().walletId()))
                .andExpect(jsonPath("$.wallet.name").value(walletActiveTypesReportDTO.wallet().name()))
                .andExpect(jsonPath("$.distinctActiveTypesQuantity").value(walletActiveTypesReportDTO.distinctActiveTypesQuantity()))
                .andExpect(jsonPath("$.walletTotalValue").value(walletActiveTypesReportDTO.walletTotalValue()))
                .andExpect(jsonPath("$.activeTypes[0].activeTypeId").value(walletActiveTypesReportDTO.activeTypes().get(0).getActiveTypeId()))
                .andExpect(jsonPath("$.activeTypes[0].activeType").value(walletActiveTypesReportDTO.activeTypes().get(0).getActiveType()))
                .andExpect(jsonPath("$.activeTypes[0].quantityOfActives").value(walletActiveTypesReportDTO.activeTypes().get(0).getQuantityOfActives()))
                .andExpect(jsonPath("$.activeTypes[0].quantityPercentage").value(walletActiveTypesReportDTO.activeTypes().get(0).getQuantityPercentage()))
                .andExpect(jsonPath("$.activeTypes[0].totalValue").value(walletActiveTypesReportDTO.activeTypes().get(0).getTotalValue()))
                .andExpect(jsonPath("$.activeTypes[0].monetaryPercentage").value(walletActiveTypesReportDTO.activeTypes().get(0).getMonetaryPercentage()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetAWalletActiveTypesReportWithAdminRole() throws Exception {
        mvc.perform(get("/wallets/{walletId}/active-types/details", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenGetAWalletActiveSectorsReportWithCustomerRole() throws Exception {
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
                        .walletId(1L)
                        .name("Wallet name")
                        .build())
                .distinctActiveSectorsQuantity(1)
                .walletTotalValue(new BigDecimal("10.0"))
                .activeSectors(List.of(activeSectorsReportDTO))
                .build();

        when(walletService.getWalletActiveSectorsReport(anyLong())).thenReturn(walletActiveSectorsReportDTO);

        mvc.perform(get("/wallets/{walletId}/active-sectors/details", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wallet.walletId").value(walletActiveSectorsReportDTO.wallet().walletId()))
                .andExpect(jsonPath("$.wallet.name").value(walletActiveSectorsReportDTO.wallet().name()))
                .andExpect(jsonPath("$.distinctActiveSectorsQuantity").value(walletActiveSectorsReportDTO.distinctActiveSectorsQuantity()))
                .andExpect(jsonPath("$.walletTotalValue").value(walletActiveSectorsReportDTO.walletTotalValue()))
                .andExpect(jsonPath("$.activeSectors[0].activeSectorId").value(walletActiveSectorsReportDTO.activeSectors().get(0).getActiveSectorId()))
                .andExpect(jsonPath("$.activeSectors[0].activeSector").value(walletActiveSectorsReportDTO.activeSectors().get(0).getActiveSector()))
                .andExpect(jsonPath("$.activeSectors[0].quantityOfActives").value(walletActiveSectorsReportDTO.activeSectors().get(0).getQuantityOfActives()))
                .andExpect(jsonPath("$.activeSectors[0].quantityPercentage").value(walletActiveSectorsReportDTO.activeSectors().get(0).getQuantityPercentage()))
                .andExpect(jsonPath("$.activeSectors[0].totalValue").value(walletActiveSectorsReportDTO.activeSectors().get(0).getTotalValue()))
                .andExpect(jsonPath("$.activeSectors[0].monetaryPercentage").value(walletActiveSectorsReportDTO.activeSectors().get(0).getMonetaryPercentage()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetAWalletActiveSectorsReportWithAdminRole() throws Exception {
        mvc.perform(get("/wallets/{walletId}/active-sectors/details", 1))
                .andExpect(status().isForbidden());
    }
}