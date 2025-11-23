|![](Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_AsignarProyectos.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Isabel Adriana Valdez Cortés|*15/01/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*25/01/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*16/05/2024*|



**Tabla de Contenido**

[17_3083_ECU_AsignarProyectos	2](#_toc167092064)

[1. Descripción	2](#_toc167092065)

[2. Diagrama del Caso de Uso	2](#_toc167092066)

[3. Actores	2](#_toc167092067)

[4. Precondiciones	2](#_toc167092068)

[5. Post condiciones	2](#_toc167092069)

[6. Flujo primario	3](#_toc167092070)

[7. Flujos alternos	6](#_toc167092071)

[8. Referencias cruzadas	15](#_toc167092072)

[9. Mensajes	15](#_toc167092073)

[10. Requerimientos No Funcionales	15](#_toc167092074)

[11. Diagrama de actividad	18](#_toc167092075)

[12. Diagrama de estados	18](#_toc167092076)

[13. Aprobación del cliente	19](#_toc167092077)






### **<a name="_toc167092064"></a>**17\_3083\_ECU\_AsignarProyectos

|<h3><a name="_toc167092065"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT relacionar proyectos con usuarios que se encuentren registrados en este sistema.</p><p></p>|
|<h3><a name="_toc167092066"></a>**2. Diagrama del Caso de Uso**</h3>|
|![](Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.002.png)|
|<h3><a name="_toc167092067"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc167092068"></a>**4. Precondiciones**</h3>|
|<p></p><p>- Se han registrado “Proyectos” y “Usuarios” previamente.</p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa.</p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar al módulo “Sistema” y al submódulo “Asignar Proyecto”.</p><p>- El Empleado SAT ha ingresado a la opción del menú “Sistema” y al submenú “Asignar Proyecto”, de acuerdo con el proceso del **(17\_3083\_ECU\_AccesoSistema)**.</p><p>&emsp;</p>|
|<h3><a name="_toc167092069"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT relacionó usuarios a un proyecto específico.</p><p>- El Empleado SAT relacionó proyectos a un usuario específico.</p><p></p>|
|<h3><a name="_toc167092070"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT ingresa al menú **“Sistema”** y al submenú **“Asignar Proyectos”**.|2. Consulta y obtiene de la base de datos (BD) la información correspondiente al catálogo “Estatus de proyecto”.|
||<p>3. <a name="_ref156661867"></a><a name="_ref156502619"></a>Muestra la pantalla “Asignar Proyectos – Por proyecto” con las siguientes secciones:</p><p>&emsp;</p><p>&emsp;Sección “Por proyecto” (por defecto abierta):</p><p>- Estatus. Aplica la regla de negocio **(RNA30)**</p><p>- Nombre corto del proyecto</p><p>- Id proyecto</p><p></p><p>Opción:</p><p>- Buscar</p><p></p><p>Tablas por defecto vacías:</p><p>- Usuarios</p><p>- Usuarios asignados</p><p>&emsp;</p><p>Opciones habilitadas:</p><p>- Agregar todos ![ref1]</p><p>- Agregar ![ref2]</p><p>- Quitar todos ![ref3]</p><p>- Quitar ![](Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.006.png)</p><p>- Filtrar ![ref4]</p><p>- Exportar Proyectos-Usuarios ![](Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.008.png)</p><p>&emsp;</p><p>Opciones inhabilitadas:</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Sección “Por usuario” (colapsada).</p><p></p><p>Ver **(17\_3083\_EIU\_AsignarProyectos)** Estilos 01.</p>|
|<p>4. <a name="_ref166747950"></a>Accede a la sección **“Por proyecto”**.</p><p>&emsp;</p><p>- En caso de seleccionar la sección **“Por usuario”**, continúa en el flujo alterno **([**FA01**](#fa01))**.</p>||
|5. Selecciona el campo **“Estatus”**.|6. Despliega en el campo de “Estatus” el listado de opciones obtenidas del catálogo “Estatus del proyecto”.|
|7. Selecciona una opción en **“Estatus”**.|8. Consulta y obtiene de la BD el nombre corto de los proyectos que se encuentren en el estatus seleccionado.|
|9. Selecciona información en el campo **“Nombre corto del proyecto”**.|10. Despliega en el campo “Nombre corto del proyecto” el listado de proyectos obtenidos.|
|11. Ingresa el **“Id proyecto”**.||
|12. Selecciona la opción **“Buscar”**.|<p>13. Verifica que se hayan ingresado los criterios obligatorios para la búsqueda, de acuerdo con la **(RNA08)** y **(RNA03)**.</p><p>&emsp;</p><p>- En caso contrario, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>14. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= AsignarProyecto\_PorProyecto</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consultar)</p><p>**Movimiento**= </p><p>- Nombre corto del proyecto que se consulta</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||15. Consulta en la BD y obtiene el nombre completo de todos los usuarios que estén registrados en el sistema. Aplica la **(RNA09)**|
||16. Consulta en la BD y obtiene el nombre completo de los usuarios asignados al proyecto que se consulta. Aplica la **(RNA10)**|
||17. Inhabilita los criterios de búsqueda y la opción “Buscar”.|
||18. Habilita las opciones “Guardar” y “Cancelar”.|
||19. Muestra en los apartados “Usuarios” y “Usuarios asignados” los datos obtenidos.|
|<p>20. <a name="_ref156568126"></a>Elige una opción:</p><p>&emsp;</p><p>- En caso de requerir asignar usuarios al proyecto, elige uno o más “Usuarios” y selecciona la opción **“Agregar”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Agregar todos”** los usuarios en una sola acción, continúa en el **([**FA09**](#fa09))**.</p><p>&emsp;</p><p>- En caso de requerir quitar usuarios del proyecto, elige uno o más “Usuarios asignados” y selecciona la opción **“Quitar”**, continúa en el **([**FA04**](#fa04))**.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Quitar todos”** en una sola acción, continúa en el **([**FA10**](#fa10))**.</p><p>&emsp;</p><p>- En caso de requerir **“Filtrar”** los datos mostrados, continúa en el **([**FA05**](#fa05))**.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Cancelar”**,** continúa en el **([**FA06**](#fa06))**.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Exportar Proyectos-Usuarios”**, continúa en el **([**FA08**](#fa08))**.</p>|21. <a name="_ref166749594"></a>Presenta los nombres de los usuarios seleccionados en el apartado “Usuarios asignados”.|
|22. <a name="_ref156503640"></a>Selecciona la opción **“Guardar”**.|<p>23. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= AsignarProyecto\_PorProyecto</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Nombre corto del proyecto</p><p>- Nombre de cada usuario asignado</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||24. Almacena en la BD los nombres de los usuarios que se encuentren en el apartado de “Usuarios asignados” y los relaciona al proyecto.|
||25. Muestra el mensaje **([**MSG001**](#msg001))** con la opción** “Aceptar”.|
|26. Selecciona la opción **“Aceptar”**.|27. Cierra el mensaje y recarga la pantalla.|
||28. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc167092071"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 Selecciona sección “Por usuario”**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA01** inicia cuando el Empleado SAT selecciona la sección **“Por usuario”**.|2. Consulta en la BD y obtiene los nombres completos de todos los usuarios que estén registrados en el sistema con estatus “Activo”. |
||<p>3. <a name="_ref156502660"></a>Despliega en la pantalla los siguientes campos de la sección “Por usuario”:</p><p>&emsp;</p><p>- Usuario\*</p><p>- Buscar</p><p>&emsp;</p><p>Tablas por defecto vacías:</p><p>- Proyectos</p><p>- Proyectos asignados</p><p>Opciones habilitadas:</p><p>- Agregar todos ![ref5]</p><p>- Agregar ![ref2]</p><p>- Quitar todos ![ref6]</p><p>- Quitar ![](Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.011.png)</p><p>- Filtrar ![ref4]</p><p>&emsp;</p><p>Opciones inhabilitadas:</p><p>- Guardar</p><p>- Cancelar</p><p></p><p>Ver **(17\_3083\_EIU\_AsignarProyectos)** Estilos 02.</p>|
|4. Selecciona el campo **“Usuario\*”**.|5. Despliega el listado obtenido de usuarios activos registrados en este sistema.|
|6. <a name="_ref158301236"></a>Elige una opción del listado de usuarios y selecciona la opción **“Buscar”**.|<p>7. Verifica que se haya seleccionado como obligatorio un nombre de usuario en el campo “Usuario”, aplica la **(RNA03)**.</p><p>&emsp;</p><p>- En caso contrario, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>8. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= AsignarProyecto\_PorUsuario</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consultar)</p><p>**Movimiento**=</p><p>- Nombre usuario</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||9. Consulta en la BD y obtiene el “Nombre corto” de todos los proyectos registrados en este sistema, de acuerdo con la **(RNA11)**.|
||10. Consulta en la BD y obtiene el “Nombre corto” de los proyectos asignados al usuario seleccionado, aplica la **(RNA12)**.|
||11. Inhabilita el criterio de búsqueda y la opción “Buscar”.|
||12. Habilita las opciones “Guardar” y “Cancelar”.|
||13. Muestra en los apartados “Proyectos” y “Proyectos asignados” los datos obtenidos.|
|<p>14. <a name="_ref156568457"></a>Elige una opción:</p><p>&emsp;</p><p>- En caso de requerir asignar proyectos al usuario, elige uno o más “Proyectos” y selecciona la opción **“Agregar”** uno o más proyectos seleccionados, el flujo continúa.</p><p>- En caso de seleccionar la opción **“Agregar todos”** los proyectos en una sola acción, continúa en el **([**FA09**](#fa09))**.</p><p>- En caso de requerir quitar proyectos del usuario, elige uno o más “Proyectos asignados” y selecciona la opción **“Quitar”** uno o más proyectos seleccionados, continúa en el **([**FA07**](#fa07))**.</p><p>- En caso de seleccionar la opción **“Quitar todos”** los proyectos en una sola acción, continúa en el **([**FA10**](#fa10))**.</p><p>- En caso de requerir **“Filtrar”** los datos mostrados, continúa en el **([**FA05**](#fa05))**.</p><p>- Si selecciona la opción **“Cancelar”**,** continúa en el **([**FA06**](#fa06))**.</p>|15. Presenta el nombre corto de los proyectos seleccionados, en el apartado “Proyectos asignados”.|
|16. <a name="_ref161136805"></a>Selecciona la opción **“Guardar”.**|<p>17. Almacena en la BD la información de las Pistas de Auditoría. </p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= AsignarProyecto\_PorUsuario</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Nombre usuario</p><p>- Nombre corto de cada proyecto asignado</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||18. Almacena en la BD el o los proyectos que se asignaron al usuario seleccionado y los relaciona.|
||19. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|20. Selecciona la opción **“Aceptar”**.|21. Cierra el mensaje y recarga la pantalla inicializando los campos de la sección nuevamente.|
||22. Fin del Caso de Uso.|

|<p></p><p></p><p><a name="fa02"></a>**FA02 Se identifica que no se ingresaron los criterios obligatorios o no ingresó como mínimo uno**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA02** inicia cuando el sistema identifica que no se ingresaron los criterios de búsqueda.|
||2. Muestra en rojo los campos pendientes de capturar.|
||3. Muestra el **([**MSG002**](#msg002))**, con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje. |
||<p>6. Regresa al paso dependiendo donde es invocado este flujo:</p><p>&emsp;</p><p>- Si se invoca en el paso 13 del Flujo primario, continúa en el paso [**4**](#_ref166747950) del Flujo primario.</p><p>- Si se invoca en el paso 7 del **([**FA01**](#fa01))**, continúa en el paso [**3**](#_ref156502660) del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa03"></a>**FA03 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA03** inicia cuando interviene un evento ajeno y no se puedan almacenar las Pistas de Auditoría.|
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje informativo de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT** o **INSR**, se muestra el **([**MSG007**](#msg007))**.</p><p>&emsp;</p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG008**](#msg008))**.</p><p></p><p>- En caso de que la pista de auditoría sea por el tipo de movimiento **PRNT**, se muestra el **([**MSG009**](#msg009))**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría.|

|<p></p><p><a name="fa04"></a>**FA04 Quitar usuarios asignados**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA04** inicia cuando el Empleado SAT selecciona la opción **“Quitar”** uno o más usuarios seleccionados.|2. Presenta el o los nombres que se seleccionaron de “Usuarios asignados” en el apartado de “Usuarios”. |
||<p>3. Continúa en el paso de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado al agregar todos los usuarios, continúa en el paso **[**20**](#_ref156568126)** del Flujo primario.</p><p>- Si fue invocado al agregar todos los proyectos,** continúa en el paso [**14**](#_ref156568457) del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa05"></a>**FA05 Filtrar resultados en tablas “Usuarios”, “Proyectos” y los asignados**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT requiere **“Filtrar”** los datos mostrados.||
|2. Ingresa la información en el campo para filtrar.|3. Realiza la búsqueda dentro de los datos del apartado en donde se requiere filtrar la información.|
||<p>4. Muestra el resultado en el apartado correspondiente, considerando los siguientes puntos y la **(RNA66)**:</p><p>&emsp;</p><p>- Si el filtro se realiza en el apartado “Usuarios”, el resultado se muestra en dicho apartado.</p><p>- Si el filtro se realiza en el apartado “Usuarios asignados”, el resultado se muestra en el mismo apartado.</p><p>- Si el filtro se realiza en el apartado “Proyectos”, el resultado se muestra en el mismo apartado.</p><p>- En caso de que el filtro se realice en el apartado “Proyectos asignados”, el resultado se muestra en el mismo apartado.</p><p>- En caso de que la información ingresada en el campo para filtrar no tenga coincidencia con algún dato, no mostrará ningún proyecto y regresa al punto 2 de este flujo.</p>|
||<p>5. Realiza lo siguiente:</p><p>- Si fue invocado en el Flujo primario, continúa en el paso [**20**](#_ref156568126) del Flujo primario.</p><p>- Si fue invocado en el **([**FA01**](#fa01))** en el paso [**14**](#_ref156568457) del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Cancelar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”**.|2. Muestra el **([**MSG003**](#msg003))** con** las opciones “Sí” y No”.|
|<p>3. Selecciona la opción **“Sí”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar **“No”**, continúa en el paso [**6**](#_ref157021808) de este flujo.</p>|4. Cierra la ventana emergente.|
||5. Inicializa los campos de la pantalla en donde se selecciona la opción dejándolos como en un inicio, no almacena ninguna información.|
||6. <a name="_ref157021808"></a>Permanece en la sección donde fue invocado: “Por proyecto”** y** “Por usuario”.|

|<p></p><p><a name="fa07"></a>**FA07 Selecciona la opción “Quitar” proyectos asignados**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA07** inicia cuando el Empleado SAT selecciona la opción **“Quitar”** uno o más proyectos.|2. Presenta el o los nombres cortos de los proyectos asignados al usuario seleccionado en el apartado de “Proyectos”. |
||<p>4. Continúa en el paso de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado al agregar todos los usuarios, continúa en el paso **[**20**](#_ref156568126)** del Flujo primario.</p><p>- Si fue invocado al agregar todos los proyectos,** continúa en el paso [**14**](#_ref156568457) del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa13"></a><a name="fa08"></a>**FA08 Selecciona la opción “Exportar Proyectos-Usuarios”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA08** inicia cuando el Empleado SAT selecciona la opción **“Exportar Proyectos-Usuarios”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= AsignarProyecto\_PorProyecto</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= </p><p>**CNST** (Consultar)</p><p>**PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Nombre corto de cada proyecto que se consulta</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||<p>3. Consulta en la BD y obtiene la siguiente información de los proyectos registrados en este sistema dependiendo la opción seleccionada en el campo “Estatus”, considerando la **(RNA70)**.</p><p>&emsp;</p><p>- Nombre corto del proyecto</p><p>- Id proyecto</p>|
||<p>4. Por cada proyecto obtenido en el paso anterior, consulta en la BD y obtiene el siguiente dato de los usuarios asignados.</p><p></p><p>- Nombre completo</p>|
||<p>5. Genera un archivo de Excel con extensión (.xlsx) que contenga la información obtenida en los pasos anteriores:</p><p>&emsp;</p><p>- Estatus del proyecto</p><p>- Nombre corto del proyecto</p><p>- Nombre completo del proyecto</p><p>- Id proyecto</p><p>- Usuarios asignados</p><p></p><p>**Nota:** Se genera una fila por cada usuario asignado y que se repita el resto de la información.</p>|
||6. Descarga el archivo de Excel con extensión (.xlsx).|
||7. Regresa al paso [**20**](#_ref156568126) del Flujo primario.|

|<p></p><p><a name="fa09"></a>**FA09 Selecciona la opción “Agregar todos” (Usuarios o Proyectos)**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA09** inicia cuando el Empleado SAT selecciona la opción **“Agregar todos”** (Usuarios o Proyectos).|2. Marcar como seleccionados los campos de selección de todos los registros que se encuentran en el apartado “Usuarios” o en “Proyectos”.|
||3. Muestra el **([**MSG004**](#msg004))** o **([**MSG005**](#msg005))** según corresponda, con las opciones “Sí” y “No”.|
|<p>4. Selecciona la opción **“Sí”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar **“No”**, regresa al paso donde fue invocado.</p>|5. Cierra el mensaje.|
||6. Muestra en el apartado “Usuarios asignados” o “Proyectos asignados” todos los registros, quedando vacío el apartado de “Usuarios” o “Proyectos” según corresponda.|
||<p>7. Continúa en el paso de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado al agregar todos los usuarios, continúa en el paso **[**20**](#_ref156568126)** del Flujo primario.</p><p>- Si fue invocado al agregar todos los proyectos,** continúa en el paso [**14**](#_ref156568457) del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa10"></a>**FA10 Selecciona la opción “Quitar todos” (Usuarios o Proyectos asignados)**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA10** inicia cuando el Empleado SAT selecciona la opción **“Quitar todos”** en una sola acción (Usuarios o Proyectos asignados).|2. Marca como seleccionados los campos de selección de todos los registros que se encuentran en el apartado “Usuarios asignados” o en “Proyectos asignados”.|
||3. Muestra el **([**MSG010**](#msg010))** o **([**MSG006**](#msg006))** según corresponda, con las opciones “Sí” y “No”.|
|<p>4. Selecciona la opción **“Sí”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar **“No”**, regresa al paso donde fue invocado.</p>|<p>5. Cierra el mensaje.</p><p></p><p></p>|
||6. Muestra en el apartado “Usuarios” o “Proyectos” todos los registros, quedando vacío el apartado de “Usuarios asignados” o “Proyectos asignados” según corresponda.|
||<p>7. Continúa en el paso de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado al quitar “Usuarios asignados”, continúa en el paso [**20**](#_ref156568126) del Flujo primario.</p><p>- Si fue invocado al quitar “Proyectos asignados”,** continúa en el paso [**14**](#_ref156568457) del **([**FA01**](#fa01))**.</p>|

|<p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc167092072"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_ECU\_AccesoSistema</p><p>- 17\_3083\_EIU\_AsignarProyectos</p><p></p>|
|<h3><a name="_toc167092073"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Se guardaron los cambios con éxito.|
|<a name="msg002"></a>**MSG002**|Favor de ingresar al menos un criterio para la búsqueda.|
|<a name="msg003"></a>**MSG003**|Al cancelar se perderán los cambios realizados. ¿Está seguro de continuar?|
|<a name="msg004"></a>**MSG004**|¿Está seguro de asignar todos los usuarios?|
|<a name="msg005"></a>**MSG005**|¿Está seguro de asignar todos los proyectos?|
|<a name="msg006"></a>**MSG006**|¿Está seguro de excluir los proyectos asignados?|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg009"></a>**MSG009**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg010"></a>**MSG010**|¿Está seguro de excluir los usuarios asignados? |

|<p></p><p></p>|
| - |
|<h3><a name="_toc167092074"></a>**10. Requerimientos No Funcionales** </h3>|
|<p></p><p></p>|

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

||
| - |

|<h3><a name="_toc167092075"></a>**11. Diagrama de actividad** </h3>|
| :- |
|<p>![](Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.012.jpeg)</p><p></p>|
|<h3><a name="_toc167092076"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no se requiere para este proceso.</p>|
**\


|<h3><a name="_toc167092077"></a>**13. Aprobación del cliente** </h3>|
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

[ref1]: Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.003.png
[ref2]: Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.004.png
[ref3]: Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.005.png
[ref4]: Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.007.png
[ref5]: Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.009.png
[ref6]: Aspose.Words.2d683e30-264f-4f6f-8c47-d470c4211f61.010.png
