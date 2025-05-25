import React, { useState, useContext, ChangeEvent, FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { loginUsuario } from "../api/authApi";
import { AuthContext } from "../context/AuthContext";
import { UsuarioLoginDto } from "../types/UsuarioTypes";
import { jwtDecode } from "jwt-decode";

const LoginForm: React.FC = () => {
  const [form, setForm] = useState<UsuarioLoginDto>({ nombre: "", password: "" });
  const [error, setError] = useState<string>("");
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const token = await loginUsuario(form);
      const decoded = jwtDecode<{ sub: string; role: string }>(token);

      if (!decoded.role?.includes("ADMIN")) {
        setError("Acceso restringido solo a administradores.");
        return;
      }

      const usuario = {
        id: null,              // El JWT no incluye el id
        nombre: decoded.sub    // "Admin" u otro nombre
      };

      login(token, usuario);
      navigate("/dashboard");
    } catch (err) {
      setError("Usuario o contraseña incorrectos.");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        name="nombre"
        placeholder="Nombre"
        onChange={handleChange}
        required
      />
      <input
        name="password"
        type="password"
        placeholder="Contraseña"
        onChange={handleChange}
        required
      />
      <button type="submit">Entrar</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </form>
  );
};

export default LoginForm;
