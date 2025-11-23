import { DateCell, LabelCell } from "../Components/CellComponents";
import {
  SelectedCellProps,
  FileCellProps,
  MoneyCellProps,
  ActionCellProps,
} from "../Components/FunctionalCells";

export function GetColumnsInfoComitesTable({
  handleShow,
  ShowMessage,
  handleEdit,
  idProyecto,
  editable = false,
  setIsLoading,
}) {
  let columns = [
    {
      accessorKey: "idContratoConvenio",
      header: "Contrato/Convenio",
      cell: LabelCell,
      filterFn: "includesString",
    },
    {
      accessorKey: "idsAfectacion",
      header: "Afectación",
      cell: SelectedCellProps,
      filterFn: "includesString",
    },
    {
      accessorKey: "idComite",
      header: "Comité",
      cell: LabelCell,
      filterFn: "includesString",
    },
    //To do: Validar fecha ---
    {
      accessorKey: "fechaSesion",
      header: "Fecha de sesión",
      cell: DateCell,
      filterFn: "includesString",
    },
    // ------
    {
      accessorKey: "infomacionArchivos",
      header: "Sesión",
      cell: (props) => FileCellProps(props, setIsLoading),
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "acuerdo",
      header: "Acuerdo",
      cell: LabelCell,
      filterFn: "includesString",
    },
    {
      accessorKey: "vigencia",
      header: "Vigencia",
      cell: LabelCell,
      filterFn: "includesString",
    },
    {
      accessorKey: "montoAutorizado",
      header: "Monto autorizado (con IVA)",
      cell: MoneyCellProps,
      filterFn: "includesString",
    },
    {
      accessorKey: "monto",
      header: "Monto MXN",
      cell: LabelCell,
      filterFn: "includesString",
    },
    {
      accessorKey: "idComiteProyecto",
      header: "Acciones",
      cell: (props) =>
        ActionCellProps({
          props,
          handleShow,
          ShowMessage,
          handleEdit,
          idProyecto,
          editable,
          setIsLoading,
        }),
      enableSorting: false,
      enableColumnFilter: false,
    },
  ];

  return columns;
}
