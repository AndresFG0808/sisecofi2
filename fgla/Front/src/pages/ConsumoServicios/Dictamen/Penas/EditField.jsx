import React, { useEffect, useState, memo } from "react";
import { Col, Row } from "react-bootstrap";
import isEmpty from "lodash/isEmpty";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import TextFieldWithIconLeft from "../../../../components/formInputs/TextFieldIcon";
import Authorization from "../../../../components/Authorization";

function EditableCell({
  getValue,
  row,
  column,
  table,
  options,
  keyValue = "id",
  keyTextValue = "texto",
  rowClassName = "",
  estatus,
  isEditable,
  isActiveAddProvider,
  onEmptyActiveProviders,
  tableType,
}) {
  console.log("dpiekdoikepodkepk 2 ", estatus)

  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [helperText, setHelperText] = useState("");
  const [className, setClassName] = useState("");

  useEffect(() => {
    if (initialValue !== value) {
      setValue(initialValue);
    }
  }, [initialValue]);

  const {
    cantidadServiciosSatRequired,
    cantidadServiciosSatHelperText,
    cantidadServiciosCCHelperText,
    montoHelperText,
  } = row.original;

  useEffect(() => {
    if (montoHelperText !== "" && helperText === "" && value === "") {
      setHelperText(montoHelperText || montoHelperText);
      setClassName("is-invalid");
    }
  }, [montoHelperText]);

  const { updateData } = table.options.meta;
  const { accessorKey } = column.columnDef;
  const { index } = row;
  const { id } = column;
  const onToggleEdit = (isOpen) => row.toggleSelected(isOpen);

  const onHandleChange = (e) => {
    if (isEmpty(helperText) === false) {
      setClassName("is-valid");
      setHelperText("");
    }
    setValue(e.target.value);
  };

  useEffect(() => {
    row.original.isNewProvider !== true &&
      onToggleEdit(row.original.isEditable);
    // isActiveAddProvider && onToggleEdit(row.original.isEditable);
  }, [isActiveAddProvider]);

  const onGetProcess = (tableType) => {
    let process = '';
    if (tableType == 1) {
      process = 'CON_SERV_DICT_PCONT_MONTO';
    }
    if (tableType == 2) {
      process = 'CON_SERV_DICT_PCONV_MONTO';
    }
    if (tableType == 3) {
      process = 'CON_SERV_DICT_DEDUC_MONTO';
    }
    return process;
  }

  const renderSwitch = () => {
    return (
      <div className={rowClassName}>
        <Authorization process={onGetProcess(tableType)} redirect={
          <p>{value !== "" && value !== null ? "$ " + value : value}</p>
        } >
          {row.original.isEditable ? (
            <TextFieldWithIconLeft
              label={""}
              startIcon={null}
              name={id}
              value={value}
              disabled={estatus === "Dictaminado" ? !isEditable : true}
              onChange={(e) => {
                const regex = /^(?:\d+|\d+\.\d{0,2})?$/;
                if (regex.test(e.target.value) || e.target.value === "") {
                  onHandleChange(e);
                  setHelperText("");
                  if (e.target.value !== "" && className !== "")
                    setClassName("is-valid");
                }
              }}
              onBlur={(e) => updateData(index, id, e.target.value)}
              className={className}
              helperText={helperText}
            />
          ) : (
            <p>{value !== "" && value !== null ? "$ " + value : value}</p>
          )}
        </Authorization>
      </div>
    );
  };

  return <>{renderSwitch()}</>;
}

// Evita re-renders del EditableCell durante el onchange del input de filtro
export default memo(EditableCell, (prevProps, nextProps) => {
  return (
    prevProps.estatus === nextProps.estatus &&
    prevProps.getValue === nextProps.getValue &&
    prevProps.isEditable === nextProps.isEditable
  );
});
