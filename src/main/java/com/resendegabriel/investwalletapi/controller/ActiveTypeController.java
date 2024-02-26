package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveTypeService;
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
import java.util.List;

@RestController
@RequestMapping("/active-types")
public class ActiveTypeController {

    @Autowired
    private IActiveTypeService activeTypeService;

    @PostMapping
    public ResponseEntity<ActiveTypeResponseDTO> create(@RequestBody @Valid ActiveTypeRequestDTO activeTypeRequestDTO) {
        var activeTypeResponseDTO = activeTypeService.create(activeTypeRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{activeTypeId}").buildAndExpand(activeTypeResponseDTO.activeTypeId()).toUri();
        return ResponseEntity.created(uri).body(activeTypeResponseDTO);
    }

    @PutMapping("/{activeTypeId}")
    public ResponseEntity<ActiveTypeResponseDTO> updateActiveTypeName(@PathVariable Long activeTypeId, @RequestBody @Valid ActiveTypeRequestDTO activeTypeRequestDTO) {
        return ResponseEntity.ok().body(activeTypeService.updateActiveTypeName(activeTypeId, activeTypeRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ActiveTypeResponseDTO>> getAll() {
        return ResponseEntity.ok().body(activeTypeService.getAll());
    }

    @GetMapping("/{activeTypeId}")
    public ResponseEntity<ActiveTypeResponseDTO> getById(@PathVariable Long activeTypeId) {
        return ResponseEntity.ok().body(activeTypeService.getById(activeTypeId));
    }
}