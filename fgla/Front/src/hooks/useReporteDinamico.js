import _ from "lodash";
import { useEffect, useState } from "react";

function extractNodesByName(obj, nodeNames) {
  let result = [];

  // Base case: if there are no nodes or if the node is not an object, return empty array
  if (!obj || !Array.isArray(obj.nodes)) {
    return result;
  }

  // Iterate over the nodes to search for the desired names
  obj.nodes.forEach((node) => {
    // Check if the node name matches one of the desired names
    if (nodeNames.includes(node.name)) {
      result.push(node);
    }

    // Recursively check if the node has its own nested nodes
    if (node.nodes && Array.isArray(node.nodes)) {
      result = result.concat(extractNodesByName(node, nodeNames)); // merge results
    }
  });

  return result;
}

const fichaSearch = ["Fecha inicio", "Fecha fin"];
const periodoSearch = [
  "Fecha de inicio del proyecto",
  "Fecha fin del proyecto",
];
const contratosSearch = [
  "Fecha inicio del contrato",
  "Fecha término del contrato",
];
const estimacionesSearch = ["Periodo inicio", "Periodo fin"];
const dictamenesSearch = ["Periodo inicio", "Periodo fin"];
const facturasSearch = ["Fecha facturación"];
const periodoNotasSearch = ["Fecha de generación"];

const isPeriodo = (dates = []) => {
  return dates.every((obj) => obj.checked);
};

export const useReporteDinamico = (state) => {
  const [periodos, setPeriodos] = useState([
    {
      name: "periodoFichaTecnica",
      displayName: "Ficha técnica",
      id: 1,
      isActive: false,
    },
    {
      name: "periodoProyecto",
      displayName: "Proyecto",
      id: 2,
      isActive: false,
    },
    {
      name: "periodoContrato",
      displayName: "Contrato",
      id: 3,
      isActive: false,
    },
    {
      name: "periodoEstimaciones",
      displayName: "Estimaciones",
      id: 4,
      isActive: false,
    },
    {
      name: "periodoDictamenes",
      displayName: "Dictámenes",
      id: 5,
      isActive: false,
    },
    {
      name: "periodoFacturas",
      displayName: "Facturas",
      id: 6,
      isActive: false,
    },
    {
      name: "periodoNotasCredito",
      displayName: "Notas de crédito",
      id: 7,
      isActive: false,
    },
  ]);

  useEffect(() => {
    const fichaTecnica = isPeriodo(extractNodesByName(state[0], fichaSearch));
    const proyecto = isPeriodo(extractNodesByName(state[0], periodoSearch));
    const contratos = isPeriodo(extractNodesByName(state[2], contratosSearch));
    const estimaciones = isPeriodo(
      extractNodesByName(state[4], estimacionesSearch)
    );
    const dictamenes = isPeriodo(
      extractNodesByName(state[5], dictamenesSearch)
    );
    const facturas = isPeriodo(extractNodesByName(state[7], facturasSearch));
    const notasCredito = isPeriodo(
      extractNodesByName(state[8], periodoNotasSearch)
    );

    setPeriodos([
      {
        name: "periodoFichaTecnica",
        displayName: "Periodo de ficha técnica",
        id: 1,
        isActive: fichaTecnica,
      },
      {
        name: "periodoProyecto",
        displayName: "Periodo del proyecto",
        id: 2,
        isActive: proyecto,
      },
      {
        name: "periodoContrato",
        displayName: "Periodo del contrato",
        id: 3,
        isActive: contratos,
      },
      {
        name: "periodoEstimaciones",
        displayName: "Periodo de estimaciones",
        id: 4,
        isActive: estimaciones,
      },
      {
        name: "periodoDictamenes",
        displayName: "Periodo de los dictámenes",
        id: 5,
        isActive: dictamenes,
      },
      {
        name: "periodoFacturas",
        displayName: "Periodo de facturas",
        id: 6,
        isActive: facturas,
      },
      {
        name: "periodoNotasCredito",
        displayName: "Periodo de las notas de crédito",
        id: 7,
        isActive: notasCredito,
      },
    ]);
  }, [state]);
  return [...periodos.filter((periodo) => periodo.isActive)];
};
