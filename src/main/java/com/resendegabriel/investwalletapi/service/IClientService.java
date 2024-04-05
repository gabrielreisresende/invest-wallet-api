package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.Client;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ClientResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientUpdateDTO;

public interface IClientService {

    ClientResponseDTO create(ClientRegisterDTO clientRegisterDTO);

    ClientResponseDTO update(Long clientId, ClientUpdateDTO clientUpdateDTO);

    ClientResponseDTO getByUserId(Long userId);

    void deleteById(Long clientId);

    Client findById(Long clientId);
}
