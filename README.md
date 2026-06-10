# NexaStock Fullstack

**NexaStock Fullstack** es una aplicación SaaS de gestión de inventario, clientes y pedidos pensada para demostrar perfil de **Full Stack Developer**, **Software Engineer** y **Backend Developer**.

El proyecto está preparado para portfolio y entrevistas: incluye backend con Spring Boot, frontend con React + TypeScript, autenticación JWT, roles, API REST documentada, Docker, datos de prueba, tests y documentación técnica.

![Vista previa de NexaStock](docs/preview.svg)

---

## Propuesta de valor

Muchas pequeñas empresas gestionan productos, clientes y pedidos con hojas de cálculo. NexaStock centraliza ese flujo en una plataforma web con control de stock, pedidos, movimientos de inventario, roles de usuario y panel de métricas.

El objetivo del proyecto no es solo “hacer pantallas”, sino demostrar capacidad para construir una solución completa con arquitectura limpia, reglas de negocio, persistencia, seguridad y despliegue reproducible.

---

## Stack técnico

### Backend

- Java 21
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- H2 para tests/local rápido
- Bean Validation
- OpenAPI / Swagger UI
- JUnit 5 + Mockito

### Frontend

- React 18
- TypeScript
- Vite
- React Router
- Axios
- CSS modular simple sin frameworks pesados

### DevOps / Calidad

- Docker Compose
- Dockerfile para backend y frontend
- GitHub Actions CI
- REST Client collection
- Documentación de arquitectura
- Separación por capas

---

## Funcionalidades implementadas

- Login con JWT.
- Roles: `ADMIN` y `MANAGER`.
- Dashboard con KPIs:
  - productos activos,
  - clientes,
  - pedidos pendientes,
  - facturación acumulada,
  - productos con stock bajo,
  - últimos pedidos.
- CRUD de productos.
- CRUD de clientes.
- Creación y consulta de pedidos.
- Descuento automático de stock al crear un pedido.
- Registro de movimientos de inventario.
- Validaciones de datos.
- Gestión centralizada de errores.
- Documentación OpenAPI.
- Datos iniciales para demo.

---

## Usuarios de prueba

Cuando arranca el backend, se crean automáticamente estos usuarios:

| Rol | Email | Contraseña |
|---|---|---|
| ADMIN | `admin@nexastock.dev` | `Admin123!` |
| MANAGER | `manager@nexastock.dev` | `Manager123!` |

---

## Cómo ejecutarlo con Docker

Requisitos:

- Docker
- Docker Compose

```bash
docker compose up --build
```

Servicios:

- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- PostgreSQL: puerto `5432`

---

## Cómo ejecutarlo en local sin Docker

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Crea un archivo `.env` en `/frontend` si quieres cambiar la URL de la API:

```env
VITE_API_URL=http://localhost:8080/api
```

---

## Estructura del proyecto

```text
nexastock-fullstack/
├── backend/
│   ├── src/main/java/com/portfolio/nexastock/
│   │   ├── auth/
│   │   ├── config/
│   │   ├── customer/
│   │   ├── dashboard/
│   │   ├── exception/
│   │   ├── order/
│   │   ├── product/
│   │   ├── stock/
│   │   └── user/
│   └── src/test/
├── frontend/
│   ├── src/components/
│   ├── src/context/
│   ├── src/pages/
│   ├── src/services/
│   └── src/types/
├── docs/
├── .github/workflows/
├── docker-compose.yml
└── README.md
```

---

## Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/login` | Login y generación de token JWT |
| GET | `/api/dashboard` | KPIs del dashboard |
| GET | `/api/products` | Listado paginado de productos |
| POST | `/api/products` | Crear producto |
| PUT | `/api/products/{id}` | Actualizar producto |
| DELETE | `/api/products/{id}` | Desactivar producto |
| GET | `/api/customers` | Listado paginado de clientes |
| POST | `/api/customers` | Crear cliente |
| PUT | `/api/customers/{id}` | Actualizar cliente |
| GET | `/api/orders` | Listado de pedidos |
| POST | `/api/orders` | Crear pedido y descontar stock |
| GET | `/api/orders/{id}` | Detalle de pedido |

---

## Qué demuestra este proyecto en una entrevista

Este proyecto permite defender experiencia en:

- Diseño de API REST profesional.
- Arquitectura por capas.
- Seguridad con JWT y Spring Security.
- Modelado de dominio con entidades relacionadas.
- Validación de datos y control de errores.
- Transacciones con reglas de negocio reales.
- React con TypeScript y consumo de API.
- Dockerización de una aplicación full stack.
- Documentación técnica orientada a equipo.
- Buenas prácticas para subir a GitHub.

---

## Ideas para ampliarlo

- Refresh tokens.
- Exportación de pedidos a PDF.
- Subida de imagen de producto.
- Auditoría avanzada por usuario.
- Filtros por fecha y estado de pedido.
- Tests de integración con Testcontainers.
- Pipeline CI/CD con despliegue automático.

---

## Texto corto para LinkedIn o portfolio

> NexaStock Fullstack es una plataforma web de gestión de inventario, clientes y pedidos desarrollada con Java 21, Spring Boot 3, Spring Security, JWT, PostgreSQL, React y TypeScript. El proyecto incluye arquitectura por capas, API REST documentada con OpenAPI, Docker Compose, validaciones, roles, tests y documentación técnica, demostrando competencias reales de Full Stack Developer, Backend Developer y Software Engineer.
