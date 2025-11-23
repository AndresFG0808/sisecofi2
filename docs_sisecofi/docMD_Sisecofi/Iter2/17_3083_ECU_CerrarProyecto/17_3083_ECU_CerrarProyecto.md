|![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_CerrarProyecto.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |

**<ID Requerimiento>**8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :- | :-: |
|*1*|*Creación del documento*|Eduardo Acosta Mora|*08/03/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*17/04/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*21/06/2024*|



**Tabla de Contenido**

[17_3083_ECU_CerrarProyecto	2](#_toc170140686)

[1. Descripción	2](#_toc170140687)

[2. Diagrama del Caso de Uso	2](#_toc170140688)

[3. Actores	2](#_toc170140689)

[4. Precondiciones	2](#_toc170140690)

[5. Post condiciones	3](#_toc170140691)

[6. Flujo primario	3](#_toc170140692)

[7. Flujos alternos	8](#_toc170140693)

[8. Referencias cruzadas	25](#_toc170140694)

[9. Mensajes	25](#_toc170140695)

[10. Requerimientos No Funcionales	26](#_toc170140696)

[11. Diagrama de actividad	29](#_toc170140697)

[12. Diagrama de estados	29](#_toc170140698)

[13. Aprobación del cliente	30](#_toc170140699)


### **<a name="_toc170140686"></a>**17\_3083\_ECU\_CerrarProyecto

|<h3><a name="_toc170140687"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT la revisión y validación de los documentos adjuntos al proyecto, incluyendo sus contratos y/o convenios modificatorios, dictámenes y facturas relacionados durante la vida del proyecto. Esta verificación permitirá determinar si los documentos cumplen las condiciones establecidas para el cierre final del proyecto.</p><p></p>|
|<h3><a name="_toc170140688"></a>**2. Diagrama del Caso de Uso**</h3>|
|![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.002.png)|
|<h3><a name="_toc170140689"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc170140690"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida. </p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa al sistema.</p><p>- Se le ha asignado el rol requerido al Empleado SAT para ingresar al módulo “Proyectos” y a la sección “Verificación de RCP” con los permisos correspondientes.</p><p>- El sistema ha validado que el Empleado SAT cuenta con el rol para ingresar al módulo “Proyectos” y a la sección “Verificación de RCP” con los permisos correspondientes.</p><p>- Se le ha asignado el proyecto al Empleado SAT.</p><p>- El sistema ha validado que el proyecto debe tener el estatus “En proceso de cierre”.</p><p>- Se han adjuntado los documentos solicitados en la matriz documental de acuerdo con la asociación de fases correspondientes al proyecto.</p><p></p>|
|<h3><a name="_toc170140691"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT actualizó el estatus del “Entregable” de acuerdo con sus necesidades.</p><p>- El Empleado SAT visualizó documentos tipo PDF.</p><p>- El Empleado SAT modificó un registro.</p><p>- El Empleado SAT descargó uno o más documentos.</p><p>- El Empleado SAT guardó el avance de la verificación.</p><p>- El Empleado SAT modificó el estatus RCP a “Revisado por AP”.</p><p>- El Empleado SAT modificó el estatus RCP a “Validado por LP”.</p><p>- El Empleado SAT modificó el estatus RCP a “En proceso”.</p><p>- El Empleado SAT modificó el estatus RCP a “Cancelado”.</p><p>- El Empleado SAT generó el RCP para su exportación.</p><p></p>|
|<h3><a name="_toc170140692"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT ingresa al módulo “Proyectos”, y a la sección **“Verificación de RCP”**.|<p>2. Realiza lo siguiente:</p><p>&emsp;</p><p>&emsp;Valida que el estatus de proyecto esté en “Proceso de cierre”, consulta en la base de datos (BD) la información para los siguientes catálogos teniendo en cuenta la regla de negocio **(RNA01)**:</p><p>&emsp;</p><p>- Área de planeación</p><p>- Estatus</p><p></p><p>- En caso contrario, muestra el mensaje **(MSG017)** con la opción de aceptar y continúa en el **(17\_3083\_ECU\_ModificarProyecto)**.</p>|
||<p>3. Consulta en la BD los siguientes campos relacionados con el proyecto:</p><p></p><p>- Nombre corto (del proyecto)</p><p>- Id proyecto sistema</p><p>- Estatus RCP (por defecto “En proceso”) **(RNA178)**</p><p>- Id proyecto AGP</p><p>- Nombre completo del proyecto</p><p>- Líder del proyecto</p><p>- Área de planeación</p><p>- Fecha de entrega</p><p>- % Planeado</p><p>- % Real</p><p></p><p>Tabla (RCP): </p><p>- # (Número consecutivo)</p><p>- Entregable **(RNA179)**</p><p>- Fase **(RNA179)**</p><p>- Estatus **(RNA180)**</p><p>- Fecha del documento</p><p>- Justificación</p><p>- # de páginas</p><p></p><p>- En caso de que el proyecto no tenga asignada una plantilla documental, continúa en el flujo alterno **([**FA01**](#fa01))**.</p>|
||<p>4. <a name="_ref164085457"></a><a name="_ref166671585"></a>Muestra en otra pantalla la sección “Verificación de RCP” de acuerdo con la **(RNA181)**, **(RNA182) y (RNA185)**:</p><p>&emsp;</p><p>- Nombre corto (del proyecto) (únicamente consulta)</p><p>- Id proyecto sistema (únicamente consulta)</p><p>- Estatus RCP (únicamente consulta)</p><p>- Id proyecto AGP (únicamente consulta)</p><p>- Nombre completo del proyecto (únicamente consulta)</p><p>- Líder del proyecto: (únicamente consulta)</p><p>- Área de planeación\*</p><p>- Fecha de entrega</p><p>- % Planeado (únicamente consulta). Aplica la **(RNA183**)</p><p>- % Real (únicamente consulta). Aplica la **(RNA184)**</p><p>&emsp;</p><p>Opción:</p><p>- Cancelar estatus RCP![ref1]</p><p>&emsp;</p><p>Tabla (RCP). Aplica la **(RNA244)**:</p><p>- # (Número consecutivo)</p><p>- Entregable</p><p>- Fase</p><p>- Estatus</p><p>- Fecha del documento</p><p>- Justificación</p><p>- # de páginas</p><p>- Acciones</p><p></p><p>Opciones:</p><p>- SATCloud ![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.004.png)</p><p>- Descargar ZIP ![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.005.png)</p><p>- Editar ![ref2]</p><p>- Descargar documento ![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.007.png)</p><p>- Ver PDF ![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.008.png)</p><p>- En proceso (No visible)</p><p>- Regresar</p><p>- Cancelar</p><p>- Guardar</p><p>- Revisado por AP</p><p>- Validado por LP (No visible si el estatus RCP es “En proceso”)</p><p>- Generar RCP (No visible si el estatus RCP esta “En proceso” o “Revisado por Área de Planeación”)</p><p>- Campos para “Filtrar” por columna</p><p></p><p>Ver **(17\_3083\_EIU\_CerrarProyecto)** Estilos 01.</p>|
|5. Selecciona una opción del campo **“Área de planeación”** y si lo requiere ingresa la **“Fecha de entrega”**, el flujo continúa.||
|<p>6. <a name="_ref164085417"></a><a name="_ref166671393"></a>Selecciona una opción: </p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Editar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Descargar documento”**, continúa en el **([**FA06**](#fa06))**.</p><p></p><p>- En caso de que seleccione la opción **“Ver PDF”**, continúa en el **([](#fa07)**</p><p>- [**FA07**](#fa07)**)**.</p><p></p><p>- En caso de que seleccione la opción para **“Filtrar”** los campos de la tabla, continúa en el **([**FA08**](#fa08))**.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar estatus RCP”**, continúa en el **([](#fa03)**</p><p>- [**FA03**](#fa03)**)**.</p>|<p>7. <a name="_ref166671423"></a>Habilita los siguientes campos sobre el registro que seleccionó:</p><p>&emsp;</p><p>- Estatus. Aplica la **(RNA180)**</p><p>- Fecha del documento</p><p>- Justificación</p><p>- # de páginas</p><p>- Acciones</p><p>&emsp;- Descartar ![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.009.png)</p><p></p>|
|8. Selecciona una opción en el campo **“Estatus”**. |9. Valida el cambio de valor que hizo en el campo “Estatus” de acuerdo con la **(RNA188)**.|
|<p>10. <a name="_ref164085570"></a>Realiza lo siguiente: </p><p></p><p>- Ingresa la información que requiera, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Descartar”**, el flujo continúa en el **(FA02)**. </p>||
|<p>11. Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Guardar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”**, continúa en el **([**FA02**](#fa02))**.</p><p></p><p>- En caso de que seleccione la opción **“Regresar”**, continúa en el **([**FA09**](#fa09))**.</p><p></p><p>- En caso de que seleccione la opción **“SATCloud”**, continúa en el **([**FA10**](#fa10))**.</p><p></p><p>- En caso de que seleccione la opción **“Descargar ZIP”**, continúa en el **([**FA11**](#fa11))**.</p>|<p>12. <a name="_ref166671411"></a>Valida que se haya ingresado información en el siguiente campo obligatorio de acuerdo con la **(RNA186)**:</p><p>&emsp;</p><p>- Área de planeación </p><p></p><p>- En caso de que no se haya ingresado información en el campo obligatorio, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>13. Valida la estructura de la información ingresada de acuerdo con la **(RNA189)**.</p><p>&emsp;</p><p>- En caso de que no se cumpla la estructura de la información ingresada, continúa en el **([**FA12**](#fa12))**.</p>|
||14. Calcula el porcentaje para los campos “% Planeado” y “% Real” de acuerdo con la **(RNA183)** y **(RNA184)**.|
||<p>15. Almacena en la BD la información de las Pistas de Auditoría.</p><p></p><p>Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= Cuando sea la primera vez que se ingrese información el tipo de movimiento debe ser **INSR** (Insertar).</p><p>Cuando sea la modificación del mismo registro el tipo de movimiento debe ser **UPDT** (modificar).</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-Nombre corto (Proyecto)</p><p>-Área de planeación</p><p>-Fecha de entrega</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>16. Almacena en la BD la siguiente información:</p><p></p><p>- Área de planeación</p><p>- Fecha de entrega</p><p>- % Planeado</p><p>- % Real</p><p>- # (Número consecutivo)</p><p>- Entregable</p><p>- Fase</p><p>- Estatus</p><p>- Fecha del documento</p><p>- Justificación</p><p>- # de páginas</p>|
||17. Muestra el mensaje **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|18. Selecciona la opción **“Aceptar”**.|19. Cierra el mensaje.|
|<p>20. <a name="_ref164086476"></a>Si lo requiere, selecciona alguna de las siguientes opciones:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“En proceso”**, continúa en el **([**FA13**](#fa13))**.</p><p></p><p>- En caso de que seleccione la opción **“Revisado por AP”**, continúa en el **([**FA14**](#fa14))**.</p><p></p><p>- En caso de que seleccione la opción **“Validado por AP”**, continúa en el **([**FA15**](#fa15))**.</p><p></p><p>- En caso de que seleccione la opción **“Generar RCP”**, continúa en el **([**FA16**](#fa16))**.</p><p></p><p>- En caso de que seleccione la opción **“Regresar”**, continúa en el **([**FA09**](#fa09))**.</p>|21. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc170140693"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 No se obtuvo información de la consulta para la tabla “RCP”**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
||1. ` `El **FA01** inicia cuando el sistema identifica que el proyecto no tiene asociada ninguna plantilla documental.|
||2. Muestra el **([**MSG002**](#msg002))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. El proceso continúa en el **(17\_3083\_ECU\_AsociarFasesMatrizDoc)**.|

|<p></p><p></p><p></p><p></p><p></p><p><a name="fa02"></a>**FA02 Selecciona la opción “Cancelar”, “Descartar”, “Cerrar ventana” o “Cerrar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA02** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”**, **“Descartar”**, **“Cerrar ventana” o “Cerrar”**.|2. Muestra el **([**MSG003**](#msg003))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona una opción:</p><p></p>|<p>4. <a name="_ref166671470"></a>Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“No”**, permanece en el paso donde fue invocado.</p><p>&emsp;</p><p></p>|
||<p>5. Realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el “Cancelar” desde la pantalla “Verificación de RCP”, cancela la operación sin completar el movimiento que estaba en proceso.</p><p>- Si fue invocado en el “Cancelar” o “Cerrar ventana” en la pantalla “Generar RCP”, cierra el modal.</p><p>- Si fue invocado en la opción “Descartar”:</p><p>- Se inicializa el registro de la tabla de la sección donde fue invocado, y cambia a solo lectura si era un registro almacenado regresando los íconos a su estado original.</p>|
||<p>6. Dependiendo la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- En caso de que haya sido llamado en el paso 10 del Flujo primario, continúa en el paso **6** del Flujo primario.</p><p></p><p>- En caso de que haya sido llamado en el paso 11 del Flujo primario, continúa en el paso **4** del Flujo primario.</p><p></p><p></p><p>- En caso de que haya sido llamado en el paso 11 del **([**FA16**](#fa16))**, continúa en el paso **20** del Flujo primario.</p>|

|<p></p><p><a name="fa03"></a>**FA03 Selecciona la opción “Cancelar estatus RCP”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA03** inicia cuando el Empleado SAT selecciona la opción **“Cancelar estatus RCP”**.|2. Muestra el **([**MSG004**](#msg004))** con las opciones “Sí” y “No”.|
|3. Selecciona una opción.|<p>4. Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“No”**, permanece en el paso donde fue invocado.</p>|
||<p>5. <a name="_ref166671670"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p></p><p>Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= </p><p>-Nombre corto (Proyecto).</p><p>-Estatus RCP</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>6. Inactiva lo siguiente:</p><p>&emsp;</p><p>- Área de planeación\*</p><p>- Fecha de entrega</p><p>&emsp;</p><p>Opciones:</p><p>- Cancelar estatus RCP![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.010.png)</p><p>&emsp;</p><p>Tabla (RCP):</p><p>- # (Número consecutivo)</p><p>- Entregable</p><p>- Fase</p><p>- Estatus</p><p>- Fecha del documento</p><p>- Justificación</p><p>- # de páginas</p><p></p><p>Opciones:</p><p>- Editar![ref2]</p><p>- Cancelar</p><p>- Guardar</p><p>- Revisado por AP</p><p>- Validado por LP (No visible)</p><p>- Generar RCP (No visible)</p>|
||7. Modifica el valor del campo “Estatus RCP” a “Cancelado”.|
||8. Fin del Caso de Uso.|

|<p></p><p><a name="fa04"></a>**FA04 No se ingresaron los datos obligatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA04** inicia cuando el sistema identifica que no se ingresaron los datos obligatorios.|
||2. Muestra en rojo los campos pendientes de capturar.|
||3. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar”. |
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||<p>6. Dependiendo la situación donde haya sido llamado, realiza lo siguiente:</p><p>&emsp;</p><p>- En caso de que no haya sido capturado el campo “Área de planeación”, continúa en el paso **5** del Flujo primario.</p><p></p><p></p><p>- En caso de que haya sido llamado en el paso 5 del **([**FA14**](#fa14))**, continúa en el paso **6** del Flujo primario.</p><p></p><p>- En caso de que haya sido llamado en el paso 2 del **([**FA16**](#fa16))**, continúa en el paso [¡Error! No se encuentra el origen de la referencia.](#_ref166671703) del Flujo primario.</p><p></p><p>- En caso de que haya sido llamado en el paso 12 del **([**FA16**](#fa16))**, continúa en el paso **8** del **([**FA16**](#fa16))**.</p>|

|<p></p><p><a name="fa05"></a>**FA05 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA05** inicia cuando interviene un evento ajeno y no se puede almacenar las Pistas de Auditoría.|
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT** e **INSR**, se muestra el **([**MSG006**](#msg006))**.</p><p></p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG007**](#msg007))**.</p><p></p><p>- En caso de que la pista de auditoría sea por el tipo de movimiento **PRNT**, se muestra el **([**MSG008**](#msg008))**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría.    |

|<p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Descargar documento”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT selecciona la opción **“Descargar documento”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-Nombre corto (Proyecto)</p><p>-Documento seleccionado</p><p>-Fase </p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||3. Consulta el documento almacenado en la BD.|
||4. Descarga el archivo.|
||5. Continúa en el paso **6** del Flujo primario.|

|<p></p><p><a name="fa07"></a>**FA07 Selecciona la opción “Ver PDF”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA07** inicia cuando el Empleado SAT selecciona la opción **“Ver PDF”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consulta)</p><p>**Movimiento**= </p><p>-Nombre corto (Proyecto)</p><p>-Documento seleccionado</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||3. Procesa el archivo PDF para su visualización.|
||<p>4. Muestra en una pantalla emergente lo siguiente:</p><p>&emsp;</p><p>- Panel de visualización (Muestra la información del archivo adjuntado).</p><p>&emsp;</p><p>Opciones:</p><p>- Cerrar</p><p>- Cerrar ventana![ref3]</p><p></p><p>Ver **(17\_3083\_EIU\_CerrarProyecto)** Estilos 02</p>|
|5. Selecciona la opción **“Cerrar”** o la opción **“Cerrar ventana”**.|6. Cierra la ventana emergente.|
||7. Continúa en el paso **6** del Flujo primario.|

|<p></p><p><a name="fa08"></a>**FA08 Selecciona la opción para “Filtrar” los campos de la tabla**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. EL **FA08** inicia cuando el Empleado SAT selecciona la opción para **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||5. Continúa en el paso **6** del Flujo primario.|

|<p></p><p><a name="fa09"></a>**FA09 Selecciona la opción “Regresar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA09** inicia cuando el Empleado SAT selecciona la opción **“Regresar”**.|2. Muestra el **([**MSG009**](#msg009))** con las opciones “Sí” y “No”.|
|3. Selecciona una opción.|<p>4. <a name="_ref166671999"></a>Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“No”**, permanece en el paso donde fue invocado</p>|
||5. El proceso continúa en el **(17\_3083\_ECU\_ModificarProyecto)**.|

|<p></p><p><a name="fa10"></a>**FA10 Selecciona la opción “SATCloud”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA10** inicia cuando el Empleado SAT selecciona la opción **“SATCloud”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- Nombre de los documentos descargados en una cadena por separado por | (pipes)</p><p>- Ejemplo: (01\_FTO\_JC\_MDR4.PDF|SCP\_MDR4.PDF</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>3. Muestra en una ventana emergente la pantalla “Datos de la descarga” lo siguiente: </p><p>&emsp;</p><p>&emsp;Campos</p><p>- url (enlace)</p><p>- contraseña</p><p></p><p>Opciones</p><p>- Copiar contraseña ![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.012.png)</p><p>- Cerrar</p><p>- Cerrar ventana ![ref3]</p><p></p><p>- En caso de que no se muestre el enlace y la contraseña, continúa en el **([**FA19**](#fa19))**.</p><p>&emsp;</p><p>Ver</p><p>**(17\_3083\_EIU\_CerrarProyecto)** Estilos 04.</p>|
|<p>4. Realiza lo siguiente:</p><p></p><p>- En caso de que copie la contraseña y selecciona el enlace, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cerrar”**, continúa en el paso **11** del Flujo primario.</p>|5. <a name="_ref166672060"></a>Realiza la conexión con el SATCloud para mostrar el archivo a descargar.|
|6. Realiza lo correspondiente para descargar los documentos adjuntos.|7. Descarga el archivo con extensión (.ZIP).|
||8. Continúa en el paso **11** del Flujo primario.|

|<p></p><p><a name="fa11"></a>**FA11 Selecciona la opción “Descargar ZIP”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA11** inicia cuando el Empleado SAT selecciona la opción **“Descargar ZIP”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- Nombre de los documentos descargados en una cadena por separado por | (pipes)</p><p>- Ejemplo: (01\_FTO\_JC\_MDR4.PDF|SCP\_MDR4.PDF</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||3. Consulta en la BD los archivos que se tienen relacionados con el proyecto y sus contratos y/o convenios modificatorios, dictámenes y facturas relacionados.|
||4. Descarga el archivo en extensión (.ZIP) que contiene los archivos adjuntos.|
||5. Continúa en el paso **11** del Flujo primario.|

|<p></p><p><a name="fa12"></a>**FA12 Estructura de información ingresada incorrecta**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA12** inicia cuando el sistema identifica que la estructura de la información ingresada es incorrecta.|
||2. Muestra el **([**MSG010**](#msg010))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso **10** del Flujo primario.|

|<p></p><p><a name="fa13"></a>**FA13 Selecciona la opción “En proceso”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA13** inicia cuando el Empleado SAT selecciona la opción **“En proceso”**.|2. Muestra el **([**MSG011**](#msg011))** con las opciones “Sí” y “No”.|
|3. Selecciona una opción.|<p>4. <a name="_ref166672169"></a>Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“No”**, permanece en el paso donde fue invocado.</p>|
||<p>5. Almacena en la BD la información de las Pistas de Auditoría.</p><p></p><p>Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= </p><p>-Nombre corto (Proyecto)</p><p>-Estatus RCP</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||6. Modifica el valor del campo “Estatus RCP” a “En proceso”.|
||7. Guarda en la BD el valor del campo “Estatus RCP”.|
||<p>8. Activa lo siguiente de acuerdo con la **(RNA182)**:</p><p>&emsp;</p><p>&emsp;Campos:</p><p>- Área de planeación\*</p><p>- Fecha de entrega</p><p></p><p>Opciones:</p><p>- Cancelar estatus RCP![ref1]</p><p>- Cancelar</p><p>- Guardar </p><p>- Editar![ref2]</p><p>- Guardar</p><p>- Revisado por AP</p><p>- Campos para “Filtrar” por columna</p>|
||<p>9. Se ocultan las siguientes opciones:</p><p>&emsp;</p><p>- Validado por LP </p><p>- Generar RCP</p>|
||10. Continúa en el paso **6** del Flujo primario. |

|<p></p><p><a name="fa14"></a>**FA14 Selecciona la opción “Revisado por AP”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA14** inicia cuando el Empleado SAT selecciona la opción **“Revisado por AP”**.|2. Muestra el **([**MSG012**](#msg012))** con las opciones “Sí” y “No”.|
|3. Selecciona una opción. |<p>4. <a name="_ref166672228"></a>Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“No”**, permanece en el paso donde fue invocado.</p>|
||<p>5. Valida que se haya ingresado información en los campos obligatorios de acuerdo con la **(RNA186)**.</p><p>&emsp;</p><p>- En caso de que no se haya ingresado información en los campos obligatorios, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>6. Valida que no exista ningún entregable con estatus “Pendiente”.</p><p>&emsp;</p><p>- En caso de que exista un entregable con estatus “Pendiente”, continúa en el **([**FA17**](#fa17))**. </p>|
||<p>7. Valida que el valor del campo “Fecha del documento” no sea menor que la fecha de inicio del proyecto y mayor a la fecha fin del proyecto.</p><p>&emsp;</p><p>- En caso de que no se cumpla, continúa en el **([](#fa18)**</p><p>- [**FA18**](#fa18)**)**.</p>|
||<p>8. Almacena en la BD la información de las Pistas de Auditoría.</p><p></p><p>Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-Nombre corto (Proyecto)</p><p>-Estatus RCP</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||9. Modifica el valor del campo “Estatus RCP” a “Revisado por Área de Planeación”.|
||<p>10. Guarda en la BD lo siguiente:</p><p>&emsp;</p><p>- Área de planeación</p><p>- Fecha de entrega</p><p>- % Planeado</p><p>- % Real</p><p>- # (Número consecutivo)</p><p>- Entregable</p><p>- Fase</p><p>- Estatus</p><p>- Fecha del documento</p><p>- Justificación</p><p>- # de páginas</p>|
||<p>11. Inactiva lo siguiente:</p><p>&emsp;</p><p>&emsp;Campo:</p><p>- Área de planeación\*</p><p></p><p>Opciones: </p><p>- Cancelar</p><p>- Guardar</p><p>- Editar ![ref2]</p><p>- Revisado por AP</p>|
||12. Se activa y visualiza las opciones “Validado por LP” de acuerdo con la **(RNA182)**.|
||13. Continúa en el paso **20** del Flujo primario. |

|<p></p><p><a name="fa15"></a>**FA15 Selecciona la opción “Validado por LP”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA15** inicia cuando el Empleado SAT selecciona la opción **“Validado por LP”**.|2. Muestra el **([**MSG013**](#msg013))** con las opciones “Sí” y “No”.|
|3. Selecciona una opción. |<p>4. <a name="_ref166672296"></a>Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“No”**, permanece en el paso donde fue invocado.</p>|
||<p>5. Almacena en la BD la información de las Pistas de Auditoría.</p><p></p><p>Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= </p><p>-Nombre corto (Proyecto).</p><p>-Estatus RCP</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||6. Modifica el valor del campo “Estatus RCP” a “Validado por Líder de Proyecto”.|
||7. Guarda en la BD el valor de los campos “Estatus RCP” y “Fecha de entrega”.|
||8. Se ocultan las opciones “Revisado por AP” y “Validado por LP”. |
||9. Activa y se visualiza la opción “Generar RCP” de acuerdo con la **(RNA182)**.|
||10. Continúa en el paso **20** del Flujo primario.|

|<p></p><p><a name="fa16"></a>**FA16 Selecciona la opción “Generar RCP”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA16** inicia cuando el Empleado SAT selecciona la opción **“Generar RCP”**.|<p>2. Valida que se haya ingresado un valor en el campo “Fecha de entrega”.</p><p>&emsp;</p><p>- En caso de que no se haya ingresado una fecha de entrega, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>3. Valida que el valor del campo “Fecha de entrega” no sea menor que la fecha de inicio del proyecto y mayor a la fecha fin del proyecto.</p><p>&emsp;</p><p>- En caso de que no se cumpla, continúa en el **([](#fa18)**</p><p>- [**FA18**](#fa18)**)**.</p>|
||4. Obtiene del administrador de plantillas la correspondiente a “RCP”.|
||5. Modifica y guarda en la BD el valor del campo “Estatus RCP” a “RCP entregado”.|
||<p>6. Almacena en la BD la información de las Pistas de Auditoría.</p><p></p><p>Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= </p><p>-Nombre corto (Proyecto).</p><p>-Estatus RCP</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>7. <a name="_ref166671931"></a>Muestra en una ventana emergente la pantalla “Generar RCP” con lo siguiente:</p><p>&emsp;</p><p>- Panel de visualización (Muestra la información del archivo RCP)</p><p>- Tipo de plantilla\*</p><p>- Formato para exportar\*</p><p>&emsp;- PDF</p><p>&emsp;- Excel</p><p></p><p>Opciones:</p><p>- Previsualizar (Inactivo)</p><p>- Cancelar</p><p>- Aceptar (Inactivo)</p><p>- Cerrar ventana![ref4]</p><p></p><p>Ver **(17\_3083\_EIU\_CerrarProyecto)** Estilos 03</p>|
|8. <a name="_ref164085500"></a>Selecciona una opción en el campo **“Tipo de plantilla”**.||
|9. Selecciona una opción para el campo **“Formato para exportar”**.|10. Activa la opción “Previsualizar”.|
|<p>11. <a name="_ref166671548"></a>Selecciona una opción.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Previsualizar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”** o **“Cerrar ventana”**, continúa en el **([**FA02**](#fa02))**.</p>|<p>12. <a name="_ref166672321"></a>Valida que se haya seleccionado una opción en los campos obligatorios de acuerdo con la **(RNA186)**.</p><p>&emsp;</p><p>- En caso de que no haya seleccionado una opción, continúa en el **([**FA04**](#fa04))**.</p>|
||13. Procesa la información de la tabla “RCP” generada para el proyecto con los datos seleccionados anteriormente.|
||14. Activa la opción “Aceptar”.|
||<p>15. Muestra la vista previa de la generación del RCP.</p><p></p>|
|<p>16. <a name="_ref166671558"></a>Selecciona una opción. </p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Aceptar”**, el flujo continúa.</p>|<p>17. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Proyecto – VerificaciónDeRCP</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-Nombre corto (Proyecto)</p><p>-Tipo de plantilla</p><p>-Generación de archivo RCP</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**. </p>|
||18. <a name="_ref166672347"></a>Descarga el archivo generado de acuerdo con la opción seleccionada en el campo “Formato para exportar”.|
||19. Fin del Caso de Uso.|

|<p></p><p></p><p></p><p></p><p></p><p><a name="fa17"></a>**FA17 Existen entregables con estatus “Pendiente”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA17** inicia cuando el sistema identifica que hay entregables con estatus **“Pendiente”**.|
||2. Muestra el **([**MSG014**](#msg014))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso **6** del Flujo primario.|

|<p></p><p><a name="fa18"></a>**FA18 Las fechas de los documentos o la fecha de entrega es menor a la fecha de inicio del proyecto o mayor a la fecha fin del proyecto**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA18** inicia cuando el sistema identifica que el valor del campo “Fecha del documento” o “Fecha de entrega” es menor a la fecha de inicio del proyecto o mayor a la fecha fin del proyecto.|
||2. Muestra el **([**MSG015**](#msg015))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||<p>5. Dependiendo la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso 7 del **(FA14)**, continúa en el paso **6** del Flujo primario.</p><p>&emsp;</p><p>- Si fue invocado en el paso 3 del **(FA16)**, continúa en el paso **5** del Flujo primario.</p>|

|<p></p><p><a name="fa19"></a>**FA19 Error al generar el enlace y contraseña** </p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA19** inicia cuando no se muestra el enlace y la contraseña.|
||2. Muestra el **([**MSG016**](#msg016))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso **11** del Flujo primario.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc170140694"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_CerrarProyecto</p><p>- 17\_3083\_ECU\_ModificarProyecto</p><p>- 17\_3083\_ECU\_AsociarFasesMatrizDoc</p><p></p>|
|<h3><a name="_toc170140695"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Información guardada correctamente.|
|<a name="msg002"></a>**MSG002**|El proyecto no tiene asignado ninguna plantilla documental.|
|<a name="msg003"></a>**MSG003**|<p>Se perderán todos los cambios realizados.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg004"></a>**MSG004**|<p>Se modificará el “Estatus del RCP” a “Cancelado”.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg005"></a>**MSG005**|Favor de ingresar los datos obligatorios |
|<a name="msg006"></a>**MSG006**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg009"></a>**MSG009**|<p>¿Está seguro de que desea regresar? </p><p>Se perderán todos los cambios no guardados.</p>|
|<a name="msg010"></a>**MSG010**|La estructura de la información ingresada es incorrecta. Intente nuevamente.|
|<a name="msg011"></a>**MSG011**|<p>Se modificará el “Estatus RCP” a “En proceso”.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg012"></a>**MSG012**|<p>Se modificará el “Estatus RCP” a “Revisado por Área de Planeación”.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg013"></a>**MSG013**|<p>Se modificará el “Estatus RCP” a “Validado por Líder de Proyecto”.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg014"></a>**MSG014**|Aún existen entregables con estatus “Pendiente”. Intente nuevamente.|
|<a name="msg015"></a>**MSG015**|Las fechas no pueden ser menor a la fecha de inicio del proyecto o mayor a la fecha fin del proyecto.|
|<a name="msg016"></a>**MSG016**|<p>Error al generar el enlace y contraseña. </p><p>Intente nuevamente.</p>|
|**MSG017**|No puedes continuar si el “Estatus del proyecto” está en “Proceso de cierre”.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc170140696"></a>**10. Requerimientos No Funcionales** </h3>|
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
|**RNF011**|Usabilidad |<p>Usabilidad, El Empleado SAT podrá navegar a través de las páginas resultantes del documento PDF. </p><p>- Ir a la siguiente página (debe mostrar la página consecutiva del documento PDF).  </p><p>- Ir a la página anterior (debe mostrar la página previa del documento PDF). </p>|

|<p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc170140697"></a>**11. Diagrama de actividad** </h3>|
|![](Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.014.png)|
||
|<h3><a name="_toc170140698"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no hay cambios significativos de estados ni transiciones.</p><p></p>|
|<h3><a name="_toc170140699"></a>**13. Aprobación del cliente** </h3>|
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

||
| :- |

|||Página 1 de 24|
| :- | :-: | -: |

[ref1]: Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.003.png
[ref2]: Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.006.png
[ref3]: Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.011.png
[ref4]: Aspose.Words.2559e9d1-c873-42fc-aee7-36a141bab18b.013.png
