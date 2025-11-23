|![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_AdministrarInfoComites.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>**8309

**Nombre del Requerimiento:<a name="_hlk156499682"></a>** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<p></p><p><a name="tabla_versiones"></a>Versión</p><p></p>|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Eduardo Acosta Mora|02/02/2024|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|11/03/2024|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|28/05/2024|



**Tabla de Contenido**

[17_3083_ECU_AdministrarInfoComites	2](#_toc167978353)

[1. Descripción	2](#_toc167978354)

[2. Diagrama del Caso de Uso	2](#_toc167978355)

[3. Actores	2](#_toc167978356)

[4. Precondiciones	2](#_toc167978357)

[5. Post condiciones	3](#_toc167978358)

[6. Flujo primario	3](#_toc167978359)

[7. Flujos alternos	8](#_toc167978360)

[8. Referencias cruzadas	21](#_toc167978361)

[9. Mensajes	22](#_toc167978362)

[10. Requerimientos No Funcionales	22](#_toc167978363)

[11. Diagrama de actividad	25](#_toc167978364)

[12. Diagrama de estados	25](#_toc167978365)

[13. Aprobación del cliente	26](#_toc167978366)


### **<a name="_toc167978353"></a>**17\_3083\_ECU\_AdministrarInfoComites

|<h3><a name="_toc167978354"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo del Caso de Uso es permitir al Empleado SAT capturar la información para crear y administrar los comités indispensables para el proyecto.</p><p></p>|
|<h3><a name="_toc167978355"></a>**2. Diagrama del Caso de Uso**</h3>|
|![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.002.png)|
|<h3><a name="_toc167978356"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc167978357"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa al sistema.</p><p>- Se le han asignado los roles requeridos al Empleado SAT para ingresar al módulo “Sistema”, submódulo "Proyectos" y a la sección "Información de comités".</p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar al módulo “Sistema”, submódulo "Proyectos" y a la sección "Información de comités".</p><p>- Se ha almacenado la información de la plantilla con la estructura documental de la fase “Comité”.</p><p></p>|
|<h3><a name="_toc167978358"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT capturó la información necesaria para crear un nuevo comité.</p><p>- El Empleado SAT adjuntó los archivos requeridos.</p><p>- El Empleado SAT administró la información de uno o varios comités.</p><p>- El Empleado SAT descargó los archivos adjuntos.</p><p></p>|
|<h3><a name="_toc167978359"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT ingresa al menú **“Sistema”** y al submódulo **“Proyectos”** y selecciona la opción **“Información de comités”**.|<p>2. Consulta en la base de datos (BD) la siguiente información de los comités:</p><p>&emsp;</p><p>&emsp;- Contrato/Convenio</p><p>&emsp;- Afectación</p><p>&emsp;- Comité</p><p>&emsp;- Fecha de sesión</p><p>&emsp;- Sesión. Aplica la regla de negocio **(RNA63)**</p><p>&emsp;- Acuerdo</p><p>&emsp;- Vigencia</p><p>&emsp;- Monto autorizado (C/IVA)</p><p>&emsp;- Monto en pesos</p>|
||<p>3. <a name="_ref165884468"></a>Muestra la pantalla “Comités” lo siguiente:</p><p>&emsp;</p><p>&emsp;Información de comités:</p><p>&emsp;Tabla (Comités) Aplica la **(RNA244)**:</p><p>&emsp;- Contrato/Convenio</p><p>&emsp;- Afectación</p><p>&emsp;- Comité</p><p>&emsp;- Fecha de sesión</p><p>&emsp;- Sesión (Enlace u opción)</p><p>&emsp;- Acuerdo</p><p>&emsp;- Vigencia</p><p>&emsp;- Monto Autorizado (C/IVA)</p><p>&emsp;- Monto en pesos</p><p>&emsp;- Acciones </p><p>&emsp;- Vista previa ![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.003.png)</p><p>&emsp;- Editar ![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.004.png)</p><p>&emsp;- Eliminar ![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.005.png). Aplica la **(RNA32)**</p><p></p><p>Opciones:</p><p>- Nuevo ![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.006.png)</p><p>&emsp;- Exportar a Excel ![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.007.png)</p><p>&emsp;- Descarga masiva ![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.008.png)</p><p>&emsp;- SATCloud ![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.009.png)</p><p>&emsp;- Campos para “Filtrar” por columna</p><p></p><p>Ver **(17\_3083\_EIU\_AdministrarInfoComites)** Estilos 01.</p>|
|<p>4. <a name="_ref167977412"></a><a name="_ref165884486"></a>Selecciona una opción: </p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Nuevo”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Exportar a Excel”**, continúa en el **([**FA02**](#fa02))**.</p><p></p><p>- En caso de que seleccione la opción **“Vista previa”**, continúa en el **([**FA03**](#fa03))**.</p><p></p><p>- En caso de que seleccione la opción **“Editar”**, continúa en el **([**FA04**](#fa04))**.</p><p></p><p>- En caso de que seleccione la opción **“Eliminar”**, continúa en el **([**FA05**](#fa05))**.</p><p></p><p>- En caso de que seleccione el enlace **“Clic”** o las opciones **“Descarga masiva”**,** o **“SATCloud”** de un registro de la columna **“Sesión”**, continúa en el **([**FA06**](#fa06))**.</p><p></p><p>- En caso de que seleccione la opción para **“Filtrar”** los campos de la tabla, continúa en el **([**FA07**](#fa07))**.</p>|<p>5. Consulta en la BD la información para los siguientes catálogos:</p><p>&emsp;</p><p>&emsp;- Contrato/Convenio</p><p>&emsp;- Contratos. Aplica la **(RNA37)**</p><p>&emsp;- Comité</p><p>&emsp;- Afectación</p><p>&emsp;- Sesión</p><p>- Número de sesión</p><p>- Clasificación</p><p>&emsp;- Plantilla</p><p>&emsp;- Tipo de moneda</p><p></p><p>**Nota:** Para el catálogo “Plantilla”, filtrará todas las plantillas que tengan la fase “Comité”.</p>|
||<p>6. <a name="_ref165887352"></a>Muestra en una ventana emergente la pantalla “Información del comité”, lo siguiente:</p><p></p><p>General:</p><p>- Contrato/Convenio\*</p><p>&emsp;- Contratos\*</p><p>&emsp;- Fecha de sesión\*</p><p>&emsp;- Comité\*</p><p>&emsp;- Afectación\*</p><p>&emsp;- Acuerdo</p><p>&emsp;- Vigencia</p><p>&emsp;- Sesión</p><p>- Número de sesión</p><p>- Clasificación</p><p>&emsp;- Plantilla\*</p><p></p><p>Muestra la pantalla  “Estructura documental”.</p><p></p><p>Ver **(17\_3083\_EIU\_GestionDocumental)** Estilos 01.</p><p></p><p>Monetario:</p><p>- Monto autorizado (C/IVA)</p><p>&emsp;- Tipo moneda</p><p>&emsp;- Tipo de cambio</p><p>&emsp;- Monto en pesos</p><p>&emsp;- Comentarios</p><p></p><p>Opciones:</p><p>- Cerrar ventana ![ref1]</p><p>&emsp;- Cancelar</p><p>&emsp;- Guardar</p><p></p><p>Ver **(17\_3083\_EIU\_AdministrarInfoComites)** Estilos 02.</p>|
|<p>7. <a name="_ref165885396"></a>Ingresa los datos correspondientes de la sección **“General”** que se le solicitan.</p><p>&emsp;</p><p>&emsp;</p>|<p>8. Identifica si en el campo comité si se seleccionó el valor “Contrato” o “CM” en el campo “Comité”, inactiva el campo “Plantilla” de acuerdo con la **(RNA33)**, el flujo continúa en el paso [**13**](#_ref167977365) de este flujo.</p><p>&emsp;</p><p>- En caso de que se seleccione un valor diferente a “Contrato” o “CM”, continúa en el paso **[**9**](#_ref167977377)** de este flujo.</p>|
|9. <a name="_ref167977377"></a>Selecciona una opción en al campo **“Plantilla”** para la gestión documental.|10. Carga la estructura de la plantilla seleccionada en la tabla “Estructura documental”.|
|11. ` `Si lo requiere, realiza alguna opción de la tabla **“Estructura documental”**.|12. El proceso se realiza en el **(17\_3083\_ECU\_GestionDocumental)** y continúa en el paso [**13**](#_ref167977365) de este flujo.|
|13. <a name="_ref167977365"></a>Ingresa los datos solicitados en la sección **“Monetario”**.|14. Calcula automáticamente el campo “Monto en pesos” de acuerdo con la **(RNA36)**.|
|<p>15. <a name="_ref167977550"></a><a name="_ref165884590"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Guardar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”** o **“Cerrar ventana”**, continúa en el **([**FA08**](#fa08))**.</p>|<p>16. Valida los campos obligatorios de acuerdo con la **(RNA03)** y **(RNA33)**.</p><p>&emsp;</p><p>- En caso de no haber ingresado información en los campos obligatorios, continúa en el **([**FA10**](#fa10))**.</p>|
||<p>17. Valida la fecha de sesión de acuerdo con la **(RNA34)**.</p><p>&emsp;</p><p>- En caso de que la fecha de sesión sea incorrecta, continúa en el **([**FA11**](#fa11))**.</p>|
||<p>18. Valida la estructura de la información ingresada en los campos, de acuerdo con la **(RNA35)**.</p><p>&emsp;</p><p>- En caso de que la estructura de la información ingresada en los campos sea incorrecta, continúa en el **([**FA12**](#fa12))**.</p>|
||<p>19. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyecto-Informacióndecomités</p><p>Fecha y Hora = Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- ID de registro</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA01**](#fa01))**.</p>|
||<p>20. Guarda en la BD la siguiente información y asocia el proyecto con la plantilla seleccionada:</p><p>&emsp;</p><p>&emsp;- Contrato/Convenio</p><p>&emsp;- Contratos</p><p>&emsp;- Fecha de sesión</p><p>&emsp;- Comité</p><p>&emsp;- Afectación</p><p>&emsp;- Sesión</p><p>- Número de sesión</p><p>- Clasificación</p><p>&emsp;- Acuerdo</p><p>&emsp;- Vigencia</p><p>&emsp;- Plantilla</p><p>&emsp;- Monto autorizado (C/IVA)</p><p>&emsp;- Tipo moneda</p><p>&emsp;- Tipo de cambio</p><p>&emsp;- Monto en pesos</p><p>&emsp;- Comentarios</p>|
||21. Activa las opciones “Guardar” y “Cancelar” de la tabla “Estructura documental”.|
|<p>22. Selecciona una opción:</p><p></p><p>- En caso de que seleccione la opción “Guardar” de la tabla “Estructura documental”, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción “Cancelar” de la tabla “Estructura documental”, el flujo continúa.</p>|<p>23. Identifica que opción es seleccionada de la tabla “Estructura documental” y realiza lo siguiente:</p><p>&emsp;</p><p>- Si selecciona la opción “Guardar”, el proceso se realiza en el **(FA10)** del **(17\_3083\_ECU\_GestionDocumental)**.</p><p></p><p>- Si selecciona la opción “Cancelar”, el proceso se realiza en el **(FA15)** del **(17\_3083\_ECU\_GestionDocumental)**.</p>|
||24. Muestra el mensaje **([**MSG002**](#msg002))** con la opción “Aceptar”.|
|25. Selecciona la opción **“Aceptar”**.|26. Cierra el mensaje.|
||27. Fin del Caso de Uso.|

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc167978360"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 No se pueden almacenar las Pistas de Auditoría**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA01** inicia cuando interviene un evento ajeno y no se puede almacenar las pistas de auditoría.|
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje de acuerdo con lo siguiente:</p><p></p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT** e **INSR**, se muestra el **([**MSG007**](#msg007))**.</p><p></p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG008**](#msg008))**.</p><p></p><p>- En caso de que la pista de auditoría es por el tipo de movimiento **PRNT**, se muestra el **([**MSG009**](#msg009))**.</p><p></p><p>- En caso de que la pista de auditoría es por el tipo de movimiento **DLT**, se muestra el **([**MSG010**](#msg010))**.</p><p></p><p>Cada mensaje con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría. |

|<p></p><p><a name="fa02"></a>**FA02 Selecciona la opción “Exportar a Excel”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA02** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. Consulta en la BD la siguiente información de los comités: </p><p>&emsp;</p><p>&emsp;- Contrato/Convenio</p><p>&emsp;- Contratos</p><p>&emsp;- Fecha de sesión</p><p>&emsp;- Comité</p><p>&emsp;- Afectación</p><p>&emsp;- Acuerdo</p><p>&emsp;- Vigencia</p><p>&emsp;- Sesión</p><p>- Número de sesión</p><p>- Clasificación</p><p>&emsp;- Monto autorizado (C/IVA)</p><p>&emsp;- Tipo de moneda</p><p>&emsp;- Tipo de cambio</p><p>&emsp;- Monto en pesos</p><p>&emsp;- Comentarios</p>|
||<p>3. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Proyecto-Informacióndecomités</p><p>Fecha y Hora = Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= </p><p>**CNST** (Consultar)</p><p>**PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- Nombre corto del proyecto</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA01**](#fa01))**.</p>|
||4. Genera un archivo de Excel con extensión (.xlsx) que contenga la información de la consulta anterior.|
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||6. Continúa en el paso [**4**](#_ref167977412) del Flujo primario.|

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p><a name="fa03"></a>**FA03 Selecciona la opción “Vista previa”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA03** inicia cuando el Empleado SAT selecciona la opción **“Vista previa”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Proyecto-Informacióndecomités</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consultar)</p><p>**Movimiento**= </p><p>- ID proyecto </p><p>- ID de registro</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA01**](#fa01))**.</p>|
||3. Consulta en la BD la información del registro seleccionado.|
||4. Consulta en la BD la información de la tabla  “Estructura documental” relacionada con el registro seleccionado.|
||<p>5. Muestra en una ventana emergente (de solo consulta), la pantalla “Información del Comité”.</p><p>&emsp;</p><p>&emsp;Datos:</p><p>&emsp;- Contrato/Convenio</p><p>&emsp;- Contratos</p><p>&emsp;- Fecha de sesión</p><p>&emsp;- Comité</p><p>&emsp;- Afectación</p><p>&emsp;- Sesión</p><p>&emsp;- Acuerdo</p><p>&emsp;- Vigencia</p><p>&emsp;- Monto autorizado (C/IVA)</p><p>&emsp;- Tipo de moneda</p><p>&emsp;- Tipo de cambio</p><p>&emsp;- Monto en pesos</p><p>&emsp;- Comentarios</p><p></p><p>Muestra la pantalla  “Estructura documental”.</p><p></p><p>Ver **(17\_3083\_EIU\_GestionDocumental)** Estilos 01.</p><p></p><p>Opciones:</p><p>- Guardar (Inhabilitado)</p><p>&emsp;- Cerrar ventana ![ref2]</p><p>&emsp;- Cancelar (Inhabilitado)</p><p></p><p>Ver **(17\_3083\_EIU\_AdministrarInfoComites)** Estilos 02.</p>|
|6. Selecciona la opción **“Cerrar ventana”**.|7. Cierra la ventana emergente.|
||8. Continúa en el paso [**4**](#_ref167977412) del Flujo primario. |

|<p></p><p><a name="fa04"></a>**FA04 Selecciona la opción “Editar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA04** inicia cuando el Empleado SAT selecciona la opción **“Editar”**.|<p>2. Consulta en la BD la información para los siguientes catálogos:</p><p>&emsp;</p><p>&emsp;- Contrato/Convenio</p><p>&emsp;- Contratos. Aplica la **(RNA37)**</p><p>&emsp;- Comité</p><p>&emsp;- Afectación</p><p>&emsp;- Sesión</p><p>- Número de sesión</p><p>- Clasificación</p><p>&emsp;- Plantilla</p><p>&emsp;- Tipo de moneda</p><p></p><p>**Notas**: la opción que mostrará por defecto será la misma que fue seleccionada al hacer el registro.</p><p>Para el catálogo “Plantilla”, filtrará todas las plantillas que tengan la fase “Comité”.</p>|
||3. Consulta en la BD la información relacionada con el registro seleccionado.|
||<p>4. <a name="_ref165887445"></a>Muestra en una ventana emergente la pantalla “Información del comité”, lo siguiente:</p><p></p><p>General:</p><p>- Contrato/Convenio\*</p><p>&emsp;- Contratos\*</p><p>&emsp;- Fecha de sesión\*</p><p>&emsp;- Comité\*</p><p>&emsp;- Afectación\*</p><p>&emsp;- Acuerdo</p><p>&emsp;- Vigencia</p><p>&emsp;- Sesión</p><p>- Número de sesión</p><p>- Clasificación</p><p>&emsp;- Plantilla\*</p><p></p><p>Muestra la pantalla  “Estructura documental”.</p><p></p><p>Ver **(17\_3083\_EIU\_GestionDocumental)** Estilos 01.</p><p></p><p>Monetario:</p><p>- Monto autorizado (C/IVA)</p><p>&emsp;- Tipo moneda</p><p>&emsp;- Tipo de cambio</p><p>&emsp;- Monto en pesos</p><p>&emsp;- Comentarios</p><p></p><p>Opciones:</p><p>- Cerrar ventana ![ref1]</p><p>&emsp;- Cancelar</p><p>&emsp;- Guardar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_AdministrarInfoComites)** Estilos 02.</p>|
|5. <a name="_ref165885454"></a>Edita la información que requiera de la sección “**General”**, si lo requiere, selecciona una opción en el campo **“Plantilla”** para la gestión documental.|<p>6. Si se seleccionó una nueva plantilla, valida que en la gestión documental no se haya cargado ningún archivo.</p><p>&emsp;</p><p>- En caso de que se haya cargado un archivo, continúa en el **([**FA13**](#fa13))**.</p>|
||7. Carga la plantilla en la tabla “Gestión documental”.|
|8. Ingresa los datos solicitados en la sección **“Monetario”**.|9. Calcula automáticamente el campo “Monto en pesos” de acuerdo con la **(RNA36)**.|
|<p>10. <a name="_ref167977588"></a><a name="_ref165884628"></a>Selecciona una opción:</p><p>&emsp;</p><p>&emsp;En caso de que seleccione la opción **“Guardar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”** o **“Cerrar”**, continúa en el **([**FA08**](#fa08))**. </p>|<p>11. Valida los campos obligatorios de acuerdo con la **(RNA03)** y **(RNA33)**.</p><p>&emsp;</p><p>- En caso de no haber ingresado información en los campos obligatorios, continúa en el **([**FA10**](#fa10))**.</p>|
||<p>12. Valida la fecha de sesión de acuerdo con la **(RNA34)**.</p><p>&emsp;</p><p>- En caso de que la fecha de sesión sea incorrecta, continúa en el **([**FA11**](#fa11))**.</p>|
||<p>13. Valida la estructura de la información ingresada en los campos de acuerdo con la **(RNA35)**.</p><p>&emsp;</p><p>- En caso de que la estructura de la información ingresada en los campos sea incorrecta, continúa en el **([**FA12**](#fa12))**.</p>|
||<p>14. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyecto-Informacióndecomités</p><p>Fecha y Hora = Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- ID de registro</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA01**](#fa01))**.</p>|
||<p>15. Guarda en la BD la siguiente información y asocia el proyecto con la plantilla seleccionada:</p><p>&emsp;</p><p>&emsp;- Contrato/Convenio</p><p>&emsp;- Contratos</p><p>&emsp;- Fecha de sesión</p><p>&emsp;- Comité</p><p>&emsp;- Afectación</p><p>&emsp;- Sesión</p><p>- Número de sesión</p><p>- Clasificación</p><p>&emsp;- Acuerdo</p><p>&emsp;- Vigencia</p><p>&emsp;- Plantilla</p><p>&emsp;- Monto autorizado (C/IVA)</p><p>&emsp;- Tipo moneda</p><p>&emsp;- Tipo de cambio</p><p>&emsp;- Monto en pesos</p><p>&emsp;- Comentarios</p>|
||16. Activa las opciones “Guardar” y “Cancelar ” de la tabla “Estructura documental”.|
|<p>17. Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción “Guardar” de la tabla “Estructura documental”, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción “Cancelar” de la tabla “Estructura documental”, el flujo continúa.</p>|<p>18. Identifica que opción es seleccionada de la tabla “Estructura documental” y realiza lo siguiente:</p><p></p><p>- Si selecciona la opción “Guardar”, el proceso se realiza en el **(FA10)** del **(17\_3083\_ECU\_GestionDocumental)**.</p><p></p><p>- Si selecciona la opción “Cancelar”, el proceso se realiza en el **(FA15)** del **(17\_3083\_ECU\_GestionDocumental)**.</p>|
||19. Muestra el **([**MSG002**](#msg002))** con la opción “Aceptar”.|
|20. Selecciona la opción **“Aceptar”**.|21. Cierra el mensaje.|
||22. Continúa en el paso [**4**](#_ref167977412) del Flujo primario.|

|<p></p><p><a name="fa05"></a>**FA05 Selecciona la opción “Eliminar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT selecciona la opción **“Eliminar”**.|<p>2. Valida que en la estructura documental no se haya cargado ningún archivo.</p><p>&emsp;</p><p>- En caso de que se haya cargado un archivo, continúa en el **([**FA13**](#fa13))**.</p>|
||3. Muestra el **([**MSG003**](#msg003))** con las opciones “Sí” y “No”.|
|<p>4. Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“No”**, continúa en el paso [**4**](#_ref167977412) del Flujo primario. </p>|<p>5. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyecto-Informacióndecomités</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **DLT** (Borrar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- ID de registro</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA01**](#fa01))**.</p>|
||6. Actualiza el estatus del registro a “Eliminado” en la BD y elimina la relación del comité con la plantilla.|
||7. Actualiza en la pantalla la tabla “Comités” ocultando el registro eliminado.|
||8. Continúa en el paso [**4**](#_ref167977412) del Flujo primario.|

|<p></p><p><a name="fa06"></a>**FA06 Selecciona las opciones o enlace “Sesión”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT selecciona la opción o enlace **“Sesión”**.|<p>Identifica que opción es seleccionada:</p><p></p><p>- En el caso de que se haya seleccionado el enlace, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción “Descarga masiva”, continúa en el **([**FA14**](#fa14))**.</p><p></p><p>- En caso de que seleccione la opción “SATCloud”, continúa en el **([**FA15**](#fa15))**.</p>|
||<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Proyecto-Informacióndecomités</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- Nombre del documento descargado</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA01**](#fa01))**.</p>|
||3. Consulta en la BD el archivo adjunto relacionado con el comité.|
||4. Descarga el archivo adjunto.|
||5. Continúa en el paso [**4**](#_ref167977412) del Flujo primario.|

|<p></p><p><a name="fa07"></a>**FA07 Selecciona la opción para filtrar los campos de la tabla**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA07** inicia cuando el Empleado SAT selecciona la opción para **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada, de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||<p>5. Continúa en el paso [**4**](#_ref167977412) del Flujo primario.</p><p></p>|

|<p></p><p><a name="fa08"></a>**FA08 Selecciona la opción “Cancelar” o “Cerrar ventana”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA08** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”** o **“Cerrar ventana”**.|2. Muestra el **([**MSG004**](#msg004))** con las opciones “Sí” y “No”.|
|<p>3. Selección una opción: </p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“No”** desde el paso 15 del Flujo primario, continúa en el paso [**15**](#_ref167977550) del Flujo primario.</p><p></p><p>- En caso de que seleccione la opción **“No”** desde el paso 10 del **([**FA04**](#fa04))**,** continúa en el paso [**10**](#_ref167977588) del **([**FA04**](#fa04))**.</p>|4. Cierra el mensaje.|
||5. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>6. Dependiendo la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso 15 del Flujo primario, continúa en el paso [**4**](#_ref167977412) del Flujo primario.</p><p></p><p>- Si fue invocado en el paso 10 del **([**FA04**](#fa04))**, continúa en el paso [**4**](#_ref167977412) del Flujo primario.</p>|

|<p></p><p><a name="fa09"></a>**FA09 Error al generar el enlace y contraseña** </p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA09** inicia cuando no se muestra el enlace y la contraseña.|
||2. Muestra el **([**MSG012**](#msg012))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso [**4**](#_ref167977412)[](#_ref164767531) del Flujo primario.|

|<p></p><p><a name="fa10"></a>**FA10 Información en campos obligatorios no ingresada**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA10** inicia cuando el sistema identifica que no se ingresó información en los campos obligatorios.|
||2. Muestra en rojo los campos pendientes de capturar.|
||3. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||<p>6. Dependiendo la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso 16 del Flujo primario, continúa en el paso [**7**](#_ref165885396) del Flujo primario.</p><p></p><p>- Si fue invocado en el paso 11 del **([**FA04**](#fa04))**, continúa en el paso [**5**](#_ref165885454) del **([**FA04**](#fa04))**.</p>|

|<p></p><p><a name="fa11"></a>**FA11 La fecha de sesión es incorrecta**.</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA11** inicia cuando el sistema identifica que la fecha de sesión es incorrecta.|
||2. Muestra el **([**MSG006**](#msg006))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||<p>5. Dependiendo la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso 17 del Flujo primario, continúa en el paso [**7**](#_ref165885396)** del Flujo primario.</p><p></p><p>- Si fue invocado en el paso 12 del **([**FA04**](#fa04))**, continúa en el paso [**5**](#_ref165885454) del **([**FA04**](#fa04))**.</p>|

|<p></p><p><a name="fa12"></a>**FA12 Estructura de información ingresada incorrecta**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA12** inicia cuando el sistema identifica que la estructura de la información ingresada es incorrecta.|
||2. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||<p>5. Dependiendo la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso 18 del Flujo primario, continúa en el paso [**7**](#_ref165885396) del Flujo primario.</p><p></p><p>- Si fue invocado en el paso 13 del **([**FA04**](#fa04))**, continúa en el paso [**5**](#_ref165885454) del **([**FA04**](#fa04))**.</p>|

|<p></p><p><a name="fa13"></a>**FA13 Ya se ha cargado un archivo en la plantilla**</p>|
| :- |

|Actor|Sistema|
| :-: | :-: |
||1. El **FA13** inicia cuando ya se ha cargado un archivo en la plantilla.|
||2. Muestra el **([**MSG011**](#msg011))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||<p>5. Dependiendo de la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso 6 del **([**FA04**](#fa04))**, continúa en el paso [**5**](#_ref165885454) **([**FA04**](#fa04))**.</p><p></p><p>- Si fue invocado en el paso 2 del **([**FA05**](#fa05))**, continúa en el paso [**4**](#_ref167977412) del Flujo primario.</p>|

|<p></p><p><a name="fa14"></a>**FA14 Selecciona la opción “Descarga masiva”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA14** inicia cuando el Empleado SAT selecciona la opción **“Descarga masiva”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Proyecto-Informacióndecomités</p><p>Fecha y Hora = Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- Nombre de los documentos descargados en una cadena por separado por | (pipes)</p><p>- Ejemplo: (01\_FTO\_JC\_MDR4.PDF|SCP\_MDR4.PDF</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA01**](#fa01))**.</p>|
||3. Consulta en la BD los archivos adjuntos relacionados con el comité.|
||4. Descarga el archivo en extensión (.ZIP) que contiene los archivos adjuntos.|
||5. Continúa en el paso [**4**](#_ref167977412) del Flujo primario.|

|<p></p><p><a name="fa15"></a>**FA15 Selecciona la opción “SATCloud”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA15** inicia cuando el Empleado SAT selecciona la opción **“SATCloud”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Proyecto-Informacióndecomités</p><p>Fecha y Hora = Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- Nombre de los documentos descargados en una cadena por separado por | (pipes)</p><p>- Ejemplo: (01\_FTO\_JC\_MDR4.PDF|SCP\_MDR4.PDF</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA01**](#fa01))**.</p>|
||<p>3. Muestra en una ventana emergente la pantalla “Datos de la descarga” lo siguiente: </p><p>&emsp;</p><p>&emsp;Campos:</p><p>&emsp;- url (enlace)</p><p>&emsp;- contraseña</p><p></p><p>Opciones:</p><p>- Copiar contraseña ![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.012.png) </p><p>&emsp;- Cerrar</p><p></p><p>- En caso de que no se muestre el enlace y la contraseña, continúa en el **([**FA09**](#fa09))**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_AdministrarInfoComites)** Estilos 03.</p>|
|<p>4. Realiza lo siguiente:</p><p>&emsp;</p><p>- En caso de que se seleccione la opción **“Copiar contraseña”** y seleccione el enlace, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cerrar”**, continúa en el paso **4** del Flujo primario. </p>|5. Realiza la conexión con el SATCloud en una nueva ventana para mostrar los archivos a descargar.|
|6. Realiza lo correspondiente para descargar los documentos adjuntos.|7. Descarga el archivo con extensión (.ZIP).|
||8. Continúa en el paso [**4**](#_ref167977412) del Flujo primario.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc167978361"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_EIU\_AdministrarInfoComites</p><p>- 17\_3083\_EIU\_GestionDocumental</p><p>- 17\_3083\_ECU\_GestionDocumental</p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p></p>|
|<h3><a name="_toc167978362"></a>**9. Mensajes**</h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|La estructura de la información ingresada es incorrecta. Intente nuevamente.|
|<a name="msg002"></a>**MSG002**|Información guardada correctamente.|
|<a name="msg003"></a>**MSG003**|¿Está seguro de que desea continuar?|
|<a name="msg004"></a>**MSG004**|<p>Se perderán todos los cambios realizados.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg005"></a>**MSG005**|Favor de ingresar los datos obligatorios marcados con un asterisco (\*).|
|<a name="msg006"></a>**MSG006**|La fecha de sesión no puede ser mayor a la fecha actual.|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg009"></a>**MSG009**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg010"></a>**MSG010**|Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).|
|<a name="msg011"></a>**MSG011**|<p>No se puede realizar la acción, ya que la plantilla contiene documentos cargados. </p><p>Para continuar, elimine primero los documentos cargados.</p>|
|<a name="msg012"></a>**MSG012**|Error al generar el enlace y contraseña. Intente nuevamente.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc167978363"></a>**10. Requerimientos No Funcionales** </h3>|
||

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001**|Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|
|**RNF002**|Concurrencia|<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p></p><p>El número de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT.</p>|
|**RNF003**|Seguridad|El acceso solo podrá ser otorgado a todo Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema.|
|**RNF004**|Usabilidad|<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación:  </p><p></p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual, como guía para el Empleado SAT.  </p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado.  </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales.</p>|
|**RNF005**|Eficiencia|Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT. |
|**RNF006**|Usabilidad|<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100:  </p><p>  </p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta).  </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta).  </p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).  </p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual, con el resultado de la consulta).  </p><p>  </p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente. </p>|
|**RNF007**|Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.   |
|**RNF008**|Fiabilidad|El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema. |
|**RNF009**|Seguridad|Mantener la información en pantalla en caso de un error al guardar las pistas de auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema. |
|**RNF010**|Integridad|Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena. |

||
| :- |
|<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
|<h3><a name="_toc167978364"></a>**11. Diagrama de actividad** </h3>|
|<p></p><p>![](Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.013.png)</p><p></p>|
|<h3><a name="_toc167978365"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No Aplica, no hay cambios significativos de estados ni transiciones.</p><p></p>|
|<h3><a name="_toc167978366"></a>**13. Aprobación del cliente** </h3>|
||

|<a name="_hlk159937993"></a>**FIRMAS DE CONFORMIDAD**||
| :-: | :- |
|**Firma 1** |**Firma 2** |
|**Nombre**: María del Carmen Castillejos Cárdenas.|**Nombre**: Rubén Delgado Ramírez.|
|**Puesto**: Usuaria ACPPI.|**Puesto**: Usuario ACPPI.|
|**Fecha:**|**Fecha:**|
|||
|**Firma 3** |**Firma 4**|
|**Nombre**: Rodolfo López Meneses.|**Nombre**: Diana Yazmín Pérez Sabido.|
|**Puesto**: Usuario ACPPI.|**Puesto**: Usuaria ACPPI.|
|**Fecha:**|**Fecha:**|
|||
|**Firma 5**|**Firma 6**|
|**Nombre**: Yesenia Helvetia Delgado Naranjo.|**Nombre:** Alejandro Alfredo Muñoz Núñez.|
|**Puesto**: APE ACPPI.|**Puesto:** RAPE ACPPI.|
|**Fecha**:|**Fecha**:|
|||
|**Firma 7**|**Firma 8**|
|**Nombre**: Luis Angel Olguin Castillo.|**Nombre**: Erick Villa Beltrán.|
|**Puesto**: Enlace ACPPI.|**Puesto**: Líder APE SDMA 6.|
|**Fecha**:|**Fecha**:|
|||
|**Firma 9**|**Firma 10**|
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:** Eduardo Acosta Mora.|
|**Puesto:** Líder Técnico SDMA 6.|**Puesto:** Analista SDMA 6.|
|**Fecha**:|**Fecha**:|
|||

|<p></p><p></p>|
| :- |

|||Página 1 de 9|
| :- | :-: | -: |

[ref1]: Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.010.png
[ref2]: Aspose.Words.7c81d30f-d018-4bc7-9659-f8945ec6e5ec.011.png
