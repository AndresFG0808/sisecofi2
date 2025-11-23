package com.sisecofi.contratos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CriteriosDeBusquedaContratoDto extends RepresentationModel<CriteriosDeBusquedaContratoDto> {
    @EqualsAndHashCode.Include
    private Long idContrato;
    private Long idProveedor;
    private Integer idEstatusContrato;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaTermino;
    private Integer idAdministracionCentral;
    private int page;
    private  int size;

    public String criterios(){
        String criterios = "";

        if (idContrato != null){
            criterios += "id contrato :" + idContrato + "|";
        }else {
            if (fechaInicio!=null){
                criterios += " fecha de inicio :" + fechaInicio + "|";
            }
            if (fechaTermino!=null){
                criterios += " fecha de termino :" + fechaTermino + "|";
            }
            if (idAdministracionCentral!=null){
                criterios += " administracion central:" + idAdministracionCentral + "|";
            }
            if (idEstatusContrato!=null){
                criterios += "id estatus de contrato :" + idEstatusContrato + "|";
            }
        }
        return criterios;
    }
}
