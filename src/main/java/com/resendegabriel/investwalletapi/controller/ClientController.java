package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.controller.doc.client.ClientDeleteDoc;
import com.resendegabriel.investwalletapi.controller.doc.client.ClientGetByIdDoc;
import com.resendegabriel.investwalletapi.controller.doc.client.ClientRegisterDoc;
import com.resendegabriel.investwalletapi.controller.doc.client.ClientUpdateDoc;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientRegisterDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ClientUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ClientResponseDTO;
import com.resendegabriel.investwalletapi.service.IClientService;
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
@RequestMapping("/clients")
@Tag(name = "2. Client", description = "Endpoints for the client manage his personal data.")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @ClientRegisterDoc
    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid ClientRegisterDTO clientRegisterDTO) {
        var clientResponseDTO = clientService.create(clientRegisterDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{clientId}").buildAndExpand(clientResponseDTO.clientId()).toUri();
        return ResponseEntity.created(uri).body(clientResponseDTO);
    }

    @ClientUpdateDoc
    @PutMapping("/{clientId}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long clientId, @RequestBody @Valid ClientUpdateDTO clientUpdateDTO) {
        return ResponseEntity.ok().body(clientService.update(clientId, clientUpdateDTO));
    }

    @ClientGetByIdDoc
    @GetMapping("/{userId}")
    public ResponseEntity<ClientResponseDTO> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(clientService.getByUserId(userId));
    }

    @ClientDeleteDoc
    @DeleteMapping("/{clientId}")
    public ResponseEntity<ClientResponseDTO> deleteById(@PathVariable Long clientId) {
        clientService.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }
}
