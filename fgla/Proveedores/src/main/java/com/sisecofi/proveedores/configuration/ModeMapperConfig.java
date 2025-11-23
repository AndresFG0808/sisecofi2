package com.sisecofi.proveedores.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sisecofi.proveedores.dto.ConsultaTituloServicioProveedorDto;
import com.sisecofi.proveedores.dto.DictamenTecnicoDto;
import com.sisecofi.proveedores.dto.DirectorioProveedorDto;
import com.sisecofi.proveedores.dto.ProveedorDto;
import com.sisecofi.proveedores.dto.TituloServicioProveedorDto;
import com.sisecofi.libreria.comunes.model.proveedores.DictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;


@Configuration
public class ModeMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //directorios
        modelMapper.typeMap(DirectorioProveedorModel.class, DirectorioProveedorDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getProveedorModel().getIdProveedor(),
                        DirectorioProveedorDto::setIdProveedor));
        //titulos proveedores
        modelMapper.typeMap(TituloServicioProveedorModel.class, TituloServicioProveedorDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getProveedorModel().getIdProveedor(),
                TituloServicioProveedorDto::setIdProveedor));
                

        modelMapper.typeMap(TituloServicioProveedorModel.class, TituloServicioProveedorDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getTituloServicioModel().getIdTituloServicio(),
                TituloServicioProveedorDto::setIdServicioTitulo));
        

        modelMapper.typeMap(DictamenTecnicoModel.class, DictamenTecnicoDto.class)
               .addMappings(mapper -> mapper.map(src -> src.getCatTituloServicioModel().getIdTituloServicio(),
               DictamenTecnicoDto::setIdServicioTitulo));       
               
        modelMapper.typeMap(DictamenTecnicoModel.class, DictamenTecnicoDto.class)
               .addMappings(mapper -> mapper.map(src -> src.getProveedorModel().getIdProveedor(),
               DictamenTecnicoDto::setIdProveedor));
               
               modelMapper.typeMap(DictamenTecnicoDto.class, DictamenTecnicoModel.class)
               .addMappings(mapper -> mapper.skip(DictamenTecnicoModel::setIdDictamenTecnicoProveedor));


        modelMapper.typeMap(TituloServicioProveedorModel.class, ConsultaTituloServicioProveedorDto.class)
                .addMappings(mapper -> mapper.map(src ->src.getProveedorModel().getIdProveedor(),
                ConsultaTituloServicioProveedorDto::setIdProveedor));

        modelMapper.typeMap(DirectorioProveedorDto.class, DirectorioProveedorModel.class)
                .addMappings(mapper -> mapper.skip(DirectorioProveedorModel::setIdDirectorioContacto));

        modelMapper.typeMap(TituloServicioProveedorDto.class, TituloServicioProveedorModel.class)
                .addMappings(mapper -> mapper.skip(TituloServicioProveedorModel::setIdTituloServicioProveedor));
                
        modelMapper.typeMap(ProveedorDto.class, ProveedorModel.class)
                .addMappings(mapper -> {
                    mapper.skip(ProveedorModel::setIdProveedor);
                    mapper.skip(ProveedorModel::setIdAgs);
                });

        return modelMapper;

    }

}
