import { createSlice } from "@reduxjs/toolkit";

export const usuarioSlice = createSlice({
  name: "Usuario",
  initialState: {
    nombre: "",
    roles: [],
  },
  reducers: {
    onSetName: (state, actions) => {
      state.nombre = actions.payload;
    },
    onSetRoles: (state, actions) => {
      state.roles = actions.payload;
    },
  },
});

export const { onSetName, onSetRoles } = usuarioSlice.actions;
