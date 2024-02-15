package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.dto.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;

public interface ICustomerService {

    CustomerResponseDTO create(CustomerRegisterDTO customerRegisterDTO);
}
