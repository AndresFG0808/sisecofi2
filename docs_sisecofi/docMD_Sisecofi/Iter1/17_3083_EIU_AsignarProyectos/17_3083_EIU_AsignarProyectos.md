|![](Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Usuario**</p><p>17\_3083\_EIU\_AsignarProyectos.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación
## <a name="_toc165361393"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :-: | :-: |
|*1*|*Creación del documento*|Isabel Adriana Valdez Cortés|*18/01/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*25/01/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*16/05/2024*|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc165361393)

[Módulo: ASIGNAR PROYECTOS	2](#_toc165361394)

[ESTILOS 01	2](#_toc165361395)

[Descripción de Elementos	3](#_toc165361396)

[Descripción de Campos	4](#_toc165361397)

[ESTILOS 02	10](#_toc165361398)

[Descripción de Elementos	11](#_toc165361399)

[Descripción de Campos	12](#_toc165361400)


## **<a name="_toc165361394"></a><a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a>MÓDULO: ASIGNAR PROYECTOS**
## <a name="_toc459285561"></a><a name="_toc165361395"></a>**ESTILOS 01**

|**Nombre de la Pantalla:** |Asignar Proyectos – Por proyecto|
| :- | :- |
|**Objetivo:**|Permitir al Empleado SAT asignar usuarios a un proyecto.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AsignarProyectos|
|||
![Interfaz de usuario gráfica, Aplicación

Descripción generada automáticamente](Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.002.png)<a name="_toc236129840"></a><a name="_toc236196645"></a> 





### <a name="_toc236129841"></a><a name="_toc236196646"></a><a name="_toc236558259"></a><a name="_toc267478971"></a><a name="_toc165361396"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1] |Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla, contiene los módulos principales y submódulos de este sistema.|
|Asignar Proyectos|Título del encabezado que identifica el submódulo a donde se está ingresando. |
|![ref2]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Contrae la sección !</p><p>Despliega la sección!</p>|
|Por proyecto|Sección que permite buscar el proyecto y muestra el resultado de la búsqueda para asignar usuarios al mismo.|
|Estatus:|Campo que muestra un listado de opciones de acuerdo con el catálogo de “Estatus de proyecto”, se permite seleccionar una opción.|
|Nombre corto del proyecto:|Campo que muestra el listado de nombres cortos de los proyectos que se encuentren con el estatus seleccionado, se permite seleccionar el proyecto al que se requiere asignar usuarios.|
|Id proyecto:|Campo que permite capturar el identificador del proyecto que se requiere buscar.|
|Buscar|Opción que permite iniciar la búsqueda en la base de datos (BD) de los usuarios asignados al proyecto seleccionado y la búsqueda de todos aquellos que se encuentren registrados en este sistema. |
|Usuarios|Apartado donde se muestra el nombre completo de todos los usuarios que han sido agregados al sistema a través del submódulo de “Usuarios”.|
|Usuarios asignados|Apartado donde se muestra el nombre completo de los usuarios que se encuentren relacionados al proyecto seleccionado.|
|![ref5]|Opción que permite filtrar los datos que se muestran en el apartado “Usuarios” y “Usuarios asignados”.|
|![ref6] |Opción que permite seleccionar a un usuario, ya sea de la sección “Usuarios” o “Usuarios asignados”.|
|![ref7]|Opción que mueve todos los datos del apartado “Usuarios” al apartado “Usuarios asignados” en una sola acción.|
|![ref8]|Opción que mueve los datos que se encuentren elegidos del apartado “Usuarios” al apartado “Usuarios asignados”.|
|![ref9]|Opción que mueve todos los datos del apartado “Usuarios asignados” al apartado “Usuarios” en una sola acción.|
|![ref10]|Opción que mueve los datos que se encuentren seleccionados del apartado “Usuarios asignados” al apartado “Usuarios”.|
|![ref11] |<p>Opción que permite exportar la información de la BD de uno o todos los proyectos con sus usuarios asignados, genera un archivo de Excel con extensión (.xlsx) que contiene: </p><p></p><p>- Estatus del proyecto</p><p>- Nombre corto del proyecto</p><p>- Nombre completo del proyecto</p><p>- Id proyecto</p><p>- Usuarios asignados</p><p></p><p>Se genera una fila por cada usuario asignado y que se repita el resto de la información.</p>|
|Guardar|Opción que inicia el proceso para almacenar en la BD la información de los usuarios asignados y los relaciona al proyecto.|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|
|![ref12]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Contrae la sección ![ref12]</p><p>Despliega la sección!</p>|
|Por usuario|Sección que permite capturar los criterios para buscar un usuario, al cual se requiere asignar algún proyecto.|

### <a name="_toc236129842"></a><a name="_toc236196647"></a><a name="_toc236558260"></a><a name="_toc165361397"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla.|N/A|N/A|
|Asignar Proyectos|Texto |N/A|L|Título del encabezado.|N/A |Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|![ref2]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Contrae la sección ![ref12]</p><p>Despliega la sección![ref14]</p>|
|Por proyecto|Sección|N/A|S|Sección que permite buscar el proyecto y muestra el resultado de la búsqueda para asignar usuarios al mismo.|N/A|Por defecto abierta|
|Estatus:|Lista de selección|N/A|L, S|Campo que muestra un listado de opciones de acuerdo con el catálogo de “Estatus de proyecto”, se permite seleccionar una opción.|N/A|N/A|
|Nombre corto del proyecto:|Lista de selección|N/A|L, S|Campo que muestra el listado de nombres cortos de los proyectos que se encuentren con el estatus seleccionado, se permite seleccionar el proyecto al que se requiere asignar usuarios.|N/A|<p>Ejemplo:</p><p>STLD 4</p>|
|Id proyecto:|Numérico|5|L, E|Campo que permite capturar el identificador del proyecto que se requiere buscar.|N/A|<p>Ejemplo:</p><p>00001</p>|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda en la base de datos (BD) de los usuarios asignados al proyecto seleccionado y la búsqueda de todos aquellos que se encuentren registrados en este sistema. |N/A|<p>Inicialmente se muestra sin color de fondo, con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|Usuarios|Texto |N/A|L, S|Apartado donde se muestra el nombre completo de todos los usuarios que han sido agregados al sistema a través del submódulo de “Usuarios”.|N/A|Por defecto se muestra vacía, al realizar una búsqueda ordena los datos alfabéticamente iniciando por Nombre(s).|
|Usuarios asignados|Texto |N/A|L, S|Apartado donde se muestra el nombre completo de los usuarios que se encuentren relacionados al proyecto seleccionado.|N/A|Por defecto se muestra vacía, al realizar una búsqueda ordena los datos alfabéticamente iniciando por Nombre(s).|
|![ref5]|Ícono|N/A|E|Opción que permite filtrar los datos que se muestran en el apartado “Usuarios” y “Usuarios asignados”.|N/A|N/A|
|![ref6]|Casilla de selección|N/A|S|Opción que permite seleccionar a un usuario, ya sea de la sección “Usuarios” o “Usuarios asignados”.|N/A|N/A|
|![ref7]|Ícono|N/A|S|Opción que mueve todos los datos del apartado “Usuarios” al apartado “Usuarios asignados” en una sola acción.|N/A|Usar *tooltip* con el nombre de la opción “Agregar todos”.|
|![ref8]|Ícono|N/A|S|Opción que mueve los datos que se encuentren elegidos del apartado “Usuarios” al apartado “Usuarios asignados”.|N/A|Usar *tooltip* con el nombre de la opción “Agregar”.|
|![ref9]|Ícono|N/A|S|Opción que mueve todos los datos del apartado “Usuarios asignados” al apartado “Usuarios” en una sola acción.|N/A|Usar *tooltip* con el nombre de la opción “Quitar todos”.|
|![ref10]|Ícono|N/A|S|Opción que mueve los datos que se encuentren seleccionados del apartado “Usuarios asignados” al apartado “Usuarios”.|N/A|Usar *tooltip* con el nombre de la opción “Quitar”.|
|![ref11] |Ícono|N/A|S|<p>Opción que permite exportar la información de la BD de uno o todos los proyectos con sus usuarios asignados, genera un archivo de Excel con extensión (.xlsx) que contiene: </p><p>` `Estatus del proyecto, Nombre corto del proyecto, Nombre completo del proyecto, Id proyecto y Usuarios asignados.</p>|N/A|<p>Se genera una fila por cada usuario asignado y que se repita el resto de la información.</p><p></p><p>Usar *tooltip* que muestre el nombre de la opción “Exportar Proyectos-Usuarios”.</p>|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la BD la información de los usuarios asignados y los relaciona al proyecto.|N/A|<p>Inicialmente se muestra sin color de fondo, con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|
|Cancelar|Botón|N/A|S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|
|![ref12]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Contrae la sección ![ref12]</p><p>Despliega la sección![ref14]</p>|
|Por usuario|Sección|N/A|S|Sección que permite capturar los criterios para buscar un usuario, al cual se requiere asignar algún proyecto.|N/A|N/A|



## <a name="_toc165361398"></a>**ESTILOS 02**

|**Nombre de la Pantalla:** |Asignar por usuario|
| :- | :- |
|**Objetivo:**|Permitir al Empleado SAT relacionar proyectos a un usuario.|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AsignarProyectos|
|||

![Interfaz de usuario gráfica, Aplicación

Descripción generada automáticamente](Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.014.png)

**Nota:** Los datos contenidos en las tablas son solo de ejemplo.






### <a name="_toc165361399"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1] |Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla, contiene los módulos principales y submódulos de este sistema.|
|Asignar Proyectos|Título del encabezado que identifica el submódulo a donde se está ingresando. |
|![ref12]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Contrae la sección ![ref12]</p><p>Despliega la sección![ref14]</p>|
|Por proyecto|Sección que permite buscar el proyecto y muestra el resultado de la búsqueda para asignar usuarios al mismo.|
|![ref2]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Contrae la sección ![ref12]</p><p>Despliega la sección![ref14]</p>|
|Por usuario|Sección que permite capturar los criterios para buscar un usuario, al cual se requiere asignar algún proyecto.|
|Usuario\*:|Campo que muestra un listado con el nombre completo de todos los usuarios que se han agregado al sistema previamente y que tengan estatus “Activo”.|
|Buscar|Opción que permite iniciar la búsqueda en la BD de los proyectos asignados al usuario seleccionado y la búsqueda de todos los proyectos registrados en este sistema. |
|Proyectos|Apartado donde se muestra el nombre corto de todos los proyectos registrados en este sistema.|
|Proyectos asignados|Apartado donde se muestra el nombre corto de los proyectos que se encuentren asignados al usuario seleccionado.|
|![ref5]|Opción que permite filtrar los datos que se muestran en el apartado “Proyectos” y “Proyectos asignados”.|
|![Forma, Rectángulo

Descripción generada automáticamente][ref6]|Opción que permite seleccionar una opción.|
|![ref7]|Opción que permite mover todos los datos del apartado “Proyectos” al apartado “Proyectos asignados”.|
|![ref8]|Opción que permite mover los datos que se encuentren seleccionados del apartado “Proyectos” al apartado “Proyectos asignados”.|
|![ref9]|Opción que permite mover todos los datos del apartado “Proyectos asignados” al apartado “Proyectos”.|
|![ref10]|Opción que permite mover los datos que se encuentren seleccionados del apartado “Proyectos asignados” al apartado “Proyectos”.|
|Guardar|Opción que inicia el proceso para almacenar en la BD la información de los proyectos asignados y relacionarlos al usuario.|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|


### <a name="_toc165361400"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla.|N/A|N/A|
|Asignar Proyectos|Texto|N/A|L|Título del encabezado.|N/A|Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|![ref12]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Contrae la sección ![ref12]</p><p>Despliega la sección![ref14]</p>|
|Por proyecto|Sección|N/A|S|Sección que permite buscar el proyecto y muestra el resultado de la búsqueda para asignar usuarios al mismo.|N/A|N/A|
|![ref14]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Contrae la sección ![ref12]</p><p>Despliega la sección![ref14]</p>|
|Por usuario|Sección|N/A|S|Sección que permite capturar los criterios para buscar un usuario, al cual se requiere asignar algún proyecto.|N/A|Ordenados alfabéticamente.|
|Usuario\*:|Lista de selección|250|L, S|Campo que muestra un listado con el nombre completo de todos los usuarios que se han agregado al sistema previamente y que tengan estatus “Activo”.|N/A|El listado debe estar ordenado alfabéticamente, iniciando por el Nombre(s).|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda en la BD de los proyectos asignados al usuario seleccionado y la búsqueda de todos los proyectos registrados en este sistema. |N/A|<p>Inicialmente se muestra sin color de fondo, con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|Proyectos|Texto |N/A|L, S|Apartado donde se muestra el nombre corto de todos los proyectos registrados en este sistema.|N/A|<p>Ejemplo: </p><p>STLD 4</p>|
|Proyectos asignados|Texto |N/A|L, S|Apartado donde se muestra el nombre corto de los proyectos que se encuentren asignados al usuario seleccionado.|N/A|<p>Ejemplo: </p><p>STLD 4</p>|
|![ref5]|Ícono|N/A|E, S|Opción que permite filtrar los datos que se muestran en el apartado “Proyectos” y “Proyectos asignados”.|N/A|N/A|
|![Forma, Rectángulo

Descripción generada automáticamente][ref6]|Casilla de selección|N/A|S|Opción que permite seleccionar una opción.|N/A|N/A|
|![ref7]|Ícono|N/A|S|Opción que permite mover todos los datos del apartado “Proyectos” al apartado “Proyectos asignados”.|N/A|Usar *tooltip* con el nombre de la opción “Agregar todos”.|
|![ref8]|Ícono|N/A|S|Opción que permite mover los datos que se encuentren seleccionados del apartado “Proyectos” al apartado “Proyectos asignados”.|N/A|Usar *tooltip* con el nombre de la opción “Agregar”.|
|![ref9]|Ícono|N/A|S|Opción que permite mover todos los datos del apartado “Proyectos asignados” al apartado “Proyectos”.|N/A|Usar *tooltip* con el nombre de la opción “Quitar todos”.|
|![ref10]|Ícono|N/A|S|Opción que permite mover los datos que se encuentren seleccionados del apartado “Proyectos asignados” al apartado “Proyectos”.|N/A|Usar *tooltip* con el nombre de la opción “Quitar”.|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la BD la información de los proyectos asignados y relacionarlos al usuario.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|
|Cancelar|Botón|N/A|S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|







Anexo - Ejemplos de botones

![](Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.015.png)

Las acciones de cada botón se definen en los estilos correspondientes.








|<a name="_hlk159937993"></a>**FIRMAS DE CONFORMIDAD**||
| :-: | :- |
|**Firma 1** |**Firma 2** |
|**Nombre**: María del Carmen Castillejos Cárdenas.|**Nombre**: Rubén Delgado Ramírez.|
|**Puesto**: Usuaria ACPPI.|**Puesto**: Usuario ACPPI.|
|**Fecha:**|**Fecha:**|
||.|
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
\*\

|||Página 6 de 6|
| :- | :-: | -: |

[ref1]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.003.png
[ref2]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.004.png
[ref3]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.005.png "C48A11DB"
[ref4]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.006.png "F291E3A1"
[ref5]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.007.png
[ref6]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.008.png
[ref7]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.009.png
[ref8]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.010.png
[ref9]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.011.png
[ref10]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.012.png
[ref11]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.013.png
[ref12]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.005.png
[ref12]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.005.png "C48A11DB"
[ref14]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.006.png
[ref14]: Aspose.Words.c3defc95-16e6-4458-888b-9ad00c44edb5.006.png "F291E3A1"
