package com.resendegabriel.investwalletapi.controller;

import com.resendegabriel.investwalletapi.controller.doc.wallet.WalletCreateDoc;
import com.resendegabriel.investwalletapi.controller.doc.wallet.WalletDeleteDoc;
import com.resendegabriel.investwalletapi.controller.doc.wallet.WalletGetAllDoc;
import com.resendegabriel.investwalletapi.controller.doc.wallet.WalletGetByIdDoc;
import com.resendegabriel.investwalletapi.controller.doc.wallet.WalletUpdateDoc;
import com.resendegabriel.investwalletapi.controller.doc.wallet.reports.WalletActiveSectorsReportDoc;
import com.resendegabriel.investwalletapi.controller.doc.wallet.reports.WalletActiveTypesReportDoc;
import com.resendegabriel.investwalletapi.controller.doc.wallet.reports.WalletActivesReportDoc;
import com.resendegabriel.investwalletapi.domain.dto.request.UpdateWalletDTO;
import com.resendegabriel.investwalletapi.domain.dto.request.WalletRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.WalletResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveSectorsReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActiveTypesReportDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.reports.WalletActivesReportDTO;
import com.resendegabriel.investwalletapi.service.IWalletService;
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
@RequestMapping("/wallets")
@Tag(name = "3. Wallet", description = "Endpoints for the customer manage his invest wallets.")
public class WalletController {

    @Autowired
    private IWalletService walletService;

    @WalletCreateDoc
    @PostMapping
    public ResponseEntity<WalletResponseDTO> create(@RequestBody @Valid WalletRequestDTO walletRequestDTO) {
        var walletResponseDTO = walletService.create(walletRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{walletId}").buildAndExpand(walletResponseDTO.walletId()).toUri();
        return ResponseEntity.created(uri).body(walletResponseDTO);
    }

    @WalletGetAllDoc
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<WalletResponseDTO>> getAll(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(walletService.getAll(customerId));
    }

    @WalletGetByIdDoc
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> getById(@PathVariable Long walletId) {
        return ResponseEntity.ok().body(walletService.getById(walletId));
    }

    @WalletUpdateDoc
    @PutMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> update(@PathVariable Long walletId, @RequestBody @Valid UpdateWalletDTO updateWalletDTO) {
        return ResponseEntity.ok().body(walletService.update(walletId, updateWalletDTO));
    }

    @WalletDeleteDoc
    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long walletId) {
        walletService.deleteById(walletId);
        return ResponseEntity.noContent().build();
    }

    @WalletActivesReportDoc
    @GetMapping("/{walletId}/actives/details")
    public ResponseEntity<WalletActivesReportDTO> getWalletActivesReport(@PathVariable Long walletId) {
        return ResponseEntity.ok().body(walletService.getWalletActivesReport(walletId));
    }

    @WalletActiveTypesReportDoc
    @GetMapping("/{walletId}/active-types/details")
    public ResponseEntity<WalletActiveTypesReportDTO> getWalletActiveTypesReport(@PathVariable Long walletId) {
        return ResponseEntity.ok().body(walletService.getWalletActiveTypesReport(walletId));
    }

    @WalletActiveSectorsReportDoc
    @GetMapping("/{walletId}/active-sectors/details")
    public ResponseEntity<WalletActiveSectorsReportDTO> getWalletActiveSectorsReport(@PathVariable Long walletId) {
        return ResponseEntity.ok().body(walletService.getWalletActiveSectorsReport(walletId));
    }
}
