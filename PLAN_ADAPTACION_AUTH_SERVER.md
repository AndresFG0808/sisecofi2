# Plan de Adaptación: Authorization Server → Directorio Activo Local SISECOFI

## Estado Actual del Authorization Server

✅ **Ya tiene:**

- Entidades `Usuario` y `Rol` con relación Many-to-Many
- Login con JWT (RSA256)
- CRUD de usuarios
- Control de acceso básico
- Base de datos Oracle

## Adaptaciones Necesarias para SISECOFI

### 1. Entidades - Agregar campos SISECOFI

**Usuario:**

- Agregar: `rfc` (RFC largo)
- Agregar: `nombre_completo`
- Agregar: `email`
- Agregar: `administracion` (nombre de la administración del SAT)
- Agregar: `estado` (ACTIVO/INACTIVO)
- Agregar: `fecha_creacion`, `fecha_actualizacion`

**Rol:**

- Mantener igual pero renombrar a roles SISECOFI
- Ejemplos: ADMIN_CONTRATOS, ADMIN_GENERAL, ADMIN_PROVEEDORES, etc.

**Nuevo - Permiso:**

- Crear entidad `Permiso` para permisos granulares
- Crear tabla relacional `ROLES_PERMISOS`

### 2. DTOs - Mejorar para SISECOFI

**LoginResponse:**

- Agregar: `token`
- Agregar: `nombre_completo`
- Agregar: `rfc`
- Agregar: `roles` (lista de nombres de rol)
- Agregar: `permisos` (lista de permisos disponibles)
- Agregar: `fecha_expiracion_token`

### 3. AuthService - Mejorar

- Validar usuario activo (estado = ACTIVO)
- Incluir roles y permisos en JWT
- Incluir RFC del usuario en JWT claims
- Mejorar manejo de excepciones

### 4. Endpoints - Agregar/Modificar

**Login:**

- POST `/api/auth/login` ✅ (ya existe)
- Retornar datos completos del usuario

**Usuarios (Admin):**

- POST `/admin/usuarios` ✅ (ya existe)
- GET `/admin/usuarios` ✅ (ya existe)
- PUT `/admin/usuarios/{username}` (modificar)
- DELETE `/admin/usuarios/{username}` ✅ (ya existe)
- PATCH `/admin/usuarios/{username}/estado` (activar/desactivar)
- GET `/admin/usuarios/{username}/roles` (obtener roles)
- POST `/admin/usuarios/{username}/roles/{rolId}` (asignar rol)
- DELETE `/admin/usuarios/{username}/roles/{rolId}` (remover rol)

**Roles:**

- GET `/admin/roles` (listar todos)
- POST `/admin/roles` (crear rol)
- GET `/admin/roles/{id}/permisos` (obtener permisos del rol)

**Permisos:**

- GET `/admin/permisos` (listar todos)

**Validación:**

- POST `/api/auth/validate` (validar token sin consumir otro)

### 5. Configuración

- Cambiar `ddl-auto: validate` a `update` para desarrollo
- Agregar configuración de puerto flexible
- Agregar configuración de JWT (secret, TTL)
- Agregar CORS para AdminContratos y otros microservicios

### 6. Base de Datos

Tablas a modificar:

- USUARIOS_OAUTH → USUARIOS
- ROLES_OAUTH → ROLES
- USUARIOS_ROLES → USUARIOS_ROLES
- Crear: PERMISOS
- Crear: ROLES_PERMISOS
- Crear: PISTAS_AUDITORIA (para cumplir requisitos SISECOFI)

### 7. Seguridad

- Mantener BCryptPasswordEncoder ✅
- JWT con RSA ✅
- CORS configurado
- Validación de roles en endpoints

## Fases de Implementación

### Fase 1: Actualizar Entidades

1. Modificar `Usuario.java` (agregar RFC, nombre, email, administración, estado)
2. Modificar `Rol.java` (agregar descripción si es necesario)
3. Crear `Permiso.java`
4. Agregar relaciones Many-to-Many entre Rol y Permiso

### Fase 2: Actualizar DTOs

1. Mejorar `LoginResponse.java`
2. Crear `UsuarioDTO.java` completo
3. Crear `RolDTO.java`
4. Crear `PermisoDTO.java`

### Fase 3: Actualizar AuthService

1. Mejorar autenticación
2. Incluir RFC en token JWT
3. Incluir permisos en token
4. Mejor manejo de errores

### Fase 4: Crear Endpoints Nuevos

1. Endpoints de Roles
2. Endpoints de Permisos
3. Endpoints de Validación
4. Endpoints de Asignación de Roles a Usuarios

### Fase 5: Configuración y BD

1. Actualizar application.yml
2. Crear scripts SQL para tablas
3. Crear datos iniciales (roles, permisos)
4. Configurar CORS

### Fase 6: Integración con AdminContratos

1. Actualizar interceptores Feign en AdminContratos
2. Configurar URL del authorization-server
3. Probar flujo completo de login

## Ventajas de esta Aproximación

✅ Reutilizamos código existente
✅ Mantiene separación de responsabilidades
✅ Cada microservicio consulta el authorization-server
✅ Centraliza gestión de usuarios y roles
✅ Facilita auditoría
✅ Es escalable para agregar más microservicios
