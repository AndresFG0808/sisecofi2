import React, { useState, useEffect, useRef } from "react";
import { Container, Row, Col, Button, Form } from "react-bootstrap";
import { MainTitle, Loader, Accordion } from "../../../components";
import CustomPicklist from "../../../components/CustomPicklist";
import IconButton from "../../../components/buttons/IconButton";
import { Formik } from "formik";
import { Tooltip } from "../../../components/Tooltip";
import { Select, TextField } from "../../../components/formInputs";
import Authorization from "../../../components/Authorization";
import {
  getData,
  postData,
  putData,
  downloadDocumentPost,
} from "../../../functions/api";
import { useToast } from "../../../hooks/useToast";
import SingleBasicModal from "../../../modals/SingleBasicModal";
import BasicModal from "../../../modals/BasicModal";
import { MESSAGES } from "./constants";
import { downloadExcelBlob } from "../../../functions/utils";
import _ from "lodash";
const VALORES_INICIALES = {
  estatus: "",
  nombreProyecto: "",
  idProyecto: "",
  central: "",
  areaPlaneacion: "",
  liderProyecto: "",
};

const AsignarProyecto = () => {
  const formRef = useRef(); // Referencia al formulario
  const handleResetForm = () => {
    if (formRef.current) {
      formRef.current.resetForm(); // Resetea el formulario utilizando la referencia
    }
  };

  const { errorToast } = useToast();
  const [loading, setLoading] = useState(true);
  const [isCancelChanges, setIsCancelChanges] = useState(false);
  const [isCancelProjectChanges, setIsCancelProjectChanges] = useState(false);
  const [idProyectoState, setIdProyecto] = useState("");
  const [estatusState, setEstatusState] = useState("");
  const [nombreProyectoState, setNombreProyectoState] = useState("");

  const [valoresIniciales, setValoresIniciales] = useState({
    ...VALORES_INICIALES,
  });
  const [usuarioSelected, setUsuarioSelected] = useState("");
  const [usuarioSelectedClassName, setUsuarioSelectedClassName] = useState("");

  const [users, setUsers] = useState([]);
  const [usersAssigned, setUsersAssigned] = useState([]);

  const [usersSource, setUsersSource] = useState([]);
  const [usersTarget, setUsersTarget] = useState([]);
  const [dataFilter, setDataFilter] = useState({});

  const [projectsSource, setProjectsSource] = useState([]);
  const [projectsTarget, setProjectsTarget] = useState([]);
  const [projects, setProjects] = useState([]);
  const [projectsAssign, setProjectsAssign] = useState([]);

  const [usersCatalog, setUsersCatalog] = useState([]);
  const [statusCatalog, setStatusCatalog] = useState([]);
  const [projectShortNameCatalog, setProjectShortNameCatalog] = useState([]);

  const [classShortNameProjectName, setClassShortNameProjectName] =
    useState("");

  const [successModalMessage, setSuccessModalMessage] = useState("");
  const [showOpenSuccessModal, setShowOpenSuccessModal] = useState(false);
  const [showCancelProjectAssign, setShowCancelProjectAssign] = useState(false);
  const [showCancelUsersAssign, setShowCancelUsersAssign] = useState(false);

  const [isActiveAssignUsers, setIsActiveAssignUsers] = useState(false);
  const [isActiveAssignProjects, setIsActiveAssignProjects] = useState(false);

  const [estatusHelperText, setEstatusHelperText] = useState(null);
  const [nombreCortoHelperText, setNombreCortoHelperText] = useState(null);
  const [idProyectoHelperText, setIdProyectoHelperText] = useState(null);

  const [isSearched, setIsSearched] = useState(false);

  useEffect(() => {
    getDataInit();
  }, []);

  const onSetInitialUsersArray = (catalog) => {
    return [...catalog].map((item) => {
      const newItem = { ...item };
      newItem.selected = false;
      return newItem;
    });
  };

  const setProyectoCorto = async (idEstatusProyecto, nombreCorto) => {
    const statusResponse = statusCatalog.filter(
      (item) => item.primaryKey === idEstatusProyecto
    )[0];
    try {
      let proyectosCatalogo = await postData(
        "/proyectos/proyecto-nombre-corto",
        {
          nombre: statusResponse.nombre,
          descripcion: statusResponse.descripcion,
          fechaCreacion: statusResponse.fechaCreacion,
          fechaModificacion: statusResponse.fechaModificacion,
          estatus: statusResponse.estatus,
          primaryKey: statusResponse.primaryKey,
          id: statusResponse.primaryKey,
          idEstatusProyecto: statusResponse.primaryKey,
        }
      );
      setProjectShortNameCatalog(proyectosCatalogo.data);
      setNombreProyectoState(
        proyectosCatalogo.data.filter(
          (item) => item.nombreCorto === nombreCorto
        )[0].idProyecto
      );
      formRef.current.values.nombreProyecto = proyectosCatalogo.data.filter(
        (item) => item.nombreCorto === nombreCorto
      )[0].idProyecto;
      return;
    } catch (e) {}
  };

  const onGetUsers = async (idProyecto, estatus, nombreProyecto) => {
    let projectShortNameApi = await postData("/proyectos/buscar-proyecto", {
      idProyecto: idProyecto,
      nombreCorto:
        nombreProyecto !== "" &&
        projectShortNameCatalog.filter(
          (item) => nombreProyecto == item.idProyecto
        )[0].nombreCorto,
      idEstatusProyecto: estatus,
    });
    await Promise.all([projectShortNameApi])
      .then(async ([projectShortNameResponse]) => {
        if (idProyecto !== "" && nombreProyecto === "" && estatus === "") {
          setEstatusState(projectShortNameResponse.data.idEstatusProyecto);
          formRef.current.values.estatus =
            projectShortNameResponse.data.idEstatusProyecto;
          await setProyectoCorto(
            projectShortNameResponse.data.idEstatusProyecto,
            projectShortNameResponse.data.nombreCorto
          );
        }

        setUsers(
          onSetInitialUsersArray([...projectShortNameResponse.data.usuarios])
        );
        setUsersAssigned(
          onSetInitialUsersArray([
            ...projectShortNameResponse.data.usuariosAsignados,
          ])
        );
        return null;
      })
      .catch((err) => {
        onShowErrorMessage(err);
        return null;
      });
  };

  const onGetUsersReport = async () => {
    setLoading(true);
    try {
      const formData = new FormData();
      formData.append(
        "idProyecto",
        isSearched === false ? "" : idProyectoState
      );
      formData.append(
        "nombreCorto",
        isSearched === false
          ? ""
          : nombreProyectoState !== ""
          ? projectShortNameCatalog.filter(
              (item) => nombreProyectoState == item.idProyecto
            )[0].nombreCorto
          : ""
      );
      formData.append(
        "idEstatusProyecto",
        isSearched === false ? "" : estatusState
      );

      let projectShortNameApi = await downloadDocumentPost(
        "/proyectos/reporte-proyectos-usuarios",
        formData
      );
      downloadExcelBlob(projectShortNameApi.data, "Proyectos-Usuarios");
    } catch (err) {
      onShowErrorMessage(err);
    }
    setLoading(false);
  };

  const getDataInit = async () => {
    const statusApi = getData("/proyectos/estatus-proyecto");
    const statusUsersCatalogApi = getData("/proyectos/usuarios-proyecto");
    Promise.all([statusApi, statusUsersCatalogApi])
      .then(async ([statusResponse, statusUsersCatalogResponse]) => {
        setStatusCatalog(statusResponse.data);
        setUsersCatalog(statusUsersCatalogResponse.data);
        setLoading(false);
      })
      .catch((err) => {
        onShowErrorMessage(err);
        setLoading(false);
      });
  };

  const onRenderErrorMessage = (err) => {
    if (err.code === "ERR_NETWORK") {
      setShowOpenSuccessModal(true);
      setSuccessModalMessage(
        "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)"
      );
    } else {
      onShowErrorMessage(err);
    }
  };

  const buscar = async (data) => {
    setLoading(true);
    setIsSearched(true);
    let isError = false;
    if (
      (data.estatus === "" || data.estatus === null) &&
      (data.nombreProyecto === "" || data.nombreProyecto === null) &&
      (data.idProyecto === "" || data.idProyecto === null)
    ) {
      if (data.estatus === "" || data.estatus === null) {
        setEstatusHelperText("Dato inválido");
      }
      if (data.nombreProyecto === null || data.nombreProyecto === "") {
        setNombreCortoHelperText("Dato inválido");
        // setClassShortNameProjectName("is-invalid");
        isError = true;
      }
      if (data.idProyecto === null || data.idProyecto === "") {
        setIdProyectoHelperText("Dato inválido");
        isError = true;
      }
    } else if (
      data.estatus !== "" &&
      data.estatus !== null &&
      (data.nombreProyecto === "" || data.nombreProyecto === null)
    ) {
      setNombreCortoHelperText("Dato inválido");
    }

    if (data.nombreProyecto === "" && data.idProyecto === "") {
      isError = true;
    }

    if (isError) {
      errorToast(MESSAGES.CAMPOS_OBLIGATORIOS);
      setLoading(false);
    } else {
      setDataFilter({ ...data });
      try {
        await onGetUsers(data.idProyecto, data.estatus, data.nombreProyecto);
        setIsActiveAssignUsers(true);
        setIsCancelChanges(true);
      } catch (err) {
        onRenderErrorMessage(err);
      }
      setLoading(false);
    }
    return true;
  };

  const handleChangeUsuario = (e) => {
    setUsuarioSelected(e.target.value);
    setUsuarioSelectedClassName("is-valid");
  };

  const onSetInitialProjectsArray = (catalog) => {
    return [...catalog].map((item) => ({
      ...item,
      selected: false,
    }));
  };

  const onGetProjects = async (usuarioSelectedArg) => {
    const obj = usersCatalog.find((item) => item.idUser == usuarioSelectedArg);
    try {
      let projectShortNameApi = await postData(
        "/proyectos/buscar-usuario-proyecto",
        {
          idUser: obj.idUser,
          nombre: obj.nombre,
          rfcCorto: obj.rfcCorto,
          administracion: obj.administracion,
          estatus: obj.estatus,
        }
      );

      if (projectShortNameApi && projectShortNameApi.data) {
        const proyectos = projectShortNameApi.data.proyectos || [];
        const proyectosAsignados =
          projectShortNameApi.data.proyectosAsignados || [];
        setProjectsSource(onSetInitialProjectsArray([...proyectos]));
        setProjectsTarget(onSetInitialProjectsArray([...proyectosAsignados]));
      }
    } catch (err) {
      onRenderErrorMessage(err);
    }
    return obj;
  };

  const onCancelUsers = () => {
    setProjectsSource([]);
    setProjectsTarget([]);
    setUsuarioSelected("");
    setUsuarioSelectedClassName("");
    setIsCancelProjectChanges(true);
  };

  const onCancelProjects = async () => {
    setIdProyecto("");
    setEstatusState("");
    setNombreProyectoState("");
    handleResetForm();
    setClassShortNameProjectName("");
    setEstatusHelperText(null);
    setNombreCortoHelperText(null);
    setIdProyectoHelperText(null);
    setUsers([]);
    setUsersAssigned([]);
    setIsActiveAssignUsers(false);
    setIsCancelChanges(true);
    setProjectShortNameCatalog([]);
    return null;
  };

  const onShowErrorMessage = (err) => {
    err.message ? errorToast(err.message) : errorToast("Error genérico");
  };

  const onCloseBasicModal = () => {
    setShowOpenSuccessModal(false);
    setSuccessModalMessage("");
  };

  const onSetArrayUsers = (catalog) => {
    return [...catalog].map((item) => ({
      idUser: item.idUser,
      nombre: item.nombre,
      rfcCorto: item.rfcCorto,
      administracion: item.administracion,
      estatus: item.estatus,
    }));
  };

  const handleChangeStatus = async (event) => {
    setLoading(true);
    formRef.current.setFieldValue("nombreProyecto", "");
    setNombreProyectoState("");
    const value =
      event.target.value === "" ? "" : parseInt(event.target.value, 10);
    setClassShortNameProjectName("");
    setEstatusHelperText(null);
    setNombreCortoHelperText(null);
    setIdProyectoHelperText(null);
    setEstatusState(value);
    if (value !== "") {
      const statusResponse = statusCatalog.filter(
        (item) => item.primaryKey === value
      )[0];
      try {
        let projectShortNameResponse = await postData(
          "/proyectos/proyecto-nombre-corto",
          {
            nombre: statusResponse.nombre,
            descripcion: statusResponse.descripcion,
            fechaCreacion: statusResponse.fechaCreacion,
            fechaModificacion: statusResponse.fechaModificacion,
            estatus: statusResponse.estatus,
            primaryKey: statusResponse.primaryKey,
            id: statusResponse.primaryKey,
            idEstatusProyecto: statusResponse.primaryKey,
          }
        );
        setProjectShortNameCatalog(projectShortNameResponse.data);
      } catch (err) {
        onShowErrorMessage(err);
      }
    } else {
      setProjectShortNameCatalog([]);
    }
    setLoading(false);
  };

  return (
    <>
      {loading && <Loader />}
      <Container className="mt-3 px-3">
        <MainTitle title="Asignar proyectos" />

        <SingleBasicModal
          handleApprove={onCloseBasicModal}
          handleDeny={onCloseBasicModal}
          approveText={"Aceptar"}
          size="md"
          show={showOpenSuccessModal}
          title={"Mensaje"}
          onHide={onCloseBasicModal}
        >
          {successModalMessage}
        </SingleBasicModal>

        <BasicModal
          size="md"
          handleApprove={async () => {
            await onCancelProjects();
            setShowCancelProjectAssign(false);
          }}
          handleDeny={() => setShowCancelProjectAssign(false)}
          denyText={"No"}
          approveText={"Sí"}
          show={showCancelProjectAssign}
          title={"Mensaje"}
          onHide={() => setShowCancelProjectAssign(false)}
        >
          Al cancelar se perderán los cambios realizados. ¿Está seguro de
          continuar?
        </BasicModal>

        <BasicModal
          size="md"
          handleApprove={() => {
            // cancelar por usuario
            setIsActiveAssignProjects(false);
            setShowCancelUsersAssign(false);
            setIsCancelProjectChanges(true);
            // reinit proyecto
            onCancelUsers();
          }}
          handleDeny={() => setShowCancelUsersAssign(false)}
          denyText={"No"}
          approveText={"Sí"}
          show={showCancelUsersAssign}
          title={"Mensaje"}
          onHide={() => setShowCancelUsersAssign(false)}
        >
          Al cancelar se perderán los cambios realizados. ¿Está seguro de
          continuar?
        </BasicModal>

        <Accordion title="Por proyecto">
          <Row>
            <Formik
              innerRef={formRef}
              initialValues={valoresIniciales}
              validateOnMount={true}
              enableReinitialize={true}
              onSubmit={(e, { resetForm }) => {
                buscar(e, resetForm);
              }}
            >
              {({
                handleSubmit,
                handleChange,
                handleBlur,
                resetForm,
                values,
                errors,
                isValid,
                setFieldValue,
              }) => {
                if (!_.isEmpty(values) && values.nombreProyecto) {
                  let _proyecto = projectShortNameCatalog.find(
                    (s) => s.nombreProyecto == values.nombreProyecto
                  );
                  if (_proyecto && values.idProyecto != _proyecto.idProyecto) {
                    setFieldValue("idProyecto", _proyecto.idProyecto);
                    // Marcar que el campo fue auto-poblado
                    setIdProyecto(_proyecto.idProyecto);
                  }
                }

                return (
                  <Form autoComplete="off" onSubmit={handleSubmit}>
                    <Row>
                      <Col md={4}>
                        <Select
                          label="Estatus:"
                          name="estatus"
                          value={values.estatus}
                          disabled={isActiveAssignUsers}
                          onChange={(event) => {
                            handleChange(event);
                            handleChangeStatus(event);
                            setIsSearched(false);
                            setEstatusHelperText("");
                          }}
                          options={statusCatalog}
                          keyValue="primaryKey"
                          keyTextValue="nombre"
                          className={
                            estatusHelperText === null
                              ? ""
                              : estatusHelperText !== ""
                              ? "is-invalid"
                              : "is-valid"
                          }
                          helperText={estatusHelperText}
                        />
                      </Col>
                      <Col md={4}>
                        <Select
                          label={`Nombre corto del proyecto ${
                            values.estatus !== "" ? "*:" : ":"
                          }`}
                          name="nombreProyecto"
                          value={values.nombreProyecto}
                          onChange={(event) => {
                            handleChange(event);
                            setClassShortNameProjectName(
                              event.target.value === "" ? "" : "is-valid"
                            );
                            setNombreProyectoState(event.target.value);
                            setIsSearched(false);
                            setNombreCortoHelperText("");
                            // Limpiar Id proyecto cuando se cambie la selección de nombre corto
                            if (event.target.value === "") {
                              setFieldValue("idProyecto", "");
                              setIdProyecto("");
                            }
                          }}
                          options={projectShortNameCatalog}
                          disabled={
                            values.estatus === "" || values.estatus === null
                              ? true
                              : isActiveAssignUsers
                          }
                          keyValue="idProyecto"
                          keyTextValue="nombreCorto"
                          className={
                            nombreCortoHelperText === null
                              ? ""
                              : nombreCortoHelperText !== ""
                              ? "is-invalid"
                              : "is-valid"
                          }
                          helperText={nombreCortoHelperText}
                        />
                      </Col>
                      <Col md={4}>
                        <TextField
                          label="Id proyecto:"
                          name="idProyecto"
                          disabled={isActiveAssignUsers}
                          readOnly={values.nombreProyecto !== "" && values.idProyecto !== ""}
                          value={values.idProyecto}
                          onChange={(event) => {
                            setIdProyecto(event.target.value);
                            handleChange(event);
                            setIsSearched(false);
                            setIdProyectoHelperText("");
                          }}
                          className={
                            idProyectoHelperText === null
                              ? ""
                              : idProyectoHelperText !== ""
                              ? "is-invalid"
                              : "is-valid"
                          }
                          helperText={idProyectoHelperText}
                        />
                      </Col>
                    </Row>

                    <Row>
                      <Col md={12} className="text-end mb-2">
                        <Authorization process={"AP_PROY_CONS"}>
                          <Button
                            variant="gray"
                            disabled={isActiveAssignUsers}
                            className="btn-sm ms-2 waves-effect waves-light"
                            type="submit"
                          >
                            Buscar
                          </Button>
                        </Authorization>
                      </Col>
                    </Row>
                  </Form>
                );
              }}
            </Formik>
          </Row>
          <Row>
            <Col md={1} />

            <Col md={10}>
              <CustomPicklist
                sourceHeader="Usuarios"
                targetHeader="Usuarios asignados"
                sourceDataList={[...users]}
                targetDataList={[...usersAssigned]}
                onChangeTarget={setUsersTarget}
                onChangeSource={setUsersSource}
                textKey={"nombre"}
                idKey={"idUser"}
                rightButtonTextModal={
                  "¿Está seguro de asignar todos los usuarios?"
                }
                leftButtonTextModal={
                  "¿Está seguro de excluir los usuarios asignados?"
                }
                isCancelChanges={isCancelChanges}
                onCanceledChanges={() => {
                  setIsCancelChanges(false);
                }}
              />
            </Col>
            <Col
              md={1}
              className="d-flex align-items-end justify-content-center pb-3"
            >
              <IconButton
                disabled={false}
                type="excel"
                onClick={onGetUsersReport}
                tooltip={"Exportar Proyectos-Usuarios"}
              />
            </Col>
          </Row>
          {isActiveAssignUsers && (
            <Row>
              <Col md={12} className="text-end">
                <Button
                  variant="red"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={() => {
                    setIsSearched(false);
                    setShowCancelProjectAssign(true);
                  }}
                >
                  Cancelar
                </Button>
                <Authorization process={"AP_PROY_ADMIN"}>
                  <Button
                    onClick={async () => {
                      try {
                        setLoading(true);
                        await putData("/proyectos/guardar-proyectos-usuario", {
                          idProyecto: parseInt(
                            dataFilter.idProyecto &&
                              dataFilter.idProyecto !== ""
                              ? dataFilter.idProyecto
                              : projectShortNameCatalog.filter(
                                  (item) =>
                                    dataFilter.nombreProyecto == item.idProyecto
                                )[0].idProyecto,
                            10
                          ),
                          nombreCorto:
                            dataFilter.nombreProyecto.trim() === ""
                              ? ""
                              : projectShortNameCatalog.filter(
                                  (item) =>
                                    dataFilter.nombreProyecto == item.idProyecto
                                )[0].nombreCorto,
                          idEstatusProyecto: parseInt(dataFilter.estatus, 10),
                          usuarios: onSetArrayUsers([...usersSource]),
                          usuariosAsignados: onSetArrayUsers([...usersTarget]),
                        });
                        await onCancelProjects();
                        setShowOpenSuccessModal(true);
                        setIsSearched(false);
                        setSuccessModalMessage(
                          "Se guardaron los cambios con éxito."
                        );
                      } catch (err) {
                        onRenderErrorMessage(err);
                      }
                      setLoading(false);
                    }}
                    variant="green"
                    className="btn-sm ms-2 waves-effect waves-light"
                  >
                    Guardar
                  </Button>
                </Authorization>
              </Col>
            </Row>
          )}
        </Accordion>

        <Accordion title="Por usuario" show={false}>
          <Row className="mb-3">
            <Col md={3} />

            <Col md={6}>
              <Select
                label="Usuario*:"
                name="estatus"
                value={usuarioSelected}
                onChange={handleChangeUsuario}
                options={usersCatalog}
                keyValue="idUser"
                keyTextValue="nombre"
                className={usuarioSelectedClassName}
                helperText={"Dato requerido"}
              />
            </Col>
            <Col md={3}>
              <Authorization process={"AP_USR_CONS"}>
                <Button
                  variant="gray"
                  className="btn-sm ms-2 waves-effect waves-light mt-4"
                  type="submit"
                  onClick={async () => {
                    setLoading(true);
                    let isError = false;
                    if (usuarioSelected === "") {
                      setUsuarioSelectedClassName("is-invalid");
                      isError = true;
                    }
                    if (isError === false) {
                      await onGetProjects(usuarioSelected);
                      setIsActiveAssignProjects(true);
                      setIsCancelProjectChanges(true);
                    } else {
                      errorToast(MESSAGES.CAMPOS_OBLIGATORIOS);
                    }
                    setLoading(false);
                  }}
                >
                  Buscar
                </Button>
              </Authorization>
            </Col>
          </Row>
          <Row>
            <Col md={1}></Col>
            <Col md={10}>
              <CustomPicklist
                sourceHeader="Proyectos"
                targetHeader="Proyectos asignados"
                sourceDataList={[...projectsSource]}
                targetDataList={[...projectsTarget]}
                onChangeTarget={setProjectsAssign}
                onChangeSource={setProjects}
                textKey={"nombreProyecto"}
                idKey={"idProyecto"}
                rightButtonTextModal={
                  "¿Está seguro de asignar todos los proyectos?"
                }
                leftButtonTextModal={
                  "¿Está seguro de excluir los proyectos asignados?"
                }
                isCancelChanges={isCancelProjectChanges}
                onCanceledChanges={() => {
                  setIsCancelProjectChanges(false);
                }}
              />
            </Col>
            <Col
              md={1}
              className="d-flex align-items-end justify-content-center pb-3"
            ></Col>
          </Row>
          <Row>
            {isActiveAssignProjects && (
              <Col md={12} className="text-end">
                <Button
                  variant="red"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={() => {
                    setShowCancelUsersAssign(true);
                  }}
                >
                  Cancelar
                </Button>
                <Authorization process={"AP_USR_ADMIN"}>
                  <Button
                    variant="green"
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={async () => {
                      setLoading(true);
                      const obj = usersCatalog.filter(
                        (item) => item.idUser == usuarioSelected
                      )[0];
                      try {
                        await putData(
                          "/proyectos/guardar-usuario-proyecto",

                          {
                            idUser: obj.idUser,
                            nombre: obj.nombre,
                            rfcCorto: obj.rfcCorto,
                            administracion: obj.administracion,
                            estatus: obj.estatus,

                            proyectos: projects,
                            proyectosAsignados: projectsAssign,
                          }
                        );
                        setShowOpenSuccessModal(true);
                        setSuccessModalMessage(
                          "Se guardaron los cambios con éxito."
                        );
                        setIsActiveAssignProjects(false);
                        onCancelUsers();
                      } catch (err) {
                        onRenderErrorMessage(err);
                      }
                      setLoading(false);
                    }}
                  >
                    Guardar
                  </Button>
                </Authorization>
              </Col>
            )}
          </Row>
        </Accordion>
      </Container>
    </>
  );
};

export default AsignarProyecto;
