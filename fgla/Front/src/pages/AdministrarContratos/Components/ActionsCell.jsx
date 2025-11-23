import React from "react";
import IconButton from "../../../components/buttons/IconButton";
import { FormControl, FormLabel } from "react-bootstrap";
import { updateRowFromSubRow } from "../AdminContratos/AltaContratos/GestionDocumental/utils";
import Authorization from "../../../components/Authorization";

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
          <Authorization process={"CONT_GD_ADMIN"}>
            <IconButton
              type={"drop"}
              onClick={() => {
                onDelete(row);
              }}
              iconSize="lg"
              disabled={editable}
              tooltip={"Eliminar archivo cargado"}
            />
          </Authorization>
        </>
      ) : null}
      {original.idCarpetaPlantillaProyecto === null ? (
        <>
          <Authorization process={"CONT_GD_DOWN_MASIVA"}>
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
          </Authorization>
        </>
      ) : null}
      {original.cargado === false || original.file ? (
        <>
          <Authorization process={"CONT_GD_ADMIN"}>
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
                //console.log('Archivo seleccionado:', event.target.files[0]);
                //console.log('Row:', table);
                //console.log('ParentId:', row.parentId)
                const { file } = row.original;
                const newFile = event.target.files[0];
                if (newFile) {
                  const size = (newFile.size / 1024 / 1024).toFixed(3);
                  if (!file) {
                    if (row.parentId) {
                      const parentIndex = row.parentId;
                      const seedRow = table.getRow(parentIndex);
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
          </Authorization>
        </>
      ) : null}
    </>
  );
}
