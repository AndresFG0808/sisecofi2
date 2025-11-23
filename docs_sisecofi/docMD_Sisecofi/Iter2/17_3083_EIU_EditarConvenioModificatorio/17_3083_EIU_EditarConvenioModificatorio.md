|![](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Usuario**</p><p>17\_3083\_EIU\_EditarConvenioModificatorio.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación
## <a name="_toc168048880"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :-: | :-: |
|*1*|*Creación del documento*|Eric Hector Pérez Pérez|*05/04/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*03/05/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*31/05/2024*|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc168048880)

[ESTILOS 01	2](#_toc168048881)

[Descripción de Elementos	2](#_toc168048882)

[Descripción de Campos	4](#_toc168048883)

[ESTILOS 02	11](#_toc168048884)

[Descripción de Elementos	12](#_toc168048885)

[Descripción de Campos	13](#_toc168048886)

[ESTILOS 03	17](#_toc168048887)

[Descripción de Elementos	17](#_toc168048888)

[Descripción de Campos	18](#_toc168048889)

[ESTILOS 04	20](#_toc168048890)

[Descripción de Elementos	21](#_toc168048891)

[Descripción de Campos	22](#_toc168048892)

[ESTILOS 05	30](#_toc168048893)

[Descripción de Elementos	30](#_toc168048894)

[Descripción de Campos	30](#_toc168048895)







<a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a>**Módulo:** **CONVENIO MODIFICATORIO**
## <a name="_toc163233335"></a><a name="_toc168048881"></a>**ESTILOS 01**

|**Nombre de la Pantalla:** |<p>Registro de servicios.</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT consultar y/o modificar los registros de servicios vinculados a un convenio modificatorio.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_EditarConvenioModificatorio|
|||

![Interfaz de usuario gráfica, Tabla

Descripción generada automáticamente](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.002.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.

### <a name="_toc163233336"></a><a name="_toc168048882"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Registro de servicios|Nombre de la sección.|
|![Forma

Descripción generada automáticamente con confianza baja]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente: </p><p>Sección contraída ![ref1]</p><p>Sección desplegada![ref2]</p>|
|![ref3]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|![ref4]|Opción que permite exportar la información de la base de datos (BD) “Registro de servicios” generando un archivo de Excel con extensión (.xlsx).|
|Id|Campo incremental que mostrará el identificador del concepto de servicio.|
|Tipo de consumo|Campo que muestra el tipo de consumo si es “Volumetría” o “Bolsa”.|
|Conceptos de servicio|Campo que muestra los conceptos de servicio asociados al contrato.|
|Número de servicios máximos|<p>Campo que muestra el número de servicios máximos del contrato o último convenio modificatorio.</p><p>**Nota:** La referencia de último convenio modificatorio (CM) para los cálculos del presente CM será el registro anteriormente guardado a éste. </p>|
|Monto máximo|Campo que muestra el monto máximo del servicio registrado en el contrato o último convenio modificatorio.|
|Precio unitario|Campo de captura que permite editar el precio unitario del servicio registrado en el contrato o último convenio modificatorio.|
|Compensación (Número de servicios)|Campo de captura que permite ingresar la compensación en unidades del servicio en el convenio modificatorio.|
|Compensación (Monto)|Campo de captura que permite ingresar la compensación del servicio en el convenio modificatorio en la moneda registrada del contrato.|
|Incremento (Número de servicios)|Campo de captura que permite ingresar el incremento en unidades del servicio en el convenio modificatorio.|
|Incremento (Monto)|Campo de captura que permite ingresar el incremento del servicio en el convenio modificatorio en la moneda registrada del contrato.|
|Número total de servicios|Campo que muestra el número total de servicios del concepto en este convenio modificatorio. |
|Monto máximo total|Campo que muestra el monto máximo total del servicio en este convenio modificatorio.|
|Aplica IEPS|Campo que muestra si el servicio aplica o no IEPS a partir de este convenio modificatorio.|
|![ref5]![ref6]|Casilla de selección para aplicar IEPS.|
|Acciones|Campo que muestra las acciones que se pueden realizar con el registro de la tabla.|
|![ref7]|Opción que permite editar los registros en la tabla. |
|![ref8]|Opción que permite cancelar la acción de edición.|
|![ref9]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref10]|Campo que permite filtrar la información de la columna en la que se requiere buscar específicamente.|
|<p>![ref11]</p><p>![ref12]</p>|Permite desplazarse de manera vertical u horizontal en la tabla. |
|Validar|Opción que valida que la sumatoria de los “Monto máximo total” de todos los conceptos sea menor o igual al “Monto máximo del contrato con CM sin impuestos” de la sección “Registro de convenio modificatorio”.|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|
|Guardar|Opción que inicia el proceso para almacenar en la BD la información de la sección “Registro de servicios” del convenio modificatorio.|

### <a name="_toc163233337"></a><a name="_toc168048883"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Registro de servicios|Sección|N/A|L|Nombre de la sección.|N/A|N/A|
|![ref13]|Ícono |N/A |S |Opción que despliega o contrae la sección. |N/A |<p>Sección contraída ![ref1]</p><p>Sección desplegada![ref14]</p>|
|![ref3]|Paginador|N/A|S |Permite navegar a través de las páginas resultantes de la consulta. |N/A |Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|![ref4]|Ícono|N/A|S|Opción que permite exportar la información de la BD “Registro de servicios” generando un archivo de Excel con extensión (.xlsx).|N/A|Usar el *tooltip* “Exportar a Excel”.|
|Id|Numérico|N/A|L|Campo incremental que mostrará el identificador del concepto de servicio.|N/A|Número incremental que iniciará en 1.  |
|Tipo de consumo|Texto|N/A|L|Campo que muestra el tipo de consumo si es “Volumetría” o “Bolsa”.|N/A|N/A|
|Conceptos de servicio|Texto|250|L|Campo que muestra los conceptos de servicio asociados al contrato.|N/A|N/A|
|Número de servicios máximos|<p>Numérico </p><p>(Decimal)</p>|15|L|Campo que muestra el número de servicios máximos del contrato o último convenio modificatorio.|N/A|<p>Se consideran números decimales con formato $ 0.000000, hasta 6 decimales. Ejemplo: $999,999,999,999.000000.</p><p></p>|
|Monto máximo|<p>Numérico </p><p>(Decimal)</p>|20|L|Campo que muestra el monto máximo del servicio registrado en el contrato o último convenio modificatorio.|N/A|<p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p></p>|
|Precio unitario|<p>Numérico </p><p>(Decimal)</p>|20|L, E|Permite editar el precio unitario del servicio registrado en el contrato o último convenio modificatorio.|N/A|<p>Campo obligatorio.</p><p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Compensación (Número de servicios)|<p>Numérico </p><p>(Decimal)</p>|15|L, E|Permite ingresar la compensación en unidades del servicio en el convenio modificatorio.|<p>Se habilita cuando el tipo de consumo sea “Volumetría” y se calcula cuando el tipo de consumo sea “Bolsa”.<br>Formula:<br>“Compensación (Monto)” / “Precio unitario”.</p><p></p>|<p>Se consideran números decimales con formato $ 0.000000, hasta 6 decimales. Ejemplo: $999,999,999,999.000000.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Compensación (Monto)|<p>Numérico </p><p>(Decimal)</p>|20|L, E|Permite ingresar la compensación del servicio en el convenio modificatorio en la moneda registrada del contrato.|<p>Se habilita cuando el tipo de consumo sea “Bolsa” y se calcula cuando el tipo de consumo sea “Volumetría”.<br>Formula:<br>“Compensación (Número de servicios)” \* “Precio unitario”.</p><p></p>|<p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Incremento (Número de servicios)|<p>Numérico </p><p>(Decimal)</p>|15|L, E|Permite ingresar el incremento en unidades del servicio en el convenio modificatorio.|<p>Se habilita cuando el tipo de consumo sea “Volumetría” y se calcula cuando el tipo de consumo sea “Bolsa”.<br>Formula:<br>“Incremento (Monto)” / “Precio unitario”.</p><p></p>|<p>Se consideran números decimales con formato $ 0.000000, hasta 6 decimales. Ejemplo: $999,999,999,999.000000.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Incremento (Monto)|<p>Numérico </p><p>(Decimal)</p>|20|L, E|Permite ingresar el incremento del servicio en el convenio modificatorio en la moneda registrada del contrato.|<p>Se habilita cuando el tipo de consumo sea “Bolsa” y se calcula cuando el tipo de consumo sea “Volumetría”.<br>Formula:<br>“Incremento (Número de servicios)” \* “Precio unitario”.</p><p></p>|<p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Número total de servicios|<p>Numérico </p><p>(Decimal)</p>|15|L|Muestra el número total de servicios del concepto en este convenio modificatorio.|<p>Se calcula con la siguiente formula:</p><p>“Número de servicios máximos” + - “Compensación (Número de servicios)” + “Incremento (Número de servicios)".</p>|<p>Campo obligatorio.</p><p>Se consideran números decimales con formato $ 0.000000, hasta 6 decimales. Ejemplo: $999,999,999,999.000000.</p>|
|Monto máximo total|<p>Numérico </p><p>(Decimal)</p>|20|L|Muestra el monto máximo total del servicio en este convenio modificatorio.|<p>Se calcula con la siguiente formula:</p><p>“Monto máximo” + - “Compensación (Monto)” + “Incremento (Monto)".</p>|<p>Campo obligatorio.</p><p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p>|
|Aplica IEPS|Texto|N/A|L|Campo que muestra si el servicio aplica o no IEPS a partir de este convenio modificatorio.|N/A|N/A|
|![ref15] ![ref16]|Casilla de selección.|N/A|L, S|Permite seleccionar si aplica IEPS.|N/A|<p>![ref15]Sin seleccionar.</p><p>![ref16]Seleccionado.</p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura.</p>|
|Acciones|Texto|N/A|L|Campo que muestra las acciones que se pueden realizar con el registro de la tabla|N/A|N/A|
|![ref7]|Ícono |N/A |S |Opción que permite editar la información de un registro adicionado en la tabla. |N/A |<p> </p><p>Usar el *tooltip* “Editar”.</p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura. </p>|
|![ref8]|Ícono|N/A|S|Opción que permite cancelar la acción de edición.|N/A|Solo se permitirá seleccionar si el rol tiene permiso de escritura. |
|![ref9]|Ícono |N/A |S |Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique. |N/A |N/A |
|![ref17]|Filtro |N/A |E |Permite filtrar información de la columna donde se requiera buscar específicamente. |N/A |Realiza el filtro de la información solo dentro de la tabla que se visualiza. |
|<p>![ref11]</p><p></p><p>![ref12]</p>|Barra de desplazamiento |N/A |S|Permite desplazarse de manera vertical u horizontal en la tabla. |N/A |N/A |
|Validar|Botón|N/A|S|Opción que valida que la sumatoria de los “Monto máximo total” de todos los conceptos sea menor o igual al “Monto máximo del contrato con CM sin impuestos” de la sección “Registro de convenio modificatorio”.|<p>Se calcula con la siguiente formula:</p><p>Es la suma del “Monto máximo total” de todos los conceptos de tipo “Volumetría" + “Monto máximo total” del primer registro de cada grupo de tipo “Bolsa”.</p>|Solo se permitirá seleccionar si el rol tiene permiso de escritura. |
|Cancelar|Botón|N/A|L, S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas. </p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura. </p>|
|Guardar|Botón|N/A|L, S|Opción que inicia el proceso para almacenar en la BD la información de la sección “Registro de servicios” del convenio modificatorio.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas. </p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura. </p>|

## <a name="_toc168048884"></a>**ESTILOS 02**

|**Nombre de la Pantalla:** |<p>Proyección de convenio modificatorio.</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permite al Empleado SAT consultar, cargar y actualizar la proyección de consumo de servicios  relacionados al convenio modificatorio.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_EditarConvenioModificatorio|
|||

![Tabla

Descripción generada automáticamente](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.021.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.

### <a name="_toc168048885"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Proyección de convenio modificatorio|Nombre de la sección.|
|![Forma

Descripción generada automáticamente con confianza baja]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente: </p><p>Sección contraída ![ref1]</p><p>Sección desplegada![ref18]</p>|
|Descargar layout![ref19]|<p>Ícono que permite descargar la estructura del *layout* de carga, sin datos.</p><p>El *layout* de convenio modificatorio estará conformado de la siguiente manera:</p><p></p>|

|Conceptos de servicio|Mes1-AA|Mes2-AA|Mes3-AA|Mes4-AA|Mes5-AA|Mes6-AA|Mes7-AA|Mes8-AA|<p></p><p>MesN-AN</p>|
| :- | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |

||<p>- El concepto de servicio (Una fila por cada concepto).</p><p>Una columna por cada mes de duración de la vigencia del contrato con convenio modificatorio.</p>|
| :- | - |
|Archivo de proyección\*:|Muestra el nombre del campo para ingresar el archivo de proyección.|
|![ref20]|Muestra el nombre del archivo con extensión (.xlsx), correspondiente a la proyección.|
|Examinar|Opción que permite abrir el gestor de archivos del equipo de cómputo del Empleado SAT, para seleccionar un archivo con extensión (.xlsx).|
|Procesar proyección |Opción que permite iniciar el proceso de almacenamiento en BD de la proyección del convenio modificatorio.|
|![ref21] |Opción que permite exportar la información de la BD “Proyección de convenio modificatorio”, generando un archivo de Excel con extensión (.xlsx).|
|Conceptos de servicio|Campo de texto que mostrará todos los conceptos de servicio de la correspondientes a la sección “Registro de servicios” del convenio modificatorio.|
|Meses-año|Estos campos son dinámicos, es decir, se debe mostrar una columna por cada mes de duración del contrato con convenio modificatorio.|
|![ref3]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|![ref9]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref10]|Campo que permite filtrar la información de la columna en la que se requiere buscar específicamente. |
|<p>![ref11]</p><p>![ref12]</p>|Permite desplazarse de manera vertical u horizontal en la tabla. |

### <a name="_toc168048886"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Proyección de convenio modificatorio|Sección|N/A|L|Nombre de la sección.|N/A|N/A|
|![ref13]|Ícono |N/A |S |Opción que despliega o contrae la sección. |N/A |<p>Sección contraída ![ref1]</p><p>Sección desplegada![ref22]</p>|
|Descargar layout ![ref19]|Ícono|N/A|L, S|Ícono que permite descargar la estructura del *layout*.|N/A|<p>Usar el *tooltip* “Descargar *layout*”.</p><p>Solo se visualiza si el Empleado SAT tiene permisos de “Editar”.</p>|
|Archivo de proyección\*:|Texto|N/A|L|Muestra el nombre del campo para ingresar el archivo de proyección.|N/A|N/A|
|![ref23]|Alfanumérico|N/A|L, E|Muestra el nombre del archivo con extensión (.xlsx), correspondiente a la proyección.|N/A|Solo se visualiza si el Empleado SAT tiene permisos de “Editar”.|
|Examinar|Botón|N/A|L, S|Opción que permite abrir el gestor de archivos del equipo de cómputo del Empleado SAT, para seleccionar un archivo con extensión (.xlsx).|N/A|Solo se visualiza si el Empleado SAT tiene permisos de “Editar”.|
|<p></p><p>Procesar proyección </p>|Botón|N/A|L, S|Opción que permite iniciar el proceso de almacenamiento en BD de la proyección del convenio modificatorio.|N/A|<p>Si tipo de consumo es “Volumetría” se validará por cada línea que la sumatoria de los meses sea igual a “Cantidad de servicios máxima” de la sección “Registro de servicios”.</p><p>Solo se visualiza si el Empleado SAT tiene permisos de “Editar”.</p>|
|![ref21] |Ícono|N/A|S|Opción que permite exportar la información de la BD “Proyección de convenio modificatorio”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar el *tooltip* “Exportar a Excel”.|
|Conceptos de servicio|Texto|N/A|L|Campo de texto que mostrará todos los conceptos de servicio correspondientes a la sección “Registro de servicios” del convenio modificatorio.|N/A|N/A|
|Meses-año|Fecha|7|L|Estos campos son dinámicos, es decir, se debe mostrar una columna por cada mes de duración del contrato con convenio modificatorio.|N/A|<p>Formato de fecha </p><p>MM-AAAA </p>|
|![ref24]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta. |N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|![ref9]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![ref25]|Filtro|250|E|Cuadro que permite filtrar la información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la tabla que se visualiza. |
|<p>![ref11]</p><p></p><p>![ref12]</p>|Barra de desplazamiento |N/A |S|Permite desplazarse de manera vertical u horizontal en la tabla. |N/A |N/A |


## <a name="_toc168048887"></a>**ESTILOS 03**

|**Nombre de la Pantalla:** |<p>Asignación de plantilla.</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permite al Empleado SAT seleccionar la plantilla o plantillas de documentos relacionados con el convenio modificatorio.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_EditarConvenioModificatorio|
|||

![Interfaz de usuario gráfica, Aplicación

Descripción generada automáticamente](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.030.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.


### <a name="_toc168048888"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Asignación de plantilla|Nombre de la sección.|
|![Forma

Descripción generada automáticamente con confianza baja]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente: </p><p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja][ref1]</p><p>Sección desplegada![Forma

Descripción generada automáticamente con confianza baja] </p>|
|Asignar fase:|Lista de selección para asignar la plantilla que se utilizará en el convenio modificatorio.|
|` `![ref26]|Opción que permite asignar una plantilla al convenio modificatorio.|
|![ref27]|Opción que permite excluir una plantilla al convenio modificatorio.|
|Cancelar|Opción que realiza el proceso para cancelar la acción realizada y regresa al último estado guardado.|
|Guardar|Opción que inicia el proceso para almacenar en la BD la información de la sección “Asignación de plantilla” del convenio modificatorio.|


### <a name="_toc168048889"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Asignación de plantilla|Sección|N/A|L|Nombre de la sección.|N/A|N/A|
|![Forma

Descripción generada automáticamente con confianza baja][ref13]|Ícono |N/A |S |Opción que despliega o contrae la sección.|N/A |<p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja][ref1]</p><p>Sección desplegada![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Asignar fase|Lista de selección|N/A|L, S|<p>Lista de selección para asignar la plantilla que se utilizará en el convenio modificatorio.</p><p>Campo vinculado al catálogo de fases.</p>|N/A|<p>Campo obligatorio.</p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura.</p>|
|![ref26]|Ícono|N/A |L, S|Opción que asigna una plantilla al convenio modificatorio.|N/A|Solo se permitirá seleccionar si el rol tiene permiso de escritura.|
|![ref27]|Ícono|N/A |L, S|Opción que permite excluir una plantilla al convenio modificatorio.|N/A|Solo se permitirá seleccionar si el rol tiene permiso de escritura.|
|Cancelar|Botón|N/A|L, S|Opción que realiza el proceso para cancelar la acción realizada y regresa al último estado guardado.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas. </p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura.</p>|
|Guardar|Botón|N/A|L, S|Opción que inicia el proceso para almacenar en la BD la información de la sección “Asignación de plantilla” del convenio modificatorio.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura. </p>|


## <a name="_toc167278556"></a><a name="_toc168048890"></a>**ESTILOS 04**

|**Nombre de la Pantalla:** |<p>Convenio Modificatorio.</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT editar la sección “Registro de convenio modificatorio”.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_EditarConvenioModificatorio|
|||

![Interfaz de usuario gráfica

Descripción generada automáticamente](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.035.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.

### <a name="_toc167278558"></a><a name="_toc168048892"></a><a name="_toc167278557"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref28] |Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema.|
|Convenio modificatorio|Título del encabezado que identifica el submódulo a donde ha ingresado el Empleado SAT.|
|Última modificación|Campo que muestra el nombre del Empleado SAT, fecha y hora de la última modificación realizada al convenio modificatorio (CM).|
|Registro de convenio modificatorio|Sección que permite registrar el convenio modificatorio.|
|![Forma

Descripción generada automáticamente con confianza baja]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente: </p><p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja][ref1]</p><p>Sección desplegada![Forma

Descripción generada automáticamente con confianza baja] </p>|
|Número de convenio\*:|<p>Campo que permite capturar el número de CM. </p><p></p>|
|Tipo de convenio\*:|Campo que muestra el “Tipo de convenio”.|
|![ref5]![ref6]|Casilla de selección del tipo de convenio modificatorio,|
|Fecha de firma\*:|Campo que permite capturar la fecha de firma del CM.|
|Fecha fin de servicio\*:|Campo que permite capturar la fecha de fin del servicio.|
|Fecha fin de contrato con CM\*:|Campo que permite capturar la fecha de fin del contrato con CM.|
|Cálculo de días naturales\*:|Campo que muestra la duración en días del contrato con convenio modificatorio.|
|Incremento\*:|Campo que permite capturar el incremento monetario del convenio modificatorio.|
|Subtotal\*:|Campo que muestra el monto máximo del contrato o último CM sin impuestos.|
|IEPS|Campo que permite capturar el IEPS del convenio modificatorio.|
|IVA\*:|Campo de selección vinculado al catálogo de “IVA” y muestra cálculo de IVA.|
|Tipo de cambio\*:|Campo que permite capturar el tipo de cambio.|
|Monto máximo del contrato con CM sin impuestos\*:|Campo que muestra el monto máximo del contrato con CM sin impuestos.|
|Monto máximo del contrato con CM con impuestos\*:|Campo que muestra el monto máximo del contrato con CM con impuestos.|
|Monto en pesos\*:|Campo que muestra el monto máximo del contrato con convenio modificatorio en pesos.|
|Comentarios:|Campo que permite capturar los comentarios.|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|
|Guardar|Opción que inicia el proceso para almacenar en la base de datos (BD) la información de la sección “Registro de convenio modificatorio”.|
|Registro de servicios|Sección colapsada “Registro de servicios”.|
|Proyección de convenio modificatorio|Sección colapsada “Proyección de convenio modificatorio”.|
|Asignación de plantilla|Sección colapsada “Asignación de plantilla”.|
|Gestión documental|Sección colapsada “Gestión documental”.|

### **DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref28]|Ícono |N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla.|N/A|N/A|
|Convenio modificatorio|Texto|N/A|L|Título del encabezado que identifica el submódulo a donde ha ingresado el Empleado SAT.|N/A|N/A|
|Última modificación|Texto|100|L|Campo que muestra el nombre del Empleado SAT, fecha y hora de la última modificación realizada al convenio modificatorio (CM).|N/A|N/A|
|Registro de convenio modificatorio|Sección|N/A|L, S|Nombre de la sección.|N/A|N/A|
|![Forma

Descripción generada automáticamente con confianza baja][ref13]|Ícono |N/A |S |<a name="_hlk167273535"></a>Opción que despliega o contrae la sección.|N/A |<p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja][ref1]</p><p>Sección desplegada![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Número de convenio\*:|Alfanumérico |40|L, E|<p>Permite capturar el número de CM. </p><p></p>|N/A|<p>Campo obligatorio.</p><p>Deberá empezar con el número de contrato seguido de “CM” y el número consecutivo al anterior CM.</p><p>En caso de ser el primer CM.</p><p>Ejemplo:</p><p>**CS-300-LP-N-P-FP-002/23CM01**</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Tipo de convenio\*:|Texto|N/A|L|<p>Campo que muestra el “Tipo de convenio”.</p><p>Los valores pueden ser:</p><p>` `“Alcance”, “Monto”, “Tiempo” y “Administrativo”.</p>|N/A|N/A|
|![ref15]![ref16]|Casilla de selección.|N/A|L, S|Permite seleccionar una o más opciones de tipo de convenio modificatorio.|N/A|<p>Debe de seleccionar por lo menos un tipo de convenio modificatorio.</p><p>![ref15]Sin seleccionar.</p><p>![ref16]Seleccionado.</p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura.</p>|
|Fecha de firma\*:|Fecha|10|L, E|Permite capturar la fecha de firma del CM.|N/A|<p>Campo obligatorio.</p><p>Formato de fecha DD/MM/AAAA.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Fecha fin de servicio\*:|Fecha|10|L, E|Permite capturar la fecha de fin del servicio.|N/A|<p>Campo obligatorio.</p><p>Formato de fecha DD/MM/AAAA.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Fecha fin de contrato con CM \*:|Fecha|10|L, E|Permite capturar la fecha de fin del contrato con CM.|N/A|<p>Campo obligatorio.</p><p>Formato de fecha DD/MM/AAAA.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Cálculo de días naturales\*:|Numérico |50|L|Muestra la duración en días del contrato con convenio modificatorio.|<p>Se calcula con:</p><p>“Fecha fin de convenio” del presente convenio modificatorio – “Fecha inicio de vigencia del contrato” del contrato.</p>|N/A|
|Incremento\*:|<p>Numérico </p><p>(Decimal)</p>|20|L, E|Permite capturar el incremento monetario del convenio modificatorio.|N/A|Solo se permitirá editar si el rol tiene permiso de escritura.|
|Subtotal\*:|<p>Numérico </p><p>(Decimal)</p>|20|L|Muestra el monto máximo del contrato o último CM sin impuestos.|N/A|<p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>El ícono ![ref29] simboliza que el campo es un monto.</p>|
|IEPS|<p>Numérico </p><p>(Decimal)</p>|20|L, E|Permite capturar el IEPS del convenio modificatorio.|N/A|<p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>El ícono ![ref29] simboliza que el campo es un monto.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|IVA\*:|Casilla de selección|N/A|L, S|Casilla de selección para elegir el “IVA” y muestra cálculo de IVA del convenio modificatorio.|<p>Se calcula con la siguiente formula:</p><p>(“Subtotal” + “Incremento” + “IEPS”) \* “IVA”.</p>|<p>Campo obligatorio.</p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura.</p><p>Para el campo calculado:</p><p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>El ícono ![ref29] simboliza que el campo es un monto.</p>|
|Tipo de cambio\*:|<p>Numérico </p><p>(Decimal)</p>|10|L, E|Permite capturar el tipo de cambio para el convenio modificatorio.|N/A|<p>Campo obligatorio. </p><p>Se consideran números decimales con formato $ 0.0000, hasta 4 decimales. Ejemplo: $999,999,999,999.0000.</p><p>El ícono ![ref29] simboliza que el campo es un monto.</p><p>Solo se permitirá editar si el rol tiene permiso de escritura.</p>|
|Monto máximo de contrato con CM sin impuestos\*:|<p>Numérico</p><p>(Decimal)</p>|20 |L|Muestra Monto máximo de contrato con CM sin impuestos.|<p>Se calcula con la siguiente formula:</p><p>“Subtotal” + “Incremento”.</p>|<p>Campo obligatorio. </p><p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>El ícono ![ref29] simboliza que el campo es un monto.</p>|
|Monto máximo de contrato con CM con impuestos\*:|Numérico (Decimal)|20|L|Muestra Monto máximo de contrato con CM con impuestos.|<p>Se calcula con la siguiente formula:</p><p>“Monto máximo de contrato con CM sin impuestos” + “IEPS” + “IVA”.</p>|<p>Campo obligatorio. </p><p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>El ícono ![ref29] simboliza que el campo es un monto.</p>|
|Monto en pesos\*:|Numérico (Decimal)|20|L|Muestra el monto máximo del contrato con convenio modificatorio en pesos.|<p>Se calcula con la siguiente formula:</p><p>“Monto en pesos” (contrato o último CM) + (“Incremento” + (“Incremento” \* “IVA”)) \* “Tipo de cambio”.</p>|<p>Campo obligatorio. </p><p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>El ícono ![ref29] simboliza que el campo es un monto.</p>|
|Comentarios|Texto|250|L, E|Permite capturar comentarios.|N/A|Solo se permitirá editar si el rol tiene permiso de escritura.|
|Cancelar|Botón|N/A|L, S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas. </p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura.</p>|
|Guardar|Botón|N/A|L, S|Opción que permite almacenar los datos ingresados.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas. </p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura.</p>|
|Registro de servicios|Sección|N/A|L, S|Sección colapsada “Registro de servicios”.|N/A|N/A|
|Proyección de convenio modificatorio|Sección|N/A|L, S|Sección colapsada “Proyección de convenio modificatorio”.|N/A|N/A|
|Asignación de plantilla|Sección|N/A|L, S|Sección colapsada “Asignación de plantilla”.|N/A|N/A|
|Gestión documental|Sección|N/A|L, S|Sección colapsada “Gestión documental”.|N/A|N/A|

## <a name="_toc160810139"></a><a name="_toc167101873"></a><a name="_toc168048893"></a>**ESTILOS 05**

|**Nombre de la Pantalla:** |<p>Pantalla en modo edición.</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Modificar las acciones en las tablas. </p><p> </p><p>Si se da clic en el ícono ![](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.039.png), pondrá los campos de la tabla en modo edición y se cambiará el ícono por el siguiente![](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.040.png)</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_EditarComvenioModificatorio.|
|<p></p><p></p>|![](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.041.png)![](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.042.png)![Interfaz de usuario gráfica, Aplicación

Descripción generada automáticamente](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.043.png)              ![Interfaz de usuario gráfica, Aplicación

Descripción generada automáticamente](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.044.png)|

\

### <a name="_toc160091275"></a><a name="_toc160810140"></a><a name="_toc167101874"></a><a name="_toc168048894"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Acciones|Campo que muestra las acciones que se pueden realizar con el registro de la tabla.|
|![ref8]|Opción que permite cancelar la acción de edición. |
|![](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.045.png) |Opción que permite editar los registros en la tabla. |



### <a name="_toc160091276"></a><a name="_toc160810141"></a><a name="_toc167101875"></a><a name="_toc168048895"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Acciones|Texto|N/A|L|Campo que muestra las acciones que se pueden realizar con el registro de la tabla.|N/A|N/A|
|![ref8]|Ícono|N/A|S|Opción que permite cancelar la acción de edición.|N/A|N/A|
|![ref30]|Ícono |N/A |S |Opción que permite editar la información de un registro adicionado en la tabla. |N/A |<p> </p><p>Usar el *tooltip* “Editar”.</p><p>Solo se permitirá seleccionar si el rol tiene permiso de escritura. </p>|


Anexo - Ejemplos de botones

![Diagrama

Descripción generada automáticamente](Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.047.png)

Las acciones de cada botón se definen en los estilos correspondientes.









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
\*\

|||Página 15 de 15|
| :- | :-: | -: |

[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.003.png
[ref1]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.004.png
[ref2]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.005.png
[ref3]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.006.png
[ref4]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.007.png
[ref5]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.008.png
[ref6]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.009.png
[ref7]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.010.png
[ref8]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.011.png
[ref9]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.012.png
[ref10]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.013.png
[ref11]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.014.png
[ref12]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.015.png
[ref13]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.016.png
[ref14]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.017.png
[ref15]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.018.png
[ref16]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.019.png
[ref17]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.020.png
[ref18]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.022.png
[ref19]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.023.png
[ref20]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.024.png
[ref21]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.025.png
[ref22]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.026.png
[ref23]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.027.png
[ref24]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.028.png
[ref25]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.029.png
[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.031.png
[ref26]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.032.png
[ref27]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.033.png
[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.034.png
[ref28]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.036.png
[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.037.png
[ref29]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.038.png
[ref30]: Aspose.Words.8b37d895-1d16-4ffb-9ee8-75c4f3bd4881.046.png
