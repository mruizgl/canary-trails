import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
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
  const [detalleId, setDetalleId] = useState<number | null>(null);
  const [paginaActual, setPaginaActual] = useState(1);
  const navigate = useNavigate();
  const faunasPorPagina = 5;

  useEffect(() => {
    fetchFaunas();
  }, []);

  const fetchFaunas = async () => {
    const res = await authFetch("http://localhost:8080/api/v3/faunas");
    const data = await res.json();
    setFaunas(data);
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm("¿Eliminar definitivamente esta fauna?")) return;
    await authFetch(`http://localhost:8080/api/v3/faunas/delete/${id}`, { method: "DELETE" });
    fetchFaunas();
  };

  const handleEdit = (faunaId: number) => {
    navigate(`/admin/faunas/editar/${faunaId}`);
  };

  const handleCrear = () => {
    navigate("/admin/faunas/crear");
  };

  const totalPaginas = Math.ceil(faunas.length / faunasPorPagina);
  const faunasPaginadas = faunas.slice(
    (paginaActual - 1) * faunasPorPagina,
    paginaActual * faunasPorPagina
  );

  const cambiarPagina = (nueva: number) => {
    if (nueva >= 1 && nueva <= totalPaginas) {
      setPaginaActual(nueva);
    }
  };

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Gestión de Fauna</h1>

      <ul className="dashboard-list">
        {faunasPaginadas.map((f) => (
          <li key={f.id} className="dashboard-item" onClick={() => setDetalleId(detalleId === f.id ? null : f.id)}>
            <div className="dashboard-row">
              <div className="dashboard-content-left">
                <span>{f.nombre}{f.descripcion ? ` - ${f.descripcion}` : ""} ({f.aprobada ? "Aprobada" : "No aprobada"})</span>
              </div>
              <div className="dashboard-buttons" onClick={(e) => e.stopPropagation()}>
                <button className="approve-btn" onClick={() => handleEdit(f.id)}>Editar</button>
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
                      <img src={`http://localhost:8080/api/v1/imagenes/fauna/${f.foto}`} alt={`Foto de ${f.nombre}`} />
                    </div>
                  )}
                </div>
              </div>
            )}
          </li>
        ))}
      </ul>

      {totalPaginas > 1 && (
        <div className="pagination">
          <button onClick={() => cambiarPagina(paginaActual - 1)} disabled={paginaActual === 1}>Anterior</button>
          <span>Página {paginaActual} de {totalPaginas}</span>
          <button onClick={() => cambiarPagina(paginaActual + 1)} disabled={paginaActual === totalPaginas}>Siguiente</button>
        </div>
      )}

      <div className="dashboard-actions" style={{ display: "flex", justifyContent: "space-between", marginTop: "2rem" }}>
        <button className="approve-btn" onClick={handleCrear}>+ Nueva Fauna</button>
        <button className="back-btn" onClick={() => navigate("/dashboard")}>← Volver al panel</button>
      </div>
    </div>
  );
};

export default FaunaAdminPage;
