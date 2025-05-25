# Documentación de Diseño – CanaryTrails

Este documento recoge el proceso de diseño, decisiones visuales y principios de usabilidad aplicados en la creación de la aplicación *CanaryTrails*, tanto para la app móvil como para el panel web administrativo.

---

## 1. Enfoque de Diseño

La estética y experiencia de usuario de CanaryTrails se ha inspirado en tres pilares clave:

- Naturaleza: colores y formas que evocan el entorno canario.
- Funcionalidad: navegación simple y clara para usuarios de todas las edades.
- Multiplataforma: diseño adaptable para dispositivos móviles y tabletas.

---

## 2. Paleta de Colores

![alt text](./assets/paleta-2.png)

| Color     | Uso sugerido                        | Hex      |
|-----------|-------------------------------------|----------|
| Ecru White | Colores de resalte o tarjetas | `#F3F5E8` |
| Lunar Green | Color del tab-nab | `#41463D` |
| Apache | Color de los input | `#D9BF68` |
| Spanish Green | Fondos suaves | `#889584` |
| Mountain Meadow | Color de resalte y botones | `#00A676` |

Esta combinación mantiene un equilibrio entre lo natural y lo moderno, alineándose con el espíritu de exploración y la identidad visual de la app.

---

## 3. Tipografía

- **Fuente principal**: `Poppins` (Google Fonts)

- **Pesos utilizados**:
  - `Regular` para textos.
  - `Bold` para encabezados y secciones destacadas.

---

## 4. Iconografía

Se utiliza una iconografía limpia basada en vectores ([Ionicicons](https://ionic.io/ionicons)), manteniendo coherencia en estilo y grosor. Los íconos ayudan a facilitar la navegación en:

- Menús.
- Favoritos.
- Ubicación GPS.
- Añadir rutas o especies.

---

## 5. Componentes de Interfaz

| Componente      | Descripción |
|------------------|-------------|
| **Navbar inferior** | Acceso rápido a rutas, favoritos, explorar y perfil. |
| **Mapa interactivo** | Visualiza el trazado de la ruta y tu posición GPS. |
| **Tarjetas de ruta** | Vista previa de rutas, con datos clave y botón de favorito. |
| **Ficha detallada** | Información completa sobre cada ruta: dificultad, duración, flora, fauna, mapa, comentarios. |
| **Vista del perfil** | Muestra la información de usuario, las rutas favoritas, las creaciones no aprobadas, etc. |

---

## 6. Experiencia de Usuario (UX)

- Interacción __intuitiva__ desde la primera pantalla.
- Colores e iconos __suaves__ y __agradables__ a la vista
- Creación de elementos __sencilla__ y __asequible__.
- Sistema de creación por __verificación__ (el usuario crea, el administrador desde el panel verifica y sube fotos)
- Como está __administrado__ por los __usuarios con permisos__, es una __app segura__ y no se permite la creación de elementos NSFW ni de cualquier indole ilegal
---

## 7. Accesibilidad

- Contraste alto entre texto y fondo.
- Tamaño de fuente legible en todos los dispositivos.
- Uso de etiquetas y roles para lectores de pantalla.
- Interfaz optimizada para navegación táctil.

---

## 8. Wireframes y Prototipos

Protipo de Figma
- Login y register en Figma

![Login y Register Figma](<./assets/figma.png>)

Wireframes

- Home   

![Home Wireframe](<./assets/wireframe/Home.png>)  

- Perfil 

![Perfil Wireframe](<./assets/wireframe/Profile.jpeg>)

- Rutas y Juegos

![Rutas y Juegos Wireframe](<./assets/wireframe/Routes&Games.jpeg>)


Los wireframes contemplan:

- Pantalla de inicio / login.
- Explorador de rutas.
- Mapa en tiempo real.
- Pantalla de minijuegos (idea no implementada).
- Perfil de usuario.

---

## 9. Adaptación a Marca

El diseño visual busca reforzar la identidad de marca:
- Nombre: *CanaryTrails*.
- Inspirado en paisajes volcánicos, verdes y rutas reales de las islas.
- Diseño visual coherente con turismo ecológico y exploración responsable.

---

## 10. Recomendaciones futuras

- Agregar modo oscuro.
- Personalización del perfil (foto, descripción).
- Sistema de traking en tiempo real (ubicacion)
- Sistema de logros o medallas por avistamientos o rutas completadas (minijuegis).

---
