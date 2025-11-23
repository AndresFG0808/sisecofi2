|![](Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_ModificarCatalogos.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |

**ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a><a name="_hlk157173234"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación.


**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Eduardo Acosta Mora|*18/01/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*10/03/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*06/06/2024*|



**Tabla de Contenido**

[17_3083_ECU_ModificarCatalogos	2](#_toc168591325)

[1. Descripción	2](#_toc168591326)

[2. Diagrama del Caso de Uso	2](#_toc168591327)

[3. Actores	2](#_toc168591328)

[4. Precondiciones	2](#_toc168591329)

[5. Post condiciones	3](#_toc168591330)

[6. Flujo Primario	3](#_toc168591331)

[7. Flujos alternos	5](#_toc168591332)

[8. Referencias cruzadas	16](#_toc168591333)

[9. Mensajes	16](#_toc168591334)

[10. Requerimientos No Funcionales	17](#_toc168591335)

[11. Diagrama de actividad	19](#_toc168591336)

[12. Diagrama de estados	19](#_toc168591337)

[13. Aprobación del cliente	20](#_toc168591338)


### **<a name="_toc168591325"></a>**17\_3083\_ECU\_ModificarCatalogos

|<h3><a name="_toc168591326"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT modificar un registro para un catálogo general o complementario.</p><p></p>|
|<h3><a name="_toc168591327"></a>**2. Diagrama del Caso de Uso**</h3>|
|![](Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.002.png)|
|<h3><a name="_toc168591328"></a>**3. Actores** </h3>|
||
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc168591329"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con una e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa al mismo.</p><p>- Se le ha asignado el rol requerido al Empleado SAT para ingresar al módulo “Sistema” y al submódulo “Catálogos”.</p><p>- El sistema ha validado que el Empleado SAT cuenta con el rol para ingresar al módulo "Sistema" y al submódulo "Catálogos".</p><p>- El Empleado SAT ha ingresado al módulo "Sistema" y al submódulo "Catálogos".</p><p>- Se ha creado el esquema de almacenamiento (Metadatos) para los catálogos que serán gestionados por el sistema.</p><p>- Se han agregado nuevos registros de un catálogo general o complementario y se ha seleccionado un registro para edición.</p><p>&emsp;</p><p></p>|
|<h3><a name="_toc168591330"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT modificó un registro de manera correcta.</p><p>&emsp;</p>|
|<h3><a name="_toc168591331"></a>**6. Flujo Primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El Caso de Uso inicia, cuando el Empleado SAT selecciona la opción **“Editar”** sobre un registro de un catálogo general desde **(17\_3083\_ECU\_AltaDeCatalogos)** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Editar”** sobre un registro de una administración general, central o administración desde **(17\_3083\_ECU\_AltaDeCatalogos)**,** continúa en el **([**FA01**](#fa01))**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Editar”** sobre un registro de una alineación desde **(17\_3083\_ECU\_AltaDeCatalogos)**,** continúa en el **([**FA04**](#fa04))**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Editar”** sobre un registro para la sección “Mapas - Objetivos” desde **(17\_3083\_ECU\_AltaDeCatalogos)**,** continúa en el **([**FA05**](#fa05))**.</p>|<p>2. Obtiene de la base de datos (BD) la siguiente información para mostrarla en los campos:</p><p>&emsp;</p><p>&emsp;- Nombre</p><p>&emsp;- Descripción</p><p>&emsp;- Estatus</p>|
||<p>3. <a name="_ref165591169"></a>Muestra en una ventana emergente la pantalla “Modificar registro” lo siguiente:</p><p>&emsp;</p><p>&emsp;Modificar registro:</p><p>&emsp;- Nombre\*</p><p>&emsp;- Descripción\*</p><p>&emsp;- Estatus\*</p><p></p><p>Opciones:</p><p>- Cerrar ventana ![ref1]</p><p>&emsp;- Guardar</p><p>&emsp;- Cancelar</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarCatalogos)** Estilos 02.</p>|
|4. <a name="_ref165590934"></a>Modifica la información que requiera.||
|<p>5. <a name="_ref165590817"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Guardar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”** o **“Cerrar ventana”**, continúa en el **([**FA06**](#fa06))**.</p>|<p>6. Valida que se haya ingresado datos en los campos obligatorios de acuerdo con la **(RNA68)** y **(RNA03)**.</p><p>&emsp;</p><p>- En caso de que no se haya ingresado información en los campos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>7. Valida la estructura de los datos ingresados en los campos de acuerdo con la **(RNA69)**.</p><p>&emsp;</p><p>- En caso de que la estructura de los datos ingresados sea incorrecta, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>8. Valida que no exista un registro con la misma información de acuerdo con la **(RNA04)**.</p><p>&emsp;</p><p>- En caso de que exista un registro con la misma información, continúa en el **([**FA09**](#fa09))**.</p>|
||<p>9. <a name="_ref158374904"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Catálogos - Generales</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Nombre del catálogo</p><p>- Id</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA10**](#fa10))**.</p>|
||<p>10. Almacena la siguiente información en la BD:</p><p>&emsp;</p><p>&emsp;- Id= Identificador generado automáticamente por el sistema.</p><p>&emsp;- Nombre</p><p>&emsp;- Descripción</p><p>&emsp;- Última modificación: Fecha del sistema (Día actual).</p><p>&emsp;- Estatus</p>|
||<p>11. Actualiza la tabla de la sección “Catálogos generales” con la información modificada del registro.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarCatalogos)** Estilos 01.</p>|
||12. Muestra el mensaje **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|13. Selecciona la opción **“Aceptar”**.|14. Cierra el mensaje.|
||15. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc168591332"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 Selecciona la opción “Editar” sobre un registro de una administración**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA01** inicia cuando el Empleado SAT selecciona la opción **“Editar”** sobre un registro de una administración.|<p>2. <a name="_ref165591187"></a>Muestra en una ventana emergente la pantalla “Modificar registro, estructura organizacional” lo siguiente:</p><p></p><p>Modificar registro:</p><p>- Catálogo: {Nombre del catálogo o sección donde fue invocado}.</p><p>&emsp;- Administración\*</p><p>&emsp;- Acrónimo\*</p><p>&emsp;- Puesto\*</p><p>&emsp;- Estatus\*</p><p></p><p>Opciones:</p><p>- Nuevo ![](Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.004.png)</p><p>&emsp;- Exportar a Excel ![](Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.005.png)</p><p></p><p>Tabla (Administradores). Aplica la **(RNA244)**:</p><p>- Id</p><p>&emsp;- Nombre completo</p><p>&emsp;- Fecha inicio de vigencia</p><p>&emsp;- Fecha fin vigencia</p><p>&emsp;- Última modificación</p><p>&emsp;- Estatus ![](Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.006.png)</p><p>&emsp;- Acciones</p><p>- Editar ![](Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.007.png)</p><p></p><p>Opciones:</p><p>- Cerrar ventana ![ref1]</p><p>&emsp;- Guardar</p><p>&emsp;- Cancelar</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarCatalogos)** Estilos 04.</p>|
|<p>3. <a name="_ref165591048"></a>Modifica la información que requiera de los siguientes campos:</p><p>&emsp;</p><p>- Administración\*</p><p>- Acrónimo\*</p><p>- Puesto del administrador\*</p><p>- Estatus \*</p>||
|<p>4. Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Nuevo”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Guardar”**, continúa en el paso **7** de este flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Cancelar”** o **“Cerrar ventana”**, continúa en el **([**FA06**](#fa06))**.</p><p></p><p>- En caso de que seleccione la opción **“Exportar a Excel”**, continúa en el **(FA03)**.</p><p></p><p>- En caso de que seleccione la opción **“Editar”**, continúa en el **(FA02)**. </p>|<p>5. Dentro de la tabla (Administradores) crea una nueva fila y habilita los siguientes campos y opciones para su registro:</p><p></p><p>- Nombre completo\*</p><p>- Fecha inicio de vigencia\*</p><p>- Fecha fin vigencia</p><p>- Estatus</p><p>- Acciones </p><p>- Descartar ![ref2]</p><p></p>|
|<p>6. Realiza lo siguiente:</p><p>&emsp;</p><p>- En caso de que ingrese la información para los campos de la tabla, continúa en el paso **4** de este flujo.</p><p></p><p>- En caso de que seleccione la opción **“Descartar”**, continúa en el **(FA06)**.</p>|<p>7. Valida que se haya ingresado datos obligatorios en los campos y la tabla (Administradores) de acuerdo con la **(RNA68)** y **(RNA03)**.</p><p>&emsp;</p><p>- En caso de que no se haya ingresado información en los campos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>8. Valida la estructura de los datos ingresados en los campos y la tabla (Administradores) de acuerdo con la **(RNA69)**.</p><p>&emsp;</p><p>- En caso de que la estructura de los datos ingresados sea incorrecta, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>9. Valida que no exista un registro con la misma información en los campos de acuerdo con la **(RNA04)**.</p><p></p><p>- En caso de que exista un registro con la misma información, continúa en el **([**FA09**](#fa09))**.</p>|
||10. Valida si el valor del campo “Estatus” de la ventana emergente es inactivo, muestra el mensaje **(MSG009)** con la opción “Sí” y “No”, el flujo continúa.|
|11. Selecciona una opción.|<p>12. Cierra el mensaje.</p><p>&emsp;</p><p>- Si seleccionó la opción “Sí”, el flujo continúa.</p><p></p><p>- Si seleccionó la opción “No”, el valor del campo estatus cambia a activo y continúa en el paso **14** de este flujo.</p>|
||13. Inactiva todos los registros dependientes de la tabla administradores y continúa en el paso **17** de este flujo.|
||<p>14. Valida si los valores del campo “Estatus” de la tabla “Administradores” contenga máximo un valor activo, continúa en el paso **17** de este flujo.</p><p>&emsp;</p><p>- En caso de haber más de un registro activo, muestra el **(MSG008)** con la opción “Aceptar”, el flujo continúa.</p>|
|15. Selecciona la opción **“Aceptar”**.|16. Cierra el mensaje y continúa en el paso **6** de este flujo.|
||<p>17. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Catálogos - Complementarios</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Nombre del catálogo</p><p>- Id</p><p>- Id administrador (En caso de aplicar)</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA10**](#fa10))**.</p>|
||<p>18. Almacena la siguiente información en la BD:</p><p>&emsp;</p><p>&emsp;- Id= Identificador generado automáticamente por el sistema.</p><p>&emsp;- Administración</p><p>&emsp;- Acrónimo</p><p>&emsp;- Puesto</p><p>&emsp;- Estatus</p><p>&emsp;- Última modificación: Fecha del sistema (Día actual).</p><p>&emsp;- En caso de aplicar: Tabla (Administradores)  </p><p>&emsp;- Id</p><p>&emsp;- Nombre completo</p><p>&emsp;- Fecha de inicio de vigencia</p><p>&emsp;- Fecha fin de vigencia</p><p>&emsp;- Última modificación: Fecha del sistema (Día actual).</p><p>&emsp;- Estatus</p>|
||<p>19. Actualiza la tabla de la sección donde fue invocado de los  “Catálogos complementarios” con la información ingresada.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarCatalogos)** Estilos 03.</p>|
||20. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|<p>21. Selecciona la opción **“Aceptar”**.</p><p></p>|22. Cierra el mensaje.|
||23. Fin del Caso de Uso.|

|<p></p><p><a name="fa03"></a>**FA02 Selecciona la opción “Editar” de la tabla (Administradores)**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA02** inicia cuando el Empleado SAT selecciona la opción **“Editar”** de la tabla administradores.|<p>2. Habilita en la fila seleccionada los siguientes campos para su edición:</p><p>&emsp;</p><p>- Nombre completo\*</p><p>- Fecha inicio de vigencia\*</p><p>- Fecha fin vigencia\*</p><p>- Estatus</p><p>- Acciones </p><p>- Descartar ![ref2]</p>|
||3. Continúa en el paso **4** del **(FA01)**.|

|<p></p><p>**FA03 Selecciona la opción “Exportar a Excel”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA03** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Catálogos – Complementarios</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Nombre del catálogo o sección que lo invoca.</p><p>- Acrónimo</p><p>- Ejemplo: Administración general | AGRE | Tabla (Administradores)</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA10**](#fa10))**.</p>|
||3. Consulta en la BD la información de la tabla de acuerdo con la **(RNA71)**.|
||4. Genera un archivo de Excel con la extensión (.xlsx) con la información correspondiente.|
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||6. Continúa en el paso **4** del **(FA01)**.|

|<p></p><p><a name="fa04"></a>**FA04 Selecciona la opción “Editar” sobre un registro de una alineación**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA04** inicia cuando el Empleado SAT selecciona la opción **“Editar”** sobre un registro de una alineación.|<p>2. <a name="_ref165591228"></a>Muestra en una ventana emergente la pantalla “Modificar registro” lo siguiente:</p><p></p><p>Modificar registro:</p><p>- Nombre\*</p><p>&emsp;- Descripción\*</p><p>&emsp;- Estatus\*</p><p></p><p>Opciones:</p><p>- Cerrar ventana ![ref1]</p><p>&emsp;- Guardar</p><p>&emsp;- Cancelar</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarCatalogos)** Estilos 02.</p>|
|3. <a name="_ref165591099"></a>Modifica la información que requiera.||
|<p>4. <a name="_ref165590883"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Guardar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”** o **“Cerrar ventana”**, continúa en el **([**FA06**](#fa06))**.</p>|<p>5. Valida que se haya ingresado datos en los campos obligatorios de acuerdo con la **(RNA68)** y **(RNA03)**.</p><p>&emsp;</p><p>- En caso de que no se haya ingresado información en los campos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>6. Valida la estructura de los datos ingresados en los campos de acuerdo con la **(RNA69)**.</p><p>&emsp;</p><p>- En caso de que la estructura de los datos ingresados sea incorrecta, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>7. Valida que no exista un registro con la misma información de acuerdo con la (**RNA04)**.</p><p></p><p>- En caso de que exista un registro con la misma información, continúa en el **([**FA09**](#fa09))**.</p>|
||<p>8. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Catálogos - Complementarios</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Alineación</p><p>- Id</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA10**](#fa10))**.</p>|
||<p>9. Almacena la siguiente información en la BD:</p><p>&emsp;</p><p>&emsp;- Id= Identificador generado automáticamente por el sistema.</p><p>&emsp;- Nombre</p><p>&emsp;- Descripción</p><p>&emsp;- Última modificación: Fecha del sistema (Día actual).</p><p>&emsp;- Estatus</p>|
||<p>10. Actualiza la tabla del catálogo alineación de los  “Catálogos complementarios” con la información ingresada.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarCatalogos)** Estilos 02.</p>|
||11. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|<p>12. Selecciona la opción **“Aceptar”**.</p><p></p>|13. Cierra el mensaje.|
||14. Fin del Caso de Uso.|

|<p></p><p><a name="fa05"></a>**FA05 Selecciona la opción “Editar” sobre un registro de la sección “Mapas – Objetivos”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT selecciona la opción **“Editar”** sobre un registro de la sección Mapas - Objetivos.|<p>2. <a name="_ref165591245"></a>Muestra en una ventana emergente la pantalla “Modificar registro de tipo Mapas - Objetivos” lo siguiente de acuerdo con la **(RNA03)**:</p><p></p><p>Modificar registro:</p><p>- Objetivo\*</p><p>&emsp;- Descripción\*</p><p>&emsp;- Estatus\*</p><p></p><p>Opciones:</p><p>- Cerrar ventana ![ref1]</p><p>&emsp;- Guardar</p><p>&emsp;- Cancelar</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarCatalogos)** Estilos 06.</p>|
|3. <a name="_ref165591115"></a>Modifica la información que requiera.||
|<p>4. <a name="_ref165590902"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Guardar”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“Cancelar”** o **“Cerrar ventana”**, continúa en el **([**FA06**](#fa06))**.</p>|<p>5. Valida que se haya ingresado datos en los campos obligatorios de acuerdo con la **(RNA68)** y **(RNA03)**.</p><p>&emsp;</p><p>- En caso de que no se haya ingresado información en los campos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>6. Valida la estructura de los datos ingresados en los campos de acuerdo con la **(RNA69)**.</p><p>&emsp;</p><p>- En caso de que la estructura de los datos ingresados sea incorrecta, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>7. Valida que no exista un registro con la misma información de acuerdo con la **(RNA04)**.</p><p></p><p>- En caso de que exista un registro con la misma información, continúa en el **([**FA09**](#fa09))**.</p>|
||<p>8. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Catálogos - Complementarios</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Catálogo Alineación (Opción seleccionada)</p><p>- Id</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA10**](#fa10))**.</p>|
||<p>9. Almacena la siguiente información en la BD:</p><p>&emsp;</p><p>&emsp;- Id= Identificador generado automáticamente por el sistema.</p><p>&emsp;- Objetivo</p><p>&emsp;- Descripción</p><p>&emsp;- Última modificación: Fecha del sistema (Día actual).</p><p>&emsp;- Estatus</p>|
||<p>24. Actualiza la tabla de la sección “Mapas - Objetivos” de los  “Catálogos complementarios” con la información ingresada.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_ModificarCatalogos)**  Estilos 05.</p>|
||10. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|<p>11. Selecciona la opción **“Aceptar”**.</p><p></p>|12. Cierra el mensaje.|
||13. Fin del Caso de Uso.|

|<p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Cancelar”, “Cerrar ventana” o “Descartar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”**, **“Cerrar ventana”** o **“Descartar”**.|2. Muestra el **([**MSG002**](#msg002))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona una opción.</p><p></p>|<p>4. Cierra el mensaje.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“No”** permanece en el paso donde fue invocado.</p>|
||<p>5. Realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el “Cancelar” o “Cerrar” cierra el modal sin almacenar ninguna información.</p><p>- Si fue invocado en la opción “Descartar”:</p><p>- Se inicializa el registro de la tabla de la sección donde fue invocado, y cambia a solo lectura si era un registro almacenado regresando los íconos a su estado original.</p><p>- Si era un registro nuevo elimina la fila.</p>|
||<p>6. Dependiendo de la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado por la opción “Descartar”, continúa en el paso **4** del **(FA01)**.</p><p></p><p>- Si fue invocado por las opciones “Cancelar” o “Cerrar ventana”, el proceso continúa en el punto de acción donde fue invocado en el **(17\_3083\_ECU\_AltaDeCatalogos)**.</p>|

|<p></p><p><a name="fa07"></a>**FA07 No se ingresaron los datos obligatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA07** inicia cuando el sistema identifica que no se ingresaron los datos obligatorios. |
||2. Muestra en rojo los campos pendientes a capturar.|
||3. Muestra el **([**MSG003**](#msg003))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Continúa en el paso previo de acción del flujo donde fue invocado.|

|<p></p><p><a name="fa08"></a>**FA08 La estructura de los datos ingresados es incorrecta**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA08** inicia cuando el sistema identifica que la estructura de los datos ingresados es incorrecta.|
||2. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso previo de acción del flujo donde fue invocado.|

|<p></p><p><a name="fa09"></a>**FA09 Registro duplicado**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA09** inicia cuando el sistema identifica que existe un registro con la misma información.|
||2. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar”. |
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso previo de acción del flujo donde fue invocado.|

|<p></p><p><a name="fa10"></a>**FA10 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA10** inicia cuando interviene un evento ajeno y no se pueden almacenar las pistas de auditoría.|
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>- Muestra el mensaje informativo de acuerdo con lo siguiente:</p><p></p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT** e **INSR**, se muestra el **([**MSG006**](#msg006))**.</p><p></p><p>- En caso de que la pista de auditoría es por el tipo de movimiento **PRNT**, se muestra el **(MSG007)**.</p><p></p><p>Cada mensaje con la opción “Aceptar”.</p>|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Regresa al paso previo que detona la acción de la pista de auditoría.|

|<p></p><p></p>|
| :- |
||
|<h3><a name="_toc168591333"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_ModificarCatalogos</p><p>- 17\_3083\_ECU\_AltaDeCatalogos</p><p></p>|

|<h3><a name="_toc168591334"></a>**9. Mensajes** </h3>|
| :- |
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Información actualizada correctamente.|
|<a name="msg002"></a>**MSG002**|<p>Se perderá toda la información no guardada.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg003"></a>**MSG003**|Favor de ingresar los datos obligatorios marcados con un asterisco (\*).|
|<a name="msg004"></a>**MSG004**|La estructura de la información ingresada es incorrecta. Intente nuevamente.|
|<a name="msg005"></a>**MSG005**|Registro duplicado. Intente nuevamente.|
|<a name="msg006"></a>**MSG006**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|**MSG007**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|**MSG008**|Existe más de un administrador para este puesto. Favor de validar.|
|**MSG009**|<p>Al cambiar el estatus de este registro, cambiará todos los estatus de los registros dependientes a éste.</p><p>¿Desea continuar?</p>|

|<p></p><p></p>|
| - |
|<h3><a name="_toc168591335"></a>**10. Requerimientos No Funcionales** </h3>|
||
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
|**RNF010**|Integridad |Al almacenar la información en la BD de tipo Texto o Alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena. |

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3>**<a name="_toc168591336"></a>11. Diagrama de actividad** </h3>|
|<p></p><p>![](Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.009.png)</p><p></p>|
||
|<h3><a name="_toc168591337"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no se requiere para este proceso.</p><p></p>|
|<h3>**<a name="_toc168591338"></a>13. Aprobación del cliente** </h3>|
||

|**FIRMAS DE CONFORMIDAD**||
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



|||Página 1 de 9|
| :- | :-: | -: |

[ref1]: Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.003.png
[ref2]: Aspose.Words.2e0c7fd6-aea2-4584-8e49-6700ce621f24.008.png
