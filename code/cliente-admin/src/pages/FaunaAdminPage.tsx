import React, { useEffect, useState } from "react";
import { authFetch } from "../utils/authFetch";
import "../styles/DashboardPage.css";

interface Usuario { id: number; nombre: string }
interface RutaSimple { id: number; nombre: string }
interface Fauna {
  id: number;
  nombre: string;
  descripcion: string;
  aprobada: boolean;
  usuario: Usuario;
  rutas: RutaSimple[];
  foto?: string;
}

const FaunaAdminPage: React.FC = () => {
  const [faunas, setFaunas] = useState<Fauna[]>([]);
  const [form, setForm] = useState<Fauna | null>(null);
  const [fotoArchivo, setFotoArchivo] = useState<File | null>(null);
  const [detalleId, setDetalleId] = useState<number | null>(null);

  useEffect(() => {
    fetchFaunas();
  }, []);

  const fetchFaunas = async () => {
    const res = await authFetch("http://localhost:8080/api/v3/faunas");
    const data = await res.json();
    setFaunas(data);
  };

  const handleEdit = (fauna: Fauna) => {
    setForm(fauna);
    setFotoArchivo(null);
  };

  const handleNew = () => {
    setForm({
      id: 0,
      nombre: "",
      descripcion: "",
      aprobada: true,
      usuario: { id: 1, nombre: "Admin" },
      rutas: []
    });
    setFotoArchivo(null);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    if (!form) return;
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async () => {
    if (!form) return;

    const isEdit = form.id !== 0;
    const method = isEdit ? "PUT" : "POST";
    const url = isEdit
      ? "http://localhost:8080/api/v3/faunas/update"
      : "http://localhost:8080/api/v3/faunas/add";

    const payload = {
      ...form,
      usuario: form.usuario.id,
      rutas: form.rutas.map(r => r.id),
    };

    const res = await authFetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    const faunaId = isEdit ? form.id : (await res.json()).id;

    if (fotoArchivo && faunaId) {
      const formData = new FormData();
      formData.append("id", faunaId.toString());
      formData.append("file", fotoArchivo);

      await authFetch(`http://localhost:8080/api/v3/faunas/upload/${faunaId}`, {
        method: "POST",
        body: formData,
      });
    }

    setForm(null);
    setFotoArchivo(null);
    fetchFaunas();
  };

  const handleDelete = async (id: number) => {
    if (!id || !window.confirm("¿Eliminar definitivamente esta fauna?")) return;
    await authFetch(`http://localhost:8080/api/v3/faunas/delete/${id}`, { method: "DELETE" });
    fetchFaunas();
  };

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Gestión de Fauna</h1>
      <button className="view-all-btn" onClick={handleNew}>+ Nueva Fauna</button>
      <ul className="dashboard-list">
        {faunas.map((f) => (
          <li key={f.id} className="dashboard-item" onClick={() => setDetalleId(detalleId === f.id ? null : f.id)}>
            <div className="dashboard-row">
              <div className="dashboard-content-left">
                <span>{f.nombre} - {f.descripcion} ({f.aprobada ? "Aprobada" : "No aprobada"})</span>
              </div>
              <div className="dashboard-buttons" onClick={(e) => e.stopPropagation()}>
                <button className="approve-btn" onClick={() => handleEdit(f)}>Editar</button>
                <button className="reject-btn" onClick={() => handleDelete(f.id)}>Eliminar</button>
              </div>
            </div>

            {detalleId === f.id && (
              <div className="dashboard-detail">
                <div className="dashboard-detail-columns">
                  <div className="dashboard-detail-text">
                    <p><strong>Usuario:</strong> {f.usuario?.nombre}</p>
                    <p><strong>Rutas vinculadas:</strong> {f.rutas.map(r => r.nombre).join(", ")}</p>
                  </div>
                  {f.foto && (
                    <div className="dashboard-detail-image">
                      <img src={`http://localhost:8080/api/v3/fotos/fauna/${f.foto}`} alt={`Foto de ${f.nombre}`} />
                    </div>
                  )}
                </div>
              </div>
            )}
          </li>
        ))}
      </ul>

      {form && (
        <div className="dashboard-form">
          <h3>{form.id && form.id !== 0 ? "Editar Fauna" : "Nueva Fauna"}</h3>

          <input name="nombre" placeholder="Nombre" value={form.nombre} onChange={handleChange} />
          <textarea name="descripcion" placeholder="Descripción" value={form.descripcion} onChange={handleChange} />

          {form.foto && (
            <div className="mb-2">
              <img
                src={`http://localhost:8080/api/v3/fotos/fauna/${form.foto}`}
                alt="Foto actual"
                style={{ maxWidth: "150px", borderRadius: "4px" }}
              />
            </div>
          )}

          <input
            type="file"
            accept="image/*"
            onChange={(e) => setFotoArchivo(e.target.files?.[0] || null)}
            className="block mb-2"
          />

          <div className="form-buttons">
            <button className="approve-btn" onClick={handleSubmit}>Guardar</button>
            <button className="reject-btn" onClick={() => { setForm(null); setFotoArchivo(null); }}>Cancelar</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default FaunaAdminPage;
