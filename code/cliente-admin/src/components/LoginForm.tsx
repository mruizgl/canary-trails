import React, { useState, ChangeEvent, FormEvent, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { loginUsuario } from "../api/authApi";
import { UsuarioLoginDto } from "../types/UsuarioTypes";
import { jwtDecode } from "jwt-decode";
import { useAppContext } from "../context/AuthContext";


const LoginForm: React.FC = () => {
  const [form, setForm] = useState<UsuarioLoginDto>({ nombre: "", password: "" });
  const [error, setError] = useState<string>("");
  const { token, login } = useAppContext();
  const navigate = useNavigate();

  useEffect(() => {
    if(token !== ""){
      
    }
  }, [])
  

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {

    e.preventDefault();

    if(token){
      navigate("/dashboard");
    }

    try {
      const token = await loginUsuario(form);

      const decoded = jwtDecode<{ role: string }>(token);

      console.log(decoded.role);
      console.log("Token:", token);

      if (!decoded.role?.includes("ADMIN")) {
        setError("Acceso restringido solo a administradores.");
        return;
      }

      login(token);
      navigate("/dashboard");

      } catch (err) {
        setError("Usuario o contraseña incorrectos.");
    }
  };


  return (
    <form onSubmit={handleSubmit}>
     
      <input name="nombre" placeholder="Nombre" onChange={handleChange} required />
      <input name="password" type="password" placeholder="Contraseña" onChange={handleChange} required />
      <button type="submit">Entrar</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </form>
  );
};

export default LoginForm;
