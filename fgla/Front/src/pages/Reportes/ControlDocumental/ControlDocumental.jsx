import React, { useState, useEffect, useMemo, useRef } from 'react'
import { Card, Container, Form, Button, Row, Col, InputGroup } from 'react-bootstrap'
import { injectActions, downloadExcelBlob } from '../../../functions/utils';
import { MainTitle, Accordion, Loader, ModalSatCloud } from '../../../components';
import { TablaEditable } from '../../../components/table/TablaEditable';
import IconButton from '../../../components/buttons/IconButton';
import moment from 'moment';
import { getData, postData, downloadDocumentPost, downloadDocument } from '../../../functions/api';
import showMessage from '../../../components/Messages';
import Multiselect from 'multiselect-react-dropdown';
import { logError } from '../../../components/utils/logError.js';
import {
  Select,
  SelectMultiple,
  TextField,
  TextFieldDate,
} from "../../../components/formInputs";
import DetalleControlDoc from './DetalleControlDoc';
import GestionDocumental from "../../Reintegros/GestionDocumental";
import { Formik, useFormikContext, useFormik, useField } from 'formik';
import { CONTROL_DOCUMENTAL } from '../../../constants/messages';

const VALORES_INICIALES = {
  idEstatusProyecto: [],
  idProyecto: [],
  documento: "",
  idFase: [],
  idPlantilla: [],

  page: 0,
  size: 15
}


const FORMAT_DATE = "DD/MM/YYYY";

const ColoredLine = () => (
  <hr
      style={{
          borderTop: "1px solid grey ", 
          marginLeft: "auto", 
          marginRight: "auto",
          width: "98%"
      }}
  />
);

export function ControlDocumental() {
  const tableReference = useRef();
  // const ID_KEY_NAME = "idProyecto";
  const [initialValues, setInitialValues] = useState({ ...VALORES_INICIALES });
  const [loading, setLoading] = useState(true);
  const [estatusProyectoOptions, setEstatusProyectoOptions] = useState([]);
  const [proyectoOptions, setProyectoOptions] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const [faseOptions, setFaseOptions] = useState([]);
  const [plantillaOptions, setPlantillaOptions] = useState([]);
  const [docsRequired, setdocsRequired] = useState([0]);
  const [docsLoading, setdocsLoading] = useState([0]);
  const [docsNotAvailable, setdocsNotAvailable] = useState([0]);
  const [docsPending, setdocsPending] = useState([0]);
  const [dataTableSelected, setDataTableSelected] = useState([]);
  const [dataTable, setDataTable] = useState([]);
  const [detalleControl, setDetalleControl] = useState(false);
  const [data, setData] = useState('');
  const [viewControlDocumental, setViewControlDocumental] = useState(false);
  const [typeControlDocumentalComite, setTypeControlDocumentalComite] = useState(false);
  const [path, setPath] = useState('');
  // const [pdf, setPdf] = useState("");
  const [valuesConsulta, setValuesConsulta] = useState({}); 
  // const [arrayOptions, setArrayOptions] = useState([]);
  const [totalDocs, setTotalDocs] = useState(0);
  const [selectedEstatus, setSelectedEstatus] = useState([]);
  const [selectedProyecto, setSelectedProyecto] = useState([]);
  const [selectedFase, setSelectedFase] = useState([]);
  const [selectedPlantilla, setSelectedPlantilla] = useState([]);
  const [dataEstatus, setDataEstatus] = useState([]);
  const [dataProyecto, setDataProyecto] = useState([]);
  const [dataFase, setDataFase] = useState([]);
  const [dataPlantilla, setDataPlantilla] = useState([]);
  const [disablePlantilla, setDisablePlantilla] = useState(false);
  // const [itemChecked, setItemChecked] = useState([]);
  const [checked, setChecked] = useState(true);
  const [showTable, setShowTable] = useState(false);
  const formRef = React.createRef();

  useEffect(() => {
    getDataInit();
  }, []);
  
  const getDataInit = () => {
    let estatus = getData('/reportes/controldoc/estatus-proyecto');
    let fase = getData('/reportes/controldoc/fase-proyecto');
    
    Promise.all([estatus, fase])
            .then(([estatusResp, faseResp]) => {
              setEstatusProyectoOptions(estatusResp.data);
              setFaseOptions(faseResp.data);
              setLoading(false);
          })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage(CONTROL_DOCUMENTAL.MSG002);
            });
  }

  const buscarReportes = () => {
    const data = {
      idEstatusProyecto: dataEstatus,
      idProyecto: dataProyecto,
      documento: inputValue,
      idFase: dataFase,
      idPlantilla: dataPlantilla,
    }
    setLoading(true);
    console.log("buscarReportes > data => ", data);
    setValuesConsulta(data);
    postData('/reportes/controldoc/reporte-control-documental', data)
        .then((response) => {
          const dataDocs = response.data;
          setTotalDocs(dataDocs.content.length);
          processData(response.data);
          setdocsRequired(dataDocs.documentosRequeridos );
          setdocsLoading(dataDocs.documentosCargados );
          setdocsNotAvailable(dataDocs.documentosQueNoAplican);
          setdocsPending(dataDocs.documentosPendientes);
          setShowTable(true);
          if (response.data.content.length === 0) {
              
              showMessage(CONTROL_DOCUMENTAL.MSG001);
          }
          setLoading(false);
        })
        .catch((error) => {
            setLoading(false);
            setShowTable(true);
            clearNoDataFound();
            showMessage(CONTROL_DOCUMENTAL.MSG004);
        });
  }

  const processData = (data) => {
    let processedDataTable = [];
    data.content.forEach((item) => {
        let row = {
            ...item,
            idProyecto: item.idProyecto,
            nombreCorto: item.nombreCorto,
            fase: item.fase,
            plantilla: item.plantilla,
            descripcion: item.descripcion,
            requerido: item.requerido,
            noAplica: item.noAplica,
            estatus: item.estatus,
            justificacion: item.justificacion,
            fechaModificacion: item.fechaModificacion,
        };
        processedDataTable.push(row);
    });
    
    setDataTable(processedDataTable);
};

const dateFormat = (date) => {
  let formatedDateTime = date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
  return formatedDateTime;
}


const handleDownloadExcel = () => {
  console.log('Valores de descarga en excel ',valuesConsulta);
  setLoading(true);
  downloadDocumentPost('/reportes/controldoc/reporte-export', valuesConsulta)
    .then((response) => {
        setLoading(false);
        console.log('Response data ',response);
        downloadExcelBlob(response.data, 'reportes/controldoc/reporte-control-documental');
    })
    .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage(CONTROL_DOCUMENTAL.MSG002);
    });
}

const handleDownloadZip = () => {
  setLoading(true);
  downloadDocumentPost('/reportes/controldoc/descargar-zip', valuesConsulta)
    .then((response) => {
      var urlDescarga = window.URL.createObjectURL(response.data);
        var link = document.createElement('a');
        link.href = urlDescarga;
        link.download = "Descarga.zip";
        link.target = "_blank";
        link.click();  
      setLoading(false);
    })
    .catch((error) => {
      logError("error => ", error);
      setLoading(false);
      showMessage(CONTROL_DOCUMENTAL.MSG002);
  });
   
}

const handleClear = (resetForm) => () => {
  console.log("handleClear");
  setDataTable([]);
  setDataTableSelected([]);
  setSelectedEstatus([0]);
  getDataInit();
  setInitialValues({ ...VALORES_INICIALES });
  setdocsRequired([0]);
  setdocsLoading([0]);
  setdocsNotAvailable([0]);
  setdocsPending([0]);
  setInputValue('');
  setShowTable(false);
  setProyectoOptions([]);
  setPlantillaOptions([]);
  setDisablePlantilla(false);

  setDataEstatus([]);
  setDataProyecto([]);
  setDataFase([]);
  setDataPlantilla([]);

  resetForm();
}

const clearNoDataFound = () => {
  setdocsRequired([0]);
  setdocsLoading([0]);
  setdocsNotAvailable([0]);
  setdocsPending([0]);
  setDataTable([]);
  setDataTableSelected([]);
}

const onInputDocument = ({target}) => {
  setInputValue( target.value );
  
}

const handleShow = (row) => () => {
  if(row.esPdf) {
    setLoading(true);
    setViewControlDocumental(false);
    downloadDocument(row.pathServicio)
    .then((response) => {
      console.log("response: ", response);

        var reader = new FileReader();
        reader.readAsDataURL(response.data);
        reader.onload = (e) => {
            setData(e.target.result.split(",")[1]);
            setLoading(false);
            setDetalleControl(true);
        };
    })
    .catch((error) => {
      logError("error => ", error);
      setLoading(false);
  });
    /*.catch((error) => error.response.data.text()).then(errText =>{
      console.error("error => ", JSON.parse(errText));
      showMessage(JSON.parse(errText).mensaje);
      setLoading(false);
    });*/
    
  }
};

const handleDownload = (row) => () => {
  console.log("handleDownload > idReporte => ", row);

  if(row.esPdf) {
    downloadDocument(row.pathServicio)
    .then((response) => {
        //console.log("response download: " + response);
        var urlDescarga = window.URL.createObjectURL(response.data);
        var link = document.createElement('a');
        link.href = urlDescarga;
        //console.log("data.content ROW: " + response.data.content);
        link.download = row.descripcion + ".pdf";
        link.target = "_blank";
        link.click();  
    })
    .catch((error) => {
      logError("error => ", error);
      setLoading(false);
  });
    console.log('idProyecto: '+row.idProyecto+' esPdf: '+row.esPdf+' path: '+row.pathServicio+' ');
  }
}

const handleDetail = (row) => () => {
  console.log('Detail icono');
  let objStr = JSON.stringify(row);
  console.log("objStr stringify: " + objStr);
  let state = { idProyecto: row.idProyecto, identificador: row.identificador, idReferencia: row.idReferencia, proyecto: objStr }
  console.log("state: " + state);
  setData(state);
  setDetalleControl(true);
  setViewControlDocumental(true);
  if(row.identificador == 4){
    setTypeControlDocumentalComite(true);
  }else{
    setTypeControlDocumentalComite(false);
  }
}

const handleChangeRequest = (ids, setFieldValue, type) => {
  setFieldValue(type, ids);
  
  switch (type) {
    case "idEstatusProyecto": {
      setDataEstatus(ids);
      setDataProyecto([]);
      setLoading(true);
      console.log('La seleccion del Id Estatus Proyecto es: ', ids);
      const data = { id: ids };
      const proyecto = postData('/reportes/controldoc/nombre-corto-proyecto', data);
        Promise.all([proyecto])
          .then(([proyectoResp]) => {
            setProyectoOptions(proyectoResp.data);     
            setLoading(false);
          })
          .catch(err => {
            logError("info => ", err);
          })
      break;
    }
    case "idProyecto": {
      console.log('La seleccion del proyecto es: ', ids);
      setDataProyecto(ids);
      break;
    }
    case "idFase": {
      setLoading(false);
      console.log('La seleccion de la fase es: ', ids);
      console.log('Longitud: ', ids.length);
      if(ids.length > 1){
        setPlantillaOptions([]);
        setDataPlantilla([]);
        setFieldValue("idPlantilla", []);
        setDisablePlantilla(true);
      }else if(ids.length > 0){    
        setDisablePlantilla(false);
        setLoading(true);
        const fase =  getData(`/reportes/controldoc/nombre-plantilla/${ids}`)
          Promise.all([fase])
            .then(([faseResp]) => {
              setDataPlantilla([]);
              setPlantillaOptions(faseResp.data);     
              setLoading(false);
            })
            .catch(err => {
              logError("error => ", err)
              setLoading(false);
            })    
      }else{
        setDisablePlantilla(false);
        setPlantillaOptions([]);
        setDataPlantilla([]);
        setFieldValue("idPlantilla", []);
      }
      setDataFase(ids);
      break;
    }
    case "idPlantilla": {
      console.log('La seleccion de la plantilla es ',ids);
      setDataPlantilla(ids);
      console.log('La seleccion de la plantilla es Final: ',dataPlantilla);
      break;
    }
  }
};

const CustomPending = ({getValue}) => {
  const pending = getValue();
    if( pending === "Pendiente"){
      return (
        <>
          { pending } &nbsp;   
          <IconButton 
            type="alert"
            iconSize = "6px"
          /> 
        </>
      )        
      } else {
        return (
          <>{ pending }</>
        )
      }
}

const CustomCheckNull = ({getValue}) => {
  const checkNull  = getValue();
    if( checkNull  !== "null"){
      return (<>{checkNull}</>)         
    } 
}

  const columns = useMemo(() => [
    {
        accessorKey: "proyecto",
        header: "Id proyecto",
        cell: (props) => <p>{props.getValue()}</p>
    },
    {
        accessorKey: "nombreCorto",
        header: "Nombre corto",
        cell: (props) => <p>{props.getValue()}</p>
    },
    {
        accessorKey: "fase",
        header: "Fase",
        cell: (props) => <p>{props.getValue()}</p>
    },
    {
        accessorKey: "plantilla",
        header: "Plantilla",
        cell: (props) => <p>{props.getValue()}</p>
    },
    {
        accessorKey: "descripcion",
        header: "Descripción",
        cell: (props) => <p>{props.getValue()}</p>
    },
    {
        accessorKey: "requerido",
        header: "Requerido",
        cell: (props) => <p>{props.getValue()}</p>
    },
    {
        accessorKey: "noAplica",
        header: "No aplica",
        cell: (props) => (
          <Form>
            <Form.Check
              disabled
              type={'checkbox'}
              checked={props.getValue()}
            />
          </Form>
        ),
        enableColumnFilter: false
    },
    {
        accessorKey: "estatus",
        header: "Estatus",
        cell: (props) => (
          <CustomPending
            getValue={props.getValue}
          />      
        ),
    },
    {
        accessorKey: "justificacion",
        header: "Justificación",
        cell: (props) => <p>{props.getValue()}</p>
    },
    {
        accessorKey: "fechaModificacion",
        header: "Fecha última modificación",
        cell: (props) => (
          <CustomCheckNull 
            getValue={props.getValue}
          />
        ),
    },
    {
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) => (
          <div style={{width: "120px"}}>
            { props.row.original.cargado === true && <>
                <IconButton
                    type={"tableDownload"}
                    iconSize="lg"
                    tooltip={"Descargar documento"}
                    onClick={handleDownload(props.row.original)}
                />
              </>
            }
            { props.row.original.esPdf === true && <>
              <IconButton
                  type={"show"}
                  iconSize="lg"
                  tooltip={"Ver PDF"}
                  onClick={handleShow(props.row.original)}
              />
              </>
            }
            { props.row.original.cargado === true && <>
                <IconButton
                  type={"showDetail"}
                  iconSize="lg"
                  tooltip={"Ver Detalle"}
                  onClick={handleDetail(props.row.original)}
                />  
              </>    
            }
          </div>
        ),
        enableColumnFilter: false,
        enableSorting: false
    },
  ], []);
 

  return (
    <Container className='mt-3 px-3'>

      {loading && <Loader />}

      <MainTitle title="Reporte de Control Documental" />

      <Accordion title={"Criterios de búsqueda"}>

        <Formik
          initialValues={initialValues}
          enableReinitialize
          validateOnMount={true}
          onSubmit={(e, { resetForm }) => buscarReportes(e, resetForm)}
        >{({ 
            handleSubmit,
            handleChange,
            handleBlur,
            resetForm,
            values,
            errors,
            touched,
            setFieldValue,
            isValid,
         }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>

          <Row className="mb-2">
            <Col md={4}>
              <SelectMultiple
                onChange={(e) => {
                  handleChangeRequest(
                    e,
                    setFieldValue,
                    "idEstatusProyecto"
                  );
                }}
                label={"Estatus del proyecto:"}
                name={"idEstatusProyecto"}
                values={values.idEstatusProyecto}
                options={estatusProyectoOptions}
                keyValue="primaryKey"
                keyTextValue="nombre"
              />
            </Col>
            <Col md={4}>
              
              <SelectMultiple
                onChange={(e) => {
                  handleChangeRequest(
                    e,
                    setFieldValue,
                    "idProyecto"
                  );
                }}
                label={"Proyecto:"}
                name={"idProyecto"}
                values={values.idProyecto}
                options={proyectoOptions}
                keyValue="idProyecto"
                keyTextValue="nombreCorto"
              />
            </Col>
            <Col md={4}>
            <TextField
                  label="Documento:"
                  name="documento"
                  value={values.documento}
                  onChange={(e) => { handleChange(e); onInputDocument(e); }}
              />
            </Col>
          </Row>

          <Row className="mb-2">
            <Col md={4}>
              
              <SelectMultiple
                onChange={(e) => {
                  handleChangeRequest(
                    e,
                    setFieldValue,
                    "idFase"
                  );
                }}
                label={"Fase:"}
                name={"idFase"}
                values={values.idFase}
                options={faseOptions}
                keyValue="primaryKey"
                keyTextValue="nombre"
              />
            </Col>
            <Col md={4}>
              
              <SelectMultiple
                onChange={(e) => {
                  handleChangeRequest(
                    e,
                    setFieldValue,
                    "idPlantilla"
                  );
                }}
                disabled={disablePlantilla}
                label={"Plantilla:"}
                name={"idPlantilla"}
                values={values.idPlantilla}
                options={plantillaOptions}
                keyValue="idPlantillaVigente"
                keyTextValue="nombre"
              />       
            </Col>
            <Col md={12} className="text-end mb-2">
              <Button
                variant="gray"
                className="btn-sm ms-2 waves-effect waves-light"                 
                onClick={handleClear(resetForm)}         
              >
                Limpiar
              </Button>
              <Button
                variant="gray"
                className="btn-sm ms-2 waves-effect waves-light"      
                onClick={() => { console.log("isValid => ", !isValid); }}
                type="submit"          
              >
                Buscar
              </Button>
            </Col>
          </Row>

          {
            showTable === true && <>
            <Row>
              <ColoredLine />
              <Col md={3} style={{justifyItems: "center"}}>
                <div className="mb-5" >
                  <Card 
                    style={{
                      boxShadow: "4px 4px 10px 0px rgba(0,0,0,0.75)",                    
                      minWidth: "230px"
                  }}>
                    <Card.Body>
                      <Card.Subtitle className="mb-2 mt-2 text-center text-muted">Documentos requeridos</Card.Subtitle>
                      <Card.Text className='mt-3 text-center' >
                      <Row>
                        <Col xs={2} style={{width: "80%", padding: 0, paddingLeft: "40px"}}> { docsRequired } </Col>
                        <Col xs={2} style={{padding: 0}}>
                          <IconButton 
                            type="fileCheck" className="icon-button-disabled"
                          /> 
                        </Col>  
                      </Row>                       
                      </Card.Text>
                    </Card.Body>                  
                  </Card>
                </div>
              </Col>
              <Col md={3} style={{justifyItems: "center"}}>
                <div className="mb-5" >
                  <Card  
                    style={{
                      boxShadow: "4px 4px 10px 0px rgba(0,0,0,0.75)",
                      minWidth: "230px"
                  }}>
                    <Card.Body>
                      <Card.Subtitle className="mb-2 mt-2 text-center text-muted">Documentos cargados</Card.Subtitle>
                      <Card.Text className='mt-3 text-center' >
                        <Row>
                          <Col xs={2} style={{width: "80%", padding: 0, paddingLeft: "40px", color: "#3F51B5"}}> { docsLoading } </Col>
                          <Col xs={2} style={{padding: 0}}>
                            <IconButton
                              type="arrowUp" className="icon-button-disabled"
                            /> 
                          </Col>  
                        </Row> 
                      </Card.Text>
                    </Card.Body>                   
                  </Card>
                </div>
              </Col>
              <Col md={3} style={{justifyItems: "center"}}>
                <div className="mb-5" >
                  <Card  
                    style={{
                      boxShadow: "4px 4px 10px 0px rgba(0,0,0,0.75)",
                      minWidth: "230px"
                  }}>
                    <Card.Body>
                      <Card.Subtitle className="mb-2 mt-2 text-center text-muted">Documentos que no aplican</Card.Subtitle>
                      <Card.Text className='mt-3 text-center' >
                        <Row>
                          <Col xs={2} style={{width: "80%", padding: 0, paddingLeft: "40px", color: "#3F51B5"}}> { docsNotAvailable } </Col>
                          <Col xs={2} style={{padding: 0}}>
                            <IconButton
                              type="noApply" className="icon-button-disabled"
                            /> 
                          </Col>  
                        </Row> 
                      </Card.Text>
                    </Card.Body>  
                  </Card>
                </div>
              </Col>
              <Col md={3} style={{justifyItems: "center"}}>
                <div className="mb-5" >
                  <Card 
                    style={{
                      boxShadow: "4px 4px 10px 0px rgba(0,0,0,0.75)",
                      minWidth: "230px"
                  }}>
                    <Card.Body>
                      <Card.Subtitle className="mb-2 mt-2 text-center text-muted">Documentos pendientes</Card.Subtitle>
                      <Card.Text className='mt-3 text-center' >
                        <Row>
                          <Col xs={2} style={{width: "80%", padding: 0, paddingLeft: "40px", color: "#FF0000"}}> { docsPending } </Col>
                          <Col xs={2} style={{padding: 0}}>
                            <IconButton
                              type="alert"
                            /> 
                          </Col>  
                        </Row> 
                      </Card.Text>                    
                    </Card.Body>            
                  </Card>
                </div>
              </Col>
            </Row>
            <Row>
                <Col md={12} className="text-end mb-2">
                    <IconButton
                        type="excel"
                        onClick={handleDownloadExcel}
                        disabled={dataTable.length === 0}
                        tooltip={"Exportar a Excel"}
                    />
                    <IconButton
                        type="zip"
                        onClick={handleDownloadZip}
                        disabled={dataTable.length === 0}
                        tooltip={"Descargar ZIP"}
                    />
                </Col>
            </Row>
            </>
          }
          </Form>
          )}
        </Formik>
        
        {
            showTable === true && <>
            <TablaEditable
                ref={tableReference}
                header="Proyectos"
                dataTable={dataTable}
                hasPagination
                isFiltered
                columns={columns}
                onUpdate={setDataTable}
            />
            </>
        }

        <DetalleControlDoc
          show={detalleControl}
          setShow={setDetalleControl}
          data={data}
          viewControlDocumental={viewControlDocumental}
          typeControlDocumentalComite={typeControlDocumentalComite}
        />

      </Accordion>

    </Container>
    
  )
}
