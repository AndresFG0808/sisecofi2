|![](Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_ModificarContrato.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


<a name="_hlk163833417"></a>**<ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de contratos de contratación


**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Angel Horacio López Alcaraz|*19/02/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*20/04/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*28/04/2024*|



**Tabla de Contenido**

[17_3083_ECU_ModificarContrato	2](#_toc165648346)

[1. Descripción	2](#_toc165648347)

[2. Diagrama del Caso de Uso	2](#_toc165648348)

[3. Actores	2](#_toc165648349)

[4. Precondiciones	2](#_toc165648350)

[5. Post condiciones	3](#_toc165648351)

[6. Flujo primario	3](#_toc165648352)

[7. Flujos alternos	9](#_toc165648353)

[8. Referencias cruzadas	53](#_toc165648354)

[9. Mensajes	53](#_toc165648355)

[10. Requerimientos No Funcionales	54](#_toc165648356)

[11. Diagrama de actividad	56](#_toc165648357)

[12. Diagrama de estados	57](#_toc165648358)

[13. Aprobación del cliente	58](#_toc165648359)






### **<a name="_toc165648346"></a>**17\_3083\_ECU\_ModificarContrato

|<h3><a name="_toc165648347"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT la consulta, modificación y exportación de la información contenida en las secciones de un contrato.</p><p></p>|
|<h3><a name="_toc165648348"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.002.png)</p><p></p>|
|<h3><a name="_toc165648349"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

||
| :- |
|<h3><a name="_toc165648350"></a>**4. Precondiciones**</h3>|
|<p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida. </p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa. </p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar o modificar en el módulo Contratos.</p><p>- Se ha seleccionado un contrato.</p><p>- Se han registrado proyectos previamente.</p><p>- Se han registrado proveedores previamente.</p><p>- Se han creado los datos de “Identificación” de un contrato previamente.</p><p>&emsp;</p>|
|<h3><a name="_toc165648351"></a>**5. Post condiciones** </h3>|
|<p></p><p>El Empleado SAT:</p><p>- Modificó información de un contrato.</p><p>- Consultó información de un contrato.</p><p>- Exportó información de alguna de las secciones de un contrato.</p><p>&emsp;</p><p>&emsp;</p>|
|<h3><a name="_toc165648352"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref168321614"></a>El Caso de Uso inicia cuando el Empleado SAT selecciona la opción **“Editar”** en el proceso de **(17\_3083\_ECU\_AltaDeContrato)**.|<p>2. <a name="_ref168321592"></a>Consulta en la base de datos (BD) y obtiene la información del contrato seleccionado:</p><p>&emsp;</p><p>Sección Identificación</p><p>Campos</p><p>- Id</p><p>- Proyecto asociado</p><p>- Estatus</p><p>- Nombre del contrato</p><p>- Nombre corto del contrato </p><p>- Última modificación</p>|
||<p>3. <a name="_ref164503327"></a>Despliega la pantalla “Contratos” y presenta la información obtenida del contrato seleccionado: </p><p>&emsp;</p><p>Campo:</p><p>- Última modificación</p><p></p><p>Sección Identificación:</p><p>- Id. Aplica la **(RNA76)**</p><p>- Proyecto asociado\*</p><p>- Estatus</p><p>- Nombre del contrato\*</p><p>- Nombre corto del contrato\*</p><p>- Última modificación</p><p>&emsp;</p><p>Opciones. Aplica las **(RNA246)** y **(RNA75)**:</p><p>- Cancelar contrato ![ref1]</p><p>- Inicial</p><p>- Ejecución</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Secciones colapsadas</p><p>- Datos generales</p><p>- Vigencia y montos</p><p>- Grupos de servicio y/o conceptos</p><p>- Registro de servicios</p><p>- Proyección de caso de negocio</p><p>- Cargar *layout* de los informes</p><p>- Atraso en el inicio de la prestación</p><p>- Informes documentales por única vez</p><p>- Informes documentales periódicos</p><p>- Informes documentales de los servicios</p><p>- Penas contractuales</p><p>- Niveles de servicio (SLA)</p><p>- Asignación de plantilla</p><p>- Gestión documental</p><p>- Convenios modificatorios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p>- Reintegros asociados</p><p>- Cierre</p><p></p><p>Opción</p><p>- ` `Regresar</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarContrato)** Estilos 01.</p>|
|<p>4. <a name="_ref166747525"></a><a name="_ref158742067"></a>Selecciona la sección **“Datos generales”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si requiere actualizar la sección **“Identificación”** del contrato, continúa en el flujo alterno **([**FA01**](#fa01))**.</p><p>- Si selecciona la sección **“Vigencia y montos”**, continúa en el **([**FA08**](#fa08))**.</p><p>- Si selecciona la sección **“Grupos de servicio y/o conceptos”**, continúa en el **([**FA09**](#fa09))**.</p><p>- Si selecciona la sección **“Registro de servicios”**, continúa en el **([**FA10**](#fa10))**.</p><p>- Si selecciona la sección **“Proyección de caso de negocio”**, continúa en el **(17\_3083\_ECU\_CasoDeNegocio)**.</p><p>- Si selecciona la opción **“Cargar layout de los informes”**, continúa en el **([**FA29**](#fa29))**.</p><p>- Si selecciona la sección **“Atraso en el inicio de la prestación”**, continúa en el **([**FA11**](#fa11))**.</p><p>- Si selecciona la sección **“Informes documentales por única vez”**, continúa en el **([**FA13**](#fa13))**.</p><p>- Si selecciona la sección **“Informes documentales periódicos”**, continúa en el **([**FA14**](#fa14))**.</p><p>- Si selecciona la sección **“Informes documentales de los servicios”** continúa en el **([**FA15**](#fa15))**.</p><p>- Si selecciona la sección **“Penas contractuales”**, continúa en el **([**FA12**](#fa12))**.</p><p>- Si selecciona la sección **“Niveles de servicio (SLA)”**, continúa en el **([**FA16**](#fa16))**.</p><p>- Si selecciona la sección **“Asignación de plantilla”**, continúa en el **([**FA17**](#fa17))**.</p><p>- Si selecciona la sección **“Gestión documental”**, continúa en el **(17\_3083\_ECU\_GestionDocumental)**.</p><p>- Si selecciona la sección **“Convenios modificatorios”**, continúa en el **([**FA22**](#fa22))**.</p><p>- Si selecciona la sección **“Dictámenes asociados”**, continúa en el **([**FA23**](#fa23))**.</p><p>- Si selecciona la sección **“Facturas asociadas”**, continúa en el **([**FA24**](#fa24))**.</p><p>- Si selecciona la sección **“Reintegros asociados”**, continúa en el **([**FA25**](#fa25))**.</p><p>- En caso de seleccionar **“Regresar”**, continúa en el **(17\_3083\_ECU\_AltaDeContrato)**.</p><p>- Si selecciona la opción **“Cancelar Contrato”** de la sección **“Identificación”**, continúa en el **([**FA34**](#fa34))**.</p><p>- Si selecciona la opción **“Cancelar”** de la sección **“Identificación”**, continúa en el **([**FA05**](#fa05))**.</p><p>- Si selecciona la opción **“Guardar”** contrato de la sección **“Identificación”**, continúa en el paso [4](#_ref167869609) del **([**FA01**](#fa01))**.</p><p>- Si selecciona la opción **“Cierre”**, continúa en el **([**FA26**](#fa26))**.</p>|<p>5. Consulta en la BD y obtiene la información del contrato seleccionado:</p><p>&emsp;</p><p>Sección Datos generales:</p><p>- Número de contrato</p><p>- Número de contrato CompraNet:</p><p>- Acuerdo</p><p>- Proveedor(es)</p><p>- RFC</p><p>- Tipo de procedimiento</p><p>- Número de procedimiento</p><p>- Convenio de colaboración</p><p>- Dominio tecnológico</p><p>- Fondeo del contrato</p><p>- Objetivo del servicio</p><p>- Alcance del servicio</p><p></p><p>Tabla Participantes en la administración del contrato</p><p>- Id</p><p>- Responsabilidad</p><p>- Administración general</p><p>- Administración central</p><p>- Nombre del servidor público</p><p>- Teléfono</p><p>- Correo</p><p>- Fecha de inicio</p><p>- Fecha de término</p><p>- Vigente</p>|
||<p>6. <a name="_ref165619205"></a>Despliega la sección y presenta la información obtenida de la sección “Datos generales” del contrato seleccionado:</p><p>&emsp;</p><p>- Número de contrato\*</p><p>- Número de contrato CompraNet</p><p>- Acuerdo</p><p>- Proveedor\*</p><p>- Nuevo proveedor![ref2]</p><p>- RFC</p><p>- Eliminar proveedor![ref3]</p><p>- Tipo de procedimiento\*</p><p>- Número de procedimiento\*</p><p>- Convenio de colaboración</p><p>- Dominio tecnológico</p><p>- Fondeo del contrato\*</p><p>- Objetivo del servicio\*</p><p>- Alcance del servicio\*</p><p></p><p>Opciones. Aplica la **(RNA246)**:</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Tabla “Participantes en la administración del contrato”. Aplica la **(RNA244)**</p><p>- Id</p><p>- Responsabilidad</p><p>- Administración general</p><p>- Administración central</p><p>- Nombre del servidor público</p><p>- Teléfono</p><p>- Correo</p><p>- Fecha de inicio</p><p>- Fecha de término</p><p>- Vigente</p><p></p><p>Opciones. Aplica la **(RNA246)**:</p><p>- Nuevo participante ![ref2]</p><p>- Exportar a Excel ![](Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.006.png)</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarContrato)** Estilos 02.</p>|
|<p>7. <a name="_ref165621163"></a>Captura los datos de la sección **“Datos generales”** y continúa en el flujo.</p><p>&emsp;</p><p>- Número de contrato\*</p><p>- Número de contrato CompraNet</p><p>- Acuerdo</p><p>- Proveedor\*</p><p>- RFC</p><p>- Tipo de procedimiento\*</p><p>- Número de procedimiento\*</p><p>- Convenio de colaboración</p><p>- Dominio tecnológico</p><p>- Fondeo del contrato\*</p><p>- Objetivo del servicio\*</p><p>- Alcance del servicio\*</p><p></p><p>- Si selecciona la opción **“Nuevo proveedor”**, continúa en el **([**FA27**](#fa27))**.</p><p>- Si selecciona la opción **“Eliminar proveedor”**, para un registro ya almacenado, continúa en el **([**FA27**](#fa27))**.</p><p>- Si selecciona la opción **“Eliminar proveedor”**, para un registro nuevo, continúa en el **([**FA05**](#fa05))**. </p><p>- En caso de que seleccione la opción **“Nuevo”** de la tabla de la sección “**Participantes en la Administración del contrato”**, continúa en el **([**FA21**](#fa21))**.</p><p>- En caso de que seleccione la opción “**Exportar a Excel”** de la tabla de la sección **“Participantes en la administración del contrato”**, continúa en el **([**FA02**](#fa02))**.</p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla de la sección “**Participantes en la Administración del contrato”**, continúa en el **([**FA37**](#fa37))**.</p><p></p>||
|<p>8. <a name="_ref165643749"></a>Selecciona la opción **“Guardar”** de la sección **“Datos generales”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de seleccionar **“Cancelar”** de la sección **“Datos generales”**, continúa en el **([**FA05**](#fa05))**.</p>|<p>9. Valida que se hayan capturado los datos obligatorios de acuerdo con las reglas de negocio **(RNA01)** y **(RNA03)**.</p><p>&emsp;</p><p>- En caso contrario, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>10. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos-Datos generales</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), según corresponda **Movimiento**= Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>&emsp;</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA06**](#fa06))**.</p>|
||<p>11. Almacena en la BD la siguiente información que se encuentra capturada en la sección “Datos generales”. Aplica la **(RNA247)**:</p><p>&emsp;</p><p>- Última modificación</p><p>- Número de contrato</p><p>- Número de contrato CompraNet</p><p>- Acuerdo</p><p>- Proveedor(es)</p><p>- RFC (s)</p><p>- Tipo de procedimiento</p><p>- Número de procedimiento</p><p>- Convenio de colaboración</p><p>- Dominio tecnológico</p><p>- Fondeo del contrato</p><p>- Objetivo del servicio</p><p>- Alcance del servicio</p><p>&emsp;</p>|
||12. Muestra el mensaje **([**MSG004**](#msg004))** con la opción** “Aceptar”.|
|13. Selecciona la opción **“Aceptar”**.|14. Cierra el mensaje.|
||15. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc165648353"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a><a name="_hlk164015195"></a>**FA01 Modificar la sección “Identificación” del contrato**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref165644530"></a>El **FA01** inicia cuando el Empleado SAT requiere actualizar los datos de identificación del contrato.|<p>2. <a name="_ref168310481"></a>Despliega la pantalla “Contratos” y presenta la información obtenida del contrato seleccionado: </p><p>&emsp;</p><p>Sección Identificación:</p><p>- Id. Aplica la **(RNA76)**</p><p>- Proyecto asociado\*</p><p>- Estatus</p><p>- Nombre del contrato\*</p><p>- Nombre corto del contrato\*</p><p>- Última modificación</p><p>&emsp;</p><p>Opciones. Aplican las **(RNA246)** y **(RNA75)**:</p><p>- Cancelar contrato ![ref1]</p><p>- Inicial</p><p>- Ejecución</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Secciones colapsadas</p><p>- Datos generales</p><p>- Vigencia y montos</p><p>- Grupos de servicio y/o conceptos</p><p>- Registro de servicios</p><p>- Proyección de caso de negocio</p><p>- Cargar *layout* de los informes</p><p>- Atraso en el inicio de la prestación</p><p>- Informes documentales por única vez</p><p>- Informes documentales periódicos</p><p>- Informes documentales de los servicios</p><p>- Penas contractuales</p><p>- Niveles de servicio (SLA)</p><p>- Asignación de plantilla</p><p>- Gestión documental</p><p>- Convenios modificatorios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p>- Reintegros asociados</p><p>- Cierre</p><p></p><p>Opción</p><p>- ` `Regresar</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarContrato)** Estilos 01.</p>|
|<p>3. <a name="_ref164503790"></a>Modifica los valores de alguno de los siguientes datos: </p><p>&emsp;</p><p>- Proyecto asociado</p><p>- Nombre del contrato</p><p>- Nombre corto del contrato</p>||
|<p>4. <a name="_ref167869609"></a>Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que se seleccione la opción **“Cancelar”**, continúa en el **([**FA05**](#fa05))**.</p><p>- En caso de que seleccione la opción **“Cancelar contrato”**, continúa en el **([**FA34**](#fa34))**.</p><p>- En caso de seleccionar la opción **“Inicial”**, continúa en el **([**FA39**](#fa39))**.</p><p>- Si selecciona la opción **“Ejecución”** continúa en el **([**FA38**](#fa38))**.</p><p></p><p></p>|<p>5. Valida que se hayan capturado los datos obligatorios de acuerdo con la **(RNA03)**.</p><p>&emsp;</p><p>- En caso contrario, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>6. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;` `Datos que se almacenan:</p><p>**Módulo**= Contratos-Identificación</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-	Id de contrato</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA06**](#fa06))**.</p>|
||<p>7. Almacena en la BD las modificaciones de los campos. Aplica la **(RNA247)**:</p><p>&emsp;</p><p>- Proyecto asociado</p><p>- Nombre del contrato</p><p>- Nombre corto del contrato</p>|
||8. Muestra el **([**MSG004**](#msg004))** con la opción** “Aceptar”.|
|9. Selecciona la opción **“Aceptar”**.|10. Cierra el mensaje.|
||11. <a name="_hlk165637478"></a>Continúa en el paso [**4**](#_ref166747525) del Flujo primario.|

|<p></p><p><a name="fa02"></a>**FA02 Selecciona la opción “Exportar a Excel”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA02** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**. |<p>2. ` `Almacena en la BD la información de las Pistas de Auditoría. </p><p>&emsp;  </p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Contratos-Sección en la que fue invocado</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS </p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema. </p><p>**Tipo de movimiento**= **PRNT** (Imprimir) </p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id contrato </p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA06**](#fa06))**. </p>|
||<p>3. Obtiene la información de la sección conforme a lo siguiente:</p><p>&emsp;</p><p>- Si se invocó en el paso 12 del Flujo principal conforme a la **(RNA232)**.</p><p>- Si se invocó en el paso 4 del **([**FA10**](#fa10))** conforme a la **(RNA196)**.</p><p>- Si se invocó en el paso 4 del **([**FA11**](#fa11))** conforme a la **(RNA197)**.</p><p>- Si se invocó en el paso 4 del **([**FA12**](#fa12))** conforme a la **(RNA198)**.</p><p>- Si se invocó en el paso 4 del **([**FA13**](#fa13))** conforme a la **(RNA199)**.</p><p>- Si se invocó en el paso 4 del **([**FA14**](#fa14))** conforme a la **(RNA200)**.</p><p>- Si se invocó en el paso 4 del **([**FA15**](#fa15))** conforme a la **(RNA201)**.</p><p>- Si se invocó en el paso 4 del **([**FA16**](#fa16))** conforme a la **(RNA202)**.</p><p>- Si se invocó en el paso 4 del **([**FA22**](#fa22))** conforme a la **(RNA233)**.</p><p>- Si se invocó en el paso 4 del **([**FA23**](#fa23))** conforme a la **(RNA235)**.</p><p>- Si se invocó en el paso 4 del **([**FA24**](#fa24))** conforme a la **(RNA234)**.</p><p>- Si se invocó en el paso 4 del **([**FA25**](#fa25))** conforme a la **(RNA236)**.</p><p>&emsp;</p>|
||4. Genera un archivo en formato Excel con extensión (.xlsx) con la información obtenida.|
||5. Descarga el archivo Excel con extensión (.xlsx). |
||<p>6. Fin del Caso de uso.</p><p>&emsp;</p>|

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p><a name="fa03"></a>**FA03 Selecciona la opción “Vigente” del participante en la administración del contrato**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref165305667"></a>El **FA03** inicia cuando el Empleado SAT selecciona la opción **“Vigente”**.|<p>2. Valida el estado actual del registro, si este se encuentra Activo, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que se encuentre Inactivo, continúa en el **([\[**FA04**\](#fa04)](#fa03))**. </p>|
||<p>3. Muestra el **([\[**MSG008**\](#msg008)](#msg003))** con las opciones “Sí” y “No”.</p><p>&emsp;</p><p></p>|
|<p>4. Selecciona la opción **“Sí”**, y continúa en el paso [**5**](#_ref168512393) del presente flujo.</p><p>&emsp;</p><p>- Si selecciona la opción **“No”**, continúa en el paso [**6**](#_ref168512407) del presente flujo.</p>|<p>5. <a name="_ref168512393"></a>Valida que exista “Fecha de término” y continúa en el flujo.</p><p>&emsp;</p><p>- Si no existe “Fecha de término”, continúa en el **([**FA35**](#fa35))**.</p>|
||6. <a name="_ref168512407"></a>Modifica el estatus del registro a “Inactivo”.|
||<p>7. <a name="_ref166853429"></a>Regresa al paso **[**3**](#_ref164503241)** del **([**FA21**](#fa21))**.</p><p>&emsp;</p><p></p>|

|<p></p><p><a name="fa04"></a>**FA04 Activar registro**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. <a name="_ref165305687"></a>El **FA04** inicia cuando identifica que el registro se encuentra Inactivo.|
||2. Modifica el estatus del registro a Activo.|
||<p>3. Regresa al paso **[**3**](#_ref164503241)** del **([**FA21**](#fa21))**.</p><p></p>|

|<p></p><p><a name="fa05"></a>**FA05 Selecciona la opción “Cancelar” o “Descartar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”** o **“Descartar”**.|2. Muestra el **([**MSG002**](#msg002))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona la opción **“No”**, y continúa en el paso [**5**](#_ref168497471) de este flujo.</p><p>&emsp;</p><p>- En caso de seleccionar **“Sí”**,** continúa en el paso [**6**](#_ref168497621) de este flujo.</p>|4. Cierra la ventana emergente y continúa en el paso [**6**](#_ref168497621) de este flujo.|
||<p>5. <a name="_ref168497471"></a>Si fue invocado en la opción “Cancelar” se inicializa la tabla de la sección donde fue invocado y no almacena ninguna información.</p><p>&emsp;</p><p>- Si fue invocado en la opción “Descartar”:</p><p>&emsp;- Se inicializa el registro de la tabla de la sección donde fue invocado y cambia a solo lectura si era un registro almacenado regresando los íconos a su estado original.</p><p>&emsp;- Si era un registro nuevo elimina la fila.</p>|
||6. <a name="_ref157021808"></a><a name="_ref168497621"></a>Permanece en la sección donde fue invocado.|

|<p></p><p><a name="fa06"></a>**FA06 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA06** inicia cuando interviene un evento ajeno y no se pueden almacenar las Pistas de Auditoría.  |
||2. Cancela la operación sin completar el movimiento que estaba en proceso. |
||<p>3. Muestra el mensaje de acuerdo con lo siguiente: </p><p>&emsp;</p><p>- Si la Pista de Auditoría es por el tipo de movimiento **UPDT** e **INSR**, se muestra el **([**MSG006**](#msg006))**. </p><p>&emsp;</p><p>- Si la Pista de Auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG012**](#msg012))**. </p><p>&emsp;</p><p>- En caso de que la Pista de Auditoría es por el tipo de movimiento **PRNT**, se muestra el **([**MSG010**](#msg010))**.</p><p>&emsp;</p><p>- Si la Pista de Auditoría es por el tipo de movimiento **DLT**, se muestra el **([**MSG022**](#msg022))**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”. </p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||<p>6. Regresa al paso previo que detona la acción de la pista de auditoría.</p><p></p>|

|<p></p><p><a name="fa07"></a>**FA07 No se ingresaron los campos obligatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|  |1. El **FA07** inicia cuando el sistema identifica que no se ingresaron los datos obligatorios. |
| |2. Muestra en rojo los campos pendientes de capturar. |
||3. Muestra el **([**MSG001**](#msg001))**, con la opción “Aceptar”. |
|4. Selecciona la opción **“Aceptar”**. |5. Cierra el mensaje.  |
||<p>6. Realiza lo siguiente: </p><p>&emsp;</p><p>- Si fue invocado en el paso 13 del Flujo principal, regresa al paso [**7**](#_ref165621163) del Flujo principal.</p><p>- Si fue invocado en el paso 4 del **([**FA01**](#fa01))**, regresa al paso [**2**](#_ref168310481) del **([**FA01**](#fa01))**.</p><p>- Si fue invocado en el paso 6 del **([**FA08**](#fa08))**, regresa al paso [**4**](#_ref165621866) del **([**FA08**](#fa08))**.</p><p>- Si fue invocado en el paso 9 del **([**FA09**](#fa09))**, regresa al paso [**7**](#_ref164505172) del **([**FA09**](#fa09))**.</p><p>- Si fue invocado en el paso 9 del **([**FA10**](#fa10))**, regresa al paso [**7**](#_ref164505193) del **([**FA10**](#fa10))**.</p><p>- Si fue invocado en el paso 7 del **([**FA11**](#fa11))**, regresa al paso [**6**](#_ref164505216) del **([**FA11**](#fa11))**.</p><p>- Si fue invocado en el paso 7 del **([**FA12**](#fa12))**, regresa al paso [**6**](#_ref164505234) del **([**FA12**](#fa12))**.</p><p>- Si fue invocado en el paso 7 del **([**FA13**](#fa13))**, regresa al paso [**6**](#_ref164505258) del **([**FA13**](#fa13))**.</p><p>- Si fue invocado en el paso 7 del **([**FA14**](#fa14))**, regresa al paso [**6**](#_ref164505282) del **([**FA14**](#fa14))**.</p><p>- Si fue invocado en el paso 7 del **([**FA15**](#fa15))**, regresa al paso [**6**](#_ref164505307) del **([**FA15**](#fa15))**.</p><p>- Si fue invocado en el paso 7 del **([**FA16**](#fa16))**, regresa al paso [**6**](#_ref164505324) del **([**FA16**](#fa16))**.</p><p>- Si fue invocado en el paso 9 del **([**FA17**](#fa17))**, regresa al paso **[**7**](#_ref168326138)** del **([**FA17**](#fa17))**.</p><p>- Si fue invocado en el paso 5 del **([**FA21**](#fa21))**, regresa al paso **[**3**](#_ref164503241)** del **([**FA21**](#fa21))**.</p>|

|<p></p><p><a name="fa08"></a>**FA08 Selecciona la opción “Vigencia y montos”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA08** inicia cuando el Empleado SAT selecciona la sección “**Vigencia y montos”**.|<p>2. <a name="_ref164504106"></a>Consulta en la BD y obtiene la información de la sección “Vigencia y montos” que se encuentre almacenada.</p><p>&emsp;</p><p>- Fecha de inicio de vigencia de los servicios</p><p>- Fecha de fin de vigencia de los servicios</p><p>- Duración de los servicios</p><p>- Fecha de inicio de vigencia del contrato</p><p>- Fecha de fin de vigencia del contrato</p><p>- Moneda</p><p>- Tipo de cambio máximo aprobado</p><p>- Aplica IVA</p><p>- <a name="_hlk165030147"></a>Porcentaje de IVA</p><p>- Porcentaje de IEPS</p><p>- Monto mínimo sin impuestos</p><p>- Monto máximo sin impuestos</p><p>- Monto en pesos sin impuestos</p><p>- Monto mínimo con impuestos</p><p>- Monto máximo con impuestos</p><p>- Monto en pesos con impuestos</p>|
||<p>3. <a name="_ref164506016"></a>Despliega la sección y presenta la información obtenida de la sección “Vigencia y montos” en los siguientes campos. Aplica la **(RNA01)**:</p><p>&emsp;</p><p>Datos:</p><p>- Fecha de inicio de vigencia de los servicios\*</p><p>- Fecha de fin de vigencia de los servicios\*</p><p>- Duración de los servicios Aplica la **(RNA204)**</p><p>- Fecha de inicio de vigencia del contrato\*</p><p>- Fecha de fin de vigencia del contrato\*</p><p>- Moneda\*</p><p>- Tipo de cambio máximo aprobado</p><p>- Aplica IVA\*</p><p>- Porcentaje de IVA</p><p>- Porcentaje de IEPS</p><p>- Monto mínimo sin impuestos\*</p><p>- Monto máximo sin impuestos\*</p><p>- Monto en pesos sin impuestos\*</p><p>- Monto mínimo con impuestos\*</p><p>- Monto máximo con impuestos\*</p><p>- Monto en pesos con impuestos\*</p><p></p><p>Opciones:</p><p>- Cancelar</p><p>- Guardar</p><p>&emsp;</p><p>Ver **(17\_3083\_EIU\_ModificarContrato)** Estilos 03.</p>|
|4. <a name="_ref164505129"></a><a name="_ref165621866"></a>Captura los datos de la sección **“Vigencia y montos”** requeridos para la modificación.||
|<p>5. Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Cancelar”**, continúa en el **([**FA05**](#fa05))**.</p><p>- Si selecciona la opción Eliminar, continúa en el **([**FA19**](#fa19))**.</p>|<p>6. Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA03)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>7. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contratos-Vigencia y montos</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= </p><p>**INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-	Id de contrato</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||8. Almacena los datos que hayan sido modificados. Aplica la **(RNA247)**.|
||9. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|10. Selecciona la opción **“Aceptar”**.|11. Cierra el mensaje.|
||12. Continúa en el paso **[**4**](#_ref166747525)** del Flujo primario.|

|<p></p><p><a name="fa09"></a>**FA09 Selecciona la opción “Grupos de servicio y/o conceptos”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA09** inicia cuando el Empleado SAT selecciona la sección **“Grupos de servicio y/o conceptos”**.|2. Consulta en la BD y obtiene la información del catálogo Tipo de consumo y lo muestra en la lista de selección. |
||<p>3. Consulta en la BD y obtiene la información de la sección “Grupos de servicio y/o conceptos” que se encuentre almacenada.</p><p>&emsp;</p><p>- Grupo</p><p>- Tipo de consumo</p><p></p>|
||<p>4. <a name="_ref164504159"></a>Despliega la sección y presenta la información obtenida de “Grupos de servicio y/o conceptos” en los siguientes campos:</p><p>&emsp;</p><p>- Grupo\*</p><p>- Tipo de consumo\*. Aplica la **(RNA01)**</p><p></p><p>Opciones</p><p>- Nuevo![ref4]</p><p>- Eliminar ![ref3]</p><p>- Cancelar</p><p>- Guardar</p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 04.</p>|
|<p>5. Selecciona la opción **“Nuevo”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si requiere modificar alguno de los datos almacenados continúa en el paso [**7**](#_ref164505172) de este flujo.</p><p>- Si selecciona la opción Eliminar, continúa en el **([**FA19**](#fa19))**.</p>|6. Muestra un nuevo campo de texto para el campo “Grupo” y una lista de selección para elegir el “Tipo de consumo”.|
|<p>7. <a name="_ref164505172"></a>Captura. los datos de **“Grupos de servicio y/o conceptos”<a name="_hlk165637190"></a>**.</p><p>&emsp;</p><p>- Grupo</p><p>- Tipo de consumo</p>||
|<p>8. Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p><p>- Si selecciona la opción **“Eliminar proveedor”**, para un registro ya almacenado, continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Eliminar proveedor”**, para un registro nuevo, continúa en el **([**FA05**](#fa05))**.</p>|<p>9. Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA195)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>10. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contratos- Grupos de servicio y/o conceptos</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**=<a name="_hlk167350559"></a> **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-	Id de contrato</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||11. Se almacenan en la BD los datos que hayan sido actualizados. Aplica la **(RNA247)**.|
||12. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|13. Selecciona la opción **“Aceptar”**.|14. Cierra el mensaje.|
||15. Continúa en el paso [**4**](#_ref166747525)** del Flujo primario.|

|<p></p><p><a name="fa10"></a>**FA10 Selecciona la opción “Registro de servicios”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA10** inicia cuando el Empleado SAT selecciona la sección **“Registro de servicios”**.|<p>2. Consulta en la BD y obtiene la información de los catálogos:</p><p>&emsp;</p><p>- Grupo</p><p>- Tipo de unidad</p><p></p>|
||<p>3. Consulta en la BD y obtiene la información de la sección “Registro de servicios” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id</p><p>- Grupo</p><p>- Tipo de consumo</p><p>- Clave productos y servicios</p><p>- Conceptos de servicio</p><p>- Tipo de unidad</p><p>- Precio unitario</p><p>- Cantidad de servicios mínima</p><p>- Cantidad de servicios máxima</p><p>- Monto mínimo</p><p>- Monto máximo</p><p>- Aplica IEPS</p>|
||<p>4. <a name="_ref164504187"></a>Despliega la sección y presenta la información obtenida de la sección “Registro de servicios”. Aplica la **(RNA01)**:</p><p>&emsp;</p><p>Tabla Registro de servicios. Aplica la **(RNA244)**:</p><p>- Id</p><p>- Grupo</p><p>- Tipo de consumo</p><p>- Clave productos y servicios</p><p>- Conceptos de servicio</p><p>- Tipo de unidad</p><p>- Precio unitario</p><p>- Cantidad de servicios mínima</p><p>- Cantidad de servicios máxima</p><p>- Monto mínimo</p><p>- Monto máximo</p><p>- Aplica IEPS </p><p></p><p>Opciones:</p><p>- Nuevo![ref4]</p><p>- Descargar Excel ![ref5]</p><p>- Editar![ref6]</p><p>- Eliminar![ref7]</p><p>- Validar</p><p>- Guardar</p><p>- Cancelar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 05.</p>|
|<p>5. <a name="_ref165643818"></a>Selecciona la opción **“Nuevo”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si selecciona la opción **“Descargar a Excel”**, continúa en el **([**FA02**](#fa02))**.</p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA18**](#fa18))**.</p><p>- Si selecciona la opción **“Eliminar”**, continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Validar”**, continúa en el **([**FA28**](#fa28))**.</p><p>- Si selecciona la opción **“Guardar”** continúa en el paso [**9**](#_ref168513348) de este flujo.</p><p>- En caso de que seleccione la opción **“Cancelar”**, continúa en el **([**FA05**](#fa05))**.</p>|<p>6. <a name="_hlk167363524"></a>Inserta un registro en la tabla de la sección “Registro de servicios” en modo edición. Aplica **(RNA250)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p>|
|7. <a name="_ref164505193"></a>Captura los datos de **“Registro de servicios”** requeridos para la modificación y** regresa al paso 4 del presente flujo.|<p>8. Si los campos capturados fueron:</p><p>- Precio unitario </p><p>- Cantidad de servicios mínima</p><p>- Cantidad de servicios máxima</p><p>- Monto mínimo</p><p>- Monto máximo</p><p>&emsp;</p><p>Aplica la **(RNA77)** y regresa al paso [**5**](#_ref165643818) de este flujo.</p>|
||<p>9. <a name="_ref168513348"></a>Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA196)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>10. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos- Registro de servicios</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>- Id del Registro de Servicios</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||11. Almacena en la BD los datos actualizados. Aplica la <a name="_hlk167353057"></a>**(RNA247)**.|
||12. Muestra la sección con la información de la tabla actualizada. Aplica la **(RNA244)**.|
||13. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|14. Selecciona la opción **“Aceptar”**.|15. Cierra el mensaje.|
||16. Continúa en el paso **[**4**](#_ref166747525)** del Flujo primario.|

|<p></p><p><a name="fa11"></a>**FA11 Selecciona la opción “Atraso en el inicio de la prestación”**</p>|
| :- |

|<a name="_hlk165494025"></a>**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA11** inicia cuando el Empleado SAT selecciona la sección **“Atraso en el inicio de la prestación”**.|<p>2. Consulta en la BD y obtiene la información de la sección “Atraso en el inicio de la prestación” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id</p><p>- Descripción</p><p>- Fecha de prestación</p><p>- Penas y/o deducciones aplicables</p>|
||<p>3. <a name="_ref164504211"></a>Despliega la sección y presenta la información obtenida de la sección “Atraso en el inicio de la prestación” en los siguientes campos:</p><p>&emsp;</p><p>Tabla “Atraso en el inicio de la prestación”. Aplica la **(RNA244)**.</p><p>- Id</p><p>- Descripción</p><p>- Fecha de prestación</p><p>- Penas y/o deducciones aplicables</p><p></p><p>Opciones:</p><p>- Nuevo ![ref4]</p><p>- Descargar Excel ![ref5]</p><p>- Editar ![ref6]</p><p>- Eliminar![ref7]</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 06.</p>|
|<p>4. <a name="_ref165643772"></a>Selecciona la opción **“Nuevo”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si selecciona la opción **“Descargar a Excel”**, continúa en el **([**FA02**](#fa02))**. </p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA18**](#fa18))**.</p><p>- Si selecciona la opción **“Eliminar”**,** continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Guardar”** continúa en el paso [**7**](#_ref168513585) de este flujo.</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p><p></p>|<p>5. Inserta un registro en la tabla de la sección “Atraso en el inicio de la prestación” en modo edición.<a name="_hlk167382068"></a> Aplica la **(RNA250)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p>|
|6. <a name="_ref164505216"></a>Captura los datos de la sección **“Atraso en el inicio de la prestación”** y** regresa al paso [**4**](#_ref165643772) del presente flujo.|<p>7. <a name="_ref168513585"></a>Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA197)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>8. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos-Atraso en el inicio de la prestación</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>- Id de atraso en el inicio de la prestación</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||9. Almacena en la BD los datos de la tabla. Aplica la **(RNA247)**.|
||10. Muestra la sección con la información de la tabla actualizada. Aplica la **(RNA244)**.|
||11. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|12. Selecciona la opción **“Aceptar”**.|13. Cierra el mensaje.|
||14. Continúa en el paso [**4**](#_ref166747525) del Flujo primario.|

|<p></p><p><a name="fa12"></a>**FA12 Penas contractuales**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA12** inicia cuando el Empleado SAT selecciona la sección **“Penas contractuales”**.|<p>2. Consulta en la BD y obtiene la información de la sección “Penas contractuales” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id</p><p>- Informe/Documento/Concepto de servicio</p><p>- Descripción</p><p>- Plazo de entrega</p><p>- Pena aplicable</p>|
||<p>3. <a name="_ref165647256"></a>Despliega la sección y presenta la información obtenida de la sección “Penas contractuales” en los siguientes campos:</p><p>&emsp;</p><p>Tabla “Penas contractuales”. Aplica la **(RNA244)**.</p><p>- Id</p><p>- Informe/Documento/Concepto de servicio</p><p>- Descripción</p><p>- Plazo de entrega</p><p>- Pena aplicable</p><p>&emsp;</p><p>Opciones:</p><p>- Descargar Excel ![ref5]</p><p>- Nuevo ![ref4]</p><p>- Editar![ref6]</p><p>- Eliminar![ref7]</p><p>- Cancelar</p><p>- Guardar</p><p>&emsp;</p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 07.</p><p></p>|
|<p>4. <a name="_ref165643866"></a>Selecciona la opción **“Nuevo”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si selecciona la opción **“Descargar a Excel”**, continúa en el **([**FA02**](#fa02))**.</p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA18**](#fa18))**.</p><p>- Si selecciona la opción **“Eliminar”**,** continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Guardar”** continúa en el paso [**7**](#_ref168513749) de este flujo.</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p>|<p>5. <a name="_ref168325847"></a>Inserta un registro en la tabla de la sección “Penas contractuales” en modo edición. Aplica la **(RNA250)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p>|
|6. <a name="_ref164505234"></a>Captura los datos de la sección **“Penas contractuales”** y** regresa al paso 4 del presente flujo.|<p>7. <a name="_ref168513749"></a>Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA198)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>8. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos- Penas contractuales</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>- Id de penas contractuales</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||9. Almacena en la BD los datos de la tabla. Aplica la **(RNA247)**.|
||10. Muestra la sección con la información de la tabla actualizada. Aplica la **(RNA244)**.|
||11. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|12. Selecciona la opción **“Aceptar”**.|13. Cierra el mensaje.|
||14. Continúa en el paso [**4**](#_ref166747525) del Flujo primario.|

|<p></p><p><a name="fa13"></a>**FA13 Selecciona la opción “Informes documentales por única vez”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA13** inicia cuando el Empleado SAT selecciona la sección **“Informes documentales por única vez”**.|<p>2. Consulta en la BD y obtiene la información de la sección “Informes documentales por única vez” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id</p><p>- Fase</p><p>- Informe documental</p><p>- Fecha de entrega</p><p>- Penas y/o deducciones aplicables</p><p>- Descripción</p>|
||<p>3. <a name="_ref164504261"></a>Despliega la sección y presenta la información obtenida de la sección “Informes documentales por única vez” en los siguientes campos:</p><p>&emsp;</p><p>Tabla “Informes documentales por única vez”. Aplica la **(RNA244)**.</p><p>- Id</p><p>- Fase</p><p>- Informe documental</p><p>- Fecha de entrega</p><p>- Penas y/o deducciones aplicables</p><p>- Descripción</p><p></p><p>Opciones:</p><p>- Nuevo![ref4]</p><p>- Descargar Excel ![ref8]</p><p>- Editar ![ref6]</p><p>- Eliminar![ref7]</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 08.</p>|
|<p>4. <a name="_ref165643886"></a>Selecciona la opción **“Nuevo”** y continúa en el flujo.</p><p></p><p>- Si selecciona la opción **“Descargar a Excel”**, continúa en el **([**FA02**](#fa02))**.</p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA18**](#fa18))**.</p><p>- Si selecciona la opción **“Eliminar”**,** continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Guardar”** continúa en el paso [**7**](#_ref168513946) de este flujo.</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p>|<p>5. Inserta un registro en la tabla de la sección “Informes documentales por única vez” en modo edición. Aplica la **(RNA250)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p><p></p><p></p><p></p>|
|6. <a name="_ref164505258"></a>Captura los datos de la sección **“Informes documentales por única vez”** y** regresa al paso [**4**](#_ref165643886) del presente flujo.|<p>7. <a name="_ref168513946"></a>Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA199)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>8. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos- Informes documentales por única vez</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>- Id informe por única vez</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||9. Almacena en la BD los datos de la tabla. Aplica la **(RNA247)**.|
||10. Muestra la sección con la información de la tabla actualizada. Aplica la **(RNA244)**.|
||11. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|12. Selecciona la opción **“Aceptar”**.|13. Cierra el mensaje.|
||14. Continúa en el paso [**4**](#_ref166747525) del Flujo primario.|

|<p></p><p><a name="fa14"></a>**FA14 Selecciona la opción “Informes documentales periódicos”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA14** inicia cuando el Empleado SAT selecciona la sección **“Informes documentales periódicos”**.|<p>2. Consulta en la BD y obtiene la información de la sección “Informes documentales periódicos” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id</p><p>- Informe documental</p><p>- Periodicidad</p><p>- Penas y/o deducciones aplicables</p><p>- Descripción</p><p></p>|
||<p>3. <a name="_ref164504284"></a>Despliega la sección y presenta la información obtenida de la sección “Informes documentales periódicos” en los siguientes campos:</p><p>Tabla Informes documentales periódicos. Aplica la **(RNA244)**:</p><p>- Id</p><p>- Informe documental</p><p>- Periodicidad</p><p>- Penas y/o deducciones aplicables</p><p>- Descripción</p><p>- ` `Acciones</p><p>- Editar ![ref6]</p><p>- Eliminar ![ref7]</p><p></p><p>Opciones:</p><p>- Nuevo ![ref4]</p><p>- Descargar Excel![ref8]</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 09.</p>|
|<p>4. <a name="_ref165643904"></a>Selecciona la opción **“Nuevo”** y continua en el flujo.</p><p></p><p>- Si selecciona la opción **“Descargar a Excel”**, continúa en el **([**FA02**](#fa02))**. </p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA18**](#fa18))**.</p><p>- Si selecciona la opción **“Eliminar”**,** continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Guardar”** continúa en el paso [**7**](#_ref168514160) de este flujo.</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p>|<p>5. Inserta un registro en la tabla de la sección “Informes documentales periódicos” en modo edición. Aplica la **(RNA250)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p>|
|6. <a name="_ref164505282"></a>Captura los datos de la sección **“Informes documentales periódicos”** y** regresa al paso [**4**](#_ref165643904) del presente flujo.|<p>7. <a name="_ref168514160"></a>Valida que se hayan ingresado todos los datos obligatorios, conforme a las **(RNA03) y (RNA198)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>8. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos- Informes documentales periódicos</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>- Id informe periódico</p><p></p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||9. Almacena en la BD los datos de la tabla. Aplica la **(RNA247)**.|
||10. Muestra la sección con la información de la tabla actualizada. Aplica la **(RNA244)**.|
||11. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|12. Selecciona la opción **“Aceptar”**.|13. Cierra el mensaje.|
||14. Continúa en el paso [**4**](#_ref166747525) del Flujo primario.|

|<p></p><p><a name="fa15"></a>**FA15** **Selecciona la opción “Informes documentales de los servicios”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA15** inicia cuando el Empleado SAT selecciona la sección **“Informes documentales de los servicios”**.|<p>2. Consulta en la BD y obtiene la información de la sección “Informes documentales de los servicios” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id</p><p>- Informe documental</p><p>- Periodicidad</p><p>- Fecha de entrega</p><p>- Penas y/o deducciones aplicables</p><p>- Descripción</p>|
||<p>3. <a name="_ref164504307"></a>Despliega la sección y presenta la información obtenida de la sección “Informes documentales de los servicios” en los siguientes campos:</p><p>&emsp;</p><p>Tabla “Informes documentales de los servicios”. Aplica la **(RNA244)**.:</p><p>- Id</p><p>- Informe documental</p><p>- Periodicidad</p><p>- Fecha de entrega</p><p>- Penas y/o deducciones aplicables</p><p>- Descripción</p><p>- Acciones</p><p>- Editar![ref6]</p><p>- Eliminar![ref7]</p><p></p><p>Opciones:</p><p>- Descargar a Excel ![](Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.012.png)</p><p>- Nuevo![ref4]</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 10.</p>|
|<p>4. <a name="_ref165643921"></a>Selecciona la opción **“Nuevo”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si selecciona la opción **“Descargar a Excel”**, continúa en el **([**FA02**](#fa02))**.</p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA18**](#fa18))**.</p><p>- Si selecciona la opción **“Eliminar”**,** continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Guardar”** continúa en el paso [**7**](#_ref168514309) de este flujo.</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p>|<p>5. Inserta un registro en la tabla de la sección “Informes documentales de los servicios” en modo edición. Aplica la **(RNA250)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p>|
|6. <a name="_ref164505307"></a>Captura los datos de **“Informes documentales de los servicios”** y** regresa al paso [**4**](#_ref165643921) del presente flujo.|<p>7. <a name="_ref168514309"></a>Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA201)**.</p><p>&emsp; </p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>8. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos- Informes documentales de los servicios</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>- Id del informe documental del servicio</p><p>&emsp;</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||9. Almacena en la BD los datos de la tabla. Aplica la **(RNA247)**.|
||10. Muestra la sección con la información de la tabla actualizada. Aplica la **(RNA244)**.|
||11. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|12. Selecciona la opción **“Aceptar”**.|13. Cierra el mensaje.|
||14. Continúa en el paso [**4**](#_ref166747525) del Flujo primario.|

|<p></p><p><a name="fa16"></a>**FA16 Selecciona la opción “Niveles de servicio (SLA)”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA16** inicia cuando el Empleado SAT selecciona la sección **“Niveles de servicio (SLA)”**.|<p>2. Consulta en la BD y obtiene la información de la sección “Niveles de servicio (SLA)” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id</p><p>- SLA</p><p>- Deducciones aplicables</p><p>- Objetivo mínimo</p><p>- Descripción</p>|
||<p>3. <a name="_ref164504331"></a>Despliega la sección y presenta la información obtenida de la sección “Niveles de servicio (SLA)” en los siguientes campos de acuerdo con la **(RNA202)**:</p><p>&emsp;</p><p>Tabla “Niveles de servicio (SLA)”. Aplica la **(RNA244)**.</p><p>- Id</p><p>- SLA</p><p>- Deducciones aplicables</p><p>- Objetivo mínimo</p><p>- Descripción</p><p>- Acciones</p><p>- Editar ![ref6]</p><p>- Eliminar ![ref7]</p><p></p><p>Opciones:</p><p>- Descargar Excel![ref8]</p><p>- Nuevo ![ref4]</p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 12.</p>|
|<p>4. <a name="_ref165643950"></a>Selecciona la opción **“Nuevo”** y continúa en el flujo.</p><p></p><p>- Si selecciona la opción **“Descargar a Excel”**, continúa en el **([**FA02**](#fa02))**.</p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA18**](#fa18))**.</p><p>- Si selecciona la opción **“Eliminar”**,** continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Guardar”** continúa en el paso [**7**](#_ref168516090) de este flujo.</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p>|<p>5. Inserta un registro en la tabla de la sección “Niveles de servicio (SLA)” en modo edición. Aplica la **(RNA250)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p>|
|6. <a name="_ref164505324"></a>Captura los datos de la sección **“Niveles de servicio (SLA)”** y** regresa al paso [**4**](#_ref165643950) del presente flujo.|<p>7. <a name="_ref168516090"></a>Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA202)**.</p><p>&emsp;</p><p>Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>8. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**=** Contratos- Niveles de servicio (SLA)</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de contrato</p><p>- Id Nivel de servicio (SLA)</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||9. Almacena en la BD los datos de la tabla. Aplica la **(RNA247)**.|
||10. Muestra la sección con la información de la tabla actualizada. Aplica la **(RNA244)**.|
||11. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|12. Selecciona la opción **“Aceptar”**.|13. Cierra el mensaje.|
||14. Continúa en el paso [**4**](#_ref166747525) del Flujo primario.|

|<p></p><p></p><p></p><p><a name="fa17"></a>**FA17 Selecciona la opción “Asignación de plantilla”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA17** inicia cuando el Empleado SAT selecciona la sección **“Asignación de plantilla”**.|2. Consulta en la BD las plantillas asociadas a la fase de “Ejecución”.|
||<p>3. Consulta en la BD y obtiene la información de la sección “Asignación de plantilla” que se encuentre almacenada.</p><p>&emsp;</p><p>- Asignar plantilla</p>|
||<p>4. Despliega la sección y presenta la información obtenida de “Asignación de plantilla” en los siguientes campos:</p><p>&emsp;</p><p>- Asignar plantilla\*</p><p></p><p>Opciones</p><p>- Nuevo![ref4]</p><p>- Eliminar ![ref3]</p><p>- Cancelar</p><p>- Guardar</p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 13.</p>|
|<p>5. Selecciona la opción **“Nuevo”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si requiere modificar alguno de los datos almacenados, continúa en el paso [**7**](#_ref168326138).</p>|6. Muestra una lista de selección nueva para el campo “Asignar plantilla”.|
|<p>7. <a name="_ref168326138"></a>Captura. los datos de **“Asignación de plantilla”**.</p><p>&emsp;</p><p>- Asignar plantilla</p>||
|<p>8. Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p><p>- Si selecciona la opción **“Eliminar”**, para un registro ya almacenado, continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Eliminar”**, para un registro nuevo, continúa en el **([**FA05**](#fa05))**.</p>|<p>9. Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA03)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>10. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contratos - Asignación de plantilla </p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>-	Id de contrato</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA06**\](#fa06)](#fa12))**.</p>|
||11. Se almacenan en la BD los datos que hayan sido actualizados. Aplica la **(RNA247)**.|
||12. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|13. Selecciona la opción **“Aceptar”**.|14. Cierra el mensaje.|
||15. Continúa en el paso [**4**](#_ref166747525)** del Flujo primario.|

|<p></p><p><a name="fa18"></a>**FA18 Selecciona la opción “Editar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA18** inicia cuando el Empleado SAT selecciona la opción **“Editar”**.|<p>2. Cambia la propiedad de los campos de la tabla a edición. </p><p></p>|
||<p>3. Cambia las acciones de la tabla por las siguientes. </p><p>&emsp;</p><p>- Descartar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p>|
|<p>4. Modifica los datos y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Descartar”** del registro seleccionado continúa en el **([**FA05**](#fa05))**.</p>|5. Regresa al punto de acción donde fue invocado.|

|<p></p><p><a name="fa19"></a>**FA19 Selecciona la Opción “Eliminar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA19** inicia cuando el Empleado SAT selecciona la opción **“Eliminar”**.|2. El sistema mostrará el **([\[**MSG017**\](#msg017)](#msg002))** con las opciones** “Sí” y “No”.|
|<p>3. Si selecciona la opción **“No”**, continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, continúa en el paso [**5**](#_ref167960678) de este flujo.</p>|4. Cierra el mensaje y continúa en el flujo de la sección que lo invocó.|
||<p>5. <a name="_ref167960678"></a>Valida que el registro no esté relacionado con otro módulo conforme a la **(RNA249)**, y continúa en el paso [**9**](#_ref167960699) de este flujo.</p><p>&emsp;</p><p>- Si el registro está relacionado con otro módulo continúa en el flujo.</p>|
||6. Muestra el **([**MSG011**](#msg011))** con la opción “Aceptar”.|
|7. Selecciona la opción **“Aceptar”**.|8. Cierra el mensaje y continúa en la sección donde fue invocado.|
||9. <a name="_ref167960699"></a>El sistema borra el registro seleccionado de la tabla de la sección que fue invocado. Aplica la **(RNA250)**.|
||10. Regresa al punto de acción donde fue invocado.|

|<p></p><p><a name="fa20"></a>**FA20 Se identifica que existen Estimaciones no canceladas**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA20** inicia cuando el sistema detecta que existen estimaciones no canceladas.|
||2. Muestra el **([**MSG005**](#msg005))**,** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”.**|4. Cierra el mensaje.|
||<p>5. Regresa al paso [**4**](#_ref166747525) del Flujo primario.</p><p></p>|

|<p></p><p></p><p><a name="fa21"></a>**FA21 Selecciona la opción “Nuevo” en la tabla “Participantes en la administración del contrato”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA21** inicia cuando el Empleado SAT selecciona la opción **“Nuevo”** de la tabla **“Participantes en la administración del contrato”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si selecciona la opción **“Descargar a Excel”**, continúa en el **([**FA02**](#fa02))**. </p><p>- Si selecciona la opción **“Editar”**, continúa en el **([**FA18**](#fa18))**.</p><p>- Si selecciona la opción **“Eliminar”**,** continúa en el **([**FA19**](#fa19))**.</p><p>- Si selecciona la opción **“Guardar”** continúa en el paso [**5**](#_ref168504224) de este flujo.</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA05**](#fa05))**.</p>|<p>2. Inserta un registro en la tabla de la sección “Participantes en la administración del contrato” en modo edición. Aplica la **(RNA250)**.</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ModificarContrato)** Estilos 19.</p>|
|<p>3. <a name="_ref164503241"></a>Captura la información de la tabla **“Participantes en la administración del contrato”** y continúa en el flujo. </p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Vigente”**,** continúa en el **([**FA03**](#fa03))**.</p>||
|<p>4. Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Cancelar”**, continúa en el **([**FA05**](#fa05))**.</p>|<p>5. <a name="_ref168504224"></a>Valida que se hayan ingresado los datos obligatorios conforme a la **(RNA193)**.</p><p>&emsp;</p><p>En caso de que no se hayan ingresado los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>6. Almacena en la BD la información de las Pistas de Auditoría. </p><p>&emsp;  </p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Contratos-Datos generales-Participantes en la administración del contrato</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS </p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id contrato </p><p>- Id del participante en la administración del contrato </p><p>  </p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA06**](#fa06))**. </p>|
||7. Almacena en la BD los datos de la tabla. Aplica la **(RNA247)**.|
||8. Muestra la sección con la información de la tabla actualizada. Aplica la **(RNA244)**.|
||9. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|10. Selecciona la opción **“Aceptar”**.|11. Cierra el mensaje.|
||12. Continúa en el paso [**4**](#_ref166747525) del Flujo primario.|

|<p></p><p><a name="fa22"></a>**FA22 Selecciona la opción Convenios modificatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA22** inicia cuando el Empleado SAT selecciona la sección “**Convenios modificatorios”**.|<p>2. Consulta en la BD y obtiene la información de la sección “Convenios modificatorios” que se encuentre almacenada.</p><p>&emsp;</p><p>- Número de convenio</p><p>- Tipo</p><p>- Fecha de firma</p><p>- Fecha fin</p><p>- Monto máximo</p><p></p>|
||<p>3. <a name="_ref165644080"></a>Despliega la sección “Convenios modificatorios” y presenta la información obtenida en los siguientes campos:</p><p>&emsp;</p><p>Opciones:</p><p>- Nuevo![ref2]</p><p>- Exportar a Excel ![ref9]</p><p>&emsp;</p><p>Tabla “Convenios modificatorios”. Aplica la **(RNA244)**:</p><p>- Número de convenio</p><p>- Tipo</p><p>- Fecha de firma</p><p>- Fecha fin</p><p>- Monto máximo</p><p>- Acciones</p><p>&emsp;- Editar ![ref10]</p><p>&emsp;- Ver detalle ![](Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.015.png)</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarProyecto)** Estilos 14.</p>|
|<p><a name="_ref165643975"></a>Selecciona alguna de las siguientes opciones:</p><p></p><p>- Si selecciona la opción **“Nuevo”**,** continúa en el flujo “**17\_3083\_ECU\_RegistroDeConvenioModificatorio”**. </p><p>- Si selecciona la opción **“Exportar a Excel”**, continúa en el **([**FA02**](#fa02))**. </p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p>- Si selecciona la opción **“Editar”,** continúa en el flujo **“17\_3083\_ECU\_EditarConvenioModificatorio”**. </p><p>- Si selecciona la opción “**Ver detalle”** continúa en el flujo “**17\_3083\_ECU\_EditarConvenioModificatorio”**.</p>|4. Fin de Caso de Uso.|

|<p></p><p></p><p><a name="fa23"></a>**FA23 Selecciona la opción Dictámenes asociados**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA23** inicia cuando el Empleado SAT selecciona la sección “**Dictámenes asociados”**.|<p>2. Consulta la BD y obtiene la información de la sección “Dictámenes asociados” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id dictamen</p><p>- Periodo control</p><p>- Periodo inicio</p><p>- Periodo fin</p><p>- Estatus</p><p>- Monto</p><p>- Monto en pesos</p><p>- Tipo de cambio referencial</p><p></p>|
||<p>3. <a name="_ref165644109"></a>Despliega la sección “Dictámenes asociados” y presenta la información obtenida en los siguientes campos:</p><p>&emsp;Opción</p><p>- Exportar a Excel ![ref11]</p><p></p><p>Tabla “Dictámenes asociados”. Aplica la **(RNA244)**:</p><p>- Id dictamen</p><p>- Periodo control</p><p>- Periodo inicio</p><p>- Periodo fin</p><p>- Estatus</p><p>- Monto</p><p>- Monto en pesos</p><p>- Tipo de cambio referencial. Aplica **(RNA235)**.</p><p>Ver **(17\_3083\_EIU\_ModificarProyecto)** Estilos 17.</p><p></p>|
|<p>4. <a name="_ref165643995"></a>Si selecciona la opción **“Exportar a Excel”**, continúa en el **([**FA02**](#fa02))**.</p><p>&emsp;</p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p></p>|5. Fin de Caso de Uso.|

|<p></p><p></p><p><a name="fa24"></a>**FA24 Selecciona la opción Facturas asociadas**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA24** inicia cuando el Empleado SAT selecciona la sección “**Facturas asociadas”**.|<p>2. Consulta la BD y obtiene la información de la sección “Facturas asociadas” que se encuentre almacenada.</p><p>&emsp;</p><p>- Id dictamen</p><p>- Comprobante fiscal</p><p>- Convenio de colaboración</p><p>- Monto</p><p>- Monto en pesos</p><p>- Estatus</p><p>- Tipo de cambio</p>|
||<p>3. <a name="_ref165644127"></a>Despliega la sección “Facturas asociadas” y presenta la información obtenida en los siguientes campos:</p><p>&emsp;</p><p>Opción:</p><p>- Exportar a Excel ![ref11]</p><p></p><p>Tabla “Facturas asociadas” Aplica la **(RNA244)**.:</p><p>- Id dictamen</p><p>- Comprobante fiscal</p><p>- Convenio de colaboración</p><p>- Monto</p><p>- Monto en pesos</p><p>- Estatus</p><p>- Tipo de cambio. Aplica **(RNA234)**.</p><p>Ver **(17\_3083\_EIU\_ModificarProyecto)** Estilos 15.</p><p></p>|
|<p>4. <a name="_ref165644015"></a>Si selecciona la opción **“Exportar a Excel”**, continúa en el **([**FA02**](#fa02))**. </p><p></p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p></p>|5. Fin de Caso de Uso.|

|<p></p><p></p><p><a name="fa25"></a>**FA25 Selecciona la opción Reintegros asociados**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA25** inicia cuando el Empleado SAT selecciona la sección “**Reintegros asociados”**.|<p>2. Consulta la BD y obtiene la información de la sección “Reintegros asociados” que se encuentre almacenada.</p><p>&emsp;</p><p>- No.</p><p>- Tipo</p><p>- Importe</p><p>- Interés</p><p>- Total</p><p>- Fecha de reintegro</p><p>- ![ref12]Importes</p><p>- ![ref13]Intereses</p><p>- ![ref14]Totales</p><p></p>|
||<p>3. <a name="_ref165644159"></a>Despliega la sección “Reintegros asociados” y presenta la información obtenida en los siguientes campos:</p><p>Opción:</p><p>- ` `Exportar a Excel ![ref11]</p><p></p><p>Tabla “Reintegros asociados”. Aplica la **(RNA244)**.</p><p>- No.</p><p>- Tipo</p><p>- Importe</p><p>- Interés</p><p>- Total</p><p>- Fecha de reintegro</p><p></p><p>Campos. Aplica la **(RNA61)**</p><p>- ![ref12]Importes</p><p>- ![ref13]Intereses</p><p>- ![ref14]Totales</p><p>Ver **(17\_3083\_EIU\_ModificarProyecto)** Estilos 16.</p>|
|<p>4. <a name="_ref165644038"></a>Si selecciona la opción **“Exportar a Excel”**, continúa en el **([**FA02**](#fa02))**. </p><p>&emsp;</p><p>- En caso de que requiera **“Filtrar”** por alguna de las columnas de la tabla, continúa en el **([**FA37**](#fa37))**.</p><p></p>|5. Fin de Caso de Uso.|

|<p></p><p><a name="fa26"></a>**FA26 Selecciona la opción Cierre**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA26** inicia cuando el Empleado SAT selecciona la sección **“Cierre”**.|<p>2. Despliega la pantalla de la sección “Cierre”:</p><p>&emsp;</p><p>- Cargar acta de cierre</p><p></p><p>Opciones</p><p>- Examinar</p><p>- Guardar</p><p>- Cierre</p><p>&emsp;</p><p>Ver **(17\_3083\_EIU\_ModificarProyecto)** Estilos 18.</p>|
|3. ` `<a name="_ref168508574"></a>Selecciona la opción **“Examinar”** de la sección **“Cargar acta de cierre”**. |4. Abre el gestor de archivos del equipo de cómputo del Empleado SAT. |
|5. ` `Selecciona el archivo a cargar. ||
|6. Selecciona la opción **“Guardar”**.|7. Carga el archivo que se seleccionó anteriormente. |
|8. Selecciona la opción **“Cierre”**.|<p>9. Valida que se haya cargado el acta de cierre.</p><p>&emsp;</p><p>- En caso de que no se haya cargado el acta de cierre, continúa en el **([**FA32**](#fa32))**.</p>|
||<p>10. Valida que no existan Estimaciones asociadas no canceladas.</p><p>&emsp;</p><p>- En caso de que haya estimaciones asociadas no canceladas, continúa en el **([**FA20**](#fa20))**.</p>|
||<p>11. Valida que todos los dictámenes asociados tengan estatus “Pagado” o “Cancelado”.</p><p>&emsp;</p><p>- En caso de que haya dictámenes asociados que tengan estatus diferente a “Pagado” o “Cancelado”, continúa en el **([**FA33**](#fa33))**.</p>|
||12. Cambia el estatus del contrato a “Cerrado”.|
||13. Cambia todas las secciones del contrato a modo solo lectura.|
||14. Continúa en el paso [**2**](#_ref168321592) del Flujo primario.|

|<p></p><p></p><p><a name="fa27"></a>**FA27 Selecciona la opción “Nuevo proveedor” o “Eliminar proveedor”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA27** inicia cuando el Empleado SAT selecciona la sección **“Nuevo”**.</p><p></p><p>- Si selecciona la opción **“Eliminar proveedor”**, continúa en el **([**FA19**](#fa19))**. </p>|2. Consulta en la BD y obtiene la información del catálogo de Proveedores. |
||3. Muestra una lista de selección para el campo Proveedor. Aplica la **(RNA001)**.|
|4. Selecciona al proveedor.|5. Obtiene de la BD el RFC del proveedor seleccionado y lo coloca en pantalla.|
||6. Regresa al paso de acción en el cual fue invocado.|

|<p></p><p><a name="fa28"></a>**FA28 Selecciona la opción “Validar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA28** inicia cuando se selecciona la opción **“Validar”**.|<p>2. Se valida que la sumatoria de los campos Monto máximo de los campos con “Tipo de consumo” sea igual a “Monto máximo sin impuestos” de la sección “Vigencia y montos”, si existen registros con “Tipo de consumo” igual a “Bolsa”, solo se tomará en consideración para la sumatoria el primer registro de cada grupo de este tipo de consumo y continúa en el paso [**3**](#_ref168506923) de este flujo.</p><p>&emsp;</p><p>- Si las sumas no coinciden, continúa en el paso [**4**](#_ref168506938) de este flujo.</p><p></p>|
||3. <a name="_ref168506923"></a>Muestra el **([**MSG020**](#msg020))** con la opción “Aceptar” y continúa en el paso 5 de este flujo.|
||4. <a name="_ref168506938"></a>Muestra el **([**MSG009**](#msg009))** con la opción “Aceptar” y continúa en el paso 5 de este flujo.|
|5. Selecciona la opción Aceptar.|6. Regresa al paso [**5**](#_ref165643818) del **([**FA10**](#fa10))**.|

|<p></p><p></p><p><a name="fa29"></a>**FA29 Selecciona la opción Cargar *layout* de los informes**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref164505634"></a>El **FA29** inicia cuando el Empleado SAT selecciona la sección **“Cargar *layout* de los informes”**.|<p>2. Obtiene de la BD los datos almacenados del catálogo Sección a cargar para mostrarlos en la lista de selección.</p><p>&emsp;</p><p>&emsp;- Sección a cargar</p>|
||<p>3. <a name="_ref167360288"></a><a name="_ref168508230"></a>Despliega la sección “Cargar *layout* de los informes”.</p><p>&emsp;</p><p>Opciones:</p><p>- Cargar informes</p><p>- Examinar</p><p>- Sección a cargar</p><p>- Cargar</p><p></p><p>Ver **(17\_3083\_EIU\_ModificarProyecto)** Estilos 11.</p>|
|4. Selecciona la opción **“Examinar”**.|5. Abre el gestor de archivos del equipo de cómputo del Empleado SAT. |
|6. Selecciona el archivo deseado.|7. Muestra el nombre del archivo seleccionado en el campo “Cargar informes”.|
|8. Selecciona una opción para el campo **“Sección a cargar”**.||
|9. Selecciona la opción **“Cargar”**.|<p>10. Lee los datos del archivo de carga y los prepara para almacenarlos en el campo correspondiente.</p><p>&emsp;Nota: el archivo no se almacena.</p>|
||<p>11. <a name="_ref164507886"></a>Valida si existen datos previamente guardados.</p><p>&emsp;</p><p>- En caso de no existir datos en la sección seleccionada, el flujo continúa.</p><p>- En caso de existir datos previamente cargados en la sección seleccionada, continúa en el **([**FA30**](#fa30))**.</p>|
||<p>12. <a name="_ref167361570"></a>Valida que la estructura del archivo seleccionado contenga los datos respecto a la sección, conforme a lo siguiente:</p><p></p><p>- Sección “Atraso en el inicio de la prestación” conforme a la **(RNA197)**.</p><p>- Sección “Informes documentales por única vez” conforme a la **(RNA199)**.</p><p>- Sección “Informes documentales periódicos” conforme a la **(RNA200)**.</p><p>- Sección “Informes de los servicios” conforme a la **(RNA201)**.</p><p>- Sección “Niveles de servicio (SLA)” conforme a la **(RNA202)**.</p><p>- Si la validación es correcta continúa con el flujo.</p><p>- Si el *layout* seleccionado no contiene la estructura seleccionada continúa en el **([**FA31**](#fa31))**.</p>|
||<p>13. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contratos-Sección a cargar seleccionada</p><p>Ejemplo:</p><p>Contratos-Atraso en el inicio de la prestación </p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar) según corresponda.</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id contrato</p><p>- Carga masiva (valor fijo)</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA06**](#fa06))**.</p>|
||<p>14. Almacena en la BD la información del archivo conforme a lo siguiente. Aplica la **(RNA247)**:</p><p>&emsp;</p><p>- Sección “Atraso en el inicio de la prestación” conforme a la **(RNA197)**.</p><p>- Sección “Informes documentales por única vez” conforme a la **(RNA199)**.</p><p>- Sección “Informes documentales periódicos” conforme a la **(RNA200)**.</p><p>- Sección “Informes de los servicios” conforme a la **(RNA201)**.</p><p>- Sección “Niveles de servicio (SLA)” conforme a la **(RNA202)**.</p><p></p>|
||15. Muestra el **([**MSG015**](#msg015))**, con la opción “Aceptar”.|
|16. Selecciona la opción **“Aceptar”**.|17. Cierra el mensaje y regresa al paso [**4**](#_ref166747525) del Flujo principal.|

|<p></p><p></p><p></p><p><a name="fa30"></a>**FA30 Existen datos previamente cargados para la sección a cargar**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA30** inicia cuando existen datos previamente cargados en la sección elegida.|
||<p>2. Valida si algún registro no está relacionado con otro módulo conforme a la **(RNA249)**, y** continúa en el paso 5 del presente flujo.</p><p>&emsp;</p><p>- Si algún registro está relacionado a otro módulo muestra el **([**MSG021**](#msg021))** con la opción “Aceptar”.</p><p></p>|
|3. Selecciona la opción **“Aceptar”** y continúa en el paso [**6**](#_ref168517484) de este flujo.|<p>4. Muestra el **([**MSG014**](#msg014))** con las opciones “Sí” y “No”.</p><p>&emsp;</p>|
|<p>5. Selecciona una opción.</p><p></p><p>- Si selecciona la opción **“No”**, el flujo continúa.</p><p>- Si selecciona la opción **“Sí”**, continúa en el paso [**12**](#_ref167361570) del **([**FA29**](#fa29))**.</p>|6. <a name="_ref168517484"></a>Cierra el mensaje, y continúa en el paso [**3**](#_ref168508230) del **([**FA29**](#fa29))**.|

|<p></p><p><a name="fa31"></a>**FA31** **La estructura del archivo no es la misma de la sección seleccionada**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA31** inicia cuando la estructura del Archivo no es la misma de la sección seleccionada.|
||2. Muestra el **([**MSG016**](#msg016))**,** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”.**|4. Cierra el mensaje.|
||<p>5. Continúa en el paso [**3**](#_ref167360288) del **([**FA29**](#fa29))**.</p><p></p>|

|<p></p><p><a name="fa32"></a>**FA32 No se ha cargado el acta de cierre**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA32** inicia cuando se detecta que no se ha cargado el acta de cierre.|
||2. Muestra el **([**MSG018**](#msg018))**,** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||<p>5. Regresa al paso [**3**](#_ref168508574) del **([**FA26**](#fa26))**.</p><p></p>|

|<p></p><p><a name="fa33"></a>**FA33 Hay dictámenes asociados con “Estatus” diferente a “Pagado”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA33** inicia cuando se detecta que existen dictámenes con estatus diferente de “Pagado”.|
||2. Muestra el **([**MSG019**](#msg019))**,** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”.**|4. Cierra el mensaje.|
||<p>5. Regresar al paso [**4**](#_ref166747525)** del Flujo primario.</p><p></p>|

|<p></p><p><a name="fa34"></a>**FA34 Selecciona la opción “Cancelar contrato”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref166749485"></a>El **FA34** inicia cuando el Empleado SAT selecciona la opción **“Cancelar contrato”**.|<p>2. Valida que no existan Estimaciones asociadas no canceladas.</p><p>&emsp;</p><p>- En caso de que haya estimaciones asociados no canceladas, continúa en el **([**FA20**](#fa20))**.</p>|
||<p>3. Valida que no tenga dictámenes asociados o estos estén cancelados.</p><p>&emsp;</p><p>- En caso de que tenga dictámenes asociados que no estén cancelados, continúa en el **([**FA33**](#fa33))**.</p>|
||4. Cambia el estatus del contrato a “Cancelado”.|
||5. Cambia todas las secciones del contrato a modo solo lectura.|
||6. Fin del Caso de Uso.|

|<p></p><p></p><p><a name="fa35"></a>**FA35 No se ingresó valor para Fecha de término**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA35** inicia cuando se identifica que no se ingresó una “Fecha de término” al seleccionar el campo “Vigente”.|
||2. Muestra el **([**MSG013**](#msg013))**, con la opción “Aceptar”.|
|3. Selecciona **“Aceptar”**.|4. Cierra el mensaje.|
||5. Regresa al paso **[**1**](#_ref165305667)** del **([**FA03**](#fa03))**.|

|<p></p><p>**FA36 El contrato tiene Dictámenes asociados no cancelados**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA36** inicia cuando se identifica que el contrato tiene dictámenes asociados no cancelados.|
||2. Muestra el **([**MSG007**](#msg007))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Regresa al paso [**4**](#_ref166747525)** del Flujo principal.|

|<p></p><p><a name="fa37"></a>**FA37 Se requiere filtrar por alguna de las columnas de la tabla**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA37** inicia cuando el Empleado SAT requiere **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.| |
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
| |4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
| |<p>5. Realiza lo siguiente:</p><p></p><p>- Regresa al paso de la sección de la tabla donde fue invocado</p>|

|<p></p><p><a name="fa38"></a>**FA38 Selecciona la opción Ejecución**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA38** inicia cuando el Empleado SAT cambia el estatus del contrato a **“Ejecución”**.|<p>2. Valida que el estatus del contrato sea “Inicial”, conforme a la **(RNA75)**.</p><p>&emsp;</p><p>- Si el estatus del contrato es “Inicial”, muestra el **([**MSG024**](#msg024))** con las opciones “Si” y “No”.</p><p></p>|
|<p>3. Selecciona la opción **“Si**” y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“No”** regresa al paso [**4**](#_ref167869609) del **([**FA01**](#fa01))**.</p><p></p>|4. Cierra el mensaje.|
||<p>5. Valida que se hayan ingresado todos los datos obligatorios de la sección “Vigencia y montos”, conforme a la **(RNA03)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>6. Valida que la sección “Registro de servicios” cuente al menos con un servicio registrado y continúa en el paso [**9**](#_ref168517920) de este flujo.</p><p></p><p>- En caso de no contar con ningún servicio registrado, muestra el **([**MSG023**](#msg023))**, con la opción “Aceptar”, y continúa en el flujo.</p><p></p>|
|7. Selecciona la opción **“Aceptar”**.|8. Cierra el mensaje.|
||9. <a name="_ref168517920"></a>Regresa al paso [**4**](#_ref167869609) del **([**FA01**](#fa01))**.|

|<p></p><p></p><p><a name="fa39"></a>**FA39 Selecciona la opción Inicial**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA39** inicia cuando el Empleado SAT cambia el estatus del contrato a **“Inicial”**.|<p>2. Valida que el estatus del contrato sea de nueva creación, conforme a la **(RNA75)**.</p><p>&emsp;</p><p>- Si el estatus del contrato es “Cancelado”, “Ejecución” o “Cierre” muestra el **([**MSG025**](#msg025))** con las opciones “Si” y “No”.</p><p></p>|
|<p>3. Selecciona la opción **“Si**” y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“No”** regresa al paso [**4**](#_ref167869609) del **([**FA01**](#fa01))**.</p><p></p>|4. Cierra el mensaje.|
||5. Regresa al paso [**2**](#_ref168321592)** del flujo primario.|

|<p></p><p></p><p></p>|
| :- |
||
|<h3><a name="_toc165648354"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_ModificarContrato</p><p>- 17\_3083\_ECU\_GestionDocumental</p><p>- 17\_3083\_ECU\_AltaDeContrato</p><p>- 17\_3083\_ECU\_EditarConvenioModificatorio</p><p>- 17\_3083\_ECU\_RegistrarConvenioModificatorio</p><p>&emsp;</p><p>&emsp;</p><p>&emsp;</p>|
|<h3><a name="_toc165648355"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Favor de ingresar los datos obligatorios marcados con un asterisco (\*).|
|<a name="msg002"></a>**MSG002**|Al cancelar se perderán los cambios realizados. ¿Está seguro de continuar?|
|<a name="msg003"></a>**MSG003**|Precaución el registro se encuentra relacionado a otro(s) módulo(s). ¿Desea continuar?|
|<a name="msg004"></a>**MSG004**|Se guardó correctamente la información.|
|<a name="msg005"></a>**MSG005**|Para poder cancelar el contrato, no deben existir estimaciones no pagadas. Favor de verificar.|
|<a name="msg006"></a>**MSG006**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01). |
|<a name="msg007"></a>**MSG007**|No se puede cancelar el contrato porque tiene dictámenes asociados.|
|<a name="msg008"></a>**MSG008**|El estatus cambiará a “Inactivo” y no podrá ser usado en otros procesos. ¿Desea continuar?|
|<a name="msg009"></a>**MSG009**|La sumatoria de los campos “Monto máximo” de la tabla “Registro de servicios” no es igual al campo “Monto máximo en pesos con impuestos” de la sección “Vigencia y montos”. Favor de verificar los datos ingresados.|
|<a name="msg010"></a>**MSG010**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01). |
|<a name="msg011"></a>**MSG011**|` `El registro no se puede eliminar porque se encuentra relacionado en otro módulo.|
|<a name="msg012"></a>**MSG012**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01). |
|<a name="msg013"></a>**MSG013**|Si se desactiva la vigencia, es necesario ingresar una fecha de término. Verifique por favor.|
|<a name="msg014"></a>**MSG014**|Existe información previamente cargada. ¿Desea actualizarla?|
|<a name="msg015"></a>**MSG015**|La información ha sido actualizada exitosamente.|
|<a name="msg016"></a>**MSG016**|El archivo cargado no contiene la misma estructura que la “Sección a cargar” seleccionada. Favor de verificar.|
|<a name="msg017"></a>**MSG017**|Se eliminará el registro seleccionado. ¿Desea continuar?|
|<a name="msg018"></a>**MSG018**|Para poder cerrar el contrato, se tiene que cargar el acta de cierre. Favor de verificar.|
|<a name="msg019"></a>**MSG019**|Para poder cerrar el contrato, no deben existir dictámenes pendientes de pago. Favor de verificar.|
|<a name="msg020"></a>**MSG020**|El Monto máximo de los servicios coincide con el Monto máximo sin impuestos del contrato.|
|<a name="msg021"></a>**MSG021**|No se puede cargar la información del layout porque tiene dictámenes asociados.|
|<a name="msg022"></a>**MSG022**|Ocurrió un error al eliminar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg023"></a>**MSG023**|El contrato no cuenta con registros de servicio, favor de verificar.|
|<a name="msg024"></a>**MSG024**|El estatus del contrato cambiará a “Ejecución”. ¿Desea continuar?|
|<a name="msg025"></a>**MSG025**|El estatus del contrato cambiará a “Inicial”. ¿Desea continuar?|

|<p></p><p></p>|
| - |
|<h3><a name="_toc165648356"></a>**10. Requerimientos No Funcionales** </h3>|
||

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001** |Disponibilidad |El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas. |
|**RNF002** |Concurrencia |<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p> </p><p>El número máximo de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT. </p>|
|**RNF003** |Seguridad |El acceso solo podrá ser otorgado al Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema. |
|**RNF004** |Usabilidad |<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación:  </p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual como guía para el Empleado SAT.  </p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado.  </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales. </p>|
|**RNF005** |Eficiencia |Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información sólo cuando sean requeridas por el Empleado SAT. |
|**RNF006** |Usabilidad |<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100:  </p><p>  </p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta).  </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta).  </p><p>- Ir a la siguiente página (debe mostrar la siguiente página considerando la actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).  </p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual con el resultado de la consulta).  </p><p>  </p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente. </p>|
|**RNF007** |Seguridad |Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.   |
|**RNF008** |Fiabilidad |El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema. |
|**RNF009**|Seguridad|Se debe mantener la información en pantalla en caso de un error al guardar las Pistas de Auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos, ya que no están controlados por el sistema.|
|**RNF010**|Integridad |Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena.|

|<p></p><p></p><p></p>|
| :- |
|<h3>**<a name="_toc165648357"></a>11. Diagrama de actividad** 	</h3>|
|<p></p><p>![](Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.020.png)</p><p></p>|
|<h3><a name="_toc165648358"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>![](Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.021.png)</p><p></p><p></p>|


|<h3><a name="_toc165648359"></a>**13. Aprobación del cliente** </h3>|
| :- |
|<p></p><p> </p>|

|**FIRMAS DE CONFORMIDAD** ||
| :-: | :- |
|**Firma 1**  |**Firma 2**  |
|**Nombre**: María del Carmen Castillejos Cárdenas. |**Nombre**: Rubén Delgado Ramírez. |
|**Puesto**: Usuaria ACPPI. |**Puesto**: Usuario ACPPI. |
|**Fecha:** |**Fecha:** |
|  |  |
|**Firma 3**  |**Firma 4** |
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
|**Nombre**: Juan Carlos Ayuso Bautista. |**Nombre**: Angel Horacio López Alcaraz.|
|**Puesto**: Líder Técnico SDMA 6. |**Puesto**: Analista de Sistemas SDMA 6. |
|**Fecha**: |**Fecha**: |
|**  | |

||
| :- |






|||Página 6 de 56|
| :- | :-: | -: |

[ref1]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.003.png
[ref2]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.004.png
[ref3]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.005.png
[ref4]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.007.png
[ref5]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.008.png
[ref6]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.009.png
[ref7]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.010.png
[ref8]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.011.png
[ref9]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.013.png
[ref10]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.014.png
[ref11]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.016.png
[ref12]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.017.png
[ref13]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.018.png
[ref14]: Aspose.Words.3805e255-dec9-40e1-8e51-b4546b8bed4e.019.png
