|![](Aspose.Words.ef82187c-5a78-46c4-80b2-6b8d8b79cbd6.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|<p>Fecha de aprobación del Template:</p><p>02/08/2023</p>|<p>**Especificación del Caso de Uso**</p><p>17\_3083\_ECU\_AsociarFasesMatrizDoc.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |

**<ID Requerimiento>**8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


**Tabla de Versiones y Modificaciones**

|<p></p><p><a name="tabla_versiones"></a>Versión</p><p></p>|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :-: | :-: | :-: |
|*1*|*Creación del documento*|Eduardo Acosta Mora|*01/02/2024*|
|*1.1*|*Revisión del documento*|<p>Luis Angel Olguin </p><p>Castillo</p>|*10/03/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|<p>06/06/2024</p><p></p>|



**Tabla de Contenido**

[17_3083_ECU_AsociarFasesMatrizDoc	2](#_toc168584919)

[1. Descripción	2](#_toc168584920)

[2. Diagrama del Caso de Uso	2](#_toc168584921)

[3. Actores	2](#_toc168584922)

[4. Precondiciones	2](#_toc168584923)

[5. Post condiciones	3](#_toc168584924)

[6. Flujo primario	3](#_toc168584925)

[7. Flujos alternos	6](#_toc168584926)

[8. Referencias cruzadas	11](#_toc168584927)

[9. Mensajes	11](#_toc168584928)

[10. Requerimientos No Funcionales	12](#_toc168584929)

[11. Diagrama de actividad	14](#_toc168584930)

[12. Diagrama de estados	14](#_toc168584931)

[13. Aprobación del cliente	15](#_toc168584932)


### **<a name="_toc168584919"></a>**17\_3083\_ECU\_AsociarFasesMatrizDoc

|<h3><a name="_toc168584920"></a>**1. Descripción** </h3>|
| :- |
|<p></p><p>El objetivo de este Caso de Uso es permitir al Empleado SAT asociar al proyecto la plantilla correspondiente a la fase.</p><p></p>|
|<h3><a name="_toc168584921"></a>**2. Diagrama del Caso de Uso**</h3>|
|<p></p><p>![](Aspose.Words.ef82187c-5a78-46c4-80b2-6b8d8b79cbd6.002.png)</p><p></p>|
|<h3><a name="_toc168584922"></a>**3. Actores** </h3>|
||

|**Actor**|**Descripción**|
| :-: | :-: |
|**Empleado SAT**|El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar cada uno de los módulos de este sistema.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc168584923"></a>**4. Precondiciones**</h3>|
|<p></p><p>- El Empleado SAT se ha autenticado en el sistema con e.firma válida.</p><p>- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa al sistema.</p><p>- Se le han asignado el rol requerido al Empleado SAT para ingresar al módulo “Sistema”, submódulo “Proyectos” y a la sección “Asociar fases”.</p><p>- El sistema ha validado que el Empleado SAT cuenta con el rol para ingresar al módulo “Sistema”, submódulo “Proyectos” y a la sección “Asociar fases”.</p><p>- Se han almacenado la o las plantillas de acuerdo con la fase.</p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
|<h3><a name="_toc168584924"></a>**5. Post condiciones** </h3>|
|<p></p><p>- El Empleado SAT asoció la fase y su plantilla correspondiente al proyecto.</p><p>- El Empleado SAT modificó la fase y su plantilla correspondiente al proyecto.</p><p>- El Empleado SAT desasoció la fase y su plantilla correspondiente al proyecto.</p><p></p>|
|<h3><a name="_toc168584925"></a>**6. Flujo primario**</h3>|
||

|**Actor**|**Sistema**|
| :-: | :-: |
|1. <a name="_ref164773001"></a>El Caso de Uso inicia cuando el Empleado SAT ingresa al módulo “Sistema”, submódulo “Proyectos” y a la sección **“Asociar fases”**.|2. Consulta de la base de datos (BD) la información de la fase y plantilla asociadas previamente para la tabla “Asociaciones”.|
||<p>3. Obtiene de la consulta anterior los siguientes datos para mostrarlos: </p><p>&emsp;</p><p>- Fase</p><p>- Plantilla</p><p>- Fecha de asignación</p>|
||<p>4. <a name="_ref164773044"></a>Muestra la pantalla “Asociar fases matriz documental” con los siguientes campos y opciones:</p><p>&emsp;</p><p>&emsp;Tabla (Asociaciones). Aplica la regla de negocio **(RNA244)**:</p><p>- Fase</p><p>- Plantilla</p><p>- Fecha de asignación</p><p>- Acciones</p><p>- Editar ![](Aspose.Words.ef82187c-5a78-46c4-80b2-6b8d8b79cbd6.003.png)</p><p>- Eliminar ![](Aspose.Words.ef82187c-5a78-46c4-80b2-6b8d8b79cbd6.004.png)</p><p></p><p>Opciones:</p><p>- Nuevo ![](Aspose.Words.ef82187c-5a78-46c4-80b2-6b8d8b79cbd6.005.png)</p><p>- Exportar a Excel ![](Aspose.Words.ef82187c-5a78-46c4-80b2-6b8d8b79cbd6.006.png)</p><p>- Guardar</p><p>- Cancelar</p><p>- Campos para “Filtrar” por columna</p><p></p><p>Ver **(17\_3083\_EIU\_AsociarFasesMatrizDoc)** Estilos 01.</p>|
|<p>5. <a name="_ref164773012"></a>Selecciona la opción **“Nuevo”**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Exportar a Excel”**, continúa en el flujo alterno **([**FA01**](#fa01))**.</p><p></p><p>- En caso de que seleccione la opción **“Editar”**, continúa en el **([**FA03**](#fa03))**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Eliminar”**, continúa en el **([**FA06**](#fa06))**.</p><p></p><p>- En caso de que seleccione la opción para **“Filtrar”** los campos de la tabla, continúa en el **([**FA08**](#fa08))**.</p>|6. Consulta en la BD la información en el catálogo correspondiente para mostrar en el campo “Fase” de acuerdo con la **(RNA01) y (RNA251)**.|
||<p>7. Dentro de la tabla “Asociaciones”, habilita los siguientes campos para su registro:</p><p>&emsp;</p><p>- Fase\*</p><p>- Plantilla\*</p><p>- Fecha de asignación\*</p><p>- Acciones </p><p>- Descartar ![ref1]</p><p></p><p>Ver</p><p>**(17\_3083\_EIU\_AsociarFasesMatrizDoc)** Estilos 01.</p>|
|8. <a name="_ref164773086"></a>Selecciona una opción del campo **“Fase”**.|9. Consulta en la BD las plantillas que se hayan creado para la fase seleccionada y las muestra en el campo “Plantilla”.|
|<p>10. <a name="_ref168559949"></a>Realiza lo siguiente:</p><p></p><p>- En caso de que seleccione una opción del campo **“Plantilla”** e ingresa un valor para el campo **“Fecha de asignación”**, el flujo continúa.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Descartar”**, continúa en el **([**FA02**](#fa02))**.</p>||
|<p>11. <a name="_ref164773030"></a>Selecciona la opción **“Guardar”**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Cancelar”**, continúa en el **([**FA02**](#fa02))**.</p>|<p>12. Valida que se haya seleccionado e ingresado información en los siguientes campos obligatorios de acuerdo con la **(RNA03)**:</p><p>&emsp;</p><p>- Fase\*</p><p>- Plantilla\*</p><p>- Fecha de asignación\*</p><p></p><p>- En caso de que no se haya seleccionado e ingresado información en los campos obligatorios, continúa en el **([**FA04**](#fa04))**.</p>|
||<p>13. Valida el valor del campo “Fecha de asignación” que no sea mayor al día actual.</p><p>&emsp;</p><p>- En caso de que la fecha sea incorrecta, el flujo continúa en el **([**FA09**](#fa09))**.</p>|
||<p>14. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>**Módulo**= Proyecto- Asociarfases</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **INSR** (Insertar) y/o **UPDT** (Modificar) y/o **DLT** (Borrar).</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de registro</p><p>- Id de proyecto</p><p>- Nombre de plantilla</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>15. Guarda en la BD correspondiente la siguiente información para asociarla al proyecto:</p><p>&emsp;</p><p>- Fase</p><p>- Plantilla</p><p>- Fecha de asignación</p>|
||16. Muestra mensaje **([**MSG001**](#msg001))** con la opción “Aceptar”.|
|17. Selecciona la opción **“Aceptar”**.|18. Cierra mensaje.|
||19. Fin del Caso de Uso.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc168584926"></a>**7. Flujos alternos** </h3>|
|<p></p><p><a name="fa01"></a>**FA01 Selecciona la opción “Exportar a Excel”**</p>|

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA01** inicia cuando el Empleado SAT selecciona la opción **“Exportar a Excel”**.|<p>2. Almacena en la BD la información de las Pistas de Auditoría.</p><p>&emsp;</p><p>&emsp;Datos que se almacenan: </p><p>**Módulo**= Proyecto -Asociarfases</p><p>**Fecha y Hora**= Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS</p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema.</p><p>**Tipo de movimiento**= **PRNT** (Imprimir).</p><p>**Movimiento**= Aplica la **(RNA239)**</p><p>- Id de proyecto</p><p>- Nombre de la plantilla</p><p></p><p>- En caso de que no se pueda almacenar las Pistas de Auditoría, continúa en el **([**FA05**](#fa05))**.</p>|
||<p>3. Obtiene la siguiente información de los proyectos como resultado de la consulta:</p><p>&emsp;</p><p>- Fase</p><p>- Plantilla</p><p>- Fecha de asignación</p>|
||4. Genera un archivo de Excel con extensión (.xlsx) con la información obtenida.|
||5. Descarga el archivo de Excel con extensión (.xlsx).|
||6. Continúa en el paso [**5**](#_ref164773012) del Flujo primario.|

|<p></p><p><a name="fa02"></a>**FA02 Selecciona la opción “Cancelar” o “Descartar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA02** inicia cuando el Empleado SAT selecciona la opción **“Cancelar”** o **“Descartar”**.|2. Muestra el **([**MSG002**](#msg002))** con las opciones “Sí” y “No”.|
|<p>3. Selección una opción: </p><p>&emsp;</p><p>- En caso de que seleccione la opción **“Sí”**, el flujo continúa.</p><p></p><p>- En caso de que seleccione la opción **“No”** desde el paso 10 del Flujo primario, continúa en el paso [**10**](#_ref168559949) del Flujo primario.</p><p></p><p>- En caso de que seleccione la opción **“No”** desde el paso 11 del Flujo primario, continúa en el paso [**11**](#_ref164773030) del Flujo primario.</p>|4. Cierra el mensaje.|
||<p>5. Dependiendo de la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en la opción “Cancelar” se inicializan los campos donde se selecciona la opción y no almacena ninguna información.</p><p>&emsp;</p><p>- Si fue invocado en la opción “Descartar”:</p><p>- Se inicializa el registro de la tabla de la sección donde fue invocado, y cambia a solo lectura si era un registro almacenado regresando los íconos a su estado original.</p><p>- Si era un registro nuevo elimina la fila.</p>|
||<p>6. Dependiendo la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- Si fue invocado en el paso 10 del Flujo primario, continúa en el paso [**5**](#_ref164773012) del Flujo primario.</p><p></p><p>- Si fue invocado en el paso 11 del Flujo primario, continúa en el paso [**4**](#_ref164773044) del Flujo primario.</p>|

|<p></p><p><a name="fa03"></a>**FA03 Selecciona la opción “Editar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA03** inicia cuando el Empleado SAT selecciona la opción **“Editar”**.|<p>2. Valida que en la gestión documental del proyecto no se haya cargado ningún archivo.</p><p>&emsp;</p><p>- En caso de que se haya cargado un archivo, continúa en el **([**FA07**](#fa07))**.</p>|
||3. Consulta en la BD la información en el catálogo correspondiente para mostrar en el campo “Fase” de acuerdo con la **(RNA01)** y **(RNA251)**.|
||<p>4. Muestra dentro de la tabla “Asociaciones”, los siguientes campos habilitados para su edición:</p><p>&emsp;</p><p>- Fase\*</p><p>- Plantilla\*</p><p>- Fecha de asignación\*</p><p>- Acciones</p><p>- ` `Descartar ![ref1]</p><p></p><p>**Nota:** Por defecto se debe mostrar la fase, plantilla y fecha de asignación que estaban almacenas en la BD.</p>|
||5. Continúa en el paso [**8**](#_ref164773086) del Flujo primario|

|<p></p><p><a name="fa04"></a>**FA04 No seleccionó e ingresó información en todos los campos obligatorios**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA04** inicia cuando no se seleccionó e ingresó información en todos los campos obligatorios.|
||2. Muestra el **([**MSG004**](#msg004))** con la opción “Aceptar”.|
||3. Muestra en rojo los campos pendientes de capturar.|
|4. Selecciona la opción **“Aceptar”**.|5. Cierra el mensaje.|
||6. Continúa en el paso [**8**](#_ref164773086) del Flujo primario. |

|<p></p><p><a name="fa05"></a>**FA05 No se pueden almacenar las Pistas de Auditoria**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA05** inicia cuando interviene un evento ajeno y no se pueda almacenar las Pistas de Auditoría.|
||2. Cancela la operación sin completar el movimiento que estaba en proceso.|
||<p>- Muestra el mensaje informativo de acuerdo con lo siguiente:</p><p></p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT** o **INSR** se muestra el **([**MSG007**](#msg007))**.</p><p></p><p>- En caso de que la pista de auditoría sea por el tipo de movimiento **PRNT**, se muestra el **([**MSG008**](#msg008))**.</p><p>&emsp;</p><p>- En caso de que la pista de auditoría es por el tipo de movimiento **DLT**, se muestra el **([**MSG009**](#msg009))**.</p><p></p><p>Cada mensaje con la opción “Aceptar”.</p>|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Regresa al paso previo que detona la acción de la pista de auditoría.|

|<p></p><p><a name="fa06"></a>**FA06 Selecciona la opción “Eliminar”**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA06** inicia cuando el Empleado SAT selecciona la opción **“Eliminar”**, sobre un registro.|<p>2. Valida que en la gestión documental no se haya cargado ningún archivo.</p><p>&emsp;</p><p>- En caso de que se haya cargado un archivo, continúa en el **([**FA07**](#fa07))**.</p>|
||3. Muestra el **([**MSG005**](#msg005))** con la opción “Sí” y “No”.|
|<p>4. Selecciona la opción **“Sí”**.</p><p>&emsp;</p><p>- En caso de que seleccione la opción **“No”**, continúa en el paso [**5**](#_ref164773012) del Flujo primario.</p>|5. Cierra el mensaje.|
||6. Actualiza en la pantalla la tabla “Asociaciones” ocultando el registro eliminado.|
||7. Continúa en el paso [**5**](#_ref164773012) del Flujo primario.|

|<p></p><p><a name="fa07"></a>**FA07 Ya se ha cargado un archivo en la gestión documental del proyecto**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA07** inicia cuando ya se ha cargado un archivo en la gestión documental del proyecto.|
||2. Muestra el **([**MSG006**](#msg006))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**. |4. Cierra el mensaje.|
||<p>5. Dependiendo la situación, realiza lo siguiente:</p><p>&emsp;</p><p>- En caso de que haya sido llamado en el paso 2 del **([**FA06**](#fa06))**, continúa en el paso [**5**](#_ref164773012) del Flujo primario.</p><p></p><p>- En caso de que haya sido llamado en el paso 2 del **([**FA03**](#fa03))**, continúa en el paso [**5**](#_ref164773012) del Flujo primario.</p>|

|<p></p><p><a name="fa08"></a>**FA08 Selecciona la opción para “Filtrar” los campos de la tabla**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
|1. El **FA08** inicia cuando el Empleado SAT selecciona la opción para **“Filtrar”** la información en alguna columna de acuerdo con lo que se muestra en la tabla.||
|2. Elige la columna para filtrar e ingresa el dato a buscar.|3. Busca dentro de la columna y filtra la información mostrada de acuerdo con los caracteres ingresados en el campo.|
||4. Muestra en tiempo real todas las coincidencias que obtiene de dicha columna.|
||5. Continúa en el paso [**5**](#_ref164773012) del Flujo primario.|

|<p></p><p><a name="fa09"></a>**FA09 Fecha de asignación incorrecta**</p>|
| :- |

|**Actor**|**Sistema**|
| :-: | :-: |
||1. El **FA09** inicia cuando el sistema identifica que la fecha de asignación es incorrecta.|
||2. Muestra el mensaje **([**MSG003**](#msg003))** con la opción “Aceptar”.|
|3. Selecciona la opción **“Aceptar”**.|4. Cierra el mensaje.|
||5. Continúa en el paso [**10**](#_ref168559949) del Flujo primario.|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc168584927"></a>**8. Referencias cruzadas** </h3>|
|<p></p><p>- 17\_3083\_EIU\_AsociarFasesMatrizDoc</p><p>- 17\_3083\_CRN\_SeguimientoFinancieroYControl</p><p></p>|
|<h3><a name="_toc168584928"></a>**9. Mensajes** </h3>|
||

|**ID Mensaje**|**Descripción**|
| :-: | :-: |
|<a name="msg001"></a>**MSG001**|Se guardó la plantilla asociada correctamente.|
|<a name="msg002"></a>**MSG002**|Se perderá la información ingresada. ¿Está seguro de continuar?|
|<a name="msg003"></a>**MSG003**|La fecha de asignación no puede ser mayor que la fecha del día actual.|
|<a name="msg004"></a>**MSG004**|Por favor ingresa toda la información obligatoria.|
|<a name="msg005"></a>**MSG005**|<p>Esta asociación se eliminará.</p><p>¿Está seguro de que desea continuar?</p>|
|<a name="msg006"></a>**MSG006**|<p>No se puede realizar la acción ya que la plantilla contiene documentos cargados. </p><p>Por favor, primero elimine los documentos cargados en la sección “Gestión documental” para continuar.</p>|
|<a name="msg007"></a>**MSG007**|Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).|
|<a name="msg008"></a>**MSG008**|Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).|
|<a name="msg009"></a>**MSG009**|Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).|

|<p></p><p></p>|
| :- |
|<h3><a name="_toc168584929"></a>**10. Requerimientos No Funcionales** </h3>|
||

|**ID de RNF**|**Requerimiento No Funcional**|**Descripción**|
| :-: | :-: | :-: |
|**RNF001**|Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|
|**RNF002**|Concurrencia|<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p></p><p>El número de accesos concurrentes que debe soportar este sistema son máximo 30 Empleados SAT.</p>|
|**RNF003**|Seguridad|El acceso solo podrá ser otorgado a todo Empleado SAT que tenga los roles asignados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema.|
|**RNF004**|Usabilidad |<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación:  </p><p></p><p>- Mensajes tipo flotantes (*tooltips*) con información de la herramienta que ofrece ayuda contextual, como guía para el Empleado SAT.  </p><p>- Componente de ordenamiento que permita acomodar la información de la tabla de forma ascendente o descendente, considerando la columna donde es seleccionado. </p><p>- Contar con un diseño responsivo que permita su óptima visualización en distintos tipos de dispositivos finales.</p>|
|**RNF005**|Eficiencia |Las consultas se dividen en generales y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT.|
|**RNF006**|Usabilidad|<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100:  </p><p>  </p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta).  </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta).  </p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).  </p><p>- Ir a la página anterior (debe mostrar la página anterior considerando la actual con el resultado de la consulta).  </p><p>  </p><p>En la tabla deben mostrarse los registros ordenados alfabéticamente. </p>|
|**RNF007**|Seguridad|Las Pistas de Auditoría deben estar protegidas contra accesos no autorizados. Solo los Empleados SAT autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.   |
|**RNF008**|Fiabilidad |El sistema debe ser capaz de manejar excepciones de manera efectiva y presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema. |
|**RNF009**|Seguridad |Mantener la información en pantalla en caso de un error al guardar las pistas de auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema. |
|**RNF010** |Integridad |Al almacenar la información en la BD de tipo Texto o alfanumérico se deben eliminar los espacios en blanco al inicio y fin de la cadena. |

|<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>|
| :- |
|<h3><a name="_toc168584930"></a>**11. Diagrama de actividad** </h3>|
|<p></p><p>![](Aspose.Words.ef82187c-5a78-46c4-80b2-6b8d8b79cbd6.008.png)</p><p></p>|
|<h3><a name="_toc168584931"></a>**12. Diagrama de estados** </h3>|
|<p></p><p>No aplica, no se requiere para este proceso.</p><p></p>|
|<h3>` `**<a name="_toc168584932"></a>13. Aprobación del cliente** </h3>|
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

|<p></p><p></p>|
| :- |

|||Página 1 de 9|
| :- | :-: | -: |

[ref1]: Aspose.Words.ef82187c-5a78-46c4-80b2-6b8d8b79cbd6.007.png
