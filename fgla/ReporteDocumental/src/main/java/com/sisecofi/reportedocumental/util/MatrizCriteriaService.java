package com.sisecofi.reportedocumental.util;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoOtroDocumentoFaseModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.CarpetaPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.CarpetaPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoFaseDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.CarpetaPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoFaseReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.CarpetaPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;
import com.sisecofi.libreria.comunes.model.gestionDocumental.carpetas.CarpetaPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.AsociasionComitePlantillaModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatrizCriteriaService {
    private final EntityManager em;
    private static final String ID_PROYECTO= "idProyecto";
    private static final String ID_CONTRATO= "idContrato";
    private static final String ID_CONVENIO= "idConvenioModificatorio";
    private static final String ID_DICTAMEN= "idDictamen";
    private static final String ENTIDAD_CARPETA= "carpetaPlantillaModel";

    public Optional<Long> findIdMatriz(Integer identificador, Long idArchivo) {
        return switch (identificador) {
            case 1 -> ejecutarCriteriaArchivoPlantillaProyecto(idArchivo);
            case 2 -> ejecutarCriteriaArchivoOtroDocumentoProyectoModel(idArchivo);
            case 3 -> ejecutarCriteriaArchivoOtroDocumentoFaseModel(idArchivo);
            case 4 -> ejecutarCriteriaArchivoPlantillaComiteModel(idArchivo);
            case 5 -> ejecutarCriteriaArchivoOtrosDocumentosComiteModel(idArchivo);
            case 6 -> ejecutarCriteriaArchivoPlantillaContrato(idArchivo);
            case 7 -> ejecutarArchivoOtroDocumentoFaseContratoModel(idArchivo);
            case 8 -> ejecutarArchivoOtroDocumentoContratoModel(idArchivo);
            case 9 -> ejecutarArchivoPlantillaConvenioModel(idArchivo);
            case 10 -> ejecutarArchivoOtroDocumentoFaseConvenioModel(idArchivo);
            case 11 -> ejecutarArchivoOtroDocumentoConvenioModel(idArchivo);
            case 12 -> ejecutarArchivoPlantillaReintegroModel(idArchivo);
            case 13 -> ejecutarArchivoOtroDocumentoFaseReintegroModel(idArchivo);
            case 14 -> ejecutarArchivoOtroDocumentoReintegroModel(idArchivo);
            case 15 -> ejecutarArchivoPlantillaDictamenModel(idArchivo);
            case 16 -> ejecutarArchivoOtroDocumentoFaseDictamenModel(idArchivo);
            case 17 -> ejecutarArchivoOtroDocumentoDictamenModel(idArchivo);
            case 18 -> ejecutarCriteriaFacturaPorArchivo(idArchivo);
            case 19 -> ejecutarCriteriaNotaPorArchivo(idArchivo);
            default -> Optional.empty();
        };
    }

    private Optional<Long> ejecutarCriteriaArchivoPlantillaProyecto(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoPlantillaProyectoModel> app = cq.from(ArchivoPlantillaProyectoModel.class);
        Join<ArchivoPlantillaProyectoModel, CarpetaPlantillaProyectoModel> d = app.join(ENTIDAD_CARPETA);
        Join<CarpetaPlantillaProyectoModel, AsociacionesModel> cm = d.join("asociacionesModel");

        cq.select(cm.get(ID_PROYECTO))
          .where(cb.equal(app.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarCriteriaArchivoOtroDocumentoProyectoModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoProyectoModel> app = cq.from(ArchivoOtroDocumentoProyectoModel.class);
        cq.select(app.get(ID_PROYECTO))
          .where(cb.equal(app.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarCriteriaArchivoOtroDocumentoFaseModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoFaseModel> app = cq.from(ArchivoOtroDocumentoFaseModel.class);
        cq.select(app.get(ID_PROYECTO))
          .where(cb.equal(app.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarCriteriaArchivoPlantillaComiteModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoPlantillaComiteModel> apc = cq.from(ArchivoPlantillaComiteModel.class);
        Join<ArchivoPlantillaContratoModel, AsociasionComitePlantillaModel> cpm = apc.join("asociasionComitePlantillaModel");
        Join<AsociasionComitePlantillaModel, ComiteProyectoModel> p = cpm.join("comiteProyectoModel");

        cq.select(p.get("idComiteProyecto"))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarCriteriaArchivoOtrosDocumentosComiteModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtrosDocumentosComiteModel> apc = cq.from(ArchivoOtrosDocumentosComiteModel.class);

        cq.select(apc.get("idComiteProyecto"))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    
    private Optional<Long> ejecutarCriteriaArchivoPlantillaContrato(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoPlantillaContratoModel> apc = cq.from(ArchivoPlantillaContratoModel.class);
        Join<ArchivoPlantillaContratoModel, CarpetaPlantillaContratoModel> cpm = apc.join(ENTIDAD_CARPETA);
        Join<CarpetaPlantillaContratoModel, ContratoPlantilla> cp = cpm.join("contratoPlantilla");
  
        cq.select(cp.get(ID_CONTRATO))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoOtroDocumentoFaseContratoModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoFaseContratoModel> apc = cq.from(ArchivoOtroDocumentoFaseContratoModel.class);


        cq.select(apc.get(ID_CONTRATO))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoOtroDocumentoContratoModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoContratoModel> apc = cq.from(ArchivoOtroDocumentoContratoModel.class);

        cq.select(apc.get(ID_CONTRATO))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoPlantillaConvenioModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoPlantillaConvenioModel> apc = cq.from(ArchivoPlantillaConvenioModel.class);
        Join<ArchivoPlantillaConvenioModel, CarpetaPlantillaConvenioModel> cpm = apc.join("carpetaPlantillaConvenioModel");
        Join<CarpetaPlantillaConvenioModel, ConvenioPlantilla> cp = cpm.join("convenioPlantilla");

        cq.select(cp.get("idConvenio"))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoOtroDocumentoFaseConvenioModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoFaseConvenioModel> apc = cq.from(ArchivoOtroDocumentoFaseConvenioModel.class);
        
        cq.select(apc.get(ID_CONVENIO))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoOtroDocumentoConvenioModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoConvenioModel> apc = cq.from(ArchivoOtroDocumentoConvenioModel.class);
        
        cq.select(apc.get(ID_CONVENIO))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoPlantillaReintegroModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoPlantillaReintegroModel> apc = cq.from(ArchivoPlantillaReintegroModel.class);
        Join<ArchivoPlantillaReintegroModel, CarpetaPlantillaReintegroModel> cp = apc.join(ENTIDAD_CARPETA);
        Join<CarpetaPlantillaReintegroModel, ReintegrosAsociadosModel> cpr = cp.join("reintegro");
        
        cq.select(cpr.get(ID_CONTRATO))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoOtroDocumentoFaseReintegroModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoFaseReintegroModel> apc = cq.from(ArchivoOtroDocumentoFaseReintegroModel.class);
        Join<ArchivoOtroDocumentoFaseReintegroModel, ReintegrosAsociadosModel> cp = apc.join("reintegro");
        
        cq.select(cp.get(ID_CONTRATO))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoOtroDocumentoReintegroModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoReintegroModel> apc = cq.from(ArchivoOtroDocumentoReintegroModel.class);
        
        cq.select(apc.get(ID_CONTRATO))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    
    private Optional<Long> ejecutarArchivoPlantillaDictamenModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoPlantillaDictamenModel> apc = cq.from(ArchivoPlantillaDictamenModel.class);
        Join<ArchivoPlantillaDictamenModel, CarpetaPlantillaDictamenModel> cp = apc.join(ENTIDAD_CARPETA);
        
        cq.select(cp.get(ID_DICTAMEN))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoOtroDocumentoFaseDictamenModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoFaseDictamenModel> apc = cq.from(ArchivoOtroDocumentoFaseDictamenModel.class);
        
        cq.select(apc.get(ID_DICTAMEN))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarArchivoOtroDocumentoDictamenModel(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<ArchivoOtroDocumentoDictamenModel> apc = cq.from(ArchivoOtroDocumentoDictamenModel.class);
        
        cq.select(apc.get(ID_DICTAMEN))
          .where(cb.equal(apc.get("id"), idArchivo));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarCriteriaFacturaPorArchivo(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<FacturaModel> f = cq.from(FacturaModel.class);
        Join<FacturaModel, ArchivoPlantillaDictamenModel> pdf = f.join("archivoPdf", jakarta.persistence.criteria.JoinType.LEFT);
        Join<FacturaModel, ArchivoPlantillaDictamenModel> xml = f.join("archivoXml", jakarta.persistence.criteria.JoinType.LEFT);

        cq.select(f.get(ID_DICTAMEN))
          .distinct(true) 
          .where(cb.or(
              cb.equal(pdf.get("id"), idArchivo),
              cb.equal(xml.get("id"), idArchivo)
          ));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    private Optional<Long> ejecutarCriteriaNotaPorArchivo(Long idArchivo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<NotaCreditoModel> n = cq.from(NotaCreditoModel.class);
        Join<NotaCreditoModel, ArchivoPlantillaDictamenModel> pdf = n.join("archivoPdf", jakarta.persistence.criteria.JoinType.LEFT);
        Join<NotaCreditoModel, ArchivoPlantillaDictamenModel> xml = n.join("archivoXml", jakarta.persistence.criteria.JoinType.LEFT);

        cq.select(n.get(ID_DICTAMEN))
          .distinct(true) 
          .where(cb.or(
              cb.equal(pdf.get("id"), idArchivo),
              cb.equal(xml.get("id"), idArchivo)
          ));

        List<Long> results = em.createQuery(cq)
                .setMaxResults(1)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    
}

