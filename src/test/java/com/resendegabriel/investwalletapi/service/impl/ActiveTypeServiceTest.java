package com.resendegabriel.investwalletapi.service.impl;

import com.resendegabriel.investwalletapi.domain.ActiveType;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.ActiveTypeResponseDTO;
import com.resendegabriel.investwalletapi.repository.ActiveTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActiveTypeServiceTest {

    @InjectMocks
    private ActiveTypeService activeTypeService;

    @Mock
    private ActiveTypeRepository activeTypeRepository;

    private static ActiveTypeRequestDTO activeTypeRequestDTO;

    private static ActiveType activeType;

    @BeforeAll
    static void init() {
        activeTypeRequestDTO = new ActiveTypeRequestDTO("Papel");
        activeType = ActiveType.builder()
                .activeTypeId(1L)
                .activeType(activeTypeRequestDTO.activeType())
                .build();
    }

    @Test
    void shouldCreateANewActiveType() {
        when(activeTypeRepository.save(any(ActiveType.class))).thenReturn(activeType);

        var response = activeTypeService.create(activeTypeRequestDTO);

        assertEquals(new ActiveTypeResponseDTO(activeType), response);
        then(activeTypeRepository).should().save(any(ActiveType.class));
        then(activeTypeRepository).shouldHaveNoMoreInteractions();
    }
}