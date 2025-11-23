import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";
import Select from "../../../../components/formInputs/Select";
import TextFieldWithIconLeft from "../../../../components/formInputs/TextFieldIcon";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import TextArea from "../../../../components/formInputs/TextArea";
import {
  useGetMonedaQuery,
  useGetAreaPlaneacionQuery,
} from "../../store/store";
import { FormatMoney, filtrarNumeros } from "../../../../functions/utils";

export function InformacionProyecto({
  data,
  handleChange,
  values,
  errors,
  touched,
  onBlur,
  editable,
  setFieldValue,
}) {
  const { data: monedaData, isError, isLoading } = useGetMonedaQuery();
  const { data: areaPlaneacionRes } = useGetAreaPlaneacionQuery();

  const handleBlurMonto = (event, setFieldValue, values) => {
    const { value, name } = event.target;
    if (value) {
      const formatMoney = FormatMoney(value, 2);
      setFieldValue(name, formatMoney);
    }
  };

  const handleChangeMonto = (event, setFieldValue, values) => {
    const { value, name } = event.target;
    setFieldValue(name, filtrarNumeros(value));
  };
  return (
    <>
      <Row className="mt-5">
        <Col md={4}>
          <TextFieldDate
            label={"Fecha de inicio del proyecto*:"}
            name="fechaInicio"
            value={values?.fechaInicio}
            onChange={handleChange}
            helperText={touched.fechaInicio ? errors.fechaInicio : ""}
            className={
              touched.fechaInicio &&
              (errors.fechaInicio ? "is-invalid" : "is-valid")
            }
            onBlur={onBlur}
            disabled={!editable}
          />
        </Col>
        <Col md={4}>
          <TextFieldDate
            label={"Fecha de fin del proyecto*:"}
            name="fechaTermino"
            value={values?.fechaTermino}
            onChange={handleChange}
            helperText={touched.fechaTermino ? errors.fechaTermino : ""}
            className={
              touched.fechaTermino &&
              (errors.fechaTermino ? "is-invalid" : "is-valid")
            }
            onBlur={onBlur}
            disabled={!editable}
          />
        </Col>
        <Col md={4}>
          <Select
            label="Área de planeación*:"
            name="idAreaPlaneacion"
            value={values.idAreaPlaneacion}
            onChange={handleChange}
            onBlur={onBlur}
            options={areaPlaneacionRes}
            keyValue="primaryKey"
            keyTextValue="acronimo"
            helperText={touched.idAreaPlaneacion ? errors.idAreaPlaneacion : ""}
            className={
              touched.idAreaPlaneacion &&
              (errors.idAreaPlaneacion ? "is-invalid" : "is-valid")
            }
            disabled={!editable}
            keyStatus="estatus"
            hideDisabledOptions={true}
          />
        </Col>
      </Row>
      <Row>
        <Col md={4}>
          <TextFieldWithIconLeft
            label={"Monto solicitado:"}
            startIcon={faDollarSign}
            value={values.montoSolicitado}
            onChange={(e) => {
              handleChangeMonto(e, setFieldValue);
            }}
            name="montoSolicitado"
            onBlur={(e) => {
              handleBlurMonto(e, setFieldValue);
            }}
            disabled={!editable}
          />
        </Col>
        <Col md={4}>
          <Select
            label="Tipo de moneda*:"
            name="idTipoMoneda"
            value={values.idTipoMoneda}
            onChange={handleChange}
            options={monedaData}
            keyValue="primaryKey"
            keyTextValue="nombre"
            helperText={touched.idTipoMoneda ? errors.idTipoMoneda : ""}
            className={
              touched.idTipoMoneda &&
              (errors.idTipoMoneda ? "is-invalid" : "is-valid")
            }
            onBlur={onBlur}
            disabled={!editable}
            keyStatus="estatus"
            hideDisabledOptions={true}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12}>
          <TextArea
            rows={2}
            label="Objetivo general*:"
            name="objetivoGeneral"
            value={values?.objetivoGeneral}
            onChange={handleChange}
            helperText={touched.objetivoGeneral ? errors.objetivoGeneral : ""}
            className={
              touched.objetivoGeneral &&
              (errors.objetivoGeneral ? "is-invalid" : "is-valid")
            }
            onBlur={onBlur}
            disabled={!editable}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12}>
          <TextArea
            rows={2}
            label="Alcance:"
            name="alcance"
            value={values?.alcance}
            onChange={handleChange}
            onBlur={onBlur}
            disabled={!editable}
          />
        </Col>
      </Row>
    </>
  );
}
