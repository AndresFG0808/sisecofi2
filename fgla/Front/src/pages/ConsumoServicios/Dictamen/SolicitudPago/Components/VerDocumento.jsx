import React, { useState, useEffect } from "react";
import { Button, Modal, Row, Col, Form } from "react-bootstrap";
import Loader from "../../../../../components/Loader";
import Select from "../../../../../components/formInputs/Select";
import { getData, postData } from "../../../../../functions/api";
import { useLocation } from "react-router-dom";
import showMessage from "../../../../../components/Messages";
import { NOTA_PAGO } from "../../../../../constants/messages";
import { useToast } from "../../../../../hooks/useToast";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import { errorValidations } from "../../../../../functions/utils";

const VerDocumento = ({ title, show, onHide, setDocumentoGenerado, setErrorCarga, onGuardar, oficioSolicitudPago, fechaSolicitud, }) => {
  const {state} = useLocation();
  const { idDictamen: idDictamenState, idContrato} = { ...state?.dictamenState };
  const [selectedPlantilla, setSelectedPlantilla] = useState('');
  const [selectedFormato, setSelectedFormato] = useState('exportarPDF');
  const [pdfUrl, setPdfUrl] = useState('');
  const [catalogoTipoPago, setCatalogoTipoPago] = useState([]);
  const [loading, setLoading] = useState(true);
  const [documentoError, setDocumentoError] = useState(false);
  const [documento, setDocumento] = useState(false);
  const { errorToast } = useToast();
  const { getMessageExists } = useErrorMessages(NOTA_PAGO);

  useEffect(() => {
    const fetchCatalogo = async () => {
      try {
        const response = await getData(`/admin-devengados/solicitud-pago/plantillas`);
        const mappedData = response.data.map(item => ({
          nombre: item.nombre,
          idTipoPago: item.idPlantillador,
        }));
        setCatalogoTipoPago(mappedData);
      } catch (error) {
        console.error("Error fetching dictamen data:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchCatalogo();
  }, [fechaSolicitud, documentoError, idDictamenState]);

  const handleRadioChange = (e) => setSelectedFormato(e.target.id);

  const handleSelectChange = (e) => setSelectedPlantilla(e.target.value);

  const handleGuarda = () => {
    const idPlantilla = parseInt(selectedPlantilla, 10);
    const nombreCheck = selectedFormato === 'exportarPDF' ? 'PDF' : 'Word';
    const idCheck = selectedFormato === 'exportarPDF' ? 1 : 2;

    const dataToSend = { idPlantilla, idCheck, nombreCheck };

    if (pdfUrl) {
      const link = document.createElement('a');
      link.href = pdfUrl;
      link.download = selectedFormato === 'exportarPDF' ? 'documento.pdf' : 'documento.docx';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      setDocumentoGenerado(true)
      setErrorCarga(false)
    }

    onHide(true);
  };

  const handleCancelar = async () => {
    setDocumentoGenerado(false)
    setErrorCarga(true)
    onHide(true);
  }

  const handlePrevisualiza = async () => {
    setLoading(true);
    try {
      if (!selectedPlantilla || isNaN(selectedPlantilla)) {
        setLoading(false);
        errorToast(NOTA_PAGO.MSG005);
        console.error("selectedPlantilla no es un número válido");
        return;
      }
      const tipoDocumento = selectedFormato === 'exportarPDF' ? 'PDF' : 'DOCX';

      const requestBody = {
        tipoDocumento,
        dictamenId: {
          idDictamen: idDictamenState,
        },
        idContrato: idContrato,
        idPlantillador: parseInt(selectedPlantilla, 10),
        oficioSolicitudPago: oficioSolicitudPago,
        fechaSolicitud: new Date(fechaSolicitud).toISOString(),
      };

      const response = await postData(`/admin-devengados/solicitud-pago/plantilla-base`, requestBody);
      setLoading(false);
      setDocumento(true);
      const base64Data = response.data.documentoBase64;
      const mimeType = tipoDocumento === 'PDF' ? 'application/pdf' : 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: mimeType });
      const blobUrl = URL.createObjectURL(blob);
      setPdfUrl(blobUrl);

      setDocumentoError(false);
    } catch (error) {
      setLoading(false);
      setDocumentoError(true);
      errorValidations(getMessageExists, error);
      console.error("Error fetching dictamen data:", error);
    }
  };

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
        <Modal.Title className="col-11 text-center fw-bold">{title}</Modal.Title>
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
              keyValue="idTipoPago"
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
                checked={selectedFormato === 'exportarPDF'}
              />
              <Form.Check
                type="radio"
                id="exportarWord"
                name="exportarFormato"
                label="Word"
                className="mt-2"
                onChange={handleRadioChange}
                checked={selectedFormato === 'exportarWord'}
              />
            </div>
            <br />
            <Button
              variant="gray"
              className="btn-sm ms-2 waves-effect waves-light"
              type="submit"
              onClick={handlePrevisualiza}
            >
              Previsualizar
            </Button>

          </Col>
          <Col md={9} className="d-flex flex-column" style={{ height: "100%" }}>
            {pdfUrl ? (
              <iframe
                src={pdfUrl}
                style={{
                  flex: 1,
                  width: "100%",
                  height: "100%",
                  border: "none"
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
        <Row className="w-100">
          <Col md={12} className="text-end">
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
              disabled={!selectedPlantilla || !selectedFormato || !documento || documentoError}
              onClick={handleGuarda}
            >
              Guardar
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
