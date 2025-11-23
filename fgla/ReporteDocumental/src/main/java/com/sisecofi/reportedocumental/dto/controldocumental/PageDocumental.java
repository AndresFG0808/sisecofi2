package com.sisecofi.reportedocumental.dto.controldocumental;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.reportedocumental.dto.CommonPageHelper;
import com.sisecofi.reportedocumental.util.Constantes;
import com.sisecofi.reportedocumental.util.UtilControlDocumental;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Setter
@Getter
public class PageDocumental implements CommonPageHelper<ProyectoDocumental> {

	@JsonIgnore
	private List<String> etiquetas;
	private List<ProyectoDocumental> content;
	@JsonIgnore
	private int size;
	@JsonIgnore
	private int totalPages;
	@JsonIgnore
	private long totalElements;
	@JsonIgnore
	private boolean first;
	@JsonIgnore
	private int number;
	@JsonIgnore
	private int numberOfElements;
	@JsonIgnore
	private boolean last;
	@JsonIgnore
	private Sort sort;
	@JsonIgnore
	private Pageable pageable;

	private int documentosRequeridos;
	private int documentosCargados;
	private int documentosQueNoAplican;
	private int documentosPendientes;
	

	public PageDocumental(Page<Object[]> page) {
		this.content = page.getContent().stream().map(m -> {
			ProyectoDocumental p1 = new ProyectoDocumental();
			p1.setIdProyecto(Long.parseLong(String.valueOf(m[0])));
			p1.setProyecto(String.valueOf(m[1]));
			p1.setNombreCorto(String.valueOf(m[2]));
			p1.setFase(String.valueOf(m[3]));
			p1.setPlantilla(String.valueOf(m[4]));
			p1.setDescripcion(String.valueOf(m[5]));
			if (Boolean.parseBoolean(String.valueOf(m[6]))) {
				p1.setRequerido(Constantes.SI);
			} else {
				p1.setRequerido(Constantes.NO);
			}
			p1.setNoAplica(Boolean.valueOf(String.valueOf(m[7])));
			p1.setJustificacion(m[9] == null ? null : String.valueOf(m[9]));
			p1.setFechaModificacion(String.valueOf(m[10]));
			p1.setCargado(Boolean.valueOf(String.valueOf(m[11])));
			String status = "";
			if (p1.isCargado() && !p1.isNoAplica()) {
				status = Constantes.CARGADO;
			} else if (!p1.isCargado() && !p1.isNoAplica()) {
				status = Constantes.PENDIENTE;
			} else if (p1.isNoAplica()) {
				status = Constantes.NO_APLICA;
			}
			p1.setEstatus(status);
			if (UtilControlDocumental.hasPdfExtension(String.valueOf(m[12]))) {
				p1.setEsPdf(true);
			}
			p1.setIdReferencia(Long.parseLong(String.valueOf(m[13])));
			p1.setIdentificador(Long.parseLong(String.valueOf(m[14])));
			if (p1.getEstatus().equals(Constantes.CARGADO)) {
				StringBuilder s = new StringBuilder();
				s.append("/");
				s.append(Constantes.PATH_SERVICIO_CONTROL_DOC);
				s.append("/");
				s.append(String.valueOf(m[14]));
				s.append("/");
				s.append(String.valueOf(m[13]));
				p1.setPathServicio(s.toString());
			}
			p1.setRutaSatCloud(String.valueOf(m[12]));
			return p1;
		}).toList();
		this.number = page.getNumber();
	    this.size = page.getSize();
	}

    @Override
    public <U> Page<U> map(Function<? super ProyectoDocumental, ? extends U> converter) {
        return null;
    }

    @Override
    public Iterator<ProyectoDocumental> iterator() {
        return content.iterator();
    }
}