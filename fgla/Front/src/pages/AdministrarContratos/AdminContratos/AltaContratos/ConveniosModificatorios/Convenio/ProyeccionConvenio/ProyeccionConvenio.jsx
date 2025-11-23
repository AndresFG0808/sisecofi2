import React, { useEffect, useMemo, useRef, useState } from "react";
import { TablaEditable } from "../../../../../../../components/table/TablaEditable";
import { EDITAR_CONVENIOS, generateColumns, rearrangeData } from "./utils";
import { Button, Col, Row } from "react-bootstrap";
import IconButton from "../../../../../../../components/buttons/IconButton";
import FileField from "../../../../../../../components/formInputs/FileField";
import {
  useGetCasoNegocioQuery,
  useLazyGetLayoutProyeccionQuery,
  useLazyGetReporteCasoNegocioQuery,
  usePostProcesarProyeccionMutation,
} from "./store";
import { useParams, useSearchParams } from "react-router-dom";
import { DownloadFileBase64 } from "../../../../../../../functions/utils/base64.utils";
import { Loader } from "../../../../../../../components";
import { useErrorMessages } from "../../../../../../../hooks/useErrorMessages";
import SingleBasicModal from "../../../../../../../modals/SingleBasicModal";
import FileFieldValue from "../../../../../../../extraComponents/formInputsArray/FileFieldValue";
import Authorization from "../../../../../../../components/Authorization";
import { useGetAuthorization } from "../../../../../../../hooks/useGetAuthorization";

export function ProyeccionConvenio({ isDetalle }) {
  const fileInputRef = useRef();
  const { idConvenio } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoConvenio = searchParams.get("cm");
  const { getMessageExists } = useErrorMessages(EDITAR_CONVENIOS);
  const [dataTable, setDataTable] = useState([{}]);
  const [proyeccionData, setProyeccionData] = useState();
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasicModal, setShowSingleBasicModal] = useState(false);
  const { isAuthorized } = useGetAuthorization(["CONT_CM_PCM_BTN_PPROC"]);

  const readOnly = isDetalle || !isAuthorized;

  const [descargarReporte, { isLoading: isLoadingReporte }] =
    useLazyGetReporteCasoNegocioQuery();
  const [procesarProyeccion, { isLoading: isLoadingProcesar }] =
    usePostProcesarProyeccionMutation();
  const [descargarLayout, { isLoading: isLoadingDescargar }] =
    useLazyGetLayoutProyeccionQuery();

  const { data: casoNegocio, isFetching } = useGetCasoNegocioQuery(
    idConvenio || idNuevoConvenio
  );

  const columns = useMemo(() => generateColumns(casoNegocio), [casoNegocio]);
  useEffect(() => {
    if (casoNegocio) {
      setDataTable(rearrangeData(casoNegocio?.mapa));
    }
  }, [casoNegocio]);

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const base64String = e.target.result.split(",")[1];
        const proyeccionData = {
          archivo: base64String,
          nombreArchivo: file.name,
        };
        setProyeccionData(proyeccionData);
      };
      reader.onerror = (error) => {};
      reader.readAsDataURL(file);
    }
  };

  const handleProcesarProyeccion = async () => {
    try {
      await procesarProyeccion({
        data: proyeccionData,
        idConvenio: idConvenio || idNuevoConvenio,
      }).unwrap();
      setSingleBasicMessage(EDITAR_CONVENIOS.MSG014);
      setShowSingleBasicModal(true);
      fileInputRef.current = "";
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (mensaje && getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else if (
        mensaje &&
        mensaje.startsWith("Verifique el layout de carga")
      ) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage(EDITAR_CONVENIOS.MSG018);
        setShowSingleBasicModal(true);
      }
      fileInputRef.current = "";
    }
  };
  const handleDownloadLayout = async () => {
    try {
      const res = await descargarLayout(idConvenio || idNuevoConvenio).unwrap();
      DownloadFileBase64(
        res,
        "layout.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (mensaje && getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage(EDITAR_CONVENIOS.MSG018);
        setShowSingleBasicModal(true);
      }
    }
  };
  const handleDownloadReporte = async () => {
    try {
      const res = await descargarReporte(
        idConvenio || idNuevoConvenio
      ).unwrap();
      DownloadFileBase64(
        res,
        "Reporte.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (mensaje && getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage(EDITAR_CONVENIOS.MSG018);
        setShowSingleBasicModal(true);
      }
    }
  };
  return (
    <>
      {isLoadingProcesar ||
      isLoadingDescargar ||
      isFetching ||
      isLoadingReporte ? (
        <Loader />
      ) : null}
      <Row>
        <Col md={4}>
          <Row>
            <Col md={12}>Descargar layout:</Col>
            <Col md={3}>
              <IconButton
                type="excel2"
                onClick={handleDownloadLayout}
                tooltip={"Descargar layout"}
              />
            </Col>
          </Row>
        </Col>
        <Col md={4}>
          <Authorization process={"CONT_CM_PCM_BTN_PPROC"}>
            <FileFieldValue
              value={proyeccionData}
              label={"Archivo proyección*:"}
              className={""}
              accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
              onChange={handleFileChange}
              disabled={readOnly}
              ref={fileInputRef}
            />
          </Authorization>
        </Col>
        <Col md={1}></Col>
        <Col md={2} className="">
          <div>‎ </div>
          <Authorization process={"CONT_CM_PCM_BTN_PPROC"}>
            <Button
              variant="gray"
              className="ms-2 waves-effect waves-light "
              size="sm"
              type="button"
              onClick={handleProcesarProyeccion}
              disabled={readOnly}
            >
              Procesar proyección
            </Button>
          </Authorization>
        </Col>
        <Col md={12}></Col>
      </Row>
      <Row>
        <Col md={12} className="text-end mb-3">
          <IconButton
            type="excel"
            onClick={handleDownloadReporte}
            tooltip={"Descargar a Excel"}
          />
        </Col>
      </Row>
      <TablaEditable columns={columns} dataTable={dataTable} hasPagination />
      <SingleBasicModal
        size={"md"}
        title={"Mensaje"}
        approveText={"Aceptar"}
        show={showSingleBasicModal}
        onHide={() => {
          setShowSingleBasicModal(false);
        }}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
