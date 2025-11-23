|![](Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Usuario**</p><p>17\_3083\_EIU\_AltaDeProyecto.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación
## <a name="_toc168565956"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :-: | :-: |
|*1*|*Creación del documento*|Isabel Adriana Valdez Cortés|*27/01/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*15/02/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*20/05/2024*|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc168565956)

[Módulo: PROYECTOS	2](#_toc168565957)

[ESTILOS 01	2](#_toc168565958)

[Descripción de Elementos	3](#_toc168565959)

[Descripción de Campos	4](#_toc168565960)

[ESTILOS 02	11](#_toc168565961)

[Descripción de Elementos	12](#_toc168565962)

[Descripción de Campos	13](#_toc168565963)


## **<a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a><a name="_toc168565957"></a>MÓDULO: PROYECTOS**
## <a name="_toc459285561"></a><a name="_toc157577608"></a><a name="_toc168565958"></a>**ESTILOS 01**

|**Nombre de la Pantalla:** |<p>Proyectos - Búsqueda</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>El Empleado SAT dependiendo del rol asignado podrá acceder a las opciones para dar de alta, consultar y/o editar el registro, así como ver el plan de trabajo o ver la información del proyecto.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeProyecto|
|||
<a name="_toc236129840"></a><a name="_toc236196645"></a> 

![Interfaz de usuario gráfica, Aplicación

Descripción generada automáticamente](Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.002.png)







### <a name="_toc236129841"></a><a name="_toc236196646"></a><a name="_toc236558259"></a><a name="_toc267478971"></a><a name="_toc157577609"></a><a name="_toc168565959"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1] |Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema. |
|Proyectos|Título del encabezado que identifica el módulo a donde se está ingresando.|
|Búsqueda|Sección que permite acceder a las opciones para dar de alta, consultar y/o editar proyectos, ver el plan de trabajo y/o ver la información del proyecto, en formato de solo lectura.|
|Estatus\*:|Campo que muestra un listado de opciones de acuerdo con el catálogo “Estatus de proyecto”, permite la selección de una opción.|
|Nombre corto del proyecto:|Campo que muestra un listado con el nombre corto de los proyectos que tenga asignados el Empleado SAT.|
|Id proyecto:|Campo que permite capturar el identificador del proyecto que se requiere buscar.|
|Área solicitante:|Campo que muestra el listado de opciones, de acuerdo con el catálogo “Administraciones Centrales”.|
|Área responsable:|Campo que muestra del catálogo “Áreas” las que estén relacionadas con el “Área solicitante” seleccionada.|
|Líder de proyecto:|Campo que permite capturar el nombre del líder del proyecto que se requiere consultar.|
|Buscar|Opción que permite iniciar la búsqueda en la base de datos (BD), de los proyectos que coincidan con los criterios ingresados y que, se encuentre asignados al Empleado SAT que realiza la búsqueda. |
|![ref2]|Opción que permite acceder a la pantalla para dar de alta un nuevo proyecto.|
|![ref3]|Opción que permite exportar la información de la tabla “Proyectos registrados”, generando un archivo de Excel con extensión (.xlsx).|
|Proyectos registrados|Texto que indica el nombre del encabezado de la tabla.|
|![ref4]|Paginador que permite navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Nombre corto del proyecto|Campo que muestra por filas el nombre corto de cada proyecto consultado.|
|Fecha inicio|Campo que muestra por filas la fecha de inicio de cada proyecto.|
|Fecha fin|Campo que muestra por filas la fecha de fin de cada proyecto.|
|Líder de proyecto|Campo que muestra por filas el nombre del líder asignado a cada proyecto.|
|Área solicitante|Campo que muestra por filas el acrónimo de la Administración Central patrocinadora, que se registró para cada proyecto.|
|Área de responsable|Campo que muestra por filas el Área de planeación, que se registró a cada proyecto.|
|Monto solicitado|Campo que muestra por filas el monto solicitado, que se registró a cada proyecto.|
|Estatus|Campo que muestra por filas el estatus del proyecto de cada proyecto.|
|Plan de trabajo|<p>Campo que muestra el texto “Ver” (tipo enlace), en caso de que el proyecto cuente con un plan de trabajo cargado, si no cuenta con un plan cargado, la columna queda vacía.</p><p>El enlace debe direccionar a la funcionalidad de plan de trabajo y mostrar el correspondiente al proyecto donde fue seleccionado.</p>|
|Acciones|Indica las acciones que se pueden realizar: “Editar” o “Ver detalle”, dependiendo el rol del Empleado SAT que ingresa al módulo.|
|![ref5]|Opción que permite editar el proyecto donde es seleccionado, se presenta para los roles que puedan editar un proyecto|
|![](Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.008.png)|Opción que permite visualizar de solo lectura la información del proyecto, se presenta para los roles de consultar proyecto.|
|![ref6]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|<p>![ref7]</p><p></p>|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|![ref8]|Permite desplazarse de manera horizontal en la tabla. |
|![ref9]|Permite desplazarse de manera vertical en la tabla. |


### <a name="_toc236129842"></a><a name="_toc236196647"></a><a name="_toc236558260"></a><a name="_toc157577610"></a><a name="_toc168565960"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla. |N/A|N/A|
|Proyectos|Texto|N/A|L|Título del encabezado.|N/A|Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|Búsqueda|Sección|N/A|L|Sección que permite acceder a las opciones para dar de alta, consultar y/o editar proyectos, ver el plan de trabajo y/o ver la información del proyecto, en formato de solo lectura.|N/A|N/A|
|Estatus\*:|Lista de selección|N/A|S|Campo que muestra un listado de opciones de acuerdo con el catálogo “Estatus de proyecto”, permite la selección de una opción.|N/A|<p>Campo obligatorio.</p><p></p><p>Opciones que contiene el catálogo:</p><p>Inicial, Planeación, Ejecución, Proceso de cierre, Cerrado y Cancelado.</p>|
|Nombre corto del proyecto:|Lista de selección|N/A|S|Campo que muestra un listado con el nombre corto de los proyectos que tenga asignados el Empleado SAT.|N/A|N/A|
|Id proyecto:|Numérico|5|E|Campo que permite capturar el identificador del proyecto que se requiere buscar.|N/A|N/A|
|Área solicitante:|Lista de selección|N/A|S|Campo que muestra el listado de opciones, de acuerdo con el catálogo “Administraciones Centrales”.|N/A|Se debe mostrar el acrónimo de cada Administración Central del catálogo.|
|Área responsable:|Lista de selección|N/A|S|Campo que muestra del catálogo “Áreas”.|N/A|Muestra las que estén relacionadas con el “Área solicitante” seleccionada.|
|Líder de proyecto:|Alfabético|N/A|E|Campo que permite capturar el nombre del líder del proyecto que se requiere consultar.|N/A|N/A|
|Buscar|Botón|N/A|S|Permite iniciar la búsqueda en la base de datos (BD), de los proyectos que coincidan con los criterios ingresados y que, se encuentre relacionados al Empleado SAT que realiza la búsqueda.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref2]|Ícono|N/A|S|Al ser seleccionado muestra el formulario con las secciones correspondientes para dar de alta un nuevo proyecto.|N/A|Usar *tooltip* que muestre el nombre de la opción “Nuevo proyecto”.|
|![ref3]|Ícono|N/A|S|Permite exportar la información de la tabla “Proyectos registrados”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.|
|Proyectos registrados|Texto|N/A|L|Nombre del encabezado de la tabla.|N/A|N/A|
|![ref10]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Nombre corto del proyecto|Alfanumérico|7 |L|Muestra por filas el Nombre corto de cada proyecto consultado.|N/A|N/A|
|Fecha inicio|Fecha|N/A|L|Muestra por filas la fecha de inicio de cada proyecto.|N/A|N/A|
|Fecha fin|Fecha|N/A|L|Muestra por filas la fecha de fin de cada proyecto.|N/A|N/A|
|Líder de proyecto|Texto|N/A|L|Muestra por filas el nombre del líder asignado a cada proyecto.|N/A|N/A|
|Área solicitante|Texto|N/A|L|Muestra por filas el acrónimo de la Administración Central patrocinadora que se registró para cada proyecto.|N/A|N/A|
|Área responsable|Texto|N/A|L|Muestra por filas el Área de planeación que se registró a cada proyecto.|N/A|N/A|
|Monto solicitado|Numérico (decimal)|N/A|L|Muestra por filas el monto solicitado, que se registró a cada proyecto.|N/A|<p>Se muestra el símbolo de pesos, con separador de miles e identificando los decimales a 2 posiciones.</p><p>Ejemplo:</p><p>$10,000.00</p>|
|Estatus|Alfabético|N/A|L|Muestra por filas el estatus del proyecto que se consulta.|N/A|N/A|
|Plan de trabajo|Enlace|N/A|S|Muestra el texto “Ver” (tipo enlace), en caso de que el proyecto cuente con un plan de trabajo cargado, si no cuenta con un plan cargado la columna queda vacía.|N/A|El enlace debe direccionar a la funcionalidad de plan de trabajo y mostrar el correspondiente al proyecto donde fue seleccionado.|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden realizar: “Editar” o “Ver detalle”, dependiendo el rol del Empleado SAT que ingresa al módulo.|N/A|N/A|
|![ref5]|Ícono|N/A|S|Permite editar el proyecto donde es seleccionado.|N/A|<p>Usar *tooltip* “Editar”.</p><p></p><p>Se presenta para los roles que puedan editar.</p>|
|![](Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.014.png)|Ícono|N/A|S|Permite visualizar la información del proyecto.|N/A|<p>Usar *tooltip* “Ver detalle”.</p><p></p><p>Se presenta para los roles que puedan consultar un proyecto</p>|
|![ref6]|Ordenador|N/A|S|Permite acomodar la información de la tabla de forma alfabética, ascendente o descendente, considerando la columna en la que es seleccionado, según aplique.|N/A|N/A|
|![ref7]|Filtro|N/A|E|Permite filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la página que se visualiza.|
|![ref8]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera horizontal en la tabla.|N/A |N/A |
|![ref9]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera vertical en la tabla.|N/A |N/A |


















## <a name="_toc168565961"></a>**ESTILOS 02**

|**Nombre de la Pantalla:** |<p>Proyecto (nuevo)</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT que cuente con el o los roles autorizados, capturar la información para crear un nuevo proyecto.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeProyecto|
|||

![Interfaz de usuario gráfica, Aplicación, Correo electrónico

Descripción generada automáticamente](Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.015.png)






### <a name="_toc168565962"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|<a name="_hlk158470589"></a>**Elemento**|**Descripción**|
| :- | :- |
|![ref1] |Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema. |
|Proyecto|Título del encabezado que identifica el módulo a donde se está ingresando.|
|Última modificación:|Campo que muestra el Nombre del Empleado SAT y la fecha y hora de la última modificación que se encuentre registrada en la BD.|
|Datos generales|Sección que permite capturar los datos para crear un nuevo proyecto.|
|Id:|Campo donde se muestra el identificador único consecutivo generado automáticamente cuando se guarda el proyecto.|
|Nombre corto\*:|Campo que permite capturar el nombre corto que se asignará al proyecto.|
|Estatus:|<p>Campo donde se muestra el estatus en el que se encuentra el proyecto. </p><p>Cuando se crea el proyecto se indica el estatus “Inicial”.</p>|
|Nombre del proyecto\*:|Campo que permite capturar el nombre completo que se asignará al proyecto.|
|Id AGP\*:|Campo que permite al Empleado SAT capturar el identificador de la Administración General de Planeación, que le asignará al proyecto.|
|Guardar|Opción que inicia el proceso para almacenar en la BD la información de los “Datos generales” del proyecto.|
|![ref11]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Sección contraída![ref12]</p><p>Sección desplegada ![ref13]</p>|
|Ficha técnica|Sección que contiene los campos para capturar la ficha técnica.|
|Asociar fases|Sección que contiene los campos para asociar las fases del proyecto con las plantillas de la matriz documental correspondiente.|
|Gestión documental|Sección que contiene la estructura asignada para gestionar la documentación.|
|Información de comités|Sección que contiene los campos para capturar la información de los comités.|
|Plan de trabajo|Sección que permite cargar y actualizar el plan de trabajo relacionado con el proyecto.|
|Participación de proveedores|Sección que contiene los campos para agregar a los proveedores que participan en el proyecto.|
|Verificación de RCP|Sección que permite ir a la pantalla para realizar la verificación de RCP (Repositorio Central de Proyectos).|
|Regresar|Opción que realiza el proceso para regresar a la pantalla anterior, “Estilos 01”.|


### <a name="_toc168565963"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla. |N/A|N/A|
|Proyecto|Texto|N/A|L|Título del encabezado.|N/A|Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|Última modificación|Texto|N/A|L|Campo que muestra el Nombre del Empleado SAT y la fecha y hora de la última modificación que se encuentre registrada en la BD.|N/A|Cuando el proyecto se esté dando de alta el campo se muestra sin información.|
|Datos generales|Sección|N/A|L|Sección que permite capturar los datos para crear un nuevo proyecto.|N/A|N/A|
|Id:|Numérico|5|L|Campo donde se muestra el identificador único consecutivo generado automáticamente cuando se guarda el proyecto.|N/A|<p>Ejemplo: **00001** </p><p>Se muestra después de haber almacenado la información del proyecto creado.</p><p>Formato de texto (Negritas).</p>|
|Nombre corto\*:|Alfanumérico|7|E|Permite capturar el nombre corto que se le asignará al proyecto.|N/A|Campo obligatorio.|
|Estatus:|Texto|N/A|L|Campo donde se muestra el estatus en el que se encuentra el proyecto.|N/A|<p>Cuando se crea el proyecto se indica el estatus “Inicial”.</p><p>Formato del texto (Negritas).</p>|
|Nombre del proyecto\*:|Alfanumérico|250|E|Permite capturar el nombre completo que se asignará al proyecto.|N/A|Campo obligatorio.|
|Id AGP\*:|Alfanumérico|12|E|Campo que permite al Empleado SAT capturar el identificador de la Administración General de Planeación, que le asignará al proyecto.|N/A|<p>Campo obligatorio.</p><p></p><p>Formato:</p><p>aaaa-0000-00</p><p>Usar *tooltip* con el texto “aaaa-0000-00”</p>|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la BD la información de los “Datos generales” del proyecto.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|
|![ref14]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Sección contraída![ref12]</p><p>Sección desplegada ![ref13]</p>|
|Ficha técnica|Sección|N/A|S|<p>Contiene los campos para capturar la ficha técnica.</p><p></p>|N/A|Se activa hasta que se almacenan los datos generales.|
|Asociar fases|Sección|N/A|S|Contiene los campos para asociar las fases del proyecto con las plantillas de la matriz documental correspondiente.|N/A|Inicialmente inhabilitada al crear el proyecto.|
|Gestión documental|Sección|N/A|S|Contiene la estructura asignada para gestionar la documentación.|N/A|Inicialmente inhabilitada al crear el proyecto.|
|Información de comités|Sección|N/A|S|Contiene los campos para capturar la información de los comités.|N/A|Inicialmente inhabilitada al crear el proyecto.|
|Plan de trabajo|Sección|N/A|S|Permite cargar y actualizar el plan de trabajo relacionado con el proyecto.|N/A|Inicialmente inhabilitada al crear el proyecto.|
|Participación de proveedores|Sección|N/A|S|Contiene los campos para agregar a los proveedores que participan en el proyecto.|N/A|Inicialmente inhabilitada al crear el proyecto.|
|Verificación de RCP|Sección|N/A|S|Sección que permite ir a la pantalla para realizar la verificación de RCP (Repositorio Central de Proyectos).|N/A|<p>Inicialmente inhabilitada al crear el proyecto.</p><p></p><p>Al seleccionar la sección, muestra una nueva pantalla para la verificación.  </p>|
|Regresar|Botón|N/A|S|Realiza el proceso para regresar a la pantalla anterior, “Estilos 01”.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|





Anexo - Ejemplos de botones

![Interfaz de usuario gráfica, Diagrama

Descripción generada automáticamente](Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.020.png)

Las acciones de cada botón se definen en los estilos correspondientes.




|<a name="_hlk159937993"></a>**FIRMAS DE CONFORMIDAD**||
| :-: | :- |
|**Firma 1** |**Firma 2** |
|**Nombre**: María del Carmen Castillejos Cárdenas.|**Nombre**: Rubén Delgado Ramírez.|
|**Puesto**: Usuaria ACPPI.|**Puesto**: Usuario ACPPI.|
|**Fecha:**|**Fecha:**|
|||
|**Firma 3** |**Firma 4**|
|**Nombre**: Rodolfo Lopez Meneses.|**Nombre**: Diana Yazmín Pérez Sabido.|
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
\*\

|||Página 6 de 6|
| :- | :-: | -: |

[ref1]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.003.png
[ref2]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.004.png
[ref3]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.005.png
[ref4]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.006.png
[ref5]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.007.png
[ref6]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.009.png
[ref7]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.010.png
[ref8]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.011.png
[ref9]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.012.png
[ref10]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.013.png
[ref11]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.016.png
[ref12]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.017.png
[ref13]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.018.png
[ref14]: Aspose.Words.a22c0b53-f216-4134-ab13-09ed74b1fe93.019.png
