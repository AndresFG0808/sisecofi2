# 📋 Guía de Configuración de SISECOFI en Local

## ✅ Cambios Realizados

### 1. **Authorization-Server** (Sistema Central de Autenticación Local)

- **Ubicación**: `c:\sisecofi\authorization-server`
- **Puerto**: 9000
- **Perfil**: dev
- **Base de Datos**: PostgreSQL (localhost:5432/sisecofi_auth)

#### Cambios:

- ✅ **AuthServiceImpl**: Actualizado para generar JWT con RFC, roles y permisos
- ✅ **AuthService**: Interfaz actualizada con método `validateToken`
- ✅ **UsuarioService/UsuarioServiceImpl**: Gestión completa de usuarios con RFC, email, administración
- ✅ **UsuarioRepository**: Queries por username, RFC, email
- ✅ **RolRepository**: Consultas de roles
- ✅ **PermisoRepository**: Consultas de permisos
- ✅ **AuthController**: Endpoints `/api/login`, `/api/validate`, `/admin/usuarios/*`
- ✅ **DTOs**: LoginResponse, UsuarioRequest, UsuarioResponse, RolDTO, PermisoDTO
- ✅ **application.yml**: Configurado para PostgreSQL, CORS, JWT
- ✅ **SecurityConfig**: Spring Security + JWT + CORS
- ✅ **Tablas SQL**: Script `init-auth-db.sql` con USUARIOS, ROLES, PERMISOS

### 2. **LibreriaComunes** (Biblioteca Compartida)

- **Ubicación**: `c:\sisecofi\fgla\LibreriaComunes`
- **Cambios pendientes**: Actualizar TokenUtil, SecurityFeignRequestInterceptorDev, CargaFilterDev, CargaFilter

### 3. **AdminContratos** (Microservicio Principal)

- **Ubicación**: `c:\sisecofi\fgla\AdminContratos\AdminContratos`
- **Puerto**: 9292
- **Base de Datos**: PostgreSQL (localhost:5432/postgres2 sisecofi)
- **Perfil**: dev o produccion

---

## 🚀 Pasos para Ejecutar en Local

### Paso 1: Crear Base de Datos

```sql
-- Conectarse a PostgreSQL
CREATE DATABASE sisecofi_auth;
```

### Paso 2: Ejecutar Script SQL

```bash
# Importar el script SQL en la base de datos creada
psql -U postgres -d sisecofi_auth -f c:\sisecofi\authorization-server\src\main\resources\init-auth-db.sql
```

### Paso 3: Iniciar Authorization-Server

```bash
cd c:\sisecofi\authorization-server
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

✅ Authorization-Server estará en: `http://localhost:9000/auth`

### Paso 4: Probar Endpoints de Autenticación

**4.1. Login:**

```bash
curl -X POST http://localhost:9000/auth/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Respuesta esperada:**

```json
{
  "token": "eyJhbGciOiJSUzI1NiIs...",
  "username": "admin",
  "rfc": "AAA000000AAA",
  "nombreCompleto": "Administrador Sistema",
  "email": "admin@sisecofi.gob.mx",
  "administracion": "SISECOFI",
  "fechaExpiracion": "2025-11-22T20:45:30.123456",
  "roles": [
    {
      "id": 1,
      "nombre": "ADMIN",
      "descripcion": "Administrador del sistema...",
      "permisos": [...]
    }
  ],
  "permisos": [...]
}
```

**4.2. Validar Token:**

```bash
curl -X GET http://localhost:9000/auth/api/validate \
  -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIs..."
```

### Paso 5: Iniciar AdminContratos

```bash
cd c:\sisecofi\fgla\AdminContratos\AdminContratos
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

✅ AdminContratos estará en: `http://localhost:9292`

---

## 📊 Estructura de Base de Datos

```
USUARIOS (id_usuario, username, password, rfc, nombre_completo, email, administracion, estado)
  ↓ (Many-to-Many)
USUARIOS_ROLES
  ↓
ROLES (id_rol, nombre, descripcion)
  ↓ (Many-to-Many)
ROLES_PERMISOS
  ↓
PERMISOS (id_permiso, nombre, descripcion, modulo)
```

---

## 🔑 Usuarios de Prueba

| Username | Password | RFC          | Rol   | Empresa  |
| -------- | -------- | ------------ | ----- | -------- |
| admin    | admin123 | AAA000000AAA | ADMIN | SISECOFI |

---

## 📝 Próximos Pasos

### 1. Actualizar TokenUtil de LibreriaComunes

- Obtener JWT del authorization-server en lugar de generar localmente
- Enviar usuario/contraseña al `/api/login`

### 2. Actualizar SecurityFeignRequestInterceptor

- Obtener JWT del contexto de seguridad
- Propagarlo en headers de llamadas Feign

### 3. Actualizar CargaFilterDev y CargaFilter

- Validar JWT del authorization-server
- Extraer RFC, roles, permisos del token
- Inyectar en contexto de seguridad

### 4. Probar Flujo Completo

- Login en Authorization-Server
- Usar JWT en AdminContratos
- Verificar llamadas Feign a otros microservicios

---

## 🛠️ Compilación

```bash
# Authorization-Server
cd c:\sisecofi\authorization-server
.\mvnw.cmd clean compile

# LibreriaComunes
cd c:\sisecofi\fgla\LibreriaComunes
.\mvnw.cmd clean compile

# AdminContratos
cd c:\sisecofi\fgla\AdminContratos\AdminContratos
.\mvnw.cmd clean compile
```

---

## 📌 Configuración de Perfiles

**dev**: Usa BD local, CORS permisivo, logs DEBUG
**produccion**: Usa BD remota (Oracle), JWT validado

---

## 🔍 Troubleshooting

**Error: "Connection refused"**

- Verificar que PostgreSQL está corriendo
- Verificar que la BD existe

**Error: "Usuario inactivo"**

- El estado del usuario debe ser 'ACTIVO'
- UPDATE USUARIOS SET ESTADO='ACTIVO' WHERE USERNAME='admin';

**Error: "Token inválido"**

- El token debe venir en header "Authorization: Bearer <token>"
- Verificar que no está expirado
