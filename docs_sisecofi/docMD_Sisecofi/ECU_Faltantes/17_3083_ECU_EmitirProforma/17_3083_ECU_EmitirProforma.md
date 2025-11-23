**Administración General de Comunicaciones ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.001.png)**

**y Tecnologías de la Información**

**Marco Documental 7.0**
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |

**<ID Requerimiento>** 8309** 

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación** 

**Tabla de Versiones y Modificaciones** 

|Versión |Descripción del cambio |Responsable de la Versión |Fecha |
| - | - | :-: | - |
|*1* |*Creación del documento* |Aylín de la Concepción Caballero Weng |*08/03/2024* |
|*1.1* |*Revisión del documento* |Luis Angel Olguin Castillo |*17/04/2024* |
|*1.2* |*Versión aprobada para firma* |Andrés Mojica Vázquez |*13/06/2024* |

**Tabla de Contenido** 

[17_3083_ECU_EmitirProforma ................................................................................................................... 2](#_page1_x82.00_y132.92)

1. [Descripción ........................................................................................................................................................ 2](#_page1_x116.00_y148.92)
1. [Diagrama del Caso de Uso ...................................................................................................................... 2](#_page1_x116.00_y237.92)
1. [Actores ................................................................................................................................................................. 2](#_page1_x116.00_y428.92)
1. [Precondiciones............................................................................................................................................... 2](#_page1_x116.00_y552.92)
1. [Post condiciones ........................................................................................................................................... 3](#_page2_x116.00_y195.92)
1. [Flujo primario .................................................................................................................................................. 3](#_page2_x116.00_y339.92)
1. [Flujos alternos .................................................................................................................................................8](#_page7_x116.00_y516.92)
1. [Referencias cruzadas............................................................................................................................... 27](#_page26_x116.00_y694.92)
1. [Mensajes .......................................................................................................................................................... 28](#_page27_x116.00_y145.92)
1. [Requerimientos No Funcionales ................................................................................................... 28 ](#_page27_x117.00_y540.92)[11.Diagrama de actividad ............................................................................................................................. 31](#_page30_x122.00_y133.92)
12. [Diagrama de estados .............................................................................................................................. 31](#_page30_x116.00_y706.92)
12. [Aprobación del cliente .......................................................................................................................... 32](#_page31_x116.00_y132.92)



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page1_x82.00_y132.92"></a>17\_3083\_ECU\_EmitirProforma  

1. **Descripción<a name="_page1_x116.00_y148.92"></a>  ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.002.png)**

El  objetivo  de  este  Caso  de  Uso,  es  permitir  al  Empleado  SAT  validar  el dictamen previamente cargado, generar una proforma y cargar el oficio de solicitud de factura.  

2. **Diagrama<a name="_page1_x116.00_y237.92"></a> del Caso de Uso ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.003.png)**

![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.004.jpeg)

3. **Actores<a name="_page1_x116.00_y428.92"></a>  ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.005.png)**



|**Actor** |**Descripción** |
| - | - |
|**Empleado SAT** |El  Empleado  SAT  es  el  que  tiene  el  o  los  roles otorgados  por  la  Administración  Central  de Seguridad,  Monitoreo  y  Control  (ACSMC),  para ingresar a cada uno de los módulos de este sistema.  |

4. **Precondiciones<a name="_page1_x116.00_y552.92"></a>** ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.006.png)
- El Empleado SAT se ha autenticado en el sistema con e.firma válida. 
- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa. 
- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar y editar el módulo “Consumo de Servicios-Dictamen” y a las secciones “Deducciones/descuentos/penalizaciones” y “Solicitud de factura”. 
- El empleado SAT ha ingresado a un dictamen de acuerdo con el **(17\_3083\_ECU\_GenerarDictamen)**. 
- El empleado SAT ha seleccionado alguna de las siguientes opciones: “Editar  dictamen”  o  “Ver  dictamen”  en  el  módulo  “Consumo  de Servicios” relacionados a un contrato. 
- El empleado SAT ha seleccionado una opción en el campo “Convenio de Colaboración” del contrato. ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.007.png)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



- El estatus del dictamen se encuentra en “Dictaminado”. ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.008.png)
- Se han registrado los tipos de plantillas en el catálogo relacionado. 
- Se ha registrado la plantilla activa de verificación para la carga de documentos en la sección documental. 
5. **Post<a name="_page2_x116.00_y195.92"></a> condiciones  ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.009.png)**
- El Empleado SAT: 
- Previsualizó la proforma asociada al dictamen. 
- Generó la proforma asociada al dictamen. 
- Modificó el estatus del dictamen a “Proforma”.  
- Visualizó  las  secciones “Deducciones/descuentos/penalizaciones”  y  “Solicitud  de factura” del módulo “Consumo de Servicios-Dictamen”. 
- Cambió el estatus del dictamen a “Inicial” (rechazar dictamen). 
6. **Flujo<a name="_page2_x116.00_y339.92"></a> primario** ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.010.png)![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.011.png)



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El Caso de Uso inicia cuando  el </p><p>Empleado  SAT  ingresa  en  la sección **“Deducciones/descuentos/pen alizaciones”**  del  módulo **“Consumo  de  Servicios  - Dictamen”**. </p><p>- En  caso  de  haber seleccionado  la  opción  **“Ver dictamen”**  en  el  módulo **“Consumo  de  Servicios”**, aplica  la  regla  de  negocio **(RNA167)**,  y  continúa  en  el flujo. </p><p>- Si  ingresa  en  la  sección **“Solicitud  de  factura”**  del módulo  **“Consumo  de Servicios  -Dictamen”**, continúa en el [**(FA10)**](#_page15_x116.00_y298.92). </p>|<p>2\.  Consulta  en  la  base  de  datos </p><p>(BD)  la  existencia  de información  para  esta  sección relacionada  a  las “Deducciones/descuentos/pen alizaciones”. </p>|
||<p>3\.  Muestra  la  sección </p><p>“Deducciones/descuentos/pen alizaciones”. Aplica las **(RNA51)** y **(RNA87)**. </p><p>Opciones:   ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.012.png)</p><p>- Nuevo registro  </p><p>- Actualizar tabla ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.013.png)</p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |

- Vista  previa  proforma ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.014.png)

  ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.015.png)

Tabla:  Aplica  las  **(RNA79)**, **(RNA94)** y **(RNA96)**. 

- No. 
- Tipo 
- Moneda  
- Monto 
- Acciones  
- Editar![ref1]
- Eliminar![ref2]

Opciones: Aplica las **(RNA246)**. 

- Cancelar 
- Guardar  
- Rechazado 
- Validar dictamen  
- Generar  proforma (oculto) 

Campo: 

- Detalle  de  penas  y deducciones 

Opciones: 

- Examinar 
- Ver. Aplica la **(RNA257)** 

Sección  “Solicitud  de  factura” (contraída) 

Ver  **(17\_3083\_EIU\_EmitirProforma)**  

Estilos 01. ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.018.png)

<a name="_page3_x121.00_y593.92"></a>4.  Realiza  una  de  las  siguientes  5.  Consulta en la BD la siguiente acciones:**  información  a  utilizar,  de 

acuerdo con la **(RNA01)**: 

- En caso de que seleccione la 

  opción  **“Nuevo  registro”**,**  el  o  Catálogo de los tipos de flujo continúa.**  deducciones, 

descuentos  o 

- En caso de que seleccione la  penalizaciones. Aplica la opción  **“Actualizar  tabla”**,  **(RNA94)** 

  continúa en el [**(FA02)**](#_page8_x116.00_y321.92).**  o  Tipo  de  moneda establecida  en  el 

- En caso de que seleccione la  contrato. 

  opción  **“Vista  previa** 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |

**proforma”**,  continúa  en  el ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.019.png)[**(FA03)**](#_page8_x116.00_y607.92).** 

- En caso de que seleccione la opción **“Editar”**, continúa en el [**(FA08)**](#_page13_x116.00_y643.92).** 
- En caso de que seleccione la opción  **“Eliminar”**,  continúa en el [**(FA09)**](#_page14_x116.00_y640.92).** 
- En caso de que seleccione la opción  **“Examinar”**,** correspondiente  al  campo **“Detalle  de  penas  y deducciones”**, continúa en el [**(FA05)**](#_page11_x116.00_y565.92).**  
- En caso de que seleccione la opción  **“Ver”** correspondiente  al  campo **“Detalle  de  penas  y deducciones”**, continúa en el [**(FA19)**](#_page26_x116.00_y283.92). 
- En caso de que seleccione la opción  **“Cancelar”**,  continúa en el **[**(FA14)**](#_page20_x116.00_y591.92)**. 
- En caso de que seleccione la opción  **“Guardar”**,  continúa en el** pas[o **12** ](#_page5_x315.00_y609.92)de este flujo. 
- En caso de que seleccione la opción  **“Rechazado”**, continúa en el [**(FA04)**](#_page10_x116.00_y434.92).** 
- En caso de que seleccione la opción  **“Validar  dictamen”**, continúa en el [**(FA12)**](#_page19_x116.00_y256.92).** 
- En caso de que seleccione la opción  **“Generar proforma”**, continúa en el [**(FA16)**](#_page22_x116.00_y164.92).** 
6. Agrega un nuevo registro a la tabla  y  muestra  los componentes  para  la  captura de  la  información  en  los siguientes  campos.  Aplica  la **(RNA94)**. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



- Tipo ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.020.png)
- Monto 
7. Muestra  en  la  columna “Acciones”**  las  siguientes opciones:  
- Descartar ![ref3]

Ver  **(17\_3083\_EIU\_EmitirProforma)**  

Estilos 05. ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.022.png)![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.023.png)

8. Carga la información leída en la BD en los siguientes campos: 
   1. Tipo=  muestra  el catálogo  de  tipo  de deducciones, descuentos  o penalizaciones.  
   1. Moneda=  muestra  el valor  del  tipo  de moneda. 
9. Ingresa<a name="_page5_x121.00_y412.92"></a>  la  información  en  los ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.024.png)campos: 
   1. Tipo 
   1. Monto 
   1. En caso de que seleccione la opción  **“Descartar”**  del registro  seleccionado continúa en el **[**(FA14)**](#_page20_x116.00_y591.92)**. 
10. Si no quiere realizar alguna otra acción continúa en el flujo.  
    1. En  caso  de  requerir  alguna 

       otra  acción  en  la  tabla 

       regresa al paso[` `**4** ](#_page3_x121.00_y593.92)de este flujo. 

11\.  Selecciona la opción **“Guardar”**. **<a name="_page5_x315.00_y609.92"></a>** 12.  Valida que se hayan ingresado 

todos los datos obligatorios de acuerdo  con  la  **(RNA95)**,  y continúa en el flujo. 

- En caso de que no se hayan ingresado  alguno  de  los datos obligatorios, continúa en el [**(FA06)**](#_page12_x116.00_y341.92). 

13\.  Valida  que  los  datos  cumplan 

con el formato de acuerdo con la  **(RNA255)**,  y  continúa  en  el flujo. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |

1. En caso de que alguno de ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.025.png)los datos no cumpla con el formato,  continúa  en  el [**(FA07)**](#_page13_x116.00_y281.92). 
14. Si existe un archivo en el campo “Detalle  de  penas  y deducciones” valida que sea un Excel  con  extensión  (.xlsx)  o PDF  con  extensión  (.pdf),  de acuerdo con la **(RNA153)**. 
    1. En  caso  de  que  el  archivo no  tenga  el  formato correcto,  continúa  en  el [**(FA18)**](#_page25_x116.00_y620.92). ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.026.png)
15. Almacena  en  la  BD  la información  de  las  Pistas  de Auditoría. 

    Datos que se almacenan:  

    **Módulo**=  Dictamen- Deducciones, descuentos y penalizaciones 

    **Fecha y Hora**= Fecha y hora del  sistema  usando  el formato  DD/MM/AAAA HH:MM:SS 

    **RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema. 

    **Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar), **DLT**  (Borrar)  según corresponda 

    **Movimiento**=  Aplica  la **(RNA239)**  

- id del dictamen 
- número del registro de la tabla 
- documento cargado 
- En  caso  de  que  no  se puedan almacenar las Pistas de Auditoría, continúa en el [**(FA01)**](#_page7_x116.00_y556.92). 
16. Identifica  el  tipo  de movimiento  realizado  y almacena en la BD la siguiente información  que  corresponda con  lo  capturado:  Aplica  la **(RNA247)**. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>- No. </p><p>- Tipo </p><p>- Moneda </p><p>- Monto </p><p>- Archivo correspondiente  al detalle  de  penas  y deducciones  (en  caso de  tener  aplica  la **(RNA38)**). </p>|
| :- | - |
||<p>17\.  Muestra  el  **[**(MSG011)**](#_page27_x127.00_y439.92)**  con  la </p><p>opción “Aceptar”. </p>|
|18\.  Selecciona la opción **“Aceptar”**. |19\.  Cierra el mensaje.  |
||<p>20\. Muestra  la  sección  con  los </p><p>campos  actualizados  de acuerdo  con  los  movimientos realizados. Aplica las **(RNA250)**, **(RNA94)**. </p><p>￿  En  caso  de  que  se  hayan realizado  movimientos  de inserción  o  actualización, deshabilita la edición de los campos  en  la  tabla  y muestra  en  la  columna “Acciones”  las  opciones </p><p>Editar ![ref4]y Eliminar ![ref5]. </p>|
||21\.  Fin del Caso de Uso.  |

7. **Flujos<a name="_page7_x116.00_y516.92"></a> alternos  ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.029.png)**

<a name="_page7_x116.00_y556.92"></a>**FA01 No se pueden almacenar las pistas de auditoría** 



|**Actor** |**Sistema** |||
| - | - | :- | :- |
||<p>1\.  El  **FA01**  inicia  cuando </p><p>interviene un evento ajeno y no se pueden almacenar las pistas de auditoría.**  </p>|||
||<p>2\.  Cancela  la  operación  sin </p><p>completar el movimiento que estaba en proceso. </p>|||
||<p>3\.  Muestra  una  ventana </p><p>emergente con el mensaje de acuerdo con lo siguiente: </p><p>￿  Si  la  pista  de  auditoría  es por el tipo de movimiento </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



||<p>**UPDT**,  **INSR**  o  **DLT**  se muestra el [**(MSG002)**](#_page27_x127.00_y224.92).** </p><p>￿  Si  la  pista  de  auditoría  es </p><p>por el tipo de movimiento **PRNT**,  se  muestra  el [**(MSG003)**](#_page27_x127.00_y249.92).** </p><p>Cada mensaje se muestra con la opción “Aceptar”. </p>|
| :- | :- |
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje. |
||<p>6\.  Regresa  al  paso  previo  que </p><p>detona la acción de la pista de auditoría. </p>|

<a name="_page8_x116.00_y321.92"></a>**FA02  Selecciona  la  opción  “Actualizar  tabla”  de  la  sección “Deducciones/descuentos/penalizaciones”** 



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA02**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción **“Actualizar tabla”**. </p>|<p>2\.  Consulta  en  la  BD  la  siguiente </p><p>información a utilizar:  </p><p>o  Información  registrada </p><p>de  las  penas contractuales,  penas convencionales, deducciones  y  registros capturados.  </p>|
||<p>3\.  Actualiza  los  registros  de  la </p><p>tabla,  calculando  los  montos asociados  a  las  penas contractuales,  penas convencionales  y  deducciones de  acuerdo  con  las  **(RNA96)**  y **(RNA250)**. </p>|
||<p>4\.  Regresa  al  paso [` `**4** ](#_page3_x121.00_y593.92) del  Flujo </p><p>primario. </p>|

<a name="_page8_x116.00_y607.92"></a>**FA03 Selecciona la opción “Vista previa proforma”** 



|**Actor** |**Sistema** |||
| - | - | :- | :- |
|1\.  El  **FA03**  inicia  cuando  el Empleado  SAT  selecciona  la opción **“Vista previa proforma”**. |<p>2\.  Consulta  en  la  BD  la  siguiente </p><p>información a utilizar:  </p><p>- Información  registrada de  los  servicios dictaminados </p><p>- Catálogo  de  tipos  de plantillas </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



||<p>- Información general del dictamen </p><p>- Información  del proveedor  asociado  al dictamen </p><p>- Identificador  del proyecto  asociado  al contrato  </p><p>- Información del contrato asociado al dictamen </p><p>- Información  registrada de  las  deducciones, descuentos  y penalizaciones </p><p>- Datos  del  responsable de  la  verificación  del contrato </p><p>- Opción  que  se encuentre  activa  en  el catálogo  “Acuerdo  de pago”.  </p>|||
| :- | :- | :- | :- |
||<p>3\.  Realiza el cálculo del total de las </p><p>deducciones  y  el  total  de  las penalizaciones de acuerdo con la **(RNA135)**. </p>|||
||<p>4\.  Muestra  la ventana emergente </p><p>“Vista  previa  proforma”  con  la siguiente información. Aplica la **(RNA01)**. </p><p>- Factura  proforma= muestra  el  identificador de  la  proforma  que corresponde  con  el identificador  del dictamen. </p><p>- Tipo  de  plantilla\*= muestra  el  catálogo  de tipos de plantillas. </p><p>- Panel  de  visualización= muestra  la  información de la proforma. </p><p>Opciones </p><p>- Previsualizar </p><p>- Aceptar ![ref6]</p><p>- Cerrar </p><p>Ver **(17\_3083\_EIU\_EmitirProforma)** Estilos 02. </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



|<p><a name="_page10_x122.00_y133.92"></a>5.  Ingresa  la  información  en  el </p><p>campo: </p><p>o  Tipo de plantilla\* </p>||
| - | :- |
|<p>6\.  Selecciona  la  opción </p><p>**“Previsualizar”**, y continúa en el flujo. </p><p>- En caso de que seleccione la opción  **“Aceptar”**,  continúa en el pas[o **10** ](#_page10_x311.00_y385.92)de este flujo. </p><p>- En caso de que seleccione la opción  **“Cerrar”**,  continúa en el pas[o **10** ](#_page10_x311.00_y385.92)de este flujo. </p>|<p>7\.  Valida que se hayan ingresado </p><p>todos  los  datos  obligatorios. Aplica la **(RNA03)**. </p><p>￿  En caso de que no se haya </p><p>ingresado  alguno  de  los datos obligatorios, continúa en el [**(FA06)**](#_page12_x116.00_y341.92). </p>|
||<p>8\.  Muestra  en  el  panel  de </p><p>visualización  la  información  de la proforma (aplica la **(RNA134)**) de  acuerdo  con  el  tipo  de plantilla seleccionada. </p>|
|9\.  Selecciona la opción **“Aceptar”**. |<a name="_page10_x311.00_y385.92"></a>10.  Cierra la ventana emergente. |
||<p>11\.  Regresa  al  paso [` `**4** ](#_page3_x121.00_y593.92) del  Flujo </p><p>primario. </p>|

<a name="_page10_x116.00_y434.92"></a>**FA04 Selecciona la opción “Rechazado”** 



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA04**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción **“Rechazado”**. </p>|<p>2\.  Muestra  el  **[**(MSG004)**](#_page27_x127.00_y269.92)**  con  las </p><p>opciones “Sí” y “No”. </p>|
|<p>3\.  Selecciona  la  opción  **“Sí”**  y </p><p>continúa en el flujo. </p><p>￿  En caso de que seleccione </p><p>la  opción  **“No”**,  regresa  al paso[` `**4** ](#_page3_x121.00_y593.92)del Flujo primario. </p>|<p>4\.  Muestra  la  ventana  emergente </p><p>“Justificación” con las siguientes opciones: </p><p>- Justificación </p><p>Opciones: </p><p>- Aceptar </p><p>- Cerrar </p><p>Ver  **(17\_3083\_EIU\_EmitirProforma)** Estilos 06. </p>|
|<p>5\.  Agrega  la  justificación  del </p><p>rechazo de dictamen. </p>||
|<p>6\.  Selecciona  la  opción </p><p>**“Aceptar”**  y  continúa  en  el flujo. </p>|<p>7\.  Almacena  en  la  BD  la </p><p>información  de  las  Pistas  de Auditoría. </p><p>Datos que se almacenan:  </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



|￿ |En caso de que seleccione la opción **“Cerrar”**, regresa al pas[o **4** ](#_page3_x121.00_y593.92)del Flujo primario. |<p>**Módulo**=  Dictamen- Deducciones,  descuentos  y penalizaciones </p><p>**Fecha y Hora**= Fecha y hora del  sistema  usando  el formato  DD/MM/AAAA HH:MM:SS </p><p>**RFC Usuario**=**  RFC largo del Empleado SAT que ingresó al sistema. </p><p>**Tipo de movimiento**= **UPDT** (Modificar) </p><p>**Movimiento**=  Aplica  la **(RNA239)** </p><p>- identificador  del dictamen </p><p>- estatus del dictamen  </p><p>￿  En caso de que no se puedan </p><p>almacenar  las  Pistas  de Auditoría,  continúa  en  el [**(FA01)**](#_page7_x116.00_y556.92). </p>|
| - | - | - |
|||<p>8\.  Actualiza en la BD el estatus del </p><p>dictamen  a  “Inicial”.  Aplica  las **(RNA247)** y **(RNA87)**. </p>|
|||<p>9\.  Concatena  el  texto  “Motivo  del </p><p>rechazo:”,  la  justificación previamente  capturada,  el carácter pipe (|) y la “Descripción”, en el campo “Descripción” de la sección “Datos generales”. </p>|
|||10\.  Cierra el mensaje. |
|||11\.  Recarga la pantalla. |
|||12\.  Fin del Caso de Uso. |

<a name="_page11_x116.00_y565.92"></a>**FA05 Selecciona la opción “Examinar”** 



|**Actor** |**Sistema** |||
| - | - | :- | :- |
|<p>1\.  El  **FA05**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción  **“Examinar”**, correspondiente  al  campo **“Detalle  de  penas  y deducciones”**  o  al  campo **“Cargar oficio\*”**.** </p>|<p>2\.  Valida que previamente no se </p><p>encuentre cargado un archivo en el campo “Detalle de penas y deducciones” o en el campo “Cargar oficio\*”. </p><p>￿  En  caso  de  que  se encuentre  cargado  un archivo  en  el  campo “Detalle  de  penas  y deducciones”  o  en  el </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



||campo  “Cargar  oficio\*”, continúa en el [**(FA17)**](#_page25_x116.00_y372.92). |
| :- | :- |
||<p><a name="_page12_x316.00_y162.92"></a>3.  Abre el gestor de archivos del </p><p>equipo  de  cómputo  del Empleado SAT. </p><p>￿  En caso de que se invoque </p><p>desde  la  sección “Deducciones/descuentos/ penalizaciones”  aplica  la **(RNA153)**. </p>|
|4\.  Selecciona el archivo. |<p>5\.  Muestra  el  archivo  que  se </p><p>seleccionó  en  el  campo correspondiente. </p>|
||<p>6\.  Continúa en el paso donde fue </p><p>invocado. </p>|

<a name="_page12_x116.00_y341.92"></a>**FA06  El  sistema  identifica  que  no  se  han  ingresado  todos  los  datos obligatorios** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El **FA06** inicia cuando el sistema </p><p>identifica  que  no  se  han ingresado  todos  los  datos obligatorios. </p>|
||<p>2\.  Muestra  el  **[**(MSG005)**](#_page27_x127.00_y286.92)**  con  la </p><p>opción “Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje.  |
||<p>5\.  Se muestran en rojo los campos </p><p>pendientes de capturar. </p>|
||<p>6\.  Realiza lo siguiente: </p><p>- Si fue invocado en el paso 12 del Flujo primario, regresa al paso[` `**9** ](#_page5_x121.00_y412.92)de dicho flujo. </p><p>- Si fue invocado en el [**(FA03)**](#_page8_x116.00_y607.92), paso  7,  regresa  al  **[**(FA03)**](#_page8_x116.00_y607.92)**, paso[` `**5**.](#_page10_x122.00_y133.92) </p><p>- Si fue invocado en el [**(FA10)**](#_page15_x116.00_y298.92), paso  6,  regresa  al  **[**(FA10)**](#_page15_x116.00_y298.92)**, paso[` `**4**.](#_page16_x122.00_y145.92) </p><p>- Si fue invocado en el [**(FA11)**](#_page18_x116.00_y158.92), paso  2,  regresa  al  **[**(FA10)**](#_page15_x116.00_y298.92)**, paso[` `**4**.](#_page16_x122.00_y145.92) </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



|||￿ ￿ ￿ |<p>Si fue invocado en el [**(FA12)**](#_page19_x116.00_y256.92), paso 3, regresa al paso[` `**4** ](#_page3_x121.00_y593.92)del Flujo primario. </p><p>Si fue invocado en el [**(FA16)**](#_page22_x116.00_y164.92), paso  7,  regresa  al  **[**(FA16)**](#_page22_x116.00_y164.92)**, paso[` `**5**.](#_page23_x125.00_y379.92) </p><p>Si fue invocado en el [**(FA16)**](#_page22_x116.00_y164.92), paso  10,  regresa  al  **[**(FA16)**](#_page22_x116.00_y164.92)**, paso[` `**5**.](#_page23_x125.00_y379.92) </p>|
| :- | :- | - | - |

<a name="_page13_x116.00_y281.92"></a>**FA07 El sistema identifica que los datos no cumplen con el formato** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El **FA07** inicia cuando el sistema </p><p>identifica  que  los  datos ingresados  no  cumplen  con  el formato  de  acuerdo  con  la **(RNA94)**. </p>|
||<p>2\.  Muestra  el  **[**(MSG006)**](#_page27_x127.00_y306.92)**  con  la </p><p>opción “Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje.  |
||<p>5\.  Se muestran en rojo los campos </p><p>que no cumplen con el formato esperado. </p>|
||<p>6\.  Realiza lo siguiente: </p><p>- Si fue invocado en el paso 13 del Flujo primario, regresa al paso[` `**9** ](#_page5_x121.00_y412.92)de dicho flujo. </p><p>- Si fue invocado en el **[**(FA10)**](#_page15_x116.00_y298.92)** paso  7,  regresa  al  **[**(FA10)**](#_page15_x116.00_y298.92)** paso[` `**4**.](#_page16_x122.00_y145.92) </p><p>- Si fue invocado en el **[**(FA11)**](#_page18_x116.00_y158.92)** paso  3,  regresa  al  **[**(FA10)**](#_page15_x116.00_y298.92)**, paso[` `**4**.](#_page16_x122.00_y145.92) </p>|

<a name="_page13_x116.00_y643.92"></a>**FA08 Selecciona la opción “Editar”** 



|**Actor** |**Sistema** |||
| - | - | :- | :- |
|<p>1\.  El  **FA08**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción  **“Editar”**  de  algún registro de la tabla. </p>|<p>2\.  Consulta  en  la  BD  la  siguiente </p><p>información  a  utilizar,  de acuerdo con la **(RNA01)**:  </p><p>o  Catálogo de los tipos de </p><p>deducciones, </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



||<p>descuentos  o penalizaciones </p><p>o  Tipo  de  moneda </p><p>establecida  en  el contrato. </p>|
| :- | :- |
||<p>3\.  Habilita en la tabla la edición de </p><p>los  siguientes  campos, mostrando  los  componentes para  la  captura  de  la información. </p><p>- Tipo </p><p>- Monto </p>|
||<p>4\.  Cambia  las  opciones  de  la </p><p>columna  “Acciones”**  por  las siguientes:  </p><p>o  Descartar ![ref3]</p><p>Ver  **(17\_3083\_EIU\_EmitirProforma)** Estilos 05. </p>|
||<p>5\.  Carga la información leída de la BD en los siguientes campos: </p><p>o  Tipo  =  muestra  el </p><p>catálogo de los tipos de deducciones, descuentos  o penalizaciones. </p>|
|<p>6\.  Modifica  la  información  que </p><p>requiera de los campos: </p><p>- Tipo </p><p>- Monto </p><p>￿  En caso de que seleccione </p><p>la  opción  **“Descartar”**  del registro  seleccionado continúa en el **[**(FA14)**](#_page20_x116.00_y591.92)**. </p>|<p>7\.  Continúa en el paso [**4** ](#_page3_x121.00_y593.92)del Flujo </p><p>primario. </p>|

<a name="_page14_x116.00_y640.92"></a>**FA09 Selecciona la opción “Eliminar”** 



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA09**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción  **“Eliminar”**  de  algún registro de la tabla. </p>|<p>2\.  Muestra  el  **[**(MSG007)**](#_page27_x127.00_y331.92)**  con  las </p><p>opciones “Sí” y “No”. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



|<p>3\.  Selecciona la opción **“No”** y el </p><p>flujo continúa. </p><p>￿  En  el  caso  de  que </p><p>seleccione  la  opción  **“Sí”**,** continúa  en  el  paso [` `**5** ](#_page15_x304.00_y219.92) de este flujo. </p>|<p>4\.  Cierra  el  mensaje,  no  realiza </p><p>ningún movimiento y continúa en el pas[o **4** ](#_page3_x121.00_y593.92)del Flujo primario. </p>|
| - | - |
||<p><a name="_page15_x304.00_y219.92"></a>5.  Elimina el registro seleccionado </p><p>de la tabla que se muestra en la pantalla. Aplica la **(RNA250)**.  </p>|
||<p>6\.  Continúa en el paso [**4** ](#_page3_x121.00_y593.92)del Flujo </p><p>primario. </p>|

<a name="_page15_x116.00_y298.92"></a>**FA10 Ingresa en la sección “Solicitud de factura”** 



|**Actor** |**Sistema** |||
| - | - | :- | :- |
|<p>1\.  El  **FA10**  inicia  cuando  el </p><p>Empleado  SAT  ingresa  en  la sección **“Solicitud de factura”** del  módulo  **“Consumo  de Servicios -Dictamen”**. </p>|<p>2\.  Consulta en la BD la existencia </p><p>de  información  para  esta sección  relacionada  a  la “Solicitud de factura”.** </p>|||
||<p>3\.  Muestra la sección “Solicitud de </p><p>factura”  con  la  siguiente información. Aplica las **(RNA87)** y **(RNA51)**. </p><p>Campos: </p><p>- Número  de  oficio  de solicitud factura\* </p><p>- Fecha  de  solicitud factura\* </p><p>- Cargar oficio\* </p><p>- Fecha  de  recepción factura\*.  Aplica  la **(RNA260)** </p><p>- Facturas  recibidas. Aplica la **(RNA260)** </p><p>Opciones: Aplica las **(RNA246)** </p><p>y **(RNA260)** </p><p>- Examinar </p><p>- Ver. Aplica la **(RNA257)** </p><p>- Recepción  de  factura</p><p>&emsp;![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.031.png)</p><p>- Cancelar </p><p>- Guardar  </p><p>Ver **(17\_3083\_EIU\_EmitirProforma )**  </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



||Estilos 03. |
| :- | - |
|<p><a name="_page16_x122.00_y145.92"></a>4.  Ingresa  la  información  en  los </p><p>campos: </p><p>- Número  de  oficio  de solicitud factura\* </p><p>- Fecha  de  solicitud factura\* </p><p>- Fecha  de  recepción factura\*  </p>||
|<p>5\.  Realiza  una  de  las  siguientes </p><p>acciones: </p><p>- En caso de que seleccione la opción **“Guardar”**, el flujo continúa. </p><p>￿ </p><p>En caso de que seleccione la  opción  **“Cancelar”**, continúa en el [**(FA14)**](#_page20_x116.00_y591.92). </p><p>￿ </p><p>En caso de que seleccione la  opción  **“Examinar”**, correspondiente  al  campo **“Cargar  oficio\*”**,  continúa en el [**(FA05)**](#_page11_x116.00_y565.92). </p><p>- En caso de que seleccione la  opción  **“Ver”** correspondiente  al  campo **“Cargar  oficio\*”**,  continúa en el [**(FA19)**](#_page26_x116.00_y283.92). </p><p>- En caso de que seleccione la  opción  **“Recepción  de factura”**,**  continúa  en  el [**(FA11)**](#_page18_x116.00_y158.92). </p>|<p>6\.  Valida que se hayan ingresado </p><p>todos  los  datos  obligatorios. Aplica la **(RNA03)**. </p><p>￿  En caso de que no se haya </p><p>ingresado  alguno  de  los datos  obligatorios, continúa en el [**(FA06)**](#_page12_x116.00_y341.92). </p>|
||<p>7\.  Valida que los datos cumplan </p><p>con el formato de acuerdo con la **(RNA255)**. </p><p>￿  En caso de que alguno de </p><p>los datos no cumpla con el formato,  continúa  en  el [**(FA07)**](#_page13_x116.00_y281.92). </p>|
||<p>8\.  Si  existe  un  archivo  en  el </p><p>campo  “Cargar  oficio\*”  valida que sea un PDF con extensión (.pdf). </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>￿  En caso de que el archivo </p><p>no  tenga  el  formato correcto,  continúa  en  el [**(FA18)**](#_page25_x116.00_y620.92). </p>|
| :- | - |
||<p>9\.  Almacena  en  la  BD  la </p><p>información  de  las  Pistas  de Auditoría. </p><p>Datos que se almacenan: </p><p>**Módulo**=  Dictamen- Solicitud de Factura </p><p>**Fecha y Hora**= Fecha y hora del  sistema  usando  el formato  DD/MM/AAAA HH:MM:SS </p><p>**RFC Usuario**= RFC largo del Empleado SAT que ingresó al sistema. </p><p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar) según corresponda **Movimiento**=  Aplica  la **(RNA239)** </p><p>- id del dictamen </p><p>- número  de  oficio  de solicitud de factura </p><p>- documento cargado </p><p>￿  En  caso  de  que  no  se </p><p>puedan almacenar las Pistas de Auditoría, continúa en el [**(FA01)**](#_page7_x116.00_y556.92). </p>|
||<p>10\.  Almacena en la BD la siguiente </p><p>información  que  corresponda con  lo  capturado:  Aplica  la **(RNA247)**. </p><p>- Número  de  oficio  de solicitud de factura\* </p><p>- Fecha  de  solicitud factura\* </p><p>- Archivo correspondiente  al oficio de la solicitud de factura  (en  caso  de tener aplica la **(RNA38)**). </p>|
||<p>11\.  Muestra  el  **[**(MSG011)**](#_page27_x127.00_y439.92)**  con  la </p><p>opción “Aceptar”. </p>|
|12\.  Selecciona la opción **“Aceptar”**. |13\.  Cierra el mensaje.  |
||<p>14\.  Muestra  la  sección  con  los </p><p>campos actualizados. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||15\.  Fin del Caso de Uso. |
| :- | - |

<a name="_page18_x116.00_y158.92"></a>**FA11 Selecciona la opción “Recepción de factura”** 



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA11**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción  **“Recepción  de factura”**.** </p>|<p>2\.  Valida que se hayan ingresado </p><p>todos  los  datos  obligatorios. Aplica la **(RNA03)**. </p><p>￿  En caso de que no se haya </p><p>ingresado  alguno  de  los datos  obligatorios, continúa en el [**(FA06)**](#_page12_x116.00_y341.92). </p>|
||<p>3\.  Valida que los datos cumplan </p><p>con el formato de acuerdo con la **(RNA255)**. </p><p>￿  En caso de que alguno de </p><p>los datos no cumpla con el formato,  continúa  en  el [**(FA07)**](#_page13_x116.00_y281.92). </p>|
||<p>4\.  Valida que la fecha capturada </p><p>en  el  campo  “Fecha  de recepción factura” sea correcta de acuerdo con la **(RNA133)**. </p><p>￿  En  caso  de  que  la  fecha </p><p>capturada no sea correcta, continúa en el [**(FA13)**](#_page20_x116.00_y394.92). </p>|
||<p>5\.  Almacena  en  la  BD  la </p><p>información  de  las  Pistas  de Auditoría. </p><p>Datos que se almacenan:  </p><p>**Módulo**=  Dictamen- Solicitud de factura </p><p>**Fecha y Hora**= Fecha y hora del  sistema  usando  el formato  DD/MM/AAAA HH:MM:SS </p><p>**RFC Usuario**=** RFC largo del Empleado SAT que ingresó al sistema. </p><p>**Tipo  de  movimiento**= **UPDT** (Modificar) **Movimiento**=  Aplica  la **(RNA239)** </p><p>- id del dictamen </p><p>- recepción de facturas </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>￿  En  caso  de  que  no  se </p><p>puedan  almacenar  las Pistas  de  Auditoría, continúa en el [**(FA01)**](#_page7_x116.00_y556.92). </p>|
| :- | - |
||<p>6\.  Guarda  en  la  BD  el  campo </p><p>Facturas  recibidas.  Aplica  las **(RNA247)** y **(RNA87)**. </p>|
||7\.  Fin del Caso de Uso. |

<a name="_page19_x116.00_y256.92"></a>**FA12 Selecciona la opción “Validar dictamen”** 



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA12**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción **“Validar dictamen”**.** </p>|<p>2\.  Valida que se hayan ingresado </p><p>todos los datos obligatorios de las  secciones  “Penas convencionales”,  “Penas contractuales”  y “Deducciones”.  </p><p>￿  En caso de que no se haya </p><p>ingresado  alguno  de  los datos  obligatorios, continúa  en  la  sección correspondiente  del **(17\_3083\_ECU\_RegistrarSe rviciosDictaminados)**. </p>|
||<p>3\.  Valida que se hayan ingresado </p><p>todos los datos obligatorios de acuerdo  con  las  **(RNA94)**  y **(RNA132)**.  </p><p>￿  En caso de que no se haya </p><p>ingresado  alguno  de  los datos  obligatorios, continúa en el [**(FA06)**](#_page12_x116.00_y341.92). </p>|
||<p>4\.  Almacena  en  la  BD  la </p><p>información  de  las  Pistas  de Auditoría. </p><p>Datos que se almacenan: </p><p>**Módulo**= Dictamen- Datos generales </p><p>**Fecha y Hora**= Fecha y hora del  sistema  usando  el formato  DD/MM/AAAA HH:MM:SS </p><p>**RFC Usuario**=** RFC largo del Empleado SAT que ingresó al sistema. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>**Tipo de movimiento**= **INSR** (Insertar), **UPDT** (Modificar) **Movimiento**=  Aplica  la **(RNA239)** </p><p>- id del dictamen </p><p>- estatus proforma </p><p>￿  En  caso  de  que  no  se </p><p>puedan  almacenar  las Pistas  de  Auditoría, continúa en el [**(FA01)**](#_page7_x116.00_y556.92). </p>|
| :- | - |
||<p>5\.  Actualiza en la BD el estatus del </p><p>dictamen  a  “Proforma”  de acuerdo  con  las  **(RNA87)**  y **(RNA247)**. </p>|
||<p>6\.  Muestra  el  mensaje  **[**(MSG001)**](#_page27_x127.00_y201.92)** </p><p>con la opción “Aceptar”. </p>|
|7\.  Selecciona la opción **“Aceptar”**. |8\.  Cierra el mensaje. |
||9\.  Recarga la pantalla. |
||10\.  Fin del Caso de Uso. |

<a name="_page20_x116.00_y394.92"></a>**FA13 El sistema identifica que la fecha de recepción ingresada no es correcta** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El **FA13** inicia cuando el sistema </p><p>identifica  que  la  fecha  de recepción  ingresada  no cumple con la **(RNA133)**. </p>|
||<p>2\.  Muestra  el  **[**(MSG008)**](#_page27_x127.00_y362.92)**  con  la </p><p>opción “Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje.  |
||5\.  Regresa al [**(FA10)**](#_page15_x116.00_y298.92), pas[o **4**.](#_page16_x122.00_y145.92) |

<a name="_page20_x116.00_y591.92"></a>**FA14 Selecciona la opción “Cancelar”, “Cerrar” o “Descartar”** 



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA14**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción  **“Cancelar”**,  **“Cerrar”**  o **“Descartar”** . </p>|<p>2\.  Muestra  el  **[**(MSG009)**](#_page27_x127.00_y393.92)**  con  las </p><p>opciones “Sí” y “No”. </p>|
|<p>3\.  Selecciona  la  opción  **“No”**  y  el </p><p>flujo continúa. </p><p>￿  En caso de que seleccione la </p><p>opción  **“Sí”**,  continúa  en  el pas[o **5** ](#_page21_x311.00_y133.92)de este flujo. </p>|<p>4\.  Cierra  el  mensaje  y  continúa </p><p>en el pas[o **6** ](#_page21_x311.00_y561.92)de este flujo. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p><a name="_page21_x311.00_y133.92"></a>5.  Cierra  el  mensaje  y  realiza  lo </p><p>siguiente: </p><p>- Si es invocado en la opción “Descartar”: </p><p>- Si  es  un  registro almacenado,  inicializa el registro de la tabla y cambia  a  solo  lectura regresando los íconos a su  estado  original. Regresa  al  paso [` `**4** ](#_page3_x121.00_y593.92) del Flujo primario. </p><p>- Si es un registro nuevo elimina la fila. Regresa al  paso [` `**4** ](#_page3_x121.00_y593.92) del  Flujo primario. </p><p>- Si es invocado en la opción “Cerrar”: </p><p>- Cierra  la  ventana emergente  y  no almacena  ninguna información.  Regresa al  paso [` `**4** ](#_page3_x121.00_y593.92) del  Flujo primario. </p><p>- Si es invocado en la opción “Cancelar”:  </p><p>- No almacena ninguna información  y  recarga la  pantalla.  Regresa  al paso  **[**4** ](#_page3_x121.00_y593.92)** del  Flujo primario. </p>|
| :- | - |
||<p><a name="_page21_x311.00_y561.92"></a>6.  Regresa  al  paso  donde  fue </p><p>invocado. </p>|

<a name="_page21_x116.00_y598.92"></a>**FA15 No se puede generar el archivo PDF o Excel** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA15**  inicia  cuando </p><p>interviene  un  evento  ajeno  y no se puede generar el archivo PDF con extensión (.pdf) o el archivo  Excel  con  extensión (.xlsx). </p>|
||<p>2\.  Cancela  la  operación  y </p><p>muestra  el  **[**(MSG010)**](#_page27_x127.00_y418.92)**  con  la opción “Aceptar”. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
| - | - |
||5\.  Regresa al [**(FA16)**](#_page22_x116.00_y164.92), paso[` `**6**.](#_page23_x125.00_y441.92) |

<a name="_page22_x116.00_y164.92"></a>**FA16 Selecciona la opción “Generar proforma”** 



|**Actor** |**Sistema** |||
| - | - | :- | :- |
|<p>1\.  El  **FA16**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción **“Generar proforma”**. </p>|<p>2\.  Consulta en la BD la siguiente </p><p>información a utilizar:  </p><p>- Catálogo  de  tipos  de plantillas </p><p>- Información registrada de  los  servicios dictaminados </p><p>- Información  general del dictamen </p><p>- Información  del proveedor  asociado  al contrato del dictamen </p><p>- Identificador  del proyecto  asociado  al contrato </p><p>- Información  del contrato  asociado  al dictamen </p><p>- Información registrada de  las  deducciones, descuentos  y penalizaciones </p><p>- Datos del responsable de  la  verificación  del contrato. </p><p>- Opción  que  se encuentre activa en el catálogo  “Acuerdo  de pago”. </p>|||
||<p>3\.  Realiza el cálculo del total de las </p><p>deducciones  y  el  total  de  las penalizaciones de acuerdo con la **(RNA135)**. </p>|||
||<p>4\.  Muestra la ventana emergente </p><p>“Generar  proforma”  con  la siguiente información. Aplica la **(RNA01)**. </p><p>o  Factura  proforma= </p><p>muestra el identificador de  la  proforma  que corresponde  con  el identificador  del dictamen. </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



||<p>- Tipo  de  plantilla\*= muestra el catálogo de tipos de plantillas. </p><p>- Formato  para exportar\*=  muestra  las opciones  de  selección “PDF” y “Excel”. </p><p>- Panel  de  visualización= muestra la información de la proforma. </p><p>Opciones </p><p>- Previsualizar </p><p>- Aceptar </p><p>- Cancelar </p><p>- Cerrar ![ref6]</p><p>Ver **(17\_3083\_EIU\_EmitirProforma)**  Estilos 04. </p>|
| :- | - |
|<p><a name="_page23_x125.00_y379.92"></a>5.  Ingresa  la  información  en  los </p><p>campos: </p><p>- Tipo de plantilla\* </p><p>- Formato para exportar\* </p>||
|<p><a name="_page23_x125.00_y441.92"></a>6.  Selecciona  la  opción </p><p>**“Previsualizar”**,**  y  continúa  en el flujo. </p><p>- En caso de que seleccione la  opción  **“Aceptar”**, continúa  en  el  paso [` `**10** ](#_page23_x311.00_y724.92) de este flujo. </p><p>- En caso de que seleccione la  opción  **“Cancelar”**, continúa en el [**(FA14)**](#_page20_x116.00_y591.92). </p><p>- En caso de que seleccione la opción **“Cerrar”**, continúa en el [**(FA14)**](#_page20_x116.00_y591.92). </p>|<p>7\.  Valida que se hayan ingresado </p><p>todos los datos obligatorios de acuerdo con la **(RNA03)**. </p><p>￿  En caso de que no se haya </p><p>ingresado  alguno  de  los datos obligatorios, continúa en el [**(FA06)**](#_page12_x116.00_y341.92). </p>|
||<p>8\.  Muestra  en  el  panel  de </p><p>visualización la información de la proforma (aplica la **(RNA134)**) de  acuerdo  con  el  tipo  de plantilla seleccionada. </p>|
||<p>9\.  Continúa en el paso [**6** ](#_page23_x125.00_y441.92)de este </p><p>flujo. </p>|
||<p><a name="_page23_x311.00_y724.92"></a>10.  Valida que se hayan ingresado </p><p>todos los datos obligatorios de acuerdo con la **(RNA03)**. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>￿  En caso de que no se haya </p><p>ingresado  alguno  de  los datos obligatorios, continúa en el [**(FA06)**](#_page12_x116.00_y341.92). </p>|
| :- | - |
||<p>11\.  Almacena  en  la  BD  la </p><p>información  de  las  Pistas  de Auditoría. </p><p>Datos que se almacenan: </p><p>**Módulo**=  Dictamen- Solicitud de Factura </p><p>**Fecha y Hora**= Fecha y hora del  sistema,  usando  el formato  DD/MM/AAAA HH:MM:SS </p><p>**RFC Usuario**=** RFC largo del Empleado SAT que ingresó al sistema. </p><p>**Tipo  de  movimiento**= **PRNT** (Imprimir) **Movimiento**=  Aplica  la **(RNA239)** </p><p>- identificador  de  la proforma </p><p>- identificador  del dictamen </p><p>￿  En  caso  de  que  no  se </p><p>puedan  almacenar  las Pistas  de  Auditoría, continúa en el [**(FA01)**](#_page7_x116.00_y556.92). </p>|
||<p>12\.  El sistema tendrá en cuenta la </p><p>selección del tipo de plantilla y el  formato  para  exportar,  y realizará una de las siguientes acciones: </p><p>-  Si  el  Empleado  SAT </p><p>seleccionó la opción de formato  para  exportar “PDF”,  el  sistema generará  un  archivo PDF  con  extensión (.pdf)  en  el  cual  se visualizará  la información  teniendo en cuenta las **(RNA134)** y **(RNA141)**. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>-  Si  el  Empleado  SAT </p><p>seleccionó la opción de formato  para  exportar “Excel”,  el  sistema generará  un  archivo Excel  con  extensión (.xlsx)  para  visualizar  la información  teniendo en cuenta las **(RNA134)** y **(RNA141)**. </p><p>￿  En  caso  de  que  no  se </p><p>puedan  generar  los archivos,  continúa  en  el [**(FA15)**](#_page21_x116.00_y598.92). </p>|
| :- | - |
||<p>13\.  Realiza la descarga del archivo </p><p>en el ordenador. </p>|
||14\.  Fin del Caso de Uso. |

<a name="_page25_x116.00_y372.92"></a>**FA17 El sistema identifica que se encuentra cargado un archivo en el campo**  



|**Actor** |**Sistema** |
| - | - |
||1\.  El **FA17** inicia cuando el sistema identifica  que  se  encuentra cargado un archivo en el campo. |
||<p>2\.  Muestra  el  **[**(MSG012)**](#_page27_x127.00_y459.92)**  con  las </p><p>opciones “Sí” y “No”. </p>|
|<p>3\.  Selecciona  la  opción  **“No”**  y  el </p><p>flujo continúa. </p><p>￿  En caso de que seleccione la </p><p>opción  **“Sí”**,  continúa  en  el pas[o **5** ](#_page25_x311.00_y572.92)de este flujo. </p>|<p>4\.  Cierra  el  mensaje  y  regresa  al </p><p>paso donde fue invocado. </p>|
||<p><a name="_page25_x311.00_y572.92"></a>5.  Cierra el mensaje y continúa en </p><p>el [**(FA05)**](#_page11_x116.00_y565.92) pas[o **3**.](#_page12_x316.00_y162.92) </p>|

<a name="_page25_x116.00_y620.92"></a>**FA18 El sistema identifica que el archivo no es del formato esperado** 



|**Actor** |**Sistema** |||
| - | - | :- | :- |
||<p>1\.  El **FA18** inicia cuando el sistema </p><p>identifica  que  el  archivo  no  es del formato esperado. </p>|||
||<p>2\.  Cancela la operación y realiza lo </p><p>siguiente: </p><p>￿  Si el archivo esperado es un Excel  con  extensión  (.xlsx), </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



||<p>muestra el [**(MSG013)**](#_page27_x127.00_y484.92) con la opción  “Aceptar”  y  el  flujo continúa. </p><p>￿  Si el archivo esperado es un </p><p>PDF  con  extensión  (.pdf), muestra el [**(MSG014)**](#_page27_x127.00_y509.92) con la opción  “Aceptar”  y  el  flujo continúa. </p>|
| :- | - |
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||5\.  Regresa al [**(FA05)**](#_page11_x116.00_y565.92) paso[` `**3**.](#_page12_x316.00_y162.92) |

<a name="_page26_x116.00_y283.92"></a>**FA19 Selecciona la opción “Ver”**  

|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA19**  inicia  cuando  el </p><p>Empleado SAT selecciona **“Ver”** correspondiente  al  campo **“Detalle  de  penas  y deducciones”**  o  al  campo **“Cargar oficio\*”**. </p>|<p>2\.  Almacena en BD las Pistas de </p><p>Auditoría. </p><p>Datos que se almacenan: </p><p>**Módulo=**  Dictamen- Sección  donde  fue invocado </p><p>**Fecha y Hora**= Fecha y hora del  sistema,  usando  el formato  DD/MM/AAAA HH:MM:SS </p><p>**RFC Usuario=** RFC largo del Empleado SAT que ingresó al sistema. </p><p>**Tipo  de  movimiento**= **PRNT** (Imprimir) **Movimiento**=  </p><p>-id de dictamen </p><p>-nombre del documento </p><p>￿  En caso de que no se pueda </p><p>almacenar  las  Pistas  de Auditoría,  continúa  en  el [**(FA01)**](#_page7_x116.00_y556.92). </p>|
||<p>3\.  Lee el documento almacenado </p><p>en la BD, y lo prepara para su descarga. </p>|
||4\.  Descarga el archivo. |
||<p>5\.  Regresa  al  paso  donde  fue </p><p>invocado. </p>|

8. **Referencias<a name="_page26_x116.00_y694.92"></a> cruzadas ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.032.png)**
- 17\_3083\_CRN\_SeguimientoFinancieroYControl 
- 17\_3083\_EIU\_EmitirProforma 
- 17\_3083\_ECU\_GenerarDictamen ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.033.png)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |

9. **Mensajes<a name="_page27_x116.00_y145.92"></a> ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.034.png)**



|**ID Mensaje** |**Descripción** |
| - | - |
|<a name="_page27_x127.00_y201.92"></a>**MSG001** |El estatus del dictamen ha cambiado a “Proforma”.** |
|**MSG002** |<a name="_page27_x127.00_y224.92"></a>Ocurrió un error al guardar la información, favor de intentar nuevamente (PA01). |
|**MSG003** |<a name="_page27_x127.00_y249.92"></a>Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01). |
|**MSG004** |<a name="_page27_x127.00_y269.92"></a>¿Está seguro de rechazar este dictamen? |
|<a name="_page27_x127.00_y286.92"></a>**MSG005** |Favor de ingresar los datos obligatorios. |
|**MSG006** |<a name="_page27_x127.00_y306.92"></a>La  información  ingresada  no  cumple  con  el  formato esperado. Por favor verifíquela y vuelva a intentarlo. |
|**MSG007** |<a name="_page27_x127.00_y331.92"></a>El registro se va a eliminar de la tabla ¿Está seguro de que desea continuar? |
|**MSG008** |La fecha de recepción ingresada debe ser mayor o igual a la <a name="_page27_x127.00_y362.92"></a>fecha  de  solicitud.  Por  favor  verifique  el  dato  y  vuelva  a intentarlo. |
|**MSG009** |<a name="_page27_x127.00_y393.92"></a>Se perderá toda la información no guardada. ¿Está seguro de que desea continuar? |
|**MSG010** |<a name="_page27_x127.00_y418.92"></a>Ocurrió un error en la generación de la proforma. Inténtelo nuevamente. |
|**MSG011** |<a name="_page27_x127.00_y439.92"></a>Los datos se guardaron correctamente. |
|**MSG012** |<a name="_page27_x127.00_y459.92"></a>El archivo existente se reemplazará. ¿Está seguro de que desea continuar? |
|**MSG013** |<a name="_page27_x127.00_y484.92"></a>El archivo seleccionado no contiene la extensión .xlsx. Favor seleccione un archivo con la extensión correcta. |
|**MSG014** |<a name="_page27_x127.00_y509.92"></a>El archivo seleccionado no contiene la extensión .pdf. Favor seleccione un archivo con la extensión correcta. |

10. **Requerimientos<a name="_page27_x117.00_y540.92"></a> No Funcionales  ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.035.png)**



|**ID de RNF** |<p>**Requerimient**</p><p>**o No** </p><p>**Funcional** </p>|**Descripción** |||
| - | - | - | :- | :- |
|**RNF001** |Disponibilidad|El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación  en  el  horario  de  9:00  a  18:00 horas.  |||
|**RNF002** |Concurrencia  |<p>El número de Empleados SAT que puede tener el sistema son 150.  </p><p>￿  El  número  máximo  de  accesos </p><p>concurrentes  que  debe  soportar este  sistema  son  30  Empleados SAT.  </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|||



|**RNF003** |Seguridad  |El  acceso  solo  podrá  ser  otorgado  al Empleado  SAT  que  tenga  los  roles asignados  por  la  Administración  Central de  Seguridad,  Monitoreo  y  Control (ACSMC)  para  cada  módulo  de  este sistema.  |||
| - | - | - | :- | :- |
|**RNF004** |Usabilidad |<p>El sistema deberá manejar los siguientes elementos para facilitar la navegación: </p><p>- Mensajes  tipo  flotantes  (*tooltips*) con información de la herramienta que ofrece ayuda contextual, como guía para el Empleado SAT. </p><p>- Componente  de  ordenamiento que  permita  acomodar  la información  de  la  tabla  de  forma ascendente  o  descendente, considerando la columna en la que es seleccionado. </p><p>￿ </p><p>Contar  con  un  diseño  responsivo que  permita  su  óptima visualización en distintos tipos de dispositivos finales.  </p>|||
|**RNF005** |Eficiencia  |Las  consultas  se  dividen  en  generales  y detalladas,  para  que  las  detalladas carguen la información sólo cuando sean requeridas por el Empleado SAT.  |||
|**RNF006** |Usabilidad  |<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando  que  el  sistema  debe mostrar  inicialmente  15  registros  por página,  permitiendo  al  Empleado  SAT seleccionar  los  registros  que  requiere visualizar,  teniendo  las  opciones  15,  50  y 100: </p><p>- Ir a la primera página (debe mostrar la primera página con el resultado de la consulta). </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta). </p><p>- Ir  a  la  siguiente  página  (debe mostrar  la  siguiente  página, considerando la página actual, con el  resultado  de  la  consulta  y  el número de registros seleccionados por el Empleado SAT). </p><p>￿ </p><p>Ir a la página anterior (debe mostrar la página anterior considerando la página actual con el resultado de la consulta). </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|||



|||En la tabla deben mostrarse los registros ordenados alfabéticamente.  |
| :- | :- | :- |
|**RNF007** |Seguridad |Las  Pistas  de  Auditoría  deben  estar protegidas contra accesos no autorizados. Sólo  los  Empleados  SAT  autorizados pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada para mantenerla confidencial  y  evitar  exposiciones  no autorizadas. |
|**RNF008****  |Usabilidad |<p>El Empleado SAT podrá navegar a través de las páginas resultantes del documento PDF.</p><p>- Ir a la siguiente página (debe mostrar la página consecutiva del documento PDF). </p><p>- Ir a la página anterior (debe mostrar la página previa del documento PDF). </p>|
|**RNF009****  |Fiabilidad  |El  sistema  debe  ser  capaz  de  manejar excepciones  de  manera  efectiva  y presentar  mensajes  claros  y comprensibles  para  garantizar  una adecuada interacción con el sistema.  |
|**RNF010****  |Seguridad  |Se  debe  mantener  la  información  en pantalla en caso de un error al guardar las Pistas de Auditoría, siempre y cuando el escenario lo permita. Hay situaciones de infraestructura o de conexión de internet que sí  pierde los datos ya que no  están controlados por el sistema.  |
|**RNF011****  |Integridad  |Al almacenar la información en la BD de tipo  Texto  o  alfanumérico  se  deben eliminar los espacios en blanco al inicio y fin de la cadena.  |



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page30_x122.00_y133.92"></a>**11.Diagrama de actividad![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.036.png)**

![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.037.jpeg)

12. **Diagrama<a name="_page30_x116.00_y706.92"></a> de estados**  ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.038.png)

Aplica  los  estados  considerados  en  el  documento 17\_3083\_ECU\_GenerarDictamen. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00|
| :-: | :- | :-: |

13. **Aprobación<a name="_page31_x116.00_y132.92"></a> del cliente**  ![](Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.039.png)



|||||
| :- | :- | :- | :- |
|**FIRMAS DE CONFORMIDAD**  ||||
|||||
|**Firma 1** |**Firma 2** |||
|**Nombre**: Andrés Mojica Vázquez.  |**Nombre**: Ricardo Chávez Gutiérrez. |||
|**Puesto**: Usuario ACPPI.  |**Puesto**: Usuario ACPPI.  |||
|**Fecha:**  |**Fecha:**  |||
|||||
|**Firma 3**  |**Firma 4**  |||
|**Nombre**: Yesenia Helvetia Delgado Naranjo.  |**Nombre:** Alejandro Alfredo Muñoz Núñez.  |||
|**Puesto**: APE ACPPI.  |**Puesto:** RAPE ACPPI.  |||
|**Fecha**:  |**Fecha**:  |||
|||||
|**Firma 5**  |**Firma 6**  |||
|**Nombre**: Luis Angel Olguin Castillo. |**Nombre**: Erick Villa Beltrán.  |||
|**Puesto**: Enlace ACPPI.  |**Puesto**: Líder APE SDMA 6.  |||
|**Fecha**:  |**Fecha**:  |||
|||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_EmitirProforma.docx|Versión del template: 7.00||



|||
| :- | :- |
|**Firma 7**  |**Firma 8**  |
|**Nombre:**  Juan  Carlos  Ayuso Bautista.  |**Nombre:**  Aylín  de  la  Concepción Caballero Weng. |
|**Puesto:** Líder Técnico SDMA 6.  |**Puesto:**  Analista  de  Sistemas  DS SDMA 6.  |
|**Fecha**:  |**Fecha**:  |
|||

Página 36 de 36 

[ref1]: Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.016.png
[ref2]: Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.017.png
[ref3]: Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.021.png
[ref4]: Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.027.png
[ref5]: Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.028.png
[ref6]: Aspose.Words.2026b33a-905c-4919-9341-28e06151ef3a.030.png
