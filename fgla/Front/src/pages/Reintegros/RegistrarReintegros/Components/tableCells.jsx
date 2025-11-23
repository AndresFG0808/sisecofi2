import React, { useState } from "react";
import Select from "../../../../components/formInputs/Select";
import TextField from "../../../../components/formInputs/TextField";
import TextFieldIcon from "../../../../components/formInputs/TextFieldIcon";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";
import IconButton from "../../../../components/buttons/IconButton";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import _ from "lodash";
import moment from "moment";
import Authorization from "../../../../components/Authorization";
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

export function convertirFecha(fecha, format = "YYYY-MM-DD") {
  // Formatos permitidos
  const formatos = ["DD/MM/YYYY", "YYYY-MM-DDTHH:mm:ss", "YYYY-MM-DD"];

  const fechaMoment = moment(fecha, formatos, true);
  if (!fechaMoment.isValid()) {
    return "";
  }
  return fechaMoment.format(format);
}

function safeParseFloat(value, defaultValue = 0) {
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
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

export const LabelCell = ({ getValue, ...props }) => {
  let value = getValue();
  return <>{value}</>;
};
export const LabelMoneyCell = ({ getValue, ...props }) => {
  let value = getValue();
  return <>{"$ " + FormatMoney(value)}</>;
};

const InputIconTotalCell = ({
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
  let { interes, importe } = row?.original;

  let value = getValue();
  let sumTotal = safeParseFloat(interes) + safeParseFloat(importe);

  let valueTotal = safeParseFloat(value);

  if (valueTotal !== sumTotal) {
    // updateData(row.index, column.id, sumTotal);
  }

  const [tempValue, setTempValue] = useState(FormatMoney(sumTotal));

  let className = "";
  let helperText = "";

  if (hasError) {
    let _value = safeParseFloat(tempValue);
    className = !_value ? "is-invalid" : "is-valid";
    helperText = !_value ? "Su valor no puede ser cero." : "";
  }

  return (
    <TextFieldIcon
      value={tempValue}
      className={className}
      maxLength={250}
      startIcon={faDollarSign}
      disabled={disabled}
      helperText={helperText}
    />
  );
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

  const [tempValue, setTempValue] = useState(FormatMoney(value));

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
    let _value = safeParseFloat(value);
    className = !_value ? "is-invalid" : "is-valid";
    helperText = !_value ? "Su valor no puede ser cero." : "";
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

const InputTextCell = ({
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

  const [tempValue, setTempValue] = useState(value);

  const handleBlur = (event) => {
    let { value } = event.target;
    updateData(row.index, column.id, value);
  };

  const handleChange = (event) => {
    let { value } = event.target;
    setTempValue(value);
  };

  return (
    <TextField
      value={tempValue}
      onBlur={handleBlur}
      onChange={handleChange}
      className={hasError ? (!tempValue ? "is-invalid" : "is-valid") : ""}
      maxLength={250}
    />
  );
};
const InputTextDateCell = ({
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

  // let value = getValue();
  let { fechaReintegro } = row.original;

  const [tempValue, setTempValue] = useState(fechaReintegro);

  // const handleBlur = (event) => {
  //   let { value } = event.target;
  //   if (value) {
  //     updateData(row.index, column.id, value);
  //     updateData(row.index, "fecha", convertirFecha(value));
  //   }
  // };

  const handleChange = (event) => {
    let { value } = event.target;
    if (value) {
      setTempValue(value);
      let fechaValue = convertirFecha(value, "DD/MM/YYYY");
      updateData(row.index, "fechaReintegro", value);
      updateData(row.index, column.id, fechaValue);
    }
  };

  const hoy = new Date().toISOString().split("T")[0];
  let className = "";
  let helperText = "";

  if (hasError) {
    className = !fechaReintegro ? "is-invalid" : "is-valid";
    helperText = !fechaReintegro ? "Dato requerido" : "";
  }

  return (
    <TextFieldDate
      value={tempValue}
      // onBlur={handleBlur}
      onChange={handleChange}
      maxDate={hoy}
      className={className}
      helperText={helperText}
    />
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
  let { idTipoReintegro } = row.original;
  const { updateData } = table.options.meta;

  const handleChangue = (event) => {
    let { value } = event.target;
    let nombre = findValue(catalogo, value);
    updateData(row.index, column.id, nombre);
    updateData(row.index, "idTipoReintegro", value);
  };

  let className = "";
  let helperText = "";
  if (hasError) {
    className = !idTipoReintegro ? "is-invalid" : "is-valid";
    helperText = !idTipoReintegro ? "Dato requerido" : "";
  }

  return (
    <Select
      value={idTipoReintegro}
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

export const validateCellText = (props) => {
  let { original } = props.row;

  return (
    <>
      {original.edicion ? (
        <InputTextCell {...props} />
      ) : (
        <LabelCell {...props} />
      )}
    </>
  );
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
export const validateCellDate = (props) => {
  let { original } = props.row;

  return (
    <>
      {original.edicion ? (
        <InputTextDateCell {...props} />
      ) : (
        <LabelCell {...props} />
      )}
    </>
  );
};
export const validateCellTotalIcon = (props) => {
  let { original } = props.row;

  return (
    <>
      {original.edicion ? (
        <InputIconTotalCell {...props} />
      ) : (
        <LabelMoneyCell {...props} />
      )}
    </>
  );
};

export const ActionCell = ({
  row,
  table,
  column,
  editar,
  handleDiscard,
  handleDrop,
  ...props
}) => {
  const { updateData, removeRow } = table.options.meta;
  let { edicion, idReintegrosAsociados } = row.original;
  let { index } = row;

  //#region Editar
  const handleEdit = () => {
    updateData(row.index, column.id, true);
  };
  //#endregion
  //#region Borrar

  //#endregion

  return (
    <Authorization process={"CON_SERV_DICT_REINT_ADMIN"}>
      {edicion ? (
        <IconButton
          type={"undo"}
          onClick={() => handleDiscard(index, idReintegrosAsociados)}
          iconSize="lg"
          tooltip={"Descartar"}
          // disabled={disabled}
          tableContainer
        />
      ) : (
        <>
          <IconButton
            type={"edit"}
            onClick={handleEdit}
            iconSize="lg"
            disabled={!editar}
            tooltip={"Editar"}
            tableContainer
          />
          <IconButton
            type={"drop"}
            onClick={() => handleDrop(index, removeRow)}
            iconSize="lg"
            disabled={!editar}
            tooltip={"Eliminar"}
            tableContainer
          />
        </>
      )}
    </Authorization>
  );
};
