package com.sisecofi.reportedocumental.dto.financiero.pages;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoDictamenDTO;
import com.sisecofi.reportedocumental.dto.financiero.mappers.DetalleEstatusDictamenMapper;
import com.sisecofi.reportedocumental.dto.financiero.mappers.ReporteSeguimientoDictamen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageReporteSeguimientoDictamen extends PageReporteFinancieroBase implements Page<ReporteSeguimientoDictamen> {
    private List<ReporteSeguimientoDictamen> content;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int number;
    private int numberOfElements;
    private boolean last;

    public PageReporteSeguimientoDictamen(ConsultaSeguimientoDictamenDTO dto, PageGeneric page) {
        initialize(dto, page);
    }

    private void initialize(ConsultaSeguimientoDictamenDTO dto, PageGeneric page) {
        setContent(page.getContent().stream().map(item -> {
            ReporteSeguimientoDictamen reporteSeguimientoDictamen = new ReporteSeguimientoDictamen();
            int indice = 0;

            reporteSeguimientoDictamen.setNombreCortoProyecto((String) item[indice++]);
            reporteSeguimientoDictamen.setNombreCortoContrato((String) item[indice++]);
            reporteSeguimientoDictamen.setVerificador((String) item[indice++]);
            reporteSeguimientoDictamen.setPeriodoControl((String) item[indice++]);

            if (dto.getListaIdEstatusDictamen() != null && !dto.getListaIdEstatusDictamen().isEmpty()) {
                indice = setInicial(dto, item, reporteSeguimientoDictamen, indice);
                indice = setDictaminado(dto, item, reporteSeguimientoDictamen, indice);
                indice = setProforma(dto, item, reporteSeguimientoDictamen, indice);
                indice = setFacturado(dto, item, reporteSeguimientoDictamen, indice);
                indice = setSolicitudPago(dto, item, reporteSeguimientoDictamen, indice);
                indice = setPagado(dto, item, reporteSeguimientoDictamen, indice);
                setCancelado(dto, item, reporteSeguimientoDictamen, indice);
            }

            return reporteSeguimientoDictamen;
        }).toList());

        setFirst(page.isFirst());
        setLast(page.isLast());
        setNumber(page.getNumber());
        setNumberOfElements(page.getNumberOfElements());
        setSize(page.getSize());
        setTotalElements(page.getTotalElements());
        setTotalPages(page.getTotalPages());
    }


    private int setInicial(ConsultaSeguimientoDictamenDTO dto, Object[] item, ReporteSeguimientoDictamen reporteSeguimientoDictamen, int indice) {
        if (dto.getListaIdEstatusDictamen().contains(0) || dto.getListaIdEstatusDictamen().contains(1)) {
            reporteSeguimientoDictamen.setInicial(
                    new DetalleEstatusDictamenMapper(
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++])
                    )
            );
        }
        return indice;
    }

    private int setDictaminado(ConsultaSeguimientoDictamenDTO dto, Object[] item, ReporteSeguimientoDictamen reporteSeguimientoDictamen, int indice) {
        if (dto.getListaIdEstatusDictamen().contains(0) || dto.getListaIdEstatusDictamen().contains(2)) {
            reporteSeguimientoDictamen.setDictaminado(
                    new DetalleEstatusDictamenMapper(
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++])
                    )
            );
        }
        return indice;
    }

    private int setProforma(ConsultaSeguimientoDictamenDTO dto, Object[] item, ReporteSeguimientoDictamen reporteSeguimientoDictamen, int indice) {
        if (dto.getListaIdEstatusDictamen().contains(0) || dto.getListaIdEstatusDictamen().contains(3)) {
            reporteSeguimientoDictamen.setProforma(
                    new DetalleEstatusDictamenMapper(
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++])
                    )
            );
        }
        return indice;
    }

    private int setFacturado(ConsultaSeguimientoDictamenDTO dto, Object[] item, ReporteSeguimientoDictamen reporteSeguimientoDictamen, int indice) {
        if (dto.getListaIdEstatusDictamen().contains(0) || dto.getListaIdEstatusDictamen().contains(4)) {
            reporteSeguimientoDictamen.setFacturado(
                    new DetalleEstatusDictamenMapper(
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++])
                    )
            );
        }
        return indice;
    }

    private int setSolicitudPago(ConsultaSeguimientoDictamenDTO dto, Object[] item, ReporteSeguimientoDictamen reporteSeguimientoDictamen, int indice) {
        if (dto.getListaIdEstatusDictamen().contains(0) || dto.getListaIdEstatusDictamen().contains(5)) {
            reporteSeguimientoDictamen.setSolicitudPago(
                    new DetalleEstatusDictamenMapper(
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++])
                    )
            );
        }
        return indice;
    }

    private int setPagado(ConsultaSeguimientoDictamenDTO dto, Object[] item, ReporteSeguimientoDictamen reporteSeguimientoDictamen, int indice) {
        if (dto.getListaIdEstatusDictamen().contains(0) || dto.getListaIdEstatusDictamen().contains(6)) {
            reporteSeguimientoDictamen.setPagado(
                    new DetalleEstatusDictamenMapper(
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++])
                    )
            );
        }
        return indice;
    }

    private void setCancelado(ConsultaSeguimientoDictamenDTO dto, Object[] item, ReporteSeguimientoDictamen reporteSeguimientoDictamen, int indice) {
        if (dto.getListaIdEstatusDictamen().contains(0) || dto.getListaIdEstatusDictamen().contains(7)) {
            reporteSeguimientoDictamen.setCancelado(
                    new DetalleEstatusDictamenMapper(
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice++]),
                            getMontoConFormato(item[indice])
                    )
            );
        }
    }

    public void setContent(List<ReporteSeguimientoDictamen> content) {
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
    public List<ReporteSeguimientoDictamen> getContent() {
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
    public Iterator<ReporteSeguimientoDictamen> iterator() {
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
    public <U> Page<U> map(Function<? super ReporteSeguimientoDictamen, ? extends U> converter) {
        return null;
    }
}
