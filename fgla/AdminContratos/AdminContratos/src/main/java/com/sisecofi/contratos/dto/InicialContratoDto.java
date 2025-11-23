package com.sisecofi.contratos.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InicialContratoDto {

   private Long idContrato;

   @NotNull(message = "El proyecto debe ser registrato")
   private Long idProyecto;

   @NotNull(message = "Nombre de contrato obligatorio")
   private String nombreContrato;

   @NotNull(message = "Nombre corto del contrato")
   private String nombreCortoContrato;
}
