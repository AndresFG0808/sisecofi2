package com.sisecofi.contratos.util.consumer;

import com.sisecofi.libreria.comunes.dto.contrato.ParticipantesAdminContratoDto;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;
import com.sisecofi.libreria.comunes.util.consumer.BaseReporte;

import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


import org.springframework.stereotype.Component;

@Component
public class ReporteParticipantesContratoConsumer extends BaseExcel implements Consumer<ParticipantesAdminContratoDto> {

	public void agregarCabeceras(String cadenaTitulos) {
		BaseReporte.agregarCabeceras(sheet, workbook, cadenaTitulos);
	}

    @Override
    public void accept(ParticipantesAdminContratoDto participantesAdminContratoDto) {
        Boolean vigente =  participantesAdminContratoDto.getVigente();
        List<String> esVigente = new ArrayList<>();
        if (Boolean.TRUE.equals(vigente)){
            esVigente.add("Si");
        }else {
            esVigente.add("No");
        }

        Row row = sheet.createRow(numeroRenglon++);
        crearCelda(row, 0 , participantesAdminContratoDto.getIdParticipantesAdministracionContrato());
        crearCelda(row,1,participantesAdminContratoDto.getResponsabolidad().getNombre());
        crearCelda(row,2,participantesAdminContratoDto.getAdministracionGeneral().getAdministracion());
        crearCelda(row,3,participantesAdminContratoDto.getAdministracionCentral().getAdministracion());
        crearCelda(row,4,participantesAdminContratoDto.getUsuarioInformacion().getNombre());
        crearCelda(row,5,participantesAdminContratoDto.getUsuarioInformacion().getTelefono());
        crearCelda(row,6,participantesAdminContratoDto.getUsuarioInformacion().getCorreo());
        crearCelda(row,7,participantesAdminContratoDto.getFechaInicio());
        crearCelda(row,8,participantesAdminContratoDto.getFechaTermino());
        crearCelda(row,9,esVigente.get(0));
    }




}
