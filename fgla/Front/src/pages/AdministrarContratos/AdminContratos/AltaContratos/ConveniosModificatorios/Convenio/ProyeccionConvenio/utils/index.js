export const dummyData = [1, 2, 3, 4];
export const generateColumns = (data) => {
  if (!data) return [];
  const headers = data?.mapa?.["0"]?.map((header) => header.trim());
  const columns = [];
  headers?.forEach((header, index) => {
    if (index === 0) return;
    if (index === 1) {
      const column = {
        accessorKey: "conceptosServicio",
        header: header,
        cell: (props) => <>{props.getValue()}</>,
        filterFn: (row, columnId, inputValue) => {
          return row.original[columnId]
            .toLowerCase()
            .trim()
            .startsWith(inputValue);
        },
      };
      columns.push(column);
    } else {
      const column = {
        accessorKey: header,
        header: header,
        cell: (props) => <>{props.getValue()}</>,
        enableColumnFilter: false,
        enableSorting: false,
      };
      columns.push(column);
    }
  });
  return columns;
};
export const rearrangeData = (originalData) => {
  const headers = originalData?.["0"].map((header) => header.trim());
  const data = [];

  // Iterate over the rest of the keys
  for (let key in originalData) {
    if (key !== "0") {
      const row = originalData[key];
      const obj = {
        id: row[0],
        conceptosServicio: row[1],
      };

      // Add the values for the dates as keys
      for (let i = 2; i < headers.length; i++) {
        obj[headers[i]] = parseFloat(row[i]); // Convert to number
      }

      data.push(obj);
    }
  }
  return data;
};
export const EDITAR_CONVENIOS = {
  MSG001:
    "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "Se cargó la proyección al convenio modificatorio.",
  MSG003:
    "El “Monto máximo del contrato con CM sin impuestos” coincide con el “Monto máximo total” de los servicios.",
  MSG004: "Se perderá la información ingresada. ¿Está seguro de cancelar?",
  MSG005:
    "El layout de carga no contiene la estructura requerida, favor de verificar.",
  MSG006:
    "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG007:
    "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG008:
    "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG009: "Existe una proyección previamente cargada, ¿desea actualizarla?",
  MSG010: "El convenio modificatorio fue actualizado exitosamente.",
  MSG011: "Se guardo correctamente la información.",
  MSG012:
    "El “Monto máximo del contrato con CM sin impuestos” no coincide con el “Monto máximo total” de los servicios.",
  MSG013:
    "Verifique el layout de carga, ya que la línea(s) [Concepto de servicio] sobrepasa el “Número total de servicios”.",
  MSG014: "Se guardó correctamente la información.",
  MSG015: "Se eliminará el registro seleccionado. ¿Desea continuar?",
  MSG016:
    "El registro no se puede eliminar porque se encuentra relacionado en otro módulo.",
  MSG017:
    "Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).",
  MSG018: "Ocurrió un error, favor de intentar más tarde.",
};
