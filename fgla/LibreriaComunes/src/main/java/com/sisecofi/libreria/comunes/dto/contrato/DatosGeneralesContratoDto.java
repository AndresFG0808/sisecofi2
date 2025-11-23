package com.sisecofi.libreria.comunes.dto.contrato;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DatosGeneralesContratoDto {

    @NotNull
    private Long idContrato;

    private String numeroContrato;

    private String numeroContratoCompraNet;

    private String acuerdo;

    @NotNull(message = "proveedores obligatorios")
    private List<Long> idsProveedores;

    private List<Long> idsProveedoresEliminados;

    @NotNull(message = "tipo de procedimiento obligatorio")
    private Integer idTipoProcedimiento;

    @NotNull(message = "numero procedimiento obligatorio")
    private String numeroProcedimiento;

    private Boolean convenioColaboracion;

    private Integer idDominioTecnologico;

    @NotNull(message = "fondeo oligatorio")
    private Integer idFondeoContrato;

    @NotNull(message = "objectivo obligatorio")
    private String objetivoServicio;

    @NotNull(message = "alcance obligatorio")
    private String alcanceServicio;

    @NotNull(message = "titulos de servicio obligatorio")
    private String titulosServicio;

    private Integer idCatConvenioColaboracion;
}
