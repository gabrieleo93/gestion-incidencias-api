# Gestión de Incidencias API

API REST desarrollada con **Java 17** y **Spring Boot** para gestionar usuarios e incidencias internas dentro de una organización o equipo de soporte.

El proyecto está orientado a demostrar buenas prácticas de backend para un perfil junior: arquitectura por capas, uso de DTOs, mappers, validaciones, manejo global de errores, persistencia con JPA, documentación con Swagger/OpenAPI, autenticación con JWT y autorización por roles.

---

## Tecnologías utilizadas

* Java 17
* Spring Boot 3.5.5
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* BCrypt
* MySQL
* Lombok
* Bean Validation
* Swagger / OpenAPI
* Maven
* Postman
* Git / GitHub

---

## Funcionalidades principales

### Usuarios

* Crear usuarios.
* Listar usuarios.
* Buscar usuario por ID.
* Validar email duplicado.
* Encriptar contraseñas con BCrypt.
* Ocultar contraseña en las respuestas.

### Autenticación y seguridad

* Login mediante email y contraseña.
* Generación de token JWT.
* Protección de endpoints privados mediante Bearer Token.
* Configuración stateless con Spring Security.
* Filtro JWT para validar peticiones autenticadas.
* Autorización por roles: `USER`, `ADMIN` y `TECNICO`.

### Incidencias

* Crear incidencias.
* Listar incidencias.
* Buscar incidencia por ID.
* Filtrar incidencias por usuario creador.
* Filtrar incidencias por estado.
* Filtrar incidencias por prioridad.
* Actualizar estado de una incidencia.
* Actualizar prioridad de una incidencia.

### Reglas de negocio

* Una incidencia nueva se crea automáticamente con estado `ABIERTA`.
* No se puede cambiar la prioridad de una incidencia `RESUELTA` o `CERRADA`.
* No se puede modificar una incidencia `CERRADA` desde el flujo normal.
* Si una incidencia pasa a `RESUELTA` o `CERRADA`, se registra la fecha de cierre.

### Manejo de errores

El proyecto incluye un manejador global de excepciones para devolver respuestas claras en formato JSON ante:

* Recursos no encontrados.
* Peticiones inválidas.
* Errores de validación.
* Errores inesperados.

---

## Roles y permisos

| Rol       | Permisos principales                                                  |
| --------- | --------------------------------------------------------------------- |
| `USER`    | Crear incidencias                                                     |
| `TECNICO` | Consultar incidencias, cambiar estado y prioridad                     |
| `ADMIN`   | Gestionar usuarios, consultar incidencias, cambiar estado y prioridad |

### Reglas de acceso implementadas

| Endpoint                  | Acceso                     |
| ------------------------- | -------------------------- |
| `POST /api/auth/login`    | Público                    |
| `POST /api/usuarios`      | Público                    |
| `GET /api/usuarios`       | Solo `ADMIN`               |
| `GET /api/usuarios/{id}`  | Solo `ADMIN`               |
| `POST /api/incidencias`   | `USER`, `TECNICO`, `ADMIN` |
| `GET /api/incidencias`    | `TECNICO`, `ADMIN`         |
| `GET /api/incidencias/**` | `TECNICO`, `ADMIN`         |
| `PUT /api/incidencias/**` | `TECNICO`, `ADMIN`         |
| Swagger/OpenAPI           | Público                    |

---

## Estructura del proyecto

```text
src/main/java/com/gestionincidencias/api
│
├── config
├── controllers
├── dto
├── entities
├── enums
├── exceptions
├── mappers
├── repositories
└── services
```

---

## Modelo de datos

### Usuario

Representa a una persona registrada en el sistema.

Campos principales:

* `id`
* `nombre`
* `apellido`
* `email`
* `password`
* `rol`
* `estado`
* `fechaRegistro`

### Incidencia

Representa un problema, solicitud o reporte generado por un usuario.

Campos principales:

* `id`
* `titulo`
* `descripcion`
* `estado`
* `prioridad`
* `fechaCreacion`
* `fechaActualizacion`
* `fechaCierre`
* `usuarioCreador`

Relación principal:

```text
Usuario 1 ---- N Incidencias
```

Un usuario puede crear muchas incidencias, pero cada incidencia pertenece a un único usuario creador.

---

## Estados de incidencia

```text
ABIERTA
EN_PROCESO
RESUELTA
CERRADA
```

---

## Prioridades de incidencia

```text
BAJA
MEDIA
ALTA
CRITICA
```

---

## Endpoints principales

### Autenticación

| Método | Endpoint          | Descripción                        |
| ------ | ----------------- | ---------------------------------- |
| POST   | `/api/auth/login` | Iniciar sesión y obtener token JWT |

### Usuarios

| Método | Endpoint             | Descripción           |
| ------ | -------------------- | --------------------- |
| POST   | `/api/usuarios`      | Crear un usuario      |
| GET    | `/api/usuarios`      | Listar usuarios       |
| GET    | `/api/usuarios/{id}` | Buscar usuario por ID |

### Incidencias

| Método | Endpoint                                         | Descripción                       |
| ------ | ------------------------------------------------ | --------------------------------- |
| POST   | `/api/incidencias`                               | Crear una incidencia              |
| GET    | `/api/incidencias`                               | Listar incidencias                |
| GET    | `/api/incidencias/{id}`                          | Buscar incidencia por ID          |
| GET    | `/api/incidencias/usuario/{usuarioId}`           | Filtrar incidencias por usuario   |
| GET    | `/api/incidencias/estado/{estado}`               | Filtrar incidencias por estado    |
| GET    | `/api/incidencias/prioridad/{prioridad}`         | Filtrar incidencias por prioridad |
| PUT    | `/api/incidencias/{id}/estado?estado=EN_PROCESO` | Actualizar estado                 |
| PUT    | `/api/incidencias/{id}/prioridad?prioridad=ALTA` | Actualizar prioridad              |

---

## Seguridad

El proyecto utiliza **Spring Security** con autenticación basada en **JWT**.

Flujo básico:

1. El usuario se registra mediante `POST /api/usuarios`.
2. El usuario inicia sesión mediante `POST /api/auth/login`.
3. El backend valida email y contraseña.
4. Si las credenciales son correctas, devuelve un token JWT.
5. El cliente debe enviar el token en las peticiones protegidas usando el header:

```http
Authorization: Bearer TOKEN
```

Ejemplo:

```http
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

---

## Ejemplos de uso

### Crear usuario

```http
POST /api/usuarios
```

Body:

```json
{
  "nombre": "Gabriel",
  "apellido": "Isturiz",
  "email": "gabriel@test.com",
  "password": "123456"
}
```

Respuesta esperada:

```json
{
  "id": 1,
  "nombre": "Gabriel",
  "apellido": "Isturiz",
  "email": "gabriel@test.com",
  "rol": "USER",
  "estado": "ACTIVO",
  "fechaRegistro": "2026-06-02T16:30:00"
}
```

---

### Login

```http
POST /api/auth/login
```

Body:

```json
{
  "email": "gabriel@test.com",
  "password": "123456"
}
```

Respuesta esperada:

```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tipo": "Bearer",
  "usuarioId": 1,
  "nombre": "Gabriel",
  "email": "gabriel@test.com",
  "rol": "USER"
}
```

---

### Crear incidencia

```http
POST /api/incidencias
```

Header:

```http
Authorization: Bearer TOKEN
```

Body:

```json
{
  "titulo": "No puedo acceder al sistema",
  "descripcion": "El usuario no puede iniciar sesión desde esta mañana.",
  "prioridad": "ALTA",
  "usuarioCreadorId": 1
}
```

Respuesta esperada:

```json
{
  "id": 1,
  "titulo": "No puedo acceder al sistema",
  "descripcion": "El usuario no puede iniciar sesión desde esta mañana.",
  "estado": "ABIERTA",
  "prioridad": "ALTA",
  "fechaCreacion": "2026-06-02T16:45:00",
  "fechaActualizacion": null,
  "fechaCierre": null,
  "usuarioCreadorId": 1,
  "nombreUsuarioCreador": "Gabriel Isturiz"
}
```

---

## Swagger

La documentación interactiva de la API está disponible en:

```text
http://localhost:8082/swagger-ui/index.html
```

Desde Swagger se pueden consultar y probar los endpoints disponibles.

---

## Configuración de base de datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE gestion_incidencias;
```

Configurar `application.properties`:

```properties
spring.application.name=api

server.port=8082

spring.datasource.url=jdbc:mysql://localhost:3306/gestion_incidencias?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

> Nota: se recomienda configurar la contraseña de base de datos y la clave secreta de JWT mediante variables de entorno para evitar subir credenciales reales al repositorio.

---

## Ejecución del proyecto

Clonar el repositorio:

```bash
git clone URL_DEL_REPOSITORIO
```

Entrar en la carpeta del proyecto:

```bash
cd gestion-incidencias-api
```

Compilar el proyecto:

```bash
mvn clean install -DskipTests
```

Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

La API estará disponible en:

```text
http://localhost:8082
```

---

## Pruebas realizadas

Se realizaron pruebas manuales con Postman para validar:

* Registro de usuarios.
* Login y generación de JWT.
* Acceso a endpoints protegidos con Bearer Token.
* Bloqueo de endpoints protegidos sin token.
* Validación de permisos por rol.
* Creación de incidencias.
* Consulta de incidencias por rol autorizado.
* Actualización de estado y prioridad por roles autorizados.
* Respuestas de error controladas mediante `GlobalExceptionHandler`.

---

## Estado actual del proyecto

Proyecto backend funcional con:

* CRUD básico de usuarios.
* Login con JWT.
* Contraseñas encriptadas con BCrypt.
* Rutas protegidas mediante Bearer Token.
* Autorización por roles `USER`, `ADMIN` y `TECNICO`.
* Gestión de incidencias.
* Filtros por usuario, estado y prioridad.
* Validaciones con Bean Validation.
* Manejo global de errores.
* Documentación Swagger/OpenAPI.
* Persistencia en MySQL.

---

## Próximas mejoras

* Añadir tests unitarios e integración.
* Crear frontend en Angular para consumir la API.
* Agregar paginación y ordenación en listados.
* Mejorar documentación con capturas de Postman y Swagger.
* Preparar colección de Postman para compartir pruebas.
* Añadir Docker para facilitar la ejecución del proyecto.

---

## Autor

Gabriel Leonardo Isturiz Guía
Desarrollador Web Junior Full Stack
