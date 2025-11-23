|![](Aspose.Words.9941e767-75ce-4ccc-bf06-f98de4292bbc.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_CrearEstimacion.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Isabel Adriana Valdez Cortés|*19/02/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*23/04/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*11/06/2024*|



**Tabla de Contenido**

[17_3083_ECU_CrearEstimacion	2](#_toc169691059)

[1. Descripción	2](#_toc169691060)

[2. Diagrama del Caso de Uso	2](#_toc169691061)

[3. Actores	2](#_toc169691062)

[4. Precondiciones	2](#_toc169691063)

[5. Post condiciones	3](#_toc169691064)

[6. Flujo primario	3](#_toc169691065)

[7. Flujos alternos	7](#_toc169691066)

[8. Referencias cruzadas	29](#_toc169691067)

[9. Mensajes	29](#_toc169691068)

[10. Requerimientos No Funcionales	30](#_toc169691069)

[11. Diagrama de actividad	32](#_toc169691070)

[12. Diagrama de estados	33](#_toc169691071)

[13. Aprobación del cliente	34](#_toc169691072)






### **<a name="_toc169691059"></a>**17\_3083\_ECU\_CrearEstimacion

|<h3><a name="_toc169691060"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT crear, editar y ver la información de las estimaciones relacionadas con un proyecto y contrato.</p><p></p>|
|<h3><a name="_toc169691061"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.9941e767-75ce-4ccc-bf06-f98de4292bbc.002.png)</p><p></p>|
|<h3><a name="_toc169691062"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema. |

||
| :- |
|<h3><a name="_toc169691063"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida. </p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa. </p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar al módulo “Consumo de Servicios”.</p><p>- El Empleado SAT ha seleccionado alguno de los íconos correspondientes para las siguientes opciones en el módulo de “Consumo de Servicios”:</p><p>- Alta de estimación</p><p>- Editar estimación</p><p>- Ver estimación</p><p>&emsp;</p>|
|<h3><a name="_toc169691064"></a>**5. Post condiciones** </h3>|
|<p></p><p>El Empleado SAT realizó alguna de las siguientes acciones:</p><p>- Capturó la información correspondiente para dar de alta una nueva estimación.</p><p>- Editó la información correspondiente de una estimación.</p><p>- Actualizó el estatus de la estimación.</p><p>- Visualizó la información de la estimación.</p><p>- El sistema almacenó la información de la nueva estimación, asignándole un id único.</p><p>&emsp;</p>|
|<h3><a name="_toc169691065"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El Caso de Uso inicia cuando el Empleado SAT selecciona la opción **“Nueva estimación”** desde el **(17\_3083\_ECU\_AdministrarDevengado)**.</p><p>&emsp;</p><p>- En caso de haber seleccionado la opción **“Editar estimación”** desde el **(17\_3083\_ECU\_AdministrarDevengado**, continúa en el flujo alterno **([**FA01**](#fa01))**.</p><p>- En caso de haber seleccionado la opción **“Ver estimación”** desde el **(17\_3083\_ECU\_AdministrarDevengado**, continúa en el **([**FA02**](#fa02))**.</p>|2. Identifica el rol del Empleado SAT que ingresa para presentar de forma correcta la pantalla. Aplica la regla de negocio **(RNA51)**.|
||<p>3. <a name="_ref169101538"></a>Obtiene de la base de datos (BD) la información con estado “Activo” de los catálogos que se usarán en las listas de selección:</p><p>&emsp;</p><p>- Año del periodo de control</p><p>- Meses</p><p>- IVA</p>|
||<p>4. Consulta en la BD la siguiente información del contrato al que se le da de alta la estimación.</p><p>&emsp;</p><p>- Número del contrato</p><p>- IVA</p><p>- Moneda</p>|
|<p></p><p></p><p></p>|<p>5. <a name="_ref164285375"></a>Muestra la pantalla de “Consumo de Servicios - Estimación” para registrar lo siguiente:</p><p>&emsp;</p><p>&emsp;“Datos generales estimación”. Aplica la **(RNA51)** y **(RNA206)**</p><p>- Id</p><p>- Nombre corto del contrato\*</p><p>- Número de contrato</p><p>- Proveedor\*</p><p>- Estatus. Aplica la **(RNA209)**</p><p>- Periodo de inicio\*</p><p>- Periodo fin\*</p><p>- Periodo de control\*</p><p>- IVA</p><p>- Tipo de cambio referencial. Aplica la **(RNA126)**</p><p>- Monto estimado total</p><p>- Monto estimado total en pesos</p><p>Opción:</p><p>- Cancelar</p><p>- Guardar</p><p>Secciones colapsadas e inhabilitadas:</p><p>- Registro de servicios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p>Opción:</p><p>- Regresar</p><p></p><p>Ver **(17\_3083\_EIU\_CrearEstimación)** Estilos 01.</p>|
|<p>6. <a name="_ref163944848"></a>Selecciona los datos:</p><p>&emsp;</p><p>- Periodo de inicio\*</p><p>- Periodo fin\*</p><p>- Periodo de control\*</p><p>- IVA\*</p>||
|7. Captura el dato para el “Tipo de cambio referencial”.||
|<p>8. <a name="_ref169101403"></a><a name="_ref164505676"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Guardar”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Cancelar”**, continúa en el **([**FA17**](#fa17))**.</p><p></p><p>- En caso de seleccionar la opción **“Regresar”**, continúa en el **([**FA03**](#fa03))**.</p>|<p>9. <a name="_ref158740842"></a><a name="_ref169003849"></a>Valida que se hayan capturado los datos obligatorios. Aplica las **(RNA03)**, **(RNA126)** y **(RNA206)**.</p><p>&emsp;</p><p>- En caso de identificar que no se ingresaron los datos obligatorios, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>10. <a name="_ref169003865"></a>Verifica que el “Periodo de inicio”, “Periodo fin” y “Periodo de control” sean correctos. Aplica la **(RNA206)**</p><p>&emsp;</p><p>- En caso de identificar que no son correctos, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>11. <a name="_ref168856458"></a>Consulta en la BD que no exista una estimación con el mismo “Periodo de control”, “Nombre corto del contrato” y “Proveedor”.</p><p>&emsp;</p><p>- En caso de identificar que se encuentra un registro, continúa en el **([**FA06**](#fa06))**.</p>|
||12. Genera el “Id” de la nueva estimación. Aplica la **(RNA221)**|
||<p>13. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Estimación- Datos generales estimación</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de estimación</p><p>- Nombre corto del proyecto</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>14. Almacena en la BD la siguiente información de la estimación creada:</p><p>&emsp;</p><p>- Id</p><p>- Nombre corto del contrato</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Estatus: “Inicial”</p><p>- Periodo de inicio</p><p>- Periodo fin</p><p>- Periodo de control</p><p>- IVA</p><p>- Tipo de cambio referencial</p><p>- Nombre del Empleado SAT. Aplica la **(RNA247)**</p><p>- Fecha y hora de la modificación</p>|
||<p>15. Habilita las secciones:</p><p>&emsp;</p><p>- Registro de servicios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p>|
||16. Muestra el mensaje **([**MSG001**](#msg001))** con la opción** “Aceptar”.|
|17. Selecciona la opción **“Aceptar”**.|18. Cierra el mensaje.|
||19. Muestra la pantalla con los campos actualizados.|
|<p>20. <a name="_ref164290421"></a>Elije una opción:</p><p>&emsp;</p><p>- En caso de seleccionar la sección **“Registro de servicios”**, continúa en el **([**FA08**](#fa08))**.</p><p>- En caso de seleccionar la sección **“Dictámenes asociados”**, continúa en el **([**FA09**](#fa09))**.</p><p>- En caso de seleccionar la sección **“Facturas asociadas”**, continúa en el **([**FA10**](#fa10))**.</p><p>- En caso de seleccionar la opción **“Regresar”**, continúa en el **([**FA03**](#fa03))**.</p>||
||21. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc169691066"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 Opción “Editar estimación”**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA01** inicia cuando el Empleado SAT selecciona la opción **“Editar estimación”**.|<p>2. Obtiene de la base de datos (BD) la información con estado “Activo” de los catálogos que se usarán en las listas de selección:</p><p>&emsp;</p><p>- Año del periodo de control</p><p>- Meses</p><p>- IVA</p>|
||3. <a name="_ref169101090"></a>Consulta en la BD y obtiene los datos generales de la estimación a modificar.|
||<p>4. Consulta en la BD si existe información almacenada para el “Registro de servicios” y precarga los datos.</p><p>&emsp;</p><p>- En caso de que no exista información, consulta en la BD la siguiente información: </p><p>&emsp;Del contrato relacionado</p><p>- IVA</p><p>- Porcentaje de IEPS</p><p>- Moneda</p><p>Registro de servicios</p><p>- Conceptos de servicio</p><p>- Tipo de unidad </p><p>- Tipo de consumo</p><p>- Precio unitario</p><p>- Cantidad de servicios máxima vigente</p><p>- Monto máximo (vigente)</p><p>- Aplica IEPS</p>|
||5. Calcula el “Monto estimado total”. Aplica la **(RNA207)**|
||<p>6. Identifica si el tipo de moneda es igual a pesos (MXN), entonces en el campo “Monto estimado total en pesos” se muestra el valor de “Monto estimado total”.</p><p>&emsp;</p><p>- En caso contrario, realizar la conversión a pesos tomando en cuenta el “Tipo de cambio referencial” y se muestra el valor en “Monto estimado total en pesos”. Aplica la **(RNA208)**</p>|
||<p>7. <a name="_ref164199342"></a>Muestra la pantalla de “Consumo de Servicios - Estimación” con la información precargada para editar:</p><p>&emsp;</p><p>&emsp;“Datos generales estimación”. Aplica la **(RNA206)**</p><p>- Id</p><p>- Nombre corto del contrato\*</p><p>- Número de contrato</p><p>- Proveedor\*</p><p>- Estatus. Aplica la **(RNA209)**</p><p>- Periodo de inicio\*</p><p>- Periodo fin\*</p><p>- Periodo de control\*</p><p>- IVA</p><p>- Tipo de cambio referencial</p><p>- Monto estimado total</p><p>- Monto estimado total en pesos</p><p>Opciones:</p><p>- Cancelar</p><p>- Guardar</p><p>- Inicial. Aplica la **(RNA210)**</p><p>- Duplicar estimación ![ref1]. Aplica la **(RNA210)**</p><p>- Cancelar estimación ![ref2]. Aplica la **(RNA210)**</p><p></p><p>Secciones colapsadas:</p><p>- Registro de servicios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p></p><p>Opción:</p><p>- Regresar</p><p>&emsp;</p><p>Ver **(17\_3083\_EIU\_CrearEstimación)** Estilos 02.</p>|
|8. <a name="_ref164156610"></a><a name="_ref164284976"></a>Si requiere, modifica los datos de la sección **“Datos generales estimación”**.||
|<p>9. <a name="_ref168853696"></a>Elige una opción:</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Guardar”**, el flujo continúa.</p><p>- En caso de seleccionar la opción **“Cancelar”**, continúa en el **([**FA17**](#fa17))**.</p><p>- En caso de seleccionar la opción **“Inicial”**, continúa en el **([**FA11**](#fa11))**.</p><p>- En caso de seleccionar la opción **“Duplicar estimación”**, continúa en el **([**FA15**](#fa15))**.</p><p>- En caso de seleccionar la opción **“Cancelar estimación”** continúa en el **([**FA16**](#fa16))**.</p><p>- En caso de seleccionar la sección **“Registro de servicios”**, continúa en el **([**FA08**](#fa08))**.</p><p>- En caso de seleccionar la sección **“Dictámenes asociados”**, continúa en el **([**FA09**](#fa09))**.</p><p>- En caso de seleccionar la sección **“Facturas asociadas”**, continúa en el **([**FA10**](#fa10))**.</p><p>- En caso de seleccionar la opción **“Regresar”**, continúa en el **([**FA03**](#fa03))**.</p>|<p>10. <a name="_ref169019576"></a>Valida que se hayan capturado los datos obligatorios. Aplica las **(RNA03)**, **(RNA126)** y **(RNA206)**.</p><p>&emsp;</p><p>- En caso de identificar que no se ingresaron los datos obligatorios, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>11. <a name="_ref169019635"></a>Verifica que el “Periodo de inicio”, “Periodo fin” y “Periodo de control” sean correctos. Aplica la **(RNA206)**</p><p>&emsp;</p><p>- En caso de identificar que no son correctos, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>12. <a name="_ref169019688"></a>Consulta en la BD que no exista una estimación con el mismo “Periodo de control”, “Nombre corto del contrato” y “Proveedor”.</p><p>&emsp;</p><p>- En caso de identificar que se encuentra un registro, continúa en el **([**FA06**](#fa06))**.</p>|
||<p>13. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Estimación- Datos generales estimación</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de estimación</p><p>- Nombre corto del proyecto</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>14. Actualiza en la BD la siguiente información de la estimación:</p><p>&emsp;</p><p>- Id</p><p>- Nombre corto del contrato</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Estatus</p><p>- Periodo de inicio</p><p>- Periodo fin</p><p>- Periodo de control</p><p>- IVA</p><p>- Tipo de cambio referencial</p><p>- Nombre del Empleado SAT. Aplica la **(RNA247)**</p><p>- Fecha y hora de la modificación</p><p>&emsp;</p><p>- Si fue invocado en el (FA15) adicional almacena los datos del “Registro de servicios” relacionados. </p>|
||<p>15. Si fue invocado en el (FA15) habilita las opciones:</p><p></p><p>- Inicial. Aplica la **(RNA210)**</p><p>- Duplicar estimación ![ref1]. Aplica la **(RNA210)**</p><p>- Cancelar estimación ![ref2]. Aplica la **(RNA210)**</p><p></p><p>- En caso contrario, el flujo continúa.</p>|
||16. Muestra el **([**MSG001**](#msg001))** con la opción** “Aceptar”.|
|17. Selecciona la opción **“Aceptar”**.|18. Cierra el mensaje y continúa en el paso [**9**](#_ref168853696) de este flujo.|

|<p></p><p><a name="fa02"></a>**FA02 Opción “Ver estimación”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA02** inicia cuando el Empleado SAT selecciona la opción **“Ver estimación”**.|2. Obtiene de la BD la información de los “Datos generales” de la estimación.|
||<p>3. Complementa la pantalla con la información obtenida en el paso anterior de solo lectura, la sección de “Datos generales estimación”.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_CrearEstimacion)** Estilo 02.</p>|
||<p>4. Muestra las siguientes secciones habilitadas para elección:</p><p>&emsp;</p><p>- Registro de servicios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p></p><p>Opciones:</p><p>- Inicial. Aplica la **(RNA210)**</p><p>- Regresar</p>|
|<p>5. <a name="_ref160268006"></a>Selecciona la sección **“Registro de servicios”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la sección **“Dictámenes asociados”**, continúa en el paso [**8**](#_ref160267595) de este flujo.</p><p>- En caso de seleccionar la sección **“Facturas asociadas”**, continúa en el paso [**10**](#_ref160267661)** de este flujo.</p><p>- En caso de seleccionar la opción **“Regresar”**, continúa en el **([**FA03**](#fa03))**.</p>|6. Obtiene de la BD la información de “Registro de servicios” relacionada con la estimación.|
||<p>7. Complementa la pantalla con la información obtenida en el paso anterior en la sección de “Registro de servicios”, con formato de solo lectura.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_CrearEstimacion)** Estilo 03.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006) de este flujo.</p>|
||8. <a name="_ref160267595"></a>Obtiene de la BD la información de “Dictámenes asociados” con el mismo Contrato, Proveedor y Periodo de control de la estimación.|
||<p>9. <a name="_ref160267535"></a>Complementa la pantalla con la información obtenida en el paso anterior en la sección de “Dictámenes asociados”, con formato de solo lectura.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_CrearEstimacion)** Estilo 04.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006)** de este flujo.</p><p>&emsp;</p>|
||10. <a name="_ref160267661"></a>Obtiene de la BD la “Facturas asociadas” con el mismo Contrato, Proveedor y Periodo de control de la estimación.|
||<p>11. Complementa la pantalla con la información obtenida en el paso anterior en la sección de “Facturas asociadas”, con formato de solo lectura.</p><p>&emsp;</p><p>&emsp;Ver **(17\_3083\_EIU\_CrearEstimacion)** Estilo 05.</p><p>&emsp;</p><p>&emsp;Regresa al paso [**5**](#_ref160268006)** de este flujo.</p>|
||12. Fin del Caso de Uso.|

|<p></p><p><a name="fa03"></a>**FA03 Opción “Regresar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA03** inicia cuando el Empleado SAT selecciona la opción **“Regresar”**.|2. Muestra el **([**MSG002**](#msg002))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona la opción **“Sí”** y el flujo** continúa.</p><p>&emsp;</p><p>- En caso de seleccionar **“No”**,** continúa en el paso [**7**](#_ref164282595)** de este flujo.</p>|4. Cierra el mensaje.|
||5. No almacena ninguna información   y en su caso si aún no está almacenada la información se libera el Id generado.|
||6. Continúa en la pantalla principal del proceso definido en el **(17\_3083\_ECU\_AdministrarDevengado)**.|
||<p>7. <a name="_ref164282595"></a>Cierra el mensaje y realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el Flujo primario paso  [**8**](#_ref164505676)** o [**20**](#_ref164290421), continúa en el paso **[**8**](#_ref164505676)** del Flujo primario.</p><p>- Si fue invocado en el paso [9](#_ref168853696) del **([**FA01**](#fa01))**, continúa en el paso **[**9**](#_ref168853696)** del **([**FA01**](#fa01))**.</p><p>- Si fue invocado en el paso 5 **([**FA02**](#fa02))**,** continúa en el paso **[**5**](#_ref160268006)** del **([**FA02**](#fa02))**.</p><p>- Si fue invocado en el paso [6](#_ref169089294) del  **([**FA15**](#fa15))**,** continúa en el paso **[**6**](#_ref169089294)** del **([**FA15**](#fa15))**.</p>|

|<p></p><p><a name="fa04"></a>**FA04 No se ingresaron los datos obligatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA04** inicia cuando el sistema identifica que no se ingresaron los datos obligatorios.|
||2. Muestra en rojo los campos pendientes de capturar. |
||3. Muestra el **([**MSG003**](#msg003))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||<p>6. Realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso [9](#_ref169003849) del Flujo primario, continúa en el paso [**6**](#_ref163944848) del Flujo primario.</p><p>&emsp;</p><p>- Si fue invocado en el paso [10](#_ref169019576) del **([**FA01**](#fa01))**, continúa en el paso  [**8**](#_ref164284976) del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa05"></a>**FA05 “Periodo de inicio”, fin” y “Periodo de control” incorrectos**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA05** inicia cuando el sistema identifica que el “Periodo de inicio”,  “Periodo fin” o “Periodo de control” son incorrectos.|
||2. Muestra en rojo los campos incorrectos. |
||3. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje. |
||<p>6. Realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso [10](#_ref169003865) del Flujo primario, continúa en el paso [**6**](#_ref163944848)  del Flujo primario.</p><p>&emsp;</p><p>- Si fue invocado en el paso [11](#_ref169019635) del **([**FA01**](#fa01))**, continúa en el paso [**8**](#_ref164284976)**  del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa06"></a>**FA06 Datos “Nombre corto”, “Proveedor” y “Periodo control” identificados en otra estimación**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA06** inicia cuando el sistema identifica que el “Nombre corto del contrato”, “Proveedor”  y “Periodo de control” se encuentra registrados en otra estimación.|
||2. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje. |
||<p>5. Realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso [11](#_ref168856458) del Flujo primario, continúa en el paso  [**6**](#_ref163944848) del Flujo primario.</p><p>- Si fue invocado en el paso [12](#_ref169019688) del **([**FA01**](#fa01))**, continúa en el paso [**8**](#_ref164156610) del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa07"></a>**FA07 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA07** inicia cuando interviene un evento ajeno y no se pueden almacenar las Pistas de Auditoría. |
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje de acuerdo con lo siguiente:</p><p></p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT** e **INSR**, se muestra el **([**MSG006**](#msg006))**.</p><p>&emsp;</p><p>- En caso de que la pista de auditoría sea por el tipo de movimiento **PRNT**, se muestra el **([**MSG007**](#msg007))**.</p><p>Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría.|

|<p></p><p><a name="fa08"></a>**FA08 Sección de “Registro de servicios”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA08** inicia cuando el Empleado SAT selecciona la sección **“Registro de servicios”**.</p><p>&emsp;</p><p>&emsp;</p><p></p>|<p>2. Consulta en la BD la siguiente información los conceptos de servicio del contrato o último convenio modificatorio.</p><p>&emsp;</p><p>- Id</p><p>- Conceptos de servicio </p><p>- Unidad de medida</p><p>- Tipo de consumo</p><p>- Precio unitario</p><p>- Cantidad de servicios máxima vigente</p><p>- Monto máximo vigente</p><p>- Monto estimado</p>|
||3. Consulta en la BD la “Cantidad de servicios estimados” registrados en la estimación para cada concepto de servicio.|
||4. Calcula el “Monto estimado”. Aplica la **(RNA212)**|
||5. <a name="_ref164197343"></a>Calcula la “Cantidad de servicios estimados acumulados”. Aplica la **(RNA211)**|
||6. Calcula el “Monto estimado acumulado”. Aplica la **(RNA213)**|
||7. Calcula el “% de servicios estimados acumulados”. Aplica la **(RNA214)**|
||8. Calcula el “% de monto estimado acumulado”. Aplica la **(RNA215)**|
||<p>9. <a name="_ref164285706"></a>Muestra la sección “Registro de servicios” con la siguiente información:</p><p>&emsp;</p><p>&emsp;Opciones:</p><p>- Actualizar precio unitario ![](Aspose.Words.9941e767-75ce-4ccc-bf06-f98de4292bbc.005.png)</p><p>- Exportar a Excel ![ref3]</p><p></p><p>Tabla “Servicios estimados”. Aplica la **(RNA216)**</p><p>- Id</p><p>- Grupo</p><p>- Conceptos de servicio </p><p>- Tipo de unidad</p><p>- Tipo de consumo</p><p>- Precio unitario</p><p>- Cantidad de servicios máxima vigente</p><p>- Cantidad de servicios estimados</p><p>- Monto estimado</p><p>- Cantidad de servicios estimados acumulados</p><p>- Monto estimado acumulado</p><p>- % de servicios estimados acumulados</p><p>- % de monto estimado acumulado</p><p>&emsp;</p><p>- Campos para “Filtrar” por columna</p><p>Opciones:</p><p>- Volumetría estimada. Aplica la **(RNA210)**</p><p>- Cancelar</p><p>- Guardar</p><p>&emsp;</p><p>Ver **(17\_3083\_EIU\_CrearEstimacion)** Estilos 03.</p>|
|10. <a name="_ref169260937"></a>Ingresa información para el dato **“Cantidad de servicios estimados”** por cada concepto de servicio que requiera. |11. <a name="_ref169264681"></a>El sistema identifica que se terminó de capturar el dato para el concepto de servicio y calcula el “Monto estimado”. Aplica la **(RNA212)**|
||12. Calcula la “Cantidad de servicios estimados acumulados”. Aplica la **(RNA211)**|
||13. Calcula el “Monto estimado acumulado”. Aplica la **(RNA213)**|
||14. Calcula el “% de servicios estimados acumulados”. Aplica la **(RNA214)**|
||15. Calcula el “% de monto estimado acumulado”. Aplica la **(RNA215)**|
|<p>16. <a name="_ref169260088"></a><a name="_ref164288703"></a>Elige una opción:</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Guardar”**, el flujo continúa.</p><p>- En caso de seleccionar la opción **“Actualizar precio unitario”**, continúa en el **([**FA18**](#fa18))**.</p><p>- En caso de seleccionar la opción **“Exportar a Excel”**, continua en el **([**FA12**](#fa12))**.</p><p>- En caso de seleccionar la opción **“Volumetría estimada”**, continúa en el **([**FA14**](#fa14))**.</p><p>- En caso de requerir **“Filtrar”** la información en alguna columna de la tabla, continúa en el **([**FA13**](#fa13))**.</p><p></p><p></p><p></p><p></p>|<p>17. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Estimación- Registros de servicios</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar) o **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id (estimación)</p><p>- Contrato</p><p>- Id (concepto de servicio)</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>18. Identifica el movimiento realizado y almacena en la BD la siguiente información relacionando con la estimación, el contrato y proveedor:</p><p>&emsp;</p><p>- Id (estimación)</p><p>- Contrato</p><p>- Id (concepto de servicio) </p><p>- Precio unitario</p><p>- Cantidad de servicios estimados</p><p>- Nombre del Empleado SAT. Aplica la **(RNA247)**</p><p>- Fecha y hora de la modificación</p>|
||19. Muestra el **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|20. Selecciona la opción **“Aceptar”**.|21. Cierra mensaje.|
||<p>22. Actualiza en la pantalla “Registro de servicios” la siguiente información de la tabla “Servicios estimados”:</p><p>&emsp;</p><p>- Monto estimado</p><p>- Cantidad de servicios estimados acumulados</p><p>- Monto estimado acumulado</p><p>- % de servicios estimados acumulados</p><p>- % de monto estimado acumulado</p>|
||23. Continúa en el paso [**16**](#_ref164288703) de este flujo.|

|<p></p><p><a name="fa09"></a>**FA09 Sección “Dictámenes asociados”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|<p>1. El **FA09** inicia cuando el Empleado SAT selecciona la sección **“Dictámenes asociados”**.</p><p></p>|<p>2. Consulta en la BD la siguiente información de todos los dictámenes relacionados al mismo Contrato, Proveedor y Periodo de control:</p><p>&emsp;</p><p>- Id del dictamen (enlace)</p><p>- Periodo de control</p><p>- Periodo inicio</p><p>- Periodo fin</p><p>- Estatus</p><p>- Monto</p><p>- Monto en pesos </p><p>- Tipo de cambio referencial</p>|
||<p>3. Muestra la sección “Dictámenes asociados” con la siguiente información:</p><p>&emsp;</p><p>&emsp;Opción:</p><p>- Exportar a Excel ![ref3]</p><p>&emsp;</p><p>Tabla “Dictámenes”. Aplica la **(RNA218)** y la **(RNA244)**</p><p>- Id del dictamen (enlace)</p><p>- Periodo de control</p><p>- Periodo inicio</p><p>- Periodo fin</p><p>- Estatus</p><p>- Monto</p><p>- Monto en pesos </p><p>- Tipo de cambio referencial</p><p></p><p>- Campos para “Filtrar” por columna</p><p></p><p>Ver **(17\_3083\_EIU\_CrearEstimacion)** Estilos 04.</p>|
|<p>4. <a name="_ref164288872"></a>Elige una opción:</p><p>&emsp;</p><p>- En caso de seleccionar el enlace de la columna **“Id del dictamen”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Exportar a Excel”**, continua en el **([**FA12**](#fa12))**.</p><p>&emsp;</p><p>- En caso de requerir **“Filtrar”** la información en alguna columna de la tabla, continúa en el **([**FA13**](#fa13))**.</p>|5. Se ejecuta el proceso del **(17\_3083\_ECU\_GenerarDictamen)**.|
||6. Fin del Caso de Uso.|

|<p></p><p><a name="fa10"></a>**FA10 Sección “Facturas asociadas”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA10** inicia cuando el Empleado SAT selecciona la sección **“Facturas asociadas”**.|<p>2. Consulta en la BD la siguiente información de todas las facturas relacionadas al mismo Contrato, Proveedor y Periodo de control:</p><p>&emsp;</p><p>- Id del dictamen</p><p>- Folio</p><p>- Monto SAT</p><p>- Monto Convenio de colaboración</p><p>- Estatus</p><p>- Tipo de cambio</p>|
||<p>3. Muestra la sección “Facturas asociadas” con la siguiente información:</p><p>&emsp;</p><p>&emsp;Opción</p><p>- Exportar a Excel ![ref3]</p><p></p><p>Tabla “Facturas”. Aplica la **(RNA219)** y la **(RNA244)**</p><p>- Id del dictamen (enlace)</p><p>- Comprobante fiscal</p><p>- Convenio de colaboración</p><p>- Monto</p><p>- Monto en pesos </p><p>- Estatus</p><p>- Tipo de cambio</p><p>&emsp;</p><p>- Campos para “Filtrar” por columna</p><p></p><p>Ver **(17\_3083\_EIU\_CrearEstimacion)** Estilos 05.</p>|
|<p>4. <a name="_ref169175121"></a>Elige una opción:</p><p>&emsp;</p><p>- En caso de seleccionar el enlace de la columna **“Id del dictamen”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Exportar a Excel”**, continua en el **([**FA12**](#fa12))**.</p><p>&emsp;</p><p>- En caso de requerir **“Filtrar”** la información en alguna columna de la tabla, continúa en el **([**FA13**](#fa13))**.</p>|5. Se ejecuta el proceso del **(17\_3083\_ECU\_GenerarDictamen)**.|
||6. Fin del Caso de Uso.|

|<p></p><p><a name="fa11"></a>**FA11 Opción “Inicial”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref164286484"></a>El **FA11** inicia cuando el Empleado SAT selecciona la opción **“Inicial”**.|2. Muestra el **([**MSG010**](#msg010))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona la opción **“Sí”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“No”**,** continúa en el paso [**8**](#_ref164283546)** de este flujo.</p>|4. <a name="_ref164236888"></a>Cierra el mensaje.|
||<p>5. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Estimación- Datos generales estimación</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id (estimación)</p><p>- Estatus</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA07**](#fa07))**.</p>|
||6. Actualiza en la BD y en pantalla el estatus de la estimación a “Inicial”.|
||<p>7. Habilita los datos de las secciones: </p><p>&emsp;</p><p>- Datos generales estimación</p><p>- Registro de servicios</p><p></p><p>Y continúa en el paso [**7**](#_ref164199342) del **([**FA01**](#fa01))**.</p>|
||8. <a name="_ref164236897"></a><a name="_ref164283546"></a>Cierra el mensaje y permanece en el paso  **[**9**](#_ref168853696)** del **([**FA01**](#fa01))**.|

|<p></p><p><a name="fa12"></a>**FA12 Opción “Exportar a Excel”** </p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref164286706"></a>El **FA12** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Estimación- Sección donde fue invocado</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>Si fue invocado en la sección de “Registro de servicios”:</p><p>- Contrato</p><p>- Proveedor</p><p>- Id del concepto de servicio</p><p>Si fue invocado en la sección de “Dictámenes asociados”:</p><p>- Contrato</p><p>- Proveedor</p><p>- Id del dictamen</p><p>Si fue invocado en la sección de “Facturas asociadas”:</p><p>- Contrato</p><p>- Proveedor</p><p>- Comprobante fiscal (Número de folio)</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>3. Obtiene <a name="_ref164008709"></a>toda la información del resultado de la consulta de donde fue seleccionada de la opción y los siguientes datos:</p><p>&emsp;</p><p>- Id (estimación)</p><p>- Contrato</p><p>- Proveedor</p><p>- Periodo de control</p>|
||4. Genera un archivo de Excel con extensión (.xlsx) que contiene la información obtenida en el paso anterior.|
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||6. Fin del Caso de Uso.|

|<p></p><p><a name="fa13"></a>**FA13 Se requiere filtrar la información de alguna columna de las tablas**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA13** inicia cuando el Empleado SAT requiere **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||<p>5. Realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso [16](#_ref169260088) del **([**FA08**](#fa08))**, continúa en el paso [**16**](#_ref169260088)** del **([**FA08**](#fa08))**.</p><p>- Si fue invocado en el paso [4](#_ref164288872) del **([**FA09**](#fa09))**, continúa en el paso [**4**](#_ref164288872)** del **([**FA09**](#fa09))**.</p><p>- Si fue invocado en el paso [4](#_ref169175121) del **([**FA10**](#fa10))**, continúa en el paso **[**4**](#_ref169175121)** de **([**FA10**](#fa10))**.</p>|

|<p></p><p><a name="fa14"></a>**FA14 Opción “Volumetría estimada” cambio de estatus**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA14** inicia cuando el Empleado SAT selecciona la opción **“Volumetría estimada”**.|<p>2. <a name="_ref164198511"></a>Valida que no se tengan campos vacíos en la sección y continúa en el paso [**5**](#_ref164201108) de este flujo:</p><p>&emsp;</p><p>- Registro de servicios</p><p></p><p>- En caso de identificar que falta información por capturar, muestra el mensaje **([**MSG009**](#msg009))**, con las opciones “Sí” y “No”.</p>|
|<p>3. Selecciona la opción **“Sí”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“No”**, continúa en el paso [**10**](#_ref169260937)** del **([**FA08**](#fa08))**.</p>|4. <a name="_ref164201308"></a><a name="_ref164201162"></a>Cierra el mensaje y rellena con cero (0) los campos vacíos por cada concepto de servicios.|
||<p>5. <a name="_ref164201108"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Estimación- Registro de servicios</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de estimación</p><p>- Estatus</p><p>- Contrato</p><p>- Proveedor</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA07**](#fa07))**.</p>|
||<p>6. Actualiza en la BD la siguiente información.</p><p>&emsp;</p><p>&emsp;Datos generales</p><p>- Estatus = Estimado</p><p>&emsp;</p><p>Registro de servicios</p><p>- Id (estimación)</p><p>- Contrato</p><p>- Id (concepto de servicio) </p><p>- Precio unitario</p><p>- Cantidad de servicios estimados</p><p>&emsp;</p><p>Datos del Empleado SAT</p><p>- Nombre del Empleado SAT. Aplica la **(RNA247)**</p><p>- Fecha y hora de la modificación</p>|
||<p>7. Inhabilita los datos y opciones de las secciones: </p><p>&emsp;</p><p>- Datos generales estimación</p><p>- Registro de servicios</p><p>Mantiene habilitadas las opciones:</p><p>- Inicial</p><p>- Duplicar estimación ![ref1]</p><p>- Cancelar estimación ![ref2]</p><p>- Exportar</p>|
||<p>19. Muestra actualizada la pantalla de “Consumo de Servicios - Estimación” con la información de solo lectura:</p><p>&emsp;</p><p>&emsp;“Datos generales estimación”. </p><p>- Id</p><p>- Nombre corto del contrato\*</p><p>- Número de contrato</p><p>- Proveedor\*</p><p>- Estatus. Aplica la **(RNA209)**</p><p>- Periodo de inicio\*</p><p>- Periodo fin\*</p><p>- Periodo de control\*</p><p>- IVA</p><p>- Tipo de cambio referencial</p><p>- Monto estimado total</p><p>- Monto estimado total en pesos</p><p>Opciones:</p><p>- Cancelar (inhabilitada)</p><p>- Guardar (inhabilitada)</p><p>- Inicial. Aplica la **(RNA210)**</p><p>- Duplicar estimación ![ref1]. Aplica la **(RNA210)**</p><p>- Cancelar estimación ![ref2]. Aplica la **(RNA210)**</p><p></p><p>Secciones colapsadas:</p><p>- Registro de servicios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p></p><p>Opción:</p><p>- Regresar</p><p>&emsp;</p><p>Ver **(17\_3083\_EIU\_CrearEstimación)** Estilos 02.</p>|
||8. <a name="_ref164284285"></a>Continúa en el paso [**9**](#_ref168853696)** del **([**FA01**](#fa01))**.|

|<p></p><p><a name="fa15"></a>**FA15 Opción “Duplicar estimación”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA15** inicia cuando el Empleado SAT selecciona la opción **“Duplicar estimación”**.|<p>2. Obtiene de la BD la siguiente información registrada de la estimación a duplicar:</p><p>&emsp;</p><p>- Datos generales estimación</p><p>- Registro de servicios</p>|
||3. Genera el “Id” de la nueva estimación.  Aplica la **(RNA221)**|
||<p>4. Muestra actualizada, la pantalla de “Consumo de Servicios - Estimación” con la información precargada y con el nuevo id (estimación):</p><p>&emsp;</p><p>&emsp;“Datos generales estimación”. Aplica la **(RNA206)**</p><p>- Id</p><p>- Nombre corto del contrato\*</p><p>- Número de contrato</p><p>- Proveedor\*</p><p>- Estatus. Aplica la **(RNA209)**</p><p>- Periodo de inicio\*</p><p>- Periodo fin\*</p><p>- Periodo de control\*</p><p>- IVA</p><p>- Tipo de cambio referencial</p><p>- Monto estimado total</p><p>- Monto estimado total en pesos</p><p>Opciones:</p><p>- Guardar</p><p>Opciones inhabilitadas:</p><p>- Inicial</p><p>- Duplicar estimación ![ref1]</p><p>- Cancelar estimación ![ref2]</p><p></p><p>Secciones colapsadas e inhabilitadas:</p><p>- Registro de servicios</p><p>- Dictámenes asociados</p><p>- Facturas asociadas</p><p></p><p>Opción:</p><p>- Regresar</p><p>&emsp;</p><p>Ver **(17\_3083\_EIU\_CrearEstimación)** Estilos 02.</p>|
|5. Si requiere, modifica los datos de la sección **“Datos generales estimación”**.||
|<p>6. <a name="_ref169089294"></a>Elige una opción:</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Guardar”**, continúa en el paso [**10**](#_ref169019576) del **([**FA01**](#fa01))** .</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Regresar”**, continúa en el **([**FA03**](#fa03))**.</p>||
||7. Fin del flujo alterno. |

|<p></p><p><a name="fa16"></a>**FA16 Opción “Cancelar estimación”** </p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref164286792"></a>El **FA16** inicia cuando el Empleado SAT selecciona la opción **“Cancelar estimación”**.|2. Muestra el **([**MSG008**](#msg008))** con las opciones “Sí” y “No”.|
|<p>3. Selecciona la opción **“Sí”** y** el flujo continúa.</p><p></p><p>- En caso de seleccionar **“No”**, continúa en el paso [**9**](#_ref168853696) del **([**FA01**](#fa01))**.</p>|<p>4. <a name="_ref164248396"></a>Cierra el mensaje y muestra la ventana emergente “Justificación de la cancelación”.</p><p>&emsp;Opciones:</p><p>- Aceptar</p><p>- Cerrar</p><p></p><p>Ver **(17\_3083\_EIU\_CrearEstimación)** Estilos 06.</p>|
|5. Captura la justificación la justificación de la cancelación de la estimación.||
|<p>6. Selecciona la opción **“Aceptar”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Cerrar”** regresa al paso [**9**](#_ref168853696) del **([**FA01**](#fa01))**.</p>|<p>7. <a name="_ref164248387"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Estimación- Datos generales estimación</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **UPDT** (Modificar)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id (estimación)</p><p>- Estatus “Cancelado”</p><p></p><p>- En caso de que no se puedan almacenar las Pistas de Auditoría, continúa en el **([**FA07**](#fa07))**.</p>|
||8. Actualiza en la BD el estatus a “Cancelado” y la “Justificación”.|
||<p>9. Muestra la pantalla “Consumo de servicios – Estimación” con toda la información inhabilitada de las secciones:</p><p>&emsp;</p><p>- Datos generales (muestra el campo “Justificación de la cancelación”)</p><p>- Registro de servicios</p><p></p><p>Opciones inhabilitadas:</p><p>- Duplicar estimación ![ref1]</p><p>- Cancelar estimación ![ref2]</p><p>Mantiene habilitada la opción:</p><p>- Inicial. Aplica la **(RNA210)**</p><p>- Regresar</p>|
|<p>10. Selecciona la opción:</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Regresar”**, el flujo continúa.</p><p>- En caso de seleccionar la opción **“Inicial”**, continúa en el **([**FA11**](#fa11))**.</p>|11. Continúa en la pantalla principal del **(17\_3083\_ECU\_AdministrarDevengado)**.|

|<p></p><p><a name="fa17"></a>**FA17 Opción “Cancelar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA17** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”**.|2. Muestra el **([**MSG002**](#msg002))** con** las opciones “Sí” y No”. |
|<p>3. Selecciona la opción **“Sí**” y el flujo continúa. </p><p>&emsp;</p><p>- En caso de seleccionar **“No”**, continúa en el paso** donde fue invocado.</p>|4. Cierra el mensaje. |
|  |5. Regresa los campos al último estado guardado.|
|  |<p>6. Realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso  [8](#_ref169101403) del Flujo primario, continúa en el paso **[**3**](#_ref169101538)**  del Flujo primario.</p><p>- Si fue invocado en el paso [9](#_ref168853696) del **([**FA01**](#fa01))**, continúa en el paso [**3**](#_ref169101090)** del **([**FA01**](#fa01))**.</p>|

|<p></p><p><a name="fa18"></a>**FA18 Opción “Actualizar precio unitario”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA18** inicia cuando el Empleado SAT selecciona la opción **“Actualizar precio unitario”**.|2. Consulta en la BD el “Número de convenio” de todos los convenios modificatorios del contrato asociado a la estimación.|
||<p>3. Muestra la ventana emergente “Seleccione un convenio modificatorio” con la siguiente información.</p><p>&emsp;</p><p>- Nombre corto del contrato</p><p>- Listado de convenios</p><p></p><p>Opciones:</p><p>- Aceptar</p><p>- Cerrar</p><p></p><p>Ver **(17\_3083\_EIU\_CrearEstimación)** Estilos 07.</p>|
|4. Selecciona un convenio modificatorio.||
|<p>5. Selecciona la opción **“Aceptar”** y el flujo continúa.</p><p>&emsp;</p><p>- En caso de seleccionar la opción **“Cerrar”** regresa al paso [**16**](#_ref169260088) del **(FA08).**</p>|6. Consulta y obtiene de la BD el “Precio unitario” registrado en el convenio modificatorio seleccionado. |
||7. Calcula el “Monto estimado”. Aplica la **(RNA212)**|
||8. Calcula la “Cantidad de servicios estimados acumulados”. Aplica la **(RNA211)**|
||9. Calcula el “Monto estimado acumulado”. Aplica la **(RNA213)**|
||10. Calcula el “% de servicios estimados acumulados”. Aplica la **(RNA214)**|
||11. Calcula el “% de monto estimado acumulado”. Aplica la **(RNA215)**|
||12. Actualiza la sección de “Registro de servicios”, mostrando el “Precio unitario con el dato obtenido de la consulta anterior y el resultado de los cálculos.|
||13. Continúa en el paso [**16**](#_ref169260088)  del **([**FA08**](#fa08))**.|

|<p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc169691067"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_CrearEstimación</p><p>- 17\_3083\_ECU\_AdministrarDevengado</p><p>- 17\_3083\_ECU\_GenerarDictamen</p><p>&emsp;</p><p></p>|
|<h3><a name="_toc169691068"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Información almacenada exitosamente.|
|<a name="msg002"></a>**MSG002**|Se perderá la información capturada ¿Desea continuar?|
|<a name="msg003"></a>**MSG003**|Favor de ingresar los datos obligatorios marcados con un asterisco (\*).|
|<a name="msg004"></a>**MSG004**|El periodo seleccionado es incorrecto.|
|<a name="msg005"></a>**MSG005**|Ya existe una estimación, favor de verificar los datos.|
|<a name="msg006"></a>**MSG006**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|¿Desea cancelar la estimación?|
|<a name="msg009"></a>**MSG009**|¿Desea rellenar los campos vacíos con cero?|
|<a name="msg010"></a>**MSG010**|¿Está seguro de reabrir la estimación?|

|<p></p><p></p>|
| - |
|<h3><a name="_toc169691069"></a>**10. Requerimientos No Funcionales** </h3>|
||

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001**|Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|
|**RNF002**|Concurrencia|<p>El número de Empleados SAT que puede tener el sistema son 150.</p><p></p><p>El número de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT.</p>|
|**RNF003**|Seguridad|El acceso solo podrá ser otorgado a todo Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema.|
|**RNF004**|Usabilidad|<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación: </p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual, como guía para el Empleado SAT.</p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado. </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales.</p>|
|**RNF005**|Eficiencia|Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT.|
|**RNF006**|Usabilidad|<p>El Empleado SAT debe poder navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones de 15, 50 y 100:</p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta).</p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta).</p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).</p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual, con el resultado de la consulta).</p><p>&emsp;</p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente.</p>|
|**RNF007**|Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.|
|**RNF08**|Fiabilidad|El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema.|
|**RNF009**|Seguridad|Se debe mantener la información en pantalla en caso de un error al guardar las Pistas de Auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema.|
|**RNF010**|Integridad|Al almacenar la información en la BD de tipo texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc169691070"></a>**11. Diagrama de actividad** </h3>|
|<p>![](Aspose.Words.9941e767-75ce-4ccc-bf06-f98de4292bbc.007.png)</p><p></p>|
|<h3><a name="_toc169691071"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>![](Aspose.Words.9941e767-75ce-4ccc-bf06-f98de4292bbc.008.png)</p>|



|<h3><a name="_toc169691072"></a>**13. Aprobación del cliente** </h3>|
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

|||Página 1 de 32|
| :- | :-: | -: |

[ref1]: Aspose.Words.9941e767-75ce-4ccc-bf06-f98de4292bbc.003.png
[ref2]: Aspose.Words.9941e767-75ce-4ccc-bf06-f98de4292bbc.004.png
[ref3]: Aspose.Words.9941e767-75ce-4ccc-bf06-f98de4292bbc.006.png
