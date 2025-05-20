# Manual de Uso y Configuración – CanaryTrails

Este documento detalla los pasos necesarios para instalar, configurar y ejecutar el proyecto CanaryTrails en entorno local. El proyecto está compuesto por tres partes principales:

- Backend (Java Spring Boot)
- Aplicación móvil (React Native)
- Sistema de gestión empresarial (Dolibarr)

---

## 1. Requisitos Previos

### Software necesario
- Java 17+
- Node.js 18+
- npm o yarn
- MySQL 8+
- Android Studio o Xcode (para correr la app en simuladores)
- Git

---

## 2. Backend – API REST (Spring Boot)

### Configuración local

1. Clona el repositorio:
   ```bash
   git clone https://github.com/mruizgl/canary-trails.git
   cd canary-trails/code/CanaryTrailsAPI
    ```

2. Crea una base de datos en MySQL:

    ```
    CREATE DATABASE canarytrails CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```

3. Configura application.properties:
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/canarytrails
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña

    spring.jpa.hibernate.ddl-auto=update
    jwt.secret=clave-secreta
    ```

4. Ejecuta la API: 
    ```
    mvn spring-boot:run
    ```

5. Accede a la API:
    * Swagger UI: `http://localhost:8080/swagger-ui.html`

    `V1` Es la parte común de autenticación  
    `V2` Es para los endpoints de usuario  
    `V3` Los endpoints de administrador


## 3. App Móvil – React Native

1. Ve al directorio del cliente:
   ```bash
   cd canary-trails/code/AppCanaryTrails
    ```

2. Instala dependencias:

    ```
    npm install
    ```

3. Ejecuta en android (emulador o dispositivo):
     ```
    npm run start
    npx react-native run-android
    ```

## 4. Base de Datos

El proyecto incluye script de creación completo en `/documentation/sql/canary_trails.sql`

## 5. Testing
- Ejecuta desde el Backend:
    ```
    mvn clean install
    ````