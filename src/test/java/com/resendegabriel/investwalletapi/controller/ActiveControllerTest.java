package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveCodeResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletSimpleDTO;
import com.resendegabriel.investwalletapi.service.IActiveService;
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
class ActiveControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IActiveService activeService;

    private static ActiveRequestDTO activeRequestDTO;

    private static ActiveResponseDTO activeResponseDTO;

    private static ActiveUpdateDTO activeUpdateDTO;

    @BeforeAll
    static void init() {
        activeRequestDTO = ActiveRequestDTO.builder()
                .quantity(100)
                .averageValue(new BigDecimal("9.98"))
                .activeCode("MXRF11")
                .walletId(1L)
                .build();

        activeResponseDTO = ActiveResponseDTO.builder()
                .activeId(1L)
                .quantity(activeRequestDTO.quantity())
                .activeCode(ActiveCodeResponseDTO
                        .builder()
                        .activeCode(activeRequestDTO.activeCode())
                        .build())
                .averageValue(activeRequestDTO.averageValue())
                .wallet(WalletSimpleDTO
                        .builder()
                        .walletId(activeRequestDTO.walletId())
                        .build())
                .build();

        activeUpdateDTO = ActiveUpdateDTO.builder()
                .quantity(150)
                .averageValue(new BigDecimal("9.99"))
                .build();
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode201WhenCreateANewActiveWithClientRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeRequestDTO);

        when(activeService.create(any(ActiveRequestDTO.class))).thenReturn(activeResponseDTO);

        mvc.perform(post("/actives")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/actives/" + activeResponseDTO.activeId()))
                .andExpect(jsonPath("$.activeId").value(activeResponseDTO.activeId()))
                .andExpect(jsonPath("$.quantity").value(activeRequestDTO.quantity()))
                .andExpect(jsonPath("$.averageValue").value(activeRequestDTO.averageValue()))
                .andExpect(jsonPath("$.activeCode.activeCode").value(activeRequestDTO.activeCode()))
                .andExpect(jsonPath("$.wallet.walletId").value(activeRequestDTO.walletId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToCreateANewActiveWithAdminRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeRequestDTO);

        mvc.perform(post("/actives")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode200WhenUpdateAnActiveWithClientRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeUpdateDTO);

        mvc.perform(put("/actives/{activeId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToUpdateAnActiveWithAdminRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeUpdateDTO);

        mvc.perform(put("/actives/{activeId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode204WhenDeleteAnActiveWithClientRole() throws Exception {
        mvc.perform(delete("/actives/{activeId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToDeleteAnActiveWithAdminRole() throws Exception {
        mvc.perform(delete("/actives/{activeId}", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode200WhenGetAnActiveByIdWithClientRole() throws Exception {
        when(activeService.getById(anyLong())).thenReturn(activeResponseDTO);

        mvc.perform(get("/actives/{activeId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeId").value(activeResponseDTO.activeId()))
                .andExpect(jsonPath("$.quantity").value(activeRequestDTO.quantity()))
                .andExpect(jsonPath("$.averageValue").value(activeRequestDTO.averageValue()))
                .andExpect(jsonPath("$.activeCode.activeCode").value(activeRequestDTO.activeCode()))
                .andExpect(jsonPath("$.wallet.walletId").value(activeRequestDTO.walletId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetAnActiveByIdWithCAdminRole() throws Exception {
        mvc.perform(get("/actives/{activeId}", 1))
                .andExpect(status().isForbidden());
    }
}