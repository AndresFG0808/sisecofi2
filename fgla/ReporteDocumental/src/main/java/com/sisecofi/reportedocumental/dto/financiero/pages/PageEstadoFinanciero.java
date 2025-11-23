package com.sisecofi.reportedocumental.dto.financiero.pages;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstadoFinancieroDTO;
import com.sisecofi.reportedocumental.dto.financiero.mappers.ReporteEstadoFinancieroMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageEstadoFinanciero extends PageReporteFinancieroBase implements Page<ReporteEstadoFinancieroMapper> {
    private List<ReporteEstadoFinancieroMapper> content;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int number;
    private int numberOfElements;
    private boolean last;

    public PageEstadoFinanciero(ConsultaEstadoFinancieroDTO dto, PageGeneric page) {
        initialize(dto, page);
    }

    private void initialize(ConsultaEstadoFinancieroDTO dto, PageGeneric page) {
        setContent(page.getContent().stream().map(item -> {
            ReporteEstadoFinancieroMapper reporte = new ReporteEstadoFinancieroMapper();
            int indice = 0;

            reporte.setNombreCortoProyecto((String) item[indice++]);
            reporte.setNombreCortoContrato((String) item[indice++]);
            reporte.setDominio((String) item[indice++]);
            reporte.setProveedor((String) item[indice++]);
            reporte.setVigente((String) item[indice++]);
            reporte.setNumeroContrato((String) item[indice++]);
            reporte.setPeriodoControl((String) item[indice++]);
            reporte.setMoneda((String) item[indice++]);

            reporte.setTipoCambioReferencialEstimado(getMontoConFormato(item[indice++]));
            reporte.setMontoDolaresEstimado(getMontoConFormato(item[indice++]));
            reporte.setMontoPesosEstimado(getMontoConFormato(item[indice++]));

            reporte.setTipoCambioReferencialDictaminado(getMontoConFormato(item[indice++]));
            reporte.setMontoDolaresDictaminado(getMontoConFormato(item[indice++]));
            reporte.setMontoPesosDictaminado(getMontoConFormato(item[indice++]));

            reporte.setTipoCambioReal(getMontoConFormato(item[indice++]));
            reporte.setMontoDolares(getMontoConFormato(item[indice++]));
            reporte.setMontoPesos(getMontoConFormato(item[indice++]));

            reporte.setDictamenSat(getMontoConFormato(item[indice++]));

            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                reporte.setDictamenCc(getMontoConFormato(item[indice++]));
            }

            reporte.setPagadoSat(getMontoConFormato(item[indice++]));

            if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 1) {
                reporte.setPagadoCc(getMontoConFormato(item[indice++]));
            }

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


    public void setContent(List<ReporteEstadoFinancieroMapper> content) {
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
    public List<ReporteEstadoFinancieroMapper> getContent() {
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
    public Iterator<ReporteEstadoFinancieroMapper> iterator() {
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
    public <U> Page<U> map(Function<? super ReporteEstadoFinancieroMapper, ? extends U> converter) {
        return null;
    }
}