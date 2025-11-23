/**Reglas basadas en  mpli SIISEF */
const lasReglas = {
    SAT_EF_ADMIN_GEN_EF: {
        dynamic: {},
        static: {
            PSAT_EF001: {
                descripcion: "ADMINISTRACIÓN DEL SISTEMA",
                puedo: 1
            },
            PSAT_EF002: {
                descripcion: "ADMINISTRACIÓN TRÁMITE DE CUENTAS",
                puedo: 1
            },
            PSAT_EF003: {
                descripcion: "ADMINISTRACIÓN DE CATALOGOS",
                puedo: 1
            },
            PSAT_EF004: {
                descripcion: "ADMINISTRACIÓN DE PLANTILLAS",
                puedo: 1
            },
            PSAT_EF005: {
                descripcion: "ADMINSITRACION DE OPCIONES AVANZADAS",
                puedo: 1
            },
            PSAT_EF006: {
                descripcion: "ASIGNACIÓN DE CARGAS DE TRABAJO",
                puedo: 1
            },
            PSAT_EF007: {
                descripcion: "BLOQUEO DE USUARIOS",
                puedo: 1
            },
            PSAT_EF008: {
                descripcion: "CARGA DE DOCUMENTOS",
                puedo: 1
            },
            PSAT_EF009: {
                descripcion: "CONSULTA DE BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF010: {
                descripcion: "CONSULTA DE COMUNICADOS DE TRAMITE DE CUENTAS",
                puedo: 1
            },
            PSAT_EF011: {
                descripcion: "CONSULTA DE PISTAS DE AUDITORIA DE TRAMITES DE CUENTA",
                puedo: 1
            },
            PSAT_EF012: {
                descripcion: "CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 1
            },
            PSAT_EF013: {
                descripcion: "CONSULTA DE USUARIOS",
                puedo: 1
            },
            PSAT_EF014: {
                descripcion: "CONSULTA DE USUARIOS BLOQUEADOS",
                puedo: 1
            },
            PSAT_EF015: {
                descripcion: "CONSULTA DE VALIDACION DE USUARIOS",
                puedo: 1
            },
            PSAT_EF016: {
                descripcion: "CREACIÓN DE COMUNICADOS DE TRÁMITES DE CUENTA",
                puedo: 1
            },
            PSAT_EF017: {
                descripcion: "SEGUIMIENTO A SOLICITUD",
                puedo: 1
            },
            PSAT_EF018: {
                descripcion: "GENERAR PLANTILLAS",
                puedo: 1
            },
            PSAT_EF019: {
                descripcion: "GENERAR REPORTES AGR",
                puedo: 1
            },
            PSAT_EP020: {
                descripcion: "GESTIÓN DE SOLICITUD DE CUENTA DE ACCESO",
                puedo: 1
            },
            PSAT_EF021: {
                descripcion: "GESTION DE SOLICITUD DE CUENTAS Y ROLES",
                puedo: 1
            },
            PSAT_EF022: {
                descripcion: "GESTION DE SUSPENSION DE NUEVOS TRAMITES DE CUENTA",
                puedo: 1
            },
            PSAT_EF023: {
                descripcion: "REGISTRAR BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF024: {
                descripcion: "REGISTRAR USUARIO",
                puedo: 1
            },
            PSAT_EF025: {
                descripcion: "REPORTE CUENTAS ACTIVAS POR USUARIO",
                puedo: 1
            },
            PSAT_EF026: {
                descripcion: "REPORTE ESTADISTICO DE TRAMITES",
                puedo: 1
            },
            PSAT_EF027: {
                descripcion: "REPORTE TOTAL DE CUENTAS ACTIVAS",
                puedo: 1
            },
            PSAT_EF028: {
                descripcion: "REPORTE TOTAL DE SOLICITUDES",
                puedo: 1
            },
            PSAT_EF029: {
                descripcion: "REPORTE USUARIOS POR ROL",
                puedo: 1
            },
            PSAT_EF030: {
                descripcion: "SUBIR DOCUMENTACIÓN",
                puedo: 1
            },
            PSAT_EF031: {
                descripcion: "VALIDAR USUARIOS",
                puedo: 1
            },
            PSAT_EF032: {
                descripcion: "VER DETALLE DE BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF033: {
                descripcion: "VER DETALLE DE COMUNICADOS DE TRAMITES DE CUENTAS",
                puedo: 1
            },
            PSAT_EF040: {
                descripcion: "EXPORTAR CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 1
            }
        }
    },
  /*  SAT_EF_CORRDINADOR: {
       
        dynamic: {},
        static: {
            PSAT_EF001: {
                descripcion: "ADMINISTRACIÓN DEL SISTEMA",
                puedo: 0
            },
            PSAT_EF002: {
                descripcion: "ADMINISTRACIÓN TRÁMITE DE CUENTAS",
                puedo: 0
            },
            PSAT_EF003: {
                descripcion: "ADMINISTRACIÓN DE CATALOGOS",
                puedo: 0
            },
            PSAT_EF004: {
                descripcion: "ADMINISTRACIÓN DE PLANTILLAS",
                puedo: 0
            },
            PSAT_EF005: {
                descripcion: "ADMINSITRACION DE OPCIONES AVANZADAS",
                puedo: 0
            },
            PSAT_EF006: {
                descripcion: "ASIGNACIÓN DE CARGAS DE TRABAJO",
                puedo: 0
            },
            PSAT_EF007: {
                descripcion: "BLOQUEO DE USUARIOS",
                puedo: 0
            },
            PSAT_EF008: {
                descripcion: "CARGA DE DOCUMENTOS",
                puedo: 0
            },
            PSAT_EF009: {
                descripcion: "CONSULTA DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF010: {
                descripcion: "CONSULTA DE COMUNICADOS DE TRAMITE DE CUENTAS",
                puedo: 0
            },
            PSAT_EF011: {
                descripcion: "CONSULTA DE PISTAS DE AUDITORIA DE TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF012: {
                descripcion: "CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF013: {
                descripcion: "CONSULTA DE USUARIOS",
                puedo: 0
            },
            PSAT_EF014: {
                descripcion: "CONSULTA DE USUARIOS BLOQUEADOS",
                puedo: 0
            },
            PSAT_EF015: {
                descripcion: "CONSULTA DE VALIDACION DE USUARIOS",
                puedo: 0
            },
            PSAT_EF016: {
                descripcion: "CREACIÓN DE COMUNICADOS DE TRÁMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF017: {
                descripcion: "SEGUIMIENTO A SOLICITUD",
                puedo: 0
            },
            PSAT_EF018: {
                descripcion: "GENERAR PLANTILLAS",
                puedo: 0
            },
            PSAT_EF019: {
                descripcion: "GENERAR REPORTES AGR",
                puedo: 0
            },
            PSAT_EP020: {
                descripcion: "GESTIÓN DE SOLICITUD DE CUENTA DE ACCESO",
                puedo: 1
            },
            PSAT_EF021: {
                descripcion: "GESTION DE SOLICITUD DE CUENTAS Y ROLES",
                puedo: 1
            },
            PSAT_EF022: {
                descripcion: "GESTION DE SUSPENSION DE NUEVOS TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF023: {
                descripcion: "REGISTRAR BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF024: {
                descripcion: "REGISTRAR USUARIO",
                puedo: 0
            },
            PSAT_EF025: {
                descripcion: "REPORTE CUENTAS ACTIVAS POR USUARIO",
                puedo: 0
            },
            PSAT_EF026: {
                descripcion: "REPORTE ESTADISTICO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF027: {
                descripcion: "REPORTE TOTAL DE CUENTAS ACTIVAS",
                puedo: 0
            },
            PSAT_EF028: {
                descripcion: "REPORTE TOTAL DE SOLICITUDES",
                puedo: 0
            },
            PSAT_EF029: {
                descripcion: "REPORTE USUARIOS POR ROL",
                puedo: 0
            },
            PSAT_EF030: {
                descripcion: "SUBIR DOCUMENTACIÓN",
                puedo: 0
            },
            PSAT_EF031: {
                descripcion: "VALIDAR USUARIOS",
                puedo: 0
            },
            PSAT_EF032: {
                descripcion: "VER DETALLE DE BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF033: {
                descripcion: "VER DETALLE DE COMUNICADOS DE TRAMITES DE CUENTAS",
                puedo: 0
            }
        }


    },
    SAT_EF_PARTICIPANTE: {
        
        dynamic: {},
        static: {
            PSAT_EF001: {
                descripcion: "ADMINISTRACIÓN DEL SISTEMA",
                puedo: 0
            },
            PSAT_EF002: {
                descripcion: "ADMINISTRACIÓN TRÁMITE DE CUENTAS",
                puedo: 0
            },
            PSAT_EF003: {
                descripcion: "ADMINISTRACIÓN DE CATALOGOS",
                puedo: 0
            },
            PSAT_EF004: {
                descripcion: "ADMINISTRACIÓN DE PLANTILLAS",
                puedo: 0
            },
            PSAT_EF005: {
                descripcion: "ADMINSITRACION DE OPCIONES AVANZADAS",
                puedo: 0
            },
            PSAT_EF006: {
                descripcion: "ASIGNACIÓN DE CARGAS DE TRABAJO",
                puedo: 0
            },
            PSAT_EF007: {
                descripcion: "BLOQUEO DE USUARIOS",
                puedo: 0
            },
            PSAT_EF008: {
                descripcion: "CARGA DE DOCUMENTOS",
                puedo: 0
            },
            PSAT_EF009: {
                descripcion: "CONSULTA DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF010: {
                descripcion: "CONSULTA DE COMUNICADOS DE TRAMITE DE CUENTAS",
                puedo: 0
            },
            PSAT_EF011: {
                descripcion: "CONSULTA DE PISTAS DE AUDITORIA DE TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF012: {
                descripcion: "CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF013: {
                descripcion: "CONSULTA DE USUARIOS",
                puedo: 0
            },
            PSAT_EF014: {
                descripcion: "CONSULTA DE USUARIOS BLOQUEADOS",
                puedo: 0
            },
            PSAT_EF015: {
                descripcion: "CONSULTA DE VALIDACION DE USUARIOS",
                puedo: 0
            },
            PSAT_EF016: {
                descripcion: "CREACIÓN DE COMUNICADOS DE TRÁMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF017: {
                descripcion: "SEGUIMIENTO A SOLICITUD",
                puedo: 0
            },
            PSAT_EF018: {
                descripcion: "GENERAR PLANTILLAS",
                puedo: 0
            },
            PSAT_EF019: {
                descripcion: "GENERAR REPORTES AGR",
                puedo: 0
            },
            PSAT_EP020: {
                descripcion: "GESTIÓN DE SOLICITUD DE CUENTA DE ACCESO",
                puedo: 0
            },
            PSAT_EF021: {
                descripcion: "GESTION DE SOLICITUD DE CUENTAS Y ROLES",
                puedo: 0
            },
            PSAT_EF022: {
                descripcion: "GESTION DE SUSPENSION DE NUEVOS TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF023: {
                descripcion: "REGISTRAR BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF024: {
                descripcion: "REGISTRAR USUARIO",
                puedo: 0
            },
            PSAT_EF025: {
                descripcion: "REPORTE CUENTAS ACTIVAS POR USUARIO",
                puedo: 0
            },
            PSAT_EF026: {
                descripcion: "REPORTE ESTADISTICO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF027: {
                descripcion: "REPORTE TOTAL DE CUENTAS ACTIVAS",
                puedo: 0
            },
            PSAT_EF028: {
                descripcion: "REPORTE TOTAL DE SOLICITUDES",
                puedo: 0
            },
            PSAT_EF029: {
                descripcion: "REPORTE USUARIOS POR ROL",
                puedo: 0
            },
            PSAT_EF030: {
                descripcion: "SUBIR DOCUMENTACIÓN",
                puedo: 0
            },
            PSAT_EF031: {
                descripcion: "VALIDAR USUARIOS",
                puedo: 0
            },
            PSAT_EF032: {
                descripcion: "VER DETALLE DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF033: {
                descripcion: "VER DETALLE DE COMUNICADOS DE TRAMITES DE CUENTAS",
                puedo: 0
            }
        }


    },
    SAT_EF_OBSERVADOR: {
        
        dynamic: {},
        static: {
            PSAT_EF001: {
                descripcion: "ADMINISTRACIÓN DEL SISTEMA",
                puedo: 0
            },
            PSAT_EF002: {
                descripcion: "ADMINISTRACIÓN TRÁMITE DE CUENTAS",
                puedo: 0
            },
            PSAT_EF003: {
                descripcion: "ADMINISTRACIÓN DE CATALOGOS",
                puedo: 0
            },
            PSAT_EF004: {
                descripcion: "ADMINISTRACIÓN DE PLANTILLAS",
                puedo: 0
            },
            PSAT_EF005: {
                descripcion: "ADMINSITRACION DE OPCIONES AVANZADAS",
                puedo: 0
            },
            PSAT_EF006: {
                descripcion: "ASIGNACIÓN DE CARGAS DE TRABAJO",
                puedo: 0
            },
            PSAT_EF007: {
                descripcion: "BLOQUEO DE USUARIOS",
                puedo: 0
            },
            PSAT_EF008: {
                descripcion: "CARGA DE DOCUMENTOS",
                puedo: 0
            },
            PSAT_EF009: {
                descripcion: "CONSULTA DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF010: {
                descripcion: "CONSULTA DE COMUNICADOS DE TRAMITE DE CUENTAS",
                puedo: 0
            },
            PSAT_EF011: {
                descripcion: "CONSULTA DE PISTAS DE AUDITORIA DE TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF012: {
                descripcion: "CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF013: {
                descripcion: "CONSULTA DE USUARIOS",
                puedo: 0
            },
            PSAT_EF014: {
                descripcion: "CONSULTA DE USUARIOS BLOQUEADOS",
                puedo: 0
            },
            PSAT_EF015: {
                descripcion: "CONSULTA DE VALIDACION DE USUARIOS",
                puedo: 0
            },
            PSAT_EF016: {
                descripcion: "CREACIÓN DE COMUNICADOS DE TRÁMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF017: {
                descripcion: "SEGUIMIENTO A SOLICITUD",
                puedo: 0
            },
            PSAT_EF018: {
                descripcion: "GENERAR PLANTILLAS",
                puedo: 0
            },
            PSAT_EF019: {
                descripcion: "GENERAR REPORTES AGR",
                puedo: 0
            },
            PSAT_EP020: {
                descripcion: "GESTIÓN DE SOLICITUD DE CUENTA DE ACCESO",
                puedo: 0
            },
            PSAT_EF021: {
                descripcion: "GESTION DE SOLICITUD DE CUENTAS Y ROLES",
                puedo: 0
            },
            PSAT_EF022: {
                descripcion: "GESTION DE SUSPENSION DE NUEVOS TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF023: {
                descripcion: "REGISTRAR BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF024: {
                descripcion: "REGISTRAR USUARIO",
                puedo: 0
            },
            PSAT_EF025: {
                descripcion: "REPORTE CUENTAS ACTIVAS POR USUARIO",
                puedo: 0
            },
            PSAT_EF026: {
                descripcion: "REPORTE ESTADISTICO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF027: {
                descripcion: "REPORTE TOTAL DE CUENTAS ACTIVAS",
                puedo: 0
            },
            PSAT_EF028: {
                descripcion: "REPORTE TOTAL DE SOLICITUDES",
                puedo: 0
            },
            PSAT_EF029: {
                descripcion: "REPORTE USUARIOS POR ROL",
                puedo: 0
            },
            PSAT_EF030: {
                descripcion: "SUBIR DOCUMENTACIÓN",
                puedo: 0
            },
            PSAT_EF031: {
                descripcion: "VALIDAR USUARIOS",
                puedo: 0
            },
            PSAT_EF032: {
                descripcion: "VER DETALLE DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF033: {
                descripcion: "VER DETALLE DE COMUNICADOS DE TRAMITES DE CUENTAS",
                puedo: 0
            }
        }

    },*/
    SAT_EF_SUPERVISOR: {
        dynamic: {},
        static: {
            PSAT_EF001: {
                descripcion: "ADMINISTRACIÓN DEL SISTEMA",
                puedo: 0
            },
            PSAT_EF002: {
                descripcion: "ADMINISTRACIÓN TRÁMITE DE CUENTAS",
                puedo: 1
            },
            PSAT_EF003: {
                descripcion: "ADMINISTRACIÓN DE CATALOGOS",
                puedo: 0
            },
            PSAT_EF004: {
                descripcion: "ADMINISTRACIÓN DE PLANTILLAS",
                puedo: 0
            },
            PSAT_EF005: {
                descripcion: "ADMINSITRACION DE OPCIONES AVANZADAS",
                puedo: 1
            },
            PSAT_EF006: {
                descripcion: "ASIGNACIÓN DE CARGAS DE TRABAJO",
                puedo: 1
            },
            PSAT_EF007: {
                descripcion: "BLOQUEO DE USUARIOS",
                puedo: 1
            },
            PSAT_EF008: {
                descripcion: "CARGA DE DOCUMENTOS",
                puedo: 1
            },
            PSAT_EF009: {
                descripcion: "CONSULTA DE BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF010: {
                descripcion: "CONSULTA DE COMUNICADOS DE TRAMITE DE CUENTAS",
                puedo: 1
            },
            PSAT_EF011: {
                descripcion: "CONSULTA DE PISTAS DE AUDITORIA DE TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF012: {
                descripcion: "CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 1
            },
            PSAT_EF013: {
                descripcion: "CONSULTA DE USUARIOS",
                puedo: 1
            },
            PSAT_EF014: {
                descripcion: "CONSULTA DE USUARIOS BLOQUEADOS",
                puedo: 1
            },
            PSAT_EF015: {
                descripcion: "CONSULTA DE VALIDACION DE USUARIOS",
                puedo: 1
            },
            PSAT_EF016: {
                descripcion: "CREACIÓN DE COMUNICADOS DE TRÁMITES DE CUENTA",
                puedo: 1
            },
            PSAT_EF017: {
                descripcion: "SEGUIMIENTO A SOLICITUD",
                puedo: 1
            },
            PSAT_EF018: {
                descripcion: "GENERAR PLANTILLAS",
                puedo: 1
            },
            PSAT_EF019: {
                descripcion: "GENERAR REPORTES AGR",
                puedo: 1
            },
            PSAT_EP020: {
                descripcion: "GESTIÓN DE SOLICITUD DE CUENTA DE ACCESO",
                puedo: 0
            },
            PSAT_EF021: {
                descripcion: "GESTION DE SOLICITUD DE CUENTAS Y ROLES",
                puedo: 1
            },
            PSAT_EF022: {
                descripcion: "GESTION DE SUSPENSION DE NUEVOS TRAMITES DE CUENTA",
                puedo: 1
            },
            PSAT_EF023: {
                descripcion: "REGISTRAR BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF024: {
                descripcion: "REGISTRAR USUARIO",
                puedo: 1
            },
            PSAT_EF025: {
                descripcion: "REPORTE CUENTAS ACTIVAS POR USUARIO",
                puedo: 1
            },
            PSAT_EF026: {
                descripcion: "REPORTE ESTADISTICO DE TRAMITES",
                puedo: 1
            },
            PSAT_EF027: {
                descripcion: "REPORTE TOTAL DE CUENTAS ACTIVAS",
                puedo: 0
            },
            PSAT_EF028: {
                descripcion: "REPORTE TOTAL DE SOLICITUDES",
                puedo: 1
            },
            PSAT_EF029: {
                descripcion: "REPORTE USUARIOS POR ROL",
                puedo: 1
            },
            PSAT_EF030: {
                descripcion: "SUBIR DOCUMENTACIÓN",
                puedo: 1
            },
            PSAT_EF031: {
                descripcion: "VALIDAR USUARIOS",
                puedo: 1
            },
            PSAT_EF032: {
                descripcion: "VER DETALLE DE BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF033: {
                descripcion: "VER DETALLE DE COMUNICADOS DE TRAMITES DE CUENTAS",
                puedo: 1
            },
            PSAT_EF040: {
                descripcion: "EXPORTAR CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 1
            }
        }
    },
    SAT_EF_GESTOR: {
        dynamic: {},
        static: {
            PSAT_EF001: {
                descripcion: "ADMINISTRACIÓN DEL SISTEMA",
                puedo: 0
            },
            PSAT_EF002: {
                descripcion: "ADMINISTRACIÓN TRÁMITE DE CUENTAS",
                puedo: 1
            },
            PSAT_EF003: {
                descripcion: "ADMINISTRACIÓN DE CATALOGOS",
                puedo: 0
            },
            PSAT_EF004: {
                descripcion: "ADMINISTRACIÓN DE PLANTILLAS",
                puedo: 0
            },
            PSAT_EF005: {
                descripcion: "ADMINSITRACION DE OPCIONES AVANZADAS",
                puedo: 0
            },
            PSAT_EF006: {
                descripcion: "ASIGNACIÓN DE CARGAS DE TRABAJO",
                puedo: 0
            },
            PSAT_EF007: {
                descripcion: "BLOQUEO DE USUARIOS",
                puedo: 0
            },
            PSAT_EF008: {
                descripcion: "CARGA DE DOCUMENTOS",
                puedo: 1
            },
            PSAT_EF009: {
                descripcion: "CONSULTA DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF010: {
                descripcion: "CONSULTA DE COMUNICADOS DE TRAMITE DE CUENTAS",
                puedo: 1
            },
            PSAT_EF011: {
                descripcion: "CONSULTA DE PISTAS DE AUDITORIA DE TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF012: {
                descripcion: "CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 1
            },
           PSAT_EF013: {
                descripcion: "CONSULTA DE USUARIOS",
                puedo: 0
            },
            PSAT_EF014: {
                descripcion: "CONSULTA DE USUARIOS BLOQUEADOS",
                puedo: 0
            },
            PSAT_EF015: {
                descripcion: "CONSULTA DE VALIDACION DE USUARIOS",
                puedo: 1
            },
            PSAT_EF016: {
                descripcion: "CREACIÓN DE COMUNICADOS DE TRÁMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF017: {
                descripcion: "SEGUIMIENTO A SOLICITUD",
                puedo: 1
            },
            PSAT_EF018: {
                descripcion: "GENERAR PLANTILLAS",
                puedo: 1
            },
            PSAT_EF019: {
                descripcion: "GENERAR REPORTES AGR",
                puedo: 1
            },
            PSAT_EP020: {
                descripcion: "GESTIÓN DE SOLICITUD DE CUENTA DE ACCESO",
                puedo: 1
            },
            PSAT_EF021: {
                descripcion: "GESTION DE SOLICITUD DE CUENTAS Y ROLES",
                puedo: 1
            },
            PSAT_EF022: {
                descripcion: "GESTION DE SUSPENSION DE NUEVOS TRAMITES DE CUENTA",
                puedo: 1
            },
            PSAT_EF023: {
                descripcion: "REGISTRAR BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF024: {
                descripcion: "REGISTRAR USUARIO",
                puedo: 0
            },
            PSAT_EF025: {
                descripcion: "REPORTE CUENTAS ACTIVAS POR USUARIO",
                puedo: 1
            },
            PSAT_EF026: {
                descripcion: "REPORTE ESTADISTICO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF027: {
                descripcion: "REPORTE TOTAL DE CUENTAS ACTIVAS",
                puedo: 0
            },
            PSAT_EF028: {
                descripcion: "REPORTE TOTAL DE SOLICITUDES",
                puedo: 1
            },
            PSAT_EF029: {
                descripcion: "REPORTE USUARIOS POR ROL",
                puedo: 1
            },
            PSAT_EF030: {
                descripcion: "SUBIR DOCUMENTACIÓN",
                puedo: 1
            },
            PSAT_EF031: {
                descripcion: "VALIDAR USUARIOS",
                puedo: 1
            },
            PSAT_EF032: {
                descripcion: "VER DETALLE DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF033: {
                descripcion: "VER DETALLE DE COMUNICADOS DE TRAMITES DE CUENTAS",
                puedo: 1
            },
            PSAT_EF040: {
                descripcion: "EXPORTAR CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 0
            }
        }
    },
    /*SAT_EF_OBSERVADOR_EF: {
        dynamic: {},
        static: {}
    },*/
    SAT_EF_ENLACE: {
        dynamic: {},
        static: {
            PSAT_EF001: {
                descripcion: "ADMINISTRACIÓN DEL SISTEMA",
                puedo: 0
            },
            PSAT_EF002: {
                descripcion: "ADMINISTRACIÓN TRÁMITE DE CUENTAS",
                puedo: 1
            },
           PSAT_EF003: {
                descripcion: "ADMINISTRACIÓN DE CATALOGOS",
                puedo: 0
            },
            PSAT_EF004: {
                descripcion: "ADMINISTRACIÓN DE PLANTILLAS",
                puedo: 0
            },
            PSAT_EF005: {
                descripcion: "ADMINSITRACION DE OPCIONES AVANZADAS",
                puedo: 0
            },
            PSAT_EF006: {
                descripcion: "ASIGNACIÓN DE CARGAS DE TRABAJO",
                puedo: 0
            },
            PSAT_EF007: {
                descripcion: "BLOQUEO DE USUARIOS",
                puedo: 0
            },
            PSAT_EF008: {
                descripcion: "CARGA DE DOCUMENTOS",
                puedo: 0
            },
            PSAT_EF009: {
                descripcion: "CONSULTA DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF010: {
                descripcion: "CONSULTA DE COMUNICADOS DE TRAMITE DE CUENTAS",
                puedo: 1
            },
            PSAT_EF011: {
                descripcion: "CONSULTA DE PISTAS DE AUDITORIA DE TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF012: {
                descripcion: "CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF013: {
                descripcion: "CONSULTA DE USUARIOS",
                puedo: 0
            },
            PSAT_EF014: {
                descripcion: "CONSULTA DE USUARIOS BLOQUEADOS",
                puedo: 0
            },//pendiente
            PSAT_EF015: {
                descripcion: "CONSULTA DE VALIDACION DE USUARIOS",
                puedo: 0
            },
            PSAT_EF016: {
                descripcion: "CREACIÓN DE COMUNICADOS DE TRÁMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF017: {
                descripcion: "SEGUIMIENTO A SOLICITUD",
                puedo: 0
            },
            PSAT_EF018: {
                descripcion: "GENERAR PLANTILLAS",
                puedo: 0
            },
            PSAT_EF019: {
                descripcion: "GENERAR REPORTES AGR",
                puedo: 0
            },
            PSAT_EP020: {
                descripcion: "GESTIÓN DE SOLICITUD DE CUENTA DE ACCESO",
                puedo: 1
            },
            PSAT_EF021: {
                descripcion: "GESTION DE SOLICITUD DE CUENTAS Y ROLES",
                puedo: 1
            },
            PSAT_EF022: {
                descripcion: "GESTION DE SUSPENSION DE NUEVOS TRAMITES DE CUENTA",
                puedo: 0
            },
            PSAT_EF023: {
                descripcion: "REGISTRAR BAJAS MASIVAS",
                puedo: 1
            },
            PSAT_EF024: {
                descripcion: "REGISTRAR USUARIO",
                puedo: 1
            },
            PSAT_EF025: {
                descripcion: "REPORTE CUENTAS ACTIVAS POR USUARIO",
                puedo: 1
            },
            PSAT_EF026: {
                descripcion: "REPORTE ESTADISTICO DE TRAMITES",
                puedo: 0
            },
            PSAT_EF027: {
                descripcion: "REPORTE TOTAL DE CUENTAS ACTIVAS",
                puedo: 0
            },
            PSAT_EF028: {
                descripcion: "REPORTE TOTAL DE SOLICITUDES",
                puedo: 0
            },
            PSAT_EF029: {
                descripcion: "REPORTE USUARIOS POR ROL",
                puedo: 0
            },
            PSAT_EF030: {
                descripcion: "SUBIR DOCUMENTACIÓN",
                puedo: 1
            },
            PSAT_EF031: {
                descripcion: "VALIDAR USUARIOS",
                puedo: 0
            },
            PSAT_EF032: {
                descripcion: "VER DETALLE DE BAJAS MASIVAS",
                puedo: 0
            },
            PSAT_EF033: {
                descripcion: "VER DETALLE DE COMUNICADOS DE TRAMITES DE CUENTAS",
                puedo: 1
            },
            PSAT_EF040: {
                descripcion: "EXPORTAR CONSULTA DE SEGUIMIENTO DE TRAMITES",
                puedo: 0
            }
        }
    }
};
  
  export default lasReglas;