export interface UsuarioLoginDto {
  nombre: string;
  password: string;
}

export interface Usuario {
  id: number,
  nombre: string
}

export interface tokenPlayload {
  sub: string,
  role: string
}
