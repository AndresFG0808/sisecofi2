**Administración General de Comunicaciones ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.001.png)**

**y Tecnologías de la Información**

**Marco Documental 7.0**
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

**<ID Requerimiento>** 8309** 

**Nombre  del  Requerimiento:**  TI\_SISECOFI-SAT\_Seguimiento  financiero  y  control documental de proyectos de contratación** 

**Tabla de Versiones y Modificaciones** 



|Versión |Descripción del cambio |Responsable de la Versión |Fecha |
| - | - | :-: | - |
|*1* |*Creación del documento* |Aylín de la Concepción Caballero Weng |*11/03/2024* |
|*1.1* |*Revisión del documento* |Luis Angel Olguin Castillo |*19/04/2024* |
|*1.2* |*Versión aprobada para firma* |Andrés Mojica Vázquez |*07/06/2024* |

**Tabla de Contenido** 

[17_3083_ECU_GenerarDictamen ............................................................................................................. 2](#_page1_x83.00_y132.92)

1. [Descripción ........................................................................................................................................................ 2](#_page1_x102.00_y148.92)
1. [Diagrama del Caso de Uso ...................................................................................................................... 2](#_page1_x102.00_y225.92)
1. [Actores ................................................................................................................................................................. 2](#_page1_x102.00_y531.92)
4. [Precondiciones............................................................................................................................................... 2](#_page1_x102.00_y666.92)
4. [Post condiciones ........................................................................................................................................... 3](#_page2_x102.00_y293.92)
4. [Flujo primario .................................................................................................................................................. 3](#_page2_x102.00_y402.92)
4. [Flujos alternos .................................................................................................................................................8](#_page7_x102.00_y412.92)
4. [Referencias cruzadas............................................................................................................................... 20](#_page19_x102.00_y502.92)
4. [Mensajes .......................................................................................................................................................... 20](#_page19_x102.00_y674.92)
4. [Requerimientos No Funcionales .................................................................................................... 21](#_page20_x102.00_y468.92)
4. [Diagrama de actividad .......................................................................................................................... 24](#_page23_x102.00_y133.92)
4. [Diagrama de estados ............................................................................................................................. 25](#_page24_x102.00_y133.92)
4. [Aprobación del cliente .......................................................................................................................... 26](#_page25_x102.00_y133.92)



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page1_x83.00_y132.92"></a>17\_3083\_ECU\_GenerarDictamen  

1. **Descripción<a name="_page1_x102.00_y148.92"></a>  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.002.png)**

El objetivo de este Caso de Uso es permitir al Empleado SAT generar, editar “Datos generales”  y consultar un dictamen relacionado a un contrato. 

2. **Diagrama<a name="_page1_x102.00_y225.92"></a> del Caso de Uso ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.003.png)**

![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.004.jpeg)

3. **Actores<a name="_page1_x102.00_y531.92"></a>  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.005.png)**



|**Actor** |**Descripción** |
| - | - |
|**Empleado SAT** |El  Empleado  SAT  tiene  el  o  los  roles  otorgados  por  la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema. |

4. **Precondiciones<a name="_page1_x102.00_y666.92"></a>** ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.006.png)
- El Empleado SAT se ha autenticado en el sistema con e.firma válida. 
- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa. 
- Se le ha asignado los roles requeridos al Empleado SAT para ingresar, insertar 
- editar al módulo “Dictamen”. ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.007.png)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |



- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar al módulo “Dictamen”. 
- El Empleado SAT ha sido asignado a un proyecto o como participante en la administración de un contrato. 
- El empleado SAT ha seleccionado  alguna de las siguientes opciones: Alta dictamen,  Editar  dictamen  o  Ver  dictamen  en  el  módulo  “Consumo  de Servicios” relacionados a un contrato.** 
- El  empleado  SAT  ha  seleccionado  una  opción  en  el  campo  Convenio  de Colaboración del contrato.** 
- El empleado SAT ha registrado la plantilla activa de verificación para la carga de documentos en la sección Gestión documental.** 
5. **Post<a name="_page2_x102.00_y293.92"></a> condiciones  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.008.png)**El Empleado SAT: 
- Registró un dictamen. 
- Modificó los “Datos Generales” de un dictamen registrado. 
- Consultó la información de un dictamen registrado. 
- Se asignó la plantilla documental para el dictamen. 
6. **Flujo<a name="_page2_x102.00_y402.92"></a> primario** ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.009.png)![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.010.png)



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  Caso  de  Uso  inicia  cuando  el </p><p>Empleado  SAT  ingresa  al  menú **“Consumo  de  Servicios”**  y selecciona  la  opción  **“Alta  de dictamen”**  desde  el **(17\_3083\_ECU\_AdministrarDeve ngado)** y continúa en el flujo. </p><p>- Si  selecciona  la  opción **“Editar”**  desde  el  módulo **“Consumo  de  servicios”** **(17\_3083\_ECU\_AdministrarD evengado)**,   continúa  en  el [**(FA11)**](#_page16_x102.00_y518.92).** </p><p>- Si  selecciona  la  opción  **“Ver detalle”**  desde  el  módulo **“Consumo  de  servicios”** **(17\_3083\_ECU\_AdministrarD evengado)**,  continúa  en  el [**(FA11)**](#_page16_x102.00_y518.92)  y  aplica  la  regla  de negocio **(RNA167)**.** </p>|<p>2\.  Consulta en la base de datos BD la </p><p>siguiente información: </p><p>- Nombre corto del contrato </p><p>- Número del contrato </p><p>- Proveedor </p><p>Catálogos correspondientes a los campos:  </p><p>- Periodo de control  </p><p>- IVA </p>|
||3\.  Muestra los siguientes datos en la pantalla  “Consumo  de  Servicios  - |

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

Dictamen”**  de  acuerdo  con  las ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.011.png)**(RNA01)** y **(RNA87)**. 

Campo: 

- Última modificación 

Sección: “Datos generales” 

- Id 
- Nombre corto del contrato 
- Número de contrato  
- Proveedor 
- Estatus 
- Periodo de inicio\* 
- Periodo fin\* 
- Periodo de control\* 
- IVA\*  
- Tipo de cambio referencial\* (inhabilitado).  Aplica  la **(RNA126)** 
- Descripción 

  ` `Opciones:  

- Duplicar  dictamen 

  (inhabilitada)  ![ref1] **(RNA99)** 

- Dictamen  anterior  ![ref2] **(RNA118)** 
- Dictamen  posterior  ![ref3] **(RNA118)** 
- Cancelar  dictamen 

  (inhabilitada) ![ref4]

- Inicial  

Tabla:  “Resumen  consolidado” (inhabilitado). Aplica la **(RNA105)** y **(RNA244)**. 

- Fase  (Dictaminado  y Facturado) 
- Subtotal 
- Deducciones 
- IEPS 
- IVA 
- Otros impuestos 
- Total 
- Total en pesos 

  Opciones. Aplica la **(RNA246)**. 

- Exportar  a  Excel  ![ref5] (Inhabilitada) 
- Cancelar  
- Guardar  

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

Secciones inhabilitadas: ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.017.png)

- “Registro  de  servicios dictaminados”  
- “Penas contractuales” 
- “Penas convencionales” 
- “Deducciones” 
- “Soporte  documental  de dictamen” 
- “Deducciones / descuentos / penalizaciones”  
- “Solicitud de factura” 
- “Facturas”  
- “Notas de crédito”  
- “Solicitud de pago” 
- “Gestión documental”  

  Opción:  

- Regresar 

Ver **(17\_3083\_EIU\_GenerarDictamen)**  Estilos 01. 

4. Ingresa<a name="_page4_x119.00_y402.92"></a>  la  información  de  los  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.018.png)![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.019.png)siguientes campos:  
- Periodo de inicio\*  
- Periodo fin\*  
- Periodo de control\*  
- IVA\*   
- Tipo  de  cambio  referencial\*  
- Descripción  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.020.png)
5. Selecciona la opción **“Guardar”** y <a name="_page4_x315.00_y524.92"></a> 6.  Valida que se hayan ingresado los continúa en el flujo.  campos  obligatorios  de  acuerdo con la **(RNA03)**.   
- En caso de que seleccione la opción **“Regresar”**, continúa  ￿  En caso contrario, continúa en en el [**(FA07)**](#_page14_x102.00_y234.92).  el [**(FA08)**](#_page14_x102.00_y495.92).** 
- Si  selecciona  la  opción 

  **“Cancelar”**,  continúa  en  el 

  [**(FA12)**](#_page18_x102.00_y507.92). 

7. Valida  la  estructura  de  los  datos ingresados en los campos. Aplica la **(RNA255)**. 
- En caso de que la estructura de los  datos  ingresados  sea incorrecta,  continúa  en  el [**(FA14)**](#_page19_x102.00_y294.92).  

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

8. Valida  que  el  periodo  inicio  y  el ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.021.png)periodo  fin  sean  correctos  de acuerdo  con  las  **(RNA103)**  y **(RNA102)**. 
   1. En caso contrario, continúa en el [**(FA01)**](#_page7_x102.00_y452.92). 
9. Valida  que  el  periodo  de  control seleccionado  esté  dentro  del “Periodo de inicio” y “Periodo fin” de acuerdo con la **(RNA101)**.   
   1. En caso contrario, continúa en el [**(FA02)**](#_page8_x102.00_y232.92). 
10. Genera  de  forma  automática  la información  de  los  siguientes campos  de  acuerdo  con  las **(RNA87)** y **(RNA115)**: 
- Id 
- Estatus ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.022.png)
11. Almacena en la BD la información de las Pistas de Auditoría. 

    Datos que se almacenan: 

    **Módulo**=  Dictamen  –  Datos generales 

    **Fecha y Hora**= Fecha y hora del sistema,  usando  el  formato DD/MM/AAAA HH:MM:SS 

    **RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema 

    **Tipo de movimiento**= **INSR** (insertar) **UPDT** (Modificar). **Movimiento**=  Aplica  la **(RNA239)**. 

- Id dictamen 
- En caso de que no se puedan almacenar  las  Pistas  de Auditoría,  continúa  en  el [**(FA09)**](#_page15_x102.00_y133.92). 
12. Almacena  en  la  BD  la  siguiente información  del  dictamen.  Aplica las **(RNA247)** y **(RNA262)**. 

    Datos generales: 

- Id (dictamen) 
- Nombre corto del contrato 
- Número de contrato 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |



- Proveedor ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.023.png)
- Estatus 
- Periodo de inicio 
- Periodo fin 
- Periodo de control 
- IVA 
- Tipo de cambio referencial 
- Descripción 
  13. Muestra el mensaje [**(MSG001)**](#_page19_x113.00_y735.92)** con la opción “Aceptar”. 
14. Selecciona la opción **“Aceptar”**.  15.  Cierra el mensaje.  
16. Recarga<a name="_page6_x315.00_y278.92"></a>  la  pantalla  y  muestra  el botón Inicial. Aplica la **(RNA256)**. 

    Habilita  la  sección  “Registro  de servicios  dictaminados”  y  las opciones  “Duplicar  dictamen”, “Dictamen  anterior”,  “Dictamen posterior”,  “Exportar  a  Excel”  y “Cancelar dictamen”.  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.024.png)

17. Realiza<a name="_page6_x119.00_y388.92"></a><a name="_page6_x141.00_y388.92"></a>  una  de  las  siguientes  18.  Fin del Caso de Uso.  

    acciones: 

- Si  selecciona  la  opción **“Duplicar  dictamen”**, continúa en el [**(FA03)**](#_page8_x102.00_y499.92). 
- Si  selecciona  la  opción **“Cancelar  dictamen”**, continúa en el [**(FA04)**](#_page12_x101.00_y170.92). 
- Si  selecciona  la  opción **“Dictamen  anterior”**  o **“Dictamen  posterior”**, continúa en el [**(FA05)**](#_page13_x102.00_y452.92). 
- Si  selecciona  la  opción **“Exportar a Excel”**, continúa en el [**(FA10)**](#_page15_x102.00_y531.92). 
- Si  ingresa  a  las  secciones **“Registro  de  servicios dictaminados**”,  **Penas contractuales**,  **“Penas convencionales**”, **“Deducciones**”,  **”Soporte documental  del  dictamen”** continúa  en  el  **(17\_3083\_ECU\_RegistrarSer viciosDictaminados)**. 
- Si  ingresa  a  la  sección “**Deducciones/descuentos y penalizaciones”**  o **“Solicitud  de  Factura”**, continúa  en  el 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

**(17\_3083\_ECU\_EmitirInform acionProforma)![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.025.png)**. 

- Si  ingresa  a  la  sección **“Facturas”**,**  continúa  en  el **(17\_3083\_ECU\_GenerarFact ura)**. 
- Si ingresa a la sección **“Notas de  crédito”**,  continúa  en  el **(17\_3083\_ECU\_GenerarNota DeCredito)**. 
- Si  ingresa  a  la  sección **“Solicitud  de  pago”**, continúa  en  el **(17\_3083\_ECU\_GenerarNotif icacionPago)**. 
- Si entra a la sección **“Gestión Documental”**,** continúa en el **(17\_3083\_ECU\_GestiónDocu mental)**. 
- Si  selecciona  la  opción **“Regresar”**,  continúa  en  el [**(FA07)**](#_page14_x102.00_y234.92). 

<a name="_page7_x102.00_y412.92"></a>**7. Flujos alternos  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.026.png)![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.027.png)**

<a name="_page7_x102.00_y452.92"></a>**FA01 Periodo de inicio o periodo fin incorrectos** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA01**  inicia  cuando  el  sistema </p><p>identifica  que  el  periodo  inicio  o periodo fin son incorrectos. </p>|
||<p>2\.  Muestra  el  mensaje  de  acuerdo </p><p>con lo siguiente: </p><p>- Si  el  periodo  de  inicio  es incorrecto  muestra  el [**(MSG002)**](#_page20_x113.00_y139.92). </p><p>- Si  el  periodo  de  fin  es incorrecto  muestra  el [**(MSG003)**](#_page20_x113.00_y163.92). </p><p>Cada  mensaje  se  muestra  con  la opción “Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje.  |
||5\.  Realiza lo siguiente: |

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>- Si se invoca en el paso  8 del Flujo primario, retorna al paso [**4** ](#_page4_x119.00_y402.92)del Flujo primario. </p><p>- Si se invoca en el paso 12 del **(FA03)**,  retorna  al  paso  **8**  del **(FA03)**. </p>|
| :- | - |

<a name="_page8_x102.00_y232.92"></a>**FA02 El periodo control no está dentro del periodo de inicio y periodo fin** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El **FA02** inicia cuando se identifica </p><p>que el “Periodo de control” no está dentro  del  “Periodo  de  inicio”  y “Periodo fin”. </p>|
||<p>2\.  Muestra  el  **[**(MSG004)**](#_page20_x113.00_y186.92)**  con  la </p><p>opción “Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje.  |
||<p>5\.  Realiza lo siguiente: </p><p>- Si se invoca en el paso 9 del Flujo primario, retorna al paso [**4** ](#_page4_x119.00_y402.92)del Flujo primario. </p><p>- Si se invoca en el paso 13 del **(FA03)**,  retorna  al  paso [` `**8** ](#_page10_x112.00_y503.92) del **(FA03)**. </p>|

<a name="_page8_x102.00_y499.92"></a>**FA03 Selecciona la opción “Duplicar dictamen”  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.028.png)**



|**Actor** |**Sistema** ||||
| - | - | :- | :- | :- |
|<p>1\.  El **FA03** inicia cuando el Empleado </p><p>SAT  selecciona  la  opción **“Duplicar dictamen”**. </p>|<p>2\.  Duplica la sección: </p><p>o  “Datos generales” </p>||||
||<p>3\.  Muestra  en  una  ventana </p><p>emergente  un  cuadro  de selección  con  las  siguientes opciones: </p><p>Secciones a duplicar: </p><p>- “Registro  de  servicios dictaminados” </p><p>- “Penas contractuales” </p><p>- “Penas convencionales” </p><p>- “Deducciones”. </p><p>Opciones:  </p><p>- Aceptar </p><p>- Cancelar </p>||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|||

- Cerrar ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.029.png)

Ver **(17\_3083\_EIU\_GenerarDictamen)**  Estilos 03. 

4. Elije  las  secciones  que  se  van  a duplicar. 
4. Selecciona la opción **“Aceptar”** y  6.  Consulta en la BD la información continúa en el flujo.  de  las  secciones  que  se 
- En  caso  de  seleccionar  la  seleccionaron para duplicar.  opción **“Cancelar”** o **“Cerrar”** 

  regresa  al  paso [` `**16** ](#_page6_x315.00_y278.92) del  Flujo 

  primario. 

7. Muestra la pantalla con los datos. ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.030.png)Sección:“ Datos generales” 

   En modo lectura 

- Id  
- Nombre corto del contrato 
- Número de contrato  
- Proveedor 
- Estatus  

  En modo edición 

- Periodo de inicio\* 
- Periodo fin\* 
- Periodo de control\* 
- IVA\* 
- Tipo  de  cambio referencial\*  
- Descripción 

  Opciones: 

- Duplicar  dictamen (inhabilitada) ![ref6]
- Dictamen  anterior  ![ref7] (inhabilitado) 
- Dictamen  posterior  ![ref8] (inhabilitada) 
- Cancelar  dictamen 

  (inhabilitada) ![ref9]

  Tabla “Resumen consolidado”. Aplica la **(RNA105)** y **(RNA167)** 

- Fase  (Dictaminado  y Facturado) 
- Subtotal 
- Deducciones 
- IEPS 
- IVA 
- Otros impuestos 
- Total 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |



- Total en pesos ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.035.png)Opciones: 
- Exportar  a  Excel  ![ref10] (inhabilitada) 
- Cancelar  
- Guardar  

  Secciones colapsadas:  

- “Registro  de  servicios dictaminados”  
- “Penas contractuales” 
- “Penas convencionales” 
- “Deducciones” 
- “Soporte  documental  del dictamen” 
- “Deducciones  / descuentos  / penalizaciones”  
- “Solicitud de factura” 
- “Facturas”  
- “Notas de crédito”  
- “Solicitud de pago” 
- “Gestión documental”  

  ` `Opción: 

- Regresar 

Ver **(17\_3083\_EIU\_GenerarDictamen)** Estilos 01.

8. Modifica<a name="_page10_x112.00_y503.92"></a>  la  información  de  la sección “Datos generales”. ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.037.png)
8. Selecciona la opción **“Guardar”** y  10.  Valida que se hayan ingresado los continúa en el flujo.  campos  obligatorios  de  acuerdo con la **(RNA03)**.   
- Si  selecciona  la  opción 

  **“Cancelar”**, regresa al paso [**17** ](#_page6_x141.00_y388.92) ￿  En caso contrario, continúa en del Flujo primario.**   el [**(FA08)**](#_page14_x102.00_y495.92). 

- Si  selecciona  la  opción 

  **“Regresar”**,  continúa  en  el 

  [**(FA07)**](#_page14_x102.00_y234.92).**  

11. Valida  la  estructura  de  los  datos ingresados en los campos. Aplica la **(RNA255)**. 
- En caso de que la estructura de  los  datos  ingresados  sea incorrecta,  continúa  en  el [**(FA14)**](#_page19_x102.00_y294.92). 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

12. Valida  que  el  periodo  inicio  y  el ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.038.png)periodo  fin  sean  correctos  de acuerdo  con  las  **(RNA103)**  y **(RNA102)**. 
    1. En caso contrario, continúa en el [**(FA01)**](#_page7_x102.00_y452.92). 
13. Valida  que  el  periodo  de  control seleccionado  esté  dentro  del “Periodo de inicio” y “Periodo fin” de acuerdo con la **(RNA101)**.   
    1. En caso contrario, continúa en el [**(FA02)**](#_page8_x102.00_y232.92). 
14. Genera  de  forma  automática  la información  de  los  siguientes campos  de  acuerdo  con  las **(RNA87)** y **(RNA115)**: 
- Id 
- Estatus ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.039.png)
15. Almacena en la BD la información de las Pistas de Auditoría. 

    Datos que se almacenan: 

    **Módulo**=  Dictamen  –  Datos generales 

    **Fecha  y  Hora**=  Fecha  y  hora del sistema, usando el formato DD/MM/AAAA HH:MM:SS 

    **RFC  Usuario**=  RFC  largo  del Empleado SAT que ingresó al sistema 

    **Tipo de movimiento**= **INSR** (insertar) **UPDT** (Modificar). **Movimiento**=  Aplica  la **(RNA239)**. 

- Id dictamen 
- En caso de que no se puedan almacenar  las  Pistas  de Auditoría,  continúa  en  el [**(FA09)**](#_page15_x102.00_y133.92). 
  16. Almacena en la BD la información del dictamen y las secciones que se  eligieron  a  duplicar.  Aplica  la **(RNA247)**. 
  16. Muestra el mensaje [**(MSG001)**](#_page19_x113.00_y735.92)** con la opción “Aceptar”. 
18. Selecciona la opción **“Aceptar”**.  19.  Cierra el mensaje. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

21\.  Continúa  en  el  paso [` `**17** ](#_page6_x141.00_y388.92) del  Flujo ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.040.png)primario.** 

<a name="_page12_x101.00_y170.92"></a>**FA04 Selecciona la opción “Cancelar dictamen”  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.041.png)**



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA04**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción **“Cancelar dictamen”**. </p>|<p>2\.  Valida que no existan facturas y/o </p><p>notas  de  crédito.  Aplica  la **(RNA87)**, y continúa en el flujo. </p><p>En caso de que existan facturas y/o notas de crédito no canceladas, continúa en el [**(** ](#_page18_x102.00_y744.92)</p><p>[￿  **FA13)**](#_page18_x102.00_y744.92). </p>|
||<p>3\.  Muestra  el  **[**(MSG014)**](#_page20_x113.00_y423.92)**  con  las </p><p>opciones “Sí” y “No”. </p>|
|<p>4\.  Selecciona  la  opción  **“Sí”**  y </p><p>continúa en el flujo. </p><p>￿  En  caso  de  que  seleccione </p><p>**“No”**, continúa en el paso [**17** ](#_page6_x119.00_y388.92)del Flujo primario. </p>|<p>5\.  Muestra una ventana emergente </p><p>“Justificación de la cancelación del dictamen”. </p><p>Justificación de la cancelación del dictamen  </p><p>Opciones:  </p><p>- Aceptar </p><p>- Cerrar </p><p>Ver </p><p>**(17\_3083\_EIU\_GenerarDictamen)**         Estilos 02. </p>|
|<p>6\.  Agrega  la  justificación  de  la </p><p>cancelación del dictamen. </p>||
|<p>7\.  Selecciona la opción **“Aceptar”** y </p><p>continúa en el flujo. </p><p>￿  En  caso  de  seleccionar  la </p><p>opción **“Cerrar”** regresa al paso[` `**16** ](#_page6_x315.00_y278.92)del Flujo primario. </p>|<p>8\.  Actualiza el estatus del dictamen </p><p>de  acuerdo  con  la  **(RNA87)**  y **(RNA247)**. </p>|
||<p>9\.  Concatena el prefijo “Motivo de la </p><p>cancelación:”,  la  justificación  y separado  por  pipe  la “Descripción”,  mostrándolo  en  el campo “Descripción”. </p>|
||<p>10\.  Almacena en la BD la información </p><p>de las Pistas de Auditoría. </p><p>Datos que se almacenan:  </p><p>**Módulo**= Dictamen </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>Fecha y Hora = Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS </p><p>**RFC  Usuario**=  RFC  largo  del Empleado SAT que ingresó al sistema. </p><p>**Tipo  de  movimiento**=  **UPDT** (modificar) </p><p>**Movimiento**=  Aplica  la **(RNA239)**. </p><p>- Id del dictamen </p><p>￿  En  caso  de  que  no  se </p><p>puedan  almacenar  las Pistas  de  Auditoría, continúa en el [**(FA09)**](#_page15_x102.00_y133.92). </p>|
| :- | - |
||<p>11\.  Almacena en la BD la información </p><p>del dictamen. </p><p>- Estatus </p><p>- Descripción </p>|
||<p>12\.  Cambia  todas  las  secciones  del </p><p>dictamen  a  modo  solo  lectura. Aplica la **(RNA167)**. </p>|
||13\.  Fin del Caso de Uso. |

<a name="_page13_x102.00_y452.92"></a>**FA05 Selecciona la opción Dictamen anterior o posterior** 



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El **FA05** inicia cuando el Empleado </p><p>SAT  selecciona  la  opción **“Dictamen  anterior”**  o **“Dictamen posterior”**. </p>|<p>2\.  Consulta  en  la  BD  y  obtiene  la </p><p>información del dictamen. Aplica la **(RNA118)**. </p>|
||<p>3\.  Valida  que  se  encuentre </p><p>información registrada, y continúa en el flujo. </p><p>￿  En  caso  de  no  contar  con </p><p>información,  continúa  en  el [**(FA06)**](#_page13_x102.00_y699.92).** </p>|
||<p>4\.  Muestra  la  pantalla  con  la </p><p>información  del  dictamen  en modo lectura. Aplica la **(RNA167)**. </p>|
||5\.  Fin del Caso de Uso. |

<a name="_page13_x102.00_y699.92"></a>**FA06 No existe información de dictamen** 

**Actor  Sistema ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.042.png)**

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||1\.  El  **FA06** inicia cuando  se identifica que  no  existe  información  del dictamen. |
| :- | - |
||2\. Muestra el [**(MSG007)**](#_page20_x113.00_y253.92)** con la opción “Aceptar”. |
|3\. Selecciona la opción **“Aceptar”**. |4\. Cierra el mensaje. |
||5\. Retorna al pas[o **17** ](#_page6_x119.00_y388.92)del Flujo primario. |

<a name="_page14_x102.00_y234.92"></a>**FA07 Selecciona la opción “Regresar”** 



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El **FA07** inicia cuando el Empleado </p><p>SAT  selecciona  la  opción **“Regresar”**. </p>|<p>2\.  Muestra  el  **[**(MSG008)**](#_page20_x113.00_y275.92)**  con  las </p><p>opciones  “Sí”  y  “No”,  y  el  flujo continúa. </p>|
|<p>3\.  Selecciona  la  opción  **“Sí”**  y </p><p>continúa en el flujo. </p><p>￿  En  caso  de  que  seleccione </p><p>**“No”**,  regresa  al  paso  donde fue invocado. </p>|4\.  Cierra el mensaje. |
||<p>5\.  Regresa a la pantalla “Consumo de </p><p>servicios”. </p><p>Ver **(17\_3083\_EIU\_AdministrarDeven gado)**  </p><p>Estilos 02.</p>|
||6\.  Fin del Caso de Uso. |

<a name="_page14_x102.00_y495.92"></a>**FA08 Se identifica que no se ingresaron los datos obligatorios ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.043.png)**



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA08**  inicia  cuando  el  sistema </p><p>identifica que no se ingresaron los datos obligatorios. </p>|
||<p>2\.  Muestra  en  rojo  los  campos </p><p>pendientes de capturar. </p>|
||<p>3\.  Muestra  el  **[**(MSG009)**](#_page20_x113.00_y299.92)**,  con  la </p><p>opción “Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje.  |
||<p>6\.  Realiza lo siguiente: </p><p>- Si  se  invoca  en  el  paso  6  del Flujo primario, retorna al paso [**4** ](#_page4_x119.00_y402.92)del Flujo primario. </p><p>- Si se invoca en el paso 10 del [**(FA03)**](#_page8_x102.00_y499.92),  retorna  al  paso [` `**8** ](#_page10_x112.00_y503.92) del **(FA03)**. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page15_x102.00_y133.92"></a>**FA09 No se pueden almacenar las Pistas de Auditoría  ![ref11]**



|**Actor** |**Sistema** |
| - | - |
||1\.  El  **FA09**  inicia  cuando  interviene un evento ajeno y no se pueden almacenar las Pistas de Auditoría.**  |
||<p>2\.  Cancela  la  operación  sin </p><p>completar  el  movimiento  que estaba en proceso. </p>|
||<p>3\.  Muestra  el  mensaje  de  acuerdo </p><p>con lo siguiente: </p><p>- Si la pista de auditoría es por el tipo  de  movimiento  **UPDT**  e **INSR** se muestra el [**(MSG010)**](#_page20_x113.00_y324.92). </p><p>- Si la pista de auditoría es por el tipo de movimiento **CNST**, se muestra el **[**(MSG012)**](#_page20_x113.00_y373.92)**.  </p><p>- En  caso  de  que  la  pista  de auditoría  es  por  el  tipo  de movimiento **PRNT**, se muestra el **[**(MSG013)**](#_page20_x113.00_y398.92)**.  </p><p>Cada mensaje se muestra con la opción “Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje . |
||<p>6\.  Regresa al paso previo que detona </p><p>la acción de la pista de auditoría. </p>|

<a name="_page15_x102.00_y531.92"></a>**FA10 Selecciona la opción “Exportar a Excel” ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.045.png)**



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA10**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción **“Exportar a Excel”**. </p>|<p>2\.  Valida  la  información  de  la  tabla </p><p>“Resumen  consolidado”.  Aplica  la **(RNA244)**, y continúa en el paso [**5** ](#_page15_x307.00_y715.92)de este flujo. </p><p>￿  En  caso  de  no  encontrar información  mostrará  el [**(MSG005)**](#_page20_x113.00_y209.92),**  con  la  opción “Aceptar” y continúa en el flujo. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |<p>4\.  Cierra el mensaje y continúa en el </p><p>pas[o **9** ](#_page16_x307.00_y489.92)de este flujo. </p>|
||<p><a name="_page15_x307.00_y715.92"></a>5.  Almacena en la BD la información </p><p>de las Pistas de Auditoría. </p><p>Datos que se almacenan:  </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

**Módulo**=  Dictamen  –  Datos ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.046.png)generales-  Resumen consolidado 

**Fecha y Hora** = Fecha y hora del sistema usando el formato DD/MM/AAAA HH:MM:SS 

**RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. 

**Tipo  de  movimiento**=  **PRNT** (Imprimir) 

**Movimiento**=  Aplica  la **(RNA239)**. 

-Id dictamen  

1. En caso de  que  no se  puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA09)**](#_page15_x102.00_y133.92). 
6. Obtiene<a name="_page16_x307.00_y353.92"></a> la información de la tabla “Resumen consolidado” conforme a lo siguiente: 
   1. Si se invoca en el paso [` `**17** ](#_page6_x141.00_y388.92)del Flujo  primario.  Aplica  la **(RNA254)**. 
7. Genera  un  archivo  Excel  con  la extensión (.xlsx) con la información obtenida en el pas[o **6** ](#_page16_x307.00_y353.92)de este flujo.  
7. Descarga el archivo xlsx.  
7. Fin<a name="_page16_x307.00_y489.92"></a> del Caso de Uso. 

<a name="_page16_x102.00_y518.92"></a>**FA11 Selecciona la opción “Editar dictamen” ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.047.png)**



|**Actor** |**Sistema** ||||
| - | - | :- | :- | :- |
|<p>1\.  El **FA11** inicia cuando el Empleado </p><p>SAT  selecciona  **“Editar”**  o   **“Ver detalle”**  desde  el  módulo **“Consumo  de  servicios”** **(17\_3083\_ECU\_AdministrarDeve ngado)**. </p>|<p>2\.  Identifica el rol del Empleado SAT </p><p>que  ingresa  para  presentar  de forma correcta la pantalla. Aplica las **(RNA51)**, **(RNA151)**, **(RNA167)** y **(RNA142)**. </p>||||
||<p>3\.  Obtiene de  la BD la información </p><p>del dictamen seleccionado. Aplica la **(RNA105)**. </p><p>o  “Datos generales” </p>||||
||<p>4\.  Despliega  la  pantalla  con  la </p><p>información  del  dictamen precargada.  Aplica  la  **(RNA01)**, **(RNA87)** y **(RNA126)**. </p>||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|||

“Datos generales”  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.048.png)

En modo lectura 

- Id  
- Estatus 
- Nombre corto del contrato 
- Número de contrato  
- Proveedor 

  En  caso  de  seleccionar  “Ver detalle”  los  siguientes  campos estarán en modo lectura. 

  En caso de seleccionar “Editar” o “Duplicar dictamen” los siguientes campos se podrán editar. 

- Periodo de inicio\* 
- Periodo fin\* 
- Periodo de control\* 
- IVA\* 
- Tipo de cambio referencial\*  
- Descripción 

`    `Opciones: 

- Duplicar  dictamen (inhabilitada) ![ref1]
- Dictamen anterior![ref2] Aplica la **(RNA118)** 
- Dictamen posterior  ![ref3] Aplica la **(RNA118)** 
- Cancelar  dictamen  ![ref4]  Aplica la **(RNA107)**  

  Tabla: “Resumen consolidado”. Aplica la **(RNA244)** 

  (Fase)  Dictaminado  y facturado 

- Subtotal 
- Deducciones 
- IEPS 
- IVA 
- Otros impuestos 
- Total 
- Total en pesos 

  Opciones: 

- Exportar a Excel ![ref5]
- Cancelar. Aplica la **(RNA167)** 
- Guardar. Aplica la **(RNA167)** 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>- Regresar </p><p>Secciones (colapsadas) </p><p>- “Registro  de  servicios dictaminados”  </p><p>- “Penas contractuales” </p><p>- “Penas convencionales” </p><p>- “Deducciones” </p><p>- “Soporte  documental  del dictamen” </p><p>- “Deducciones  /  descuentos  / penalizaciones”  </p><p>- “Solicitud de factura” </p><p>- “Facturas”  </p><p>- “Notas de crédito”  </p><p>- “Solicitud de pago” </p><p>- “Gestión documental”  </p><p>Ver **(17\_3083\_EIU\_GenerarDictamen)**  Estilos 01. </p>|
| :- | - |
|5\.  Modifica los datos que requiera. ||
|<p>6\.  Selecciona la opción **“Guardar”** y </p><p>continúa  en  el  paso [` `**6** ](#_page4_x315.00_y524.92) del  Flujo primario. </p><p>￿  Si  selecciona  la  opción </p><p>**“Cancelar”**,  continúa  en  el [**(FA12)**](#_page18_x102.00_y507.92). </p>|7\.  Fin del Caso de Uso. |

<a name="_page18_x102.00_y507.92"></a>**FA12 Selecciona la opción “Cancelar” ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.049.png)**



|**Actor<a name="_page18_x102.00_y744.92"></a>** |**Sistema** |
| - | - |
|<p>1\.  El **FA12** inicia cuando el Empleado </p><p>SAT  selecciona  la  opción **“Cancelar”**. </p>|<p>2\.  Muestra  el  **[**(MSG008)**](#_page20_x113.00_y275.92)**  con**  las </p><p>opciones “Sí” y No”.**  </p>|
|<p>3\.  Selecciona la opción **“Sí**” y el flujo </p><p>continúa.  </p><p>￿  En  caso  de  seleccionar  **“No”**, continúa en el paso **6** de este flujo.  </p>|4\.  Cierra la ventana emergente.  |
||<p>5\.  Inicializa los campos de la pantalla </p><p>en donde se selecciona la opción dejándolos como en un inicio, no almacena ninguna información.  </p>|
||<p>6\.  Permanece  en  la  sección  donde </p><p>fue invocado.  </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

**FA13 Se encontraron facturas y notas de crédito asociadas ![ref11]**



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El **FA13** inicia cuando se identifica </p><p>que  existen  facturas  y  notas  de crédito asociadas. </p>|
||<p>2\.  Muestra el [**(MSG006)**](#_page20_x113.00_y231.92)** con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje. |
||<p>5\.  Retorna  al  paso [` `**17** ](#_page6_x141.00_y388.92) del  Flujo </p><p>primario. </p>|

<a name="_page19_x102.00_y294.92"></a>**FA14 Estructura de datos incorrecta** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El **FA14** inicia cuando identifica que </p><p>la  estructura  de  los  datos ingresados es incorrecta. </p>|
||<p>2\.  Muestra en rojo los campos con la </p><p>estructura incorrecta. </p>|
||<p>3\.  Muestra el [**(MSG011)**](#_page20_x113.00_y349.92) con la opción </p><p>“Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje. |
||<p>6\.  Retorna  al  paso [` `**4** ](#_page4_x119.00_y402.92) del  Flujo </p><p>primario. </p>|

8. **Referencias<a name="_page19_x102.00_y502.92"></a> cruzadas  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.050.png)**
- 17\_3083\_CRN\_SeguimientoFinancieroYControl 
- 17\_3083\_EIU\_GenerarDictamen 
- 17\_3083\_ECU\_RegistrarServiciosDictaminados 
- 17\_3083\_ECU\_EmitirProforma 
- 17\_3083\_ECU\_GenerarFactura 
- 17\_3083\_ECU\_GenerarNotaDeCredito 
- 17\_3083\_ECU\_GenerarNotificacionPago 
- 17\_3083\_ECU\_ AdministrarDevengado 
- 17\_3083\_EIU\_AdministrarDevengado 
- 17\_3083\_ECU\_GestionDocumental 
9. **Mensajes<a name="_page19_x102.00_y674.92"></a>  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.051.png)**

   **ID Mensaje  Descripción ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.052.png)**

<a name="_page19_x113.00_y735.92"></a>**MSG001**  Se guardó correctamente la información. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |



|**MSG002** |<a name="_page20_x113.00_y139.92"></a>El  periodo  de  inicio  es  incorrecto.  Por  favor  verifique  la información. |
| - | :- |
|<a name="_page20_x113.00_y163.92"></a>**MSG003** |El periodo fin es incorrecto. Por favor verifique la información. |
|**MSG004** |<a name="_page20_x113.00_y186.92"></a>El periodo de control debe estar dentro del periodo de inicio y periodo fin del contrato. |
|<a name="_page20_x113.00_y209.92"></a>**MSG005** |No se encontró información para exportar. |
|<a name="_page20_x113.00_y231.92"></a>**MSG006** |Debe cancelar las facturas y/o notas de crédito. |
|<a name="_page20_x113.00_y253.92"></a>**MSG007** |No existe información del dictamen. |
|<a name="_page20_x113.00_y275.92"></a>**MSG008** |Se perderá la información capturada ¿Desea continuar? |
|**MSG009** |<a name="_page20_x113.00_y299.92"></a>Favor de ingresar los datos obligatorios marcados con un asterisco (\*). |
|**MSG010** |<a name="_page20_x113.00_y324.92"></a>Ocurrió  un  error  al  guardar  la  información,  favor  de  intentar nuevamente (PA01). |
|**MSG011** |<a name="_page20_x113.00_y349.92"></a>La estructura de la información ingresada es incorrecta.  Intente nuevamente. |
|**MSG012** |<a name="_page20_x113.00_y373.92"></a>Ocurrió  un  error  al  consultar  la  información,  favor  de  intentar nuevamente (PA01).  |
|**MSG013** |<a name="_page20_x113.00_y398.92"></a>Ocurrió  un  error  al  exportar  la  información,  favor  de  intentar nuevamente (PA01).  |
|**MSG014** |<a name="_page20_x113.00_y423.92"></a>El  dictamen  pasará  a  estatus  cancelado.  ¿Está  seguro  de continuar? |

10. **Requerimientos<a name="_page20_x102.00_y468.92"></a> No Funcionales  ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.053.png)**



|**ID RNF** |**Requerimiento No Funcional** |**Descripción** |||
| - | :-: | - | :- | :- |
|**RNF001** |Disponibilidad  |El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.|||
|**RNF002** |Concurrencia  |<p>El número de Empleados SAT que puede tener el sistema son 150. </p><p>￿  El  número  máximo  de  accesos </p><p>concurrentes  que  debe  soportar  este sistema son 30 Empleados SAT.</p>|||
|**RNF003** |Seguridad  |El acceso solo podrá ser otorgado al Empleado SAT  que  tenga  los  roles  asignados  por  la Administración Central de Seguridad, Monitoreo y  Control  (ACSMC)  para  cada  módulo  de  este sistema. |||
|**RNF004** |Usabilidad  |<p>El  sistema  deberá  manejar  los  siguientes elementos para facilitar la navegación: </p><p>￿  Mensajes  tipo  flotantes  (tooltips)  con información de la herramienta que ofrece </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|||



|||<p>ayuda  contextual  como  guía  para  el Empleado SAT. </p><p>- Componente  de  ordenamiento  que permita  acomodar  la  información  de  la tabla de forma ascendente o descendente, considerando  la  columna  en  la  que  es seleccionado. </p><p>- Contar  con  un  diseño  responsivo  que permita  su  óptima  visualización  en distintos tipos de dispositivos finales. </p>|||
| :- | :- | :- | :- | :- |
|**RNF005** |Eficiencia  |Las consultas se dividen en generales y detalladas, para que las  detalladas carguen la información solo  cuando  sean  requeridas  por  el  Empleado SAT. |||
|**RNF006** |Usabilidad  |<p>El Empleado SAT podrá navegar a través de las páginas resultantes de la consulta considerando que  el  sistema  debe  mostrar  inicialmente  15 registros por página, permitiendo al Empleado SAT  seleccionar  los  registros  que  requiere visualizar, teniendo las opciones 15, 50 y 100:  </p><p>- Ir  a  la  primera  página  (debe  mostrar  la primera  página  con  el  resultado  de  la consulta). </p><p>- Ir a la última página (debe mostrar la última página con el resultado de la consulta).  </p><p>- Ir  a  la  siguiente  página  (debe  mostrar  la siguiente página, considerando actual, con el resultado de la consulta y el número de registros  seleccionados  por  el  Empleado SAT). </p><p>￿ </p><p>Ir  a  la  página  anterior  (debe  mostrar  la página anterior considerando la actual con el resultado de la consulta).  </p><p>` `En  la  tabla  deben  mostrarse  los  registros  ordenados alfabéticamente. </p>|||
|**RNF007** |Seguridad  |Las  Pistas  de  Auditoría  deben  estar  protegidas contra  accesos  no  autorizados.  Solo  los Empleados SAT autorizados pueden consultar las tablas y la información en ellas se definirá durante la etapa de diseño; la cual debe estar cifrada para mantenerla confidencial y evitar exposiciones no autorizadas.|||
|**RNF008** |Fiabilidad |El  sistema  debe  ser  capaz  de  manejar excepciones  de  manera  efectiva  y  presentar mensajes claros y comprensibles para garantizar una adecuada interacción con el sistema. |||
|**RNF009** |Seguridad |Se debe mantener la información en pantalla en caso de un error al guardar las Pistas de Auditoría, siempre  y  cuando  el  escenario  lo  permita.  Hay situaciones de infraestructura o de conexión de |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|||

internet que sí pierde los datos ya que no están ![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.054.png)controlados por el sistema.  

Al  almacenar  la  información  en  la  BD  de  tipo **RNF010**  Integridad  Texto  o  alfanumérico  se  deben  eliminar  los 

espacios en blanco al inicio y fin de la cadena.  



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

11. **Diagrama<a name="_page23_x102.00_y133.92"></a> de actividad**  ![ref12]![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.056.png)

![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.057.jpeg)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

12. **Diagrama<a name="_page24_x102.00_y133.92"></a> de estados**  ![ref12]

![](Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.058.jpeg)



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_GenerarDictamen.docx|Versión del template: 7.00|
| :-: | :- | :-: |

13. **Aprobación<a name="_page25_x102.00_y133.92"></a> del cliente  ![ref12]**



|||
| :- | :- |
|**FIRMAS DE CONFORMIDAD** ||
|||
|**Firma 1**|**Firma 2**|
|**Nombre**: Andrés Mojica Vázquez.|**Nombre**: Ricardo Chávez Gutiérrez.|
|**Puesto**: Usuario ACPPI|**Puesto**: Usuario ACPPI|
|**Fecha:** |**Fecha:** |
|||
|**Firma 3**|**Firma 4**|
|**Nombre**:  Yesenia  Helvetia Delgado Naranjo.|**Nombre:**  Alejandro  Alfredo  Muñoz Núñez.|
|**Puesto**: APE ACPPI|**Puesto:** RAPE ACPPI|
|**Fecha**: |**Fecha**: |
|||
|**Firma 5**|**Firma 6**|
|**Nombre**: Luis Angel Olguin Castillo.|**Nombre**: Erick Villa Beltrán.|
|**Puesto**: Enlace ACPPI|**Puesto**: Líder APE SDMA 6|
|**Fecha**:|**Fecha**:|
|||
|**Firma 7**|**Firma 8**|
|**Nombre:**  Juan  Carlos  Ayuso Bautista.|<p>` `**Nombre:**  Aylín  de  la  Concepción</p><p>Caballero Weng.</p>|
|**Puesto:** Líder Técnico SDMA 6|**Puesto:**  Analista  de  Sistemas  DS SDMA 6|
|**Fecha**:|**Fecha**:|
|||

Página 26 de 26 

[ref1]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.012.png
[ref2]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.013.png
[ref3]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.014.png
[ref4]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.015.png
[ref5]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.016.png
[ref6]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.031.png
[ref7]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.032.png
[ref8]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.033.png
[ref9]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.034.png
[ref10]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.036.png
[ref11]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.044.png
[ref12]: Aspose.Words.b5fc15ac-73e1-4679-b081-5f493aa177b0.055.png
