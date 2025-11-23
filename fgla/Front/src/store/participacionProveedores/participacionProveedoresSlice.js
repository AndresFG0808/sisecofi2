import { createSlice } from '@reduxjs/toolkit'
// Slice
export const participacionProveedoresSlice = createSlice({
  name: 'participacionProveedores',
  initialState: {
    tableData: [],
  },
  reducers: {
    setDataTable: (state, action) => {
      state.tableData = action.payload;
    },
  },
});
// Actions
const { setDataTable } = participacionProveedoresSlice.actions
export const setDataTableAction = (dataTable) => async dispatch => {
  try {
    // aquí se puede ejecutar una api
    dispatch(setDataTable(dataTable));
  } catch (e) {
    return console.error(e.message);
  }
};
