import React, { useState } from "react";
import TextFieldIcon from "../../../../../components/formInputs/TextFieldIcon";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import Select from "../../../../../components/formInputs/Select";
import _ from "lodash";
import { PROFORMA } from "../../../../../constants/messages";
import IconButton from "../../../../../components/buttons/IconButton";
import Authorization from "../../../../../components/Authorization";

export const FormatMoney = (value, decimales = 2) => {
  try {
    value = value ? value.toString() : "0";
    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: decimales,
      maximumFractionDigits: decimales,
    };
    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
    let _value = value.replaceAll(",", "");
    _value = parseFloat(_value, decimales);
    let formatMoney = FORMAT_MONEY.format(_value).split("$")[1];
    return formatMoney;
  } catch (error) {
    return "0.00";
  }
};

function safeParseFloat(value, defaultValue = 0) {
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}

function filtrarNumeros(cadena) {
  const regex = /[\d.,]+/g;
  const numeros = cadena.match(regex);
  if (!numeros) {
    return ""; // Si no se encuentran números, devolvemos una cadena vacía
  }
  let result = numeros.join("");

  const numerosFiltrados = result;
  return numerosFiltrados;
}

export const findValue = (catalogo, id, keyCatalogo = "idTipoReintegro") => {
  if (!_.isEmpty(catalogo)) {
    let item = catalogo.find((s) => s[keyCatalogo] == id);
    if (item) {
      return item.nombre;
    }
  }
  return id;
};

const InputIconCell = ({
  getValue,
  row,
  disabled,
  typeCell,
  table,
  column,
  hasError,
  ...props
}) => {
  const { updateData } = table.options.meta;

  let value = getValue();

  const [tempValue, setTempValue] = useState(
    value ? FormatMoney(value) : value
  );

  const handleBlur = (event) => {
    let { value } = event.target;
    setTempValue(FormatMoney(value));
    updateData(row.index, column.id, value);
  };

  const handleChange = (event) => {
    let { value } = event.target;
    setTempValue(filtrarNumeros(value));
  };

  const [showError, setShowError] = useState(false);

  let className = "";
  let helperText = "";

  if (hasError) {
    let _value = value ? safeParseFloat(value) : value;
    let invalidValue = !_value && _value !== 0;
    className = invalidValue ? "is-invalid" : "is-valid";
    helperText = invalidValue ? "Dato requerido" : "";
  }

  return (
    <TextFieldIcon
      value={tempValue}
      onBlur={handleBlur}
      onChange={handleChange}
      maxLength={250}
      startIcon={faDollarSign}
      disabled={disabled}
      className={className}
      helperText={helperText}
    />
  );
};

export const LabelMoneyCell = ({ getValue, ...props }) => {
  let value = getValue();
  return <>{"$ " + FormatMoney(value)}</>;
};

export const validateCellIcon = (props) => {
  let { original } = props.row;

  return (
    <>
      {original.edicion ? (
        <InputIconCell {...props} />
      ) : (
        <LabelMoneyCell {...props} />
      )}
    </>
  );
};

const SelectCell = ({
  getValue,
  catalogo,
  table,
  row,
  column,
  hasError,
  editar,
  keyValue = "primaryKey",
  keyTextValue = "nombre",
}) => {
  let value = getValue();
  let { idTipo } = row.original;
  const { updateData } = table.options.meta;

  const handleChangue = (event) => {
    let { value } = event.target;
    let nombre = findValue(catalogo, value, keyValue);
    updateData(row.index, column.id, nombre);
    updateData(row.index, "idTipo", value);
  };

  let className = "";
  let helperText = "";
  if (hasError) {
    className = !idTipo ? "is-invalid" : "is-valid";
    helperText = !idTipo ? "Dato requerido" : "";
  }

  return (
    <Select
      value={idTipo}
      options={catalogo}
      keyValue={keyValue}
      keyTextValue={keyTextValue}
      onChange={handleChangue}
      className={className}
      helperText={helperText}
    />
  );
};

export const validateCellSelect = (props) => {
  let { original } = props.row;

  return (
    <>
      {original.edicion ? <SelectCell {...props} /> : <LabelCell {...props} />}
    </>
  );
};

export const LabelCell = ({ getValue, ...props }) => {
  let value = getValue();
  return <>{value}</>;
};

export const ActionCell = ({
  row,
  table,
  column,
  originalData,
  setDataTable,
  handleShowConfirmModal,
  setDeleteItems,
  isDetalle,
  modificar,
  dictamenValidado,
  ...props
}) => {
  const { updateData, removeRow } = table.options.meta;
  let { edicion, isFetched, idDdp } = row.original; //validar el id
  let { index } = row;

  //#region descartar
  const discard = () => {
    if (idDdp) {
      let originalRow = {
        ...originalData[index],
      };
      setDataTable((prev) => {
        let _prev = [...prev];
        _prev[index] = originalRow;
        return _prev;
      });
    } else {
      setDataTable((prev) => {
        let _prev = [...prev];
        _prev.splice(index, 1);
        return _prev;
      });
    }
  };
  const handleDiscard = () => {
    handleShowConfirmModal(PROFORMA.MSG009, discard);
  };
  //#endregion

  //#region Editar
  const handleEdit = () => {
    updateData(row.index, column.id, true);
  };
  //#endregion
  //#region Borrar
  const deleteItem = () => {
    setDeleteItems((prev) => [...prev, idDdp]);
    removeRow(index);
  };
  const handleDrop = () => {
    handleShowConfirmModal(PROFORMA.MSG007, deleteItem); //MSG17
  };
  //#endregion

  return (
    <Authorization process={"CON_SERV_DICT_DDP_ADMIN"}>
      {edicion && !isFetched ? (
        <IconButton
          type={"undo"}
          onClick={handleDiscard}
          iconSize="lg"
          // disabled={disabled}
          tableContainer
        />
      ) : (
        !isFetched && (
          <>
            <IconButton
              type={"edit"}
              onClick={handleEdit}
              iconSize="lg"
              disabled={isDetalle || !modificar || dictamenValidado}
              tooltip={"Editar"}
              tableContainer
            />
            <IconButton
              type={"drop"}
              onClick={handleDrop}
              iconSize="lg"
              disabled={isDetalle || !modificar || dictamenValidado}
              tooltip={"Eliminar"}
              tableContainer
            />
          </>
        )
      )}
    </Authorization>
  );
};

export const base64Doc = ``;
