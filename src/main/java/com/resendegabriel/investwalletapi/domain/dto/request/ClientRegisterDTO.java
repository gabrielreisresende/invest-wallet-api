package com.resendegabriel.investwalletapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Builder
public record ClientRegisterDTO(@NotBlank String base64Credentials,

                                @CPF @NotNull String cpf,

                                @NotBlank String firstName,

                                @NotBlank String lastName,

                                @NotBlank String phone,

                                @NotNull
                                  LocalDate birthDate
) {
}
