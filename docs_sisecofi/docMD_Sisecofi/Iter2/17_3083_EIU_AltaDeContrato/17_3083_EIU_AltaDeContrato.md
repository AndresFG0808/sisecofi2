|![](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Empleado SAT**</p><p>17\_3083\_EIU\_AltaDeContrato.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |

**Requerimiento>** 8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación.
## <a name="_toc167209960"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :-: | :-: |
|*1*|*Creación del documento*|Angel Horacio López Alcaraz|*14/02/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*23/04/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*21/05/2024*|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc167209960)

[Módulo: Alta de contrato	2](#_toc167209961)

[ESTILOS 01	2](#_toc167209962)

[Descripción de Elementos	3](#_toc167209963)

[Descripción de Campos	4](#_toc167209964)

[ESTILOS 02	11](#_toc167209965)

[Descripción de Elementos	12](#_toc167209966)

[Descripción de Campos	13](#_toc167209967)


##












## **<a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a> <a name="_toc167209961"></a>MÓDULO: ALTA DE CONTRATO**
## <a name="_toc459285561"></a> **<a name="_toc167209962"></a>ESTILOS 01**
##
|**Nombre de la Pantalla:** |<p>Contratos</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT consultar los contratos cargados, generar un nuevo contrato e iniciar el proceso de edición de la información de los contratos previamente capturados.</p><p></p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeContrato|
|||
<a name="_toc236129840"></a><a name="_toc236196645"></a> ![Interfaz de usuario gráfica

Descripción generada automáticamente](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.002.png)  

**
### <a name="_toc236129841"></a><a name="_toc236196646"></a><a name="_toc236558259"></a><a name="_toc267478971"></a><a name="_toc167209963"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|![ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos del sistema.|
|Contratos|Título que identifica la pantalla.|
|Buscar contrato|Sección que permite realizar la búsqueda respecto a los contratos mostrados.|
|Estatus del contrato:|Campo que permite seleccionar el “Estatus” de cada contrato. Cuenta con los valores “Inicial”, “Ejecución”, “Cerrado” y “Cancelado”.|
|Vigencia|Nombre del campo.|
|De:|Campo que permite seleccionar desde el calendario, la fecha de inicio del periodo a buscar.|
|Al:|Campo que permite seleccionar desde el calendario, la fecha de fin del periodo a buscar.|
|Proveedor:|Campo que permite seleccionar a un proveedor del listado que se obtiene del catálogo de proveedores.|
|Administración central:|Campo que permite seleccionar la administración central patrocinadora del catálogo “Administraciones”.|
|Buscar|Opción que, permite iniciar la búsqueda en la base de datos (BD) de los contratos que coincidan con los criterios de búsqueda ingresados y cuyos proyectos estén asociados con el Empleado SAT.|
|![](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.004.png)|Opción que permite crear un nuevo contrato. |
|![ref2]|Opción que permite exportar la información de la BD “Contratos”, generando un archivo de Excel con extensión (.xlsx).|
|Contratos |Título de la tabla.|
|Id|Campo que incremental mostrará el identificador del contrato.|
|Nombre del contrato|Campo que muestra el nombre del contrato. |
|Nombre del proyecto|Campo que muestra el proyecto asociado al contrato. |
|Número de contrato|Campo que muestra el número del contrato.|
|Proveedor|Campo que muestra el o los proveedores asociados a un contrato.|
|Tipo de procedimiento|Campo que muestra el tipo de procedimiento seleccionado previamente en un contrato.|
|Inicio|Campo que muestra la fecha de inicio del contrato.|
|Término |Campo que muestra la fecha de término del contrato.|
|Último CM|Campo que muestra la fecha de término del último convenio modificatorio asociado al contrato. |
|Monto máximo|Campo que muestra el monto máximo del contrato en su moneda origen.|
|Monto máximo último CM|Campo que muestra el monto máximo del último convenio modificatorio en su moneda origen. |
|Monto en pesos|Campo que muestra el “Monto en pesos”, si existe un dato en “Monto máximo último CM” se mostrará el “Monto en pesos” del último convenio modificatorio; en caso contrario será el “Monto máximo en pesos” del contrato.|
|Administración central|Campo que muestra la administración central asociada al contrato.|
|Administrador del contrato|Campo que muestra el administrador del contrato.|
|Acciones|Campo donde se muestran las acciones a realizar con el contrato.|
|![ref3]|Opción que permite editar un contrato registrado. |
|![ref4]|Opción que permite ver el detalle de la información de un contrato.|
|![ref5]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|![ref6]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique. |
|![ref7]|Campo que permite filtrar la información de la columna en la que se requiere buscar específicamente.|
|<p>! </p><p>![ref9]</p>|Permite desplazarse de manera vertical u horizontal en la tabla. |
###
### <a name="_toc236129842"></a><a name="_toc236196647"></a><a name="_toc236558260"></a><a name="_toc167209964"></a>**DESCRIPCIÓN DE CAMPOS**

|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![ref1]|Ícono|N/A|S|Muestra el menú principal desplegado en la parte izquierda de la pantalla.|N/A|N/A|
|Contratos|Texto|N/A|L|Título que identifica la pantalla.|N/A|N/A|
|Buscar contrato|Texto|N/A|L|Título que identifica la búsqueda de contratos.|N/A|N/A|
|Estatus del contrato:|Lista de selección|N/A|S|Campo que muestra el “Estatus” de cada contrato. Cuenta con los valores “Inicial”, “Ejecución”, “Cerrado” y “Cancelado”.|N/A|N/A|
|Vigencia|Texto|N/A|L|Nombre del campo.|N/A|N/A|
|De:|Fecha|10|E, S|Campo que permite seleccionar desde el calendario, la fecha de inicio del periodo a buscar.|N/A|Formato de fecha DD/MM/AAAA|
|Al:|Fecha|10|E, S|Campo que permite seleccionar desde el calendario, la fecha de fin del periodo a buscar.|N/A|Formato de fecha DD/MM/AAAA|
|Proveedor:|Lista de selección|N/A|S|Campo que permite seleccionar a un proveedor del listado que se obtiene del catálogo de proveedores.|N/A|N/A|
|Administración central:|Lista de selección|N/A|S|Campo que permite seleccionar la administración central patrocinadora del catálogo “Administraciones”.|N/A|N/A|
|Buscar|Botón|N/A|S|Opción que, permite iniciar la búsqueda en la BD de los contratos que coincidan con los criterios ingresados y cuyos proyectos estén asociados con el Empleado SAT que realiza la búsqueda.|N/A|Inicialmente se muestra sin color de fondo y con contorno en color gris. Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras.|
|![](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.013.png)|Ícono|N/A|S|Opción que permite crear un nuevo contrato. |N/A|<p>Usar el *tooltip* “Nuevo contrato”.</p><p>Solo podrá ser visible para el rol que tenga permisos de insertar.</p>|
|![ref2]|Ícono|N/A|S|Opción que permite descargar un archivo de Excel con extensión (.xlsx) de la información almacenada en la BD de la sección consultada|N/A|Usar el *tooltip* “Exportar a Excel”|
|Contratos |Texto|N/A|L|Título de la tabla.|N/A|N/A|
|Id|Numérico|5|L|Campo incremental que mostrará el identificador del contrato.|N/A|N/A|
|Nombre del contrato|Texto|N/A|L|Campo que muestra el nombre corto del contrato. |N/A|Usar el *tooltip* “Nombre corto del contrato”|
|Nombre del proyecto|Texto|N/A|L|Campo que muestra el proyecto asociado del contrato.|N/A|Usar el *tooltip* “Nombre corto del proyecto asociado”.|
|Número de contrato|Alfanumérico|N/A|L|Campo que muestra el número del contrato.|N/A|N/A|
|Proveedor|Texto|N/A|L|Campo que muestra el o los proveedores asociados a un contrato.|N/A|En caso de que sean 2 o más proveedores, concatenará el nombre separado por; (punto y coma).|
|Tipo de procedimiento|Texto|N/A|L|Campo que muestra el tipo de procedimiento seleccionado previamente en un contrato.|N/A|N/A|
|Inicio|Fecha|10|L|Campo que muestra la fecha de inicio del contrato.|N/A|Formato de fecha DD/MM/AAAA|
|Término |Fecha|10|L|Campo que muestra la fecha de término del contrato.|N/A|Formato de fecha DD/MM/AAAA|
|Último CM|Fecha|10|L|Campo que muestra la fecha de término del último convenio modificatorio asociado al contrato. |N/A|Usar el *tooltip* “Fecha de Último Convenio Modificatorio”.|
|Monto máximo|Numérico (decimal)|20|L|Campo que muestra el monto máximo del contrato en su moneda origen.|N/A|Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00. |
|Monto máximo último CM|Numérico (decimal)|20|L|Campo que muestra el monto máximo del último convenio modificatorio en su moneda origen. |N/A|<p>Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.</p><p>Usar el *tooltip* “Monto máximo último convenio modificatorio”.</p>|
|Monto en pesos|Numérico (decimal)|20|L|<p>Campo que muestra el “Monto en pesos”, si existe un dato en “Monto máximo último CM” se mostrará el “Monto en pesos” del último convenio modificatorio, en caso contrario será el “Monto máximo en pesos” del contrato.</p><p></p>|N/A|Se consideran números decimales con formato $ 0.00, hasta 2 decimales. Ejemplo: $999,999,999,999.00.|
|Administración central|Texto|N/A|L|Campo que muestra la administración central asociada al contrato.|N/A|En caso de que sean 2 o más Administraciones centrales patrocinadoras, concatenará el nombre separado por; (punto y coma).|
|Administrador del contrato|Texto|N/A|L|Campo que muestra el administrador del contrato.|N/A|N/A|
|Acciones|Texto|N/A|L|Campo donde se muestran las acciones a realizar con el contrato.|N/A|N/A|
|![ref3]|Ícono|N/A|S|Opción que permite editar un contrato registrado. |N/A|<p>Usar el *tooltip* “Editar”.</p><p>Solo podrá ser visible para el rol que tenga permisos de modificar.</p>|
|![ref4]|Ícono|N/A|S|Opción que permite ver el detalle de la información de un contrato.|N/A|Usar el *tooltip* “Ver detalle”.|
|![ref5]|Paginador |N/A |S |Permite navegar a través de las páginas resultantes de la consulta. |N/A |Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|![ref6]|Ícono |N/A |S |Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique. |N/A |N/A |
|![ref7]|Filtro |N/A |E |Permite filtrar información de la columna donde se requiera buscar específicamente. |N/A |Realiza el filtro de la información solo dentro de la página que se visualiza. |
|<p>! </p><p>![ref9]</p><p></p>|Barra de desplazamiento |N/A |S |Permite desplazarse de manera vertical u horizontal en la tabla. |N/A |N/A |
## <a name="_toc158381725"></a><a name="_toc167209965"></a>**ESTILOS 02**

|**Nombre de la Pantalla:** |<p>Alta de contrato sección Identificación</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permitir al Empleado SAT crear la sección “Identificación” para un nuevo contrato.</p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_AltaDeContrato|
|||
![Imagen que contiene Interfaz de usuario gráfica

Descripción generada automáticamente](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.014.png)


### <a name="_toc158381726"></a><a name="_toc161137293"></a><a name="_toc167209966"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|<a name="_hlk157361071"></a>**Elemento**|**Descripción**|
| :- | :- |
|![Icono

Descripción generada automáticamente][ref1]|Opción que al seleccionarla muestra el menú principal desplegado en la parte izquierda de la pantalla; contiene los módulos principales y submódulos del sistema.|
|Contratos|Título que identifica la pantalla.|
|Identificación |Nombre de la sección.|
|Id|Campo incremental que mostrará el identificador del contrato.|
|Proyecto asociado\*:|Campo de selección que permite escoger el “Proyecto asociado” al contrato.|
|Estatus:|Campo que muestra los estatus del contrato. Estos pueden ser “Inicial”, “Ejecución”, “Cerrado” y “Cancelado”.|
|![ref11]|Opción que permite cambiar el estatus del contrato a “Cancelado”. |
|Nombre del contrato\*:|Campo de captura que permite ingresar el nombre del contrato.|
|Nombre corto del contrato\*:|Campo de captura que permite ingresar el nombre corto del contrato.|
|Última modificación:|Campo que muestra el nombre (Empleado SAT), fecha y hora de la última modificación.|
|Inicial|Opción que permite cambiar el estatus del contrato a “Inicial”.|
|Ejecución|Opción que permite cambiar el estatus del contrato a “Ejecución”.|
|Cancelar|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|
|Guardar|Opción que inicia el proceso para almacenar en la BD la información de la sección “Identificación” del contrato.|
|![](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.016.png "BB37EE4F") |<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente: </p><p>Sección contraída ![](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.017.png "EBF21C35") </p><p>Sección desplegada![](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.016.png "B3A9110B") </p>|
|Datos generales|Sección colapsada “Datos generales” del contrato.|
|Vigencia y montos|Sección colapsada “Datos de vigencia y montos” del contrato.|
|Grupos de servicio y/o conceptos |Sección colapsada “Grupos de servicio o conceptos” del contrato.|
|Registro de servicios|Sección colapsada “Registro de servicios” del contrato.|
|Proyección de caso de negocio|Sección colapsada “Proyección de caso de negocio” del contrato.|
|Cargar layout de los informes|Sección colapsada “Cargar *layout* de los informes” del contrato.|
|Atraso en el inicio de la prestación|Sección colapsada “Atraso en el inicio de la prestación” del contrato.|
|Informes documentales por única vez|Sección colapsada “Informes documentales por única vez” del contrato.|
|Informes documentales periódicos|Sección colapsada “Informes documentales periódicos” del contrato.|
|Informes documentales de los servicios |Sección colapsada “Informes documentales de los servicios” del contrato.|
|Penas contractuales|Sección colapsada “Penas contractuales” del contrato.|
|Niveles de servicio (SLA)|Sección colapsada “Niveles de servicio (SLA)” del contrato.|
|Asignación de plantilla|Sección colapsada “Asignación de plantilla” del contrato.|
|Gestión documental|Sección colapsada “Gestión documental” del contrato.|
|Convenios modificatorios |Sección colapsada “Convenios modificatorios” asociados a los contratos.|
|Dictámenes asociados|Sección colapsada que muestra los “Dictámenes asociados” al contrato.|
|Facturas asociadas|Sección colapsada que muestra las “Facturas asociadas” al contrato.|
|Reintegros asociados|Sección colapsada que muestra los “Reintegros asociados” al contrato.|
|Cierre|Sección colapsada que permite visualizar el Acta de cierre.|
|Regresar|Opción que realiza el proceso para regresar a la pantalla de búsqueda de “Contratos”.|


### <a name="_toc158381727"></a><a name="_toc161137294"></a><a name="_toc167209967"></a><a name="_hlk164794879"></a>**DESCRIPCIÓN DE CAMPOS**
###
|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|![Icono

Descripción generada automáticamente][ref1]|Ícono |N/A |S |Muestra el menú principal desplegado en la parte izquierda de la pantalla. |N/A |N/A |
|Contratos|Texto|N/A|L|Título que identifica la pantalla.|N/A|N/A|
|Identificación|Sección|N/A|L|Nombre de la sección.|N/A|N/A|
|Id:|Numérico (entero)|5|L|Campo incremental que mostrará el identificador del contrato.|N/A|Campo obligatorio automático. Ejemplo: 00001|
|Proyecto asociado\*:|Lista de selección|N/A|L, S|Campo de selección que permite escoger el “Proyecto asociado” al contrato.|N/A|Campo obligatorio.|
|Estatus:|Texto|N/A|L|<p>Campo que muestra los estatus del contrato.</p><p>Estos pueden ser “Inicial”, “Ejecución”, “Cerrado” y “Cancelado”.</p>|N/A|N/A|
|![ref11]|Ícono|N/A|S|Opción que permite cambiar el estatus del contrato a “Cancelado”. |N/A|Usar el *tooltip* “Cancelar contrato”.|
|Nombre del contrato\*:|Texto|250|L, E|Campo de captura que permite ingresar el nombre del contrato.|N/A|Campo obligatorio.|
|Nombre corto del contrato\*:|Texto|50|L, E|Campo de captura que permite ingresar el nombre corto del contrato.|N/A|Campo obligatorio.|
|Última modificación|Texto|100|L|Campo que muestra el nombre (Empleado SAT), fecha y hora de la última modificación.|N/A|N/A|
|Inicial|Botón|N/A|L, S|Opción que permite cambiar el estatus del contrato a “Inicial”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno en color gris.</p><p>Cuando se le coloca el cursor encima debe cambiar a fondo gris.</p><p>No se mostrará, hasta que sea creado un nuevo contrato.</p>|
|Ejecución|Botón|N/A|L, S|Opción que permite cambiar el estatus del contrato a “Ejecución”.|N/A|<p>Inicialmente se muestra sin color de fondo y con contorno en color gris.</p><p>Cuando se le coloca el cursor encima debe cambiar a fondo gris.</p><p>No se mostrará, hasta que sea creado un nuevo contrato. </p>|
|Cancelar|Botón|N/A|S|Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado.|N/A|Inicialmente, se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32). Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.|
|Guardar|Botón|N/A|S|Opción que inicia el proceso para almacenar en la BD la información de la sección “Identificación” del contrato.|N/A|Inicialmente se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B). Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas.|
|![ref12]|Ícono|N/A|S|Opción que despliega o contrae la sección.|N/A|<p>Sección contraída ![](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.019.png)</p><p>Sección desplegada![ref12]</p>|
|Datos generales|Sección|N/A|L|Sección colapsada “Datos generales” del contrato.|N/A|N/A|
|Vigencia y montos|Sección|N/A|L|Sección colapsada “Datos de vigencia y montos” del contrato.|N/A|N/A|
|Grupos de servicio y/o conceptos |Sección|N/A|L|Sección colapsada “Grupos de servicio o conceptos” del contrato.|N/A|N/A|
|Registro de servicios|Sección|N/A|L|Sección colapsada “Registro de servicios” del contrato.|N/A|N/A|
|Proyección de caso de negocio|Sección|N/A|L|Sección colapsada “Proyección de caso de negocio” del contrato.|N/A|N/A|
|Cargar layout de los informes|Sección|N/A|L|Sección colapsada “Cargar *layout* de los informes” del contrato.|N/A|N/A|
|Atraso en el inicio de la prestación|Sección|N/A|L|Sección colapsada “Atraso en el inicio de la prestación” del contrato.|N/A|N/A|
|Informes documentales por única vez|Sección|N/A|L|Sección colapsada “Informes documentales por única vez” del contrato.|N/A|N/A|
|Informes documentales periódicos|Sección|N/A|L|Sección colapsada “Informes documentales periódicos” del contrato.|N/A|N/A|
|Informes periódicos de los servicios |Sección|N/A|L|Sección colapsada “Informes documentales de los servicios” del contrato.|N/A|N/A|
|Penas contractuales |Sección|N/A|L|Sección colapsada “Penas contractuales” del contrato.|N/A|N/A|
|Niveles de servicio (SLA)|Sección|N/A|L|Sección colapsada “Niveles de servicio (SLA)” del contrato.|N/A|N/A|
|Asignación de plantilla|Sección|N/A|L|Sección colapsada “Asignación de plantilla” del contrato.|N/A|N/A|
|Gestión documental |Sección|N/A|<p>L</p><p></p>|Sección colapsada “Gestión documental” del contrato.|N/A|N/A|
|Convenios modificatorios |Sección|N/A|L|Sección colapsada “Convenios modificatorios” asociados a los contratos.|N/A|N/A|
|Dictámenes asociados|Sección|N/A|L|Sección colapsada que muestra los “Dictámenes asociados” al contrato.|N/A|N/A|
|Facturas asociadas|Sección|N/A|L|Sección colapsada que muestra las “Facturas asociadas” al contrato.|N/A|N/A|
|Reintegros asociados|Sección|N/A|L|Sección colapsada que muestra los “Reintegros asociados” al contrato.|N/A|N/A|
|Cierre|Sección|N/A|L|Sección colapsada que permite visualizar el Acta de cierre.|N/A|N/A|
|Regresar|Botón|N/A|S|Opción que realiza el proceso para regresar a la pantalla búsqueda de “Contratos”.|N/A|Inicialmente se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32). Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas.|




Anexo - Ejemplos de botones

![Interfaz de usuario gráfica, Diagrama

Descripción generada automáticamente](Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.020.png)

Las acciones de cada botón se definen en los estilos correspondientes.































|**FIRMAS DE CONFORMIDAD** ||
| :-: | :- |
|**Firma 1**  |**Firma 2**  |
|**Nombre**: María del Carmen Castillejos Cárdenas. |**Nombre**: Rubén Delgado Ramírez. |
|**Puesto**: Usuaria ACPPI. |**Puesto**: Usuario ACPPI. |
|**Fecha**: |**Fecha**: |
|  |  |
|**Firma 3**  |**Firma 4** |
|**Nombre**: Rodolfo López Meneses. |**Nombre**: Diana Yazmín Pérez Sabido. |
|**Puesto**: Usuario ACPPI. |**Puesto**: Usuaria ACPPI. |
|**Fecha**: |**Fecha**: |
|  |  |
|**Firma 5** |**Firma 6** |
|**Nombre**: Yesenia Helvetia Delgado Naranjo. |**Nombre**: Alejandro Alfredo Muñoz Núñez. |
|**Puesto**: APE ACPPI. |**Puesto**: RAPE ACPPI. |
|**Fecha**: |**Fecha**: |
|  |  |
|**Firma 7** |**Firma 8** |
|**Nombre**: Luis Angel Olguin Castillo. |**Nombre**: Erick Villa Beltrán. |
|**Puesto**: Enlace ACPPI. |**Puesto**: Líder APE SDMA 6. |
|**Fecha**: |**Fecha**: |
|**  |**  |
|**Firma 9** |**Firma 10** |
|**Nombre**: Juan Carlos Ayuso Bautista. |**Nombre**: Angel Horacio López Alcaraz. |
|**Puesto**:** Líder Técnico SDMA 6. |**Puesto**: Analista de Sistemas SDMA 6. |
|**Fecha**: |**Fecha**: |
|**  | |



|||Página 21 de 21|
| :- | :-: | -: |

[ref1]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.003.png
[ref2]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.005.png
[ref3]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.006.png
[ref4]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.007.png
[ref5]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.008.png
[ref6]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.009.png
[ref7]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.010.png
[ref8]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.011.png "27A1341E"
[ref9]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.012.png
[ref11]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.015.png
[ref12]: Aspose.Words.029765d9-e995-4254-aec8-cbdf182af3be.018.png
