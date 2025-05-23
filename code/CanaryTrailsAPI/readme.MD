
---

## API REST – Backend (Spring Boot)

**Lenguaje:** Java 17  
**Framework:** Spring Boot  
**Seguridad:** JWT  
**Base de datos:** MySQL  
**ORM:** Spring Data JPA  
**Documentación:** Swagger

### Paquetes principales
- `controller`: Controladores REST.
- `entities`: Entidades JPA del modelo de datos.
- `dto`: Data Transfer Objects para entrada/salida.
- `repository`: Interfaces para acceso a la base de datos.
- `service`: Lógica de negocio (implementa `IServiceGeneric`).
- `mapper`: Conversores entre entidades y DTOs (MapStruct).
- `security`: Configuración de autenticación/autorización con JWT.

### Seguridad
- Autenticación con tokens JWT.
- Control de acceso por rol (`ROLE_ADMIN`, `ROLE_USER`).
- Filtros personalizados (`JwtFilter`, `SecurityConfig`).

### Testing
- JUnit 5 y Mockito para tests de:
  - Repositorios.
  - Servicios.
  - Controladores.
  - Mappers.  

Cuenta con una cobertura de código del 89% generada por JaCoCo  
  
![alt text](../../documentation/designs/cobertura.png)

---

## App Móvil – React Native

**Lenguaje:** JavaScript / TypeScript  
**Framework:** React Native  
**Librerías clave:**
- `react-navigation` → Navegación por pantallas.
- `axios` → Llamadas a la API.
- `react-native-maps` → Visualización de rutas.
- `redux` (opcional) → Gestión de estado.
- `i18n` → Multilenguaje.
- `expo-file-system` y `async-storage` → Modo offline.

### 📲 Pantallas principales
- Registro / Login
- Explorar rutas
- Ficha de ruta
- Mapa GPS en tiempo real
- Subir ruta
- Flora / Fauna asociada
- Perfil

---

## Sistema Administrativo – Dolibarr

**Propósito:** Gestión de la empresa que comercializa la app.  
**Configuraciones básicas:**
- Gestión de clientes y proveedores.
- Productos/servicios (ej. planes premium o merchandising).
- Presupuestos, facturación y pedidos.
- Logotipo y diseño personalizado.

**Ruta recomendada de acceso:**  
`http://localhost/dolibarr/` o `/var/www/html/dolibarr/`

---

## Pruebas y Calidad

- Cobertura de código con JaCoCo.
- Validación de DTOs con `javax.validation`.
- Tests para control de errores (`@ControllerAdvice`).
- CI/CD opcional con GitHub Actions (configurable).

---

## Despliegue 
En el futuro:
- **Backend:** Puede ser desplegado DigitalOcean.
- **App:** Compilar como APK para Android o IPA para iOS.
- **Admin:** Dolibarr puede instalarse en servidor web Apache o Nginx.

---

## Observaciones Técnicas

- Se utiliza una tabla polimórfica `fotos` para manejar imágenes de rutas, fauna y flora.
- Todas las relaciones están correctamente modeladas con claves foráneas y tablas intermedias.
- Controladores REST siguen convención RESTful: `GET`, `POST`, `PUT`, `DELETE`.

---

## Enlaces útiles

- 📘 [Manual de uso](./manual-de-uso.MD)
- 📘 [Manual de usuario](./manual-de-usuario.MD)
- 📘 [Diseño UI/UX](./documentacion-de-diseno.MD)

---
