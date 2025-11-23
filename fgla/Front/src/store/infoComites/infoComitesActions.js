import { createAsyncThunk } from "@reduxjs/toolkit";
import {
  getData,
  getDataParams,
  postData,
  putData,
  deleteData,
  putDataForm,
} from "../../functions/api";

const BASE_PATH = "/proyectos";

export const GetPlantillasVigentes = createAsyncThunk(
  "InfoComites/PlantillaVigente",
  async (_, { rejectWithValue }) => {
    try {
      const res = await getData("/proyectos/plantilla/platilla-vigente");
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const GetPlantillaById = createAsyncThunk(
  "InfoComites/Plantilla",
  async (idPlantilla, { rejectWithValue }) => {
    try {
      const res = await getData(`/proyectos/plantillas-carpetas/${idPlantilla}`);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const GetInfoComites = createAsyncThunk(
  "infoComites/GetInfoComites",
  async (idProyecto, { rejectWithValue }) => {
    try {
      const res = await getData(
        BASE_PATH + "/informacion-comites/" + idProyecto
      );
      // const res = await getData(BASE_PATH + "/informacion-comites");

      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const GetInfoComites2 = createAsyncThunk(
  "infoComites/GetInfoComites",
  async (data, { rejectWithValue }) => {
    try {
      const res = await postData(BASE_PATH + "/comite-proyectos", data);

      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const GetInfoComite = createAsyncThunk(
  "infoComites/GetInfoComite",
  async (idProyecto, { rejectWithValue }) => {
    try {
      const res = await getData(`${BASE_PATH}/detalle-comite/${idProyecto}`);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);
export const GetInfoComiteFiles = createAsyncThunk(
  "infoComites/GetInfoComiteFiles",
  async (idProyecto, { rejectWithValue }) => {
    try {
      const res = await getData(`${BASE_PATH}/detalle-comite/${idProyecto}`);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const UpdateComite = createAsyncThunk(
  "infoComites/UpdateComite",
  async (data, { rejectWithValue }) => {
    try {
      const res = await putData(BASE_PATH + "/comite-proyecto", data);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const DeleteComite = createAsyncThunk(
  "infoComites/DeleteComite",
  async (idProyecto, { rejectWithValue }) => {
    try {
      const res = await deleteData(
        `${BASE_PATH}/informacion-comite/${idProyecto}`
      );
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const DownloadReport = createAsyncThunk(
  "infoComites/DownloadReport",
  async (idProyecto, { rejectWithValue }) => {
    try {
      const res = await getData(BASE_PATH + "/reporte/"+idProyecto);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const CreateComite = createAsyncThunk(
  "infoComites/CreateComite",
  async (data, { rejectWithValue }) => {
    try {
      const res = await postData(BASE_PATH + "/comite-proyecto", data);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const DescargarFolder = createAsyncThunk(
  "infoComites/DescargarFolder",
  async (formData, { rejectWithValue }) => {
    try {
      const res = await putDataForm(BASE_PATH + "/descargar-folder", formData);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const GetSatCloud = createAsyncThunk(
  "infoComites/GetSatCloud",
  async (formData, { rejectWithValue }) => {
    try {
      const res = await putDataForm(
        `${BASE_PATH}/descargar-folder-sat-cloud`,
        formData
      );
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const GetPlantillaDocumentos = createAsyncThunk(
  "infoComites/PlantillaDocumentos",
  async (idPlantilla, { rejectWithValue }) => {
    try {
      const res = await getData(
        `${BASE_PATH}/plantilla-carpetas/${idPlantilla}`
      );
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const CargarArchivos = createAsyncThunk(
  "infoComites/CargarArchivos",
  async (data, { rejectWithValue }) => {
    try {
      const res = await postData(`${BASE_PATH}/cargar-archivos`, data);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const ActualizarArchivos = createAsyncThunk(
  "infoComites/ActualizarArchivos",
  async (data, { rejectWithValue }) => {
    try {
      const res = await putData(`${BASE_PATH}/actualizar-archivos`, data);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const BorrarArchivos = createAsyncThunk(
  "infoComites/BorrarArchivos",
  async (data, { rejectWithValue }) => {
    try {
      const res = await postData(`${BASE_PATH}/eliminar-archivos`, data);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const DescargarArchivo = createAsyncThunk(
  "infoComites/DescargarArchivo",
  async (formData, { rejectWithValue }) => {
    try {
      const res = await putDataForm(BASE_PATH + "/descargar-archivo", formData);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const GetDetalleProyecto = createAsyncThunk(
  "infoComites/DetalleProyecto",
  async (idProyecto, { rejectWithValue }) => {
    try {
      const res = await getData(`${BASE_PATH}/ultima-mod/${idProyecto}`);
      return res.data;
    } catch (error) {
      return rejectWithValue(error);
    }
  }
);

export const clearInfoComite = createAsyncThunk('infoComites/clearInfoComiteFiles');