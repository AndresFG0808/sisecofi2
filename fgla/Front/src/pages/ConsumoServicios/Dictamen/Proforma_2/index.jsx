import React, { useState, useEffect } from "react";
import { TableComponent } from "./TableComponent";

const Proforma = ({ chageStatusSeccion, type, }) => {
    const [loading, setLoading] = useState(true);
  
    useEffect(() => {
      getDataInit();
    }, []);
  
    const getDataInit = () => {
      setLoading(false);
    };
  
    return (
      <>
        <TableComponent type={type} />
      </>
    );
  };
  
export default Proforma;
