import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChevronDown,
  faChevronRight,
} from "@fortawesome/free-solid-svg-icons";
import IconButton from "../../../../components/buttons/IconButton";
import TextField from "../../../../components/formInputs/TextField";
import { Tooltip } from "../../../../components/Tooltip";
import { isAddDocument, isFile } from "../utils";

const template = {
  isInput: true,
  cargado: false,
  // carpeta: "CM/Carpeta1/Carpeta2/Carpeta3",
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
              <span
                style={{ textDecoration: "underline", cursor: "pointer" }}
                onClick={() => {
                  callback(original.ruta);
                }}
              >
                {getValue()}
              </span>
            ) : (
              <>{getValue()}</>
            )}
            {isAddDocument(getValue()) ? (
              <>
                <Tooltip text={"Nuevo documento"} placement="top">
                  <span>
                    <IconButton
                      type={"add"}
                      iconSize="lg"
                      onClick={() => {
                        onAddDocument();
                      }}
                      disabled={!editable}
                    />
                  </span>
                </Tooltip>
              </>
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
