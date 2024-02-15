package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.domain.dto.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.repository.CustomerRepository;
import com.resendegabriel.investwalletapi.service.auth.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserService userService;

    private static CustomerRegisterDTO customerRegisterDTO;

    private static CustomerResponseDTO customerResponseDTO;

    private static User user;

    @BeforeAll
    static void init() {
        customerRegisterDTO = CustomerRegisterDTO.builder()
                .base64Credentials("23423423423fgwergert")
                .cpf("231.215.850-70")
                .firstName("FirstName")
                .lastName("LastName")
                .birthDate(LocalDate.now())
                .phone("312312323")
                .build();

        user = User.builder()
                .userId(1L)
                .email("teste@email.com")
                .password("4234234fvwfgeg4g4243")
                .role(UserRole.CUSTOMER)
                .build();

        customerResponseDTO = CustomerResponseDTO.builder()
                .cpf(customerRegisterDTO.cpf())
                .firstName(customerRegisterDTO.firstName())
                .lastName(customerRegisterDTO.lastName())
                .birthDate(customerRegisterDTO.birthDate())
                .phone(customerRegisterDTO.phone())
                .user(new UserResponseDTO(user))
                .wallets(new ArrayList<>())
                .build();
    }

    @Test
    void shouldCreateANewCustomer() {
        when(userService.save(anyString())).thenReturn(user);
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer(customerRegisterDTO, user));

        var response = customerService.create(customerRegisterDTO);

        assertEquals(customerResponseDTO, response);
        then(userService).should().save(anyString());
        then(userService).shouldHaveNoMoreInteractions();
        then(customerRepository).should().save(any(Customer.class));
        then(customerRepository).shouldHaveNoMoreInteractions();
    }
}