package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.Customer;
import com.resendegabriel.investwalletapi.domain.dto.request.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.CustomerUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.ResourceNotFoundException;
import com.resendegabriel.investwalletapi.repository.CustomerRepository;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import com.resendegabriel.investwalletapi.service.auth.UserService;
import com.resendegabriel.investwalletapi.service.mail.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IMailService mailService;

    @Override
    @Transactional
    public CustomerResponseDTO create(CustomerRegisterDTO customerRegisterDTO) {
        var user = userService.save(customerRegisterDTO.base64Credentials());
        var newCustomer = new Customer(customerRegisterDTO, user);
        var customerResponseDTO = customerRepository.save(newCustomer).toDto();
        mailService.sendWelcomeEmail(user.getEmail(), newCustomer.getFirstName());
        return customerResponseDTO;
    }

    @Override
    @Transactional
    public CustomerResponseDTO update(Long customerId, CustomerUpdateDTO customerUpdateDTO) {
        var customer = findById(customerId);
        if (customerUpdateDTO.email() != null)
            userService.updateEmail(customer.getUser().getUserId(), customerUpdateDTO.email());
        customer.updateData(customerUpdateDTO);
        return customer.toDto();
    }

    @Override
    public CustomerResponseDTO getByUserId(Long userId) {
        return customerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no customer with this userId. UserId " + userId)).toDto();
    }

    @Override
    @Transactional
    public void deleteById(Long customerId) {
        findById(customerId);
        customerRepository.deleteById(customerId);
    }

    @Override
    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("There isn't any customer with this id. Id " + customerId));
    }
}
