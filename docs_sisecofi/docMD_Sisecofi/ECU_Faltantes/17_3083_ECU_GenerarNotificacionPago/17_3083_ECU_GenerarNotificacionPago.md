**Administración General de Comunicaciones y Tecnologías de la Información![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.001.png)**

**Marco Documental 7.0**
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

**<ID Requerimiento>** 8309** 

**Nombre  del  Requerimiento:**  TI\_SISECOFI-SAT\_Seguimiento  financiero  y  control documental de proyectos de contratación** 

**Tabla de Versiones y Modificaciones** 



|Versión |Descripción del cambio |Responsable de la Versión |Fecha |
| - | - | :-: | - |
|*1* |*Creación del documento* |Angel Horacio López Alcaraz |*28/03/2024* |
|*1.1* |*Revisión del documento* |Luis Angel Olguin Castillo |*19/04/2024* |
|*1.2* |*Versión aprobada para firma* |Andrés Mojica Vázquez |*18/06/2024* |

**Tabla de Contenido** 

[17_3083_ECU_GenerarNotificacionPago ............................................................................................ 2 ](#_page1_x82.00_y132.92)

1. [Descripción ........................................................................................................................................................ 2 ](#_page1_x102.00_y148.92)
1. [Diagrama del Caso de Uso ...................................................................................................................... 2 ](#_page1_x102.00_y225.92)
1. [Actores ................................................................................................................................................................. 2 ](#_page1_x102.00_y462.92)
1. [Precondiciones............................................................................................................................................... 2 ](#_page1_x102.00_y597.92)
1. [Post condiciones ........................................................................................................................................... 3 ](#_page2_x102.00_y195.92)
1. [Flujo primario .................................................................................................................................................. 3 ](#_page2_x102.00_y347.92)
7. [Flujos alternos ................................................................................................................................................. 7 ](#_page6_x102.00_y543.92)
7. [Referencias cruzadas................................................................................................................................ 17 ](#_page16_x102.00_y295.92)
7. [Mensajes ........................................................................................................................................................... 17 ](#_page16_x102.00_y410.92)
7. [Requerimientos No Funcionales .................................................................................................... 18 ](#_page17_x102.00_y133.92)
7. [Diagrama de actividad .......................................................................................................................... 20 ](#_page19_x102.00_y145.92)
7. [Diagrama de estados ............................................................................................................................. 20 ](#_page19_x102.00_y714.92)
7. [Aprobación del cliente ........................................................................................................................... 21 ](#_page20_x102.00_y133.92)



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page1_x82.00_y132.92"></a>17\_3083\_ECU\_GenerarNotificacionPago 

1. **Descripción<a name="_page1_x102.00_y148.92"></a>  ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.002.png)**

El objetivo de este Caso de Uso es permitir al Empleado SAT generar el registro de una notificación y referencia de pago. 

2. **Diagrama<a name="_page1_x102.00_y225.92"></a> del Caso de Uso ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.003.png)**

![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.004.png)

3. **Actores<a name="_page1_x102.00_y462.92"></a>  ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.005.png)**



|**Actor** |**Descripción** |
| - | - |
|**Empleado SAT** |El Empleado SAT cuenta con el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema. |

4. **Precondiciones<a name="_page1_x102.00_y597.92"></a>** ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.006.png)
- El Empleado SAT se ha autenticado en el sistema con e.firma válida. 
- El  sistema  ha  consumido  el  servicio  “Oauth”  para  obtener  los  datos  del Empleado SAT que ingresa. 
- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar a la sección “Solicitud de pago”. 
- El  Empleado  SAT  ha  ingresado  a  un  dictamen  de  acuerdo  con  el **(17\_3083\_ECU\_GenerarDictamen)**. 
- El Empleado SAT ha seleccionado alguna de las siguientes opciones: Editar dictamen y Ver dictamen en el módulo “Consumo de Servicios” relacionados a un contrato. ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.007.png)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



- El  Empleado  SAT  ha  seleccionado  una  opción  en  el  campo  Convenio  de ![ref1]Colaboración del contrato. 
- El empleado SAT ha registrado la plantilla activa de verificación para la carga de documentos en la sección documental. 
5. **Post<a name="_page2_x102.00_y195.92"></a> condiciones  ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.009.png)**
- El Empleado SAT realizó el registro de la solicitud de pago. ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.010.png)
- El Empleado SAT cargó el documento oficio de solicitud de pago  con las firmas de aceptación correspondientes en formato PDF. 
- El Empleado SAT realizó el registro de la sección de “Referencia de pago”. 
- El  sistema  cambió  el  estatus  de  las  facturas  recibidas  y  del  dictamen  a “Solicitud de pago”. 
- El  sistema  cambió  el  estatus  de  las  facturas  recibidas  y  del  dictamen  a “Pagado”. 
6. **Flujo<a name="_page2_x102.00_y347.92"></a> primario** ![ref2]



|**Actor** |**Sistema** ||||
| - | - | :- | :- | :- |
|<p>1\.  El  Caso  de  Uso  inicia  cuando  el </p><p>Empleado SAT selecciona la sección **“Solicitud de pago”**. </p><p>￿  En caso de haber seleccionado </p><p>la opción “Ver dictamen” en el módulo “Consumo de Servicios”, aplica  la  regla  de  negocio **(RNA167)**. </p>|<p>2\.  Identifica el rol  del  Empleado  SAT </p><p>que  ingresa  para  mostrar  las opciones  correspondientes.  Aplica las **(RNA142)** y **(RNA87)**. </p>||||
||<p>3\.  Obtiene de la base de datos (BD) la </p><p>información de los catálogos que se usarán en las listas desplegables: </p><p>- Tipo  de  notificación  de pago\* </p><p>- Desglose\* </p>||||
||<p>4\.  Consulta en la BD la existencia de </p><p>información  relacionada  con  la “Solicitud de pago” y/o “Referencia de pago”. </p>||||
||<p>5\.  Muestra en pantalla la información </p><p>del  formulario  “Solicitud  de  pago” conforme a las **(RNA154)** y **(RNA87)** con los siguientes campos:** </p>||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||



- Oficio de solicitud de pago\*:  ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.012.png)
- Fecha de solicitud\*: 
- Generar documento\*: 
- Añadir PDF\*: 

  Opciones 

- ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.013.png) (Generar documento) 
- Examinar 
- Cancelar.  Aplica  la **(RNA246)**. 
- Guardar. Aplica la **(RNA246)**. 
- Solicitud de pago. Aplica la **(RNA51)**. 

  Sección “Referencia de Pago” 

- Tipo  de  notificación  de pago\*: 
- Oficio  de  notificación  de pago\*: 
- Fecha de notificación\*: 

  Facturas. Aplica la **(RNA 154)**. 

- Comprobante fiscal\*: 
- Desglose\*: 
- Folio de ficha de pago\*: 
- Fecha de pago\*: 
- Tipo de cambio pagado\*: 
- Pagado NAFIN\*: 
- Ficha NAFIN\*: 

  Opciones: 

- Examinar 
- Cancelar.  Aplica  la **(RNA246)**. 
- Guardar. Aplica la **(RNA246)**. 
- Pagado. Aplica la **(RNA51)**. 

Ver **(17\_3083\_EIU\_GenerarNotificacion Pago)** Estilos 01. 

6. Si  el  dictamen  se  encuentra  en estatus “Facturado” continúa en el pas[o 7 ](#_page3_x113.00_y726.92)de este flujo. 
- De otro modo, si el dictamen se encuentra en estatus “Solicitud de pago” continúa en el paso [**15** ](#_page5_x113.00_y379.92)del presente flujo. 
7. Ingresa<a name="_page3_x113.00_y726.92"></a>  en  el  campo  **“Oficio  de solicitud  de  pago\*”**  el  dato 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

correspondiente  al  número  de  la ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.014.png)solicitud de pago. 

8. Ingresa  en  el  campo  **“Fecha  de** 

   **solicitud\*”** el dato correspondiente 

   a la fecha de la solicitud de pago. 

9. Selecciona<a name="_page4_x113.00_y213.92"></a>  la  opción  **“Guardar”**  y <a name="_page4_x320.00_y213.92"></a> 10.  Valida  la  estructura  de  los  datos continúa en el flujo.  ingresados  en  los  campos  de acuerdo con la **(RNA255)** y continúa 
- Si selecciona la opción **“Generar**  en el flujo. 

  **documento”**,  continúa  en  el 

  [**(FA09)**](#_page13_x102.00_y145.92).  ￿  En caso de que la estructura de 

- Si  selecciona  la  opción  los  datos  ingresados  sea **“Cancelar”**,  continúa  en  el  incorrecta,  continúa  en  el [**(FA02)**](#_page7_x102.00_y361.92).  **[**(FA10)**](#_page15_x102.00_y460.92)**. 
- Si  selecciona  la  opción 

  **“Examinar”**,  continúa  en  el 

  [**(FA07)**](#_page12_x102.00_y157.92). 

- Si  selecciona  la  opción 

  **“Solicitud  de  Pago”**,  continúa 

  en el [**(FA06)**](#_page10_x102.00_y512.92). 

11. Si<a name="_page4_x320.00_y410.92"></a> existe algún archivo en el campo “Añadir PDF”, valida que sea del tipo PDF y continúa en el flujo. 
- En  caso  de  que  el  archivo  no cumpla  con  el  formato, continúa en el [**(FA03)**](#_page8_x102.00_y133.92). 
12. Almacena en la BD la información ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.015.png)de las Pistas de Auditoría. 

    Datos que se almacenan: 

    **Módulo**= Dictamen-Solicitud de Pago 

    **Fecha y Hora**= Fecha y hora del sistema,  usando  el  formato DD/MM/AAAA HH:MM:SS 

    **RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. 

    **Tipo  de  movimiento**=  **INSR** (Insertar) / **UPDT** (Modificar) **Movimiento**=  Aplica  la **(RNA239)** 

- Id de Dictamen 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

- En caso de que no se  puedan ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.016.png)almacenar  las  Pistas  de Auditoría, continúa en el [**(FA01)**](#_page6_x102.00_y583.92). 
13. Almacena en la BD la información ingresada  en  la  sección  “Solicitud de pago”. Aplica la **(RNA247)**. 
- En caso de contar con el archivo “Solicitud  de  Pago",  se almacena  en  la  BD.  Aplica  la **(RNA38)**. 
14. Habilita las opciones de la sección “Referencia de Pago” y continúa el pas[o 15.](#_page5_x113.00_y379.92) 
- De  otro  modo,  continúa  en  el pas[o **21** ](#_page6_x320.00_y476.92)de este flujo. 
15. Captura<a name="_page5_x113.00_y379.92"></a>  la  información  de  los campos de la sección **“Referencia de Pago”**. 
15. Selecciona<a name="_page5_x113.00_y459.92"></a> la opción **“Guardar”** y el <a name="_page5_x320.00_y459.92"></a> 17.  Valida  la  estructura  de  los  datos ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.017.png)flujo continúa.  ingresados  en  los  campos  de acuerdo con la **(RNA255)** y continúa 
- Si  selecciona  la  opción  en el flujo. 

  **“Cancelar”**,  continúa  en  el 

  [**(FA02)**](#_page7_x102.00_y361.92).  ￿  En caso de que la estructura de 

- Si  selecciona  la  opción  los  datos  ingresados  sea **“Pagado”**,  continúa  en  el  incorrecta,  continúa  en  el [**(FA05)**](#_page9_x102.00_y316.92).  **[**(FA10)**](#_page15_x102.00_y460.92)**. 
- Si  selecciona  la  opción 

  **“Examinar”**,  continúa  en  el 

  [**(FA07)**](#_page12_x102.00_y157.92). 

18. Si<a name="_page5_x320.00_y631.92"></a> existe algún archivo en el campo “Ficha  NAFIN”,  valida  que  sea  del tipo PDF y continúa en el flujo. 
- En  caso  de  que  el  archivo  no cumpla  con  el  formato, continúa en el [**(FA03)**](#_page8_x102.00_y133.92). 
19. Almacena en la BD la información de las Pistas de Auditoría. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

Datos que se almacenan: ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.018.png)

**Módulo**=  Dictamen-Referencia de Pago 

**Fecha y Hora**= Fecha y hora del sistema,  usando  el  formato DD/MM/AAAA HH:MM:SS 

**RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. 

**Tipo  de  movimiento**=  **INSR** (Insertar) / **UPDT** (Modificar) **Movimiento**=  Aplica  la **(RNA239)** 

- Id de Dictamen 
- Comprobante fiscal 
- En caso de que no se  puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA01)**](#_page6_x102.00_y583.92). 
20. Almacena en la BD la información ingresada en la sección “Referencia de pago”. Aplica la **(RNA247)**. 
- En  caso  de  contar  con  los archivos  “Ficha  NAFIN",  se almacena  en  la  BD.  Aplica  la **(RNA38)**. 
21. Fin<a name="_page6_x320.00_y476.92"></a> del Caso de Uso. 

<a name="_page6_x102.00_y543.92"></a>**7. Flujos alternos  ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.019.png)![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.020.png)**

<a name="_page6_x102.00_y583.92"></a>**FA01 No se pueden almacenar Pistas de Auditoría** 

|**Actor** |**Sistema** ||||
| - | - | :- | :- | :- |
||<p>1\.  El **FA01** inicia cuando interviene un </p><p>evento  ajeno  y  no  se  pueden almacenar las Pistas de Auditoría.  </p>||||
||<p>2\.  Cancela la operación sin completar </p><p>el  movimiento  que  estaba  en proceso. </p>||||
||<p>3\.  Muestra el mensaje de acuerdo con </p><p>lo siguiente: </p>||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||



||<p>- Si la pista de auditoría es por el tipo  de  movimiento  **UPDT**  e **INSR**, se muestra el [**(MSG001)**](#_page16_x113.00_y473.92). </p><p>- En  caso  de  que  la  pista  de auditoría  sea  por  el  tipo  de movimiento **PRNT**, se muestra el [**(MSG002)**](#_page16_x113.00_y497.92).  </p><p>Cada  mensaje  se  muestra  con  la opción “Aceptar”. </p>|
| :- | - |
|4\.  Selecciona la opción **“Aceptar”**.** |5\.  Cierra el mensaje. |
||6\.  Regresa al paso previo que detona la acción de la pista de auditoría. |

<a name="_page7_x102.00_y361.92"></a>**FA02 Selecciona la opción “Cancelar” ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.021.png)**

|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El **FA02** inicia cuando el Empleado </p><p>SAT  selecciona  la  opción **“Cancelar”**. </p>|<p>2\.  Muestra  el  **[**(MSG003)**](#_page16_x113.00_y518.92)**,**  con  las </p><p>opciones “Sí” y “No”. </p>|
|<p>3\.  Selecciona una opción: </p><p>- Selecciona la opción **“No”**, y el flujo continúa. </p><p>- Si  selecciona  la  opción  **“Si”**, continúa en el pas[o **5**.](#_page7_x318.00_y540.92) </p>|<p>4  Cierra el mensaje y continúa en el </p><p>pas[o 6.](#_page7_x318.00_y570.92) </p>|
||<p><a name="_page7_x318.00_y540.92"></a>5.  Cierra  el  mensaje  y  recarga  la </p><p>sección. </p>|
||<p><a name="_page7_x318.00_y570.92"></a>6.  Realiza lo siguiente: </p><p>- Si fue invocado en el paso [9 ](#_page4_x113.00_y213.92)del flujo primario, regresa al paso [**7** ](#_page3_x113.00_y726.92)del flujo primario. </p><p>- Si fue invocado en el pas[o 16 ](#_page5_x113.00_y459.92)del flujo primario, regresa al pas[o **15** ](#_page5_x113.00_y379.92)del flujo primario. </p><p>- Si fue invocado en el paso [8 ](#_page14_x113.00_y556.92)del [**(FA09)**](#_page13_x102.00_y145.92),  cierra  la  ventana  y regresa  al  paso [` `**9** ](#_page4_x113.00_y213.92) del  Flujo primario. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page8_x102.00_y133.92"></a>**FA03 Formato PDF no válido ![ref1]**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA03**  inicia  cuando  el  sistema </p><p>identifica que el formato del archivo PDF cargado no es correcto.  </p>|
||<p>2\.  Muestra el [**(MSG004)**](#_page16_x113.00_y535.92), con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||<p>5\.  Realiza una acción: </p><p>- Si fue invocado en el pas[o 11 ](#_page4_x320.00_y410.92)del flujo primario, regresa al paso[` `**9** ](#_page4_x113.00_y213.92)del flujo primario. </p><p>- Si fue invocado en el pas[o 18 ](#_page5_x320.00_y631.92)del** flujo primario, regresa al pas[o **16** ](#_page5_x113.00_y459.92)del flujo primario. </p>|

<a name="_page8_x102.00_y383.92"></a>**FA04 No se ingresaron los datos obligatorios ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.022.png)**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA04**  inicia  cuando  el  sistema </p><p>identifica que no se ingresaron los datos obligatorios. </p>|
||<p>2\.  Muestra  en  rojo  los  campos </p><p>pendientes de capturar. </p>|
||<p>3\.  Muestra el [**(MSG005)**](#_page16_x113.00_y556.92), con la opción </p><p>“Aceptar”. </p><p>￿  Si proviene de la validación de </p><p>un  archivo,  muestra  el [**(MSG007)**](#_page16_x113.00_y594.92)  con  la  opción “Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje. |
||<p>6\.  Realiza una acción: </p><p>Si  fue  invocado  en  el  paso [ 2 ](#_page13_x320.00_y158.92) del [**(FA09)**](#_page13_x102.00_y145.92) regresa al paso [**7** ](#_page3_x113.00_y726.92)del Flujo primario. </p><p>Si  fue  invocado  en  el  paso [ 8 ](#_page14_x113.00_y556.92) del [**(FA09)**](#_page13_x102.00_y145.92) regresa al paso [**7** ](#_page14_x113.00_y494.92)de dicho flujo. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



||<p>Si  fue  invocado  en  el  paso [ 2 ](#_page10_x320.00_y525.92) del [**(FA06)**](#_page10_x102.00_y512.92) regresa al paso [**7** ](#_page3_x113.00_y726.92)del Flujo primario. </p><p>Si  fue  invocado  en  el  paso [ 2 ](#_page9_x320.00_y329.92) del [**(FA05)**](#_page9_x102.00_y316.92) regresa al paso [**15** ](#_page5_x113.00_y379.92)del Flujo primario. </p><p>Si  fue  invocado  en  el  paso [ 3 ](#_page9_x320.00_y433.92) del [**(FA05)**](#_page9_x102.00_y316.92) regresa al paso [**16** ](#_page5_x113.00_y459.92)del Flujo primario. </p><p>Si  fue  invocado  en  el  paso [ 3 ](#_page10_x320.00_y629.92) del [**(FA06)**](#_page10_x102.00_y512.92) regresa al paso [**9** ](#_page4_x113.00_y213.92)del Flujo primario. </p>|
| :- | - |

<a name="_page9_x102.00_y316.92"></a>**FA05 Selecciona la opción “Pagado”** ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.023.png)

|1\.  El **FA05** inicia cuando el Empleado <a name="_page9_x320.00_y329.92"></a>SAT selecciona la opción **“Pagado”**. |<p>2\.  Valida  que  se  hayan  ingresado </p><p>todos  los  datos  obligatorios conforme a la **(RNA03)**. </p><p>En  caso  de  no  haber  ingresado todos  los  campos  obligatorios, continúa en el [**(FA04)**](#_page8_x102.00_y383.92). </p>|
| -: | - |
||<p><a name="_page9_x320.00_y433.92"></a>3.  Valida  que  exista  un  archivo </p><p>cargado  en  la  BD  en  el  campo “Ficha  NAFIN”  de  la  sección “Referencia de Pago”. </p><p>En  caso  de  no  haber  ingresado todos  los  campos  obligatorios, continúa en el [**(FA04)**](#_page8_x102.00_y383.92). </p>|
||<p><a name="_page9_x320.00_y539.92"></a>4.  Valida  la  estructura  de  los  datos </p><p>ingresados  en  los  campos  de acuerdo con la **(RNA255)**. </p><p>En caso de que la estructura de los datos  ingresados  sea  incorrecta, continúa en el [**(FA10)**](#_page15_x102.00_y460.92). </p>|
||<p>5\.  Almacena en la BD la información </p><p>de las Pistas de Auditoría. </p><p>Datos que se almacenan:  </p><p>**Módulo**=Dictamen-Datos generales </p><p>**Fecha y Hora**= Fecha y hora del sistema,  usando  el  formato DD/MM/AAAA HH:MM:SS </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



||<p>**RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. </p><p>**Tipo  de  movimiento**=  **UPDT** (Modificar) </p><p>**Movimiento**=  Aplica  la **(RNA239)** </p><p>- Id del Dictamen </p><p>- Estatus= Pagado </p><p>￿  En caso de que no se puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA01)**](#_page6_x102.00_y583.92). </p>|
| :- | - |
||<p>6\.  Almacena en la BD la información </p><p>del cambio de estatus a “Pagado”. Aplica la **(RNA247)**. y **(RNA156)**. </p>|
||<p>7\.  Muestra el [**(MSG006)**](#_page16_x113.00_y577.92) con la opción </p><p>“Aceptar”.</p>|
|8\.  Selecciona la opción “Aceptar”. |9\.  Cierra el mensaje |
||10\.  Recarga la pantalla.|
||11\.  Fin del Caso de Uso.|

<a name="_page10_x102.00_y512.92"></a>**FA06 Selecciona la opción “Solicitud de pago”** ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.024.png)

|<p>1\.  El **FA06** inicia cuando el Empleado </p><p><a name="_page10_x320.00_y525.92"></a>SAT selecciona la opción **“Solicitud de pago”**. </p>|<p>2\.  Valida  que  se  hayan  ingresado </p><p>todos  los  datos  obligatorios conforme a la **(RNA03)**. </p><p>￿  En caso de no haber ingresado </p><p>todos  los  campos  obligatorios, continúa en el [**(FA04)**](#_page8_x102.00_y383.92). </p>|
| - | - |
||<p><a name="_page10_x320.00_y629.92"></a>3.  Valida un archivo cargado en la BD </p><p>en  el  campo  “Añadir  PDF”  de  la sección “Solicitud de Pago”. </p><p>￿  En caso de no haber ingresado </p><p>todos  los  campos  obligatorios, continúa en el [**(FA04)**](#_page8_x102.00_y383.92). </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

4. Valida<a name="_page11_x320.00_y133.92"></a>  la  estructura  de  los  datos ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.025.png)ingresados  en  los  campos  de acuerdo con la **(RNA255)**. 
- En caso de que la estructura de los  datos  ingresados  sea incorrecta,  continúa  en  el [**(FA10)**](#_page15_x102.00_y460.92). 
5. Almacena en la BD la información ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.026.png)de las Pistas de Auditoría. 

   Datos que se almacenan:  

   **Módulo**=Dictamen-Datos generales 

   **Fecha y Hora**= Fecha y hora del sistema,  usando  el  formato DD/MM/AAAA HH:MM:SS 

   **RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. 

   **Tipo  de  movimiento**=  **UPDT** (Modificar) 

   **Movimiento**=  Aplica  la **(RNA239)** 

- Id del Dictamen 
- Estatus=  Solicitud  de Pago 
- En caso de que no se  puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA01)**](#_page6_x102.00_y583.92). 
  6. Almacena en la BD la información el cambio  de  estatus  a  “Solicitud  de pago”.  Aplica  la  **(RNA247)**.  y **(RNA156)**. 
  6. Muestra el [**(MSG008)**](#_page16_x113.00_y608.92) con la opción “Aceptar”. 
8. Selecciona la opción “Aceptar”. 9.  Cierra el mensaje. 
10. Recarga la pantalla.
10. Fin del Caso de Uso. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page12_x102.00_y157.92"></a>**FA07 Selecciona la opción “Examinar”** 

|<p>1\.  El **FA07** inicia cuando el Empleado </p><p>SAT  selecciona  la  opción **“Examinar**. </p>|<p>2\.  Valida que no existan documentos </p><p>previamente cargados en la sección donde fue invocado y continúa en el paso 5 de este flujo. </p><p>￿  En  caso  de  existir  archivos </p><p>previamente cargados, muestra el [**(MSG011)**](#_page16_x113.00_y659.92) con las opciones “Si” y “No” y el flujo continúa. </p>|
| - | - |
|3\.  Selecciona una opción. |<p>4\.  Cierra el mensaje. </p><p>- Si  selecciona  la  opción  “Si”,  el flujo continúa. </p><p>- Si  selecciona  la  opción  “No” regresa  al  paso  donde  fue invocado. </p>|
||<p>5\.  Muestra  el  gestor  de  archivos  del </p><p>equipo de cómputo del Empleado SAT. </p>|
|<p>6\.  Selecciona el archivo con extensión </p><p>**.PDF**. </p>|<p>7\.  Muestra  el  archivo  que  se </p><p>seleccionó  en  el  campo correspondiente. </p>|
||<p>8\.  Regresa  al  paso  donde  fue </p><p>invocado. </p>|

<a name="_page12_x102.00_y564.92"></a>**FA08 Selecciona la opción “Cerrar” o “Cancelar” en Generar plantilla ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.027.png)**

|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El **FA08** inicia cuando el Empleado </p><p>SAT selecciona la opción **“Cerrar”** o **“Cancelar”**  de  la  ventana emergente.** </p>|<p>2\.  Muestra  el  **[**(MSG009)**](#_page16_x113.00_y625.92)**  con  las </p><p>opciones “Sí” y “No”.** </p>|
|<p>3\.  Selecciona la opción **“Sí”**, y el flujo </p><p>continúa. </p><p>￿  En  caso  de  que  seleccione  la </p><p>opción  **“No”**,  continúa  en  el pas[o **7** ](#_page14_x113.00_y494.92)del [**(FA09)**](#_page13_x102.00_y145.92). </p>|4\.  Cierra el mensaje. |
||<p>5\.  Continúa  en  el  paso [` `**9** ](#_page4_x113.00_y213.92) del  Flujo </p><p>primario. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page13_x102.00_y145.92"></a>**FA09 Generar documento ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.028.png)**

|<p>1\.  El **FA09** inicia cuando El Empleado </p><p><a name="_page13_x320.00_y158.92"></a>SAT selecciona la opción **“Generar documento”**.** </p>|<p>2\.  Valida que se hayan ingresado los </p><p>campos  obligatorios  “Oficio  de solicitud  de  pago\*”  y  “Fecha  de solicitud  de  pago\*”,  conforme  a  la **(RNA156)** y **(RNA03)**. </p><p>￿  Si no se ingresaron los campos </p><p>obligatorios,  continúa  en  el [**(FA04)**](#_page8_x102.00_y383.92). </p>|
| - | - |
||<p>3\.  Obtiene  del  administrador  de </p><p>plantillas,  la  correspondiente  al “Oficio de Solicitud de pago”. </p>|
||<p>4\.  Inserta  en  la  plantilla  “Oficio  de </p><p>Solicitud  de  pago”  los  datos ingresados  “Oficio  de  solicitud  de pago\*” y “Fecha de solicitud\*”. </p>|
||<p>5\.  Consulta  en  la  BD  la  siguiente </p><p>información a utilizar, conforme a la **(RNA190)**:  </p><p>- Nombre corto del contrato </p><p>- Número de contrato </p><p>- Nombre largo del contrato  </p><p>- ID. Proyecto </p><p>- ID. Proveedor (ID AGS) </p><p>- ID. Acuerdo </p><p>- ID. Contrato </p><p>- ID. De comp. Fiscal </p><p>- ID. Pedido </p><p>- ID. De nota de crédito </p><p>- Nombre  del  beneficiario (Nombre del proveedor) </p><p>- Importe a Pagar (Importe de todas  las  facturas  del dictamen) </p><p>- Tipo de moneda </p><p>- Fecha  de  recepción  (Fecha de recepción de la factura)  </p><p>- Periodo del servicio </p><p>- No. De factura o recibo </p><p>- No. De nota de crédito </p><p>- Nombre  del  verificador (participantes  en  la administración del contrato) </p><p>- Puesto del verificador (tabla usuarios del sistema) </p><p>- Nombres con puestos a los que se les copiará por correo </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

electrónico  (catálogo ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.029.png)complementario:  nombre de la persona que ocupa el puesto ACPPI y nombre de la  persona  que  ocupa  el puesto:  Administrador central  y  Administrador general). 

6. Muestra  la  ventana  emergente “Generar  Plantilla”  con  la  vista previa  del  “Oficio  de  Solicitud  de pago”, conforme a la **(RNA187)**, con la siguiente información: 
- Tipo de plantilla\* 
- Formato a exportar 
- Panel de visualización 

  Opciones 

- Aceptar. Aplica la **(RNA246)**. 
- Cancelar.  Aplica  la **(RNA246)**. 
- Cerrar ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.030.png)

Ver **(17\_3083\_EIU\_GenerarSolicitudPa go)** Estilos 02.  

7. Selecciona<a name="_page14_x113.00_y494.92"></a>  una  opción  en  los campos:** 
- **“Tipo de plantilla\*”** 
- **“Formato a exportar\*”** 
8. Selecciona<a name="_page14_x113.00_y556.92"></a> una opción:  9.  Valida  que  se  hayan  ingresado 
- Si  selecciona  la  opción  todos  los  datos  obligatorios, **“Aceptar”**, el flujo continúa.  conforme a la **(RN187)**. 
- Si  selecciona  la  opción 

  **“Cancelar”**,  continúa  en  el  ￿  En  caso  de  que  no  se  hayan [**(FA08)**](#_page12_x102.00_y564.92).  ingresado los datos obligatorios, 

- Si selecciona la opción **“Cerrar”**,  continúa en el [**(FA04)**](#_page8_x102.00_y383.92). 

  continúa en el [**(FA08)**](#_page12_x102.00_y564.92). 

10. Cierra la ventana emergente. 
10. Almacena en la BD la información de las Pistas de Auditoría. 

    Datos que se almacenan: 

    **Módulo**= Dictamen-Solicitud de Pago 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



||<p>**Fecha y Hora**= Fecha y hora del sistema,  usando  el  formato DD/MM/AAAA HH:MM:SS </p><p>**RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. </p><p>**Tipo  de  movimiento**=  **PRNT** (Imprimir) </p><p>**Movimiento**=  Aplica  la **(RNA239)**  para  los  campos mapeados. </p><p>-  Id del Dictamen </p><p>￿  En caso de que no se  puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA01)**](#_page6_x102.00_y583.92). </p>|
| :- | - |
||<p>12\.  Genera  el  “Oficio  de  solicitud  de </p><p>pago” en el formato seleccionado.  </p>|
||13\.  Descarga el archivo. |
||14\.  Regresa al pas[o **9** ](#_page4_x113.00_y213.92)del Flujo primario. |

<a name="_page15_x102.00_y460.92"></a>**FA10 La estructura de los datos ingresados es incorrecta ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.031.png)**

|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA10**  inicia  cuando  el  sistema </p><p>identifica  que  la  estructura  de  los datos ingresados es incorrecta.  </p>|
||<p>2\.  Muestra  en  rojo  los  campos  con </p><p>estructura incorrecta. </p>|
||<p>3\.  Muestra el [**(MSG010)**](#_page16_x113.00_y642.92) con la opción </p><p>“Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje. |
||<p>6\.  Realiza una acción: </p><p>- Si fue invocado en el paso [10 ](#_page4_x320.00_y213.92)del flujo primario, regresa al paso [**7**.](#_page3_x113.00_y726.92) del flujo primario. </p><p>- Si fue invocado en el paso [17 ](#_page5_x320.00_y459.92)del flujo primario, regresa al paso [**15**.](#_page5_x113.00_y379.92) del flujo primario. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



||￿ ￿ |<p>Si fue invocado en el paso [4 ](#_page11_x320.00_y133.92)del [**(FA06)**](#_page10_x102.00_y512.92),  regresa  al  paso [` `**7**.](#_page3_x113.00_y726.92)  del flujo primario. </p><p>Si fue invocado en el paso [4 ](#_page9_x320.00_y539.92)del [**(FA05)**](#_page9_x102.00_y316.92),  regresa  al  paso [` `**15** ](#_page5_x113.00_y379.92) del flujo primario. </p>|
| :- | - | - |

<a name="_page16_x102.00_y295.92"></a>**Referencias cruzadas  ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.032.png)**

- 17\_3083\_CRN\_SeguimientoFinancieroYControl 
- 17\_3083\_EIU\_GenerarNotificacionPago 
- 17\_3083\_ECU\_GenerarDictamen 
9. **Mensajes<a name="_page16_x102.00_y410.92"></a>  ![ref2]**



|**ID Mensaje** |**Descripción** |
| - | - |
|**MSG001** |<a name="_page16_x113.00_y473.92"></a>Ocurrió  un  error  al  guardar  el  registro,  favor  de  intentar nuevamente (PA01). |
|**MSG002** |<a name="_page16_x113.00_y497.92"></a>Ocurrió  un  error  al  generar  el  documento,  favor  de  intentar nuevamente (PA01).  |
|<a name="_page16_x113.00_y518.92"></a>**MSG003** |Se perderá la información ingresada, ¿desea continuar? |
|<a name="_page16_x113.00_y535.92"></a>**MSG004** |Debe ingresar un archivo PDF, favor de verificar. |
|**MSG005** |<a name="_page16_x113.00_y556.92"></a>Favor de ingresar los datos obligatorios marcados con un asterisco (\*). |
|<a name="_page16_x113.00_y577.92"></a>**MSG006** |El estatus del Dictamen ha cambiado a “Pagado”. |
|<a name="_page16_x113.00_y594.92"></a>**MSG007** |Falta ingresar el archivo PDF. Favor de verificar. |
|<a name="_page16_x113.00_y608.92"></a>**MSG008** |El estatus del Dictamen ha cambiado a “Solicitud de pago”. |
|<a name="_page16_x113.00_y625.92"></a>**MSG009** |Se cancelará la acción en curso. ¿Está seguro de continuar? |
|<a name="_page16_x113.00_y642.92"></a>**MSG010** |La estructura de la información es incorrecta. Favor de validar. |
|<a name="_page16_x113.00_y659.92"></a>**MSG011** |Ya  existe  un  documento  previamente  cargado.  ¿Desea reemplazarlo? |

*. ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.033.png)*



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

10. **Requerimientos<a name="_page17_x102.00_y133.92"></a> No Funcionales  ![ref3]![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.035.png)**



|**ID de RNF** |**Requerimiento No Funcional** |**Descripción** |||
| - | :-: | - | :- | :- |
|**RNF001** |Disponibilidad |El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas. |||
|**RNF002** |Concurrencia |<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p>El  número  de  accesos  concurrentes  que  debe soportar este sistema son máximo 30 Empleados SAT.</p>|||
|**RNF003** |Seguridad |El acceso solo podrá ser otorgado al Empleado SAT  que  tenga  los  roles  asignados  por  la Administración Central de Seguridad, Monitoreo y  Control  (ACSMC)  para  cada  módulo  de  este sistema. |||
|**RNF004** |Usabilidad |<p>El  sistema  deberá  manejar  los  siguientes elementos para facilitar la navegación:  </p><p>- Mensajes  tipo  flotantes  (*tooltips*)  con información de la herramienta que ofrece ayuda  contextual,  como  guía  para  el Empleado SAT.   </p><p>- Componente  de  ordenamiento  que permita  acomodar  la  información  de  la tabla de forma ascendente o descendente, considerando  la  columna  donde  es seleccionado.   </p><p>- Contar  con  un  diseño  responsivo  que permita su óptima visualización en distintos tipos de dispositivos finales.   </p>|||
|**RNF005** |Eficiencia |Las  consultas  se  dividen  en  generales  y detalladas,  para  que  las  detalladas  carguen  la información solo cuando sean requeridas por el Empleado SAT.  |||
|**RNF006** |Usabilidad |<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que  el  sistema  debe  mostrar  inicialmente  15 registros por página, permitiendo al Empleado SAT  seleccionar  los  registros  que  requiere visualizar, teniendo las opciones 15, 50 y 100: </p><p>- Ir a la  primera página  (debe mostrar  la primera  página  con  el  resultado  de  la consulta). </p><p>- Ir  a  la  última  página  (debe  mostrar  la última  página  con  el  resultado  de  la consulta). </p><p>￿ </p><p>Ir a la siguiente página (debe mostrar la siguiente página, considerando la página </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||



|||<p>actual, con el resultado de la consulta y el número de registros seleccionados por el Empleado SAT).  </p><p>￿  Ir a  la  página  anterior  (debe  mostrar la </p><p>página  anterior  considerando  la  actual, con el resultado de la consulta).  </p><p>En  la  tabla  deben  mostrarse  los  registros ordenados alfabéticamente.  </p>|
| :- | :- | - |
|**RNF007** |Seguridad |Las Pistas de Auditoría deben estar protegidas contra  accesos  no  autorizados.  Solo  los Empleados  SAT  autorizados  pueden consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada  para  mantenerla  confidencial  y  evitar exposiciones no autorizadas.    |
|**RNF008** |Fiabilidad |El  sistema  debe  ser  capaz  de  manejar excepciones  de  manera  efectiva  y  presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema.  |
|**RNF009** |Usabilidad |<p>Usabilidad,  El  Empleado  SAT  podrá  navegar  a través de las páginas resultantes del documento PDF. </p><p>- Ir a la siguiente página (debe mostrar la página  consecutiva  del  documento PDF).  </p><p>- Ir a  la  página  anterior  (debe  mostrar la página previa del documento PDF). </p>|
|**RNF010** |Seguridad |Se debe mantener la información en pantalla en caso  de  un  error  al  guardar  las  Pistas  de Auditoría,  siempre  y  cuando  el  escenario  lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema. |



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

11. **Diagrama<a name="_page19_x102.00_y145.92"></a> de actividad**  ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.036.png)

![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.037.jpeg)

12. **Diagrama<a name="_page19_x102.00_y714.92"></a> de estados**  ![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.038.png)

Aplica los estados considerados en el documento 17\_3083\_ECU\_GenerarDictamen. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

13. **Aprobación<a name="_page20_x102.00_y133.92"></a> del cliente  ![ref3]**



|||||
| :- | :- | :- | :- |
|**FIRMAS DE CONFORMIDAD**  ||||
|||||
|**Firma 1**  |**Firma 2**  |||
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
|**Nombre**:  Luis  Angel  Olguin Castillo.  |**Nombre**: Erick Villa Beltrán.  |||
|**Puesto**: Enlace ACPPI.  |**Puesto**: Líder APE SDMA 6.  |||
|**Fecha**:  |**Fecha**:  |||
|||||
|||||
|**Firma 7** |**Firma 8**  |||
|||||
|**Nombre**:**  Juan  Carlos  Ayuso Bautista.  |<p>` `**Nombre**:  Angel  Horacio  López</p><p>Alcaraz. </p>|||
|**Puesto**:** Líder Técnico SDMA 6.  |**Puesto**:** Analista de Sistemas SDMA 6. |||
|**Fecha**:  |**Fecha**:  |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarNotificacionPago.docx** |Versión del template: 7.00||

![](Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.039.png)
Página 23 de 23 

[ref1]: Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.008.png
[ref2]: Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.011.png
[ref3]: Aspose.Words.4e411ff2-b877-4034-a732-c7e1ca1c9edd.034.png
