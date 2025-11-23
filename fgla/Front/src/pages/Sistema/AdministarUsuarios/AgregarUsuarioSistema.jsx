import React, { useState, useContext } from "react";
import { Container } from "react-bootstrap";
import { Accordion, Loader } from "../../../components";
import { AdministrarUsuarioContext } from "./AdministrarUsuarioContext";
import AdministrarUsuariosFilter from "./AdministrarUsuariosFilter/AdministrarUsuariosFilter";
import AdministrarUsuariosTable from "./AdministrarUsuariosTable/AdministrarUsuariosTable";
import { ADMINISTRAR_USUARIOS_SISTEMA } from "../../../constants/messages";
import * as yup from "yup";
import { Formik } from "formik";
import {
  usePostBuscarUsuariosMutation,
  usePutBuscarUsuariosDirectoriosMutation,
} from "./store";
import _ from "lodash";
import Authorization from "../../../components/Authorization";

export function AgrearUsuarioSistema() {
  const [dataTable, setDataTable] = useState([]);
  const [actionType, setActionType] = useState("directorio");

  const { handleShowMessage } = useContext(AdministrarUsuarioContext);

  const [postBuscar, { isLoading: isLoadingBuscar }] =
    usePostBuscarUsuariosMutation();
  const [postBuscarDA, { isLoading: isLoadingBuscarDA }] =
    usePutBuscarUsuariosDirectoriosMutation();

  const handleSubmit = (values, actions) => {
    let { actionType } = values;
    setDataTable([]);
    setActionType(actionType);
    if (actionType === "sistema") {
      postBuscar(values).then((response) => {
        if (response.error) {
          handleShowMessage(ADMINISTRAR_USUARIOS_SISTEMA.MSG007);
        } else if (!_.isEmpty(response?.data)) {
          let { data } = response;
          setDataTable(data);
        }
      });
    }
    if (actionType === "directorio") {
      postBuscarDA(values).then((response) => {
        if (response.error) {
          handleShowMessage(ADMINISTRAR_USUARIOS_SISTEMA.MSG007);
        } else if (!_.isEmpty(response?.data)) {
          let { data } = response;
          setDataTable(data);
        }
      });
    }
  };

  return (
    <>
      {(isLoadingBuscar || isLoadingBuscarDA) && <Loader />}
        <Accordion title="Agregar usuario al sistema" showChevron={false} collapse={false}>
          <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            enableReinitialize={true}
            onSubmit={handleSubmit}
            validateOnMount={true}
          >
            {({ handleSubmit, resetForm,errors }) => {
              
              const resetPage = () => {
                setDataTable([]);
                resetForm();
              };
              return (
                <>
                  <Authorization process={"USR_CONS"}>
                    <AdministrarUsuariosFilter
                      handleSubmit={handleSubmit}
                      dataTable={dataTable}
                      actionType={actionType}
                    />
                  </Authorization>
                  <AdministrarUsuariosTable
                    dataTable={dataTable}
                    actionType={actionType}
                    resetPage={resetPage}
                  />
                </>
              );
            }}
          </Formik>
        </Accordion>
    </>
  );
}

const initialValues = {
  nombre: "",
  apellidoPaterno:"",
  apellidoMaterno:"",
  rfcCorto: "",
  estatus: true,
};

const atLeastOneField = (fields) => {
  return function (value) {
    const parentValues = this.parent;
    const camposValidos =fields.some(field => Boolean(parentValues[field]));
    return  value || camposValidos;
  };
};


const validationSchema = yup.object().shape(
  {
    nombre: yup.string().test(
      "at-least-one",
      ADMINISTRAR_USUARIOS_SISTEMA.MSG001,
      atLeastOneField(["rfcCorto", "apellidoPaterno", "apellidoMaterno"])
    ),
    apellidoPaterno: yup.string().test(
      "at-least-one",
      ADMINISTRAR_USUARIOS_SISTEMA.MSG001,
      atLeastOneField(["rfcCorto", "nombre", "apellidoMaterno"])
    ),
    apellidoMaterno: yup.string().test(
      "at-least-one",
      ADMINISTRAR_USUARIOS_SISTEMA.MSG001,
      atLeastOneField(["rfcCorto", "nombre", "apellidoPaterno"])
    ),
    rfcCorto: yup
      .string()
      .test(
        "at-least-one",
        ADMINISTRAR_USUARIOS_SISTEMA.MSG001,
        atLeastOneField(["nombre", "apellidoPaterno", "apellidoMaterno"])
      )
      .nullable()
      .transform((value) =>
        value === null || value === undefined ? "" : value
      )
      .min(4, "La longitud mínima es de 4 dígitos")
      .max(8, "La longitud máxima es de 8 dígitos")
      .matches(/^[A-Za-z]{4}/, "Los primeros 4 dígitos deben ser alfabéticos")
      .test(
        "is-valid-5th-6th-char",
        "El quinto y sexto carácter deben ser numéricos",
        (value) => {
          if (!value) return true;
          if (value.length < 5) return true;
          const fifthChar = value[4];
          const sixthChar = value[5];
          return (
            !isNaN(fifthChar) && (sixthChar === undefined || !isNaN(sixthChar))
          );
        }
      )
      .test(
        "is-valid-7th-8th-char",
        "El séptimo y octavo carácter deben ser alfanuméricos",
        (value) => {
          if (!value) return true;
          if (value.length < 7) return true;
          const seventhChar = value[6];
          const eighthChar = value[7];
          const isAlphanumeric = (char) => /^[a-z0-9]+$/i.test(char);
          return (
            (seventhChar === undefined || isAlphanumeric(seventhChar)) &&
            (eighthChar === undefined || isAlphanumeric(eighthChar))
          );
        }
      ),
  }
);
