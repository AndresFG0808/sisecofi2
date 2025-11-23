package com.sisecofi.contratos.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class DictamenDecodeDto {
    @JsonProperty("idContrato")
    private int idContrato;

    @JsonProperty("idEstatusDictamen")
    private int idEstatusDictamen;

    @JsonProperty("estatus")
    private boolean estatus;

    @JsonProperty("periodoInicio")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date periodoInicio;

    @JsonProperty("periodoFin")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date periodoFin;

    @JsonProperty("periodoControl")
    private String periodoControl;
    @JsonProperty("iva")
    private String iva;

    @JsonProperty("tipoCambioReferencial")
    private double tipoCambioReferencial;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("ultimaModificación")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    private String ultimaModificacion;

    @JsonProperty("idDictamen")
    private String idDictamen;
}
