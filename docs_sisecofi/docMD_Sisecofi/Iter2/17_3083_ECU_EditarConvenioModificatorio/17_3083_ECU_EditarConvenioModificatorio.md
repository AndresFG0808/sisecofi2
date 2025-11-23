|![](Aspose.Words.bf6c5b44-4900-49d9-850e-76575d145695.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_EditarConvenioModificatorio.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Eric Hector Pérez Pérez|*05/04/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*03/05/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*28/05/2024*|



**Tabla de Contenido**

[17_3083_ECU_EditarConvenioModificatorio	2](#_toc165300778)

[1. Descripción	2](#_toc165300779)

[2. Diagrama del Caso de Uso	2](#_toc165300780)

[3. Actores	2](#_toc165300781)

[4. Precondiciones	2](#_toc165300782)

[5. Post condiciones	3](#_toc165300783)

[6. Flujo primario	3](#_toc165300784)

[7. Flujos alternos	6](#_toc165300785)

[8. Referencias cruzadas	19](#_toc165300786)

[9. Mensajes	19](#_toc165300787)

[10. Requerimientos No Funcionales	20](#_toc165300788)

[11. Diagrama de actividad	22](#_toc165300789)

[12. Diagrama de estados	23](#_toc165300790)

[13. Aprobación del cliente	23](#_toc165300791)






### **<a name="_toc165300778"></a>**17\_3083\_ECU\_EditarConvenioModificatorio

|<h3><a name="_toc165300779"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT editar y consultar convenios modificatorios relacionado a un contrato.</p><p></p>|
|<h3><a name="_toc165300780"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.bf6c5b44-4900-49d9-850e-76575d145695.002.png)</p><p></p>|
|<h3><a name="_toc165300781"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc165300782"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa.</p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar o editar “Convenio Modificatorio”.</p><p>- El Empleado SAT ha ingresado al módulo "Convenio Modificatorio".</p><p>- El Empleado SAT ha seleccionado un contrato y un convenio modificatorio.</p><p>&emsp;</p>|
|<h3><a name="_toc165300783"></a>**5. Post condiciones** </h3>|
|<p></p><p>El Empleado SAT: </p><p>- Registró servicios.</p><p>- Cargó y/o consultó una proyección para el convenio modificatorio.</p><p>- Descargó el documento del *layout* del "Convenio Modificatorio".</p><p>- Realizó la carga de información en la “Gestión documental”.</p><p>- Consultó información de un convenio modificatorio. </p><p></p>|
|<h3><a name="_toc165300784"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El Caso de Uso inicia cuando el Empleado SAT ingresa a la sección **“Convenio Modificatorio”** y selecciona la opción **“Editar”** del **(17\_3083\_ECU\_ModificarContrato)**.</p><p></p><p>- En caso de haber seleccionado la opción **“Ver detalle”**, aplica **(RNA167)**.</p><p></p>|<p>2. Consulta en la base de datos (BD) y obtiene la información del convenio modificatorio seleccionado:</p><p>&emsp;</p><p>- Última modificación</p><p>- Número de convenio</p><p>- Tipo de convenio</p><p>- Fecha de firma</p><p>- Fecha fin de servicio</p><p>- Fecha fin de contrato con CM</p><p>- Cálculo de días naturales</p><p>- Incremento</p><p>- Subtotal</p><p>- IVA</p><p>- Impuestos</p><p>- Tipo de cambio</p><p>- Monto máximo del contrato con CM sin impuestos</p><p>- Monto máximo del contrato con CM con impuestos</p><p>- Monto en pesos</p><p>- Comentarios</p>|
||<p>3. Muestra la pantalla “Registro de convenio modificatorio” con los siguientes criterios de registro. Aplica las reglas de negocio **(RNA163)** y **(RNA253)**.</p><p>&emsp;</p><p>Campo: </p><p>- Última modificación</p><p>&emsp;</p><p>Sección: Registro de convenio modificatorio</p><p></p><p>Campos:</p><p></p><p>- Número de convenio\*</p><p>- Tipo de convenio\*</p><p>- Fecha de firma\*</p><p>- Fecha fin de servicio\*</p><p>- Fecha fin de contrato con CM\*</p><p>- Cálculo de días naturales\*</p><p>- Incremento\*</p><p>- Subtotal\*</p><p>- IVA\*</p><p>- Impuestos\*</p><p>- Tipo de cambio\*</p><p>- Monto máximo del contrato con CM sin impuestos\*</p><p>- Monto máximo del contrato con CM con impuestos\*</p><p>- Monto en pesos\*</p><p>- Comentarios</p><p></p><p>Opciones. Aplica la **(RNA246)**:</p><p></p><p>- Cancelar</p><p>- Guardar</p><p></p><p>Secciones: </p><p></p><p>- Registro de servicios</p><p>- Proyección de convenio modificatorio</p><p>- Asignación de plantilla </p><p>- Gestión documental</p><p>&emsp;</p><p>Ver</p><p>**(17\_3083\_EIU\_EditarConvenioModificatorio)** Estilos 04.</p>|
|<p>4. <a name="_ref167872944"></a>Selecciona la sección **“Registro de servicios”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si requiere actualizar la sección **“Registro de convenio modificatorio”** continúa en el flujo alterno **([**FA07**](#fa07)[](#fa10))**.</p><p>- Selecciona la sección **“Proyección de convenio modificatorio”** continúa en el **([**FA10**](#fa10)[](#fa10))**.</p><p>- Selecciona la sección **“Asignación de plantilla”** y continúa en el **([**FA11**](#fa11))**.</p><p>- Selecciona la sección **“Gestión documental”** y continúa en el **([**FA09**](#fa09))**.</p><p>- Selecciona la opción **“Guardar”** de la sección **“Registro de convenio modificatorio”** continúa en el paso [**5**](#_ref167874669) del **([**FA07**](#fa07))**.</p><p>- Selecciona la opción **“Cancelar”** de la sección **“Registro de convenio modificatorio”** y continúa en el **([**FA03**](#fa03))**.</p>|<p>5. Consulta en la BD y obtiene la información relacionada al convenio modificatorio - “Registro de servicios” que se encuentre almacenada.</p><p></p><p>Sección Registro de servicios</p><p>Campos:</p><p>- <a name="_hlk167806357"></a>Id</p><p>- Tipo de consumo</p><p>- Concepto de servicio</p><p>- Número de servicios máximos</p><p>- Monto máximo</p><p>- Precio unitario</p><p>- Compensación (Número de servicios)</p><p>- Compensación (Monto)</p><p>- Incremento (Número de servicios)</p><p>- Incremento (Monto)</p><p>- Número total de servicios</p><p>- Monto máximo total</p><p>- Aplica IEPS</p><p></p>|
||<p>6. Muestra la pantalla “Registro de servicios” con los siguientes criterios, de acuerdo con las reglas de negocio **(RNA172)** y **(RNA165)**:</p><p>&emsp;</p><p>`      `Tabla:</p><p>- Id</p><p>- Tipo de consumo</p><p>- Concepto de servicio</p><p>- Número de servicios máximos</p><p>- Monto máximo</p><p>- Precio unitario</p><p>- Compensación (Número de servicios)</p><p>- Compensación (Monto)</p><p>- Incremento (Número de servicios)</p><p>- Incremento (Monto)</p><p>- Número total de servicios</p><p>- Monto máximo total</p><p>- Aplica IEPS</p><p>- Acciones</p><p></p><p>`       `Íconos:</p><p></p><p>- Editar![](Aspose.Words.bf6c5b44-4900-49d9-850e-76575d145695.003.png)</p><p>- Exportar a Excel ![ref1]</p><p></p><p>Botones:</p><p></p><p>- Validar </p><p>- Cancelar </p><p>- Guardar</p><p>&emsp;</p><p>Ver</p><p>**(17\_3083\_EIU\_EditarConvenioModificatorio)** Estilos 01.</p>|
|7. <a name="_ref167792038"></a>Ingresa datos en los campos habilitados.|8. Aplica **(RNA173)** para calcular los campos “Número total de servicios” y “Monto máximo total”.|
|<p>9. <a name="_ref167873856"></a>Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si selecciona la opción **“Cancelar”**,** continúa en el flujo alterno **([**FA03**](#fa03))**.</p><p>- Si selecciona la opción **“Validar”**,** continúa en el flujo alterno **([**FA13**](#fa13))**.</p><p>- Si selecciona la opción **“Exportar a Excel”**, de la tabla **“Registro de servicios”**, continúa en el **([**FA06**](#fa06)[](#fa06)[](#fa06)[](#fa06))**.</p><p>- En caso de requerir aplicar el **“Filtro”** en el campo de texto de la tabla, continúa en el **([**FA12**](#fa12))**.</p><p>- Si selecciona la opción **“Editar”**,** continúa en el **([**FA14** ](#fa14))**.</p>|<p>10. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo =** Contrato-CM-RegistrodeServicios</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar) o **UPDT** (Modificar) según corresponda</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id contrato</p><p>-Id de convenio modificatorio</p><p>- Id registro de servicio</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>11. Almacena en la BD la siguiente información del convenio modificatorio y aplica la regla **(RNA247)**.</p><p>&emsp;</p><p>- Id</p><p>- Tipo de consumo</p><p>- Concepto de servicio</p><p>- Número de servicios máximos</p><p>- Monto máximo</p><p>- Precio unitario</p><p>- Compensación (Número de servicios)</p><p>- Compensación (Monto)</p><p>- Incremento (Número de servicios)</p><p>- Incremento (Monto)</p><p>- Número total de servicios</p><p>- Monto máximo total</p><p>- Aplica IEPS</p>|
||12. Muestra el mensaje **([**MSG011**](#msg011))**,** con la opción** “Aceptar”.|
|13. Selecciona la opción **“Aceptar”**.|14. Cierra el mensaje.|
||15. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc165300785"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 La estructura del *layout* no es la misma de la opción “Descargar layout"**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA01** inicia cuando la estructura del *layout* no es la misma de la opción “Descargar layout”. |
||2. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso [**4**](#_ref167874280) del **([**FA10**](#fa10))**.|

|<p></p><p><a name="fa02"></a>**FA02 No se ingresaron los datos obligatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA02** inicia cuando el sistema identifica que no se ingresaron los datos obligatorios.|
||2. Muestra en rojo los campos pendientes de capturar.|
||<p>3. Muestra el mensaje **([**MSG001**](#msg001))**, con la opción “Aceptar”.</p><p>&emsp;</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje. |
||<p>6. Continúa con lo siguiente:</p><p></p><p>- Si se invoca en el paso 6 del **([**FA07**](#fa07))**, continua en el paso **[**3**](#_ref164503790)** del **([**FA07**](#fa07))**.</p><p>- Si se invoca en el paso 9 del **([**FA11**](#fa11))**, continua en el paso [**7**](#_ref167962932) del **([**FA11**](#fa11))**.</p>|

|<p></p><p><a name="fa03"></a>**FA03 Selecciona la opción “Cancelar” o “Descartar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA03** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”** o **“Descartar”**.|2. Muestra el **([**MSG004**](#msg004))** con** las opciones “Sí” y “No”.|
|<p>3. Selecciona la opción **“Sí”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de seleccionar **“No”**,** continúa en el paso [**6**](#_ref165014932) de este flujo.</p>|4. Cierra el mensaje.|
||5. Inicializa los campos de la pantalla en donde se selecciona la opción dejándolos sin cambios, no almacena ninguna información.|
||6. Regresa al punto de acción donde fue invocado.|

|<p></p><p><a name="fa04"></a>**FA04 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA04** inicia cuando interviene un evento ajeno y no se pueden almacenar las Pistas de Auditoría. |
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje de acuerdo con lo siguiente:</p><p></p><p>- Si la Pista de Auditoría es por el tipo de movimiento **UPDT** o **INSR**, se muestra el **([**MSG006**](#msg006))**.</p><p>&emsp;</p><p>- Si la Pista de Auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG007**](#msg007))**.</p><p></p><p>- En caso de que la Pista de Auditoría es por el tipo de movimiento **PRNT**, se muestra el **([**MSG008**](#msg008))**.</p><p>&emsp;</p><p>- Si la Pista de Auditoría es por el tipo de movimiento **DLT**, se muestra el **([**MSG017**](#msg017))**.</p><p></p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||7. Regresa al paso previo que detona la acción de la pista de auditoría. |

|<p></p><p><a name="fa05"></a>**FA05 Selecciona “Descargar layout”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT selecciona la opción **“Descargar layout”**.|<p>2. <a name="_ref165015739"></a>Construye el *layout* con base en la **(RNA169)**.</p><p></p>|
||<p>3. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contrato-CM-Proyección</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**.</p><p>- Id Convenio modificatorio</p><p>&emsp;- Id Concepto de servicio</p><p>&emsp;&emsp;</p><p>16. En caso de que no se puedan almacenar las Pista de Auditoría, continúa en el **([**FA04**](#fa04))**.</p>|
||4. Genera un archivo de Excel con extensión (.xlsx) que contenga la información obtenida.|
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||<p>6. Continúa con lo siguiente:</p><p></p><p>17. Si se invoca en el paso 5 del **([**FA10**](#fa10))**, continua en el paso [**6**](#_ref165016820) del **([**FA10**](#fa10))**.</p>|

|<p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Exportar a Excel”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref165015765"></a>El **FA06** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contrato-CM-Sección en la que fue invocado</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario** = RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p></p><p>- Id convenio modificatorio</p><p>&emsp;- Id contrato</p><p>&emsp;- Id sección en la que fue invocado</p><p></p><p>- En caso de que no se puedan almacenar las Pista de Auditoría, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>3. Obtiene la información de la sección conforme a lo siguiente:</p><p></p><p>- Si se invocó en el paso [**9**](#_ref167873856) del Flujo principal conforme a la **(RNA166)**.</p><p>- Si se invocó en el paso [**5**](#_ref165016820) del **(FA10)** conforme a la **(RNA166)**.</p>|
||4. Genera un archivo de Excel con extensión (.xlsx) que contenga la información obtenida.|
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||<p>6. Fin de Caso de Uso.</p><p>	</p>|

|<p></p><p><a name="fa07"></a>**FA07 Modificar la sección “Registro de convenio modificatorio”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref165644530"></a>El **FA07** inicia cuando el Empleado SAT requiere actualizar los datos del “**Registro de convenio modificatorio**”.|<p>2. Despliega la pantalla “Registro de convenio modificatorio” y presenta la información obtenida del convenio modificatorio seleccionado: </p><p></p><p>Campo: </p><p>- Última modificación</p><p>&emsp;</p><p>Sección: “Registro de convenio modificatorio”</p><p></p><p>- Número de convenio\*</p><p>- Tipo de convenio\*. Aplica la **(RNA163)**</p><p>&emsp;- Alcance</p><p>&emsp;- Monto</p><p>&emsp;- Tiempo</p><p>&emsp;- Administrativo</p><p>- Fecha de firma\*</p><p>- Fecha fin de servicio\*</p><p>- Fecha fin de contrato con CM\*</p><p>- Cálculo de días naturales\*</p><p>- Incremento\*</p><p>- Subtotal\*</p><p>- IVA\*</p><p>- Impuestos\*</p><p>- Tipo de cambio\*</p><p>- Monto máximo del contrato con CM sin impuestos\*</p><p>- Monto máximo del contrato con CM con impuestos\*</p><p>- Monto en pesos\*</p><p>- Comentarios</p><p></p><p>Opciones. Aplica la **(RNA246)**:</p><p></p><p>- Cancelar</p><p>- Guardar</p><p>&emsp;</p><p>Secciones colapsadas: </p><p></p><p>- Registro de servicios</p><p>- Proyección de convenio modificatorio</p><p>- Asignación de plantilla </p><p>- Gestión documental</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_EditarConvenioModificatorio)** Estilos 04.</p>|
|<p>3. <a name="_ref164503790"></a>Modifica los valores de alguno de los siguientes datos: </p><p>&emsp;</p><p>- Número convenio</p><p>- Tipo de convenio(s) seleccionado</p><p>- Fecha de firma</p><p>- Fecha fin de servicio</p><p>- Fecha fin de contrato con CM</p><p>- Incremento</p><p>- IVA</p><p>- Tipo de cambio</p><p>- Comentarios</p>|4. Aplica **(RNA164)** para calcular los campos inhabilitados de acuerdo con el “Tipo de convenio” seleccionado.|
|<p>5. <a name="_ref167874669"></a>Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que se seleccione la opción **“Cancelar”**, continúa en el **([**FA03**](#fa03))**.</p>|<p>6. Valida que se hayan capturado los datos obligatorios de acuerdo con la **(RNA03)**.</p><p>&emsp;</p><p>- En caso contrario, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>7. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;` `Datos que se almacenan:</p><p>**Módulo**= Contrato-CM-Sección en la que fue invocado</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario** = RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id convenio modificatorio</p><p>&emsp;- Id contrato</p><p>&emsp;- Id sección en la que fue invocado</p><p>&emsp;&emsp;</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([\[**FA04**\](#fa04)**)**](#fa06)**.</p>|
||<p>8. Almacena en la BD las modificaciones de los campos. Aplica la **(RNA247)**:</p><p>&emsp;</p><p>- Número convenio</p><p>- Tipo de convenio(s) seleccionado</p><p>- Fecha de firma</p><p>- Fecha fin de servicio</p><p>- Fecha fin de convenio</p><p>- Cálculo de días naturales</p><p>- Incremento</p><p>- Subtotal</p><p>- IVA</p><p>- Impuestos</p><p>- Tipo de cambio</p><p>- Monto máximo del contrato con CM sin impuestos</p><p>- Monto máximo del contrato con CM con impuestos</p><p>- Monto en pesos</p><p>- Comentarios</p>|
||9. Muestra el **([**MSG011**](#msg011))** con la opción** “Aceptar”.|
|10. Selecciona la opción **“Aceptar”**.|11. Cierra el mensaje.|
||12. <a name="_hlk165637478"></a>Continúa en el paso [**4**](#_ref167872944) del Flujo primario.|

|<p></p><p><a name="fa08"></a>**FA08 Existe una proyección previamente cargada**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA08** inicia cuando existe una proyección previamente cargada.|
||2. Muestra el **([**MSG009**](#msg009))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona una opción.</p><p></p><p>- Si selecciona la opción **“No”**, el flujo continúa.</p><p>&emsp;</p><p>- Si selecciona la opción **“Sí”**, continúa en el paso [**5**](#_ref163999691) de este flujo.</p>|4. Cierra el mensaje, y continúa en el paso **[**4**](#_ref167874280)** del **([**FA10**](#fa10))**.|
||5. Cierra el mensaje, y continúa en el paso **[**11**](#_ref167894319)** del **([**FA10**](#fa10))**.|

|<p></p><p><a name="fa09"></a>**FA09 Selección “Gestión documental”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA09** inicia cuando el Empleado SAT selecciona la sección **“Gestión documental”**.|<p>2. Direcciona al documento “Gestión documental”.</p><p>Ver</p><p>**(17\_3083\_EIU\_GestionDocumental)** Estilos 01.</p>|

|<p></p><p><a name="fa10"></a>**FA10 Selección “Proyección de convenio modificatorio”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref165016646"></a>El **FA10** inicia cuando el Empleado SAT selecciona la sección **“Proyección de convenio modificatorio”**.|2. Consulta en la base de datos (BD) y obtiene la información de la “Proyección de convenio modificatorio”.|
||<p>3. <a name="_ref165017063"></a>Despliega la pantalla “Proyección de convenio modificatorio”.</p><p>&emsp;</p><p>&emsp;Opciones:</p><p>- Descargar layout ![](Aspose.Words.bf6c5b44-4900-49d9-850e-76575d145695.005.png)</p><p>- Archivo proyección</p><p>- Examinar</p><p>- Procesar proyección</p><p>- Descargar ![ref1]</p><p>&emsp;</p><p>Tabla (Proyección de convenio modificatorio). Aplica la regla de negocio **(RNA244)**.</p><p>- Conceptos de servicio</p><p>- mm1-a1</p><p>- mmn-an. Aplica la **(RNA80)**.</p><p>Ver **(17\_3083\_EIU\_EditarConvenioModificatorio)** Estilos 02.</p>|
|<p>4. <a name="_ref167874280"></a>Selecciona la opción **“Examinar”** y el continúa en el flujo.</p><p></p><p>- En caso de que seleccione la opción **“Descargar layout”**, continúa en el **([**FA05**](#fa05))**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Exportar a Excel”**, de la tabla **“Proyección de convenio modificatorio”**, continúa en el **([**FA06**](#fa06))**.</p><p>&emsp;</p><p>- En caso de requerir aplicar el **“Filtro”** en el campo de texto de la columna **“Conceptos de servicio”**, de la tabla, continúa en el **([**FA12**](#fa12))**.</p>|5. <a name="_ref165016820"></a>Abre el gestor de archivos del equipo de cómputo del Empleado SAT. |
|<p>6. Selecciona el archivo de Excel con extensión (.xlsx).</p><p>	</p>|7. Muestra el nombre del archivo seleccionado en el campo “Archivo de proyección”.|
|8. Selecciona la opción **“Procesar proyección”**.|<p>9. <a name="_ref165014697"></a>Valida sí existe una proyección previamente cargada.</p><p>&emsp;</p><p>- En caso de no existir una proyección cargada, el flujo continúa.</p><p>- En caso de existir una proyección previamente cargada, continúa en el **([**FA08**](#fa08))**.</p>|
||<p>10. ` `<a name="_ref165015008"></a>Valida que la estructura del *layout* seleccionado sea la misma que la estructura del *layout* de la opción “Descargar layout”, acorde con la **(RNA80)**, y el flujo continúa.</p><p>&emsp;</p><p>- En caso de que la estructura del *layout* no sea igual, continúa en el **([**FA01**](#fa01))**.</p>|
||<p>11. <a name="_ref167894319"></a>Valida que la suma de unidades de cada concepto de servicio de tipo “Volumetría” sea menor o igual al “Número total de servicios”, de acuerdo con la **(RNA231)** y** el flujo continúa.</p><p></p><p>- En caso de no cumplir con la validación, continúa en el **([**FA16**](#fa16))**.</p>|
||<p>12. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contrato-CM-Proyección</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario** = RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= </p><p>**INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda** </p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id convenio modificatorio</p><p>&emsp;- Id concepto de servicio</p><p>&emsp;- Carga masiva</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>13. Almacena en la BD la información del *layout*. Aplica la **(RNA247)**.</p><p>&emsp;</p><p>- Conceptos de servicio</p><p>- mm1-a1 </p><p>- mmn-an. Aplica la **(RNA80)**.</p>|
||14. Muestra el **([**MSG002**](#msg002))** con la opción** “Aceptar”.|
|<p>15. Selecciona la opción **“Aceptar”**.</p><p>	</p>|16. Cierra el mensaje.|
||<p>17. Muestra en la pantalla la información cargada en la tabla “Proyección de caso de negocio”. Aplica la **(RNA244)**.</p><p></p><p>- Conceptos de servicio</p><p>- mm1-a1 </p><p>- mmn-an. Aplica la **(RNA80)**.</p>|
||18. Fin del Caso de Uso.|

|<p></p><p><a name="fa11"></a>**FA11 Selecciona la opción “Asignación de plantilla”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA11** inicia cuando el Empleado SAT selecciona la sección **“Asignación de plantilla”**.|2. Consulta en la BD las plantillas asociadas a la fase de “Ejecución”.|
||<p>3. Consulta en la BD y obtiene la información de la sección “Asignación de plantilla” que se encuentre almacenada.</p><p>&emsp;</p><p>- Asignar plantilla</p>|
||<p>4. Despliega la sección y presenta la información obtenida de “Asignación de plantilla” en los siguientes campos:</p><p>&emsp;</p><p>- Asignar plantilla\*</p><p></p><p>Opciones:</p><p></p><p>- Nuevo![](Aspose.Words.bf6c5b44-4900-49d9-850e-76575d145695.006.png)</p><p>- Eliminar ![](Aspose.Words.bf6c5b44-4900-49d9-850e-76575d145695.007.png)</p><p>- Cancelar</p><p>- Guardar</p><p>&emsp;</p><p>Ver</p><p>**(17\_3083\_EIU\_EditarConvenioModificatorio)** Estilos 03.</p>|
|<p>5. Selecciona la opción **“Nuevo”**.</p><p>&emsp;</p><p>- Si requiere modificar alguno de los datos almacenados continúa en el paso 7.</p>|6. Muestra una lista de selección nueva para el campo “Asignar plantilla”.|
|<p>7. <a name="_ref167962932"></a>Captura. los datos de **“Asignación de plantilla”**.</p><p>&emsp;</p><p>- Asignar plantilla</p>||
|<p>8. Selecciona la opción **“Guardar”** y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Cancelar”** continúa en el **([**FA03**](#fa03))**.</p><p>- Si selecciona la opción **“Eliminar”**, para un registro ya almacenado, continúa en el **([**FA17**](#fa17))**.</p><p>- Si selecciona la opción **“Eliminar”**, para un registro nuevo, continúa en el **([**FA03**](#fa03))**.</p>|<p>9. Valida que se hayan ingresado todos los datos obligatorios, conforme a la **(RNA03)**.</p><p>&emsp;</p><p>- Si se identifica que no se ingresaron todos los datos obligatorios, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>10. Almacena en la BD las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Contrato-CM-Asignación de plantilla</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario** = RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT** (Borrar) según corresponda</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id convenio modificatorio</p><p>&emsp;- Asignar plantilla</p><p>&emsp;&emsp;</p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA04**](#fa04))**.</p>|
||11. Se almacenan en la BD los datos que hayan sido actualizados. Aplica la **(RNA247)**.|
||12. Muestra el **([**MSG014**](#msg014))** con la opción “Aceptar”.|
|13. Selecciona la opción **“Aceptar”**.|14. Cierra el mensaje.|
||15. Continúa en el paso [**4**](#_ref167872944) del Flujo primario.|

|<p></p><p><a name="fa12"></a>**FA12 Selecciona la opción “Filtrar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA12** inicia cuando el Empleado SAT requiere **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.</p><p></p>||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||<p>5. Realiza lo siguiente: </p><p>&emsp;</p><p>- Si fue invocado en el Flujo primario, continúa en el paso [**9**](#_ref167873856) de dicho flujo. </p><p>- Si fue invocado en el **([**FA10**](#fa10))**, continúa en el paso [**4**](#_ref167874280)** de dicho flujo. </p><p>&emsp;</p>|

|<p></p><p><a name="fa13"></a>**FA13 Opción “Validar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA13** inicia cuando el Empleado SAT selecciona la opción **“Validar”**.</p><p></p>|<p>2. Valida que el campo “Monto máximo de contrato con CM sin impuestos” de este convenio sea menor e igual a la suma del “Monto máximo total” de los servicios de acuerdo con la **(RNA175)**.</p><p>&emsp;</p><p>- En caso de que el punto no sea válido continua en el **([**FA15**](#fa15))**.</p><p></p>|
||3. Muestra el **([**MSG003**](#msg003))** con la opción** “Aceptar”.|
|19. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Regresa al paso [**9**](#_ref167873856) del Flujo primario.|

|<p></p><p><a name="fa14"></a>**FA14 Selecciona la opción “Editar” de la sección “Registro de servicios”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA14** inicia cuando el Empleado SAT selecciona la opción **“Editar”** de la sección **“Registro de servicios”**.</p><p></p>|2. Muestra los siguientes criterios para editar de acuerdo con las **(RNA172)** y **(RNA165)** y cambia la propiedad de esos campos de edición.|
||<p>3. Cambias las acciones de la tabla por las siguientes:</p><p>&emsp;</p><p>- Descartar</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_EditarConvenioModificatorio)** Estilos 05.</p>|
|<p>4. Modifica los datos y continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Descartar”** del registro seleccionado continúa en el **([**FA03**](#fa03))**.</p>|5. Regresa al punto de acción donde fue invocado.|

|<p></p><p><a name="fa15"></a>**FA15 Validación del “Registro de servicios” incorrecta**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||<p>1. El **FA15** inicia cuando el sistema identifica que no se validaron los datos de “Registro de servicios”, acuerdo con la  **(RNA175)**.</p><p></p>|
||2. Muestra el mensaje **([**MSG012**](#msg012))**, con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Regresa al paso [**9**](#_ref167873856) del Flujo primario.|

|<p></p><p><a name="fa16"></a>**FA16 Validación de suma de unidades de cada concepto de servicio**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA16** inicia cuando la suma de unidades de cada concepto de servicio establecida en el *layout* de carga es mayor al “Número total de servicios”, de la sección “Registro de servicios”.|
||2. Muestra el **([**MSG013**](#msg013))**, con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso  **[**4**](#_ref167874280)** del **([**FA10**](#fa10))**.|

|<p></p><p><a name="fa17"></a>**FA17 Selecciona la Opción “Eliminar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA17** inicia cuando el Empleado SAT selecciona la opción **“Eliminar”**.|2. El sistema mostrará el **([**MSG015**](#msg015))** con las opciones** “Sí” y “No”.|
|<p>3. Si selecciona la opción **“No”**, continúa en el flujo.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, continúa en el paso 5 de este flujo.</p>|4. Cierra el mensaje y continúa en el flujo de la sección que lo invocó.|
||<p>5. Valida que el registro no esté relacionado con otro módulo conforme a la **(RNA249)**,** continúa en el paso 9 de este flujo.</p><p>&emsp;</p><p>- Si el registro está relacionado con otro módulo continúa en el flujo.</p>|
||6. Muestra el **([**MSG016**](#msg016))** con la opción “Aceptar”.|
|7. Selecciona la opción **“Aceptar”**.|8. Cierra el mensaje y continúa en la sección donde fue invocado.|
||9. Borra el registro seleccionado de la tabla de la sección que fue invocado.|
||10. Regresa al punto de acción donde fue invocado.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc165300786"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_EditarConvenioModificatorio</p><p>- 17\_3083\_EIU\_ModificarContratos</p><p>- 17\_3083\_EIU\_GestionDocumental</p><p>- 17\_3083\_EIU\_RegistrarConvenioModificatorio</p><p></p>|
|<h3><a name="_toc165300787"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Favor de ingresar los datos obligatorios marcados con un asterisco (\*).|
|<a name="msg002"></a>**MSG002**|Se cargó la proyección al convenio modificatorio.|
|<a name="msg003"></a>**MSG003**|El “Monto máximo del contrato con CM sin impuestos” coincide con el “Monto máximo total” de los servicios.|
|<a name="msg004"></a>**MSG004**|Se perderá la información ingresada. ¿Está seguro de cancelar?|
|<a name="msg005"></a>**MSG005**|El *layout* de carga no contiene la estructura requerida, favor de verificar.|
|<a name="msg006"></a>**MSG006**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg009"></a>**MSG009**|Existe una proyección previamente cargada, ¿desea actualizarla?|
|<a name="msg010"></a>**MSG010**|El convenio modificatorio fue actualizado exitosamente.|
|<a name="msg011"></a>**MSG011**|Se guardo correctamente la información.|
|<a name="msg012"></a>**MSG012**|El “Monto máximo del contrato con CM sin impuestos” **no** coincide con el “Monto máximo total” de los servicios.|
|<a name="msg013"></a>**MSG013**|Verifique el *layout* de carga, ya que la línea(s) [Concepto de servicio] sobrepasa el “Número total de servicios”.|
|<a name="msg014"></a>**MSG014**|Se guardó correctamente la información.|
|<a name="msg015"></a>**MSG015**|Se eliminará el registro seleccionado. ¿Desea continuar?|
|<a name="msg016"></a>**MSG016**|El registro no se puede eliminar porque se encuentra relacionado en otro módulo.|
|<a name="msg017"></a>**MSG017**|Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).|

|<p></p><p></p><p></p>|
| - |
|<h3><a name="_toc165300788"></a>**10. Requerimientos No Funcionales** </h3>|

||
| :- |

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001**|Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas. |
|**RNF002**|Concurrencia|<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p></p><p>El número máximo de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT. </p>|
|**RNF003**|Seguridad|El acceso solo podrá ser otorgado al Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema. |
|**RNF004**|Usabilidad|<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación: </p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual como guía para el Empleado SAT. </p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado. </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales. </p><p>&emsp;</p>|
|**RNF005**|Eficiencia|Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT. |
|**RNF006**|Usabilidad|<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100: </p><p> </p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta). </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta). </p><p>- Ir a la siguiente página (debe mostrar la siguiente página considerando la actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT). </p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual, con el resultado de la consulta). </p><p> </p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente.</p>|
|**RNF007**|Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas. |
|**RNF008**|Usabilidad|<p>Usabilidad, El Empleado SAT podrá navegar a través de las páginas resultantes del documento PDF. </p><p>- Ir a la siguiente página (debe mostrar la página consecutiva del documento PDF). </p><p>- Ir a la página anterior (debe mostrar la página previa del documento PDF).</p>|
|**RNF009**|Fiabilidad|El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema.|
|**RNF010**|Seguridad |Mantener la información en pantalla en caso de un error al guardar las pistas de auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema. |
|**RNF011**|Integridad |Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena. |

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc165300789"></a>**11. Diagrama de actividad** </h3>|
|<p></p><p>![](Aspose.Words.bf6c5b44-4900-49d9-850e-76575d145695.008.png)</p><p></p>|
|<h3><a name="_toc165300790"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no se requiere para este proceso.</p><p></p><p></p>|
|<h3><a name="_toc165300791"></a>**13. Aprobación del cliente** </h3>|
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
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:** Eric Hector Pérez Pérez.|
|**Puesto:** Líder Técnico SDMA 6.|**Puesto:** Analista de Sistemas DS SDMA 6.|
|**Fecha**:|**Fecha**:|
|||

||
| :- |






|||Página 3 de 17|
| :- | :-: | -: |

[ref1]: Aspose.Words.bf6c5b44-4900-49d9-850e-76575d145695.004.png
