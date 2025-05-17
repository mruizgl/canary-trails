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

-- Tabla intermedia: ruta_municipio
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
('usuario', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'user@email.com', 1,
'token_de_verificacion_user', 1674825600, 'ROLE_ADMIN', 'src/main/resources/uploads/usuario/default.png'),
('admin', '$2a$12$qyXWzEJL0yBYTIeMhvwUEOBGP7MY5yXkiQq6I66KtX3b//i2daYVm', 'admin@email.com', 1,
'token_de_verificacion_admin', 1674825700, 'ROLE_USER', 'src/main/resources/uploads/usuario/default.png');

INSERT INTO `faunas` (`nombre`, `descripcion`, `aprobada`, `usuario_id`) VALUES
('Phoenix Canariensis', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', '1', '1'),
('Ejemplo', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', '0', '1');

INSERT INTO `floras` (`nombre`, `especie`, `tipo_hoja`, `salida_flor`, `caida_flor`, `descripcion`, `aprobada`, `usuario_id`) VALUES
( 'Ejemplo', 'Especie', 'tipo 1', 'salida', 'caida', 'descripcion', '0', '1');

INSERT INTO `rutas` (`nombre`, `dificultad`, `tiempo_duracion`, `distancia_metros`, `desnivel`, `aprobada`, `usuario_id`) VALUES
('Paisaje Lunar', 'Intermedia', '5', '13090', '852.51', '1', '1');

INSERT INTO `comentarios` (`titulo`, `descripcion`, `usuario_id`, `ruta_id`) VALUES
('Esto mola', 'Esto mola mucho', '1', '1'),
('Esto no mola', 'Esto no mola nada', '1', '1');

INSERT INTO `coordenadas` (`latitud`, `longitud`) VALUES
('34.762203', '-35.112988'),
('-27.333332', '50.000000');