import React, { useContext, useEffect, useState } from "react";
import { authFetch } from "../utils/authFetch";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import "../styles/DashboardPage.css";

interface Flora { id: number; nombre: string; descripcion: string; aprobada: boolean; }
interface Fauna { id: number; nombre: string; descripcion: string; aprobada: boolean; }
interface Ruta { id: number; nombre: string; descripcion?: string; aprobada: boolean; }

const DashboardPage: React.FC = () => {
  const [floras, setFloras] = useState<Flora[]>([]);
  const [faunas, setFaunas] = useState<Fauna[]>([]);
  const [rutas, setRutas] = useState<Ruta[]>([]);
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => { fetchAll(); }, []);

  const fetchAll = async () => {
    const floraRes = await authFetch("http://localhost:8080/api/v3/floras");
    const faunaRes = await authFetch("http://localhost:8080/api/v3/faunas");
    const rutaRes = await authFetch("http://localhost:8080/api/v3/rutas");
    const floraData = await floraRes.json();
    const faunaData = await faunaRes.json();
    const rutaData = await rutaRes.json();
    setFloras(floraData.filter((f: Flora) => !f.aprobada));
    setFaunas(faunaData.filter((f: Fauna) => !f.aprobada));
    setRutas(rutaData.filter((r: Ruta) => !r.aprobada));
  };

  const aprobarFlora = async (flora: any) => {
    const payload = {
      id: flora.id, nombre: flora.nombre, descripcion: flora.descripcion,
      especie: flora.especie, tipoHoja: flora.tipoHoja, salidaFlor: flora.salidaFlor,
      caidaFlor: flora.caidaFlor, aprobada: true,
      usuario: flora.usuario?.id || flora.usuario,
      rutas: flora.rutas?.map((r: any) => r.id) || [],
    };
    await authFetch("http://localhost:8080/api/v3/floras/update", {
      method: "PUT", headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    fetchAll();
  };

  const aprobarFauna = async (fauna: any) => {
    const payload = {
      id: fauna.id, nombre: fauna.nombre, descripcion: fauna.descripcion,
      aprobada: true, usuario: fauna.usuario?.id || fauna.usuario,
      rutas: fauna.rutas?.map((r: any) => r.id) || [],
    };
    await authFetch("http://localhost:8080/api/v3/faunas/update", {
      method: "PUT", headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    fetchAll();
  };

  const aprobarRuta = async (ruta: any) => {
    const payload = {
      id: ruta.id, nombre: ruta.nombre, dificultad: ruta.dificultad,
      tiempoDuracion: ruta.tiempoDuracion, distanciaMetros: ruta.distanciaMetros,
      desnivel: ruta.desnivel, aprobada: true, usuario: ruta.usuario?.id || ruta.usuario,
      faunas: ruta.faunas?.map((f: any) => f.id) || [],
      floras: ruta.floras?.map((f: any) => f.id) || [],
      coordenadas: ruta.coordenadas?.map((c: any) => c.id) || [],
      municipios: ruta.municipios?.map((m: any) => m.id) || [],
    };
    await authFetch("http://localhost:8080/api/v3/rutas/update", {
      method: "PUT", headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    fetchAll();
  };

  const rechazar = async (tipo: "flora" | "fauna" | "ruta", id: number) => {
    const confirmar = window.confirm("¿Estás seguro de que deseas rechazar esta propuesta?");
    if (!confirmar) return;
    try {
      const res = await authFetch(`http://localhost:8080/api/v3/${tipo}s/delete/${id}`, { method: "DELETE" });
      res.ok ? alert("Eliminado correctamente.") : alert("Error al eliminar.");
      fetchAll();
    } catch (error) {
      alert("Ocurrió un error al rechazar.");
      console.error(error);
    }
  };

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Panel de Administración</h1>

      <section className="dashboard-section">
        <h2>Flora pendiente</h2>
        <ul className="dashboard-list">
          {floras.map((f) => (
            <li className="dashboard-item" key={f.id}>
              <span>{f.nombre} - {f.descripcion}</span>
              <div>
                <button className="approve-btn" onClick={() => aprobarFlora(f)}>Aprobar</button>
                <button className="reject-btn" onClick={() => rechazar("flora", f.id)}>Rechazar</button>
              </div>
            </li>
          ))}
        </ul>
      </section>

      <section className="dashboard-section">
        <h2>Fauna pendiente</h2>
        <ul className="dashboard-list">
          {faunas.map((f) => (
            <li className="dashboard-item" key={f.id}>
              <span>{f.nombre} - {f.descripcion}</span>
              <div>
                <button className="approve-btn" onClick={() => aprobarFauna(f)}>Aprobar</button>
                <button className="reject-btn" onClick={() => rechazar("fauna", f.id)}>Rechazar</button>
              </div>
            </li>
          ))}
        </ul>
      </section>

      <section className="dashboard-section">
        <h2>Rutas pendientes</h2>
        <ul className="dashboard-list">
          {rutas.map((r) => (
            <li className="dashboard-item" key={r.id}>
              <span>{r.nombre} - {r.descripcion || "sin descripción"}</span>
              <div>
                <button className="approve-btn" onClick={() => aprobarRuta(r)}>Aprobar</button>
                <button className="reject-btn" onClick={() => rechazar("ruta", r.id)}>Rechazar</button>
              </div>
            </li>
          ))}
        </ul>
      </section>

      <button className="logout-btn" onClick={handleLogout}>Cerrar sesión</button>
    </div>
  );
};

export default DashboardPage;
