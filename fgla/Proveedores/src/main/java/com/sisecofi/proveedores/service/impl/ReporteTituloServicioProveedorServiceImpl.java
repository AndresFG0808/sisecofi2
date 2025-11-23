package com.sisecofi.proveedores.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.service.ReporteTituloServicioProveedorService;
import com.sisecofi.proveedores.util.Constantes;
import com.sisecofi.proveedores.util.consumer.ReporteTituloServicioProveedorConsumer;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.CatalogoException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteTituloServicioProveedorServiceImpl implements ReporteTituloServicioProveedorService {

    private final ReporteTituloServicioProveedorConsumer reporteTituloServicioProveedorConsumer;
    private final TituloServicioProveedorServiceImpl tituloServicioProveedorServiceImpl;
    private final PistaService pistaService;
    private final ProveedorRepository proveedorRepository;

    @Override
    public byte[] obtenerReporteTituloServicioProveedor(Long idProveedor) {

        List<TituloServicioResponseDto> lista = tituloServicioProveedorServiceImpl
                .obtenerDatosReporteServicioPorProveedor(idProveedor);
        reporteTituloServicioProveedorConsumer.inializar("Reporte Titulos de Servicio Proveedores");
        reporteTituloServicioProveedorConsumer.agregarCabeceras(Constantes.TITULOS_SERVICIOS_PROVEEDOR);
        lista.stream().forEach(reporteTituloServicioProveedorConsumer);


        return reporteTituloServicioProveedorConsumer.cerrarBytes();

    }

    @Transactional
    private void registrarPistaTituloServicio(Long idProveedor) {
        Optional<ProveedorModel> proveedorOpt = proveedorRepository.findByIdProveedor(idProveedor);
        if (proveedorOpt.isPresent()) {
            ProveedorModel proveedor = proveedorOpt.get();

            StringBuilder builder = new StringBuilder();

            builder.append("Id Proveedor: ").append(proveedor.getIdProveedor()).append(" | ")
                    .append("Nombre Proveedor: ").append(proveedor.getNombreProveedor());

            pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(),
                    TipoMovPista.IMPRIME_REGISTRO.getId(),
                    TipoSeccionPista.PROVEEDOR_TITULO_SERVICIO.getIdSeccionPista(),
                    builder.toString(),
                    Optional.empty());
        } else {
            throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_LA_PISTA);
        }
    }

}
