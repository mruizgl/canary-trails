import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { authFetch } from "../utils/authFetch";
import { jwtDecode } from "jwt-decode";

const CrearFaunaPage: React.FC = () => {
  const navigate = useNavigate();
  const [nombre, setNombre] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [fotoArchivo, setFotoArchivo] = useState<File | null>(null);
  const [usuarioId, setUsuarioId] = useState<number | null>(null);

  useEffect(() => {
    const fetchUsuario = async () => {
      const token = localStorage.getItem("token");
      if (!token) return;

      const decoded = jwtDecode<{ sub: string }>(token);
      const nombreUsuario = decoded.sub;

      const res = await authFetch("http://localhost:8080/api/v3/usuarios");
      const usuarios = await res.json();
      const usuario = usuarios.find((u: any) => u.nombre === nombreUsuario);
      if (usuario) setUsuarioId(usuario.id);
    };

    fetchUsuario();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!usuarioId) return alert("Usuario no detectado");

    const payload = {
      nombre,
      descripcion,
      aprobada: true,
      usuario: usuarioId,
      rutas: []
    };

    const res = await authFetch("http://localhost:8080/api/v3/faunas/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    const data = await res.json();

    if (fotoArchivo) {
      const formData = new FormData();
      formData.append("id", data.id);
      formData.append("file", fotoArchivo);

      await authFetch(`http://localhost:8080/api/v3/faunas/upload/${data.id}`, {
        method: "POST",
        body: formData
      });
    }

    navigate("/admin/faunas");
  };

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Crear nueva fauna</h1>
      <form onSubmit={handleSubmit} className="form-card">
        <input
          type="text"
          placeholder="Nombre"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          required
        />
        <textarea
          placeholder="Descripción"
          value={descripcion}
          onChange={(e) => setDescripcion(e.target.value)}
          required
        />
        <input
          type="file"
          accept="image/*"
          onChange={(e) => setFotoArchivo(e.target.files?.[0] || null)}
        />
        <div className="form-buttons">
          <button type="submit" className="approve-btn">Guardar</button>
          <button type="button" className="reject-btn" onClick={() => navigate("/admin/faunas")}>Cancelar</button>
        </div>
        <button className="back-btn" onClick={() => navigate("/dashboard")}>← Volver al panel</button>
      </form>
      
    </div>
    
  );
};

export default CrearFaunaPage;
