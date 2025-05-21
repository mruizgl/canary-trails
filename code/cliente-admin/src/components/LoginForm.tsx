import React, { useState, useContext, ChangeEvent, FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { loginUsuario } from "../api/authApi";
import { AuthContext } from "../context/AuthContext";
import { UsuarioLoginDto } from "../types/UsuarioTypes";

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
      login(token);
      navigate("/dashboard");
    } catch (err) {
      setError("Usuario o contraseña incorrectos.");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Iniciar sesión</h2>
      <input name="nombre" placeholder="Nombre" onChange={handleChange} required />
      <input name="password" type="password" placeholder="Contraseña" onChange={handleChange} required />
      <button type="submit">Entrar</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </form>
  );
};

export default LoginForm;
