
import React, { useState, useEffect } from 'react';
import Modal from 'react-bootstrap/Modal';
import { TablaDinamica } from '../../../components/table';
import { downloadExcelBlob, injectActions } from "../../../functions/utils";
import { Row, Col, Button } from 'react-bootstrap';
import IconButton from '../../../components/buttons/IconButton';
import { downloadDocument } from '../../../functions/api';
import { Tooltip } from "../../../components/Tooltip";
import showMessage from '../../../components/Messages';
import { PROVEEDORES as MESSAGES } from '../../../constants/messages';

const HEADERS = [
    { dataField: "ordenTitulo", text: "Id" },
    { dataField: "numeroTitulo", text: "Número del título", filter: true, sort: true, },
    { dataField: "nombreTituloServicio", text: "Título de servicio" },
    { dataField: "idEstatusTituloServicio", text: "Estatus" },
    { dataField: "vencimientoTitulo", text: "Fecha de vencimiento", filter: true, sort: true, },
    { dataField: "vigencia", text: "Vigencia" },
    { dataField: "comentarios", text: "Comentarios", filter: true, sort: true, },
];

const DetalleTitulos = ({
    show,
    onHide,
    size,
    asTitle,
    title,
    handleApprove,
    approveText,
    idProveedor,
    directorioTitulo,
    estatusTitulos,
    ...props
}) => {
    console.log(estatusTitulos);
    
    const ID_KEY_NAME = "idDetalleTitulos";
    const [dataTable, setDataTable] = useState([]);

    useEffect(() => {
        if (directorioTitulo) {
            processData(directorioTitulo);
        }
    }, [directorioTitulo]);

    const processData = (data) => {
        if (!Array.isArray(data)) {
            console.error("processData: data no es un arreglo", data);
            return;
        }
        let processedDataTable = [];
        data.forEach((item) => {
            let row = {
                ...item,
                idEstatusTituloServicio: semaforoEstatusColor(item),
                vigencia: semaforoVigenciaColor(item)
            };
            processedDataTable.push(row);
        });
        setDataTable(processedDataTable);
    };
    const semaforoEstatusColor = (data) => (
      <CirculoEstatus color={data.semaforoEstatus} idEstatusTituloServicio={data.idEstatusTituloServicio} estatusTitulos={estatusTitulos} />
    );
    const semaforoVigenciaColor = (data) => (
    
        
          <CirculoVigencia color={data.vigencia} />
         
        
     );

    const CirculoEstatus = ({
      color,
      idEstatusTituloServicio,
      estatusTitulos,
    }) => {
      const colorMap = {
        Verde: "#228B22",
        Amarillo: "#FFD700",
        Azul: "#0071B3",
        Rojo: "#FF0000",
        verde: "#228B22",
        amarillo: "#FFD700",
        azul: "#0071B3",
        rojo: "#FF0000",
        gris: "gray",
        Gris: "gray",
      };

      const tooltipTextMap = {
        Verde: "Vigente",
        verde: "Vigente",
        Amarillo: "En opinión de la ANAM",
        amarillo: "En opinión de la ANAM",
        Azul: "En revisión electrónica",
        azul: "En revisión electrónica",
        Rojo: "Rechazo por dictamen técnico",
        rojo: "Rechazo por dictamen técnico",
        gris: "No Vigente",
        Gris: "No Vigente",
      };

      const circleColor = colorMap[color] || "gray";
      let tooltipText = "No Vigente";
      if (Array.isArray(estatusTitulos)) {
        let estatus = estatusTitulos.find(
          (s) => s.primaryKey == idEstatusTituloServicio
        );
        if (estatus) {
          tooltipText = estatus.nombre;
        }
      }
      // const tooltipText = tooltipTextMap[color] || 'No Vigente';

      const divStyle = {
        display: "flex",
        alignItems: "start",
        //   whiteSpace: "nowrap",
        textAlign: "start",
      };

      const circleStyle = {
        backgroundColor: circleColor,
        borderRadius: "50%",
        display: "inline-block",
        marginRight: "5px",
        minHeight: "16px",
        minWidth: "16px",
        maxHeight: "16px",
        maxWidth: "16px",
      };
      return (
        <Tooltip text={tooltipText}>
          <div style={divStyle}>
            <div style={circleStyle} />
            <span>{tooltipText}</span>
          </div>
        </Tooltip>
      );
    };

    const CirculoVigencia = ({ color }) => {
      if (!color) {
        return null;
      }

      const isZeroM = color === "0m";
      const numero = parseFloat(color.replace("m", ""));

      const circleColor = (num, isZeroM) => {
        if (isZeroM) return "#FF0000";
        if (num === 0) return null;
        if (num > 0 && num <= 3) return "#FF0000";
        if (num > 3) return "white";
        return "gray";
      };

      const borderColor = (num, isZeroM) => {
        if (num > 3) return "black";
        return "transparent";
      };

      const tooltipText = (num, isZeroM) => {
        if (isZeroM || (num > 0 && num <= 3)) {
          return "Menor a tres meses respecto a la fecha de hoy";
        }
        return "Mayor a tres meses respecto a la fecha de hoy";
      };

      const circleStyle = {
        width: "16px",
        height: "16px",
        borderRadius: "50%",
        backgroundColor: circleColor(numero, isZeroM),
        display: "inline-block",
        marginRight: "5px",
        border: `1px solid ${borderColor(numero, isZeroM)}`,
        verticalAlign: "middle", // Ajusta este valor según sea necesario
      };

      return circleColor(numero, isZeroM) ? (
        <Tooltip text={tooltipText(numero, isZeroM)}>
              <div style={{ display:"flex", alignItems:"start",whiteSpace: "nowrap" }}>
              <div style={circleStyle} />
            <span>{color}</span>
          </div>
        </Tooltip>
      ) : null;
    };


    const handleCloseModal = () => {
        onHide();
    };

    const handleEdit = (id) => () => {
    };

    const handleShow = (idProveedor) => async () => {
    };

    const onChangeStatusTitulos = (id) => () => {
    };

    const handleDownloadExcel = async () => {
        try {
            const response = await downloadDocument(`/proveedores/reporte-titulos-servicio?idProveedor=${idProveedor}`);
            downloadExcelBlob(response.data, "Títulos de servicio");
        } catch (error) {
            showMessage(MESSAGES.MSG005);
        }
    };

    return (
        <Modal show={show} size={size} onHide={handleCloseModal} centered {...props}>
            <Modal.Header closeButton>
                <Modal.Title as={asTitle}>
                    <div className="text-center">{title}</div>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
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
                <TablaDinamica
                    idKeyName={ID_KEY_NAME}
                    idKeyLink={ID_KEY_NAME}
                    headers={HEADERS}
                    data={injectActions(dataTable)}
                    actionFns={{ handleEdit, handleShow }}
                    onChangeStatus={onChangeStatusTitulos}
                />
            </Modal.Body>
            <Modal.Footer>
                <Row>
                    <Col md={12} className="text-end">
                        <Button
                            variant="red"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={handleApprove}
                        >
                            {approveText}
                        </Button>
                    </Col>
                </Row>
            </Modal.Footer>
        </Modal>
    );
};

DetalleTitulos.defaultProps = {
    size: 'lg',
    asTitle: 'h3',
};

export default DetalleTitulos;
