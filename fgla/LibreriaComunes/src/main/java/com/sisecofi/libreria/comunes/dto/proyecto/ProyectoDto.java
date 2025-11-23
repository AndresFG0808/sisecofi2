package com.sisecofi.libreria.comunes.dto.proyecto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoDto {

  private Long idProyecto;
  private Long idUser;
}