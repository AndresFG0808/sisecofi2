|![](Aspose.Words.2f8f11da-50ca-4f27-bd20-10f910f9cd8f.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_AccesoSistema.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |

**<ID Requerimiento>8309**

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<p></p><p><a name="tabla_versiones"></a>Versión</p><p></p>|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Angel Horacio López Alcaraz|*27/02/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*19/04/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*04/06/2024*|


**Tabla de Contenido**

[17_3083_ECU_AccesoSistema	2](#_toc163830159)

[1. Descripción	2](#_toc163830160)

[2. Diagrama del Caso de Uso	2](#_toc163830161)

[3. Actores	2](#_toc163830162)

[4. Precondiciones	2](#_toc163830163)

[5. Post condiciones	3](#_toc163830164)

[6. Flujo primario	3](#_toc163830165)

[7. Flujos alternos	7](#_toc163830166)

[8. Referencias cruzadas	11](#_toc163830167)

[9. Mensajes	12](#_toc163830168)

[10. Requerimientos No Funcionales	12](#_toc163830169)

[11. Diagrama de actividad	14](#_toc163830170)

[12. Diagrama de estados	14](#_toc163830171)

[13. Aprobación del cliente	15](#_toc163830172)


### **<a name="_toc163830159"></a>**17\_3083\_ECU\_AccesoSistema

|<h3><a name="_toc163830160"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT ingresar al sistema SISECOFI para acceder a los módulos autorizados y realizar las operaciones correspondientes asignadas a su rol.</p><p></p>|
|<h3><a name="_toc163830161"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.2f8f11da-50ca-4f27-bd20-10f910f9cd8f.002.png)</p><p></p>|
|<h3><a name="_toc163830162"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|
|**Componente externo de acceso**|Sistema encargado de validar los datos del Empleado SAT que ingresa al portal a través de su e.firma y que proporciona la información de dicho Empleado SAT al SISECOFI.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc163830163"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT cuenta con una e.firma válida.</p><p>- El Empleado SAT cuenta con la **URL** del **SISECOFI**.</p><p>- En la base de datos (BD) de seguridad se ha creado el perfil de acceso del Empleado SAT para ingresar a las funcionalidades asignadas en el sistema.</p><p>&emsp;</p>|
|<h3><a name="_toc163830164"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT ingresó correctamente al sistema con una e.firma válida.</p><p>- El sistema consumió el servicio “Oauth” para obtener los datos del Empleado SAT que ingresó. (Nombre completo, RFC largo, Roles).</p><p>- El Empleado SAT cerró sesión en el sistema.</p><p>- El Empleado SAT ingresó a las diferentes opciones del menú de acuerdo con su rol:</p><p>- Sistema</p><p>- Proveedores</p><p>- Proyectos</p><p>- Contratos</p><p>- Consumo de Servicios</p><p>- Reintegros</p><p>- Reporte Documental</p><p>- Reporte Financiero</p><p>- Consultar Información</p><p>- Consultar documentos</p><p>&emsp;</p>|
|<h3><a name="_toc163830165"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref164168717"></a>El Caso de Uso inicia cuando el Empleado SAT ingresa a la **URL** del **SISECOFI**.|<p>2. <a name="_ref164168768"></a>Establece la conexión con el componente externo de acceso y muestra la pantalla “Acceso con e.firma”, con los siguientes campos:</p><p>&emsp;</p><p>- Acceso con e.firma:</p><p>- Certificado (.cer)\*</p><p>- Clave privada (.key)\*</p><p>- Contraseña de clave privada\*</p><p>- RFC (inhabilitado)</p><p></p><p>Opciones</p><p>- Buscar (Certificado (.cer)\*</p><p>- Buscar (Clave privada (.key)\*</p><p>- Enviar (inhabilitado)</p><p>Íconos:</p><p>- *Tooltip* ![](Aspose.Words.2f8f11da-50ca-4f27-bd20-10f910f9cd8f.003.png)</p><p></p><p></p><p>Ver **(17\_3083\_EIU\_AccesoSistema)** Estilos 01.</p>|
|3. <a name="_ref164168781"></a>Selecciona la opción **“Buscar”** del campo **“Certificado (.cer)\*”**  y continúa en el flujo.|4. Abre el gestor de archivos del equipo de cómputo del Empleado SAT.|
|5. <a name="_ref168391137"></a>Selecciona el archivo con extensión (.cer).|<p>6. Muestra en el campo “Certificado (.cer)\*” el nombre del archivo con extensión (.cer) ingresado.</p><p></p>|
||7. Muestra en el campo “RFC” el RFC asociado a la e.firma.|
|8. <a name="_ref164168835"></a>Selecciona la opción **“Buscar”** del campo **“Clave privada (.key)\*”** y continúa en el flujo.|9. Abre el gestor de archivos del equipo de cómputo del Empleado SAT.|
|10. Selecciona el archivo con extensión (.key).|<p>11. Muestra en el campo “Clave privada (.key)\*” el nombre del archivo con extensión (.key) ingresado.</p><p></p>|
|12. <a name="_ref164168815"></a>Ingresa la contraseña en el campo **“Contraseña de clave privada\*”**.|13. Habilita la opción “Enviar”.|
|14. Selecciona la opción **“Enviar”** y continúa en el flujo.|<p>15. Realiza la validación de la e.firma ingresada conforme a la **(RNA192)**.</p><p>- Si el archivo (.cer) seleccionado no corresponde a un tipo de archivo válido, continúa en el flujo alterno **([**FA01**](#fa01))**.</p><p>- Si el archivo (.key) seleccionado no corresponde a un tipo de archivo válido, continúa en el **([**FA01**](#fa01))**.</p><p>- En caso de ser incorrecta la validación de la contraseña, continúa en el **([**FA02**](#fa02))**.</p><p>- Si la e.firma no se encuentra vigente, continúa en el **([**FA08**](#fa08))**.</p><p>&emsp;</p>|
||<p>16. Realiza la validación de que el Empleado SAT cuente con los roles necesarios para acceder al sistema conforme a la **(RNA192)**.</p><p>- En caso de no contar con roles necesarios para acceder al sistema, continúa en el **([**FA03**](#fa03))**.</p>|
||<p>17. Obtiene del “Componente externo de acceso” los datos del Empleado SAT que ingresó al sistema, conforme a la **(RNA192)**.</p><p>&emsp;</p><p>- RFC Largo</p><p>- Nombre completo </p><p>- Roles asignados </p>|
||<p>18. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>**Módulo**= AccesoSistema</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consulta)</p><p>**Movimiento**= </p><p>-Menú principal</p><p></p><p>En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA04**](#fa04))**.</p><p></p>|
||<p>21 <a name="_ref164168735"></a>Muestra la pantalla principal del **SISECOFI**.</p><p>- Bienvenido “Nombre del Empleado SAT”</p><p>&emsp;</p><p>&emsp;Opciones:</p><p>- Menú principal ![ref1]</p><p>- Inicio</p><p>- Cerrar</p><p></p><p>Ver **(17\_3083\_EIU\_AccesoSistema)** Estilos 02.</p>|
|<p>22. <a name="_ref164765203"></a>Realiza una acción:</p><p>- Si selecciona la opción **“Menú principal”**, el flujo continúa.</p><p>- Si selecciona la opción **“Inicio”**, continúa en el **([**FA05**](#fa05))**.</p><p>- Si selecciona la opción **“Cerrar”**,** continúa en el **([**FA06**](#fa06))**.</p><p></p><p></p><p></p>|<p>23. <a name="_ref164765134"></a>Muestra la pantalla con cada uno de los módulos del sistema habilitados, conforme a la **(RNA203)**.</p><p>&emsp;</p><p>&emsp;</p><p>Opciones:</p><p>- Inicio</p><p>- Cerrar</p><p></p><p>Módulos: **(Solo referenciales)**</p><p>- Sistema ![ref2]</p><p>- Proveedores ![ref2]</p><p>- Proyectos ![ref2]</p><p>- Contratos ![ref2]</p><p>- Consumo de Servicios ![ref2]</p><p>- Reintegros ![ref2]</p><p>- Reporte Documental ![ref2]</p><p>- Reporte Financiero ![ref2]</p><p>- Consultar Información ![ref2]</p><p>- Consultar Documentos ![ref2]</p><p></p><p>Ver **(17\_3083\_EIU\_AccesoSistema)** Estilos 03.</p>|
|<p>24. Realiza una de las siguientes acciones:</p><p></p><p>- Si selecciona la opción **“Sistema”**, continúa en el **([**FA07**](#fa07))**.</p><p>- Si selecciona la opción **“Proveedores”**, continúa en el **(17\_3083\_ECU\_AltaDeProveedor)**.</p><p>- Si selecciona la opción **“Proyectos”**, continúa en el **(17\_3083\_ECU\_AltaProyectos)**.</p><p>- Si selecciona la opción **“Contratos”**, continúa en el **(17\_3083\_ECU\_AltaDeContrato)**.</p><p>- Si selecciona la opción: **“Consumo de Servicios”**, continúa en el **(17\_3083\_ECU\_AdministrarDevengado)**.</p><p>- Si selecciona la opción **“Reintegros”**, continúa en el **(17\_3083\_ECU\_RegistrarReintegro)**.</p><p>- Si selecciona la opción **“Reporte Documental”**, continúa en el **(17\_3083\_ECU\_GestionDocumental)**.</p><p>- Si selecciona la opción **“Reporte Financiero”**, continúa en el **(17\_3083\_ECU\_GenerarReporteFinanciero)**.</p><p>- Si selecciona la opción **“Consultar información”**, continúa en el **(17\_3083\_ECU\_ConsultarInformacion)**.</p><p>- Si selecciona la opción **“Consultar documentos”**,** continúa en el **(17\_3083\_ECU\_ConsultarDocumentos)**.</p><p>- Si selecciona la opción **“Inicio”**,** continúa en el **([**FA05**](#fa05))**.</p><p>- Si selecciona la opción **“Cerrar”**,** continúa en el **([**FA06**](#fa06))**.</p><p></p>|25. Fin del Caso de Uso.|

|<p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc163830166"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 Se ingresó un tipo de archivo inválido**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|<p></p><p></p>|1. El **FA01** inicia cuando el sistema detecta que el tipo de archivo seleccionado no corresponde a un tipo de archivo válido, conforme a la **(RNA192)**.|
||<p>2. Muestra el mensaje según corresponda:</p><p></p><p>- Si fue invocado en el campo “Certificado (.cer)\*” muestra el mensaje **([**MSG001**](#msg001))**.</p><p>&emsp;</p><p>- Si fue invocado en el campo “Clave privada (.key)\*” muestra el **([**MSG006**](#msg006))**.</p><p>&emsp;</p><p>El mensaje se muestra con la opción “Aceptar”</p>|
|3. Selecciona la opción **“Aceptar**” y el flujo continúa.|4. Cierra el mensaje.|
||<p>5. Continúa en el paso [**3**](#_ref164168781) del Flujo primario.</p><p></p>|

|<p></p><p></p><p></p><p><a name="fa02"></a>**FA02 Contraseña incorrecta**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA02** inicia cuando el sistema valida, a través del “Componente externo de acceso”, que la “Contraseña” asociada a la e.firma es incorrecta conforme a la **(RNA192)**. |
||2. Muestra el **([**MSG002**](#msg002))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”** y el flujo continúa.|4. Cierra el mensaje.|
||5. Continúa en el paso [**12**](#_ref164168815) del Flujo primario.|

|<p></p><p></p><p><a name="fa03"></a>**FA03 No se cuenta con los roles para acceder al sistema**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA03** inicia cuando el sistema valida que el Empleado SAT no cuenta con los roles necesarios para ingresar (validado a través del “Componente externo de acceso”), conforme a la **(RNA192)**.|
||2. Muestra el **([**MSG003**](#msg003))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar**” y continúa en el flujo.|4. Cierra el mensaje.|
||5. Fin del Caso de Uso.|

|<p></p><p></p><p><a name="fa04"></a>**FA04 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA04** inicia cuando interviene un evento ajeno y no se pueden almacenar las Pistas de Auditoría.|
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje de acuerdo con lo siguiente:</p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST,** se muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar**” y el flujo continúa.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría.|

|<p></p><p></p><p><a name="fa05"></a>**FA05 Selecciona la opción “Inicio”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT selecciona la opción **“Inicio”**.|<p>2. Muestra la pantalla principal del **SISECOFI** conforme a la **(RNA203)**.</p><p>&emsp;Ver **(17\_3083\_EIU\_AccesoSistema)** Estilos 02.</p>|
||3. Continúa en el paso [**22**](#_ref164765203)** del Flujo primario.|

|<p></p><p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Cerrar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT selecciona la opción **“Cerrar”**.|2. Muestra el **([**MSG005**](#msg005))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona una opción.</p><p>- Si selecciona la opción **“Sí”**, el flujo continúa.</p><p>- Si selecciona **“No”**,** regresa al paso donde fue invocado.</p>|4. Cierra el mensaje.|
||5. Continúa en el paso [**1**](#_ref164168717)** del Flujo primario.|
|<p></p><p></p>||

||
| :- |
|<p></p><p><a name="fa07"></a>**FA07 Selecciona la opción “Sistema”**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA07** inicia cuando el Empleado SAT selecciona la opción **“Sistema”**.|<p>2. Consulta en la BD y obtiene la información del módulo “Sistema” que se encuentra almacenada:</p><p></p><p>**(Solo referenciales)**</p><p>- Catálogos</p><p>- Matriz documental</p><p>- Administrador de formatos de impresión</p><p>- Usuarios</p><p>- Asignar proyectos</p><p>- Consultar Pistas de auditoría</p>|
||<p>3. Muestra el menú con los submódulos de “Sistema”. **(Solo referenciales)**</p><p></p><p>- Catálogos</p><p>- Matriz documental</p><p>- Administrador de formatos de impresión</p><p>- Usuarios</p><p>- Asignar proyectos</p><p>- Consultar pistas de auditoría</p><p></p><p>Opciones:</p><p>- Menú principal ![ref1]</p><p>- Inicio</p><p>- Cerrar</p><p></p><p></p><p>Ver **(17\_3083\_EIU\_AccesoSistema)** Estilos 04.</p>|
|<p>4. Selecciona una opción.</p><p>- Si selecciona la opción **“Catálogos”**, continúa en el **(17\_3083\_ECU\_AltaDeCatalogos)**.</p><p>- Si selecciona la opción **“Matriz documental”**, continúa en el **(17\_3083\_ECU\_ConfigurarControlDocumentos)**.</p><p>- Si selecciona la opción **“Administrador de Formatos Impresión”**, continúa en el **(17\_3083\_ECU\_AdministradorDeFormatosImpresion)**</p><p>- Si selecciona la opción **“Usuarios”**, continúa en el **(17\_3083\_ECU\_AdministrarUsuariosDelSistema)**.</p><p>- Si selecciona la opción **“Asignar Proyectos”**, continúa en el **(17\_3083\_ECU\_AsignarProyectos)**.</p><p>- Si selecciona **“Consultar pistas de auditoría”**, continúa en el **(17\_3083\_ECU\_ConsultarPistasAuditoria)**.</p>|5. Fin del Caso de Uso.|

||
| :- |
|<p></p><p></p><p><a name="fa08"></a>**FA08 La e.firma no se encuentra vigente**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA08** inicia cuando el sistema identifica, a través del “Componente externo de acceso”, que la e.firma no se encuentra vigente conforme a la **(RNA192)**. |
||2. Muestra el **([**MSG007**](#msg007))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”** y el flujo continúa.|4. Cierra el mensaje.|
||5. Fin del Caso de Uso.|

|<p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc163830167"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_AccesoSistema</p><p>- 17\_3083\_ECU\_ConsultarPistasAuditoria</p><p>- 17\_3083\_ECU\_AdministrarUsuariosDelSistema</p><p>- 17\_3083\_ECU\_AltaDeCatalogos</p><p>- 17\_3083\_ECU\_AdministradorDeFormatosImpresion</p><p>- 17\_3083\_ECU\_ConfigurarControlDocumentos</p><p>- 17\_3083\_ECU\_AsignarProyectos</p><p>- 17\_3083\_ECU\_AltaDeProveedor</p><p>- 17\_3083\_ECU\_AltaProyecto</p><p>- 17\_3083\_ECU\_AltaDeContrato</p><p>- 17\_3083\_ECU\_RegistrarReintegro</p><p>- 17\_3083\_ECU\_AdministrarDevengado</p><p>- 17\_3083\_ECU\_ConsultarInformacion</p><p>- 17\_3083\_ECU\_ConsultarDocumentos</p><p>- 17\_3083\_ECU\_GenerarReporteFinanciero</p><p>- 17\_3083\_ECU\_ConsultarYGenerarReporteDeControl</p><p></p>|
|<h3><a name="_toc163830168"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|El Certificado seleccionado es inválido, intente de nuevo.|
|<a name="msg002"></a>**MSG002**|La contraseña de la Clave privada es incorrecta.|
|<a name="msg003"></a>**MSG003**|Acceso denegado, no se han encontrado roles asignados a su usuario.|
|<a name="msg004"></a>**MSG004**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg05"></a><a name="msg005"></a>**MSG005**|¿Desea cerrar la sesión actual?|
|<a name="msg006"></a>**MSG006**|El archivo de la Clave privada es inválido, intente de nuevo.|
|<a name="msg007"></a>**MSG007**|La e.firma ingresada no se encuentra vigente. Favor de verificar.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc163830169"></a>**10. Requerimientos No Funcionales** </h3>|
||

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001**|Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|
|**RNF002**|Concurrencia|<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p>El número máximo de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT.</p>|
|**RNF003**|Seguridad|El acceso solo podrá ser otorgado a todo Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema.|
|**RNF004**|Usabilidad|<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación: </p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual, como guía para el Empleado SAT. </p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado. </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales. </p>|
|**RNF005**|Eficiencia|Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT. |
|**RNF006**|Usabilidad|<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100: </p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta). </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta). </p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).</p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual, con el resultado de la consulta). </p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente.</p>|
|**RNF007**|Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas. |
|**RNF008**|Fiabilidad|El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema.|
|**RNF009**|Seguridad|Se debe mantener la información en pantalla en caso de un error al guardar las pistas de auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema.|

|<p></p><p></p>|
| - |
|<h3><a name="_toc163830170"></a>**11. Diagrama de actividad** </h3>|
|<p></p><p>![Diagrama, Esquemático

Descripción generada automáticamente](Aspose.Words.2f8f11da-50ca-4f27-bd20-10f910f9cd8f.006.png)</p><p></p>|
|<h3><a name="_toc163830171"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No Aplica. No se requiere para este proceso.</p><p></p><p></p>|
|<h3><a name="_toc163830172"></a>**13. Aprobación del cliente** </h3>|
||

|**FIRMAS DE CONFORMIDAD** ||
| :-: | :- |
|**Firma 1**  |**Firma 2**  |
|**Nombre**: María del Carmen Castillejos Cárdenas.|**Nombre**: Rubén Delgado Ramírez. |
|**Puesto**: Usuaria ACPPI.|**Puesto**: Usuario ACPPI.|
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
|**Firma 7**|**Firma 8** |
|**Nombre**: Luis Angel Olguin Castillo. |**Nombre**: Erick Villa Beltrán. |
|**Puesto**: Enlace ACPPI. |**Puesto**: Líder APE SDMA 6. |
|**Fecha**: |**Fecha**: |
|**  |**  |
|**Firma 9** |**Firma 10** |
|**Nombre**: Juan Carlos Ayuso Bautista. |**Nombre**: Angel Horacio López Alcaraz|
|**Puesto**: Líder Técnico SDMA 6. |**Puesto**: Analista de Sistemas SDMA 6.|
|**Fecha**: |**Fecha**: |
|**  | |

||
| :- |

|||Página 13 de 13|
| :- | :-: | -: |

[ref1]: Aspose.Words.2f8f11da-50ca-4f27-bd20-10f910f9cd8f.004.png
[ref2]: Aspose.Words.2f8f11da-50ca-4f27-bd20-10f910f9cd8f.005.png
