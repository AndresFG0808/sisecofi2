import React from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChevronDown,
  faChevronRight,
} from "@fortawesome/free-solid-svg-icons";
import TextField from "../../../../components/formInputs/TextField";
import IconButton from "../../../../components/buttons/IconButton";
import Authorization from "../../../../components/Authorization";

const template = {
  isInput: true,
  cargado: false,
  descripcion: null,
  estatus: true,
  noAplica: false,
  nombre: null,
  obligatorio: true,
  orden: 0,
  tamanoMb: null,
  errors: null,
  isNewRow: true,
};
const regex = /\.[a-zA-Z0-9]+$/;
const isFase = /OTROS_DOCUMENTOS/;
const isFile = (path) => regex.test(path);
const isAddDocument = (descripcion = "") =>
  descripcion?.toLowerCase()?.trim()?.includes("otros documentos");
const isFaseType = (path) => isFase.test(path);

export function ExpandedCell({ row, getValue, callback, table, editable }) {
  const { original } = row;
  const { options } = table;

  const onAddDocument = () => {
    if (row.parentId) {
      const parentIndex = row.parentId
        ? parseInt(row.parentId.split("")[0])
        : undefined;
      const parentRow = row.getParentRow();
      const newRow = {
        ...original,
        subRows: [
          ...original.subRows,
          {
            ...template,
            carpeta: original.ruta,
            ruta: parentRow.original.ruta,
            idReintegro: parentRow.original.idReintegro,
          },
        ],
      };
      const final = updateRowFromSubRow(row, newRow);
      options.meta?.updateSubRows(parentIndex, final);
    } else {
      options.meta?.updateSubRows(row.index, {
        ...row.original,
        subRows: [
          ...row.original.subRows,
          { ...template, carpeta: original.ruta },
        ],
      });
    }
    row.toggleExpanded(true);
  };
  const onChangeDescripcion = (e) => {
    if (row.parentId) {
      const parentIndex = row.parentId
        ? parseInt(row.parentId.split("")[0])
        : undefined;
      const newRow = {
        ...original,
        descripcion: e.target.value,
      };
      const final = updateRowFromSubRow(row, newRow);
      options.meta?.updateSubRows(parentIndex, final);
    } else {
      options.meta?.updateSubRows(row.index, {
        ...row.original,
        descripcion: e.target.value,
      });
    }
  };
  return (
    <>
      {original.isInput ? (
        <TextField name={`descripcion${row.id}`} onBlur={onChangeDescripcion} />
      ) : (
        <div
          style={{
            paddingLeft: `${row.depth * 2}rem`,
          }}
        >
          <div>
            {row.getCanExpand() ? (
              <button
                {...{
                  onClick: row.getToggleExpandedHandler(),
                  style: {
                    border: "1px solid transparent",
                    background: "transparent",
                  },
                }}
              >
                {row.getIsExpanded() ? (
                  <FontAwesomeIcon icon={faChevronDown} />
                ) : (
                  <FontAwesomeIcon icon={faChevronRight} />
                )}
              </button>
            ) : (
              ""
            )}
            {isFile(original.ruta) ? (
              <Authorization
                process={"CON_SERV_DICT_REINT_CONS"}
                redirect={<>{getValue()}</>}
              >
                <span
                  style={{ textDecoration: "underline", cursor: "pointer" }}
                  onClick={() => {
                    callback(original.ruta);
                  }}
                >
                  {getValue()}
                </span>
              </Authorization>
            ) : (
              <>{getValue()}</>
            )}
            {isAddDocument(getValue()) ? (
              <Authorization process={"CON_SERV_DICT_REINT_CONS"}>
                <span>
                  <IconButton
                    type={"add"}
                    iconSize="lg"
                    onClick={() => {
                      console.log("test");
                      onAddDocument();
                    }}
                    disabled={editable}
                    tooltip={"Nuevo documento"}
                  />
                </span>
              </Authorization>
            ) : null}
          </div>
        </div>
      )}
    </>
  );
}
function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = structuredClone(bottomRow.getParentRow().original);
  updatedRow.subRows[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
