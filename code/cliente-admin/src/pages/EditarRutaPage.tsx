import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { authFetch } from "../utils/authFetch";

const EditarRutaPage: React.FC = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [ruta, setRuta] = useState<any>(null);
  const [fotoArchivo, setFotoArchivo] = useState<File | null>(null);
  const [todasFloras, setTodasFloras] = useState<any[]>([]);
  const [todasFaunas, setTodasFaunas] = useState<any[]>([]);

  useEffect(() => {
    const fetchRuta = async () => {
      const res = await authFetch(`http://localhost:8080/api/v3/rutas/${id}`);
      const data = await res.json();
      setRuta(data);
    };

    const fetchExtras = async () => {
      const florasRes = await authFetch("http://localhost:8080/api/v3/floras");
      const faunasRes = await authFetch("http://localhost:8080/api/v3/faunas");
      setTodasFloras(await florasRes.json());
      setTodasFaunas(await faunasRes.json());
    };

    fetchRuta();
    fetchExtras();
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!ruta) return;

    const payload = {
      id: ruta.id,
      nombre: ruta.nombre,
      dificultad: ruta.dificultad,
      tiempoDuracion: ruta.tiempoDuracion,
      distanciaMetros: ruta.distanciaMetros,
      desnivel: ruta.desnivel,
      aprobada: true,
      usuario: ruta.usuario.id,
      faunas: ruta.faunas.map((f: any) => f.id),
      floras: ruta.floras.map((f: any) => f.id),
      coordenadas: ruta.coordenadas.map((c: any) => c.id),
      municipios: ruta.municipios.map((m: any) => m.id)
    };

    const res = await authFetch("http://localhost:8080/api/v3/rutas/update", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (res.ok) {
      if (fotoArchivo) {
        const formData = new FormData();
        formData.append("id", ruta.id.toString());
        formData.append("file", fotoArchivo);
        await authFetch(`http://localhost:8080/api/v3/rutas/upload/${ruta.id}`, {
          method: "POST",
          body: formData
        });
      }

      navigate("/admin/rutas");
    } else {
      alert("Error al actualizar la ruta");
    }
  };

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Editar ruta</h1>
      {ruta && (
        <form onSubmit={handleSubmit} className="form-card">
          <input
            type="text"
            value={ruta.nombre}
            onChange={e => setRuta({ ...ruta, nombre: e.target.value })}
            placeholder="Nombre"
            required
          />
          <input
            type="text"
            value={ruta.dificultad}
            onChange={e => setRuta({ ...ruta, dificultad: e.target.value })}
            placeholder="Dificultad"
            required
          />
          <input
            type="number"
            value={ruta.tiempoDuracion}
            onChange={e => setRuta({ ...ruta, tiempoDuracion: Number(e.target.value) })}
            placeholder="Tiempo (min)"
          />
          <input
            type="number"
            value={ruta.distanciaMetros}
            onChange={e => setRuta({ ...ruta, distanciaMetros: parseFloat(e.target.value) })}
            placeholder="Distancia (m)"
          />
          <input
            type="number"
            value={ruta.desnivel}
            onChange={e => setRuta({ ...ruta, desnivel: parseFloat(e.target.value) })}
            placeholder="Desnivel (m)"
          />

          <div className="form-group">
            <label>Flora asociada</label>
            <select
              multiple
              value={ruta.floras.map((f: any) => f.id)}
              onChange={e => {
                const seleccionadas = Array.from(e.target.selectedOptions, o => Number(o.value));
                const nuevas = todasFloras.filter(f => seleccionadas.includes(f.id));
                setRuta({ ...ruta, floras: nuevas });
              }}
            >
              {todasFloras.map(f => (
                <option key={f.id} value={f.id}>{f.nombre}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Fauna asociada</label>
            <select
              multiple
              value={ruta.faunas.map((f: any) => f.id)}
              onChange={e => {
                const seleccionadas = Array.from(e.target.selectedOptions, o => Number(o.value));
                const nuevas = todasFaunas.filter(f => seleccionadas.includes(f.id));
                setRuta({ ...ruta, faunas: nuevas });
              }}
            >
              {todasFaunas.map(f => (
                <option key={f.id} value={f.id}>{f.nombre}</option>
              ))}
            </select>
          </div>

          <input
            type="file"
            accept="image/*"
            onChange={(e) => setFotoArchivo(e.target.files?.[0] || null)}
          />
          <div className="form-buttons">
            <button type="submit" className="approve-btn">Guardar</button>
            <button type="button" className="reject-btn" onClick={() => navigate("/admin/rutas")}>Cancelar</button>
          </div>
        </form>
      )}
    </div>
  );
};

export default EditarRutaPage;
