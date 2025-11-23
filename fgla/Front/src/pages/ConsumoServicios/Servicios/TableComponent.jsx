import React, { memo, useState, useContext } from "react";
import { TablaDinamica } from "../../../components/table";
import { injectActions } from "../../../functions/utils";
import { useNavigate } from "react-router-dom";
import { ConsumoServiciosContext } from "../Context/ConsumoServiciosContext";
import { HEADERS_ESTIMACION, HEADERS_DICTAMEN } from "./constants";
import { isEmptyArray } from "formik";
import Authorization from "../../../components/Authorization";

const PAGEABLE = {
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  size: 15,
};

const ID_KEY_NAME = "id";

function TableComponent() {
  const { tipoConsumo, dataTable, isVisibleEditable, processTypeByTipoConsumo, } = useContext(
    ConsumoServiciosContext
  );
  const [pageable, setPageable] = useState(PAGEABLE);
  const navigate = useNavigate();

  const onChangeStatusProyecto = (id) => () => {};

  const onClickRow = (idElement, editable) => {
    let obj = dataTable.find(({ id }) => id === idElement);
    let path =
      obj.type === "Dictamen"
        ? "/consumoServicios/consumoServicios/dictamen"
        : "/consumoServicios/consumoServicios/estimacion";
    let dictamenState = {
      idDictamen: obj.idBd,
      idDictamenVisual: obj.id,
      idContrato: obj.idContrato,
      idProveedor: obj.idProveedor,
      proveedor: obj.proveedor,
      dictamen: { ...obj },
      editableDuplicateButton: false,
      editable,
    };
    let estimacionState = {
      idEstimacion: obj.id,
      idContrato: obj.idContrato,
      idProveedor: obj.idProveedor,
      proveedor: obj.proveedor,
      estimacion: { ...obj },
      editable,
    };
    navigate(path, {
      state: {
        dictamenState,
        estimacionState,
      },
    });
  };

  const handleEdit = (idElement) => () => onClickRow(idElement, true);
  const handleShow = (idElement) => () => onClickRow(idElement, false);

  const updateDataTable = (values) => {};

  const onGetVisibleEditable = () => {
    let visible = true;
    if (isEmptyArray(dataTable) === false) {
      const { estatusResponsabilidad } = dataTable[0];
      visible =
        estatusResponsabilidad === false ||
        estatusResponsabilidad === undefined ||
        estatusResponsabilidad === null
          ? true
          : false;
      if (visible === true) {
        visible = isVisibleEditable;
      }
    }
    return visible;
  };

  return (
    <>
      {dataTable !== null && tipoConsumo !== null && (
        <>
          {tipoConsumo === "Dictaminado" ? (
            <TablaDinamica
              idKeyName={ID_KEY_NAME}
              idKeyLink={ID_KEY_NAME}
              headers={
                tipoConsumo === "Dictaminado"
                  ? HEADERS_DICTAMEN
                  : HEADERS_ESTIMACION
              }
              header={
                tipoConsumo === "Dictaminado" ? "Dictámenes" : "Estimaciones"
              }
              data={injectActions(dataTable, {
                edit: isVisibleEditable, // onGetVisibleEditable(),
                show: true,
              })}
              actionFns={{ handleEdit, handleShow }}
              onChangeStatus={onChangeStatusProyecto}
              pageable={pageable}
              updateData={updateDataTable}
            />
          ) : (
            <Authorization
              process={processTypeByTipoConsumo}
              redirect={
                <TablaDinamica
                  idKeyName={ID_KEY_NAME}
                  idKeyLink={ID_KEY_NAME}
                  headers={
                    tipoConsumo === "Dictaminado"
                      ? HEADERS_DICTAMEN
                      : HEADERS_ESTIMACION
                  }
                  header={
                    tipoConsumo === "Dictaminado"
                      ? "Dictámenes"
                      : "Estimaciones"
                  }
                  data={injectActions(dataTable, {
                    edit: false,
                    show: true,
                  })}
                  actionFns={{ handleEdit, handleShow }}
                  onChangeStatus={onChangeStatusProyecto}
                  pageable={pageable}
                  updateData={updateDataTable}
                />
              }
            >
              <TablaDinamica
                idKeyName={ID_KEY_NAME}
                idKeyLink={ID_KEY_NAME}
                headers={
                  tipoConsumo === "Dictaminado"
                    ? HEADERS_DICTAMEN
                    : HEADERS_ESTIMACION
                }
                header={
                  tipoConsumo === "Dictaminado" ? "Dictámenes" : "Estimaciones"
                }
                data={injectActions(dataTable, {
                  edit: onGetVisibleEditable(),
                  show: true,
                })}
                actionFns={{ handleEdit, handleShow }}
                onChangeStatus={onChangeStatusProyecto}
                pageable={pageable}
                updateData={updateDataTable}
              />
            </Authorization>
          )}
        </>
      )}
    </>
  );
}

export default memo(TableComponent, (prevProps, nextProps) => {
  return prevProps.dataTable === nextProps.dataTable;
});
