package com.resendegabriel.investwalletapi.domain.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Builder
public record ClientUpdateDTO(@Email String email,

                              @CPF String cpf,

                              String firstName,

                              String lastName,

                              String phone,

                              LocalDate birthDate) {
}
