
# **ArgentaFact** - *Sistema integral de facturación y gestión de cuentas*



## **Descripción del Proyecto**
ArgentaFact es un sistema diseñado para la gestión integral de cuentas y facturación. Su funcionalidad principal radica en centralizar la administración de clientes, la emisión de comprobantes de acuerdo con la normativa argentina actual y el control detallado de los pagos vinculados a cada cuenta.
![ArgentaFact Logo](/docs/logo_argentaFact.png)


El sistema está desarrollado en **Java**, utilizando el framework **Spring Boot** para el backend, y **PostgreSQL** como base de datos relacional.

---

## **Características Principales**
- **Gestión de Cuentas**: Alta, baja y modificación de cuentas.
- **Gestión de Clientes**: Registro y manejo de clientes.
- **Gestión de Servicios**: Registro y manejo de servicios.
- **Gestión de Facturas**: Registro de operacion de venta.
- **Base de Datos**: Persistencia de datos con PostgreSQL.

---

## **Instrucciones de Uso**

### **1. Clonar el Repositorio**
Clona este repositorio en tu máquina local utilizando el siguiente comando:
```bash
git clone https://github.com/exebrites/ArgentaFact.git
```

### **2. Abrir el Proyecto**
1. Abre el proyecto en un IDE compatible con Java, como **IntelliJ IDEA** o **Eclipse**.
2. Asegúrate de que el IDE tenga soporte para **Maven** para gestionar las dependencias del proyecto.

### **3. Configurar la Base de Datos**
1. Instala PostgreSQL en tu sistema si no lo tienes configurado.
2. Crea una base de datos con el nombre `argentafact`.
3. Configura las credenciales de acceso a la base de datos en el archivo `src/main/resources/application.properties`. Por ejemplo:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/argentafact
    spring.datasource.username=TU_USUARIO
    spring.datasource.password=TU_CONTRASEÑA
    spring.jpa.hibernate.ddl-auto=update
    ```

### **4. Descargar Dependencias**
Ejecuta **Maven** para descargar las dependencias necesarias:
```bash
mvn clean install
```

### **5. Ejecutar el Proyecto**
1. Corre la aplicación desde el archivo principal (`ArgentaFactApplication.java`) en el IDE o desde la terminal:
    ```bash
    mvn spring-boot:run
    ```
2. Accede a la aplicación desde tu navegador en la dirección:  
   `http://localhost:8080`

---

## **Requisitos Previos**
- **Java JDK 17 o superior**.
- **PostgreSQL** instalado y configurado.
- **Maven** instalado (opcional si usas un IDE que lo gestione automáticamente).
- Un IDE compatible con proyectos **Spring Boot** (recomendado: IntelliJ IDEA).

---

## **Contribuciones**
Este proyecto es parte del trabajo integrador de la materia **Programación Orientada a Objetos II**. Si deseas contribuir, por favor abre un **issue** o envía un **pull request** con tus mejoras o sugerencias.

---

## **Licencia**
El proyecto ArgentaFact está disponible bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.