import { UsuarioLoginDto } from "../types/UsuarioTypes";

const API_URL = "http://localhost:8080/api/v1";

export const loginUsuario = async (credenciales: UsuarioLoginDto): Promise<string> => {
  
  const response = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credenciales),
  });

  if (!response.ok) {
    throw new Error("Credenciales inválidas");
  }

  return await response.text();
};
