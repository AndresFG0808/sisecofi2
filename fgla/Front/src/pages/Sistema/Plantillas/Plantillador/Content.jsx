import React from 'react';
import { Row, Col } from 'react-bootstrap';
import IconButton from '../../../../components/buttons/IconButton';
import { Accordion } from '../../../../components';
import Editor from "./Editor";
import { downloadDocumentPost } from '../../../../functions/api';
import { downloadExcelBlob } from '../../../../functions/utils';
import { logError } from '../../../../components/utils/logError.js';

const Content = ({ contenido, setContenido, plantillaData }) => {

    const handleDownload = () => {

        let data = {
            ...plantillaData,
            header: undefined,
            contenido: undefined,
            footer: undefined
        }

        downloadDocumentPost("/administracion/plantillador/plantillas/layout-ayuda", data)
            .then((response) => {
                downloadExcelBlob(response.data, "layout");
            })
            .catch((error) => {
                logError("error => ", error);
            }
            )
    }
    return (
        <Accordion title="Cuerpo" show={false}>
            <Row>
                <Col md={12} className="text-end mb-2">
                    <IconButton
                        type="excel"
                        onClick={handleDownload}
                        tooltip={"Descargar layout de ayuda"}
                    />
                </Col>
            </Row>
            <Editor currentEdition={contenido} setCurrentEdition={setContenido} />
        </Accordion>
    );
}

export default Content;