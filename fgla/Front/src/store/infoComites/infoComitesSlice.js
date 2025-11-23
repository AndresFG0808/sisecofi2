import { createSlice } from "@reduxjs/toolkit";
import * as actions from "./infoComitesActions";

const handleAsyncActions = (builder, asyncThunk, stateKey) => {
  builder
    .addCase(asyncThunk.pending, (state) => {
      state[stateKey] = null;
      state.isLoading = true;
      state.error = null;
    })
    .addCase(asyncThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      state[stateKey] = action.payload;
      state.error = null;
    })
    .addCase(asyncThunk.rejected, (state, action) => {
      state.isLoading = false;
      state.error = action.payload;
    });
};

export const infoComitesSlice = createSlice({
  name: "infoComites",
  initialState: {
    tableData: [],
    catalogosNotMapped: {},
    infoComite: null,
    infoComiteFiles:null,
    plantillaInfo: null,
    deleteComite: null,
    updateComite: null,
    isLoading: false,
    error: null,
    folder: null,
    plantillasVigentes: null,
    satCloud: null,
    plantillaDocs: null,
    detalleProyecto:null
  },
  reducers: {},

  extraReducers: (builder) => {
    handleAsyncActions(builder, actions.GetInfoComites, "tableData");
    handleAsyncActions(builder, actions.GetInfoComite, "infoComite");
    handleAsyncActions(builder, actions.GetInfoComiteFiles, "infoComiteFiles");
    handleAsyncActions(builder, actions.UpdateComite, "updateComite");
    handleAsyncActions(builder, actions.GetPlantillaById, "plantillaInfo");
    handleAsyncActions(builder, actions.DeleteComite, "deleteComite");
    handleAsyncActions(builder, actions.DescargarFolder, "folder");
    handleAsyncActions(builder, actions.GetSatCloud, "satCloud");
    handleAsyncActions(builder, actions.GetDetalleProyecto, "detalleProyecto")
    handleAsyncActions(builder, actions.GetPlantillaDocumentos, "plantillaDocs");
    handleAsyncActions(builder, actions.GetPlantillasVigentes, "plantillasVigentes");
    builder.addCase(actions.clearInfoComite.pending, (state) => {
      // Limpia el estado
      state.infoComite = null;
      state.infoComiteFiles = null;
    });
  },
});
