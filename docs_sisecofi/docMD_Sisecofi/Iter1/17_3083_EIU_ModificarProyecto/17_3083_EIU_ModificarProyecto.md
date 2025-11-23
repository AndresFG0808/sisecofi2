|![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Usuario**</p><p>17\_3083\_EIU\_ModificarProyecto.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación
## <a name="_toc167713565"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :-: | :-: |
|*1*|*Creación del documento*|Isabel Adriana Valdez Cortés|*09/02/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*16/02/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*27/05/2024*|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc167713565)

[Módulo: PROYECTOS	2](#_toc167713566)

[ESTILOS 01	2](#_toc167713567)

[Descripción de Elementos	3](#_toc167713568)

[Descripción de Campos	4](#_toc167713569)

[ESTILOS 02	11](#_toc167713570)

[Descripción de Elementos	12](#_toc167713571)

[Descripción de Campos	13](#_toc167713572)

[ESTILOS 03	17](#_toc167713573)

[Descripción de Elementos	18](#_toc167713574)

[Descripción de Campos	22](#_toc167713575)


## **
## <a name="_toc459285561"></a><a name="_toc157577608"></a><a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a><a name="_toc167713566"></a>**MÓDULO: PROYECTOS**
## <a name="_toc167713567"></a>**ESTILOS 01**

|**Nombre de la Pantalla:** |Modificar proyecto|
| :- | :- |
|**Objetivo:**|Permite al Empleado SAT que cuente con el o los roles válidos, agregar o modificar la información en cada una de las secciones que se presentan.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_ModificarProyecto|
|||
<a name="_toc236129840"></a><a name="_toc236196645"></a> 

![Interfaz de usuario gráfica, Texto, Aplicación, Correo electrónico

Descripción generada automáticamente](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.002.png)


**Nota:** Los datos mostrados en la pantalla son solo de ejemplo.

### <a name="_toc236129841"></a><a name="_toc236196646"></a><a name="_toc236558259"></a><a name="_toc267478971"></a><a name="_toc157577609"></a><a name="_toc167713568"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|<a name="_hlk158470589"></a><a name="_toc236129842"></a><a name="_toc236196647"></a><a name="_toc236558260"></a>**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema. |
|Proyecto|Título del encabezado que identifica el módulo a donde se está ingresando.|
|Última modificación:|Campo que muestra el Nombre del Empleado SAT, la fecha y hora de la última modificación que se encuentre registrada en la BD.|
|Datos generales|Sección que permite modificar los datos capturados del proyecto. |
|Id:|Campo donde se muestra el identificador único consecutivo asignado al proyecto.|
|Nombre corto\*:|Campo que muestra el nombre corto que se dio de alta para el proyecto.|
|Estatus:|<p>Campo donde se muestra el estatus en el que se encuentra el proyecto. </p><p>Cuando se crea el proyecto se indica el estatus “Inicial”.</p>|
|![ref2]|Opción que después de ser creado el proyecto, se debe mostrar siempre y cuando el Empleado SAT cuente con el permiso para editar el proyecto. Permite modificar el estatus del proyecto a “Cancelado”.|
|Nombre del proyecto\*:|Campo que permite capturar el nombre completo para el proyecto.|
|Id AGP\*:|Campo que permite al Empleado SAT capturar el identificador de la Administración General de Planeación, que le asignará al proyecto.|
|Cerrado|Opción que se muestra o se oculta, considerando el estatus actual en el que se encuentre el proyecto.|
|Proceso de cierre|Opción que se muestra o se oculta, considerando el estatus actual en el que se encuentre el proyecto.|
|Ejecución|Opción que se muestra o se oculta, considerando el estatus actual en el que se encuentre el proyecto.|
|Inicial|Opción que se muestra o se oculta, dependiendo el estatus actual en el que se encuentre el proyecto.|
|Guardar|Opción que inicia el proceso para almacenar en la base de datos (BD) la información de los “Datos generales” del proyecto.|
|![ref3]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Sección contraída!</p><p>Sección desplegada !</p>|
|Ficha técnica|Sección que contiene los campos para capturar la información de la ficha técnica.|
|Asociar fases|Sección que contiene los campos para asociar las fases del proyecto con las plantillas de la matriz documental correspondiente.|
|Gestión documental|Sección que contiene la estructura asignada para gestionar la documentación.|
|Información de comités|Sección que contiene los campos para capturar la información de los comités.|
|Plan de trabajo|Sección que permite cargar y actualizar el plan de trabajo relacionado con el proyecto.|
|Participación de proveedores|Sección que contiene los campos para agregar a los proveedores que participan en el proyecto.|
|Verificación de RCP|Sección que permite ir a la pantalla para realizar la verificación de RCP (Repositorio Central de Proyectos).|
|Regresar|Opción que realiza el proceso para regresar a la pantalla “Proyectos - Búsqueda”.|

### <a name="_toc157577610"></a><a name="_toc167713569"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla.|N/A|N/A|
|Proyecto|Texto|N/A|L|Título del encabezado.|N/A|Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|Última modificación:|Texto|N/A|L|Campo que muestra el Nombre del Empleado SAT, la fecha y hora de la última modificación que se encuentre registrada en la BD.|N/A|<p>Usar formato de fecha/tiempo:</p><p>DD/MM/AAA HH:MM:SS</p>|
|Datos generales|Sección|N/A|L|Sección que permite modificar los datos capturados del proyecto.|N/A|N/A|
|Id:|Numérico|5|L|Campo donde se muestra el identificador único consecutivo asignado al proyecto.|N/A|Ejemplo: 00001|
|Nombre corto\*:|Alfanumérico|7|L|Campo que muestra el nombre corto que se dio de alta para el proyecto.|N/A|<p>Ejemplo:</p><p>STLD 4</p><p></p><p>Una vez almacenado en la BD,  el campo no se puede modificar, se muestra inhabilitado.</p>|
|Estatus:|Texto|N/A|L|Campo donde se muestra el estatus en el que se encuentra el proyecto.|N/A|Cuando se crea el proyecto se indica el estatus “Inicial”.|
|![ref2]|Ícono|N/A|S|Opción que después de ser creado el proyecto, se debe mostrar siempre y cuando el Empleado SAT cuente con el permiso para editar el proyecto. Permite modificar el estatus del proyecto a “Cancelado”.|N/A|Usar *tooltip* que muestre el nombre de la opción “Cancelar proyecto”.|
|Nombre del proyecto\*:|Alfanumérico|250|E|Campo donde se muestra el nombre completo que se le asignó al proyecto.|N/A|N/A|
|Id AGP\*:|Alfanumérico|12|E|Campo que permite al Empleado SAT capturar el identificador de la Administración General de Planeación, que le asignará al proyecto.|N/A|<p>Campo obligatorio.</p><p></p><p>Formato:</p><p>aaaa-0000.00</p>|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la BD la información de los “Datos generales” del proyecto.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|
|Inicial|Botón|N/A|S|Opción que se muestra o se oculta, dependiendo el estatus actual en el que se encuentre el proyecto.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|Ejecución|Botón|N/A|S|Opción que se muestra o se oculta, considerando el estatus actual en el que se encuentre el proyecto.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|Proceso de cierre|Botón|N/A|S|Opción que se muestra o se oculta, considerando el estatus actual en el que se encuentre el proyecto.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|Cerrado|Botón|N/A|S|Opción que se muestra o se oculta, considerando el estatus actual en el que se encuentre el proyecto.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref6]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Sección contraída![ref7]</p><p>Sección desplegada![ref8]</p>|
|Ficha técnica|Sección|N/A|S|Sección que contiene los campos para capturar la información de la ficha técnica.|N/A|N/A|
|Asociar fases|Sección|N/A|S|Contiene los campos para asociar las fases del proyecto con las plantillas de la matriz documental correspondiente.|N/A|N/A|
|Gestión documental|Sección|N/A|S|Contiene la estructura asignada para gestionar la documentación.|N/A|N/A|
|Información de comités|Sección|N/A|S|Contiene los campos para capturar la información de los comités.|N/A|N/A|
|Plan de trabajo|Sección|N/A|S|Permite cargar y actualizar el plan de trabajo relacionado con el proyecto.|N/A|N/A|
|Participación de proveedores|Sección|N/A|S|Contiene los campos para agregar a los proveedores que participan en el proyecto.|N/A|N/A|
|Verificación de RCP|Sección|N/A|S|Sección que permite ir a la pantalla para realizar la verificación de RCP (Repositorio Central de Proyectos).|N/A|Al seleccionar la sección, se muestra una nueva pantalla para la verificación.  |
|Regresar|Botón|N/A|S|Opción que realiza el proceso para regresar a la pantalla “Proyectos- Búsqueda”.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|
































## <a name="_toc167713570"></a>**ESTILOS 02**

|**Nombre de la Pantalla:** |Modificar proyecto -Datos generales de lectura|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT con rol distinto a “Administrador de sistema”, visualizar la información de los “Datos generales” del proyecto.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_ModificarProyecto|
|||

![Interfaz de usuario gráfica, Aplicación, Correo electrónico

Descripción generada automáticamente](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.011.png)







### <a name="_toc167713571"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema. |
|Proyecto|Título del encabezado que identifica el módulo a donde se está ingresando.|
|Última modificación:|Campo que muestra el Nombre del Empleado SAT, la fecha y hora de la última modificación que se encuentre registrada en la BD.|
|Datos generales|Sección que permite visualizar los datos del proyecto.|
|Id:|Campo donde se muestra el identificador único consecutivo asignado al proyecto.|
|Nombre corto\*:|Campo donde se muestra el nombre corto del proyecto.|
|Estatus:|<p>Campo donde se muestra el estatus en el que se encuentra el proyecto. </p><p>Cuando se crea el proyecto se indica el estatus “Inicial”.</p>|
|Nombre del proyecto\*:|Campo donde se muestra el nombre completo que se le asignó al proyecto. |
|Id AGP\*:|Campo donde se muestra el identificador de la Administración General de Planeación. |
|![ref3]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Sección contraída!</p><p>Sección desplegada !</p>|
|Ficha técnica|Sección que contiene los campos para capturar la información de la ficha técnica.|
|Asociar fases|Sección que contiene los campos para asociar las fases del proyecto con las plantillas de la matriz documental correspondiente.|
|Gestión documental|Sección que contiene la estructura asignada para gestionar la documentación.|
|Información de comités|Sección que contiene los campos para capturar la información de los comités.|
|Plan de trabajo|Sección que permite cargar y actualizar el plan de trabajo relacionado con el proyecto.|
|Participación de proveedores|Sección que contiene los campos para agregar a los proveedores que participan en el proyecto.|
|Verificación de RCP|Sección que permite ir a la pantalla para realizar la verificación de RCP (Repositorio Central de Proyectos).|
|Regresar|Opción que realiza el proceso para regresar a la pantalla “Proyectos - Búsqueda”.|



### <a name="_toc167713572"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla.|N/A|N/A|
|Proyecto|Texto|N/A|L|Título del encabezado.|N/A|Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|Última modificación|Texto|N/A|L|Campo que muestra el Nombre del Empleado SAT, la fecha y hora de la última modificación que se encuentre registrada en la BD.|N/A|<p>Usar formato de fecha/tiempo:</p><p>DD/MM/AAA HH:MM:SS</p>|
|Datos generales|Sección|N/A|L|Sección que permite visualizar los datos del proyecto.|N/A|N/A|
|Id:|Numérico|5|L|Campo donde se muestra el identificador único consecutivo asignado al proyecto.|N/A|Ejemplo: 00001|
|Nombre corto\*:|Alfanumérico|7|L|Campo donde se muestra el nombre corto del proyecto.|N/A|N/A|
|Estatus:|Texto|N/A|L|Campo donde se muestra el estatus en el que se encuentra el proyecto.|N/A|Cuando se crea el proyecto se indica el estatus “Inicial”.|
|Nombre del proyecto\*:|Alfanumérico|250|L|Campo donde se muestra el nombre completo que se le asignó al proyecto.|N/A|N/A|
|Id AGP\*:|Alfanumérico|12|L|Campo donde se muestra el identificador de la Administración General de Planeación.|N/A|<p>Formato:</p><p>aaaa-0000.00</p>|
|![ref6]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Sección contraída![ref7]</p><p>Sección desplegada![ref8]</p>|
|Ficha técnica|Sección|N/A|S|Sección que contiene los campos para capturar la información de la ficha técnica.|N/A|N/A|
|Asociar fases|Sección|N/A|S|Contiene los campos para asociar las fases del proyecto con las plantillas de la matriz documental correspondiente.|N/A|N/A|
|Gestión documental|Sección|N/A|S|Contiene la estructura asignada para gestionar la documentación.|N/A|N/A|
|Información de comités|Sección|N/A|S|Contiene los campos para capturar la información de los comités.|N/A|N/A|
|Plan de trabajo|Sección|N/A|S|Permite cargar y actualizar el plan de trabajo relacionado con el proyecto.|N/A|N/A|
|Participación de proveedores|Sección|N/A|S|Contiene los campos para agregar a los proveedores que participan en el proyecto.|N/A|N/A|
|Verificación de RCP|Sección|N/A|S|Sección que permite ir a la pantalla para realizar la verificación de RCP.|N/A|Al seleccionar la sección, se muestra una nueva pantalla para la verificación.  |
|Regresar|Botón|N/A|S|Opción que realiza el proceso para regresar a la pantalla “Proyectos- Búsqueda”.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|




## <a name="_toc167713573"></a>**ESTILOS 03**

|**Nombre de la Pantalla:** |Ficha técnica|
| :- | :- |
|**Objetivo:**|Permitir al Empleado SAT capturar la información de la sección Ficha técnica, relacionada al proyecto.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_ModificarProyecto|

![Interfaz de usuario gráfica

Descripción generada automáticamente](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.012.png)
### <a name="_toc167713574"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref11]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Sección contraída![ref7]</p><p>Sección desplegada![ref8]</p>|
|Ficha técnica|Sección que contiene los campos para capturar la información de la ficha técnica.|
|Administración patrocinadora\*:|Campo que muestra el listado de acrónimos de las Administraciones Generales de acuerdo con el catálogo correspondiente.|
|<p>Nombre de la admón.</p><p>patrocinadora:</p>|Campo donde se muestra automáticamente el nombre completo de la Administración General de acuerdo con el acrónimo seleccionado.|
|Administrador patrocinador:|Campo donde se muestra el nombre completo del administrador que se encuentre asignado para la Administración General seleccionada.|
|Administración central patrocinadora\*:|Campo que muestra el listado de acrónimos de las Administraciones Centrales patrocinadoras de acuerdo con el catálogo correspondiente.|
|Nombre de la admón. central patrocinadora:|Campo donde se muestra automáticamente el nombre completo de la Administración Central patrocinadora de acuerdo con el acrónimo seleccionado.|
|Administrador central patrocinador:|Campo donde se muestra el nombre completo del administrador que se encuentre asignado para la Administración Central seleccionada.|
|![ref12]|<p>Opción que se muestra debajo del primer bloque de campos de “Administrador central patrocinador” y permite agregar un nuevo bloque de los siguientes campos para registrar otra “Administración central patrocinadora”:</p><p></p><p>- Administración central patrocinadora\*</p><p>- Nombre de la admón. central patrocinadora</p><p>- Administrador central patrocinador</p>|
|![ref13]|Opción que se muestra debajo del campo “Administrador central patrocinador” a partir del segundo bloque de administración central agregado, permite eliminar el bloque de “Administración central patrocinadora”, donde sea seleccionado.|
|Administración participante:|Campo que muestra el listado de acrónimos de las Administraciones Generales de acuerdo con el catálogo correspondiente.|
|Nombre de la admón. participante:|Campo donde se muestra automáticamente el nombre completo de la “Administración participante” de acuerdo con el acrónimo seleccionado.|
|Administrador participante:|Campo donde se muestra el nombre completo del administrador que se encuentre asignado para la Administración General seleccionada.|
|Clasificación del proyecto\*:|<p>Campo que muestra las siguientes opciones de acuerdo con el catálogo correspondiente:</p><p></p><p>- Nuevo</p><p>- Continuidad</p>|
|Financiamiento\*:|<p>Campo que muestra las opciones las siguientes de acuerdo con el catálogo correspondiente:</p><p></p><p>- FACLA</p><p>- FAPA</p><p>- PEF</p>|
|Tipo de procedimiento\*:|<p>Campo que permite seleccionar una opción del listado que se muestra de acuerdo con el catálogo de “Tipos de contratación”, opciones que se muestran:</p><p>- Licitación Pública</p><p>- Adjudicación Directa</p><p>- Invitación a cuando menos 3 personas</p>|
|Líder del proyecto\*:|Titulo para identificar el apartado en donde se captura la información obligatoria del líder del proyecto, permitiendo dar de alta o baja y visualizando el histórico de los mismos.|
|![ref14]|Opción que permite agregar un nuevo 'Líder del proyecto” solo si no existe un líder con estatus “Activo” para el proyecto. |
|![ref15]|Opción que permite exportar la información de la tabla “Histórico” generando un archivo de Excel con extensión (.xlsx).|
|Historico|Encabezado de la tabla que contiene el historial de los empleados del SAT que se han asignado como líder del proyecto, mostrando a los que ya están inactivos y al líder actual activo.|
|![ref16]|Paginador que permite navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Líder del proyecto|Campo que muestra un listado de los usuarios con estatus activo que estén dados de alta en el sistema (se obtiene del módulo de “Usuarios”).|
|Puesto|<p>Campo donde se muestra el puesto del “Líder del proyecto”, el cual se obtiene del módulo de “Usuarios”.</p><p>Una vez almacenado, muestra el dato que se encuentre en la BD.</p>|
|<a name="_hlk158721851"></a>Correo|<p>Campo donde se muestra el correo del “Líder del proyecto”, el cual se obtiene del módulo de “Usuarios”.</p><p>Una vez almacenado, muestra el dato que se encuentre en la BD.</p>|
|Fecha inicio|Campo que inicialmente muestra un calendario ![ref17], permitiendo seleccionar la fecha de inicio del “Líder del proyecto” y una vez almacenado, se muestra el dato que se encuentre en la BD.|
|Fecha fin|Campo que inicialmente muestra un calendario ![ref17], permitiendo seleccionar la fecha en que el “Líder del proyecto” se inactiva. Una vez almacenado en la BD se muestra la información registrada.|
|Estatus|Campo que muestra el ícono ![ref18] para indicar el estatus del “Líder del proyecto” ya sea “Activo” o “Inactivo”.|
|Acciones|Indica las acciones que se pueden realizar: “Editar”, “Eliminar” o “Descartar”, dependiendo el rol del Empleado SAT que ingresa al módulo.|
|![ref19]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.022.png "9221BAC") |Opción que muestra un calendario y permite seleccionar una fecha. |
|<p></p><p>![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.023.png "CB2F7A9A") </p>|<p>Opción que permite modificar el estatus del registro a estado activo o inactivo de la siguiente forma: </p><p>![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.024.png "DA30ADB8")Activo</p><p>![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.025.png "10EA3486")Inactivo </p>|
|![ref20]|Opción que permite cancelar la acción de edición.|
|! |Opción que permite editar los registros en la tabla. |
|! |Opción que permite eliminar los registros en la tabla. |
|Alineación del proyecto\*:|Titulo para identificar el apartado en donde se captura la información obligatoria de la alineación del proyecto, permitiendo agregar o editar la información de alineación.|
|![ref14]|Opción que permite agregar a un nuevo registro de “Alineación del proyecto”. |
|![ref15]|Opción que permite exportar la información de la tabla “Alineación”, generando un archivo de Excel con extensión (.xlsx).|
|Mapa|<p>Campo que inicialmente muestra el listado de las opciones que se encuentran en el catálogo de “Alineación”, contiene:</p><p></p><p>- ` `Mapa estratégico</p><p>- ` `Mapa especifico de la AGCTI</p><p>&emsp;</p><p>Una vez almacenado el registro, se debe mostrar el dato que se encuentre en la BD.</p>|
|Periodo|<p>Campo que inicialmente muestra el listado de las opciones que se encuentran en el catálogo de “Periodo”.</p><p>Una vez almacenado el registro, se debe mostrar el dato que se encuentra en la BD.</p>|
|Objetivo|<p>Campo que inicialmente muestra el listado de las opciones que se encuentran como objetivos de la alineación del proyecto, ejemplo de lo que contiene:</p><p></p><p>- U01-Facilidad </p><p>- U02- Piso parejo</p><p>- P01-Incentivar el cumplimiento</p><p></p><p>Una vez almacenado el registro, se debe mostrar el dato que se encuentra en la BD.</p>|
|Acciones|Indica las acciones que se pueden realizar: “Editar”, “Eliminar” o “Descartar”, dependiendo el rol del Empleado SAT que ingresa al módulo.|
|![ref20]|Opción que permite cancelar la acción de edición.|
|!|Opción que permite editar los registros en la tabla. |
|! |Opción que permite eliminar los registros en la tabla. |
|![ref25]|Permite desplazarse de manera horizontal en la tabla. |
|![ref26]|Permite desplazarse de manera vertical en la tabla. |
|Fecha de inicio del proyecto\*:|<p>Campo de calendario que permite seleccionar la fecha de inicio del proyecto.</p><p>Una vez almacenado, se debe mostrar el dato que se encuentra en la BD.</p>|
|Fecha fin del proyecto\*:|<p>Campo de calendario que permite seleccionar la fecha fin del proyecto.</p><p>Una vez almacenado, se debe mostrar el dato que se encuentra en la BD.</p>|
|Área de planeación\*:|<p>Campo que muestra el listado de opciones de acuerdo con el catálogo de “Áreas” que estén relacionadas con la Administración General “AGCTI” y la Administración Central “ACPPI”.</p><p>Una vez almacenado, se debe mostrar el dato que se encuentra en la BD.</p>|
|Monto solicitado:|Campo que permite capturar el monto que se solicita para el proyecto.|
|Tipo de moneda\*:|<p>Campo que muestra el listado de opciones de acuerdo con el catálogo “Tipo de moneda”:</p><p></p><p>- MXN (pesos)</p><p>- USD (dólares americanos)</p><p>&emsp;</p><p>Una vez almacenado, se debe mostrar el dato que se encuentra en la BD.</p>|
|Objetivo general\*:|<p>Campo que permite capturar el objetivo del proyecto.</p><p>Una vez almacenado, se debe mostrar la información que se encuentra en la BD.</p>|
|Alcance:|<p>Campo que permite capturar el alcance del proyecto.</p><p>Una vez almacenado, se debe mostrar el dato que se encuentra en la BD.</p>|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|
|Guardar|Opción que inicia el proceso para almacenar en la BD la información que se captura para la ficha técnica del proyecto.|



### <a name="_toc167713575"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref11]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Sección contraída![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.031.gif)</p><p>Sección desplegada![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.031.gif) </p>|
|Ficha técnica|Sección|N/A|S|Sección que contiene los campos para capturar la información de la ficha técnica.|N/A|N/A|
|<p>Administración</p><p>patrocinadora\*:</p>|Lista de selección|N/A|S|Muestra el listado de acrónimos de las Administraciones Generales de acuerdo con el catálogo correspondiente.|N/A|Campo obligatorio.|
|<p>Nombre de la admón.</p><p>patrocinadora:</p>|Alfabético|N/A|L|Muestra automáticamente el nombre completo de la Administración General de acuerdo con el acrónimo seleccionado.|N/A|Se obtiene de acuerdo con el catálogo correspondiente.|
|Administrador patrocinador:|Alfabético|N/A|L|Muestra el nombre completo del administrador que se encuentre asignado para la Administración General seleccionada.|N/A|Se obtiene de acuerdo con el catálogo correspondiente.|
|Administración central patrocinadora\*:|Lista de selección|N/A|S|Muestra el listado de acrónimos de las Administraciones Centrales patrocinadoras de acuerdo con el catálogo correspondiente.|N/A|<p>Campo obligatorio.</p><p></p><p>Se debe obtener del catálogo de las “Administraciones Centrales” las que correspondan con la “Administración patrocinadora” (Administración General).</p>|
|Nombre de la admón. central patrocinadora:|Alfabético|N/A|L|Campo que muestra automáticamente el nombre completo de la Administración Central patrocinadora de acuerdo con el acrónimo seleccionado.|N/A|Se obtiene de acuerdo con el catálogo correspondiente.|
|Administrador central patrocinador:|Alfabético|N/A|L|Muestra el nombre completo del administrador que se encuentre asignado para la Administración Central seleccionada.|N/A|Se obtiene de acuerdo con el catálogo correspondiente.|
|![ref12]|Ícono|N/A|S|Opción que se muestra debajo del primer bloque de “Administración central patrocinador” y permite agregar un nuevo bloque.|N/A|<p>Se agregan los campos:</p><p>- Administración central patrocinadora\*</p><p>- Nombre de la admón. central patrocinador</p><p>- Administrador central patrocinador</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Agregar Administración Central”.</p><p>Los campos que se agreguen deberán ir al final del último que se tenga.</p>|
|![ref13]|Ícono|N/A|S|Opción que se muestra debajo del campo “Administrador central patrocinador” a partir del segundo bloque de administración central agregado, permite eliminar el bloque de “Administración central patrocinadora”, donde sea seleccionado.|N/A|<p>Se debe mostrar al final de cada bloque de “Administración central patrocinadora” que se tenga.</p><p>Usar *tooltip* que muestre el nombre de la opción “Eliminar Administración Central”.</p>|
|Administración participante:|Lista de selección|N/A|S|Muestra el listado de acrónimos de las Administraciones Generales de acuerdo con el catálogo correspondiente.|N/A|N/A|
|Nombre de la admón. participante:|Alfabético|N/A|L|Muestra automáticamente el nombre completo de la “Administración participante” de acuerdo con el acrónimo seleccionado.|N/A|N/A|
|Administrador participante:|Alfabético|N/A|L|Muestra el nombre completo del administrador que se encuentre asignado para la Administración General seleccionada.|N/A|N/A|
|Clasificación del proyecto\*:|Lista de selección|N/A|S|Muestra las opciones de acuerdo con el catálogo correspondiente.|N/A|<p>Campo obligatorio.</p><p></p><p>Opciones:</p><p>Nuevo y Continuidad.</p>|
|Financiamiento\*:|Lista de selección|N/A|S|Muestra las opciones de acuerdo con el catálogo correspondiente.|N/A|<p>Campo obligatorio.</p><p></p><p>Opciones: FACLA</p><p>FAPA</p><p>PEF</p>|
|Tipo de procedimiento\*:|Lista de selección|N/A|S|Campo que permite seleccionar una opción del listado que se muestra de acuerdo con el catálogo de “Tipos de contratación”.|N/A|<p>Campo obligatorio.</p><p></p><p>Opciones que se muestran:</p><p>Licitación Pública, Adjudicación Directa e </p><p>Invitación a cuando menos 3 personas.</p>|
|Líder del proyecto\*:|Texto|N/A|L|Identifica el apartado en donde se captura la información obligatoria del líder del proyecto, permitiendo dar de alta o baja y visualizando el histórico de los mismos.|N/A|Campo obligatorio.|
|![ref14]|Ícono|N/A|S|Permite agregar un nuevo 'Líder del proyecto” solo si el último líder agregado está inactivo para el proyecto|N/A|Usar *tooltip* que muestre el nombre de la opción “Agregar líder”.|
|![ref15]|Ícono|N/A|S|Permite exportar la información de la tabla “Histórico”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar histórico”.|
|Historico|Texto|N/A|L|Encabezado de la tabla que contiene el historial de los empleados SAT que se han asignado como líder del proyecto, mostrando a los que ya están inactivos y al líder actual activo.|N/A|N/A|
|![ref27]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Líder del proyecto|Lista de selección|N/A|S|Campo que muestra un listado de los usuarios con estatus activo que estén dados de alta en el sistema (se obtiene del módulo de “Usuarios”).|N/A|<p>Campo obligatorio.</p><p></p><p>Una vez almacenado muestra el nombre completo de la persona que se agregó como líder del proyecto.</p>|
|Puesto|Alfanumérico|N/A|L|Campo donde se muestra el puesto del “Líder del proyecto”, el cual se obtiene del módulo de “Usuarios”.|N/A|<p>Una vez almacenado se muestra el dato que se encuentre en la BD.</p><p></p><p>Usar *tooltip* con el texto *“*Puesto registrado en el módulo de Usuarios”.</p>|
|Correo|Alfanumérico|N/A|L|<p>Campo donde se muestra el correo del “Líder del proyecto”, el cual se obtiene del módulo de “Usuarios”.</p><p>Una vez almacenado, muestra el dato que se encuentre en la BD.</p>|N/A|Una vez almacenado se muestra el dato que se encuentre en la BD.|
|Fecha inicio|Ícono y Texto|10|L, S|<p>Inicialmente muestra un calendario ![ref17], permitiendo </p><p>seleccionar la fecha de inicio del “Líder del proyecto” y una vez almacenado, muestra el dato que se encuentre en la BD.</p>|N/A|<p></p><p>Campo obligatorio.</p><p></p><p>Formato de fecha:</p><p>DD/MM/AAAA</p><p>Por defecto debe estar seleccionado el día actual.</p><p>Permite seleccionar una fecha anterior al día actual, pero no una fecha posterior.</p>|
|Fecha fin|Ícono y Texto|10|L, S|Inicialmente muestra un calendario ![ref17], permitiendo seleccionar la fecha en que el “Líder del proyecto” se inactiva. Una vez almacenado en la BD se muestra la información registrada.|N/A|<p>Formato de fecha:</p><p>DD/MM/AAAA.</p><p></p><p>Permite seleccionar una fecha anterior al día actual, pero no una fecha posterior.</p>|
|Estatus|Texto|N/A|L|Campo que muestra el ícono ![ref18] para indicar el estatus del “Líder del proyecto” ya sea “Activo” o “Inactivo”.|N/A|N/A|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden realizar: “Editar”, “Eliminar” o “Descartar”, dependiendo el rol del Empleado SAT que ingresa al módulo.|N/A|N/A|
|![ref19]|Ordenador|N/A|S|Permite acomodar la información de la tabla de forma alfabética, ascendente o descendente, considerando la columna en la que es seleccionado, según aplique.|N/A|N/A|
|![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.033.png)|Ícono |N/A |S |Opción que muestra un calendario y permite seleccionar una fecha. |N/A|<p>Formato de fecha:</p><p>DD/MM/AAAA</p>|
|![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.034.png)|Ícono |N/A |S |Opción que permite modificar el estatus del registro a estado activo o inactivo. |N/A |<p>![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.024.png "1AC6345C")Activo</p><p>![](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.025.png "9FE306CA")Inactivo </p><p></p>|
|![ref20]|Ícono|N/A|S|Opción que permite cancelar la acción de edición.|N/A|Usar el *tooltip* “Descartar”.|
|! |Ícono|N/A|S|Opción que permite editar los registros en la tabla. |N/A|Usar el *tooltip* “Editar”.|
|! |Ícono|N/A|S|Opción que permite eliminar los registros en la tabla. |N/A|Usar el *tooltip* “Eliminar”.|
|Alineación del proyecto\*:|Texto|N/A|L|Texto para identificar el apartado en donde se captura la información obligatoria de la alineación del proyecto, permitiendo agregar o editar la información de alineación.|N/A|<p>Campos de la tabla son obligatorios.</p><p>Por lo menos se debe capturar 1 opción de cada mapa.</p>|
|![ref14]|Ícono|N/A|S|Permite agregar a un nuevo registro de “Alineación del proyecto”. |N/A|Usar *tooltip* que muestre el nombre de la opción “Agregar alineación”.|
|![ref15]|Ícono|N/A|S|Permite exportar la información de la tabla de “Alineación”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar alineación”.|
|Mapa|Lista de selección|N/A|S|Campo que inicialmente muestra el listado de las opciones que se encuentran en el catálogo de “Alineación”.|N/A|<p>Campo obligatorio.</p><p></p><p>Contiene:</p><p>` `Mapa estratégico.</p><p>` `Mapa específico de la AGCTI.</p><p>Una vez almacenado el registro, se debe mostrar el dato que se encuentre en la BD.</p>|
|Periodo|Lista de selección|N/A|S|Campo que inicialmente muestra el listado de las opciones que se encuentran en el catálogo de “Periodo”.|N/A|<p>Campo obligatorio.</p><p></p><p>Una vez almacenado el registro, se debe mostrar el dato que se encuentra en la BD.</p>|
|Objetivo|Lista de selección|N/A|S|Campo que inicialmente muestra el listado de las opciones que se encuentran como objetivos de la Alineación.|N/A|<p>Campo obligatorio.</p><p></p><p>Ejemplo de lo que contiene:</p><p>- U01-Facilidad</p><p>- U02- Piso parejo</p><p>- P01-Incentivar el cumplimiento</p><p></p><p>Una vez almacenado el registro, se debe mostrar el dato que se encuentra en la BD.</p>|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden realizar: “Editar”, “Eliminar” o “Descartar”, dependiendo el rol del Empleado SAT que ingresa al módulo.|N/A|N/A|
|![ref20]|Ícono|N/A|S|Opción que permite cancelar la acción de edición.|N/A|Usar el *tooltip* “Descartar”.|
|!|Ícono|N/A|S|Opción que permite editar los registros en la tabla. |N/A|<p>Usar el *tooltip* “Editar”.</p><p></p><p>Cuando es seleccionado habilita los campos de acuerdo con la definición de cada campo.</p>|
|! |Ícono|N/A|S|Opción que permite eliminar los registros en la tabla. |N/A|Usar el *tooltip* “Eliminar”.|
|![ref25]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera horizontal en la tabla.|N/A |N/A|
|![ref26]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera vertical en la tabla.|N/A |N/A|
|Fecha de inicio del proyecto\*:|Fecha|10|S|Campo de calendario que permite seleccionar la fecha de inicio del proyecto.|N/A|<p>Campo obligatorio.</p><p></p><p>Formato: DD/MM/AAAA</p><p></p><p>Una vez almacenado se debe mostrar el dato que se encuentra en la BD.</p>|
|Fecha fin del proyecto\*:|Fecha|10|S|Campo de calendario que permite seleccionar la fecha fin del proyecto.|N/A|<p>Campo obligatorio.</p><p></p><p>Formato: DD/MM/AAAA</p><p></p><p>Una vez almacenado se debe mostrar el dato que se encuentra en la BD.</p>|
|Área de planeación\*:|Lista de selección|N/A|S|Muestra el listado de opciones de acuerdo con el catálogo de “Áreas” que estén relacionadas con la Administración General “AGCTI” y la Administración Central “ACPPI”.|N/A|<p>Campo obligatorio.</p><p></p><p>Una vez almacenado se debe mostrar el dato que se encuentra en la BD.</p>|
|Monto solicitado:|<p>Numérico</p><p>(Decimal)</p>|20|E|Campo que permite capturar el monto que se solicita para el proyecto.|N/A|Se consideran números decimales con formato $0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00|
|Tipo de moneda\*:|Lista de selección|N/A|S|Campo que muestra el listado de opciones de acuerdo al catálogo de “Tipo de moneda”.|N/A|<p>Campo obligatorio.</p><p></p><p>Una vez almacenado se debe mostrar el dato que se encuentra en la BD.</p><p></p><p>Opciones del listado:</p><p>- MXN (pesos)</p><p>- USD (dólares americanos)</p>|
|Objetivo general\*:|Alfanumérico|250|E|Campo que permite capturar el objetivo del proyecto.|N/A|<p>Campo obligatorio.</p><p></p><p>Una vez almacenado se debe mostrar la información que se encuentra en la BD.</p>|
|Alcance:|Alfanumérico|250|E|Campo que permite capturar el alcance del proyecto.|N/A|Una vez almacenado se debe mostrar el dato que se encuentra en la BD.|
|Cancelar|Botón|N/A|S|Realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la BD la información que se captura para la ficha técnica del proyecto.|N/A |<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|





Anexo - Ejemplos de botones

![Interfaz de usuario gráfica, Diagrama

Descripción generada automáticamente](Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.035.png)

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
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:** Isabel Adriana Valdez Cortés.|
|**Puesto:** Líder Técnico SDMA 6.|**Puesto:** Analista de Sistemas DS SDMA 6. |
|**Fecha**:|**Fecha**:|
|||
\*\

|||Página 6 de 6|
| :- | :-: | -: |

[ref1]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.003.png
[ref2]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.004.png
[ref3]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.005.png
[ref4]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.006.png "C48A11DB"
[ref5]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.007.png "F291E3A1"
[ref6]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.008.png
[ref7]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.009.png
[ref8]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.010.png
[ref11]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.013.png
[ref12]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.014.png
[ref13]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.015.png
[ref14]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.016.png
[ref15]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.017.png
[ref16]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.018.png
[ref17]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.019.png
[ref18]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.020.png
[ref19]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.021.png
[ref20]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.026.png
[ref21]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.027.png "1F37C12B"
[ref22]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.028.png "C3BB5F71"
[ref25]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.029.png
[ref26]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.030.png
[ref27]: Aspose.Words.4bc48b94-da81-4225-b449-ebf55b821f2f.032.png
