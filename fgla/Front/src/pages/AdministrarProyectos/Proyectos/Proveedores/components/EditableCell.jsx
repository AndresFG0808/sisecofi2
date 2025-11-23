import React, { useEffect, useState, memo } from "react";
import { Col, Row } from "react-bootstrap";
import { Select } from "../../../../../components/formInputs";
import isEmpty from "lodash/isEmpty";
import SelectWrapper from "./SelectWrapper";
// import CustomDatepickerIcon from "../../../../../components/shared/input/CustomDatepickerIcon";
import moment from "moment";
import TextFieldDate from "../../../../../components/formInputs/TextFieldDate";
const validations = ["si", "sí", "s"];
function EditableCell({
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
  tipoProcedimiento = "",
  validationKey = "",
  onChange,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [helperText, setHelperText] = useState("");
  const [className, setClassName] = useState("");
  const { updateData } = table.options.meta;
  const { requiredFields } = row.original;
  const { accessorKey } = column.columnDef;
  const { index } = row;
  const { id } = column;
  const onToggleEdit = (isOpen) => row.toggleSelected(isOpen);
  const prevCell = type === "date" ? row.original[validationKey] : null;
  const isValidDate = validations.includes(
    options?.find((option) => option.id === prevCell)?.texto?.toLowerCase()
  );

  useEffect(() => {
    if (initialValue !== "" && value === "") setValue(initialValue);
  }, [value, initialValue]);

  const onHandleChange = (e) => {
    if (isEmpty(helperText) === false) {
      setClassName("is-valid");
      setHelperText("");
    }
    updateData(index, id, e.target.value);
    setValue(e.target.value);
    onChange(table);
  };

  const onClickSelect = () => {
    if (isEmpty(options) && id === "idProveedor" && onEmptyActiveProviders)
      onEmptyActiveProviders();
  };

  const handleDate = (value) => {
    setValue(value);
    updateData(index, id, value);
    onChange(table);
  };

  useEffect(() => {
    if (
      requiredFields[0].requiredField === accessorKey &&
      requiredFields[0].helperText &&
      value === "" &&
      helperText === ""
    ) {
      setHelperText("Dato requerido");
      setClassName("is-invalid");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [requiredFields, accessorKey]);

  useEffect(() => {
    if (
      isEditable === false &&
      initialValue !== value &&
      initialValue !== "" &&
      value !== ""
    )
      updateData(index, id, initialValue);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialValue, isEditable, value]);

  useEffect(() => {
    if (row.original.isNewProvider !== true)
      onToggleEdit(row.original.isEditable);
    // isActiveAddProvider && onToggleEdit(row.original.isEditable);
  }, [
    isActiveAddProvider,
    row.original.isEditable,
    onToggleEdit,
    row.original.isNewProvider,
    id,
  ]);

  const renderSwitch = () => {
    if (!isEditable) {
      switch (type) {
        case "select":
          return (
            <div className={className}>
              <SelectWrapper options={options} value={value} type={type} />
            </div>
          );
        case "date":
          return <>{value !== null && moment(value).format("DD/MM/YYYY")}</>;
        default:
          return <>{value}</>;
      }
    } else {
      switch (type) {
        case "select":
          return (
            <Row className={rowClassName}>
              <Col>
                <Select
                  name={""}
                  onClick={onClickSelect}
                  value={value}
                  onChange={onHandleChange}
                  options={options}
                  keyValue={keyValue}
                  keyTextValue={keyTextValue}
                  readOnly={row.getIsSelected() === false}
                  disabled={row.getIsSelected() === false}
                  className={className}
                  helperText={helperText}
                  defaultOptionText="Seleccione"
                />
              </Col>
            </Row>
          );
        case "date":
          return (
            <>
              <TextFieldDate
                name={"fechaAsignacion"}
                value={value}
                onChange={(event) => handleDate(event.target.value)}
                helperText={
                  isValidDate && !!!value ? "Favor de ingresar una fecha" : ""
                }
                className={isValidDate && !!!value ? "is-invalid" : ""}
              />
            </>
          );
        default:
          return <>{value}</>;
      }
    }
  };

  return <>{renderSwitch()}</>;
}

// Evita re-renders del EditableCell durante el onchange del input de filtro
export default memo(EditableCell, (prevProps, nextProps) => {
  return (
    prevProps.getValue === nextProps.getValue &&
    prevProps.isEditable === nextProps.isEditable
  );
});
