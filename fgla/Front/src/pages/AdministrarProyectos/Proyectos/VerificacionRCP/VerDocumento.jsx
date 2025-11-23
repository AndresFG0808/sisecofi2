import React, { useState, useEffect } from "react";
import { Button, Modal, Row, Col, Form } from "react-bootstrap";
import Loader from "../../../../components/Loader";
import Select from "../../../../components/formInputs/Select";
import { getData, downloadDocument } from "../../../../functions/api";
import { useLocation } from "react-router-dom";
import { downloadExcelBlob } from "../../../../functions/utils";

const VerDocumento = ({
  idProyecto,
  title,
  show,
  onHide,
  setDocumentoGenerado,
  setErrorCarga,
  onGuardar,
  oficioSolicitudPago,
  fechaSolicitud,
  onGetService,
  setLoaderPrincipal,
}) => {
  const location = useLocation();
  let idDictamenState = location?.state?.dictamenState?.idDictamen;
  idDictamenState = idDictamenState === undefined ? "" : idDictamenState;
  let idContrato = location?.state?.dictamenState?.idContrato;
  idContrato = idContrato === undefined ? "" : idContrato;
  const [selectedPlantilla, setSelectedPlantilla] = useState("");
  const [selectedFormato, setSelectedFormato] = useState("exportarPDF");
  const [pdfUrl, setPdfUrl] = useState("");
  const [catalogoTipoPago, setCatalogoTipoPago] = useState([]);
  const [loading, setLoading] = useState(true);
  const [fileBlob, setFileBlob] = useState(null);

  useEffect(() => {
    const fetchCatalogo = async () => {
      try {
        const response = await getData(`/proyectos/plantilla-rcp`);
        const { data } = response;
        const mappedData = data.map((item) => ({
          nombre: item.nombre,
          idPlantillador: item.idPlantillador,
        }));
        setCatalogoTipoPago(mappedData);
      } catch (error) {
        console.error("Error fetching dictamen data:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchCatalogo();
  }, [oficioSolicitudPago, fechaSolicitud, idDictamenState]);

  const handleRadioChange = (e) => setSelectedFormato(e.target.id);

  const handleSelectChange = (e) => {
    setSelectedPlantilla(e.target.value);
    setFileBlob(null);
  }

  const handleGuarda = () => {
    if (selectedFormato === "exportarPDF" && pdfUrl) {
      downloadPDF(pdfUrl);
    } else {
      setLoaderPrincipal(true);
      downloadRCP();
    }
    onHide(true);
  };

  const handleCancelar = async () => {
    setDocumentoGenerado(false);
    setErrorCarga(true);
    onHide(true);
  };

  const downloadRCP = async () => {
    setLoading(true);
    try {
      if (!selectedPlantilla || isNaN(selectedPlantilla)) {
        console.error("selectedPlantilla no es un número válido");
        return;
      }
      const tipoDocumento = selectedFormato === "exportarPDF" ? "PDF" : "EXCEL";
      downloadDocument(
        `/proyectos/cierre/${idProyecto}/${selectedPlantilla}/false/descarga?type_file=${tipoDocumento === "PDF" ? "pdf" : "excel"
        }`
      ).then(async (responseData) => {
        const mimeType =
          tipoDocumento === "PDF"
            ? "application/pdf"
            : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (tipoDocumento === "EXCEL") {
          setFileBlob(responseData.data);
          downloadExcelBlob(responseData.data, "documento");
        } else {
          const blobUrl1 = URL.createObjectURL(
            new Blob([responseData.data], { type: mimeType })
          );
          downloadPDF(blobUrl1);
        }
        setLoading(false);
        setLoaderPrincipal(false);
      });
    } catch (error) {
      setLoading(false);
      setLoaderPrincipal(false);
      console.error("Error fetching dictamen data:", error);
    }
  };

  const handlePrevisualiza = async () => {
    setLoading(true);
    try {
      if (!selectedPlantilla || isNaN(selectedPlantilla)) {
        console.error("selectedPlantilla no es un número válido");
        return;
      }
      const tipoDocumento = selectedFormato === "exportarPDF" ? "PDF" : "EXCEL";
      if (tipoDocumento === "PDF") {
        downloadDocument(
          `/proyectos/cierre/${idProyecto}/${selectedPlantilla}/false/descarga?type_file=pdf`
        ).then(async (responseData) => {
          const blobUrl1 = URL.createObjectURL(
            new Blob([responseData.data], { type: "application/pdf" })
          );
          setPdfUrl(blobUrl1);
          setLoading(false);
        }).catch(()=>{
          setLoading(false);
        });
      } else if (tipoDocumento === "EXCEL") {
        setPdfUrl('');
        downloadRCP();
      }
    } catch (error) {
      setLoading(false);
      console.error("Error fetching dictamen data:", error);
    }
  };

  const downloadPDF = (blobUrl) => {
    const link = document.createElement("a");
    link.href = blobUrl;
    link.download = "documento.pdf";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    setDocumentoGenerado(true);
    setErrorCarga(false);
  }

  return (
    <Modal
      show={show}
      dialogClassName="modalMax modal-document"
      onHide={onHide}
      size="lg"
      centered
    >
      {loading && <Loader />}
      <Modal.Header closeButton className="modal-title">
        <Modal.Title className="col-11 text-center fw-bold">
          {title}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ height: "80vh", overflowY: "auto" }}>
        <Row style={{ height: "100%" }}>
          <Col md={3} style={{ height: "100%" }}>
            <Select
              label="Tipo de plantilla:*"
              name="tipoPlantilla"
              value={selectedPlantilla}
              onChange={handleSelectChange}
              options={catalogoTipoPago}
              keyValue="idPlantillador"
              keyTextValue="nombre"
              disabled={catalogoTipoPago.length === 0}
            />
            <div className="mt-4">
              <h6>Formato para exportar</h6>
              <Form.Check
                type="radio"
                id="exportarPDF"
                name="exportarFormato"
                label="PDF"
                onChange={handleRadioChange}
                checked={selectedFormato === "exportarPDF"}
              />
              <Form.Check
                type="radio"
                id="exportarWord"
                name="exportarFormato"
                label="Excel"
                className="mt-2"
                onChange={handleRadioChange}
                checked={selectedFormato === "exportarWord"}
              />
            </div>
            <Row>
              <Col md={12} className="text-end mt-2">
                <Button
                  variant="gray"
                  className="btn-sm ms-2 waves-effect waves-light"
                  type="submit"
                  disabled={
                    selectedPlantilla === null || selectedPlantilla === ""
                      ? true
                      : false
                  }
                  onClick={handlePrevisualiza}
                >
                  Previsualizar
                </Button>
              </Col>
            </Row>

            <br />
          </Col>
          <Col md={9} className="d-flex flex-column" style={{ height: "100%" }}>
            {pdfUrl ? (
              <iframe
                src={pdfUrl}
                style={{
                  flex: 1,
                  width: "100%",
                  height: "100%",
                  border: "none",
                }}
                frameBorder="0"
              ></iframe>
            ) : (
              <div>No hay documento para mostrar</div>
            )}
          </Col>
        </Row>
      </Modal.Body>
      <Modal.Footer>
        <Row>
          <Col md={12} className="text-end mt-3">
            <Button
              variant="red"
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={handleCancelar}
            >
              Cancelar
            </Button>
            <Button
              variant="green"
              className="btn-sm ms-2 waves-effect waves-light"
              disabled={!selectedPlantilla || !selectedFormato}
              onClick={handleGuarda}
            >
              Aceptar
            </Button>
          </Col>
        </Row>
      </Modal.Footer>
    </Modal>
  );
};

VerDocumento.defaultProps = {
  title: "Informe",
  show: false,
};

export default VerDocumento;
