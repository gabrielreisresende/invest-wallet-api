package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.domain.dto.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.CustomerUpdateDTO;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody @Valid CustomerRegisterDTO customerRegisterDTO) {
        var customerResponseDTO = customerService.create(customerRegisterDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}").buildAndExpand(customerResponseDTO.customerId()).toUri();
        return ResponseEntity.created(uri).body(customerResponseDTO);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long customerId, @RequestBody @Valid CustomerUpdateDTO customerUpdateDTO) {
        return ResponseEntity.ok().body(customerService.update(customerId, customerUpdateDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerResponseDTO> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(customerService.getByUserId(userId));
    }
}
