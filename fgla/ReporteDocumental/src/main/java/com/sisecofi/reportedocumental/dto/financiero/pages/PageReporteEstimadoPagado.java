package com.sisecofi.reportedocumental.dto.financiero.pages;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstimadoPagadoDTO;
import com.sisecofi.reportedocumental.dto.financiero.mappers.ReporteEstimadoPagadoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageReporteEstimadoPagado extends PageReporteFinancieroBase implements Page<ReporteEstimadoPagadoMapper> {
    private List<ReporteEstimadoPagadoMapper> content;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int number;
    private int numberOfElements;
    private boolean last;

    public PageReporteEstimadoPagado(ConsultaEstimadoPagadoDTO dto, PageGeneric page) {
        initialize(dto, page);
    }

    private void initialize(ConsultaEstimadoPagadoDTO dto, PageGeneric page) {
        if (dto.getIdConvenioColaboracion() == null) {
            dto.setIdConvenioColaboracion(2);
        }

        setContent(page.getContent().stream().map(item -> {
            ReporteEstimadoPagadoMapper reporte = new ReporteEstimadoPagadoMapper();
            int ultimoIndice = 0;

            reporte.setNombreCortoProyecto((String) item[ultimoIndice++]);
            reporte.setNombreCortoContrato((String) item[ultimoIndice++]);
            reporte.setProveedor((String) item[ultimoIndice++]);
            reporte.setNumeroContrato((String) item[ultimoIndice++]);
            reporte.setVigente((String) item[ultimoIndice++]);
            reporte.setPeriodoControl((String) item[ultimoIndice++]);
            reporte.setEstimado(getMontoConFormato(item[ultimoIndice++]));

            ultimoIndice = setDictaminado(dto, item, reporte, ultimoIndice);
            ultimoIndice = setDeduccion(dto, item, reporte, ultimoIndice);
            ultimoIndice = setNotaCredito(dto, item, reporte, ultimoIndice);
            ultimoIndice = setSubtotal(dto, item, reporte, ultimoIndice);
            ultimoIndice = setIva(dto, item, reporte, ultimoIndice);
            ultimoIndice = setIeps(dto, item, reporte, ultimoIndice);
            ultimoIndice = setOtrosImpuestos(dto, item, reporte, ultimoIndice);

            setTotalPagado(dto, item, reporte, ultimoIndice);

            return reporte;
        }).toList());

        setFirst(page.isFirst());
        setLast(page.isLast());
        setNumber(page.getNumber());
        setNumberOfElements(page.getNumberOfElements());
        setSize(page.getSize());
        setTotalElements(page.getTotalElements());
        setTotalPages(page.getTotalPages());
    }


    private void setTotalPagado(ConsultaEstimadoPagadoDTO dto, Object[] item, ReporteEstimadoPagadoMapper reporte, int ultimoIndice) {
        reporte.setTotalPagadoSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            reporte.setTotalPagadoCc(getMontoConFormato(item[ultimoIndice]));
        }
    }

    private int setOtrosImpuestos(ConsultaEstimadoPagadoDTO dto, Object[] item, ReporteEstimadoPagadoMapper reporte, int ultimoIndice) {
        reporte.setOtrosImpuestosSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            reporte.setOtrosImpuestosCc(getMontoConFormato(item[ultimoIndice++]));
        }
        return ultimoIndice;
    }

    private int setIeps(ConsultaEstimadoPagadoDTO dto, Object[] item, ReporteEstimadoPagadoMapper reporte, int ultimoIndice) {
        reporte.setIepsSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            reporte.setIepsCc(getMontoConFormato(item[ultimoIndice++]));
        }
        return ultimoIndice;
    }

    private int setIva(ConsultaEstimadoPagadoDTO dto, Object[] item, ReporteEstimadoPagadoMapper reporte, int ultimoIndice) {
        reporte.setIvaSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            reporte.setIvaCc(getMontoConFormato(item[ultimoIndice++]));
        }
        return ultimoIndice;
    }

    private int setSubtotal(ConsultaEstimadoPagadoDTO dto, Object[] item, ReporteEstimadoPagadoMapper reporte, int ultimoIndice) {
        reporte.setSubtotalSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            reporte.setSubtotalCc(getMontoConFormato(item[ultimoIndice++]));
        }
        return ultimoIndice;
    }

    private int setNotaCredito(ConsultaEstimadoPagadoDTO dto, Object[] item, ReporteEstimadoPagadoMapper reporte, int ultimoIndice) {
        reporte.setNotaCreditoSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            reporte.setNotaCreditoCc(getMontoConFormato(item[ultimoIndice++]));
        }
        return ultimoIndice;
    }

    private int setDeduccion(ConsultaEstimadoPagadoDTO dto, Object[] item, ReporteEstimadoPagadoMapper reporte, int ultimoIndice) {
        reporte.setDeduccionSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            reporte.setDeduccionCc(getMontoConFormato(item[ultimoIndice++]));
        }
        return ultimoIndice;
    }

    private int setDictaminado(ConsultaEstimadoPagadoDTO dto, Object[] item, ReporteEstimadoPagadoMapper reporte, int ultimoIndice) {
        reporte.setDictaminadoSat(getMontoConFormato(item[ultimoIndice++]));
        if (dto.getIdConvenioColaboracion() == 1) {
            reporte.setDictaminadoCc(getMontoConFormato(item[ultimoIndice++]));
        }
        return ultimoIndice;
    }

    public void setContent(List<ReporteEstimadoPagadoMapper> content) {
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
    public List<ReporteEstimadoPagadoMapper> getContent() {
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
    public Iterator<ReporteEstimadoPagadoMapper> iterator() {
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
    public <U> Page<U> map(Function<? super ReporteEstimadoPagadoMapper, ? extends U> converter) {
        return null;
    }
}
