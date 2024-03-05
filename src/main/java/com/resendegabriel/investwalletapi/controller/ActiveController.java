package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.controller.doc.active.ActiveCreateDoc;
import com.resendegabriel.investwalletapi.controller.doc.active.ActiveDeleteDoc;
import com.resendegabriel.investwalletapi.controller.doc.active.ActiveGetByIdDoc;
import com.resendegabriel.investwalletapi.controller.doc.active.ActiveUpdateDoc;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveUpdateDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveService;
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
@RequestMapping("/actives")
@Tag(name = "Active", description = "Endpoints for the customer manage his actives.")
public class ActiveController {

    @Autowired
    private IActiveService activeService;

    @ActiveCreateDoc
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

    @ActiveUpdateDoc
    @PutMapping("/{activeId}")
    public ResponseEntity<ActiveResponseDTO> update(@PathVariable Long activeId, @RequestBody @Valid ActiveUpdateDTO activeUpdateDTO) {
        return ResponseEntity.ok().body(activeService.update(activeId, activeUpdateDTO));
    }

    @ActiveDeleteDoc
    @DeleteMapping("/{activeId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long activeId) {
        activeService.deleteById(activeId);
        return ResponseEntity.noContent().build();
    }

    @ActiveGetByIdDoc
    @GetMapping("/{activeId}")
    public ResponseEntity<ActiveResponseDTO> getById(@PathVariable Long activeId) {
        return ResponseEntity.ok().body(activeService.getById(activeId));
    }
}
