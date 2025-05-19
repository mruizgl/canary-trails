
export type tokenPlayload ={
  sub: string;
  rol: string;
}


export type Usuario = {
  id: number,
  nombre: string,
  correo: string,
  password: string,
  foto: string
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

}

export type Flora ={

}

export type Coordenada ={

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