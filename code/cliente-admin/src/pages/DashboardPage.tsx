import React, { useContext, useEffect, useState } from "react";
import { authFetch } from "../utils/authFetch";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";


interface Flora { id: number; nombre: string; descripcion: string; aprobada: boolean; }
interface Fauna { id: number; nombre: string; descripcion: string; aprobada: boolean; }
interface Ruta { id: number; nombre: string; descripcion?: string; aprobada: boolean; }

const DashboardPage: React.FC = () => {
  const [floras, setFloras] = useState<Flora[]>([]);
  const [faunas, setFaunas] = useState<Fauna[]>([]);
  const [rutas, setRutas] = useState<Ruta[]>([]);

  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    fetchAll();
  }, []);

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

  const aprobar = async (tipo: "flora" | "fauna" | "ruta", item: any) => {
    const endpoint = `http://localhost:8080/api/v3/${tipo}s/update`;
    await authFetch(endpoint, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ ...item, aprobada: true }),
    });
    fetchAll();
  };

  const rechazar = async (tipo: "flora" | "fauna" | "ruta", id: number) => {
    await authFetch(`http://localhost:8080/api/v3/${tipo}s/delete/${id}`, {
      method: "DELETE",
    });
    fetchAll();
  };

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Panel de Administración</h1>

      <section>
        <h2>Flora pendiente</h2>
        <ul>
          {floras.map((f) => (
            <li key={f.id}>
              {f.nombre} - {f.descripcion}
              <button onClick={() => aprobar("flora", f)}>Aprobar</button>
              <button onClick={() => rechazar("flora", f.id)}>Rechazar</button>
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h2>Fauna pendiente</h2>
        <ul>
          {faunas.map((f) => (
            <li key={f.id}>
              {f.nombre} - {f.descripcion}
              <button onClick={() => aprobar("fauna", f)}>Aprobar</button>
              <button onClick={() => rechazar("fauna", f.id)}>Rechazar</button>
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h2>Rutas pendientes</h2>
        <ul>
          {rutas.map((r) => (
            <li key={r.id}>
              {r.nombre} - {r.descripcion || "sin descripción"}
              <button onClick={() => aprobar("ruta", r)}>Aprobar</button>
              <button onClick={() => rechazar("ruta", r.id)}>Rechazar</button>
            </li>
          ))}
        </ul>
      </section>

      <button onClick={handleLogout}>Cerrar sesión</button>
    </div>
  );
};

export default DashboardPage;
