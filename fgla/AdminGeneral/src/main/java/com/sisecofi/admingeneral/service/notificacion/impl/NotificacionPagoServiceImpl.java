package com.sisecofi.admingeneral.service.notificacion.impl;

import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;
import com.sisecofi.admingeneral.service.notificacion.NotificacionPagoService;
import com.sisecofi.admingeneral.service.plantillador.PlantillaService;
import com.sisecofi.admingeneral.util.docx4j.FileGenerator;
import com.sisecofi.libreria.comunes.dto.plantillador.NotificacionDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificacionPagoServiceImpl implements NotificacionPagoService {

    static Locale locale = Locale.forLanguageTag("es-MX");

    PlantillaService plantillaService;
    FileGenerator fileGenerator;

    @Override
    public byte[] generarDocumento(NotificacionDTO request) {
        
        var contenidoBase = plantillaService.obtenerContenidoPlantillador(request.getIdPlantillador(), null);

        RequestPlantilla requestPlantilla = new RequestPlantilla();
        requestPlantilla.setHeader(reemplazarValores(contenidoBase.getHeader(), request));
        requestPlantilla.setContenido(reemplazarValores(contenidoBase.getContenido(), request));
        requestPlantilla.setFooter(reemplazarValores(contenidoBase.getFooter(), request));

        return switch (request.getTipoDocumento()) {
            case "PDF" -> fileGenerator.generatePdfFromHtml(requestPlantilla);
            case "DOCX" -> fileGenerator.generateWordHtml(requestPlantilla);
            default -> throw new IllegalArgumentException("Tipo de documento no valido");
        };
    }

    private String reemplazarValores(String html, NotificacionDTO request) {
        Map<String, String> contentVariables = new HashMap<>();
        var formatoLargo = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy.", locale);
        var formatoCorto = DateTimeFormatter.ofPattern("dd/MMM/yyyy", locale);

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);

        contentVariables.put("${Num_de_oficio_de_solicitud}", request.getNumOficioSolicitud());
        contentVariables.put("${Fondeo}", request.getFondeoContrato());
        contentVariables.put("${Administración_General}", request.getAdministracionGeneral());
        contentVariables.put("${fecha}", formatoLargo.format(request.getFecha()));
        contentVariables.put("${Numero_contrato}", request.getNumeroContrato());
        contentVariables.put("${Nombre corto del contrato}", request.getNombreCortoContrato());
        contentVariables.put("${nombre_largo_del_contrato}", request.getNombreLargoContrato());
        contentVariables.put("${ID_Proyecto}", request.getProyectoId());
        contentVariables.put("${ID_Proveedor}", request.getProveedorId());
        contentVariables.put("${ID_Acuerdo}", request.getAcuerdoId());
        contentVariables.put("${ID_Contrato}", request.getContratoId());
        contentVariables.put("${ID_Folio_Fiscal}", request.getFolioFiscalId());
        contentVariables.put("${ID_Pedido}", request.getPedidoId());
        contentVariables.put("${ID_de_nota_de_crédito}", request.getNotaCreditoId());
        contentVariables.put("${Proveedor}", request.getProveedor());
        contentVariables.put("${importe_sat}", formatoMoneda.format(request.getImporteSat()));
        contentVariables.put("${importe_anam}", formatoMoneda.format(request.getImporteAnam()));
        contentVariables.put("${importe}", formatoMoneda.format(request.getImporte()));
        contentVariables.put("${moneda}", request.getMoneda());
        contentVariables.put("${Número_contrato}", request.getNumeroContrato());
        contentVariables.put("${Fecha_recepción}", formatoCorto.format(request.getFechaRecepcion()));
        contentVariables.put("${Comprobantes}", request.getComprobantes());
        contentVariables.put("${Gasto}", request.getGasto());
        contentVariables.put("${Periodo_del_servicio}", request.getPeriodoServicio());
        contentVariables.put("${No_factura}", request.getNumeroFactura());
        contentVariables.put("${No_nota}", request.getNumeroNota());
        contentVariables.put("${Fecha_limite}", request.getFechaLimite());
        contentVariables.put("${Unidad_administrativa}", request.getUnidadAdministrativa());
        contentVariables.put("${Verificador}", request.getVerificador());
        contentVariables.put("${Puesto}", request.getPuesto());
        contentVariables.put("${ADM_del_contrato}", request.getAdmContrato());
        contentVariables.put("${ADM_central}", request.getAdmCentral());
        contentVariables.put("${pagina}", request.getPagina().toString());
        contentVariables.put("${paginas}", request.getPaginas().toString());

        for (Map.Entry<String, String> entry : contentVariables.entrySet()) {
            html = html.replace(entry.getKey(), entry.getValue());
        }

        return html;
    }
}
