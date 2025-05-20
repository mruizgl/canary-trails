## App Móvil – React Native

**Lenguaje:** JavaScript / TypeScript  
**Framework:** React Native  
**Gestor de paquetes:** npm  
**Ecosistema:** Android

---

### Estructura de Carpetas Principal  
```
AppCanaryTrails/
│
├── src/
│ ├── api/ → Configuración de Axios y peticiones a la API
│ ├── components/ → Componentes reutilizables (cards, botones, etc.)
│ ├── constants/ → Estilos globales, colores, iconos, traducciones
│ ├── navigation/ → Stack, tab y drawer navigators
│ ├── screens/ → Pantallas principales (Inicio, Ruta, Perfil, etc.)
│ ├── store/ → Gestión de estado (Redux, Context API u otra)
│ └── utils/ → Funciones auxiliares, validaciones, helpers
│
├── assets/ → Imágenes, iconos, fuentes
├── App.js → Punto de entrada de la app
├── .env → Variables de entorno
└── package.json
```


---

### Comunicación con el Backend

- Se usa **Axios** como cliente HTTP (`/src/api/api.js`).
- Las rutas de la API están centralizadas en un archivo para facilitar su gestión y multientorno.
- La variable `API_BASE_URL` se carga desde `.env`.

```js
import axios from "axios";
const API = axios.create({
  baseURL: process.env.API_BASE_URL || "http://localhost:8080/api/",
});
