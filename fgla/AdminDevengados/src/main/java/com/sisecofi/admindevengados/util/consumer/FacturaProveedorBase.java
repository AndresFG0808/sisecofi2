package com.sisecofi.admindevengados.util.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacturaProveedorBase {
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(length = 13)
    @JacksonXmlProperty(localName = "Rfc")
    private String rfc;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(length = 13)
    @JacksonXmlProperty(localName = "Nombre")
    private String nombre;

}
