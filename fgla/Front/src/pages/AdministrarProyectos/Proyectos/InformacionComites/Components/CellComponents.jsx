import React, { useEffect, useState,useContext } from "react";
import {
  faChevronDown,
  faChevronRight,
  faTriangleExclamation,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Form } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
import { Tooltip } from "../../../../../components/Tooltip";
import { Link } from "react-router-dom";
import { ComentariosSimple } from "../../../../../components";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { Formik } from "formik";
import Authorization from "../../../../../components/Authorization";

//#region Comites

export function LabelCell({ getValue }) {
  const initialValue = getValue();

  return <>{initialValue}</>;
}

export function SelectedLabelCell({ getValue }) {
  const initialValue = getValue();
  let value = initialValue || "";
  let formatValue = <></>;
  if (value && typeof value == "string") {
    let values = value.split(",");
    formatValue = values.map((item, index) => {
      return <div key={index}>{item} </div>;
    });
  }

  return formatValue;
}

export function DateCell({ getValue }) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);

  return <>{value}</>;
}

function cutText(text, word) {
  if (typeof text !== "string" || typeof word !== "string") {
    throw new Error("Both parameters must be strings.");
  }

  if (text.length === 0 || word.length === 0) {
    return text; // If the text or the word is empty, return the complete text.
  }

  const wordIndex = text.toLowerCase().lastIndexOf(word.toLowerCase());
  if (wordIndex !== -1) {
    const endWord = wordIndex;
    return text.substring(0, endWord);
  } else {
    return text; // If the word is not found, return the complete text.
  }
}

export function FileCell({
  getValue,
  row,
  handleDownloadFolder,
  handleShowSatCloud,
}) {
  //#region SatCloud

  //#endregion
  const initialValue = getValue();
  const [returnValue, setReturnValue] = useState(<></>);
  let otrosDocumentos = row?.original["informacionArchivosOtrosDocumentos"];
  let otrosCount = Array.isArray(otrosDocumentos) ? otrosDocumentos.length : 0;
  var idComiteProyecto = row?.original?.idComiteProyecto;
  let cantitadArchivos = Array.isArray(initialValue) ? initialValue.length : 0;
  cantitadArchivos = cantitadArchivos + otrosCount;
  let allFiles = [...initialValue, ...otrosDocumentos];
  let path = null;
  let fileName = "";
  if (cantitadArchivos === 1) {
    let { ruta, nombre } = allFiles[0];
    path = ruta;
    fileName = nombre;
  }
  if (cantitadArchivos > 1) {
    let { carpeta } = allFiles[0];

    path = cutText(carpeta, "/");
  }

  useEffect(() => {
    if (cantitadArchivos > 1) {
      setReturnValue(
        <div href="#" className="d-flex">
          <IconButton
            type={"download"}
            onClick={() => handleShowSatCloud(path)}
            iconSize="lg"
            toooltip={"SATCloud"}
            tableContainer
          />
          <IconButton
            type={"zip"}
            onClick={() => handleDownloadFolder(path, cantitadArchivos)}
            iconSize="lg"
            toooltip={"Descarga masiva"}
            tableContainer
          />
        </div>
      );
    }

    if (cantitadArchivos === 1) {
      setReturnValue(
        <div className="d-flex px-2">
          <span className="ml-5" style={{ marginLeft: "5px" }}>
            <Link
              href="#"
              onClick={() =>
                handleDownloadFolder(path, cantitadArchivos, fileName)
              }
            >
              Clic
            </Link>
          </span>
        </div>
      );
    }
  }, [
    handleShowSatCloud,
    handleDownloadFolder,
    idComiteProyecto,
    path,
    cantitadArchivos,
    fileName,
  ]);

  return (
    <Authorization process={"PROY_INFO_COM_DOWN"}>{returnValue}</Authorization>
  );
}

function formatCurrency(amount) {
  // Verificar si la entrada es un número
  if (typeof amount !== "number") {
    return "";
  }
  // Formatear el número con separadores de miles y dos decimales
  let formattedAmount = amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, "$&,");
  // Agregar el símbolo de la moneda al principio
  formattedAmount = "" + formattedAmount;
  return formattedAmount;
}

export function MoneyCell({ getValue }) {
  const initialValue = getValue();
  const [returnValue, setReturnValue] = useState(<></>);

  useEffect(() => {
    setReturnValue(formatCurrency(initialValue));
  }, [initialValue]);

  return <>{returnValue}</>;
}

export function ActionCell({
  getValue,
  row,
  handleShow,
  handleEdit,
  handleDelete,
  editable = false,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
 
  var isLast = row?.original?.ultimoValor;
  let { infomacionArchivos, informacionArchivosOtrosDocumentos } =
    row?.original;

  let otrosCount = Array.isArray(infomacionArchivos)
    ? infomacionArchivos.length
    : 0;
  var cantitadArchivos = Array.isArray(informacionArchivosOtrosDocumentos)
    ? informacionArchivosOtrosDocumentos.length
    : 0;

  cantitadArchivos = cantitadArchivos + otrosCount;
  return (
    <div className="d-flex">
      <IconButton
        type={"show"}
        onClick={() => handleShow(value)}
        iconSize="lg"
        tooltip={"Vista previa"}
        tableContainer
      />
      <Authorization process={"PROY_INFO_COM_ADMIN"}>
        <IconButton
          type={"edit"}
          onClick={() => handleEdit(value)}
          iconSize="lg"
          disabled={!editable}
          tooltip={"Editar"}
          tableContainer
        />
      </Authorization>

      
        <Authorization process={"PROY_INFO_COM_ADMIN"}>
          <IconButton
            type={"drop"}
            onClick={() => handleDelete(value, cantitadArchivos)}
            iconSize="lg"
            disabled={!editable}
            tooltip={"Eliminar"}
            tableContainer
          />
        </Authorization>
     
    </div>
  );
}

//#endregion

//#region Estructura Documental
function IsParentRow(row) {
  return row?.getCanExpand() || row?.getIsExpanded();
}

export function ExpandibleCell({
  row,
  getValue,
  handleAddDocument,
  handleSave,
  disableForm,
  documentList,
  handleDownload,
}) {
  let value = getValue();

  let addDocument = row?.original["otrosDocumentos"];
  let { otherFile, ruta, tamanoMb, id } = row?.original;
  let isValidRuta = "/ruta" !== ruta && tamanoMb;
  if (otherFile && documentList?.length > 0) {
    let findDoc = documentList.find((s) => s.id === id);
    if (findDoc) {
      value = findDoc.descripcion;
    }
  }

  let initialValues = {
    descripcion: value,
  };

  let isParentRow = IsParentRow(row);

  return (
    <>
      {(isParentRow || addDocument) && (
        <div
          className="text-start"
          style={{
            paddingLeft: `${row.depth * 2}rem`,
          }}
        >
          <div>
            {row.getCanExpand() ? (
              <button
                {...{
                  onClick: row.getToggleExpandedHandler(),
                  style: { border: "1px solid transparent" },
                }}
              >
                {row.getIsExpanded() ? (
                  <FontAwesomeIcon icon={faChevronDown} />
                ) : (
                  <FontAwesomeIcon icon={faChevronRight} />
                )}
              </button>
            ) : (
              ""
            )}
            <>{value}</>

            {addDocument ? (
              <Authorization process={"PROY_INFO_COM_ADMIN"}>
                <IconButton
                  type={"add"}
                  iconSize="lg"
                  onClick={() => handleAddDocument(row)}
                  tooltip={"Nuevo documento"}
                  tableContainer
                />
              </Authorization>
            ) : null}
          </div>
        </div>
      )}

      {!isParentRow &&
        !addDocument &&
        (otherFile ? (
          <Formik
            initialValues={initialValues}
            onSubmit={(values) => {
              if (values?.descripcion) {
                handleSave(values.descripcion);
              }
            }}
          >
            {({ handleSubmit, handleChange, handleBlur, values }) => {
              return (
                <div
                  className="text-start"
                  style={{
                    paddingLeft: `${row.depth * 2}rem`,
                  }}
                >
                  <Form
                    autoComplete="off"
                    onSubmit={handleSubmit}
                    onBlur={handleSubmit}
                  >
                    <Form.Control
                      type="text"
                      name="descripcion"
                      value={values.descripcion}
                      disabled={disableForm}
                      onBlur={handleBlur}
                      onChange={handleChange}
                    />
                  </Form>
                </div>
              );
            }}
          </Formik>
        ) : (
          <div
            className="text-start"
            style={{
              paddingLeft: `${row.depth * 2}rem`,
            }}
          >
            {!isValidRuta && <>{value}</>}
            {isValidRuta && (
              <Authorization
                process={"PROY_INFO_COM_DOWN"}
                redirect={<>{value}</>}
              >
                <Link href="#" onClick={() => handleDownload(ruta)}>
                  {value}
                </Link>
              </Authorization>
            )}
          </div>
        ))}
    </>
  );
}

export function CheckCell({ getValue, row, handleChange, disabled }) {
  let initialValue = getValue();
  var isParentRow = IsParentRow(row);
  let addDocument = row?.original["otrosDocumentos"];

  let { noAplica } = row.original;
  const handleCheck = (e) => {
    handleChange(e.target.checked);
  };
  return (
    <div className="check-box-black">
      {!(isParentRow || addDocument) && (
        <Form.Check
          type="checkbox"
          disabled={disabled}
          checked={initialValue && !noAplica}
          name={"formCheck"}
          onChange={handleCheck}
        />
      )}
    </div>
  );
}

export function CellEstatus({ row }) {
  var isParentRow = IsParentRow(row);

  let addDocument = row?.original["otrosDocumentos"];
  let isLoadFile = false;
  let { base64, ruta, noAplica, obligatorio } = row.original;
  isLoadFile = base64 || ruta || noAplica;
  return (
    <>
      {!(isParentRow || addDocument) && obligatorio && !isLoadFile && (
        <FontAwesomeIcon
          icon={faTriangleExclamation}
          style={{ color: "#FFCC00" }}
        />
      )}
    </>
  );
}

export function CheckJustifyCell({ getValue, row, handleChange, disabled }) {
  let initialValue = getValue();
  var isParentRow = IsParentRow(row);
  let addDocument = row?.original["otrosDocumentos"];

  const [showModalComentarios, setShowModalComentarios] = useState(false);
  let justificacion = row?.original["justificacion"];

  const handleShow = () => {
    setShowModalComentarios(true);
  };
  const handleClose = () => {
    setShowModalComentarios(false);
    handleChange(false, justificacion);
  };

  const handleSave = (value) => {
    handleClose();
    handleChange(true, value);
  };

  const handleCheck = (e) => {
    if (e.target.checked) {
      justificacion = row?.original["justificacion"];
      handleMessageModal();
    } else {
      handleChange(false, justificacion);
    }
  };

  const [showMessageModal, setShowMessageModal] = useState(false);
  const messageModal = "Debe agregar una justificación.";
  const handleMessageModal = () => {
    setShowMessageModal(true);
  };
  const handleCloseMessageModal = () => {
    setShowMessageModal(false);
    handleShow();
  };
  return (
    <div className="check-box-black">
      {!(isParentRow || addDocument) && (
        <>
          <Form.Check
            type="checkbox"
            disabled={disabled}
            checked={initialValue}
            name={"formCheck"}
            onChange={handleCheck}
          />
          <SingleBasicModal
            size="md"
            approveText={"Aceptar"}
            show={showMessageModal}
            title="Mensaje"
            onHide={handleCloseMessageModal}
          >
            {messageModal}
          </SingleBasicModal>
          <ComentariosSimple
            show={showModalComentarios}
            comentario={justificacion}
            handleSave={handleSave}
            handleCancel={handleClose}
          />
        </>
      )}
    </div>
  );
}

export function CellComentarios({ getValue, row, disabled, handleChange }) {
  const [showModalComentarios, setShowModalComentarios] = useState(false);
  let value = getValue();
  let addDocument = row?.original["otrosDocumentos"];

  const handleShow = () => {
    value = getValue();
    setShowModalComentarios(true);
  };
  const handleClose = () => {
    setShowModalComentarios(false);
  };
  var isParentRow = IsParentRow(row);
  const handleSave = (value) => {
    handleChange(value);
    handleClose();
  };
  return (
    <>
      {!(isParentRow || addDocument) && (
        <>
          <Tooltip placement="top" text={"Justificación"}>
            <span>
              <button
                type="button"
                onClick={handleShow}
                style={{ border: "1px solid transparent" }}
                disabled={disabled}
              >
                ...
              </button>
            </span>
          </Tooltip>

          <ComentariosSimple
            show={showModalComentarios}
            comentario={value}
            handleSave={handleSave}
            handleCancel={handleClose}
          />
        </>
      )}
    </>
  );
}

export function ActionDocsCell({
  row,
  handleShow,
  handleDrop,
  handleSatCloud,
  handleDownload,
  handleUpdate,
  disabled,
  pathFolder,
}) {
  var isParentRow = IsParentRow(row);
  
  let { ruta, otherFile, otrosDocumentos,size } = row.original;
  let hasFile = !!size
  return (
    <>
      {(isParentRow || otrosDocumentos) && (
        <Authorization process={"PROY_INFO_COM_DOWN"}>
          <IconButton
            type={"download"}
            onClick={() => handleSatCloud(pathFolder)}
            iconSize="lg"
            disabled={disabled || !pathFolder}
            tooltip={"SATCloud"}
            tableContainer
          />
          <IconButton
            type={"zip"}
            onClick={() => handleDownload(pathFolder)}
            iconSize="lg"
            disabled={disabled || !pathFolder}
            tooltip={"Descarga masiva"}
            tableContainer
          />
        </Authorization>
      )}
      {!isParentRow && !otrosDocumentos && (
        <>
          {!otherFile && ruta && ruta.includes(".pdf") && (
            <IconButton
              type={"show"}
              onClick={handleShow}
              iconSize="lg"
              // disabled={disabled}
              tooltip={"Ver PDF"}
              tableContainer
            />
          )}
          <IconButton
            type={"upload"}
            onClick={handleUpdate}
            iconSize="lg"
            disabled={disabled}
            tooltip={"Cargar documento"}
            tableContainer
          />
          {!otherFile && ruta && hasFile && (
            <IconButton
              type={"drop"}
              onClick={handleDrop}
              iconSize="lg"
              disabled={disabled}
              tooltip={"Eliminar archivo cargado"}
              tableContainer
            />
          )}
        </>
      )}
    </>
  );
}

//#endregion
