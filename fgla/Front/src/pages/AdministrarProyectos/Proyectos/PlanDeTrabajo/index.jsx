import React, { useState, useEffect, useRef } from "react";
import { useLocation } from "react-router-dom";
import { Row, Col, Button } from "react-bootstrap";
import { Loader, LabelValue } from "../../../../components";
import { Select } from "../../../../components/formInputs";
import FileFieldValue from "../../../../extraComponents/formInputsArray/FileFieldValue";
import IconButton from "../../../../components/buttons/IconButton";
import showMessage from "../../../../components/Messages";
import { useSelector } from "react-redux";
import { logError } from '../../../../components/utils/logError.js';
import {
  downloadDocument,
  getData,
  postData,
  uploadDocumentPut,
} from "../../../../functions/api";
import PlanTabla from "./PlanTabla";
import PlanGrafica from "./PlanGrafica";
import {
  CARGA_PLAN_TRABAJO,
  PLAN_MODIFICAR,
} from "../../../../constants/messages";
import BasicModal from "../../../../modals/BasicModal";
import {
  downloadExcelBlob,
  errorValidations,
} from "../../../../functions/utils";
import moment from "moment";
import { useToast } from "../../../../hooks/useToast";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import Authorization from "../../../../components/Authorization";
import { useDispatch } from "react-redux";
import { GetDetalleProyecto } from "../../../../store/infoComites/infoComitesActions";
import { useErrorMessages } from "../../../../hooks/useErrorMessages";
import _ from "lodash";
import { useCallback } from "react";
const FORMAT_DATE = "DD/MM/YYYY";

const PlanDeTrabajo = () => {
  const dispatch = useDispatch();
  const [basicModalMessage, setBasicModalMessage] = useState("");
  const [basicModalShow, setBasicModalShow] = useState(false);
  const fileInputRef = useRef();
  const tableRef = useRef();
  const { proyecto, editable } = useSelector((state) => state.proyectos);
  const { errorToast } = useToast();
  const { state } = useLocation();
  const { idProyecto: idProyectoState } = { ...state };
  const [loading, setLoading] = useState(true);
  const [loadingUpdate, setLoadingUpdate] = useState(false);
  const [showChart, setShowChart] = useState(false);
  const [showConfirmReplace, setShowConfirmReplace] = useState(false);
  const [fileExcel, setFileExcel] = useState(null);
  const [errorExcel, setErrorExcel] = useState(null);
  const [nivelEsquema, setNivelEsquema] = useState("");
  const [dataTable, setDataTable] = useState([]);
  const [dataTableOriginal, setDataTableOriginal] = useState([]);
  const [dataTableSaved, setDataTableSaved] = useState([]);
  const [dataChart, setDataChart] = useState([]);
  const [nivelEsquemaOptions, setNivelEsquemaOptions] = useState([]);
  const [ultimaModificacionPlan, setUltimaModificacionPlan] = useState("");
  const [planGuardado, setPlanGuardado] = useState(false);
  const [updatePlan, setUpdatePlan] = useState(false);
  const [plantillasPlanOptions, setPlantillasPlanOptions] = useState([]);
  const [plantillaPlan, setPlantillaPlan] = useState("");
  const [showConfirmCancel, setShowConfirmCancel] = useState(false);
  const [updateTable, setUpdateTable] = useState(false);
  const [showTable, setShowTable] = useState(true);
  const originalDataPlain = useRef([]);
  const { getMessageExists } = useErrorMessages(CARGA_PLAN_TRABAJO);
  const [hasChanges, setHasChanges] = useState(false);

  //#region Confirm Modal

  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [confirmModalMessage, setConfirmModalMesage] = useState("");
  const [confrimModalTitle, setConfirmModalTitle] = useState("Mensaje");
  const _confirmData = {
    approve: () => {},
    deny: () => {},
  };
  const [confirmData, setConfirmData] = useState(_confirmData);

  const handleShowConfirmModal = (
    message,
    approve = _confirmData.approve,
    deny = _confirmData.deny,
    title = "Mensaje"
  ) => {
    setConfirmModalMesage(message);
    setConfirmData({ approve, deny });
    setShowConfirmModal(true);
    setConfirmModalTitle(title ? title : "Mensaje");
  };
  const handleApprove = () => {
    if (confirmData?.approve) {
      confirmData.approve();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };

  const handleDenny = () => {
    if (confirmData?.deny) {
      confirmData.deny();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };

  //#endregion
  useEffect(() => {
    getDataInit();
    return () => {
      cleanTempPlan();
    };
  }, []);

  useEffect(() => {
    const handleBeforeUnload = (event) => {
      cleanTempPlan();
      event.preventDefault();
      event.returnValue = "";
    };

    if (hasChanges) {
      window.addEventListener("beforeunload", handleBeforeUnload);
    } else {
      window.removeEventListener("beforeunload", handleBeforeUnload);
    }
    return () => {
      console.log("return > beforeunload");
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }, [hasChanges]);

  /*     useEffect(() => {
            getDataInit();
            console.log("getPlainData => ", getPlainData(localPlan));
    
            const handleBeforeUnload = (event) => {
                cleanTempPlan();
                event.preventDefault();
                event.returnValue = '';
            };
    
            window.addEventListener('beforeunload', handleBeforeUnload);
    
            return () => {
                console.log("return > beforeunload");
                cleanTempPlan();
                window.removeEventListener('beforeunload', handleBeforeUnload);
            };
        }, []); */

  // const getPlainData = (data) => {
  //     console.log("getPlainData > data => ", data)
  //     const resultado = [];
  //     function recorrer(obj, idParent) {
  //         //console.log("recorrer > obj => ", obj);
  //         //let addParent = obj.map(tarea => ({ ...tarea, idParent }));
  //         let addParent = { ...obj, idParent };
  //         resultado.push(addParent);

  //         if (obj.subRows && obj.subRows.length > 0) {
  //             obj.subRows.forEach(item => recorrer(item, obj.idTarea));
  //         }
  //     }
  //     recorrer(data[0], -1);
  //     return resultado;
  // }

  const dateFormat = (date) => {
    let formatedDateTime =
      date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
    return formatedDateTime;
  };

  const getDataInit = () => {
    getData("/proyectos/plan-trabajo/consultar-plantilla")
      .then((response) => {
        console.log("consultar-plantilla ===> ", response.data);
        setPlantillasPlanOptions(response.data);
      })
      .catch((error) => {
        logError("error => ", error);
      });

    getData(
      "/proyectos/plan-trabajo/tabla-plan-trabajo?idProyecto=" +
        (idProyectoState || proyecto?.proyecto?.idProyecto)
    )
      .then((response) => {
        console.log("getPlanTrabajo ===> ", response.data);
        const { listaTareas, ultimaModificacion } = response.data;
        if (listaTareas instanceof Array) {
          setPlanGuardado(true);
          setDataTable([listaTareas[0]]);
          setDataTableSaved([listaTareas[0]]);
          setDataTableOriginal(JSON.parse(JSON.stringify([listaTareas[0]])));
          setDataChart([listaTareas[0]]);
          let dataPlain = cleanData([listaTareas[0]]);
          getNivelEsquemaMax(dataPlain);
          originalDataPlain.current = dataPlain;
          setShowChart(true);
        }
        setUltimaModificacionPlan(dateFormat(ultimaModificacion));
        setLoading(false);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        if (error.response.data.error === CARGA_PLAN_TRABAJO.MSG003) {
          showMessage(CARGA_PLAN_TRABAJO.MSG003);
        } else {
          showMessage(CARGA_PLAN_TRABAJO.ERROR_CONSULTA);
        }
      });
  };

  const cargarPlan = () => {
    if (dataTable.length > 0) {
      setShowConfirmReplace(true);
    } else {
      cargarPlanAccept();
    }
  };

  const cargarPlanAccept = () => {
    setShowConfirmReplace(false);
    setLoading(true);
    setUpdatePlan(planGuardado);
    const formData = new FormData();
    formData.append("file", fileExcel);

    uploadDocumentPut(
      "/proyectos/plan-trabajo/cargar-guardar?idProyecto=" +
        proyecto?.proyecto?.idProyecto,
      formData
    )
      .then((response) => {
        console.log("response.data => ", response.data);
        setShowChart(false);
        setPlanGuardado(false);
        setDataTable([response.data[0]]);
        setDataTableOriginal(JSON.parse(JSON.stringify([response.data[0]])));
        let dataPlain = cleanData(response.data);
        console.log("cargar-plan > dataPlain =-> ", dataPlain);
        getNivelEsquemaMax(dataPlain);
        originalDataPlain.current = dataPlain;
        setFileExcel(null);
        fileInputRef.current = "";
        setLoading(false);
        setHasChanges(true);

        handleShowConfirmModal(
          "¿Desea calcular el porcentaje de avance del proyecto?",
          calcularCompletados,
          () => {},
          "Alerta"
        );
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        setFileExcel(null);
        fileInputRef.current = "";
        errorValidations(getMessageExists, error);
      });
  };

  const getNivelEsquemaMax = (data) => {
    console.log("getNivelEsquemaMax => ", data);
    let niveles = new Set();

    for (let item of data) {
      niveles.add(item.nivelEsquema);
    }

    let nivelMax = Math.max(...niveles);
    let nivelesArray = [];

    for (let i = 0; i <= nivelMax; i++) {
      nivelesArray.push({ id: i, nivel: i });
    }

    setNivelEsquemaOptions(nivelesArray);
  };

  const cleanData = (data) => {
    const resultado = [];
    function recorrer(obj) {
      resultado.push(obj);
      if (obj.subRows) {
        obj.subRows.forEach(recorrer);
      }
    }
    recorrer(data[0]);
    return resultado;
  };

  const mapTareasRealesGuardar = () => {
    if (_.isEmpty(tareasModificadas)) return [];

    return tareasModificadas.filter(
      (tarea) =>
        tarea?.duracionReal !== undefined ||
        tarea?.fechaInicioReal ||
        tarea?.fechaFinReal
    );
  };

  const mapTareasCompletadoGuardar = () => {
    if (_.isEmpty(tareasModificadas)) return [];
    return tareasModificadas.filter((tarea) => tarea?.completado);
  };

  const calcularReales = () => {
    let tareasRealesGuardar = mapTareasRealesGuardar();
    let tareasCompletadasGuardar = mapTareasCompletadoGuardar();
    setLoading(true);
    console.log("guardarReales =>");
    console.log("guardarModificadas =>", tareasRealesGuardar);

    postData(
      `/proyectos/plan-trabajo/calcular-reales?idProyecto=${proyecto?.proyecto?.idProyecto}`,
      { modificaciones: tareasRealesGuardar }
    )
      .then((response) => {
        console.log("calcularReales => ", response.data);
        // const { listaTareas, ultimaModificacion } = response.data;
        const listaTareas = response.data;
        console.log("listaTareas[0] => ", listaTareas[0]);
        setShowTable(false);
        setTimeout(() => {
          setShowTable(true);
          setPlanGuardado(true);
          setUpdatePlan(false);
          setDataTable([listaTareas[0]]);
          setDataTableSaved([listaTareas[0]]);
          setDataTableOriginal(JSON.parse(JSON.stringify([listaTareas[0]])));
          setDataChart([listaTareas[0]]);
          getNivelEsquemaMax(cleanData([listaTareas[0]]));
          setShowChart(true);
          setLoading(false);
          //   setUltimaModificacionPlan(dateFormat(ultimaModificacion));
          showMessage(CARGA_PLAN_TRABAJO.MSG002);
          setHasChanges(false);
        }, 100);
        calcularCompletados(tareasCompletadasGuardar);
        // setTareasModificadas([]);
        // setTareasOriginales([]);
        dispatch(GetDetalleProyecto(proyecto.proyecto.idProyecto));
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        setBasicModalMessage("Ocurrió un error.");
        setBasicModalShow(true);
      });
  };

  const calcularCompletados = (tareasCompletadosGuardar) => {
    // let tareasCompletadosGuardar = mapTareasCompletadoGuardar();
    // if (!_.isArray(tareasCompletadosGuardar)) {
    setLoading(true);
    console.log("guardarReales =>");

    postData(
      `/proyectos/plan-trabajo/calcular-completado?idProyecto=${proyecto?.proyecto?.idProyecto}`,
      Array.isArray(tareasCompletadosGuardar) ? tareasCompletadosGuardar : []
    )
      .then((response) => {
        console.log("calcularReales => ", response.data);
        // const { listaTareas, ultimaModificacion } = response.data;
        const listaTareas = response.data;
        console.log("listaTareas[0] => ", listaTareas[0]);
        setShowTable(false);
        setTimeout(() => {
          setShowTable(true);
          setPlanGuardado(true);
          setUpdatePlan(false);
          setDataTable([listaTareas[0]]);
          setDataTableSaved([listaTareas[0]]);
          setDataTableOriginal(JSON.parse(JSON.stringify([listaTareas[0]])));
          setDataChart([listaTareas[0]]);
          getNivelEsquemaMax(cleanData([listaTareas[0]]));
          setShowChart(true);
          setLoading(false);
          //   setUltimaModificacionPlan(dateFormat(ultimaModificacion));
          showMessage("La información fue guardada y almacenada con éxito.");
          // showMessage(CARGA_PLAN_TRABAJO.MSG002);
          setHasChanges(false);
        }, 100);
        setTareasModificadas([]);
        setTareasOriginales([]);
        dispatch(GetDetalleProyecto(proyecto.proyecto.idProyecto));
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        setBasicModalMessage("Ocurrió un error.");
        setBasicModalShow(true);
      });
    // }
  };

  // const guardarNuevoPlan = () => {
  //   setLoading(true);
  //   console.log("guardarNuevoPlan => ");
  //   postData(
  //     "/proyectos/plan-trabajo/guardar-plan?idProyecto=" +
  //       proyecto?.proyecto?.idProyecto,
  //     dataTable
  //   )
  //     .then((response) => {
  //       console.log("guardarNuevoPlan => ", response.data);
  //       const { listaTareas, ultimaModificacion } = response.data;
  //       console.log("listaTareas[0] => ", listaTareas[0]);
  //       setShowTable(false);
  //       setTimeout(() => {
  //         setShowTable(true);
  //         setPlanGuardado(true);
  //         setUpdatePlan(false);
  //         setDataTable([listaTareas[0]]);
  //         setDataTableSaved([listaTareas[0]]);
  //         setDataTableOriginal([listaTareas[0]]);
  //         setDataChart([listaTareas[0]]);
  //         getNivelEsquemaMax(cleanData([listaTareas[0]]));
  //         setShowChart(true);
  //         setLoading(false);
  //         setUltimaModificacionPlan(dateFormat(ultimaModificacion));
  //         showMessage(CARGA_PLAN_TRABAJO.MSG002);
  //         setHasChanges(false);
  //       }, 100);
  //       dispatch(GetDetalleProyecto(proyecto.proyecto.idProyecto));
  //     })
  //     .catch((error) => {
  //       console.error("error => ", error);
  //       setLoading(false);
  //     });
  // };

  const [tareasModificadas, setTareasModificadas] = useState([]);
  const [tareasOriginales, setTareasOriginales] = useState([]);
  console.log("Tareas modificadas", tareasModificadas);
  console.log("Tareas originales", tareasOriginales);

  const actualizarPlanReales = (idTarea, campo, data, originalInfo) => {
    setTareasOriginales((prevData) => {
      let _tareas = Array.isArray(prevData) ? [...prevData] : [];

      let tareaIndex = _tareas.findIndex((s) => s.idTarea === idTarea);
      if (tareaIndex === -1) {
        _tareas.push(originalInfo);
      }
      return _tareas;
    });

    setTareasModificadas((prevData) => {
      // Crear una copia segura del arreglo anterior
      let _tareas = Array.isArray(prevData) ? [...prevData] : [];

      // Buscar el índice de la tarea
      let tareaIndex = _tareas.findIndex((s) => s.idTarea === idTarea);

      if (tareaIndex !== -1) {
        // Actualizar el campo de la tarea existente
        _tareas[tareaIndex][campo] = data[campo]; // 'data' es lo que quieres asignar a 'campo'
      } else {
        // Crear una nueva tarea y agregarla al arreglo
        let nuevaTarea = { idTarea };
        nuevaTarea[campo] = data[campo];
        _tareas.push(nuevaTarea);
      }

      return _tareas; // Retornar el nuevo arreglo modificado
    });

    // setLoading(true);
    // setLoadingUpdate(true);
    // let { idProyecto } = proyecto?.proyecto;

    // console.log("actualizarPlanReales > planGuardado => ", planGuardado);

    // if (planGuardado) {
    //     postData('/proyectos/plan-trabajo/modificacion-original?idProyecto=' + idProyecto + "&idTarea=" + idTarea + "&campoModificado=" + campo, data)
    //         .then((response) => {
    //             console.log("actualizarPlanReales => ", response.data);
    //             setDataTableOriginal(response.data);
    //             setDataTable(response.data);
    //             setLoading(false);
    //             setLoadingUpdate(false);
    //             setHasChanges(true);
    //         })
    //         .catch((error) => {
    //             console.error("error => ", error);
    //             setDataTable(dataTableOriginal);
    //             errorToast(CARGA_PLAN_TRABAJO.ERROR);
    //             setLoading(false);
    //             setLoadingUpdate(false);
    //         });
    // } else {
    //     postData('/proyectos/plan-trabajo/modificacion-temporal?idProyecto=' + idProyecto + "&idTarea=" + idTarea + "&campoModificado=" + campo, data)
    //         .then((response) => {
    //             console.log("actualizarPlanReales => ", response.data);
    //             setDataTableOriginal(response.data);
    //             setDataTable(response.data);
    //             setLoading(false);
    //             setLoadingUpdate(false);
    //             setHasChanges(true);
    //         })
    //         .catch((error) => {
    //             console.error("error => ", error);
    //             setDataTable(dataTableOriginal);
    //             errorToast(CARGA_PLAN_TRABAJO.ERROR);
    //             setLoading(false);
    //             setLoadingUpdate(false);
    //         });
    // }
  };

  // const actualizarCompletado = (idTarea, campo, data, porcentajes = false) => {
  //     setLoading(true);
  //     setLoadingUpdate(true);
  //     let { idProyecto } = proyecto?.proyecto;
  //     let urlTarea = idTarea ? "&idTarea=" + idTarea : "";

  //     if (planGuardado) {
  //         postData('/proyectos/plan-trabajo/calcular-porcentaje-original?idProyecto=' + idProyecto + urlTarea, data)
  //             .then((response) => {
  //                 console.log("actualizarPlanReales => ", response.data);
  //                 setDataTableOriginal(response.data);
  //                 setDataTable(response.data);
  //                 setLoading(false);
  //                 setLoadingUpdate(false);
  //                 porcentajes && showMessage(PLAN_MODIFICAR.MSG003);
  //                 setHasChanges(true);
  //             })
  //             .catch((error) => {
  //                 console.error("error => ", error);
  //                 setDataTable(dataTableOriginal);
  //                 errorToast(CARGA_PLAN_TRABAJO.ERROR);
  //                 setLoading(false);
  //                 setLoadingUpdate(false);
  //             });
  //     } else {
  //         postData('/proyectos/plan-trabajo/calcular-porcentaje?idProyecto=' + idProyecto + urlTarea, data)
  //             .then((response) => {
  //                 console.log("actualizarPlanReales => ", response.data);
  //                 setDataTableOriginal(response.data);
  //                 setDataTable(response.data);
  //                 setLoading(false);
  //                 setLoadingUpdate(false);
  //                 porcentajes && showMessage("Los cálculos se han realizado correctamente");
  //                 setHasChanges(true);
  //             })
  //             .catch((error) => {
  //                 console.error("error => ", error);
  //                 setDataTable(dataTableOriginal);
  //                 errorToast(CARGA_PLAN_TRABAJO.ERROR);
  //                 setLoading(false);
  //                 setLoadingUpdate(false);
  //             });
  //     }
  // }

  const descartarCambios = useCallback(
    (idTarea, row, table) => {
      function setOriginalData(row, table, originalData) {
        setDataRow(row, table, originalData);
      }

      function setDataRow(row, table, originalData) {
        let { updateSubRows, updateData } = table.options.meta;

        if (row.parentId) {
          const parentIndex = row.parentId
            ? parseInt(row.parentId.split("")[0])
            : undefined;
          const newRow = row.original;

          for (let key in originalData) {
            if (originalData.hasOwnProperty(key)) {
              // Verifica que la propiedad sea propia y no heredada
              // updateData(row.id,key,originalData[key]);
              newRow[key] = originalData[key];
            }
          }
          const final = updateRowFromSubRow(row, newRow);
          updateSubRows(parentIndex, final);
        } else {
          for (let key in originalData) {
            if (originalData.hasOwnProperty(key)) {
              // Verifica que la propiedad sea propia y no heredada
              updateData(row.index, key, originalData[key]);
            }
          }
        }
      }

      function updateRowFromSubRow(bottomRow, newRow) {
        if (!bottomRow.parentId) return newRow;
        const newBottomRow = bottomRow.getParentRow();
        const updatedRow = bottomRow.getParentRow().original;
        updatedRow.subRows[bottomRow.index] = newRow;
        return updateRowFromSubRow(newBottomRow, updatedRow);
      }

      if (Array.isArray(tareasOriginales)) {
        let tareaOriginalIndex = tareasOriginales.findIndex(
          (s) => s.idTarea === idTarea
        );
        let tareaModificadaIndex = tareasModificadas.findIndex(
          (s) => s.idTarea === idTarea
        );

        if (![tareaOriginalIndex, tareaModificadaIndex].includes(-1)) {
          let tareaOriginal = tareasOriginales[tareaOriginalIndex];
          setOriginalData(row, table, tareaOriginal);

          setTareasModificadas((prev) => {
            if (Array.isArray(prev)) {
              let data = [...prev];
              data.splice(tareaModificadaIndex, 1);
              return data;
            }
            return prev;
          });

          setTareasOriginales((prev) => {
            if (Array.isArray(prev)) {
              let data = [...prev];
              data.splice(tareaOriginalIndex, 1);
              return data;
            }
            return prev;
          });
        }
      }

      // console.log("descartarCambios ========================>>>");
      // let { idProyecto } = proyecto?.proyecto;
      // let urlTarea = idTarea ? "&idTareaOriginal=" + idTarea : "";

      // let actualData = [...tableRef.current.table.options.meta.getDataTable()];
      // let tareaActual = cleanData(actualData).find(tarea => tarea.idTarea === idTarea)
      // let tareaOriginal = originalDataPlain.current.find(tarea => tarea.idTarea === idTarea);

      // console.log("=========> originalDataPlain", originalDataPlain);
      // console.log("=========> tarea original", tareaOriginal);
      // console.log("=========> tarea actual", tareaActual);

      // if (tareaActual && tareaOriginal) {

      //     const { duracionReal: duracionOriginal, fechaInicioReal: inicioOriginal, fechaFinReal: finOriginal } = tareaOriginal;
      //     const { duracionReal: duracionActual, fechaInicioReal: inicioActual, fechaFinReal: finActual } = tareaActual;

      //     console.log("=========> tarea original", tareaOriginal);
      //     console.log("=========> tarea actual", tareaActual);

      //     let duracionOriginalExists =
      //         duracionOriginal !== undefined &&
      //         duracionOriginal !== null &&
      //         duracionOriginal !== "";

      //     let duracionActualExists =
      //         duracionActual !== undefined &&
      //         duracionActual !== null &&
      //         duracionActual !== "";

      //     let duracionExist = duracionOriginalExists && duracionActualExists;
      //     let duracionDiferente = Number(duracionOriginal) !== Number(duracionActual);

      //     let payload = {
      //         duracionReal: Number(duracionOriginal),
      //         fechaInicioReal: inicioOriginal === "" ? null : inicioOriginal,
      //         fechaFinReal: finOriginal === "" ? null : finOriginal
      //     };

      //     console.log("payload => ", payload);

      /* if (duracionActualExists) {
                console.log("descartarCambios > if ===========>");
                actualizarPlanReales(idTarea, "duracionReal", { duracionReal: Number(duracionOriginal) });
            } else if (finOriginal) {
                actualizarPlanReales(idTarea, "fechaFinReal", { fechaFinReal: finOriginal });
            } else if (inicioOriginal) {
                actualizarPlanReales(idTarea, "fechaInicioReal", { fechaInicioReal: inicioOriginal });
            } else {
                console.log("descartarCambios > else ===========>");
                actualizarPlanReales(idTarea, "duracionReal", { duracionReal: 0 });
            } */

      /* if (planGuardado) {

                postData('/proyectos/plan-trabajo/revertir-cambio?idProyecto=' + idProyecto + urlTarea)
                    .then((response) => {
                        console.log("descartarCambios => ", response.data);
                        setDataTableOriginal(response.data);
                        setDataTable(response.data);
                        setLoading(false);
                    })
                    .catch((error) => {
                        console.error("error => ", error);
                        setDataTable(dataTableOriginal);
                        errorToast(CARGA_PLAN_TRABAJO.ERROR);
                        setLoading(false);
                    });
            } else {
                postData('/proyectos/plan-trabajo/revertir-cambio-temporal?idProyecto=' + idProyecto + urlTarea)
                    .then((response) => {
                        console.log("descartarCambios => ", response.data);
                        setDataTableOriginal(response.data);
                        setDataTable(response.data);
                        setLoading(false);
                    })
                    .catch((error) => {
                        console.error("error => ", error);
                        setDataTable(dataTableOriginal);
                        errorToast(CARGA_PLAN_TRABAJO.ERROR);
                        setLoading(false);
                    });
            } */
      // }
    },
    [tareasOriginales]
  );

  // const calcularPorcentajes = () => {
  //     console.log("calcularPorcentajes => ");
  //     actualizarCompletado(null, null, {}, true);
  // }

  const calcularPorcentajesMasivo = () => {
    setLoading(true);
    postData("/proyectos/plan-trabajo/calculo-masivo")
      .then((response) => {
        setLoading(false);
        console.log("calcularPorcentajesMasivo => ", response.data);
        showMessage("La información fue guardada y almacenada con éxito.");
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        errorToast(CARGA_PLAN_TRABAJO.ERROR);
      });
  };

  const handleSelectExcel = (event) => {
    console.log("handleSelectExcel => ");
    let hasFile = event.target.files[0] === undefined ? false : true;

    if (hasFile) {
      let documento = event.target.files[0];
      let extPermitida = ".XLSX";
      let fileExtension = "." + documento.name.split(".").pop().toUpperCase();
      let fileExtensionAccept = fileExtension === extPermitida.toUpperCase();

      if (!fileExtensionAccept) {
        setFileExcel(null);
        setErrorExcel("Archivo no valido");
        showMessage(CARGA_PLAN_TRABAJO.MSG004);
        fileInputRef.current = "";
      } else {
        setFileExcel(documento);
        setErrorExcel(null);
      }
    } else {
      setFileExcel(null);
      setErrorExcel(null);
    }
  };

  const handleDownloadExcel = () => {
    console.log("handleDownloadExcel => ");
    setLoading(true);
    downloadDocument(
      "/proyectos/plan-trabajo/generar-reporte?idProyecto=" +
        proyecto?.proyecto?.idProyecto
    )
      .then((response) => {
        setLoading(false);
        downloadExcelBlob(response.data, "plan-trabajo");
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage(CARGA_PLAN_TRABAJO.MSG007);
      });
  };

  const handleDownloadPlantilla = () => {
    console.log("handleDownloadPlantilla => ");
    setLoading(true);
    postData(
      "/proyectos/plan-trabajo/descargar-plantilla?idPlantillador=" +
        plantillaPlan
    )
      .then((response) => {
        setLoading(false);
        console.log("handleDownloadPlantilla > response => ", response.data);
        const { file, nombrePlantilla } = response.data;
        DownloadFileBase64(
          file,
          nombrePlantilla.split("/").pop(),
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage(CARGA_PLAN_TRABAJO.ERROR_CONSULTA);
      });
  };

  const onchangePlantilla = (e) => {
    console.log("onchangePlantilla > e => ", e.target.value);
    setPlantillaPlan(e.target.value);
  };

  const onchangeNivelEsquema = (e) => {
    console.log("onchangeNivelEsquema => ", e.target.value);
    let { value } = e.target;
    setNivelEsquema(value === "" ? null : parseInt(value));
  };

  const cancelPlanAccept = () => {
    console.log("cancelPlanAccept => ");
    setShowTable(false);
    setTimeout(() => {
      setNivelEsquema("");
      setShowConfirmCancel(false);
      setFileExcel(null);
      setDataTable(JSON.parse(JSON.stringify(dataTableOriginal)));
      setShowChart(updatePlan || planGuardado);
      setUpdatePlan(false);
      setShowTable(true);
      setHasChanges(false);
      setTareasModificadas([]);
      setTareasOriginales([]);
    }, 100);

    //setUpdateTable(!updateTable);

    // postData("/proyectos/plan-trabajo/cancelar-plan?idProyecto=" + proyecto?.proyecto?.idProyecto)
    //     .then((response) => {
    //         console.log("cancelPlanAccept > response => ", response);
    //     })
    //     .catch(error => {
    //         console.error("error => ", error);
    //     })
  };

  const cleanTempPlan = () => {
    postData(
      "/proyectos/plan-trabajo/cancelar-plan?idProyecto=" +
        proyecto?.proyecto?.idProyecto
    )
      .then((response) => {
        console.log("cancelPlanAccept > response => ", response);
      })
      .catch((error) => {
        logError("error => ", error);
      });
  };

  return (
    <>
      {loading && <Loader />}

      <BasicModal
        size="md"
        handleApprove={handleApprove}
        handleDeny={handleDenny}
        denyText={"No"}
        approveText={"Sí"}
        show={showConfirmModal}
        title={confrimModalTitle}
        onHide={handleDenny}
      >
        {confirmModalMessage}
      </BasicModal>

      <Row>
        <Col md={4}>
          <Select
            label="Plan tipo:"
            name="planTipo"
            value={0}
            options={plantillasPlanOptions}
            onChange={onchangePlantilla}
            keyValue="idPlantillador"
            keyTextValue="nombre"
          />
        </Col>
        <Col md={3}>
          <div style={{ paddingTop: "25px" }}>
            <IconButton
              type="excel"
              onClick={handleDownloadPlantilla}
              disabled={!plantillaPlan}
              tooltip={"Descargar Plan tipo"}
            />
          </div>
        </Col>
        <Authorization process={"PROY_PT_CARGA_PLAN"}>
          <Col md={4}>
            <FileFieldValue
              ref={fileInputRef}
              value={fileExcel}
              label="Archivo a cargar*:"
              accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
              onChange={handleSelectExcel}
              disabled={!editable}
              className={errorExcel ? "is-invalid" : ""}
              helperText={errorExcel ? errorExcel : ""}
            />
          </Col>
          <Col md={1} className="text-end">
            <div style={{ paddingTop: "25px" }}>
              <IconButton
                type="calendarUpload"
                onClick={cargarPlan}
                disabled={!fileExcel}
                tooltip={"Cargar plan de trabajo"}
              />
            </div>
          </Col>
        </Authorization>
      </Row>

      <hr />

      {showChart && <PlanGrafica data={dataChart} />}

      <Row>
        <Col md={4}>
          <Select
            label="Nivel de esquema:"
            name={"nivelEsquema"}
            options={nivelEsquemaOptions}
            value={nivelEsquema}
            onChange={(e) => onchangeNivelEsquema(e)}
            keyValue="id"
            keyTextValue="nivel"
            disabled={dataTable.length === 0}
          />
        </Col>
        <Col md={5}></Col>
        <Col md={2}>
          <LabelValue
            label="Última modificación:"
            value={ultimaModificacionPlan}
          />
        </Col>
        <Col md={1} className="text-end">
          <div className="pt-4">
            <IconButton
              type="excel"
              onClick={handleDownloadExcel}
              disabled={dataTable.length === 0}
            />
          </div>
        </Col>
      </Row>

      {showTable && (
        <PlanTabla
          tableRef={tableRef}
          dataTable={dataTable}
          setDataTable={setDataTable}
          planGuardado={planGuardado}
          updatePlan={updatePlan}
          levelExpand={nivelEsquema}
          actualizarPlanReales={actualizarPlanReales}
          // actualizarCompletado={actualizarCompletado}
          descartarCambios={descartarCambios}
          updateTable={updateTable}
          editable={editable}
          loadingUpdate={loadingUpdate}
        />
      )}

      <Row className="align-items-center">
        <Col md={3}>
          <Authorization process={"PROY_PT_BTN_CALC_TP"}>
            <Button
              variant="gray"
              className="btn-sm waves-effect waves-light"
              onClick={calcularPorcentajesMasivo}
              disabled={!editable}
            >
              Calcular todos los
              <br />
              proyectos
            </Button>
          </Authorization>
        </Col>

        <Col md={9} className="text-end">
          <Authorization process={["PROY_PT_CARGA_PLAN", "PROY_PT_MODIF"]}>
            <Button
              variant="red"
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={() => setShowConfirmCancel(true)}
              disabled={dataTable.length === 0 || !editable}
            >
              Cancelar
            </Button>
          </Authorization>

          {/* <Authorization process={"PROY_PT_BTN_CALC_PORCENT"} >
                        <Button
                            variant="green"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={calcularCompletados}
                            disabled={dataTable.length === 0 || !editable}
                        >
                            Calcular %
                        </Button>
                    </Authorization> */}

          <Authorization process={["PROY_PT_CARGA_PLAN", "PROY_PT_MODIF"]}>
            <Button
              variant="green"
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={calcularReales}
              disabled={dataTable.length === 0 || !editable}
            >
              Guardar
            </Button>
          </Authorization>
        </Col>
      </Row>

      <BasicModal
        show={basicModalShow}
        size={"md"}
        title={"Mensaje"}
        approveText={"Sí"}
        denyText={"No"}
        handleApprove={() => setBasicModalShow(false)}
        handleDeny={() => setBasicModalShow(false)}
        onHide={() => setBasicModalShow(false)}
      >
        {basicModalMessage}
      </BasicModal>

      <BasicModal
        show={showConfirmReplace}
        size={"md"}
        title="Mensaje"
        approveText={"Sí"}
        denyText={"No"}
        handleApprove={() => cargarPlanAccept()}
        handleDeny={() => setShowConfirmReplace(false)}
        onHide={() => setShowConfirmReplace(false)}
      >
        {CARGA_PLAN_TRABAJO.MSG001}
      </BasicModal>

      <BasicModal
        show={showConfirmCancel}
        size={"md"}
        title="Mensaje"
        approveText={"Sí"}
        denyText={"No"}
        handleApprove={cancelPlanAccept}
        handleDeny={() => setShowConfirmCancel(false)}
        onHide={() => setShowConfirmCancel(false)}
      >
        {CARGA_PLAN_TRABAJO.MSG005}
      </BasicModal>
    </>
  );
};

export default PlanDeTrabajo;
