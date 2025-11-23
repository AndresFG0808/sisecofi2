**Administración General de Comunicaciones ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.001.png)**

**y Tecnologías de la Información**

**Marco Documental 7.0**
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

` `**<ID Requerimiento>**8309** 

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación** 

**Tabla de Versiones y Modificaciones** 

|Versión |Descripción del cambio |Responsable de la Versión |Fecha |
| - | - | :-: | - |
|*1* |*Creación del documento* |Angel Horacio López Alcaraz |*01/03/2024* |
|*1.1* |*Revisión del documento* |Luis Angel Olguin Castillo |*16/04/2024* |
|*1.2* |*Revisión del documento* |Andrés Mojica Vázquez |*18/06/2024* |

**Tabla de Contenido** 

[17_3083_ECU_GenerarNotaDeCredito ................................................................................................. 2 ](#_page1_x82.00_y132.92)

1. [Descripción ........................................................................................................................................................ 2 ](#_page1_x102.00_y148.92)
1. [Diagrama del Caso de Uso ...................................................................................................................... 2 ](#_page1_x102.00_y225.92)
1. [Actores ................................................................................................................................................................. 2 ](#_page1_x102.00_y442.92)
1. [Precondiciones............................................................................................................................................... 2 ](#_page1_x102.00_y577.92)
1. [Post condiciones ........................................................................................................................................... 3 ](#_page2_x102.00_y195.92)
1. [Flujo primario .................................................................................................................................................. 3 ](#_page2_x102.00_y297.92)
7. [Flujos alternos .................................................................................................................................................8 ](#_page7_x102.00_y466.92)
7. [Referencias cruzadas................................................................................................................................ 16 ](#_page15_x102.00_y230.92)
7. [Mensajes ........................................................................................................................................................... 16 ](#_page15_x102.00_y333.92)
7. [Requerimientos No Funcionales .................................................................................................... 17 ](#_page16_x102.00_y158.92)
7. [Diagrama de actividad ........................................................................................................................... 19 ](#_page18_x102.00_y145.92)
7. [Diagrama de estados ............................................................................................................................. 20 ](#_page19_x102.00_y711.92)
7. [Aprobación del cliente ........................................................................................................................... 21 ](#_page20_x102.00_y158.92)



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page1_x82.00_y132.92"></a>17\_3083\_ECU\_GenerarNotaDeCredito 

1. **Descripción<a name="_page1_x102.00_y148.92"></a>  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.002.png)**

El objetivo de este Caso de Uso es permitir al Empleado SAT generar el registro de notas de crédito. 

2. **Diagrama<a name="_page1_x102.00_y225.92"></a> del Caso de Uso ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.003.png)**

![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.004.png)

3. **Actores<a name="_page1_x102.00_y442.92"></a>  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.005.png)**



|**Actor** |**Descripción** |
| - | - |
|**Empleado SAT** |El Empleado SAT es el que tiene el o los roles otorgados por la  Administración  Central  de  Seguridad,  Monitoreo  y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema. |

4. **Precondiciones<a name="_page1_x102.00_y577.92"></a>** ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.006.png)
- El Empleado SAT se ha autenticado en el sistema con e.firma válida. 
- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa. 
- El sistema ha validado que el Empleado SAT  cuenta con los roles para ingresar a la sección “Notas de crédito” del módulo “Consumo de Servicios”. 
- El  Empleado  SAT  ha  ingresado  a  un  dictamen  de  acuerdo  con  el **(17\_3083\_ECU\_GenerarDictamen)**. 
- El Empleado SAT ha seleccionado alguna de las siguientes opciones: Editar dictamen  y  Ver  dictamen  en  el  módulo  “Consumo  de  Servicios” relacionados a un contrato. 
- El Empleado SAT ha seleccionado una opción en el campo Convenio de Colaboración del contrato. ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.007.png)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



- El sistema ha validado que se cuenta con los Web Service activos para ![ref1]realizar la validación de las notas de crédito. 
- El empleado SAT ha registrado la plantilla activa de verificación para la carga de documentos en la sección documental. 
5. **Post<a name="_page2_x102.00_y195.92"></a> condiciones  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.009.png)**
- El Empleado SAT pudo generar una nueva nota de crédito relacionada a un dictamen. 
- El Empleado SAT pudo editar la información de una o más notas de crédito. 
- El Empleado SAT pudo cancelar una o más notas de crédito. 
6. **Flujo<a name="_page2_x102.00_y297.92"></a> primario** ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.010.png)![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.011.png)



|**Actor** |**Sistema** ||||
| - | - | :- | :- | :- |
|<p>1\.  El  Caso  de  Uso  inicia  cuando  el Empleado  SAT  selecciona  la sección  **“Notas  de  crédito”**  del **(17\_3083\_ECU\_GenerarDictamen)**. </p><p>￿  En caso de haber seleccionado </p><p>la opción “Ver dictamen” en el módulo  “Consumo  de Servicios”,  aplica  la  regla  de negocio **(RNA167)**. </p>|<p>2\.  Obtiene de la base de datos (BD) la </p><p>información  de  los  catálogos correspondientes  para  los siguientes campos conforme a la **(RNA01)** y **(RNA83)**: </p><p>o  Estatus </p>||||
||<p>3\.  Consulta en la BD la existencia de </p><p>información de notas de crédito. </p><p>￿  En  caso  de  no  encontrar </p><p>registros, aplica la **(RNA244)**. </p>||||
||<p>4\.  Muestra en pantalla la información </p><p>del formulario “Notas de crédito”, conforme a las **(RNA87)**, **(RNA51)**, **(RNA142)**  y  **(RNA143)**  con  los siguientes campos:** </p><p>Opciones: </p><p>- Nuevo registro ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.012.png)</p><p>- Editar ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.013.png)</p><p>- Cancelar nota de crédito ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.014.png)</p>||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|||



||<p>Formulario </p><p>- N° (Id nota de crédito) </p><p>- Archivo a cargar\* </p><p>- Añadir PDF\* </p><p>Opciones: </p><p>- Examinar, del campo “Archivo a cargar”. </p><p>- Leer XML\* </p><p>- Examinar,  del  campo  “Añadir PDF”. </p><p>- Folio\* </p><p>- Comprobante fiscal\* </p><p>- Fecha de generación\* </p><p>- Estatus </p><p>- Moneda\* </p><p>- Tasa\* </p><p>- Subtotal\* </p><p>- IEPS\* </p><p>- IVA\* </p><p>- Otros impuestos </p><p>- Total\* </p><p>- Total en pesos\* </p><p>- Comentarios </p><p>Desglose  de  montos.  Aplica  la **(RNA144)**. </p><p>- % SAT\* </p><p>- Monto\* </p><p>- Monto en pesos\* </p><p>- % Convenio de colaboración\* </p><p>- Monto </p><p>- Monto en pesos </p><p>Opciones: </p><p>- Cancelar (habilitado). Aplica la **(RNA246)** </p><p>- Guardar. Aplica la **(RNA246)**. </p><p>Ver **(17\_3083\_EIU\_GenerarNotaDeCre dito)** Estilos 01. </p>|
| :- | - |
|<p><a name="_page3_x108.00_y694.92"></a>5.  Selecciona  la  opción  **“Nuevo** </p><p>**registro”** y el flujo continúa. </p>|<p>6\.  Inserta el formulario al inicio de la </p><p>sección, con información ordenada de  forma  descendente  tomando en consideración el No. de nota. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



|<p>- Si selecciona la opción **“Editar”** en el nuevo registro, continúa en el [**(FA09)**](#_page11_x102.00_y145.92). </p><p>- Si  selecciona  la  opción **“Cancelar nota de crédito”** un nuevo registro, continúa en el [**(FA10)**](#_page12_x102.00_y429.92). </p>||
| - | :- |
|<p><a name="_page4_x108.00_y247.92"></a>7.  Selecciona  la  opción  **“Examinar”** </p><p>correspondiente  al  campo **“Archivo a cargar\*”**. </p>|<p>8\.  Muestra el gestor de archivos del </p><p>equipo de cómputo del Empleado SAT. </p>|
|9\.  Selecciona el archivo XML. |<p>10\.  Muestra el nombre del documento </p><p>cargado en el campo “Leer XML”. </p>|
|11\.  Selecciona la opción **“Leer XML”**. |<p><a name="_page4_x311.00_y321.92"></a>12.  Valida el formato XML. </p><p>￿  En caso de haber seleccionado </p><p>un  tipo  de  archivo  distinto, continúa en el **[**(FA02)**](#_page8_x102.00_y133.92)**. </p>|
||<p>13\.  Realiza la lectura y validación de los </p><p>siguientes campos del formulario, conforme a la **(RNA145)**. </p><p>- Folio\* </p><p>- Emisor RFC </p><p>- Emisor Nombre </p><p>- Comprobante fiscal\* </p><p>- Fecha de generación\* </p><p>- Moneda\* </p><p>- TasaoCuota(IVA)\* </p><p>- Subtotal\* </p><p>- IEPS(Monto)\* </p><p>- IVA(Monto)\* </p><p>- Otros impuestos </p><p>- Total\* </p><p>Conceptos </p><p>- ClaveProdServ </p><p>- ClaveUnidad= “E48” </p><p>￿  En caso de que la estructura de los  campos  del  CFDI  no  sea correcta, continúa en el [**(FA13)**](#_page14_x102.00_y620.92). </p>|
||<p>14\.  Valida que “Emisor RFC” y “Emisor </p><p>Nombre”  sean  correctos  y correspondan  al  proveedor registrado. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



||<p>￿  En  caso  de  ser  diferente, </p><p>continúa en el [**(FA03)**](#_page8_x102.00_y353.92). </p>|
| :- | - |
||<p>15\.  Valida  que  el  comprobante  fiscal </p><p>no  haya  sido  utilizado  en  otro contrato. </p><p>￿  En  caso  de  ser  un </p><p>comprobante  ya  utilizado, continúa en el [**(FA04)**](#_page8_x102.00_y582.92). </p>|
||<p>16\.  Valida  la  estructura  del  archivo  a </p><p>través del Web Service descrito en el **(17\_3083\_EZS\_SeguimientoFinan cieroyControl)**. </p><p>￿  En caso de que el archivo no </p><p>cumpla con los requerimientos de  validación,  continúa  en  el [**(FA01)**](#_page7_x102.00_y506.92). </p>|
||<p>17\.  Carga  el  archivo  y  muestra  el </p><p>formulario  con  los  siguientes campos: </p><p>- Folio\* </p><p>- Comprobante fiscal\* </p><p>- Fecha de generación\* </p><p>- Estatus </p><p>- Moneda\* </p><p>- Tasa\* </p><p>- Subtotal\* </p><p>- IEPS\* </p><p>- IVA\* </p><p>- Otros impuestos </p><p>- Total\* </p><p>- Total  en  pesos\*.  Aplica  la **(RNA261)**. </p>|
|<p><a name="_page5_x108.00_y627.92"></a>18.  Selecciona  la  opción  **“Examinar”** </p><p>para  cargar  el  documento  PDF, correspondiente al campo **“Añadir PDF\*”**. </p>|<p>19\.  Muestra el gestor de archivos del </p><p>equipo de cómputo del Empleado SAT. </p>|
|<p>20\. Selecciona el archivo con extensión </p><p>.PDF. </p>|<p>21\.  Valida que el archivo seleccionado </p><p>corresponda a un tipo de archivo PDF válido. </p><p>￿  En caso de haber seleccionado </p><p>un  tipo  de  archivo  distinto  a PDF, continúa en el [**(FA12)**](#_page14_x102.00_y363.92). </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



||<p>22\. Muestra el nombre del documento </p><p>cargado en el campo añadir PDF. </p>|
| :- | - |
|<p><a name="_page6_x108.00_y170.92"></a>23. Captura  la  información  en  el </p><p>siguiente campo de acuerdo con lo que le aplique a la nota de crédito: </p><p>- Comentarios </p><p>Sección desglose de montos: </p><p>- Monto\* </p>|<p>24\. Valida que el monto ingresado no </p><p>supere  el  total  de  la  nota  de crédito. </p><p>￿  En caso de haber ingresado un </p><p>“Monto\*” mayor al monto total de  la  “Nota  de  crédito”, continúa en el [**(FA05)**](#_page9_x102.00_y145.92). </p>|
||<p>25\. Muestra los cálculos de los campos </p><p>de la sección desglose de montos. Aplica la **(RNA144)**. </p>|
|<p>26\. Selecciona la opción **“Guardar”** y el </p><p>flujo continúa. </p><p>￿  En  caso  de  que  seleccione  la </p><p>opción **“Cancelar”**, continúa en el **[**(FA06)**](#_page9_x102.00_y377.92)**. </p>|<p>27\. Valida  que  se  hayan  ingresado </p><p>todos  los  campos  obligatorios, conforme a las **(RNA03)**. </p><p>￿  En caso de no haber ingresado </p><p>todos los campos obligatorios de la nota de crédito, continúa en el [**(FA07)**](#_page10_x102.00_y133.92). </p>|
||<p>28\. Valida  la  estructura  de  los  datos </p><p>ingresados  en  los  campos  de acuerdo con la **(RNA255)**. </p><p>￿  En caso de que la estructura de </p><p>los  datos  ingresados  sea incorrecta,  continúa  en  el [**(FA11)**](#_page14_x102.00_y133.92). </p>|
||<p>29\. Almacena en la BD la información </p><p>de las Pistas de Auditoría. </p><p>Datos que se almacenan: </p><p>**Módulo**= Dictamen- NC </p><p>**Fecha y Hora**= Fecha y hora del sistema,  usando  el  formato DD/MM/AAAA HH:MM:SS </p><p>**RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. </p><p>**Tipo  de  movimiento**=  **INSR** (Insertar) y/o **UPDT** (Modificar) </p><p>**Movimiento**=  Aplica  la **(RNA239)** </p><p>- Id de Dictamen </p><p>- Folio  de  la  Nota  de crédito </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



||<p>-  Comprobante fiscal </p><p>￿  En caso de que no se puedan </p><p>almacenar  las  Pistas  de Auditoría,  continúa  en  el [**(FA08)**](#_page10_x102.00_y375.92). </p>|
| :- | - |
||<p>30\. Almacena en la BD la información </p><p>capturada  de  la  nota  de  crédito. Aplica la **(RNA247)**. </p>|
||<p>31\.  Guarda los documentos agregados </p><p>aplicando la **(RNA38)** y **(RNA146)**. </p>|
||<p>32\. Actualiza el estatus del dictamen a </p><p>“Facturado”, solo en caso de que el dictamen se encuentre en estatus “Proforma”. </p>|
||<p>33\. Muestra el [**(MSG001)**](#_page15_x108.00_y394.92) con la opción </p><p>“Aceptar”. </p>|
|34\. Selecciona la opción **“Aceptar”**. |35\. Cierra el mensaje. |
||36\. Fin del Caso de Uso. |

7. **Flujos<a name="_page7_x102.00_y466.92"></a> alternos  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.015.png)**

<a name="_page7_x102.00_y506.92"></a>**FA01 Valida nota de crédito a través del WS ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.016.png)**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA01**  inicia  cuando  el  sistema </p><p>identifica que la nota de crédito no cuenta con estatus valido. </p>|
||<p>2\.  Muestra el [**(MSG016)**](#_page15_x108.00_y753.92) con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||5\.  Fin del Caso de Uso. |



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page8_x102.00_y133.92"></a>**FA02 Formato XML no válido ![ref1]**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA02**  inicia  cuando  el  sistema </p><p>identifica que se seleccionó un tipo de archivo no válido. </p>|
||<p>2\.  Muestra el mensaje [**(MSG005)**](#_page15_x108.00_y484.92) con </p><p>la opción “Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||<p>5\.  Regresa  al  paso [` `**7** ](#_page4_x108.00_y247.92) del  Flujo </p><p>primario. </p>|

<a name="_page8_x102.00_y353.92"></a>**FA03 Validación incorrecta del RFC del proveedor** 

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA03**  inicia  cuando  el  sistema </p><p>valida  que  el  “Emisor  RFC”  y “Emisor Nombre” que contiene el XML, no corresponde al proveedor registrado. </p>|
||<p>2\.  Muestra el [**(MSG007)**](#_page15_x108.00_y534.92) con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||<p>5\.  Regresa  al  paso [` `**7** ](#_page4_x108.00_y247.92) del  Flujo </p><p>primario. </p>|

<a name="_page8_x102.00_y582.92"></a>**FA04 El comprobante fiscal ya ha sido utilizado en otro contrato ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.017.png)**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA04**  inicia  cuando  el  sistema </p><p>valida que el Comprobante fiscal” y “Folio”  ya  ha  sido  utilizado previamente en otro contrato. </p>|
||<p>2\.  Muestra el [**(MSG006)**](#_page15_x108.00_y509.92) con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||<p>5\.  Regresa  al  paso [` `**7** ](#_page4_x108.00_y247.92) del  Flujo </p><p>primario. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page9_x102.00_y145.92"></a>**FA05 El Monto ingresado es mayor al monto total de la nota de crédito** 

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA05**  inicia  cuando  el  sistema </p><p>valida que el “Monto\*” ingresado es superior al monto total de la “Nota de crédito”. </p>|
||<p>2\.  Muestra el [**(MSG010)**](#_page15_x108.00_y605.92) con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||<p>5\.  Regresa  al  paso [` `**23** ](#_page6_x108.00_y170.92) del  Flujo </p><p>primario. </p>|

<a name="_page9_x102.00_y377.92"></a>**FA06 Selecciona la opción “Cancelar” del formulario de la nota de crédito ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.018.png)**

|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El **FA06** inicia cuando el Empleado </p><p>SAT  selecciona  la  opción **“Cancelar”**  del  formulario  **“Notas de crédito”**. </p>|<p>2\.  Muestra  el  **[**(MSG008)**](#_page15_x108.00_y557.92)**  con**  las </p><p>opciones “Sí” y “No”. </p>|
|<p>3\.  Selecciona  la  opción  **“Sí**”,  y </p><p><a name="_page9_x311.00_y481.92"></a>continúa en el pas[o **5** ](#_page9_x311.00_y555.92)de este flujo. </p><p>￿  Si  selecciona  la  opción  **“No”**, </p><p>continúa en el paso [**4** ](#_page9_x311.00_y481.92)de este flujo. </p>|<p>4\.  Cierra la ventana emergente y no </p><p>realiza ninguna acción,  regresa al paso donde se invocó. </p>|
||<a name="_page9_x311.00_y555.92"></a>5.  Cierra el mensaje |
||<p>6\.  Regresa  los  campos  a  la  forma </p><p>inicial. </p><p>- En  caso  de  ser  un  registro nuevo, elimina el formulario. </p><p>- En  caso  de  ser  una  edición restablece los campos iniciales. </p>|
||<p>7\.  Regresa  al  paso [` `**5** ](#_page3_x108.00_y694.92) del  Flujo </p><p>primario. </p>|



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page10_x102.00_y133.92"></a>**FA07 No se ingresaron todos los datos obligatorios ![ref1]**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA07**  inicia  cuando  el  sistema </p><p>identifica  que  no  se  ingresaron todos  los  datos  obligatorios  en  el formulario “Notas de crédito”. </p>|
||<p>2\.  Muestra  en  rojo  los  campos </p><p>pendientes de capturar. </p>|
||<p>3\.  Muestra el [**(MSG009)**](#_page15_x108.00_y580.92) con la opción </p><p>“Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje. |
||<p>6\.  Regresa  al  paso [` `**23** ](#_page6_x108.00_y170.92) del  Flujo </p><p>primario. </p>|

<a name="_page10_x102.00_y375.92"></a>**FA08 No se pueden almacenar las Pistas de Auditoría ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.019.png)**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El **FA08** inicia cuando interviene un </p><p>evento  ajeno  y  no  se  pueden almacenar las Pistas de Auditoría. </p>|
||<p>2\.  Cancela la operación sin completar </p><p>el  movimiento  que  estaba  en proceso. </p>|
||<p>3\.  Muestra el mensaje informativo de </p><p>acuerdo con lo siguiente: </p><p>- Si la pista de auditoría es por el tipo  de  movimiento  **UPDT**  e **INSR**, se muestra el [**(MSG011)**](#_page15_x108.00_y630.92). </p><p>- Si la pista de auditoría es por el tipo  de  movimiento  **CNST**,  se muestra el [**(MSG012)**](#_page15_x108.00_y655.92). </p><p>Cada  mensaje  se  muestra  con  la opción “Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje. |
||<p>6\.  Regresa al paso previo que detona </p><p>la acción de la pista de auditoría. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page11_x102.00_y145.92"></a>**FA09 Selecciona la opción “Editar” la información de una nota de crédito ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.020.png)**

|**Actor** |**Sistema** ||||
| - | - | :- | :- | :- |
|<p>1\.  El **FA09** inicia cuando el Empleado </p><p>SAT selecciona la opción **“Editar”** el registro de una **“Nota de crédito”**. </p>|<p>2\.  Muestra el formulario de la “Nota </p><p>de  crédito”  conforme  a  las **(RNA87)**, **(RNA51)**, **(RNA142)** con los siguientes datos: </p><p>Formulario </p><p>- N°  </p><p>- Archivo a cargar\* </p><p>- Añadir PDF\* </p><p>Opciones: </p><p>- Examinar, del campo Archivo a cargar. </p><p>- Leer XML  </p><p>- Examinar,  del  campo  añadir PDF. </p><p>- Folio\* </p><p>- Comprobante fiscal\* </p><p>- Fecha de generación\* </p><p>- Estatus </p><p>- Moneda\* </p><p>- Tasa\* </p><p>- Subtotal\* </p><p>- IEPS\* </p><p>- IVA\* </p><p>- Otros impuestos </p><p>- Total\* </p><p>- Total en pesos\* </p><p>- Comentarios </p><p>Desglose  de  montos.  Aplica  la **(RNA144)** </p><p>- %SAT  </p><p>- Monto\*  </p><p>- Monto en pesos\*  </p><p>- % Convenio de colaboración  </p><p>- Monto\*  </p><p>- Monto en pesos\* </p><p>Opciones: </p><p>- Cancelar. Aplica la **(RNA246)**. </p><p>- Guardar. Aplica la **(RNA246)**. </p>||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|||

Ver ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.021.png)**(17\_3083\_EIU\_GenerarNotaDeCre dito)** Estilos 01. 

3\.  Actualiza  el  XML  de  la  **“Nota  de**  4.  Muestra  el  **[**(MSG002)**](#_page15_x108.00_y415.92)**  con  las 

**crédito”**  y  selecciona  la  opción  opciones “Si” y “No”. 

**“Leer XML”**. 

- En caso de no actualizar el XML regresa  al  paso [` `**7** ](#_page4_x108.00_y247.92) del  flujo primario. 

5\.  Selecciona  la  opción  **“No”**  y <a name="_page12_x311.00_y281.92"></a> 6.  Cierra el mensaje y regresa al paso 

continúa en el pas[o **6** ](#_page12_x311.00_y281.92)de este flujo. [` `**7** ](#_page4_x108.00_y247.92)del flujo primario. 

- En  caso  de  seleccionar  la opción **“Si”** continúa en el paso [**7** ](#_page12_x311.00_y367.92)de este flujo. 

<a name="_page12_x311.00_y367.92"></a>7.  Cierra el mensaje y regresa al paso 

[**12** ](#_page4_x311.00_y321.92)del Flujo primario. 

<a name="_page12_x102.00_y429.92"></a>**FA10 Cancelar una nota de crédito  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.022.png)**

|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El **FA10** inicia cuando el Empleado </p><p>SAT selecciona la opción **“Cancelar nota de crédito”**. </p>|<p>2\.  Muestra  el  **[**(MSG003)**](#_page15_x108.00_y438.92)**  con  las </p><p>opciones “Sí” y “No”. </p>|
|<p>3\.  Selecciona  la  opción  **“Si”**  y </p><p><a name="_page12_x311.00_y521.92"></a>continúa en el paso [**5** ](#_page12_x311.00_y619.92)del presente flujo. </p><p>￿  Si  selecciona  la  opción  **“No”**, </p><p>continúa  en  el  paso [` `**4** ](#_page12_x311.00_y521.92) de  este flujo. </p>|4\.  Cierra  la  ventana  emergente  y regresa al pas[o **5** ](#_page3_x108.00_y694.92)del** Flujo primario. |
||<p><a name="_page12_x311.00_y619.92"></a>5.  Muestra  la  ventana  emergente </p><p>“Justificación”  con  las  siguientes opciones: </p><p>- Justificación </p><p>Opciones: </p><p>- Aceptar </p><p>- Cerrar </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



||Ver **(17\_3083\_EIU\_GenerarNotaDeCre dito)** Estilos 02. |
| :- | :- |
|<p>6\.  Agrega  la  justificación  de  la </p><p>cancelación de la nota de crédito. </p>||
|<p>7\.  Selecciona  la  opción  la  opción </p><p>**“Aceptar”** y el flujo continúa. </p><p>￿  En  caso  de  que  seleccione  la </p><p>opción **“Cerrar”**, regresa al paso [**5** ](#_page3_x108.00_y694.92)del Flujo primario. </p>|<p>8\.  Almacena en la BD la información </p><p>de las Pistas de Auditoría. </p><p>Datos que se almacenan: </p><p>**Módulo**= Dictamen-NC </p><p>**Fecha y Hora**= Fecha y hora del sistema,  usando  el  formato DD/MM/AAAA HH:MM:SS </p><p>**RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. </p><p>**Tipo  de  movimiento**=  **UPDT** (Modificar) </p><p>**Movimiento**=  Aplica  la **(RNA239)** </p><p>- Id de Dictamen </p><p>- Folio  de  la  Nota  de crédito </p><p>- Comprobante fiscal </p><p>- Estatus= Cancelado </p><p>￿  En  caso  de  que  no  se  puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA08)**](#_page10_x102.00_y375.92). </p>|
||<p>9\.  Actualiza el estatus de la “Nota de </p><p>crédito” a “Cancelado” y muestra la información  de  la  nota  en  modo lectura. Aplica la **(RNA247)**. </p>|
||<p>10\.  Concatena  el  texto  “Motivo  de </p><p>cancelación:”,  la  justificación previamente capturada, el carácter pipe (|) y los “Comentarios”, en el campo “Comentarios”. </p>|
||<p>11\.  Muestra el [**(MSG004)**](#_page15_x108.00_y461.92)** con la opción </p><p>“Aceptar”. </p>|
|12\.  Selecciona la opción **“Aceptar”**. |13\.  Cierra el mensaje. |
||14\.  Recarga la pantalla. |
||15\.  Fin del Caso de Uso.  |

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page14_x102.00_y133.92"></a>**FA11 La estructura de los datos ingresados es incorrecta ![ref1]**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA11**  inicia  cuando  el  sistema </p><p>identifica que la estructura de los datos ingresados es incorrecta.  </p>|
||<p>2\.  Muestra  en  rojo  los  campos  con </p><p>estructura incorrecta. </p>|
||<p>3\.  Muestra el [**(MSG014)**](#_page15_x108.00_y705.92) con la opción </p><p>“Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje. |
||<p>6\.  Regresa  al  paso [` `**23** ](#_page6_x108.00_y170.92) del  Flujo </p><p>primario. </p>|

<a name="_page14_x102.00_y363.92"></a>**FA12 Formato PDF no válido** 

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA12**  inicia  cuando  el  sistema </p><p>identifica  que  el  formato  del archivo  PDF  cargado  no  es correcto. </p>|
||<p>2\.  Muestra el [**(MSG013)**](#_page15_x108.00_y680.92) con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||<p>5\.  Realiza lo siguiente: </p><p>￿  Regresa  al  paso [` `**18** ](#_page5_x108.00_y627.92) del  Flujo </p><p>primario. </p>|

<a name="_page14_x102.00_y620.92"></a>**FA13 Validación de campos CFDI incorrecta ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.023.png)**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA13**  inicia  cuando  el  sistema </p><p>identifica que la estructura de los campos  del  CFDI  ingresados  es incorrecta. </p>|
||<p>2\.  Muestra el [**(MSG015)**](#_page15_x108.00_y730.92) con la opción </p><p>“Aceptar”. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

3\.  Selecciona la opción **“Aceptar”**.  4.  Cierra el mensaje. ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.024.png)

5\.  Regresa  al  paso [` `**7** ](#_page4_x108.00_y247.92) del  Flujo 

primario.  

8. **Referencias<a name="_page15_x102.00_y230.92"></a> cruzadas  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.025.png)**
- 17\_3083\_CRN\_SeguimientoFinancieroYControl. 
- 17\_3083\_EZS\_SeguimientoFinancieroyControl. 
- 17\_3083\_EIU\_GenerarNotaDeCredito. 
- 17\_3083\_ECU\_GenerarDictamen. 
9. **Mensajes<a name="_page15_x102.00_y333.92"></a>  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.026.png)![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.027.png)**



|**ID Mensaje** |**Descripción** |
| - | - |
|<a name="_page15_x108.00_y394.92"></a>**MSG001** |La nota de crédito se registró exitosamente. |
|<a name="_page15_x108.00_y415.92"></a>**MSG002** |¿Está seguro de actualizar el XML de la nota de crédito? |
|**MSG003** |<a name="_page15_x108.00_y438.92"></a>La  nota  de  crédito  se  cancelará  y  no  se  podrá  modificar  la información posteriormente. ¿Desea continuar? |
|<a name="_page15_x108.00_y461.92"></a>**MSG004** |La nota de crédito ha sido cancelada exitosamente. |
|**MSG005** |<a name="_page15_x108.00_y484.92"></a>El tipo de archivo seleccionado no es un archivo válido, favor de verificarlo y seleccionar un archivo con extensión XML. |
|**MSG006** |<a name="_page15_x108.00_y509.92"></a>El comprobante fiscal del archivo seleccionado ya fue utilizado previamente, favor de verificarlo. |
|**MSG007** |<a name="_page15_x108.00_y534.92"></a>El comprobante fiscal no corresponde al RFC y/o razón social del proveedor del contrato-dictamen, favor de verificarlo. |
|<a name="_page15_x108.00_y557.92"></a>**MSG008** |Se perderá la información no guardada, ¿Desea continuar? |
|**MSG009** |<a name="_page15_x108.00_y580.92"></a>No se ingresaron todos los campos obligatorios en el formulario, favor de verificarlo. |
|**MSG010** |<a name="_page15_x108.00_y605.92"></a>El  monto  ingresado  es  superior  al  monto  total  de  la  nota  de crédito, favor de verificarlo. |
|**MSG011** |<a name="_page15_x108.00_y630.92"></a>Ocurrió  un  error  al  guardar  el  registro,  favor  de  intentar nuevamente (PA01). |
|**MSG012** |<a name="_page15_x108.00_y655.92"></a>Ocurrió un error al  consultar la información, favor de intentar nuevamente (PA01). |
|**MSG013** |<a name="_page15_x108.00_y680.92"></a>La extensión del archivo no es correcta. Favor de verificar y cargar archivo con extensión PDF. |
|**MSG014** |<a name="_page15_x108.00_y705.92"></a>La  estructura  de  la  información  es  incorrecta.  Intente nuevamente.|
|**MSG015** |<a name="_page15_x108.00_y730.92"></a>La estructura de los datos no es correcta de acuerdo al anexo 20, favor de validar. |
|<a name="_page15_x108.00_y753.92"></a>**MSG016** |Mensaje de rechazo. |

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

10. **Requerimientos<a name="_page16_x102.00_y158.92"></a> No Funcionales  ![ref2]![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.029.png)**



|**ID de RNF** |**Requerimiento No Funcional** |**Descripción** |||
| - | :-: | - | :- | :- |
|**RNF001** |Disponibilidad |El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas. |||
|**RNF002** |Concurrencia |<p>El número de Empleados SAT que puede tener el sistema son 150.  </p><p>El número de accesos concurrentes que debe soportar  este  sistema  son  máximo  30 Empleados SAT. </p>|||
|**RNF003** |Seguridad |El  acceso  solo  podrá  ser  otorgado  a  todo Empleado SAT que tenga los roles  asignados por  la  Administración  Central  de  Seguridad, Monitoreo y Control (ACSMC) para cada módulo de este sistema. |||
|**RNF004** |Usabilidad |<p>El  sistema  deberá  manejar  los  siguientes elementos para facilitar la navegación:  </p><p>- Mensajes  tipo  flotantes  (*tooltips*)  con información de la herramienta que ofrece ayuda  contextual,  como  guía  para  el Empleado SAT.   </p><p>- Componente  de  ordenamiento  que permita  acomodar  la  información  de  la tabla  de  forma  ascendente  o descendente,  considerando  la  columna donde es seleccionado. </p><p>￿ </p><p>Contar  con  un  diseño  responsivo  que permita  su  óptima  visualización  en distintos tipos de dispositivos finales. </p>|||
|**RNF005** |Eficiencia |Las  consultas  se  dividen  en  generales  y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT. |||
|**RNF006** |Usabilidad |<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que  el  sistema  debe  mostrar  inicialmente  15 registros por página, permitiendo al Empleado SAT  seleccionar  los  registros  que  requiere visualizar, teniendo las opciones 15, 50 y 100: </p><p>￿  Ir a la primera página (debe mostrar la </p><p>primera  página  con  el  resultado  de  la consulta). </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|||



|||<p>- Ir  a  la  última  página  (debe  mostrar  la última  página  con  el  resultado  de  la consulta). </p><p>- Ir a la siguiente página (debe mostrar la siguiente página, considerando la página actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT). </p><p>- Ir a la página anterior (debe mostrar la página  anterior  considerando  la  actual con el resultado de la consulta).   </p><p>En  la  tabla  deben  mostrarse  los  registros ordenados alfabéticamente.  </p>|
| :- | :- | - |
|**RNF007** |Seguridad |Las Pistas de Auditoría deben estar protegidas contra  accesos  no  autorizados.  Solo  los Empleados  SAT  autorizados  pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada  para  mantenerla  confidencial  y  evitar exposiciones no autorizadas.  |
|**RNF008** |Fiabilidad|El  sistema  debe  ser  capaz  de  manejar excepciones  de  manera  efectiva  y  presentar mensajes  claros  y  comprensibles  para garantizar  una  adecuada  interacción  con  el sistema.  |
|**RNF009** |Seguridad |Se debe mantener la información en pantalla en  caso  de  un  error  al  guardar  las  Pistas  de Auditoría,  siempre  y  cuando  el  escenario  lo permita.  Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema. |
|**RNF010** |Integridad |Al almacenar la información en la BD de tipo Texto  o  alfanumérico  se  deben  eliminar  los espacios en blanco al inicio y fin de la cadena |



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

11. **Diagrama<a name="_page18_x102.00_y145.92"></a> de actividad**  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.030.png)![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.031.png)

![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.032.jpeg)



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.033.png)

12. **Diagrama<a name="_page19_x102.00_y711.92"></a> de estados**  ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.034.png)

No Aplica, no hay cambios de estados ni transiciones. ![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.035.png)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

13. **Aprobación<a name="_page20_x102.00_y158.92"></a> del cliente  ![ref2]**



|||||
| :- | :- | :- | :- |
|**FIRMAS DE CONFORMIDAD**  ||||
|||||
|**Firma 1**   |**Firma 2**   |||
|**Nombre**: Andrés Mojica Vázquez |**Nombre**: Ricardo Chávez Gutiérrez |||
|**Puesto**: Usuario ACPPI.  |**Puesto**: Usuario ACPPI.  |||
|**Fecha:**  |**Fecha:**  |||
|||||
|**Firma 3**   |**Firma 4**  |||
|**Nombre**: Yesenia Helvetia Delgado Naranjo.  |<p>` `**Nombre**:**  Alejandro  Alfredo  Muñoz</p><p>Núñez.  </p>|||
|**Puesto**: APE ACPPI.  |**Puesto:** RAPE ACPPI.  |||
|**Fecha**:  |**Fecha**:  |||
|||||
|**Firma 5**  |**Firma 6**  |||
|**Nombre**: Luis Angel Olguin Castillo. |` `**Nombre**: Erick Villa Beltrán.  |||
|**Puesto**: Enlace ACPPI.  |**Puesto**: Líder APE SDMA 6.  |||
|**Fecha**:  |**Fecha**:  |||
|||||
|||||
|**Firma 7** |**Firma 8**  |||
|||||
|**Nombre**:**  Juan  Carlos  Ayuso Bautista.  |<p>` `**Nombre**: Angel  Horacio  López</p><p>Alcaraz. </p>|||
|**Puesto**:** Líder Técnico SDMA 6.  |**Puesto**:** Analista de Sistemas SDMA 6. |||
|**Fecha**:  |**Fecha**:  |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotaDeCredito.docx** |Versión del template: 7.00||

![](Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.036.png)
Página 24 de 24 

[ref1]: Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.008.png
[ref2]: Aspose.Words.cd9a2c7f-07af-4547-a187-85524fa9304c.028.png
