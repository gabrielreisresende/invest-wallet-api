package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/actives")
public class ActiveController {

    @Autowired
    private IActiveService activeService;

    @PostMapping
    public ResponseEntity<ActiveResponseDTO> create(@RequestBody @Valid ActiveRequestDTO activeRequestDTO) {
        var activeResponseDTO = activeService.create(activeRequestDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{activeId}")
                .buildAndExpand(activeResponseDTO.activeId())
                .toUri();
        return ResponseEntity.created(uri).body(activeResponseDTO);
    }
}
