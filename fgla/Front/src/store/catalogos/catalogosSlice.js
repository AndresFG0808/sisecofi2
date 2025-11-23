import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { getData } from "../../functions/api";

export const getMoneda = createAsyncThunk(
  "catalogos/moneda",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/tipo-moneda");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getSesion = createAsyncThunk(
  "catalogos/sesion",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/sesion");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getSesionNumero = createAsyncThunk(
  "catalogos/sesion-numero",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/sesion-numero");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getSesionClasificacion = createAsyncThunk(
  "catalogos/sesion-clasificacion",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/sesion-clasificacion");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getPlantilla = createAsyncThunk(
  "catalogos/plantilla",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/plantilla");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getFinanciamiento = createAsyncThunk(
  "catalogos/financiamiento",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/financiamiento");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getContratos = createAsyncThunk(
  "catalogos/contratos",
  async (idProyecto, { rejectWithValue }) => {
    try {
      const res = await getData(`/proyectos/contratos/${idProyecto}`);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getContratoConvenio = createAsyncThunk(
  "catalogos/contrato-convenio",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/contrato-convenio");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getComite = createAsyncThunk(
  "catalogos/comite",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/comite");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getAfectacion = createAsyncThunk(
  "catalogos/afectacion",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/afectacion");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getAdministracionPatrocinadora = createAsyncThunk(
  "catalogos/administracion-patrocinadora",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/administracion-patrocinadora");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getAdministracionParticipante = createAsyncThunk(
  "catalogos/administracion-participante",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/administracion-participante");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const getAdministracionCentralPatrocinadora = createAsyncThunk(
  "catalogos/administracion-central-patrocinadora",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData(
        "/proyectos/administracion-Central-patrocinadora"
      );
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const catalogosSlice = createSlice({
  name: "catalogos",
  initialState: {
    isLoading: false,
    catalogos: {
      moneda: null,
      sesion: null,
      sesionNumero: null,
      sesionClasificacion: null,
      plantilla: null,
      financiamiento: null,
      contratos: null,
      contratoConvenio: null,
      comite: null,
      clasificacionProyecto: null,
      areaSolicitante: null,
      areaResponsable: null,
      afectacion: null,
      administrarProyectos: null,
      administracionPatrocinadora: null,
      administracionParticipante: null,
      administracionCentralPatrocinadora: null,
    },
    catalogo: null,
    error: null,
  },
  extraReducers: (builder) => {
    builder
      .addCase(getMoneda.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getMoneda.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.moneda = action.payload;
        state.error = null;
      })
      .addCase(getMoneda.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getSesion.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getSesion.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.sesion = action.payload;
        state.error = null;
      })
      .addCase(getSesion.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getSesionClasificacion.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getSesionClasificacion.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.sesionClasificacion = action.payload;
        state.error = null;
      })
      .addCase(getSesionClasificacion.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getSesionNumero.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getSesionNumero.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.sesionNumero = action.payload;
        state.error = null;
      })
      .addCase(getSesionNumero.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getPlantilla.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getPlantilla.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.plantilla = action.payload;
        state.error = null;
      })
      .addCase(getPlantilla.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getAdministracionPatrocinadora.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getAdministracionPatrocinadora.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.administracionPatrocinadora = action.payload;
        state.error = null;
      })
      .addCase(getAdministracionPatrocinadora.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(
        getAdministracionCentralPatrocinadora.pending,
        (state, action) => {
          state.isLoading = true;
          state.error = null;
        }
      )
      .addCase(
        getAdministracionCentralPatrocinadora.fulfilled,
        (state, action) => {
          state.isLoading = false;
          state.catalogos.administracionCentralPatrocinadora = action.payload;
          state.error = null;
        }
      )
      .addCase(
        getAdministracionCentralPatrocinadora.rejected,
        (state, action) => {
          state.isLoading = false;
          state.error = action.payload;
        }
      )
      .addCase(getFinanciamiento.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getFinanciamiento.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.financiamiento = action.payload;
        state.error = null;
      })
      .addCase(getFinanciamiento.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getContratos.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getContratos.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.contratos = action.payload;
        state.error = null;
      })
      .addCase(getContratos.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getContratoConvenio.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getContratoConvenio.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.contratoConvenio = action.payload;
        state.error = null;
      })
      .addCase(getContratoConvenio.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getComite.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getComite.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.comite = action.payload;
        state.error = null;
      })
      .addCase(getComite.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      })
      .addCase(getAfectacion.pending, (state, action) => {
        state.isLoading = true;
        state.error = null;
      })
      .addCase(getAfectacion.fulfilled, (state, action) => {
        state.isLoading = false;
        state.catalogos.afectacion = action.payload;
        state.error = null;
      })
      .addCase(getAfectacion.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
      });
  },
});
