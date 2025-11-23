package com.sisecofi.proveedores.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proveedores.dto.ProveedoGeneralDto;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.service.ReporteGeneralProveedoresService;
import com.sisecofi.proveedores.util.Constantes;
import com.sisecofi.proveedores.util.consumer.ReporteGeneralProveedoresConsumer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReporteGeneralProveedoresServiceImpl implements ReporteGeneralProveedoresService {

    private final ReporteGeneralProveedoresConsumer reporteGeneralProveedoresConsumer;
    private final ProveedorRepository proveedorRepository;
    private final PistaService pistaService;

    @Override
    public byte[] obtenerReporteGeneralProveedores(Integer idGiroEmpresarial, Integer idTituloServicio,
            Integer idCatResultadoDictamen) {

        // Cargar todos los registros
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Object[]> proveedoresPage = proveedorRepository.obtenerProveedoresConTituloAgrupado(idGiroEmpresarial,
                idTituloServicio, idCatResultadoDictamen, pageable);
        List<ProveedoGeneralDto> lista = proveedoresPage.getContent().stream().map(this::convertirProveedorGeneralDto)
                .toList();

        reporteGeneralProveedoresConsumer.inializar("Reporte Proveedor General");
        reporteGeneralProveedoresConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_PROVEEDOR_GENERAL);
        lista.forEach(reporteGeneralProveedoresConsumer);

        registrarPistaProveedores(lista);

        return reporteGeneralProveedoresConsumer.cerrarBytes();

    }

    private ProveedoGeneralDto convertirProveedorGeneralDto(Object[] data) {
        ProveedoGeneralDto dto = new ProveedoGeneralDto();
        dto.setIdProveedor((Long) data[0]);
        dto.setNombreProveedor((String) data[1]);
        dto.setNombreComercial((String) data[2]);
        dto.setIdAgs((String) data[3]);
        dto.setGiroDeLaEmpresa((String) data[4]);
        dto.setRfc((String) data[5]);
        dto.setRepresentanteLegal((String) data[6]);
        dto.setTituloDeServicio((String) data[7]);
        dto.setVigencia((String) data[8]);
        dto.setColorVigencia((String) data[9]);
        dto.setVencimientoTitulo((Date) data[10]);
        dto.setEstatus((Boolean) data[11]);
        dto.setCumpleDictamen((String) data[12]);
        return dto;
    }

    @Transactional
    private void registrarPistaProveedores(List<ProveedoGeneralDto> listaProveedores) {
        listaProveedores.forEach(proveedor -> {

            StringBuilder builder = new StringBuilder();
            builder.append("Id proveedor: ").append(proveedor.getIdProveedor()).append(" | ")
                    .append("Nombre Proveedor: ").append(proveedor.getNombreProveedor()).append("");

            pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(),
                    TipoMovPista.IMPRIME_REGISTRO.getId(),
                    TipoSeccionPista.PROVEEDOR_DATOS_GENERALES.getIdSeccionPista(),
                    builder.toString(),
                    Optional.empty());
        });
    }

}
