import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

// TODO: Replace with axios agent; dummy url: "https://jsonplaceholder.typicode.com/todos"
export const clientRequest = createAsyncThunk("ui/fetch", async (url) => {
  const data = await fetch(url);
  return data.json();
});

export const timeRequest = createAsyncThunk(
  "ui/time-based-request",
  async (time) => {
    await new Promise((resolve) => setTimeout(resolve, time));
    return null;
  }
);

export const uiSlice = createSlice({
  name: "ui",
  initialState: {
    isLoading: false,
    data: null,
    error: null,
  },
  //
  reducers: {
    onLoading: (state) => {
      state.isLoading = true;
    },
    onNotLoading: (state) => {
      state.isLoading = false;
    },
    onActivateLoading: (state, { payload }) => {
      state.isLoading = payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(clientRequest.pending, (state, action) => {
        state.isLoading = true;
        state.data = null;
        state.error = null;
      })
      .addCase(clientRequest.fulfilled, (state, action) => {
        state.isLoading = false;
        state.data = action.payload;
        state.error = null;
      })
      .addCase(clientRequest.rejected, (state, action) => {
        state.isLoading = false;
        state.data = null;
        state.error = action.payload;
      })
      .addCase(timeRequest.pending, (state, action) => {
        state.isLoading = false;
      })
      .addCase(timeRequest.fulfilled, (state, action) => {
        state.isLoading = true;
      });
  },
});

export const { onLoading, onNotLoading, onActivateLoading, modificarTitulo } =
  uiSlice.actions;
