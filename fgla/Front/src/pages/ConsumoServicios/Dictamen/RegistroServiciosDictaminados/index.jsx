import React, { useState, useEffect } from "react";
import TableComponent from './TableComponent';
const DatosGenerales = () => {
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getDataInit();
  }, []);

  const getDataInit = () => {
    setLoading(false);
  };

  return (
    <>
      <TableComponent />
    </>
  );
};

export default DatosGenerales;
