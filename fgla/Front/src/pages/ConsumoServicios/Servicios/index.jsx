import React, { useState } from "react";
import { Container } from "react-bootstrap";
import MainTitle from "../../../components/MainTitle";
import Loader from "../../../components/Loader";
import Accordion from "../../../components/Accordion";
import FiltroConsumoServicios from "./FiltroConsumoServicios";
import { ConsumoServiciosContext } from "../Context/ConsumoServiciosContext";
import TableComponent from "./TableComponent";

const ConsumoServicios = () => {
  const [loading, setLoading] = useState(true);
  const [dataTable, setDataTable] = useState(null);
  const [tipoConsumo, setTipoConsumo] = useState(null);
  const [isVisibleEditable, setIsVisibleEditable] = useState(false);
  const [processTypeByTipoConsumo, setProcessTypeByTipoConsumo] = useState(null);
  const [processTypeEditByTipoConsumo, setProcessTypeEditByTipoConsumo] = useState(null);


  return (
    <Container className="mt-3 px-3">
      <ConsumoServiciosContext.Provider
        value={{
          setLoading,
          setDataTable,
          dataTable,
          tipoConsumo,
          setTipoConsumo,
          setIsVisibleEditable,
          isVisibleEditable,
          setProcessTypeByTipoConsumo,
          processTypeByTipoConsumo,
          setProcessTypeEditByTipoConsumo,
          processTypeEditByTipoConsumo,
        }}
      >
        {loading && <Loader />}
        <MainTitle title="Consumo de Servicios" />
        <Accordion title="Búsqueda" collapse={false} showChevron={false}>
          <FiltroConsumoServicios />
          <TableComponent dataTable={dataTable} />
        </Accordion>
      </ConsumoServiciosContext.Provider>
    </Container>
  );
};

export default ConsumoServicios;
