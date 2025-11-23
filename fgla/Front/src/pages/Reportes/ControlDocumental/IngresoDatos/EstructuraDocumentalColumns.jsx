import { LabelCell } from "../components/CellComponents";
import {
  CheckCellProps,
  NoApplyCellProps,
  CellStatusProps,
  JustifyCell,
  ActionDocsCellProps,
  ExpandibleCellProps,
} from "../components/FunctionalCellsEstructutraDocumental";

export function GetColumns(
  documentList,
  idComiteProyecto,
  disableForm,
  GetDocumentById,
  UpdateDocumentList,
  DeleteDocument,
  handleShowMessage,
  handeShowPdf,
  handleSatCloud,
  InfoComites
) {
  let columns = [
    {
      accessorKey:  "descripcion",
      // accessorKey: "nombre",
      header: "Descripción",
      cell: (props) =>
        ExpandibleCellProps(
          props,
          disableForm,
          GetDocumentById,
          UpdateDocumentList,
          documentList,
          handleShowMessage
        ),
      footer: (props) => props.column.id,
    },
    {
      accessorKey: "obligatorio",
      header: "Requerido",
      cell: (props) => CheckCellProps(props, disableForm, true),
      enableColumnFilter: false,
      enableSorting: false,
      footer: (props) => props.column.id,
    },
    {
      accessorKey: "noAplica",
      header: "No aplica",
      cell: (props) =>
        NoApplyCellProps(
          props,
          disableForm,
          false,
          GetDocumentById,
          UpdateDocumentList
        ),
      enableColumnFilter: false,
      enableSorting: false,
      footer: (props) => props.column.id,
    },
    {
      accessorKey: "status",
      header: "Estatus",
      cell: (props) => CellStatusProps(props, GetDocumentById),
      enableColumnFilter: false,
      enableSorting: false,
      footer: (props) => props.column.id,
    },
    {
      accessorKey: "justificacion",
      header: "Justificación",
      cell: (props) =>
        JustifyCell(
          props,
          disableForm,
          false,
          GetDocumentById,
          UpdateDocumentList
        ),
      enableColumnFilter: false,
      enableSorting: false,
      footer: (props) => props.column.id,
    },
    {
      accessorKey: "size",
      header: "Tamaño",
      cell: LabelCell,
      enableColumnFilter: false,
      enableSorting: false,
      footer: (props) => props.column.id,
    },
    {
      accessorKey: "date",
      header: "Fecha última modificación",
      cell: LabelCell, //(props) => CellFileDate(props, "date", documentList),
      footer: (props) => props.column.id,
    },
  ];

  let cellActions = {
    accessorKey: "idArchivoPlantilla",
    header: "Acciones",
    cell: (props) =>
      ActionDocsCellProps(
        props,
        disableForm,
        GetDocumentById,
        UpdateDocumentList,
        DeleteDocument,
        handleShowMessage,
        handeShowPdf,
        handleSatCloud,
        InfoComites
      ),
    enableColumnFilter: false,
    enableSorting: false,
    footer: (props) => props.column.id,
  };
  if (true || !disableForm) {
    columns.push(cellActions);
  }

  return columns;
}
