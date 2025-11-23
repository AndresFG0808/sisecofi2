**Administración General de Comunicaciones ![ref1]**

**y Tecnologías de la Información**

**Marco Documental 7.0**
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

**<ID Requerimiento>** 8309** 

**Nombre del Requerimiento:** TI\_SISECOFI-SAT\_Seguimiento financiero y control documental de proyectos de contratación** 

<a name="_page0_x68.00_y184.12"></a>**Tabla de Versiones y Modificaciones** 



|Versión |Descripción del cambio |Responsable de la Versión |Fecha |
| - | - | :-: | - |
|*1* |*Creación del documento* |Angel Horacio López Alcaraz |*28/03/2024* |
|*1.1* |*Revisión del documento* |Luis Angel Olguin Castillo |*19/04/2024* |
|*1.2* |*Versión aprobada para firma* |Andrés Mojica Vázquez |20/06/2024 |

**TABLA DE CONTENIDO** 

[Tabla de Versiones y Modificaciones .................................................................................................................................... 1 ](#_page0_x68.00_y184.12)[Módulo: CONSUMO DE SERVICIOS-DICTAMEN .......................................................................................................... 2 ](#_page1_x68.00_y135.12)[ESTILOS 01 ............................................................................................................................................................................................ 2 ](#_page1_x68.00_y161.12)[Descripción de Elementos ......................................................................................................................................................... 2 ](#_page1_x68.00_y273.12)[Descripción de Campos .............................................................................................................................................................. 4 ](#_page3_x68.00_y528.12)[ESTILOS 02 ......................................................................................................................................................................................... 18 ](#_page17_x68.00_y135.12)[Descripción de Elementos ....................................................................................................................................................... 19 ](#_page18_x68.00_y135.12)[Descripción de Campos ............................................................................................................................................................ 19](#_page18_x68.00_y449.12)



|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page1_x68.00_y135.12"></a>**MÓDULO: CONSUMO DE SERVICIOS-DICTAMEN  <a name="_page1_x68.00_y161.12"></a>ESTILOS 01** 

Página 2 de 23
**Administración General de Comunicaciones ![ref1]**

**y Tecnologías de la Información**

**Marco Documental 7.0**

**Nombre de la Pantalla:  Objetivo:** 

**Casos de uso relacionados:** 

Solicitud de pago 

Permitir  al  Empleado  SAT  registrar  la  sección “Solicitud de pago” relacionada a un dictamen. 

17\_3083\_ECU\_GenerarNotificacionPago* 

Página 18 de 18
**Administración General de Comunicaciones ![ref1]**

**y Tecnologías de la Información**

**Marco Documental 7.0**

![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.002.jpeg)

<a name="_page1_x68.00_y273.12"></a>**DESCRIPCIÓN DE ELEMENTOS**  



|**Elemento** |**Descripción** |||
| - | - | :- | :- |
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00||



|Solicitud de pago |Nombre de la sección. |||
| :- | - | :- | :- |
|![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.003.png)|<p>Opción que despliega o contrae la sección, tomando en cuenta lo siguiente:  </p><p>Sección contraída ![ref2] Sección desplegada![ref3]</p>|||
|Oficio de solicitud de pago\*: |Campo que permite ingresar el oficio de la solicitud de pago. |||
|Fecha de solicitud\*: |Campo que permite ingresar la fecha de la solicitud de pago. |||
|![ref4]|Opción que muestra la ventana emergente “Generar plantilla”. |||
|Añadir PDF\*: |Muestra el nombre del campo de “Añadir PDF”.. |||
|![ref5]|Muestra el nombre del archivo cargado |||
|Examinar |Opción  que  permite  abrir  el  explorador  de  archivos  de  la computadora para seleccionar el archivo que se adjuntará. |||
|Cancelar |Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado. |||
|Guardar |Opción que inicia el proceso para almacenar en la base de datos (BD) la información de la sección “Solicitud de pago”. |||
|Solicitud de pago |Opción que permite cambiar el estatus del dictamen a “Solicitud de pago”. |||
|Referencia de Pago |Nombre de la sección. |||
|Tipo de notificación de pago\*: |Campo que permite seleccionar el dato del medio por el cual se realizó la notificación de pago. |||
|Oficio de notificación de pago\*: |Campo que permite ingresar el id del oficio de notificación de pago. |||
|Fecha de notificación\*: |Campo  que  permite  ingresar  la  fecha  en  la  que  se  realizó  la notificación de pago. |||
|Factura N° |<p>El sistema extrae de la BD la o las facturas asociadas al dictamen de forma automática.   </p><p>\*Puede haber tantas facturas como se carguen en el dictamen. </p>|||
|Comprobante fiscal\*: |Campo que muestra el comprobante fiscal asociado a la factura. |||
|Desglose\*: |Opción que muestra a quien le pertenece el pago (SAT). |||
|Folio de ficha de pago\*: |Campo  que  permite  ingresar  el  folio  de  la  ficha  de  pago correspondiente a la parte del SAT de la factura. |||
|Fecha de pago\*: |Campo que permite ingresar la fecha de pago correspondiente a la parte del SAT de la factura. |||
|Tipo de cambio pagado\*: |Campo  que  permite  ingresar  el  tipo  de  cambio  pagado correspondiente a la parte del SAT de la factura. |||
|Pagado NAFIN\*: |Campo  que  permite  ingresar  el  monto  pagado  NAFIN correspondiente a la parte del SAT de la factura. |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00||



|Ficha NAFIN\*: |Muestra el nombre del campo de “Ficha NAFIN”. |
| - | - |
|![ref5]|Muestra el nombre del archivo cargado |
|Examinar |Opción  que  permite  abrir  el  explorador  de  archivos  de  la computadora para seleccionar el archivo que se adjuntará. |
|Comprobante fiscal\*: |Campo que muestra el comprobante fiscal asociado a la factura. |
|Desglose\*: |Opción que muestra a quien  le  pertenece el  pago  (Convenio  de colaboración). |
|Folio ficha de pago\*: |Campo  que  permite  ingresar  el  folio  de  la  ficha  de  pago correspondiente  a  la  parte  del  Convenio  de  colaboración  de  la factura. |
|Fecha de pago\*: |Campo que permite ingresar la fecha de pago correspondiente a la parte del Convenio de colaboración de la factura. |
|Tipo de cambio pagado\*: |Campo que permite ingresar el tipo de cambio correspondiente a la parte del Convenio de colaboración de la factura. |
|Pagado NAFIN\*: |Campo  que  permite  ingresar  el  monto  pagado  NAFIN correspondiente  a  la  parte  del  Convenio  de  colaboración  de  la factura. |
|Ficha NAFIN\*: |Muestra el nombre del campo de “Ficha NAFIN”. |
|![ref5]|Muestra el nombre del archivo cargado. |
|Examinar |Opción  que  permite  abrir  el  explorador  de  archivos  de  la computadora para seleccionar el archivo que se adjuntará. |
|![ref6]|Permite desplazarse de manera vertical en la información que se muestra. |
|Cancelar |Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado. |
|Guardar |Opción que inicia el proceso para almacenar en la BD la información de la sección “Referencia de pago”.  |
|Pagado |Opción que permite cambiar el estatus del dictamen a “Pagado”. |

<a name="_page3_x68.00_y528.12"></a>**DESCRIPCIÓN DE CAMPOS** 



|**Elemento** |**Tipo** |**Longitu d** |**Nivel de Acceso (L, E, S)** |**Descripción del Campo** |**Fórmulas** |**Precisione s** |||
| - | - | :-: | :-: | - | - | :-: | :- | :- |
|Solicitud de Pago |Sección |N/A |L |Nombre de la sección. |N/A |N/A |||
|![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.009.png)|Ícono |N/A |S |Opción que despliega o contrae la sección |N/A |<p>Sección contraída </p><p>![ref2]</p><p>Sección desplegada![ref3]</p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|Oficio de solicitud de pago\*: |Alfanuméric o |N/A |L, E |Campo que permite ingresar el oficio de la solicitud de pago. |N/A |Campo obligatorio. Solo se muestra habilitado en modo editar dictamen. |||
| :-: | :-: | - | - | :-: | - | :-: | :- | :- |
|Fecha de solicitud\*: |Fecha |10 |L, E, S |Campo que permite ingresar la fecha de la solicitud de pago. |N/A |<p>Campo obligatorio. Formato de fecha </p><p>DD/MM/AA</p><p>AA. Solo se muestra habilitado en modo editar dictamen. </p>|||
|![ref4]|Ícono |N/A |L, S |Opción que muestra la ventana emergente “Generar plantilla”. |N/A |<p>Usar *tooltip* “Generar </p><p>documento</p><p>”. </p><p>Solo se muestra habilitado en modo editar dictamen. </p>|||
|Añadir PDF\*: |Texto |N/A |L |Muestra el nombre del campo de “Añadir PDF”. |N/A |Campo obligatorio. |||
|![ref5]|Alfanuméric o |100 |L, E, S |Muestra el nombre del archivo cargado |N/A |<p>Usar *tooltip* Cargar archivo con extensión (.PDF). </p><p>Se habilitará en estatus “Facturado” y en modo edición. </p>|||
|Examinar |Botón |N/A |L, S |Opción que permite abrir |N/A |Se habilitará |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||<p>el explorador de archivos de la </p><p>computador</p><p>a para seleccionar el archivo que se adjuntará. </p>||<p>en estatus “Facturado” y en modo edición. </p><p>Inicialment e se muestra sin color de fondo y con contorno y letras en color gris. </p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras. </p>|||
| :- | :- | :- | :- | :-: | :- | :-: | :- | :- |
|Cancelar |Botón |N/A |L, S |Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado. |N/A |<p>Se habilitará en estatus “Facturado” y en modo edición. </p><p>Inicialment e se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||||letras blancas. |||
| :- | :- | :- | :- | :- | :- | :- | :- | :- |
|Guardar |Botón |N/A |L, S |Opción que inicia el proceso para almacenar en la base de datos (BD) la información de la sección “Solicitud de pago”. |N/A |<p>Se habilitará en estatus “Facturado” y en modo edición. </p><p>Inicialment e se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde oscuro (#10312B) y letras blancas. </p>|||
|Solicitud de pago |Botón |N/A |L, S |Opción que permite cambiar el estatus del dictamen a “Solicitud de pago”. |N/A |<p>Se habilitará en estatus “Facturado” y en modo edición. </p><p>Inicialment e se muestra sin color de fondo y con contorno y letras en color gris. </p><p>Cuando se pone el </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||||cursor encima debe cambiar a fondo gris y letras negras. |||
| :- | :- | :- | :- | :- | :- | :-: | :- | :- |
|Referencia de Pago |Sección |N/A |L |Nombre de la sección. |N/A |N/A |||
|Tipo de notificación de pago\*: |Lista de selección |N/A |L, S |Campo que permite seleccionar el dato del medio por el cual se realizó la notificación de pago. |N/A |<p>Campo obligatorio. Contiene las opciones: \*Correo electrónico \*Oficio \*Otros. </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
|Oficio de notificación de pago\*: |Alfanuméric o |N/A |L, E |Campo que permite ingresar el id del oficio de notificación de pago. |N/A |Campo obligatorio. Se muestra habilitado en caso de que el “Tipo de notificación de pago” sea “Oficio”. Se habilitará en estatus “Solicitud de pago” y en modo edición. |||
|Fecha de notificación\*: |Fecha |10 |L, E, S |Campo que permite ingresar la fecha en la que se |N/A |Campo obligatorio. Formato de fecha |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||realizó la notificación de pago. ||<p>DD/MM/AA AA. </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
| :- | :- | :- | :- | :-: | :- | :-: | :- | :- |
|Factura N° |Texto |N/A |L |El sistema extrae de la BD la o las facturas asociadas al dictamen de forma automática. \*Puede haber tantas facturas como se carguen en el dictamen. |N/A |Factura N° correspond e al número de la factura de la sección “Facturas”. |||
|Comprobant e fiscal\*: |Alfanuméric o |40 |L |<p>Campo que muestra el </p><p>comprobant</p><p>e fiscal asociado a la factura. </p>|N/A |<p>Campo obligatorio. Ejemplo:  </p><p>4A1B43E2- 1183-4AD4-</p><p>A3DE- C2DA787A</p><p>E56A. </p>|||
|Desglose\*: |Lista de selección |N/A |L |Opción que muestra a quien le pertenece el pago (SAT). |N/A |Campo obligatorio. Se habilitará en estatus “Solicitud de pago” y en modo edición. |||
|Folio ficha de pago\*: |Alfanuméric o |N/A |L, E |<p>Campo que permite ingresar el folio de la ficha de pago </p><p>correspondie nte a la parte </p>|N/A |Campo obligatorio. Se habilitará en estatus “Solicitud de pago” y |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||del SAT de la factura. ||en modo edición. |||
| :- | :- | :- | :- | :-: | :- | :-: | :- | :- |
|Fecha de pago\*: |Fecha |10 |L, E, S |<p>Campo que permite ingresar la fecha de pago </p><p>correspondie</p><p>nte a la parte del SAT de la factura. </p>|N/A |<p>Campo obligatorio. Formato de fecha </p><p>DD/MM/AA</p><p>AA. </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
|Tipo de cambio pagado\*: |Numérico (Decimal) |20 |L, E |<p>Campo que permite ingresar el tipo de cambio pagado </p><p>correspondie</p><p>nte a la parte del SAT de la factura. </p>|N/A |<p>Se consideran números decimales con formato $ 0.0000, hasta 4 decimales. deben estar redondead</p><p>os. Ejemplo: $999,999,99</p><p>9,999.0000. Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
|Pagado NAFIN\*: |Numérico (Decimal) |20 |L, E |<p>Campo que permite ingresar el monto pagado NAFIN </p><p>correspondie</p><p>nte a la parte del SAT de la factura. </p>|N/A |Campo obligatorio. Se consideran números decimales con formato $ 0.00, hasta 2 decimales. deben estar |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||||<p>redondead os. Ejemplo: $999,999,99</p><p>9,999.00. Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
| :- | :- | :- | :- | :- | :- | :- | :- | :- |
|Ficha NAFIN\*: |Texto |N/A |L |Muestra el nombre del campo de “Ficha NAFIN”. |N/A |N/A |||
|![ref5]|Alfanuméric o |100 |L, E, S |Muestra el nombre del archivo cargado. |N/A |<p>Usar “*tooltip*” Cargar archivo con extensión (.PDF). </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
|Examinar |Botón |N/A |S |<p>Opción que permite abrir el explorador de archivos de la </p><p>computador</p><p>a para seleccionar el archivo que se adjuntará. </p>|N/A |<p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p><p>Inicialment e se muestra sin color de fondo y con contorno y letras en color gris. </p><p>Cuando se pone el cursor </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||||encima debe cambiar a fondo gris y letras negras. |||
| :- | :- | :- | :- | :- | :- | :-: | :- | :- |
|Comprobant e fiscal\*: |Alfanuméric o |40 |L |<p>Campo que muestra el </p><p>comprobant</p><p>e fiscal asociado a la factura. </p>|N/A |<p>Ejemplo:  4A1B43E2-</p><p>1183-4AD4-</p><p>A3DE- C2DA787A</p><p>E56A. \*Solo aplica en caso de </p><p>existir un convenio de </p><p>colaboració</p><p>n. </p>|||
|Desglose\*: |Lista de selección |N/A |L |<p>Opción que muestra a quien le pertenece el pago (Convenio de colaboración</p><p>). </p>|N/A |<p>\*Solo aplica en caso de </p><p>existir un convenio de </p><p>colaboració</p><p>n. </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
|Folio ficha de pago\*: |Alfanuméric o |N/A |L, E |<p>Campo que permite ingresar el folio de la ficha de pago </p><p>correspondie</p><p>nte a la parte del Convenio de colaboración de la factura. </p>|N/A |<p>Campo obligatorio. \*Solo aplica en caso de </p><p>existir un convenio de </p><p>colaboració</p><p>n. </p><p>Se habilitará en estatus “Solicitud de pago” y </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||||en modo edición |||
| :- | :- | :- | :- | :- | :- | :-: | :- | :- |
|Fecha de pago\*: |Fecha |10 |L, E, S |<p>Campo que permite ingresar la fecha de pago </p><p>correspondie</p><p>nte a la parte del Convenio de colaboración de la factura. </p>|N/A |<p>Campo obligatorio. Formato de fecha </p><p>DD/MM/AA</p><p>AA. \*Solo aplica en caso de existir un convenio de </p><p>colaboració</p><p>n. </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
|Tipo de cambio pagado\*: |Numérico (Decimal) |20 |L, E |<p>Campo que permite ingresar el tipo de cambio </p><p>correspondie</p><p>nte a la parte del Convenio de colaboración de la factura. </p>|N/A |<p>Se consideran números decimales con formato $ 0.0000, hasta 4 decimales. deben estar redondead</p><p>os. Ejemplo: $999,999,99</p><p>9,999.0000. \*Solo aplica en caso de existir un convenio de </p><p>colaboració</p><p>n. </p><p>Se habilitará en estatus “Solicitud de pago” y </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||||en modo edición. |||
| :- | :- | :- | :- | :- | :- | :-: | :- | :- |
|Pagado NAFIN\*: |Numérico (Decimal) |20 |L, E |<p>Campo que permite ingresar el monto pagado NAFIN </p><p>correspondie</p><p>nte a la parte del Convenio de colaboración de la factura. </p>|N/A |<p>Campo obligatorio. Se consideran números decimales con formato $ 0.00, hasta 2 decimales. deben estar redondead</p><p>os. Ejemplo: $999,999,99</p><p>9,999.00. \*Solo aplica en caso de existir un convenio de </p><p>colaboració</p><p>n. </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
|Ficha NAFIN\*: |Texto |N/A |L |Muestra el nombre del campo de “Ficha NAFIN”. |N/A |N/A |||
|![ref5]|Alfanuméric o |100 |L, E, S |Muestra el nombre del archivo cargado. |N/A |Usar *tooltip* “Cargar archivo con extensión (.PDF)”. \*Solo aplica en caso de existir un convenio de |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||||<p>colaboració n. </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
| :- | :- | :- | :- | :- | :- | :-: | :- | :- |
|Examinar |Botón |N/A |L, S |<p>Opción que permite abrir el explorador de archivos de la </p><p>computador</p><p>a para seleccionar el archivo que se adjuntará. </p>|N/A |<p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p><p>Inicialment e se muestra sin color de fondo y con contorno y letras en color gris. </p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras. \*Solo aplica en caso de existir un convenio de </p><p>colaboració</p><p>n. </p>|||
|![ref6]|Barra de desplazamie nto |N/A |S |Permite desplazarse de manera vertical en la información que se muestra. |N/A |N/A |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|Cancelar |Botón |N/A |L, S |Opción que realiza el proceso para cancelar la acción y regresa al último estado guardado. |N/A |<p>Inicialment e se muestra sin color de fondo y con el texto y contorno en color guinda (#691c32). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo guinda (#691c32) y letras blancas. Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|||
| - | - | - | - | :-: | - | :-: | :- | :- |
|Guardar |Botón |N/A |L, S |Opción que inicia el proceso para almacenar en la BD la información de la sección “Referencia de pago”.  |N/A |<p>Inicialment e se muestra sin color de fondo y con el texto y contorno en color verde oscuro (#10312B). </p><p>Cuando se le pone el cursor encima debe cambiar a fondo verde </p>|||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|||||||



|||||||oscuro (#10312B) y letras blancas. Se habilitará en estatus “Solicitud de pago” y en modo edición. |
| :- | :- | :- | :- | :- | :- | :-: |
|Pagado |Botón |N/A |L, S |Opción que permite cambiar el estatus del dictamen a “Pagado”. |N/A |<p>Inicialment e se muestra sin color de fondo y con contorno y letras en color gris. </p><p>Cuando se pone el cursor encima debe cambiar a fondo gris y letras negras. </p><p>Se habilitará en estatus “Solicitud de pago” y en modo edición. </p>|



|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |



Página 18 de 23
**Administración General de Comunicaciones ![ref1]**

**y Tecnologías de la Información**

**Marco Documental 7.0**

<a name="_page17_x68.00_y135.12"></a>**ESTILOS 02** 

**Nombre de la Pantalla:**  

**Objetivo:** 

**Casos de uso relacionados:** 

Vista previa solicitud de pago. 

Permite al Empleado SAT previsualizar y generar la solicitud de pago.  

17\_3083\_ECU\_GenerarNotificacionPago.docx  

Página 23 de 23
**Administración General de Comunicaciones ![ref1]**

**y Tecnologías de la Información**

**Marco Documental 7.0**

![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.010.jpeg)

**Nota:** Los datos mostrados en la tabla son solo de ejemplo. 

|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00|
| :-: | :- | :-: |

<a name="_page18_x68.00_y135.12"></a>**DESCRIPCIÓN DE ELEMENTOS** 



|**Elemento** |**Descripción** |
| - | - |
|Generar plantilla* |Nombre de la ventana emergente.* |
|![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.011.png)|Opción que cierra la ventana emergente.* |
|Solicitud de pago |Etiqueta que muestra el identificador de la solicitud de pago.  |
|Tipo de plantilla\*: |Campo  de  selección  que  muestra  el  catálogo  que  contiene  los  tipos  de plantillas que se pueden utilizar para generar la solicitud de pago.  |
|Formato a exportar\*: |Campo que muestra las opciones de archivo para exportar el documento. |
|![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.012.png)|Paginador  que  permite  navegar  a  través  de  las  páginas  del  panel  de visualización. |
|Área de visualización |Permite  la  previsualización  de  la  Solicitud  de  pago  de  acuerdo  con  la estructura y formato del tipo de plantilla seleccionada. |
|![ref7]|Permite desplazarse de manera horizontal en la información que se muestra. |
|![ref8]|Permite desplazarse de manera vertical en la información que se muestra. |
|Aceptar |Opción  que  permite  aceptar  los  cambios,  cierra  la  ventana  emergente  y genera el archivo de la Solicitud de pago. |
|Cancelar |Opción que cierra la ventana emergente. |

<a name="_page18_x68.00_y449.12"></a>**DESCRIPCIÓN DE CAMPOS** 



|**Elemento** |**Tipo** |**Longitu d** |**Nivel de Acceso (L, E, S)** |**Descripción del Campo** |**Fórmulas** |**Precisione s** ||||
| - | - | :-: | - | - | - | :-: | :- | :- | :- |
|Generar plantilla* |Texto |N/A |L |Nombre de la ventana emergente. |N/A |N/A ||||
|![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.015.png)|Ícono |N/A |S |Opción que cierra la ventana emergente. |N/A |Usar *tooltip* “Cerrar”. ||||
|Solicitud de pago |Texto |N/A |L |Etiqueta que muestra el identificador de la solicitud de pago.  |N/A |N/A ||||
|Tipo de plantilla\*: |Lista de selección |N/A |S |Campo de selección que muestra el catálogo que contiene los |N/A  |Campo obligatorio . ||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00||||||||



|||||tipos de plantillas que se pueden utilizar para generar la solicitud de pago.  ||||||
| :- | :- | :- | :- | :-: | :- | :- | :- | :- | :- |
|Formato a exportar\*: |Casilla de selección |N/A |S |Campo que muestra las opciones de archivo para exportar el documento. |N/A |<p>Campo obligatorio . </p><p>Las opciones que se muestran son PDF y Word. </p>||||
|![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.016.png)|Paginador |N/A |S |Paginador que permite navegar a través de las páginas del panel de visualización. |N/A |N/A ||||
|Área de visualización |Panel de visualización |N/A |L |<p>Permite la previsualizaci</p><p>ón de la Solicitud de pago de acuerdo con la estructura y formato del tipo de plantilla seleccionada. </p>|N/A |Debe contener barra de desplazam iento tanto vertical como horizontal. ||||
|![ref9]|Barra de desplazamie nto |N/A |S |Permite desplazarse de manera horizontal en la información que se muestra. |N/A |N/A ||||
|![ref8]|Barra de desplazamie nto |N/A |S |Permite desplazarse de manera vertical en la información que se muestra. |N/A |N/A ||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00||||||||



|Aceptar |Botón |N/A |S |Opción que permite aceptar los cambios, cierra la ventana emergente y genera el archivo de la Solicitud de pago. |N/A |<p>Inicialmen te se muestra sin color de fondo y con el texto y contorno en color verde oscuro </p><p>(#10312B). </p><p>Cuando se le pone el cursor encima debe cambiar con fondo verde oscuro (#10312B) y letras blancas. </p>||||
| - | - | - | - | :-: | - | :-: | :- | :- | :- |
|Cancelar |Botón |N/A |S |Opción que cierra la ventana emergente. |N/A |<p>Inicialmen te se muestra sin color de fondo y con el texto y contorno en color guinda </p><p>(#691c32). </p><p>Cuando se le pone el cursor encima debe cambiar con fondo guinda (#691c32) y letras blancas. </p>||||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00||||||||

Anexo - Ejemplos de botones 

![](Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.018.png)

Las acciones de cada botón se definen en los estilos correspondientes. 



|||||
| :- | :- | :- | :- |
|**FIRMAS DE CONFORMIDAD** ||||
|||||
|**Firma 1**  |**Firma 2**  |||
|**Nombre**: Andrés Mojica Vázquez** |**Nombre**: Ricardo Chávez Gutiérrez** |||
|**Puesto**: Usuario ACPPI.**  |**Puesto**: Usuario ACPPI.**  |||
|**Fecha:****  |**Fecha:****  |||
|||||
|**Firma 3****   |**Firma 4****  |||
|**Nombre**: Yesenia Helvetia Delgado Naranjo.**  |**Nombre**:**  Alejandro  Alfredo  Muñoz Núñez.**  |||
|**Puesto**: APE ACPPI.**  |**Puesto**:** RAPE ACPPI.**  |||
|**Fecha**:**  |**Fecha**:**  |||
|||||
|**Firma 5****  |**Firma 6**  |||
|**Nombre**: Luis Angel Olguin Castillo.**  |**Nombre**: Erick Villa Beltrán.  |||
|Fecha de aprobación del Template: 02/08/2023|**Especificación de Interacción de Usuario** 17\_3083\_EIU\_GenerarNotificacionPago.docx** |Versión del template: 7.00||



|**Puesto**: Enlace ACPPI.**  |**Puesto**: Líder APE SDMA 6.  |
| - | - |
|**Fecha**:**  |**Fecha**:  |
|||
|**Firma 7** |**Firma 8**  |
|**Nombre**:**  Juan  Carlos  Ayuso Bautista.**  |**Nombre**:  Angel  Horacio  López Alcaraz. |
|**Puesto**:** Líder Técnico SDMA 6.**  |**Puesto**:** Analista de Sistemas SDMA 6.**  |
|**Fecha**:**  |**Fecha**:**  |
|||

Página 23 de 23

[ref1]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.001.png
[ref2]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.004.png
[ref3]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.005.png
[ref4]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.006.png
[ref5]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.007.png
[ref6]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.008.png
[ref7]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.013.png
[ref8]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.014.png
[ref9]: Aspose.Words.6e24debe-bf4a-4911-a3f9-8a146239d025.017.png
