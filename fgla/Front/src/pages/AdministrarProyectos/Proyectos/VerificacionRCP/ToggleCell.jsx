import React, { useEffect, useRef, useState } from "react";
import IconButton from "../../../../components/buttons/IconButton";
import { Tooltip } from "../../../../components/Tooltip";
import CancelModal from "./CancelModal";
import BasicModal from "../../../../modals/BasicModal";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import { putDataForm } from "../../../../functions/api";
// import { MESSAGES } from "./constants";
import { VerDocumento } from "../../../../components";
import showMessage from "../../../../components/Messages";
import Authorization from "../../../../components/Authorization";
import { Loader } from "../../../../components";

export function ToggleCell({
  row,
  table,
  onRemoveProvider,
  onActiveEditIcon,
  onRevertRow,
  editable,
  onRemovedRow,
  onPostService,
  message,
  isDisabled,
  errorDownloadFileText,
  idCierre,
}) {
  const [isOpenModal, setIsOpenModal] = useState(false);

  const { removeRow } = table?.options?.meta;
  const {
    isEditable,
    isNewRegister,
    carpeta,
    nombre,
    descripcion,
    fase,
    extension,
    ruta,
    estatus,
    estatusGestion,
    estatusDocumento,
  } = row.original;
  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);
  const { updateData } = table.options.meta;
  const { index } = row;
  const [showDocumentoPDF, setShowDocumentoPDF] = useState(false);
  const [pdfDocumentData, setPdfDocumentData] = useState("");
  const [loading, setLoading] = useState(false);

  const mimeTypes = {
    ".docx":
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
    ".doc": "application/msword",
    ".xlsx":
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    ".xls": "application/vnd.ms-excel",
    ".pptx":
      "application/vnd.openxmlformats-officedocument.presentationml.presentation",
    ".ppt": "application/vnd.ms-powerpoint",
    ".zip": "application/zip",
    ".rar": "application/vnd.rar",
    ".pdf": "application/pdf",
    ".jpg": "image/jpeg",
    ".jpeg": "image/jpeg",
    ".png": "image/png",
    ".gif": "image/gif",
    ".bmp": "image/bmp",
    ".tiff": "image/tiff",
    ".tif": "image/tiff",
    ".webp": "image/webp",
    ".svg": "image/svg+xml",
    ".txt": "text/plain",
    ".html": "text/html",
    ".htm": "text/html",
    ".css": "text/css",
    ".js": "application/javascript",
    ".json": "application/json",
    ".xml": "application/xml",
    ".mp3": "audio/mpeg",
    ".wav": "audio/wav",
    ".mp4": "video/mp4",
    ".avi": "video/x-msvideo",
  };

  const base64ToPdfBlobUrl = (base64) => {
    const byteCharacters = window.atob(base64);
    const byteArrays = [];

    const sliceSize = 512;
    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      const slice = byteCharacters.slice(offset, offset + sliceSize);
      const byteNumbers = new Array(slice.length);

      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    const blob = new Blob(byteArrays, { type: "application/pdf" });
    const blobUrl = URL.createObjectURL(blob);
    return blobUrl;
  };

  const getMimeType = (extension) => {
    return mimeTypes[extension.toLowerCase()] || "";
  };
  return (
    <>
      {loading && <Loader />}

      {isEditable === false ? (
        <>
          <Tooltip placement="top" text={"Editar"}>
            <span>
              <IconButton
                iconSize="lg"
                type="edit"
                onClick={() => {
                  updateData(index, "isEditable", true);
                  onActiveEditIcon(true);
                }}
                disabled={
                  estatus === "2" && estatusGestion === true
                    ? true
                    : isDisabled
                    ? isDisabled
                    : carpeta === null
                    ? true
                    : false
                }
              />
            </span>
          </Tooltip>
          <Authorization process={"PROY_VRCP_DOWN_IND"}>
            <Tooltip placement="top" text={"Descargar"}>
              <span>
                <IconButton
                  disabled={extension === null ? true : false}
                  iconSize="lg"
                  type="download"
                  onClick={async () => {
                    try {
                      setLoading(true);
                      const formData = new FormData();
                      formData.append("path", ruta);
                      formData.append("nombreCorto", nombre);
                      formData.append("documentoSeleccionado", descripcion);
                      formData.append("fase", fase);
                      const response = await putDataForm(
                        "/proyectos/descargar-archivo-cierre",
                        formData
                      );
                      if (response.data !== null && response.data !== "") {
                        DownloadFileBase64(
                          response.data,
                          `${nombre}.${extension}`,
                          getMimeType(`.${extension}`)
                        );
                      }
                    } catch (err) {
                      if (err?.response?.status === 404) {
                        showMessage(errorDownloadFileText);
                      } else {
                        let errorMessage =
                          err?.response?.data !== "" &&
                          err?.response?.data?.mensaje[0];
                        let errorIdDuplicado =
                          errorMessage === errorDownloadFileText;
                        if (errorIdDuplicado && err?.response?.status !== 403) {
                          showMessage(errorMessage);
                        } else if (err?.response?.status !== 403) {
                          showMessage(errorDownloadFileText);
                        }
                      }
                    }
                    setLoading(false);
                  }}
                />
              </span>
            </Tooltip>
          </Authorization>
          <Tooltip placement="top" text={"Ver PDF"}>
            <span>
              <IconButton
                disabled={extension === "pdf" ? false : true}
                iconSize="lg"
                type="show"
                onClick={async () => {
                  try {
                    setLoading(true);
                    const formData = new FormData();
                    formData.append("path", ruta);
                    formData.append("nombreCorto", nombre);
                    formData.append("documentoSeleccionado", descripcion);
                    formData.append("fase", fase);
                    const response = await putDataForm(
                      "/proyectos/descargar-archivo-cierre",
                      formData
                    );
                    if (response.data !== null && response.data !== "") {
                      const pdfDocument = base64ToPdfBlobUrl(response.data);
                      setPdfDocumentData(pdfDocument);
                      setShowDocumentoPDF(true);
                      onPostService("/proyectos/pista-ver-pdf", {
                        nombreCortoProyecto: nombre,
                        entregable: estatusDocumento,
                        estatus: true,
                      });
                    }
                  } catch (err) {
                    if (err?.response?.status === 404) {
                      showMessage(errorDownloadFileText);
                    } else {
                      let errorMessage =
                        err?.response?.data !== "" &&
                        err?.response?.data?.mensaje[0];
                      let errorIdDuplicado =
                        errorMessage === errorDownloadFileText;
                      if (errorIdDuplicado && err?.response?.status !== 403) {
                        showMessage(errorMessage);
                      } else if (err?.response?.status !== 403) {
                        showMessage(errorDownloadFileText);
                      }
                    }
                    onPostService("/proyectos/pista-ver-pdf", {
                      nombreCortoProyecto: nombre,
                      entregable: estatusDocumento,
                      estatus: false,
                    });
                  }
                  setLoading(false);
                }}
              />
            </span>
          </Tooltip>

          <VerDocumento
            show={showDocumentoPDF}
            onHide={() => setShowDocumentoPDF(false)}
            title={"Ver PDF"}
            urlPdfBlob={pdfDocumentData}
          />
          <BasicModal
            handleApprove={() => {
              setIsOpenModal(false);
              removeRow(row.index);
              onRemovedRow(row);
              onRemoveProvider();
            }}
            handleDeny={() => setIsOpenModal(false)}
            denyText={"No"}
            approveText={"Sí"}
            size="md"
            show={isOpenModal}
            title={"Mensaje"}
            onHide={() => setIsOpenModal(false)}
          >
            {message}
          </BasicModal>
        </>
      ) : (
        <>
          <Tooltip placement="top" text={"Descartar"}>
            <span>
              <IconButton
                iconSize="1x"
                type="undo"
                onClick={() => {
                  setIsOpenCancelModal(true);
                  onActiveEditIcon(false);
                }}
                disabled={false}
              />
            </span>
          </Tooltip>
          {isOpenCancelModal && (
            <CancelModal
              handleApprove={async () => {
                setIsOpenCancelModal(false);
                if (isNewRegister) {
                  removeRow(row.index);
                } else {
                  onRevertRow(row.original);
                  updateData(index, "isEditable", false);
                }
              }}
              handleDeny={() => setIsOpenCancelModal(false)}
              isOpenCancelModal={isOpenCancelModal}
              title={message}
            />
          )}
        </>
      )}
    </>
  );
}
