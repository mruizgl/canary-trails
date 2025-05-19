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
('admin', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'admin@email.com', 1,
'token_de_verificacion_admin', 1674825700, 'ROLE_ADMIN', 'default.png'),
('usuario', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'user@email.com', 1,
'token_de_verificacion_user', 1674825600, 'ROLE_USER', 'default.png'),
('Pedro', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'petermartesc@gmail.com', 1,
'e082424a-e6d9-4f45-b6ee-16273ba6a6e3', 1747635123718, 'ROLE_USER', 'default.png');

INSERT INTO `faunas` (`nombre`, `descripcion`, `aprobada`, `usuario_id`) VALUES
('Phoenix Canariensis', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', '1', '1'),
('Ejemplo', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', '0', '1');

INSERT INTO `floras` (`nombre`, `especie`, `tipo_hoja`, `salida_flor`, `caida_flor`, `descripcion`, `aprobada`, `usuario_id`) VALUES
( 'Ejemplo', 'Especie', 'tipo 1', 'salida', 'caida', 'descripcion', '0', '1');

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

INSERT INTO `coordenadas` (`latitud`, `longitud`) VALUES
-- Sendero de los Sentidos
(28.5563, -16.2634),
(28.5570, -16.2628),
(28.5577, -16.2622),

-- Pico del Teide
(28.2726, -16.6425),
(28.2710, -16.6410),
(28.2695, -16.6395),

-- Barranco del Infierno
(28.1182, -16.7260),
(28.1167, -16.7250),
(28.1150, -16.7240),

-- Paisaje Lunar
(28.1495, -16.6350),
(28.1480, -16.6335),
(28.1465, -16.6320),

-- Rambla de Castro
(28.3889, -16.5730),
(28.3900, -16.5720),
(28.3912, -16.5710),

-- Chinyero Circular
(28.2980, -16.7930),
(28.2965, -16.7920),
(28.2950, -16.7910),

-- Cueva del Viento
(28.3745, -16.7060),
(28.3752, -16.7050),
(28.3759, -16.7040),

-- Arenas Negras
(28.3610, -16.7630),
(28.3595, -16.7615),
(28.3580, -16.7600),

-- Malpaís de Güímar
(28.2931, -16.3830),
(28.2940, -16.3820),
(28.2950, -16.3810),

-- Anaga - Taganana
(28.5620, -16.1900),
(28.5605, -16.1885),
(28.5590, -16.1870);

INSERT INTO coordenada_ruta (ruta_id, coordenada_id) VALUES
-- Sendero de los Sentidos (ruta_id = 1)
(1, 1),
(1, 2),
(1, 3),

-- Pico del Teide (ruta_id = 2)
(2, 4),
(2, 5),
(2, 6),

-- Barranco del Infierno (ruta_id = 3)
(3, 7),
(3, 8),
(3, 9),

-- Paisaje Lunar (ruta_id = 4)
(4, 10),
(4, 11),
(4, 12),

-- Rambla de Castro (ruta_id = 5)
(5, 13),
(5, 14),
(5, 15),

-- Chinyero Circular (ruta_id = 6)
(6, 16),
(6, 17),
(6, 18),

-- Cueva del Viento (ruta_id = 7)
(7, 19),
(7, 20),
(7, 21),

-- Arenas Negras (ruta_id = 8)
(8, 22),
(8, 23),
(8, 24),

-- Malpaís de Güímar (ruta_id = 9)
(9, 25),
(9, 26),
(9, 27),

-- Anaga - Taganana (ruta_id = 10)
(10, 28),
(10, 29),
(10, 30);

-- Comentarios adicionales para rutas existentes
INSERT INTO comentarios (titulo, descripcion, usuario_id, ruta_id) VALUES
('Impresionante paisaje', 'La vista desde la cima es espectacular, lo recomiendo mucho.', 2, 2),
('Perfecta para principiantes', 'Ruta sencilla y bien señalizada, ideal para familias.', 3, 1),
('Tramos difíciles', 'Algunos tramos requieren buen calzado y resistencia.', 1, 4),
('Mucha vegetación', 'Me encantó la flora autóctona que se puede observar.', 2, 5),
('Rápida y entretenida', 'Buena ruta para hacer en una mañana.', 3, 7),
('Un reto para los amantes de la montaña', 'El desnivel es considerable, pero vale la pena.', 2, 10),
('Ambiente tranquilo', 'Perfecto para desconectar y disfrutar de la naturaleza.', 1, 3);

-- Faunas relacionadas con rutas
INSERT INTO faunas (nombre, descripcion, aprobada, usuario_id, foto) VALUES
('Lagarto tizón', 'Reptil endémico que se puede observar en zonas rocosas.', 1, 1, 'lagarto_tizon.jpg'),
('Pájaro carpintero canario', 'Ave típica de la isla con un característico tamborileo.', 1, 2, 'pajaro_carpintero.jpg'),
('Murciélago de herradura', 'Mamífero nocturno que habita en cuevas y bosques.', 1, 3, 'murcielago_herradura.jpg'),
('Gineta canaria', 'Pequeño mamífero carnívoro que habita en los bosques.', 1, 2, 'gineta_canaria.jpg'),
('Cernícalo vulgar', 'Ave rapaz común en zonas abiertas y rocosas.', 1, 1, 'cernicalo_vulgar.jpg'),
('Tortuga mora', 'Reptil que se encuentra en áreas semiáridas de Tenerife.', 1, 3, 'tortuga_mora.jpg');

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
('Pino canario', 'Pinus canariensis', 'Aciculada', 'Abril', 'Septiembre', 'Pino autóctono de Canarias que domina las zonas altas.', 1, 1, 'pino_canario.jpg'),
('Retama del Teide', 'Spartocytisus supranubius', 'Pequeñas hojas', 'Mayo', 'Octubre', 'Arbusto endémico que crece en las cumbres del Teide.', 1, 2, 'retama_teide.jpg'),
('Cardón', 'Euphorbia canariensis', 'Sustentacular', 'Marzo', 'Julio', 'Cactus típico de las zonas secas de Tenerife.', 1, 3, 'cardon.jpg'),
('Bejeque', 'Euphorbia balsamifera', 'Sustentacular', 'Abril', 'Septiembre', 'Planta suculenta típica de zonas costeras y secas.', 1, 1, 'bejeque.jpg'),
('Faya', 'Myrica faya', 'Perennifolia', 'Mayo', 'Noviembre', 'Árbol típico de los bosques húmedos de Canarias.', 1, 2, 'faya.jpg'),
('Malpaís', 'Launaea arborescens', 'Lanceolada', 'Abril', 'Agosto', 'Planta resistente que crece en terrenos volcánicos.', 1, 3, 'malpais.jpg');

-- Asociación de floras con rutas
INSERT INTO ruta_flora (ruta_id, flora_id) VALUES
(2, 1), -- Pino canario en Pico del Teide
(4, 2), -- Retama del Teide en Paisaje Lunar
(9, 3), -- Cardón en Malpaís de Güímar
(5, 4), -- Bejeque en Rambla de Castro
(3, 5), -- Faya en Barranco del Infierno
(8, 6); -- Malpaís en Arenas Negras
