|![](Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_AsignarProveedoresParticipantes.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309<

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación.


**Tabla de Versiones y Modificaciones**

|Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|Creación del documento.|Osiris Vianey Segovia Pasarán|09/02/2024|
|*1.1*|Revisión del documento.|Luis Angel Olguin Castillo|09/03/2024|
|*1.2*|Versión aprobada para firma|María del Carmen Castillejos Cárdenas Rubén Delgado Ramírez|25/05/2024|



**Tabla de Contenido**

[17_3083_ECU_AsignarProveedoresParticipantes	2](#_toc167723110)

[1. Descripción	2](#_toc167723111)

[2. Diagrama del Caso de Uso	2](#_toc167723112)

[3. Actores	2](#_toc167723113)

[4. Precondiciones	2](#_toc167723114)

[5. Post condiciones	3](#_toc167723115)

[6. Flujo primario	3](#_toc167723116)

[7. Flujos alternos	7](#_toc167723117)

[8. Referencias cruzadas	13](#_toc167723118)

[9. Mensajes	13](#_toc167723119)

[10. Requerimientos No Funcionales	14](#_toc167723120)

[11. Diagrama de actividad	16](#_toc167723121)

[12. Diagrama de estados	16](#_toc167723122)

[13. Aprobación del cliente	17](#_toc167723123)






### **<a name="_toc167723110"></a>**17\_3083\_ECU\_AsignarProveedoresParticipantes

|<h3><a name="_toc167723111"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT agregar a los proveedores participantes en los diferentes proyectos administrados por el sistema.</p><p></p>|
|<h3><a name="_toc167723112"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.002.png)</p><p> </p><p></p>|
|<h3><a name="_toc167723113"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

||
| :- |
|<h3><a name="_toc167723114"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autentificado en el sistema con e-firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa.</p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles y permisos para ingresar a los módulos “Proyectos”, “Modificar proyecto” y a la sección “Participación de proveedores”.</p><p>- El Empleado SAT ha ingresado a la opción del “Participación de Proveedores”, desde el caso de uso **(17\_3083\_ECU\_ModificarProyecto)**.</p><p>- El Empleado ha registrado los datos generales del proyecto. </p><p>- Se han registrado proveedores y se encuentran en “Activo” dentro del módulo de proveedores en el proceso **(17\_3083\_ECU\_AltaDeProveedor)**.</p><p>&emsp;</p>|
|<h3><a name="_toc167723115"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT agregó proveedores participantes.</p><p>- El Empleado SAT modificó la información registrada para el proveedor.</p><p>- El Empleado SAT eliminó un registro de proveedor. </p><p></p>|
|<h3><a name="_toc167723116"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT realiza una modificación del proyecto y selecciona la sección **“Participación de proveedores”** en el proceso de **(17\_3083\_ECU\_ModificarProyecto)**.|<p>2. Obtiene de la base de datos (BD)</p><p>&emsp;` `la información de los siguientes catálogos. Aplica las reglas de negocio **(RNA01)**, **(RNA85)**.   </p><p>- Proveedor</p><p>- Investigación mercado</p><p>- Respuesta IM</p><p>- Junta de aclaración</p><p>- Licitación pública</p><p>**Nota**:  se crea un catálogo de “Respuesta proveedor” para el contenido de los últimos cuatro catálogos. </p>|
||<p>3. <a name="_ref165466530"></a>Muestra la sección “Participación de proveedores” con información de los proveedores registrados anteriormente. Aplica la **(RNA31)**:</p><p>&emsp;</p><p>&emsp;Tabla proveedores Aplica la **(RNA42)** y **(RNA244)**.</p><p>&emsp;</p><p>- Id</p><p>- Proveedor</p><p>- Investigación mercado</p><p>- Fecha IM</p><p>- Respuesta IM</p><p>- Fecha respuesta IM</p><p>- Junta de aclaraciones</p><p>- Fecha JA</p><p>- Licitación pública</p><p>- Fecha proposición LP</p><p>- Comentarios</p><p>- ![ref1]Nuevo comentario </p><p>- Acciones. Aplica la **(RNA246)**</p><p>- Editar ![](Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.004.png)</p><p>- Eliminar ![](Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.005.png)</p><p>&emsp;</p><p>Opciones:</p><p>- ![ref2]![ref3]Nuevo       </p><p>- Exportar a Excel</p><p>- Campos para “Filtrar” por columna </p><p>Ver **(17\_3083\_EIU\_AsignarProveedoresParticipantes)** Estilos 01.</p>|
|<p>4. <a name="_ref167798473"></a><a name="_ref158590339"></a><a name="_ref165301640"></a>Selecciona la opción de “**Nuevo**” y continúa en el flujo. </p><p>&emsp;</p><p>- En caso de que ingrese un parámetro de búsqueda **“Filtrar”**, continúa en el **([**FA01**](#fa01))**.</p><p>- En caso de que seleccione la opción **“Exportar a Excel”**, continúa en el **([**FA05**](#fa05))**.</p><p>- En caso de que seleccione la opción **“Editar”** de algún registro de la tabla, continúa en el **([**FA06**](#fa06))**.</p><p>- En caso de que seleccione la opción **“Eliminar”** de algún registro de la tabla, continúa en el **([**FA07**](#fa07))**.</p><p>- En caso de seleccionar **“Guardar”** continua en el paso, continua en el paso 10 de este flujo. </p><p>- En caso de seleccionar la opción **“Cancelar”**, continúa en el **([**FA04**](#fa04))**.</p><p>&emsp;</p>|<p>5. <a name="_ref165303420"></a>Muestra la pantalla, la sección “Participación de proveedores” habilitando los campos para agregar a los proveedores participantes en el proyecto seleccionado.</p><p>&emsp;</p><p>&emsp;Tabla proveedores.</p><p>- Id</p><p>- Proveedor. </p><p>- Investigación mercado</p><p>- Fecha IM</p><p>- Respuesta IM</p><p>- Fecha respuesta IM</p><p>- Junta de aclaraciones</p><p>- Fecha JA</p><p>- Licitación pública</p><p>- Fecha proposición LP</p><p>- Comentarios</p><p>- ![ref1]Nuevo comentario </p><p>- Acciones</p><p>- Descartar ![ref4]</p><p></p><p>Opciones:</p><p></p><p>- Guardar. Aplicar la **(RNA246)**</p><p>- Cancelar Aplicar la **(RNA246)**</p><p>- ![ref1]Nuevo    </p><p>- ![ref5]Exportar a Excel</p><p>- Filtro </p><p>- Campos para “Filtrar” por columna </p><p>Ver **(17\_3083\_EIU\_AsignarProveedoresParticipantes)** Estilos 02 y Estilos 04</p>|
|<p>6. <a name="_ref165467437"></a>Selecciona en el catálogo de la columna **“Proveedor”** un proveedor. </p><p>&emsp;</p><p></p><p></p>|<p>7. Consulta la información en la BD del catálogo “Proveedor” de acuerdo con la **(RNA42)**.</p><p>&emsp;</p><p>- Si, dentro del catálogo de proveedores no se encuentran proveedores activos, continúa en el **([**FA03**](#fa03))**.</p>|
|<p>8. <a name="_ref165467128"></a>Agrega los datos de la tabla de proveedores participantes:</p><p>- Investigación mercado</p><p>- Fecha IM</p><p>- Respuesta IM</p><p>- Fecha respuesta IM</p><p>- Junta de aclaraciones</p><p>- Fecha JA</p><p>- Licitación pública</p><p>- Fecha proposición LP</p><p></p><p>- En caso de agregar un comentario continúa en el **([**FA08**](#fa08))**.</p><p>- En caso de seleccionar la opción **“Descartar”**, continúa en el **([**FA04**](#fa04))**.</p>||
|<p>9. Si no quiere realizar alguna otra acción continua en el flujo </p><p>&emsp;</p><p>- En caso de requerir alguna otra acción en la tabla regresa al paso **4** de este flujo.</p><p>&emsp;</p>||
|<p>10. <a name="_ref158410164"></a><a name="_ref165302192"></a><a name="_ref165466078"></a>Selecciona la opción **“Guardar”**, continúa en el flujo.</p><p>- En caso de seleccionar la opción **“Cancelar”**, continúa en el **([**FA04**](#fa04))**.</p>|<p>11. Valida la acción realizada.</p><p>&emsp;</p><p>- En caso de agregar o editar valida que se hayan capturado los datos obligatorios, conforme a la **(RNA42)**.</p><p></p><p>- En caso de identificar que no se ingresaron los datos obligatorios, continúa en el **([**FA09**](#fa09))**.</p><p>- En caso de que solo sean movimientos de eliminación, continúa en el flujo.</p>|
||<p>12. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Participación de Proveedores </p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM: SS.</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Proveedor </p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>13. Identifica el tipo de movimiento realizado y almacena en la BD la información que corresponda a los proveedores participantes:</p><p>&emsp;</p><p>&emsp;Tabla proveedores:</p><p>&emsp;</p><p>- Id</p><p>- Proveedor</p><p>- Investigación mercado</p><p>- Fecha IM</p><p>- Respuesta IM</p><p>- Fecha respuesta IM</p><p>- Junta de aclaraciones</p><p>- Fecha JA</p><p>- Licitación pública</p><p>- Fecha proposición LP</p><p>- Comentario (…) </p>|
||14. Actualiza la tabla, Aplica la **(RNA250)**.|
||15. Muestra el mensaje **([**MSG001**](#msg001))** con la opción** “Aceptar”.|
|16. <a name="_ref158596256"></a>Selecciona la opción **“Aceptar”**.|17. Cierra el mensaje.|
||18. <a name="_ref165543978"></a>Muestra la pantalla con los campos actualizados de acuerdo con los movimientos realizados. |
||19. Fin del Caso de Uso.|
|||

||
| :- |
|<h3><a name="_toc167723117"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 Selecciona la opción “Filtrar”** </p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA01** inicia cuando el Empleado SAT requiere **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra todas las coincidencias que obtiene en tiempo real de dicha columna.|
||5. Regresa al paso [**4**](#_ref167798473) del Flujo Primario. |

|<p></p><p><a name="fa02"></a>**FA02 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA02** inicia cuando interviene un evento ajeno y no se puedan almacenar las Pistas de Auditoría. |
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje informativo de acuerdo con lo siguiente:</p><p>&emsp;</p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT**, **INSR** o **DLT** se muestra el **([**MSG002**](#msg002))**.</p><p>&emsp;</p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG003**](#msg003))**.</p><p>&emsp;</p><p>- En caso de que la pista de auditoría es por el tipo de movimiento **PRNT**, se muestra el **([**MSG004**](#msg004))**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje. |
||6. Regresa al paso previo que detona la acción de la pista de auditoría. |

|<p></p><p><a name="fa03"></a>**FA03 No se cuenta con proveedores activos**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA03** inicia cuando el sistema identifica que el sistema no cuenta con proveedores activos en el módulo de proveedores.|
||2. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje, colapsa la sección de proveedores participantes.|
||5. Fin del Caso de Uso.|

|<p></p><p><a name="fa04"></a>**FA04 Selecciona la opción “Cancelar”, “Cerrar”, “Descartar”**  </p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA04** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”**, **“Cerrar”** o **“Descartar”**|2. Muestra el **([**MSG006**](#msg006))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona la opción **“No”**, flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar **“Sí”**,**  </p><p>&emsp;continúa en el paso [5](#_ref165465730) de este flujo</p>|4. <a name="_ref165465764"></a>Cierra el mensaje, y continúa en el paso **[**6**](#_ref165543685)** de este flujo.|
||<p>5. <a name="_ref165465730"></a> Realiza lo siguiente. </p><p>- Si fue invocado en la opción “Cancelar” se inicializan los campos donde se selecciona la opción y no almacena ninguna información.</p><p>- Si fue invocado en el “Cerrar” cierra el modal sin almacenar ninguna información,</p><p>- Si fue invocado en la opción “Descartar”:</p><p>- Se inicializa el registro de la tabla de la sección donde fue invocado, y cambia a solo lectura si era un registro almacenado regresando los íconos a su estado original.</p><p>- Si era un registro nuevo elimina la fila.</p><p></p>|
||<p>6. <a name="_ref165543685"></a>Realiza lo siguiente:</p><p></p><p>- Si se invoca en el paso 10 del Flujo primario, regresa al paso [**3**](#_ref165466530)** de dicho flujo.</p><p>- Si se invoca en el paso 8 del Flujo primario, regresa al paso [**3**](#_ref165466530)** de dicho flujo.</p><p>- Si se invoca en el paso 4 del Flujo primario, regresa al paso [**3**](#_ref165466530)** de dicho flujo.</p><p>- Si se invoca en el paso 5 del **([**FA06**](#fa06))** regresa al paso [**3**](#_ref165466530)** del Flujo primario.</p><p>- Si se invoca en el paso 3 del **([**FA08**](#fa08))** regresa al paso [**2**](#_ref165466809) del **([**FA08**](#fa08))**.</p><p></p>|

|<p></p><p><a name="fa05"></a>**FA05 Selecciona la opción “Exportar a Excel”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref165302256"></a>El **FA05** inicia cuando el Empleado SAT selecciona el icono **“Exportar a Excel”** de la tabla proveedores. |<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Participación de Proveedores</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS.</p><p>**RFC Usuario**= RFC largo del usuario que ingresó al sistema.</p><p>**Tipo de movimiento**= </p><p>- **PRNT** (Imprimir).</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p></p><p>- Proveedores </p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA02**](#fa02))**. </p>|
||<p>3. Obtiene la siguiente información.</p><p>&emsp;Tabla proveedores:</p><p>&emsp;</p><p>- Id</p><p>- Proveedor</p><p>- Investigación mercado</p><p>- Fecha IM</p><p>- Respuesta IM</p><p>- Fecha respuesta IM</p><p>- Junta de aclaraciones</p><p>- Fecha JA</p><p>- Licitación pública</p><p>- Fecha proposición LP</p><p>- Comentarios </p>|
||4. Genera un archivo en formato Excel (.xlsx) con la información obtenida.|
||5. Dependiendo el explorador realiza la descarga del archivo.|
||6. Fin de Caso de Uso.|

|<p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Editar”** </p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref166609736"></a>El **FA06** inicia cuando el Empleado SAT selecciona “**Editar**” un registro de un proveedor participante de un proyecto seleccionado. |<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Participación de Proveedores</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS.</p><p>**RFC Usuario**= RFC largo del usuario que ingresó al sistema.</p><p>**Tipo de movimiento**= </p><p>- **CNST** (Consultar).</p><p>**Movimiento**=</p><p>- Proveedores </p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>3. ` `Consulta en la BD la información relacionada con el registro seleccionado y obtiene la siguiente información en formato edición.</p><p>&emsp;</p><p>- Proveedor</p><p>- Investigación mercado</p><p>- Fecha IM</p><p>- Respuesta IM</p><p>- Fecha respuesta IM</p><p>- Junta de aclaración</p><p>- Fecha JA</p><p>- Licitación pública</p><p>- Fecha proposición LP</p><p>- Comentarios</p>|
||<p>4. <a name="_ref166608141"></a>Muestra la pantalla, habilitando los campos para editar la información de proveedores participantes, en un proyecto, de acuerdo con la **(RNA85)** y **(RNA42)**.</p><p>&emsp;</p><p>- Proveedor</p><p>- Investigación mercado</p><p>- Fecha IM</p><p>- Respuesta IM</p><p>- Fecha respuesta IM</p><p>- Junta de aclaración</p><p>- Fecha JA</p><p>- Licitación pública</p><p>- Fecha proposición LP</p><p>- Comentarios</p><p>- ![ref1]Nuevo comentario </p><p>- Acciones</p><p>- Descartar ![ref4]</p><p></p><p>Ver **(17\_3083\_EIU\_AsignarProveedoresParticipantes)** Estilos 02. Y Estilos 04.</p>|
|<p>5. <a name="_ref165467286"></a>Modifica cualquiera de los siguientes campos, continua el flujo. </p><p>- Proveedor</p><p>- Investigación mercado</p><p>- Fecha IM</p><p>- Respuesta IM</p><p>- Fecha respuesta IM</p><p>- Junta de aclaración</p><p>- Fecha JA</p><p>- Licitación pública</p><p>- Fecha proposición LP</p><p></p><p>- En caso de requerir modificar el comentario, continúa en el **([**FA08**](#fa08))**. De no ser necesario, continúa en el flujo.</p><p>- En caso de seleccionar la opción **“Descartar”**, continúa en el **([**FA04**](#fa04))**.</p>||
||6. Regresa al paso [**4**](#_ref167798473) del Flujo Primario.|

|<p></p><p><a name="fa07"></a>**FA07 Selecciona la opción “Eliminar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref165302557"></a>El **FA07** inicia cuando el Empleado SAT selecciona “**Eliminar**” un registro de un proveedor. |2. Muestra el **([**MSG008**](#msg008))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona la opción **“No”** continúa en el flujo. </p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Sí”**, continúa en el paso **5** de este flujo.</p>|4. Cierra el mensaje, no realiza ningún movimiento, muestra la pantalla con los proveedores. |
||5. Elimina el registro de la tabla de proveedores. Aplica la **(RNA250)**.|
||6. Regresa al paso [**4**](#_ref167798473) del Flujo Primario.|

|<p></p><p><a name="fa08"></a>**FA08 Selecciona la opción “Nuevo comentario”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA08** inicia cuando el Empleado SAT selecciona **“Nuevo comentario”** de la columna comentarios. |<p>2. <a name="_ref165466809"></a>Muestra una ventana emergente “Agregar comentarios”:</p><p>&emsp;</p><p>- Comentarios</p><p>Opciones</p><p></p><p>- Guardar</p><p>- Cancelar</p><p>- Cerrar  ![](Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.010.png)</p><p></p><p>Ver **(17\_3083\_EIU\_AsignarProveedoresParticipantes)** Estilos 03.</p>|
|<p>3. Agrega o modifica el comentario al seleccionar **“Nuevo comentario”** en la columna de **“Comentarios”** y continúa en el flujo. </p><p>- En caso de seleccionar la opción **“Cancelar”** o **“Cerrar”**, continúa en el **([**FA04**](#fa04))**.</p><p>&emsp;</p>|4. Muestra el mensaje **([**MSG001**](#msg001))** con la opción “Aceptar”|
|5. Selecciona la opción **“Aceptar”**.|6. Cierra el mensaje.|
||<p>7. Realiza lo siguiente:</p><p></p><p>- Si se invoca en el paso 8 del Flujo primario, regresa al paso [**8**](#_ref165467128)** de dicho flujo.</p><p>- Si se invoca en el paso 5 del **([**FA06**](#fa06))** regresa al paso [**5**](#_ref165467286)** del Flujo primario.</p><p></p>|

|<p></p><p><a name="fa09"></a>**FA09 No se ingresaron los datos obligatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA09** inicia cuando el sistema identifica que no se ingresaron los datos obligatorios.|
||2. Muestra en rojo los campos pendientes de captura. |
||3. Muestra el **([**MSG007**](#msg007))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje. |
||<p>6. Regresa al paso </p><p>- Si se invoca en el paso 11 del Flujo primario, regresa al paso **[**6**](#_ref165467437)** de dicho flujo.</p><p>&emsp;</p>|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc167723118"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_AsignarProveedoresParticipantes</p><p>- 17\_3083\_ECU\_AltaDeProveedor</p><p>- 17\_3083\_ECU\_ModificarProyecto</p><p>&emsp;</p><p></p>|
|<h3><a name="_toc167723119"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|La información ha sido actualizada.|
|<a name="msg002"></a>**MSG002**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg003"></a>**MSG003**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg004"></a>**MSG004**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg005"></a>**MSG005**|No se tienen proveedores para ser agregados, favor de registrar en el módulo correspondiente. |
|<a name="msg006"></a>**MSG006**|Se perderá la información ingresada. ¿Está seguro de continuar?|
|<a name="msg007"></a>**MSG007**|Favor de ingresar los datos obligatorios.|
|<a name="msg008"></a>**MSG008**|¿Está seguro de eliminar el registro del proveedor participante en el proyecto? |

|<p></p><p></p>|
| - |
|<h3><a name="_toc167723120"></a>**10. Requerimientos No Funcionales** </h3>|
||

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001**|Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|
|**RNF002**|Concurrencia|<p>El número de Empleados SAT que puede tener el sistema son 150.</p><p></p><p>` `El número de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT.</p>|
|**RNF003**|Seguridad|El acceso solo podrá ser otorgado a todo Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema.|
|**RNF004**|Usabilidad|<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación: </p><p></p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual, como guía para el Empleado SAT. </p><p></p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado.</p><p></p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales.</p>|
|**RNF005**|Eficiencia|Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información sólo cuando sean requeridas por el Empleado SAT.|
|**RNF006**|Usabilidad|<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100: </p><p></p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta). </p><p>- ` `Ir a la última página (debe mostrar la última página con el resultado de la consulta). </p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT). </p><p>- ` `Ir a la página anterior (debe mostrar la página anterior considerando la actual con el resultado de la consulta). </p><p>&emsp;</p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente.</p>|
|**RNF007**|Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.|
|**RNF008**|Fiabilidad|El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema.|
|**RNF009**|Seguridad|Se debe mantener la información en pantalla en caso de un error al guardar las Pistas de Auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema.|
|**RNF010**|Integridad |Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena. |

||
| :- |

|<h3><a name="_toc167723121"></a>**11. Diagrama de actividad** </h3>|
| :- |
|<p></p><p>![Imagen que contiene Diagrama

Descripción generada automáticamente](Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.011.jpeg)</p><p></p>|
|<h3><a name="_toc167723122"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no se requiere para este proceso.</p><p></p>|
|<h3><a name="_toc167723123"></a>**13. Aprobación del cliente** </h3>|
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
|**Puesto**: Enlace ACPPI.|**Puesto**: Líder APE.|
|**Fecha**:|**Fecha**:|
|||
|**Firma 9**|**Firma 10**|
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:** Maria del** Carmen Gutiérrez Sánchez.|
|**Puesto:** Líder Técnico.|**Puesto:** Analista de Sistemas DS SDMA 6.  |
|**Fecha**:|**Fecha**:|
|||

|<p></p><p></p>|
| :- |

|||Página 1 de 9|
| :- | :-: | -: |

[ref1]: Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.003.png
[ref2]: Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.006.png
[ref3]: Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.007.png
[ref4]: Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.008.png
[ref5]: Aspose.Words.e55a4b35-9944-41e8-acba-1b2043c99432.009.png
