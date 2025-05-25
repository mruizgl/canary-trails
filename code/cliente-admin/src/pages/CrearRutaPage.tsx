import React, { useEffect, useState } from "react";
import { authFetch } from "../utils/authFetch";
import { useNavigate } from "react-router-dom";
import Select from "react-select";
import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import { jwtDecode } from "jwt-decode";

interface Fauna { id: number; nombre: string }
interface Flora { id: number; nombre: string }
interface Municipio { id: number; nombre: string }
interface Coordenada { latitud: number; longitud: number }

const CrearRutaPage: React.FC = () => {
  const navigate = useNavigate();

  const [nombre, setNombre] = useState("");
  const [dificultad, setDificultad] = useState("baja");
  const [tiempo, setTiempo] = useState(0);
  const [distancia, setDistancia] = useState(0);
  const [desnivel, setDesnivel] = useState(0);

  const [usuarioId, setUsuarioId] = useState<number | null>(null);
  const [faunas, setFaunas] = useState<Fauna[]>([]);
  const [floras, setFloras] = useState<Flora[]>([]);
  const [municipios, setMunicipios] = useState<Municipio[]>([]);
  const [faunaIds, setFaunaIds] = useState<number[]>([]);
  const [floraIds, setFloraIds] = useState<number[]>([]);
  const [municipioIds, setMunicipioIds] = useState<number[]>([]);
  const [coordenadas, setCoordenadas] = useState<Coordenada[]>([]);

  useEffect(() => {
    const fetchUsuarioYDatos = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) return;

        const decoded = jwtDecode<{ sub: string }>(token);
        const nombreUsuario = decoded.sub;

        const usuariosRes = await authFetch("http://localhost:8080/api/v3/usuarios");
        const usuarios = await usuariosRes.json();
        const usuario = usuarios.find((u: any) => u.nombre === nombreUsuario);
        if (!usuario) return alert("No se encontró el usuario logueado");
        setUsuarioId(usuario.id);

        const faunaRes = await authFetch("http://localhost:8080/api/v3/faunas");
        const floraRes = await authFetch("http://localhost:8080/api/v3/floras");
        const municipiosRes = await authFetch("http://localhost:8080/api/v3/municipios");

        setFaunas(await faunaRes.json());
        setFloras(await floraRes.json());
        setMunicipios(await municipiosRes.json());
      } catch (err) {
        console.error("Error cargando datos:", err);
      }
    };

    fetchUsuarioYDatos();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!usuarioId) return alert("No se ha detectado usuario logueado");

    const payload = {
      nombre,
      dificultad,
      tiempoDuracion: tiempo,
      distanciaMetros: distancia,
      desnivel,
      aprobada: false,
      usuario: usuarioId,
      faunas: faunaIds,
      floras: floraIds,
      municipios: municipioIds,
      coordenadas
    };

    const res = await authFetch("http://localhost:8080/api/v3/rutas/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (res.ok) {
      alert("Ruta creada correctamente");
      navigate("/admin/rutas");
    } else {
      alert("Error al crear ruta: " + await res.text());
    }
  };

  const MapaCoordenadas = () => {
    useMapEvents({
      click(e: L.LeafletMouseEvent) {
        setCoordenadas([...coordenadas, {
          latitud: e.latlng.lat,
          longitud: e.latlng.lng
        }]);
      }
    });
    return null;
  };

  const markerIcon: L.Icon<any> = new L.Icon({
    iconUrl: "https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png",
    iconSize: [25, 41],
    iconAnchor: [12, 41]
  });

  return (
    <div className="dashboard-page">
      <h1 className="dashboard-title">Crear nueva ruta</h1>
      <form onSubmit={handleSubmit} className="form-card">
        <div className="form-group">
          <label>Nombre</label>
          <input type="text" value={nombre} onChange={e => setNombre(e.target.value)} required />
        </div>

        <div className="form-group">
          <label>Dificultad</label>
          <select value={dificultad} onChange={e => setDificultad(e.target.value)}>
            <option value="baja">Baja</option>
            <option value="media">Media</option>
            <option value="alta">Alta</option>
          </select>
        </div>

        <div className="form-group-inline">
          <div>
            <label>Tiempo (min)</label>
            <input type="number" value={tiempo} onChange={e => setTiempo(Number(e.target.value))} />
          </div>
          <div>
            <label>Distancia (m)</label>
            <input type="number" value={distancia} onChange={e => setDistancia(Number(e.target.value))} />
          </div>
          <div>
            <label>Desnivel (m)</label>
            <input type="number" value={desnivel} onChange={e => setDesnivel(Number(e.target.value))} />
          </div>
        </div>

        <div className="form-group">
          <label>Fauna asociada</label>
          <Select
            isMulti
            options={faunas.map(f => ({ value: f.id, label: f.nombre }))}
            onChange={selected => setFaunaIds(selected.map(s => s.value))}
          />
        </div>

        <div className="form-group">
          <label>Flora asociada</label>
          <Select
            isMulti
            options={floras.map(f => ({ value: f.id, label: f.nombre }))}
            onChange={selected => setFloraIds(selected.map(s => s.value))}
          />
        </div>

        <div className="form-group">
          <label>Municipios</label>
          <Select
            isMulti
            options={municipios.map(m => ({ value: m.id, label: m.nombre }))}
            onChange={selected => setMunicipioIds(selected.map(s => s.value))}
          />
        </div>

        <div className="form-group">
          <label>Coordenadas (clic en el mapa para añadir)</label>
          <MapContainer
            center={[28.1, -15.4] as [number, number]}
            zoom={8}
            style={{ height: "300px", width: "100%", marginBottom: "1rem" }}
          >
            <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
            <MapaCoordenadas />
            {coordenadas.map((c, idx) => (
              <Marker
                key={idx}
                position={[c.latitud, c.longitud] as [number, number]}
                icon={markerIcon}
              />
            ))}
          </MapContainer>
        </div>
        

        <button type="submit" className="approve-btn">Crear ruta</button>
        <button className="back-btn" onClick={() => navigate("/admin/rutas")}>← Volver a rutas</button>
      </form>
       
            
    </div>
  );
};

export default CrearRutaPage;
