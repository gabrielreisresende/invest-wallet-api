package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveCodeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveCodeResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveCodeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActiveCodeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IActiveCodeService activeCodeService;

    private static ActiveCodeRequestDTO activeCodeRequestDTO;

    private static ActiveCodeResponseDTO activeCodeResponseDTO;

    @BeforeAll
    static void init() {
        var activeType = new ActiveTypeResponseDTO(1L, "Papel");
        var activeSector = new ActiveSectorResponseDTO(1L, "Hibrido");
        activeCodeRequestDTO = new ActiveCodeRequestDTO("MXRF11", activeType, activeSector);
        activeCodeResponseDTO = new ActiveCodeResponseDTO(activeCodeRequestDTO.activeCode(), activeType, activeSector);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode201WhenCreateANewActiveCodeWithAdminRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeCodeRequestDTO);

        when(activeCodeService.create(any(ActiveCodeRequestDTO.class))).thenReturn(activeCodeResponseDTO);

        mvc.perform(post("/active-codes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/active-codes?activeCode=" + activeCodeResponseDTO.activeCode()))
                .andExpect(jsonPath("$.activeCode").value(activeCodeRequestDTO.activeCode()))
                .andExpect(jsonPath("$.activeType.activeTypeId").value(activeCodeRequestDTO.activeType().activeTypeId()))
                .andExpect(jsonPath("$.activeType.activeType").value(activeCodeRequestDTO.activeType().activeType()))
                .andExpect(jsonPath("$.activeSector.activeSectorId").value(activeCodeRequestDTO.activeSector().activeSectorId()))
                .andExpect(jsonPath("$.activeSector.activeSector").value(activeCodeRequestDTO.activeSector().activeSector()));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode403WhenTryToCreateANewActiveCodeWithClientRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeCodeRequestDTO);

        mvc.perform(post("/active-codes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode200WhenGetAllActiveCodesWithAdminRole() throws Exception {
        when(activeCodeService.getAll()).thenReturn(List.of(activeCodeResponseDTO));

        mvc.perform(get("/active-codes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].activeCode").value(activeCodeRequestDTO.activeCode()))
                .andExpect(jsonPath("$[0].activeType.activeTypeId").value(activeCodeRequestDTO.activeType().activeTypeId()))
                .andExpect(jsonPath("$[0].activeType.activeType").value(activeCodeRequestDTO.activeType().activeType()))
                .andExpect(jsonPath("$[0].activeSector.activeSectorId").value(activeCodeRequestDTO.activeSector().activeSectorId()))
                .andExpect(jsonPath("$[0].activeSector.activeSector").value(activeCodeRequestDTO.activeSector().activeSector()));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode200WhenGetAllActiveCodesWithClientRole() throws Exception {
        when(activeCodeService.getAll()).thenReturn(List.of(activeCodeResponseDTO));

        mvc.perform(get("/active-codes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].activeCode").value(activeCodeRequestDTO.activeCode()))
                .andExpect(jsonPath("$[0].activeType.activeTypeId").value(activeCodeRequestDTO.activeType().activeTypeId()))
                .andExpect(jsonPath("$[0].activeType.activeType").value(activeCodeRequestDTO.activeType().activeType()))
                .andExpect(jsonPath("$[0].activeSector.activeSectorId").value(activeCodeRequestDTO.activeSector().activeSectorId()))
                .andExpect(jsonPath("$[0].activeSector.activeSector").value(activeCodeRequestDTO.activeSector().activeSector()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode200WhenGetByIdAnActiveCodeWithAdminRole() throws Exception {
        when(activeCodeService.getByCode(anyString())).thenReturn(activeCodeResponseDTO);

        mvc.perform(get("/active-codes/details?activeCode={activeCode}", activeCodeRequestDTO.activeCode()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeCode").value(activeCodeRequestDTO.activeCode()))
                .andExpect(jsonPath("$.activeType.activeTypeId").value(activeCodeRequestDTO.activeType().activeTypeId()))
                .andExpect(jsonPath("$.activeType.activeType").value(activeCodeRequestDTO.activeType().activeType()))
                .andExpect(jsonPath("$.activeSector.activeSectorId").value(activeCodeRequestDTO.activeSector().activeSectorId()))
                .andExpect(jsonPath("$.activeSector.activeSector").value(activeCodeRequestDTO.activeSector().activeSector()));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode200WhenGetByIdAnActiveCodeWithClientRole() throws Exception {
        when(activeCodeService.getByCode(anyString())).thenReturn(activeCodeResponseDTO);

        mvc.perform(get("/active-codes/details?activeCode={activeCode}", activeCodeRequestDTO.activeCode()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeCode").value(activeCodeRequestDTO.activeCode()))
                .andExpect(jsonPath("$.activeType.activeTypeId").value(activeCodeRequestDTO.activeType().activeTypeId()))
                .andExpect(jsonPath("$.activeType.activeType").value(activeCodeRequestDTO.activeType().activeType()))
                .andExpect(jsonPath("$.activeSector.activeSectorId").value(activeCodeRequestDTO.activeSector().activeSectorId()))
                .andExpect(jsonPath("$.activeSector.activeSector").value(activeCodeRequestDTO.activeSector().activeSector()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode204WhenDeleteByIdAnActiveCodeWithAdminRole() throws Exception {
        mvc.perform(delete("/active-codes?activeCode={activeCode}", activeCodeRequestDTO.activeCode()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode403WhenTryToDeleteByIdAnActiveCodeWithClientRole() throws Exception {
        mvc.perform(delete("/active-codes?activeCode={activeCode}", activeCodeRequestDTO.activeCode()))
                .andExpect(status().isForbidden());
    }
}