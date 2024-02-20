package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.domain.dto.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.WalletResponseDTO;
import com.resendegabriel.investwalletapi.service.IWalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private IWalletService walletService;

    @PostMapping
    public ResponseEntity<WalletResponseDTO> create(@RequestBody @Valid WalletRequestDTO walletRequestDTO) {
        var walletResponseDTO = walletService.create(walletRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{walletId}").buildAndExpand(walletResponseDTO.walletId()).toUri();
        return ResponseEntity.created(uri).body(walletResponseDTO);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<WalletResponseDTO>> getAll(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(walletService.getAll(customerId));
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> getById(@PathVariable Long walletId) {
        return ResponseEntity.ok().body(walletService.getById(walletId));
    }
}
