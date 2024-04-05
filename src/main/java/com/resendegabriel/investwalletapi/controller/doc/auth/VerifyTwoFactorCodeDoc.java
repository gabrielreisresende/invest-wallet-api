package com.resendegabriel.investwalletapi.controller.doc.auth;

import com.resendegabriel.investwalletapi.domain.auth.dto.LoginResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Verify Two Factor Code",
        description = "Endpoint to verify if the two factor code entered by the user is valid")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "202",
                description = "Two factor code is valid",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = LoginResponseDTO.class))),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request or Two Factor code invalid",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
                responseCode = "404",
                description = "Resource not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
                responseCode = "500",
                description = "Server error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StandardError.class)))
})
public @interface VerifyTwoFactorCodeDoc {
}
