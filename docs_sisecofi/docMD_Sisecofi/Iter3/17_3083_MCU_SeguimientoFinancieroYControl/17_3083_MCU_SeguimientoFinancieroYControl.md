|![](Aspose.Words.cdeb7059-40ac-4dff-8ba8-54412099441e.001.png)|**Administración General de Comunicaciones y Tecnologías de la Información**|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>03/10/2023</p>|<p>**Modelo de Casos de Uso**</p><p></p><p>17\_3083\_MCU\_SeguimientoFinancieroYControl.docx** </p><p></p>|Versión del template: 7.00|
| :-: | :-: | :-: |

**<ID Requerimiento> 8309**

**Nombre del Requerimiento:<a name="_hlk156499682"></a><a name="_hlk157173234"></a>** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación.
# <a name="_toc176342657"></a>**TABLA DE VERSIONES Y MODIFICACIONES**

|<a name="tabla_versiones"></a>Versión|<p>Descripción del cambio</p><p></p><p></p><p></p>|<p>Responsable de la Versión</p><p></p>|<p>Fecha</p><p></p>|
| :-: | :-: | :-: | :-: |
|*1*|*Creación del documento*|<p>Lucina García Vargas</p><p>María del Carmen Gutiérrez Sánchez.</p>|*20/07/2024*|
|*1.1*|*Revisión del documento*|Rodolfo López Meneses.|*27/07/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas.</p><p>Rubén Delgado Ramírez.</p>|*29/07/2024*|
|*2*|*Actualización de roles para homologación con el MLPI*|María del Carmen Gutiérrez Sánchez.|*02/09/2024*|
|*2.1*|*Revisión del documento*|Rodolfo López Meneses.|*04/09/2024*|
|*2.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas.</p><p>Rubén Delgado Ramírez.</p>|*04/09/2024*|

**Tabla de Contenido**

[Tabla de Versiones y Modificaciones	1](#_toc176342657)

[Modelo de casos de uso	6](#_toc176342658)

[Nombre del actor: Empleado SAT	6](#_toc176342659)

[Descripción	6](#_toc176342660)

[Características/Responsabilidades	6](#_toc176342661)

[Relaciones de comunicación	12](#_toc176342662)

[Relaciones de generalización	12](#_toc176342663)

[Nombre del actor: Directorio Activo	13](#_toc176342664)

[Descripción:	13](#_toc176342665)

[Características/Responsabilidades	13](#_toc176342666)

[Relaciones de comunicación	13](#_toc176342667)

[Relaciones de generalización	13](#_toc176342668)

[Nombre del actor: Componente externo de acceso	13](#_toc176342669)

[Descripción:	13](#_toc176342670)

[Características/Responsabilidades	13](#_toc176342671)

[Relaciones de comunicación	13](#_toc176342672)

[Relaciones de generalización	13](#_toc176342673)

[Nombre del actor: Consulta CFDI Service	13](#_toc176342674)

[Descripción:	13](#_toc176342675)

[Características/Responsabilidades	13](#_toc176342676)

[Relaciones de comunicación	13](#_toc176342677)

[Descripción del sistema/subsistema	14](#_toc176342678)

[**Sistema/Subsistema**	14](#_toc176342679)

[**Objetivo**	14](#_toc176342680)

[**Casos de uso relacionados**	14](#_toc176342681)

[**Nombre del Caso de Uso**: 17_3083_ECU_AccesoSistema	14](#_toc176342682)

[Propósito del Caso de Uso	14](#_toc176342683)

[Relaciones de comunicación	14](#_toc176342684)

[Relaciones de tipo *<<include>>*	14](#_toc176342685)

[Relaciones de tipo *<<extend>>*	14](#_toc176342686)

[**Nombre del Caso de Uso**: 17_3083_ECU_AdministrarUsuariosDelSistema	15](#_toc176342687)

[Propósito del Caso de Uso	15](#_toc176342688)

[Relaciones de comunicación	15](#_toc176342689)

[Relaciones de tipo <<include>>	15](#_toc176342690)

[Relaciones de tipo *<<extend>>*	15](#_toc176342691)

[**Nombre del Caso de Uso**: 07_843_AltaDeCatalogos	15](#_toc176342692)

[Propósito del Caso de Uso	15](#_toc176342693)

[Relaciones de comunicación	15](#_toc176342694)

[Relaciones de tipo *<<include>>*	15](#_toc176342695)

[Relaciones de tipo *<<extend>>*	15](#_toc176342696)

[**Nombre del Caso de Uso**: 17_3083_ECU_ModificarCatalogos	15](#_toc176342697)

[Propósito del Caso de Uso	15](#_toc176342698)

[Relaciones de comunicación	15](#_toc176342699)

[Relaciones de tipo *<<include>>*	15](#_toc176342700)

[Relaciones de tipo *<<extend>>*	15](#_toc176342701)

[**Nombre del Caso de Uso**: 17_3083_ECU_AsignarProyectos	16](#_toc176342702)

[Propósito del Caso de Uso	16](#_toc176342703)

[Relaciones de comunicación	16](#_toc176342704)

[Relaciones de tipo *<<include>>*	16](#_toc176342705)

[Relaciones de tipo *<<extend>>*	16](#_toc176342706)

[**Nombre del Caso de Uso**: 17_3083_ECU_AdministradorDeFormatosImpresion	16](#_toc176342707)

[Propósito del Caso de Uso	16](#_toc176342708)

[Relaciones de comunicación	16](#_toc176342709)

[Relaciones de tipo *<<include>>*	16](#_toc176342710)

[Relaciones de tipo *<<extend>>*	16](#_toc176342711)

[**Nombre del Caso de Uso**: 17_3083_ECU_ModificarPlantillas	16](#_toc176342712)

[Propósito del Caso de Uso	16](#_toc176342713)

[Relaciones de comunicación	16](#_toc176342714)

[Relaciones de tipo *<<include>>*	16](#_toc176342715)

[Relaciones de tipo *<<extend>>*	16](#_toc176342716)

[**Nombre del Caso de Uso**: 17_3083_ECU_ConsultarPistasAuditoria	17](#_toc176342717)

[Propósito del Caso de Uso	17](#_toc176342718)

[Relaciones de comunicación	17](#_toc176342719)

[Relaciones de tipo *<<include>>*	17](#_toc176342720)

[Relaciones de tipo *<<extend>>*	17](#_toc176342721)

[**Nombre del Caso de Uso**: 17_3083_ECU_ConsultarPapeleradeReciclaje	17](#_toc176342722)

[Propósito del Caso de Uso	17](#_toc176342723)

[Relaciones de comunicación	17](#_toc176342724)

[Relaciones de tipo *<<include>>*	17](#_toc176342725)

[Relaciones de tipo *<<extend>>*	17](#_toc176342726)

[**Nombre del Caso de Uso**: 17_3083_ECU_ConfigurarControlDocumentos	17](#_toc176342727)

[Propósito del Caso de Uso	17](#_toc176342728)

[Relaciones de comunicación	17](#_toc176342729)

[Relaciones de tipo *<<include>>*	17](#_toc176342730)

[Relaciones de tipo *<<extend>>*	17](#_toc176342731)

[**Nombre del Caso de Uso**: 17_3083_ECU_AltaDeProveedor	18](#_toc176342732)

[Propósito del Caso de Uso	18](#_toc176342733)

[Relaciones de comunicación	18](#_toc176342734)

[Relaciones de tipo *<<include>>*	18](#_toc176342735)

[Relaciones de tipo *<<extend>>*	18](#_toc176342736)

[**Nombre del Caso de Uso**: 17_3083_ECU_ModificarInfoDeProveedor	18](#_toc176342737)

[Propósito del Caso de Uso	18](#_toc176342738)

[Relaciones de comunicación	18](#_toc176342739)

[Relaciones de tipo *<<include>>*	18](#_toc176342740)

[Relaciones de tipo *<<extend>>*	18](#_toc176342741)

[**Nombre del Caso de Uso**: 17_3083_ECU_AltaDeProyecto	18](#_toc176342742)

[Propósito del Caso de Uso	18](#_toc176342743)

[Relaciones de comunicación	18](#_toc176342744)

[Relaciones de tipo *<<include>>*	18](#_toc176342745)

[Relaciones de tipo *<<extend>>*	18](#_toc176342746)

[**Nombre del Caso de Uso**: 17_3083_ECU_ModificarProyecto	19](#_toc176342747)

[Propósito del Caso de Uso	19](#_toc176342748)

[Relaciones de comunicación	19](#_toc176342749)

[Relaciones de tipo *<<include>>*	19](#_toc176342750)

[Relaciones de tipo *<<extend>>*	19](#_toc176342751)

[**Nombre del Caso de Uso**: 17_3083_ECU_AsociarFasesMatrizDoc	19](#_toc176342752)

[Propósito del Caso de Uso	19](#_toc176342753)

[Relaciones de comunicación	19](#_toc176342754)

[Relaciones de tipo *<<include>>*	19](#_toc176342755)

[Relaciones de tipo *<<extend>>*	19](#_toc176342756)

[**Nombre del Caso de Uso**: 17_3083_ECU_AdministraInfoComites	19](#_toc176342757)

[Propósito del Caso de Uso	19](#_toc176342758)

[Relaciones de comunicación	19](#_toc176342759)

[Relaciones de tipo *<<include>>*	19](#_toc176342760)

[Relaciones de tipo *<<extend>>*	19](#_toc176342761)

[**Nombre del Caso de Uso**: 17_3083_ECU_CargarPlanDeTrabajo	20](#_toc176342762)

[Propósito del Caso de Uso	20](#_toc176342763)

[Relaciones de comunicación	20](#_toc176342764)

[Relaciones de tipo *<<include>>*	20](#_toc176342765)

[Relaciones de tipo *<<extend>>*	20](#_toc176342766)

[**Nombre del Caso de Uso**: 17_3083_ECU_ModificarPlanDeTrabajo	20](#_toc176342767)

[Propósito del Caso de Uso	20](#_toc176342768)

[Relaciones de comunicación	20](#_toc176342769)

[Relaciones de tipo *<<include>>*	20](#_toc176342770)

[Relaciones de tipo *<<extend>>*	20](#_toc176342771)

[**Nombre del Caso de Uso**: 17_3083_ECU_AsignarProveedoresParticipantes	20](#_toc176342772)

[Propósito del Caso de Uso	20](#_toc176342773)

[Relaciones de comunicación	20](#_toc176342774)

[Relaciones de tipo *<<include>>*	20](#_toc176342775)

[Relaciones de tipo *<<extend>>*	20](#_toc176342776)

[**Nombre del Caso de Uso**: 17_3083_ECU_CerrarProyecto	21](#_toc176342777)

[Propósito del Caso de Uso	21](#_toc176342778)

[Relaciones de comunicación	21](#_toc176342779)

[Relaciones de tipo *<<include>>*	21](#_toc176342780)

[Relaciones de tipo *<<extend>>*	21](#_toc176342781)

[**Nombre del Caso de Uso**: 17_3083_ECU_AltaDeContrato	21](#_toc176342782)

[Propósito del Caso de Uso	21](#_toc176342783)

[Relaciones de comunicación	21](#_toc176342784)

[Relaciones de tipo *<<include>>*	21](#_toc176342785)

[Relaciones de tipo *<<extend>>*	21](#_toc176342786)

[**Nombre del Caso de Uso**: 17_3083_ECU_ModificarContrato	21](#_toc176342787)

[Propósito del Caso de Uso	21](#_toc176342788)

[Relaciones de comunicación	21](#_toc176342789)

[Relaciones de tipo *<<include>>*	21](#_toc176342790)

[Relaciones de tipo *<<extend>>*	21](#_toc176342791)

[**Nombre del Caso de Uso**: 17_3083_ECU_CasoDeNegocio	22](#_toc176342792)

[Propósito del Caso de Uso	22](#_toc176342793)

[Relaciones de comunicación	22](#_toc176342794)

[Relaciones de tipo *<<include>>*	22](#_toc176342795)

[Relaciones de tipo *<<extend>>*	22](#_toc176342796)

[**Nombre del Caso de Uso**: 17_3083_ECU_RegistrarConvenioModificatorio	22](#_toc176342797)

[Propósito del Caso de Uso	22](#_toc176342798)

[Relaciones de comunicación	22](#_toc176342799)

[Relaciones de tipo *<<include>>*	22](#_toc176342800)

[Relaciones de tipo *<<extend>>*	22](#_toc176342801)

[**Nombre del Caso de Uso**: 17_3083_ECU_EditarConvenioModificatorio	22](#_toc176342802)

[Propósito del Caso de Uso	22](#_toc176342803)

[Relaciones de comunicación	22](#_toc176342804)

[Relaciones de tipo *<<include>>*	22](#_toc176342805)

[Relaciones de tipo *<<extend>>*	22](#_toc176342806)

[**Nombre del Caso de Uso**: 17_3083_ECU_RegistrarReintegro	23](#_toc176342807)

[Propósito del Caso de Uso	23](#_toc176342808)

[Relaciones de comunicación	23](#_toc176342809)

[Relaciones de tipo *<<include>>*	23](#_toc176342810)

[Relaciones de tipo *<<extend>>*	23](#_toc176342811)

[**Nombre del Caso de Uso**: 17_3083_ECU_AdministrarDevengado	23](#_toc176342812)

[Propósito del Caso de Uso	23](#_toc176342813)

[Relaciones de comunicación	23](#_toc176342814)

[Relaciones de tipo *<<include>>*	23](#_toc176342815)

[Relaciones de tipo *<<extend>>*	23](#_toc176342816)

[**Nombre del Caso de Uso**: 17_3083_ECU_CrearEstimación	23](#_toc176342817)

[Propósito del Caso de Uso	23](#_toc176342818)

[Relaciones de comunicación	23](#_toc176342819)

[Relaciones de tipo *<<include>>*	23](#_toc176342820)

[Relaciones de tipo *<<extend>>*	23](#_toc176342821)

[**Nombre del Caso de Uso**: 17_3083_ECU_GenerarDictamen	24](#_toc176342822)

[Propósito del Caso de Uso	24](#_toc176342823)

[Relaciones de comunicación	24](#_toc176342824)

[Relaciones de tipo *<<include>>*	24](#_toc176342825)

[Relaciones de tipo *<<extend>>*	24](#_toc176342826)

[**Nombre del Caso de Uso**: 17_3083_ECU_RegistrarServiciosDictaminados	24](#_toc176342827)

[Propósito del Caso de Uso	24](#_toc176342828)

[Relaciones de comunicación	24](#_toc176342829)

[Relaciones de tipo *<<include>>*	24](#_toc176342830)

[Relaciones de tipo *<<extend>>*	24](#_toc176342831)

[**Nombre del Caso de Uso**: 17_3083_ECU_EmitirProforma	24](#_toc176342832)

[Propósito del Caso de Uso	24](#_toc176342833)

[Relaciones de comunicación	24](#_toc176342834)

[Relaciones de tipo *<<include>>*	25](#_toc176342835)

[Relaciones de tipo *<<extend>>*	25](#_toc176342836)

[**Nombre del Caso de Uso**: 17_3083_ECU_GenerarFactura	25](#_toc176342837)

[Propósito del Caso de Uso	25](#_toc176342838)

[Relaciones de comunicación	25](#_toc176342839)

[Relaciones de tipo *<<include>>*	25](#_toc176342840)

[Relaciones de tipo *<<extend>>*	25](#_toc176342841)

[**Nombre del Caso de Uso**: 17_3083_ECU_GenerarNotaDeCredito	25](#_toc176342842)

[Propósito del Caso de Uso	25](#_toc176342843)

[Relaciones de comunicación	25](#_toc176342844)

[Relaciones de tipo *<<include>>*	25](#_toc176342845)

[Relaciones de tipo *<<extend>>*	25](#_toc176342846)

[**Nombre del Caso de Uso**: 17_3083_ECU_GenerarNotificaciónPago	25](#_toc176342847)

[Propósito del Caso de Uso	25](#_toc176342848)

[Relaciones de comunicación	25](#_toc176342849)

[Relaciones de tipo *<<include>>*	25](#_toc176342850)

[Relaciones de tipo *<<extend>>*	26](#_toc176342851)

[**Nombre del Caso de Uso**: 17_3083_ECU_GestionDocumental	26](#_toc176342852)

[Propósito del Caso de Uso	26](#_toc176342853)

[Relaciones de comunicación	26](#_toc176342854)

[Relaciones de tipo *<<include>>*	26](#_toc176342855)

[Relaciones de tipo *<<extend>>*	26](#_toc176342856)

[**Nombre del Caso de Uso**: 17_3083_ECU_ConsultarReporteDeControlDocumental	26](#_toc176342857)

[Propósito del Caso de Uso	26](#_toc176342858)

[Relaciones de comunicación	26](#_toc176342859)

[Relaciones de tipo *<<include>>*	26](#_toc176342860)

[Relaciones de tipo *<<extend>>*	26](#_toc176342861)

[**Nombre del Caso de Uso**: 17_3083_ECU_GenerarReporteFinanciero	26](#_toc176342862)

[Propósito del Caso de Uso	26](#_toc176342863)

[Relaciones de comunicación	27](#_toc176342864)

[Relaciones de tipo *<<include>>*	27](#_toc176342865)

[Relaciones de tipo *<<extend>>*	27](#_toc176342866)

[**Nombre del Caso de Uso**: 17_3083_ECU_ConstruirReportes	27](#_toc176342867)

[Propósito del Caso de Uso	27](#_toc176342868)

[Relaciones de comunicación	27](#_toc176342869)

[Relaciones de tipo *<<include>>*	27](#_toc176342870)

[Relaciones de tipo *<<extend>>*	27](#_toc176342871)

[**Nombre del Caso de Uso**: 17_3083_ECU_ConsultarTableroDeControl	27](#_toc176342872)

[Propósito del Caso de Uso	27](#_toc176342873)

[Relaciones de comunicación	27](#_toc176342874)

[Relaciones de tipo *<<include>>*	27](#_toc176342875)

[Relaciones de tipo <<extend	27](#_toc176342876)







## <a name="_toc176342658"></a>**MODELO DE CASOS DE USO** 
![](Aspose.Words.cdeb7059-40ac-4dff-8ba8-54412099441e.002.png)

**Descripción de actores**

<a name="_toc150535484"></a><a name="_toc173334811"></a><a name="_toc176342659"></a>Nombre del actor: Empleado SAT

<a name="_toc150535485"></a><a name="_toc173334812"></a><a name="_toc176342660"></a>Descripción 

El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.

<a name="_toc150535487"></a><a name="_toc150535486"></a><a name="_toc154501685"></a><a name="_toc173334813"></a><a name="_toc176342661"></a>Características/Responsabilidades

Como “Administrador del sistema” podrá ingresar a las siguientes opciones del menú:

- Sistema
- Catálogos
- Usuarios
- Asignar proyectos
- Matriz documental
- Formatos de impresión
- Pistas de auditoría
- Papelera de reciclaje
- Proveedores
- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Administrador del Sistema Secundario” podrá ingresar a las siguientes opciones del menú:

- Sistema
- Catálogos
- Usuarios
- Asignar proyectos
- Matriz documental
- Formatos de impresión
- Pistas de auditoría
- Papelera de reciclaje
- Proveedores
- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control


Como “Administrador Matriz Documental” podrá ingresar a las siguientes opciones del menú:

- Sistema
- Matriz documental
- Proyectos
- Contratos
- Reportes
- Reporte de Control documental

Como “Apoyo ACPPI” podrá ingresar a las siguientes opciones del menú:

- Proveedores
- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Apoyo al Líder de Proyecto” podrá ingresar a las siguientes opciones del menú:

- Proyectos
- Contratos
- Reportes
- Reporte de Control documental

Como “Gestor Títulos de Autorización” podrá ingresar a las siguientes opciones del menú:

- Proveedores
- Tablero de Control

Como “Gestor Documental Contrato” podrá ingresar a las siguientes opciones del menú:

- Contratos
- Reportes
- Reporte de Control documental

Como “Usuario Consulta” podrá ingresar a las siguientes opciones del menú:

- Proveedores
- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Líder de Proyecto” podrá ingresar a las siguientes opciones del menú:

- Proveedores
- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Administrados de Contrato” podrá ingresar a las siguientes opciones del menú:

- Proveedores
- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Participantes en la Administración de Estimaciones” podrá ingresar a las siguientes opciones del menú:

- Consumo de servicios
- Reportes
- Reporte financiero
- Resumen financiero
- Estimado / pagado 
- Seguimiento por concepto de servicio

Como “Participantes en la Administración de Dictamen” podrá ingresar a las siguientes opciones del menú:

- Proyectos
- Contratos
- Consumo de servicios
- Reportes
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Participantes en la Administración de la Verificación” podrá ingresar a las siguientes opciones del menú:

- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Verificador General” podrá ingresar a las siguientes opciones del menú:

- Proveedores
- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Verificador Especifico del Contrato” podrá ingresar a las siguientes opciones del menú:

- Proveedores
- Proyectos
- Contratos
- Consumo de servicios
- Reintegros
- Reportes
- Reporte de Control documental
- Construir reportes
- Reporte financiero
- Resumen financiero
- Seguimiento de dictamen
- Facturas / penalizaciones / deducciones / reintegros
- Estimado / pagado 
- Seguimiento por concepto de servicio
- Estado financiero
- Tablero de Control

Como “Todos Los Proyectos” podrá ingresar a las siguientes opciones del menú:

- Proyectos
- Contratos

<a name="_toc173334814"></a><a name="_toc176342662"></a>Relaciones de comunicación

- <a name="_hlk172836827"></a><a name="_toc150535488"></a>17\_3083\_ECU\_AccesoSistema
- 17\_3083\_ECU\_AdministrarUsuariosDelSistema
- 17\_3083\_ECU\_AltaDeCatalogos
- 17\_3083\_ECU\_ModificarCatalogos
- 17\_3083\_ECU\_AsignarProyectos
- 17\_3083\_ECU\_AsociarFasesMatrizDoc
- 17\_3083\_ECU\_AdministradorDeFormatosImpresion
- 17\_3083\_ECU\_ModificarPlantillas
- 17\_3083\_ECU\_ConsultarPistasAuditoria
- 17\_3083\_ECU\_ConsultarPapeleradeReciclaje
- 17\_3083\_ECU\_ConfigurarControlDocumentos
- 17\_3083\_ECU\_AltaDeProveedor
- 17\_3083\_ECU\_ModificarInfoDeProveedor
- 17\_3083\_ECU\_AltaDeProyecto
- 17\_3083\_ECU\_ModificarProyecto
- 17\_3083\_ECU\_GestionDocumental
- 17\_3083\_ECU\_AdministrarInfoComites
- 17\_3083\_ECU\_CargarPlanDeTrabajo
- 17\_3083\_ECU\_ModificarPlanDeTrabajo
- 17\_3083\_ECU\_AsignarProveedoresParticipantes
- 17\_3083\_ECU\_CerrarProyecto
- 17\_3083\_ECU\_AltaDeContrato
- 17\_3083\_ECU\_CasoDeNegocio
- 17\_3083\_ECU\_ModificarContrato
- 17\_3083\_ECU\_RegistrarConvenioModificatorio
- 17\_3083\_ECU\_EditarConvenioModificatorio
- 17\_3083\_ECU\_RegistrarReintegro
- 17\_3083\_ECU\_AdministrarDevengado
- 17\_3083\_ECU\_CrearEstimacion
- 17\_3083\_ECU\_GenerarDictamen
- 17\_3083\_ECU\_RegistrarServiciosDictaminados
- 17\_3083\_ECU\_EmitirProforma
- 17\_3083\_ECU\_GenerarFactura
- 17\_3083\_ECU\_GenerarNotaDeCredito
- 17\_3083\_ECU\_GenerarNotificacionPago
- 17\_3083\_ECU\_ConsultarReporteDeControlDocumental
- 17\_3083\_ECU\_ConstruirReportes
- 17\_3083\_ECU\_GenerarReporteFinanciero
- 17\_3083\_ECU\_ConsultarTableroDeControl

<a name="_toc173334815"></a><a name="_toc176342663"></a>Relaciones de generalización

- <a name="_toc150535489"></a>No aplica, no se requieren.








<a name="_toc173334816"></a><a name="_toc176342664"></a>Nombre del actor: Directorio Activo

<a name="_toc173334817"></a><a name="_toc176342665"></a>Descripción: 

Es el esquema donde se consulta la información de los empleados del SAT.

<a name="_toc173334818"></a><a name="_toc176342666"></a>Características/Responsabilidades

Repositorio central de Empleados SAT activos, servidores, roles y puestos.

<a name="_toc173334819"></a><a name="_toc176342667"></a>Relaciones de comunicación

- 17\_3083\_ECU\_AdministrarUsuariosDelSistema

<a name="_toc173334820"></a><a name="_toc176342668"></a>Relaciones de generalización

No aplica, no se requieren.

<a name="_toc173334821"></a><a name="_toc176342669"></a>Nombre del actor: Componente externo de acceso

<a name="_toc173334822"></a><a name="_toc176342670"></a>Descripción: 

Sistema encargado de validar los datos del Empleado SAT que ingresa al portal a través de su e.firma y que proporciona la información de dicho Empleado SAT al SISECOFI. 

<a name="_toc173334823"></a><a name="_toc176342671"></a>Características/Responsabilidades

Validar la identidad del Empleado SAT y permitir acceder al sistema <a name="_hlk172837293"></a>SISECOFI.

<a name="_toc173334824"></a><a name="_toc176342672"></a>Relaciones de comunicación

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173334825"></a><a name="_toc176342673"></a>Relaciones de generalización

No aplica, no se requieren.

<a name="_toc173334826"></a><a name="_toc176342674"></a>Nombre del actor: Consulta CFDI Service

<a name="_toc173334827"></a><a name="_toc176342675"></a>Descripción: 

El servicio proporciona consultas detalladas relacionadas con el XML de las facturas asociadas a un proyecto, así como con el XML de las notas de crédito. Este servicio valida que ambos documentos se encuentren activos y correctamente vinculados al proyecto registrado en el sistema SISECOFI.

<a name="_toc173334828"></a><a name="_toc176342676"></a>Características/Responsabilidades

Validar los datos de las facturas y notas de crédito agregadas al sistema. 

<a name="_toc173334829"></a><a name="_toc176342677"></a>Relaciones de comunicación

- 17\_3083\_ECU\_GenerarFactura
- 17\_3083\_ECU\_GenerarNotaDeCredito

## <a name="_toc150874955"></a><a name="_toc173334830"></a><a name="_toc176342678"></a>**DESCRIPCIÓN DEL SISTEMA/SUBSISTEMA** 
### <a name="_toc150874956"></a><a name="_toc173334831"></a><a name="_toc176342679"></a>**SISTEMA/SUBSISTEMA**
<a name="_toc150874957"></a>TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación
### <a name="_toc173334832"></a><a name="_toc176342680"></a>**OBJETIVO**
<a name="_toc150874958"></a>Permitir la administración y control de proyectos, contratos, facturas y documentación derivada en sus diversas fases, conforme a las reglas de operación. Esto incluye el seguimiento y control financiero de los contratos, así como la revisión y validación de la información relacionada. Se considera también la adaptación a los posibles cambios en las reglas de operación a lo largo del ciclo de vida de los proyectos.


### <a name="_toc173334833"></a><a name="_toc176342681"></a>**CASOS DE USO RELACIONADOS**

<a name="_toc40356165"></a><a name="_toc150535537"></a><a name="_toc176342682"></a>**Nombre del Caso de Uso**: <a name="_toc40356166"></a><a name="_toc150535538"></a>17\_3083\_ECU\_AccesoSistema

<a name="_toc173334834"></a><a name="_toc176342683"></a>Propósito del Caso de Uso

<a name="_toc40356167"></a><a name="_toc150535539"></a>El objetivo de este Caso de Uso es permitir al Empleado SAT ingresar al sistema SISECOFI para acceder a los módulos autorizados y realizar las operaciones correspondientes asignadas a su rol.

<a name="_toc173334835"></a><a name="_toc176342684"></a>Relaciones de comunicación

- <a name="_toc40356168"></a>Componente externo de acceso

<a name="_toc150535540"></a><a name="_toc173334836"></a><a name="_toc176342685"></a>Relaciones de tipo *<<include>>*

- <a name="_toc40356169"></a><a name="_toc150535541"></a>17\_3083\_ECU\_AdministrarUsuariosDelSistema 
- 17\_3083\_ECU\_AltaDeCatalogos
- 17\_3083\_ECU\_AdministradorDeFormatosImpresion
- 17\_3083\_ECU\_ConfigurarControlDocumentos
- 17\_3083\_ECU\_AsignarProyectos
- 17\_3083\_ECU\_ConsultarPistasAuditoria
- 17\_3083\_ECU\_ConsultarPapeleradeReciclaje
- 17\_3083\_ECU\_AltaDeProveedor
- 17\_3083\_ECU\_AltaDeProyecto
- 17\_3083\_ECU\_AltaDeContrato
- 17\_3083\_ECU\_RegistrarReintegro
- 17\_3083\_ECU\_AdministrarDevengado
- 17\_3083\_ECU\_ConsultarReporteDeControlDocumental
- 17\_3083\_ECU\_GenerarReporteFinanciero
- 17\_3083\_ECU\_ConstruirReportes
- 17\_3083\_ECU\_ConsultarTableroDeControl

<a name="_toc173334837"></a><a name="_toc176342686"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc150535542"></a><a name="_toc173334838"></a><a name="_toc176342687"></a>**Nombre del Caso de Uso**: <a name="_toc164030300"></a>17\_3083\_ECU\_AdministrarUsuariosDelSistema

<a name="_toc150535543"></a><a name="_toc173334839"></a><a name="_toc176342688"></a>Propósito del Caso de Uso

<a name="_toc150535544"></a>El objetivo de este Caso de Uso es permitir al Empleado SAT agregar al sistema usuarios que se encuentren en el Directorio Activo del SAT.

<a name="_toc173334840"></a><a name="_toc176342689"></a>Relaciones de comunicación

- <a name="_toc150535545"></a>Directorio activo

<a name="_toc173334841"></a><a name="_toc176342690"></a>Relaciones de tipo <<include>>

- <a name="_toc150535546"></a>17\_3083\_ECU\_AccesoSistema

<a name="_toc173334842"></a><a name="_toc176342691"></a>Relaciones de tipo *<<extend>>*

- <a name="_toc150535547"></a>No aplica, no se requieren

<a name="_toc173334843"></a><a name="_toc176342692"></a>**Nombre del Caso de Uso**: 07\_843\_AltaDeCatalogos

<a name="_toc176342693"></a>Propósito del Caso de Uso

<a name="_toc150535549"></a>El objetivo de este Caso de Uso es permitir al Empleado SAT dar de alta un nuevo registro para un catálogo general o complementario. 

<a name="_toc173334844"></a><a name="_toc176342694"></a>Relaciones de comunicación

- <a name="_toc150535550"></a>No aplica, no se requieren.

<a name="_toc173334845"></a><a name="_toc176342695"></a>Relaciones de tipo *<<include>>*

- <a name="_toc150535551"></a>17\_3083\_ECU\_AccesoSistema

<a name="_toc173334846"></a><a name="_toc176342696"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_ModificarCatalogos

<a name="_toc173334847"></a><a name="_toc176342697"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ModificarCatalogos

<a name="_toc173334848"></a><a name="_toc176342698"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT modificar un registro para un catálogo general o complementario.

<a name="_toc173334849"></a><a name="_toc176342699"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334850"></a><a name="_toc176342700"></a>Relaciones de tipo *<<include>>*

- <a name="_hlk172839239"></a>No aplica, no se requieren.

<a name="_toc173334851"></a><a name="_toc176342701"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_AltaDeCatalogos





<a name="_toc150535557"></a><a name="_toc173334852"></a><a name="_toc176342702"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_AsignarProyectos

<a name="_toc150535558"></a><a name="_toc173334853"></a><a name="_toc176342703"></a>Propósito del Caso de Uso

<a name="_toc150535559"></a>El objetivo de este Caso de Uso es permitir al Empleado SAT relacionar proyectos con usuarios que se encuentren registrados en este sistema.

<a name="_toc173334854"></a><a name="_toc176342704"></a>Relaciones de comunicación

- <a name="_toc150535560"></a>No aplica, no se requieren.

<a name="_toc173334855"></a><a name="_toc176342705"></a>Relaciones de tipo *<<include>>*

- <a name="_toc150535561"></a>17\_3083\_ECU\_AccesoSistema

<a name="_toc173334856"></a><a name="_toc176342706"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren

<a name="_toc173334857"></a><a name="_toc176342707"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_AdministradorDeFormatosImpresion

<a name="_toc173334858"></a><a name="_toc176342708"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT visualizar la información de las plantillas previamente cargadas para: “Formato\_Sol\_Pago”, “Proforma Factura”, “Proforma Nota de crédito”, “Proforma Penalización”, “RCP” y “Plan de trabajo”. Además de permitirle generar nuevas versiones de las plantillas, ingresar a editar las ya existentes o cargar el plan tipo que requiera.

<a name="_toc173334859"></a><a name="_toc176342709"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334860"></a><a name="_toc176342710"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173334861"></a><a name="_toc176342711"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_ModificarPlantillas

<a name="_toc173334862"></a><a name="_toc176342712"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ModificarPlantillas

<a name="_toc173334863"></a><a name="_toc176342713"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT modificar las plantillas “Formato\_Sol\_Pago”, “Proforma Factura”, “Proforma Nota de crédito”, “Proforma Penalización” y “RCP” que se usan en el sistema; así como la descarga del documento de ayuda para la referencia de variables.

<a name="_toc173334864"></a><a name="_toc176342714"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334865"></a><a name="_toc176342715"></a>Relaciones de tipo *<<include>>*

- No aplica, no se requieren.

<a name="_toc173334866"></a><a name="_toc176342716"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_AdministradorDeFormatosImpresion


<a name="_toc173334867"></a><a name="_toc176342717"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ConsultarPistasAuditoria

<a name="_toc173334868"></a><a name="_toc176342718"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT consultar las Pistas de Auditoría, generadas por los movimientos en el sistema. 

<a name="_toc173334869"></a><a name="_toc176342719"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334870"></a><a name="_toc176342720"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173334871"></a><a name="_toc176342721"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren

<a name="_toc173334872"></a><a name="_toc176342722"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ConsultarPapeleradeReciclaje

<a name="_toc173334873"></a><a name="_toc176342723"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT que cuente con los roles necesarios, recuperar documentos eliminados, así como eliminar de forma permanente aquellos que no sean requeridos.

<a name="_toc173334874"></a><a name="_toc176342724"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334875"></a><a name="_toc176342725"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173334876"></a><a name="_toc176342726"></a>Relaciones de tipo *<<extend>>*** 

- No aplica, no se requieren.

<a name="_toc150535552"></a><a name="_toc173334877"></a><a name="_toc176342727"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ConfigurarControlDocumentos

<a name="_toc150535553"></a><a name="_toc173334878"></a><a name="_toc176342728"></a>Propósito del Caso de Uso

<a name="_toc150535554"></a>El objetivo de este Caso de Uso es permitir al Empleado SAT administrar y configurar las plantillas para el control documental de acuerdo con los lineamientos de proyectos nuevos y vigentes.

<a name="_toc173334879"></a><a name="_toc176342729"></a>Relaciones de comunicación

- <a name="_toc150535555"></a>No aplica, no se requieren.

<a name="_toc173334880"></a><a name="_toc176342730"></a>Relaciones de tipo *<<include>>*

- <a name="_toc150535556"></a>17\_3083\_ECU\_AccesoSistema

<a name="_toc173334881"></a><a name="_toc176342731"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc154386992"></a><a name="_toc173334882"></a><a name="_toc176342732"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_AltaDeProveedor

<a name="_toc173334883"></a><a name="_toc176342733"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT ingresar al módulo “Proveedores”, registrar nuevos proveedores y realizar la consulta de los existentes. Además, facilita el acceso a la modificación de la información registrada.

<a name="_toc173334884"></a><a name="_toc176342734"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334885"></a><a name="_toc176342735"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173334886"></a><a name="_toc176342736"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_ModificarInfoDeProveedor


<a name="_toc173334887"></a><a name="_toc176342737"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ModificarInfoDeProveedor

<a name="_toc173334888"></a><a name="_toc176342738"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT ingresar al módulo de proveedores, editar los datos existentes de las cuatro secciones, así como insertar nuevos datos en las secciones de, “Directorio de contacto”, “Títulos de servicio”, y “Dictamen técnico” relacionados a un proveedor.

<a name="_toc173334889"></a><a name="_toc176342739"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334890"></a><a name="_toc176342740"></a>Relaciones de tipo *<<include>>*

- No aplica, no se requieren

<a name="_toc173334891"></a><a name="_toc176342741"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_AltaDeProveedor

<a name="_toc173334892"></a><a name="_toc176342742"></a>**Nombre del Caso de Uso**: <a name="_toc165572116"></a>17\_3083\_ECU\_AltaDeProyecto

<a name="_toc173334893"></a><a name="_toc176342743"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT gestionar el o los proyectos administrados; incluye la creación de un nuevo proyecto, con un identificador único, el registro de la información relevante, la consulta y/o edición de la información del proyecto, así como la cancelación y cierre de este.

<a name="_toc173334894"></a><a name="_toc176342744"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334895"></a><a name="_toc176342745"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173334896"></a><a name="_toc176342746"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_ModificarProyecto

<a name="_toc173334897"></a><a name="_toc176342747"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ModificarProyecto

<a name="_toc173334898"></a><a name="_toc176342748"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT modificar o ingresar la información de los proyectos gestionados en este sistema, permitiendo ingresar a cada sección correspondiente para su llenado.

<a name="_toc173334899"></a><a name="_toc176342749"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334900"></a><a name="_toc176342750"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AsociarFasesMatrizDoc
- 17\_3083\_ECU\_GestionDocumental
- 17\_3083\_ECU\_AdministrarInfoComites
- 17\_3083\_ECU\_CargarPlanDeTrabajo
- 17\_3083\_ECU\_AsignarProveedoresParticipantes
- 17\_3083\_ECU\_CerrarProyecto

<a name="_toc173334901"></a><a name="_toc176342751"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_AltaDeProyecto

<a name="_toc173334902"></a><a name="_toc176342752"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_AsociarFasesMatrizDoc

<a name="_toc173334903"></a><a name="_toc176342753"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT asociar al proyecto la plantilla correspondiente a la fase.

<a name="_toc173334904"></a><a name="_toc176342754"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334905"></a><a name="_toc176342755"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_ModificarProyecto

<a name="_toc173334906"></a><a name="_toc176342756"></a>Relaciones de tipo *<<extend>>*

- <a name="_toc173334907"></a>No aplica, no se requieren.

<a name="_toc173334908"></a><a name="_toc176342757"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_AdministraInfoComites

<a name="_toc173334909"></a><a name="_toc176342758"></a>Propósito del Caso de Uso

El objetivo del Caso de Uso es permitir al Empleado SAT capturar la información para crear y administrar los comités indispensables para el proyecto.

<a name="_toc173334910"></a><a name="_toc176342759"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334911"></a><a name="_toc176342760"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_ModificarProyecto

<a name="_toc173334912"></a><a name="_toc176342761"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc173334913"></a><a name="_toc176342762"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_CargarPlanDeTrabajo

<a name="_toc173334914"></a><a name="_toc176342763"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es facilitar al Empleado SAT la descarga de la plantilla del plan de trabajo adjuntada en el sistema (Plan tipo). Esto permite cargar posteriormente el archivo con la información necesaria para el plan de trabajo de un proyecto. 

<a name="_toc173334915"></a><a name="_toc176342764"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334916"></a><a name="_toc176342765"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_ModificarProyecto

<a name="_toc173334917"></a><a name="_toc176342766"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_ModificarPlanDeTrabajo

<a name="_toc173334918"></a><a name="_toc176342767"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ModificarPlanDeTrabajo

<a name="_toc173334919"></a><a name="_toc176342768"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT modificar el plan de trabajo relacionado con el proyecto. 

<a name="_toc173334920"></a><a name="_toc176342769"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334921"></a><a name="_toc176342770"></a>Relaciones de tipo *<<include>>*

- No aplica, no se requieren

<a name="_toc173334922"></a><a name="_toc176342771"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_CargarPlanDeTrabajo

<a name="_toc173334923"></a><a name="_toc176342772"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_AsignarProveedoresParticipantes

<a name="_toc173334924"></a><a name="_toc176342773"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT agregar a los proveedores participantes en los diferentes proyectos administrados por el sistema.

<a name="_toc173334925"></a><a name="_toc176342774"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334926"></a><a name="_toc176342775"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_ModificarProyecto

<a name="_toc173334927"></a><a name="_toc176342776"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.



<a name="_toc173334928"></a><a name="_toc176342777"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_CerrarProyecto

<a name="_toc173334929"></a><a name="_toc176342778"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT la revisión y validación de los documentos adjuntos al proyecto, incluyendo sus contratos y/o convenios modificatorios, dictámenes y facturas relacionados durante la vida del proyecto. Esta verificación permitirá determinar si los documentos cumplen las condiciones establecidas para el cierre final del proyecto.

<a name="_toc176342779"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173334930"></a><a name="_toc176342780"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_ModificarProyecto

<a name="_toc173334931"></a><a name="_toc176342781"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_AsociarFasesMatrizDoc

<a name="_toc173334932"></a><a name="_toc176342782"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_AltaDeContrato

<a name="_toc173334933"></a><a name="_toc176342783"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT consultar, crear y exportar a Excel la información relacionada con los contratos.

<a name="_toc176342784"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334934"></a><a name="_toc176342785"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173334935"></a><a name="_toc176342786"></a>Relaciones de tipo *<<extend>>*** 

- 17\_3083\_ECU\_ModificarContrato

<a name="_toc173334936"></a><a name="_toc176342787"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ModificarContrato

<a name="_toc173334937"></a><a name="_toc176342788"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT la consulta, modificación y exportación de la información contenida en las secciones de un contrato.

<a name="_toc173334938"></a><a name="_toc176342789"></a>Relaciones de comunicación

- Directorio Activo.

<a name="_toc173334939"></a><a name="_toc176342790"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_CasoDeNegocio
- 17\_3083\_ECU\_GestiónDocumental

<a name="_toc173334940"></a><a name="_toc176342791"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_AltaDeContrato
- 17\_3083\_ECU\_RegistrarConvenioModificatorio
- 17\_3083\_ECU\_RegistrarReintegro

<a name="_toc173334941"></a><a name="_toc176342792"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_CasoDeNegocio

<a name="_toc173334942"></a><a name="_toc176342793"></a>Propósito del Caso de Uso

El objetivo del Caso de Uso es permitir al Empleado SAT consultar, cargar y modificar la proyección de consumo de servicios relacionado con el contrato.

<a name="_toc173334943"></a><a name="_toc176342794"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334944"></a><a name="_toc176342795"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_ModificarContrato

<a name="_toc173334945"></a><a name="_toc176342796"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc173334946"></a><a name="_toc176342797"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_RegistrarConvenioModificatorio

<a name="_toc173334947"></a><a name="_toc176342798"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT registrar convenios modificatorios relacionados a un contrato.

<a name="_toc176342799"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334948"></a><a name="_toc176342800"></a>Relaciones de tipo *<<include>>*

- No aplica, no se requieren.

<a name="_toc173334949"></a><a name="_toc176342801"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_EditarConvenioModificatorio
- 17\_3083\_ECU\_ModificarContrato

<a name="_toc173334950"></a><a name="_toc176342802"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_EditarConvenioModificatorio

<a name="_toc173334951"></a><a name="_toc176342803"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT editar y consultar convenios modificatorios relacionado a un contrato.

<a name="_toc173334952"></a><a name="_toc176342804"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334953"></a><a name="_toc176342805"></a>Relaciones de tipo *<<include>>*

- No aplica, no se requieren

<a name="_toc173334954"></a><a name="_toc176342806"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_RegistrarConvenioModificatorio



<a name="_toc173334955"></a><a name="_toc176342807"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_RegistrarReintegro

<a name="_toc173334956"></a><a name="_toc176342808"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT agregar, modificar y eliminar los reintegros asociados a un contrato. Así como exportar la información asociada.

<a name="_toc173334957"></a><a name="_toc176342809"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334958"></a><a name="_toc176342810"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema
- 17\_3083\_ECU\_GestionDocumental

<a name="_toc173334959"></a><a name="_toc176342811"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_ModificarContrato.

<a name="_toc173334960"></a><a name="_toc176342812"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_AdministrarDevengado

<a name="_toc173334961"></a><a name="_toc176342813"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT gestionar el módulo “Consumo de Servicios”, en el cual se realizan consultas y exportación de estimaciones y dictámenes de los contratos administrados en este sistema. Asimismo, permite el acceso para dar de alta y editar la información de una estimación o dictamen específico.

<a name="_toc173334962"></a><a name="_toc176342814"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334963"></a><a name="_toc176342815"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173334964"></a><a name="_toc176342816"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_CrearEstimacion
- 17\_3083\_ECU\_GenerarDictamen

<a name="_toc173334965"></a><a name="_toc176342817"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_CrearEstimación

<a name="_toc173334966"></a><a name="_toc176342818"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT crear, editar y ver la información de las estimaciones relacionadas con un proyecto y contrato.

<a name="_toc173334967"></a><a name="_toc176342819"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334968"></a><a name="_toc176342820"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_GenerarDictamen.

<a name="_toc173334969"></a><a name="_toc176342821"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_AdministrarDevengado

<a name="_toc173334970"></a><a name="_toc176342822"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_GenerarDictamen

<a name="_toc173334971"></a><a name="_toc176342823"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT generar, editar “Datos generales” y consultar un dictamen relacionado a un contrato.

<a name="_toc173334972"></a><a name="_toc176342824"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334973"></a><a name="_toc176342825"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_RegistrarServiciosDictaminados
- 17\_3083\_ECU\_EmitirProforma
- 17\_3083\_ECU\_GenerarFactura
- 17\_3083\_ECU\_GenerarNotifcacionPago
- 17\_3083\_ECU\_GenerarNotaDeCredito
- 17\_3083\_ECU\_GestionDocumental

<a name="_toc173334974"></a><a name="_toc176342826"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_AdministrarDevengado

<a name="_toc173334975"></a><a name="_toc176342827"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_RegistrarServiciosDictaminados

<a name="_toc173334976"></a><a name="_toc176342828"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT realizar el registro en las siguientes secciones:

- “Registro de servicios dictaminados”
- “Penas contractuales”
- “Penas convencionales”
- ` `“Deducciones” 
- ` `“Soporte documental del dictamen”

<a name="_toc173334977"></a><a name="_toc176342829"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334978"></a><a name="_toc176342830"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_GenerarDictamen

<a name="_toc173334979"></a><a name="_toc176342831"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc173334980"></a><a name="_toc176342832"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_EmitirProforma

<a name="_toc173334981"></a><a name="_toc176342833"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT validar el dictamen previamente cargado, generar una proforma y cargar el oficio de solicitud de factura. 

<a name="_toc173334982"></a><a name="_toc176342834"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334983"></a><a name="_toc176342835"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_GenerarDictamen

<a name="_toc173334984"></a><a name="_toc176342836"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc173334985"></a><a name="_toc176342837"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_GenerarFactura

<a name="_toc173334986"></a><a name="_toc176342838"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT la carga de facturas relacionadas con el dictamen de servicios.

<a name="_toc173334987"></a><a name="_toc176342839"></a>Relaciones de comunicación

- Consulta CFDI Service.

<a name="_toc173334988"></a><a name="_toc176342840"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_GenerarDictamen

<a name="_toc173334989"></a><a name="_toc176342841"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc173334990"></a><a name="_toc176342842"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_GenerarNotaDeCredito

<a name="_toc173334991"></a><a name="_toc176342843"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT generar el registro de notas de crédito.

<a name="_toc173334992"></a><a name="_toc176342844"></a>Relaciones de comunicación

- Consulta CFDI Service.

<a name="_toc173334993"></a><a name="_toc176342845"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_GenerarDictamen

<a name="_toc173334994"></a><a name="_toc176342846"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc173334995"></a><a name="_toc176342847"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_GenerarNotificaciónPago

<a name="_toc173334996"></a><a name="_toc176342848"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT generar el registro de una notificación y referencia de pago.

<a name="_toc173334997"></a><a name="_toc176342849"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173334998"></a><a name="_toc176342850"></a>Relaciones de tipo *<<include>>*

- No aplica, no se requieren.

<a name="_toc173334999"></a><a name="_toc176342851"></a>Relaciones de tipo *<<extend>>*

- 17\_3083\_ECU\_GenerarDictamen

<a name="_toc173335000"></a><a name="_toc176342852"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_GestionDocumental

<a name="_toc173335001"></a><a name="_toc176342853"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT consultar, cargar y descargar documentos relacionados a un “Proyecto”, “Contrato”, “Dictamen” y “Convenio modificatorio” así como tener el estatus de cada uno de los documentos relacionados a las diferentes fases permitiendo realizar consultas entre documentos cargados y documentos pendientes de carga.

<a name="_toc173335002"></a><a name="_toc176342854"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173335003"></a><a name="_toc176342855"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_ModificarProyecto
- 17\_3083\_ECU\_ModificarContrato
- 17\_3083\_ECU\_GenerarDictamen
- 17\_3083\_ECU\_RegistrarReintegro

<a name="_toc173335004"></a><a name="_toc176342856"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren.

<a name="_toc173335005"></a><a name="_toc176342857"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ConsultarReporteDeControlDocumental

<a name="_toc173335006"></a><a name="_toc176342858"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT la búsqueda de los documentos adjuntos al proyecto, incluyendo sus contratos y/o convenios modificatorios, dictámenes y facturas relacionados durante la vida del proyecto.

<a name="_toc173335007"></a><a name="_toc176342859"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173335008"></a><a name="_toc176342860"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173335009"></a><a name="_toc176342861"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren

<a name="_toc173335010"></a><a name="_toc176342862"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_GenerarReporteFinanciero

<a name="_toc173335011"></a><a name="_toc176342863"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT generar los reportes financieros de tipo: Resumen, Seguimiento de dictamen, Facturas/penalizaciones/deducciones/reintegros, Estimado/pagado, Seguimiento por línea de servicio y Estado financiero a partir de los criterios de búsqueda seleccionados; así como realizar la descarga de la información resultante.

<a name="_toc173335012"></a><a name="_toc176342864"></a>Relaciones de comunicación

- No aplica, no se requieren.

<a name="_toc173335013"></a><a name="_toc176342865"></a>Relaciones de tipo *<<include>>*

- 17\_3083\_ECU\_AccesoSistema

<a name="_toc173335014"></a><a name="_toc176342866"></a>Relaciones de tipo *<<extend>>*

- No aplica, no se requieren

<a name="_toc173335015"></a><a name="_toc176342867"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ConstruirReportes

<a name="_toc173335016"></a><a name="_toc176342868"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT construir reportes del sistema.

<a name="_toc173335017"></a><a name="_toc176342869"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173335018"></a><a name="_toc176342870"></a>Relaciones de tipo *<<include>>*

- ` `17\_3083\_ECU\_AccesoSistema

<a name="_toc173335019"></a><a name="_toc176342871"></a>Relaciones de tipo *<<extend>>*** 

- No aplica, no se requieren.

<a name="_toc173335020"></a><a name="_toc176342872"></a>**Nombre del Caso de Uso**: 17\_3083\_ECU\_ConsultarTableroDeControl

<a name="_toc173335021"></a><a name="_toc176342873"></a>Propósito del Caso de Uso

El objetivo de este Caso de Uso es permitir al Empleado SAT visualizar el componente *Power BI* que tendrá la información obtenida de la base de datos del sistema.

<a name="_toc173335022"></a><a name="_toc176342874"></a>Relaciones de comunicación

- No aplica, no se requieren

<a name="_toc173335023"></a><a name="_toc176342875"></a>Relaciones de tipo *<<include>>*

- ` `17\_3083\_ECU\_AccesoSistema

<a name="_toc176342876"></a>Relaciones de tipo <<extend

- No aplica, no se requieren











|<a name="_hlk159937993"></a>**FIRMAS DE CONFORMIDAD**||
| :-: | :- |
|**Firma 1** |**Firma 2** |
|**Nombre**:  Diana Yazmín Pérez Sabido.|**Nombre**:  Rodolfo López Meneses.|
|**Puesto**: Usuaria ACPPI.|**Puesto**: Usuario ACPPI.|
|**Fecha:**|**Fecha:**|
|||
|**Firma 3** |**Firma 4**|
|**Nombre**:  Rubén Delgado Ramírez.|**Nombre**:  María del Carmen Castillejos Cárdenas.|
|**Puesto**: Usuario ACPPI.|**Puesto**:  APE ACPPI|
|**Fecha:**|**Fecha:**|
|||
|**Firma 5**|**Firma 6**|
|**Nombre**:  Alejandro Alfredo Muñoz Núñez.|**Nombre**: Erick Villa Beltrán.|
|**Puesto**:  RAPE ACPPI.|**Puesto**: Líder APE SDMA 6.|
|**Fecha**:|**Fecha**:|
|||
|**Firma 7**|**Firma 8**|
|**Nombre**: Juan Carlos Ayuso Bautista.|**Nombre**:  María del Carmen Gutiérrez Sánchez.|
|**Puesto**: Líder Técnico SDMA 6.|**Puesto**: Analista de Sistemas DS SDMA 6. |
|**Fecha**:|**Fecha**:|
|||

|||Página 1 de 3|
| :- | :-: | -: |

