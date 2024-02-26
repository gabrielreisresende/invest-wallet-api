package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.ActiveSector;
import com.resendegabriel.investwalletapi.domain.dto.ActiveSectorRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveSectorResponseDTO;
import com.resendegabriel.investwalletapi.repository.ActiveSectorRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActiveSectorServiceTest {

    @InjectMocks
    private ActiveSectorService activeSectorService;

    @Mock
    private ActiveSectorRepository activeSectorRepository;

    private static ActiveSectorRequestDTO activeSectorRequestDTO;

    @Spy
    private static ActiveSector activeSector;

    @BeforeAll
    static void init() {
        activeSectorRequestDTO = new ActiveSectorRequestDTO("Shopping");
        activeSector = ActiveSector.builder()
                .activeSectorId(1L)
                .activeSector(activeSectorRequestDTO.activeSector())
                .build();
    }

    @Test
    void shouldCreateANewActiveSector() {
        when(activeSectorRepository.save(any(ActiveSector.class))).thenReturn(activeSector);

        var response = activeSectorService.create(activeSectorRequestDTO);

        assertEquals(new ActiveSectorResponseDTO(activeSector), response);
        then(activeSectorRepository).should().save(any(ActiveSector.class));
        then(activeSectorRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldUpdateAnActiveSector() {
        var activeSectorUpdateDTO = new ActiveSectorRequestDTO("New name");
        when(activeSectorRepository.findById(anyLong())).thenReturn(Optional.of(activeSector));

        var response = activeSectorService.updateActiveSectorName(1L, activeSectorUpdateDTO);

        assertEquals(new ActiveSectorResponseDTO(activeSector), response);
        then(activeSectorRepository).should().findById(anyLong());
        then(activeSectorRepository).shouldHaveNoMoreInteractions();
        then(activeSector).should().updateActiveSectorName(anyString());
    }

    @Test
    void shouldGetAllActiveSectors() {
        when(activeSectorRepository.findAll()).thenReturn(List.of(activeSector));

        var response = activeSectorService.getAll();

        assertEquals(List.of(new ActiveSectorResponseDTO(activeSector)), response);
        then(activeSectorRepository).should().findAll();
        then(activeSectorRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldGetByIdAnActiveSector() {
        when(activeSectorRepository.findById(anyLong())).thenReturn(Optional.of(activeSector));

        var response = activeSectorService.getById(1L);

        assertEquals(new ActiveSectorResponseDTO(activeSector), response);
        then(activeSectorRepository).should().findById(anyLong());
        then(activeSectorRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldDeleteByIdAnActiveSector() {
        when(activeSectorRepository.findById(anyLong())).thenReturn(Optional.of(activeSector));

        activeSectorService.deleteById(1L);

        then(activeSectorRepository).should().findById(anyLong());
        then(activeSectorRepository).should().deleteById(anyLong());
        then(activeSectorRepository).shouldHaveNoMoreInteractions();
    }
}