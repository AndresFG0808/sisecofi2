|![](Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.001.png)|Administración General de Comunicaciones y Tecnologías de la Información|
| :- | -: |
||Marco Documental 7.0|
|Fecha de aprobación del Template: 02/08/2023|<p>**Especificación de Interacción de Empleado SAT**</p><p>17\_3083\_EIU\_CasoDeNegocio.docx</p>|Versión del template: 7.00|
| :-: | :-: | :-: |


**<ID Requerimiento>** 8309

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación.
## <a name="_toc163744821"></a>**Tabla de Versiones y Modificaciones**

|<a name="tabla_versiones"></a>Versión|Descripción del cambio|Responsable de la Versión|Fecha|
| :- | :- | :-: | :-: |
|*1*|*Creación del documento*|Edgar Vergara Tadeo|*23/02/2024*|
|*1.1*|*Revisión del documento*|Luis Angel Olguin Castillo|*12/03/2024*|
|*1.2*|*Versión aprobada para firma*|<p>María del Carmen Castillejos Cárdenas</p><p>Rubén Delgado Ramírez</p>|*27/05/2024*|



**TABLA DE CONTENIDO**

[Tabla de Versiones y Modificaciones	1](#_toc163744821)

[Módulo: CONTRATOS	2](#_toc163744822)

[ESTILOS 01	2](#_toc163744823)

[Descripción de Elementos	2](#_toc163744824)

[Descripción de Campos	3](#_toc163744825)


##
















## <a name="_toc236129839"></a><a name="_toc236196644"></a><a name="_toc236558257"></a> **<a name="_toc163744822"></a>MÓDULO: CONTRATOS**
## <a name="_toc459285561"></a> **<a name="_toc163744823"></a>ESTILOS 01**

|**Nombre de la Pantalla:** |<p>Proyección de caso de negocio</p><p></p>|
| :- | :- |
|**Objetivo:**|<p>Permite al Empleado SAT consultar, cargar y actualizar la proyección de consumo de servicios  relacionados al contrato.</p><p></p><p></p>|
|**Casos de uso relacionados:**|17\_3083\_ECU\_CasoDeNegocio|
|||
![Imagen que contiene Tabla

Descripción generada automáticamente](Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.002.png)

### <a name="_toc236129841"></a><a name="_toc236196646"></a><a name="_toc236558259"></a><a name="_toc267478971"></a><a name="_toc163744824"></a>**DESCRIPCIÓN DE ELEMENTOS** 

|**Elemento**|**Descripción**|
| :- | :- |
|Proyección de caso de negocio|Sección que permite mostrar la información de la sección “Proyección de caso de negocio”.|
|![ref1]|<p>Opción que al ser seleccionada despliega o contrae la sección, tomando en cuenta lo siguiente:</p><p>Contrae la sección ![](Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.004.png)</p><p>Despliega la sección ![ref2]</p>|
|Descargar *layout![ref3]*|<p>Icono que permite descargar la estructura del *layout* de carga, sin datos.</p><p>El *layout* de caso de negocio estará conformado de la siguiente manera:</p><p></p>|

|Conceptos de servicio|Mes1-AA|Mes2-AA|Mes3-AA|Mes4-AA|Mes5-AA|Mes6-AA|Mes7-AA|Mes8-AA|<p></p><p>MesN-AN</p>|
| :- | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |

||<p>- El concepto de servicio (Una fila por cada concepto).</p><p>Una columna por cada mes de duración de la vigencia del contrato. </p>|
| :- | - |
|Archivo proyección CN|Muestra el nombre del campo para ingresar el archivo de proyección CN.|
|![ref4]|Muestra el nombre del archivo con extensión (.xlsx), correspondiente a la proyección.|
|Examinar|Opción que permite abrir el gestor de archivos del equipo de cómputo del Empleado SAT, para seleccionar un archivo con extensión (.xlsx).|
|Procesar proyección |Opción que permite iniciar el proceso de almacenamiento en base de datos (BD) de la proyección del caso de negocio.|
|![ref5] |Opción que permite exportar la información de la BD “Proyección de caso de negocio”, generando un archivo de Excel con extensión (.xlsx).|
|Conceptos de servicio|Campo de texto que mostrará todos los conceptos de servicio de la correspondientes a la sección “Registro de servicios” del contrato.|
|Meses-año|Estos campos son dinámicos, es decir, se debe mostrar una columna por cada mes de duración del contrato.|
|![ref6]|Paginador que permite navegar a través de las páginas resultantes de la consulta, considerando que el sistema debe mostrar inicialmente 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página.|
|![ref7]|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|
|![ref8]|Campo que permite filtrar la información de la columna en la que se requiere buscar específicamente. |
|<p>![](Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.012.png "27A1341E") </p><p></p>|Permite desplazarse de manera horizontal en la tabla. |
|<p>![ref9]</p><p></p>|Permite desplazarse de manera vertical en la tabla. |
###
### <a name="_toc236129842"></a><a name="_toc236196647"></a><a name="_toc236558260"></a><a name="_toc163744825"></a>**DESCRIPCIÓN DE CAMPOS**
###
|**Elemento**|**Tipo**|**Longitud**|<p>**Nivel de Acceso**</p><p>**(L, E, S)**</p>|**Descripción del Campo**|**Fórmulas**|**Precisiones**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Proyección de caso de negocio|Sección|N/A|L|Sección que permite mostrar la información de “Proyección de caso de negocio”.|N/A|N/A|
|![ref10]|Ícono |N/A |S |Opción que al ser seleccionada despliega o contrae la sección. |N/A |<p>Contrae la sección  ![](Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.015.png "D476EB3F") </p><p>Despliega la sección  ![](Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.016.png "1DED56A5") </p>|
|Descargar *layout* ![ref3]|Ícono|N/A|L, S|ícono que permite descargar la estructura del *layout* .|N/A|<p>Usar el *tooltip* “Descargar *layout*”.</p><p>Solo se visualiza si el Empleado SAT tiene permisos de “Editar”.</p>|
|Archivo proyección CN|Texto|N/A|L|Muestra el nombre del campo para ingresar el archivo de proyección CN.|N/A|N/A|
|![ref11]|Alfanumérico|N/A|L|Muestra el nombre del archivo con extensión (.xlsx), correspondiente a la proyección.|N/A|Solo se visualiza si el Empleado SAT tiene permisos de “Editar”.|
|Examinar|Botón|N/A|L, S|Opción que permite abrir el gestor de archivos del equipo de cómputo del Empleado SAT, para seleccionar un archivo con extensión (.xlsx).|N/A|Solo se visualiza si el Empleado SAT tiene permisos de “Editar”.|
|<p></p><p>Procesar proyección </p>|Botón|N/A|L, S|Opción que permite iniciar el proceso de almacenamiento en base de datos (BD) de la proyección del caso de negocio.|N/A|<p>Si tipo de consumo es “Volumetría” se validará por cada línea que la sumatoria de los meses sea igual a “Cantidad de servicios máxima” de la sección “Registro de servicios”.</p><p>Solo se visualiza si el Empleado SAT tiene permisos de “Editar”.</p>|
|![ref5] |Ícono|N/A|S|Opción que permite exportar la información de la BD “Proyección de caso de negocio”, generando un archivo de Excel con extensión (.xlsx).|N/A|Usar el *tooltip* “Exportar a Excel”.|
|Conceptos de servicio|Texto|N/A|L|Campo de texto que mostrará todos los conceptos de servicio de la correspondientes a la sección “Registro de servicios” del contrato.|N/A|N/A|
|Meses-año|Fecha|7|L|Estos campos son dinámicos, es decir, se debe mostrar una columna por cada mes de duración del contrato.|N/A|<p>Formato de fecha </p><p>MM-AAAA </p>|
|![ref12]|Paginador|N/A|S|Permite navegar a través de las páginas resultantes de la consulta. |N/A|Inicialmente se deben mostrar 15 registros por página, permitiendo visualizar entre 15, 50 y 100 registros por página. |
|![ref7]|Ícono|N/A|S|Opción que ordena la información de la tabla de forma ascendente o descendente y de forma alfabética, según aplique.|N/A|N/A|
|![ref13]|Filtro|250|E|Cuadro que permite filtrar la información de la columna en la que se requiere buscar específicamente.|N/A|Realiza el filtro de la información solo dentro de la tabla que se visualiza. |
|<p>![](Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.012.png "30CF95DC") </p><p></p><p></p>|Barra de desplazamiento |N/A |S |Permite desplazarse de manera horizontal en la tabla. |N/A |N/A |
|<p>![ref14]</p><p></p>|Barra de desplazamiento |N/A |S |Permite desplazarse de manera vertical en la tabla. |N/A |N/A |



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
|**Nombre:** Juan Carlos Ayuso Bautista.|**Nombre:** Eric Hector Pérez Pérez.|
|**Puesto:** Líder Técnico SDMA 6.|**Puesto:** Analista de Sistemas DS SDMA 6.|
|**Fecha**:|**Fecha**:|
|||
\*\

|||Página 5 de 7|
| :- | :-: | -: |

[ref1]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.003.png
[ref2]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.005.png
[ref3]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.006.png
[ref4]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.007.png
[ref5]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.008.png
[ref6]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.009.png
[ref7]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.010.png
[ref8]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.011.png
[ref9]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.013.png
[ref10]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.014.png
[ref11]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.017.png
[ref12]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.018.png
[ref13]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.019.png
[ref14]: Aspose.Words.3668e7a9-f058-4fc6-af38-d5aa61aac0a9.020.png
