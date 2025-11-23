import React, { useEffect, useState, memo } from "react";
import CustomDatepickerIcon from "../../../../components/shared/input/CustomDatepickerIcon";
import moment from "moment";

function DatepickerCell({ getValue, row, column, table }) {
  const initialValue = getValue();
  const { updateData } = table.options.meta;
  const { isEditable, fechaDocumentoErrorText } = row.original;
  const { index } = row;
  const { id } = column;

  const handleDate = (value) => {
    updateData(index, "isUpdated", true);
    updateData(index, id, value);
    setHelperText(null);
    setClassName("is-valid");
  };

  const [helperText, setHelperText] = useState(null);
  const [className, setClassName] = useState("");

  useEffect(() => {
    if (
      fechaDocumentoErrorText !== null &&
      helperText === null &&
      initialValue === "" &&
      id === "fechaDocumento"
    ) {
      setHelperText(fechaDocumentoErrorText);
      if (fechaDocumentoErrorText !== null) setClassName("is-invalid");
    } else if (
      id === "fechaDocumento" &&
      helperText !== null &&
      fechaDocumentoErrorText === null
    ) {
      setHelperText(null);
      setClassName("");
    }
  }, [fechaDocumentoErrorText, id, helperText, initialValue]);

  const renderSwitch = () => {
    if (!isEditable) {
      return (
        <>
          {initialValue !== null &&
            initialValue !== "" &&
            moment(initialValue).format("DD/MM/YYYY")}
        </>
      );
    } else {
      return (
        <>
          <p style={{ marginBottom: "0" }}>
            {initialValue !== "" &&
            initialValue !== null &&
            initialValue !== undefined
              ? moment(initialValue).format("DD/MM/YYYY")
              : ""}
          </p>
          <CustomDatepickerIcon
            name={"fechaDocumento"}
            value={initialValue === "" ? null : initialValue}
            disabled={false}
            maxDate={new Date()}
            onChange={handleDate}
            outputDateFormat="YYYY-MM-DD"
            inputDateFormat="mm/dd/yyyy"
            className={className}
          />
          {helperText && (
            <p
              style={{
                marginTop: "0.25rem",
                fontSize: "0.875em",
                color: "#dc3545",
              }}
            >
              {helperText}
            </p>
          )}
        </>
      );
    }
  };

  return <>{renderSwitch()}</>;
}

// Evita re-renders del EditableCell durante el onchange del input de filtro
export default memo(DatepickerCell, (prevProps, nextProps) => {
  return (
    prevProps.getValue === nextProps.getValue &&
    prevProps.isEditable === nextProps.isEditable
  );
});
