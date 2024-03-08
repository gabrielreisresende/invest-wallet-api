package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.controller.doc.activeSector.ActiveSectorCreateDoc;
import com.resendegabriel.investwalletapi.controller.doc.activeSector.ActiveSectorDeleteDoc;
import com.resendegabriel.investwalletapi.controller.doc.activeSector.ActiveSectorGetAllDoc;
import com.resendegabriel.investwalletapi.controller.doc.activeSector.ActiveSectorGetByIdDoc;
import com.resendegabriel.investwalletapi.controller.doc.activeSector.ActiveSectorUpdateDoc;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveSectorRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveSectorService;
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
import java.util.List;

@RestController
@RequestMapping("/active-sectors")
@Tag(name = "7. Active Sector", description = "Endpoints for the admin users manage the active sectors and for all users get the active sectors infos.")
public class ActiveSectorController {

    @Autowired
    private IActiveSectorService activeSectorService;

    @ActiveSectorCreateDoc
    @PostMapping
    public ResponseEntity<ActiveSectorResponseDTO> create(@RequestBody @Valid ActiveSectorRequestDTO activeSectorRequestDTO) {
        var activeSectorResponseDTO = activeSectorService.create(activeSectorRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{activeSectorId}").buildAndExpand(activeSectorResponseDTO.activeSectorId()).toUri();
        return ResponseEntity.created(uri).body(activeSectorResponseDTO);
    }

    @ActiveSectorUpdateDoc
    @PutMapping("/{activeSectorId}")
    public ResponseEntity<ActiveSectorResponseDTO> updateActiveTypeName(@PathVariable Long activeSectorId, @RequestBody @Valid ActiveSectorRequestDTO activeSectorRequestDTO) {
        return ResponseEntity.ok().body(activeSectorService.updateActiveSectorName(activeSectorId, activeSectorRequestDTO));
    }

    @ActiveSectorGetAllDoc
    @GetMapping
    public ResponseEntity<List<ActiveSectorResponseDTO>> getAll() {
        return ResponseEntity.ok().body(activeSectorService.getAll());
    }

    @ActiveSectorGetByIdDoc
    @GetMapping("/{activeSectorId}")
    public ResponseEntity<ActiveSectorResponseDTO> getById(@PathVariable Long activeSectorId) {
        return ResponseEntity.ok().body(activeSectorService.getById(activeSectorId));
    }

    @ActiveSectorDeleteDoc
    @DeleteMapping("/{activeSectorId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long activeSectorId) {
        activeSectorService.deleteById(activeSectorId);
        return ResponseEntity.noContent().build();
    }
}