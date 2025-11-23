import React, { useState, useEffect, useMemo, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Form, Row, Col, Button } from "react-bootstrap";
import { TextField, TextFieldDate, TextArea, Select } from "../../../../components/formInputs";
import { Loader } from "../../../../components";
import IconButton from '../../../../components/buttons/IconButton';
import { Formik } from "formik";
import * as yup from "yup";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { useToast } from "../../../../hooks/useToast";
import { postData, getData, putData, deleteData, downloadDocument } from '../../../../functions/api';
import { InputEditableCell } from '../componets/InputEditableCell';
import { InputDateCell } from '../componets/InputDateCell';
import { InputSemaforoCell } from '../componets/InputSemaforoCell';
import { SemaforoVigencia } from '../componets/SemaforoVigencia';
import { ToggleCell } from '../componets/ToggleCell';
import { PROVEEDORES as MESSAGES } from '../../../../constants/messages';
import BasicModal from "../../../../modals/BasicModal";
import { DropdownCell } from "../componets/DropdownCell";
import { Tooltip } from "../../../../components/Tooltip";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import showMessage from '../../../../components/Messages';
import { downloadExcelBlob } from "../../../../functions/utils";
import Authorization from "../../../../components/Authorization";
import { schemaTitulo, tableValidationsTitulos } from "./utils";
import { logError } from '../../../../components/utils/logError.js';


const VALORES_INICIALES = {
  idTituloServicioProveedor: 0,
  numeroTitulo: "",
  tituloServicio: "",
  estatus: "",
  vencimientoTitulo: "",
  comentarios: "",
  vigencia: "",
  idServicioTitulo: "",
  ordenTitulo: "",
  idProveedor: 0,
};

const InformacionTitulos = ({ idProveedor, setIdTitulos, chageStatusSeccion, ver, edit, proveedor, setProveedor }) => {
  const ID_KEY_NAME = "idTituloServicioProveedor";
  const { errorToast } = useToast();
  const tableReference = useRef();
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
  const [tituloServicioOptions, setTituloServicioOptions] = useState([]);
  const [estatusOptions, setEstatusOptions] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [isSubmit, setIsSubmit] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [modalAction, setModalAction] = useState(null);
  const [modalActionData, setModalActionData] = useState(null);
  const [activo, setActivo] = useState(false);
  const [borrar, setBorrar] = useState(false);
  const [actualiza, setActualiza] = useState({});
  const [initialDataTable, setInitialDataTable] = useState([]);
  const [cancelModal, setCancelModal] = useState(false);
  const [selectedRow, setSelectedRow] = useState();
  const [shouldRefetch, setShouldRefetch] = useState(false);
  const [cellErrors, setCellErrors] = useState({});
  const [estatusTitulos,setEstatusTitulos] = useState([]);



  const esquema = yup.object({
    numeroTitulo: yup
      .string()
      .required("Dato requerido")
      .matches(/^[a-zA-Z0-9/-]{0,10}$/, "Sólo se permiten los caracteres / y - y un máximo de 10 caracteres"),
    tituloServicio: yup.string().required("Dato requerido"),
    estatus: yup.string().required("Dato requerido"),
    vencimientoTitulo: yup.string().required("Dato requerido"),
  });
  const [backMessage, setBackMessage] = useState(false);
  const [successMesage, setSuccessMesage] = useState(false);

  useEffect(() => {
    getData("/proveedores/consultar-todos-estatus-semaforo")
      .then((response) => {
        if(Array.isArray( response?.data)){
          setEstatusTitulos(response.data)
        }
      })
      .catch((err) => {
       logError(err);
      });
  }, []);

  
  useEffect(() => {
    const catalogoDesgloce = async () => {
      try {
        const estatusServicio = await getData('/proveedores/consultar-todos-estatus-semaforo');
        console.log()
        const optionsEstatus = estatusServicio.data.map(item => ({
          semaforoEstatus: item.nombre,
          idEstatusTituloServicio: item.primaryKey,
          estatus: item.estatus,
        }));
        setEstatusOptions(optionsEstatus);
      } catch (error) {
        showMessage(MESSAGES.MSG004);
        setLoading(false);
      }
    }
    catalogoDesgloce()
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      console.log('test')
      if (datoEliminado) {
      }
      try {
        if (effectiveIdProveedor === null) {

        } else {
          const response = await getData(
            `/proveedores/proveedor/${effectiveIdProveedor}`
          );
          console.log(response)
          const contactos = response.data.tituloServicioProveedor;
          setInitialDataTable(contactos);
          setDataTable(contactos);
        }

      } catch (error) {
        showMessage(MESSAGES.MSG004);
        setLoading(false);
      }

      try {
        const tituloServicio = await getData('/proveedores/consultar-todos-titulos-servicios');
        const optionsTitulos = tituloServicio.data.map(item => ({
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

      await deleteData(`/proveedores/eliminar-servicio-proveedor/${modalActionData.idTituloServicioProveedor}`);
      showMessage(MESSAGES.MSG0011)
      setDatoEliminado((prev) => !prev);

    }
    handleCloseModal();
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setBackMessage(false)
  };
  const handleDeny = () => {
    handleCloseModal();
    setCancelModal(false)
  };

  const handleSubmit = async (data, { resetForm }) => {
    setLoading(true);
    const table = [...tableReference.current.table.options.meta.getDataTable()];
    const formatDate = (dateString) => {
      const [year, month, day] = dateString.split('-');
      return `${day}/${month}/${year}`;
    };
    const formattedValue = formatDate(data.vencimientoTitulo);

    const objetoAEnviar = {
      numeroTitulo: data.numeroTitulo,
      vencimientoTitulo: formattedValue,
      comentarios: data.comentarios,
      idServicioTitulo: data.tituloServicio,
      idEstatusTituloServicio: data.estatus,
      idTituloServicio: data.estatus,
      idProveedor: effectiveIdProveedor,
    };
    try {
      let response;

      console.log(data.idTituloServicioProveedor)
      if (onEdit) {
        setLoading(true);
        response = await putData(`/proveedores/actualizar-servicio-proveedor/${data.idTituloServicioProveedor}`, objetoAEnviar);
        setDataTable(prev => prev.map(item => item.idTituloServicioProveedor === data.idTituloServicioProveedor ? response.data : item));
        setDataTable((prev) =>
          table.map((item) =>
            item.idTituloServicioProveedor === data.idTituloServicioProveedor
              ? { ...response.data, idTituloServicioProveedor: item.idTituloServicioProveedor }
              : item
          )
        );
        setInitialDataTable((prev) =>
          prev.map((item) =>
            item.idTituloServicioProveedor === data.idTituloServicioProveedor
              ? { ...response.data, idTituloServicioProveedor: item.idTituloServicioProveedor }
              : item
          )
        );
        setLoading(false);
        showMessage(MESSAGES.MSG008);

        setOnEdit(false);
        setLoading(false);
        setShouldRefetch(true);
        resetForm();
      } else {
        const table = [...tableReference.current.table.options.meta.getDataTable()];

        const objetoEnviado = {
          ...data,
          idTituloServicioProveedor: parseInt(data.idTituloServicioProveedor),
          idEstatusTituloServicio: parseInt(data.estatus),
          idProveedor: effectiveIdProveedor,
          vencimientoTitulo: formattedValue,
          idServicioTitulo: data.tituloServicio,
        };
        response = await postData('/proveedores/crear-servicio-proveedor', objetoEnviado);
        console.log(response.data)
        setDataTable([...table, response.data]);
        showMessage(MESSAGES.MSG008)
        setIdTitulos(response.data.idTituloServicioProveedor)
        setActualiza(response.data);
        setLoading(false);
        /* setShouldRefetch(true); */
      }
      resetForm();
    } catch (error) {
      if (error.response.data.mensaje.includes('El RFC ya se encuentra almacenado en la BD')) {
        showMessage(MESSAGES.MSG007);
        setLoading(false);
      } else if (error.response.data.mensaje.includes('El Nombre del proveedor ya se encuentra almacenado en la BD')) {
        showMessage(MESSAGES.MSG007);
        setLoading(false);
      } else {
        setLoading(false);
        showMessage(MESSAGES.MSG003);
      }
    } finally {
      setLoading(false);
    }
  };

  const rowValidation = async (props) => {
    const rowError = await tableValidationsTitulos([props.row.original], schemaTitulo);

    if (rowError.dataErrors) {
      const errors = rowError.resultados[0].errors;
      setCellErrors(errors);
      console.log(rowError)
      errorToast(MESSAGES.MSG001);
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
      const response = await downloadDocument(`/proveedores/reporte-titulos-servicio?idProveedor=${effectiveIdProveedor}`);
      downloadExcelBlob(response.data, "Títulos de servicio");
      setLoading(false);
    } catch (error) {
      setLoading(false);
      showMessage(MESSAGES.MSG005);
    }
  };

  const CirculoEstatus = ({ color,idEstatusTituloServicio,estatusTitulos }) => {
    const colorMap = {
      'Verde': '#228B22',
      'Amarillo': '#FFD700',
      'Azul': '#0071B3',
      'Rojo': '#FF0000',
      'verde': '#228B22',
      'amarillo': '#FFD700',
      'azul': '#0071B3',
      'rojo': '#FF0000',
      'gris': 'gray',
      'Gris': 'gray',
    };

    const tooltipTextMap = {
      'Verde': 'Vigente',
      'verde': 'Vigente',
      'Amarillo': 'En opinión de la ANAM',
      'amarillo': 'En opinión de la ANAM',
      'Azul': 'En revisión electrónica',
      'azul': 'En revisión electrónica',
      'Rojo': 'Rechazo por dictamen técnico',
      'rojo': 'Rechazo por dictamen técnico',
      'gris': 'No Vigente',
      'Gris': 'No Vigente',
      
    };


    const circleColor = colorMap[color] || 'gray';
    // const tooltipText = tooltipTextMap[color] || 'No Vigente';
    let tooltipText = "No Vigente";
    let tooltipDescripcion = "";
    if (Array.isArray(estatusTitulos)) {
      let estatus = estatusTitulos.find(
        (s) => s.primaryKey == idEstatusTituloServicio
      );
      if (estatus) {
        tooltipText = estatus.nombre;
      }
    }
    const divStyle = {
      marginTop: "0px",
      display: "flex",
      alignItems: "start",
      // whiteSpace: "nowrap",
      textAlign: "start"
    };
    const circleStyle = {
      minWidth: "16px",
      minHeight: "16px",
      maxWidth: "16px",
      maxHeight: "16px",
      borderRadius: "50%",
      backgroundColor: circleColor,
      display: "inline-block",
      marginRight: "5px",
    };
    return (

      <Tooltip text={tooltipText}>
        <div style={divStyle}>
          <div
            style={circleStyle}
          />
          <span>{tooltipText}</span>
        </div>
      </Tooltip>
    );
  };

  const CirculoVigencia = ({ color }) => {
    if (!color) {
      return null;
    }

    const isZeroM = color === '0m';
    const numero = parseFloat(color.replace('m', ''));

    const circleColor = (num, isZeroM) => {
      if (isZeroM) return '#FF0000';
      if (num === 0) return null;
      if (num > 0 && num <= 3) return '#FF0000';
      if (num > 3) return 'white';
      return 'gray';
    };

    const borderColor = (num, isZeroM) => {
      if (num > 3) return 'black';
      return 'transparent';
    };

    const tooltipText = (num, isZeroM) => {
      if (isZeroM || (num > 0 && num <= 3)) {
        return 'Menor a tres meses respecto a la fecha de hoy';
      }
      return 'Mayor a tres meses respecto a la fecha de hoy';
    };

    const circleStyle = {
      width: '16px',
      height: '16px',
      borderRadius: '50%',
      backgroundColor: circleColor(numero, isZeroM),
      display: 'inline-block',
      marginRight: '5px',
      border: `1px solid ${borderColor(numero, isZeroM)}`
    };

    return circleColor(numero, isZeroM) ? (
      <Tooltip text={tooltipText(numero, isZeroM)}>
        <div style={circleStyle} />
      </Tooltip>
    ) : null;
  };

  const onChangeData = async (row) => {
    const table = [...tableReference.current.table.options.meta.getDataTable()];
    console.log(row)
    const tituloServicioOption = tituloServicioOptions.find(
      option => option.nombreTituloServicio === row.original.nombreTituloServicio
    );

    const idServicioTitulo = tituloServicioOption
      ? tituloServicioOption.idServicioTitulo
      : isNaN(parseInt(row.original.nombreTituloServicio, 10))
        ? tituloServicioOption.idServicioTitulo
        : parseInt(row.original.nombreTituloServicio, 10);

    const data = {
      idTituloServicioProveedor: row.original.idTituloServicioProveedor,
      numeroTitulo: row.original.numeroTitulo,
      vencimientoTitulo: row.original.vencimientoTitulo,
      comentarios: row.original.comentarios,
      idServicioTitulo: idServicioTitulo,
      idEstatusTituloServicio: parseInt(row.original.idEstatusTituloServicio, 10),
      idProveedor: row.original.idProveedor || effectiveIdProveedor,
      vigencia: row.original.vigencia
    };

    if (row.action === 'delete') {
      setShowModal(true);
      setModalMessage(MESSAGES.MSG0010);
      setModalAction("delete");
      setModalActionData(data);
      setActivo(false)
      setBorrar(true)
    }
    if (row.action === 'save') {
      setLoading(true);
      try {
        let response = await putData(
          `/proveedores/actualizar-servicio-proveedor/${data.idTituloServicioProveedor}`,
          data
        );
        setDataTable((prev) =>
          table.map((item) =>
            item.idTituloServicioProveedor === data.idTituloServicioProveedor
              ? { ...response.data, idTituloServicioProveedor: item.idTituloServicioProveedor }
              : item
          )
        );
        setInitialDataTable((prev) =>
          prev.map((item) =>
            item.idTituloServicioProveedor === data.idTituloServicioProveedor
              ? { ...response.data, idTituloServicioProveedor: item.idTituloServicioProveedor }
              : item
          )
        );
        showMessage(MESSAGES.MSG008)
        setLoading(false);
        setOnEdit(false);
      } catch (error) {
        showMessage(MESSAGES.MSG003);
        setLoading(false);
      }
    }

    if (row.action === 'undo') {
      setSelectedRow(row);

    }
  };

  const undoRow = () => {
    const table = [...tableReference.current.table.options.meta.getDataTable()];

    let originalRow = initialDataTable.find(
      item => item.idTituloServicioProveedor === selectedRow.original.idTituloServicioProveedor
    );

    setDataTable((prev) =>
      table.map((item) =>
        item.idTituloServicioProveedor === originalRow.idTituloServicioProveedor
          ? { ...originalRow }
          : item
      )
    );
  }
  const handleInputChange = (e, handleChange) => {
    const { value, name } = e.target;
    const regex = /^[a-zA-Z0-9/-]{0,10}$/;

    if (regex.test(value) || value === '') {
      handleChange(e);
    }
  };

  const columns = useMemo(
    () => [
      {
        accessorKey: "ordenTitulo",
        header: "Id",
        cell: (props) => (
          <span>{props.row.index + 1}</span>
        ),
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "numeroTitulo",
        header: "Número de título",
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
        accessorKey: "nombreTituloServicio",
        header: "Título de servicio",
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
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "idEstatusTituloServicio",
        header: "Estatus",
        cell: (props) => 
          {
            let {idEstatusTituloServicio,semaforoEstatus:color}= props.row.original;
            
            return(
          <InputSemaforoCell
            estatusOptions={estatusOptions}
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
            keyStatus="estatus"
            hideDisabledOptions={true}
            cellErrors={cellErrors}
            render={() => <CirculoEstatus color={color} estatusTitulos={estatusTitulos} idEstatusTituloServicio={idEstatusTituloServicio}  />}
          />
        )},
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "vencimientoTitulo",
        header: "Fecha de vencimiento",
        cell: (props) => (
          <InputDateCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
            cellErrors={cellErrors}
          />
        ),
      },
      {
        accessorKey: "vigencia",
        header: "Vigencia",
        cell: (props) => (
          <SemaforoVigencia
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
            render={() => <CirculoVigencia

              color={props.row.original.vigencia} />}
          />
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
      ...(ver !== true ? [
        {
          accessorKey: "acciones",
          header: "Acciones",
          cell: (props) => {
            const { row, table } = props;
            const isSelected = row.getIsSelected();
            const handleAction = (action) => {
              table.options.meta.getRowData({ ...row, action });
              row.toggleSelected(!isSelected);
            };
            return (
              <>
                {isSelected ? (
                  <>
                    <Authorization process={"PROV_TA_MODIF"}>
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
                          setCancelModal(true)
                        }}
                        tooltip={"Descartar"}
                        tableContainer
                      />
                    </Authorization>
                  </>
                ) : (
                  <>
                    <Authorization process={"PROV_TA_DLT"}>
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
                          props.table.options.meta.getRowData({ ...props.row, action: 'delete' });
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
        }
      ] : []),
    ],
    [tituloServicioOptions, actualiza, cellErrors,estatusTitulos]
  );

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
          handleSubmit, handleChange, handleBlur, values, errors, touched, isValid, resetForm
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={4}>
                <TextField
                  label="Número del título*:"
                  name="numeroTitulo"
                  value={values.numeroTitulo}
                  onChange={(e) => handleInputChange(e, handleChange)}
                  onBlur={handleBlur}
                  className={touched.numeroTitulo && (errors.numeroTitulo ? 'is-invalid' : 'is-valid')}
                  helperText={touched.numeroTitulo ? errors.numeroTitulo : ''}
                  disabled={ver === true}
                  inputProps={{ maxLength: 10 }}
                />
              </Col>
              <Col md={4}>
                <Select
                  label="Título de servicio*:"
                  name="tituloServicio"
                  value={values.tituloServicio}
                  onChange={handleChange}
                  options={tituloServicioOptions}
                  keyValue="idServicioTitulo"
                  keyStatus="estatus"
                  hideDisabledOptions
                  keyTextValue="nombreTituloServicio"
                  onBlur={handleBlur}
                  className={touched.tituloServicio && (errors.tituloServicio ? 'is-invalid' : 'is-valid')}
                  helperText={touched.tituloServicio ? errors.tituloServicio : ''}
                  disabled={ver === true} />
              </Col>
              <Col md={4}>
                <Select
                  label="Estatus*:"
                  name="estatus"
                  value={values.estatus}
                  onChange={handleChange}
                  options={estatusOptions}
                  keyValue="idEstatusTituloServicio"
                  keyTextValue="semaforoEstatus"
                  keyStatus="estatus"
                  hideDisabledOptions
                  onBlur={handleBlur}
                  className={touched.estatus && (errors.estatus ? 'is-invalid' : 'is-valid')}
                  helperText={touched.estatus ? errors.estatus : ''}
                  disabled={ver === true} />
              </Col>
              <Col md={4}>
                <TextFieldDate
                  label={"Vencimiento de título*:"}
                  name="vencimientoTitulo"
                  value={values.vencimientoTitulo}
                  onChange={handleChange}
                  helperText={touched.vencimientoTitulo ? errors.vencimientoTitulo : ""}
                  className={touched.vencimientoTitulo &&
                    (errors.vencimientoTitulo ? "is-invalid" : "is-valid")}
                  onBlur={handleBlur}
                  disabled={ver === true} />
              </Col>
              <Col md={8}>
                <TextArea
                  label="Comentarios:"
                  name="comentarios"
                  value={values.comentarios}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  disabled={ver === true} />
              </Col>
            </Row>
            <Row>
              <Col md={12} className="text-end">
                {ver === true ? (
                  null
                ) : (
                  <div>
                    <Authorization process={"PROV_TA_ALTA"}>
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
        onGetRowData={onChangeData} />
      <BasicModal
        show={showModal}
        onHide={handleCloseModal}
        size={"md"}
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
    </>
  );
};

export default InformacionTitulos;
