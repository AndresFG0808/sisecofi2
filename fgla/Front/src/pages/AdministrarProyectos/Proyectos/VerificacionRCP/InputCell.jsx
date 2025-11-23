import React, { useEffect, useState, memo } from "react";
import { Col, Row } from "react-bootstrap";
import isEmpty from "lodash/isEmpty";
import TextField from "../../../../components/formInputs/TextField";

function InputCell({
  getValue,
  row,
  column,
  table,
  options,
  keyValue = "id",
  keyTextValue = "texto",
  rowClassName = "",
  type,
  isEditable,
  isActiveAddProvider,
  onEmptyActiveProviders,
}) {
  const { id } = column;
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [helperText, setHelperText] = useState(null);
  const [className, setClassName] = useState("");

  useEffect(() => {
    if (initialValue !== value) {
      setValue(initialValue);
    }
  }, [initialValue]);

  const { justificacionErrorText, estatus, paginasErrorText } = row.original;

  useEffect(() => {
    if (
      (helperText === null &&
        value === "" &&
        id === "paginas" &&
        paginasErrorText !== null) ||
      (helperText === null &&
        value === "" &&
        id === "justificacion" &&
        justificacionErrorText !== null)
    ) {
      if (id === "paginas") {
        setHelperText(paginasErrorText);
        setClassName("is-invalid");
      } else if (id === "justificacion") {
        setHelperText(justificacionErrorText);
        setClassName("is-invalid");
      }
    } else if (
      id === "justificacion" &&
      helperText !== null &&
      justificacionErrorText === null
    ) {
      setHelperText(null);
      setClassName("");
    } else if (
      id === "paginas" &&
      helperText !== null &&
      paginasErrorText === null
    ) {
      setHelperText(null);
      setClassName("");
    }
  }, [paginasErrorText, value, helperText, justificacionErrorText]);

  const { updateData } = table.options.meta;
  const { accessorKey } = column.columnDef;
  const { index } = row;
  const onToggleEdit = (isOpen) => row.toggleSelected(isOpen);

  const onHandleChange = (e) => {
    if (isEmpty(helperText) === false) {
      setClassName("is-valid");
      setHelperText(null);
    }
    setValue(e.target.value);
  };

  useEffect(() => {
    row.original.isNewProvider !== true &&
      onToggleEdit(row.original.isEditable);
    // isActiveAddProvider && onToggleEdit(row.original.isEditable);
  }, [isActiveAddProvider]);

  const renderSwitch = () => {
    return (
      <div className={rowClassName}>
        {row.original.isEditable ? (
          <TextField
            label={""}
            name={id}
            value={value}
            disabled={id === "paginas" && estatus === "2" ? true : false}
            onChange={(e) => {
              if (
                id === "paginas" && e.target.value != '0'
                ||
                id === 'justificacion' && e.target.value.length < 301
              ) {
                onHandleChange(e);
                setHelperText("");
                if (e.target.value !== "" && className !== "")
                  setClassName("is-valid");
              }
            }}
            onBlur={(e) => {
              updateData(index, 'isUpdated', true);
              updateData(index, id, e.target.value);
            }}
            className={className}
            helperText={helperText}
          />
        ) : (
          <p>{initialValue}</p>
        )}
      </div>
    );
  };

  return <>{renderSwitch()}</>;
}

// Evita re-renders del EditableCell durante el onchange del input de filtro
export default memo(InputCell, (prevProps, nextProps) => {
  return (
    prevProps.getValue === nextProps.getValue &&
    prevProps.isEditable === nextProps.isEditable
  );
});
