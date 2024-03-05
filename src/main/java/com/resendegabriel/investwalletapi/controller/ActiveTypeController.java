package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.controller.doc.activeType.ActiveTypeCreateDoc;
import com.resendegabriel.investwalletapi.controller.doc.activeType.ActiveTypeDeleteDoc;
import com.resendegabriel.investwalletapi.controller.doc.activeType.ActiveTypeGetAllDoc;
import com.resendegabriel.investwalletapi.controller.doc.activeType.ActiveTypeGetByIdDoc;
import com.resendegabriel.investwalletapi.controller.doc.activeType.ActiveTypeUpdateDoc;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveTypeService;
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
@RequestMapping("/active-types")
@Tag(name = "Active Type", description = "Endpoints for the admin users manage the active types and for all users get the active types infos.")
public class ActiveTypeController {

    @Autowired
    private IActiveTypeService activeTypeService;

    @ActiveTypeCreateDoc
    @PostMapping
    public ResponseEntity<ActiveTypeResponseDTO> create(@RequestBody @Valid ActiveTypeRequestDTO activeTypeRequestDTO) {
        var activeTypeResponseDTO = activeTypeService.create(activeTypeRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{activeTypeId}").buildAndExpand(activeTypeResponseDTO.activeTypeId()).toUri();
        return ResponseEntity.created(uri).body(activeTypeResponseDTO);
    }

    @ActiveTypeUpdateDoc
    @PutMapping("/{activeTypeId}")
    public ResponseEntity<ActiveTypeResponseDTO> updateActiveTypeName(@PathVariable Long activeTypeId, @RequestBody @Valid ActiveTypeRequestDTO activeTypeRequestDTO) {
        return ResponseEntity.ok().body(activeTypeService.updateActiveTypeName(activeTypeId, activeTypeRequestDTO));
    }

    @ActiveTypeGetAllDoc
    @GetMapping
    public ResponseEntity<List<ActiveTypeResponseDTO>> getAll() {
        return ResponseEntity.ok().body(activeTypeService.getAll());
    }

    @ActiveTypeGetByIdDoc
    @GetMapping("/{activeTypeId}")
    public ResponseEntity<ActiveTypeResponseDTO> getById(@PathVariable Long activeTypeId) {
        return ResponseEntity.ok().body(activeTypeService.getById(activeTypeId));
    }

    @ActiveTypeDeleteDoc
    @DeleteMapping("/{activeTypeId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long activeTypeId) {
        activeTypeService.deleteById(activeTypeId);
        return ResponseEntity.noContent().build();
    }
}