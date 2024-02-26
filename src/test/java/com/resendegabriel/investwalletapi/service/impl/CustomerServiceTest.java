package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.domain.dto.request.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.CustomerUpdateDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.CustomerRepository;
import com.resendegabriel.investwalletapi.service.auth.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Spy
    private static Customer customer;

    private static CustomerUpdateDTO customerUpdateDTO;

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

        customerUpdateDTO = CustomerUpdateDTO.builder()
                .email("novoEmail@Email.com")
                .phone("4342342345")
                .build();

        customer = Customer.builder()
                .customerId(1L)
                .user(user)
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

    @Test
    void shouldUpdateACustomerData() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        var response = customerService.update(1L, customerUpdateDTO);

        assertEquals(new CustomerResponseDTO(customer).phone(), response.phone());
        assertEquals((new CustomerResponseDTO(customer)).user().email(), response.user().email());
        then(userService).should().updateEmail(anyLong(), anyString());
        then(userService).shouldHaveNoMoreInteractions();
        then(customer).should().updateData(any(CustomerUpdateDTO.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToUpdateACustomerThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> customerService.update(1L, customerUpdateDTO));
    }

    @Test
    void shouldGetACustomerByUserId() {
        when(customerRepository.findByUser_UserId(anyLong())).thenReturn(Optional.of(customer));

        var response = customerService.getByUserId(1L);

        assertEquals(new CustomerResponseDTO(customer), response);
        then(customerRepository).should().findByUser_UserId(anyLong());
        then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToGetByUserIdACustomerThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> customerService.getByUserId(1L));
    }

    @Test
    void shouldSoftDeleteACustomerById() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        customerService.deleteById(1L);

        then(customerRepository).should().deleteById(anyLong());
        then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToDeleteByIdACustomerThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteById(1L));
    }
}