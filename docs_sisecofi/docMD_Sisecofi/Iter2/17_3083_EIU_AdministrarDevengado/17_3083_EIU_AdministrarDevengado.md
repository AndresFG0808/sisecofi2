|![](Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Usuario**</p><p>17\_3083\_EIU\_AdministrarDevengado.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |

**<ID Requerimiento>** 8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación
## <a name="_toc169813509"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :-: | :-: |
|*1*|*Creación del documento*|Osiris Vianey Segovia Pasarán|*14/02/2024*|
|*1.1*|*Se actualiza documento*|Isabel Adriana Valdez Cortés|*06/03/2024*|
|*1.2*|*Revisión del documento*|Luis Angel Olguin Castillo|*16/04/2024*|
|*1.3*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*10/06/2024*|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc169813509)

[Módulo: CONSUMO DE SERVICIOS	2](#_toc169813510)

[ESTILOS 01	2](#_toc169813511)

[Descripción de Elementos	3](#_toc169813512)

[Descripción de Campos	4](#_toc169813513)

[ESTILOS 02	13](#_toc169813514)

[Descripción de Elementos	14](#_toc169813515)

[Descripción de Campos	15](#_toc169813516)


## **<a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a><a name="_toc169813510"></a>MÓDULO: CONSUMO DE SERVICIOS**
## <a name="_toc169813511"></a>**ESTILOS 01**

|**Nombre de la Pantalla:** |<p>Consumo de Servicios – Búsqueda estimaciones</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT realizar la consulta de acuerdo con los criterios solicitados para visualizar la información de las “Estimaciones”, además de permitir visualizar las opciones (Alta de estimación, Alta dictamen, Exportar, Editar estimación y Ver estimación) dependiendo de rol y los permisos que tenga asignado.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AdministrarDevengado|


![](Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.002.png)


### ` `**<a name="_toc169813512"></a>DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema.|
|Consumo de Servicios|Título del encabezado que contiene el nombre del módulo “Consumo de Servicios”.|
|Búsqueda|Sección donde se muestran los criterios de búsqueda.|
|Contrato vigente\*:|Campo que tiene las siguientes opciones: “Sí”, “No”, “Todos”. |
|Contratos\*:|Muestra un listado con el nombre corto de cada contrato vigente o no vigente.|
|Proveedores\*:|Campo que muestra el nombre de los proveedores que tiene asignado el contrato.|
|Tipo de consumo\*:|<p>Campo que muestra las siguientes opciones: </p><p>- “Estimado”</p><p>- “Dictaminado”</p>|
|Estatus:|Campo que permite seleccionar los estatus correspondientes a las estimaciones o dictámenes.|
|Buscar|Opción que permite iniciar la búsqueda en la base de datos (BD) de la información de acuerdo con los criterios de búsqueda ingresados.|
|![ref2]|Opción que permite dar de alta una nueva estimación.|
|![ref3]|Opción que permite dar de alta un nuevo dictamen.|
|![ref4]|Opción que permite exportar la información de la tabla “Estimaciones”, generando un archivo de Excel con extensión (.xlsx).|
|Estimaciones|Encabezado de la tabla que contiene la información de la o las estimaciones.|
|![ref5]|Paginador que permite navegar a través de las páginas resultantes de la consulta.|
|Id estimación|Identificador único generado para una estimación.|
|Periodo de control|Campo que muestra el mes y año definido para la estimación que se consultó.|
|Periodo inicial|Campo que muestra el periodo inicial de cada estimación.|
|Periodo final|Campo que muestra el periodo final de cada estimación.|
|Proveedor|Campo que muestra el nombre del proveedor.|
|Estatus|Campo que muestra el estatus de la estimación.|
|Monto estimado|Campo que muestra el monto estimado que se capturó por cada estimación.|
|Monto estimado en pesos|Campo que muestra el monto estimado en pesos que se capturó por cada estimación.|
|Monto dictaminado|Campo que muestra el monto dictaminado que se obtiene del dictamen.|
|<p>Monto</p><p>dictaminado en pesos</p>|Campo que muestra el monto dictaminado en pesos que se obtiene del dictamen.|
|Acciones|Nombre de la columna que muestra las acciones que se pueden realizar.|
|![ref6]|Opción que permite editar la estimación donde es seleccionado.|
|![ref7]|Permite visualizar la información de la estimación.|
|<p></p><p>![ref8]</p>|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|<p>![ref9]</p><p></p>|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|![ref10]|Permite desplazarse de manera horizontal en la tabla. |
|![ref11]|Permite desplazarse de manera vertical en la tabla. |

### <a name="_toc169813513"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema.|N/A|N/A|
|Consumo de Servicios|Texto|N/A|L|Título del encabezado que contiene el nombre del módulo “Consumo de Servicios”.|N/A|Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|Búsqueda|Sección|N/A|L|Sección donde se muestran los criterios de búsqueda.|N/A|N/A|
|Contrato vigente\*:|Lista de selección|N/A|S|Campo que tiene las siguientes opciones: “Sí”, “No”, “Todos”.|N/A|N/A|
|Contratos\*:|Lista de selección|N/A|S|Muestra un listado con el nombre corto de cada contrato vigente o no vigente.|N/A|Se muestran las opciones resultantes dependiendo de la opción seleccionada en “Contrato vigente”.|
|Proveedores\*:|Lista de selección|N/A|S|<p>Campo que muestra el nombre de los proveedores que tiene asignado el contrato.</p><p></p>|N/A|Si se identifica un solo proveedor, debe mostrarlo por defecto.|
|Tipo de consumo\*:|Lista de selección|N/A|S|<p>Campo que muestra las siguientes opciones: “Estimado” y</p><p>“Dictaminado”</p>|N/A|N/A|
|Estatus:|Lista de selección|N/A|S|Campo que permite seleccionar los estatus correspondientes a las estimaciones o dictámenes.|N/A|<p>Muestra el listado de estatus dependiendo, si es “Estimado” (“Inicial”, “Estimado” o “Cancelado”) o “Dictaminado” (“Inicial”,</p><p>“Dictaminado”,</p><p>“Proforma”,</p><p>“Facturado”,</p><p>“Pagado” y</p><p>“Cancelado”).</p>|
|Buscar|Botón|N/A|S|Permite iniciar la búsqueda en la BD de la información de acuerdo con los criterios de búsqueda ingresados.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref2]|Ícono|N/A|S|Opción que permite dar de alta una nueva estimación.|N/A|Usar *tooltip* que muestre el nombre de la opción “Alta de estimación”. Nota: Este ícono deberá mostrarse solo al Empleado SAT que tenga el rol para agregar una nueva estimación en el módulo “Consumo de Servicios”.|
|![ref3]|Ícono|N/A|S|Opción que permite dar de alta un nuevo dictamen.|N/A|Usar *tooltip* que muestre el nombre de la opción “Alta de dictamen”. Nota: Este ícono deberá mostrarse solo al Empleado SAT que tenga el rol para agregar una nueva estimación en el módulo “Consumo de Servicios”.|
|![ref12]|Ícono|N/A|S|Opción que permite exportar la información de la tabla “Estimaciones”, generando un archivo de Excel con extensión (.xlsx).|N/A|<p>Usar *tooltip* que muestre el nombre de la opción “Exportar”.</p><p>Se muestra al presentar la tabla del resultado de la búsqueda, en caso de que no existan resultados en la búsqueda, esta opción se muestra inhabilitada.</p>|
|Estimaciones|Texto|N/A|L|Encabezado de la tabla que contiene la información de la o las estimaciones.|N/A|N/A|
|![ref5]|Paginador|N/A|S|Paginador que permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Id estimación|Alfanumérico|60|L|Identificador único generado para un estimación|N/A|<p>Se forma de la siguiente manera:</p><p>NombreCortoContrato|IDProveedor|consecutivoEstimación-E</p><p></p><p>Ejemplo:</p><p>STLD 4|00001|00001-E</p><p></p>|
|Periodo de control|Alfanumérico|20|L|Campo que muestra el mes y año definido para la estimación que se consultó.|N/A|<p>Ejemplo:</p><p>Enero 2024</p>|
|Periodo inicial|Fecha|10|L|Campo que muestra el periodo inicial de cada estimación.|N/A|<p>Formato</p><p>DD/MM/AAAA </p>|
|Periodo final|Fecha|10|L|Campo que muestra el periodo final de cada estimación.|N/A|<p>Formato</p><p>DD/MM/AAAA</p>|
|Proveedor|Alfanumérico|150|L|Campo que muestra el nombre del proveedor.|N/A|<p>Ejemplo: </p><p>SYE SOFTWARE SA DE CV</p>|
|Estatus|Alfabético|N/A|L|Muestra el estatus de la estimación.|N/A|Ejemplos: “Inicial”, “Estimado” o “Cancelado”.|
|Monto estimado|Numérico (Decimal)|20|L|Campo que muestra el monto estimado que se capturó por cada estimación.|N/A|Se consideran números decimales con formato $0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00|
|Monto estimado en pesos|Numérico (Decimal)|20|L|Campo que muestra el monto estimado en pesos que se capturó por cada estimación.|N/A|Se consideran números decimales con formato $0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00|
|Monto dictaminado|Numérico (Decimal)|20|L|Muestra por monto dictaminado que se obtiene del dictamen.|N/A|Se consideran números decimales con formato $0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00|
|Monto dictaminado en pesos|Numérico (Decimal)|20|L|Muestra el monto dictaminado en pesos que se obtiene del dictamen.|N/A|Se consideran números decimales con formato $0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00|
|Acciones|Texto|N/A|L|Nombre de la columna que muestra las acciones que se pueden realizar.|N/A|N/A|
|![ref13]|Ícono|N/A|S|Opción que permite editar la estimación donde es seleccionado.|N/A|Usar *tooltip* que muestre el nombre de la opción “Editar estimación”. Nota: Este ícono deberá mostrarse solo al Empleado SAT que tenga el rol para editar una estimación en el módulo “Consumo de Servicios”.|
|![ref14]|Ícono|N/A|S|Permite visualizar la información de la estimación.|N/A|Usar *tooltip* que muestre el nombre de la opción “Ver estimación”. Nota: Este ícono deberá mostrarse solo al Empleado SAT que tenga el rol de solo consulta en el módulo “Consumo de Servicios”.|
|![ref8]|Ordenador|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![ref9]|Filtro|N/A|E|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la página que se visualiza.|
|![ref10]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera horizontal en la tabla. |N/A |N/A |
|![ref11]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera vertical en la tabla. |N/A |N/A |

\




## <a name="_toc169813514"></a>**ESTILOS 02**

|**Nombre de la Pantalla:** |<p>Consumo de Servicio – Búsqueda dictámenes</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT realizar la consulta de acuerdo con los criterios solicitados para visualizar la información de los “Dictámenes”, además de permitir visualizar las opciones (Alta de estimación, Alta dictamen, Exportar, Editar dictamen y Ver dictamen) dependiendo de rol y los permisos que tenga asignado.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AdministrarDevengado|


![Interfaz de usuario gráfica

Descripción generada automáticamente](Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.017.png)









### <a name="_toc169813515"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema.|
|Consumo de Servicios|Título del encabezado que contiene el nombre del módulo “Consumo de Servicios”.|
|Búsqueda|Sección donde se muestran los criterios de búsqueda.|
|Contrato vigente\*:|Campo que tiene las siguientes opciones: “Sí”, “No”, “Todos”. |
|Contratos\*:|Muestra un listado con el nombre corto de cada contrato vigente o no vigente de los proyectos asignados al Empleado SAT. Se muestran las opciones que coincidan con la opción seleccionada en “Contrato vigente”.|
|Proveedores\*:|<p>Campo que muestra el nombre de los proveedores que tiene asignados el contrato que se seleccionó previamente.</p><p>En caso de tener solo 1 proveedor se muestra seleccionado automáticamente.</p>|
|Tipo de consumo\*:|<p>Campo que muestra las siguientes opciones: </p><p>- “Estimado”</p><p>- “Dictaminado”</p>|
|Estatus:|Campo que permite seleccionar los estatus correspondientes a las estimaciones o dictámenes, dependiendo de si se consulta por “Estimado” o “Dictaminado”.|
|Buscar|Opción que permite iniciar la búsqueda en la BD de la información de acuerdo con los criterios de búsqueda ingresados.|
|![ref2]|Opción que permite dar de alta una nueva estimación; deberá estar habilitado en todo momento. |
|![ref3]|Opción que permite dar de alta un nuevo dictamen; deberá estar habilitado en todo momento.|
|![Un dibujo con letras

Descripción generada automáticamente con confianza media]|Opción que permite descargar información de la tabla “Dictámenes”, generando un archivo de Excel con extensión (.xlsx).|
|Dictámenes|Encabezado de la tabla que contiene la información del o los dictámenes coincidentes con los criterios de búsqueda.|
|![ref5]|Paginador que permite navegar a través de las páginas resultantes de la consulta.|
|Id dictamen|Identificador único generado para un dictamen.|
|Monto|Campo que muestra el “Monto dictaminado total en pesos” calculado en el dictamen. |
|Estatus|Indica el estatus del dictamen.|
|Comprobante fiscal|Campo que muestra el número del o los folios de la factura, separados por comas. |
|Pendientes de pago|Campo que muestra el número de facturas que faltan por pagar.|
|Acciones|Nombre de la columna que muestra las acciones que se pueden realizar.|
|![ref6]|Opción que permite editar el dictamen donde es seleccionado.|
|![ref15]|Opción que permite visualizar la información de la estimación.|
|<p></p><p>![ref8]</p>|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|<p>![ref9]</p><p></p>|Campo para filtrar información de la columna en la que se requiere buscar específicamente.|
|![ref10]|Permite desplazarse de manera horizontal en la tabla. |
|![ref11]|Permite desplazarse de manera vertical en la tabla. |


### <a name="_toc169813516"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos de este sistema.|N/A|N/A|
|Consumo de Servicios|Texto|N/A|L|Título del encabezado que contiene el nombre del módulo “Consumo de Servicios”.|N/A|Para el encabezado puede hacerse uso de mayúsculas y minúsculas.|
|Búsqueda|Sección|N/A|L|Sección donde se muestran los criterios de búsqueda.|N/A|N/A|
|Contrato vigente\*:|Lista de selección|N/A|S|Campo que tiene las siguientes opciones: “Sí”, “No”, “Todos”.|N/A|N/A|
|Contratos\*:|Lista de selección|N/A|S|Muestra un listado con el número de cada contrato vigente o no vigente.|N/A|Se muestran las opciones resultantes de la opción seleccionada en “Contrato vigente”.|
|Proveedores\*:|Lista de selección|N/A|S|<p>Campo que muestra el nombre de los proveedores que tiene asignado el contrato que se seleccionó previamente.</p><p>En caso de tener solo 1 proveedor se muestra seleccionado automáticamente.</p>|N/A|<p>Campo obligatorio solo cuando en ”Tipo de consumo”  se selecciona “Dictaminado”,</p><p>en el caso de ser “Estimado” es opcional.</p>|
|Tipo de consumo\*:|Lista de selección|N/A|S|<p>Muestra las siguientes opciones:</p><p>“Estimado”</p><p>“Dictaminado”</p>|N/A|N/A|
|Estatus:|Lista de selección|N/A|S|Muestra las opciones dependiendo si se consulta por “Estimado” o “Dictaminado”.|N/A|<p>Muestra el listado de estatus dependiendo, si es “Estimado” (“Inicial”, “Estimado” o “Cancelado”) o “Dictaminado” (“Inicial”,</p><p>“Dictaminado”,</p><p>“Proforma”,</p><p>“Facturado”,</p><p>“Pagado” y</p><p>“Cancelado”).</p>|
|Buscar|Botón|N/A|S|Permite iniciar la búsqueda en la BD de la información de acuerdo con los criterios de búsqueda ingresados.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno y letras en color gris.</p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.</p>|
|![ref2]|Ícono|N/A|S|Permite dar de alta una nueva estimación.|N/A|<p>Usar *tooltip* que muestre el nombre de la opción “Alta de estimación”. Nota: Este ícono deberá mostrarse solo al Empleado SAT que tenga el rol para agregar una nueva estimación en el módulo “Consumo de Servicios”.</p><p>Se muestra al presentar la tabla del resultado de la búsqueda.</p>|
|![ref3]|Ícono|N/A|S|Permite dar de alta un nuevo dictamen.|N/A|<p>Usar *tooltip* que muestre el nombre de la opción “Alta de dictamen”. Nota: Este ícono deberá mostrarse solo al Empleado SAT que tenga el rol para agregar una nueva estimación en el módulo “Consumo de Servicios”. </p><p>Se muestra al presentar la tabla del resultado de la búsqueda.</p>|
|![ref16]|Ícono|N/A|S|Permite exportar la información de la tabla de “Dictámenes”, generando un archivo de Excel con extensión (.xlsx).|N/A|<p>Usar *tooltip* que muestre el nombre de la opción “Exportar dictámenes”.</p><p>Se muestra al presentar la tabla del resultado de la búsqueda, en caso de que no existan resultados en la búsqueda, esta opción no se muestra.</p>|
|Dictámenes|Texto|N/A|L|Encabezado de la tabla que contiene la información del o los dictámenes coincidentes con los criterios de búsqueda.|N/A|N/A|
|![ref5]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta.|N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|Id dictamen|Alfanumérico|60|L|Identificador único generado para un dictamen.|N/A|<p>Se forma de la siguiente manera:</p><p>NombreCortoContrato|IdProveedor|consecutivoDictamen</p><p></p><p>Ejemplo:</p><p>STLD 4|00001|00001</p>|
|Monto|Numérico (Decimal)|20|L|Campo que muestra el “Monto dictaminado total en pesos” calculado en el dictamen. |N/A|Se consideran números decimales con formato $0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00|
|Estatus|Texto|N/A|L|Indica el estatus del dictamen.|N/A|<p>Debe tener alguno de los siguientes estatus: “Inicial”,</p><p>“Dictaminado”,</p><p>“Proforma”,</p><p>“Facturado”,</p><p>“Pagado” y</p><p>“Cancelado”.</p>|
|Comprobante fiscal|Alfanumérico|18|L|Campo que muestra el número del o los folios de la factura, separados por comas. |N/A|<p>Usar *tooltip* que muestre</p><p>“Número del o los folios”.</p>|
|Pendientes de pago|Numérico|5|L|Campo que muestra el número de facturas que faltan por pagar.|N/A|N/A|
|Acciones|Texto|N/A|L|Nombre de la columna que muestra las acciones que se pueden realizar.|N/A|N/A|
|![ref13]|Ícono|N/A|S|Opción que permite editar el dictamen donde es seleccionado.|N/A|Usar *tooltip* que muestre el nombre de la opción “Editar dictamen”. Nota: Este ícono deberá mostrarse solo al Empleado SAT que tenga el rol para editar un dictamen en el módulo “Consumo de Servicios”.|
|![ref17]|ícono|N/A|S|Opción que permite visualizar la información de la estimación.|N/A|Usar *tooltip* que muestre el nombre de la opción “Ver dictamen”. Nota: Este ícono deberá mostrarse solo al Empleado SAT que tenga el rol para consultar el dictamen en el módulo “Consumo de Servicios”.|
|![ref8]|Ordenador|N/A|S|Permite acomodar la información de la tabla de forma alfabética, ascendente o descendente, considerando la columna en la que es seleccionado, según aplique.|N/A|Se permite realizar un filtro y mostrar los datos en orden alfabético.|
|![ref9]|Filtro|N/A|E|Permite filtrar información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la página que se visualiza.|
|![ref10]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera horizontal en la tabla. |N/A |N/A |
|![ref11]|Barra de desplazamiento |N/A |S |Permite desplazarse de manera vertical en la tabla. |N/A |N/A |














Anexo - Ejemplos de botones

![Interfaz de usuario gráfica, Diagrama

Descripción generada automáticamente](Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.022.png)

Las acciones de cada botón se definen en los estilos correspondientes.
**\


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








|||Página 17 de 17|
| :- | :-: | -: |

[ref1]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.003.png
[ref2]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.004.png
[ref3]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.005.png
[ref4]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.006.png
[ref5]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.007.png
[ref6]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.008.png
[ref7]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.009.png
[ref8]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.010.png
[ref9]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.011.png
[ref10]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.012.png
[ref11]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.013.png
[ref12]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.014.png
[ref13]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.015.png
[ref14]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.016.png
[Un dibujo con letras

Descripción generada automáticamente con confianza media]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.018.png
[ref15]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.019.png
[ref16]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.020.png
[ref17]: Aspose.Words.ea3e56e4-5cd5-4270-b9a5-be183e337da5.021.png
