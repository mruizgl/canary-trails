import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { authFetch } from "../utils/authFetch";
import "../styles/DashboardPage.css";

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

const FloraAdminPage: React.FC = () => {
    const [floras, setFloras] = useState<Flora[]>([]);
    const [detalleId, setDetalleId] = useState<number | null>(null);
    const [paginaActual, setPaginaActual] = useState(1);
    const [busqueda, setBusqueda] = useState("");
    const [filtroEspecie, setFiltroEspecie] = useState("todas");

    const navigate = useNavigate();
    const florasPorPagina = 5;

    useEffect(() => {
        fetchFloras();
    }, []);

    const fetchFloras = async () => {
        const res = await authFetch("http://localhost:8080/api/v3/floras");
        const data = await res.json();
        setFloras(data);
    };

    const handleDelete = async (id: number) => {
        if (!window.confirm("¿Eliminar definitivamente esta flora?")) return;

        const flora = floras.find(f => f.id === id);
        if (!flora) return;


        await authFetch("http://localhost:8080/api/v3/floras/update", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                id: flora.id,
                nombre: flora.nombre,
                especie: flora.especie,
                tipoHoja: flora.tipoHoja,
                salidaFlor: flora.salidaFlor,
                caidaFlor: flora.caidaFlor,
                descripcion: flora.descripcion,
                aprobada: flora.aprobada,
                usuario: flora.usuario.id,
                rutas: []
            })
        });


        const res = await authFetch(`http://localhost:8080/api/v3/floras/delete/${id}`, {
            method: "DELETE"
        });

        if (res.ok) {
            fetchFloras();
        } else {
            console.error("Error al eliminar:", await res.text());
        }
    };


    const handleEdit = (floraId: number) => {
        navigate(`/admin/floras/editar/${floraId}`);
    };

    const handleCrear = () => {
        navigate("/admin/floras/crear");
    };

    const especiesUnicas = Array.from(new Set(floras.map(f => f.especie)));

    const florasFiltradas = floras.filter(f => {
        const coincideNombre = f.nombre.toLowerCase().includes(busqueda.toLowerCase());
        const coincideEspecie = filtroEspecie === "todas" || f.especie === filtroEspecie;
        return coincideNombre && coincideEspecie;
    });

    const totalPaginas = Math.ceil(florasFiltradas.length / florasPorPagina);
    const florasPaginadas = florasFiltradas.slice(
        (paginaActual - 1) * florasPorPagina,
        paginaActual * florasPorPagina
    );

    const cambiarPagina = (nueva: number) => {
        if (nueva >= 1 && nueva <= totalPaginas) {
            setPaginaActual(nueva);
        }
    };

    return (
        <div className="dashboard-page">
            <h1 className="dashboard-title">Gestión de Flora</h1>

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
                    value={filtroEspecie}
                    onChange={e => {
                        setFiltroEspecie(e.target.value);
                        setPaginaActual(1);
                    }}
                    className="search-select"
                >
                    <option value="todas">Todas las especies</option>
                    {especiesUnicas.map(especie => (
                        <option key={especie} value={especie}>{especie}</option>
                    ))}
                </select>
            </div>

            <ul className="dashboard-list">
                {florasPaginadas.map((f) => (
                    <li key={f.id} className="dashboard-item" onClick={() => setDetalleId(detalleId === f.id ? null : f.id)}>
                        <div className="dashboard-row">
                            <div className="dashboard-content-left">
                                <span>{f.nombre} - {f.especie} ({f.aprobada ? "Aprobada" : "No aprobada"})</span>
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
                                        <p><strong>Tipo de hoja:</strong> {f.tipoHoja}</p>
                                        <p><strong>Floración:</strong> {f.salidaFlor} - {f.caidaFlor}</p>
                                        <p><strong>Descripción:</strong> {f.descripcion}</p>
                                        <p><strong>Usuario:</strong> {f.usuario?.nombre}</p>
                                        <p><strong>Rutas vinculadas:</strong> {f.rutas.map(r => r.nombre).join(", ")}</p>
                                    </div>
                                    {f.foto && (
                                        <div className="dashboard-detail-image">
                                            <img src={`http://localhost:8080/api/v1/imagenes/flora/${f.foto}`} alt={`Foto de ${f.nombre}`} />
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
                <button className="approve-btn" onClick={handleCrear}>+ Nueva Flora</button>
                <button className="back-btn" onClick={() => navigate("/dashboard")}>← Volver al panel</button>
            </div>
        </div>
    );
};

export default FloraAdminPage;
