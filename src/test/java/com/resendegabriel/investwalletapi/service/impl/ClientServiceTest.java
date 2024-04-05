package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Client;
import com.resendegabriel.investwalletapi.domain.auth.User;
import com.resendegabriel.investwalletapi.domain.auth.dto.UserResponseDTO;
import com.resendegabriel.investwalletapi.domain.auth.enums.UserRole;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ClientResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.ClientRepository;
import com.resendegabriel.investwalletapi.service.auth.UserService;
import com.resendegabriel.investwalletapi.service.mail.IMailService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserService userService;

    @Mock
    private IMailService mailService;

    private static ClientRegisterDTO clientRegisterDTO;

    private static ClientResponseDTO clientResponseDTO;

    @Spy
    private static Client client;

    private static ClientUpdateDTO clientUpdateDTO;

    private static User user;

    @BeforeAll
    static void init() {
        clientRegisterDTO = ClientRegisterDTO.builder()
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
                .role(UserRole.CLIENT)
                .build();

        clientResponseDTO = ClientResponseDTO.builder()
                .cpf(clientRegisterDTO.cpf())
                .firstName(clientRegisterDTO.firstName())
                .lastName(clientRegisterDTO.lastName())
                .birthDate(clientRegisterDTO.birthDate())
                .phone(clientRegisterDTO.phone())
                .user(new UserResponseDTO(user))
                .wallets(new ArrayList<>())
                .build();

        clientUpdateDTO = ClientUpdateDTO.builder()
                .email("novoEmail@Email.com")
                .phone("4342342345")
                .build();

        client = Client.builder()
                .clientId(1L)
                .user(user)
                .wallets(new ArrayList<>())
                .build();
    }

    @Test
    void shouldCreateANewClient() {
        when(userService.save(anyString())).thenReturn(user);
        when(clientRepository.save(any(Client.class))).thenReturn(new Client(clientRegisterDTO, user));

        var response = clientService.create(clientRegisterDTO);

        assertEquals(clientResponseDTO, response);
        then(userService).should().save(anyString());
        then(userService).shouldHaveNoMoreInteractions();
        then(clientRepository).should().save(any(Client.class));
        then(clientRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldSendAWelcomeEmailAfterTheClientRegistration() {
        when(userService.save(anyString())).thenReturn(user);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        clientService.create(clientRegisterDTO);

        then(mailService).should().sendWelcomeEmail(anyString(), anyString());
        then(mailService).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldNotSendAWelcomeEmailWhenHappenARegistrationError() {
        when(userService.save(anyString())).thenReturn(user);
        when(clientRepository.save(any(Client.class))).thenThrow(new RuntimeException("Failed to save client"));

        assertThrows(RuntimeException.class, () -> clientService.create(clientRegisterDTO));
        then(mailService).shouldHaveNoInteractions();
    }

    @Test
    void shouldRegisterANewClientEvenIfMailServiceThrowMailSendException() {
        when(userService.save(anyString())).thenReturn(user);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        doThrow(new MailSendException("Failed to send email")).when(mailService).sendMail(anyString(), anyString(), anyString());

        var response = clientService.create(clientRegisterDTO);

        assertEquals(new ClientResponseDTO(client), response);
        assertThrows(MailSendException.class, () -> mailService.sendMail(anyString(), anyString(), anyString()));
        then(userService).should().save(anyString());
        then(userService).shouldHaveNoMoreInteractions();
        then(clientRepository).should().save(any(Client.class));
        then(clientRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldUpdateAClientData() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        var response = clientService.update(1L, clientUpdateDTO);

        assertEquals(new ClientResponseDTO(client).phone(), response.phone());
        assertEquals((new ClientResponseDTO(client)).user().email(), response.user().email());
        then(userService).should().updateEmail(anyLong(), anyString());
        then(userService).shouldHaveNoMoreInteractions();
        then(client).should().updateData(any(ClientUpdateDTO.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToUpdateAClientThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> clientService.update(1L, clientUpdateDTO));
    }

    @Test
    void shouldGetAClientByUserId() {
        when(clientRepository.findByUser_UserId(anyLong())).thenReturn(Optional.of(client));

        var response = clientService.getByUserId(1L);

        assertEquals(new ClientResponseDTO(client), response);
        then(clientRepository).should().findByUser_UserId(anyLong());
        then(clientRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToGetByUserIdAClientThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> clientService.getByUserId(1L));
    }

    @Test
    void shouldSoftDeleteAClientById() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        clientService.deleteById(1L);

        then(clientRepository).should().deleteById(anyLong());
        then(clientRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTryToDeleteByIdAClientThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteById(1L));
    }
}