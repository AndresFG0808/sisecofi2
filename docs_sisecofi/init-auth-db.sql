-- ============================================
-- Script de Inicialización de Base de Datos
-- Base de datos: sisecofi_auth
-- Sistema: SISECOFI - Authorization Server
-- ============================================

-- Crear base de datos (ejecutar esto primero si no existe)
-- CREATE DATABASE sisecofi_auth ENCODING='UTF8';

-- Conectarse a la base de datos sisecofi_auth antes de ejecutar lo siguiente

-- ============================================
-- TABLA: USUARIOS
-- ============================================
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rfc VARCHAR(13) UNIQUE NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    administracion VARCHAR(255),
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP,
    intentos_fallidos INTEGER DEFAULT 0,
    bloqueado BOOLEAN DEFAULT FALSE,
    fecha_expiracion_password TIMESTAMP,
    requiere_cambio_password BOOLEAN DEFAULT FALSE
);

-- ============================================
-- TABLA: ROLES
-- ============================================
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA: PERMISOS
-- ============================================
CREATE TABLE IF NOT EXISTS permisos (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(100) UNIQUE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    modulo VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA: USUARIO_ROLES (Relación N:M)
-- ============================================
CREATE TABLE IF NOT EXISTS usuario_roles (
    usuario_id INTEGER NOT NULL,
    rol_id INTEGER NOT NULL,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- ============================================
-- TABLA: ROL_PERMISOS (Relación N:M)
-- ============================================
CREATE TABLE IF NOT EXISTS rol_permisos (
    rol_id INTEGER NOT NULL,
    permiso_id INTEGER NOT NULL,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (rol_id, permiso_id),
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permiso_id) REFERENCES permisos(id) ON DELETE CASCADE
);

-- ============================================
-- TABLA: TOKENS_REVOCADOS
-- ============================================
CREATE TABLE IF NOT EXISTS tokens_revocados (
    id SERIAL PRIMARY KEY,
    jti VARCHAR(255) UNIQUE NOT NULL,
    usuario_id INTEGER,
    fecha_revocacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_expiracion TIMESTAMP NOT NULL,
    motivo VARCHAR(500),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- ============================================
-- TABLA: AUDITORIA_ACCESOS
-- ============================================
CREATE TABLE IF NOT EXISTS auditoria_accesos (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER,
    username VARCHAR(100),
    accion VARCHAR(50) NOT NULL, -- LOGIN, LOGOUT, LOGIN_FAILED, TOKEN_REFRESH
    ip_address VARCHAR(50),
    user_agent TEXT,
    exitoso BOOLEAN DEFAULT TRUE,
    mensaje TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- ============================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- ============================================
CREATE INDEX IF NOT EXISTS idx_usuarios_username ON usuarios(username);
CREATE INDEX IF NOT EXISTS idx_usuarios_rfc ON usuarios(rfc);
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);
CREATE INDEX IF NOT EXISTS idx_usuarios_estado ON usuarios(estado);
CREATE INDEX IF NOT EXISTS idx_tokens_jti ON tokens_revocados(jti);
CREATE INDEX IF NOT EXISTS idx_tokens_expiracion ON tokens_revocados(fecha_expiracion);
CREATE INDEX IF NOT EXISTS idx_auditoria_usuario ON auditoria_accesos(usuario_id);
CREATE INDEX IF NOT EXISTS idx_auditoria_fecha ON auditoria_accesos(fecha);
CREATE INDEX IF NOT EXISTS idx_auditoria_accion ON auditoria_accesos(accion);

-- ============================================
-- DATOS INICIALES: ROLES
-- ============================================
INSERT INTO roles (nombre, descripcion) VALUES
('ADMIN', 'Administrador del sistema con acceso total'),
('SUPERVISOR', 'Supervisor con permisos de lectura y escritura'),
('OPERADOR', 'Operador con permisos básicos de operación'),
('CONSULTA', 'Usuario de solo consulta')
ON CONFLICT (nombre) DO NOTHING;

-- ============================================
-- DATOS INICIALES: PERMISOS
-- ============================================
INSERT INTO permisos (codigo, nombre, descripcion, modulo) VALUES
-- Usuarios
('USER_CREATE', 'Crear Usuarios', 'Permiso para crear nuevos usuarios', 'USUARIOS'),
('USER_READ', 'Consultar Usuarios', 'Permiso para consultar usuarios', 'USUARIOS'),
('USER_UPDATE', 'Actualizar Usuarios', 'Permiso para actualizar usuarios', 'USUARIOS'),
('USER_DELETE', 'Eliminar Usuarios', 'Permiso para eliminar usuarios', 'USUARIOS'),

-- Roles
('ROLE_CREATE', 'Crear Roles', 'Permiso para crear roles', 'ROLES'),
('ROLE_READ', 'Consultar Roles', 'Permiso para consultar roles', 'ROLES'),
('ROLE_UPDATE', 'Actualizar Roles', 'Permiso para actualizar roles', 'ROLES'),
('ROLE_DELETE', 'Eliminar Roles', 'Permiso para eliminar roles', 'ROLES'),

-- Contratos
('CONTRATO_CREATE', 'Crear Contratos', 'Permiso para crear contratos', 'CONTRATOS'),
('CONTRATO_READ', 'Consultar Contratos', 'Permiso para consultar contratos', 'CONTRATOS'),
('CONTRATO_UPDATE', 'Actualizar Contratos', 'Permiso para actualizar contratos', 'CONTRATOS'),
('CONTRATO_DELETE', 'Eliminar Contratos', 'Permiso para eliminar contratos', 'CONTRATOS'),

-- Proyectos
('PROYECTO_CREATE', 'Crear Proyectos', 'Permiso para crear proyectos', 'PROYECTOS'),
('PROYECTO_READ', 'Consultar Proyectos', 'Permiso para consultar proyectos', 'PROYECTOS'),
('PROYECTO_UPDATE', 'Actualizar Proyectos', 'Permiso para actualizar proyectos', 'PROYECTOS'),
('PROYECTO_DELETE', 'Eliminar Proyectos', 'Permiso para eliminar proyectos', 'PROYECTOS'),

-- Catálogos
('CATALOGO_CREATE', 'Crear Catálogos', 'Permiso para crear catálogos', 'CATALOGOS'),
('CATALOGO_READ', 'Consultar Catálogos', 'Permiso para consultar catálogos', 'CATALOGOS'),
('CATALOGO_UPDATE', 'Actualizar Catálogos', 'Permiso para actualizar catálogos', 'CATALOGOS'),
('CATALOGO_DELETE', 'Eliminar Catálogos', 'Permiso para eliminar catálogos', 'CATALOGOS'),

-- Reportes
('REPORTE_READ', 'Generar Reportes', 'Permiso para generar reportes', 'REPORTES'),
('REPORTE_EXPORT', 'Exportar Reportes', 'Permiso para exportar reportes', 'REPORTES')
ON CONFLICT (codigo) DO NOTHING;

-- ============================================
-- ASIGNAR PERMISOS A ROLES
-- ============================================
-- ADMIN tiene todos los permisos
INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'ADMIN'
ON CONFLICT DO NOTHING;

-- SUPERVISOR tiene permisos de lectura, creación y actualización
INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'SUPERVISOR' 
  AND p.codigo NOT LIKE '%_DELETE'
ON CONFLICT DO NOTHING;

-- OPERADOR tiene permisos básicos de operación
INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'OPERADOR' 
  AND (p.codigo LIKE '%_READ' OR p.codigo LIKE '%_CREATE' OR p.codigo LIKE '%_UPDATE')
  AND p.modulo IN ('CONTRATOS', 'PROYECTOS', 'CATALOGOS')
ON CONFLICT DO NOTHING;

-- CONSULTA solo tiene permisos de lectura
INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'CONSULTA' 
  AND p.codigo LIKE '%_READ'
ON CONFLICT DO NOTHING;

-- ============================================
-- DATOS INICIALES: USUARIO ADMINISTRADOR
-- ============================================
-- Password: admin123 (encriptado con BCrypt)
-- BCrypt hash para "admin123": $2a$10$N9qo8uLOickgx2ZMRZoMye1oNrpPZdqUlTZFqHO9v9H3CZMDvNiXi
INSERT INTO usuarios (username, password, rfc, nombre_completo, email, administracion, estado)
VALUES (
    'admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMye1oNrpPZdqUlTZFqHO9v9H3CZMDvNiXi',
    'AAA000000AAA',
    'Administrador Sistema',
    'admin@sisecofi.gob.mx',
    'SISECOFI',
    'ACTIVO'
)
ON CONFLICT (username) DO NOTHING;

-- Asignar rol ADMIN al usuario admin
INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id
FROM usuarios u, roles r
WHERE u.username = 'admin' AND r.nombre = 'ADMIN'
ON CONFLICT DO NOTHING;

-- ============================================
-- USUARIO DE DESARROLLO (OPCIONAL)
-- ============================================
-- Password: dev123
INSERT INTO usuarios (username, password, rfc, nombre_completo, email, administracion, estado)
VALUES (
    'ayuso2104',
    '$2a$10$xB8nJl9p6Q1oEf5X7Y8z7OqKqGp5JZ9K1nU8vL5R7wM6xT3dN1qVy',
    'AYUS210490DEV',
    'Usuario Desarrollo',
    'ayuso2104@sisecofi.gob.mx',
    'SISECOFI',
    'ACTIVO'
)
ON CONFLICT (username) DO NOTHING;

-- Asignar rol ADMIN al usuario de desarrollo
INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id
FROM usuarios u, roles r
WHERE u.username = 'ayuso2104' AND r.nombre = 'ADMIN'
ON CONFLICT DO NOTHING;

-- ============================================
-- FUNCIÓN DE LIMPIEZA DE TOKENS EXPIRADOS
-- ============================================
CREATE OR REPLACE FUNCTION limpiar_tokens_expirados()
RETURNS void AS $$
BEGIN
    DELETE FROM tokens_revocados 
    WHERE fecha_expiracion < CURRENT_TIMESTAMP - INTERVAL '7 days';
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- TRIGGER PARA ACTUALIZAR FECHA_ACTUALIZACION
-- ============================================
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_actualizacion = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_usuarios_fecha_actualizacion
    BEFORE UPDATE ON usuarios
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion();

-- ============================================
-- VERIFICACIÓN DE INSTALACIÓN
-- ============================================
DO $$
DECLARE
    total_usuarios INTEGER;
    total_roles INTEGER;
    total_permisos INTEGER;
BEGIN
    SELECT COUNT(*) INTO total_usuarios FROM usuarios;
    SELECT COUNT(*) INTO total_roles FROM roles;
    SELECT COUNT(*) INTO total_permisos FROM permisos;
    
    RAISE NOTICE '==============================================';
    RAISE NOTICE 'INSTALACIÓN COMPLETADA';
    RAISE NOTICE '==============================================';
    RAISE NOTICE 'Usuarios creados: %', total_usuarios;
    RAISE NOTICE 'Roles creados: %', total_roles;
    RAISE NOTICE 'Permisos creados: %', total_permisos;
    RAISE NOTICE '==============================================';
    RAISE NOTICE 'Usuario admin: admin / admin123';
    RAISE NOTICE 'Usuario dev: ayuso2104 / dev123';
    RAISE NOTICE '==============================================';
END $$;
