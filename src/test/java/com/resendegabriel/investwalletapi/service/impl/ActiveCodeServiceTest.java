package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.ActiveCode;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveCodeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveCodeResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.repository.ActiveCodeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActiveCodeServiceTest {

    @InjectMocks
    private ActiveCodeService activeCodeService;

    @Mock
    private ActiveCodeRepository activeCodeRepository;

    private static ActiveCodeRequestDTO activeCodeRequestDTO;

    @Spy
    private static ActiveCode activeCode;

    @BeforeAll
    static void init() {
        var activeType = new ActiveTypeResponseDTO(1L, "Papel");
        var activeSector = new ActiveSectorResponseDTO(1L, "Hibrido");
        activeCodeRequestDTO = new ActiveCodeRequestDTO("MXRF11", activeType, activeSector);
        activeCode = new ActiveCode(activeCodeRequestDTO);
    }

    @Test
    void shouldCreateANewActiveCode() {
        when(activeCodeRepository.save(any(ActiveCode.class))).thenReturn(activeCode);

        var response = activeCodeService.create(activeCodeRequestDTO);

        assertEquals(new ActiveCodeResponseDTO(activeCode), response);
        then(activeCodeRepository).should().save(any(ActiveCode.class));
        then(activeCodeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldGetAllActiveCode() {
        when(activeCodeRepository.findAll()).thenReturn(List.of(activeCode));

        var response = activeCodeService.getAll();

        assertEquals(List.of(new ActiveCodeResponseDTO(activeCode)), response);
        then(activeCodeRepository).should().findAll();
        then(activeCodeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldGetByCodeAnActiveCode() {
        when(activeCodeRepository.findByActiveCode(anyString())).thenReturn(Optional.of(activeCode));

        var response = activeCodeService.getByCode(activeCodeRequestDTO.activeCode());

        assertEquals(new ActiveCodeResponseDTO(activeCode), response);
        then(activeCodeRepository).should().findByActiveCode(anyString());
        then(activeCodeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldDeleteByIdAnActiveSector() {
        when(activeCodeRepository.findByActiveCode(anyString())).thenReturn(Optional.of(activeCode));

        activeCodeService.delete(activeCodeRequestDTO.activeCode());

        then(activeCodeRepository).should().findByActiveCode(anyString());
        then(activeCodeRepository).should().delete(any(ActiveCode.class));
        then(activeCodeRepository).shouldHaveNoMoreInteractions();
    }
}