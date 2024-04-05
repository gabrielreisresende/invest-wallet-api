package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Client;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ClientResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.ClientRepository;
import com.resendegabriel.investwalletapi.service.IClientService;
import com.resendegabriel.investwalletapi.service.auth.UserService;
import com.resendegabriel.investwalletapi.service.mail.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IMailService mailService;

    @Override
    @Transactional
    public ClientResponseDTO create(ClientRegisterDTO clientRegisterDTO) {
        var user = userService.save(clientRegisterDTO.base64Credentials());
        var newClient = new Client(clientRegisterDTO, user);
        var clientResponseDTO = clientRepository.save(newClient).toDto();
        mailService.sendWelcomeEmail(user.getEmail(), newClient.getFirstName());
        return clientResponseDTO;
    }

    @Override
    @Transactional
    public ClientResponseDTO update(Long clientId, ClientUpdateDTO clientUpdateDTO) {
        var client = findById(clientId);
        if (clientUpdateDTO.email() != null)
            userService.updateEmail(client.getUser().getUserId(), clientUpdateDTO.email());
        client.updateData(clientUpdateDTO);
        return client.toDto();
    }

    @Override
    public ClientResponseDTO getByUserId(Long userId) {
        return clientRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no client with this userId. UserId " + userId)).toDto();
    }

    @Override
    @Transactional
    public void deleteById(Long clientId) {
        findById(clientId);
        clientRepository.deleteById(clientId);
    }

    @Override
    public Client findById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("There isn't any client with this id. Id " + clientId));
    }
}
