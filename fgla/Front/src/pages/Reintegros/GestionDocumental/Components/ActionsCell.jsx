import React from "react";

import { FormControl, FormLabel } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import Authorization from "../../../../components/Authorization";
function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = structuredClone(bottomRow.getParentRow().original);
  updatedRow.subRows[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
export function ActionsCell({
  getValue,
  row,
  table,
  onDownloadSat,
  onDownloadMasiva,
  onShowFile,
  onUploadFile,
  onDelete,
  editable,
  callback,
}) {
  const { original } = row;
  let { descarga = false } = original ? original : {};
  return (
    <Authorization process={"CON_SERV_DICT_REINT_CONS"}>
      {original?.ruta &&
      original?.ruta?.includes(".pdf") &&
      original?.cargado ? (
        <>
          <IconButton
            type={"show"}
            onClick={() => {
              onShowFile(original);
            }}
            iconSize="lg"
            tooltip={"Ver documento"}
          />
        </>
      ) : null}
      {original?.ruta && original?.cargado && !original?.precargado ? (
        <>
          <IconButton
            type={"drop"}
            onClick={() => {
              onDelete(row);
            }}
            iconSize="lg"
            disabled={editable}
            tooltip={"Eliminar archivo cargado"}
          />
        </>
      ) : null}
      {original.idCarpetaPlantillaProyecto === null ? (
        <>
          <IconButton
            type={"download"}
            onClick={() => {
              onDownloadSat(row);
            }}
            iconSize="lg"
            tooltip={"Descarga masiva por fase en SATCloud"}
            disabled={!descarga}
          />
          <IconButton
            type={"zip"}
            onClick={() => {
              onDownloadMasiva(row);
            }}
            iconSize="lg"
            tooltip={"Descarga masiva por fase en formato ZIP"}
            disabled={!descarga}
          />
        </>
      ) : null}
      {original.cargado === false || original.file ? (
        <>
          <FormLabel htmlFor={row.id}>
            <span className={`${row.original.cargado ? "cargado" : ""}`}>
              <IconButton
                type={"upload"}
                onClick={() => {}}
                iconSize="lg"
                disabled={editable}
                tooltip={"Cargar documento"}
              />
            </span>
          </FormLabel>
          <FormControl
            onChange={(event) => {
              console.log("Fila donde se sube el archivo", row);
              const { file } = row.original;
              const newFile = event.target.files[0];
              if (newFile) {
                const size = (newFile.size / 1024 / 1024).toFixed(3);
                if (!file) {
                  if (row.parentId) {
                    const newFile = event.target.files[0];
                    const seedRow = table.getRow(row.parentId.split(".")[0]);
                    const newRow = {
                      ...row.original,
                      file: newFile,
                      tamanoMb: size,
                      estatus: true,
                      cargado: true,
                      precargado: true,
                      fechaModificacion: new Date(),
                      fase: seedRow.original.nombre,
                    };
                    const final = updateRowFromSubRow(row, newRow);
                    table?.options?.meta.updateSubRows(row.index, final);
                  }
                } else {
                  if (callback) callback({ file: newFile, tamanoMb: size });
                }
              }
            }}
            id={row.id}
            type="file"
            style={{ display: "none" }}
            disabled={editable}
          />
        </>
      ) : null}
    </Authorization>
  );
}
