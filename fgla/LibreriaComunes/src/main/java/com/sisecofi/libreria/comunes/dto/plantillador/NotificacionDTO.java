package com.sisecofi.libreria.comunes.dto.plantillador;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificacionDTO {
    String numOficioSolicitud;
    Long idPlantillador;
    String tipoDocumento;
    String administracionGeneral;
    LocalDate fecha;
    String numeroContrato;
    String nombreCortoContrato;
    String nombreLargoContrato;
    String proyectoId;
    String proveedorId;
    String acuerdoId;
    String contratoId;
    String folioFiscalId;
    String pedidoId;
    String notaCreditoId;
    String proveedor;
    Double importeSat;
    Double importeAnam;
    Double importe;
    String moneda;
    LocalDate fechaRecepcion;
    String comprobantes; //***Fecha de recepción de comprobantes
    String gasto;//lo completa el usuario, falla servicio interno si no se manda
    String periodoServicio;
    String numeroFactura;
    String numeroNota;
    String fechaLimite;
    String unidadAdministrativa;////lo completa el usuario, falla servicio interno si no se manda
    String verificador;
    String puesto;
    String admContrato;
    String admCentral;

    Integer pagina = 1;
    Integer paginas = 1;

    String fondeoContrato;
}
