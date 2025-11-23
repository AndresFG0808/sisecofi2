package com.sisecofi.admindevengados.model.solicitudpago;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB +"documento_solicitud_pago")
@Getter
@Setter
public class DocumentoSolicitudPagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumentoSolicitudPago;

    private Long idSolicitudPago;

    private String dictamenId;

    private String content;

    private String header;

    private String fooder;

}
