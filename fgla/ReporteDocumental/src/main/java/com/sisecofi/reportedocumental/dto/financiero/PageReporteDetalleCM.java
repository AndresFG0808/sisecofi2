package com.sisecofi.reportedocumental.dto.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.pages.PageReporteFinancieroBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageReporteDetalleCM extends PageReporteFinancieroBase implements Page<DetalleCM> {
    private List<DetalleCM> content;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int number;
    private int numberOfElements;
    private boolean last;

    public PageReporteDetalleCM(PageGeneric page) {
        initializePage(page);
    }

    private void initializePage(PageGeneric page) {
        setContent(page.getContent().stream().map(item -> {
            DetalleCM detalleCM = new DetalleCM();
            int indice = 0;

            detalleCM.setNumeroConvenio((String) item[indice++]);
            detalleCM.setTipoConvenio((String) item[indice++]);
            detalleCM.setFechaFirma((String) item[indice++]);
            detalleCM.setFechaFinServicio((String) item[indice++]);
            detalleCM.setFechaFinContratoCm((String) item[indice++]);
            detalleCM.setMontoMaximoContratoCmConImpuestos(getMontoConFormato(item[indice++]));
            detalleCM.setMontoMaximoContratoCmSinImpuestos(getMontoConFormato(item[indice++]));
            detalleCM.setMontoPesos(getMontoConFormato(item[indice++]));
            detalleCM.setComentarios((String) item[indice]);

            return detalleCM;
        }).toList());

        setFirst(page.isFirst());
        setLast(page.isLast());
        setNumber(page.getNumber());
        setNumberOfElements(page.getNumberOfElements());
        setSize(page.getSize());
        setTotalElements(page.getTotalElements());
        setTotalPages(page.getTotalPages());
    }


    public void setContent(List<DetalleCM> content) {
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
    public List<DetalleCM> getContent() {
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
    public Iterator<DetalleCM> iterator() {
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
    public <U> Page<U> map(Function<? super DetalleCM, ? extends U> converter) {
        return null;
    }

}