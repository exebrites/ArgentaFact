# Trabajo en equipo​
​
# Diseño OO​
```mermaid
classDiagram
  

  class Empleado {
  -idEmpleado: Long
  -nombre: string
  -email: string
  -contraseña: string
}

class Cliente {
  -idCliente: Long
  -nombre: string
  -apellido: string
  -cuit: string
  -direccion: string
  -telefono: string
  -saldo: decimal
  -condicionFiscal: CondicionFiscal
}

class Servicio {
  -idServicio: Long
  -nombreServicio: string
  -descripcion: string
  -precio: decimal
}

class Factura {
  -idFactura: Long
  -numeroFactura: string
  -fechaEmision: date
  -tipoFactura: TipoFactura
  -total: decimal
  -estado: EstadoFactura
  +emitir()
  +anular()
}

class DetalleFactura {
  -idDetalleFactura: Long
  -cantidad: int
  -precioUnitario: decimal
  -subtotal: decimal
}

class Pago {
  -idPago: Long
  -fecha: date
  -monto: decimal
  -medioPago: TipoPago
  -estadoPago: EstadoPago
}

class Comprobante {
  -idComprobante: Long
  -tipoComprobante: TipoComprobante
  -archivo: string
  +generarArchivo()
}

class HistoricoFiscal {
  -idHistorico: Long
  -fechaOperacion: date
  -referenciaFactura: Factura
  -referenciaEmpleado: Empleado
  -referenciaCliente: Cliente
}
%% Enumeraciones
class TipoFactura {
    <<enumeration>>
    A
    B
    C
}

class EstadoFactura {
    <<enumeration>>
    EMITIDA
    PAGADA
    PENDIENTE
    PARCIALMENTE_PAGADA
    ANULADA
}

class EstadoPago {
    <<enumeration>>
    REGISTRADO
    ANULADO
}

class TipoPago {
    <<enumeration>>
    EFECTIVO
    TRANSFERENCIA
    TARJETA_CREDITO
    TARJETA_DEBITO
    CHEQUE
    BILLETERA_VIRTUAL
}

class TipoComprobante {
    <<enumeration>>
    FACTURA
    NOTA_CREDITO
    NOTA_DEBITO
    RECIBO
}

class CondicionFiscal {
    <<enumeration>>
    RESPONSABLE_INSCRIPTO
    MONOTRIBUTISTA
    EXENTO
    CONSUMIDOR_FINAL
}  
%% Relaciones
Empleado "1" --> "*" Factura : emite
Empleado "1" --> "*" Pago : registra
Empleado "1" --> "*" Comprobante : genera

Cliente "1" --> "*" Factura
Cliente "1" --> "*" Pago
Cliente "1" --> "*" Comprobante : recibe

Factura "1" --> "*" DetalleFactura
DetalleFactura "*" --> "1" Servicio

Factura "1" --> "*" Pago
Pago "*" --> "1" Factura
Pago "1" --> "1" HistoricoFiscal

Factura "1" --> "*" Comprobante
Factura "1" --> "1" HistoricoFiscal

Comprobante "1" --> "1" HistoricoFiscal

```

# Wireframe y caso de uso​

## Inicio de sesion
![Inicio de sesion](img/inicio_de_sesion.png)
## Dashboard
![Dashboard](img/dasboard_.png)
## Facturacion
### cargar factura
![Facturaci n](img/facturacion.png)
### preview factura
![Previsual Facturacion](img/preview_facturacion.png)
### formato pdf
![Formato PDF](img/formato_pdf.png)
## Gestion clientes
### Listar cliente
![img/listado_de_clientes.png](img/listado_de_clientes.png)
### Crear cliente
![img/crear_cliente.png](img/crear_cliente.png)


# Backlog de iteraciones​
​
# Tareas