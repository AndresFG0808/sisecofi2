# COMPARACIÓN: Query SIN MAX vs CON MAX

## 🔍 DIFERENCIA PRINCIPAL

### **SIN MAX (con todos los campos en GROUP BY)**
- **Resultado:** 179 filas para el contrato "CS-300-AD-N-P-FC-004/20"
- Genera **MÚLTIPLES FILAS** por contrato porque agrupa por TODOS los campos únicos

### **CON MAX (solo número de contrato en GROUP BY)**  
- **Resultado:** 26 filas en total (1 fila por contrato)
- Genera **UNA SOLA FILA** por contrato consolidando toda la información

---

## 📊 EJEMPLO REAL: Contrato "CS-300-AD-N-P-FC-004/20"

### ❌ SIN MAX - GENERA 179 FILAS DUPLICADAS:

```
"CS-300-AD-N-P-FC-004/20"  "ANTIVIRUS"      NULL  NULL  671.89  27646.00   11520.22  25.34  0  0.00  0.00  11520.22
"CS-300-AD-N-P-FC-004/20"  "Antivirus 2"    NULL  NULL  671.89  27646.00   11520.22  25.34  0  0.00  0.00  11520.22
"CS-300-AD-N-P-FC-004/20"  "APS"            NULL  NULL  671.89  27646.00   11520.22  25.34  0  0.00  0.00  11520.22
"CS-300-AD-N-P-FC-004/20"  "APS - 4"        838...  972... 671.89  221168.00  92161.76  202.72 0  0.00  0.00  92161.76
"CS-300-AD-N-P-FC-004/20"  "APS 2"          NULL  NULL  671.89  27646.00   11520.22  25.34  0  0.00  0.00  11520.22
...
(continúa hasta 179 filas con el mismo contrato pero diferente "nombre_corto_contrato")
```

**Problema:** El mismo contrato aparece 179 veces porque cada `nombre_corto_contrato` diferente crea una fila nueva.

---

### ✅ CON MAX - GENERA 1 SOLA FILA:

```
"CS-300-AD-N-P-FC-004/20"  "VUCEM 3"  999899145.11  1159883008.00  671.89  ...  (datos agregados)
```

**Solución:** MAX() selecciona UN SOLO valor de cada columna descriptiva y agrupa todos los montos.

---

## 🎯 ¿POR QUÉ PASA ESTO?

### **SIN MAX:**
Cuando haces `GROUP BY` con múltiples columnas, PostgreSQL crea **una fila por cada combinación única**:

```sql
GROUP BY ncm."NÚMERO DE CONTRATO", 
         arg."Nombre corto del contrato",  ← Crea filas diferentes
         afn."monto_anded",
         arg."máximo resultante s/ impuestos MXN",
         arg."mínimo contratdo s/ impuestos MXN"
```

Si un contrato tiene 179 nombres diferentes de proyectos relacionados (por los JOINs), obtienes **179 filas**.

---

### **CON MAX:**
Cuando haces `GROUP BY` solo por contrato y usas `MAX()`:

```sql
GROUP BY ncm."NÚMERO DE CONTRATO"  ← Solo agrupa por contrato
SELECT MAX(arg."Nombre corto del contrato")  ← Selecciona UN nombre
```

PostgreSQL:
1. Agrupa todas las filas del mismo contrato
2. Para campos de texto: usa `MAX()` para seleccionar UNO (generalmente el último alfabéticamente)
3. Para campos numéricos: suma con `SUM()` todos los valores

**Resultado:** 1 fila por contrato con todos los datos consolidados.

---

## 📈 IMPACTO EN LOS DATOS NUMÉRICOS

### Sin WHERE (removiendo filtro `afn."monto_anded" = 671.89`):

#### **CON MAX (correcto):**
```
"CS-300-AD-N-P-FC-004/20"  "STLD 4"  33425.56  38773.00  2221.00
    monto_dev_antes_ded:    8,432,030.00
    monto_pagado_total:     3,513,667.10
```

#### **SIN MAX (multiplicado incorrectamente):**
```
"CS-300-AD-N-P-FP-010/25"  "MICROSOFT 7"  ...  671.89
    monto_dev_antes_ded:    1,003,197,062.64  ← INFLADO por duplicados
```

Los montos SIN MAX están **MULTIPLICADOS** porque:
- Si un contrato tiene 10 nombres diferentes
- Los consumos mensuales se suman 10 veces
- **Los totales quedan 10x más grandes de lo real**

---

## 🔑 RESUMEN DE DIFERENCIAS

| Aspecto | SIN MAX (múltiples GROUP BY) | CON MAX (solo contrato) |
|---------|------------------------------|-------------------------|
| **Filas por contrato** | 179 filas | 1 fila |
| **Datos descriptivos** | Repetidos con valores diferentes | MAX selecciona uno |
| **Datos numéricos (SUM)** | **Inflados/duplicados** | ✅ Correctos |
| **Total de filas** | Cientos o miles | 26 contratos |
| **Uso típico** | Ver detalle por subcategoría | **Consolidar por contrato** |

---

## ✅ RECOMENDACIÓN

**USA CON MAX** cuando necesitas:
- Un resumen consolidado por contrato
- Totales correctos sin duplicación
- Una fila por entidad principal (contrato)

**USA SIN MAX** cuando necesitas:
- Ver el detalle de cada combinación específica
- Analizar diferencias entre subcategorías
- Pero ten cuidado: los totales pueden estar inflados por el producto cartesiano de los JOINs

---

## 🎓 CONCLUSIÓN

La diferencia es **producto cartesiano**:
- **SIN MAX:** Los JOINs crean múltiples filas → GROUP BY las mantiene separadas → Sumas duplicadas
- **CON MAX:** GROUP BY por contrato consolida todo → MAX selecciona un valor → Sumas correctas

**En tu caso de uso (reportes por contrato), la versión CON MAX es la correcta.**
