package com.sisecofi.reportedocumental.dto.financiero.pages;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaSeguimientoLineaServicioDTO;
import com.sisecofi.reportedocumental.dto.financiero.mappers.DetalleGeneralSeguimientoLineaServicioMapper;
import com.sisecofi.reportedocumental.dto.financiero.mappers.DictaminadaPagadaDTO;
import com.sisecofi.reportedocumental.dto.financiero.mappers.PlaneadaEstimadaDTO;
import com.sisecofi.reportedocumental.dto.financiero.mappers.ReporteSeguimientoLineaServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class PageSeguimientoLineaServicio extends PageReporteFinancieroBase implements Page<ReporteSeguimientoLineaServicio> {
    private List<ReporteSeguimientoLineaServicio> content;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int number;
    private int numberOfElements;
    private boolean last;

    public PageSeguimientoLineaServicio(ConsultaSeguimientoLineaServicioDTO dto, PageGeneric page) {
        initialize(dto, page);
    }

    private void initialize(ConsultaSeguimientoLineaServicioDTO dto, PageGeneric page) {
        if (dto.getIdConvenioColaboracion() == null) {
            dto.setIdConvenioColaboracion(2);
        }

        if (dto.getListaIdServicioContrato() == null || dto.getListaIdServicioContrato().isEmpty()) {
            dto.setListaIdServicioContrato(new ArrayList<>());
            dto.getListaIdEstatusVolumetria().add(1);
        }

        setContent(page.getContent().stream().map(item -> {
            ReporteSeguimientoLineaServicio reporte = new ReporteSeguimientoLineaServicio();
            int indice = 0;

            DetalleGeneralSeguimientoLineaServicioMapper detalleGeneral = new DetalleGeneralSeguimientoLineaServicioMapper();
            detalleGeneral.setNombreCortoProyecto((String) item[indice++]);
            detalleGeneral.setNombreCortoContrato((String) item[indice++]);
            detalleGeneral.setNumeroContrato((String) item[indice++]);
            detalleGeneral.setEstatusVolumetria((String) item[indice++]);
            detalleGeneral.setNumeroConsecutivoConceptoServicio(((Integer) item[indice++]).longValue());
            detalleGeneral.setGrupoServicio((String) item[indice++]);
            detalleGeneral.setConceptoServicio((String) item[indice++]);
            detalleGeneral.setTipoConsumo((String) item[indice++]);
            detalleGeneral.setTipoUnidad((String) item[indice++]);
            detalleGeneral.setPrecioUnitario(getMontoConFormato(item[indice++]));
            detalleGeneral.setAplicaIva((String) item[indice++]);
            detalleGeneral.setAplicaIeps((String) item[indice++]);
            detalleGeneral.setCantidadServiciosMinima((BigDecimal) item[indice++]);
            detalleGeneral.setCantidadServiciosMaxima((BigDecimal) item[indice++]);
            detalleGeneral.setCantidadServiciosMaximaUltimoCM((BigDecimal) item[indice++]);

            List<PlaneadaEstimadaDTO> listaPlaneada = new ArrayList<>();
            List<PlaneadaEstimadaDTO> listaEstimada = new ArrayList<>();
            List<DictaminadaPagadaDTO> listaDictaminada = new ArrayList<>();
            List<DictaminadaPagadaDTO> listaPagada = new ArrayList<>();

            if (dto.getPeriodoInicio() != null && dto.getPeriodoFin() != null) {
                indice = setPlaneadoEstimado(dto, item, getCaledario(dto.getPeriodoInicio()), getCaledario(dto.getPeriodoFin()), listaPlaneada, 2, indice);
                indice = setPlaneadoEstimado(dto, item, getCaledario(dto.getPeriodoInicio()), getCaledario(dto.getPeriodoFin()), listaEstimada, 3, indice);
                indice = setDictaminadoPagado(dto, item, getCaledario(dto.getPeriodoInicio()), getCaledario(dto.getPeriodoFin()), listaDictaminada, 4, indice);
                setDictaminadoPagado(dto, item, getCaledario(dto.getPeriodoInicio()), getCaledario(dto.getPeriodoFin()), listaPagada, 5, indice);
            }

            reporte.setDetalleGeneral(detalleGeneral);
            reporte.setPlaneada(listaPlaneada);
            reporte.setEstimada(listaEstimada);
            reporte.setDictaminada(listaDictaminada);
            reporte.setPagada(listaPagada);

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


    private int setPlaneadoEstimado(ConsultaSeguimientoLineaServicioDTO dto, Object[] item, Calendar fechaInicio, Calendar fechaFin, List<PlaneadaEstimadaDTO> lista, int idEstusVolumetria, int indice) {
        do {
            String[] mesAnio = getSimpleDateFormat().format(fechaInicio.getTime()).split(" ");
            String mes = mesAnio[0].substring(0, 1).toUpperCase() + mesAnio[0].substring(1).toLowerCase();
            String anio = mesAnio[1];

            // 1 Todos, 2 Planeada, 3 Estimada, 4 Dictaminada, 5 Pagada
            PlaneadaEstimadaDTO planeadaDTO = new PlaneadaEstimadaDTO();

            planeadaDTO.setAnio(anio);
            planeadaDTO.setMes(mes);

            if (dto.getListaIdEstatusVolumetria().contains(1) || dto.getListaIdEstatusVolumetria().contains(idEstusVolumetria)) {
                if (dto.isVolumetria()) {
                    planeadaDTO.setCantidadServicios(parseCantidadServicios(item[indice++]));
                }

                if (dto.isMonto()) {
                    planeadaDTO.setMonto(getMontoConFormato(item[indice++]));
                }
            }

            lista.add(planeadaDTO);

            fechaInicio.add(Calendar.MONTH, 1);
        } while (fechaInicio.before(fechaFin) || fechaInicio.equals(fechaFin));

        return indice;
    }

    @SuppressWarnings("java:S3776")
    private int setDictaminadoPagado(ConsultaSeguimientoLineaServicioDTO dto, Object[] item, Calendar fechaInicio, Calendar fechaFin, List<DictaminadaPagadaDTO> lista, int idEstusVolumetria, int indice) {
        do {
            String[] mesAnio = getSimpleDateFormat().format(fechaInicio.getTime()).split(" ");
            String mes = mesAnio[0].substring(0, 1).toUpperCase() + mesAnio[0].substring(1).toLowerCase();
            String anio = mesAnio[1];

            // 1 Todos, 2 Planeada, 3 Estimada, 4 Dictaminada, 5 Pagada
            DictaminadaPagadaDTO dictaminadaPagadaDTO = new DictaminadaPagadaDTO();

            dictaminadaPagadaDTO.setAnio(anio);
            dictaminadaPagadaDTO.setMes(mes);

            if (dto.getListaIdEstatusVolumetria().contains(1) || dto.getListaIdEstatusVolumetria().contains(idEstusVolumetria)) {
                if (dto.isVolumetria()) {
                    dictaminadaPagadaDTO.setCantidadServiciosSat((BigDecimal) item[indice++]);

                    if (dto.getIdConvenioColaboracion() == 1) {
                        dictaminadaPagadaDTO.setCantidadServiciosCc((BigDecimal) item[indice++]);
                    }
                }

                if (dto.isMonto()) {
                    dictaminadaPagadaDTO.setMontoSat(getMontoConFormato(item[indice++]));

                    if (dto.getIdConvenioColaboracion() == 1) {
                        dictaminadaPagadaDTO.setMontoCc(getMontoConFormato(item[indice++]));
                    }
                }
            }

            lista.add(dictaminadaPagadaDTO);

            fechaInicio.add(Calendar.MONTH, 1);
        } while (fechaInicio.before(fechaFin) || fechaInicio.equals(fechaFin));

        return indice;
    }

    private SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("es", "MX"));
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf;
    }

    private Calendar getCaledario(LocalDate periodoInicio) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, periodoInicio.getYear());
        calendar.set(Calendar.MONTH, periodoInicio.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar;
    }

    private BigDecimal parseCantidadServicios(Object valor) {
        if (valor instanceof Double valorC) {
            return BigDecimal.valueOf(valorC);
        } else if (valor instanceof BigDecimal valorC) {
            return valorC;
        } else {
            throw new IllegalArgumentException("El tipo del valor no es compatible: " + valor.getClass().getName());
        }
    }

    public void setContent(List<ReporteSeguimientoLineaServicio> content) {
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
    public List<ReporteSeguimientoLineaServicio> getContent() {
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
    public Iterator<ReporteSeguimientoLineaServicio> iterator() {
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
    public <U> Page<U> map(Function<? super ReporteSeguimientoLineaServicio, ? extends U> converter) {
        return null;
    }
}