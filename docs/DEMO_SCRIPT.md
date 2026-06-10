# Guion para enseñar el proyecto en vídeo o entrevista

## 1. Presentación breve

"NexaStock es una aplicación full stack para gestionar inventario, clientes y pedidos. La he creado para demostrar competencias de backend, full stack y software engineering con un caso de negocio realista."

## 2. Mostrar login

Explica que el backend usa Spring Security y JWT. Enseña los dos usuarios demo:

- ADMIN: puede crear, actualizar y desactivar productos.
- MANAGER: puede consultar y operar con clientes y pedidos.

## 3. Mostrar dashboard

Explica que el dashboard consume un endpoint agregado del backend y muestra KPIs reales:

- productos activos,
- clientes activos,
- facturación,
- productos con stock bajo,
- últimos pedidos.

## 4. Mostrar productos

Crea un producto nuevo desde el frontend. Explica:

- validaciones,
- SKU único,
- stock mínimo,
- control de permisos con `@PreAuthorize`.

## 5. Mostrar clientes

Crea un cliente. Explica que hay validación de email y búsqueda paginada.

## 6. Crear pedido

Selecciona cliente, producto y cantidad. Explica la parte más importante:

- el pedido se guarda,
- se calcula el total,
- se descuenta stock,
- se registra movimiento de inventario,
- todo ocurre dentro de una transacción.

## 7. Mostrar Swagger

Entra en `/swagger-ui/index.html` y enseña que la API está documentada.

## 8. Mostrar Docker

Explica que el proyecto se levanta con:

```bash
docker compose up --build
```

## 9. Cierre

"Este proyecto demuestra que puedo desarrollar una solución completa desde la base de datos hasta la interfaz, aplicando seguridad, arquitectura por capas, documentación, testing y despliegue reproducible."
