package com.sisecofi.reportedocumental.dto.financiero.pages;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaTipoDTO;
import com.sisecofi.reportedocumental.dto.financiero.mappers.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageReporteTipo extends PageReporteFinancieroBase implements Page<ReporteTipoMapper> {
    private List<ReporteTipoMapper> content;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int number;
    private int numberOfElements;
    private boolean last;

    public PageReporteTipo(ConsultaTipoDTO dto, PageGeneric page) {
        initialize(dto, page);
    }

    private void initialize(ConsultaTipoDTO dto, PageGeneric page) {
        setContent(page.getContent().stream().map(item -> {
            ReporteTipoMapper reporteTipo = new ReporteTipoMapper();
            int indice = 0;

            DetalleGeneralTipoMapper detalleGeneral = new DetalleGeneralTipoMapper();
            detalleGeneral.setNombreCortoProyecto((String) item[indice++]);
            detalleGeneral.setNombreCortoContrato((String) item[indice++]);
            detalleGeneral.setNumeroContrato((String) item[indice++]);
            detalleGeneral.setProveedor((String) item[indice++]);

            if (dto.getTipos().contains("Facturas") || dto.getTipos().contains("Deducciones") || dto.getTipos().contains("Penalizaciones")) {
                detalleGeneral.setPeriodoInicio((String) item[indice++]);
                detalleGeneral.setPeriodoFin((String) item[indice++]);
                detalleGeneral.setPeriodoControl((String) item[indice++]);
                detalleGeneral.setDescripcion((String) item[indice++]);
            }

            reporteTipo.setDetalleGeneral(detalleGeneral);

            indice = setFacturas(dto, item, indice, reporteTipo);

            indice = setDeducciones(dto, item, indice, reporteTipo);

            indice = setPenalizaciones(dto, item, indice, reporteTipo);

            setReintegros(dto, item, indice, reporteTipo);

            return reporteTipo;
        }).toList());

        setFirst(page.isFirst());
        setLast(page.isLast());
        setNumber(page.getNumber());
        setNumberOfElements(page.getNumberOfElements());
        setSize(page.getSize());
        setTotalElements(page.getTotalElements());
        setTotalPages(page.getTotalPages());
    }


    private void setReintegros(ConsultaTipoDTO dto, Object[] item, int indice, ReporteTipoMapper reporteTipo) {
        if (dto.getTipos().contains("Reintegros")) {
            DetalleReintegroMapper detalleReintegro = new DetalleReintegroMapper();
            detalleReintegro.setTipo((String) item[indice++]);
            detalleReintegro.setImporte(getMontoConFormato(item[indice++]));
            detalleReintegro.setIntereses(getMontoConFormato(item[indice++]));
            detalleReintegro.setTotalSat(getMontoConFormato(item[indice++]));
            detalleReintegro.setTotalCc(getMontoConFormato(item[indice++]));
            detalleReintegro.setFechaReintegro((String) item[indice]);

            reporteTipo.setDetalleReintegro(detalleReintegro);
        }
    }

    private int setPenalizaciones(ConsultaTipoDTO dto, Object[] item, int indice, ReporteTipoMapper reporteTipo) {
        if (dto.getTipos().contains("Penalizaciones")) {
            DetallePenalizacionMapper detallePenalizacion = new DetallePenalizacionMapper();

            detallePenalizacion.setMontoDolaresSat(getMontoConFormato(item[indice++]));
            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                detallePenalizacion.setMontoDolaresCc(getMontoConFormato(item[indice++]));
            }

            detallePenalizacion.setMontoPesosSat(getMontoConFormato(item[indice++]));
            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                detallePenalizacion.setMontoPesosCc(getMontoConFormato(item[indice++]));
            }

            reporteTipo.setDetallePenalizacion(detallePenalizacion);
        }
        return indice;
    }

    private int setDeducciones(ConsultaTipoDTO dto, Object[] item, int indice, ReporteTipoMapper reporteTipo) {
        if (dto.getTipos().contains("Deducciones")) {
            DetalleDeduccionMapper detalleDeduccion = new DetalleDeduccionMapper();

            detalleDeduccion.setMontoDolaresSat(getMontoConFormato(item[indice++]));
            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                detalleDeduccion.setMontoDolaresCc(getMontoConFormato(item[indice++]));
            }

            detalleDeduccion.setMontoPesosSat(getMontoConFormato(item[indice++]));
            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                detalleDeduccion.setMontoPesosCc(getMontoConFormato(item[indice++]));
            }

            reporteTipo.setDetalleDeduccion(detalleDeduccion);
        }
        return indice;
    }

    private int setFacturas(ConsultaTipoDTO dto, Object[] item, int indice, ReporteTipoMapper reporteTipo) {
        if (dto.getTipos().contains("Facturas")) {
            DetalleFacturaMapper detalleFactura = new DetalleFacturaMapper();
            detalleFactura.setFolioFactura((String) item[indice++]);
            detalleFactura.setComprobanteFiscal((String) item[indice++]);
            detalleFactura.setFechaFacturacion((String) item[indice++]);
            detalleFactura.setFechaPago((String) item[indice++]);
            detalleFactura.setMoneda((String) item[indice++]);
            detalleFactura.setTipoCambio(getMontoConFormato(item[indice++]));

            detalleFactura.setMontoDolaresSat(getMontoConFormato(item[indice++]));
            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                detalleFactura.setMontoDolaresCc(getMontoConFormato(item[indice++]));
            }

            detalleFactura.setMontoPesosSat(getMontoConFormato(item[indice++]));
            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                detalleFactura.setMontoPesosCc(getMontoConFormato(item[indice++]));
            }

            detalleFactura.setOtroImpuestosSat(getMontoConFormato(item[indice++]));
            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                detalleFactura.setOtroImpuestosCc(getMontoConFormato(item[indice++]));
            }

            detalleFactura.setFolioFichaPagoSat((String) item[indice++]);
            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                detalleFactura.setFolioFichaPagoCc((String) item[indice++]);
            }

            detalleFactura.setComentarios((String) item[indice++]);

            reporteTipo.setDetalleFactura(detalleFactura);
        }
        return indice;
    }

    public void setContent(List<ReporteTipoMapper> content) {
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
    public List<ReporteTipoMapper> getContent() {
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
    public Iterator<ReporteTipoMapper> iterator() {
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
    public <U> Page<U> map(Function<? super ReporteTipoMapper, ? extends U> converter) {
        return null;
    }

}
