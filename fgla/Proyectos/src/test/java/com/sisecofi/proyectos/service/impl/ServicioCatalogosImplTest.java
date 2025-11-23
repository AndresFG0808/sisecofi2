package com.sisecofi.proyectos.service.impl;
/*
import com.mock.BaseCatalogoMock;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.util.FactoryClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ServicioCatalogosImplTest {
	
	@Mock
	private CatalogoMicroservicio catalogoMicroservicio;

	@InjectMocks
    private ServicioCatalogosImpl servicioCatalogosImpl;
    
   

    @BeforeEach
    void setComiteMock(){
        BaseCatalogoMock.baseCatalogoMock(servicioCatalogosImpl, catalogoMicroservicio);
    }

    @Test
    void obtenerContratoConvenioInfo() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerContratoConvenioInfo();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }

    @Test
    void obtenerContratosInfo() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerContratosInfo();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
        	
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }

    @Test
    void obtenetComitesInfo() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenetComitesInfo();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }

    @Test
    void obtenerAfectacionInfo() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerAfectacionInfo();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }

    @Test
    void obtenerSesionInfo() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerSesionInfo();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }

    @Test
    void obtenerPlantillaInfo() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerPlantillaInfo();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }

    @Test
    void obtenerTipoDeMonedaInfo() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerTipoDeMonedaInfo();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
    
    @Test
    void obtenerAdminPatrocinadora() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerAdmonGenerales();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
    
    @Test
    void obtenerAdminCentralPatrocinadora() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerAdmonCentrales();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
    
    @Test
    void obtenerAdminParticipante() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerAdmonGenerales();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
    
    @Test
    void obtenerClasificacionProyecto() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerClasificacionProyecto();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
    
    @Test
    void obtenerFinanciamiento() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerFinanciamiento();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
    
    @Test
    void obtenerEstatus() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerEstatus();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
    
    
    @Test
    void ObtenerEstatusInicial() {
    	BaseCatalogoModel baseCatalogoModel = servicioCatalogosImpl.obtenerEstatusInicial();
    	baseCatalogoModel.setNombre(FactoryClass.obtenerEstatus().getNombre());
    	String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);   
    }
    
    @Test
    void obtenerTipoProcedimiento() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerTipoProcedimiento();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
    
    @Test
    void obtenerPeriodo() {
        List<BaseCatalogoModel> baseCatalogoModelList = servicioCatalogosImpl.obtenerPeriodo();
        String nombreCatalogo = FactoryClass.obtenerEstatus().getNombre();
        for(BaseCatalogoModel baseCatalogoModel : baseCatalogoModelList){
            assertEquals( baseCatalogoModel.getNombre(), nombreCatalogo);
        }
    }
}*/