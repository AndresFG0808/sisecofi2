package com.sisecofi.libreria.comunes.dto.dinamico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author ayuso2104@gmail.com
 */

public class PageGeneric implements Page<Object[]> {

    private List<String> etiquetas;
    private List<Agrupacion> grupoEtiquetas;
    private List<Object[]> content;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int number;
    private int numberOfElements;
    private boolean last;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Page> PageGeneric(T page, List<String> camposFront) {
        super();
        this.content = page.getContent(); 
        this.size = page.getSize();       
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.first = page.isFirst();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.last = page.isLast();
        this.etiquetas = camposFront;
    }

    public PageGeneric() {
        super();
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public List<Agrupacion> getGrupoEtiquetas() {
        return grupoEtiquetas;
    }

    public void setGrupoEtiquetas(List<Agrupacion> grupoEtiquetas) {
        this.grupoEtiquetas = grupoEtiquetas;
    }

    public void setContent(List<Object[]> content) {
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
    public List<Object[]> getContent() {
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
    public Iterator<Object[]> iterator() {
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
    public <U> Page<U> map(Function<? super Object[], ? extends U> converter) {
        return null;
    }

}
