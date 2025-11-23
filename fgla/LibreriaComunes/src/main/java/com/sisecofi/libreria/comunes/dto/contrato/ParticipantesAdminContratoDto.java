package com.sisecofi.libreria.comunes.dto.contrato;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatResponsabilidad;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParticipantesAdminContratoDto  {
   private Long idParticipantesAdministracionContrato;
   private UsuarioInfoDto usuarioInformacion;
   private LocalDateTime fechaInicio;
   private LocalDateTime fechaTermino;
   private Boolean vigente;
   private CatAdmonGeneral administracionGeneral;
   private CatAdmonCentral administracionCentral;
   private CatResponsabilidad responsabolidad;

}
