import React, { useState, useEffect } from "react";
import FormularioDatosGenerales from "./FormularioDatosGenerales";

const DatosGenerales = ({ chageStatusSeccion, proyecto }) => {
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getDataInit();
  }, []);

  const getDataInit = () => {
    setLoading(false);
  };

  return (
    <>
      <FormularioDatosGenerales />
    </>
  );
};

export default DatosGenerales;
