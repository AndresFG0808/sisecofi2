|![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_CargarPlanDeTrabajo.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>**8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :-: | :-: | :-: |
|*1*|*Creación del documento*|Eduardo Acosta Mora|*20/02/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*13/04/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|<p>*18/06/2024*</p><p></p>|



**Tabla de Contenido**

[17_3083_ECU_CargarPlanDeTrabajo	2](#_toc169782623)

[1. Descripción	2](#_toc169782624)

[2. Diagrama del Caso de Uso	2](#_toc169782625)

[3. Actores	2](#_toc169782626)

[4. Precondiciones	2](#_toc169782627)

[5. Post condiciones	3](#_toc169782628)

[6. Flujo primario	3](#_toc169782629)

[7. Flujos alternos	9](#_toc169782630)

[8. Referencias cruzadas	16](#_toc169782631)

[9. Mensajes	16](#_toc169782632)

[10. Requerimientos No Funcionales	17](#_toc169782633)

[11. Diagrama de actividad	19](#_toc169782634)

[12. Diagrama de estados	20](#_toc169782635)

[13. Aprobación del cliente	21](#_toc169782636)


### **<a name="_toc169782623"></a>**17\_3083\_ECU\_CargarPlanDeTrabajo 

|<h3><a name="_toc169782624"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es facilitar al Empleado SAT la descarga de la plantilla del plan de trabajo adjuntada en el sistema (Plan tipo). Esto permite cargar posteriormente el archivo con la información necesaria para el plan de trabajo de un proyecto. </p><p></p>|
|<h3><a name="_toc169782625"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.002.png)</p><p></p>|
|<h3><a name="_toc169782626"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc169782627"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con una e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa al mismo.</p><p>- Se le ha asignado el rol requerido al Empleado SAT para ingresar al módulo “Sistema”, submódulo “Proyectos” y a la sección “Plan de trabajo”.</p><p>- El sistema ha validado que el Empleado SAT cuenta con el rol para ingresar al módulo “Sistema”, submódulo “Proyectos” y a la sección “Plan de trabajo” con el permiso correspondiente a la carga del plan de trabajo.</p><p>- Se han almacenado las plantillas de plan de trabajo (Plan Tipo) en el módulo “Plantillas”.</p><p></p><p></p>|
|<h3><a name="_toc169782628"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT descargó la plantilla de plan de trabajo (Plan tipo).  </p><p>- El Empleado SAT descargó el plan de trabajo almacenado en la BD.</p><p>- El Empleado SAT adjuntó un plan de trabajo.</p><p></p>|
|<h3><a name="_toc169782629"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT ingresa al módulo “Sistema”, submódulo “Proyectos” y a la sección **“Plan de trabajo”**.|<p>2. Consulta en la BD la información de los siguientes catálogos de acuerdo con la regla de negocio **(RNA01)**:</p><p>&emsp;</p><p>&emsp;- Plan tipo</p><p>&emsp;- ` `Nivel de esquema. Aplica la **(RNA57)**</p><p></p><p>- En caso de que no haya un plan de trabajo cargado, el campo “Nivel de esquema” estará inhabilitado.</p>|
||<p>3. Consulta en la BD la información para la tabla “Plan de trabajo”.</p><p>&emsp;</p><p>- En caso de que no se obtenga información de la consulta, continúa en el **(FA01)**.</p>|
||<p>4. Actualiza el Diagrama de Gantt con la información obtenida del paso anterior.</p><p>&emsp;</p><p>- En caso de que no se obtenga información de la consulta anterior, no se mostrará el Diagrama de Gantt.</p>|
||<p>5. <a name="_ref163806843"></a>Muestra la pantalla “Plan de trabajo” con lo siguiente, de acuerdo con la **(RNA52)**:</p><p>&emsp;</p><p>&emsp;Campo:</p><p>&emsp;- Plan tipo</p><p></p><p>Opción: </p><p>- Descargar plan tipo *![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.003.png)* (Inhabilitado)</p><p></p><p>Campo:</p><p>- Archivo a cargar\* </p><p></p><p>Opciones:</p><p>- Examinar</p><p>&emsp;- Cargar plan ![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.004.png) (Inhabilitado)</p><p>&emsp;- Mostrar todas las tareas ![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.005.png)</p><p></p><p>Etiquetas:</p><p>- % Completado</p><p>&emsp;- Fecha planeada</p><p></p><p>Gráfica (Diagrama de Gantt)</p><p></p><p>Campos:</p><p>- Nivel de esquema</p><p>&emsp;- Última modificación (Solo consulta)</p><p></p><p>Opción: </p><p>- Descargar plan de trabajo ![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.006.png)</p><p></p><p>Tabla (Plan de trabajo). Aplica la **(RNA244)**:</p><p>- Id tarea</p><p>&emsp;- Nombre de la tarea</p><p>&emsp;- Activo</p><p>&emsp;- Duración planeada</p><p>&emsp;- Fecha de inicio planeada</p><p>&emsp;- Fecha fin planeada</p><p>&emsp;- Duración real</p><p>&emsp;- Fecha inicio real</p><p>&emsp;- Fecha fin real</p><p>&emsp;- Predecesora</p><p>&emsp;- Planeado %</p><p>&emsp;- Completado %</p><p>&emsp;- Acciones</p><p></p><p>Opciones de la tabla:</p><p>- Activo ![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.007.png) </p><p>&emsp;- Modificar ![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.008.png) </p><p>&emsp;- Descartar ![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.009.png)</p><p></p><p>Opciones:</p><p>- Cancelar</p><p>&emsp;- Calcular %</p><p>&emsp;- Guardar </p><p>&emsp;- Calcular todos los proyectos (Solo visible con el permiso correspondiente)</p><p></p><p>Ver **(17\_3083\_EIU\_CargarPlanDeTrabajo)** Estilos 01.</p>|
|<p>6. <a name="_ref163808420"></a>Selecciona una opción: </p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Examinar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione una opción para el campo **“Plan tipo”**, continúa en el **([**FA03**](#fa03))**.</p>|7. Abre el gestor de archivos del equipo de cómputo del Empleado SAT.|
|<p>8. Selecciona el archivo de Excel con extensión (.xlsx).</p><p>&emsp;</p><p>- En caso de que no seleccione el archivo, continúa en el paso **6** de este flujo.</p>|<p>9. Valida que se haya adjuntado un archivo con las características de acuerdo con la **(RNA22)**.</p><p>&emsp;</p><p>- En caso de que no cumpla las características requeridas, continúa en el **([**FA06**](#fa06))**.</p>|
||10. Habilita las opciones “Cancelar” y “Cargar plan”.|
|<p>11. <a name="_ref163808473"></a>Selecciona una opción: </p><p></p><p>- En caso de que seleccione la opción **“Cargar plan”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”**, continúa en el **([**FA07**](#fa07))**.</p>|<p>12. Identifica si tiene ya un plan de trabajo cargado y realiza lo siguiente:</p><p>&emsp;</p><p>- En caso de que haya un plan de trabajo cargado, muestra el mensaje **([**MSG001**](#msg001))**, con las opciones “Sí” y “No” y el flujo continúa.</p><p></p><p>- En caso de que sea la primera vez que se cargue un plan de trabajo para el proyecto, se omitirá el mensaje y continúa en el paso **15** de este flujo.</p>|
|13. Selecciona una opción.|<p>14. Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“No”**, regresa al paso [**5**](#_ref163806843) de este flujo.</p>|
||<p>15. <a name="_ref165304245"></a>Valida el contenido del archivo adjunto, de acuerdo con la **(RNA55)**.</p><p>&emsp;</p><p>- En caso de que el archivo adjunto no cumpla con el contenido solicitado, continúa en el **(FA02)**.</p>|
||<p>16. Procesa el archivo adjunto con los siguientes datos para mostrarlos en la tabla “Plan de trabajo”.</p><p>&emsp;</p><p>&emsp;- Id tarea</p><p>&emsp;- Nivel de esquema</p><p>&emsp;- Nombre de la tarea</p><p>&emsp;- Activo</p><p>&emsp;- Duración planeada</p><p>&emsp;- Fecha de inicio planeada</p><p>&emsp;- Fecha fin planeada</p><p>&emsp;- Duración real</p><p>&emsp;- Fecha inicio real</p><p>&emsp;- Fecha fin real</p><p>&emsp;- Predecesora</p><p>&emsp;- Planeado %</p><p>&emsp;- Completado %</p><p></p><p>**Nota:** No se ejecutarán los cálculos hasta que se seleccione la opción “Guardar”.</p>|
||<p>17. Complementa la pantalla “Plan de trabajo” con los nuevos datos de acuerdo con la **(RNA52)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_CargarPlanDeTrabajo)** Estilos 01.</p>|
||<p>18. Se realiza lo siguiente:</p><p>&emsp;</p><p>&emsp;Habilita la opción “Guardar”.</p><p>&emsp;Inhabilita la opción “Examinar” y “Cargar plan”.</p>|
|<p>19. <a name="_ref163808506"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Guardar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”**, continúa en el **([**FA07**](#fa07))**.</p>|<p>20. Calcula los siguientes datos del archivo adjunto de acuerdo con la **(RNA56)**:</p><p>&emsp;</p><p>&emsp;- Duración real (En caso de que aplique)</p><p>&emsp;- Fecha inicio real (En caso de que aplique)</p><p>&emsp;- Fecha fin real (En caso de que aplique)</p><p>&emsp;- Planeado % (Tareas padre y/o hijo)</p><p>&emsp;- Completado % (Solo tareas padre)</p>|
||21. Valida si existen tarea con un valor en el campo “Completado %” y aplica la **(RNA59)**.|
||22. Obtiene y muestra la fecha del sistema (Día actual) para colocarla en el campo “Última modificación”, sin opción a editar.|
||<p>23. <a name="_ref164001550"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>**Módulo**= Proyecto -PlanDeTrabajo</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= Cuando se cargue un plan de trabajo el tipo de movimiento debe ser **INSR** (Insertar).</p><p>Cuando se sobrescriba el plan de trabajo el tipo de movimiento debe de ser **UPDT** (modificar).</p><p>**Movimiento**= </p><p>- ID de proyecto- Plan de trabajo – Carga masiva</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>24. <a name="_ref164001559"></a>Guarda en la BD la siguiente información:</p><p></p><p>- Última modificación: Fecha del sistema (Día actual)</p><p>Tabla (Plan de trabajo)</p><p>- Id tarea</p><p>&emsp;- Nivel de esquema</p><p>&emsp;- Nombre de la tarea</p><p>&emsp;- Activo</p><p>&emsp;- Duración planeada</p><p>&emsp;- Fecha de inicio planeada</p><p>&emsp;- Fecha fin planeada</p><p>&emsp;- Duración real</p><p>&emsp;- Fecha inicio real</p><p>&emsp;- Fecha fin real</p><p>&emsp;- Predecesora</p><p>&emsp;- Planeado %</p><p>&emsp;- Completado %</p>|
||25. Muestra una ventana emergente de carga con el mensaje “Calculando” sin opción a cerrar o cancelar.|
||26. Actualiza la tabla “Plan de trabajo” con los datos calculados.|
||27. Genera el Diagrama de Gantt con los datos almacenados.|
||28. Muestra el **([**MSG002**](#msg002))**, con la opción “Aceptar”.|
|29. Selecciona la opción **“Aceptar”**.|30. Cierra el mensaje.|
||<p>31. Habilita el siguiente campo de acuerdo con los datos almacenados:</p><p></p><p>- Nivel de esquema. Aplica la **(RNA57)**</p>|
||<p>32. Se realiza lo siguiente:</p><p>&emsp;</p><p>&emsp;Se inhabilitan las opciones  “Cargar plan”, “Cancelar” y “Guardar”.</p><p>&emsp;Se habilita la opción “Examinar”.</p>|
|<p>33. <a name="_ref163808388"></a>Si lo requiere, selecciona una de las siguientes opciones:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Descargar plan de trabajo”**, continúa en el **([**FA04**](#fa04))**.</p><p>&emsp;</p><p>- En caso de que seleccione una opción en el campo de selección **“Nivel de esquema”**, continúa en **([**FA05**](#fa05))**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Mostrar todas las tareas”**, continúa en **([**FA09**](#fa09))**.</p><p></p><p>- En caso de que seleccione la opción **“Modificar”** de uno de los registros de la tabla, el proceso continúa en **(17\_3083\_ECU\_ModificarPlanDeTrabajo)**. </p><p>&emsp;</p><p>- En caso de que seleccione la opción “Calcular todas los proyectos”, continúa en el **(FA10)**.</p>|34. El sistema calculará la información del campo “Planeado % (Tareas padre y/o hijo)” y se notificará vía correo electrónico de acuerdo con la **(RNA53)**.|
||35. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc169782630"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 No se obtuvo información de la consulta para la tabla “Plan de trabajo”**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA01** inicia cuando no se obtuvo información de la consulta para la tabla “Plan de trabajo”.|
||2. Muestra el **([**MSG003**](#msg003))** con la opción “Aceptar”. |
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso [**5**](#_ref163806843) del Flujo primario.|

|<p></p><p><a name="fa10"></a>**FA02 El contenido del archivo adjunto es incorrecto**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA02** inicia cuando el sistema identifica que el contenido del archivo adjunto es incorrecto.|
||<p>2. Dependiendo del error, mostrara el mensaje correspondiente:</p><p>&emsp;</p><p>- En caso de que no se cumpla el punto uno, muestra el **(MSG008)**.</p><p></p><p>- En caso de que no se cumpla el punto dos, muestra el **(MSG009)**.</p><p></p><p>- En caso de que no se cumpla el punto tres, muestra el **(MSG010)**.</p><p></p><p>- En caso de que no se cumpla el punto cuatro, muestra el **(MSG011)**.</p><p></p><p>- En caso de que no se cumpla el punto cinco, muestra el **(MSG012)**.</p><p></p><p>- En caso de que no se cumpla el punto seis, muestra el **(MSG013)**.</p><p></p><p>- En caso de que no se cumpla el punto siete, muestra el **(MSG014)**.</p><p></p><p>- En caso de que no se cumpla el punto ocho, muestra el **(MSG015)**.</p><p></p><p>- En caso de que no se cumpla el punto nueve, muestra el **(MSG016)**.</p><p></p><p>- En caso de que no se cumpla el punto diez, muestra el **(MSG017)**.</p><p>&emsp;</p><p>- En caso de que no se cumpla el punto once, muestra el **(MSG018)**.</p><p>&emsp;</p><p>- En caso de que no se cumpla el punto doce, muestra el **(MSG019)**.</p><p></p><p>- En caso de que no se cumpla el punto trece, muestra el **(MSG020)**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso **5** del Flujo primario.|

|<p></p><p><a name="fa03"></a>**FA03 Selecciona una opción para el campo “Plan tipo”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA03** inicia cuando el Empleado SAT selecciona una opción para el campo **“Plan tipo”**.|2. Activa la opción “Descargar plan tipo”.|
|3. Selecciona la opción **“Descargar plan tipo”**.|<p>4. <a name="_ref164001582"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>**Módulo**= Proyecto - PlanDeTrabajo</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Plan tipo (Seleccionado)</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA08**](#fa08))**.</p>|
||5. <a name="_ref164001594"></a>Consulta en la BD la plantilla (Plan tipo) seleccionado anteriormente para el plan de trabajo.|
||6. Descarga en un archivo de Excel con extensión (.xlsx) la plantilla que se ocupará como ayuda de acuerdo con la **(RNA55)**.|
||7. Inactiva la opción “Descargar plan tipo” y regresa al valor inicial el campo “Plan tipo”|
||8. Continúa en el paso [**6**](#_ref163808420) del Flujo primario.|

|<p></p><p><a name="fa04"></a>**FA04 Selecciona la opción “Descargar plan de trabajo”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA04** inicia cuando el Empleado SAT selecciona la opción **“Descargar plan de trabajo”**.|<p>2. <a name="_ref164001609"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>**Módulo**= Proyecto - PlanDeTrabajo</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**=</p><p>- ID de proyecto- Plan de trabajo – Carga masiva</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>3. Consulta en la BD la información de la tabla “Plan de trabajo” la siguiente información:</p><p>&emsp;</p><p>&emsp;- Id tarea</p><p>&emsp;- Nivel de esquema</p><p>&emsp;- Nombre de la tarea</p><p>&emsp;- Activo</p><p>&emsp;- Duración planeada</p><p>&emsp;- Fecha de inicio planeada</p><p>&emsp;- Fecha fin planeada</p><p>&emsp;- Duración real</p><p>&emsp;- Fecha inicio real</p><p>&emsp;- Fecha fin real</p><p>&emsp;- Predecesora</p><p>&emsp;- Planeado %</p><p>&emsp;- Completado %</p>|
||4. Genera un archivo de Excel con la misma estructura de la plantilla (Plan tipo)  extensión (.xlsx) con la información obtenida anteriormente. |
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||6. Fin del Caso de Uso.|

|<p></p><p><a name="fa05"></a>**FA05 Selecciona una opción en el campo de selección “Nivel de esquema”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT selecciona una opción en el campo **“Nivel de esquema”**.|2. Identifica la opción seleccionada para desglosar o contraer las tareas, de acuerdo con la **(RNA57)**.|
||3. Muestra las tareas contraídas o desglosadas de acuerdo con la opción seleccionada.|
||4. Continúa en el paso **33** del Flujo primario.|

|<p></p><p></p><p></p><p></p><p></p><p></p><p><a name="fa06"></a>**FA06 El archivo adjunto no cumple las características**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA06** inicia cuando la estructura del archivo adjunto es incorrecta.|
||2. Muestra el **(MSG004)**, con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso [**6**](#_ref163808420) del Flujo primario.|

|<p></p><p><a name="fa07"></a>**FA07 Selecciona la opción “Cancelar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA07** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”**.|2. Muestra el **([**MSG005**](#msg005))** con las opciones “Sí” y “No”.|
|3. Selecciona una opción.|<p>4. Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“No”** permanece en el paso donde fue invocado.</p>|
||5. Cancela la operación sin completar el movimiento que estaba en proceso.|
||6. Continúa en el paso **5** del Flujo primario.|

|<p></p><p><a name="fa11"></a><a name="fa08"></a>**FA08 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA08** inicia cuando interviene un evento ajeno y no se pueden almacenar las Pistas de Auditoría.|
||2. Cancela la operación sin completar el movimiento que estaba en proceso. |
||<p>3. Muestra el mensaje de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT** o **INSR**, se muestra el **([**MSG006**](#msg006))**.</p><p>&emsp;</p><p>- En caso de que la pista de auditoría sea por el tipo de movimiento **PRNT**, se muestra el **(MSG007)**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría.  |

|<p></p><p><a name="fa12"></a><a name="fa09"></a>**FA09 Selecciona la opción “Mostrar todas las tareas”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA09** inicia cuando el Empleado SAT selecciona la opción **“Mostrar todas las tareas”**.|2. Despliega todas las tareas con el nivel de esquema 1 en delante, de acuerdo con la **(RNA58)**.|
||3. Continúa en el paso **33** del Flujo primario.|

|<p></p><p>**FA10 Selecciona la opción “Calcular todos los proyectos”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA10** inicia cuando el Empleado SAT selecciona la opción **“Calcular todos los proyectos”**.|<p>2. Calcula los siguientes datos de todos los proyectos activos de acuerdo con la **(RNA56)**:</p><p>&emsp;</p><p>&emsp;- Duración real (En caso de que aplique)</p><p>&emsp;- Fecha inicio real (En caso de que aplique)</p><p>&emsp;- Fecha fin real (En caso de que aplique)</p><p>&emsp;- Completado % (Solo tareas padre)</p><p>&emsp;- Planeado % (Tareas padre y/o hijo)</p>|
||3. Valida si existen tarea con un valor en el campo “Completado %” y aplica la **(RNA59)**.|
||4. Obtiene y muestra la fecha actual para colocarla en el campo “Última modificación” sin opción a editar.|
||<p>5. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>**Módulo**= Proyecto -PlanDeTrabajo</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= </p><p>- Id de proyecto - Plan de trabajo – Cálculos masivos</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **(FA05)**.</p>|
||<p>6. Guarda en la BD la siguiente información de todos los planes de trabajo activos:</p><p>&emsp;</p><p>&emsp;- Última modificación: Fecha de sistema (Día actual)</p><p></p><p>Tabla (Plan de trabajo):</p><p>- Duración real</p><p>&emsp;- Fecha inicio real</p><p>&emsp;- Fecha fin real</p><p>&emsp;- Completado %</p><p>&emsp;- Planeado % (Tareas padre y/o hijo)</p>|
||7. Muestra una ventana emergente de carga con el mensaje “Calculando” sin opción a cerrar o cancelar .|
||8. Muestra el **([**MSG003**](#msg003))** con la opción “Aceptar”.|
|9. Selecciona la opción **“Aceptar”**.|10. Cierra el mensaje.|
||11. Fin del Caso de uso.|

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
||
|<h3><a name="_toc169782631"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_CargarPlanDeTrabajo</p><p>- 17\_3083\_ECU\_ModificarPlanDeTrabajo</p><p></p>|
|<h3><a name="_toc169782632"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|<p>Se está adjuntando un nuevo plan de trabajo y sobrescribirá el existente.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg002"></a>**MSG002**|Plan de trabajo guardado correctamente.|
|<a name="msg003"></a>**MSG003**|No se encontró un plan de trabajo asignado al proyecto.|
|<a name="msg004"></a>**MSG004**|Se debe adjuntar un archivo de Excel con extensión (.xlsx).|
|<a name="msg005"></a>**MSG005**|<p>Se perderá toda la información no guardada.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg006"></a>**MSG006**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|<p>Por cada tarea se tendrán que ingresar valores en los siguientes campos obligatoriamente: "Id tarea", "Nivel de esquema", "Nombre de la tarea", "Activo", "Duración planeada", "Fecha de inicio planeada" y "Fecha fin planeada". </p><p>Consulte el “Plan tipo”.</p>|
|<a name="msg009"></a>**MSG009**|<p>El "Id tarea" debe ser único e irrepetible. </p><p>Consulte el “Plan tipo”.</p>|
|**MSG010**|<p>Solo puede haber un nivel de esquema con el valor "Cero". </p><p>Consulte el “Plan tipo”.</p>|
|**MSG011**|<p>Por cada tarea(hijo) debe haber una tarea (padre). </p><p>Consulte el “Plan tipo”.</p>|
|**MSG012**|<p>Los valores para "Activo" deben ser "Sí" o "No". </p><p>Consulte el “Plan tipo”.</p>|
|**MSG013**|<p>Los porcentajes deben ser enteros (Aplicar reglas de redondeo). </p><p>Consulte el “Plan tipo”.</p>|
|**MSG014**|<p>Ninguna tarea (Padre) puede tener la fecha de inicio planeada menor que su conjunto sus tareas (hijos). </p><p>Consulte el “Plan tipo”.</p>|
|**MSG015**|<p>Ninguna tarea (padre) puede tener la fecha fin planeada mayor que su conjunto de tareas (hijos).</p><p>Consulte el “Plan tipo”.</p>|
|**MSG016**|Para calcular los valores de los campos "Duración real", obligatoriamente se necesita los valores de los campos "Fecha inicio real" y "Fecha fin real". |
|**MSG017**|Para calcular los valores de los campos "Fecha inicio real", obligatoriamente se necesita los valores de los campos "Duración real" y "Fecha fin real". |
|**MSG018**|Para calcular los valores de los campos "Fecha fin real", obligatoriamente se necesita los valores de los campos "Fecha inicio real" y "Duración real". |
|**MSG019**|<p>El valor para el campo "Predecesora" debe tener un id menor que la tarea que la invoca. </p><p>Consulte el “Plan tipo”.</p>|
|**MSG020**|<p>No se pueden realizar modificaciones en los encabezados, añadir o eliminar columnas. </p><p>Consulte el “Plan tipo”.</p>|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc169782633"></a>**10. Requerimientos No Funcionales** </h3>|
||

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001**|Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|
|**RNF002**|Concurrencia|<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p></p><p>` `El número de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT.</p>|
|**RNF003**|Seguridad|El acceso solo podrá ser otorgado a todo Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema.|
|**RNF004**|Usabilidad|<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación:  </p><p></p><p>- Mensajes tipo flotantes (tooltips) con información de la herramienta que ofrece ayuda contextual, como guía para el Empleado SAT.  </p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado.  </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales.</p>|
|**RNF005**|Eficiencia|Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT. |
|**RNF006**|Usabilidad|<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100:  </p><p>  </p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta).  </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta).  </p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).  </p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual, con el resultado de la consulta).  </p><p>  </p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente. </p>|
|**RNF007**|Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.   |
|**RNF008**|Fiabilidad |El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema. |
|**RNF009**|Seguridad|Mantener la información en pantalla en caso de un error al guardar las pistas de auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema. |
|**RNF010**|Integridad |Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena. |

|<p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc169782634"></a>**11. Diagrama de actividad** </h3>|
|<p></p><p>![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.010.png)</p><p>![](Aspose.Words.0524e585-3b28-4b4a-8d51-842c288ea1e8.011.png)</p>|
|<h3><a name="_toc169782635"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no hay cambios significativos de estados ni transiciones.</p><p></p>|
|<h3><a name="_toc169782636"></a>**13. Aprobación del cliente** </h3>|
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

|||Página 16 de 17|
| :- | :-: | -: |

