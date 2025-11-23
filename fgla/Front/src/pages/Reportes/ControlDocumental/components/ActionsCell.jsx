import React, { useRef } from "react";
import IconButton from "../../../../components/buttons/IconButton";
import { FormControl, FormLabel } from "react-bootstrap";
import { handleFileChange, updateRowFromSubRow } from "../utils";
import { Tooltip } from "../../../../components/Tooltip";
import Authorization from "../../../../components/Authorization";

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

  return (
    <>
      {original?.ruta &&
      original?.ruta?.includes(".pdf") &&
      original?.cargado ? (
        <>
          {/* <Authorization process={"PROY_GD_ADMIN"}> */}
            <IconButton
              tooltip={"Ver documento"}
              type={"show"}
              onClick={() => {
                onShowFile(original);
              }}
              iconSize="lg"
            />
          {/* </Authorization> */}
        </>
      ) : null}
      {original?.ruta && original?.cargado ? (
        <>
          {/* <Authorization process={"PROY_GD_ADMIN"}> */}
            <IconButton
              tooltip={"Eliminar archivo cargado"}
              type={"drop"}
              onClick={() => {
                onDelete(row);
              }}
              iconSize="lg"
              disabled={!editable}
            />
          {/* </Authorization> */}
        </>
      ) : null}
      {original.idCarpetaPlantillaProyecto === null ? (
        <>
          {/* <Authorization process={"PROY_GD_DOWN_MASIVA"}> */}
            <IconButton
              tooltip={"Descarga masiva por fase en SATCloud"}
              type={"download"}
              onClick={() => {
                onDownloadSat(row);
              }}
              iconSize="lg"
            />
            <IconButton
              tooltip={"Descarga masiva por fase en formato ZIP"}
              type={"zip"}
              onClick={() => {
                onDownloadMasiva(row);
              }}
              iconSize="lg"
            />
          {/* </Authorization> */}
        </>
      ) : null}
      {original.cargado === false || original.file ? (
        <>
          {/* <Authorization process={"PROY_GD_ADMIN"}> */}
            <FormLabel htmlFor={row.id}>
              <Tooltip text={"Cargar documento"}>
                <span className={`${row.original.cargado ? "cargado" : ""}`}>
                  <IconButton
                    type={"upload"}
                    onClick={() => {
                      console.log("Fila que se dio on click", row);
                    }}
                    iconSize="lg"
                    disabled={!editable}
                  />
                </span>
              </Tooltip>
            </FormLabel>
            <FormControl
              onChange={(event) => {
                const { file } = row.original;
                const { nombre } = row.getParentRow().original;
                const newFile = event.target.files[0];

                if (newFile) {
                  // Obtener la ruta del archivo
                  const size = (newFile.size / 1024 / 1024).toFixed(3);
                  if (!file) {
                    if (row.parentId) {
                      const parentIndex = row.parentId
                        ? parseInt(row.parentId.split("")[0])
                        : undefined;
                      const seedRow = table.getRow(row.parentId.split("")[0]);
                      const newRow = {
                        ...row.original,
                        file: newFile,
                        tamanoMb: size,
                        estatus: true,
                        cargado: true,
                        fechaModificacion: new Date(),
                        fase: seedRow.original.nombre,
                      };
                      const final = updateRowFromSubRow(row, newRow);
                      table?.options?.meta.updateSubRows(parentIndex, final);
                    }
                  } else {
                    if (callback) callback({ file: newFile, tamanoMb: size });
                  }
                }
              }}
              id={row.id}
              type="file"
              style={{ display: "none" }}
              disabled={!editable}
            />
          {/* </Authorization> */}
        </>
      ) : null}
    </>
  );
}
