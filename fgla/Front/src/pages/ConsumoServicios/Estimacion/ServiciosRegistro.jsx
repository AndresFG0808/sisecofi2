import React, { useState, useEffect, useMemo, useContext, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import Loader from "../../../components/Loader";
import * as yup from "yup";
import {
  postData,
  getData,
  putData,
  deleteData,
  downloadDocument,
} from "../../../functions/api";
import { InputEditableCell } from "./componets/InputEditableCell";
import { Semaforo } from "./componets/Semaforo";
import { TablaEditable } from "../../../components/table/TablaEditable";
import { Formik } from "formik";
import { Form, Row, Col, Button } from "react-bootstrap";
import IconButton from "../../../components/buttons/IconButton";
import BasicModal from "../../../modals/BasicModal";
import { Tooltip } from "../../../components/Tooltip";
import showMessage from "../../../components/Messages";
import { CREAR_ESTIMACION as MESSAGES } from "../../../constants/messages";
import BasicModalAccpet from "./componets/BasicModalAccpet";
import { downloadExcelBlob } from "../../../functions/utils";
import { DictamenContext } from "../Dictamen/context";
import Authorization from "../../../components/Authorization";

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

const ServiciosRegistro = ({
  setReloadRegistroServicios,
  setupdateMonto,
  setlastModificacion,
  reload,
  idDesencriptado,
  setActualizaFecha,
  isduplicado,
  setReloadRegistro,
  setShowVolumetriaActiva,
  setDicRelacionados,
  estadoInicial,
  estimacionCancelada,
  originalEstate,
  idProveedor,
  setVolumetríaActiva,
  volumetriaActiva,
  idEstimacion,
}) => {
  const location = useLocation();
  const editable = location?.state?.estimacionState?.editable || null;

  let idContrato = location?.state?.estimacionState?.idContrato || null;
  idContrato = idContrato === undefined ? "" : idContrato;

  const { isEditable } = useContext(DictamenContext);

  const navigate = useNavigate();

  const { state } = location;
  const tableReference = useRef();
  const proveedorID = state?.idProveedor || null;
  const effectiveIdProveedor = idProveedor || proveedorID;
  const [loading, setLoading] = useState(true);
  const [dataTable, setDataTable] = useState([]);
  const [valoresIniciales, setValoresIniciales] = useState(VALORES_INICIALES);
  const [showModal, setShowModal] = useState(false);
  const [convenioSeleccionado, setConvenioSeleccionado] = useState("");

  const [updateVolumetria, setUpdateVolumetria] = useState(false);
  const [volumetriaCarga, setVolumetriaCarga] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);
  const [showModalVolumetria, setShowModalVolumetria] = useState(false);
  const [dataConvenio, setDataConvenio] = useState({
    nombreCorto: "",
    convenios: [],
  });
  const [hasNullServicios, setHasNullServicios] = useState(false);
  const [modifiedEstate, setModifiedEstate] = useState([]);

  const esquema = yup.object({});
  const [forceData, setForceData] = useState(false);

  const formatNumber = (number) => {
    if (number === null || number === undefined) {
      return "";
    }
    if (number === 0) {
      return "0";
    }
    const [integerPart, decimalPart] = number.toString().split(".");

    if (decimalPart) {
      return new Intl.NumberFormat("es-MX", {
        minimumFractionDigits: 0,
        maximumFractionDigits: 6,
      }).format(number);
    } else {
      return new Intl.NumberFormat("es-MX", {
        minimumFractionDigits: 0,
        maximumFractionDigits: 0,
      }).format(number);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      setVolumetriaCarga(false);
      try {
        const response = await getData(
          `/admin-devengados/servicios-estimados?id=${idEstimacion}`
        );

        const dicRelacionados = response.data;
        const transformedData = dicRelacionados.map((item, index) => ({
          idServicioContrato: item.servicioBase.idServicioContrato,
          customID: index + 1,
          idServicioEstimado: item.idServicioEstimado,
          grupo: item.servicioBase.grupoServiciosModel.grupo,
          concepto: item.servicioBase.concepto,
          tipoUnidad: item.servicioBase.catTipoUnidad?.nombre ?? "N/A",
          tipoConsumo:
            item.servicioBase.grupoServiciosModel.catTipoConsumo?.nombre ??
            "N/A",
          precioUnitarioActual: item.precioUnitarioActual,
          cantidadMaxima: item.cantidadMaximaServiciosVigente,
          montoMaximoVigente: item.montoMaximoVigente,
          cantidadServiciosEstimados: formatNumber(
            item.cantidadServiciosEstimados
          ),
          montoEstimado: item.montoEstimado,
          serviciosAcumulados: item.serviciosAcumulados,
          montoEstimadoAcumulado: item.montoEstimadoAcumulado,
          porcentajeServiciosEstimadosAcumulados:
            item.porcentajeServiciosEstimadosAcumulados,
          porcentajeMontoEstimadoAcumulado:
            item.porcentajeMontoEstimadoAcumulado,
        }));

        updateDataTable(transformedData);
        setModifiedEstate(dicRelacionados);
        setLoading(false);
      } catch (error) {
        console.log(error);
        showMessage("Problema para consultar estimaciones");
        setLoading(false);
      }
    };

    fetchData();
  }, [
    isduplicado,
    reload,
    effectiveIdProveedor,
    volumetriaActiva,
    estadoInicial,
    estimacionCancelada,
    forceData,
    idEstimacion,
    updateVolumetria,
  ]);

  const updateDataTable = (updatedData) => {
    setDataTable(updatedData);
    setHasNullServicios(
      updatedData.some((item) => item.cantidadServiciosEstimados === null)
    );
  };

  const handleAcept = async () => {
    setupdateMonto(true);
  };

  const handleVolumetriaEstimada = async () => {
    setLoading(true);
    try {
      const dataToSave = dataTable.map((item, index) => ({
        ...item,
        cantidadServiciosEstimados: parseFloat(
          item.cantidadServiciosEstimados.toString().replace(/,/g, "")
        ),
      }));
      console.log(dataToSave);
      const response = await putData(
        `/admin-devengados/servicios-estimados/volumetria?id=${idEstimacion}`,
        dataToSave
      );
      console.log(response);

      const updatedData = response.data.servicios.map((item, index) => ({
        idServicioEstimado: item.idServicioEstimado,
        customID: index + 1,
        grupo: item.servicioBase.grupoServiciosModel.grupo,
        concepto: item.servicioBase.concepto,
        tipoUnidad: item.servicioBase.catTipoUnidad?.nombre ?? "N/A",
        tipoConsumo:
          item.servicioBase.grupoServiciosModel.catTipoConsumo?.nombre ?? "N/A",
        precioUnitarioActual: item.precioUnitarioActual,
        cantidadMaxima: item.cantidadMaximaServiciosVigente,
        montoMaximoVigente: item.montoMaximoVigente,
        cantidadServiciosEstimados: formatNumber(
          item.cantidadServiciosEstimados
        ),
        montoEstimado: item.montoEstimado,
        serviciosAcumulados: item.serviciosAcumulados,
        montoEstimadoAcumulado: item.montoEstimadoAcumulado,
        porcentajeServiciosEstimadosAcumulados:
          item.porcentajeServiciosEstimadosAcumulados,
        porcentajeMontoEstimadoAcumulado: item.porcentajeMontoEstimadoAcumulado,
      }));

      console.log(updatedData);
      setlastModificacion(response.data.ultimaModificacion);
      updateDataTable(updatedData);
      setVolumetríaActiva(true);

      setLoading(false);
      setShowVolumetriaActiva(true);
      setUpdateVolumetria(true);
      setShowModalVolumetria(false);
      showMessage(MESSAGES.MSG001);
    } catch (error) {
      setLoading(false);
      console.log("Error al enviar los datos:", error);
      setShowVolumetriaActiva(false);
      setShowModalVolumetria(false);
      showMessage(MESSAGES.MSG006);
    }
  };

  const handleOpenVolumetriaModal = async () => {
    const hasInvalidCantidadServicios = dataTable.some(
      (item) =>
        item.cantidadServiciosEstimados === null ||
        item.cantidadServiciosEstimados === "" ||
        item.cantidadServiciosEstimados === " "
    );
    console.log(hasInvalidCantidadServicios);

    if (hasInvalidCantidadServicios) {
      setShowModalVolumetria(true);
    } else {
      setLoading(true);
      try {
        const dataToSave = dataTable.map((item, index) => ({
          ...item,
          idEstimacion: originalEstate?.[index]?.idEstimacion,
          cantidadServiciosEstimados: parseFloat(
            item.cantidadServiciosEstimados.toString().replace(/,/g, "")
          ),
        }));
        const response = await putData(
          `/admin-devengados/servicios-estimados/volumetria?id=${idEstimacion}`,
          dataToSave
        );

        const updatedData = response.data.servicios.map((item, index) => ({
          idServicioEstimado: item.idServicioEstimado,
          customID: index + 1,
          grupo: item.servicioBase.grupoServiciosModel.grupo,
          concepto: item.servicioBase.concepto,
          tipoUnidad: item.servicioBase.catTipoUnidad?.nombre ?? "N/A",
          tipoConsumo:
            item.servicioBase.grupoServiciosModel.catTipoConsumo?.nombre ??
            "N/A",
          precioUnitarioActual: item.precioUnitarioActual,
          cantidadMaxima: item.cantidadMaximaServiciosVigente,
          montoMaximoVigente: item.montoMaximoVigente,
          cantidadServiciosEstimados: formatNumber(
            item.cantidadServiciosEstimados
          ),
          montoEstimado: item.montoEstimado,
          serviciosAcumulados: item.serviciosAcumulados,
          montoEstimadoAcumulado: item.montoEstimadoAcumulado,
          porcentajeServiciosEstimadosAcumulados:
            item.porcentajeServiciosEstimadosAcumulados,
          porcentajeMontoEstimadoAcumulado:
            item.porcentajeMontoEstimadoAcumulado,
        }));
        setVolumetriaCarga(true);
        updateDataTable(updatedData);
        setVolumetríaActiva(true);
        setLoading(false);
        showMessage(MESSAGES.MSG001);
      } catch (error) {
        setLoading(false);
        console.log("Error al enviar los datos:", error);
        showMessage(MESSAGES.MSG006);
      }
    }
  };

  const handleCloseVolumetriaModal = () => {
    setShowModalVolumetria(false);
  };

  const handleGoBack = () => {
    setShowModal(true);
  };

  const handleSubmit = async (data, { resetForm }) => {
    setLoading(true);
    const objetoAEnviar = {
      ...data,
    };
  };

  const handleDownloadExcel = async () => {
    try {
      const response = await downloadDocument(
        `/admin-devengados/exportar-servicios-estimados?id=${idEstimacion}`
      );
      downloadExcelBlob(response.data, "Registro de servicios");
    } catch (error) {
      showMessage(MESSAGES.MSG007);
    }
  };

  const CirculoEstatus = ({ porcentaje }) => {
    let color = "gray";

    if (porcentaje >= 40 && porcentaje < 70) {
      color = "verde";
    } else if (porcentaje < 40) {
      color = "Amarillo";
    } else if (porcentaje >= 70 && porcentaje < 95) {
      color = "Naranja";
    } else if (porcentaje >= 95) {
      color = "rojo";
    }

    const colorMap = {
      verde: "#228B22",
      Amarillo: "#FFD700",
      Naranja: "#FFA500",
      rojo: "#FF0000",
    };

    const circleColor = colorMap[color] || "gray";

    return (
      <div
        style={{
          width: "16px",
          height: "16px",
          borderRadius: "50%",
          backgroundColor: circleColor,
          display: "inline-block",
          marginRight: "5px",
        }}
      />
    );
  };

  const handleSaveData = async () => {
    const dataToSave = dataTable.map((item) => ({
      ...item,
      cantidadServiciosEstimados: parseFloat(
        item.cantidadServiciosEstimados.toString().replace(/,/g, "")
      ),
    }));
    try {
      setLoading(true);
      const response = await putData(
        `/admin-devengados/servicios-estimados-guardar?id=${idEstimacion}`,
        dataToSave
      );
      setlastModificacion(response.data.ultimaModificacion);
      const dicRelacionados = response.data.servicios;

      updateTableWithCalculatedData(dicRelacionados);
      setReloadRegistro(true);
      setActualizaFecha(true);
      showMessage(MESSAGES.MSG001);
      setLoading(false);
      setReloadRegistroServicios(true);
    } catch (error) {
      setLoading(false);
      setReloadRegistroServicios(false);
      console.log("Error al enviar los datos:", error);
      showMessage(MESSAGES.MSG006);
    }
  };

  const updateTableWithCalculatedData = (dicRelacionados) => {
    const updatedDataTable = dicRelacionados.map((item, index) => {
      const calculatedData = dicRelacionados.find(
        (calcItem) => calcItem.idServicioEstimado === item.idServicioEstimado
      );
      return {
        ...item,
        montoEstimado: calculatedData.montoEstimado,
        customID: index + 1,
        cantidadServiciosEstimadosEnt:
          calculatedData.cantidadServiciosEstimadosEnt,
        serviciosAcumulados: calculatedData.serviciosAcumulados,
        montoEstimadoAcumulado: calculatedData.montoEstimadoAcumulado,
        porcentajeServiciosEstimadosAcumulados:
          calculatedData.porcentajeServiciosEstimadosAcumulados,
        porcentajeMontoEstimadoAcumulado:
          calculatedData.porcentajeMontoEstimadoAcumulado,
        montoEstimadoEnt: calculatedData.montoEstimadoEnt,
        idServicioEstimado: calculatedData.idServicioEstimado,
        grupo: calculatedData.servicioBase.grupoServiciosModel.grupo,
        concepto: calculatedData.servicioBase.concepto,
        tipoUnidad: calculatedData.servicioBase.catTipoUnidad?.nombre ?? "N/A",
        tipoConsumo:
          calculatedData.servicioBase.grupoServiciosModel.catTipoConsumo
            ?.nombre ?? "N/A",
        precioUnitarioActual: calculatedData.precioUnitarioActual,
        cantidadMaxima: calculatedData.cantidadMaximaServiciosVigente,
        montoMaximoVigente: calculatedData.montoMaximoVigente,
      };
    });
    setDataTable(updatedDataTable);
  };

  const onChangeData = async () => {
    setLoading(true);
    setIsProcessing(true);
    try {
      let data = tableReference.current.table.options.meta.getDataTable();

      const newEstate = originalEstate.map((item) => {
        let serachData = data.find(
          ({ idServicioEstimado }) =>
            idServicioEstimado === item.idServicioEstimado
        );

        if (!serachData) {
          console.error(
            `Data not found for idServicioEstimado: ${item.idServicioEstimado}`
          );
          return item;
        }
        let cantidadServiciosEstimados = serachData.cantidadServiciosEstimados;
        console.log(cantidadServiciosEstimados);
        if (
          cantidadServiciosEstimados === " " ||
          cantidadServiciosEstimados === "" ||
          cantidadServiciosEstimados === null
        ) {
          console.log("Cantidad de servicios estimados es nulo");
          cantidadServiciosEstimados = " ";
        } else if (cantidadServiciosEstimados === 0) {
          cantidadServiciosEstimados = 0;
        }

        let newData = { ...item, cantidadServiciosEstimados };
        return newData;
      });

      const CalculateEstate = newEstate.map((item) => {
        const precioUnitarioActual = item.precioUnitarioActual;
        const cantidadServiciosEstimados = item.cantidadServiciosEstimados;
        const montoEstimado = precioUnitarioActual * cantidadServiciosEstimados;

        return {
          ...item,
          montoEstimado,
        };
      });

      updateTableWithCalculatedData(CalculateEstate);
      /* setDicRelacionados(CalculateEstate); */

      /* Calcular precio unitario por la cantidad de servicios estimados
      
      solo se tendría que modificar el monto estimado 
      
      */

      /*  console.log(newEstate);
       const response = await postData("/admin-devengados/servicios-estimados-calcular", newEstate);
       console.log(response);
       const calculo = response.data;
       console.log(calculo);
       updateTableWithCalculatedData(calculo);
 
       if (response) {
         try {
           const responseTotal = await postData("/admin-devengados/calcular-estimado-total", calculo);
           console.log(responseTotal);
           setlastModificacion(responseTotal.data.ultimaModificacion);
           const dicRelacionados = responseTotal.data;
           console.log(dicRelacionados);
           setDicRelacionados(dicRelacionados);
 
         } catch (error) {
           console.error("Error in calcular-estimado-total:", error);
         }
       } else {
         console.error("First service call was not successful:", response);
       } */
    } catch (error) {
      console.error("Error in onChangeData:", error);
    } finally {
      setIsProcessing(false);
      setLoading(false);
    }
  };

  const columns = useMemo(
    () => [
      {
        accessorKey: "customID",
        header: "Id",
        filterFn: "includesString",
        cell: (props) => <span>{props.getValue()}</span>,
      },
      {
        accessorKey: "grupo",
        header: "Grupo",
        cell: (props) => <span>{props.getValue()}</span>,
      },
      {
        accessorKey: "concepto",
        header: "Conceptos de servicio",
        cell: (props) => <span>{props.getValue()}</span>,
      },
      {
        accessorKey: "tipoUnidad",
        header: "Tipo de unidad",
        cell: (props) => <span>{props.getValue()}</span>,
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "tipoConsumo",
        header: "Tipo de consumo",
        cell: (props) => <span>{props.getValue()}</span>,
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "precioUnitarioActual",
        header: "Precio unitario",
        cell: (props) => {
          const formattedValue = new Intl.NumberFormat("es-MX", {
            style: "currency",
            currency: "MXN",
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
          }).format(props.getValue());

          return <span>{formattedValue}</span>;
        },
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "cantidadMaxima",
        header: "Cantidad de servicios máximo vigente",
        cell: (props) => {
          const formattedValue = new Intl.NumberFormat("es-MX", {}).format(
            props.getValue()
          );

          return <span>{formattedValue}</span>;
        },
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "montoMaximoVigente",
        header: "Monto máximo vigente",
        cell: (props) => {
          const formattedValue = new Intl.NumberFormat("es-MX", {
            style: "currency",
            currency: "MXN",
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
          }).format(props.getValue());

          return <span>{formattedValue}</span>;
        },
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "cantidadServiciosEstimados",
        header: "Cantidad de servicios estimados",
        cell: (props) => {
          const renderCellContent = () => {
            if (estadoInicial && editable && !volumetriaActiva) {
              return (
                <InputEditableCell
                  column={props.column}
                  getValue={props.getValue}
                  row={props.row}
                  table={props.table}
                  isEditable={true}
                  onValueChange={onChangeData}
                />
              );
            } else {
              return <span>{props.getValue()}</span>;
            }
          };
          return renderCellContent();
        },
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "montoEstimado",
        header: (
          <Tooltip
            placement="top"
            text={
              "Monto estimado= Cantidad de servicios estimados acumulados x Precio unitario."
            }
          >
            <span>Monto estimado</span>
          </Tooltip>
        ),
        cell: (props) => {
          const formattedValue = new Intl.NumberFormat("es-MX", {
            style: "currency",
            currency: "MXN",
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
          }).format(props.getValue());

          return <span>{formattedValue}</span>;
        },
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "serviciosAcumulados",
        header: (
          <Tooltip
            placement="top"
            text={
              "Cantidad de servicios estimados acumulados= cantidad de servicios dictaminados y estimados acumulados a la fecha de corte."
            }
          >
            <span>Cantidad de servicios estimados acumulados</span>
          </Tooltip>
        ),
        cell: (props) => (
          <InputEditableCell
            onChangeData={onChangeData}
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
        accessorKey: "montoEstimadoAcumulado",
        header: (
          <Tooltip
            placement="top"
            text={
              "Monto estimado acumulado= Monto dictaminado y estimado acumulado a la fecha de corte."
            }
          >
            <span>Monto estimado acumulado</span>
          </Tooltip>
        ),
        cell: (props) => {
          const formattedValue = new Intl.NumberFormat("es-MX", {
            style: "currency",
            currency: "MXN",
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
          }).format(props.getValue());

          return <span>{formattedValue}</span>;
        },
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "porcentajeServiciosEstimadosAcumulados",
        header: (
          <Tooltip
            placement="top"
            text={
              "% de cantidad de servicios estimados acumulados= Cantidad de servicios estimados acumulados / Cantidad de servicios máxima vigente del contrato."
            }
          >
            <span>% de servicios estimados acumulados</span>
          </Tooltip>
        ),
        cell: (props) => {
          const value = props.getValue() ?? 0;
          const valueWithPercent = `${parseFloat(value).toFixed(2)}%`;

          return (
            <Semaforo
              column={props.column}
              getValue={() => valueWithPercent}
              row={props.row}
              table={props.table}
              render={() => <CirculoEstatus porcentaje={value} />}
            />
          );
        },
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "porcentajeMontoEstimadoAcumulado",
        header: "% de monto estimado acumulado",
        cell: (props) => {
          const value = props.getValue() ?? 0;
          const valueWithPercent = `${parseFloat(value).toFixed(2)}%`;

          return (
            <Semaforo
              column={props.column}
              getValue={() => valueWithPercent}
              row={props.row}
              table={props.table}
              render={() => <CirculoEstatus porcentaje={value} />}
            />
          );
        },
        enableSorting: false,
        enableColumnFilter: false,
      },
    ],
    [
      reload,
      effectiveIdProveedor,
      editable,
      volumetriaActiva,
      estadoInicial,
      estimacionCancelada,
    ]
  );

  const handleAccept = () => {
    setReloadRegistro(true);
    setDicRelacionados(null);
    handleCloseModal();
  };

  const handleDeny = () => {
    handleCloseModal();
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };
  const ActualizaEstimacion = ({ setDataConvenio }) => {
    const [isOpenModal, setIsOpenModal] = useState(false);
    const [selectedConvenio, setSelectedConvenio] = useState(null);
    const [dataConvenio, setDataConve] = useState({
      nombreCorto: "",
      convenios: [],
    });
    const obteneConvenios = async () => {
      try {
        const response = await getData(
          `/admin-devengados/servicios-estimados/obtener-convenios?id=${idEstimacion}`
        );
        console.log(response);
        if (response.data && response.data.convenios.length > 0) {
          console.log(response);
          setDataConve(response.data);
          setDataConvenio(response.data);
          setReloadRegistro(false);
        } else {
          setDataConve({ nombreCorto: "", convenios: [] });
          setDataConvenio({ nombreCorto: "", convenios: [] });
          setReloadRegistro(false);
        }
      } catch (error) {
        console.log("Error al obtener los convenios:", error);
      }
    };

    useEffect(() => {
      if (isOpenModal && (!dataConvenio.convenios || dataConvenio.convenios.length === 0)) {
        obteneConvenios();
      }
      setSelectedConvenio(dataConvenio.seleccionado);
    }, [isOpenModal]);

    const handleActualizaEstimacion = async () => {
      setLoading(true);
      if (selectedConvenio) {
        try {
          const convenioString = `"${selectedConvenio}"`;
          const response = await putData(
            `/admin-devengados/servicios-estimados/cambiar-precio-unitario?id=${idEstimacion}`,
            convenioString
          );
          const calculo = response.data;
          if (response) {
            try {
              const responseTotal = await postData(
                "/admin-devengados/calcular-estimado-total",
                calculo
              );
              console.log(responseTotal);

              const dicRelacionados = responseTotal.data;
              console.log(dicRelacionados);
              setDicRelacionados(dicRelacionados);
            } catch (error) {
              console.error("Error in calcular-estimado-total:", error);
            }
          } else {
            console.error("First service call was not successful:", response);
          }
          setForceData(true);
          const dicRelacionados = response.data;
          updateTableWithCalculatedData(dicRelacionados);
          setLoading(false);
          showMessage(MESSAGES.MSG001);
        } catch (error) {
          setLoading(false);
          console.log("Error al enviar los datos:", error);
          showMessage(MESSAGES.MSG006);
        }
      }
      setIsOpenModal(false);
      setLoading(false);
    };

    const cierraModal = async () => {
      setIsOpenModal(false);
    };

    const handleCheckboxChange = (convenio) => {
      console.log(convenio);
      setSelectedConvenio(selectedConvenio === convenio ? null : convenio);
    };

    return (
      <>
        <Tooltip placement="top" text={"Actualizar precio unitario"}>
          <span>
            <IconButton
              type="rotate"
              onClick={() => setIsOpenModal(true)}
              disabled={
                !estadoInicial ||
                !editable ||
                volumetriaActiva ||
                estimacionCancelada
              }
            />
          </span>
        </Tooltip>
        {isOpenModal && (
          <BasicModalAccpet
            show={isOpenModal}
            size={"md"}
            onHide={cierraModal}
            title="Seleccione un convenio modificatorio"
            approveText={"Aceptar"}
            handleApprove={handleActualizaEstimacion}
          >
            <div>
              {dataConvenio &&
                dataConvenio.convenios &&
                dataConvenio.convenios.length > 0 ? (
                <>
                  <span>
                    Nombre corto del contrato: {dataConvenio.nombreCorto}
                  </span>
                  {dataConvenio.convenios.map((convenio, index) => (
                    <div
                      key={index}
                      style={{ display: "flex" }}
                      className={`flex flex-wrap p-2 align-items-center gap-3 ${selectedConvenio === convenio
                          ? "item-selected"
                          : "item-assign"
                        }`}
                    >
                      <Form.Check
                        type={"checkbox"}
                        id={`convenio-${index}`}
                        style={{ cursor: "pointer" }}
                        onChange={() => handleCheckboxChange(convenio)}
                        checked={selectedConvenio === convenio}
                      />
                      <div className="flex-1 flex flex-column gap-2">
                        <span>{convenio}</span>
                      </div>
                    </div>
                  ))}
                </>
              ) : (
                <p>No hay convenios disponibles.</p>
              )}
            </div>
          </BasicModalAccpet>
        )}
      </>
    );
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
          handleSubmit,
          handleChange,
          handleBlur,
          values,
          errors,
          touched,
          isValid,
          resetForm,
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={12} className="text-end mb-3">
                <ActualizaEstimacion dataConvenio={dataConvenio} />
                <Tooltip placement="top" text={"Exportar a Excel"}>
                  <span>
                    <IconButton type="excel" onClick={handleDownloadExcel} />
                  </span>
                </Tooltip>
              </Col>
            </Row>
            <TablaEditable
              ref={tableReference}
              dataTable={dataTable}
              columns={columns}
              header="Servicios estimados"
              hasPagination
              isFiltered
              onDelete={setDataTable}
              onUpdate={setDataTable}
              onGetRowData={onChangeData}
            />
            <Row>
              <Col md={12} className="text-end">
                {/*  {!volumetriaActiva ? ( */}
                <div>
                  <Authorization process={"CON_SERV_ESTIM_STA_ESTIM"}>
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={handleOpenVolumetriaModal}
                      disabled={
                        !estadoInicial ||
                        !editable ||
                        volumetriaActiva ||
                        estimacionCancelada
                      }
                    >
                      Volumetría estimada
                    </Button>
                  </Authorization>
                  <Authorization process={"CON_SERV_ADMIN_ESTIM"}>
                    <Button
                      disabled={
                        !estadoInicial ||
                        !editable ||
                        volumetriaActiva ||
                        estimacionCancelada
                      }
                      variant="red"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={handleGoBack}
                    >
                      Cancelar
                    </Button>

                    <Button
                      disabled={
                        !estadoInicial ||
                        !editable ||
                        volumetriaActiva ||
                        estimacionCancelada ||
                        isProcessing
                      }
                      variant="green"
                      className="btn-sm ms-2 waves-effect waves-light"
                      type="button"
                      onClick={() => {
                        if (!isValid) {
                          showMessage(MESSAGES.MSG003);
                        } else {
                          handleSaveData();
                        }
                      }}
                    >
                      Guardar
                    </Button>
                  </Authorization>
                </div>
                {/*   ) : (
                  <p></p>
                )} */}
              </Col>
            </Row>
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
            <BasicModal
              show={showModalVolumetria}
              size={"md"}
              onHide={handleCloseVolumetriaModal}
              title="Mensaje"
              denyText="No"
              handleDeny={handleCloseVolumetriaModal}
              approveText="Sí"
              handleApprove={handleVolumetriaEstimada}
            >
              {MESSAGES.MSG009}
            </BasicModal>
          </Form>
        )}
      </Formik>
    </>
  );
};

export default ServiciosRegistro;
