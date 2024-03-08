package com.resendegabriel.investwalletapi.service;

import com.resendegabriel.investwalletapi.domain.ActiveCode;
import com.resendegabriel.investwalletapi.domain.dto.request.ActiveCodeRequestDTO;
import com.resendegabriel.investwalletapi.domain.dto.response.ActiveCodeResponseDTO;

import java.util.List;

public interface IActiveCodeService {

    ActiveCodeResponseDTO create(ActiveCodeRequestDTO activeCodeRequestDTO);

    List<ActiveCodeResponseDTO> getAll();

    ActiveCodeResponseDTO getByCode(String activeCode);

    void delete(String activeCode);

    ActiveCode findActiveCodeEntity(String activeCode);
}
