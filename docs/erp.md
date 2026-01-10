# Especificación de requisitos de software
## Enunciado del problema

Las empresas prestadoras de servicios enfrentan desafíos en la gestión eficiente en cuanto a la administración de las cuentas de sus clientes como puede ser el seguimiento de las mismas o los registros de pagos y de las deudas que poseen las cuentas de cada cliente, ya que estos se realizan de manera manual o con herramientas que generan errores y no garantizan un control y buen funcionamiento administrativo.

## Especificación del requisito

Se requiere un sistema que se encargue de automatizar tareas, reduzca errores contables y permita un control de las cuentas, asegurando una eficiencia operativa y mejora en la toma de decisiones.

 
## Clientes potenciales
Los potenciales clientes para este sistema son: 
Pequeñas o medianas empresas que requieran gestionar sus productos/servicios
Emprendedores quienes desean tener una organización de sus clientes

## Solución propuesta
El software que se propone es una plataforma web dedicada a la gestión de clientes, servicios, facilitar la facturación de los mismos y llevar un control de las deudas y pagos que tenga cada cliente con la empresa.

**ArgentaFact**  
_Sistema integral de facturación y gestión de cuentas_

El sistema ArgentaFact se define como un sistema integral de facturación y gestión de cuentas, ya que centraliza la administración de clientes, la emisión de comprobantes según la normativa argentina vigente y el control de pagos asociados a cada cuenta.”

![ArgentaFact Logo](logo_argentaFact.png)

## Requisitos
### Historias de Usuario



| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU1                                                                                      |
| TÍTULO            | FACTURAR VENTAS                                                                          |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 5                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO facturar las ventas realizadas a un cliente PARA rendir cuentas al ente tributario (ARCA), PARA tener un registro de las operaciones |


| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU2                                                                                      |
| TÍTULO            | ANULAR FACTURA                                                                           |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 3                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO anular una factura existente PARA corregir errores de facturación o registrar devoluciones. |


| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU3                                                                                      |
| TÍTULO            | GENERAR NOTAS DE CRÉDITO                                                                 |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 2                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO generar una Nota de Crédito asociada a la factura anulada PARA mantener la coherencia contable y fiscal, PARA certificar la anulación de una factura. |
 


| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU4                                                                                      |
| TÍTULO            | COBRO MASIVO A CUENTAS CORRIENTES CON DÉBITO                                             |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 13                                                                                       |
| HISTORIA DE USUARIO | COMO gerente DESEO que se realice el cobro masivo a todos los clientes que están adheridos al débito automático PARA evitar pagos fuera de la fecha estimada. |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU5                                                                                      |
| TÍTULO            | GENERAR REPORTES DE CUENTAS                                                              |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 5                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO generar reportes con los datos completos y el estado de cuenta de cada cliente PARA detectar deudas o cuentas inactivas. |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU6                                                                                      |
| TÍTULO            | LISTAR FACTURAS                                                                          |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 3                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO generar un listado de facturas emitidas, anuladas y pendientes, PARA analizar la facturación general y el estado de cada comprobante. |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU7                                                                                      |
| TÍTULO            | FILTRAR FACTURAS                                                                         |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 1                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO visualizar la facturación total agrupada por mes, PARA obtener un resumen mensual de ingresos y actividades. |


| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU8                                                                                      |
| TÍTULO            | FILTRAR COMPROBANTES POR EL TIPO                                                         |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 1                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO filtrar los comprobantes por tipo (Factura A, B, C, Nota de Crédito, Nota de Débito) PARA controlar el volumen y frecuencia de cada tipo de operación. |

---

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU9                                                                                      |
| TÍTULO            | GESTIONAR CLIENTES                                                                       |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 2                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO crear, editar y eliminar clientes PARA registrar los clientes de la empresa y mantener actualizada la información de los clientes. |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU10                                                                                     |
| TÍTULO            | CONOCER CONDICION FISCAL                                                                 |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 2                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO conocer la condición fiscal de cada cliente PARA generar un tipo de factura segun su condicion fiscal |

 
| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU11                                                                                     |
| TÍTULO            | GESTIONAR CUENTA                                                                         |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 2                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO crear, editar y eliminar una cuenta de un cliente PARA registrar el saldo del cliente |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU12                                                                                     |
| TÍTULO            | REGISTRAR PAGO                                                                           |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 5                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO registrar el pago de una factura sea parcial o total PARA pagar de forma parcial o total el saldo del servicio adquirido |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU13                                                                                     |
| TÍTULO            | EMITIR FACTURAS MASIVAS                                                                  |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 8                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO emitir facturas de forma masiva en un periodo PARA registrar la prestación de servicio a varios clientes en un periodo |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU14                                                                                     |
| TÍTULO            | GENERAR ARCHIVO DE COMPROBANTE                                                           |
| PRIORIDAD         | Es bueno tenerlas                                                                        |
| TIEMPO ESTIMADO (DÍAS) | 1                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO generar archivo del comprobante PARA descargar, imprimir o compartir mediante medios electrónicos a los clientes PARA que el cliente obtenga un duplicado del comprobante y mantener su satisfacción |

 

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU15                                                                                     |
| TÍTULO            | GESTIONAR SERVICIOS                                                                      |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 2                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO crear, editar y eliminar servicios de la empresa PARA dar a conocer las prestaciones a los clientes y PARA mantener consistente los servicios prestados en los comprobantes de venta. |


| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU16                                                                                     |
| TÍTULO            | GESTIÓN AUTOMÁTICA DE COMPROBANTES                                                       |
| PRIORIDAD         | Es bueno tenerla                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 5                                                                                        |
| HISTORIA DE USUARIO | COMO empleado DESEO que el sistema pueda determinar automáticamente el tipo de comprobante al momento de facturar según la CUIT y régimen de cada cliente. PARA ahorrar tiempo y PARA evitar errores al ingresar datos por teclado |


 | Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU17                                                                                     |
| TÍTULO            | REGISTRAR HISTORICO FISCAL                                                               |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 3                                                                                        |
| HISTORIA DE USUARIO | COMO empleado DESEO llevar el registro de cada operación realizada en empresa PARA generar un histórico fiscal. |

 | Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU18                                                                                     |
| TÍTULO            | GESTIONAR DATOS DE CONTACTO DE LA EMPRESA                                                |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 1                                                                                        |
| HISTORIA DE USUARIO | COMO gerente PUEDO agregar o modificar información relacionada a los datos de contacto de la empresa PARA mantener actualizado cualquier documento externo de la empresa |
 


| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU19                                                                                     |
| TÍTULO            | GESTIONAR FORMAS DE PAGO                                                                 |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 1                                                                                        |
| HISTORIA DE USUARIO | COMO gerente PUEDO crear, editar y eliminar formas de pago PARA ofrecer a los clientes otros medios de pago alternativos al efectivo |


| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU20                                                                                     |
| TÍTULO            | GESTIONAR ALÍCUOTA                                                                       |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 3                                                                                        |
| HISTORIA DE USUARIO | COMO gerente PUEDO asignar la alícuota correspondiente al servicio prestado PARA cumplir con los reglamentos fiscales del país. |


| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU21                                                                                     |
| TÍTULO            | FACTURAR PAGO ADELANTADO                                                                 |
| PRIORIDAD         | Es bueno tenerla                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 3                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO realizar la factura por adelantado de un servicio prestado PARA facilitar al cliente el pago por servicios prestados y PARA registrar la operación por un servicio futuro |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU22                                                                                     |
| TÍTULO            | CONOCER SALDO PENDIENTE                                                                  |
| PRIORIDAD         | Es bueno tenerlo                                                                         |
| TIEMPO ESTIMADO (DÍAS) | 2                                                                                        |
| HISTORIA DE USUARIO | COMO empleado PUEDO conocer el saldo pendiente de las cuentas de los clientes PARA notificar al cliente si adeuda un servicio |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU23                                                                                     |
| TÍTULO            | CALCULAR SALDO PENDIENTE                                                                 |
| PRIORIDAD         | Debe tener                                                                               |
| TIEMPO ESTIMADO (DÍAS) | 5                                                                                        |
| HISTORIA DE USUARIO | COMO empleado DESEO que el sistema calcule automáticamente el saldo pendiente en caso de un pago parcial o total PARA evitar realizar el cálculo manual e introducir datos erróneos |

| Campo             | Valor                                                                                    |
|-------------------|------------------------------------------------------------------------------------------|
| ID                | HU24                                                                                     |
| TÍTULO            | GESTION AUTONOMA DEL CLIENTE                                                             |
| PRIORIDAD         | Fuera del alcance                                                                        |
| TIEMPO ESTIMADO (DÍAS) | 20                                                                                       |
| HISTORIA DE USUARIO | COMO gerente DESEO que el cliente pueda conocer el estado de su cuenta, y pagar las facturas de manera online PARA dar una mejor experiencia y PARA mejorar la satisfacción del cliente |


## Arquitectura de software
El software será una aplicación web. Seguirá la arquitectura cliente-servidor. Utilizando el patrón Modelo-Vista-Controlador

Tecnologías a utilizar: Spring Boot, PostgreSQL, Boostrap
 



