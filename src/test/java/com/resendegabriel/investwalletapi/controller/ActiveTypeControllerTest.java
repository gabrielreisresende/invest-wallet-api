package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveTypeResponseDTO;
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
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode403WhenTryToCreateANewActiveTypeWithClientRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(activeTypeRequestDTO);

        mvc.perform(post("/active-types")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode200WhenUpdateAnActiveTypeWithAdminRole() throws Exception {
        var activeTypeUpdateDTO = new ActiveTypeRequestDTO("New name");
        String json = new ObjectMapper().writeValueAsString(activeTypeUpdateDTO);

        mvc.perform(put("/active-types/{activeTypeId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode403WhenTryToUpdateAnActiveTypeWithClientRole() throws Exception {
        var activeTypeUpdateDTO = new ActiveTypeRequestDTO("New name");
        String json = new ObjectMapper().writeValueAsString(activeTypeUpdateDTO);

        mvc.perform(put("/active-types/{activeTypeId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode200WhenGetAllActiveTypesWithAdminRole() throws Exception {
        when(activeTypeService.getAll()).thenReturn(List.of(activeTypeResponseDTO));

        mvc.perform(get("/active-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].activeTypeId").value(activeTypeResponseDTO.activeTypeId()))
                .andExpect(jsonPath("$[0].activeType").value(activeTypeResponseDTO.activeType()));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode200WhenGetAllActiveTypesWithClientRole() throws Exception {
        when(activeTypeService.getAll()).thenReturn(List.of(activeTypeResponseDTO));

        mvc.perform(get("/active-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].activeTypeId").value(activeTypeResponseDTO.activeTypeId()))
                .andExpect(jsonPath("$[0].activeType").value(activeTypeResponseDTO.activeType()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode200WhenGetByIdAnActiveTypeWithAdminRole() throws Exception {
        when(activeTypeService.getById(anyLong())).thenReturn(activeTypeResponseDTO);

        mvc.perform(get("/active-types/{activeTypeId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeTypeId").value(activeTypeResponseDTO.activeTypeId()))
                .andExpect(jsonPath("$.activeType").value(activeTypeResponseDTO.activeType()));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode200WhenGetByIdAnActiveTypeWithClientRole() throws Exception {
        when(activeTypeService.getById(anyLong())).thenReturn(activeTypeResponseDTO);

        mvc.perform(get("/active-types/{activeTypeId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeTypeId").value(activeTypeResponseDTO.activeTypeId()))
                .andExpect(jsonPath("$.activeType").value(activeTypeResponseDTO.activeType()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode204WhenDeleteByIdAnActiveTypeWithAdminRole() throws Exception {
        mvc.perform(delete("/active-types/{activeTypeId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode403WhenTryToDeleteByIdAnActiveTypeWithClientRole() throws Exception {
        mvc.perform(delete("/active-types/{activeTypeId}", 1))
                .andExpect(status().isForbidden());
    }
}