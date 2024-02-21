package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveTypeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActiveTypeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IActiveTypeService activeTypeService;

    private static ActiveTypeRequestDTO activeTypeRequestDTO;

    private static ActiveTypeResponseDTO activeTypeResponseDTO;

    @BeforeAll
    static void init() {
        activeTypeRequestDTO = new ActiveTypeRequestDTO("Papel");
        activeTypeResponseDTO = new ActiveTypeResponseDTO(1L, activeTypeRequestDTO.activeType());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode201WhenCreateANewActiveTypeWithAdminRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeTypeRequestDTO);

        when(activeTypeService.create(any(ActiveTypeRequestDTO.class))).thenReturn(activeTypeResponseDTO);

        mvc.perform(post("/active-types")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/active-types/" + activeTypeResponseDTO.activeTypeId()))
                .andExpect(jsonPath("$.activeTypeId").value(1))
                .andExpect(jsonPath("$.activeType").value(activeTypeRequestDTO.activeType()));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode403WhenTryToCreateANewActiveTypeWithCustomerRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeTypeRequestDTO);

        mvc.perform(post("/active-types")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}