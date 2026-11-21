# WebServer – API REST de Gestión Comercial

API REST desarrollada con **Spring Boot** que sirve como backend para la gestión de un negocio con varias sucursales. Permite administrar **categorías, productos, clientes, proveedores, sucursales y empleados** mediante operaciones CRUD (crear, leer, actualizar y eliminar) sobre una base de datos **MySQL**.

El proyecto incluye además un flujo completo de **integración y despliegue continuo (CI/CD)**: cada cambio en `main` se compila, se prueba, se empaqueta en una imagen **Docker**, se publica en **GitHub Container Registry** y se despliega automáticamente en un clúster **Kubernetes**.

---

## Tecnologías utilizadas

### Backend
| Tecnología | Uso en el proyecto |
|---|---|
| **Java 21** | Lenguaje principal. Se usan `record` para los DTOs. |
| **Spring Boot 4.1.1** | Framework base de la aplicación. |
| **Spring Web MVC** | Exposición de los endpoints REST (`@RestController`). |
| **Spring Data JPA (Hibernate)** | Acceso a datos y mapeo de entidades a tablas. |
| **Spring JDBC** | Soporte de conexión a la base de datos. |
| **Jakarta Bean Validation** | Validación de las peticiones (`@NotBlank`, `@Size`, `@Pattern`, `@Email`, etc.). |
| **Lombok** | Reduce código repetitivo (getters, setters, constructores, builder). |
| **Maven (Maven Wrapper)** | Gestión de dependencias y construcción (`mvnw`). |

### Base de datos
| Tecnología | Uso |
|---|---|
| **MySQL** (`mysql-connector-j`) | Base de datos de la aplicación en ejecución. |
| **H2** (en memoria, modo MySQL) | Base de datos usada únicamente en las pruebas. |

### Pruebas
- **Spring Boot Test** con `spring-boot-starter-webmvc-test` y `spring-boot-starter-jdbc-test`.
- Pruebas de controladores para **Cliente**, **Producto** y **Proveedor**.

### DevOps / Infraestructura
| Tecnología | Uso |
|---|---|
| **Docker** | `Dockerfile` multi-etapa: compila con `eclipse-temurin:21` y ejecuta con `eclipse-temurin:21-jre`. |
| **GitHub Actions** | Pipeline CI/CD en `.github/workflows/docker-image.yml`. |
| **GitHub Container Registry (ghcr.io)** | Registro donde se publican las imágenes. |
| **Kubernetes** | Manifiestos en `k8s/` (Deployment con 2 réplicas + Service tipo LoadBalancer). |

---

## Arquitectura

El código sigue una arquitectura en capas dentro de `src/main/java/com/API/WebServer/`:

```
controller/   → Endpoints REST: reciben la petición HTTP y devuelven la respuesta
service/      → Lógica de negocio y reglas de validación
repository/   → Interfaces Spring Data JPA para acceder a la base de datos
model/        → Entidades JPA (tablas de la base de datos)
dto/          → Objetos de entrada (Request) y salida (Response) de la API
mapper/       → Conversión entre entidades y DTOs
exception/    → Excepciones personalizadas y manejador global de errores
```

Flujo de una petición:

```
Cliente HTTP → Controller → Service → Repository → MySQL
                   ↑            │
                  DTO  ←  Mapper ←  Entidad
```

---

## Funcionalidades

Todas las rutas tienen el prefijo `/api`. Las peticiones y respuestas usan **JSON**.

### Categorías – `/api/categorias`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/categorias` | Lista todas las categorías. |
| GET | `/api/categorias?nombre={texto}` | Busca categorías cuyo nombre contenga el texto (sin distinguir mayúsculas, ordenadas por nombre). |
| GET | `/api/categorias/{idCategoria}` | Obtiene una categoría por su ID. |
| GET | `/api/categorias/exacto/{nombre}` | Obtiene una categoría por su nombre exacto. |
| POST | `/api/categorias` | Crea una categoría. |
| PUT | `/api/categorias/{idCategoria}` | Actualiza una categoría. |
| DELETE | `/api/categorias/{idCategoria}` | Elimina una categoría. |

**Reglas:** el nombre es obligatorio, único y de máximo 50 caracteres; la descripción es obligatoria (máx. 50). No se puede eliminar una categoría que tenga productos asociados (responde `409 Conflict`).

### Productos – `/api/productos`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/productos` | Lista todos los productos. |
| GET | `/api/productos?idCategoria={id}` | Lista los productos de una categoría. |
| GET | `/api/productos/{idProducto}` | Obtiene un producto por su ID. |
| POST | `/api/productos` | Crea un producto. |
| PUT | `/api/productos/{idProducto}` | Actualiza un producto. |
| DELETE | `/api/productos/{idProducto}` | Elimina un producto. |

**Reglas:** nombre obligatorio (máx. 100), descripción opcional (máx. 255), precio mayor a 0 con máximo 8 enteros y 2 decimales, stock mayor o igual a 0 y una categoría existente (si no existe responde `400 Bad Request`).

### Clientes – `/api/clientes`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/clientes` | Lista todos los clientes. |
| GET | `/api/clientes?dni={dni}` | Busca un cliente por su DNI. |
| POST | `/api/clientes` | Registra un cliente. |
| PUT | `/api/clientes/{idCliente}` | Actualiza un cliente. |
| DELETE | `/api/clientes/{idCliente}` | Elimina un cliente. |

**Reglas:** nombres, apellidos y dirección obligatorios (3 a 50 caracteres), DNI de exactamente 8 caracteres y único, teléfono de al menos 7 caracteres y fecha de registro obligatoria al crear.

### Proveedores – `/api/proveedores`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/proveedores` | Lista todos los proveedores. |
| GET | `/api/proveedores/{id}` | Obtiene un proveedor por su ID. |
| GET | `/api/proveedores/buscar?razonSocial={texto}` | Busca proveedores por razón social (coincidencia parcial, sin distinguir mayúsculas). |
| GET | `/api/proveedores/ruc/{ruc}` | Obtiene un proveedor por su RUC. |
| POST | `/api/proveedores` | Crea un proveedor. |
| PUT | `/api/proveedores/{id}` | Actualiza un proveedor. |
| DELETE | `/api/proveedores/{id}` | Elimina un proveedor. |

**Reglas:** RUC obligatorio de 11 dígitos y único (si se repite responde `409 Conflict`), razón social obligatoria, teléfono con formato válido, correo con formato de email. Teléfono y correo también son únicos. No se puede eliminar un proveedor con registros asociados.

### Sucursales – `/api/sucursales`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/sucursales` | Lista todas las sucursales. |
| POST | `/api/sucursales` | Crea una sucursal. |
| PUT | `/api/sucursales/{idSucursal}` | Actualiza una sucursal. |
| DELETE | `/api/sucursales/{idSucursal}` | Elimina una sucursal. |

**Reglas:** nombre obligatorio (máx. 50); dirección (máx. 200), teléfono (máx. 9) y ciudad (máx. 100) opcionales.

### Empleados – `/api/empleados`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/empleados` | Lista todos los empleados. |
| GET | `/api/empleados?dni={dni}` | Busca un empleado por su DNI. |
| POST | `/api/empleados` | Registra un empleado. |
| PUT | `/api/empleados/{idEmpleado}` | Actualiza un empleado. |
| DELETE | `/api/empleados/{idEmpleado}` | Elimina un empleado. |

**Reglas:** cada empleado pertenece a una sucursal (`idSucursal` obligatorio y positivo). Nombres y apellidos obligatorios (3 a 100 caracteres), DNI obligatorio de exactamente 8 dígitos y único. Campos opcionales: cargo (máx. 100), sueldo (no negativo, máximo 8 enteros y 2 decimales), teléfono (7 a 20 caracteres) y correo con formato de email (máx. 150).

### Manejo de errores

El `GlobalExceptionHandler` centraliza las respuestas de error:

| Código | Cuándo ocurre |
|---|---|
| `400 Bad Request` | Datos inválidos. Devuelve `{"errores": {"campo": "mensaje"}}` con cada campo que falló. |
| `404 Not Found` | El recurso solicitado no existe. |
| `409 Conflict` | RUC duplicado o se intenta eliminar un registro que tiene datos relacionados. |

Ejemplo de error de validación:

```json
{
  "errores": {
    "ruc": "El RUC debe tener 11 dígitos",
    "razonSocial": "La razón social es obligatoria"
  }
}
```

---

## Ejemplos de uso

Crear una categoría:

```bash
curl -X POST http://localhost:8080/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Bebidas", "descripcion": "Gaseosas, jugos y aguas"}'
```

Crear un producto en esa categoría:

```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Agua mineral 625ml", "descripcion": "Sin gas", "precio": 2.50, "stock": 100, "idCategoria": 1}'
```

Buscar un proveedor por RUC:

```bash
curl http://localhost:8080/api/proveedores/ruc/20123456789
```

---

## Configuración y ejecución

### Requisitos
- Java 21
- Una base de datos MySQL accesible
- (Opcional) Docker y Kubernetes para el despliegue

### Variables de entorno

La aplicación lee su configuración desde variables de entorno o desde un archivo `.env` en la raíz del proyecto (opcional):

| Variable | Descripción | Ejemplo |
|---|---|---|
| `DB_URL` | URL JDBC de MySQL | `jdbc:mysql://localhost:3306/webserver` |
| `DB_USERNAME` | Usuario de la base de datos | `root` |
| `DB_PASSWORD` | Contraseña de la base de datos | `********` |
| `PORT` / `SERVER_PORT` | Puerto HTTP (por defecto `8080`) | `8080` |

> El archivo `.env` no debe subirse al repositorio.

### Ejecutar en local

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

La API quedará disponible en `http://localhost:8080`.

### Ejecutar las pruebas

```bash
./mvnw test
```

Las pruebas usan una base H2 en memoria (configurada en `src/test/resources/application.properties`), por lo que no necesitan MySQL.

### Ejecutar con Docker

```bash
docker build -t webserver .
docker run -p 8080:8080 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/webserver \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=tu_password \
  webserver
```

Se pueden ajustar las opciones de la JVM con la variable `JAVA_OPTS` (por defecto `-Xms256m -Xmx512m`).

---

## Pipeline CI/CD

El workflow de GitHub Actions (`.github/workflows/docker-image.yml`) se ejecuta en cada *push* y *pull request* hacia `main` y consta de cuatro etapas:

1. **build** – Compila el proyecto con JDK 21 (Temurin).
2. **test** – Ejecuta las pruebas automatizadas.
3. **package** – Genera el `.jar`, construye la imagen Docker y, si no es un pull request, la publica en `ghcr.io` con las etiquetas `latest` y el SHA corto del commit.
4. **deploy** – Solo en *push* a `main`. Corre en un runner *self-hosted* que:
   - Crea/actualiza el Secret de Kubernetes `mysql-credentials` con las credenciales de la base de datos.
   - Aplica `k8s/deployment.yaml` reemplazando `IMAGE_PLACEHOLDER` por la imagen recién publicada.
   - Aplica `k8s/service.yaml`.
   - Espera a que el despliegue termine (`kubectl rollout status`).

### Kubernetes
- **Deployment `demo`**: 2 réplicas, puerto 8080, credenciales inyectadas desde el Secret `mysql-credentials`, *readiness probe* TCP y límite de 512 MiB de memoria. Descarga la imagen usando el secret `ghcr-secret`.
- **Service `demo`**: tipo `LoadBalancer`, expone el puerto **80** y lo redirige al 8080 del contenedor.

Secrets necesarios en GitHub: `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`.

---

## Estructura del proyecto

```
Herramientas-Proyecto/
├── .github/workflows/docker-image.yml   # Pipeline CI/CD
├── k8s/
│   ├── deployment.yaml                  # Deployment de Kubernetes
│   └── service.yaml                     # Service LoadBalancer
├── src/
│   ├── main/
│   │   ├── java/com/API/WebServer/      # Código fuente (capas)
│   │   └── resources/application.properties
│   └── test/
│       ├── java/com/API/WebServer/      # Pruebas de controladores
│       └── resources/application.properties
├── Dockerfile
├── mvnw / mvnw.cmd
└── pom.xml
```
