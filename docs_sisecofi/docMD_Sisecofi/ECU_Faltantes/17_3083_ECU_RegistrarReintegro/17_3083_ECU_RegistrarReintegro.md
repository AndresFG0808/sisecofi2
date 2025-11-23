**Administración General de Comunicaciones ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.001.png)**

**y Tecnologías de la Información**

**Marco Documental 7.0**
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

**<ID Requerimiento>** 8309** 

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación** 

**Tabla de Versiones y Modificaciones** 

|Versión |Descripción del cambio |Responsable de la Versión |Fecha |
| - | - | :-: | - |
|*1* |*Creación del documento* |Aylín de la Concepción Caballero Weng |*01/04/2024* |
|*1.1* |*Revisión del documento* |Luis Angel Olguin Castillo |*18/04/2024* |
|*1.2* |*Versión aprobada para firma* |Andrés Mojica Vázquez |*07/06/2024* |

**Tabla de Contenido** 

[17_3083_ECU_RegistrarReintegro ........................................................................................................... 2](#_page1_x82.00_y132.92)

1. [Descripción ........................................................................................................................................................ 2](#_page1_x116.00_y148.92)
1. [Diagrama del Caso de Uso ...................................................................................................................... 2](#_page1_x116.00_y231.92)
1. [Actores ................................................................................................................................................................. 2](#_page1_x116.00_y415.92)
1. [Precondiciones............................................................................................................................................... 2](#_page1_x116.00_y539.92)
1. [Post condiciones ........................................................................................................................................... 2](#_page1_x116.00_y672.92)
1. [Flujo primario .................................................................................................................................................. 3](#_page2_x116.00_y133.92)
1. [Flujos alternos .................................................................................................................................................8](#_page7_x116.00_y607.92)
1. [Referencias cruzadas................................................................................................................................ 14](#_page13_x116.00_y210.92)
1. [Mensajes ........................................................................................................................................................... 14](#_page13_x116.00_y294.92)
1. [Requerimientos No Funcionales .................................................................................................... 14 ](#_page13_x116.00_y587.92)[11.Diagrama de actividad ............................................................................................................................. 16](#_page15_x122.00_y369.92)
12. [Diagrama de estados .............................................................................................................................. 17](#_page16_x116.00_y713.92)
12. [Aprobación del cliente ........................................................................................................................... 18](#_page17_x116.00_y133.92)



|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page1_x82.00_y132.92"></a>17\_3083\_ECU\_RegistrarReintegro 

1. **Descripción<a name="_page1_x116.00_y148.92"></a>  ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.002.png)**

El objetivo de este Caso de Uso, es permitir al Empleado SAT agregar, modificar y  eliminar  los  reintegros  asociados  a  un  contrato.  Así  como  exportar  la información asociada. 

2. **Diagrama<a name="_page1_x116.00_y231.92"></a> del Caso de Uso ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.003.png)**

![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.004.png)

3. **Actores<a name="_page1_x116.00_y415.92"></a>  ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.005.png)**



|**Actor** |**Descripción** |
| - | - |
|**Empleado SAT** |El Empleado SAT es el que tiene el o los roles otorgados por la Administración Central de Seguridad, Monitoreo y Control (ACSMC) para ingresar a cada uno de los módulos de este sistema.  |

4. **Precondiciones<a name="_page1_x116.00_y539.92"></a>** ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.006.png)
- El Empleado SAT se ha autenticado en el sistema con e.firma válida. 
- El sistema ha consumido el servicio “Oauth” para obtener los datos del Empleado SAT que ingresa. 
- El sistema ha validado que el Empleado SAT cuenta con los roles para ingresar, añadir, modificar o eliminar registros en el módulo “Reintegros”. 
- El Empleado SAT cuenta con proyectos asignados. 
- El Empleado SAT ha ingresado al módulo “Reintegros”. 
5. **Post<a name="_page1_x116.00_y672.92"></a> condiciones  ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.007.png)**
- El  Empleado  SAT  agregó,  modificó,  consultó,  eliminó  o  exportó  la información de los reintegros asociados a un contrato. ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.008.png)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

6. **Flujo<a name="_page2_x116.00_y133.92"></a> primario** ![ref1]![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.010.png)



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El Caso de Uso inicia cuando  el </p><p>Empleado  SAT  selecciona  el módulo **“Reintegros”**.** </p>|<p>2\.  Consulta en la base de datos (BD) la </p><p>información  para  los  siguientes catálogos  de  acuerdo  con  la **(RNA01)**: </p><p>o  Contratos vigentes. </p>|
||<p>3\.  Muestra  la  pantalla  principal </p><p>“Reintegros”  con  la  siguiente información.  </p><p>Sección “Registro” </p><p>- Contratos vigentes\*.  </p><p>- Contratos\* </p><p>Opción: </p><p>- Buscar </p><p>Ver  **(17\_3083\_EIU\_RegistrarReintegro)**  Estilos 01. </p>|
|<p><a name="_page2_x122.00_y448.92"></a>4.  Selecciona  una  opción  en  el </p><p>campo **“Contratos vigentes\*”**. </p>|<p>5\.  Consulta  en  la  BD  los  nombres </p><p>cortos de los contratos asociados a los proyectos que tiene asignado el Empleado SAT, de acuerdo con la opción  seleccionada  en  el  campo “Contratos  vigentes\*”.  Aplica  las **(RNA51)**, **(RNA176)** y **(RNA238)**. </p>|
||<p>6\.  Muestra en el campo “Contratos\*” la </p><p>información  obtenida  de  la consulta del paso 5. </p><p>￿  En  caso  de  no  obtener </p><p>información,  continúa  en  el [**(FA03)**](#_page8_x116.00_y607.92). </p>|
|<p>7\.  Selecciona  una  opción  en  el </p><p>campo **“Contratos\*”**. </p>||
|8\.  Selecciona la opción **“Buscar”**. |<p>9\.  Valida  que  se  hayan  ingresado </p><p>todos  los  datos  obligatorios  de acuerdo con la **(RNA03)**. </p><p>￿  En  caso  de  que  no  se  hayan </p><p>ingresado alguno de los  datos obligatorios,  continúa  en  el [**(FA04)**](#_page9_x116.00_y133.92). </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

10. Almacena en la BD la información ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.011.png)de las Pistas de Auditoría. 

    Datos que se almacenan:  

    **Módulo**= Reintegros 

    **Fecha y Hora**= Fecha y hora del sistema  usando  el  formato DD/MM/AAAA HH:MM:SS 

    **RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. 

    **Tipo  de  movimiento**=  **CNST** (Consulta) 

    **Movimiento**=  Aplica  la **(RNA239)** 

- nombre corto del contrato que se consulta  
- En caso de que no se puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA02)**](#_page8_x116.00_y223.92). 
11. Consulta  en  la  BD  y  obtiene  la ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.012.png)![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.013.png)siguiente  información  de  los reintegros  asociados  al  contrato seleccionado  en  el  campo “Contratos\*”. 
- No. 
- Tipo 
- Importe 
- Interés 
- Total 
- Fecha de reintegro 
- En  caso  de  que  no  haya resultado  en  la  consulta, continúa en el [**(FA05)**](#_page9_x116.00_y428.92). 
12. Deshabilita  los  campos “Contratos\*”,  “Contratos  vigentes\*” y la opción “Buscar”. ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.014.png)
12. Muestra<a name="_page3_x316.00_y635.92"></a>  la  pantalla  “Reintegros”, considerando  los  siguientes campos y opciones: 

    Sección “Registro” 

    Campos inhabilitados 

- Contratos vigentes\*.  
- Contratos\* 

Opciones. Aplica la **(RNA191)**: 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |



- Buscar (inhabilitada)  ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.015.png)
- Nuevo registro ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.016.png)
- Exportar a Excel ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.017.png)

Tabla:  Carga  la  información obtenida  en  el  paso  10.  Aplica  las **(RNA27)** y **(RNA244)** 

- No. 
- Tipo 
- Importe 
- Interés 
- Total 
- Fecha de reintegro 
- Acciones.  Aplica  las **(RNA191)**  
- Editar ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.018.png)
- Eliminar ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.019.png)
- Campos  para  “Filtrar”  por columna. 

Sumatorias. Aplica la **(RNA61)**: 

- ∑ Importes  
- ∑ Intereses  
- ∑ Totales  

Opciones. Aplica la **(RNA191)**:  

- Guardar 
- Cancelar (habilitado) 

` `Sección  “Gestión  documental” (contraída) 

Ver  

**(17\_3083\_EIU\_RegistrarReintegro)**  Estilos 01. ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.020.png)

14. Realiza<a name="_page4_x122.00_y597.92"></a>  una  de  las  siguientes  15.  Consulta  en  la  BD  la  siguiente acciones:**  información  a  utilizar,  de  acuerdo con la **(RNA01)**: 
- En caso de que seleccione la opción  **“Nuevo  registro”**,  el  o  Catálogo  de  los  tipos  de flujo continúa.**   reintegros. 
- En caso de que seleccione la 

  opción  **“Exportar  a  Excel”**, 

  continúa en el [**(FA06)**](#_page9_x116.00_y584.92). 

- En caso de que seleccione la opción  **“Editar”**  de  algún 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

registro de la tabla, continúa ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.021.png)en el [**(FA07)**](#_page10_x116.00_y479.92). 

- En caso de que seleccione la opción  **“Eliminar”**  de  algún registro de la tabla, continúa en el [**(FA08)**](#_page11_x116.00_y486.92). 
- En  caso  de  que  ingrese  un parámetro de búsqueda para **“Filtrar”**,  continúa  en  el [**(FA09)**](#_page12_x116.00_y133.92). 
- En caso de que seleccione la opción  **“Guardar”**,**  continúa en el pas[o **22** ](#_page6_x316.00_y323.92)de este flujo.** 
- En caso de que seleccione la opción  **“Cancelar”**,**  continúa en el [**(FA10)**](#_page12_x116.00_y371.92). 
- En caso de que ingrese en la sección  **“Gestión documental”**, continúa en el **(17\_3083\_ECU\_GestionDocu mental)**.  
16. Agrega una nueva fila a la tabla, y ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.022.png)muestra  los  componentes  para  la captura  de  la  información  en  los siguientes  campos.  Aplica  la **(RNA27)**: 
- Tipo  
- Importe 
- Interés 
- Fecha de reintegro 

Ver  **(17\_3083\_EIU\_RegistrarReintegro)** Estilos 01. 

17. Muestra en la columna “Acciones”** la siguiente opción:  
- Descartar ![ref2]

Ver  **(17\_3083\_EIU\_RegistrarReintegro)** Estilos 01. 

18. Carga la información leída de la BD en los siguientes campos: 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

- Tipo=  muestra  el  catálogo ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.024.png)de los tipos de reintegros. 
19. Ingresa<a name="_page6_x122.00_y163.92"></a>  la  información  en  los  20. Realiza  el  cálculo  para  el  campo campos:  “Total”, de acuerdo con la **(RNA61)**, y lo muestra en pantalla.  
- Tipo 
- Importe 
- Interés 
- Fecha de reintegro 
- En caso de que seleccione la opción  **“Descartar”**  del registro  seleccionado continúa en el **[**(FA10)**](#_page12_x116.00_y371.92)**. 
  - Regresa al pas[o **14** ](#_page4_x122.00_y597.92)de este flujo. ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.025.png)![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.026.png)
  - Valida<a name="_page6_x316.00_y323.92"></a>  que  se  hayan  ingresado todos  los  datos  obligatorios  de acuerdo con la **(RNA177)**, y continúa el flujo. 
    - En  caso  de  que  no  se  hayan ingresado alguno de los  datos obligatorios,  continúa  en  el [**(FA04)**](#_page9_x116.00_y133.92). 
    - En  caso  de  que  se  hayan realizado  movimientos  de eliminación, continúa el flujo.  ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.027.png)![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.028.png)
  - Valida que los datos cumplan con el formato  de  acuerdo  con  las **(RNA27)** y **(RNA255)**, y continúa el flujo. 
    - En caso de que alguno de los datos  no  cumpla  con  el formato, continúa en el [**(FA01)**](#_page7_x116.00_y647.92). 
    - En  caso  de  que  se  hayan realizado  movimientos  de eliminación, continúa el flujo. ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.029.png)

24\. Almacena en la BD la información de las Pistas de Auditoría. 

Datos que se almacenan:  

**Módulo**= Reintegros 

**Fecha y Hora**= Fecha y hora del sistema  usando  el  formato DD/MM/AAAA HH:MM:SS 

**RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |



||<p>**Tipo  de  movimiento**=  **INSR** (Insertar),  **UPDT**  (Modificar), **DLT**  (Borrar)  según corresponda </p><p>**Movimiento**=  Aplica  la **(RNA239)** </p><p>- nombre corto del contrato </p><p>- identificador del reintegro </p><p>￿  En caso de que no se puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA02)**](#_page8_x116.00_y223.92). </p>|
| :- | - |
||<p>25\. Identifica  el  tipo  de  movimiento </p><p>realizado  y  almacena  en  la  BD  la siguiente  información  que corresponda con lo capturado: </p><p>- No. </p><p>- Tipo </p><p>- Importe </p><p>- Interés </p><p>- Total </p><p>- Fecha de reintegro </p>|
||<p>26\. Identifica  el  tipo  de  movimiento </p><p>realizado y aplica la **(RNA258)** para la sección “Gestión documental” . </p>|
||<p>27\. Muestra el [**(MSG010)**](#_page13_x127.00_y559.92)** con la opción </p><p>“Aceptar”. </p>|
|28\. Selecciona la opción **“Aceptar”**. |29\. Cierra el mensaje.  |
||<p>30\. Muestra la pantalla con los campos </p><p>actualizados  de  acuerdo  con  los movimientos  realizados.  Aplica  la **(RNA250)**. </p>|
||<p>31\.  Actualiza el valor de los campos de </p><p>sumatorias “Importes”, “Intereses” y “Totales”. Aplica la **(RNA61)**. </p>|
||32\. Fin del Caso de Uso. |

<a name="_page7_x116.00_y607.92"></a>**7. Flujos alternos  ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.030.png)![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.031.png)**

<a name="_page7_x116.00_y647.92"></a>**FA01 El sistema identifica que los datos no cumplen con el formato**  



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA01**  inicia  cuando  el  sistema </p><p>identifica que los datos ingresados no cumplen con el formato. </p>|
||<p>2\.  Muestra el [**(MSG008)**](#_page13_x127.00_y513.92)** con la opción </p><p>“Aceptar”. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |



|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje.  |
| - | - |
||<p>5\.  Se  muestran  en  rojo  los  campos </p><p>que  no  cumplen  con  el  formato esperado. </p>|
||<p>6\.  Regresa  al  paso [` `**19** ](#_page6_x122.00_y163.92) del  Flujo </p><p>primario. </p>|

<a name="_page8_x116.00_y223.92"></a>**FA02 No se pueden almacenar las Pistas de Auditoría** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El **FA02** inicia cuando interviene un </p><p>evento  ajeno  y  no  se  pueden almacenar las Pistas de Auditoría.**  </p>|
||<p>2\.  Cancela la operación sin completar </p><p>el  movimiento  que  estaba  en proceso. </p>|
||<p>3\.  Muestra el mensaje de acuerdo con </p><p>lo siguiente: </p><p>- Si la pista de auditoría es por el tipo de movimiento **UPDT**, **INSR** </p><p>&emsp;o  **DLT**  se  muestra  el  mensaje [**(MSG001)**](#_page13_x127.00_y350.92).** </p><p>- Si la pista de auditoría es por el tipo  de  movimiento  **CNST**,  se muestra el [**(MSG002)**](#_page13_x127.00_y375.92).** </p><p>- Si la pista de auditoría es por el tipo  de  movimiento  **PRNT**,  se muestra el [**(MSG003)**](#_page13_x127.00_y400.92).**  </p><p>Cada  mensaje  se  muestra  con  la opción “Aceptar”. </p>|
|4\.  Selecciona la opción **“Aceptar”**. |5\.  Cierra el mensaje. |
||6\.  Regresa al paso previo que detona la acción de la pista de auditoría. |

<a name="_page8_x116.00_y607.92"></a>**FA03 No se obtiene información en la consulta de contratos ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.032.png)**



|**Actor** |**Sistema** |
| - | - |
||1\.  El **FA03** inicia cuando no se obtuvo resultados en la consulta realizada.**  |
||<p>2\.  Muestra el [**(MSG009)**](#_page13_x127.00_y538.92)** con la opción </p><p>“Aceptar”.  </p>|
|1\.  Selecciona la opción **“Aceptar”**. |2\.  Cierra el mensaje.  |
||3\.  Regresa al pas[o **4** ](#_page2_x122.00_y448.92)del Flujo primario. |

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page9_x116.00_y133.92"></a>**FA04  El  sistema  identifica  que  no  se  han  ingresado  todos  los  datos ![ref3]obligatorios** 



|**Actor** |**Sistema** |
| - | - |
||<p>1\.  El  **FA04**  inicia  cuando  el  sistema </p><p>identifica que no se han ingresado todos los datos obligatorios. </p>|
||<p>2\.  Muestra el [**(MSG005)**](#_page13_x127.00_y443.92)** con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje.  |
||<p>5\.  Se  muestran  en  rojo  los  campos </p><p>pendientes de capturar. </p>|
||<p>6\.  Realiza lo siguiente: </p><p>- Si fue invocado en el paso 9 del Flujo primario, regresa al paso[` `**4** ](#_page2_x122.00_y448.92)de dicho flujo. </p><p>- Si  fue  invocado  en  el  paso  22 del  Flujo  primario,  regresa  al paso[` `**19** ](#_page6_x122.00_y163.92)de dicho flujo. </p>|

<a name="_page9_x116.00_y428.92"></a>**FA05 No se obtiene información en la consulta de reintegros** 



|**Actor** |**Sistema** |
| - | - |
||1\.  El **FA05** inicia cuando no se obtuvo resultados en la consulta realizada.**  |
||<p>2\.  Muestra el [**(MSG004)**](#_page13_x127.00_y425.92)** con la opción </p><p>“Aceptar”. </p>|
|3\.  Selecciona la opción **“Aceptar”**. |4\.  Cierra el mensaje.  |
||<p>5\.  Regresa  al  paso [` `**13** ](#_page3_x316.00_y635.92) del  Flujo </p><p>primario. </p>|

<a name="_page9_x116.00_y584.92"></a>**FA06 Selecciona la opción “Exportar a Excel” ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.034.png)**



|**Actor** |**Sistema** ||||
| - | - | :- | :- | :- |
|<p>1\.  El  **FA06**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción **“Exportar a Excel”**.**  </p>|<p>2\.  Almacena en la BD la información </p><p>de las Pistas de Auditoría. </p><p>Datos que se almacenan:  </p><p>**Módulo**= Reintegros </p><p>**Fecha y Hora**= Fecha y hora del sistema  usando  el  formato DD/MM/AAAA HH:MM:SS </p><p>**RFC  Usuario**=  RFC  largo  del Empleado  SAT  que  ingresó  al sistema. </p>||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|||



||<p>**Tipo  de  movimiento**=  **PRNT** (Imprimir) </p><p>**Movimiento**=  Aplica  la **(RNA239)** </p><p>- nombro corto del contrato </p><p>- identificador del reintegro </p><p>￿  En caso de que no se puedan almacenar  las  Pistas  de Auditoría, continúa en el [**(FA02)**](#_page8_x116.00_y223.92).** </p>|
| :- | :- |
||<p>3\.  Obtiene  la  siguiente  información </p><p>de la BD de los reintegros asociados con el contrato: </p><p>- Nombre corto del contrato </p><p>- No. </p><p>- Tipo </p><p>- Importe </p><p>- Interés </p><p>- Total </p><p>- Fecha de reintegro </p>|
||<p>4\.  Genera  un  archivo  Excel  con </p><p>extensión (.xlsx) con la información obtenida en el paso 3. </p>|
||<p>5\.  Dependiendo el explorador realiza </p><p>la descarga del archivo. </p>|
||6\.  Fin del Caso de Uso.  |

<a name="_page10_x116.00_y479.92"></a>**FA07 Selecciona la opción “Editar”** ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.035.png)



|**Actor** |**Sistema** |||
| - | - | :- | :- |
|<p>1\.  El  **FA07**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción  **“Editar”**  de  algún registro de la tabla. </p>|<p>2\.  Consulta  en  la  BD  la  siguiente </p><p>información  a  utilizar,  de  acuerdo con la **(RNA01)**:  </p><p>o  Catálogo  de  los  tipos  de </p><p>reintegros </p>|||
||<p>3\.  Habilita en la tabla la edición de los </p><p>siguientes  campos.  Aplica  la **(RNA27)**.  </p><p>- Tipo  </p><p>- Importe </p><p>- Interés </p><p>- Fecha de reintegro </p><p>Ver  **(17\_3083\_EIU\_RegistrarReintegro)** Estilos 01. </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00||



||<p>4\.  Cambia las opciones de la columna </p><p>“Acciones”** por las siguientes:  </p><p>o  Descartar ![ref2]</p><p>Ver  **(17\_3083\_EIU\_RegistrarReintegro)** Estilos 02. </p>|
| :- | - |
||<p>5\.  Carga la información leída de la BD </p><p>en los siguientes campos: </p><p>o  Tipo=  muestra  el  catálogo de los tipos de reintegros. </p>|
|<p>6\.  Modifica  la  información  en  los </p><p>campos: </p><p>- Tipo </p><p>- Importe </p><p>- Interés </p><p>- Fecha de reintegro </p><p>￿  En caso de que seleccione la </p><p>opción  **“Descartar”**  del registro  seleccionado continúa en el **[**(FA10)**](#_page12_x116.00_y371.92)**. </p>|<p>7\.  Realiza  el  cálculo  para  el  campo </p><p>“Total”, de acuerdo con la **(RNA61)**, y lo muestra en pantalla. </p>|
||<p>8\.  Continúa  en  el  paso [` `**14** ](#_page4_x122.00_y597.92) del  Flujo </p><p>primario. </p>|

<a name="_page11_x116.00_y486.92"></a>**FA08 Selecciona la opción “Eliminar” ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.036.png)**



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA08**  inicia  cuando  el </p><p>Empleado  SAT  selecciona  la opción  **“Eliminar”**  de  algún registro de la tabla. </p>|<p>2\.  Muestra  el  **[**(MSG006)**](#_page13_x127.00_y463.92)**  con  las </p><p>opciones “Sí” y “No”. </p>|
|<p>3\.  Selecciona  la  opción  **“No”**  y  el </p><p>flujo continúa. </p><p>￿  En caso de que seleccione la </p><p>opción  **“Sí”**,**  continúa  en  el pas[o **5** ](#_page11_x316.00_y658.92)de este flujo. </p>|<p>4\.  Cierra  el  mensaje,  no  realiza </p><p>ningún  movimiento  y  regresa  al pas[o **13** ](#_page3_x316.00_y635.92)del Flujo primario. </p>|
||<p><a name="_page11_x316.00_y658.92"></a>5.  Elimina el registro seleccionado de </p><p>la  tabla  que  se  muestra  en  la pantalla. Aplica la **(RNA250)**. </p>|
||<p>6\.  Continúa  en  el  paso [` `**14** ](#_page4_x122.00_y597.92) del  Flujo </p><p>primario. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page12_x116.00_y133.92"></a>**FA09 Selecciona la opción “Filtrar” ![ref3]**



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El  **FA09**  inicia  cuando  el </p><p>Empleado SAT requiere **“Filtrar”** la  información  en  alguna columna de acuerdo con lo que se muestra en la tabla. </p>||
|<p>2\.  Elige  la  columna  para  filtrar  e </p><p>ingresa el dato a buscar. </p>|<p>3\.  Busca  dentro  de  la  columna  y </p><p>filtra la información mostrada de acuerdo  con  los  caracteres ingresados en el campo. </p>|
||<p>4\.  Muestra  todas  las  coincidencias </p><p>que  obtiene  en  tiempo  real  de dicha columna. </p>|
||<p>5\.  Regresa  al  paso [` `**14** ](#_page4_x122.00_y597.92) del  Flujo </p><p>primario. </p>|

<a name="_page12_x116.00_y371.92"></a>**FA10 Selecciona la opción “Cancelar” o “Descartar” ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.037.png)**



|**Actor** |**Sistema** |
| - | - |
|<p>1\.  El **FA10** inicia cuando el Empleado </p><p>SAT  selecciona  la  opción **“Cancelar”** o **“Descartar”**. </p>|<p>2\.  Muestra  el  **[**(MSG007)**](#_page13_x127.00_y488.92)**  con  las </p><p>opciones “Sí” y “No”. </p>|
|<p>3\.  Selecciona la opción **“No”** y el flujo </p><p>continúa. </p><p>￿  En caso de que seleccione la </p><p>opción  **“Sí”**,  continúa  en  el pas[o **5** ](#_page12_x322.00_y530.92)de este flujo. </p>|<p>4\.  Cierra el mensaje y continúa en el </p><p>pas[o **6** ](#_page13_x322.00_y170.92)de este flujo. </p>|
||<p><a name="_page12_x322.00_y530.92"></a>5.  Realiza lo siguiente:  </p><p>- Si  fue  invocado  en  la  opción “Cancelar”,  no  almacena ninguna información y recarga la pantalla. Regresa al paso[` `**4** ](#_page2_x122.00_y448.92)del Flujo primario. </p><p>- Si  fue  invocado  en  la  opción “Descartar” y: </p><p>&emsp;-  Era  un  registro </p><p>almacenado, este vuelve a su estado original y cambia a  solo  lectura  regresando los  íconos  de  “Editar”  y “Eliminar”. Regresa al paso [**14** ](#_page4_x122.00_y597.92)del Flujo primario. </p>|

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

- Y  era  un  registro  nuevo ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.038.png)elimina  la  fila.  Regresa  al paso[` `**13** ](#_page3_x316.00_y635.92)del Flujo primario. 

<a name="_page13_x322.00_y170.92"></a>6.  Regresa  al  paso  donde  fue 

invocado. 

8. **Referencias<a name="_page13_x116.00_y210.92"></a> cruzadas ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.039.png)**
- 17\_3083\_CRN\_SeguimientoFinancieroYControl 
- 17\_3083\_EIU\_RegistrarReintegro 
- 17\_3083\_ECU\_GestionDocumental 
9. **Mensajes<a name="_page13_x116.00_y294.92"></a> ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.040.png)**



|**ID Mensaje** |**Descripción** |
| - | - |
|<a name="_page13_x127.00_y350.92"></a>**MSG001** |Ocurrió un error al guardar la información, favor de intentar nuevamente (PA01).** |
|<a name="_page13_x127.00_y375.92"></a>**MSG002** |Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01). |
|<a name="_page13_x127.00_y400.92"></a>**MSG003** |Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01). |
|<a name="_page13_x127.00_y425.92"></a>**MSG004** |El contrato no contiene reintegros. |
|<a name="_page13_x127.00_y443.92"></a>**MSG005** |Favor de ingresar los datos obligatorios. |
|**MSG006** |<a name="_page13_x127.00_y463.92"></a>Se perderá toda la información relacionada a este reintegro. ¿Está seguro de que desea continuar? |
|**MSG007** |<a name="_page13_x127.00_y488.92"></a>Se perderá toda la información no guardada. ¿Está seguro de que desea continuar? |
|**MSG008** |<a name="_page13_x127.00_y513.92"></a>La información ingresada no cumple con el formato esperado. Por favor verifique los datos y vuelva a intentarlo. |
|**MSG009** |<a name="_page13_x127.00_y538.92"></a>El usuario no tiene proyectos asignados. Favor de validar con el administrador del sistema. |
|<a name="_page13_x127.00_y559.92"></a>**MSG010** |Los datos se guardaron correctamente. |

10. **Requerimientos<a name="_page13_x116.00_y587.92"></a> No Funcionales  ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.041.png)**



|**ID de RNF** |<p>**Requerimient**</p><p>**o No Funcional** </p>|**Descripción** |||
| - | - | - | :- | :- |
|**RNF001** |Disponibilidad  |El sistema deberá estar activo las 24 horas del día, los 365 días del año con picos de operación en el horario de 9:00 a 18:00 horas.  |||
|**RNF002** |Concurrencia  |<p>El número de Empleados SAT que puede tener el sistema son 150.  </p><p>￿  El  número  máximo  de  accesos </p><p>concurrentes  que  debe  soportar  este sistema  son  máximo  30  Empleados SAT.  </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|||



|**RNF003** |Seguridad  |El acceso solo podrá ser otorgado al Empleado SAT  que  tenga  los  roles  asignados  por  la Administración  Central  de  Seguridad, Monitoreo  y  Control  (ACSMC)  para  cada módulo de este sistema.  |||
| - | - | - | :- | :- |
|**RNF004** |Usabilidad |<p>El  sistema  deberá  manejar  los  siguientes elementos para facilitar la navegación: </p><p>- Mensajes  tipo  flotantes  (*tooltips*)  con información  de  la  herramienta  que ofrece  ayuda  contextual,  como  guía para el Empleado SAT. </p><p>- Componente  de  ordenamiento  que permita acomodar la información de la tabla  de  forma  ascendente  o descendente, considerando la columna donde es seleccionado. </p><p>￿ </p><p>Contar  con  un  diseño  responsivo  que permita  su  óptima  visualización  en distintos tipos de dispositivos finales.  </p>|||
|**RNF005** |Eficiencia  |Las  consultas  se  dividen  en  generales  y detalladas, para que las detalladas carguen la información solo cuando sean requeridas por el Empleado SAT. |||
|**RNF006** |Usabilidad  |<p>El Empleado SAT podrá navegar a través de las páginas  resultantes  de  la  consulta considerando  que  el  sistema  debe  mostrar inicialmente  15  registros  por  página, permitiendo al Empleado SAT seleccionar los registros que requiere visualizar, teniendo las opciones 15, 50 y 100: </p><p>- Ir a la primera página (debe mostrar la primera  página  con  el  resultado  de  la consulta). </p><p>- Ir  a  la  última  página  (debe  mostrar  la última  página  con  el  resultado  de  la consulta). </p><p>- Ir a la siguiente página (debe mostrar la siguiente  página,  considerando  la actual, con el resultado de la consulta y el  número  de  registros  seleccionados por el Empleado SAT). </p><p>￿ </p><p>Ir a la página anterior (debe mostrar la página anterior considerando la actual con el resultado de la consulta). </p><p>En  la  tabla  deben  mostrarse  los  registros ordenados alfabéticamente. </p>|||
|**RNF007** |Seguridad |Las Pistas de Auditoría deben estar protegidas contra  accesos  no  autorizados.  Solo  los Empleados  SAT  autorizados  pueden |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|||



|||consultarlas, y la información en ellas se definirá durante la etapa de diseño, la cual debe estar cifrada  para  mantenerla  confidencial  y  evitar exposiciones no autorizadas. |
| :- | :- | - |
|**RNF008****  |Fiabilidad  |El  sistema  debe  ser  capaz  de  manejar excepciones  de  manera  efectiva  y  presentar mensajes  claros  y  comprensibles  para garantizar  una  adecuada  interacción  con  el sistema.  |
|**RNF009****  |Seguridad  |Se debe mantener la información en pantalla en  caso  de  un  error  al  guardar  las  Pistas  de Auditoría,  siempre  y  cuando  el  escenario  lo permita. Hay situaciones de infraestructura o de conexión de internet que sí pierde los datos ya que no están controlados por el sistema.  |
|**RNF010****  |Integridad  |Al almacenar la información en la BD de tipo Texto  o  alfanumérico  se  deben  eliminar  los espacios en blanco al inicio y fin de la cadena.  |

<a name="_page15_x122.00_y369.92"></a>**11.Diagrama de actividad![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.042.png)**

![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.043.jpeg)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.044.jpeg)

12. **Diagrama<a name="_page16_x116.00_y713.92"></a> de estados**  ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.045.png)

No aplica, no hay cambios significativos de estados ni transiciones. ![](Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.046.png)

|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00|
| :-: | :- | :-: |

13. **Aprobación<a name="_page17_x116.00_y133.92"></a> del cliente**  ![ref1]



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
|Fecha de aprobación del Template: 02/08/2023|**Especificación del Caso de Uso** 17\_3083\_ECU\_RegistrarReintegro.docx|Versión del template: 7.00||



|||
| :- | :- |
|**Firma 7**  |**Firma 8**  |
|**Nombre:**  Juan  Carlos  Ayuso Bautista.  |**Nombre:**  Aylín  de  la  Concepción Caballero Weng. |
|**Puesto:** Líder Técnico SDMA 6.  |**Puesto:**  Analista  de  Sistemas  DS SDMA 6.  |
|**Fecha**:  |**Fecha**:  |
|||

Página 20 de 20 

[ref1]: Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.009.png
[ref2]: Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.023.png
[ref3]: Aspose.Words.682854c6-fd30-4840-9709-768ef6934d85.033.png
