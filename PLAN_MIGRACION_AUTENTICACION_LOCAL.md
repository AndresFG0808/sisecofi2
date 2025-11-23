# Plan de Migración a Autenticación Local - SISECOFI AdminContratos

## Estado Actual

- **Autenticación**: OAuth remoto con e.firma (SAT)
- **Componente Externo**: Validación de certificados digitales
- **Tokens**: JWT firmados con clave pública del SAT (producción) o UsernameToken en dev
- **Filtros de Seguridad**:
  - `FiltroToken` (producción): valida JWT con clave pública SAT
  - `FiltroTokenDev` (desarrollo): usa `CustomFilterDev` que inyecta `UsernameToken`

## Problema

- Se requiere autenticación local independiente del SAT
- Necesidad de definir 16 tipos de administradores/roles locales
- Migrar de OAuth remoto a sistema de autenticación local

## Fases de Implementación

### Fase 1: Análisis de Roles y Permisos

1. **Revisar documentación** en `docMD_Sisecofi`
2. **Identificar los 16 tipos de administradores**:
   - Empleado SAT
   - ACSMC (Administración Central de Seguridad, Monitoreo y Control)
   - Otros roles a identificar
3. **Mapear permisos por rol**

### Fase 2: Diseño de Modelo de Datos Local

Crear tablas en BD:

- `usuarios` (id, username, email, password_hash, nombre_completo, rfc, activo, fecha_creacion)
- `roles` (id, nombre_rol, descripcion)
- `usuarios_roles` (usuario_id, rol_id)
- `permisos` (id, nombre_permiso, descripcion, modulo)
- `roles_permisos` (rol_id, permiso_id)

### Fase 3: Implementar Autenticación Local

1. **Crear endpoint de login**

   - POST `/api/auth/login`
   - Body: `{username, password}`
   - Response: `{token, usuario_info}`

2. **Generar JWT local**

   - Usar `jjwt` library (ya está en dependencias)
   - Secret configurado en `application.properties`
   - TTL configurable por perfil

3. **Actualizar CustomFilter para validar JWT local**
   - Remover validación con clave pública SAT
   - Validar JWT con secret local
   - Extraer roles y permisos del token

### Fase 4: Implementar Autorización

1. **Usar `@PreAuthorize`** en controladores

   - `@PreAuthorize("hasRole('ADMIN_CONTRATOS')")`
   - `@PreAuthorize("hasAnyRole('ADMIN_GENERAL', 'ADMIN_CONTRATOS')")`

2. **Configurar MethodSecurity**
   - `@EnableMethodSecurity(prePostEnabled = true)` ✓ Ya está

### Fase 5: Migrar Interceptores Feign

- `SecurityFeignRequestInterceptor`: usar JWT local
- `SecurityFeignRequestInterceptorDev`: mantener pero compatible con JWT local

### Fase 6: Configurar Perfiles

- **dev**: autenticación local con BD embebida o local
- **local**: autenticación local con BD local
- **produccion**: mantener o actualizar según sea necesario

## Archivos a Crear/Modificar

### Crear en `LibreriaComunes`:

- `security/JwtTokenProvider.java` - Generación y validación de JWT
- `security/CustomFilterLocal.java` - Filtro para autenticación local
- `controller/AuthController.java` - Endpoint de login
- `service/UsuarioService.java` - Gestión de usuarios
- `service/RolService.java` - Gestión de roles
- `model/Usuario.java` - Entidad de usuario
- `model/Rol.java` - Entidad de rol
- `model/Permiso.java` - Entidad de permiso
- `dto/LoginRequest.java`
- `dto/LoginResponse.java`
- `dto/UsuarioDTO.java`

### Modificar en `AdminContratos`:

- `security/FiltroTokenDev.java` - Usar nuevo CustomFilterLocal
- `application-dev.properties` - Configuración JWT local
- `application-local.properties` - Nuevo perfil local
- Agregar `@PreAuthorize` en controladores según roles

### Modificar en `pom.xml`:

- ✓ Ya tiene `jjwt` (io.jsonwebtoken:jjwt)

## Pasos Siguientes

1. ✓ Analizar estructura actual (EN PROGRESO)
2. Revisar documentación de roles
3. Diseñar esquema de BD
4. Crear entidades y repositorios
5. Implementar autenticación local
6. Configurar JWT
7. Actualizar seguridad en AdminContratos
8. Probar y documentar

## Notas Importantes

- Mantener compatibilidad con autenticación remota en producción
- Los perfiles (dev/produccion) deben ser flexibles
- Documentación menciona roles del SAT, pero necesitamos roles locales
- Se requiere una capa de mapeo entre sistemas
