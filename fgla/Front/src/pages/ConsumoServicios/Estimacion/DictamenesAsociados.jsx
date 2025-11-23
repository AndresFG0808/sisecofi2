import React, { useState, useEffect, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Link, useParams, useSearchParams } from "react-router-dom";
import Loader from "../../../components/Loader";
import * as yup from "yup";
import { useToast } from "../../../hooks/useToast";
import { postData, getData, putData, deleteData, downloadDocument, } from '../../../functions/api';
import { InputEditableCell } from './componets/InputEditableCell';
import { TablaEditable } from "../../../components/table/TablaEditable";
import { Formik } from "formik";
import { Form, Row, Col, Button } from "react-bootstrap";
import IconButton from "../../../components/buttons/IconButton";
import BasicModal from "../../../modals/BasicModal";
import { Tooltip } from "../../../components/Tooltip";
import showMessage from '../../../components/Messages';
import { CREAR_ESTIMACION as MESSAGES } from '../../../constants/messages';
import { downloadExcelBlob } from "../../../functions/utils";
import moment from "moment";
import { InputDateCell } from "./componets/InputDateCell";

const VALORES_INICIALES = {
  idTituloServicioProveedor: 0,
  numeroTitulo: "",
  tituloServicio: "",
  estatus: "",
  vencimientoTitulo: "",
  comentarios: "",
  vigencia: "",
  idServicioTitulo: "",
  idProveedor: 0,
};



const DictamenesAsociados = ({ idProveedor, idContrato, chageStatusSeccion, ver, edit, proveedor, setProveedor, idEstimacion }) => {
  const { successToast, infoToast, errorToast } = useToast();
  const ID_KEY_NAME = "idTituloServicioProveedor";
  const navigate = useNavigate();
 const {state} = useLocation();
  const { idDictamen: idDictamenState, idProveedor: proveedorID} = { ...state?.dictamenState };
  const effectiveIdProveedor = idProveedor || proveedorID;
  const [loading, setLoading] = useState(true);
  const [dataTable, setDataTable] = useState([]);
  const [valoresIniciales, setValoresIniciales] = useState(VALORES_INICIALES);
  const [showModal, setShowModal] = useState(false);
  const esquema = yup.object({
  });

  useEffect(() => {
    const fetchData = async () => {
      console.log(idProveedor)
      if (idEstimacion) {
        try {
          const response = await getData(
            `/admin-devengados/estimacion/dictamenes-relacionados?id=${idEstimacion}`
          );
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
        `/admin-devengados/estimacion/dictamenes-relacionados-exportar?id=${idEstimacion}`
      );
      downloadExcelBlob(response.data, "Dictámenes asociados");
    } catch (error) {
      showMessage(MESSAGES.MSG007);
    }
  };

  const CirculoEstatus = ({ porcentaje }) => {
    let color = 'gray';

    if (porcentaje >= 40 && porcentaje < 85) {
      color = 'verde';
    } else if (porcentaje < 40) {
      color = 'Amarillo';
    } else if (porcentaje >= 85 && porcentaje < 95) {
      color = 'Naranja';
    } else if (porcentaje >= 95) {
      color = 'rojo';
    }

    const colorMap = {
      'verde': '#228B22',
      'Amarillo': '#FFD700',
      'Naranja': '#FFA500',
      'rojo': '#FF0000',
    };

    const circleColor = colorMap[color] || 'gray';

    return (
      <div
        style={{
          width: '16px',
          height: '16px',
          borderRadius: '50%',
          backgroundColor: circleColor,
          display: 'inline-block',
          marginRight: '5px',
        }}
      />
    );
  };

  const onChangeData = async (row) => {

  };

  const columns = useMemo(
    () => [
      {
        accessorKey: "id",
        header: "Id del dictamen",
        cell: (props) => {
          const dictamenState = {
            idDictamen: parseInt(String(props.getValue()).split("|").pop()),
            idContrato: idContrato,
            idProveedor: idProveedor,
            idDictamenVisual: props.getValue(),
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
        accessorKey: "periodoControl",
        header: "Periodo de control",
        cell: (props) => {
          const controlDate = String(props.getValue());
          return (
            <InputDateCell
              column={props.column}
              getValue={() => controlDate}
              row={props.row}
              table={props.table}
            />
          );
        },
      },
      {
        accessorKey: "periodoInicio",
        header: "Periodo inicio",
        cell: (props) => {
          const rawDate = String(props.getValue());
          const formattedDate = moment(rawDate).isValid()
            ? moment(rawDate).format("DD/MM/YYYY")
            : rawDate;
          return (
            <InputDateCell
              column={props.column}
              getValue={() => formattedDate}
              row={props.row}
              table={props.table}
            />
          );
        },
      },
      {
        accessorKey: "periodoFin",
        header: "Periodo fin",
        cell: (props) => {
          const rawDate = String(props.getValue());
          const formattedDate = moment(rawDate).isValid()
            ? moment(rawDate).format("DD/MM/YYYY")
            : rawDate;
          return (
            <InputDateCell
              column={props.column}
              getValue={() => formattedDate}
              row={props.row}
              table={props.table}
            />
          );
        },
      },
      {
        accessorKey: "estatus",
        header: "Estatus",
        cell: (props) => (
          <InputEditableCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
          />
        ),
      },
      {
        accessorKey: "montoDictaminado",
        header: "Monto",
        cell: (props) => (
          <InputEditableCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
          />
        ),
      },
      {
        accessorKey: "montoDictaminadoPesos",
        header: "Monto en pesos",
        cell: (props) => (
          <InputEditableCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
          />
        ),
      },
      {
        accessorKey: "tipoCambioReferencial",
        header: (
          <Tooltip
            placement="top"
            text={"En caso de que el estatus sea pagado el tipo de cambio es real a la fecha de pago."}
          >
            <span>Tipo de cambio referencial</span>
          </Tooltip>
        ),
        cell: (props) => <span>{props.getValue()}</span>,
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
     {loading && <Loader zIndex={`${loading ?"10" : "9999"}`} />}
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
              header="Dictámenes"
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

export default DictamenesAsociados;
