import React, { useEffect, useState } from "react";
import { Col, Form, Row } from "react-bootstrap";

import { useFormikContext } from "formik";
import { mapOptionsSelect } from "../Components/ValidationSchema";
import FormCheckList from "../../../../../components/formInputs/FormCheckList";
import {
  SelectInput,
  DateInput,
  TextInput,
  TextInputErrors,
  CheckboxGroup,
} from "../Components/InputsComponents";
// import { useDispatch } from "react-redux";
// import { GetPlantillaById } from "../../../../../store/infoComites/infoComitesActions";
export default function FormDatosGenerales({
  disableForm = false,
  catalogos,
  infoComites,
}) {
  // const dispatch = useDispatch();

  var { plantillasVigentes } = infoComites;

  var {
    contratoConvenio,
    contratos,
    comite,
    afectacion,
    sesionNumero,
    sesionClasificacion,
  } = catalogos.catalogos;

  // var afectacionList = mapOptionsSelect(afectacion);
  const { handleSubmit, values, setFieldValue } = useFormikContext();

  const [disablePlantilla, setDisablePlantilla] = useState(false);

  let { idComite, informacionArchivos, idContratoConvenio } = values;

  useEffect(() => {
    let notRequiredComite = ["cm", "contrato"];
    let hasDocuments =
      Array.isArray(informacionArchivos) &&
      informacionArchivos.filter((s) => s.tamanoMb && s.tamanoMb > 0).length >
        0;

    let _disable = false;
    if (idComite) {
      let options = mapOptionsSelect(comite);
      let option = options.find((s) => s.idValue == idComite);
      if (
        option?.value &&
        notRequiredComite.includes(option.value.toLowerCase()) &&
        !hasDocuments
      ) {
        _disable = true;
        setFieldValue("idPlantilla", "");
      }
      setDisablePlantilla(_disable);
    }
  }, [comite, idComite, informacionArchivos, setFieldValue]);

  const [requiredComite, setRequiredComite] = useState(false);
  const [requiredContrato, setrequiredContrato] = useState(false);

  useEffect(() => {
    let notRequiredComite = ["cm", "contrato"];
    let notRequiredContratoConvenio = ["0"];
    const requiredValidation = (
      value,
      options,
      notRequired = notRequiredComite
    ) => {
      let option = options.find((s) => s.idValue == value);

      if (option?.value && notRequired.includes(option.value.toLowerCase())) {
        return false;
      }
      return true;
    };

    let comiteOptions = mapOptionsSelect(comite);
    let contratoConvenioOptions = mapOptionsSelect(contratoConvenio);

    let requiredComite = requiredValidation(
      idComite,
      comiteOptions,
      notRequiredComite
    );
    let requiredContratoConvenio = requiredValidation(
      idContratoConvenio,
      contratoConvenioOptions,
      notRequiredContratoConvenio
    );
    setRequiredComite(requiredComite);
    setrequiredContrato(requiredContratoConvenio);
  }, [comite, contratoConvenio, idComite, idContratoConvenio]);

  return (
    <Form autoComplete="off" onSubmit={handleSubmit}>
      <Row>
        <Col md={4}>
          <SelectInput
            label="Contrato/Convenio*:"
            name="idContratoConvenio"
            options={mapOptionsSelect(contratoConvenio)}
            disableForm={disableForm}
          />
        </Col>
        <Col md={4}>
          <SelectInput
            label={requiredContrato ? "Contratos*:" : "Contratos:"}
            name="idContrato"
            options={mapOptionsSelect(contratos, "idContrato", "nombreCorto")}
            disableForm={disableForm}
            showClases={requiredContrato}
          />
        </Col>
        <Col md={4}>
          <DateInput
            label="Fecha de sesión*:"
            name="fechaSesion"
            disableForm={disableForm}
          />
        </Col>
      </Row>
      <Row>
        <Col md={4}>
          <SelectInput
            label="Comité*:"
            name="idComite"
            options={mapOptionsSelect(comite)}
            disableForm={disableForm}
          />
        </Col>
        <Col md={4}>
          <TextInputErrors
            label={requiredComite ? "Acuerdo*:" : "Acuerdo:"}
            name="acuerdo"
            disableForm={disableForm}
            showClases={requiredComite}
          />
        </Col>

        <Col md={4}>
          <Row>
            {
              //#region * Campo con dos inputs por ahora se muestran asi
            }
            <Col md={6}>
              <SelectInput
                label={requiredComite ? "Sesión*:" : "Sesión:"}
                name="idSesionNumero"
                options={mapOptionsSelect(sesionNumero)}
                disableForm={disableForm}
                showClases={requiredComite}
              />
            </Col>
            <Col md={6}>
              <SelectInput
                label="‎" //Caracter invisible para que no se rompa el estilo
                name="idSesionClasificacion"
                options={mapOptionsSelect(sesionClasificacion)}
                disableForm={disableForm}
                showClases={requiredComite}
              />
            </Col>
            {
              //#endregion
            }
          </Row>
        </Col>
      </Row>
      <Row>
        <Col md={4}>
          <TextInputErrors
            label="Vigencia:"
            name="vigencia"
            disableForm={disableForm}
            // showClases={false}
          />
        </Col>

        <Col md={4}>
          <SelectInput
            label={requiredComite ? "Plantilla*:" : "Plantilla:"}
            name="idPlantilla"
            options={mapOptionsSelect(
              plantillasVigentes,
              "idPlantillaVigente",
              "nombre"
            )}
            disableForm={disableForm || disablePlantilla}
            showClases={requiredComite}
          />
        </Col>
      </Row>

      {/* <Row>
        <CheckboxGroup
          options={mapOptionsSelect(afectacion)}
          labelTitle={"Afectación:"}
          name={"idsAfectacion"}
          disableForm={disableForm}
        />
      </Row> */}
      <Row>
        <FormCheckList
          options={afectacion}
          labelTitle={"Afectación:"}
          name={"idsAfectacion"}
          disabled={disableForm}
          showClasses={false}
        />
      </Row>
    </Form>
  );
}
