package com.resendegabriel.investwalletapi.controller.doc.client;

import com.resendegabriel.investwalletapi.domain.dto.response.ClientResponseDTO;
import com.resendegabriel.investwalletapi.exceptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get client details",
        description = "Endpoint to get a client data by client id")
@SecurityRequirement(name = "Bearer Authentication")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Get client data successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ClientResponseDTO.class))),
        @ApiResponse(
                responseCode = "403",
                description = "Access unauthorized",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request",
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
public @interface ClientGetByIdDoc {
}
