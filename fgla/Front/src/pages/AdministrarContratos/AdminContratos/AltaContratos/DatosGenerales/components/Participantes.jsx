import React, { useEffect, useMemo, useRef, useState } from "react";
import { TablaEditable } from "../../../../../../components/table/TablaEditable";
import { Button, Col, FormCheck, Row } from "react-bootstrap";
import { Loader } from "../../../../../../components";
import IconButton from "../../../../../../components/buttons/IconButton";
import {
  curatedModifiedParticipantes,
  curatedNewParticipantes,
  participantesValidation,
  rearrangeParticipantes,
  template,
} from "../utils";
import {
  useDeleteParticipantesContratoMutation,
  useGetAdmonCentralQuery,
  useGetAdmonGeneralQuery,
  useGetParticipantesQuery,
  useGetResponsabilidadQuery,
  useGetUsuariosQuery,
  useGetUsuariosFiltroMutation,
  useGetAdmonCentralPorGeneralMutation,
  usePostParticipantesContratoMutation,
  usePostReporteParticipantesMutation,
  usePutParticipantesContratoMutation,
} from "../store";
import { DateCell, DisplayCell, SelectCell, SelectCellMod } from "../../../../Components";
import SingleBasicModal from "../../../../../../modals/SingleBasicModal";
import BasicModal from "../../../../../../modals/BasicModal";
import { ALTA_CONTRATOS, MODIFICAR_CONTRATO } from "../../../../../../constants/messages";
import _ from "lodash";
import { useParams, useSearchParams } from "react-router-dom";
import { useToast } from "../../../../../../hooks/useToast";
import { DownloadFileBase64 } from "../../../../../../functions/utils/base64.utils";
import Authorization from "../../../../../../components/Authorization";
import { useErrorMessages } from "../../../../../../hooks/useErrorMessages";

export function Participantes({ isDetalle }) {
  const tableReference = useRef();
  const { errorToast } = useToast();
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoContrato = searchParams.get("q");
  const [dataTable, setDataTable] = useState([]);
  const [memoizedData, setMemoizedData] = useState(new Map());
  const [deletedParticipantes, setDeletedParticipantes] = useState([]);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [showUndoModal, setShowUndoModal] = useState(false);
  const [showDropModal, setShowDropModal] = useState(false);
  const [showDeactivateModal, setShowDeactivateModal] = useState(false);
  const [showInactiveModal, setShowInactiveModal] = useState(false);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const [selectedRow, setSelectedRow] = useState();


  const { data: admonGeneral, isLoading: isLoadingAdmonGeneral } =
    useGetAdmonGeneralQuery();
  const { data: admonCentral, isLoading: isLoadingAdmonCentral } =
    useGetAdmonCentralQuery();
  const { data: servidoresPublicos, isLoading: isLoadingServidores } =
    useGetUsuariosQuery();
  const { data: responsabilidad, isLoading: isLoadingResponsabilidad } =
    useGetResponsabilidadQuery();

  const { data: participantes, isLoading: isLoadingParticipantes } =
    useGetParticipantesQuery(idContrato || idNuevoContrato, {
      skip:
        idContrato !== undefined || idNuevoContrato !== undefined
          ? false
          : true,
    });
  const [guardarParticipantes, { isLoading: isLoadingGuardar }] =
    usePostParticipantesContratoMutation();
  const [actualizarParticipantes, { isLoading: isLoadingActualizar }] =
    usePutParticipantesContratoMutation();
  const [borrarParticipantes, { isLoading: isLoadingBorrarParticipantes }] =
    useDeleteParticipantesContratoMutation();
  const [descargarReporte, { isLoading: isLoadingDescargar }] =
    usePostReporteParticipantesMutation();

  useEffect(() => {
    if (participantes) {
      const previousParticipantes = rearrangeParticipantes(participantes);
      setDataTable(previousParticipantes);
      setMemoizedData(
        new Map(
          previousParticipantes.map((participante) => [
            participante.UUID,
            participante,
          ])
        )
      );
      setDeletedParticipantes([]);
      tableReference?.current?.table.toggleAllRowsSelected(false);
    }
  }, [participantes]);


  const onDescargarReporte = async () => {
    try {
      const res = await descargarReporte(
        idContrato || idNuevoContrato
      ).unwrap();
      DownloadFileBase64(
        res,
        "Reporte.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasic(true);
      }
    }
  };

  const onAddRow = () => {
    const newRow = template();
    setDataTable((prev) => [...prev, newRow]);
    setMemoizedData((map) => new Map(map.set(newRow.UUID, newRow)));
  };
  const onReset = () => {
    const newData = [...memoizedData?.values()];
    const resetData = newData.reduce((prev, current) => {
      if (current.isNewRow) return prev;
      prev.push(current);
      return prev;
    }, []);
    setDataTable(resetData);
    setDeletedParticipantes(() => []);
    tableReference?.current?.table.toggleAllRowsSelected(false);
  };
  const onSubmit = async () => {
    try {
      if (dataTable.length <= 0 && deletedParticipantes.length <= 0) {
        errorToast("No hay campos en la tabla para modificar.");
        return;
      }
      const newParticipantes = [];
      const modifiedParticipantes = [];

      const participantesErrors = await participantesValidation(dataTable);
      if (participantesErrors?.dataErrors) {
        errorToast(MODIFICAR_CONTRATO.MSG001);
        setDataTable(participantesErrors.resultados);
        return;
      }

      dataTable.forEach((row) => {
        if (row.isNewRow) {
          newParticipantes.push(row);
          return;
        }
        const memoizedRow = memoizedData.get(row.UUID);
        if (!_.isEqual(row, memoizedRow)) {
          modifiedParticipantes.push(row);
          return;
        }
      });
      if (deletedParticipantes.length > 0) {
        const data = {
          idContrato: parseInt(idContrato || idNuevoContrato),
          idsParticipantes: [...deletedParticipantes],
        };
        await borrarParticipantes({ data }).unwrap();
      }
      if (newParticipantes.length > 0) {
        const data = curatedNewParticipantes(
          newParticipantes,
          idContrato || idNuevoContrato
        );
        await guardarParticipantes({ data }).unwrap();
      }
      if (modifiedParticipantes.length > 0) {
        const data = curatedModifiedParticipantes(
          modifiedParticipantes,
          idContrato || idNuevoContrato
        );
        await actualizarParticipantes({
          data,
        }).unwrap();
      }
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
      setShowSingleBasic(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage(ALTA_CONTRATOS.MSG0010);
        setShowSingleBasic(true);
      }
    }
  };


  const [usuariosPorFila, setUsuariosPorFila] = useState({});
  const [triggerUsuarios] = useGetUsuariosFiltroMutation();

  const actualizarUsuariosFila = async (
    idResponsabilidad,
    idGeneral,
    idCentral,
    row
  ) => {
    try {
      const filtros = {
        idResponsabilidad:
          idResponsabilidad !== null && idResponsabilidad !== undefined
            ? parseInt(idResponsabilidad)
            : row.original.responsabilidad
              ? parseInt(row.original.responsabilidad)
              : null,

        idGeneral:
          idGeneral !== null && idGeneral !== undefined
            ? parseInt(idGeneral)
            : row.original.administracionGeneral
              ? parseInt(row.original.administracionGeneral)
              : null,

        idCentral:
          idCentral !== null && idCentral !== undefined
            ? parseInt(idCentral)
            : row.original.administracionCentral
              ? parseInt(row.original.administracionCentral)
              : null,
      };

      if (filtros.idResponsabilidad || filtros.idGeneral || filtros.idCentral) {
        const usuarios = await triggerUsuarios(filtros).unwrap();

        setUsuariosPorFila((prev) => ({
          ...prev,
          [row.original.UUID]: usuarios,
        }));
      }
    } catch (err) {
      console.error("Error actualizando usuarios para la fila", err);
    }
  };

  const [centralesPorFila, setCentralesPorFila] = useState({});
  const [triggerCentrales] = useGetAdmonCentralPorGeneralMutation();

  const actualizarCentralesFila = async (idGeneral, row) => {
    try {
      if (!idGeneral) return;

      const centrales = await triggerCentrales(idGeneral).unwrap();

      setCentralesPorFila((prev) => ({
        ...prev,
        [row.original.UUID]: centrales,
      }));
    } catch (err) {
      console.error("Error actualizando centrales para la fila", err);
    }
  };



  const columns = useMemo(
    () => [
      {
        accessorKey: "id",
        header: "Id",
        cell: (props) => <> {props.row.index + 1}</>,
        enableColumnFilter: false,
      },
      {
        accessorKey: "responsabilidad",
        header: "Responsabilidad",
        cell: (props) => (
          <SelectCell
            {...props}
            name="responsabilidad"
            keyValue="primaryKey"
            keyTextValue="nombre"
            options={responsabilidad}
            displayValue="responsabilidadDisplay"
            disabled={isDetalle}
            onChangeExtra={(newRow) =>
              actualizarUsuariosFila(
                newRow.responsabilidad,
                null,
                null,
                props.row
              )
            }
          />
        ),
        filterFn: (row, columnId, filterValue) =>
          row?.original?.responsabilidadDisplay
            ?.toLowerCase()
            ?.trim()
            ?.includes(filterValue.toLowerCase()),
      }
      ,

      {
        accessorKey: "administracionGeneral",
        header: "Administración general",
        cell: (props) => (
          <SelectCell
            {...props}
            name="administracionGeneral"
            keyValue="primaryKey"
            keyTextValue="acronimo"
            options={admonGeneral}
            displayValue="administracionGeneralDisplay"
            disabled={isDetalle}
            onChangeExtra={(newRow) => {
              const rowData = props.row.original;
              const uuid = rowData.UUID;

              // Buscar el acrónimo del general seleccionado
              const generalSeleccionado = admonGeneral?.find(
                (g) => g.primaryKey === newRow.administracionGeneral
              );

              actualizarCentralesFila(newRow.administracionGeneral, props.row);

              props.table.options.meta.updateRowById(uuid, {
                ...rowData,
                administracionGeneral: newRow.administracionGeneral,
                administracionGeneralDisplay: generalSeleccionado?.acronimo || "",
                administracionCentral: null,
                administracionCentralDisplay: "",
                nombreServidorPublico: null,
                nivel: null,
                usuarioInformacion: null,
                nombreServidorPublicoDisplay: "",
                telefono: "",
                correo: "",
              });

              actualizarUsuariosFila(null, newRow.administracionGeneral, null, props.row);
            }}

          />
        ),
        filterFn: (row, columnId, filterValue) =>
          row?.original?.administracionGeneralDisplay
            ?.toLowerCase()
            ?.trim()
            ?.includes(filterValue.toLowerCase()),
      }
      ,

      {
        accessorKey: "administracionCentral",
        header: "Administración central",
        cell: (props) => {
          if (
            props.row.original?.responsabilidadDisplay === "Administrador General" &&
            props.row.original?.administracionGeneralDisplay === "AGCTI"
          ) {
            return null;
          }

          const centralesFila =
            centralesPorFila[props.row.original.UUID] || admonCentral;

          return (
            <SelectCell
              {...props}
              name="administracionCentral"
              keyValue="primaryKey"
              keyTextValue="acronimo"
              options={centralesFila}
              displayValue="administracionCentralDisplay"
              disabled={isDetalle}
              onChangeExtra={(newRow) =>
                actualizarUsuariosFila(
                  null,
                  null,
                  newRow.administracionCentral,
                  props.row
                )
              }
            />
          );
        },
        filterFn: (row, columnId, filterValue) =>
          row?.original?.administracionCentralDisplay
            ?.toLowerCase()
            ?.trim()
            ?.includes(filterValue.toLowerCase()),
      }
      ,
      {
        accessorKey: "nombreServidorPublico",
        header: "Nombre del servidor público",
        cell: (props) => {
          const usuariosFila =
            usuariosPorFila[props.row.original.UUID] || servidoresPublicos;

          return (
            <SelectCellMod
              z
              column={props.column}
              getValue={() =>
                props.row.original?.nombreServidorPublico && props.row.original?.nivel
                  ? `${Number(props.row.original.nombreServidorPublico)}-${Number(props.row.original.nivel)}`
                  : ""
              }

              name={"nombreServidorPublico"}
              row={props.row}
              table={props.table}
              options={usuariosFila}
              //keyValue={(opt) => `${opt.idUsuario}-${opt.nivel}`}
              keyValue={(opt) => `${Number(opt.idUsuario)}-${Number(opt.nivel)}`}

              keyTextValue={"nombre"}
              displayValue={"nombreServidorPublicoDisplay"}
              disabled={isDetalle}
              onChangeExtra={(newRow) => {
                // Actualiza los valores de nombre, nivel y display al seleccionar un usuario
                props.table.options.meta.updateRowById(
                  newRow.UUID,
                  newRow
                );
              }}
            />
          );
        },
      },


      {
        accessorKey: "telefono",
        header: "Teléfono",
        cell: (props) => {
          const idUsuario = parseInt(props.row.getValue("nombreServidorPublico"));
          const nivel = props.row.original?.nivel;

          const usuario = servidoresPublicos?.find(
            (user) => user.idUsuario === idUsuario && user.nivel === nivel
          );

          const telefono = usuario
            ? usuario.telefono?.trim() || ""
            : props.row.original?.telefono?.trim() || "";

          return <>{telefono}</>;
        },
      },

      {
        accessorKey: "correo",
        header: "Correo",
        cell: (props) => {
          const idUsuario = parseInt(props.row.getValue("nombreServidorPublico"));
          const nivel = props.row.original?.nivel;

          const usuario = servidoresPublicos?.find(
            (user) => user.idUsuario === idUsuario && user.nivel === nivel
          );

          const correo = usuario
            ? usuario.correo?.trim() || ""
            : props.row.original?.correo?.trim() || "";

          return <>{correo}</>;
        },
      },


      {
        accessorKey: "fechaInicio",
        header: "Fecha de Inicio",
        cell: (props) => (
          <>
            <DateCell
              column={props.column}
              getValue={props.getValue}
              name={"fechaInicio"}
              row={props.row}
              table={props.table}
              disabled={isDetalle}
              bias={"start"}
            />
          </>
        ),
      },
      {
        accessorKey: "fechaTermino",
        header: "Fecha de término",
        cell: (props) => (
          <>
            {!props.row.original.vigente ? (
              <DateCell
                column={props.column}
                getValue={props.getValue}
                name={"fechaTermino"}
                row={props.row}
                table={props.table}
                disabled={isDetalle}
                bias={"end"}
              />
            ) : null}
          </>
        ),
      },
      {
        accessorKey: "vigente",
        header: "Vigente",
        cell: (props) => (
          <>
            <FormCheck
              type="switch"
              checked={props.getValue()}
              name={`vigente${props.row.id}`}
              onChange={(e) => {
                if (props.getValue()) {
                  setSelectedRow({
                    ...props.row,
                    UUID: props.row.original.UUID,
                  });
                  setShowDeactivateModal(true);
                } else {
                  props.table.options.meta.updateData(
                    props.row.index,
                    props.column.id,
                    !props.getValue()
                  );
                }
              }}
              disabled={
                (!props.row.getIsSelected() && !props.row.original.isNewRow) ||
                isDetalle
              }
            />
          </>
        ),
        enableColumnFilter: false,
        enableSorting: false,
      },
      {
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) => (
          <>
            <>
              {props.row.getIsSelected() || props.row.original.isNewRow ? (
                <IconButton
                  type={"undo"}
                  iconSize="lg"
                  tooltip={"Cancelar"}
                  onClick={() => {
                    setSelectedRow({
                      ...props.row,
                      UUID: props.row.original.UUID,
                    });
                    setShowUndoModal(true);
                  }}
                  disabled={isDetalle}
                />
              ) : (
                <>
                  <Authorization process={"CONT_DG_ADMIN"}>
                    <IconButton
                      type={"edit"}
                      iconSize="lg"
                      tooltip={"Editar"}
                      onClick={() => {
                        props.row.toggleSelected(!props.row.getIsSelected(), {
                          selectChildren: false,
                        });

                        const original = props.row.original;
                        actualizarUsuariosFila(
                          original.responsabilidad,
                          original.administracionGeneral,
                          original.administracionCentral,
                          props.row
                        );

                        if (original.administracionGeneral) {
                          actualizarCentralesFila(original.administracionGeneral, props.row);
                        }
                      }}
                      disabled={isDetalle}
                    />

                    <IconButton
                      type={"drop"}
                      iconSize="lg"
                      tooltip={"Eliminar"}
                      onClick={() => {
                        setSelectedRow({
                          ...props.row,
                          UUID: props.row.original.UUID,
                          idParticipantesAdministracionContrato:
                            props.row.original
                              .idParticipantesAdministracionContrato,
                        });
                        setShowDropModal(true);
                      }}
                      disabled={isDetalle}
                    />
                  </Authorization>
                </>
              )}
            </>
          </>
        ),
        enableColumnFilter: false,
        enableSorting: false,
      },
    ],
    [servidoresPublicos, admonCentral, admonGeneral, responsabilidad, usuariosPorFila, actualizarUsuariosFila]
  );
  return (
    <>
      {isLoadingAdmonGeneral ||
        isLoadingAdmonCentral ||
        isLoadingServidores ||
        isLoadingResponsabilidad ||
        isLoadingGuardar ||
        isLoadingActualizar ||
        isLoadingBorrarParticipantes ||
        isLoadingParticipantes ||
        isLoadingDescargar ? (
        <Loader zIndex={`${isLoadingParticipantes ? "10" : "9999"}`} />
      ) : null}
      <Row>
        <Col md={12} className={"text-end mb-2 mt-4"}>
          <Authorization process={"CONT_DG_ADMIN"}>
            <IconButton
              type={"add"}
              onClick={onAddRow}
              disabled={isDetalle}
              tooltip={"Agregar"}
            />
          </Authorization>
          <IconButton
            type={"excel"}
            onClick={() => onDescargarReporte()}
            disabled={participantes?.length <= 0}
            tooltip={"Exportar a excel"}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12}>
          <TablaEditable
            header={"Participantes en la administración del contrato"}
            ref={tableReference}
            dataTable={dataTable}
            columns={columns}
            hasPagination
            onUpdate={setDataTable}
            onDelete={setDataTable}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12} className={"text-end"}>
          <Authorization process={"CONT_DG_ADMIN"}>
            {!isDetalle ? (
              <>
                {" "}
                <Button
                  variant="red"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={() => {
                    setShowCancelModal(true);
                  }}
                >
                  Cancelar
                </Button>
                <Button
                  variant="green"
                  className="btn-sm ms-2 waves-effect waves-light"
                  type="submit"
                  onClick={onSubmit}
                >
                  Guardar
                </Button>
              </>
            ) : null}
          </Authorization>
        </Col>
      </Row>
      <SingleBasicModal
        show={showSingleBasic}
        title={"Mensaje"}
        size={"md"}
        approveText={"Aceptar"}
        onHide={() => setShowSingleBasic(false)}
      >
        {singleBasicMessage}
      </SingleBasicModal>
      <BasicModal
        show={showCancelModal}
        approveText={"Sí"}
        denyText={"No"}
        size={"md"}
        onHide={() => setShowCancelModal(false)}
        title={"Mensaje"}
        handleApprove={() => {
          onReset();
          setShowCancelModal(false);
        }}
        handleDeny={() => setShowCancelModal(false)}
      >
        {MODIFICAR_CONTRATO.MSG002}
      </BasicModal>
      <BasicModal
        show={showUndoModal}
        approveText={"Sí"}
        denyText={"No"}
        size={"md"}
        onHide={() => setShowUndoModal(false)}
        title={"Mensaje"}
        handleApprove={() => {
          const memoizedRow = memoizedData.get(selectedRow.UUID);
          console.log(memoizedRow);
          if (memoizedRow.isNewRow) {
            tableReference.current.table.options.meta.removeRowById(
              selectedRow.UUID
            );
            selectedRow.toggleSelected(false);
          } else {
            tableReference.current.table.options.meta.updateRowById(
              selectedRow.UUID,
              memoizedRow
            );
            selectedRow.toggleSelected(false);
          }
          setShowUndoModal(false);
        }}
        handleDeny={() => setShowUndoModal(false)}
      >
        {MODIFICAR_CONTRATO.MSG002}
      </BasicModal>
      <BasicModal
        show={showInactiveModal}
        approveText={"Sí"}
        denyText={"No"}
        size={"md"}
        onHide={() => setShowInactiveModal(false)}
        title={"Mensaje"}
        handleApprove={() => {
          setShowInactiveModal(false);
        }}
        handleDeny={() => setShowInactiveModal(false)}
      >
        {MODIFICAR_CONTRATO.MSG008}
      </BasicModal>
      <BasicModal
        show={showDropModal}
        approveText={"Sí"}
        denyText={"No"}
        size={"md"}
        onHide={() => setShowDropModal(false)}
        title={"Mensaje"}
        handleApprove={() => {
          console.log(selectedRow);
          setDeletedParticipantes((prev) => [
            ...prev,
            selectedRow.idParticipantesAdministracionContrato,
          ]);
          tableReference.current.table.options.meta.removeRowById(
            selectedRow.UUID
          );
          selectedRow.toggleSelected(false);
          setShowDropModal(false);
        }}
        handleDeny={() => setShowDropModal(false)}
      >
        {MODIFICAR_CONTRATO.MSG017}
      </BasicModal>
      <BasicModal
        show={showDeactivateModal}
        approveText={"Sí"}
        denyText={"No"}
        size={"md"}
        onHide={() => setShowDeactivateModal(false)}
        title={"Mensaje"}
        handleApprove={() => {
          console.log(selectedRow);
          tableReference.current.table.options.meta.updateData(
            selectedRow.index,
            "vigente",
            !selectedRow.original.vigente
          );
          setShowDeactivateModal(false);
        }}
        handleDeny={() => setShowDeactivateModal(false)}
      >
        {MODIFICAR_CONTRATO.MSG008}
      </BasicModal>
    </>
  );
}
