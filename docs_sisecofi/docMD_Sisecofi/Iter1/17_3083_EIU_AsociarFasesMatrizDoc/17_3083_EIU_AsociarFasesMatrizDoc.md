|![](Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Usuario**</p><p>17\_3083\_EIU\_AsociarFasesMatrizDoc.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento: <a name="_hlk156499682"></a>**TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación


## <a name="_toc168584961"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :-: | :-: | :-: | :-: |
|*1*|*Creación del documento*|Eduardo Acosta Mora|*30/01/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*10/03/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|06/06/2024|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc168584961)

[Módulo: ASOCIAR FASES	2](#_toc168584962)

[ESTILOS 01	2](#_toc168584963)

[Descripción de Elementos	3](#_toc168584964)

[Descripción de Campos	4](#_toc168584965)










##
## **<a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a><a name="_toc168584962"></a>MÓDULO: ASOCIAR FASES** 
## <a name="_toc168584963"></a>**ESTILOS 01**

|**Nombre de la Pantalla:**|<p>Asociar fases matriz documental</p><p></p>|
| :- | - |
|**Objetivo:**|<p>Permitir al Empleado SAT asociar fases al proyecto.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_ASociarFasesMatrizDoc|
|||
![](Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.002.png)

<a name="_toc236129840"></a><a name="_toc236196645"></a>**Nota:** Los datos mencionados en la tabla son solo de ejemplo.


### <a name="_toc236129841"></a><a name="_toc236196646"></a><a name="_toc236558259"></a><a name="_toc267478971"></a><a name="_toc168584964"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Asociar fases |Sección que permite administrar las asociaciones de fases.|
|![ref2]|Opción que permite crear una nueva asociación.|
|![ref3]|Opción que permite exportar la información de la tabla “Asociaciones”, generando un archivo de Excel con extensión (.xlsx).|
|Asociaciones|Título de la tabla, la cual muestra las asociaciones creadas.|
|Fase|<p>Indica la o las fases que tiene el proyecto.</p><p>Campo que permite seleccionar la fase de acuerdo con el seguimiento del proyecto.</p>|
|Plantilla|<p>Indica la o las plantillas que tiene el proyecto.</p><p>Campo que permite seleccionar la plantilla de acuerdo con la fase seleccionada anteriormente.</p>|
|Fecha de asignación|<p>Indica la o las fechas en que se le asocia la plantilla al proyecto.</p><p>Permite seleccionar la fecha de asignación de la plantilla al proyecto.</p>|
|Acciones |Indica las acciones que se pueden hacer con los registros, mediante las opciones ![ref4] y ![ref5].|
|![ref6]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![](Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.011.png)![](Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.012.png)|Campo para filtrar información de la columna donde se requiera buscar específicamente.|
|![ref7]|<p>Opción que habilita los campos y permite editar el registro de la tabla. </p><p>- Se mostrará activo cuando sea un registro existente en la base de datos (BD).</p><p>- Se mostrará inactivo cuando sea un registro nuevo.</p>|
|![ref8]|Opción que permite eliminar el registro de la tabla.|
|![ref9]|Opción que permite descartar la acción |
|![ref10]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado. |
|Guardar|Opción que inicia el proceso para almacenar en la BD las plantillas asociadas.|

### <a name="_toc236129842"></a><a name="_toc236196647"></a><a name="_toc236558260"></a><a name="_toc168584965"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Opción que al ser seleccionada despliega o contrae la sección.|N/A|<p>Sección contraída ![Forma

Descripción generada automáticamente con confianza baja]</p><p>Sección desplegada ![Forma

Descripción generada automáticamente con confianza baja]</p>|
|Asociar fases|Sección|N/A|L|Sección que permite administrar las asociaciones de fases.|N/A|N/A|
|![ref2]|Ícono|N/A|S|Opción que permite crear una nueva asociación.|N/A|Usar *tooltip* que muestre el nombre de la opción “Nuevo”.|
|![ref11]|Ícono|N/A|S|Opción que permite exportar la información de la tabla “Asociaciones”, generando un archivo de Excel con extensión (.xlsx).|N/A|<p></p><p>Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.</p>|
|Asociaciones|Texto|N/A|L|Título de la tabla, la cual muestra las asociaciones creadas.|N/A|N/A|
|Fase|Lista de selección|N/A|L, S|<p>Indica la o las fases que tiene el proyecto.</p><p>Campo que permite seleccionar la fase de acuerdo con el seguimiento del proyecto.</p>|N/A|N/A|
|Plantilla|Lista de selección|N/A|L, S|<p>Indica la o las plantillas que tiene el proyecto.</p><p>Campo que permite seleccionar la plantilla de acuerdo con la fase seleccionada anteriormente.</p>|N/A|N/A|
|Fecha de asignación|Fecha|10|L, E|<p>Indica la o las fechas en que se le asocia la plantilla al proyecto.</p><p>Permite seleccionar la fecha de asignación de la plantilla al proyecto.</p>|N/A|Formato de fecha DD/MM/AAAA|
|Acciones|Texto|N/A|L|Indica las acciones que se pueden hacer con los registros, mediante los opciones ![ref4] y ![ref5].|N/A|N/A|
|![ref6]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![](Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.018.png)|Filtro|N/A|E|Campo para filtrar información de la columna donde se requiera buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la página que se visualiza.|
|![ref7]|Ícono|N/A|S|Opción que habilita los campos y permite editar el registro de la tabla.|N/A|<p>Se mostrará activo cuando sea un registro existente en la BD.</p><p>Se mostrará inactivo cuando sea un registro nuevo.</p><p>Usar *tooltip* que muestre el nombre de la opción “Editar”.</p>|
|![ref8]|Ícono|N/A|S|Opción que permite eliminar el registro de la tabla.|N/A|Usar *tooltip* que muestre el nombre de la opción “Eliminar/ Cancelar”.|
|![ref9]|Ícono|N/A|S|Opción que permite descartar la acción|N/A|N/A|
|![ref12]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Cancelar|Botón|N/A|S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32).</p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.</p>|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la BD las plantillas asociadas.|N/A|<p>Inicialmente, se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.</p>|





Anexo - Ejemplos de botones

![Diagrama

Descripción generada automáticamente](Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.020.png)

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
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:**  Eduardo Acosta Mora|
|**Puesto:** Líder Técnico SDMA 6.|**Puesto:** Analista SDMA 6. |
|**Fecha**:|**Fecha**:|
|||



























|||Página 6 de 6|
| :- | :-: | -: |

[ref1]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.003.png
[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.004.png
[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.005.png
[ref2]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.006.png
[ref3]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.007.png
[ref4]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.008.png
[ref5]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.009.png
[ref6]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.010.png
[ref7]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.013.png
[ref8]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.014.png
[ref9]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.015.png
[ref10]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.016.png
[ref11]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.017.png
[ref12]: Aspose.Words.c44c8121-556a-4dd9-ad10-e9f31c9fa574.019.png
