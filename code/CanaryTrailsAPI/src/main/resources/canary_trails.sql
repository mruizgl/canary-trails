-- Elimina las tablas intermedias primero para evitar errores de FK
DROP TABLE IF EXISTS ruta_fauna;
DROP TABLE IF EXISTS ruta_flora;
DROP TABLE IF EXISTS usuario_ruta_favorita;
DROP TABLE IF EXISTS comentarios;
DROP TABLE IF EXISTS coordenada_ruta;
DROP TABLE IF EXISTS ruta_municipio;
DROP TABLE IF EXISTS ruta_foto;

-- Elimina las tablas principales dependientes
DROP TABLE IF EXISTS rutas;
DROP TABLE IF EXISTS coordenadas;
DROP TABLE IF EXISTS floras;
DROP TABLE IF EXISTS faunas;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS municipios;
DROP TABLE IF EXISTS zonas;


-- Tabla: zonas
CREATE TABLE zonas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla: municipios
CREATE TABLE municipios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL UNIQUE,
    altitud_media INT NOT NULL,
    latitud_geografica DECIMAL(10, 6) NOT NULL, -- es decimal?
    longitud_geografica DECIMAL(10, 6) NOT NULL,
    zona_id INT NOT NULL,
    FOREIGN KEY (zona_id) REFERENCES zonas(id)
);

-- Tabla: usuario
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    correo VARCHAR(320) NOT NULL UNIQUE, -- tiene como máximo 320 caracteres
    token_verificacion VARCHAR(255),
    fecha_creacion BIGINT NOT NULL,
    verificado TINYINT(1) DEFAULT 0,
    rol VARCHAR(20),
    foto TEXT NOT NULL
);

-- Tabla: ruta
CREATE TABLE rutas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL UNIQUE,
    dificultad VARCHAR(20) NOT NULL,
    tiempo_duracion BIGINT NOT NULL, -- se guarda en tiempo...?
    distancia_metros FLOAT NOT NULL,
    desnivel FLOAT NOT NULL,
    aprobada TINYINT(1) DEFAULT 0,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE ruta_foto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ruta_id INT NOT NULL,
    nombre_foto TEXT NOT NULL,
    FOREIGN KEY (ruta_id) REFERENCES rutas(id)
);

-- Tabla intermedia: ruta_municipio
CREATE TABLE ruta_municipio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ruta_id INT NOT NULL,
    municipio_id INT NOT NULL,
    FOREIGN KEY (ruta_id) REFERENCES rutas(id),
    FOREIGN KEY (municipio_id) REFERENCES municipios(id)
);

-- Tabla: coordenadas
CREATE TABLE coordenadas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    latitud DECIMAL(10, 6) NOT NULL,
    longitud DECIMAL(10, 6) NOT NULL,
    UNIQUE KEY longitud_latitud (latitud, longitud)
);

-- Tabla intermedia: coordenada_ruta
CREATE TABLE coordenada_ruta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ruta_id INT NOT NULL,
    coordenada_id INT NOT NULL,
    FOREIGN KEY (ruta_id) REFERENCES rutas(id),
    FOREIGN KEY (coordenada_id) REFERENCES coordenadas(id)
);

-- Tabla: faunas
CREATE TABLE faunas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT NOT NULL,
    aprobada TINYINT(1) DEFAULT 0,
    usuario_id INTEGER NOT NULL,
    foto TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabla: floras
CREATE TABLE floras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    especie VARCHAR(100) NOT NULL,
    tipo_hoja VARCHAR(50) NOT NULL,
    salida_flor VARCHAR(20) NOT NULL,
    caida_flor VARCHAR(20) NOT NULL,
    descripcion TEXT NOT NULL,
    aprobada TINYINT(1) DEFAULT 0,
    usuario_id INTEGER NOT NULL,
    foto TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabla: comentario
CREATE TABLE comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    usuario_id INT NOT NULL,
    ruta_id INT NOT NULL,
    FOREIGN KEY (ruta_id) REFERENCES rutas(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabla: rutas_favoritas
CREATE TABLE usuario_ruta_favorita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    ruta_id INT NOT NULL,
    UNIQUE (usuario_id, ruta_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (ruta_id) REFERENCES rutas(id)
);

-- Tabla intermedia: ruta_flora
CREATE TABLE ruta_flora (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ruta_id INT NOT NULL,
    flora_id INT NOT NULL,
    UNIQUE (ruta_id, flora_id),
    FOREIGN KEY (ruta_id) REFERENCES rutas(id),
    FOREIGN KEY (flora_id) REFERENCES floras(id)
);

-- Tabla intermedia: ruta_fauna
CREATE TABLE ruta_fauna (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ruta_id INT NOT NULL,
    fauna_id INT NOT NULL,
    UNIQUE (ruta_id, fauna_id),
    FOREIGN KEY (ruta_id) REFERENCES rutas(id),
    FOREIGN KEY (fauna_id) REFERENCES faunas(id)
);

INSERT INTO `usuarios` (`nombre`, `password`,  `correo`, `verificado`, `token_verificacion`, `fecha_creacion`, `rol`, `foto`) VALUES
('Admin', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'admin@email.com', 1,
'token_de_verificacion_admin', 1674825700, 'ROLE_ADMIN', 'default.png'),
('Usuario', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'user@email.com', 1,
'token_de_verificacion_user', 1674825600, 'ROLE_USER', 'default.png'),
('Pedro', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'pedro@email.com', 1,
'e082424a-e6d9-4f45-b6ee-16273ba6a6e3', 1747635123718, 'ROLE_USER', 'default.png'),
('Maria', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'maria@email.com', 1, 'token_maria', 1674825800, 'ROLE_USER', 'default.png'),
('Juan', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'juan@email.com', 1, 'token_juan', 1674825900, 'ROLE_USER', 'default.png'),
('Laura', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'laura@email.com', 1, 'token_laura', 1674826000, 'ROLE_USER', 'default.png'),
('Carlos', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'carlos@email.com', 1, 'token_carlos', 1674826100, 'ROLE_USER', 'default.png'),
('Ana', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'ana@email.com', 1, 'token_ana', 1674826200, 'ROLE_USER', 'default.png'),
('David', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'david@email.com', 1, 'token_david', 1674826300, 'ROLE_USER', 'default.png'),
('Sofia', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'sofia@email.com', 1, 'token_sofia', 1674826400, 'ROLE_USER', 'default.png'),
('Pablo', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'pablo@email.com', 1, 'token_pablo', 1674826500, 'ROLE_USER', 'default.png'),
('Elena', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'elena@email.com', 1, 'token_elena', 1674826600, 'ROLE_USER', 'default.png'),
('Javier', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'javier@email.com', 1, 'token_javier', 1674826700, 'ROLE_USER', 'default.png');

INSERT INTO `rutas` (`nombre`, `dificultad`, `tiempo_duracion`, `distancia_metros`, `desnivel`, `aprobada`, `usuario_id`) VALUES
('Sendero de los Sentidos', 'Fácil', '45', 1300, 80, true, 1), -- La Laguna
('Pico del Teide', 'Difícil', '360', 7600, 1400, true, 2),       -- La Orotava
('Barranco del Infierno', 'Media', '180', 6200, 350, true, 1),  -- Adeje
('Paisaje Lunar', 'Media', '270', 11600, 600, true, 2),         -- Vilaflor
('Rambla de Castro', 'Fácil', '90', 4300, 150, true, 1),       -- Los Realejos
('Chinyero Circular', 'Media', '165', 6400, 320, false, 1),     -- Santiago del Teide (podrías asignarlo a Icod o El Tanque si no tienes Santiago)
('Cueva del Viento', 'Fácil', '60', 1800, 70, true, 1),        -- Icod de los Vinos
('Arenas Negras', 'Media', '210', 8900, 480, false, 1),         -- Garachico (si no lo tienes, puedes moverlo a El Tanque o Icod)
('Malpaís de Güímar', 'Fácil', '80', 3500, 90, true, 1),       -- Candelaria (o Fasnia si no tienes Güímar)
('Anaga - Taganana', 'Difícil', '300', 12400, 750, false, 2);   -- Santa Cruz de Tenerife

INSERT INTO `ruta_foto` (`ruta_id`, `nombre_foto`) VALUES
(1, 'ruta_default.jpg'),
(2, 'ruta_default_1.jpg'),
(3, 'ruta_default_2.jpg'),
(4, 'ruta_default_3.jpg'),
(5, 'ruta_default_4.jpg'),
(6, 'ruta_default_5.jpg'),
(7, 'ruta_default_6.jpg'),
(8, 'ruta_default_7.jpg'),
(9, 'ruta_default_8.jpg'),
(10, 'ruta_default_9.jpg');

INSERT INTO `zonas` (`nombre`) VALUES
('Zona Norte'),
('Zona Metropolitana'),
('Zona Sur');

INSERT INTO `municipios` (`nombre`, `altitud_media`, `latitud_geografica`, `longitud_geografica`, `zona_id`) VALUES
('Santa Cruz de Tenerife', 4, 28.4636, -16.2518, 2),
('San Cristóbal de La Laguna', 546, 28.4874, -16.3159, 2),
('La Orotava', 390, 28.3906, -16.5234, 1),
('Puerto de la Cruz', 15, 28.4144, -16.5442, 1),
('Los Realejos', 420, 28.3723, -16.5823, 1),
('Candelaria', 20, 28.3535, -16.3723, 3),
('Arona', 610, 28.0997, -16.6825, 3),
('Adeje', 280, 28.1225, -16.7277, 3),
('Granadilla de Abona', 650, 28.1188, -16.5779, 3),
('Guía de Isora', 600, 28.2111, -16.7792, 3),
('Icod de los Vinos', 235, 28.3678, -16.7070, 1),
('Tacoronte', 510, 28.5027, -16.4153, 1),
('El Sauzal', 420, 28.4778, -16.4302, 1),
('La Victoria de Acentejo', 400, 28.4292, -16.4466, 1),
('La Matanza de Acentejo', 430, 28.4362, -16.4545, 1),
('El Rosario', 550, 28.4313, -16.3792, 2),
('Tegueste', 390, 28.5201, -16.3339, 2),
('Fasnia', 420, 28.2507, -16.4312, 3),
('Vilaflor', 1400, 28.1558, -16.6353, 3),
('Buenavista del Norte', 100, 28.3744, -16.8531, 1),
('Santiago del Teide', 930, 28.2933, -16.8081, 3),
('Güímar', 289, 28.3169, -16.4144, 3),
('Garachico', 30, 28.3739, -16.7663, 1);

INSERT INTO `ruta_municipio` (`ruta_id`, `municipio_id`) VALUES
(1, 2),
(2, 3),
(3, 7),
(4, 19),
(5, 5),
(6, 21),
(7, 11),
(8, 23),
(9, 22),
(10, 1);


INSERT INTO `comentarios` (`titulo`, `descripcion`, `usuario_id`, `ruta_id`) VALUES
('Esto mola', 'Esto mola mucho', '1', '1'),
('Esto no mola', 'Esto no mola nada', '1', '1');

-- Insertamos nuevas coordenadas más detalladas para cada ruta
INSERT INTO coordenadas (latitud, longitud) VALUES
-- Sendero de los Sentidos (Ruta 1) - 5 puntos (inicio, 3 giros, fin)
(28.5563, -16.2634), -- Inicio
(28.5568, -16.2630), -- Giro suave derecha
(28.5572, -16.2625), -- Giro pronunciado izquierda
(28.5575, -16.2620), -- Giro derecha hacia final
(28.5577, -16.2618), -- Fin

-- Pico del Teide (Ruta 2) - 7 puntos (ruta más larga con más giros)
(28.2726, -16.6425), -- Inicio
(28.2720, -16.6420), -- Primer giro izquierda
(28.2715, -16.6415), -- Continuación
(28.2710, -16.6410), -- Giro derecha
(28.2705, -16.6405), -- Zigzag
(28.2700, -16.6400), -- Último giro
(28.2695, -16.6395), -- Fin

-- Barranco del Infierno (Ruta 3) - 6 puntos
(28.1182, -16.7260), -- Inicio
(28.1178, -16.7255), -- Primer giro
(28.1173, -16.7252), -- Descenso pronunciado
(28.1167, -16.7250), -- Giro en U
(28.1160, -16.7245), -- Último tramo
(28.1155, -16.7240), -- Fin

-- Paisaje Lunar (Ruta 4) - 8 puntos (ruta con muchos cambios)
(28.1495, -16.6350), -- Inicio
(28.1492, -16.6348), -- Primer giro
(28.1488, -16.6345), -- Cambio dirección
(28.1485, -16.6342), -- Giro izquierda
(28.1482, -16.6338), -- Curva derecha
(28.1478, -16.6335), -- Zona rocosa
(28.1472, -16.6330), -- Último tramo
(28.1465, -16.6320), -- Fin

-- Rambla de Castro (Ruta 5) - 5 puntos
(28.3889, -16.5730), -- Inicio
(28.3892, -16.5725), -- Giro junto al mar
(28.3895, -16.5720), -- Curva izquierda
(28.3905, -16.5715), -- Subida
(28.3912, -16.5710), -- Fin

-- Chinyero Circular (Ruta 6) - 6 puntos (ruta circular)
(28.2980, -16.7930), -- Inicio
(28.2975, -16.7928), -- Primer giro
(28.2970, -16.7925), -- Curva derecha
(28.2965, -16.7922), -- Punto más alto
(28.2960, -16.7918), -- Regreso
(28.2955, -16.7915), -- Fin (mismo que inicio)

-- Cueva del Viento (Ruta 7) - 4 puntos (ruta corta)
(28.3745, -16.7060), -- Inicio
(28.3748, -16.7055), -- Giro entrada cueva
(28.3752, -16.7050), -- Dentro cueva
(28.3755, -16.7045), -- Fin

-- Arenas Negras (Ruta 8) - 7 puntos
(28.3610, -16.7630), -- Inicio
(28.3605, -16.7625), -- Primer giro
(28.3600, -16.7620), -- Zona de lava
(28.3595, -16.7618), -- Giro pronunciado
(28.3590, -16.7615), -- Subida
(28.3585, -16.7610), -- Último tramo
(28.3580, -16.7605), -- Fin

-- Malpaís de Güímar (Ruta 9) - 5 puntos
(28.2931, -16.3830), -- Inicio
(28.2935, -16.3825), -- Primer giro
(28.2940, -16.3822), -- Zona de malpaís
(28.2945, -16.3818), -- Curva final
(28.2950, -16.3815), -- Fin

-- Anaga - Taganana (Ruta 10) - 9 puntos (ruta más larga)
(28.5620, -16.1900), -- Inicio
(28.5615, -16.1895), -- Primer giro
(28.5610, -16.1890), -- Bajada pronunciada
(28.5605, -16.1888), -- Curva cerrada
(28.5600, -16.1885), -- Zona boscosa
(28.5595, -16.1882), -- Última subida
(28.5592, -16.1878), -- Mirador
(28.5590, -16.1875), -- Bajada final
(28.5588, -16.1870); -- Fin

-- Asociamos las nuevas coordenadas a las rutas
INSERT INTO coordenada_ruta (ruta_id, coordenada_id) VALUES
-- Sendero de los Sentidos (5 puntos)
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),

-- Pico del Teide (7 puntos)
(2, 6), (2, 7), (2, 8), (2, 9), (2, 10), (2, 11), (2, 12),

-- Barranco del Infierno (6 puntos)
(3, 13), (3, 14), (3, 15), (3, 16), (3, 17), (3, 18),

-- Paisaje Lunar (8 puntos)
(4, 19), (4, 20), (4, 21), (4, 22), (4, 23), (4, 24), (4, 25), (4, 26),

-- Rambla de Castro (5 puntos)
(5, 27), (5, 28), (5, 29), (5, 30), (5, 31),

-- Chinyero Circular (6 puntos)
(6, 32), (6, 33), (6, 34), (6, 35), (6, 36), (6, 37),

-- Cueva del Viento (4 puntos)
(7, 38), (7, 39), (7, 40), (7, 41),

-- Arenas Negras (7 puntos)
(8, 42), (8, 43), (8, 44), (8, 45), (8, 46), (8, 47), (8, 48),

-- Malpaís de Güímar (5 puntos)
(9, 49), (9, 50), (9, 51), (9, 52), (9, 53),

-- Anaga - Taganana (9 puntos)
(10, 54), (10, 55), (10, 56), (10, 57), (10, 58), (10, 59), (10, 60), (10, 61), (10, 62);

-- Comentarios adicionales para rutas existentes
INSERT INTO comentarios (titulo, descripcion, usuario_id, ruta_id) VALUES
-- Sendero de los Sentidos (Ruta 1)
('Experiencia sensorial', 'Los paneles explicativos hacen que aprecies cada detalle del bosque.', 1, 1),
('Ideal con niños', 'Mis hijos disfrutaron mucho con los juegos didácticos del recorrido.', 3, 1),
('Bien mantenido', 'El camino está impecable y muy bien cuidado todo el año.', 2, 1),

-- Pico del Teide (Ruta 2)
('Vistas increíbles', 'Ver el amanecer desde la cumbre es una experiencia única.', 3, 2),
('Preparación necesaria', 'Lleva ropa de abrigo aunque haga calor abajo, en la cima hace mucho frío.', 1, 2),
('Recompensa merecida', 'El esfuerzo de la subida vale totalmente por las panorámicas.', 2, 2),

-- Barranco del Infierno (Ruta 3)
('Cascada espectacular', 'El salto de agua al final del barranco es impresionante en invierno.', 2, 3),
('Reserva con antelación', 'Recuerden que necesitan permiso y hay cupos limitados diarios.', 1, 3),
('Zapatos antideslizantes', 'Algunas piedras pueden estar resbaladizas, mejor calzado adecuado.', 3, 3),

-- Paisaje Lunar (Ruta 4)
('Parece otro planeta', 'Las formaciones geológicas son únicas en la isla.', 3, 4),
('Mejor al atardecer', 'Los colores de las rocas con la luz del sol bajo son mágicos.', 2, 4),
('Lleva agua suficiente', 'No hay fuentes en todo el recorrido y hace mucho calor.', 1, 4),

-- Rambla de Castro (Ruta 5)
('Paseo costero precioso', 'Las vistas a los acantilados y al mar son impresionantes.', 1, 5),
('Fácil acceso', 'Muy bien comunicada con aparcamiento cerca del inicio.', 3, 5),
('Gofio millo recomendado', 'No dejen de probar el gofio en el puesto al final del camino.', 2, 5),

-- Chinyero Circular (Ruta 6)
('Naturaleza volcánica', 'Se aprecia muy bien el contraste entre lava y pinar.', 2, 6),
('Sombra escasa', 'Llevar protección solar ya que hay pocos árboles.', 1, 6),
('Perfecta para running', 'El terreno es ideal para corredores de trail.', 3, 6),

-- Cueva del Viento (Ruta 7)
('Guía imprescindible', 'La explicación del guía hace que la visita cobre vida.', 3, 7),
('No para claustrofóbicos', 'Algunos pasos son estrechos, mejor informarse antes.', 2, 7),
('Frío en el interior', 'Llevar alguna chaqueta aunque fuera haga calor.', 1, 7),

-- Arenas Negras (Ruta 8)
('Túnel de lava impresionante', 'La sección del tubo volcánico es lo más destacado.', 1, 8),
('Mejor con guía local', 'Aprendimos mucho sobre la erupción del Chinyero.', 3, 8),
('Cuidado al bajar', 'Algunas pendientes de ceniza son traicioneras.', 2, 8),

-- Malpaís de Güímar (Ruta 9)
('Paisaje lunar costero', 'El contraste entre el malpaís y el mar es espectacular.', 2, 9),
('Caminos bien marcados', 'Aunque parece un laberinto de lava, está bien señalizado.', 1, 9),
('Zona de nidificación', 'Vimos varias aves protegidas anidando en los riscos.', 3, 9),

-- Anaga - Taganana (Ruta 10)
('Bosque encantado', 'La laurisilva parece sacada de un cuento de hadas.', 3, 10),
('Niebla frecuente', 'Es común que se nuble, llevar ropa impermeable.', 2, 10),
('Vistas a Taganana', 'El mirador sobre el pueblo es de postal.', 1, 10),
('Ruta exigente', 'Los desniveles son constantes, buena forma física requerida.', 2, 10),
('Tesoro escondido', 'Una de las rutas más auténticas y menos masificadas.', 3, 10);

-- Faunas relacionadas con rutas
INSERT INTO faunas (nombre, descripcion, aprobada, usuario_id, foto) VALUES
('Lagarto tizón', 'Reptil endémico que se puede observar en zonas rocosas.', 1, 1, 'fauna_default.jpg'),
('Pájaro carpintero canario', 'Ave típica de la isla con un característico tamborileo.', 1, 2, 'fauna_default.jpg'),
('Murciélago de herradura', 'Mamífero nocturno que habita en cuevas y bosques.', 1, 3, 'fauna_default.jpg'),
('Gineta canaria', 'Pequeño mamífero carnívoro que habita en los bosques.', 1, 2, 'fauna_default.jpg'),
('Cernícalo vulgar', 'Ave rapaz común en zonas abiertas y rocosas.', 1, 1, 'fauna_default.jpg'),
('Tortuga mora', 'Reptil que se encuentra en áreas semiáridas de Tenerife.', 1, 3, 'fauna_default.jpg');

-- Asociación de faunas con rutas
INSERT INTO ruta_fauna (ruta_id, fauna_id) VALUES
(1, 1), -- Lagarto tizón en Sendero de los Sentidos
(2, 2), -- Pájaro carpintero en Pico del Teide
(7, 3), -- Murciélago de herradura en Cueva del Viento
(4, 4), -- Gineta canaria en Paisaje Lunar
(5, 5), -- Cernícalo vulgar en Rambla de Castro
(9, 6); -- Tortuga mora en Malpaís de Güímar

-- Floras relacionadas con rutas
INSERT INTO floras (nombre, especie, tipo_hoja, salida_flor, caida_flor, descripcion, aprobada, usuario_id, foto) VALUES
('Pino canario', 'Pinus canariensis', 'Aciculada', 'Abril', 'Septiembre', 'Pino autóctono de Canarias que domina las zonas altas.', 1, 1, 'flora_default.jpg'),
('Retama del Teide', 'Spartocytisus supranubius', 'Pequeñas hojas', 'Mayo', 'Octubre', 'Arbusto endémico que crece en las cumbres del Teide.', 1, 2, 'flora_default.jpg'),
('Cardón', 'Euphorbia canariensis', 'Sustentacular', 'Marzo', 'Julio', 'Cactus típico de las zonas secas de Tenerife.', 1, 3, 'flora_default.jpg'),
('Bejeque', 'Euphorbia balsamifera', 'Sustentacular', 'Abril', 'Septiembre', 'Planta suculenta típica de zonas costeras y secas.', 1, 1, 'flora_default.jpg'),
('Faya', 'Myrica faya', 'Perennifolia', 'Mayo', 'Noviembre', 'Árbol típico de los bosques húmedos de Canarias.', 1, 2, 'flora_default.jpg'),
('Malpaís', 'Launaea arborescens', 'Lanceolada', 'Abril', 'Agosto', 'Planta resistente que crece en terrenos volcánicos.', 1, 3, 'flora_default.jpg');

-- Asociación de floras con rutas
INSERT INTO ruta_flora (ruta_id, flora_id) VALUES
(2, 1), -- Pino canario en Pico del Teide
(4, 2), -- Retama del Teide en Paisaje Lunar
(9, 3), -- Cardón en Malpaís de Güímar
(5, 4), -- Bejeque en Rambla de Castro
(3, 5), -- Faya en Barranco del Infierno
(8, 6); -- Malpaís en Arenas Negras

-- Hacer que la ruta 2 (Pico del Teide) tenga 12 favoritos
INSERT INTO usuario_ruta_favorita (usuario_id, ruta_id) VALUES
(1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (6, 2), (7, 2), (8, 2), (9, 2), (10, 2), (11, 2), (12, 2),
-- Hacer que la ruta 1 (Sendero de los Sentidos) tenga 11 favoritos
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1), (9, 1), (10, 1), (11, 1),
(1, 3), (2, 3), (3, 3), (4, 3), (5, 3),  -- 5 para ruta 3
(1, 4), (2, 4), (3, 4),                   -- 3 para ruta 4
(1, 5), (2, 5), (3, 5), (4, 5), (5, 5), (6, 5), -- 6 para ruta 5
(1, 6), (2, 6),                            -- 2 para ruta 6
(1, 7), (2, 7), (3, 7), (4, 7),           -- 4 para ruta 7
(1, 8), (2, 8), (3, 8), (4, 8), (5, 8), (6, 8), (7, 8), -- 7 para ruta 8
(1, 9), (2, 9), (3, 9), (4, 9),           -- 4 para ruta 9
(1, 10), (2, 10), (3, 10), (4, 10), (5, 10), (6, 10); -- 6 para ruta 10
