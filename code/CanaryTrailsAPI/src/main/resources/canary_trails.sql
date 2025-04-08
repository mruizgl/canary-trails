DROP TABlE IF EXISTS usuarios;
DROP TABlE IF EXISTS faunas;
DROP TABLE IF EXISTS floras;
DROP TABlE IF EXISTS fotos_usuarios;
DROP TABlE IF EXISTS fotos_fauna;
DROP TABlE IF EXISTS fotos_flora;
DROP TABlE IF EXISTS fotos_rutas;
DROP TABlE IF EXISTS ruta;
DROP TABlE IF EXISTS rutas_favoritas;
DROP TABLE IF EXISTS municipio;
DROP TABLE IF EXISTS zona;
DROP TABLE IF EXISTS comentarios;


CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(200) NOT NULL,
    verificado TINYINT(1) DEFAULT 0,
);

CREATE TABLE fotos_usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- id foreing key
    url VARCHAR(200) NOT NULL,
);

CREATE TABLE faunas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    aprobado TINYINT(1) DEFAULT 0,
    estado VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL
);

CREATE TABLE fotos_fauna (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- id foreing key
    url VARCHAR(200) NOT NULL,
);

CREATE TABLE floras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    aprobado TINYINT(1) DEFAULT 0,
    estado VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL
);

CREATE TABLE fotos_flora (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- id foreing key
    url VARCHAR(200) NOT NULL,
);

CREATE TABLE zonas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    -- coordenadas
);

CREATE TABLE municipios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    -- coordenadas
);

CREATE TABLE rutas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    aprobado TINYINT(1) DEFAULT 0,
    -- dificultad CHAR(3)/INT NOT NULL,
    correo VARCHAR(200) NOT NULL,
    -- distancia
    -- tiempo
    -- desnivel
    -- coordenada_inicio
    -- coordenada_fin
);

CREATE TABLE fotos_rutas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- id foreing key
    url VARCHAR(200) NOT NULL,
);

CREATE TABLE rutas_favoritas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- id_usuario
    -- id_ruta
);

CREATE TABLE coordenadas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contenido TEXT NOT NULL
);