import { ROUTES } from "../constants/routes";
import {
  faGears,
  faAddressCard,
  faListCheck,
  faFileSignature,
  faFileInvoiceDollar,
  faFilterCircleDollar,
  faChartLine,
} from "@fortawesome/free-solid-svg-icons";
// TODO: Roles
export const MENU = [
  {
    label: "Sistema",
    path: ROUTES.sistema.menuPath,
    icon: faGears,
    menuAccess: "SISTEMA",
    subs: [
      {
        label: "Catálogos",
        path: ROUTES.sistema.menuPath + "/catalogos",
        menuAccess: "CATALOGOS",
      },
      {
        label: "Usuarios",
        path: ROUTES.sistema.menuPath + "/usuarios",
        menuAccess: "USUARIOS",
      },
      {
        label: "Asignar proyectos",
        path: ROUTES.sistema.menuPath + "/asignarProyecto",
        menuAccess: "ASIGNAR_PROYECTOS",
      },
      {
        label: "Matriz documental",
        path: ROUTES.sistema.menuPath + "/matrizDocumental",
        menuAccess: "MATRIZ_DOCUMENTAL",
      },
      {
        label: "Formatos de impresión",
        path: ROUTES.sistema.menuPath + "/plantillas",
        menuAccess: "FORMATOS_IMPRESION",
      },
      {
        label: "Pistas de auditoría",
        path: ROUTES.sistema.menuPath + "/pistasAuditoria",
        menuAccess: "PISTAS_AUDITORIA",
      },
      {
        label: "Papelera de reciclaje",
        path: ROUTES.sistema.menuPath + "/papelera",
        menuAccess: "PAPELERA_RECICLAJE",
      },
    ],
  },
  {
    label: "Proveedores",
    path: ROUTES.proveedores.menuPath + "/proveedores",
    icon: faAddressCard,
    menuAccess: "PROVEEDORES",
  },
  {
    label: "Proyectos",
    path: ROUTES.proyectos.menuPath + "/proyectos",
    icon: faListCheck,
    menuAccess: "PROYECTOS",
  },
  {
    label: "Contratos",
    path: ROUTES.contratos.menuPath + "/contratos",
    icon: faFileSignature,
    menuAccess: "CONTRATOS",
  },
  {
    label: "Consumo de Servicios",
    path: ROUTES.consumoServicios.menuPath + "/consumoServicios",
    icon: faFileInvoiceDollar,
    menuAccess: "CONSUMO_SERVICIOS",
  },
  {
    label: "Reintegros",
    path: ROUTES.reintegros.menuPath,
    icon: faFilterCircleDollar,
    menuAccess: "REINTEGROS",
  },
  {
    label: 'Reportes',
    path: ROUTES.reportes.menuPath,
    icon: faChartLine,
    menuAccess: "REPORTES",
    subs: [
      {
        label: 'Reporte de control documental',
        path: ROUTES.reportes.menuPath + '/controlDocumental',
        menuAccess: "REPORTES_CONTROL_DOCUMENTAL"
      },
      {
        label: 'Construir reportes',
        path: ROUTES.reportes.menuPath + '/construir',
        menuAccess: "CONSTRUIR_REPORTES"
      },
      {
        label: 'Reporte financiero',
        path: ROUTES.reportes.menuPath + '/financiero',
        menuAccess: "REPORTE_FINANCIERO",
        subs: [
          {
            label: 'Resumen',
            path: ROUTES.reportes.menuPath + '/financiero/resumen',
            menuAccess: "RESUMEN_FINANCIERO"
          },
          {
            label: 'Seguimiento de dictamen',
            path: ROUTES.reportes.menuPath + '/financiero/seguimientoDictamen',
            menuAccess: "SEGUIMIENTO_DICTAMEN"
          },
          {
            label: 'Facturas/ Penalizaciones/ Deducciones/ Reintegros',
            path: ROUTES.reportes.menuPath + '/financiero/facturas-penalizaciones-deducciones-reintegros',
            menuAccess: "FACT_PEN_DEDUC_REINT"
          },
          {
            label: 'Estimado / Pagado',
            path: ROUTES.reportes.menuPath + '/financiero/estimado-pagado',
            menuAccess: "ESTIMADO_PAGADO"
          },
          {
            label: 'Seguimiento por línea de servicio',
            path: ROUTES.reportes.menuPath + '/financiero/seguimientoLineaServicio',
            menuAccess: "SEGUIMIENTO_CONCEPTO_SERV"
          },
          {
            label: 'Estado financiero',
            path: ROUTES.reportes.menuPath + '/financiero/estadoFinanciero',
            menuAccess: "ESTADO_FINANCIERO"
          }
        ]
      },
      {
        label: 'Tablero de control',
        path: ROUTES.reportes.menuPath + '/tableroDeControl',
        menuAccess: "TABLERO_CONTROL"
      }
    ]
  }
];
