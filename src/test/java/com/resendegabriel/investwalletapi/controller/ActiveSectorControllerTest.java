package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveSectorRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveSectorService;
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
class ActiveSectorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IActiveSectorService activeSectorService;

    private static ActiveSectorRequestDTO activeSectorRequestDTO;

    private static ActiveSectorResponseDTO activeSectorResponseDTO;

    @BeforeAll
    static void init() {
        activeSectorRequestDTO = new ActiveSectorRequestDTO("Shopping");
        activeSectorResponseDTO = new ActiveSectorResponseDTO(1L, activeSectorRequestDTO.activeSector());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode201WhenCreateANewActiveSectorWithAdminRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeSectorRequestDTO);

        when(activeSectorService.create(any(ActiveSectorRequestDTO.class))).thenReturn(activeSectorResponseDTO);

        mvc.perform(post("/active-sectors")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/active-sectors/" + activeSectorResponseDTO.activeSectorId()))
                .andExpect(jsonPath("$.activeSectorId").value(1))
                .andExpect(jsonPath("$.activeSector").value(activeSectorRequestDTO.activeSector()));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode403WhenTryToCreateANewActiveSectorWithCustomerRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeSectorRequestDTO);

        mvc.perform(post("/active-sectors")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode200WhenUpdateAnActiveSectorWithAdminRole() throws Exception {
        var activeSectorUpdateDTO = new ActiveSectorRequestDTO("New name");
        String json = new ObjectMapper().writeValueAsString(activeSectorUpdateDTO);

        mvc.perform(put("/active-sectors/{activeSectorId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode403WhenTryToUpdateAnActiveSectorWithCustomerRole() throws Exception {
        var activeSectorUpdateDTO = new ActiveSectorRequestDTO("New name");
        String json = new ObjectMapper().writeValueAsString(activeSectorUpdateDTO);

        mvc.perform(put("/active-sectors/{activeSectorId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode200WhenGetAllActiveTypesWithAdminRole() throws Exception {
        when(activeSectorService.getAll()).thenReturn(List.of(activeSectorResponseDTO));

        mvc.perform(get("/active-sectors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].activeSectorId").value(activeSectorResponseDTO.activeSectorId()))
                .andExpect(jsonPath("$[0].activeSector").value(activeSectorResponseDTO.activeSector()));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenGetAllActiveTypesWithCustomerRole() throws Exception {
        when(activeSectorService.getAll()).thenReturn(List.of(activeSectorResponseDTO));

        mvc.perform(get("/active-sectors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].activeSectorId").value(activeSectorResponseDTO.activeSectorId()))
                .andExpect(jsonPath("$[0].activeSector").value(activeSectorResponseDTO.activeSector()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode200WhenGetByIdAnActiveTypeWithAdminRole() throws Exception {
        when(activeSectorService.getById(anyLong())).thenReturn(activeSectorResponseDTO);

        mvc.perform(get("/active-sectors/{activeSectorId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeSectorId").value(activeSectorResponseDTO.activeSectorId()))
                .andExpect(jsonPath("$.activeSector").value(activeSectorResponseDTO.activeSector()));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenGetByIdAnActiveTypeWithCustomerRole() throws Exception {
        when(activeSectorService.getById(anyLong())).thenReturn(activeSectorResponseDTO);

        mvc.perform(get("/active-sectors/{activeSectorId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeSectorId").value(activeSectorResponseDTO.activeSectorId()))
                .andExpect(jsonPath("$.activeSector").value(activeSectorResponseDTO.activeSector()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode204WhenDeleteByIdAnActiveTypeWithAdminRole() throws Exception {
        mvc.perform(delete("/active-sectors/{activeSectorId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode403WhenTryToDeleteByIdAnActiveTypeWithCustomerRole() throws Exception {
        mvc.perform(delete("/active-sectors/{activeSectorId}", 1))
                .andExpect(status().isForbidden());
    }
}