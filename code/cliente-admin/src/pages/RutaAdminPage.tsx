import React, { useEffect, useState } from "react";
import { authFetch } from "../utils/authFetch";
import { useNavigate } from "react-router-dom";

interface Usuario { nombre: string }
interface Ruta {
    id: number;
    nombre: string;
    descripcion?: string;
    dificultad: string;
    tiempoDuracion: number;
    distanciaMetros: number;
    desnivel: number;
    aprobada: boolean;
    usuario: Usuario;
    floras: { nombre: string }[];
    faunas: { nombre: string }[];
    municipios: { nombre: string }[];
    coordenadas: { latitud: number, longitud: number }[];
    fotos: string[];
}

const RutaAdminPage: React.FC = () => {
    const [rutas, setRutas] = useState<Ruta[]>([]);
    const [paginaActual, setPaginaActual] = useState(1);
    const [detalleVisible, setDetalleVisible] = useState<number | null>(null);
    const [busqueda, setBusqueda] = useState("");
    const [filtroDificultad, setFiltroDificultad] = useState("todas");

    const navigate = useNavigate();
    const rutasPorPagina = 5;

    useEffect(() => {
        const fetchRutas = async () => {
            const res = await authFetch("http://localhost:8080/api/v3/rutas");
            const data = await res.json();
            setRutas(data);
        };
        fetchRutas();
    }, []);

    const rutasFiltradas = rutas.filter(r => {
        const coincideNombre = r.nombre.toLowerCase().includes(busqueda.toLowerCase());
        const coincideDificultad = filtroDificultad === "todas" || r.dificultad === filtroDificultad;
        return coincideNombre && coincideDificultad;
    });

    const totalPaginas = Math.ceil(rutasFiltradas.length / rutasPorPagina);
    const rutasPaginadas = rutasFiltradas.slice((paginaActual - 1) * rutasPorPagina, paginaActual * rutasPorPagina);

    const cambiarPagina = (nueva: number) => {
        if (nueva >= 1 && nueva <= totalPaginas) {
            setPaginaActual(nueva);
        }
    };

    const toggleDetalle = (id: number) => {
        setDetalleVisible(prev => (prev === id ? null : id));
    };

    const handleNuevaRuta = () => navigate("/admin/rutas/crear");

    const handleEditar = (id: number) => navigate(`/admin/rutas/editar/${id}`);

    const handleEliminar = async (id: number) => {
        if (!window.confirm("¿Seguro que deseas eliminar esta ruta?")) return;
        await authFetch(`http://localhost:8080/api/v3/rutas/delete/${id}`, { method: "DELETE" });
        const res = await authFetch("http://localhost:8080/api/v3/rutas");
        const data = await res.json();
        setRutas(data);
    };

    return (
        <div className="dashboard-page">
            <h1 className="dashboard-title">Todas las Rutas</h1>

            <div className="dashboard-actions" style={{ gap: "1rem", marginBottom: "1rem" }}>
                <input
                    type="text"
                    placeholder="Buscar por nombre..."
                    value={busqueda}
                    onChange={e => {
                        setBusqueda(e.target.value);
                        setPaginaActual(1);
                    }}
                    className="search-input"
                />
                <select
                    value={filtroDificultad}
                    onChange={e => {
                        setFiltroDificultad(e.target.value);
                        setPaginaActual(1);
                    }}
                    className="search-select"
                >
                    <option value="todas">Todas las dificultades</option>
                    <option value="baja">Baja</option>
                    <option value="media">Media</option>
                    <option value="alta">Alta</option>
                </select>
            </div>

            <ul className="dashboard-list">
                {rutasPaginadas.map(r => (
                    <li key={r.id} className="dashboard-item" onClick={() => toggleDetalle(r.id)}>
                        <div className="dashboard-row">
                            <div className="dashboard-content-left">
                                <span>{r.nombre}{r.descripcion ? ` - ${r.descripcion}` : ""}</span>
                            </div>
                            <div className="dashboard-buttons" onClick={(e) => e.stopPropagation()}>
                                <button className="approve-btn" onClick={() => handleEditar(r.id)}>Editar</button>
                                <button className="reject-btn" onClick={() => handleEliminar(r.id)}>Eliminar</button>
                            </div>
                        </div>

                        {detalleVisible === r.id && (
                            <div className="dashboard-detail">
                                <div className="dashboard-detail-columns">
                                    <div className="dashboard-detail-text">
                                        <p><strong>Dificultad:</strong> {r.dificultad}</p>
                                        <p><strong>Duración:</strong> {r.tiempoDuracion} min</p>
                                        <p><strong>Distancia:</strong> {r.distanciaMetros} m</p>
                                        <p><strong>Desnivel:</strong> {r.desnivel} m</p>
                                        <p><strong>Usuario:</strong> {r.usuario?.nombre}</p>
                                        <p><strong>Flora:</strong> {r.floras.map(f => f.nombre).join(", ")}</p>
                                        <p><strong>Fauna:</strong> {r.faunas.map(f => f.nombre).join(", ")}</p>
                                        <p><strong>Municipios:</strong> {r.municipios.map(m => m.nombre).join(", ")}</p>
                                        <p><strong>Coordenadas:</strong> {r.coordenadas.map(c => `[${c.latitud}, ${c.longitud}]`).join(", ")}</p>
                                    </div>
                                    
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

            <div
                className="dashboard-actions"
                style={{
                    marginTop: "2rem",
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center"
                }}
            >
                <button className="approve-btn" onClick={handleNuevaRuta}>Crear nueva ruta</button>
                <button className="back-btn" onClick={() => navigate("/dashboard")}>← Volver al panel</button>
            </div>
        </div>
    );
};

export default RutaAdminPage;
