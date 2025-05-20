# Anteproyecto – CanaryTrails

**Autores:** Melissa Ruiz, Pedro Martín Escuela  
**Centro:** I.E.S. Puerto de la Cruz – Telesforo Bravo  
**Módulo:** Proyecto (CFGS DAM)  
**Tutor:** Eleazar Morales Díaz / David Betancor Quijada  

---

## Diagramas
### Diagrama de paquetes 
<img src= "../anteproyecto/diagrama-paquetes/paquete1.png">
<img src= "../anteproyecto/diagrama-paquetes/paquete2.png">


## Introducción

Este documento recoge el planteamiento y diseño inicial del proyecto **CanaryTrails**, una aplicación multiplataforma para la planificación, exploración y disfrute de rutas de senderismo en las Islas Canarias. Incluye funcionalidades relacionadas con fauna y flora, navegación GPS, y opciones offline.

---

## Objetivo general del proyecto

Diseñar y desarrollar una **aplicación móvil** especializada en rutas de senderismo por Canarias, integrando información sobre **flora y fauna autóctona** y fomentando el turismo local mediante funcionalidades interactivas y educativas.

---

## Descripción del proyecto (alcance)

CanaryTrails permitirá:
- Buscar rutas filtradas por dificultad, duración y ubicación.
- Ver mapas, imágenes y especies asociadas a cada ruta.
- Iniciar rutas con GPS y usar la app sin conexión.
- Guardar favoritas, comentar, valorar y proponer contenido.
- Jugar mediante el avistamiento de especies (gamificación educativa).

---

## Roles de usuario

- **Usuario común:** puede usar todas las funciones salvo validación.
- **Administrador:** gestiona rutas, usuarios y contenido propuesto.

---

## Arquitectura y estructura

- **Frontend móvil:** React Native + TypeScript, navegación modular, estilo natural minimalista.
- **Backend:** Java 17 con Spring Boot, REST API segura con JWT, Swagger, testing con JUnit.
- **Base de datos:** MySQL relacional, modelado con claves foráneas, control de integridad.
- **CI/CD:** Azure DevOps o GitHub Actions.

---

## Funcionalidades específicas

- Registro/Login seguro con JWT.
- Exploración avanzada con filtros.
- Guardar rutas favoritas.
- Uso en modo offline.
- Subida de rutas y especies con aprobación administrativa.
- Comentarios y valoraciones.
- Gamificación tipo "avistamiento Pokémon".
- Navegación GPS y multilingüe (ES/EN).

---

## Tecnología principal

- **Frontend:** React Native, React Navigation, AsyncStorage, i18next.
- **Backend:** Java 17, Spring Boot, Spring Security, JWT, Swagger.
- **BBDD:** MySQL.
- **APIs externas:** Mapbox / Google Maps, OpenWeather, geolocalización.

---

## Tareas y planificación

(Ver tabla de tareas detallada en el documento PDF original)

---

## Recursos Humanos y Materiales

- Melissa: Diseño UI/UX, testin, integración, documentación.
- Pedro: Backend, base de datos, seguridad, integración, desarrollo móvil
- Herramientas: Figma, IntelliJ, VS Code, Docker, GitHub, Postman, emuladores.

---

## Seguimiento y control de calidad

- Uso de GitHub Projects + Issues + Milestones.
- Reuniones de seguimiento cada 2-3 días.
- Pruebas unitarias con JUnit y Mockito.
- Validación en dispositivos reales y emuladores.

---

## Wireframes y diseño

Diseño en Figma con enfoque natural, accesible y minimalista.  
Colores base: verde (#57E389), naranja (#F47A3D), gris (#41463D), lila (#9D8DF1), azul (#B8CDF8).

---

## Plan de Marketing

- **Buyer Persona:** Laura, 32 años, senderista en Tenerife.
- **Competencia:** Wikiloc, AllTrails – sin foco local ni educativo.
- **Tendencias:** apps outdoor, turismo sostenible, gamificación.
- **Estrategia:** redes sociales, SEO, colaboraciones locales.
- **Modelo de negocio:** freemium con suscripciones y experiencias premium.

---

## Plan de Sostenibilidad

- **Económica:** bajos costes, hosting eficiente.
- **Social:** accesibilidad, educación ambiental.
- **Ambiental:** app optimizada, sin papel, mensajes eco.

---

**Repositorio GitHub:** [https://github.com/mruizgl/canary-trails](https://github.com/mruizgl/canary-trails)
