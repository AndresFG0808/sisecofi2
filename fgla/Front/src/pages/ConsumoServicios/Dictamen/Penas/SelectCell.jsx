import React, { useEffect, useState, useRef, } from "react";
import { Col, Row } from "react-bootstrap";
import isEmpty from "lodash/isEmpty";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import TextFieldWithIconLeft from "../../../../components/formInputs/TextFieldIcon";
import Select from "../../../../components/formInputs/Select";
import SelectWrapper from "./SelectWrapper";
import Authorization from "../../../../components/Authorization";


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
  status,
  idDictamen,
  tableType,
  onRemoveErrorMessagesByIdRow = () => {},
}) {
  const { index } = row;
  const { id } = column;
  const { updateData } = table.options.meta;
  const initialValue = getValue();
  const { isEditable, tipoPena } = row.original;
  const [helperText, setHelperText] = useState("");
  const [className, setClassName] = useState("");
  const [catalog, setCatalog] = useState([]);

  function usePrevious(value) {
    const ref = useRef();
    useEffect(() => {
      ref.current = value;
    });
    return ref.current;
  }
  

  const prevRow = usePrevious({row});
  useEffect(()=>{
    if (
      prevRow !== undefined && prevRow.row.original.tipo !== row.original.tipo
    ) {
      setHelperText(null);
      setClassName(null);
    }
  }, [prevRow, row]);


  const {
    documentos,
    documentosHelperText,
    tipoHelperText,
    conceptosServicioHelperText,
    documentosCatalog,
    conceptoServiciosCatalog,
    tipo,
    documentoNombre,
    conceptosServicio,
    isNewRegister,
  } = row.original;

  const cb = () => {};

  const switchOption = (
    idFieldArg,
    tipoCb = cb,
    documentosCb = cb,
    desgloseCb = cb,
    conceptoServiciosCb = cb

  ) => {
    switch (idFieldArg) {
      case "tipo":
        tipoCb();
        break;
      case "documentos":
        documentosCb();
        break;
      case "conceptosServicio":
        conceptoServiciosCb();
        break;
      case "desglose":
        desgloseCb();
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
      () => {
        if (isEmpty(documentosCatalog) === false) setCatalog(documentosCatalog);
      },
      () => {
        if (isEmpty(options) === false) setCatalog(options);
      },
      () => {
        if (isEmpty(conceptoServiciosCatalog) === false)
          setCatalog(conceptoServiciosCatalog);
      }
    );
  }, [documentosCatalog, options, conceptoServiciosCatalog]);

  useEffect(() => {
    if (
      isEmpty(documentosCatalog) &&
      initialValue !== null &&
      initialValue !== "" &&
      id === "tipo" &&
      isEmpty(catalog) === false &&
      isEditable
    ) {
     // updateData(index, "documentos", "");
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
      (documentosHelperText !== "" || tipoHelperText !== "" || conceptosServicioHelperText !== "") &&
      helperText === "" &&
      initialValue === "" &&
      (id === "documentos" || id === "tipo" || id === "conceptosServicio")
    ) {
      if (id === "documentos" && documentosHelperText !== "") {
        setHelperText(documentosHelperText);
        setClassName("is-invalid");

      }
      if (id === "tipo" && tipoHelperText !== "") {
        setHelperText(tipoHelperText);
        setClassName("is-invalid");

      }
      if (id === "conceptosServicio" && conceptosServicioHelperText !== "") {
        setHelperText(conceptosServicioHelperText);
        setClassName("is-invalid");
        if (
          prevRow !== undefined && prevRow.row.original.tipo !== row.original.tipo
        ) {
          setHelperText(null);
          setClassName(null);
        }
      }
    }
  }, [documentosHelperText, tipoHelperText, conceptosServicioHelperText, id, helperText, initialValue]);

  const onCallApi = async (idField, value) => {
    switchOption(
      idField,
      () => {
        updateData(index, "penaAplicable", "");
        if (value !== "") {
          onGetDocumentByIdType(value);
          if (type === 3 && value !== "1") {
            updateData(index, "conceptosServicio", "N/A");
          } else if (type === 3) {
            updateData(index, "conceptosServicio", "");
          }
            
          if (type === 3) onGetConceptoServiciosByIdType(value);
        } else {
          updateData(index, "documentosCatalog", []);
          updateData(index, "documentos", "");
          if (type === 3) {
            updateData(index, "conceptoServiciosCatalog", []);
            updateData(index, "conceptosServicio", "");
          }
        }
      },
      () => {
        if (value !== "") {
          const penaAplicable = catalog.filter((item) => item.id == value)[0]
            .penaAplicable;
          updateData(index, "penaAplicable", penaAplicable);
        } else {
          updateData(index, "penaAplicable", "");
        }
      }
    );
  };

  const onConvertDocumentsTypeCatalog = (catalog, typeName) => {
    let newCatalog = [];

    if (isEmpty(catalog) === false) {
      newCatalog = catalog.map((item) => {
        const element = {
          id: null,
          informeDocumental: item.informeDocumental ? item.informeDocumental : item.informeDocumentoConcepto,
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
    if (response !== null && isEmpty(response) === false) {
      const newCatalog = onConvertDocumentsTypeCatalog(
        [...response],
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
    } else if (idArg === "tipo" || idArg === "desglose") {
      return "primaryKey";
    }
  };

  const getNameByID = (idArg) => {
    if (idArg === "documentos") {
      return "informeDocumental";
    } else if (idArg === "tipo" || idArg === "desglose") {
      return "nombre";
    }
  };

  const getCatalogByIdField = () => {
    let selectCatalog = [];
    if (id === "documentos") {
      selectCatalog = documentosCatalog;
    } else if (id === "conceptosServicio") {
      selectCatalog = conceptoServiciosCatalog;
    } else {
      selectCatalog = catalog;
    }
    return selectCatalog;
  };

  const onGetProcess = (tableType) => {
    let process = '';
    if (tableType == 1) {
      process = 'CON_SERV_DICT_PCONT_ADMIN';
    }
    if (tableType == 2) {
      process = 'CON_SERV_DICT_PCONV_ADMIN';
    }
    if (tableType == 3) {
      process = 'CON_SERV_DICT_DEDUC_ADMIN';
    }
    return process;
  }

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

<Authorization process={onGetProcess(tableType)} redirect={
          onGetSelectText()
        } >
            <Select
              name={""}
              onClick={() => {}}
              value={initialValue}
              onChange={(e) => {
                console.log("deijd9ue9d8ue98du ", index, id)
                updateData(index, id, e.target.value);
                if (isEmpty(helperText) === false) {
                  setClassName("is-valid");
                  setHelperText("");
                }
                onCallApi(id, e.target.value);
                if (type == 3 && id === "tipo") onRemoveErrorMessagesByIdRow(index, id);
              }}
              options={getCatalogByIdField()}
              keyValue={keyValue}
              keyTextValue={keyTextValue}
              readOnly={false}
              disabled={
                status === 'Cancelado' || status === 'Dictaminado'
                ?
                true
                :
                id === "conceptosServicio" && tipo != "1" && tipo !== '' ? true : false
              }
              className={className}
              helperText={helperText}
              defaultOptionText="Seleccione una opción"
            />
        </Authorization>


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
