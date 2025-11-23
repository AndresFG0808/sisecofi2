package com.sisecofi.admindevengados.util.consumer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.sisecofi.admindevengados.dto.PenaContractualExcelDto;

@Component
public class ReportePenaContractualConsumer extends PenasDeduccionesBaseConsumer {
    @Override
    protected void crearCeldas(Row row, PenaContractualExcelDto model) {
        crearCelda(row, 0, model.getDictamenId());
        crearCelda(row, 1, model.getIdPenaPrimary());
        crearCelda(row, 2, model.getTipoPena());
        crearCelda(row, 3, model.getDocumentoNombre());
        crearCelda(row, 4, model.getPenaAplicable());
        crearCelda(row, 5, model.getDesglose());
        crearCelda(row, 6, model.getMonto());
    }
}
