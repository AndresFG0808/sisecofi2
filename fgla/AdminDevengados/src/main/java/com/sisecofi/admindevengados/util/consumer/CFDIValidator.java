package com.sisecofi.admindevengados.util.consumer;

import org.w3c.dom.*;

import com.sisecofi.admindevengados.service.CatalogoService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.libreria.comunes.model.catalogo.CatClaveProducto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.time.LocalDateTime;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CFDIValidator {
	
	private final CatalogoService catalogoService;

	public String validarCFDI(MultipartFile file) {
		try {
			Document doc = parseXML(file);
			Element comprobanteElement = doc.getDocumentElement();
			
			String fechaStr= comprobanteElement.getAttribute("Fecha");
			LocalDateTime fechaHora = LocalDateTime.parse(fechaStr);
			LocalDateTime fechaValidacion = LocalDateTime.of(2025, 10, 20, 0, 0, 0);
	        
	        if (!fechaHora.isBefore(fechaValidacion)) {
	        	validarPorFecha (comprobanteElement, doc);
	        }

			return "Validación exitosa";
		} catch (Exception e) {
			throw new CfdiException("Error al procesar el XML: " + e.getMessage(), e);
		}
	}
	
	private void validarPorFecha(Element comprobanteElement, Document doc) {
		if (!validarAtributosComprobante(comprobanteElement)) {
			throw new CfdiException("Error en el metodo de pago.");
    	}

		if (!validarEmisor(doc)) {
			throw new CfdiException("Error en el RFC o nombre del emisor.");
		}

		if (!validarConceptos(doc)) {
			throw new CfdiException("Error en los conceptos del CFDI.");
		}
		
		if (!validarFormaPago(comprobanteElement)) {
			throw new CfdiException("Error en la forma de pago.");
		}
		
		if (!validarCondicionesDePago(comprobanteElement)) {
			throw new CfdiException("Error en las condiciones de pago.");
		}
		
		if (!validarMoneda(comprobanteElement)) {
			throw new CfdiException("Error al validar la moneda.");
		}
		
		if (!validarReceptor(doc)) {
			throw new CfdiException("Error en los datos del receptor");
		}
	}
	

	private Document parseXML(MultipartFile file) {
		try {
			InputStream xmlInput = file.getInputStream();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);

			dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			dbFactory.setXIncludeAware(false);
			dbFactory.setExpandEntityReferences(false);

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlInput);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			throw new CfdiException("Error al procesar el archivo XML: " + e.getMessage(), e);
		}
	}

	private boolean validarAtributosComprobante(Element comprobanteElement) {
		return validarMetodoPago(comprobanteElement.getAttribute("MetodoPago"));
	}

	private boolean validarEmisor(Document doc) {
		NodeList emisorList = doc.getElementsByTagName("cfdi:Emisor");
		if (emisorList.getLength() == 0)
			return false;

		Element emisorElement = (Element) emisorList.item(0);

		return validarRFC(emisorElement.getAttribute("Rfc")) && !emisorElement.getAttribute("Nombre").isEmpty();
	}

	private boolean validarConceptos(Document doc) {
		NodeList conceptosList = doc.getElementsByTagName("cfdi:Concepto");
		List<CatClaveProducto>catalogoClave= catalogoService.obtenerCatalogoClaveProducto();
		for (int i = 0; i < conceptosList.getLength(); i++) {
			Element conceptoElement = (Element) conceptosList.item(i);
			String clave = conceptoElement.getAttribute("ClaveProdServ").trim();
			if (!validarClaveUnidad(conceptoElement.getAttribute("ClaveUnidad"))
					|| !validarUnidad(conceptoElement.getAttribute("Unidad"))
					|| conceptoElement.getAttribute("Descripcion").isEmpty() 
					|| catalogoClave.stream().noneMatch(c -> c.getClave().equals(clave)) ) {
				return false;
			}
		}
		return true;
	}

	private boolean validarMetodoPago(String metodoPago) {
		return Constantes.LISTA_PAGOS.contains(metodoPago.trim().toUpperCase());
	}

	private boolean validarRFC(String rfc) {
		return rfc.matches("[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}");
	}

	private boolean validarClaveUnidad(String claveUnidad) {
		if (claveUnidad == null || claveUnidad.trim().isEmpty()) {
			return false;
		}
		return Constantes.LISTA_CLAVE_UNIDAD.contains(claveUnidad.trim().toUpperCase());
	}

	private boolean validarUnidad(String unidad) {
		if (unidad.trim().isEmpty()) {
			return true;
		}
		return Constantes.LISTA_UNIDAD.contains(unidad.trim().toUpperCase());
	}

	private boolean validarFormaPago(Element comprobanteElement) {
		String formaPago = comprobanteElement.getAttribute("FormaPago");
		return Constantes.LISTA_FORMA_PAGO.contains(formaPago.trim().toUpperCase());
	}
	

	private boolean validarCondicionesDePago(Element comprobanteElement) {
		String formaPago = comprobanteElement.getAttribute("CondicionesDePago");
		return Constantes.LISTA_CONDICIONES_PAGO.contains(formaPago.trim().toUpperCase());
	}

	private boolean validarMoneda(Element comprobanteElement) {
		String formaPago = comprobanteElement.getAttribute("Moneda");
		return Constantes.LISTA_MONEDA.contains(formaPago.trim().toUpperCase());
	}

	private boolean validarReceptor(Document doc) {
		NodeList receptorList = doc.getElementsByTagName("cfdi:Receptor");
		if (receptorList.getLength() == 0)
			return false;

		Element receptorElement = (Element) receptorList.item(0);

		return (receptorElement.getAttribute("Rfc").trim().equalsIgnoreCase("FAP211125MA2")
				&& receptorElement.getAttribute("Nombre").trim()
						.equalsIgnoreCase("FIDEICOMISO DE ADMINISTRACION Y PAGO NUMERO 80775")
						&& receptorElement.getAttribute("RegimenFiscalReceptor").trim().equalsIgnoreCase("603")
						&& Constantes.LISTA_USO_CFDI.contains(receptorElement.getAttribute("UsoCFDI").trim().toUpperCase()));
	}
	
	
	
}
