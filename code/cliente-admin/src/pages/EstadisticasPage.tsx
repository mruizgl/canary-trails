import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { BarChart, Bar, XAxis, YAxis, Tooltip, PieChart, Pie, Cell, Legend } from "recharts";
import { authFetch } from "../utils/authFetch";

interface Ruta {
  id: number;
  nombre: string;
  dificultad: string;
  aprobada: boolean;
}

interface Flora {
  id: number;
  aprobada: boolean;
}

interface Fauna {
  id: number;
  aprobada: boolean;
}

const EstadisticasPage: React.FC = () => {
  const navigate = useNavigate();
  const [rutas, setRutas] = useState<Ruta[]>([]);
  const [floras, setFloras] = useState<Flora[]>([]);
  const [faunas, setFaunas] = useState<Fauna[]>([]);

  useEffect(() => {
    const fetchAll = async () => {
      const rutasRes = await authFetch("http://localhost:8080/api/v3/rutas");
      const florasRes = await authFetch("http://localhost:8080/api/v3/floras");
      const faunasRes = await authFetch("http://localhost:8080/api/v3/faunas");
      setRutas(await rutasRes.json());
      setFloras(await florasRes.json());
      setFaunas(await faunasRes.json());
    };
    fetchAll();
  }, []);

  const dataRutasPorDificultad = ["baja", "media", "alta"].map((nivel) => ({
    dificultad: nivel,
    cantidad: rutas.filter((r) => r.dificultad === nivel).length
  }));

  const dataContenido = [
    { tipo: "Rutas", total: rutas.length },
    { tipo: "Flora", total: floras.length },
    { tipo: "Fauna", total: faunas.length }
  ];

  const dataAprobados = [
    {
      tipo: "Aprobadas",
      cantidad:
        rutas.filter(r => r.aprobada).length +
        floras.filter(f => f.aprobada).length +
        faunas.filter(f => f.aprobada).length
    },
    {
      tipo: "Pendientes",
      cantidad:
        rutas.filter(r => !r.aprobada).length +
        floras.filter(f => !f.aprobada).length +
        faunas.filter(f => !f.aprobada).length
    }
  ];

  const colores = ["#82ca9d", "#8884d8", "#ffc658"];

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Estadísticas Generales</h1>

      <div style={{ display: "flex", justifyContent: "center", flexWrap: "wrap", gap: "2rem", marginBottom: "2rem" }}>
        <div className="chart-wrapper">
          <h2>Rutas por dificultad</h2>
          <BarChart width={300} height={250} data={dataRutasPorDificultad}>
            <XAxis dataKey="dificultad" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="cantidad" fill="#82ca9d" />
          </BarChart>
        </div>

        <div className="chart-wrapper">
          <h2>Contenido total</h2>
          <BarChart width={300} height={250} data={dataContenido}>
            <XAxis dataKey="tipo" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="total" fill="#8884d8" />
          </BarChart>
        </div>

        <div className="chart-wrapper">
          <h2>Aprobados vs pendientes</h2>
          <PieChart width={300} height={300}>
            <Pie
              dataKey="cantidad"
              data={dataAprobados}
              cx="50%"
              cy="50%"
              outerRadius={100}
              label
            >
              {dataAprobados.map((_, index) => (
                <Cell key={index} fill={colores[index % colores.length]} />
              ))}
            </Pie>
            <Tooltip />
            <Legend />
          </PieChart>
        </div>
      </div>

      <div style={{ textAlign: "center" }}>
        <button className="back-btn" onClick={() => navigate("/dashboard")}>
          ← Volver al panel
        </button>
      </div>
    </div>
  );
};

export default EstadisticasPage;
