package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ClientResponseDTO;
import com.resendegabriel.investwalletapi.service.IClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

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
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IClientService clientService;

    private static ClientRegisterDTO clientRegisterDTO;

    private static ClientUpdateDTO clientUpdateDTO;

    private static ClientResponseDTO clientResponseDTO;

    private static UserResponseDTO userResponseDTO;

    private static ObjectMapper mapper;

    @BeforeAll
    static void init() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        clientRegisterDTO = ClientRegisterDTO.builder()
                .base64Credentials("23423423423fgwergert")
                .cpf("231.215.850-70")
                .firstName("FirstName")
                .lastName("LastName")
                .birthDate(LocalDate.now())
                .phone("312312323")
                .build();

        clientUpdateDTO = ClientUpdateDTO.builder()
                .email("novoEmail@Email.com")
                .phone("4342342345")
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .userId(1L)
                .email("teste@email.com")
                .userRole(UserRole.CLIENT)
                .build();

        clientResponseDTO = ClientResponseDTO.builder()
                .clientId(1L)
                .cpf("231.215.850-70")
                .firstName("FirstName")
                .lastName("LastName")
                .birthDate(LocalDate.now())
                .phone("312312323")
                .user(userResponseDTO)
                .wallets(new ArrayList<>())
                .build();
    }

    @Test
    void shouldReturnCode201WhenCreateAClient() throws Exception {
        String json = mapper.writeValueAsString(clientRegisterDTO);

        when(clientService.create(any(ClientRegisterDTO.class))).thenReturn(clientResponseDTO);

        mvc.perform(post("/clients")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/clients/" + clientResponseDTO.clientId()))
                .andExpect(jsonPath("$.clientId").value(1))
                .andExpect(jsonPath("$.cpf").value(clientRegisterDTO.cpf()))
                .andExpect(jsonPath("$.firstName").value(clientRegisterDTO.firstName()))
                .andExpect(jsonPath("$.lastName").value(clientRegisterDTO.lastName()))
                .andExpect(jsonPath("$.phone").value(clientRegisterDTO.phone()))
                .andExpect(jsonPath("$.birthDate").value(clientRegisterDTO.birthDate().toString()))
                .andExpect(jsonPath("$.user.userId").value(userResponseDTO.userId()))
                .andExpect(jsonPath("$.user.email").value(userResponseDTO.email()))
                .andExpect(jsonPath("$.user.userRole").value(userResponseDTO.userRole().toString()))
                .andExpect(jsonPath("$.wallets").isEmpty());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode200WhenUpdateAClientWithClientRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(clientUpdateDTO);

        mvc.perform(put("/clients/{clientId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToUpdateAClientWithAdminRole() throws Exception {
        String json = mapper.writeValueAsString(clientUpdateDTO);

        mvc.perform(put("/clients/{clientId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode200WhenGetAClientByUserIdWithClientRole() throws Exception {
        when(clientService.getByUserId(anyLong())).thenReturn(clientResponseDTO);

        mvc.perform(get("/clients/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(clientResponseDTO.clientId()))
                .andExpect(jsonPath("$.cpf").value(clientResponseDTO.cpf()))
                .andExpect(jsonPath("$.firstName").value(clientResponseDTO.firstName()))
                .andExpect(jsonPath("$.lastName").value(clientResponseDTO.lastName()))
                .andExpect(jsonPath("$.phone").value(clientResponseDTO.phone()))
                .andExpect(jsonPath("$.birthDate").value(clientResponseDTO.birthDate().toString()))
                .andExpect(jsonPath("$.user.userId").value(clientResponseDTO.user().userId()))
                .andExpect(jsonPath("$.user.email").value(clientResponseDTO.user().email()))
                .andExpect(jsonPath("$.user.userRole").value(clientResponseDTO.user().userRole().toString()))
                .andExpect(jsonPath("$.wallets").value(clientResponseDTO.wallets()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetAClientByUserIdWithAdminRole() throws Exception {
        mvc.perform(get("/clients/{userId}", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldReturnCode204WhenDeleteAClientWithClientRole() throws Exception {
        mvc.perform(delete("/clients/{clientId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode204WhenDeleteAClientWithAdminRole() throws Exception {
        mvc.perform(delete("/clients/{clientId}", 1))
                .andExpect(status().isForbidden());
    }
}