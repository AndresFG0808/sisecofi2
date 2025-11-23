import React, { useEffect, useState } from "react";
import { Row, Col } from "react-bootstrap";
import Select from "../../../../components/formInputs/Select";
import TextField from "../../../../components/formInputs/TextField";
import IconButton from "../../../../components/buttons/IconButton";
import BasicModal from "../../../../modals/BasicModal";
import { Tooltip } from "../../../../components/Tooltip";
import { MODIFICAR_PROYECTOS } from "../../../../constants/messages";
import LabelValue from "../../../../components/LabelValue";
import {
  useGetAdministracionCentralPatrocinadoraQuery,
  useGetAdministracionParticipanteQuery,
  useGetAdministracionPatrocinadoraQuery,
  useGetClasificaciónQuery,
  useGetFinanciamientoQuery,
  useGetProcedimientosQuery,
  useLazyGetAdministracionCentralPatrocinadoraQuery,
} from "../../store";
import Loader from "../../../../components/Loader";
import { handleReadOnlyValues } from "./utils";
import { FieldArray } from "formik";
import { SelectDependent } from "./Components/SelectDependent";

export function AdministracionProyecto({
  data,
  handleChange,
  values,
  errors,
  touched,
  onBlur,
  handleBlur,
  editable,
}) {
  const { idAdmonCentrales, idAdmonPatrocinadora } = values;
  const [selectedIndex, setSelectedIndex] = useState();

  const { data: financiamientoData, isFetching: financiamientoLoading } =
    useGetFinanciamientoQuery();
  const { data: procedimientosData, isFetching: procedimientosLoading } =
    useGetProcedimientosQuery();
  const { data: clasificacionData, isFetching: clasificacionLoading } =
    useGetClasificaciónQuery();
  const {
    data: admonPatrocinadoraData,
    isFetching: admonPatrocinadoraLoading,
  } = useGetAdministracionPatrocinadoraQuery();
  const { data: admonParticipanteData, isFetching: admonParticipanteLoading } =
    useGetAdministracionParticipanteQuery();
  const [
    getAdmonCentralPatrocinadora,
    {
      data: admonCentralPatrocinadoraData,
      isFetching: admonCentralPatrocinadoraLoading,
    },
  ] = useLazyGetAdministracionCentralPatrocinadoraQuery();
  const [show, setShow] = useState(false);

  const [admonsReadOnly, setAdmonsReadOnly] = useState({
    idAdmonPatrocinadora: {
      administracionPatrocinadora: "",
      administradorPatrocinador: "",
    },
    administracionCentralPatrocinadora: {
      administracionCentralPatrocinadora: "",
      administradorCentralPatrocinador: "",
    },
    idAdmonParticipante: {
      administracionParticipante: "",
      administradorParticipante: "",
    },
  });

  useEffect(() => {
    if (idAdmonPatrocinadora) {
      getAdmonCentralPatrocinadora(values?.idAdmonPatrocinadora);
    }
  }, [idAdmonPatrocinadora]);

  const changeAdministracion = (event) => {
    const { value: admonCentralId, name } = event.target;
    handleChange(event);
    switch (name) {
      case "idAdmonPatrocinadora": {
        getAdmonCentralPatrocinadora(admonCentralId);
        handleReadOnlyValues(
          admonCentralId,
          name,
          admonPatrocinadoraData,
          setAdmonsReadOnly,
          "administracionPatrocinadora",
          "administradorPatrocinador"
        );
        break;
      }
      case "idAdmonParticipante": {
        handleReadOnlyValues(
          admonCentralId,
          name,
          admonPatrocinadoraData,
          setAdmonsReadOnly,
          "administracionParticipante",
          "administradorParticipante"
        );
        break;
      }
    }
  };

  return (
    <>
      {financiamientoLoading ||
      procedimientosLoading ||
      clasificacionLoading ||
      admonPatrocinadoraLoading ||
      admonParticipanteLoading ||
      admonCentralPatrocinadoraLoading ? (
        <Loader />
      ) : null}
      <Row>
        <Col md={4}>
          <Select
            handleBlur={onBlur}
            label="Administración patrocinadora*:"
            name="idAdmonPatrocinadora"
            value={values?.idAdmonPatrocinadora}
            onChange={changeAdministracion}
            options={admonPatrocinadoraData}
            keyValue="primaryKey"
            keyTextValue="acronimo"
            helperText={
              touched.idAdmonPatrocinadora ? errors.idAdmonPatrocinadora : ""
            }
            className={
              touched.idAdmonPatrocinadora &&
              (errors.idAdmonPatrocinadora ? "is-invalid" : "is-valid")
            }
            onBlur={onBlur}
            disabled={!editable}
            keyStatus="estatus"
            hideDisabledOptions={true}
          />
        </Col>
        <Col md={4}>
          <TextField
            label="Nombre de la admón. patrocinadora:"
            name="administracionPatrocinadoraNombre"
            value={
              values?.idAdmonPatrocinadora
                ? admonsReadOnly?.idAdmonPatrocinadora
                    ?.administracionPatrocinadora
                : ""
            }
            disabled={true}
            placeholder={data?.admonPatrocinadora?.administracion || ""}
          />
        </Col>
        <Col md={4}>
          <TextField
            label="Administrador patrocinador:"
            name="administradorPatrocinador"
            value={
              values?.idAdmonPatrocinadora
                ? admonsReadOnly?.idAdmonPatrocinadora
                    ?.administradorPatrocinador
                : ""
            }
            disabled={true}
            placeholder={
              data?.admonPatrocinadora?.administradores?.find(
                (admon) => admon?.estatus === true
              )?.administrador || ""
            }
          />
        </Col>
      </Row>
      <FieldArray
        name="idAdmonCentrales"
        render={(arrayHelpers) => {
          return (
            <>
              {values?.idAdmonCentrales && values?.idAdmonCentrales?.length > 0
                ? values?.idAdmonCentrales?.map((el, index) => {
                    return (
                      <>
                        <SelectDependent
                          value={el}
                          handleChange={handleChange}
                          handleBlur={onBlur}
                          options={admonCentralPatrocinadoraData}
                          touched={touched}
                          errors={errors}
                          index={index}
                          data={data}
                          disabled={!editable}
                        />
                        {index === 0 ? (
                          <Row>
                            <Col md={12} className="text-end">
                              <Tooltip text={"Agregar Administración Central"}>
                                <span>
                                  <IconButton
                                    type="add"
                                    onClick={() => {
                                      arrayHelpers.push("");
                                    }}
                                    disabled={!editable}
                                  />
                                </span>
                              </Tooltip>
                            </Col>
                          </Row>
                        ) : null}
                        {index === 0 && values?.idAdmonCentrales.length > 1 ? (
                          <Row>
                            <Col>
                              <LabelValue
                                label={"Administración central patrocinadora*:"}
                              />
                            </Col>
                            <Col>
                              <LabelValue
                                label={
                                  "Nombre de la admón. central patrocinadora:"
                                }
                              />
                            </Col>
                            <Col>
                              <LabelValue
                                label={"Administrador central patrocinador:"}
                              />
                            </Col>
                          </Row>
                        ) : null}
                        {index !== 0 ? (
                          <Row className="mb-3 text-end">
                            <Col>
                              <Tooltip text={"Eliminar Administración Central"}>
                                <span>
                                  <IconButton
                                    type="delete"
                                    onClick={() => {
                                      setSelectedIndex(index);
                                      setShow(true);
                                    }}
                                    disabled={!editable}
                                  />
                                </span>
                              </Tooltip>
                            </Col>
                          </Row>
                        ) : null}
                      </>
                    );
                  })
                : null}
              <BasicModal
                size={"md"}
                show={show}
                title={"Mensaje"}
                approveText={"Sí"}
                denyText={"No"}
                handleApprove={() => {
                  arrayHelpers.remove(selectedIndex);
                  setShow(false);
                }}
                handleDeny={() => {
                  setShow(false);
                }}
                onHide={() => {
                  setShow(false);
                }}
              >
                {MODIFICAR_PROYECTOS.MSG007}
              </BasicModal>
            </>
          );
        }}
      />

      <Row>
        <Col md={4}>
          <Select
            label="Administración participante:"
            name="idAdmonParticipante"
            value={values?.idAdmonParticipante}
            onChange={changeAdministracion}
            options={admonParticipanteData}
            keyValue="primaryKey"
            keyTextValue="acronimo"
            onBlur={onBlur}
            disabled={!editable}
            keyStatus="estatus"
            hideDisabledOptions={true}
          />
        </Col>
        <Col md={4}>
          <TextField
            label="Nombre de la admón. participante:"
            name="administracionParticipanteNombre"
            value={
              values?.idAdmonParticipante
                ? admonsReadOnly?.idAdmonParticipante
                    ?.administracionParticipante
                : ""
            }
            disabled
            readOnly
            placeholder={data?.admonParticipante?.administracion || ""}
          />
        </Col>
        <Col md={4}>
          <TextField
            label="Administrador participante:"
            name="administradorParticipante"
            value={
              values?.idAdmonParticipante
                ? admonsReadOnly?.idAdmonParticipante?.administradorParticipante
                : ""
            }
            disabled
            readOnly
            placeholder={
              data?.admonParticipante?.administradores?.find(
                (admon) => admon?.estatus === true
              )?.administrador || ""
            }
          />
        </Col>
      </Row>
      <Row>
        <Col md={4}>
          <Select
            label="Clasificación del proyecto*:"
            name="idClasificacionProyecto"
            value={values?.idClasificacionProyecto}
            onChange={handleChange}
            options={clasificacionData}
            keyValue="primaryKey"
            keyTextValue="nombre"
            helperText={
              touched.idClasificacionProyecto
                ? errors.idClasificacionProyecto
                : ""
            }
            className={
              touched.idClasificacionProyecto &&
              (errors.idClasificacionProyecto ? "is-invalid" : "is-valid")
            }
            onBlur={onBlur}
            disabled={!editable}
            keyStatus="estatus"
            hideDisabledOptions={true}
          />
        </Col>
        <Col md={4}>
          <Select
            label="Financiamiento*:"
            name="idFinanciamiento"
            value={values?.idFinanciamiento}
            onChange={handleChange}
            options={financiamientoData}
            keyValue="primaryKey"
            keyTextValue="nombre"
            helperText={touched.idFinanciamiento ? errors.idFinanciamiento : ""}
            className={
              touched.idFinanciamiento &&
              (errors.idFinanciamiento ? "is-invalid" : "is-valid")
            }
            onBlur={onBlur}
            disabled={!editable}
            keyStatus="estatus"
            hideDisabledOptions={true}
          />
        </Col>
        <Col md={4}>
          <Select
            label="Tipo de procedimiento*:"
            name="idTipoProcedimiento"
            value={values?.idTipoProcedimiento}
            onChange={handleChange}
            options={procedimientosData}
            keyValue="primaryKey"
            keyTextValue="nombre"
            helperText={
              touched.idTipoProcedimiento ? errors.idTipoProcedimiento : ""
            }
            className={
              touched.idTipoProcedimiento &&
              (errors.idTipoProcedimiento ? "is-invalid" : "is-valid")
            }
            onBlur={onBlur}
            disabled={!editable}
            keyStatus="estatus"
            hideDisabledOptions={true}
          />
        </Col>
      </Row>
    </>
  );
}
