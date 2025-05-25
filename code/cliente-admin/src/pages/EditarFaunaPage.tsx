import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { authFetch } from "../utils/authFetch";

interface Fauna {
  id: number;
  nombre: string;
  descripcion: string;
  aprobada: boolean;
  usuario: { id: number };
  rutas: number[];
  foto?: string;
}

const EditarFaunaPage: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [fauna, setFauna] = useState<Fauna | null>(null);
  const [fotoArchivo, setFotoArchivo] = useState<File | null>(null);

  useEffect(() => {
    const fetchFauna = async () => {
      const res = await authFetch(`http://localhost:8080/api/v3/faunas/${id}`);
      const data = await res.json();
      setFauna({
        id: data.id,
        nombre: data.nombre,
        descripcion: data.descripcion,
        aprobada: data.aprobada,
        usuario: data.usuario,
        rutas: data.rutas.map((r: any) => r.id),
        foto: data.foto
      });
    };
    fetchFauna();
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!fauna) return;

    const payload = {
      id: fauna.id,
      nombre: fauna.nombre,
      descripcion: fauna.descripcion,
      aprobada: true,
      usuario: fauna.usuario.id,
      rutas: fauna.rutas
    };

    await authFetch("http://localhost:8080/api/v3/faunas/update", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (fotoArchivo) {
      const formData = new FormData();
      formData.append("id", fauna.id.toString());
      formData.append("file", fotoArchivo);

      await authFetch(`http://localhost:8080/api/v3/faunas/upload/${fauna.id}`, {
        method: "POST",
        body: formData
      });
    }

    navigate("/admin/faunas");
  };

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Editar fauna</h1>
      {fauna && (
        <form onSubmit={handleSubmit} className="form-card">
          <input
            type="text"
            value={fauna.nombre}
            onChange={(e) => setFauna({ ...fauna, nombre: e.target.value })}
            placeholder="Nombre"
            required
          />
          <textarea
            value={fauna.descripcion}
            onChange={(e) => setFauna({ ...fauna, descripcion: e.target.value })}
            placeholder="Descripción"
            required
          />
          {fauna.foto && (
            <div className="mb-2">
              <img
                src={`http://localhost:8080/api/v1/imagenes/fauna/${fauna.foto}`}
                alt="Foto actual"
                style={{ maxWidth: "150px", borderRadius: "4px" }}
              />
            </div>
          )}
          <input
            type="file"
            accept="image/*"
            onChange={(e) => setFotoArchivo(e.target.files?.[0] || null)}
          />
          <div className="form-buttons">
            <button type="submit" className="approve-btn">Guardar</button>
            <button type="button" className="reject-btn" onClick={() => navigate("/admin/faunas")}>Cancelar</button>
          </div>
        </form>
      )}
    </div>
  );
};

export default EditarFaunaPage;
