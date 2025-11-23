package com.sisecofi.contratos.util.validator;

import org.springframework.stereotype.Component;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class InformesValidador {

	public boolean validaInformesDocumentalesUniVez(InformesDocumentalesUnicaVezModel informes) {
		boolean resultado = false;
		if (!informes.getFase().equals("") && !informes.getFechaEntrega().equals("")
				&& !informes.getInformeDocumental().equals("")
				&& !informes.getPenasDeduccionesAplicables().equals("")) {
			resultado = true;
		}
		return resultado;
	}

	//ajustar dependiendo de la validacion del ecu
	public boolean validaInformesDocumentalesPeriodicos(InformesDocumentalesPeriodicosModel informes) {
		boolean resultado = false;
		if (!informes.getInformeDocumental().equals("") && !informes.getPenaConvencionalDeductiva().equals("")
				&& informes.getIdPeriodicidad()!=null) {
			resultado = true;
		}
		return resultado;
	}

	//ajustar dependiendo de la validacion del ecu
	public boolean validaInformesDocumentalesServicios(InformesDocumentalesServiciosModel informes) {
		boolean resultado = false;
		if (!informes.getInformeDocumental().equals("") && !informes.getPenasDeduccionesAplicables().equals("")
				&& informes.getIdPeriodicidad()!=null && !informes.getFechaEntrega().equals("")) {
			resultado = true;
		}
		return resultado;
	}
	
	public boolean validaNivelesServicioSLA(NivelesServicioSLAModel niveles) {
		boolean resultado = false;
		if(!niveles.getDeduccionesAplicables().equals("") && !niveles.getSla().equals("") && !niveles.getObjectivoMinimo().equals("")) {
			resultado = true;
		}
		return resultado;
	}

}
