|![](Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_ConsultarReporteDeControlDocumental.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento:<a name="_hlk156499682"></a>** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :- | :-: |
|*1*|*Creación del documento*|Eduardo Acosta Mora|*08/07/2024*|
|*1.1*|*Revisión del documento*|Diana Yazmín Pérez Sabido|*18/07/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*18/07/2024*|



**Tabla de Contenido**

[17_3083_ECU_ConsultarReporteDeControlDocumental	2](#_toc175158483)

[1. Descripción	2](#_toc175158484)

[2. Diagrama del Caso de Uso	2](#_toc175158485)

[3. Actores	2](#_toc175158486)

[4. Precondiciones	2](#_toc175158487)

[5. Post condiciones	3](#_toc175158488)

[6. Flujo primario	3](#_toc175158489)

[7. Flujos alternos	7](#_toc175158490)

[8. Referencias cruzadas	14](#_toc175158491)

[9. Mensajes	14](#_toc175158492)

[10. Requerimientos No Funcionales	15](#_toc175158493)

[11. Diagrama de actividad	17](#_toc175158494)

[12. Diagrama de estados	17](#_toc175158495)

[13. Aprobación del cliente	18](#_toc175158496)


### **<a name="_toc175158483"></a>**17\_3083\_ECU\_ConsultarReporteDeControlDocumental

|<h3><a name="_toc175158484"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT la búsqueda de los documentos adjuntos al proyecto, incluyendo sus contratos y/o convenios modificatorios, dictámenes y facturas relacionados durante la vida del proyecto.</p><p></p>|
|<h3><a name="_toc175158485"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.002.png)</p><p></p>|
|<h3><a name="_toc175158486"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc175158487"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida. </p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa al sistema.</p><p>- Se le ha asignado el rol requerido al Empleado SAT para ingresar al módulo “Reporte De Control Documental” con los permisos correspondientes.</p><p>- El sistema ha validado que el Empleado SAT cuenta con el rol para ingresar al módulo “Reporte de Control Documental” con los permisos correspondientes.</p><p>- Se han adjuntado archivos a los proyectos, contratos y/o dictámenes.</p><p>- El Empleado SAT ha ingresado a la opción del menú “Reportes”, submenú “Reporte De Control Documental”, de acuerdo con el proceso del **(17\_3083\_ECU\_AccesoSistema)**.</p><p></p>|
|<h3><a name="_toc175158488"></a>**5. Post condiciones** </h3>|
|<p></p><p>El Empleado SAT realizó lo siguiente:</p><p>- Consultó los archivos de acuerdo con los criterios de búsqueda.</p><p>- Descargó los archivos de acuerdo con los criterios de búsqueda.</p><p>- Visualizó los documentos pendientes.</p><p>- Consultó a detalle los archivos de un proyecto.</p><p></p>|
|<h3><a name="_toc175158489"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT selecciona la opción del submenú **“Reporte de Control Documental”**.|<p>2. Identifica que el Empleado SAT cuente con proyectos asignados de acuerdo con la regla de negocio **(RNA78)**.</p><p>&emsp;</p><p>- En caso de que no cuente con proyectos asignados, continúa en el flujo alterno **([**FA01**](#fa01))**. </p>|
||<p>3. Obtiene de la base de datos (BD) la información de los siguientes catálogos de acuerdo con la **(RNA01)**:</p><p></p><p>- Estatus del proyecto</p><p>- Fase</p>|
||<p>4. Muestra la pantalla “Reporte de Control Documental” inicialmente solo con los criterios de búsqueda y opciones:</p><p>&emsp;</p><p>&emsp;Criterios de búsqueda:</p><p>- Estatus del proyecto</p><p>- Proyecto</p><p>- Documento</p><p>- Fase</p><p>- Plantilla</p><p></p><p>Opciones:</p><p>- Buscar</p><p>- Limpiar</p><p></p><p>Ver **(17\_3083\_EIU\_ConsultarReporteDeControlDocumental)** Estilos 01.</p>|
|<p>5. <a name="_ref172011115"></a>Realiza lo siguiente:</p><p>&emsp;</p><p>- En caso de que seleccione o ingrese un criterio de búsqueda, el flujo continúa: </p><p>- Estatus del proyecto</p><p>- Documento</p><p>- Fase</p><p></p><p>- En caso de que no ingrese un criterio de búsqueda, continúa en el paso [**8**](#_ref172011084) de este flujo.</p>|<p>6. En caso de que seleccione una o más opciones en el campo “Estatus del proyecto”, consulta en la BD los nombres cortos del campo “Proyectos” de acuerdo con lo seleccionado en el campo “Estatus del proyecto”.</p><p>&emsp;</p><p>&emsp;En caso de que seleccione una opción en el campo “Fase”, consulta en la BD la información de las plantillas de acuerdo con la opción seleccionada en el campo “Fase”.</p><p>&emsp;</p><p>- En caso de que haya seleccionado más de una opción en el campo “Fase”, se inhabilita el campo “Plantilla” y continúa en el paso [**8**](#_ref172011084) de este flujo.</p>|
|<p>7. Realiza lo siguiente: </p><p>&emsp;</p><p>- Si lo requiere, selecciona una o más opciones en el campo **“Proyectos”**.</p><p></p><p>- Si lo requiere, selecciona una opción en el campo **“Plantillas”**.</p>||
|<p>8. <a name="_ref172011084"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Buscar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Limpiar”**, el flujo continúa en el **([**FA02**](#fa02))**.</p>|<p>9. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= ReporteDeControlDocumental</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consulta)</p><p>**Movimiento**= </p><p>- Filtros seleccionados e ingresados.</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||<p>10. Muestra la pantalla “Procesando información” con el siguiente mensaje **([**MSG006**](#msg006))**.</p><p></p><p>Ver **(17\_3083\_EIU\_ConsultarReporteDeControlDocumental)** Estilos 04.</p>|
||<p>11. Consulta en la BD la información coincidente con los criterios de búsqueda de acuerdo con la **(RNA109)**:</p><p></p><p>- En caso de que no se obtenga información de la consulta, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>12. Calcula los siguientes valores de acuerdo con la **(RNA50)**:</p><p>&emsp;</p><p>- Documentos requeridos ![ref1]</p><p>- Documentos cargados ![ref2]</p><p>- Documentos que no aplican ![ref3] </p><p>- Documentos pendientes ![ref4]</p>|
||<p>13. Muestra en la pantalla “Reporte de Control Documental” la tabla “Proyectos” con el resultado de la búsqueda.</p><p>&emsp;</p><p>&emsp;Opciones:</p><p>- Exportar a Excel ![ref5]</p><p>- Descargar ZIP ![ref6]</p><p>- SATCloud ![ref7]</p><p></p><p>Datos de los documentos:</p><p>- Documentos requeridos ![ref1]</p><p>- Documentos cargados ![ref2]</p><p>- Documentos que no aplican ![ref3] </p><p>- Documentos pendientes ![ref4]</p><p></p><p>Tabla “Proyectos”. Aplica la **(RNA244)**</p><p>- Id proyecto</p><p>- Nombre corto</p><p>- Fase</p><p>- Plantilla</p><p>- Descripción</p><p>- Requerido. Aplica la **(RNA60)**</p><p>- No aplica</p><p>- Estatus</p><p>- Justificación</p><p>- Fecha última modificación</p><p>- Acciones. Aplica la **(RNA05)**</p><p></p><p>Opciones:</p><p>- Ver PDF ![](Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.010.png)</p><p>- Descargar documento ![](Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.011.png)</p><p>- Ver detalle ![](Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.012.png)</p><p>- Campos para “Filtrar” por columna</p>|
|<p>14. <a name="_ref172011135"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Ver detalle”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Exportar a Excel”**, el flujo continúa en el **([**FA05**](#fa05))**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Descargar ZIP”**, el flujo continúa en el **([**FA06**](#fa06))**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“SATCloud”**, el flujo continúa en el **([**FA07**](#fa07))**.</p><p></p><p>- En caso de que seleccione la opción **“Ver PDF”**, el flujo continúa en el **([**FA08**](#fa08))**.</p><p></p><p>- En caso de que seleccione la opción **“Descargar documento”**, el flujo continúa en el **([**FA09**](#fa09))**.</p><p></p><p>- En caso de que seleccione la opción para **“Filtrar”** los campos de la tabla, continúa en el **([**FA10**](#fa10))**.</p>|15. El proceso continúa en el en otra ventana del  **(17\_3083\_ECU\_GestionDocumental)** del proyecto, contrato y/o dictamen de acuerdo con la **(RNA241)**.|
||16. Fin del Caso de Uso.|

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc175158490"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 No se cuenta con proyectos asignados**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA01** inicia cuando el sistema identifica que el Empleado SAT no cuenta con proyectos asignados. |
||2. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Fin del Caso de Uso.|

|<p></p><p><a name="fa02"></a>**FA02 Selecciona la opción “Limpiar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA02** inicia cuando el Empleado SAT selecciona la opción **“Limpiar”**.|2. Borra la información ingresada en los criterios de búsqueda y los deja en el estado inicial de selección.|
||<p>3. Oculta lo siguiente: </p><p>&emsp;</p><p>&emsp;Opciones:</p><p>- Exportar a Excel ![ref5]</p><p>- Descargar ZIP ![ref6]</p><p>- SATCloud ![ref7]</p><p></p><p>Datos de los documentos:</p><p>- Documentos requeridos ![ref1]</p><p>- Documentos cargados ![ref2]</p><p>- Documentos que no aplican ![ref3] </p><p>- Documentos pendientes ![ref4]</p><p></p><p>Tabla “Proyectos”</p>|
||4. Continúa en el paso [**5**](#_ref172011115) del Flujo primario.|

|<p></p><p><a name="fa03"></a>**FA03 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA03** inicia cuando interviene un evento ajeno y no se puede almacenar las Pistas de Auditoría.|
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje de acuerdo con lo siguiente:</p><p></p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG002**](#msg002))**.</p><p></p><p>- En caso de que la pista de auditoría sea por el tipo de movimiento **PRNT**, se muestra el **([**MSG003**](#msg003))**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría.    |

|<p></p><p><a name="fa04"></a>**FA04 No existen resultados que coincidan en la búsqueda**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA04** inicia cuando el sistema identifica que no existen coincidencias con los criterios de búsqueda.|
||2. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|<p>4. Cierra el mensaje y oculta lo siguiente: </p><p>&emsp;</p><p>&emsp;Opciones:</p><p>- Exportar a Excel ![ref5]</p><p>- Descargar ZIP ![ref6]</p><p>- SATCloud ![ref7]</p><p></p><p>Datos de los documentos:</p><p>- Documentos requeridos ![ref1]</p><p>- Documentos cargados ![ref2]</p><p>- Documentos que no aplican ![ref3] </p><p>- Documentos pendientes ![ref4]</p><p></p><p>Tabla “Proyectos”</p>|
||5. Fin del Caso de Uso.|

|<p></p><p><a name="fa05"></a>**FA05 Selecciona la opción “Exportar a Excel”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= ReporteDeControlDocumental</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de proyecto</p><p>- Nombre corto</p><p>- Fase</p><p>- Descripción</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||3. Genera un archivo de Excel con la extensión (.xlsx) con la información correspondiente de acuerdo con el resultado de búsqueda.|
||4. Descarga el archivo de Excel con extensión (.xlsx).|
||5. Fin del Caso de Uso.|

|<p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Descargar ZIP”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT selecciona la opción **“Descargar ZIP”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= ReporteDeControlDocumental</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>&emsp;Nombre de los documentos descargados en una cadena separado por | (pipes)Ejemplo: (01\_FTO\_JC\_MDR4.PDF|SCP\_MDR4.pdf</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||<p>3. Muestra la pantalla “Procesando información” con el siguiente **([**MSG006**](#msg006))**.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_ConsultarReporteDeControlDocumental)** Estilos 04.</p>|
||4. Consulta en la BD los archivos que se tienen relacionados con el proyecto y sus contratos y/o convenios modificatorios, dictámenes y facturas relacionados de acuerdo con el resultado de búsqueda.|
||5. Genera y descarga un archivo en extensión (.zip) que contiene los archivos adjuntos.|
||6. Continúa en el paso [**14**](#_ref172011135) del Flujo primario.|

|<p></p><p><a name="fa07"></a>**FA07 Selecciona la opción “SATCloud”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA07** inicia cuando el Empleado SAT selecciona la opción **“SATCloud”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= ReporteDeControlDocumental</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- ID proyecto </p><p>- Nombre de los documentos descargados en una cadena por separado por | (pipes)</p><p>&emsp;Ejemplo: (01\_FTO\_JC\_MDR4.pdf|SCP\_MDR4.pdf)</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||<p>3. Muestra en una ventana emergente la pantalla “Datos de la descarga” con lo siguiente: </p><p>&emsp;</p><p>&emsp;Datos:</p><p>- url (enlace)</p><p>- contraseña</p><p></p><p>Opciones</p><p>- Copiar contraseña ![](Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.013.png)</p><p>- Cerrar</p><p>- Cerrar ventana ![ref8]</p><p></p><p>- En caso de que no se muestre el enlace y la contraseña, continúa en el **([**FA11**](#fa11))**.</p><p>&emsp;</p><p>Ver **(17\_3083\_EIU\_ConsultarReprteDeControlDocumental)** Estilos 03.</p>|
|<p>4. Realiza lo siguiente:</p><p></p><p>- En caso de que copie la contraseña o seleccione el enlace, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cerrar”**, continúa en el paso [**14**](#_ref172011135) del Flujo primario.</p>|5. <a name="_ref166672060"></a>Realiza la conexión con el SATCloud para mostrar el archivo a descargar de acuerdo con el resultado de búsqueda.|
|6. Realiza lo correspondiente para descargar los documentos adjuntos.|7. Descarga el archivo con extensión (.ZIP).|
||8. Continúa en el paso [**14**](#_ref172011135) del Flujo primario.|

|<p></p><p><a name="fa08"></a>**FA08 Selecciona la opción “Ver PDF”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA08** inicia cuando el Empleado SAT selecciona la opción **“Ver PDF”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= ReporteDeControlDocumental **Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consulta)</p><p>**Movimiento**= </p><p>-Nombre corto (Proyecto)</p><p>-Documento seleccionado</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||3. Procesa el archivo PDF para su visualización.|
||<p>4. Muestra en una pantalla emergente lo siguiente:</p><p>&emsp;</p><p>- Panel de visualización (Muestra la información del archivo).</p><p>&emsp;</p><p>Opciones:</p><p>- Cerrar</p><p>- Cerrar ventana![ref8]</p><p></p><p>Ver **(17\_3083\_EIU\_ConsultarReprteDeControlDocumental)** Estilos 02.</p>|
|5. Selecciona la opción **“Cerrar”** o la opción **“Cerrar ventana”**.|6. Cierra la ventana emergente.|
||7. Continúa en el paso [**14**](#_ref172011135) del Flujo primario.|

|<p></p><p><a name="fa09"></a>**FA09 Selecciona la opción “Descargar documento”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA09** inicia cuando el Empleado SAT selecciona la opción **“Descargar documento”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= ReporteDeControlDocumental **Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-Nombre corto (Proyecto)</p><p>-Documento seleccionado</p><p>-Fase </p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA03**](#fa03))**.</p>|
||3. Consulta y obtiene de la BD el documento almacenado.|
||4. Descarga el archivo.|
||5. Continúa en el paso [**14**](#_ref172011135) del Flujo primario.|

|<p></p><p><a name="fa10"></a>**FA10 Selecciona la opción para “Filtrar” los campos de la tabla**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA10** inicia cuando el Empleado SAT selecciona la opción para **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||5. Continúa en el paso [**14**](#_ref172011135)** del Flujo primario.|

|<p></p><p><a name="fa11"></a>**FA11 Error al generar el enlace y contraseña** </p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA11** inicia cuando no se muestra el enlace y la contraseña.|
||2. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso [**14**](#_ref172011135) del Flujo primario.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc175158491"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_ConsultarReporteDeControlDocumental</p><p>- 17\_3083\_ECU\_AccesoSistema</p><p>- 17\_3083\_ECU\_GestionDocumental</p><p></p>|
|<h3><a name="_toc175158492"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|No se cuenta con proyectos asignados.|
|<a name="msg002"></a>**MSG002**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg003"></a>**MSG003**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg004"></a>**MSG004**|No se encontraron resultados en la búsqueda.|
|<a name="msg005"></a>**MSG005**|<p>Error al generar el enlace y contraseña. </p><p>Intente nuevamente.</p>|
|<a name="msg006"></a>**MSG006**|Procesando…|

|<p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc175158493"></a>**10. Requerimientos No Funcionales** </h3>|
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
|**RNF008**|Fiabilidad |El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema. |
|**RNF009**|Seguridad|Mantener la información en pantalla en caso de un error al guardar las pistas de auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema. |
|**RNF010**|Integridad |Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena. |
|**RNF011**|Usabilidad |<p>El Empleado SAT podrá navegar a través de las páginas resultantes del documento PDF. </p><p>- Ir a la siguiente página (debe mostrar la página consecutiva del documento PDF).  </p><p>- Ir a la página anterior (debe mostrar la página previa del documento PDF). </p>|

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc175158494"></a>**11. Diagrama de actividad** </h3>|
|<p></p><p>![](Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.015.jpeg)</p><p></p>|
|<h3><a name="_toc175158495"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no hay cambios significativos de estados ni transiciones.</p><p></p>|
|<h3><a name="_toc175158496"></a>**13. Aprobación del cliente** </h3>|
||

|**FIRMAS DE CONFORMIDAD** ||
| :-: | :- |
|**Firma 1**  |**Firma 2**  |
|**Nombre**: Diana Yazmín Pérez Sabido.|**Nombre**: Rodolfo López Meneses. |
|**Puesto**: Usuaria ACPPI.|**Puesto**: Usuario ACPPI.|
|**Fecha:** |**Fecha:** |
|  |  |
|**Firma 3**  |**Firma 4** |
|**Nombre**: Rubén Delgado Ramírez. |**Nombre**: María del Carmen Castillejos Cárdenas. |
|**Puesto**: Usuario ACPPI. |**Puesto**: APE ACPPI. |
|**Fecha:** |**Fecha:** |
|  |  |
|**Firma 5** |**Firma 6** |
|**Nombre**: Alejandro Alfredo Muñoz Núñez. |**Nombre**: Erick Villa Beltrán. |
|**Puesto**: RAPE ACPPI. |**Puesto**: Líder APE SDMA 6. |
|**Fecha**: |**Fecha**: |
|  |  |
|**Firma 7**|**Firma 8**|
|**Nombre**: Juan Carlos Ayuso Bautista. |**Nombre**: Eduardo Acosta Mora|
|**Puesto**: Líder Técnico SDMA 6. |**Puesto**: Analista SDMA 6.|
|**Fecha**: |**Fecha**: |
|||

||
| :- |

|||Página 1 de 9|
| :- | :-: | -: |

[ref1]: Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.003.png
[ref2]: Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.004.png
[ref3]: Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.005.png
[ref4]: Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.006.png
[ref5]: Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.007.png
[ref6]: Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.008.png
[ref7]: Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.009.png
[ref8]: Aspose.Words.8c3befc3-3d0a-4f3f-98fb-82826d7b47bd.014.png
