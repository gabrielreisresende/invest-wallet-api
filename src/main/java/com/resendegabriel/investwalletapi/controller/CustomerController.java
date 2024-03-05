package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.controller.doc.customer.CustomerDeleteDoc;
import com.resendegabriel.investwalletapi.controller.doc.customer.CustomerGetByIdDoc;
import com.resendegabriel.investwalletapi.controller.doc.customer.CustomerRegisterDoc;
import com.resendegabriel.investwalletapi.controller.doc.customer.CustomerUpdateDoc;
import com.resendegabriel.investwalletapi.domain.dto.request.CustomerRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.CustomerUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.CustomerResponseDTO;
import com.resendegabriel.investwalletapi.service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@Tag(name = "Customer", description = "Endpoints for the customer manage his personal data.")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @CustomerRegisterDoc
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody @Valid CustomerRegisterDTO customerRegisterDTO) {
        var customerResponseDTO = customerService.create(customerRegisterDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}").buildAndExpand(customerResponseDTO.customerId()).toUri();
        return ResponseEntity.created(uri).body(customerResponseDTO);
    }

    @CustomerUpdateDoc
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long customerId, @RequestBody @Valid CustomerUpdateDTO customerUpdateDTO) {
        return ResponseEntity.ok().body(customerService.update(customerId, customerUpdateDTO));
    }

    @CustomerGetByIdDoc
    @GetMapping("/{userId}")
    public ResponseEntity<CustomerResponseDTO> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(customerService.getByUserId(userId));
    }

    @CustomerDeleteDoc
    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> deleteById(@PathVariable Long customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }
}
