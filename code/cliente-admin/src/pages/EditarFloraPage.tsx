import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { authFetch } from "../utils/authFetch";

interface Usuario { id: number; nombre: string }
interface RutaSimple { id: number; nombre: string }
interface Flora {
  id: number;
  nombre: string;
  especie: string;
  tipoHoja: string;
  salidaFlor: string;
  caidaFlor: string;
  descripcion: string;
  aprobada: boolean;
  usuario: Usuario;
  rutas: RutaSimple[];
  foto?: string;
}

const EditarFloraPage: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [flora, setFlora] = useState<Flora | null>(null);
  const [fotoArchivo, setFotoArchivo] = useState<File | null>(null);

  useEffect(() => {
    const fetchFlora = async () => {
      const res = await authFetch(`http://localhost:8080/api/v3/floras/${id}`);
      const data = await res.json();
      setFlora(data);
    };
    fetchFlora();
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!flora) return;

    const payload = {
      id: flora.id,
      nombre: flora.nombre,
      especie: flora.especie,
      tipoHoja: flora.tipoHoja,
      salidaFlor: flora.salidaFlor,
      caidaFlor: flora.caidaFlor,
      descripcion: flora.descripcion,
      aprobada: true,
      usuario: flora.usuario.id,
      rutas: flora.rutas.map(r => r.id)
    };

    const res = await authFetch("http://localhost:8080/api/v3/floras/update", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (res.ok && fotoArchivo) {
      const formData = new FormData();
      formData.append("id", flora.id.toString());
      formData.append("file", fotoArchivo);

      await authFetch(`http://localhost:8080/api/v3/floras/upload/${flora.id}`, {
        method: "POST",
        body: formData
      });
    }

    navigate("/admin/floras");
  };

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Editar flora</h1>
      {flora && (
        <form onSubmit={handleSubmit} className="form-card">
          <input
            type="text"
            value={flora.nombre}
            onChange={e => setFlora({ ...flora, nombre: e.target.value })}
            placeholder="Nombre"
            required
          />
          <input
            type="text"
            value={flora.especie}
            onChange={e => setFlora({ ...flora, especie: e.target.value })}
            placeholder="Especie"
            required
          />
          <input
            type="text"
            value={flora.tipoHoja}
            onChange={e => setFlora({ ...flora, tipoHoja: e.target.value })}
            placeholder="Tipo de hoja"
          />
          <input
            type="text"
            value={flora.salidaFlor}
            onChange={e => setFlora({ ...flora, salidaFlor: e.target.value })}
            placeholder="Salida flor"
          />
          <input
            type="text"
            value={flora.caidaFlor}
            onChange={e => setFlora({ ...flora, caidaFlor: e.target.value })}
            placeholder="Caída flor"
          />
          <textarea
            value={flora.descripcion}
            onChange={e => setFlora({ ...flora, descripcion: e.target.value })}
            placeholder="Descripción"
          />

          {flora.foto && (
            <div className="mb-2">
              <img
                src={`http://localhost:8080/api/v1/imagenes/flora/${flora.foto}`}
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
            <button type="button" className="reject-btn" onClick={() => navigate("/admin/floras")}>Cancelar</button>
          </div>
        </form>
      )}
    </div>
  );
};

export default EditarFloraPage;
