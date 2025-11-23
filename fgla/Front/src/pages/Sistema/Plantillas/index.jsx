import React, { useState, useEffect } from 'react';
import { Container, Row, Button, Col } from 'react-bootstrap';
import { MainTitle, Accordion, Loader, ShowMessage } from '../../../components';
import { Select } from '../../../components/formInputs';
import { ADMIN_FORMATOS, MODIFICAR_PLANTILLA } from '../../../constants/messages';
import BasicModal from '../../../modals/BasicModal';
import PlantillasTabla from './PlantillasTabla';
import showMessage from '../../../components/Messages';
import { getData, postData, putData, uploadDocument } from '../../../functions/api';
import { logError } from '../../../components/utils/logError.js';

const Plantillador = () => {
    const [loading, setLoading] = useState(true);
    const [isOpen, setIsOpen] = useState(false);
    const [dataTable, setDataTable] = useState([]);
    const [dataTableOriginal, setDataTableOriginal] = useState([]);
    const [tipoPlantillaOptions, setTipoPlantillaOptios] = useState([]);
    const [plantillasNuevas, setPlantillasNuevas] = useState([]);
    const [planSelected, setPlanSelected] = useState(null);
    const [saveAction, setSaveAction] = useState(null);
    const [tipoSelected, setTipoSelected] = useState('');

    useEffect(() => {
        getDataInit();
    }, []);

    const getDataInit = (callback = () => { }) => {
        let tipoPlantilla = getData('/administracion/plantillador/tipo-plantilla');
        let plantillas = getData('/administracion/plantillador');

        Promise.all([tipoPlantilla, plantillas])
            .then(([tipoPlantillaResp, plantillasResp]) => {
                setTipoPlantillaOptios(tipoPlantillaResp.data);
                let data = plantillasResp.data;
                setDataTable(tipoSelected ? [data.find(i => i.idTipoPlantillador === tipoSelected)] : data);
                setDataTableOriginal(data);
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                ShowMessage("Ocurrió un error");
            })
            .finally(() => {
                callback();
            })
    };

    const handleChangeIdTipo = (e) => {
        let selected = Number(e?.target?.value);
        setTipoSelected(selected);
        if (selected) {
            let filtered = [...dataTableOriginal].find(plantilla => plantilla.idTipoPlantillador === selected);
            setDataTable([filtered]);
        } else {
            setDataTable(dataTableOriginal);
        }
    }

    const guardarPlantillas = () => {
        setLoading(true);
        postData("/administracion/plantillador/plantillas", plantillasNuevas)
            .then((response) => {
                setSaveAction(null);
                setPlantillasNuevas([]);
                getDataInit(() => showMessage(ADMIN_FORMATOS.MSG001));
            })
            .catch((error) => {
                logError("error => ", error);
                ShowMessage("Ocurrió un error");
                setLoading(false);
            });
    }

    const updateStatus = (plantillaUpdate) => {
        setLoading(true);
        let data = {
            ...plantillaUpdate,
            subRows: undefined,
            estatus: !plantillaUpdate.estatus
        };

        putData("/administracion/plantillador/plantillas", [data])
            .then((response) => {
                getDataInit(() => showMessage(MODIFICAR_PLANTILLA.MSG005));
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage("Ocurrió un error");
            })
    }

    const handleSave = () => {
        switch (saveAction) {
            case "guardarPlantilla":
                guardarPlantillas();
                break;
            case "guardarProforma":
                guardarPlantillas();
                break;
            case "uploadPlan":
                uploadPlantillaPlan();
                break;
            default:
        }
    }

    const uploadPlantillaPlan = () => {
        setLoading(true);
        const { documento, idPlantillador, name } = planSelected;

        const formData = new FormData();
        formData.append('file', documento);

        uploadDocument("/administracion/plantillas/cargar-excel?name=" + name + "&id=" + idPlantillador, formData)
            .then((response) => {
                setLoading(false);
                showMessage(ADMIN_FORMATOS.MSG001);
                setSaveAction(null);
            })
            .catch((error) => {
                logError("error => ", error);
                ShowMessage("Ocurrió un error");
                setLoading(false);
            });
    }

    const handleCancel = () => {
        setIsOpen(false);
        let cleanNew = dataTable.map(obj => {
            if (obj.subRows) {
                obj.subRows = obj.subRows.filter(plantilla => plantilla.idPlantillador || plantilla.idSubPlantillador);
            }
            return obj;
        });
        setDataTable(cleanNew);
        setPlantillasNuevas([]);
        setPlanSelected(null);
        setSaveAction(null);
    };

    const addPlantilla = (plantilla) => {
        setPlantillasNuevas([...plantillasNuevas, plantilla]);
    }

    return (

        <Container className='mt-3 px-3'>

            {loading && <Loader />}

            <MainTitle title="Plantillas" />

            <Accordion title="Búsqueda" collapse={false} showChevron={false} >

                <Row className='mb-4'>
                    <Col md={4}>
                        <Select
                            label="Tipo de plantilla"
                            name={"tipoPlantilla"}
                            value={tipoSelected}
                            onChange={e => handleChangeIdTipo(e)}
                            options={tipoPlantillaOptions}
                            defaultOptionText="Todas"
                            keyValue="primaryKey"
                            keyTextValue="nombre"
                        />
                    </Col>
                </Row>

                <PlantillasTabla
                    data={dataTable}
                    setData={setDataTable}
                    addPlantilla={addPlantilla}
                    setPlanSelected={setPlanSelected}
                    setSaveAction={setSaveAction}
                    saveAction={saveAction}
                    updateStatus={updateStatus}
                />

                <Row>
                    <Col md={12} className="text-end">
                        <Button
                            variant="red"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={() => setIsOpen(true)}
                        >
                            Cancelar
                        </Button>
                        <Button
                            variant="green"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={handleSave}
                        >
                            Guardar
                        </Button>
                    </Col>
                </Row>
            </Accordion>

            <BasicModal
                show={isOpen}
                size={"md"}
                title="Mensaje"
                approveText={"Sí"}
                denyText={"No"}
                handleApprove={handleCancel}
                handleDeny={() => setIsOpen(false)}
                onHide={() => setIsOpen(false)}
            >
                {MODIFICAR_PLANTILLA.MSG001}
            </BasicModal>

        </Container >
    );
}

export default Plantillador;
