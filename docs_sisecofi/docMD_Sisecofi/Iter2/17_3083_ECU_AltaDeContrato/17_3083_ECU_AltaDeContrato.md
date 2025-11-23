|![](Aspose.Words.103b625d-27a9-42ed-b8eb-1e25e78744cc.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_AltaDeContrato.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de contratos de contratación


**Tabla de Versiones y Modificaciones**

|Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Angel Horacio López Alcaraz|23/01/2024|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|02/05/2024|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|21/05/2024|


**Tabla de Contenido**

[17_3083_ECU_AltaDeContrato.	2](#_toc168054214)

[1. Descripción	2](#_toc168054215)

[2. Diagrama del Caso de Uso	2](#_toc168054216)

[3. Actores	2](#_toc168054217)

[4. Precondiciones	2](#_toc168054218)

[5. Post condiciones	3](#_toc168054219)

[6. Flujo primario	3](#_toc168054220)

[7. Flujos alternos	7](#_toc168054221)

[8. Referencias cruzadas	20](#_toc168054222)

[9. Mensajes	21](#_toc168054223)

[10. Requerimientos No Funcionales	21](#_toc168054224)

[11. Diagrama de actividad	23](#_toc168054225)

[12. Diagrama de estados	23](#_toc168054226)

[13. Aprobación del cliente	24](#_toc168054227)


### **<a name="_toc168054214"></a>**17\_3083\_ECU\_AltaDeContrato.

|<h3><a name="_toc168054215"></a>**1. Descripción** </h3>||
| :- | :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT consultar, crear y exportar a Excel la información relacionada con los contratos.</p><p></p>||
|<h3><a name="_toc168054216"></a>**2. Diagrama del Caso de Uso**</h3>||
|<p></p><p>![](Aspose.Words.103b625d-27a9-42ed-b8eb-1e25e78744cc.002.png)</p><p></p>||
|||
|<h3><a name="_toc168054217"></a>**3. Actores** </h3>||
|||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>||
| :- | :- |
|<h3><a name="_toc168054218"></a>**4. Precondiciones**</h3>||
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa.</p><p>- Se le han asignado los roles requeridos al Empleado SAT para ingresar al módulo “Contratos”.</p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar y/o insertar al módulo “Contratos”.</p><p>- Se han registrado proyectos previamente.</p><p>- Se han dado de alta proveedores previamente.</p><p>- Se ha asociado el proyecto al Empleado SAT.</p><p>&emsp;</p>||
|<h3><a name="_toc168054219"></a>**5. Post condiciones** </h3>||
|<p></p><p>- El Empleado SAT consultó los contratos existentes.</p><p>- El Empleado SAT creó un nuevo contrato.</p><p>&emsp;</p><p>&emsp;</p>||
|<h3><a name="_toc168054220"></a>**6. Flujo primario**</h3>||
|||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT ingresa al módulo **“Contratos”**.|<p>2. Obtiene de la base de datos (BD) la información de los siguientes catálogos y los muestra en las listas de selección: </p><p>&emsp;</p><p>- Estatus del contrato</p><p>- Proveedor</p><p>- Administración central</p>|
||<p>3. Obtiene de la (BD) la información de los siguientes campos:</p><p>&emsp;</p><p>- Id</p><p>- Nombre del contrato</p><p>- Nombre del proyecto</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Tipo de procedimiento</p><p>- Inicio</p><p>- Término</p><p>- Último CM</p><p>- Monto máximo</p><p>- Monto máximo último CM</p><p>- Monto en pesos</p><p>- Administración central</p><p>- Administrador del contrato</p><p></p>|
|<p></p><p></p>|<p>4. <a name="_ref164173489"></a><a name="_ref158409910"></a><a name="_ref167203790"></a>Despliega la pantalla “Contratos”. Aplica las **(RNA243)**:** </p><p></p><p>Buscar contrato:</p><p>- Estatus del contrato</p><p>- Vigencia</p><p>&emsp;- De</p><p>&emsp;- Al</p><p>- Proveedor</p><p>- Administración central</p><p>&emsp;</p><p>Opciones:</p><p>- ![ref1]Buscar</p><p>- Nuevo contrato</p><p>- Exportar a Excel ![ref2]</p><p>Tabla Contratos. Aplica la **(RNA244)**:</p><p>- Id</p><p>- Nombre del contrato</p><p>- Nombre del proyecto</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Tipo de procedimiento</p><p>- Inicio</p><p>- Término</p><p>- Último CM</p><p>- Monto máximo</p><p>- Monto máximo último CM</p><p>- Monto en pesos. Aplica la **(RNA245)**.</p><p>- Administración central</p><p>- Administrador del contrato</p><p>- Acciones</p><p>- Editar ![ref3]</p><p>- Ver detalle ![ref4]</p><p>- Campos para Filtro </p><p>&emsp;</p><p>Ver **(17\_3083\_EIU\_AltaDeContrato)** Estilos 01.</p>|
|<p>5. <a name="_ref164173550"></a>Selecciona la opción **“Nuevo”** del módulo **“Contratos”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione algún criterio de búsqueda continúa en el **([**FA01**](#fa01))**.</p><p>- Si selecciona la opción **“Exportar a Excel”**, continúa en el **([**FA04**](#fa04))**.</p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA05**](#fa05))**.</p><p>- Si selecciona la opción **“Ver detalle”**, continúa en el **([**FA06**](#fa06))**.</p><p>- En caso de que requiera aplicar un **“Filtro”** a la información en alguna columna de la tabla, continúa en el **([**FA09**](#fa09))**.</p>|6. <a name="_ref164173912"></a>Obtiene de la BD la información de los proyectos en estatus “Ejecución” que tenga asociados el Empleado SAT y los muestra en la lista de selección “Proyecto asociado”.|
||<p>7. <a name="_ref164173515"></a>Muestra la pantalla para registrar los siguientes datos del contrato:</p><p></p><p>Sección Identificación.</p><p>- Id. Aplica la **(RNA76)**</p><p>- Proyecto asociado\*. Aplica la **(RNA158)**</p><p>- Estatus. Aplica la **(RNA75)**</p><p>- Nombre del contrato\*</p><p>- Nombre corto del contrato\*</p><p>- Última modificación. Aplica la **(RNA159)**</p><p></p><p>Opciones. Aplica la **(RNA75)**:</p><p>- Inicial</p><p>- Ejecución</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Secciones inhabilitadas. Aplica la **(RNA160):**</p><p>- Datos generales</p><p>- Vigencia y montos</p><p>- Grupos de servicio y/o conceptos</p><p>- Registro de servicios</p><p>- Proyección de caso de negocio</p><p>- Cargar layout de los informes</p><p>- Atraso en el inicio de la prestación</p><p>- Informes documentales por única vez</p><p>- Informes documentales periódicos</p><p>- Informes documentales de los servicios</p><p>- Penas contractuales</p><p>- Niveles de servicio (SLA)</p><p>- Asignación de plantilla</p><p>- Gestión documental</p><p>- Convenios modificatorios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p>- Reintegros asociados</p><p>- Cierre</p><p>&emsp;</p><p>Opción:</p><p>- ` `Regresar</p><p></p><p>Ver **(17\_3083\_EIU\_AltaDeContrato)** Estilos 02.</p>|
|<p>8. <a name="_ref158409503"></a>Ingresa los datos de la sección **“Identificación”**:</p><p>&emsp;</p><p>- Proyecto asociado</p><p>- Nombre del contrato</p><p>- Nombre corto del contrato</p>||
|<p>9. Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Cancelar”**, continúa en el **([**FA07**](#fa07))**.</p>|<p>10. Valida que se hayan capturado los datos obligatorios de acuerdo con la **(RNA03)**.</p><p>&emsp;</p><p>&emsp;En caso de identificar que no se ingresaron los datos obligatorios, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>11. Valida que no existan valores duplicados conforme a la **(RNA237)**.</p><p>&emsp;</p><p>- En caso de identificar que existen duplicados, continúa en el **([**FA11**](#fa11))**.</p>|
||12. Genera el Id del contrato de acuerdo con la **(RNA76)**.|
||<p>13. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos-Identificación</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar)</p><p>**Movimiento** = Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>&emsp;</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>14. Almacena en la BD la siguiente información del contrato creado:</p><p>&emsp;</p><p>- Id</p><p>- Proyecto asociado</p><p>- Estatus. Aplica la **(RNA75)**</p><p>- Nombre del contrato</p><p>- Nombre corto del contrato</p><p>- Última modificación</p>|
||15. Muestra el mensaje **([**MSG004**](#msg004))**,** con la opción “Aceptar”.|
|16. Selecciona la opción **“Aceptar”**.|17. Cierra el mensaje.|
||18. Fin del Caso de Uso.|

|<p></p><p></p>||
| :- | :- |
|<h3><a name="_toc158638713"></a><a name="_toc168054221"></a>**7. Flujos alternos** </h3>||
|<p></p><p><a name="fa01"></a>**FA01 Selecciona al menos un criterio de búsqueda**</p>||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref167199112"></a>El **FA01** inicia cuando el Empleado SAT selecciona al menos un valor en los criterios de búsqueda.||
|2. Selecciona la opción **“Buscar”**.|<p>3. Verifica que se haya seleccionado un valor en al menos un criterio de búsqueda.</p><p>&emsp;</p><p>- En caso de que no se haya seleccionado al menos un criterio de búsqueda continúa en el **([**FA10**](#fa10))**.</p>|
||<p>4. Verifica que existan contratos que coincidan con los criterios de búsqueda.</p><p>&emsp;</p><p>- En caso de que no existan contratos que coincidan con los criterios de búsqueda, continúa en el **([**FA03**](#fa03))**.</p>|
||5. Habilita la opción “Exportar a Excel”.|
||<p>6. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contratos</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consulta)</p><p>**Movimiento**= </p><p>- Estatus del contrato</p><p>- Vigencia</p><p>- Proveedor</p><p>- Administración central</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>7. Consulta y obtiene de la BD la siguiente información de los contratos de los proyectos asociados al Empleado SAT, que coincidan con los criterios de búsqueda.</p><p>&emsp;</p><p>- Id</p><p>- Nombre del contrato</p><p>- Nombre del proyecto</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Tipo de procedimiento</p><p>- Inicio</p><p>- Término</p><p>- Último CM</p><p>- Monto máximo</p><p>- Monto máximo último CM</p><p>- Monto en pesos</p><p>- Administración central</p><p>- Administrador del contrato</p>|
||<p>8. Muestra la pantalla de “Contratos”** con la información obtenida de los contratos que coincidan con los criterios de búsqueda.</p><p>&emsp;</p><p>Buscar contrato:</p><p>- Estatus del contrato</p><p>- Vigencia</p><p>&emsp;- De</p><p>&emsp;- Al</p><p>- Proveedor</p><p>- Administración central</p><p>Opciones:</p><p>- ![ref5]Buscar</p><p>- Nuevo contrato </p><p>- Exportar a Excel ![ref2]</p><p></p><p>Tabla Contratos. Aplica la **(RNA244)**:</p><p>- Id</p><p>- Nombre del contrato</p><p>- Nombre del proyecto</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Tipo de procedimiento</p><p>- Inicio</p><p>- Término</p><p>- Último CM</p><p>- Monto máximo</p><p>- Monto máximo último CM</p><p>- Monto en pesos</p><p>- Administración central</p><p>- Administrador del contrato</p><p>- Acciones</p><p>&emsp;- Editar ![ref3]</p><p>&emsp;- Ver detalle![ref4]</p><p>- Campos para Filtro</p><p></p><p>Ver **(17\_3083\_EIU\_AltaDeContrato)** Estilos 01.</p>|
||9. Regresa al paso **[**5**](#_ref164173550)** del Flujo primario.|

|<p></p><p></p><p><a name="fa02"></a>**FA02 No se ingresaron los datos obligatorios**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA02** inicia cuando el sistema identifica que no se ingresaron los datos obligatorios.|
||2. Muestra en rojo los campos pendientes de capturar.|
||3. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje. |
||6. Regresa al paso [**7**](#_ref164173515) del Flujo primario.|

|<p></p><p></p><p></p><p></p><p></p><p><a name="fa03"></a>**FA03 No existen contratos que coincidan con los criterios de la búsqueda**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA03** inicia cuando el sistema identifica que no existen contratos que coincidan con los criterios de búsqueda.|
||2. Muestra el **([**MSG002**](#msg002))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje. |
||5. Regresa al paso [**5**](#_ref164173550) del Flujo primario.|

|<p></p><p></p><p><a name="fa04"></a>**FA04 Selecciona la opción “Exportar a Excel”**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA04** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id del contrato</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA08**](#fa08))**.</p>|
||3. Obtiene la información correspondiente a los contratos de la BD que cumplan con el criterio de búsqueda seleccionado.|
||4. Genera un archivo Excel con extensión (.xlsx), con la información obtenida.|
||5. Descarga el archivo Excel con extensión (.xlsx).|
||6. Fin de Caso de Uso.|

|<p></p><p></p><p><a name="fa05"></a>**FA05 Selecciona la opción “Editar”**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA05** inicia cuando el Empleado SAT selecciona la opción **“Editar”** en un contrato.</p><p></p>|2. Se ejecuta el proceso del **(17\_3083\_ECU\_ModificarContrato)**.|
||3. Fin del Caso de Uso.|

|<p></p><p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Ver detalle”**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA06** inicia cuando el Empleado SAT selecciona la opción **“Ver detalle”** de un contrato.</p><p></p>|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consulta)</p><p>**Movimiento**=</p><p>-	Id contrato</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA08**\](#fa08)](#fa12))**.</p>|
||<p>3. Consulta en la BD la información del contrato.</p><p>&emsp;</p><p>- Id</p><p>- Proyecto asociado</p><p>- Estatus</p><p>- Nombre del contrato</p><p>- Nombre corto del contrato</p><p>- Última modificación</p>|
||<p>4. Muestra la información del contrato: “Identificación” en modo solo lectura.</p><p></p><p>- Id</p><p>- Proyecto asociado\*</p><p>- Estatus</p><p>- Nombre del contrato\*</p><p>- Nombre corto del contrato\*</p><p>- Última modificación</p><p></p><p>Opciones. Aplica la **(RNA75)**:</p><p>- Inicial</p><p>- Ejecución</p><p>- Cancelar</p><p>- Guardar (aparecerá deshabilitado)</p><p></p><p>Secciones colapsadas:</p><p>- Datos generales</p><p>- Vigencia y montos</p><p>- Grupos de servicio y/o conceptos</p><p>- Registro de servicios</p><p>- Proyección de caso de negocio</p><p>- Cargar layout de los informes</p><p>- Atraso en el inicio de la prestación</p><p>- Informes documentales por única vez</p><p>- Informes documentales periódicos</p><p>- Informes documentales de los servicios</p><p>- Penas contractuales</p><p>- Niveles de servicio (SLA)</p><p>- Asignación de plantilla</p><p>- Gestión documental</p><p>- Convenios modificatorios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p>- Reintegros asociados</p><p>- Cierre</p><p></p><p>Opción:</p><p>- ` `Regresar</p><p></p><p>Ver **(17\_3083\_EIU\_AltaDeContrato)** Estilos 02.</p>|
|<p>5. <a name="_ref164173586"></a>Selecciona la sección **“Datos generales”** y continúa en el flujo. </p><p>&emsp;</p><p>- Si selecciona la sección **“Vigencia y montos”**,** continúa en el paso <a name="_hlk157333842"></a>[**8**](#_ref164172817) de este flujo. </p><p>- Si selecciona la sección **“Grupos de servicio y/o conceptos”**, continúa en el paso [**10**](#_ref164173037) de este flujo.</p><p>- Si selecciona la sección **“Registro de servicios”**, continúa en el paso [**12**](#_ref164173065) de este flujo.</p><p>- Si selecciona la sección **“Proyección de caso de negocio”**, continúa en el paso [**14**](#_ref164173092) de este flujo.</p><p>- Si selecciona la sección **“Cargar layout de los informes”**, continúa en el paso [**16**](#_ref164173115) de este flujo.</p><p>- Si selecciona la sección **“Atraso en el inicio de la prestación”**, continúa en el paso [**18**](#_ref164173135) de este flujo.</p><p>- Si selecciona la sección **“Informes documentales por única vez”**, continúa en el paso [**20**](#_ref164173154) de este flujo.</p><p>- Si selecciona la sección **“Informes documentales periódicos”**, continúa en el paso [**22**](#_ref164173176) de este flujo.</p><p>- Si selecciona la sección **“Informes documentales de los servicios”**, continúa en el paso [**24**](#_ref164173194) de este flujo.</p><p>- Si selecciona la sección **“Penas contractuales”**, continúa en el paso [**26**](#_ref164173218) de este flujo.</p><p>- Si selecciona la sección **“Niveles de servicio (SLA)”**, continúa en el paso [**28**](#_ref164173241) de este flujo.</p><p>- ` `Si selecciona la sección **“Asignación de plantilla”**, continúa en el paso [**30**](#_ref164173257) de este flujo.</p><p>- Si selecciona la sección **“Gestión documental”**, continúa en el paso [**32**](#_ref164173283) de este flujo.</p><p>- Si selecciona la sección **“Convenios modificatorios”**, continúa en el paso [**34**](#_ref164173303) de este flujo.</p><p>- Si selecciona la sección **“Dictámenes asociados”**, continúa en el paso [**36**](#_ref164173323) de este flujo.</p><p>- Si selecciona la sección **“Facturas asociadas”**, continúa en el paso [**38**](#_ref164173346) de este flujo.</p><p>- Si selecciona la sección **“Reintegros asociados”**,** continúa en el paso [**40**](#_ref164173392) de este flujo.</p><p>- Si selecciona la sección **“Cierre”,** continúa en el paso [**42**](#_ref167203099) de este flujo.</p>|6. Obtiene de la BD la información de la opción “Datos generales” del contrato seleccionado.|
||<p>7. <a name="_hlk163472703"></a>Muestra la información obtenida en el paso anterior, en la sección de “Datos generales”, en formato de solo lectura.</p><p>&emsp;Regresa al paso [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 02.</p>|
||8. <a name="_ref164172817"></a>Obtiene de la BD la información de “Vigencia y montos” del contrato seleccionado.|
||<p>9. Muestra la información obtenida en el paso anterior, en la sección “Vigencia y montos”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 03.</p>|
||10. <a name="_ref164173037"></a>Obtiene de la BD la sección “Grupos de servicio y/o conceptos” del contrato seleccionado.|
||<p>11. Muestra la información obtenida en el paso anterior, en la sección “Grupos de servicio y/o conceptos”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 04.</p>|
||12. <a name="_ref164173065"></a>Obtiene de la BD la información de la sección “Registro de servicios” del contrato seleccionado.|
||<p>13. Muestra la información obtenida en el paso anterior, en la sección “Registro de servicios”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 05.</p>|
||14. <a name="_ref164173092"></a>Obtiene de la BD la información de la sección “Proyección de caso de negocio” del contrato seleccionado.|
||<p>15. Muestra la información obtenida en el paso anterior, en la sección “Proyección de caso de negocio”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_CasoDeNegocio)**</p><p>&emsp;Estilos 01.</p>|
||16. <a name="_ref164173115"></a>Obtiene de la BD la información del catálogo “Sección a cargar”.|
||<p>17. Muestra la información obtenida en el paso anterior, en la sección “Cargar layout de los informes”, en formato de solo lectura.</p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 11.</p>|
||18. <a name="_ref164173135"></a>Obtiene de la BD la información de la sección “Atraso en el inicio de la prestación” del contrato seleccionado.|
||<p>19. Muestra la información obtenida en el paso anterior, en la sección “Atraso en el inicio de la prestación”, en formato de solo lectura.</p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 06.</p>|
||20. <a name="_ref164173154"></a>Obtiene de la BD la información de la sección “Informes documentales por única vez” del contrato seleccionado.|
||<p>21. Muestra la información obtenida en el paso anterior, en la sección “Informes documentales por única vez”, en formato de solo lectura.</p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 08.</p>|
||22. <a name="_ref164173176"></a>Obtiene de la BD la información de la sección “Informes documentales periódicos” del contrato seleccionado.|
||<p>23. Muestra la información obtenida en el paso anterior, en la sección “Informes documentales periódicos”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 09.</p>|
||24. <a name="_ref164173194"></a>Obtiene de la BD la información de la sección “Informes documentales de los servicios” del contrato seleccionado.|
||<p>25. Muestra la información obtenida en el paso anterior, en la sección “Informes documentales de los servicios”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 10.</p>|
||26. <a name="_ref164173218"></a>Obtiene de la BD la información de la sección “Penas contractuales” del contrato seleccionado.|
||<p>27. Muestra la información obtenida en el paso anterior, en la sección “Penas contractuales”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 07.</p>|
||28. <a name="_ref164173241"></a>Obtiene de la BD la información de la sección “Niveles de servicio (SLA)” del contrato seleccionado.|
||<p>29. Muestra la información obtenida en el paso anterior, en la sección “Niveles de servicio (SLA)”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 12.</p>|
||30. <a name="_ref164173257"></a>Obtiene de la BD la información de la sección “Asignación de plantilla” de la plantilla asociada al contrato.|
||<p>31. Muestra la información obtenida en el paso anterior, en la sección “Asignación de plantilla”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 13.</p>|
||32. <a name="_ref164173283"></a>Obtiene de la BD la información de la sección “Gestión documental” del contrato seleccionado.|
||<p>33. Muestra la información obtenida en el paso anterior, en la sección “Gestión documental”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_GestionDocumental)**</p><p>&emsp;Estilos 01.</p>|
||34. <a name="_ref164173303"></a>Obtiene de la BD la información de la sección “Convenios modificatorios” del contrato seleccionado.|
||<p>35. Muestra la información obtenida en el paso anterior, en la sección “Convenios modificatorios”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 14.</p>|
||36. <a name="_ref164173323"></a>Obtiene de la BD la información de la sección “Dictámenes asociados” del contrato seleccionado.|
||<p>37. Muestra la información obtenida en el paso anterior, en la sección “Dictámenes asociados”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 17.</p>|
||38. <a name="_ref164173346"></a>Obtiene de la BD la información de la sección “Facturas asociadas” del contrato seleccionado.|
||<p>39. Muestra la información obtenida en el paso anterior, en la sección “Facturas asociadas”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 15.</p>|
||40. <a name="_ref164173392"></a>Obtiene de la BD la información de la sección “Reintegros asociados” del contrato seleccionado.|
||<p>41. Muestra la información obtenida en el paso anterior, en la sección “Reintegros asociados”, en formato de solo lectura. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 16.</p>|
||42. <a name="_ref167203099"></a>Obtiene de la BD el documento “Acta de cierre” del contrato seleccionado.|
||<p>43. Muestra el documento obtenido en el paso anterior, en la sección “Cierre”. </p><p>&emsp;Regresa al paso  [**5**](#_ref164173586) del presente flujo.</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarContrato)**</p><p>&emsp;Estilos 18.</p>|
||44. Fin del Caso de Uso.|

|<p></p><p></p><p><a name="fa07"></a>**FA07 Selecciona la opción “Cancelar”**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA07** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”**.|<p>2. Muestra el **([**MSG003**](#msg003))**,** con las opciones “Sí” y “No”.</p><p></p>|
|<p>3. Selecciona la opción **“Sí”** y** continúa en el paso [5](#_ref168056418).</p><p>&emsp;</p><p>- En caso de seleccionar **“No”**,** continúa en el paso [**4**](#_ref168056447).</p>|4. <a name="_ref168056447"></a>Cierra el mensaje y permanece en la pantalla donde fue invocado.|
||<p>5. <a name="_ref168056418"></a>Cierra la ventana emergente.</p><p>&emsp;</p><p>- En caso de no haber guardado la información de “Identificación”, se libera el Id generado y regresa al paso [**4**](#_ref167203790)** del Flujo primario.</p>|

|<p></p><p></p><p><a name="fa08"></a>**FA08 No se pueden almacenar las Pistas de Auditoría**</p>||
| :- | :- |

|**Actor**|`	`**Sistema**	|
| :-: | :- |
|** |1. El **FA08** inicia cuando interviene un evento ajeno y no se pueden almacenar las Pistas de Auditoría. |
| |2. Cancela la operación sin completar el movimiento que estaba en proceso.|
| |<p>3. Muestra el mensaje de acuerdo con lo siguiente: </p><p>&emsp; </p><p>- Si la Pista de Auditoría es por el tipo de movimiento **UPDT** e **INSR**, se muestra el **([**MSG005**](#msg005))**.</p><p>&emsp;** </p><p>- Si la Pista de Auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG006**](#msg006))**.</p><p></p><p>- En caso de que la Pista de Auditoría sea por el tipo de movimiento **PRNT**, se muestra el **([**MSG007**](#msg007))**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
| |6. Regresa al paso previo que detona la acción de la pista de auditoría.|

|<p></p><p></p><p><a name="fa12"></a><a name="fa09"></a>**FA09 Filtra la información de alguna columna de la tabla**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA09** inicia cuando el Empleado SAT requiere **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||5. Regresa a la pantalla donde fue invocado.|

|<p></p><p></p><p><a name="fa10"></a>**FA10 No se han ingresado criterios de búsqueda**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA10** inicia cuando el sistema identifica que no se ha ingresado al menos un criterio de búsqueda.|
||2. Muestra el **([**MSG008**](#msg008))**,** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”.**|4. Cierra el mensaje. |
||5. Regresa al paso [**1**](#_ref167199112) del **([**FA01**](#fa01))**.|

|<p></p><p></p><p><a name="fa11"></a>**FA11 Existe un valor duplicado**</p>||
| :- | :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|  |1. El **FA11** inicia cuando el sistema identifica que existe un dato ya almacenado igual al valor que se acaba de ingresar. Aplica a la **(RNA237)**.|
|  |2. Muestra el **([**MSG009**](#msg009))** con la opción “Aceptar”. |
|3. Selecciona la opción **“Aceptar”**. |4. Cierra el mensaje y permanece en la pantalla en la que fue invocado. |

|||
| :- | :- |
|||
|<h3><a name="_toc168054222"></a>**8. Referencias cruzadas** </h3>||
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_AltaDeContrato</p><p>- 17\_3083\_ECU\_ModificarContrato</p><p>- 17\_3083\_EIU\_ModificarContrato</p><p>- 17\_3083\_EIU\_GestionDocumental</p><p>- 17\_3083\_EIU\_CasoDeNegocio</p><p></p>||
|<h3><a name="_toc168054223"></a>**9. Mensajes** </h3>||
|||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Favor de ingresar los datos obligatorios marcados con (\*).|
|<a name="msg002"></a>**MSG002**|No existen resultados que coincidan con los criterios de búsqueda ingresados.|
|<a name="msg003"></a>**MSG003**|Se perderá la información que no haya guardado. ¿Desea cancelar?|
|<a name="msg004"></a>**MSG004**|Contrato creado exitosamente.|
|<a name="msg005"></a>**MSG005**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg006"></a>**MSG006**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|Introduzca al menos un criterio de búsqueda.|
|<a name="msg009"></a>**MSG009**|Ya existe un contrato con los datos ingresados. Favor de revisar.|

|<p></p><p></p>||
| - | :- |
|<h3><a name="_toc168054224"></a>**10. Requerimientos No Funcionales** </h3>||
|||

|**ID RNF** |**Requerimiento No Funcional** |**Descripción** |
| :-: | :-: | :-: |
|**RNF001** |Disponibilidad |El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|
|**RNF002** |Concurrencia |<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p></p><p>El número de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT.</p>|
|**RNF003** |Seguridad |El acceso solo podrá ser otorgado a todo Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema.|
|**RNF004** |Usabilidad |<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación:  </p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual, como guía para el Empleado SAT.  </p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado.  </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales.</p>|
|**RNF005** |Eficiencia |Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT.|
|**RNF006** |Usabilidad |<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100.</p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta).  </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta).  </p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).  </p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual con el resultado de la consulta).  </p><p></p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente. </p>|
|**RNF007** |Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.   |
|**RNF008** |Fiabilidad|El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema. |
|**RNF009** |Seguridad|Se debe mantener la información en pantalla en caso de un error al guardar las Pistas de Auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos, ya que no están controlados por el sistema. |
|**RNF010**|Integridad|Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena.|

|<p></p><p></p>||
| :- | :- |
|<h3><a name="_toc168054225"></a>**11. Diagrama de actividad** </h3>||
|<p></p><p>![](Aspose.Words.103b625d-27a9-42ed-b8eb-1e25e78744cc.008.png)</p><p></p><p></p>||
|<h3><a name="_toc168054226"></a>**12. Diagrama de estados** </h3>||
|<p></p><p>No aplica, no se requiere para este proceso.</p><p></p><p></p>||
|<h3><a name="_toc168054227"></a>**13. Aprobación del cliente** </h3>||
|<p></p><p></p>||

|**FIRMAS DE CONFORMIDAD** ||
| :-: | :- |
|**Firma 1**  |**Firma 2**  |
|**Nombre**: María del Carmen Castillejos Cárdenas. |**Nombre**: Rubén Delgado Ramírez. |
|**Puesto**: Usuaria ACPPI. |**Puesto**: Usuario ACPPI. |
|**Fecha:** |**Fecha:** |
|  |  |
|**Firma 3**  |**Firma 4** |
|**Nombre**: Rodolfo López Meneses. |**Nombre**: Diana Yazmín Pérez Sabido. |
|**Puesto**: Usuario ACPPI. |**Puesto**: Usuaria ACPPI. |
|**Fecha:** |**Fecha:** |
|  |  |
|**Firma 5** |**Firma 6** |
|**Nombre**: Yesenia Helvetia Delgado Naranjo. |**Nombre**: Alejandro Alfredo Muñoz Núñez. |
|**Puesto**: APE ACPPI. |**Puesto:** RAPE ACPPI. |
|**Fecha**: |**Fecha**: |
|  |  |
|**Firma 7** |**Firma 8** |
|**Nombre**: Luis Angel Olguin Castillo. |**Nombre**: Erick Villa Beltrán. |
|**Puesto**: Enlace ACPPI. |**Puesto**: Líder APE SDMA 6. |
|**Fecha**: |**Fecha**: |
|**  |**  |
|**Firma 9** |**Firma 10** |
|**Nombre**: Juan Carlos Ayuso Bautista. |**Nombre**: Angel Horacio López Alcaraz. |
|**Puesto**: Líder Técnico SDMA 6. |**Puesto**: Analista de Sistemas SDMA 6. |
|**Fecha**:|**Fecha**:|
|**  | |

|||
| :- | :- |

|||Página 9 de 26|
| :- | :-: | -: |

[ref1]: Aspose.Words.103b625d-27a9-42ed-b8eb-1e25e78744cc.003.png
[ref2]: Aspose.Words.103b625d-27a9-42ed-b8eb-1e25e78744cc.004.png
[ref3]: Aspose.Words.103b625d-27a9-42ed-b8eb-1e25e78744cc.005.png
[ref4]: Aspose.Words.103b625d-27a9-42ed-b8eb-1e25e78744cc.006.png
[ref5]: Aspose.Words.103b625d-27a9-42ed-b8eb-1e25e78744cc.007.png
