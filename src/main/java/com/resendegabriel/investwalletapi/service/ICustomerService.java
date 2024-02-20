package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.dto.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerUpdateDTO;

public interface ICustomerService {

    CustomerResponseDTO create(CustomerRegisterDTO customerRegisterDTO);

    CustomerResponseDTO update(Long customerId, CustomerUpdateDTO customerUpdateDTO);

    CustomerResponseDTO getByUserId(Long userId);

    void deleteById(Long customerId);

    Customer findById(Long customerId);
}
