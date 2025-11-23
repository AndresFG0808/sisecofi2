import React, { useState } from "react";
import { Col, Row } from "react-bootstrap";
import Select from "../../../../../components/formInputs/Select";
import TextField from "../../../../../components/formInputs/TextField";

export function SelectDependent({
  value,
  handleChange,
  handleBlur,
  options,
  touched,
  errors,
  index,
  data,
  disabled,
}) {
  const [readerValue, setReaderValue] = useState({
    administracionCentralPatrocinadoraNombre: data?.admonCentrales?.find(
      (admon) => admon.primaryKey === value
    )?.administracion,
    administradorCentralPatrocinador: data?.admonCentrales
      ?.find((admon) => admon.primaryKey === value)
      ?.administradores?.find((admon) => admon.estatus === true)?.administrador,
  });
  const onChange = (e) => {
    const admon = options?.find(
      (option) => option?.primaryKey === parseInt(e.target.value)
    );
    setReaderValue({
      administracionCentralPatrocinadoraNombre: admon?.administracion,
      administradorCentralPatrocinador:
        admon?.administradores?.find((admon) => admon.estatus === true)
          ?.administrador || "",
    });
    handleChange(e);
  };

  return (
    <>
      <Row key={index}>
        <Col>
          <Select
            label={`${
              index === 0 ? "Administración central patrocinadora*:" : ""
            }`}
            name={`idAdmonCentrales.${index}`}
            onBlur={handleBlur}
            value={value}
            onChange={onChange}
            options={options}
            keyValue="primaryKey"
            keyTextValue="acronimo"
            helperText={
              touched?.idAdmonCentrales?.[index]
                ? errors?.idAdmonCentrales?.[index]
                : ""
            }
            className={
              touched?.idAdmonCentrales?.[index] &&
              (errors?.idAdmonCentrales?.[index] ? "is-invalid" : "is-valid")
            }
            disabled={disabled}
            keyStatus="estatus"
            hideDisabledOptions={true}
          />
        </Col>
        <Col>
          <TextField
            label={`${
              index === 0 ? "Nombre de la admón. central patrocinadora:" : ""
            }`}
            value={
              value ? readerValue?.administracionCentralPatrocinadoraNombre : ""
            }
            name="administracionCentralPatrocinadoraNombre"
            placeholder={data?.admonCentrales?.[index]?.administracion || ""}
            readOnly
            disabled
          />
        </Col>
        <Col>
          <TextField
            value={value ? readerValue?.administradorCentralPatrocinador : ""}
            label={`${
              index === 0 ? "Administrador central patrocinador:" : ""
            }`}
            name="administradorCentralPatrocinador"
            placeholder={
              data?.admonCentrales
                ?.find((admon) => admon.primaryKey === value)
                ?.administradores?.find((admon) => admon.estatus === true)
                ?.administrador || ""
            }
            readOnly
            disabled
          />
        </Col>
      </Row>
    </>
  );
}
