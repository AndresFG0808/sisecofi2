|![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Usuario**</p><p>17\_3083\_EIU\_AltaDeCatalogos.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>**8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación

## <a name="_toc168581706"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :-: | :-: | :-: |
|*1*|*Creación del documento*|Eduardo Acosta Mora|*08/01/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*25/01/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*04/06/2024*|




**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc168581706)

[Módulo: CATÁLOGOS	2](#_toc168581707)

[ESTILOS 01	2](#_toc168581708)

[Descripción de Elementos	3](#_toc168581709)

[Descripción de Campos	3](#_toc168581710)

[ESTILOS 02	6](#_toc168581711)

[Descripción de Elementos	7](#_toc168581712)

[Descripción de Campos	8](#_toc168581713)

[ESTILOS 03	13](#_toc168581714)

[Descripción de Elementos	14](#_toc168581715)

[Descripción de Campos	14](#_toc168581716)

[ESTILOS 04	17](#_toc168581717)

[Descripción de Elementos	18](#_toc168581718)

[Descripción de Campos	21](#_toc168581719)

[ESTILOS 05	34](#_toc168581720)

[Descripción de Elementos	35](#_toc168581721)

[Descripción de Campos	35](#_toc168581722)

[ESTILOS 06	38](#_toc168581723)

[Descripción de Elementos	39](#_toc168581724)

[Descripción de Campos	41](#_toc168581725)

[ESTILOS 07	49](#_toc168581726)

[Descripción de Elementos	50](#_toc168581727)

[Descripción de Campos	50](#_toc168581728)


## **<a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a><a name="_toc168581707"></a>MÓDULO: CATÁLOGOS**
## <a name="_toc168581708"></a>**ESTILOS 01**

|**Nombre de la Pantalla:**|Catálogos.|
| :- | - |
|**Objetivo:**|Permitir al usuario realizar la búsqueda de información por catálogos generales o por catálogos complementarios.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeCatalogos.|
|||
![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.002.png)

<a name="_toc236129840"></a><a name="_toc236196645"></a>**Nota:** Los datos mencionados en la tabla son solo de ejemplo.


### <a name="_toc236129841"></a><a name="_toc236196646"></a><a name="_toc236558259"></a><a name="_toc267478971"></a><a name="_toc168581709"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla. Contiene los módulos principales y submódulos de este sistema.|
|Catálogos|Título que identifica el submódulo a donde ingresa el Empleado SAT.|
|![ref2]|<p>Opción que despliega o contrae la sección tomando en cuenta lo siguiente:</p><p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Catálogos generales|Sección que permite gestionar los catálogos generales.|
|Catálogo\*:|Permite seleccionar un catálogo general administrado por el sistema.|
|Buscar|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” en la sección “Catálogos generales”.|
|Catálogos complementarios|Sección que permite gestionar los catálogos complementarios.|
|Catálogo\*:|Permite seleccionar un catálogo complementario administrado por el sistema.|
|Buscar|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” en la sección “Catálogos complementarios”.|

### <a name="_toc236129842"></a><a name="_toc236196647"></a><a name="_toc236558260"></a><a name="_toc168581710"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla. Contiene los módulos principales y submódulos de este sistema.|N/A|N/A|
|Catálogos|Texto|N/A|L|Título que identifica el submódulo a donde ingresa el Empleado SAT.|N/A|N/A|
|![ref2]|Ícono|N/A|S|<p>Opción que despliega o contrae la sección.</p><p></p>|N/A|<p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Catálogos generales|Sección|N/A|S|Sección que permite gestionar los catálogos generales.|N/A|N/A|
|Catálogo\*:|Lista de selección|N/A|S|Permite seleccionar un catálogo general administrado por el sistema.|N/A|Campo obligatorio|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” en la sección “Catálogos generales”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|Catálogos complementarios|Sección|N/A|S|Sección que permite gestionar los catálogos complementarios.|N/A|N/A|
|Catálogo\*:|Lista de selección|N/A|S|Permite seleccionar un catálogo complementario administrado por el sistema.|N/A|Campo obligatorio|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” en la sección “Catálogos complementarios”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|



## <a name="_toc168581711"></a>**ESTILOS 02**

|**Nombre de la Pantalla:**|Catálogos generales.|
| :- | - |
|**Objetivo:**|Permite buscar y gestionar los registros del catálogo general seleccionado.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeCatalogos.|
|||
![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.007.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.


### <a name="_toc168581712"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla. Contiene los módulos principales y submódulos de este sistema.|
|Catálogos|Título que identifica el submódulo a donde ingresa el Empleado SAT.|
|![ref3]|<p>Opción que despliega o contrae la sección tomando en cuenta lo siguiente:</p><p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Catálogos generales|Sección que permite gestionar los catálogos generales.|
|Catálogo\*:|Permite seleccionar un catálogo general administrado por el sistema.|
|Buscar|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” en la sección “Catálogos generales”.|
|![ref4]|Opción que permite crear un nuevo registro en un catálogo.|
|![ref5]|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Catálogo”, generando un archivo de Excel con extensión (.xlsx).|
|![ref6]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|Id|Campo incremental que se asigna de manera automática para identificar el registro ingresado. |
|Nombre|Indica el nombre de cada registro relacionado al catálogo.|
|Descripción|Indica la descripción de cada registro relacionado al catálogo.|
|Fecha de creación|Indica la fecha de creación de cada registro relacionado al catálogo.|
|Última modificación|Indica la fecha de última modificación de cada registro relacionado al catálogo.|
|Estatus|<p>Indica el estatus del registro mediante los íconos:</p><p>![ref7] Activo </p><p>![ref8] Inactivo</p>|
|Acciones|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|
|![ref10]|Opción que permite abrir una ventana emergente para la edición del registro.|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![ref12] Activo</p><p>![ref13] Inactivo</p>|
|![ref14]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref15]|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|Catálogos complementarios|Sección que permite gestionar los catálogos complementarios.|

### <a name="_toc168581713"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla. Contiene los módulos principales y submódulos de este sistema.|N/A|N/A|
|Catálogos|Texto|N/A|L|Título que identifica el submódulo a donde ingresa el Empleado SAT.|N/A|N/A|
|![ref3]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Catálogos generales|Sección|N/A|S|Sección que permite gestionar los catálogos generales.|N/A|N/A|
|Catálogo\*:|Lista de selección|N/A|S|Permite seleccionar un catálogo general administrado por el sistema.|N/A|Campo obligatorio|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” en la sección “Catálogos generales”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref4]|Ícono|N/A|S|Opción que permite crear un nuevo registro en un catálogo.|N/A|Usar *tooltip* que muestre el nombre de la opción “Nuevo”.|
|![ref5]|Ícono|N/A|S|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Catálogo”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.|
|![ref6]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Id|Número|5|L|Campo incremental que se asigna de manera automática para identificar el registro ingresado.|N/A|N/A|
|Nombre|Alfanumérico|150|L|Indica el nombre de cada registro relacionado al catálogo.|N/A|N/A|
|Descripción|Texto|300|L|Indica la descripción de cada registro relacionado al catálogo.|N/A|N/A|
|Fecha de creación|Fecha|10|L|Indica la fecha de creación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Última modificación|Fecha|10|L|Indica la fecha de última modificación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Estatus|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref7]Activo</p><p>![ref8]Inactivo</p>|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|N/A|N/A|
|![ref10]|Ícono|N/A|S|Opción que permite abrir una ventana emergente para la edición del registro.|N/A|Usar *tooltip* que muestre el nombre de la opción “Editar”.|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p>![ref7]Activo</p><p>![ref8]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|![ref14]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.021.png)|Filtro|N/A|E|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la tabla que se visualiza.|
|Catálogos complementarios|Sección|N/A|S|Sección que permite gestionar los catálogos complementarios.|N/A|Sección contraída.|


## <a name="_toc168581714"></a>**ESTILOS 03**

|**Nombre de la Pantalla:**|Nuevo registro.|
| :- | - |
|**Objetivo:**|Permite al Empleado SAT ingresar información para un nuevo registro de catálogos generales o complementarios de tipo alineación.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeCatalogos.|
|||
![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.022.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.


### <a name="_toc168581715"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Nuevo registro|Título de la ventana emergente.|
|![ref16]|Opción que permite cerrar la ventana emergente.|
|Nombre\*:|Permite ingresar el nombre del registro que se dará de alta en un catálogo general o en un catálogo complementario de tipo alineación.|
|Descripción\*:|Permite ingresar la descripción del registro que se dará de alta en un catálogo general o en un catálogo complementario de tipo alineación.|
|Estatus\*:|<p>Indica el estatus del registro mediante los íconos:</p><p>![ref12] Activo</p><p>![ref17] Inactivo</p>|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![ref12] Activo</p><p>![ref17] Inactivo</p>|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado. |
|Guardar|Opción que inicia el proceso para almacenar en la base de datos la información ingresada para el registro. |

### <a name="_toc168581716"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Nuevo registro|Texto|N/A|L|Título de la ventana emergente.|N/A|N/A|
|![ref16]|Ícono|N/A|S|Opción que permite cerrar la ventana emergente.|N/A|Usar *tooltip* que muestre el nombre de la opción “Cerrar ventana”.|
|Nombre\*:|Alfanumérico|150|E|Permite ingresar el nombre del registro que se dará de alta en un catálogo general o en un catálogo complementario de tipo alineación.|N/A|<p>Campo obligatorio</p><p></p><p>Usar *tooltip* “Información que aparecerá en las listas de selección”.</p>|
|Descripción\*:|Texto|300|E|Permite ingresar la descripción del registro que se dará de alta en un catálogo general o en un catálogo complementario de tipo alineación.|N/A|Campo obligatorio|
|Estatus\*:|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref12]Activo</p><p>![ref17]Inactivo</p><p></p><p>Campo obligatorio</p>|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p></p><p>![ref12]Activo</p><p>![ref17]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|Cancelar|Botón|N/A|S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente, se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la base de datos la información ingresada para el registro.|N/A|<p>Inicialmente, se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|


## <a name="_toc168581717"></a>**ESTILOS 04**

|**Nombre de la Pantalla:**|Catálogos complementarios (Administración general).|
| :- | - |
|**Objetivo:**|Permite buscar y administrar los registros de la estructura organizacional.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeCatalogos.|
|||
![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.025.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.


### <a name="_toc168581718"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla. Contiene los módulos principales y submódulos de este sistema.|
|Catálogos|Título que identifica el submódulo a donde ingresa el Empleado SAT.|
|![ref3]|<p>Opción que despliega o contrae la sección tomando en cuenta lo siguiente:</p><p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Catálogos generales|Sección que permite gestionar los catálogos generales.|
|Catálogos complementarios|Sección que permite gestionar los catálogos complementarios.|
|Catálogo\*:|Permite seleccionar un catálogo complementario.|
|Buscar|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” de la sección “Catálogos complementarios”.|
|![ref4]|Opción que permite crear un nuevo registro de una administración general.|
|![ref5]|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Catálogo”, generando un archivo de Excel con extensión (.xlsx).|
|![ref6]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|Id|Campo incremental que se asigna de manera automática para identificar el registro ingresado. |
|Administración|Indica el nombre de las administraciones generales relacionadas al catálogo.|
|Acrónimo|Indica el acrónimo de cada administración general.|
|Administrador general|Indica el nombre del administrador para cada administración general.|
|Puesto|Indica el puesto para cada administración general.|
|Fecha de creación|Indica la fecha de creación de cada registro relacionado al catálogo.|
|Última modificación|Indica la fecha de última modificación de cada registro relacionado al catálogo.|
|Estatus |<p>Indica el estatus del registro mediante los íconos:</p><p>![ref7] Activo </p><p>![ref8] Inactivo</p>|
|Acciones|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|
|![ref10]|Opción que permite abrir una ventana emergente para la edición del registro.|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![ref12] Activo</p><p>![ref18] Inactivo</p>|
|![ref14]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref15]|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|![ref19]|Permite desplazarse de manera horizontal en la tabla. |
|![ref20]|Permite desplazarse de manera vertical en la tabla. |
|Administraciones centrales|Sección que permite gestionar los subcatálogos de las administraciones centrales.|
|Administración general\*:|Permite seleccionar una administración general.|
|Buscar|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Administración general” de la sección “Administraciones centrales”.|
|![ref4]|Opción que permite crear un nuevo registro de una administración central.|
|![ref5]|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Administración general” de la sección “Administraciones centrales”., generando un archivo de Excel con extensión (.xlsx).|
|![ref6]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|Id|Campo incremental que se asigna de manera automática para identificar el registro ingresado. |
|Administración|Indica el nombre de las administraciones centrales relacionadas al catálogo.|
|Acrónimo|Indica el acrónimo de cada administración central.|
|Administrador central|Indica el nombre del administrador para cada administración central.|
|Puesto|Indica el puesto para cada administración central.|
|Fecha de creación|Indica la fecha de creación de cada registro relacionado al catálogo.|
|Última modificación|Indica la fecha de última modificación de cada registro relacionado al catálogo.|
|Estatus |<p>Indica el estatus del registro mediante los íconos:</p><p>![ref7] Activo </p><p>![ref8]Inactivo</p>|
|Acciones|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|
|![ref10]|Opción que permite abrir una ventana emergente para la edición del registro.|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![ref12] Activo</p><p>![ref18] Inactivo</p>|
|![ref14]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref15]|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|![ref19]|Permite desplazarse de manera horizontal en la tabla. |
|![ref20]|Permite desplazarse de manera vertical en la tabla. |
|Administraciones|Sección que permite gestionar los Subcatálogos de las administraciones (Áreas).|
|Administración central:|Permite seleccionar una administración central.|
|Buscar|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Administración central” de la sección “Administraciones”.|
|![ref4]|Opción que permite crear un nuevo registro en una administración.|
|![ref5]|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Administración central”, generando un archivo de Excel con extensión (.xlsx).|
|![ref6]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|Id|Campo incremental que se asigna de manera automática para identificar el registro ingresado. |
|Área|Indica las áreas relacionadas al catálogo.|
|Acrónimo|Indica el acrónimo de cada área.|
|Administrador de área|Indica el nombre del administrador para cada área.|
|Puesto|Indica el puesto para cada administrador de área.|
|Fecha de creación|Indica la fecha de creación de cada registro relacionado al catálogo.|
|Última modificación|Indica la fecha de última modificación de cada registro relacionado al catálogo.|
|Estatus |<p>Indica el estatus del registro mediante los íconos:</p><p>![ref7] Activo </p><p>![ref8] Inactivo</p>|
|Acciones|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|
|![ref10]|Opción que permite abrir una ventana emergente para la edición del registro.|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![ref12] Activo</p><p>![ref18] Inactivo</p>|
|![ref14]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref15]|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|![ref19]|Permite desplazarse de manera horizontal en la tabla. |
|![ref20]|Permite desplazarse de manera vertical en la tabla. |

### <a name="_toc168581719"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla. Contiene los módulos principales y submódulos de este sistema.|N/A|N/A|
|Catálogos|Texto|N/A|L|Título que identifica el submódulo a donde ingresa el Empleado SAT.|N/A|N/A|
|![ref3]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Catálogos generales|Sección|N/A|S|Sección que permite gestionar los catálogos generales.|N/A|Sección contraida|
|Catálogos complementarios|Sección|N/A|S|Sección que permite gestionar los catálogos complementarios.|N/A|N/A|
|Catálogo\*:|Lista de selección|N/A|S|Permite seleccionar un catálogo complementario.|N/A|Campo obligatorio|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” de la sección “Catálogos complementarios”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref4]|Ícono|N/A|S|Opción que permite crear un nuevo registro de una administración general.|N/A|Usar *tooltip* que muestre el nombre de la opción “Nuevo”.|
|![ref5]|Ícono|N/A|S|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Catálogo”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.|
|![ref6]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Id|Número|5|L|Campo incremental que se asigna de manera automática para identificar el registro ingresado.|N/A|N/A|
|Administración|Alfanumérico|150|L|Indica el nombre de las administraciones generales relacionadas al catálogo.|N/A|N/A|
|Acrónimo|Alfanumérico|20|L|Indica el acrónimo de cada administración general.|N/A|N/A|
|Administrador general|Alfanumérico|150|L|Indica el nombre del administrador para cada administración general.|N/A|Deberá tomar el registro activo de la tabla (Administradores)|
|Puesto|Alfanumérico|150|L|Indica el puesto para cada administración general.|N/A|N/A|
|Fecha de creación|Fecha|10|L|Indica la fecha de creación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Última modificación|Fecha|10|L|Indica la fecha de última modificación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Estatus|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref7]Activo</p><p>![ref8]Inactivo</p>|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|N/A|N/A|
|![ref10]|Ícono|N/A|S|Opción que permite abrir una ventana emergente para la edición del registro.|N/A|Usar *tooltip* que muestre el nombre de la opción “Editar”.|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p>![ref7]Activo</p><p>![ref21]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|![ref14]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.030.png)|Filtro|N/A|E|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la tabla que se visualiza.|
|![ref19]|Barra de desplazamiento|N/A|S|Permite desplazarse de manera horizontal en la tabla.|N/A|N/A|
|![ref20]|Barra de desplazamiento|N/A|S|Permite desplazarse de manera vertical en la tabla.|N/A|N/A|
|Administraciones centrales|Sección|N/A|L|Sección que permite gestionar los subcatálogos de las administraciones centrales.|N/A|N/A|
|Administración general\*:|Lista de selección|N/A|S|Permite seleccionar una administración general.|N/A|Campo obligatorio|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Administración general” de la sección “Administraciones centrales”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref4]|Ícono|N/A|S|Opción que permite crear un nuevo registro de una administración central.|N/A|Usar *tooltip* que muestre el nombre de la opción “Nuevo”.|
|![ref5]|Ícono|N/A|S|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Administración general” de la sección “Administraciones centrales”., generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.|
|![ref6]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Id|Número|5|L|Campo incremental que se asigna de manera automática para identificar el registro ingresado.|N/A|N/A|
|Administración|Alfanumérico|150|L|Indica el nombre de las administraciones centrales relacionadas al catálogo.|N/A|N/A|
|Acrónimo|Alfanumérico|20|L|Indica el acrónimo de cada administración central.|N/A|N/A|
|Administrador central|Alfanumérico|150|L|Indica el nombre del administrador para cada administración central.|N/A|Deberá tomar el registro activo de la tabla (Administradores)|
|Puesto|Alfanumérico|150|L|Indica el puesto para cada administración central.|N/A|N/A|
|Fecha de creación|Fecha|10|L|Indica la fecha de creación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Última modificación|Fecha|10|L|Indica la fecha de última modificación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Estatus|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref7]Activo</p><p>![ref8]Inactivo</p>|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|N/A|N/A|
|![ref10]|Ícono|N/A|S|Opción que permite abrir una ventana emergente para la edición del registro.|N/A|Usar *tooltip* que muestre el nombre de la opción “Editar”.|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p>![ref7]Activo</p><p>![ref21]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|![ref14]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.031.png)|Filtro|N/A|E|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la tabla que se visualiza.|
|![ref19]|Barra de desplazamiento|N/A|S|Permite desplazarse de manera horizontal en la tabla.|N/A|N/A|
|![ref20]|Barra de desplazamiento|N/A|S|Permite desplazarse de manera vertical en la tabla.|N/A|N/A|
|Administraciones|Sección|N/A|L|Sección que permite gestionar los Subcatálogos de las administraciones (Áreas).|N/A|N/A|
|Administración central\*:|Lista de selección|N/A|S|Permite seleccionar una administración central.|N/A|Campo obligatorio|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Administración central” de la sección “Administraciones”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref4]|Ícono|N/A|S|Opción que permite crear un nuevo registro en una administración.|N/A|Usar *tooltip* que muestre el nombre de la opción “Nuevo”.|
|![ref5]|Ícono|N/A|S|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Administración central”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.|
|![ref6]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Id|Número|5|L|Campo incremental que se asigna de manera automática para identificar el registro ingresado.|N/A|N/A|
|Área|Alfanumérico|150|L|Indica las áreas relacionadas al catálogo.|N/A|N/A|
|Acrónimo|Alfanumérico|20|L|Indica el acrónimo de cada área.|N/A|N/A|
|Administrador de área|Alfanumérico|150|L|Indica el nombre del administrador para cada área.|N/A|Deberá tomar el registro activo de la tabla (Administradores)|
|Puesto|Alfanumérico|150|L|Indica el puesto para cada administración de área.|N/A|N/A|
|Fecha de creación|Fecha|10|L|Indica la fecha de creación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Última modificación|Fecha|10|L|Indica la fecha de última modificación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Estatus|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref7]Activo</p><p>![ref8]Inactivo</p>|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|N/A|N/A|
|![ref10]|Ícono|N/A|S|Opción que permite abrir una ventana emergente para la edición del registro.|N/A|Usar *tooltip* que muestre el nombre de la opción “Editar”.|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p>![ref7]Activo</p><p>![ref21]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|![ref14]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![ref15]|Filtro|N/A|E|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la tabla que se visualiza.|
|![ref19]|Barra de desplazamiento|N/A|S|Permite desplazarse de manera horizontal en la tabla.|N/A|N/A|
|![ref20]|Barra de desplazamiento|N/A|S|Permite desplazarse de manera vertical en la tabla.|N/A|N/A|

## <a name="_toc168581720"></a>**ESTILOS 05**

|**Nombre de la Pantalla:**|Nuevo registro, catálogo administración general.|
| :- | - |
|**Objetivo:**|Permite al Empleado SAT ingresar información para un nuevo registro de la estructura organizacional.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeCatalogos.|
|||
![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.032.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.


### <a name="_toc168581721"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Nuevo registro|Título de la ventana emergente.|
|![ref16]|Opción que permite cerrar la ventana emergente.|
|Catálogo: |Indica el catálogo complementario al que se le creará un nuevo registro.|
|Administración\*:|Permite ingresar la administración.|
|Acrónimo\*:|Permite ingresar el acrónimo de la administración general.|
|Puesto|Permite ingresar el puesto del administrador.|
|Estatus\*:|<p>Indica el estatus del registro mediante los íconos:</p><p>![ref12] Activo</p><p>![ref17] Inactivo</p>|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![ref12] Activo</p><p>![ref17] Inactivo</p>|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado. |
|Guardar|Opción que inicia el proceso para almacenar en la base de datos la información ingresada para el registro. |

### <a name="_toc168581722"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Nuevo registro|Texto|N/A|L|Título de la ventana emergente.|N/A|N/A|
|![ref16]|Ícono|N/A|S|Opción que permite cerrar la ventana emergente.|N/A|Usar *tooltip* que muestre el nombre de la opción “Cerrar ventana”.|
|Catálogo:|Texto|N/A|L|Indica el catálogo complementario al que se le creará un nuevo registro.|N/A|N/A|
|Administración\*:|Alfanumérico|150|E|Permite ingresar la administración.|N/A|Campo obligatorio|
|Acrónimo\*:|Alfanumérico|20|E|Permite ingresar el acrónimo de la administración.|N/A|Campo obligatorio|
|Puesto|Alfanumérico|150|E|Permite ingresar el puesto de la administración.|N/A|Campo obligatorio|
|Estatus\*:|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref12]Activo</p><p>![ref17]Inactivo</p><p></p><p>Campo obligatorio</p>|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p></p><p>![ref12]Activo</p><p>![ref17]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|Cancelar|Botón|N/A|S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente, se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la base de datos la información ingresada para el registro.|N/A|<p>Inicialmente, se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|



## <a name="_toc168581723"></a>**ESTILOS 06**

|**Nombre de la Pantalla:**|Catálogos complementarios (Alineación)|
| :- | - |
|**Objetivo:**|Permite buscar y administrar los registros del catálogo complementario de tipo alineación.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeCatalogos|
|||
![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.033.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.


### <a name="_hlk168571411"></a><a name="_toc168581724"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla. Contiene los módulos principales y submódulos de este sistema.|
|Catálogos|Título que identifica el submódulo a donde ingresa el Empleado SAT.|
|![ref3]|<p>Opción que despliega o contrae la sección tomando en cuenta lo siguiente:</p><p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Catálogos generales|Sección que permite gestionar los catálogos generales.|
|Catálogos complementarios|Sección que permite gestionar los catálogos complementarios.|
|Catálogo\*:|Permite seleccionar un catálogo complementario administrado por el sistema.|
|Buscar|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” en la sección “Catálogos complementarios”.|
|![ref4]|Opción que permite crear un nuevo registro en un catálogo complementario de tipo alineación.|
|![ref5]|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Catálogo” generando un archivo de Excel con extensión (.xlsx).|
|![ref6]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|Id|Campo incremental que se asigna de manera automática para identificar el registro ingresado. |
|Nombre|Indica el nombre de cada registro relacionado al catálogo.|
|Descripción|Indica la descripción de cada registro relacionado al catálogo.|
|Fecha de creación|Indica la fecha de creación de cada registro relacionado al catálogo.|
|Última modificación|Indica la fecha de última modificación de cada registro relacionado al catálogo.|
|Estatus|<p>Indica el estatus del registro mediante los íconos:</p><p>![ref7] Activo </p><p>![ref21] Inactivo</p>|
|Acciones|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|
|![ref10]|Opción que permite abrir una ventana emergente para la edición del registro.|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![ref12] Activo</p><p>![ref13] Inactivo</p>|
|![ref14]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref15]|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|Mapas - Objetivos|Sección que permite gestionar los mapas - objetivos|
|Alineación:|Permite seleccionar una alineación.|
|Buscar|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Alineación” en la sección “Mapas - Objetivos”.|
|![ref4]|Opción que permite crear un nuevo registro de la alineación seleccionada.|
|![ref5]|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Alineación”, generando un archivo de Excel con extensión (.xlsx).|
|![ref6]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|Id|Campo incremental que se asigna de manera automática para identificar el registro ingresado. |
|Objetivo|Indica el objetivo de cada registro relacionado al catálogo.|
|Descripción|Indica la descripción de cada registro relacionado al catálogo.|
|Fecha de creación|Indica la fecha de creación de cada registro relacionado al catálogo.|
|Última modificación|Indica la fecha de última modificación de cada registro relacionado al catálogo.|
|Estatus|<p>Indica el estatus del registro mediante los íconos:</p><p>![ref7] Activo </p><p>![ref21] Inactivo</p>|
|Acciones|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|
|![ref10]|Opción que permite abrir una ventana emergente para la edición del registro.|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![ref12] Activo</p><p>![ref13] Inactivo</p>|
|![ref14]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref15]|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|

### <a name="_toc168581725"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla. Contiene los módulos principales y submódulos de este sistema.|N/A|N/A|
|Catálogos|Texto|N/A|L|Título que identifica el submódulo a donde ingresa el Empleado SAT.|N/A|Sección contraída.|
|![ref3]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Catálogos generales|Sección|N/A|S|Sección que permite gestionar los catálogos generales.|N/A|N/A|
|Catálogos complementarios|Sección|N/A|S|Sección que permite gestionar los catálogos complementarios.|N/A|N/A|
|Catálogo\*:|Lista de selección|N/A|S|Permite seleccionar un catálogo complementario administrado por el sistema.|N/A|Campo obligatorio|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Catálogo” en la sección “Catálogos complementarios”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref4]|Ícono|N/A|S|Opción que permite crear un nuevo registro en un catálogo complementario de tipo alineación.|N/A|Usar *tooltip* que muestre el nombre de la opción “Nuevo”.|
|![ref5]|Ícono|N/A|S|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Catálogo” generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.|
|![ref6]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Id|Número|5|L|Campo incremental que se asigna de manera automática para identificar el registro ingresado.|N/A|N/A|
|Nombre|Alfanumérico|150|L|Indica el nombre de cada registro relacionado al catálogo.|N/A|N/A|
|Descripción|Texto|300|L|Indica la descripción de cada registro relacionado al catálogo.|N/A|N/A|
|Fecha de creación|Fecha|10|L|Indica la fecha de creación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Última modificación|Fecha|10|L|Indica la fecha de última modificación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Estatus|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref7]Activo</p><p>![ref8]Inactivo</p>|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|N/A|N/A|
|![ref10]|Ícono|N/A|S|Opción que permite abrir una ventana emergente para la edición del registro.|N/A|Usar *tooltip* que muestre el nombre de la opción “Editar”.|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p>![ref7]Activo</p><p>![ref21]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|![ref14]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![ref15]|Filtro|N/A|E|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la tabla que se visualiza.|
|Mapas - Objetivos|Sección|N/A|L|Sección que permite gestionar los mapas - objetivos|N/A|N/A|
|Alineación\*:|Lista de selección|N/A|S|Permite seleccionar una alineación.|N/A|Campo obligatorio|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda de información en la base de datos de acuerdo con lo seleccionado en el campo “Alineación” en la sección “Mapas - Objetivos”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p><p></p>|
|![ref4]|Ícono|N/A|S|Opción que permite crear un nuevo registro de la alineación seleccionada.|N/A|Usar *tooltip* que muestre el nombre de la opción “Nuevo”.|
|![ref5]|Ícono|N/A|S|Opción que permite exportar la información de la base de datos de acuerdo con la opción seleccionada en el campo “Alineación”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.|
|![ref6]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Id|Número|5|L|Campo incremental que se asigna de manera automática para identificar el registro ingresado.|N/A|N/A|
|Objetivo|Texto|150|L|Indica el objetivo de cada registro relacionado al catálogo.|N/A|N/A|
|Descripción|Texto|300|L|Indica la descripción de cada registro relacionado al catálogo.|N/A|N/A|
|Fecha de creación|Fecha|10|L|Indica la fecha de creación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Última modificación|Fecha|10|L|Indica la fecha de última modificación de cada registro relacionado al catálogo.|N/A|Formato de fecha DD/MM/AAAA|
|Estatus|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref7]Activo</p><p>![ref8]Inactivo</p>|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden hacer con los registros mediante el ícono ![ref9].|N/A|N/A|
|![ref10]|Ícono|N/A|S|Opción que permite abrir una ventana emergente para la edición del registro.|N/A|Usar *tooltip* que muestre el nombre de la opción “Editar”.|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p>![ref7]Activo</p><p>![ref21]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|![ref14]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.034.png)|Filtro|N/A|E|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la tabla que se visualiza.|



## <a name="_toc168581726"></a>**ESTILOS 07**

|**Nombre de la Pantalla:**|Nuevo registro, catálogo mapa estratégico o mapa específico.|
| :- | - |
|**Objetivo:**|Permite al Empleado SAT ingresar información para un nuevo registro del catálogo complementario de la sección “Mapa - Objetivo”.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeCatalogos.|
|||
![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.035.png)

**Nota:** Los datos mencionados en la tabla son solo de ejemplo.



### <a name="_toc168581727"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Nuevo registro|Título de la ventana emergente.|
|![ref16]|Opción que permite cerrar la ventana emergente.|
|Objetivo\*:|Permite ingresar el objetivo del registro que se dará de alta.|
|Descripción\*:|Permite ingresar la descripción del registro que se dará de alta.|
|Estatus\*:|<p>Indica el estatus del registro mediante los íconos:</p><p>![ref12]Activo</p><p>![ref17]Inactivo</p>|
|![ref11]|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma:</p><p>![ref12]Activo</p><p>![ref17]Inactivo</p>|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|
|Guardar|Opción que inicia el proceso para almacenar en la base de datos la información ingresada para el registro.|

### <a name="_toc168581728"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Nuevo registro|Texto|N/A|L|Título de la ventana emergente.|N/A|N/A|
|![ref16]|Ícono|N/A|S|Opción que permite cerrar la ventana emergente.|N/A|Usar *tooltip* que muestre el nombre de la opción “Cerrar ventana”.|
|Objetivo\*:|Texto|150|E|Permite ingresar el objetivo del registro que se dará de alta.|N/A|Campo obligatorio|
|Descripción\*:|Alfanumérico|300|E|Permite ingresar la descripción del registro que se dará de alta.|N/A|Campo obligatorio|
|Estatus\*:|Texto|N/A|L|Indica el estatus del registro.|N/A|<p>![ref12]Activo</p><p>![ref17]Inactivo</p><p></p><p>Campo obligatorio</p>|
|![ref11]|Ícono|N/A|S|Opción que permite modificar el estatus del registro a estado activo o inactivo.|N/A|<p></p><p>![ref12]Activo</p><p>![ref17]Inactivo</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Estatus”.</p>|
|Cancelar|Botón|N/A|S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente, se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la base de datos la información ingresada para el registro.|N/A|<p>Inicialmente, se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|



Anexo - Ejemplos de botones

![](Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.036.png)

Las acciones de cada botón se definen en los Estilos correspondientes.

























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
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:** Eduardo Acosta Mora.|
|**Puesto:** Líder Técnico SDMA 6.|**Puesto:** Analista SDMA 6.|
|**Fecha**:|**Fecha**:|
|||

|||Página 6 de 6|
| :- | :-: | -: |

[ref1]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.003.png
[ref2]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.004.png
[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.005.png
[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.006.png
[ref3]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.008.png
[ref4]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.009.png
[ref5]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.010.png
[ref6]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.011.png
[ref7]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.012.png
[ref8]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.013.png
[ref9]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.014.png
[ref10]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.015.png
[ref11]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.016.png
[ref12]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.017.png
[ref13]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.018.png
[ref14]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.019.png
[ref15]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.020.png
[ref16]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.023.png
[ref17]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.024.png
[ref18]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.026.png
[ref19]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.027.png
[ref20]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.028.png
[ref21]: Aspose.Words.eb620d23-df96-4b11-9c5f-f059de92270f.029.png
