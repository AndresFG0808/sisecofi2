import { createSlice } from "@reduxjs/toolkit";
export const conveniosSlice = createSlice({
  name: "Convenios",
  initialState: {
    convenio: {},
    disabledSections: {
      registro: false,
      registroServicios: true,
      proyeccion: true,
      asignacionPlantilla: true,
      gestionDocumental: true,
    },
    disabled: false,
  },
  reducers: {
    setSelectedConvenio: (state, actions) => {
      state.convenio = actions.payload;
    },
    onToggleDisableAllSectionsConvenios: (state, actions) => {
      state.disabledSections = {
        registroServicios: actions.payload,
        proyeccion: actions.payload,
        gestionDocumental: actions.payload,
      };
    },
  },
});

export const { setSelectedConvenio, onToggleDisableAllSectionsConvenios } =
  conveniosSlice.actions;
