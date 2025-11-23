import React, { useContext, useState } from "react";
import { Row, Col } from "react-bootstrap";
import { Tooltip } from "../../../../components/Tooltip";
import IconButton from "../../../../components/buttons/IconButton";
import { Loader } from "../../../../components";
import { AdministrarUsuarioContext } from "../AdministrarUsuarioContext";
import { ADMINISTRAR_USUARIOS_SISTEMA } from "../../../../constants/messages";
import { downloadExcelBlob } from "../../../../functions/utils";
import { downloadDocumentPost } from "../../../../functions/api";
import { logError } from '../../../../components/utils/logError.js';
import _ from "lodash";

export default function AdministrarUsuariosDownload({
  dataTable,
  actionType,
  values,
}) {
  const { handleShowMessage } = useContext(AdministrarUsuarioContext);
  // const [descargarReporte, { isLoading }] =
  //   usePostDescargarUsuariosReporteMutation();

  const [isLoading, setLoading] = useState(false);
  const handleDownloadExcel = () => {
    setLoading(true);

    downloadDocumentPost(
      "/administracion/usuarios/buscar-usuarios-reporte",
      values
    )
      .then((response) => {
        setLoading(false);
        downloadExcelBlob(response.data, "reporte");
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        handleShowMessage(ADMINISTRAR_USUARIOS_SISTEMA.MSG011);
      });
  };

  return (
    <>
      {!_.isEmpty(dataTable) && (
        <Row>
          {isLoading && <Loader />}
          <Col md={12} className="text-end mb-3">
            <Tooltip placement="top" text={"Exportar a Excel"}>
              <span>
                <IconButton type="excel" onClick={handleDownloadExcel} />
              </span>
            </Tooltip>
          </Col>
        </Row>
      )}
    </>
  );
}
