package com.resendegabriel.investwalletapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.domain.dto.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerUpdateDTO;
import com.resendegabriel.investwalletapi.repository.CustomerRepository;
import com.resendegabriel.investwalletapi.service.ICustomerService;
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
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ICustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    private static CustomerRegisterDTO customerRegisterDTO;

    private static CustomerUpdateDTO customerUpdateDTO;

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

        customerUpdateDTO = CustomerUpdateDTO.builder()
                .email("novoEmail@Email.com")
                .phone("4342342345")
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

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenUpdateACustomerWithCustomerRole() throws Exception {
        String json = new ObjectMapper().writeValueAsString(customerUpdateDTO);

        mvc.perform(put("/customers/{customerId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToUpdateACustomerWithAdminRole() throws Exception {
        String json = mapper.writeValueAsString(customerUpdateDTO);

        mvc.perform(put("/customers/{customerId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode200WhenGetACustomerByUserIdWithCustomerRole() throws Exception {
        when(customerService.getByUserId(anyLong())).thenReturn(customerResponseDTO);

        mvc.perform(get("/customers/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerResponseDTO.customerId()))
                .andExpect(jsonPath("$.cpf").value(customerResponseDTO.cpf()))
                .andExpect(jsonPath("$.firstName").value(customerResponseDTO.firstName()))
                .andExpect(jsonPath("$.lastName").value(customerResponseDTO.lastName()))
                .andExpect(jsonPath("$.phone").value(customerResponseDTO.phone()))
                .andExpect(jsonPath("$.birthDate").value(customerResponseDTO.birthDate().toString()))
                .andExpect(jsonPath("$.user.userId").value(customerResponseDTO.user().userId()))
                .andExpect(jsonPath("$.user.email").value(customerResponseDTO.user().email()))
                .andExpect(jsonPath("$.user.userRole").value(customerResponseDTO.user().userRole().toString()))
                .andExpect(jsonPath("$.wallets").value(customerResponseDTO.wallets()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode403WhenTryToGetACustomerByUserIdWithAdminRole() throws Exception {
        mvc.perform(get("/customers/{userId}", 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void shouldReturnCode204WhenDeleteACustomerWithCustomerRole() throws Exception {
        mvc.perform(delete("/customers/{customerId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCode204WhenDeleteACustomerWithAdminRole() throws Exception {
        mvc.perform(delete("/customers/{customerId}", 1))
                .andExpect(status().isForbidden());
    }
}