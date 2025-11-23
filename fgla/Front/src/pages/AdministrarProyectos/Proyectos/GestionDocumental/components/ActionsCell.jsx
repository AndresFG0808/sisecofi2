import React from "react";
import IconButton from "../../../../../components/buttons/IconButton";
import { FormControl, FormLabel } from "react-bootstrap";
//import { updateRowFromSubRow } from "../utils";
import { Tooltip } from "../../../../../components/Tooltip";
import Authorization from "../../../../../components/Authorization";

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
          <Authorization process={"PROY_GD_ADMIN"}>
            <IconButton
              tooltip={"Ver documento"}
              type={"show"}
              onClick={() => {
                onShowFile(original);
              }}
              iconSize="lg"
            />
          </Authorization>
        </>
      ) : null}
      {original?.ruta && original?.cargado && !original?.precargado ? (
        <>
          <Authorization process={"PROY_GD_ADMIN"}>
            <IconButton
              tooltip={"Eliminar archivo cargado"}
              type={"drop"}
              onClick={() => {
                onDelete(row);
              }}
              iconSize="lg"
              disabled={!editable}
            />
          </Authorization>
        </>
      ) : null}
      {original.idCarpetaPlantillaProyecto === null ? (
        <>
          <Authorization process={"PROY_GD_DOWN_MASIVA"}>
            <IconButton
              tooltip={"Descarga masiva por fase en SATCloud"}
              type={"download"}
              onClick={() => {
                onDownloadSat(row);
              }}
              iconSize="lg"
              disabled={!descarga}
            />
            <IconButton
              tooltip={"Descarga masiva por fase en formato ZIP"}
              type={"zip"}
              onClick={() => {
                onDownloadMasiva(row);
              }}
              iconSize="lg"
              disabled={!descarga}
            />
          </Authorization>
        </>
      ) : null}
      {original.cargado === false || original.file ? (
        <>
          <Authorization process={"PROY_GD_ADMIN"}>
            <FormLabel htmlFor={row.id}>
              <Tooltip text={"Cargar documento"}>
                <span className={`${row.original.cargado ? "cargado" : ""}`}>
                  <IconButton
                    type={"upload"}
                    onClick={() => {}}
                    iconSize="lg"
                    disabled={!editable}
                  />
                </span>
              </Tooltip>
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
              disabled={!editable}
            />
          </Authorization>
        </>
      ) : null}
    </>
  );
}
