import { createSlice } from "@reduxjs/toolkit";
export const contratoSlice = createSlice({
  name: "Contrato",
  initialState: {
    contrato: {},
    disabledSections: {
      identificacion: false,
      datosGenerales: true,
      vigenciaMontos: true,
      gruposServicio: true,
      registroServicios: true,
      proyeccionCasoNegocio: true,
      layoutInformes: true,
      atrasoPrestacion: true,
      informesDocumentales: true,
      informesPeriodicos: true,
      informesDocumentalesServicios: true,
      penasContractuales: true,
      nivelesServicio: true,
      asignacionPlantilla: true,
      gestionDocumental: true,
      conveniosModificatorios: true,
      dictamenesAsociados: true,
      facturasAsociadas: true,
      reintegrosAsociados: true,
      cierre: true,
    },
    disabled: false,
  },
  reducers: {
    setSelectedContrato: (state, actions) => {
      state.contrato = actions.payload;
    },
    onToggleDisableAllSections: (state, actions) => {
      state.disabledSections = {
        datosGenerales: actions.payload,
        vigenciaMontos: actions.payload,
        gruposServicio: actions.payload,
        registroServicios: actions.payload,
        proyeccionCasoNegocio: actions.payload,
        layoutInformes: actions.payload,
        atrasoPrestacion: actions.payload,
        informesDocumentales: actions.payload,
        informesPeriodicos: actions.payload,
        informesDocumentalesServicios: actions.payload,
        penasContractuales: actions.payload,
        nivelesServicio: actions.payload,
        asignacionPlantilla: actions.payload,
        gestionDocumental: actions.payload,
        conveniosModificatorios: actions.payload,
        dictamenesAsociados: actions.payload,
        facturasAsociadas: actions.payload,
        reintegrosAsociados: actions.payload,
        cierre: actions.payload,
      };
    },
  },
});

export const { setSelectedContrato, onToggleDisableAllSections } =
  contratoSlice.actions;
