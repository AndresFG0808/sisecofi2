|![](Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Usuario**</p><p>17\_3083\_EIU\_ConsultarPistasAuditoria.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |

**<ID Requerimiento>** 8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación
## <a name="_toc171950056"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :-: | :-: |
|*1*|*Creación del documento*|María del Carmen Gutiérrez Sánchez|*25/06/2024*|
|*1.1*|*Revisión del documento*|Diana Yazmín Pérez Sabido|*17/07/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*18/07/2024*|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc171950056)

[Módulo: PISTAS DE AUDITORÍA	2](#_toc171950057)

[ESTILOS 01	2](#_toc171950058)

[Descripción de Elementos	3](#_toc171950059)

[Descripción de Campos	4](#_toc171950060)

[ESTILOS 02	9](#_toc171950061)

[Descripción de Elementos	10](#_toc171950062)

[Descripción de Campos	10](#_toc171950063)

[ESTILOS 03	12](#_toc171950064)

[Descripción de Elementos	13](#_toc171950065)

[Descripción de Campos	13](#_toc171950066)


## **<a name="_toc171950057"></a><a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a>MÓDULO: PISTAS DE AUDITORÍA**
## <a name="_toc171950058"></a>**ESTILOS 01**

|**Nombre de la Pantalla:** |<p>Pistas de Auditoría</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT realizar la consulta de acuerdo con los criterios solicitados para visualizar la información de los “Movimientos” que coincidan con la búsqueda, además de permitirle acceder al detalle del movimiento.</p><p></p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ ECU\_ConsultarPistasAuditoria|

![Interfaz de usuario gráfica

Descripción generada automáticamente](Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.002.png)




### <a name="_toc171950059"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema.|
|Pistas de auditoría|Título del encabezado que contiene el nombre del módulo “Pistas de Auditoría”.|
|Consultar pistas de auditoría|Sección donde se muestran los criterios de búsqueda.|
|Fecha de Inicio\*:|Campo que permite la selección de la fecha inicio. |
|Fecha fin\*:|Campo que permite la selección de la fecha fin. |
|Empleado SAT:|Campo que permite seleccionar el Empleado SAT.|
|Módulo\*:|Campo que permite la selección del módulo. |
|Sección\*:|Campo que permite la selección de la sección relacionada al módulo.|
|Tipo de movimiento:|Campo que permite la selección del tipo de movimiento.|
|Limpiar|Opción que permite limpiar los criterios de búsqueda.|
|Buscar|Opción que permite iniciar la búsqueda en la base de datos (BD) de la información de acuerdo con los criterios de búsqueda ingresados.|
|![ref2]|Opción que permite exportar la información de la tabla “Movimientos”, generando un archivo de Excel con extensión (.xlsx).|
|Movimiento|Nombre del encabezado de la tabla.|
|![ref3]|Paginador que permite navegar a través de las páginas resultantes de la consulta considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Módulo|Campo que muestra el nombre del módulo.|
|Sección|Campo que muestra el nombre de la sección.|
|Fecha y hora|Campo que muestra la fecha y hora del movimiento. |
|Empleado SAT|Campo que muestra el nombre del Empleado SAT.|
|RFC |Campo que muestra el RFC del Empleado SAT.|
|Tipo de movimiento|Campo que muestra el Tipo de movimiento.|
|Acciones|Nombre de la columna que muestra las acciones que se pueden realizar.|
|![](Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.006.png)|Opción que visualizar el detalle de la información de las Pistas de auditoría.|
|![ref4]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref5]|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|![ref6]|Permite desplazarse de manera horizontal en la tabla. |
|![ref7]|Permite desplazarse de manera vertical en la tabla. |

### <a name="_toc171950060"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla.|N/A|N/A|
|Pistas de auditoría|Texto|N/A|L|Título del encabezado que contiene el nombre del módulo “Pistas de Auditoría”.|N/A|Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|Consultar pistas de auditoría|Sección|N/A|L|Sección donde se muestran los criterios de búsqueda.|N/A|N/A|
|Fecha de Inicio\*:|Fecha|10|L, E, S|Campo que permite la selección de la fecha inicio. |N/A|<p>Campo obligatorio.</p><p>Formato de fecha DD/MM/AAAA.</p>|
|Fecha fin\*:|Fecha|N/A|S, L|Campo que permite la selección de la fecha fin.|N/A|<p>Campo obligatorio.</p><p>Formato de fecha DD/MM/AAAA.</p>|
|Empleado SAT:|Lista de selección|N/A|S|Campo que permite seleccionar el Empleado SAT.|N/A|Estado inicial contraído.|
|Módulo\*:|Lista de selección|N/A|S|Campo que permite la selección del módulo. |N/A|N/A|
|Sección\*:|Lista de selección|N/A|S|Campo que permite la selección de la sección relacionada al módulo.|N/A|N/A|
|Tipo de movimiento:|Lista de selección|N/A|S|Campo que permite la selección del tipo de movimiento.|N/A|N/A|
|Limpiar|Botón|N/A|S|Opción que permite limpiar los criterios de búsqueda.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|Buscar|Botón|N/A|S|Opción que permite iniciar la búsqueda en la base de datos (BD) de la información de acuerdo con los criterios de búsqueda ingresados.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.  </p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref8]|Ícono|N/A|S|Opción que permite exportar la información de la tabla “Movimientos”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar *tooltip* que muestre el nombre de la opción “Exportar a Excel”.|
|Movimiento|Texto|N/A|L|Nombre del encabezado de la tabla.|N/A|N/A|
|![ref3]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Módulo|Texto|N/A|L|Campo que muestra el nombre del módulo.|N/A|N/A|
|Sección|Alfanumérico|N/A|L|Campo que muestra el nombre de la sección.|N/A|N/A|
|Fecha y hora|Fecha|10|L|Campo que muestra la fecha y hora del movimiento. |N/A|Formato de fecha DD/MM/AAAA|
|Empleado SAT|Alfanumérico|N/A|L|Campo que muestra el RFC del Empleado SAT.|N/A|N/A|
|RFC|Alfanumérico|N/A|L|Campo que muestra el RFC del Empleado SAT.|N/A|N/A|
|Tipo de movimiento|Texto|N/A|L|Campo que muestra el Tipo de movimiento.|N/A|N/A|
|Acciones|Texto|N/A|L|Nombre de la columna que muestra las acciones que se pueden realizar.|N/A|N/A|
|![](Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.012.png)|Ícono|N/A|S|Opción que visualizar el detalle de la información de las Pistas de auditoría.|N/A|Usar *tooltip* que muestre el nombre de la opción “Ver detalle”. |
|![ref4]|Ordenador|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![ref5]|Filtro|N/A|E|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la página que se visualiza.|
|![ref6]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera horizontal en la tabla. |N/A |N/A |
|![ref7]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera vertical en la tabla. |N/A |N/A |



## <a name="_toc171950061"></a>**ESTILOS 02**

|**Nombre de la Pantalla:** |<p>Detalle de movimiento</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT consultar el detalle del movimiento seleccionado.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ ECU\_ConsultarPistasAuditoria|


![Interfaz de usuario gráfica, Texto, Aplicación

Descripción generada automáticamente](Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.013.png)


### <a name="_toc171950062"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Detalle movimiento|Muestra el detalle del movimiento.|
|Detalle movimiento anterior|Muestra el detalle del movimiento anterior.|
|Última modificación|Muestra el detalle de última modificación.|
|Cerrar|Opción que permite cerrar la modal.|
|![ref9]|Opción que permite cerrar la modal.|


### <a name="_toc171950063"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Detalle movimiento|Alfanumérico|N/A|S|Muestra el detalle del movimiento.|N/A|Debe de mostrarse con sombreado en los contornos para identificar que se trata del Detalle movimiento.|
|Detalle movimiento anterior|Alfanumérico|N/A|L|Muestra el detalle del movimiento anterior.|N/A|N/A|
|Última modificación|Alfanumérico|N/A|L|Muestra el detalle de la última modificación.|N/A|N/A|
|Cerrar|Botón|N/A|S|Opción que permite cerrar la modal.|N/A|<p>Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B).</p><p>Cuando se le pone el cursor encima debe cambiar con fondo verde oscuro (#10312B) y letras blancas.</p>|
|![ref9]|Ícono|N/A|S|Opción que permite cerrar la modal.|N/A|Usar *tooltip* que muestre el nombre de la opción “Cerrar ventana”.|



## <a name="_toc171950064"></a>**ESTILOS 03**

|**Nombre de la Pantalla:** |<p>Procesando información</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Mostrar el avance porcentual del proceso de consulta realizado.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ ECU \_ConsultarPistasAuditoria|


![Interfaz de usuario gráfica

Descripción generada automáticamente](Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.015.png)



### <a name="_toc171950065"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![Forma

Descripción generada automáticamente con confianza baja]|Opción que muestra el avance porcentual del proceso ejecutado.|
|Procesando…|Mensaje informativo que indica que el proceso se está ejecutando.|


### <a name="_toc171950066"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![Forma

Descripción generada automáticamente con confianza baja]|Ícono|N/A|L|Opción que muestra el avance porcentual del proceso ejecutado.|N/A|N/A.|
|Procesando…|Texto|N/A|L|Mensaje informativo que indica que el proceso se está ejecutando.|N/A|N/A|




Anexo - Ejemplos de botones

![Interfaz de usuario gráfica, Diagrama

Descripción generada automáticamente](Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.017.png)

Las acciones de cada botón se definen en los estilos correspondientes.



|<a name="_hlk159937993"></a>**FIRMAS DE CONFORMIDAD**||
| :-: | :- |
|**Firma 1** |**Firma 2** |
|**Nombre**:  Diana Yazmín Pérez Sabido.|**Nombre**:    Rodolfo López Meneses.|
|**Puesto**: Usuaria ACPPI.|**Puesto**: Usuario ACPPI.|
|**Fecha:**|**Fecha:**|
|||
|**Firma 3** |**Firma 4**|
|**Nombre**:    Rubén Delgado Ramírez.|**Nombre**:  María del Carmen Castillejos Cárdenas.|
|**Puesto**: Usuario ACPPI.|**Puesto**:  APE ACPPI|
|**Fecha:**|**Fecha:**|
|||
|**Firma 5**|**Firma 6**|
|**Nombre**:  Alejandro Alfredo Muñoz Núñez.|**Nombre**: Erick Villa Beltrán.|
|**Puesto**:  RAPE ACPPI.|**Puesto**: Líder APE SDMA 6.|
|**Fecha**:|**Fecha**:|
|||
|**Firma 7**|**Firma 8**|
|**Nombre**: Juan Carlos Ayuso Bautista.|**Nombre**:  María del Carmen Gutiérrez Sánchez.|
|**Puesto**: Líder Técnico SDMA 6.|**Puesto**: Analista de Sistemas DS SDMA 6. |
|**Fecha**:|**Fecha**:|
|||



















|||Página 17 de 17|
| :- | :-: | -: |

[ref1]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.003.png
[ref2]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.004.png
[ref3]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.005.png
[ref4]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.007.png
[ref5]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.008.png
[ref6]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.009.png
[ref7]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.010.png
[ref8]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.011.png
[ref9]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.014.png
[Forma

Descripción generada automáticamente con confianza baja]: Aspose.Words.b89c60b8-54e6-4701-b9cc-2a179aad2058.016.png
