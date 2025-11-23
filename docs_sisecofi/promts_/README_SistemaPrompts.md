# 📚 SISTEMA DE PROMPTS SISECOFI - GUÍA DE USO

## 🎯 DESCRIPCIÓN GENERAL

Este sistema de prompts unificado garantiza que todos los asistentes de IA generen reportes JSON consistentes y de alta calidad para el sistema SISECOFI.

---

## 📋 ARCHIVO PRINCIPAL (LEER PRIMERO)

### 📊 **`EspecificacionReporteJSON.txt`**
- **Propósito**: Estándar central OBLIGATORIO para todos los reportes JSON
- **Contenido**: Estructura completa, reglas por campo, ejemplos, checklist
- **Uso**: CONSULTAR SIEMPRE antes de generar cualquier JSON
- **Estado**: Documento principal - siempre actualizado

---

## 🛠️ PROMPTS ESPECIALIZADOS

### 1. **`AnalisisHallazgos.txt`**
- **Propósito**: Análisis completo de hallazgos/errores reportados
- **Características**:
  - ✅ Análisis documental obligatorio (ECU/EIU/CRN)
  - ✅ Referencias exactas con páginas/coordenadas
  - ✅ Flujo estructurado paso a paso
  - ✅ Integración con EspecificacionReporteJSON.txt
- **Usar cuando**: Se reporte un hallazgo para análisis completo

### 2. **`InformeSolucion.txt`**
- **Propósito**: Generación específica de informes de solución
- **Características**:
  - ✅ Proceso paso a paso para soluciones
  - ✅ Reglas detalladas por campo JSON
  - ✅ Ejemplos específicos por tipo de cambio
  - ✅ Checklist de validación extendido
- **Usar cuando**: Se necesite un informe formal de solución

### 3. **`GestionImagenesHallazgos.txt`**
- **Propósito**: Protocolo para manejo de imágenes en hallazgos
- **Características**:
  - ✅ Procesamiento obligatorio de todas las imágenes
  - ✅ Análisis visual estructurado
  - ✅ Integración con reportes JSON
  - ✅ Validaciones de evidencia visual
- **Usar cuando**: El usuario proporcione imágenes como evidencia

### 4. **`ReporteHallazgos.txt`**
- **Propósito**: Reporte general de hallazgos con énfasis en imágenes
- **Características**:
  - ✅ Protocolo de imágenes integrado
  - ✅ Gestión de evidencia visual
  - ✅ Correlación imagen-hallazgo
  - ✅ Validaciones de consistencia
- **Usar cuando**: Se reporten hallazgos con evidencia visual

---

## 🚀 FLUJO DE TRABAJO RECOMENDADO

### **Para cualquier tarea SISECOFI:**

```mermaid
1. LEER → EspecificacionReporteJSON.txt (OBLIGATORIO)
2. IDENTIFICAR → Tipo de tarea (análisis, solución, imágenes)
3. CONSULTAR → Prompt específico correspondiente
4. EJECUTAR → Proceso según prompt especializado
5. VALIDAR → Contra checklist de EspecificacionReporteJSON.txt
6. ENTREGAR → JSON validado y completo
```

---

## ✅ CHECKLIST RÁPIDO ANTES DE USAR

### **Preparación:**
- [ ] He leído `EspecificacionReporteJSON.txt` completamente
- [ ] Identifiqué el tipo de tarea a realizar
- [ ] Seleccioné el prompt específico apropiado
- [ ] Tengo acceso a la documentación SISECOFI

### **Durante el proceso:**
- [ ] Sigo el flujo del prompt seleccionado
- [ ] Aplico las reglas de `EspecificacionReporteJSON.txt`
- [ ] Registro TODOS los archivos analizados
- [ ] Uso coordenadas exactas para documentación

### **Antes de entregar:**
- [ ] Valido contra checklist completo de EspecificacionReporteJSON.txt
- [ ] Verifico consistencia técnica y gramatical
- [ ] Confirmo que todos los campos obligatorios están completos
- [ ] Reviso que las referencias de documentación son exactas

---

## 🎨 REGLAS UNIVERSALES

### **SIEMPRE:**
- ✅ Consultar `EspecificacionReporteJSON.txt` PRIMERO
- ✅ Incluir TODOS los archivos analizados en `modificaciones`
- ✅ Usar números de línea específicos, NO genéricos
- ✅ Aplicar reglas N/A según tipo de cambio
- ✅ Referenciar documentación con páginas exactas

### **NUNCA:**
- ❌ Omitir archivos analizados durante investigación
- ❌ Usar "Línea 1" u otros números genéricos
- ❌ Dejar campos obligatorios vacíos sin justificación
- ❌ Generar JSON sin validar contra la especificación
- ❌ Ignorar imágenes proporcionadas por el usuario

---

## 📞 RESOLUCIÓN DE PROBLEMAS

### **Si el JSON no valida:**
1. Consultar `EspecificacionReporteJSON.txt` nuevamente
2. Revisar checklist completo de validación
3. Verificar aplicación correcta de reglas N/A
4. Confirmar que todos los archivos analizados están registrados

### **Si hay inconsistencias:**
1. Revisar prompt específico usado
2. Validar contra flujo de trabajo recomendado
3. Confirmar que la documentación referenciada es exacta
4. Verificar numeración de líneas contra código real

---

## 🔄 MANTENIMIENTO

### **Actualización de prompts:**
- Todos los cambios se reflejan primero en `EspecificacionReporteJSON.txt`
- Los prompts específicos se actualizan para referenciar la especificación central
- Se mantiene consistencia entre todos los documentos

### **Versionado:**
- `EspecificacionReporteJSON.txt` contiene la versión actual del estándar
- Fecha de última actualización al final de cada documento
- Registro de cambios importantes en la especificación principal

---

**Versión del sistema**: 1.0  
**Fecha**: 12/11/2025  
**Responsable**: Sistema unificado SISECOFI  
**Próxima revisión**: Según necesidades del proyecto