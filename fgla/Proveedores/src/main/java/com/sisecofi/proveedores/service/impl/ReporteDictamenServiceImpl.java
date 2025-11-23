package com.sisecofi.proveedores.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.service.ReporteDictamenService;
import com.sisecofi.proveedores.util.Constantes;
import com.sisecofi.proveedores.util.consumer.ReporteDictamenTecnicoConsumer;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.CatalogoException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReporteDictamenServiceImpl implements ReporteDictamenService {

  private final ReporteDictamenTecnicoConsumer reporteDictamenTecnicoConsumer;
  private final DictamenTecnicoServiceImpl dictamenServiceImpl;
  private final PistaService pistaService;
  private final ProveedorRepository proveedorRepository;

  @Override
  public byte[] obtenerReporteDictamenTecnico(Long idProveedor) {

    List<DictamenTecnicoResponseDto> lista = dictamenServiceImpl.obtenerReporteDictamenProveedor(idProveedor);
    reporteDictamenTecnicoConsumer.inializar("Reporte Dictamen Tecnico");
    reporteDictamenTecnicoConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_DICTAMEN_TECNICO);
    lista.stream().forEach(reporteDictamenTecnicoConsumer);

        registrarPistaDictamenTecnico(idProveedor);

    return reporteDictamenTecnicoConsumer.cerrarBytes();

  }

  @Transactional
    private void registrarPistaDictamenTecnico(Long idProveedor) {
        Optional<ProveedorModel> proveedorOpt = proveedorRepository.findByIdProveedor(idProveedor);
        if (proveedorOpt.isPresent()) {
            ProveedorModel proveedor = proveedorOpt.get();

            StringBuilder builder = new StringBuilder();

            builder.append("Id Proveedor: ").append(proveedor.getIdProveedor()).append(" | ")
                    .append("Nombre Proveedor: ").append(proveedor.getNombreProveedor());

            pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(),
                    TipoMovPista.IMPRIME_REGISTRO.getId(),
                    TipoSeccionPista.PROVEEDOR_DICTAMEN_TECNICO.getIdSeccionPista(),
                    builder.toString(),
                    Optional.empty());
        } else {
            throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_LA_PISTA);
        }
    }





}
