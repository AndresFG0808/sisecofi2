import React from "react";

import { FormControl, FormLabel } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
//import { updateRowFromSubRow } from "../../../../AdministrarContratos/AdminContratos/AltaContratos/GestionDocumental/utils";

export function ActionsCell({
  row,
  table,
  onDownloadSat,
  onDownloadMasiva,
  onShowFile,
  onDelete,
  editable,
  callback,
}) {
  const { original } = row;
  let { descarga = false } = original ? original : {};
  return (
    <>
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
                //console.log('Row:', row);
                const newFile = event.target.files[0];
                const seedRow = table.getRow(row.parentId.split(".")[0]);
                if (newFile) {
                  const size = (newFile.size / 1024 / 1024).toFixed(3);
                  if (callback) {
                    callback({
                      file: newFile,
                      tamanoMb: size,
                      rowUUID: row.original.UUID,
                      fase: seedRow.original.nombre,
                    });
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
    </>
  );
}
