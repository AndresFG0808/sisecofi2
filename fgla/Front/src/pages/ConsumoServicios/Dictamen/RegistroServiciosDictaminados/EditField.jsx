import React, { useEffect, useState, memo } from "react";
import TextFieldWithIconLeft from "../../../../components/formInputs/TextFieldIcon";
import Authorization from "../../../../components/Authorization";

const OPTIONS_MONEY = { style: "currency", currency: "USD" };
const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
const FORMAT_NUMBER = new Intl.NumberFormat("en-US");
const FORMAT_NUMBER_DECIMALS = new Intl.NumberFormat("en-US", {
  minimumFractionDigits: 0,
  maximumFractionDigits: 6,
  useGrouping: true,
});

function EditableCell({
  getValue,
  row,
  column,
  table,
  rowClassName = "",
  isEditable,
  setDataTable,
  actualizarServicios,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [helperText, setHelperText] = useState("");
  const [errorsRow, setErrosRow] = useState(null);
  const [error, setError] = useState("");
  const { index } = row;

  useEffect(() => {
    setValue(value ? value.toString() : '0');
  }, [initialValue]);

  useEffect(() => {
    setErrosRow(row.original.errors || null);
  }, [row]);

  const [className, setClassName] = useState("");
  const { updateDatapromise } = table.options.meta;
  const { revertData } = table.options.meta;
  const { id } = column;

  const formatNumber = (num) => {
    if (!num) return "";
    const parts = num.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
  };

  const validateNumber = (inputValue) => {
    const unformattedValue = unformatNumber(inputValue);
    const [integerPart, decimalPart] = unformattedValue.split(".");

    if (unformattedValue.startsWith("-")) {
      setError("");
      return false;
    }

    if (/[^0-9.]/.test(unformattedValue) || unformattedValue.includes("-")) {
      setError("");
      return false;
    }

    if (integerPart.length > 12 || (decimalPart && decimalPart.length > 6)) {
      setError("Número inválido. Máximo 12 dígitos enteros y 6 decimales.");
      return false;
    }

    setError("");
    return true;
  };

  const unformatNumber = (num) => {
    if (!num) return "";
    return num.toString().replace(/,/g, "");
  };

  const realizaCalculos = async (inputValue) => {
    let { cantidadServiciosCc, cantidadServiciosSat, precioUnitario } = row.original;

    let cantidadSAT = id === "cantidadServiciosSat" ? Number(inputValue) : cantidadServiciosSat;
    let cantidadCC = id === "cantidadServiciosCc" ? Number(inputValue) : cantidadServiciosCc;
    let cantidadServicios = Number(cantidadSAT) + Number(cantidadCC);
    let montoDictaminado = cantidadServicios * precioUnitario;

    setDataTable(prevDataTable => {

      const newDataTable = prevDataTable.map((item) => {
        const newItem = { ...item };
        if (item.consecutivo === row.original.consecutivo) {
          newItem.cantidadServiciosSat = id === "cantidadServiciosSat" ? inputValue : cantidadServiciosSat;
          newItem.cantidadServiciosCc = id === "cantidadServiciosCc" ? inputValue : cantidadServiciosCc;
          newItem.cantidadTotalServicios = cantidadServicios;
          newItem.cantidadTotalServiciosView = FORMAT_NUMBER_DECIMALS.format(cantidadServicios);
          newItem.montoDictaminado = montoDictaminado;
          newItem.montoDictaminadoView = "$ " + FORMAT_MONEY.format(montoDictaminado).split("$")[1]
        }
        return { ...newItem };
      });

      revertData(newDataTable);
      return newDataTable;
    });
  }

  const onHandleChange = (e) => {
    const inputValue = e.target.value.trim();
    const unformattedValue = unformatNumber(inputValue);

    if (validateNumber(unformattedValue)) {
      const formattedValue = formatNumber(unformattedValue);
      setValue((prevValue) => (prevValue >= 0 ? inputValue : formattedValue));
      realizaCalculos(unformattedValue);
    }
  };
  const handleBlur = (e) => {
    const unformattedValue = unformatNumber(e.target.value);
    updateDatapromise(index, id, unformattedValue).then(() => {
      actualizarServicios();
    });
  };

  const renderField = () => {
    return (
      <div className={rowClassName}>
        <Authorization process={"CON_SERV_ADMIN_DICT"} redirect={value}>
          <TextFieldWithIconLeft
            label={""}
            startIcon={null}
            name={id}
            value={value || ""}
            disabled={isEditable}
            onChange={(e) => {
              onHandleChange(e);
              setHelperText("");
              if (e.target.value !== "" && className !== "")
                setClassName("is-valid");
            }}
            onBlur={handleBlur}
            className={
              errorsRow &&
              (errorsRow[column.id] && !value ? "is-invalid" : "is-valid")
            }
            helperText={
              errorsRow && (errorsRow[column.id] ? "Dato requerido" : "")
            }
          />
        </Authorization>
      </div>
    );
  };

  return <>{renderField()}</>;
}

// Evita re-renders del EditableCell durante el onchange del input de filtro
export default memo(EditableCell, (prevProps, nextProps) => {
  return (
    prevProps.getValue === nextProps.getValue &&
    prevProps.isEditable === nextProps.isEditable
  );
});