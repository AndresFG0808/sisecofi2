import React, { useMemo } from "react";
import { Row } from "react-bootstrap";
import { TablaEditable } from "../../../../../components/table/TablaEditable";

export function BuscarContrato() {
  const data = [];
  const columns = useMemo(
    () => [
      {
        accessorKey: "id",
        header: "id",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "nombreContrato",
        header: "Nombre del contrato",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "nombreProyecto",
        header: "Nombre del proyecto",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "numeroContrato",
        header: "Número de contrato",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "tipoProcedimiento",
        header: "Tipo de procedimiento",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "inicio",
        header: "Inicio",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "termino",
        header: "Término",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "cm",
        header: "Último CM",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "montoMax",
        header: "Monto máximo",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "monotMaxCM",
        header: "Monto máximo último CM",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "montoPesos",
        header: "Monto en pesos",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "admonCentral",
        header: "Administración Central",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "admonContrto",
        header: "Administrador de contrato",
        cell: () => <p>test</p>,
      },
      {
        accessorKey: "acciones",
        header: "Acciones",
        cell: () => <p>test</p>,
      },
    ],
    []
  );
  return (
    <>
      <Row></Row>
      <Row></Row>
      <Row>
        <TablaEditable
          data={data}
          columns={columns}
          header={"Contratos"}
          hasPagination
        />
      </Row>
    </>
  );
}
