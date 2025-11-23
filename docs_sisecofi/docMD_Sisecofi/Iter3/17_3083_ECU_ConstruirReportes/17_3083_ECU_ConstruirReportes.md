|![](Aspose.Words.15efcb08-50ff-4e8f-81c6-410523ffe82a.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_ConstruirReportes.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :- | :-: | :-: |
|*1*|*Creación del documento*|Eric Hector Pérez Pérez|*11/07/2024*|
|*1.1*|*Revisión del documento*|Diana Yazmín Pérez Sabido|*22/07/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*22/07/2024*|



**Tabla de Contenido**

[17_3083_ECU_ConstruirReportes	2](#_toc172549425)

[1. Descripción	2](#_toc172549426)

[2. Diagrama del Caso de Uso	2](#_toc172549427)

[3. Actores	2](#_toc172549428)

[4. Precondiciones	2](#_toc172549429)

[5. Post condiciones	3](#_toc172549430)

[6. Flujo primario	3](#_toc172549431)

[7. Flujos alternos	11](#_toc172549432)

[8. Referencias cruzadas	14](#_toc172549433)

[9. Mensajes	14](#_toc172549434)

[10. Requerimientos No Funcionales	15](#_toc172549435)

[11. Diagrama de actividad	17](#_toc172549436)

[12. Diagrama de estados	17](#_toc172549437)

[13. Aprobación del cliente	18](#_toc172549438)






### **<a name="_toc172549425"></a>**17\_3083\_ECU\_ConstruirReportes

|<h3><a name="_toc172549426"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT construir reportes del sistema.</p><p></p>|
|<h3><a name="_toc172549427"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.15efcb08-50ff-4e8f-81c6-410523ffe82a.002.png)</p><p></p>|
|<h3><a name="_toc172549428"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc172549429"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa.</p><p>- Se le han asignado los roles requeridos al Empleado SAT para ingresar, al módulo “Construir Reportes”.</p><p>- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar al módulo “Construir Reportes”.</p><p>- El Empleado SAT ha ingresado al módulo “Construir Reportes”.</p><p>&emsp;</p>|
|<h3><a name="_toc172549430"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT construyó reportes.</p><p>&emsp;</p>|
|<h3><a name="_toc172549431"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El Caso de Uso inicia cuando el Empleado SAT ingresa a la sección **“Construir Reportes”**.|<p>2. Consulta en la base de datos (BD) la información de los siguientes catálogos. Aplica la regla de negocio **(RNA01)**.</p><p>&emsp;Sección: “Reporte” (Criterios de búsqueda)</p><p></p><p>Proyecto </p><p>- Estatus</p><p></p><p>Contrato</p><p>- Estatus</p><p>- Dominio tecnológico</p><p>- Convenio de colaboración</p><p></p><p>Proveedor</p><p>- Razón social</p><p>- Título de servicio</p><p></p>|
||<p>3. Muestra la pantalla “Construir Reportes” con los siguientes criterios:</p><p></p><p>Sección: “Criterios de búsqueda”</p><p></p><p>Proyecto </p><p>- Estatus</p><p>- Nombre corto</p><p></p><p>Contrato</p><p>- Estatus</p><p>- Nombre corto</p><p>- Dominio tecnológico</p><p>- Convenio de colaboración. Aplica la **(RNA168)**</p><p></p><p>Proveedor</p><p>- Razón social</p><p>- Título de servicio</p><p>&emsp;</p><p>Periodo</p><p>- Inicio</p><p>- Término</p><p>- Aplicación del periodo. Aplica la **(RNA170)**</p><p></p><p>Presentación de la Información</p><p>- Acumulada</p><p>- Mensual</p><p>&emsp;</p><p>Sección: “Campos para reporte”</p><p></p><p>Proyecto:</p><p></p><p>Datos Generales</p><p>- Id</p><p>- Nombre corto</p><p>- Estatus</p><p>- Nombre del proyecto</p><p>- Id AGP</p><p></p><p>Ficha Técnica</p><p>- Administración patrocinadora</p><p>- Nombre de la admón. Patrocinadora</p><p>- Administrador patrocinador</p><p>- Administración central patrocinadora</p><p>- Nombre de la admón. central patrocinadora</p><p>- Administrador central patrocinador</p><p>- Administración participante</p><p>- Nombre de la admón. Participante</p><p>- Administrador participante</p><p>- Clasificación del proyecto</p><p>- Financiamiento</p><p>- Tipo de procedimiento</p><p>- Líder del proyecto</p><p>- Puesto</p><p>- Correo</p><p>- Fecha inicio</p><p>- Fecha fin</p><p>- Estatus</p><p>- Alineación del proyecto</p><p>- Mapa</p><p>- Periodo</p><p>- Objetivo</p><p>- Fecha de inicio del proyecto</p><p>- Fecha fin del proyecto</p><p>- Área de planeación</p><p>- Monto solicitado</p><p>- Tipo de moneda</p><p>- Objetivo general</p><p>- Alcance</p><p></p><p>Proveedor:</p><p></p><p>- Nombre del proveedor</p><p>- Nombre comercial</p><p>- Giro de la empresa</p><p>- Directorio de contacto</p><p>- RFC</p><p>- Representante legal</p><p>- Título de servicio</p><p>- Vigencia</p><p>- Fecha vencimiento</p><p>- Cumple dictamen</p><p></p><p>Contrato:</p><p></p><p>- Estatus del contrato</p><p>- Id</p><p>- Nombre del contrato</p><p>- Nombre del proyecto</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Tipo de procedimiento</p><p>- Fecha inicio del contrato</p><p>- Fecha término del contrato</p><p>- Último CM</p><p>- Monto máximo</p><p>- Monto máximo de último CM</p><p>- Monto en pesos</p><p>- Administración central</p><p>- Administrador del contrato</p><p></p><p>Convenio Modificatorio</p><p>- Número de convenio</p><p>- Tipo de convenio</p><p>- Fecha de firma</p><p>- Fecha fin de servicio</p><p>- Fecha fin de contrato con CM</p><p>- Cálculo de días naturales</p><p>- Incremento</p><p>- Subtotal</p><p>- IEPS</p><p>- IVA</p><p>- Tipo de cambio</p><p>- Monto máximo del contrato con CM sin impuestos</p><p>- Monto máximo del contrato con CM con impuestos</p><p>- Monto en pesos</p><p>- Comentarios</p><p></p><p>Concepto de servicio:</p><p></p><p>- Id</p><p>- Grupo</p><p>- Tipo de consumo</p><p>- Conceptos de servicio</p><p>- Tipo de unidad</p><p>- Precio unitario</p><p>- Cantidad de servicios mínima</p><p>- Cantidad de servicios máxima</p><p>- Monto mínimo</p><p>- Monto máximo</p><p>- Aplica IEPS</p><p>- Cantidad de servicios máximos del último CM</p><p>- Monto máximo del último CM</p><p>&emsp;</p><p>Planeado</p><p>- Cantidad de servicios planeado</p><p>- Monto planeado</p><p>- % Servicios planeados acumulados</p><p>- % Monto planeado acumulado</p><p></p><p>Estimado</p><p>- Cantidad de servicios estimado</p><p>- Monto estimado</p><p>- % Servicios estimados acumulados</p><p>- % Monto estimado acumulado</p><p>&emsp;</p><p>Dictaminado</p><p>- Cantidad de servicios dictaminado</p><p>- Monto dictaminado</p><p>- % Servicios dictaminados acumulados</p><p>- % Monto dictaminado acumulado</p><p></p><p>Pagado</p><p>- Cantidad de servicios pagados</p><p>- Monto pagado</p><p>- % Servicios pagados acumulados</p><p>- % Monto pagado acumulado</p><p></p><p>Estimaciones:</p><p></p><p>- Id</p><p>- Nombre corto del contrato</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Estatus</p><p>- Periodo de inicio</p><p>- Periodo fin</p><p>- Periodo de control</p><p>- IVA</p><p>- Tipo de cambio referencial</p><p>- Monto estimado total</p><p>- Monto estimado total en pesos</p><p>- Justificación</p><p></p><p>Dictamen:</p><p></p><p>- Id</p><p>- Nombre corto del contrato</p><p>- Número de contrato</p><p>- Proveedor</p><p>- Estatus</p><p>- Periodo de inicio</p><p>- Periodo fin</p><p>- Periodo de control</p><p>- IVA</p><p>- Tipo de cambio referencial</p><p>- Descripción</p><p>- Fase</p><p>- Subtotal</p><p>- Deducciones</p><p>- IEPS</p><p>- Otros impuestos</p><p>- Total</p><p>- Total en pesos</p><p>&emsp;</p><p>Penalizaciones / Deducciones:</p><p></p><p>- Tipo</p><p>- Tipo de informe</p><p>- Documento</p><p>- Descripción</p><p>- Desglose</p><p>- Concepto de servicio</p><p>- Monto</p><p>- Periodo inicio</p><p>- Periodo término</p><p>- Periodo control</p><p></p><p>Facturas:</p><p></p><p>- Folio</p><p>- Comprobante fiscal</p><p>- Fecha de facturación</p><p>- Estatus</p><p>- Moneda</p><p>- Tasa</p><p>- Subtotal</p><p>- IEPS</p><p>- IVA</p><p>- Otros impuestos</p><p>- Total facturado </p><p>- Total facturado en pesos</p><p>- Total pagado en pesos</p><p>- ` `Comentarios</p><p></p><p>Desglose de Montos:</p><p></p><p>- % SAT</p><p>- Monto</p><p>- Monto en pesos</p><p>- % Convenio de colaboración</p><p>- Monto</p><p>- Monto en pesos</p><p>- Ficha NAFIN</p><p>- Fecha NAFIN</p><p>- Tipo de cambio NAFIN</p><p></p><p>Notas de Crédito:</p><p></p><p>- Folio</p><p>- Comprobante fiscal</p><p>- Fecha de generación</p><p>- Estatus</p><p>- Moneda</p><p>- Tasa</p><p>- Subtotal</p><p>- IEPS</p><p>- IVA</p><p>- Otros impuestos</p><p>- Total</p><p>- Total en pesos</p><p>- Comentarios</p><p></p><p>Desglose de Montos:</p><p></p><p>- % SAT</p><p>- Monto</p><p>- Monto en pesos</p><p>- % Convenio de colaboración</p><p>- Monto</p><p>- Monto en pesos</p><p></p><p>Tabla:</p><p>- Reporte. Aplica la **(RNA171)**</p><p></p><p>Opciones:</p><p></p><p>- Exportar a Excel ![ref1] Aplica la **(RNA171)**</p><p>- Buscar</p><p>- Limpiar Campos</p><p></p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ConstruirReportes)** Estilos 01.</p>|
|4. <a name="_ref171346577"></a>Ingresa por lo menos un campo de la sección **“Criterios de búsqueda”**.|5. Aplica **(RNA155)** de acuerdo con el campo(s) ingresado en la sección “Criterios de búsqueda”.|
|6. Ingresa por lo menos un campo de la sección **“Campos para reporte”**.||
|<p>7. <a name="_ref164151376"></a>Selecciona la opción **“Buscar”** y continúa en el flujo.</p><p>&emsp;</p><p>- Si selecciona la opción **“Limpiar Campos”**,** continúa en el flujo alterno **([**FA03**](#fa03))**.</p>|<p>8. Valida que se haya ingresado como mínimo un criterio de búsqueda y un campo para reporte.</p><p>&emsp;</p><p>- En caso contrario que no haya ingresado al menos un criterio de búsqueda y un campo para reporte continúa en el **([**FA01**](#fa01))**.</p>|
||<p>9. Valida que el periodo de las fechas sea correcto.</p><p>&emsp;</p><p>- En caso de no ser correcto el periodo de las fechas continúa en el **([**FA07**](#fa07))**.</p>|
||<p>10. <a name="_ref163994799"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Construir Reportes</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM: SS</p><p>**RFC Usuario**= RFC largo del usuario que ingresó al sistema.</p><p>**Tipo de movimiento**= **CNST** (Consulta).</p><p>**Movimiento**= </p><p></p><p>- Criterios de búsqueda</p><p>- Campos para reporte</p><p>&emsp;</p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>11. Muestra la pantalla “Procesando” con el mensaje **([**MSG006**](#msg006))**. </p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_ConstruirReportes)** Estilos 02.</p>|
||<p>12. Consulta en la BD la información coincidente con los criterios de búsqueda y los campos para el reporte.</p><p>&emsp;</p><p>- En caso de ingresar al menos un criterio de búsqueda y un campo para el reporte, consulta en la BD de acuerdo con los elementos ingresados y el flujo continúa.</p><p></p><p>- En caso de que no se obtenga información de la consulta, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>13. Muestra en la tabla “Reporte”, las columnas con base a los campos seleccionados en la sección “Campos para reporte” y la información con base a los campos seleccionados en la sección “Criterios de búsqueda”.</p><p></p><p>Muestra lo siguiente:</p><p></p><p>Opciones:</p><p>- Exportar a Excel ![ref1]</p><p></p>|
|<p>14. <a name="_ref171346777"></a>Selecciona una opción:</p><p>&emsp;</p><p>- En caso de seleccionar **“Exportar a Excel”**,** continúa en el flujo alterno **([**FA05**](#fa05))**.</p><p>- En caso de seleccionar **“Filtrar”** la información en alguna columna de la tabla, continúa en el **([**FA06**](#fa06))**.</p><p>- En caso de seleccionar **“Limpiar Campos”**,** continúa en el flujo alterno **([**FA03**](#fa03))**.</p>||
||15. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
||
|<h3><a name="_toc172549432"></a>**7. Flujos alternos** </h3>|

|<p></p><p><a name="fa01"></a>**FA01 No se ingresó por lo menos un criterio de búsqueda y campo para reporte**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA01** inicia cuando el sistema identifica que no se ingresó al menos un criterio de búsqueda y un campo para reporte.|
||2. Muestra el mensaje **([**MSG001**](#msg001))**, con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Regresa al paso [**4**](#_ref171346577) del Flujo primario. |

|<p></p><p></p><p><a name="fa02"></a>**FA02 No se pueden almacenar las Pistas de Auditoría**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA02** inicia cuando interviene un evento ajeno y no se puedan almacenar las Pistas de Auditoría. |
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>3. Muestra el mensaje de acuerdo con lo siguiente:</p><p></p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST**, se muestra el **([**MSG002**](#msg002))**.</p><p></p><p>- En caso de que la pista de auditoría es por el tipo de movimiento **PRNT**, se muestra el **([**MSG003**](#msg003))**.</p><p>&emsp;</p><p>&emsp;Cada mensaje se muestra con la opción “Aceptar”.</p>|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Regresa al paso previo que detona la acción de la pista de auditoría.|

|<p></p><p><a name="fa03"></a>**FA03 Selecciona la opción “Limpiar campos”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA03** inicia cuando el Empleado SAT selecciona la opción **“Limpiar campos”**|2. Borra la información ingresada en los criterios de búsqueda y campos para reporte dejándolos en el estado inicial de selección. |
||<p>3. Oculta lo siguiente:</p><p></p><p>Opciones:</p><p>- Exportar a Excel ![ref1]</p><p></p><p>Tabla “Reporte”</p><p></p>|
||4. Regresa al paso [**4**](#_ref171346577) del Flujo primario.|

|<p></p><p><a name="fa04"></a>**FA04 No existen resultados que coincidan en la búsqueda**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA04** inicia cuando el sistema identifica que no existen coincidencias con los criterios de búsqueda.|
||2. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”. |
|3. Selecciona la opción **“Aceptar”**.|<p>Cierra el mensaje y oculta lo siguiente:</p><p></p><p>Opciones:</p><p>- Exportar a Excel ![ref1]</p><p></p><p>Tabla “Reporte”</p><p></p>|
||4. Regresa al paso [**4**](#_ref171346577) del Flujo primario.|

|<p></p><p><a name="fa05"></a>**FA05 Selecciona la opción “Exportar a Excel”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA05** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. <a name="_ref164004953"></a>Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp; </p><p>&emsp;Datos que se almacenan:</p><p>**Módulo**= Construir Reportes</p><p>**Fecha y Hora**= Fecha y hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= </p><p>**PRNT** (Imprimir)</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p></p><p>- Criterios de búsqueda </p><p>&emsp;</p><p>- En caso de que no se puedan almacenar las Pista de Auditoría, continúa en el **([**FA02**](#fa02))**.</p>|
||<p>3. Obtiene la siguiente información:</p><p>&emsp;- Reporte</p>|
||4. Genera un archivo de Excel con extensión (.xlsx) que contenga la información obtenida.|
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||6. Fin del Caso de Uso.|

|<p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Filtrar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT requiere **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||5. Continúa en el paso [**14**](#_ref171346777) del Flujo primario.|

|<p></p><p><a name="fa07"></a>**FA07 Fechas incorrectas**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA07** inicia cuando el sistema identifica que el valor del campo “Fecha inicio” es mayor a la “Fecha fin” o “Fecha fin es menor a la “Fecha inicio”. |
||2. Muestra el **([**MSG005**](#msg005))** con la opción “Aceptar” y marca el campo de la fecha incorrecta en color rojo.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso [**4**](#_ref171346577) del Flujo primario.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc172549433"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p>- 17\_3083\_EIU\_ConstruirReportes</p><p>&emsp;</p>|
|<h3><a name="_toc172549434"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Favor de ingresar como mínimo un criterio de búsqueda y un campo para reporte.|
|<a name="msg002"></a>**MSG002**|Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).|
|<a name="msg003"></a>**MSG003**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg004"></a>**MSG004**|No se encontraron resultados de la búsqueda.|
|<a name="msg005"></a>**MSG005**|La fecha ingresada es incorrecta.|
|<a name="msg006"></a>**MSG006**|Procesando...|

|<p></p><p></p>|
| - |
|<h3><a name="_toc172549435"></a>**10. Requerimientos No Funcionales** </h3>|

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
|**RNF008**|Usabilidad|<p>Usabilidad, El Empleado SAT podrá navegar a través de las páginas resultantes del documento PDF: </p><p>- Ir a la siguiente página (debe mostrar la página consecutiva del documento PDF). </p><p>- Ir a la página anterior (debe mostrar la página previa del documento PDF).</p>|
|**RNF009**|Fiabilidad|El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema.|
|**RNF010**|Seguridad |Mantener la información en pantalla en caso de un error al guardar las pistas de auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierden los datos ya que no están controlados por el sistema. |
|**RNF011**|Integridad |Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena. |

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc172549436"></a>**11. Diagrama de actividad** </h3>|
|<p></p><p>![](Aspose.Words.15efcb08-50ff-4e8f-81c6-410523ffe82a.004.png)</p><p></p>|
|<h3><a name="_toc172549437"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no se requiere para este proceso.</p><p></p>|
|<h3><a name="_toc172549438"></a>**13. Aprobación del cliente** </h3>|
||

|<a name="_hlk159937993"></a>**FIRMAS DE CONFORMIDAD**||
| :-: | :- |
|**Firma 1**|**Firma 2**|
|**Nombre**: Diana Yazmín Pérez Sabido.|**Nombre**: Rodolfo López Meneses.|
|**Puesto**: Usuaria ACPPI.|**Puesto**: Usuario ACPPI.|
|**Fecha:**|**Fecha:**|
|||
|**Firma 3**|**Firma 4**|
|**Nombre**: Rubén Delgado Ramírez.|**Nombre**: María del Carmen Castillejos Cárdenas.|
|**Puesto**: Usuario ACPPI.|**Puesto**: Usuaria ACPPI.|
|**Fecha:**|**Fecha:**|
|||
|**Firma 5**|**Firma 6**|
|**Nombre:** Alejandro Alfredo Muñoz Núñez.|**Nombre**: Erick Villa Beltrán.|
|**Puesto:** RAPE ACPPI.|**Puesto**: Líder APE SDMA 6.|
|**Fecha**:|**Fecha**:|
|||
|**Firma 7**|**Firma 8**|
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:** Eric Hector Pérez Pérez.|
|**Puesto:** Líder Técnico SDMA 6.|**Puesto:** Analista de Sistemas DS SDMA 6.|
|**Fecha**:|**Fecha**:|
|||

||
| :- |

|||Página 3 de 17|
| :- | :-: | -: |

[ref1]: Aspose.Words.15efcb08-50ff-4e8f-81c6-410523ffe82a.003.png
