import React, { useState, useEffect, useRef, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Form, Row, Col, Button } from "react-bootstrap";
import { FormCheck as BootstrapFormCheck } from "react-bootstrap";
import { FormCheck as CustomFormCheck, TextField, TextArea } from "../../../../components/formInputs";
import { Loader } from "../../../../components";
import IconButton from "../../../../components/buttons/IconButton";
import { Formik } from "formik";
import * as yup from "yup";
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
import { ToggleCell } from "../componets/ToggleCell";
import { PROVEEDORES as MESSAGES } from '../../../../constants/messages';
import BasicModal from "../../../../modals/BasicModal";
import { Tooltip } from "../../../../components/Tooltip";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import showMessage from '../../../../components/Messages';
import { downloadExcelBlob } from "../../../../functions/utils";
import Authorization from "../../../../components/Authorization";
import { schemaContacto, tableValidationsContacto } from "./utils";

const VALORES_INICIALES = {
  idDirectorioContacto: "",
  nombreContacto: "",
  telefonoOficina: "",
  telefonoCelular: "",
  correoElectronico: "",
  comentarios: "",
  representanteLegal: true,
};

const DirectorioContacto = ({
  chageStatusSeccion,
  ver,
  edit,
  proveedor,
  setProveedor,
  setIdDirectorio,
  idProveedor,
}) => {
  const ID_KEY_NAME = "idDirectorioContacto";
  const { errorToast } = useToast();
  const location = useLocation();
  const navigate = useNavigate();
  const { state } = location;
  const proveedorID = state?.idProveedor || null;
  const effectiveIdProveedor = idProveedor || proveedorID;
  const [loading, setLoading] = useState(true);
  const [dataTable, setDataTable] = useState([]);
  const [valoresIniciales, setValoresIniciales] = useState(VALORES_INICIALES);
  const [datoEliminado, setDatoEliminado] = useState(false);
  const [onEdit, setOnEdit] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [isSubmit, setIsSubmit] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [modalAction, setModalAction] = useState(null);
  const [modalActionData, setModalActionData] = useState(null);
  const [activo, setActivo] = useState(false);
  const [borrar, setBorrar] = useState(false);
  const [cellErrors, setCellErrors] = useState({});
  const tableReference = useRef();
  const esquema = yup.object({
    nombreContacto: yup
      .string()
      .required("Dato requerido")
      .test("no-blank", "No puede ser un espacio en blanco", (value) => value.trim() !== ""),
    // telefonoOficina: yup
    //   .string()
    //   .required("Dato requerido")
    //   .test("no-blank", "No puede ser un espacio en blanco", (value) => value.trim() !== ""),
    correoElectronico: yup.string()
      .matches(
        /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
        "Formato de correo electrónico inválido"
      )
      .nullable()
      .notRequired(),
  });
  const [backMessage, setBackMessage] = useState(false);
  const [successMesage, setSuccessMesage] = useState(false);
  const [initialDataTable, setInitialDataTable] = useState([]);
  const [cancelModal, setCancelModal] = useState(false);
  const [selectedRow, setSelectedRow] = useState();
  const [shouldRefetch, setShouldRefetch] = useState(false);

  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [selectedRowIndex, setSelectedRowIndex] = useState(null);



  useEffect(() => {
    const fetchData = async () => {
      if (datoEliminado) {

      }
      try {
        if (effectiveIdProveedor === null) {
          console.log(proveedorID)
        } else {
          const response = await getData(
            `/proveedores/proveedor/${effectiveIdProveedor}`
          );
          const contactos = response.data.directorioContactos;
          setDataTable(contactos);
          setInitialDataTable(contactos);
        }

      } catch (error) {
        showMessage(MESSAGES.MSG004);
        setLoading(false);
      }

      setLoading(false);
      setShouldRefetch(false);
    };
    fetchData();
  }, [effectiveIdProveedor, datoEliminado, shouldRefetch]);

  const handleSubmit = async (data, { resetForm }) => {
    setLoading(true);
    const table = [...tableReference.current.table.options.meta.getDataTable()];
    const representanteLegal = !!data.representanteLegal;
    const objetoAEnviar = {
      ...data,
      correoElectronico: data.correoElectronico.toLowerCase(),
      telefonoOficina: data.telefonoOficina,
      representanteLegal,
      idProveedor: effectiveIdProveedor,
    };

    try {
      let response;
      if (onEdit) {
        try {
          response = await putData(
            `/proveedores/directorio/${data.idDirectorioContacto}`,
            objetoAEnviar
          );
          setShouldRefetch(true)
          showMessage(MESSAGES.MSG008)
          setOnEdit(false);
          resetForm();
        } catch (error) {
          showMessage(MESSAGES.ERROR);
          setShouldRefetch(true);
          setLoading(false);
        }
      } else {
        try {
          response = await postData(
            "/proveedores/crear-directorio-contacto",
            objetoAEnviar
          );
          showMessage(MESSAGES.MSG008)
          setDataTable([...table, response.data]);
          setInitialDataTable([...initialDataTable, response.data]);
          resetForm();
          setIdDirectorio(response.data.idDirectorioContacto)

        } catch (error) {
          showMessage(MESSAGES.ERROR);
          setLoading(false);
        }

      }
      setOnEdit(false);
      resetForm();
    } catch (error) {
      console.error("Error => ", error);
      if (error?.response?.status === 400) {
        showMessage(MESSAGES.ERROR);
        setLoading(false);
      } else {
        showMessage(MESSAGES.ERROR);
        setLoading(false);
      }
    }
    setLoading(false);
  };

  const handleGoBack = () => {
    setBackMessage(true)
    setShowModal(true);
    setModalMessage(MESSAGES.MSG002);
    setIsSubmit(false);
    setActivo(true)
    setBorrar(false)
  };

  const handleAccept = async () => {
    if (activo === true) {
      navigate("/proveedores/proveedores");
    } else if (borrar === true) {
      await deleteData(`/proveedores/directorio/${modalActionData.idDirectorioContacto}`);
      showMessage(MESSAGES.MSG0011)
      setDatoEliminado((prev) => !prev);
    }
    handleCloseModal();
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setBackMessage(false)
    setCancelModal(false)
  };
  const handleDeny = () => {
    handleCloseModal();
  };

  const rowValidation = async (props) => {
    const rowError = await tableValidationsContacto([props.row.original], schemaContacto);
    if (rowError.dataErrors) {
      const errors = rowError.resultados[0].errors;
      setCellErrors(errors);
      console.log(rowError.resultados[0].errors)
      if (rowError.resultados[0].errors.correoElectronico) {
        errorToast(rowError.resultados[0].errors.correoElectronico);
      } else {
        errorToast(MESSAGES.MSG001);
      }
    } else {
      setCellErrors({});
      props.table.options.meta.getRowData({
        ...props.row,
        action: "save",
      });
      props.row.toggleSelected(!props.row.getIsSelected());
      console.log('no tiene errores')
    }

  };

  const handleDownloadExcel = async () => {
    try {
      setLoading(true);
      const response = await downloadDocument(
        `/proveedores/reporte-directorio?idProveedor=${effectiveIdProveedor}`
      );
      downloadExcelBlob(response.data, "Directorio de contacto");
      setLoading(false);
    } catch (error) {
      setLoading(false);
      showMessage(MESSAGES.MSG005);
    }
  };

  const onChangeData = async (row) => {
    const table = [...tableReference.current.table.options.meta.getDataTable()];
    let newData = {
      ...row.original,
      correoElectronico: row.original.correoElectronico.toLowerCase(),
    };

    const regActual = table.find(
      (option) =>
        option.idDirectorioContacto ===
        row.original.idDirectorioContacto
    );

    if (row.action === 'delete') {
      setShowModal(true);
      setModalMessage(MESSAGES.MSG0010);
      setModalAction("delete");
      setModalActionData(newData);
      setActivo(false)
      setBorrar(true)
    } else if (row.action === 'save') {
      try {
        setLoading(true);
        const response = await putData(
          `/proveedores/directorio/${newData.idDirectorioContacto}`,
          newData
        );
        setDataTable((prev) =>
          prev.map((item) =>
            item.idDirectorioContacto === newData.idDirectorioContacto
              ? { ...response.data, idDirectorioContacto: item.idDirectorioContacto }
              : item
          )
        );
        setInitialDataTable((prev) =>
          prev.map((item) =>
            item.idDirectorioContacto === newData.idDirectorioContacto
              ? { ...response.data, idDirectorioContacto: item.idDirectorioContacto }
              : item
          )
        );
        setLoading(false);
        showMessage(MESSAGES.MSG008);
        setOnEdit(false);
      } catch (error) {
        showMessage(MESSAGES.ERROR);
        setLoading(false);
      }
    } else if (row.action === 'check') {
      try {
        newData.representanteLegal = !newData.representanteLegal;

        const response = await putData(
          `/proveedores/directorio/${newData.idDirectorioContacto}`,
          newData
        );
        showMessage(MESSAGES.MSG008)

        setDataTable((prev) =>
          prev.map((item) =>
            item.idDirectorioContacto === newData.idDirectorioContacto
              ? { ...response.data, idDirectorioContacto: item.idDirectorioContacto }
              : item
          )
        );
        setInitialDataTable((prev) =>
          prev.map((item) =>
            item.idDirectorioContacto === newData.idDirectorioContacto
              ? { ...response.data, idDirectorioContacto: item.idDirectorioContacto }
              : item
          )
        );
      } catch (error) {
        showMessage(MESSAGES.ERROR);
        setShouldRefetch(true);
      }
    }
    if (row.action === 'undo') {
      setSelectedRow(row);
      setCancelModal(true);
    }
  };

  const handleCheckClick = (props) => {
    const currentValue = props.getValue();
    if (currentValue) {
      setSelectedRowIndex(props.row.index);
      setShowConfirmModal(true);
    } else {
      props.table.options.meta.updateData(props.row.index, props.column.id, !currentValue);
      props.table.options.meta.getRowData({ ...props.row, action: 'check' });
    }
  };

  const handleConfirmAccept = async () => {
    const row = tableReference.current.table.getRow(selectedRowIndex);
    const newData = {
      ...row.original,
      representanteLegal: false,
    };

    try {
      const response = await putData(
        `/proveedores/directorio/${newData.idDirectorioContacto}`,
        newData
      );
      showMessage(MESSAGES.MSG008);
      setDataTable((prev) =>
        prev.map((item) =>
          item.idDirectorioContacto === newData.idDirectorioContacto
            ? { ...response.data, idDirectorioContacto: item.idDirectorioContacto }
            : item
        )
      );
      setInitialDataTable((prev) =>
        prev.map((item) =>
          item.idDirectorioContacto === newData.idDirectorioContacto
            ? { ...response.data, idDirectorioContacto: item.idDirectorioContacto }
            : item
        )
      );
    } catch (error) {
      showMessage(MESSAGES.ERROR);
    }
    setShowConfirmModal(false);
  };

  const handleConfirmDeny = () => {
    setShowConfirmModal(false);
  };


  const undoRow = () => {
    const table = [...tableReference.current.table.options.meta.getDataTable()];

    let originalRow = initialDataTable.find(
      item => item.idDirectorioContacto === selectedRow.original.idDirectorioContacto
    );

    setDataTable((prev) =>
      table.map((item) =>
        item.idDirectorioContacto === originalRow.idDirectorioContacto
          ? { ...originalRow }
          : item
      )
    );
  }

  const columns = useMemo(() => {
    const baseColumns = [
      {
        accessorKey: "ordenDirectorio",
        header: "Id",
        cell: (props) => <span>{props.getValue()}</span>,
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "nombreContacto",
        header: "Nombre del contacto",
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
        accessorKey: "telefonoOficina",
        header: "Teléfono oficina",
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
        accessorKey: "telefonoCelular",
        header: "Teléfono celular",
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
        accessorKey: "correoElectronico",
        header: "Correo electrónico",
        cell: (props) => (
          <InputEditableCell
            column={props.column}
            getValue={() => props.getValue().toLowerCase()}
            row={props.row}
            table={props.table}
            cellErrors={cellErrors}
          />
        ),
      },
      {
        accessorKey: "representanteLegal",
        header: "Representante legal",
        cell: (props) => (
          ver === true ? (
            <BootstrapFormCheck
              type="switch"
              checked={props.getValue()}
            />
          ) : (
            <BootstrapFormCheck
              type="switch"
              onClick={() => handleCheckClick(props)}
              checked={props.getValue()}
            />
          )
        ),
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "comentarios",
        header: "Comentarios",
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
    ];

    if (ver !== true) {
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
                  <Authorization process={"PROV_DC_MODIF"}>
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
                  <ToggleCell
                    getValue={props.getValue}
                    column={props.column}
                    row={row}
                    table={table}
                  />
                  <Authorization process={"PROV_DC_DLT"}>
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
  }, [dataTable, ver, cellErrors]);

  return (
    <>
      {loading && <Loader />}
      <Formik
        initialValues={valoresIniciales}
        enableReinitialize
        validationSchema={esquema}
        onSubmit={handleSubmit}
        validateOnMount={true}
      >
        {({
          handleSubmit, handleChange, handleBlur, values, errors, touched, isValid, resetForm,
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={4}>
                <TextField
                  label="Nombre del contacto*:"
                  name="nombreContacto"
                  value={values.nombreContacto}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={touched.nombreContacto &&
                    (errors.nombreContacto ? "is-invalid" : "is-valid")}
                  helperText={touched.nombreContacto ? errors.nombreContacto : ""}
                  disabled={ver} />
              </Col>
              <Col md={4}>
                <TextField
                  label="Teléfono oficina:"
                  name="telefonoOficina"
                  value={values.telefonoOficina}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  disabled={ver} />
              </Col>
              <Col md={4}>
                <TextField
                  label="Teléfono celular:"
                  name="telefonoCelular"
                  value={values.telefonoCelular}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  disabled={ver} />
              </Col>
              <Col md={8}>
                <TextField
                  label="Correo electrónico:"
                  name="correoElectronico"
                  value={values.correoElectronico.toLowerCase()}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={touched.correoElectronico && (errors.correoElectronico ? "is-invalid" : "")}
                  helperText={touched.correoElectronico ? errors.correoElectronico : ""}
                  disabled={ver} />
              </Col>
              <Col md={4}>
                <CustomFormCheck
                  label="Representante legal:"
                  name="representanteLegal"
                  value={values.representanteLegal}
                  onChange={(e) => {
                    handleChange(e);
                  }}
                  disabled={ver} />

              </Col>
              <Col md={8}>
                <TextArea
                  label="Comentarios:"
                  name="comentarios"
                  value={values.comentarios}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  disabled={ver} />
              </Col>
            </Row>
            <Row>
              <Col md={12} className="text-end">
                {ver ? null : (
                  <Authorization process={"PROV_DC_ALTA"}>
                    <div>
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
                        onClick={() => { !isValid && errorToast(MESSAGES.MSG001) }}
                      >
                        Guardar
                      </Button>
                    </div>
                  </Authorization>
                )}
              </Col>
            </Row>
            <Row>
              <Col md={12} className="text-end mb-2">
                <IconButton
                  type="excel"
                  onClick={handleDownloadExcel}
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
        onGetRowData={onChangeData} />
      <BasicModal
        show={showModal}
        size={"md"}
        onHide={handleCloseModal}
        title="Mensaje"
        denyText={"No"}
        handleDeny={handleDeny}
        approveText={"Sí"}
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
      <BasicModal
        show={showConfirmModal}
        size={"md"}
        onHide={handleConfirmDeny}
        title="Confirmación"
        denyText={"No"}
        handleDeny={handleConfirmDeny}
        approveText={"Sí"}
        handleApprove={handleConfirmAccept}
      >
         {MESSAGES.MSG009}
      </BasicModal>
    </>
  );

};

export default DirectorioContacto;
