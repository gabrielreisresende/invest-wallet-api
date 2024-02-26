package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.dto.request.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.CustomerUpdateDTO;

public interface ICustomerService {

    CustomerResponseDTO create(CustomerRegisterDTO customerRegisterDTO);

    CustomerResponseDTO update(Long customerId, CustomerUpdateDTO customerUpdateDTO);

    CustomerResponseDTO getByUserId(Long userId);

    void deleteById(Long customerId);

    Customer findById(Long customerId);
}
