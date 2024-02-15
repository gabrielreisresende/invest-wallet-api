package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.domain.dto.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ICustomerService customerService;

    private static CustomerRegisterDTO customerRegisterDTO;

    private static CustomerResponseDTO customerResponseDTO;

    private static UserResponseDTO userResponseDTO;

    private static ObjectMapper mapper;

    @BeforeAll
    static void init() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        customerRegisterDTO = CustomerRegisterDTO.builder()
                .base64Credentials("23423423423fgwergert")
                .cpf("231.215.850-70")
                .firstName("FirstName")
                .lastName("LastName")
                .birthDate(LocalDate.now())
                .phone("312312323")
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .userId(1L)
                .email("teste@email.com")
                .userRole(UserRole.CUSTOMER)
                .build();

        customerResponseDTO = CustomerResponseDTO.builder()
                .customerId(1L)
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
    void shouldReturnCode201WhenCreateACustomer() throws Exception {
        String json = mapper.writeValueAsString(customerRegisterDTO);

        when(customerService.create(any(CustomerRegisterDTO.class))).thenReturn(customerResponseDTO);

        mvc.perform(post("/customers")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/customers/" + customerResponseDTO.customerId()))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.cpf").value(customerRegisterDTO.cpf()))
                .andExpect(jsonPath("$.firstName").value(customerRegisterDTO.firstName()))
                .andExpect(jsonPath("$.lastName").value(customerRegisterDTO.lastName()))
                .andExpect(jsonPath("$.phone").value(customerRegisterDTO.phone()))
                .andExpect(jsonPath("$.birthDate").value(customerRegisterDTO.birthDate().toString()))
                .andExpect(jsonPath("$.user.userId").value(userResponseDTO.userId()))
                .andExpect(jsonPath("$.user.email").value(userResponseDTO.email()))
                .andExpect(jsonPath("$.user.userRole").value(userResponseDTO.userRole().toString()))
                .andExpect(jsonPath("$.wallets").isEmpty());
    }
}