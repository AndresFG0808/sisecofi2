import React, { useState, useEffect } from "react";
import { useToast } from "../../../../hooks/useToast";
import { SelectInput } from "./inputs";
import { Row, Col, Button, Form } from "react-bootstrap";

import { REGISTRAR_REINTEGROS } from "../../../../constants/messages";
import _ from "lodash";
import Authorization from "../../../../components/Authorization";
import { useFormikContext } from "formik";

export default function ReintegrosFilter({
  handleShowMessage,
  handleShowConfirmModal,
  obtenerReintegros,
  disabledFilter,
  contratosVigentesCat,
  getContratosCat,
  contratosCat,
}) {
  const { errorToast } = useToast();
  const { handleSubmit, handleChange, setFieldValue, isValid, values } =
    useFormikContext();

  const handleChangeContratoVig = (event, handleChange, setFieldValue) => {
    handleChange(event);
    let { value } = event.target;

    if (value) {
      setFieldValue("contrato", "");
      getContratosCat({ vigencia: value, todos: true }).then((response) => {
        if (response?.error || _.isEmpty(response?.data)) {
          handleShowMessage(REGISTRAR_REINTEGROS.MSG009);
        }
      });
    }
  };

  let { contratosVigentes = "" } = values ?? {};

  useEffect(() => {
    if (!contratosVigentes) {
      setFieldValue("contrato", "");
    }
  }, [contratosVigentes, setFieldValue]);

  return (
    <>
      <Form autoComplete="off" onSubmit={handleSubmit}>
        <Row>
          <Authorization process={"CON_SERV_DICT_REINT_CONS"}>
            <Col md={4}>
              <SelectInput
                label={"Contratos vigentes*:"}
                name={"contratosVigentes"}
                options={contratosVigentesCat}
                keyValue="nombre"
                disabled={disabledFilter}
                onChange={(event) =>
                  handleChangeContratoVig(event, handleChange, setFieldValue)
                }
                showClasses={!disabledFilter}
              />
            </Col>
            <Col md={4}>
              <SelectInput
                label={"Contratos*:"}
                name={"contrato"}
                options={contratosCat}
                keyValue={"idContrato"}
                keyTextValue={"nombreCorto"}
                disabled={
                  disabledFilter ||
                  _.isEmpty(contratosCat) ||
                  !contratosVigentes
                }
                showClasses={!(
                  disabledFilter ||
                  _.isEmpty(contratosCat) ||
                  !contratosVigentes)
                }
              />
            </Col>
            <Col md={4}>
              <Button
                variant="gray"
                className="btn-sm ms-2 waves-effect waves-light mt-4"
                type="submit"
                disabled={disabledFilter}
                onClick={() =>
                  !isValid && errorToast(REGISTRAR_REINTEGROS.MSG005)
                }
              >
                Buscar
              </Button>
            </Col>
          </Authorization>
        </Row>
      </Form>
    </>
  );
}
