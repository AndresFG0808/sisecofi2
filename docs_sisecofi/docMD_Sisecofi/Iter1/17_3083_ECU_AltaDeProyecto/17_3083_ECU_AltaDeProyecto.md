|![](Aspose.Words.0f14b42d-9ce5-4360-bd7d-16dc5da7d479.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_AltaDeProyecto.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Isabel Adriana Valdez Cortés|*29/01/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*15/02/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*20/05/2024*|



**Tabla de Contenido**

[17_3083_ECU_AltaDeProyecto	2](#_toc167610087)

[1. Descripción	2](#_toc167610088)

[2. Diagrama del Caso de Uso	2](#_toc167610089)

[3. Actores	2](#_toc167610090)

[4. Precondiciones	2](#_toc167610091)

[5. Post condiciones	3](#_toc167610092)

[6. Flujo primario	3](#_toc167610093)

[7. Flujos alternos	6](#_toc167610094)

[8. Referencias cruzadas	17](#_toc167610095)

[9. Mensajes	18](#_toc167610096)

[10. Requerimientos No Funcionales	18](#_toc167610097)

[11. Diagrama de actividad	21](#_toc167610098)

[12. Diagrama de estados	21](#_toc167610099)

[13. Aprobación del cliente	22](#_toc167610100)






### **<a name="_toc167610087"></a>**17\_3083\_ECU\_AltaDeProyecto

|<h3><a name="_toc167610088"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT gestionar el o los proyectos administrados; incluye la creación de un nuevo proyecto, con un identificador único, el registro de la información relevante, la consulta y/o edición de la información del proyecto, así como la cancelación y cierre de este.</p><p></p>|
|<h3><a name="_toc167610089"></a>**2**. **Diagrama del Caso de Uso**</h3>|
|![](Aspose.Words.0f14b42d-9ce5-4360-bd7d-16dc5da7d479.002.png)|
|<h3><a name="_toc167610090"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

||
| :- |
|<h3><a name="_toc167610091"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa.</p><p>- Se han asignado proyectos al Empleado SAT.</p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar al módulo “Proyectos”.</p><p>- El Empleado SAT ha ingresado a la opción del menú “Proyectos”, de acuerdo con el proceso del **(17\_3083\_ECU\_AccesoSistema)**.</p><p>&emsp;</p>|
|<h3><a name="_toc167610092"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT dio de alta un nuevo proyecto y capturó la información correspondiente.</p><p>- El sistema almacenó la información del nuevo proyecto, asignándole un identificador (Id) único.</p><p>- El empleado SAT consultó la información del proyecto.</p><p>&emsp;</p>|
|<h3><a name="_toc167610093"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT selecciona la opción del menú **“Proyectos”**.|2. Identifica el rol del Empleado SAT que ingresa para mostrar las opciones correspondientes de acuerdo con la regla de negocio **(RNA31)**.|
||<p>3. <a name="_ref167102233"></a>Obtiene de la base de datos (BD), la información con estado “Activo” de los siguientes catálogos:</p><p>&emsp;</p><p>- Estatus de proyecto</p><p>- Administraciones Centrales</p><p>- Áreas</p>|
||4. Consulta el nombre corto de los proyectos que estén asignados al Empleado SAT que ingresa al módulo.|
||<p>5. <a name="_ref158409910"></a>Muestra la pantalla del módulo “Proyectos” con lo siguiente:</p><p>&emsp;</p><p>&emsp;Búsqueda, aplica la **(RNA240)**:</p><p>&emsp;</p><p>- Estatus\*. Aplica la **(RNA30)**</p><p>- Nombre corto del proyecto</p><p>- Id proyecto</p><p>- Área solicitante</p><p>- Área responsable</p><p>- Líder de proyecto</p><p>&emsp;</p><p>Opciones:</p><p></p><p>- ![](Aspose.Words.0f14b42d-9ce5-4360-bd7d-16dc5da7d479.003.png)Buscar (habilitado)</p><p>- Nuevo proyecto </p><p>&emsp;(habilitado)</p><p>- ![](Aspose.Words.0f14b42d-9ce5-4360-bd7d-16dc5da7d479.004.png)Exportar a Excel</p><p>&emsp;(inhabilitado)</p><p>Tabla “Proyectos registrados”, de inicio sin información. Aplica la **(RNA244)**:</p><p>- Nombre corto del proyecto</p><p>- Fecha inicio</p><p>- Fecha fin</p><p>- Líder de proyecto</p><p>- Área solicitante</p><p>- Área responsable</p><p>- Monto solicitado</p><p>- Estatus</p><p>- Plan de trabajo (enlace Ver)</p><p>- Acciones</p><p></p><p>Ver  **(17\_3083\_EIU\_AltaDeProyecto)** Estilos 01.</p>|
|<p>6. <a name="_ref165322946"></a>Selecciona la opción **“Nuevo proyecto”** y el flujo continúa.</p><p>&emsp;</p><p>- Si requiere **“Buscar”**, continúa en el **([**FA01**](#fa01))**.</p>|<p>7. <a name="_ref165324211"></a>Muestra la pantalla para registrar los siguientes datos del proyecto:</p><p>&emsp;</p><p>&emsp;Dato:</p><p>&emsp;</p><p>- Última modificación</p><p>&emsp;</p><p>Sección “Datos generales”, aplica la **(RNA40):**</p><p></p><p>- Id</p><p>- Nombre corto\*</p><p>- Estatus: “Inicial”</p><p>- Nombre del proyecto\*</p><p>- Id AGP\*</p><p>&emsp;</p><p>Opción:</p><p></p><p>- Guardar</p><p></p><p>Secciones inhabilitadas:</p><p>- Ficha técnica</p><p>- Asociar fases</p><p>- Gestión documental</p><p>- Información de comités</p><p>- Plan de trabajo</p><p>- Participación de proveedores</p><p>- Verificación de RCP (Repositorio Central de Proyectos)</p><p></p><p>Opción:</p><p>- Regresar</p><p></p><p>Ver **(17\_3083\_EIU\_AltaDeProyecto)** Estilos 02.</p>|
|<p>8. <a name="_ref158409503"></a>Ingresa los datos generales:</p><p>&emsp;</p><p>- Nombre corto\*</p><p>- Nombre del proyecto\*</p><p>- Id AGP\*</p>||
|<p>9. <a name="_ref165328862"></a>Selecciona la opción **“Guardar”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Regresar”**, continúa en el **([**FA08**](#fa08))**. </p>|<p>10. <a name="_ref158740842"></a>Valida que se hayan capturado los datos obligatorios de acuerdo con las **(RNA40)** y **(RNA03)**.</p><p>&emsp;</p><p>- En caso de identificar que no se ingresaron los datos obligatorios, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>11. <a name="_ref165324138"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyectos</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id proyecto</p><p>- Nombre corto del proyecto</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||<p>12. Verifica que la estructura del dato ingresado en el campo “Id AGP” sea correcta de acuerdo con la **(RNA40)**.</p><p>&emsp;</p><p>- En caso de identificar que la estructura del dato “Id AGP” es incorrecta, continúa en el **([**FA09**](#fa09))**.</p>|
||<p>13. Consulta en la BD el “Id AGP” ingresado y valida que no se encuentra registrado para otro proyecto.</p><p>&emsp;</p><p>- En caso de identificar que el “Id AGP” se encuentra registrado, continúa en el **([**FA10**](#fa10))**.</p>|
||14. Genera el Id del nuevo proyecto de acuerdo con la **(RNA29)**.|
||<p>15. Almacena en la BD la siguiente información del proyecto creado:</p><p>&emsp;</p><p>- Id</p><p>- Nombre corto del proyecto</p><p>- Estatus: Inicial</p><p>- Nombre del proyecto</p><p>- Id AGP</p><p>- Nombre del Empleado SAT. Aplica la **(RNA247)**</p><p>- Fecha y hora de la</p>|
||16. Muestra el mensaje **([**MSG006**](#msg006))**,** con la opción** “Aceptar”.|
|17. Selecciona la opción **“Aceptar”**.|18. Habilita la sección de “Ficha técnica” y muestra la pantalla con los campos actualizados de acuerdo con el movimiento realizado.|
|19. Si el usuario requiere continuar, selecciona la opción **“Ficha técnica”**, el proceso continúa en el **(17\_3083\_ECU\_ModificarProyecto)**.||
||20. Fin del Caso de Uso.|

|<p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc167610094"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 Selección de la opción “Buscar”**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA01** inicia cuando el Empleado SAT requiere **“Buscar”<a name="_ref158132003"></a>**.||
|2. Selecciona el campo **“Estatus\*”**.|3. Despliega en el campo de “Estatus\*” el listado de opciones obtenidas del catálogo “Estatus del proyecto”.|
|4. Selecciona una opción en **“Estatus”**.||
|5. Selecciona el campo **“Nombre corto del proyecto”**.|6. Despliega en el campo de “Nombre corto del proyecto” el listado de proyectos asignados al Empleado SAT.|
|7. Selecciona una opción en **“Nombre corto del proyecto”**.||
|<p>8. Si requiere ingresa el **“Id proyecto”**.</p><p>&emsp;</p><p>- En caso de que no requiera ingresar el **“Id proyecto”**, continúa en el paso [**10**](#_ref167559767)** de este flujo.</p>|9. Inhabilita todos los demás criterios de búsqueda y continúa en el paso [**18**](#_ref167558997) de este flujo.|
|10. <a name="_ref167559767"></a>Selecciona el campo **“Área solicitante”**.|11. Despliega en el campo de “Área solicitante” el listado de opciones obtenidas del catálogo de “Administraciones Centrales”.|
|12. Selecciona una opción en **“Área solicitante”**.|13. <a name="_ref167559638"></a>Consulta en la BD en el catálogos de “Áreas” las opciones relacionadas con el “Área solicitante” seleccionada.|
|14. Selecciona el campo **“Área responsable”**.|15. Despliega en el campo de “Área responsable” el listado de opciones obtenidas en el paso [**13**](#_ref167559638)** de este flujo.|
|16. Selecciona una opción en **“Área responsable”**.||
|17. Captura el nombre del **“Líder de proyecto”**.||
|<p>18. <a name="_ref167558997"></a>Selecciona la opción **“Buscar”**.</p><p>&emsp;</p><p>- En caso de seleccionar **“Nuevo proyecto”**, en el paso [7](#_ref165324211) del Flujo primario.</p>|<p>19. <a name="_ref158740888"></a>Valida que se haya seleccionado como mínimo una opción en el campo “Estatus\*” o que se haya ingresado el “Id proyecto” para la búsqueda, aplica la **(RNA03)**.</p><p>&emsp;</p><p>- En caso de identificar que no fue seleccionada una opción, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>20. <a name="_ref165324294"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyectos</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consultar)</p><p>**Movimiento**= </p><p>- Datos seleccionados o ingresados en los criterios de búsqueda.</p><p>&emsp;</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||<p>21. Consulta en la BD si existen proyectos que coincidan con los criterios ingresados y que estén asignados al Empleado SAT que realiza la búsqueda.</p><p>&emsp;</p><p>- En caso de identificar que no existen proyectos que coincidan, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>22. Obtiene de la BD la siguiente información de los proyectos del resultado de la consulta anterior conforme a la **(RNA39)**:</p><p></p><p>- Nombre corto del proyecto</p><p>- Fecha inicio</p><p>- Fecha fin</p><p>- Líder de proyecto</p><p>- Área solicitante</p><p>- Área responsable</p><p>- Monto solicitado</p><p>- Estatus</p>|
||23. Busca en la BD el plan de trabajo cargado previamente para cada proyecto.|
||24. Habilita la opción “Exportar a Excel”.|
||<p>25. <a name="_ref165324645"></a>Muestra en la pantalla de “Proyectos”** la tabla con la información obtenida de los proyectos conforme a la **(RNA39)**:</p><p>&emsp;</p><p>&emsp;Tabla “Proyectos registrados”. Aplica la **(RNA244)**:</p><p>&emsp;</p><p>- Nombre corto del proyecto</p><p>- Fecha inicio</p><p>- Fecha fin</p><p>- Líder de proyecto</p><p>- Área solicitante</p><p>- Área responsable</p><p>- Monto solicitado</p><p>- Estatus</p><p>- Plan de trabajo (enlace “Ver”)</p><p>- Acciones</p><p>- Editar ![](Aspose.Words.0f14b42d-9ce5-4360-bd7d-16dc5da7d479.005.png)</p><p>- ![](Aspose.Words.0f14b42d-9ce5-4360-bd7d-16dc5da7d479.006.png)Ver detalle </p><p>- Campos para “Filtrar” por columna</p><p></p><p>Ver **(17\_3083\_EIU\_AltaDeProyecto)** Estilos 01.</p>|
|<p>26. <a name="_ref158827313"></a>Selecciona una opción:</p><p>&emsp;</p><p>- Si selecciona **“Exportar a Excel”**, continúa en el **([**FA04**](#fa04))**.</p><p>&emsp;</p><p>- Si selecciona el enlace **“Ver”** en la columna “Plan de trabajo”,** el proceso continúa en el **(17\_3083\_ECU\_ModificarPlanDeTrabajo)**.</p><p>&emsp;</p><p>- Si selecciona **“Editar”**, el proceso continúa en el **(17\_3083\_ECU\_ModificarProyecto)**.</p><p>&emsp;</p><p>- En caso de seleccionar **“Ver detalle”**, continúa en el **([**FA07**](#fa07))**.</p><p>&emsp;</p><p>- En caso de requerir **“Filtrar”** la información en alguna columna de la tabla, continúa en el **([**FA06**](#fa06))**.</p>||
||27. Fin del Caso de Uso.|

|<p></p><p><a name="fa02"></a>**FA02 No existen proyectos que coincidan con la búsqueda**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA02** inicia cuando el sistema identifica que no existen proyectos que coincidan con los criterios de búsqueda.|
||2. Muestra el **([**MSG002**](#msg002))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”.**|4. Cierra el mensaje, limpia los criterios de búsqueda.|
||5. Regresa al paso [**3**](#_ref167102233)** del Flujo primario.|

|<p></p><p></p><p><a name="fa03"></a>**FA03 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA03** inicia cuando interviene un evento ajeno y no se puedan almacenar las Pistas de Auditoría. |
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje informativo de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT** o **INSR**, se muestra el **([**MSG007**](#msg007))**.</p><p>&emsp;</p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG008**](#msg008))**.</p><p>&emsp;</p><p>- En caso de que la pista de auditoría sea por el tipo de movimiento **PRNT**, se muestra el **([**MSG009**](#msg009))**. </p><p>&emsp;</p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría.|

|<p></p><p></p><p><a name="fa04"></a>**FA04 Selección de “Exportar a Excel”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA04** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. <a name="_ref165324497"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyectos-Sección en la que fue invocado</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Nombre corto del proyecto</p><p>- Fecha inicio</p><p>- Fecha fin</p><p>- Líder de proyecto</p><p>- Área solicitante</p><p>- Área responsable</p><p>- Monto solicitado</p><p>- Estatus</p><p>&emsp;</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||<p>3. Obtiene la siguiente información de los proyectos que se muestran en la tabla “Proyectos registrados”:</p><p>&emsp;</p><p>- Nombre corto del proyecto</p><p>- Fecha inicio</p><p>- Fecha fin</p><p>- Líder de proyecto</p><p>- Área solicitante</p><p>- Área responsable</p><p>- Monto solicitado</p><p>- Estatus</p>|
||4. Genera un archivo de Excel con extensión (.xlsx) que contenga la información obtenida. |
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||6. Fin del Caso de Uso.|

|<p></p><p><a name="fa05"></a>**FA05 No se ingresaron los datos obligatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA05** inicia cuando el sistema identifica que no fue seleccionada una opción del campo obligatorio.|
||2. Muestra en rojo los campos pendientes de capturar. |
||3. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje. |
||<p>6. Realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso [10](#_ref158740842) del Flujo primario, regresa al paso [**8**](#_ref158409503) del Flujo primario.</p><p>- Si fue invocado en el paso [19](#_ref158740888) del **([**FA01**](#fa01))**, regresa al paso [**6**](#_ref165322946) del Flujo primario.</p>|

|<p></p><p><a name="fa06"></a>**FA06 Filtrar información de alguna columna de la tabla**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT requiere **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.|<p></p><p></p><p></p><p>	</p>|
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||5. Regresa al paso [**26**](#_ref158827313) del **([**FA01**](#fa01))**.|

|<p></p><p><a name="fa07"></a>**FA07 Selección de “Ver detalle”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA07** inicia cuando el Empleado SAT selecciona **“Ver detalle”** de un proyecto.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyectos-Proyectos registrados</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consultar)</p><p>**Movimiento**=</p><p>- Id proyecto</p><p>- Nombre corto del proyecto </p><p>&emsp;</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||3. Obtiene de la BD la información de los “Datos generales” del proyecto seleccionado.|
||<p>4. Muestra la siguiente información en la sección de “Datos generales” en formato de solo lectura y permite seleccionar alguna sección:</p><p>&emsp;</p><p>&emsp;Datos generales, aplica la **(RNA40)**:</p><p>&emsp;</p><p>- Id</p><p>- Nombre corto\*</p><p>- Estatus</p><p>- Nombre del proyecto\*</p><p>- Id AGP\*</p><p></p><p>Secciones colapsada (habilitadas):</p><p></p><p>- Ficha técnica</p><p>- Asociar fases</p><p>- Gestión documental</p><p>- Información de comités</p><p>- Plan de trabajo</p><p>- Participación de proveedores</p><p>- Verificación de RCP</p><p>Opción:</p><p></p><p>- Regresar</p><p>Ver **(17\_3083\_EIU\_ModificarProyecto)** Estilos 02.</p>|
|<p>5. <a name="_ref160268006"></a>Selecciona la sección **“Ficha técnica”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la sección **“Asociar fases”**, continúa en el paso [**8**](#_ref165371838).</p><p>- En caso de seleccionar la sección **“Gestión documental”**, continúa en el paso [**10**](#_ref160267595).</p><p>- En caso de seleccionar la sección **“Información de comités”**, continúa en el paso [**12**](#_ref160267661).</p><p>- En caso de seleccionar la sección **“Plan de trabajo”**,** continúa en el paso [**14**](#_ref160267737).</p><p>- En caso de seleccionar la sección **“Participación de proveedores”**,** continúa en el paso [**16**](#_ref160267810).</p><p>- En caso de seleccionar la sección **“Verificación de RCP”**, continúa en el paso [**18**](#_ref160267880).</p><p>- En caso de seleccionar la opción **“Regresar”**, continúa en la pantalla principal **“Proyectos”**,** en el paso [**5**](#_ref158409910) del Flujo primario.</p>|6. Obtiene de la BD la información de la “Ficha técnica” del proyecto seleccionado.|
||<p>7. Muestra la información obtenida en el paso anterior en la sección de “Ficha técnica” en formato de solo lectura, aplica la **(RNA241)**.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarProyecto)** Estilos 03.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006) de este flujo.</p>|
||8. <a name="_ref165371838"></a>Obtiene de la BD la información de “Asociar fases” del proyecto seleccionado.|
||<p>9. Muestra la información obtenida en el paso anterior en la sección de “Asociar fases” en formato de solo lectura, aplica la **(RNA241)**.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_AsociarFasesMatrizDoc)** Estilos 01.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006) de este flujo.</p>|
||10. <a name="_ref160267595"></a>Obtiene de la BD la información de “Gestión documental” del proyecto seleccionado.|
||<p>11. <a name="_ref160267535"></a>Muestra la información obtenida en el paso anterior en la sección de “Gestión documental” en formato de solo lectura, aplica la **(RNA241)**.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_GestionDocumental)** Estilos 01.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006) de este flujo.</p>|
||12. <a name="_ref160267661"></a>Obtiene de la BD la información de “Información de comités” del proyecto seleccionado.|
||<p>13. Muestra la información obtenida en el paso anterior en la sección de “Información de comités” en formato de solo lectura, aplica la **(RNA241)**.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_AdministrarInfoComites)** Estilos 01.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006) de este flujo.</p>|
||14. <a name="_ref160267737"></a>Obtiene de la BD la información del “Plan de trabajo” del proyecto seleccionado.|
||<p>15. Muestra la información obtenida en el paso anterior en la sección de “Plan de trabajo” en formato de solo lectura, aplica la **(RNA241)**.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarPlanDeTrabajo)** Estilos 02.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006) de este flujo.</p>|
||16. <a name="_ref160267810"></a>Obtiene de la BD la información de “Participación de proveedores” del proyecto seleccionado.|
||<p>17. Muestra la información obtenida en el paso anterior en la sección de “Participación de proveedores” en formato de solo lectura, aplica la **(RNA241)**.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_AsignarProveedoresParticipantes)** Estilos 01.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006) de este flujo.</p>|
||18. <a name="_ref160267880"></a>Obtiene de la BD la información de “Verificación de RCP” del proyecto seleccionado.|
||<p>19. Muestra la información obtenida en el paso anterior en la sección de “Verificación de RCP” en formato de solo lectura, aplica la **(RNA241)**.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_CerrarProyecto)** Estilos 01.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006) de este flujo.</p>|
||20. Fin del Caso de Uso.|

|<p></p><p><a name="fa08"></a>**FA08 Selección de “Regresar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA08** inicia cuando el Empleado SAT selecciona la opción **“Regresar”**.|2. Muestra el **([**MSG003**](#msg003))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona la opción **“Sí”** continúa en el paso [**5**](#_ref165372913).</p><p>&emsp;</p><p>- En caso de seleccionar **“No”**, continúa en el paso [**4**](#_ref165372920).</p>|4. <a name="_ref165372920"></a>Cierra el mensaje y permanece en el paso [**9**](#_ref165328862) del Flujo primario donde fue invocado. |
||5. <a name="_ref165372913"></a>Cierra el mensaje, limpia los campos de la pantalla. |
||6. Regresa al paso [**3**](#_ref167102233) del Flujo primario.|

|<p></p><p><a name="fa09"></a>**FA09 Estructura del “Id AGP” incorrecta**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA09** inicia cuando el sistema identifica que la estructura del dato “Id AGP” es incorrecta.|
||2. Muestra en rojo el campo incorrecto. |
||3. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso [**8**](#_ref158409503) del Flujo primario.|

|<p></p><p><a name="fa10"></a>**FA10 El “Id AGP” se encuentra registrado en la BD**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA10** inicia cuando el sistema identifica que el “Id AGP” se encuentra registrado en la BD.|
||2. Muestra en rojo el campo incorrecto. |
||3. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso [**8**](#_ref158409503) del Flujo primario.|

|<p>**	</p><p></p><p></p>|
| :- |
|<h3><a name="_toc167610095"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_ECU\_AccesoSistema</p><p>- 17\_3083\_EIU\_AltaDeProyecto</p><p>- 17\_3083\_ECU\_ModificarProyecto</p><p>- 17\_3083\_EIU\_ModificarProyecto</p><p>- 17\_3083\_ECU\_ModificarPlanDeTrabajo</p><p>- 17\_3083\_EIU\_ModificarPlanDeTrabajo</p><p>- 17\_3083\_EIU\_GestionDocumental</p><p>- 17\_3083\_EIU\_AdministrarInfoComites</p><p>- 17\_3083\_EIU\_AsignarProveedoresParticipantes</p><p>- 17\_3083\_EIU\_CerrarProyecto</p><p></p>|
|<h3><a name="_toc167610096"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Favor de ingresar los datos obligatorios marcados con un asterisco (\*).|
|<a name="msg002"></a>**MSG002**|No existen proyectos que coincidan con los criterios de búsqueda ingresados.|
|<a name="msg003"></a>**MSG003**|Se perderá la información que no haya guardado, ¿Desea regresar?|
|<a name="msg004"></a>**MSG004**|El dato ingresado en “Id AGP” es incorrecto.|
|<a name="msg005"></a>**MSG005**|El “Id AGP” ya se encuentra registrado en un proyecto. Verifique el id.|
|<a name="msg006"></a>**MSG006**|Proyecto creado exitosamente.|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg009"></a>**MSG009**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|

||
| :- |
|<h3><a name="_toc167610097"></a>**10. Requerimientos No Funcionales** </h3>|
||

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001**|Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|
|**RNF002**|Concurrencia|<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p> </p><p>El número de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT. </p>|
|**RNF003**|Seguridad|El acceso solo podrá ser otorgado a todo Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema.|
|**RNF004**|Usabilidad|<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación: </p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual como guía para el Empleado SAT.</p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado. </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales.</p>|
|**RNF005**|Eficiencia|Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT.|
|**RNF006**|Usabilidad|<p>El Empleado SAT debe poder navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones de 15, 50 y 100:</p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta).</p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta).</p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).</p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual, con el resultado de la consulta).</p><p>&emsp;</p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente.</p>|
|**RNF007**|Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.|
|**RNF008**|Fiabilidad|El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema.|
|**RNF009**|Seguridad|Se debe mantener la información en pantalla en caso de un error al guardar las Pistas de Auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema.|
|**RNF010**|Integridad|Al almacenar la información en la BD de tipo texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena.|

||
| :- |

|<h3><a name="_toc167610098"></a>**11. Diagrama de actividad** </h3>|
| :- |
|![](Aspose.Words.0f14b42d-9ce5-4360-bd7d-16dc5da7d479.007.png)|
|<h3><a name="_toc167610099"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no se requiere para este proceso.</p>|

|<h3><a name="_toc167610100"></a>**13. Aprobación del cliente** </h3>|
| :- |
|<p></p><p></p>|

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
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:** Isabel Adriana Valdez Cortés.|
|**Puesto:** Líder Técnico SDMA 6.|**Puesto:** Analista de Sistemas DS SDMA 6.|
|**Fecha**:|**Fecha**:|
|||

||
| :- |






|||Página 1 de 9|
| :- | :-: | -: |

