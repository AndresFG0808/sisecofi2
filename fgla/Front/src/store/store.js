import { configureStore } from "@reduxjs/toolkit";
import { catalogosSlice } from "./catalogos/catalogosSlice";
import { participacionProveedoresSlice } from "./participacionProveedores/participacionProveedoresSlice";
import { createStorage, encrypt, decrypt } from "./StoreManager";
import { infoComitesSlice } from "./infoComites/infoComitesSlice";
import storageSession from "redux-persist/lib/storage/session";
import { persistReducer, persistStore } from "redux-persist";
import { combineReducers } from "redux";
import { fichasSlice } from "./fichas/fichasSlice";
import { uiSlice } from "./ui/uiSlice";
import { apiSAT } from "./features";
import { proyectosSlice } from "./pryectos";
import { contratoSlice } from "./contratos";
import { conveniosSlice } from "./convenios";
import { usuarioSlice } from "./authentication";

const persistConfig = {
  key: "root",
  storage: createStorage(),
  transforms: [
    {
      in: (state) => encrypt(state),
      out: (state) => decrypt(state),
    },
  ],
  blacklist: [
    "navigation",
    "ui",
    "apiSAT",
    "infoComites",
    "proyectos",
    "contratos",
    "convenios",
  ],
};

const rootReducer = combineReducers({
  ui: uiSlice.reducer,
  proyectos: proyectosSlice.reducer,
  catalogos: catalogosSlice.reducer,
  participacionProveedores: participacionProveedoresSlice.reducer,
  infoComites: infoComitesSlice.reducer,
  fichaTecnica: fichasSlice.reducer,
  contratos: contratoSlice.reducer,
  convenios: conveniosSlice.reducer,
  usuario: usuarioSlice.reducer,
  [apiSAT.reducerPath]: apiSAT.reducer,
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: ["persist/PERSIST"],
      },
    }).concat(apiSAT.middleware),
});

export const persistor = persistStore(store);
