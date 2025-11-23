import React, { useState, useEffect } from "react";
import FormularioSolicitudFacturas from "./FormularioSolicitudFacturas";
import { Formik } from "formik";
import { getData } from "../../../../functions/api";
import Loader from "../../../../components/Loader";
import { Row, Col, Button } from "react-bootstrap";
import { Tooltip } from "../../../../components/Tooltip";
import IconButton from "../../../../components/buttons/IconButton";
import * as yup from "yup";
const SolicitudFacturas = ({reload,submitForm}) => {
  const valoresIniciales = {};
  const validationSchema = yup.object({});
  const onSubmit = (values) => {
    console.log(values);
  };

  const [isLoading, setIsLoading] = useState(false);
  const [estatusDictamen, setEstatusDictamen] = useState([]);
  const [numeroFactura, setNumeroFactura] = useState(1);

  useEffect(() => {
    setIsLoading(true);
    getData("/") 
      .then((response) => {
        setIsLoading(false);
        let { data } = response;
        setEstatusDictamen(data);
      })
      .catch((error) => {
        setIsLoading(false);
        setEstatusDictamen([]);
      });
  }, []);

  return (
    <>
      {isLoading && <Loader />}
      <Formik
        initialValues={valoresIniciales}
        enableReinitialize={true}
        validationSchema={validationSchema}
        onSubmit={onSubmit}
        validateOnMount={true}
      >
        {({ values }) => {
          return <FormularioSolicitudFacturas estatusDictamen={estatusDictamen} reload={reload} submitForm={submitForm} />;
        }}
      </Formik>
    </>
  );
};

export default SolicitudFacturas;
