package com.sisecofi.proyectos.dto;

import com.sisecofi.proyectos.util.enums.ErroresEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class ComiteProyectoDto {

    private Integer idComiteProyecto;

    private Long idProyecto;

    private Integer idTipoMoneda;

    private Integer idContratoConvenio;

    private List<Integer> idsAfectacion;

    private Integer idComite;

    private Integer idPlantilla;

    private LocalDateTime fechaSesion;

    private Integer idContrato;

    private Integer idSesionNumero;

    private Integer idSesionClasificacion;

    @Size(max = 100)
    private String acuerdo;

    @NotNull(message = ErroresEnum.MensajeValidation.MSJ)
    @Size(max = 100)
    private String vigencia;

    private BigDecimal montoAutorizado;

    private BigDecimal tipoCambio;

    private BigDecimal monto;

    @Size(max = 2000, message = "El campo comentarios contiene mas de 2000 caracteres")
    private String comentarios;

    private Boolean estatus;
}
