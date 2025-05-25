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

2. Crea la base de datos en MySQL a partir del [canary_trais.sql](../code/CanaryTrailsAPI/src/main/resources/canary_trails.sql) de la API

3. Configura `application.properties` con tus datos de la Base de Datos:
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/nombre_bbdd
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    ```

4. Ejecuta la API: 
    ```
    mvn clean spring-boot:run
    ```

5. Accede a la API:
    * Swagger UI: `http://localhost:8080/swagger-ui/index.html`

    `V1` Es la parte __publica__ para autenticación  
    `V2` Securizado para los endpoints de __usuario registrados__
    `V3` Los endpoints securizados específicamente para __usuarios registrados__ con __rol de administrador__


## 3. App Móvil – React Native

1. Ve al directorio del cliente:
   ```bash
   cd canary-trails/code/AppCanaryTrails
    ```

2. Instala dependencias:

    ```
    npm install
    ```
3. Abre el emulador de android (por ejemplo uno de __Android Studio__)

4. Ejecuta el proyecto mediante Metro:

    ```
    npm run start
    ```

    Y elige lanzarlo en android (elegir la `a`):

    ```bash

                  Welcome to Metro v0.81.0
                Fast - Scalable - Integrated


    info Dev server ready

    i - run on iOS
    a - run on Android
    r - reload app
    d - open Dev Menu
    j - open DevTools
    ```

## 4. Testing

- Ejecuta desde raiz de la __API__:
    ```
    mvn clean install
    ````