package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.domain.dto.ActiveSectorRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveSectorService;
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
import java.util.List;

@RestController
@RequestMapping("/active-sectors")
public class ActiveSectorController {

    @Autowired
    private IActiveSectorService activeSectorService;

    @PostMapping
    public ResponseEntity<ActiveSectorResponseDTO> create(@RequestBody @Valid ActiveSectorRequestDTO activeSectorRequestDTO) {
        var activeSectorResponseDTO = activeSectorService.create(activeSectorRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{activeSectorId}").buildAndExpand(activeSectorResponseDTO.activeSectorId()).toUri();
        return ResponseEntity.created(uri).body(activeSectorResponseDTO);
    }

    @PutMapping("/{activeSectorId}")
    public ResponseEntity<ActiveSectorResponseDTO> updateActiveTypeName(@PathVariable Long activeSectorId, @RequestBody @Valid ActiveSectorRequestDTO activeSectorRequestDTO) {
        return ResponseEntity.ok().body(activeSectorService.updateActiveSectorName(activeSectorId, activeSectorRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ActiveSectorResponseDTO>> getAll() {
        return ResponseEntity.ok().body(activeSectorService.getAll());
    }

    @GetMapping("/{activeSectorId}")
    public ResponseEntity<ActiveSectorResponseDTO> getById(@PathVariable Long activeSectorId) {
        return ResponseEntity.ok().body(activeSectorService.getById(activeSectorId));
    }

    @DeleteMapping("/{activeSectorId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long activeSectorId) {
        activeSectorService.deleteById(activeSectorId);
        return ResponseEntity.noContent().build();
    }
}