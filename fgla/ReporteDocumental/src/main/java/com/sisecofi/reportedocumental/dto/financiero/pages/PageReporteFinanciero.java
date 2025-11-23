package com.sisecofi.reportedocumental.dto.financiero.pages;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaResumenDTO;
import com.sisecofi.reportedocumental.dto.financiero.mappers.DesgloceMapper;
import com.sisecofi.reportedocumental.dto.financiero.mappers.DetalleFinancieroMapper;
import com.sisecofi.reportedocumental.dto.financiero.mappers.DetalleGeneralResumenMapper;
import com.sisecofi.reportedocumental.dto.financiero.mappers.ReporteFinancieroMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageReporteFinanciero extends PageReporteFinancieroBase implements Page<ReporteFinancieroMapper> {
    private List<ReporteFinancieroMapper> content;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int number;
    private int numberOfElements;
    private boolean last;

    public PageReporteFinanciero(ConsultaResumenDTO dto, PageGeneric page) {
        initialize(dto, page);
    }

    private void initialize(ConsultaResumenDTO dto, PageGeneric page) {
        if (dto.getIdConvenioColaboracion() == null) {
            dto.setIdConvenioColaboracion(2);
        }

        setContent(page.getContent().stream().map(item -> {
            ReporteFinancieroMapper reporteFinanciero = new ReporteFinancieroMapper();
            int ultimoIndice = 0;

            reporteFinanciero.setNombreCortoProyecto((String) item[ultimoIndice++]);
            reporteFinanciero.setNombreCortoContrato((String) item[ultimoIndice++]);

            if (dto.isIncluirDatosGenerales()) {
                ultimoIndice = setDatosGenerales(item, ultimoIndice, reporteFinanciero);
            }

            if (dto.isIncluirDetalleFinanciero()) {
                setDetalleFinanciero(dto, item, ultimoIndice, reporteFinanciero);
            }

            return reporteFinanciero;
        }).toList());

        setFirst(page.isFirst());
        setLast(page.isLast());
        setNumber(page.getNumber());
        setNumberOfElements(page.getNumberOfElements());
        setSize(page.getSize());
        setTotalElements(page.getTotalElements());
        setTotalPages(page.getTotalPages());
    }


    private int setDatosGenerales(Object[] item, int ultimoIndice, ReporteFinancieroMapper reporteFinanciero) {
        DetalleGeneralResumenMapper detalleGeneralResumen = new DetalleGeneralResumenMapper();
        detalleGeneralResumen.setIdProyectoAgp((String) item[ultimoIndice++]);
        detalleGeneralResumen.setEstatusProyecto((String) item[ultimoIndice++]);
        detalleGeneralResumen.setIdContrato((Long) item[ultimoIndice++]);
        detalleGeneralResumen.setNumeroContrato((String) item[ultimoIndice++]);
        detalleGeneralResumen.setNombreContrato((String) item[ultimoIndice++]);
        detalleGeneralResumen.setObjetivoServicio((String) item[ultimoIndice++]);
        detalleGeneralResumen.setAlcanceServicio((String) item[ultimoIndice++]);
        detalleGeneralResumen.setContratoVigente((String) item[ultimoIndice++]);
        detalleGeneralResumen.setDominio((String) item[ultimoIndice++]);
        detalleGeneralResumen.setTipoContratacion((String) item[ultimoIndice++]);
        detalleGeneralResumen.setProveedor((String) item[ultimoIndice++]);
        detalleGeneralResumen.setFechaInicioContrato((String) item[ultimoIndice++]);
        detalleGeneralResumen.setFechaFinContrato((String) item[ultimoIndice++]);
        detalleGeneralResumen.setFechaFinUltimoCm((String) item[ultimoIndice++]);
        detalleGeneralResumen.setMinimoContratadoConImpuestosMxn(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMaximoContratadoConImpuestosMxn(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMaximoCmConImpuestosMxn(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMinimoContratadoSinImpuestosMxn(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMaximoContratadoSinImpuestosMxn(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMaximoCmSinImpuestosMxn(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMoneda((String) item[ultimoIndice++]);
        detalleGeneralResumen.setTipoCambio(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMinimoContratadoConImpuestos(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMaximoContratadoConImpuestos(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setMaximoCmConImpuestos(getMontoConFormato(item[ultimoIndice++]));
        detalleGeneralResumen.setAdministradorContrato((String) item[ultimoIndice++]);
        detalleGeneralResumen.setConveniosModificatorios((String) item[ultimoIndice++]);
        detalleGeneralResumen.setMesesRestantesContrato((BigDecimal) item[ultimoIndice++]);
        detalleGeneralResumen.setUltimaEstimacion(getPeriodoControlConFormatoFecha(item[ultimoIndice++]));
        detalleGeneralResumen.setUltimoDictamen(getPeriodoControlConFormatoFecha(item[ultimoIndice++]));
        detalleGeneralResumen.setUltimoPago(getPeriodoControlConFormatoFecha(item[ultimoIndice++]));
        reporteFinanciero.setDetalleGeneral(detalleGeneralResumen);
        return ultimoIndice;
    }

    private void setDetalleFinanciero(ConsultaResumenDTO dto, Object[] item, int ultimoIndice, ReporteFinancieroMapper reporteFinanciero) {
        DetalleFinancieroMapper detalleFinanciero = new DetalleFinancieroMapper();
        DesgloceMapper desgloce = new DesgloceMapper();

        detalleFinanciero.setDevengadoAntesDeduccionesSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setDevengadoAntesDeduccionesCc(getMontoConFormato(item[ultimoIndice++]));
        }

        detalleFinanciero.setPagadoAntesDeduccionesSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setPagadoAntesDeduccionesCc(getMontoConFormato(item[ultimoIndice++]));
        }

        detalleFinanciero.setDeduccionesSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setDeduccionesCc(getMontoConFormato(item[ultimoIndice++]));
        }

        detalleFinanciero.setIvaSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setIvaCc(getMontoConFormato(item[ultimoIndice++]));
        }

        detalleFinanciero.setIepsSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setIepsCc(getMontoConFormato(item[ultimoIndice++]));
        }

        detalleFinanciero.setOtrosImpuestosSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setOtrosImpuestosCc(getMontoConFormato(item[ultimoIndice++]));
        }

        detalleFinanciero.setPenalizacionesSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setPenalizacionesCc(getMontoConFormato(item[ultimoIndice++]));
        }

        detalleFinanciero.setReintegroSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setReintegroCc(getMontoConFormato(item[ultimoIndice++]));
        }

        detalleFinanciero.setPagadoSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            desgloce.setPagadoCc(getMontoConFormato(item[ultimoIndice]));
        }

        if (dto.getIdConvenioColaboracion() == 1) {
            detalleFinanciero.setDesgloceMapper(desgloce);
        }

        reporteFinanciero.setDetalleFinanciero(detalleFinanciero);
    }

    public void setContent(List<ReporteFinancieroMapper> content) {
        this.content = content;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getNumberOfElements() {
        return numberOfElements;
    }

    @Override
    public List<ReporteFinancieroMapper> getContent() {
        return content;
    }

    @Override
    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    @Override
    public Sort getSort() {
        return Sort.unsorted();
    }

    @Override
    public boolean isFirst() {
        return first;
    }

    @Override
    public boolean isLast() {
        return last;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Pageable nextPageable() {
        return null;
    }

    @Override
    public Pageable previousPageable() {
        return null;
    }

    @Override
    public Iterator<ReporteFinancieroMapper> iterator() {
        return content.iterator();
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public <U> Page<U> map(Function<? super ReporteFinancieroMapper, ? extends U> converter) {
        return null;
    }

}
