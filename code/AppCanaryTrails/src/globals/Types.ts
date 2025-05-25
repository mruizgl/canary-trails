
export type tokenPlayload ={
  sub: string;
  rol: string;
}


export type Usuario = {
  id: number,
  nombre: string,
  correo: string,
  password: string,
  rol: string,
  fechaCreacion: Date,
  foto: string,
  faunas: Array<Fauna>,
  floras: Array<Flora>,
  rutas: Array<RutaType>,
  comentarios: Array<Comentario>,
  rutasFavoritas: Array<RutaType>
}

export type Municipio ={
  id: number,
  nombre: string,
  altitudMedia: number,
  latitud: number,
  longitud: number,
}

export type Comentario ={
  id: number,
  titulo: string,
  descripcion: string,
  usuario: Usuario,
}

export type Fauna ={
  id: number,
  nombre: string,
  descripcion: string,
  foto: string
}

export type FaunaDetailed ={
  id: number,
  nombre: string,
  descripcion: string,
  aprobada: boolean,
  foto: string
}

export type FaunaCreate ={
  nombre: string,
  descripcion: string,
  aprobada: boolean,
  usuario: number
}

export type Flora ={
  id: number,
  nombre: string,
  descripcion: string,
  foto: string
}

export type FloraDetailed ={
  id: number,
  nombre: string,
  descripcion: string,
  aprobada: boolean,
  foto: string
}

export type FloraCreate ={
  nombre: string,
  especie: string,
  tipoHoja: string,
  salidaFlor: string,
  caidaFlor: string,
  descripcion: string,
  aprobada: boolean,
  usuario: number
}

export type Coordenada ={
  id: number,
  latitud: number,
  longitud: number,
}

export type CoordenadaMaps ={
  latitude: number,
  longitude: number,
}

export type RutaType = {
  id: number,
  nombre: string,
  dificultad: string,
  tiempoDuracion: number,
  distanciaMetros: number,
  desnivel: number,
  aprobada: boolean,
  usuario: Usuario,     //Usuario sin informacion adicional
  comentarios: Array<Comentario>,   //Comentario con la información del usuario que lo hizo
  faunas: Array<Fauna>,
  floras: Array<Flora>,
  coordenadas: Array<Coordenada>,
  municipios: Array<Municipio>,  //Municipio sin información adicional
  fotos: Array<string>
}

export type RutaCreateType = {
  nombre: string,
  dificultad: string,
  tiempoDuracion: number,
  distanciaMetros: number,
  desnivel: number,
  aprobada: boolean,
  usuario: number,     //Usuario sin informacion adicional
  faunas: Array<number>,
  floras: Array<number>,
  coordenadas: Array<CoordenadaCreateRuta>,
  municipios: Array<number>
}

export type CoordenadaCreateRuta ={
  latitud: number,
  longitud: number
}