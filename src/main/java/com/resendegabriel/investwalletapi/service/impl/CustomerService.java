package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.dto.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerUpdateDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.CustomerRepository;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import com.resendegabriel.investwalletapi.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public CustomerResponseDTO create(CustomerRegisterDTO customerRegisterDTO) {
        var user = userService.save(customerRegisterDTO.base64Credentials());
        return new CustomerResponseDTO(customerRepository.save(new Customer(customerRegisterDTO, user)));
    }

    @Override
    @Transactional
    public CustomerResponseDTO update(Long customerId, CustomerUpdateDTO customerUpdateDTO) {
        var customer = findById(customerId);
        if (customerUpdateDTO.email() != null)
            userService.updateEmail(customer.getUser().getUserId(), customerUpdateDTO.email());
        customer.updateData(customerUpdateDTO);
        return new CustomerResponseDTO(customer);
    }

    private Customer findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("There isn't any customer with this id. Id " + customerId));
    }
}
