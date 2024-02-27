package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.domain.dto.request.ActiveCodeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveCodeResponseDTO;
import com.resendegabriel.investwalletapi.service.IActiveCodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/active-codes")
public class ActiveCodeController {

    @Autowired
    private IActiveCodeService activeCodeService;

    @PostMapping
    public ResponseEntity<ActiveCodeResponseDTO> create(@RequestBody @Valid ActiveCodeRequestDTO activeCodeRequestDTO) {
        var activeCodeResponseDTO = activeCodeService.create(activeCodeRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .query("activeCode={activeCode}")
                .buildAndExpand(activeCodeResponseDTO.activeCode())
                .toUri();
        return ResponseEntity.created(uri).body(activeCodeResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ActiveCodeResponseDTO>> getAll() {
        return ResponseEntity.ok().body(activeCodeService.getAll());
    }

    @GetMapping("/details")
    public ResponseEntity<ActiveCodeResponseDTO> getByCode(@RequestParam String activeCode) {
        return ResponseEntity.ok().body(activeCodeService.getByCode(activeCode));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String activeCode) {
        activeCodeService.delete(activeCode);
        return ResponseEntity.noContent().build();
    }
}