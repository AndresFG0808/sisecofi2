import React, { useState, useEffect, useRef, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Form, Row, Col, Button } from "react-bootstrap";
import { Loader } from "../../../../components";
import { Formik } from "formik";
import * as yup from "yup";
import { Select, TextField, TextArea } from "../../../../components/formInputs";
import IconButton from "../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { useToast } from "../../../../hooks/useToast";
import {
  postData,
  getData,
  putData,
  deleteData,
  downloadDocument,
} from "../../../../functions/api";
import { InputEditableCell } from "../componets/InputEditableCell";
import { DropdownCell } from "../componets/DropdownCell";
import { ToggleCell } from "../componets/ToggleCell";
import { PROVEEDORES as MESSAGES } from "../../../../constants/messages";
import BasicModal from "../../../../modals/BasicModal";
import showMessage from "../../../../components/Messages";
import { downloadExcelBlob } from "../../../../functions/utils";
import Authorization from "../../../../components/Authorization";
import {catalogFilterResultadoByText,catalogFilterByText, schema, tableValidations } from "./utils";

const VALORES_INICIALES = {
  idDictamenTecnicoProveedor: "",
  responsable: "",
  idServicioTitulo: "",
  idResultadoDictamenTecnico: "",
  observacion: "",
  idProveedor: "",
  servicio: "",
  resultado: "",
  estadoResultado: "",
  anio: "",
  tituloServicio: "",
};
const DictamenTecnico = ({
  idProveedor,
  ver,
}) => {
  const { state } = useLocation();
  const { errorToast } = useToast();
  const proveedorID = state?.idProveedor || null;
  const effectiveIdProveedor = idProveedor || proveedorID;
  const [resultadoOptions, setResultadoOptions] = useState([]);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [dataTable, setDataTable] = useState([]);
  const [tituloServicioOptions, setTituloServicioOptions] = useState([]);
  const [datoEliminado, setDatoEliminado] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [actualiza, setActualiza] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [modalAction, setModalAction] = useState(null);
  const [modalActionData, setModalActionData] = useState(null);
  const [activo, setActivo] = useState(false);
  const [borrar, setBorrar] = useState(false);
  const [cellErrors, setCellErrors] = useState({});
  const [initialDataTable, setInitialDataTable] = useState([]);
  const [backMessage, setBackMessage] = useState(false);
  const [cancelModal, setCancelModal] = useState(false);
  const [selectedRow, setSelectedRow] = useState();
  const tableReference = useRef();
  const [shouldRefetch, setShouldRefetch] = useState(false);
 
  const esquema = yup.object({
    tituloServicio: yup.string().required("Dato requerido"),
    anio: yup
      .string()
      .required("Dato requerido")
      .matches(/^\d{4}$/, "Debe ser un año de 4 dígitos"),
    responsable: yup
      .string()
      .required("Dato requerido")
      .test("no-blank", "No puede ser un espacio en blanco", (value) => value.trim() !== ""),
    estadoResultado: yup
      .string()
      .required("Dato requerido")
      .test("no-blank", "No puede ser un espacio en blanco", (value) => value.trim() !== ""),
    observacion: yup
      .string()
      .required("Dato requerido")
      .test("no-blank", "No puede ser un espacio en blanco", (value) => value.trim() !== ""),
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (effectiveIdProveedor === null) {
          console.log(proveedorID);
        } else {
          const response = await getData(
            `/proveedores/proveedor/${effectiveIdProveedor}`
          );
          console.log(response.data.dictamenTecnico);
          setInitialDataTable(response.data.dictamenTecnico);
          setDataTable(response.data.dictamenTecnico);
        }
      } catch (error) {
        showMessage(MESSAGES.MSG004);
        setLoading(false);
      }

      try {
        const response = await getData(
          `/proveedores/obtener-resultado-dictamen`
        );
        const optionsResultado = response.data.map((item) => ({
          resultado: item.nombre,
          idResultadoDictamenTecnico: item.primaryKey,
        }));
        setResultadoOptions(optionsResultado);
      } catch (error) {
        showMessage(MESSAGES.MSG004);
        setLoading(false);
      }

      try {
        const tituloServicioData = await getData(
          "/proveedores/consultar-todos-titulos-servicios"
        );
        const optionsTitulos = tituloServicioData.data.map((item) => ({
          nombreTituloServicio: item.nombre,
          idServicioTitulo: item.primaryKey,
          estatus: item.estatus,
        }));
        setTituloServicioOptions(optionsTitulos);
      } catch (error) {
        showMessage(MESSAGES.MSG004);
        setLoading(false);
      }
      setLoading(false);
      setShouldRefetch(false);
    };
    fetchData();
  }, [effectiveIdProveedor, datoEliminado, shouldRefetch]);


  const handleServicioFiltro = (...args) =>
    catalogFilterByText(tituloServicioOptions, ...args);
  
  const handleResultadoFiltro = (...args) =>
    catalogFilterResultadoByText(resultadoOptions, ...args);

  const handleSubmit = async (data, { resetForm }) => {
    setLoading(true);
    const table = [...tableReference.current.table.options.meta.getDataTable()];

    const objetoAEnviar = {
      ...data,
      idServicioTitulo: parseInt(data.tituloServicio),
      idResultadoDictamenTecnico: parseInt(data.estadoResultado),
      idProveedor: effectiveIdProveedor,
    };

    try {
      let response = await postData(
        "/proveedores/crear-dictamen-tecnico",
        objetoAEnviar
      );
      showMessage(MESSAGES.MSG008);
      setDataTable([...table, response.data]);
      setInitialDataTable([...initialDataTable, response.data]);
      resetForm();
    } catch (error) {
      showMessage(MESSAGES.ERROR);
      setLoading(false);
    }
    setLoading(false);
  };

  const rowValidation = async (props) => {
    const rowError = await tableValidations([props.row.original], schema);
  
    if (rowError.dataErrors) {
      const errors = rowError.resultados[0].errors;
      setCellErrors(errors);
      errorToast(MESSAGES.MSG001);
    } else {
      setCellErrors({});
      props.table.options.meta.getRowData({
        ...props.row,
        action: "save",
      });
      props.row.toggleSelected(!props.row.getIsSelected());
    }
  };

  const handleDownloadExcel = async () => {
    try {
      setLoading(true);
      const response = await downloadDocument(
        `/proveedores/reporte-dictamen-tecnico?idProveedor=${effectiveIdProveedor}`
      );
      downloadExcelBlob(response.data, "Dictamen técnico");
      setLoading(false);
    } catch (error) {
      setLoading(false);
      showMessage(MESSAGES.MSG005);
    }
  };

  const handleGoBack = () => {
    setBackMessage(true);
    setShowModal(true);
    setModalMessage(MESSAGES.MSG002);
    setIsSubmit(false);
    setActivo(true);
    setBorrar(false);
  };

  const handleAccept = async () => {
    if (borrar === true) {
      await deleteData(
        `/proveedores/eliminar-dictamen-tecnico/${modalActionData.idDictamenTecnicoProveedor}`
      );
      setDatoEliminado((prev) => !prev);
      setShouldRefetch(true);
      showMessage(MESSAGES.MSG0011);
    } else if (activo === true) {
      navigate("/proveedores/proveedores");
    }
    handleCloseModal();
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setBackMessage(false);
    setCancelModal(false);
  };

  const handleDeny = () => {
    handleCloseModal();
  };

  const onChangeData = async (row) => {
    const table = [...tableReference.current.table.options.meta.getDataTable()];
    const regActual = table.find(
      (option) =>
        option.idDictamenTecnicoProveedor ===
        row.original.idDictamenTecnicoProveedor
    );

    const tituloServicioOption = tituloServicioOptions.find(
      (option) => option.idServicioTitulo === Number(regActual.nombreTituloServicio)
    );

    const data = {
      idDictamenTecnicoProveedor: regActual.idDictamenTecnicoProveedor,
      responsable: regActual.responsable,
      anio: regActual.anio,
      observacion: regActual.observacion,
      idProveedor: regActual.idProveedor || state?.idProveedor,
      idServicioTitulo: tituloServicioOption.idServicioTitulo,
      idResultadoDictamenTecnico: regActual.resultado
        ? parseInt(regActual.resultado, 10)
        : "",
    };

    if (row.action === "delete") {
      setShowModal(true);
      setModalMessage(MESSAGES.MSG0010);
      setBackMessage(true);
      setModalAction("delete");
      setModalActionData(data);
      setActivo(false);
      setBorrar(true);
    }
    if (row.action === "save") {
      try {
        setLoading(true);
        let response = await putData(
          `/proveedores/actualizar-dictamen-tecnico/${data.idDictamenTecnicoProveedor}`,
          data
        );
        setDataTable((prev) =>
          table.map((item) =>
            item.idDictamenTecnicoProveedor === data.idDictamenTecnicoProveedor
              ? { ...response.data, ordenDictamenProveedor: item.ordenDictamenProveedor }
              : item
          )
        );
        setInitialDataTable((prev) =>
          prev.map((item) =>
            item.idDictamenTecnicoProveedor === data.idDictamenTecnicoProveedor
              ? { ...response.data, ordenDictamenProveedor: item.ordenDictamenProveedor }
              : item
          )
        );
        setLoading(false);
        showMessage(MESSAGES.MSG008);
      } catch (error) {
        showMessage(MESSAGES.ERROR);
        setLoading(false);
      }
    }
    if (row.action === "undo") {
      setSelectedRow(row);
    }
  };

  const undoRow = () => {
    const table = [...tableReference.current.table.options.meta.getDataTable()];

    let originalRow = initialDataTable.find(
      item => item.idDictamenTecnicoProveedor === selectedRow.original.idDictamenTecnicoProveedor
    );

    setDataTable((prev) =>
      table.map((item) =>
        item.idDictamenTecnicoProveedor === originalRow.idDictamenTecnicoProveedor
          ? { ...originalRow }
          : item
      )
    );
  }

  const columns = useMemo(() => {
    const baseColumns = [
      {
        accessorKey: "ordenDictamenProveedor",
        header: "Id",
        cell: (props) => <span>{props.getValue()}</span>,
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "nombreTituloServicio",
        header: "Servicio",
        filterFn: handleServicioFiltro,
        cell: (props) => (
          <DropdownCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
            options={tituloServicioOptions}
            keyValue="idServicioTitulo"
            keyTextValue="nombreTituloServicio"
            displayProperty={"nombreTituloServicio"}
            name={"tituloServicio"}
            keyStatus="estatus"
            hideDisabledOptions={true}
            cellErrors={cellErrors}
          />
        ),
        enableSorting: true,
        enableColumnFilter: true,
      },
      {
        accessorKey: "anio",
        header: "Año",
        cell: (props) => (
          <InputEditableCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
            cellErrors={cellErrors}
          />
        ),
      },
      {
        accessorKey: "responsable",
        header: "Responsable",
        cell: (props) => (
          <InputEditableCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
            cellErrors={cellErrors}
            validate={(value) => value.trim() !== ""}
          />
        ),
      },
      {
        accessorKey: "resultado",
        header: "Resultado",
        filterFn: handleResultadoFiltro,
        cell: (props) => {
          return (
            <DropdownCell
              name="estadoResultado"
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              table={props.table}
              options={resultadoOptions}
              keyValue="idResultadoDictamenTecnico"
              keyTextValue="resultado"
              displayProperty={"resultado"}
              validate={(value) => value.trim() !== ""}
              cellErrors={cellErrors}
              hideDisabledOptions={false}
            />
          );
        },
        enableSorting: true,
        enableColumnFilter: true,
      },
      {
        accessorKey: "observacion",
        header: "Observación",
        cell: (props) => (
          <InputEditableCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            cellErrors={cellErrors}
            table={props.table}
          />
        ),
      },
    ];
  
    if (!ver) {
      baseColumns.push({
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) => {
          const { row, table } = props;
          const isSelected = row.getIsSelected();
  
          return (
            <>
              {isSelected ? (
                <>
                  <Authorization process={"PROV_DT_MODIF"}>
                    <IconButton
                      type="save"
                      iconSize="lg"
                      onClick={() => {
                        rowValidation(props)
                      }}
                      tooltip={"Guardar"}
                      tableContainer
                    />
                    <IconButton
                      type="undo"
                      iconSize="lg"
                      onClick={() => {
                        props.table.options.meta.getRowData({ ...props.row, action: 'undo' });
                        setSelectedRow(props.row);
                        setCancelModal(true);
                      }}
                      tooltip={"Descartar"}
                      tableContainer
                    />
                  </Authorization>
                </>
              ) : (
                <>
                  <Authorization process={"PROV_DT_DLT"}>
                    <ToggleCell
                      getValue={props.getValue}
                      column={props.column}
                      row={row}
                      table={table}
                    />
                    <IconButton
                      type="drop"
                      iconSize="lg"
                      onClick={() => {
                        props.table.options.meta.getRowData({
                          ...props.row,
                          action: "delete",
                        });
                      }}
                      tooltip={"Eliminar"}
                      tableContainer
                    />
                  </Authorization>
                </>
              )}
            </>
          );
        },
        enableSorting: false,
        enableColumnFilter: false,
      });
    }
  
    return baseColumns;
  }, [actualiza, tituloServicioOptions, resultadoOptions, cellErrors]);

  return (
    <>
      {loading && <Loader />}
      <Formik
        initialValues={{ ...VALORES_INICIALES }}
        enableReinitialize
        validationSchema={esquema}
        onSubmit={handleSubmit}
        validateOnMount={true}
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          values,
          errors,
          touched,
          isValid,
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={4}>
                <Select
                  label="Servicio*:"
                  name="tituloServicio"
                  value={values.tituloServicio}
                  onChange={handleChange}
                  options={tituloServicioOptions}
                  keyValue="idServicioTitulo"
                  keyStatus="estatus"
                  hideDisabledOptions
                  keyTextValue="nombreTituloServicio"
                  onBlur={handleBlur}
                  className={
                    touched.tituloServicio &&
                    (errors.tituloServicio ? "is-invalid" : "is-valid")
                  }
                  helperText={
                    touched.tituloServicio ? errors.tituloServicio : ""
                  }
                  disabled={ver === true}
                />
              </Col>
              <Col md={4}>
                <TextField
                  label="Año*:"
                  name="anio"
                  value={values.anio}
                  onChange={(e) => {
                    const { value } = e.target;
                    if (/^\d{0,4}$/.test(value)) {
                      handleChange(e);
                    }
                  }}
                  onBlur={handleBlur}
                  className={
                    touched.anio && (errors.anio ? "is-invalid" : "is-valid")
                  }
                  helperText={touched.anio ? errors.anio : ""}
                  disabled={ver === true}
                  inputProps={{
                    maxLength: 4,
                  }}
                  inputMode="numeric"
                />
              </Col>

              <Col md={4}>
                <TextField
                  label="Responsable*:"
                  name="responsable"
                  value={values.responsable}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={
                    touched.responsable &&
                    (errors.responsable ? "is-invalid" : "is-valid")
                  }
                  helperText={touched.responsable ? errors.responsable : ""}
                  disabled={ver === true}
                />
              </Col>
              <Col md={4}>
                <Select
                  label="Resultado*:"
                  name="estadoResultado"
                  value={values.estadoResultado}
                  onChange={handleChange}
                  options={resultadoOptions}
                  keyValue="idResultadoDictamenTecnico"
                  keyTextValue="resultado"
                  onBlur={handleBlur}
                  className={
                    touched.estadoResultado &&
                    (errors.estadoResultado ? "is-invalid" : "is-valid")
                  }
                  helperText={
                    touched.estadoResultado ? errors.estadoResultado : ""
                  }
                  disabled={ver === true}
                />
              </Col>
              <Col md={8}>
                <TextArea
                  label="Observación*:"
                  name="observacion"
                  value={values.observacion}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={
                    touched.observacion &&
                    (errors.observacion ? "is-invalid" : "is-valid")
                  }
                  helperText={touched.observacion ? errors.observacion : ""}
                  disabled={ver === true}
                />
              </Col>
            </Row>
            <Row>
              <Col md={12} className="text-end">
                {ver ? null : (
                  <div>
                    <Authorization process={"PROV_DT_ALTA"}>
                      <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={handleGoBack}
                      >
                        Cancelar
                      </Button>
                      <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="submit"
                        onClick={() => {
                          !isValid && errorToast(MESSAGES.MSG001);
                        }}
                      >
                        Guardar
                      </Button>
                    </Authorization>
                  </div>
                )}
              </Col>
            </Row>
            <Row>
              <Col md={12} className="text-end mb-2">
                <IconButton
                  type="excel"
                  onClick={handleDownloadExcel}
                  disabled={dataTable.length === 0}
                  tooltip={"Exportar a Excel"}
                />
              </Col>
            </Row>
          </Form>
        )}
      </Formik>
      <TablaEditable
        ref={tableReference}
        dataTable={dataTable}
        columns={columns}
        hasPagination
        isFiltered
        onDelete={setDataTable}
        onUpdate={setDataTable}
        onGetRowData={onChangeData}
      />
      <BasicModal
        show={showModal}
        onHide={handleCloseModal}
        size={"md"}
        title="Mensaje"
        denyText={backMessage ? "No" : "Cerrar"}
        handleDeny={handleDeny}
        approveText={backMessage ? "si" : "Aceptar"}
        handleApprove={handleAccept}
      >
        {modalMessage}
      </BasicModal>
      <BasicModal
        show={cancelModal}
        onHide={handleCloseModal}
        size={"md"}
        title="Mensaje"
        denyText={"No"}
        handleDeny={handleDeny}
        approveText={"Sí"}
        handleApprove={() => {
          if (selectedRow) {
            selectedRow.toggleSelected(!selectedRow.getIsSelected(), {
              selectChildren: false,
            });
            undoRow();
          }
          setCancelModal(false);
        }}
      >
        {MESSAGES.MSG002}
      </BasicModal>
    </>
  );
};

export default DictamenTecnico;
