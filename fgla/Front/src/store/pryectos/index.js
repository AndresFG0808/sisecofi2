import { createSlice } from "@reduxjs/toolkit";

export const proyectosSlice = createSlice({
  name: "Proyecto",
  initialState: {
    proyecto: {},
    // ENV DE DESARROLLO habilitarlas, de lo contrario desahbilitatrlas
    // Checar qué secciones activas
    seccionesInactivas: {
      datosGenerales: false,
      fichaTecnica: true,
      asociarFases: true,
      gestionDocumental: true,
      informacionComites: true,
      planTrabajo: true,
      participacionProveedores: true,
      verificacionRCP: true,
    },
    editable: false,
  },
  reducers: {
    onEditProyecto: (state, actions) => {
      state.proyecto = actions.payload;
    },
    onToggleSection: (state, actions) => {
      state.seccionesInactivas[actions.payload.seccion] =
        actions?.payload?.active;
    },
    onToggleAllSections: (state, actions) => {
      state.seccionesInactivas = {
        fichaTecnica: actions.payload,
        asociarFases: actions.payload,
        gestionDocumental: actions.payload,
        informacionComites: actions.payload,
        planTrabajo: actions.payload,
        participacionProveedores: actions.payload,
        verificacionRCP: actions.payload,
      };
    },
    onToggleEditar: (state, actions) => {
      state.editable = actions.payload;
    },
  },
});

export const {
  onEditProyecto,
  onToggleSection,
  onToggleAllSections,
  onToggleEditar,
} = proyectosSlice.actions;
