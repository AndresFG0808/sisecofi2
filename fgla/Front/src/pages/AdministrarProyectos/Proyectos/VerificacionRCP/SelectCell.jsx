import React, { useEffect, useState, memo } from "react";
import { Col, Row } from "react-bootstrap";
import isEmpty from "lodash/isEmpty";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import TextFieldWithIconLeft from "../../../../components/formInputs/TextFieldIcon";
import Select from "../../../../components/formInputs/Select";
import SelectWrapper from "./SelectWrapper";

function EditableCell({
  getValue,
  row,
  column,
  table,
  options,
  keyValue = "id",
  keyTextValue = "texto",
  rowClassName = "",
  onGetService,
  onPostService,
  idContrato,
  urlByDocument,
  type,
  idDictamen,
}) {
  const { index } = row;
  const { id } = column;
  const { updateData } = table.options.meta;
  const initialValue = getValue();
  const { isEditable, tipoPena } = row.original;
  const [helperText, setHelperText] = useState(null);
  const [className, setClassName] = useState("");
  const [catalog, setCatalog] = useState([]);

  const {
    documentos,
    documentosHelperText,
    tipoHelperText,
    documentosCatalog,
    conceptoServiciosCatalog,
    tipo,
    documentoNombre,
    conceptosServicio,
    isNewRegister,
    estatusErrorText,
  } = row.original;

  const cb = () => {};

  const switchOption = (
    idFieldArg,
    estatusCb = cb,
    documentosCb = cb,
    desgloseCb = cb,
    conceptoServiciosCb = cb
  ) => {
    switch (idFieldArg) {
      case "estatus":
        estatusCb();
        break;
      default:
        return null;
    }
  };

  useEffect(() => {
    switchOption(
      id,
      () => {
        if (isEmpty(options) === false) setCatalog(options);
      },
    );
  }, [options]);

  useEffect(() => {
    if (
      isEmpty(documentosCatalog) &&
      initialValue !== null &&
      initialValue !== "" &&
      id === "tipo" &&
      isEmpty(catalog) === false &&
      isEditable
    ) {
      onGetDocumentByIdType(tipo);
    }
  }, [documentosCatalog, initialValue, id, catalog, isEditable]);

  useEffect(() => {
    if (
      isEmpty(conceptoServiciosCatalog) &&
      initialValue !== null &&
      initialValue !== "" &&
      id === "tipo" &&
      isEmpty(catalog) === false &&
      isEditable
    ) {
      onGetConceptoServiciosByIdType(tipo);
    }
  }, [conceptoServiciosCatalog, initialValue, id, catalog, isEditable]);

  useEffect(() => {
    if (
      (estatusErrorText !== null || tipoHelperText !== "") &&
      helperText === null &&
      initialValue === "" &&
      (id === "estatus" || id === "tipo")
    ) {
      setHelperText(estatusErrorText);
      if (estatusErrorText!==null) setClassName("is-invalid");
      
    }
  }, [estatusErrorText, tipoHelperText, id, helperText, initialValue]);

  const onCallApi = async (idField, value) => {
    switchOption(
      idField,
      () => {
        /*
        “Estatus” sea “No aplica” el campo “# de páginas” se inactivará.
        */
      },
      () => {
      }
    );
  };

  const onConvertDocumentsTypeCatalog = (catalog, typeName) => {
    let newCatalog = [];

    if (isEmpty(catalog) === false) {
      newCatalog = catalog.map((item) => {
        const element = {
          id: null,
          informeDocumental: item.descripcion,
          penaAplicable: item.penaAplicable,
        };
        switch (typeName) {
          case "Informes documentales de los servicios":
            element.id = item.idServicios;
            element.penaAplicable = item.penasDeduccionesAplicables;
            break;
          case "Informes documentales por única vez":
            element.id = item.id;
            element.penaAplicable = item.penasDeduccionesAplicables;
            break;
          case "Informes documentales periódicos":
            element.id = item.idPeriodicos;
            element.penaAplicable = item.penaConvencionalDeductiva;
            break;
          case "Penas contractuales":
            element.id = item.idPenaContractualContrato;
            element.penaAplicable = item.penaAplicable;
            break;
          case "Atraso en el inicio de la prestación":
            element.id = item.idAtrasoPrestacion;
            element.penaAplicable = item.penasDeducciones;
            break;
          case "Niveles de servicio":
            element.id = item.idServiciosSla;
            element.penaAplicable = item.deduccionesAplicables;
            break;

          default:
            return null;
        }
        return element;
      });
    }
    return newCatalog;
  };

  const onGetDocumentByIdType = async (typeArg) => {
    const typeName =
      isEmpty(catalog) === false
        ? catalog.filter((item) => item.primaryKey === parseInt(typeArg, 10))[0]
            .nombre
        : tipoPena;
    const url = `${urlByDocument}${idContrato}/${typeName}`;
    const response = await onGetService(url);
    if (response !== null && isEmpty(response.data) === false) {
      const newCatalog = onConvertDocumentsTypeCatalog(
        [...response.data],
        typeName
      );
      updateData(index, "documentosCatalog", newCatalog);
    } else if (isEmpty(documentosCatalog) === false) {
      updateData(index, "documentosCatalog", []);
    }
    return response;
  };

  const onGetConceptoServiciosByIdType = async (typeArg) => {
    let text = "";
    if (isEmpty(catalog) === false)
      text = catalog.filter((item) => item.primaryKey === parseInt(typeArg, 10))[0]
        .nombre;
    const url = `/admin-devengados/obtener-conceptos-servicio`;
    const data = {
      tipo: text,
      dictamenId: {
        idDictamen,
      },
    };
    const response = await onPostService(url, data);
    if (response !== null && isEmpty(response.data) === false) {
      const newCatalog = response.data.map((item, index) => {
        return {
          id: item,
          nombre: item,
        };
      });
      updateData(index, "conceptoServiciosCatalog", newCatalog);
    } else if (isEmpty(documentosCatalog) === false) {
      updateData(index, "conceptoServiciosCatalog", []);
    }
    return response;
  };

  const getKeyByID = (idArg) => {
    if (idArg === "documentos") {
      return "idPenaContractualDocumento";
    } else if (idArg === "estatus") {
      return "primaryKey";
    }
  };

  const getNameByID = (idArg) => {
    if (idArg === "documentos") {
      return "informeDocumental";
    } else if (idArg === "estatus") {
      return "nombre";
    }
  };

  const getCatalogByIdField = () => {
    let selectCatalog = [];
    if (id === "estatus") {
      selectCatalog = catalog.filter((item)=>item.primaryKey !== '4');
    } else if (id === "conceptosServicio") {
      selectCatalog = conceptoServiciosCatalog;
    } else {
      selectCatalog = catalog;
    }
    return selectCatalog;
  };

  const onGetSelectText = () => {
    let component = null;
    if (id === "documentos" && isNewRegister === false) {
      component = documentoNombre;
    } else if (id === "conceptosServicio" && isNewRegister === false) {
      component = conceptosServicio;
    } else {
      component = (
        <SelectWrapper
          id={id}
          options={catalog}
          value={initialValue}
          keyProp={getKeyByID(id)}
          name={getNameByID(id)}
        />
      );
    }
    return component;
  };
  const renderSwitch = () => {
    return (
      <>
        {isEditable ? (
          <div className={rowClassName}>
            <Select
              name={""}
              onClick={() => {}}
              value={initialValue}
              onChange={(e) => {
                updateData(index, 'isUpdated', true);
                updateData(index, id, e.target.value);
                updateData(index, "fechaDocumentoErrorText", null);
                updateData(index, "paginasErrorText", null);
                updateData(index, "justificacionErrorText", null);
                if (isEmpty(helperText) === false) {
                  setClassName("is-valid");
                  setHelperText(null);
                }
                onCallApi(id, e.target.value);
              }}
              options={getCatalogByIdField()}
              keyValue={keyValue}
              keyTextValue={keyTextValue}
              readOnly={false}
              disabled={
                id === "conceptosServicio" && tipo !== "1" && tipo !== '' ? true : false
              }
              className={className}
              helperText={helperText}
              defaultOptionText="Seleccione"
            />
          </div>
        ) : (
          <div className={rowClassName}>{onGetSelectText()}</div>
        )}
      </>
    );
  };

  return <>{renderSwitch()}</>;
}

// Evita re-renders del EditableCell durante el onchange del input de filtro
export default EditableCell;
