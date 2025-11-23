import React, { useState, useEffect, useMemo } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import Loader from "../../../components/Loader";
import * as yup from "yup";
import { useToast } from "../../../hooks/useToast";
import { postData, getData, putData, deleteData, downloadDocument } from '../../../functions/api';
import { InputEditableCell } from './componets/InputEditableCell';
import { InputIconCell } from './componets/InputIconCell';
import { Semaforo } from './componets/Semaforo';
import { TablaEditable } from "../../../components/table/TablaEditable";
import { Formik } from "formik";
import { Form, Row, Col, Button } from "react-bootstrap";
import IconButton from "../../../components/buttons/IconButton";
import BasicModal from "../../../modals/BasicModal";
import { Tooltip } from "../../../components/Tooltip";
import showMessage from '../../../components/Messages';
import { CREAR_ESTIMACION as MESSAGES } from '../../../constants/messages';
import { downloadExcelBlob } from "../../../functions/utils";
import { filterFns } from '@tanstack/react-table';


const VALORES_INICIALES = {
  idDictamen: "",
  comprobanteFiscal: "",
  convenioColaboracion: "",
  estatus: "",
  monto: "",
  montoPesos: "",
  tipoCambioReferencial: "",
};

const FacturasAsociadas = ({ idProveedor, idContrato, chageStatusSeccion, ver, edit, proveedor, setProveedor, idEstimacion }) => {
  const { successToast, infoToast, errorToast } = useToast();
  const ID_KEY_NAME = "idTituloServicioProveedor";
  const location = useLocation();
  const navigate = useNavigate();
  const { state } = location;
  const proveedorID = state?.idProveedor || null;
  const effectiveIdProveedor = idProveedor || proveedorID;
  const [loading, setLoading] = useState(true);
  const [dataTable, setDataTable] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [valoresIniciales, setValoresIniciales] = useState(VALORES_INICIALES);
  const filterFnCustom = (row, columnId, filterValue) => {
    const cellValue = parseFloat(row.getValue(columnId));
    return cellValue.toString().includes(filterValue);
  };

  const esquema = yup.object({
    /* numeroTitulo: yup.string().required("Dato requerido"),
    tituloServicio: yup.string().required("Dato requerido"),
    estatus: yup.string().required("Dato requerido"),
    vencimientoTitulo: yup.string().required("Dato requerido"), */
  });

  useEffect(() => {
    const fetchData = async () => {
      if (idEstimacion) {
        try {
          const response = await getData(
            `/admin-devengados/estimacion/facturas-relacionadas?id=${idEstimacion}`
          );
          console.log(response.data)
          const dicRelacionados = response.data;
          setDataTable(dicRelacionados);
          setLoading(false);
        } catch (error) {
          console.log(error)
          showMessage("Problema para consultar estimaciónes ");
          setLoading(false);
        }
      } else {
        setLoading(false);
      }
    };
    fetchData();
  }, [effectiveIdProveedor]);


  const handleDownloadExcel = async () => {
    try {
      const response = await downloadDocument(

        `/admin-devengados/estimacion/facturas-relacionadas-exportar?id=${idEstimacion}`
      );
      downloadExcelBlob(response.data, "Facturas asociadas");
    } catch (error) {
      showMessage(MESSAGES.MSG007);
    }
  };

  const onChangeData = async (row) => {

  };

  const columns = useMemo(
    () => [
      {
        accessorKey: "idDictamen",
        header: "Id del dictamen",
        cell: (props) => {
          const dictamenState = {
            idDictamen: props.getValue(),
            idContrato: idContrato,
            idProveedor: idProveedor,
            editableDuplicateButton: true,
            editable: true,
          };

          return (
            <Link
              to="/consumoServicios/consumoServicios/dictamen"
              state={{ dictamenState }}
            >
              {props.getValue()}
            </Link>
          );
        },
      },
      {
        accessorKey: "comprobanteFiscal",
        header: "Comprobante fiscal",
        cell: (props) => (
          <span>{props.getValue()}</span>
        ),
      },
      {
        accessorKey: "convenioColaboracion",
        header: "Convenio de colaboración",
        cell: (props) => (
          <InputIconCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
          />
        ),
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "monto",
        header: "Monto",
        cell: (props) => {
          const originalValue = props.getValue();
          const formattedValue = new Intl.NumberFormat('es-MX', {
            style: 'currency',
            currency: 'MXN',
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
          }).format(originalValue);

          return <span>{formattedValue}</span>;
        },
        filterFn: 'includesString',
        enableColumnFilter: true,
      },
      {
        accessorKey: "montoPesos",
        header: "Monto en pesos",
        cell: (props) => {
          const originalValue = props.getValue();
          const formattedValue = new Intl.NumberFormat('es-MX', {
            style: 'currency',
            currency: 'MXN',
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
          }).format(originalValue);

          return <span>{formattedValue}</span>;
        },
        filterFn: filterFnCustom,
        enableColumnFilter: true,
      },
      {
        accessorKey: "estatus",
        header: "Estatus",
        cell: (props) => (
          <span>{props.getValue()}</span>
        ),
      },
      {
        accessorKey: "tipoCambioReferencial",
        header: "Tipo de cambio",
        cell: (props) => (
          <span>{props.getValue()}</span>
        ),
      }
    ],
    []
  );

  const handleAccept = () => {

    navigate('/consumoServicios/consumoServicios');
    handleCloseModal();

  };

  const handleDeny = () => {
    handleCloseModal();
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleGoBack = () => {
    setShowModal(true);
  };

  const handleSubmit = async (data, { resetForm }) => {
    setLoading(true);
    const objetoAEnviar = {
      ...data,
    };

    try {
      let response;
      if (edit === true) {
        response = console.log("putDataPath");
        showMessage(MESSAGES.MSG001);
        setLoading(false);
      } else {
        response = console.log("PostData")
        showMessage(MESSAGES.MSG001);
        setLoading(false);
      }
    } catch (error) {
      if (error.response.data.mensaje.includes('ya se encuentra registro')) {
        showMessage(MESSAGES.MSG005)
        setLoading(false);
      } else {
        showMessage(MESSAGES.MSG006)
        setLoading(false);
      }
    }
  };



  return (
    <>
      {loading && <Loader zIndex={`${loading ? "10" : "9999"}`} />}
      <Formik
        initialValues={valoresIniciales}
        enableReinitialize
        validationSchema={esquema}
        onSubmit={handleSubmit}
      >
        {({
          handleSubmit, handleChange, handleBlur, values, errors, touched, isValid, resetForm,
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={12} className="text-end mb-3">
                <Tooltip placement="top" text={"Exportar a Excel"}>
                  <span>
                    <IconButton
                      type="excel"
                      onClick={handleDownloadExcel}
                    />
                  </span>
                </Tooltip>
              </Col>
            </Row>
            <TablaEditable
              dataTable={dataTable}
              columns={columns}
              header="Facturas"
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
              denyText="No"
              handleDeny={handleDeny}
              approveText="Sí"
              handleApprove={handleAccept}
            >
              {MESSAGES.MSG002}
            </BasicModal>
          </Form>
        )}
      </Formik>
    </>
  );
};

export default FacturasAsociadas;
