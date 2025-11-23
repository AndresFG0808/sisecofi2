package com.sisecofi.proveedores.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proveedores.repository.DirectorioProveedorRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.service.ReporteService;
import com.sisecofi.proveedores.util.Constantes;
import com.sisecofi.proveedores.util.consumer.ReporteDirectorioConsumer;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.CatalogoException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteDirectorioConsumer reporteConsumer;
    private final PistaService pistaService;
    private final ProveedorRepository proveedorRepository;
    private final DirectorioProveedorRepository directorioProveedorRepository;

    @Override
    public byte[] obtenerReporteDirectorioContacto(Long idProveedor) {

        List<DirectorioProveedorModel> directorioProveedorModel = directorioProveedorRepository.findByEstatusTrueAndIdProveedor(idProveedor);
        
        reporteConsumer.inializar("Directorios Registrados");
        reporteConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_DIRECTORIO_CONTACTO);
        directorioProveedorModel.stream().forEach(reporteConsumer);

        registrarPistaDirectorioContacto(idProveedor);

        return reporteConsumer.cerrarBytes();

    }

    @Transactional
    private void registrarPistaDirectorioContacto(Long idProveedor) {
        Optional<ProveedorModel> proveedorOpt = proveedorRepository.findByIdProveedor(idProveedor);
        if (proveedorOpt.isPresent()) {
            ProveedorModel proveedor = proveedorOpt.get();
            
            StringBuilder builder = new StringBuilder();

            builder.append("ID Proveedor: ").append(proveedor.getIdProveedor()).append(" | ")
                    .append("Nombre Proveedor: ").append(proveedor.getNombreProveedor());

            pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(),
                    TipoMovPista.IMPRIME_REGISTRO.getId(),
                    TipoSeccionPista.PROVEEDOR_DIRECTORIO_CONTACTO.getIdSeccionPista(),
                    builder.toString(),
                    Optional.empty());
        } else {
            throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_LA_PISTA);
        }
    }

}
