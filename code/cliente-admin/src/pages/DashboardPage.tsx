import React, { useContext, useEffect, useState } from "react";
import { authFetch } from "../utils/authFetch";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
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
    foto: string;
}
interface Fauna {
    id: number;
    nombre: string;
    descripcion: string;
    aprobada: boolean;
    usuario: Usuario;
    rutas: RutaSimple[];
    foto: string;
}
interface Ruta {
    id: number;
    nombre: string;
    dificultad: string;
    tiempoDuracion: number;
    distanciaMetros: number;
    desnivel: number;
    aprobada: boolean;
    usuario: Usuario;
    floras: Flora[];
    faunas: Fauna[];
    comentarios: any[];
    coordenadas: { id: number }[];
    municipios: { id: number; nombre: string }[];
    fotos: string[];
    descripcion?: string;
}

const DashboardPage: React.FC = () => {
    const [floras, setFloras] = useState<Flora[]>([]);
    const [faunas, setFaunas] = useState<Fauna[]>([]);
    const [rutas, setRutas] = useState<Ruta[]>([]);
    const [detalleVisible, setDetalleVisible] = useState<{ tipo: string; id: number } | null>(null);
    const { logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const [paginaFlora, setPaginaFlora] = useState(1);
    const [paginaFauna, setPaginaFauna] = useState(1);
    const [paginaRuta, setPaginaRuta] = useState(1);
    const porPagina = 5;

    useEffect(() => { fetchAll(); }, []);

    const fetchAll = async () => {
        const floraData = await (await authFetch("http://localhost:8080/api/v3/floras")).json();
        const faunaData = await (await authFetch("http://localhost:8080/api/v3/faunas")).json();
        const rutaData = await (await authFetch("http://localhost:8080/api/v3/rutas")).json();
        setFloras(floraData.filter((f: Flora) => !f.aprobada));
        setFaunas(faunaData.filter((f: Fauna) => !f.aprobada));
        setRutas(rutaData.filter((r: Ruta) => !r.aprobada));
    };

    const toggleDetalle = (tipo: string, id: number) => {
        setDetalleVisible(prev => (prev?.tipo === tipo && prev?.id === id ? null : { tipo, id }));
    };

    const paginar = <T,>(array: T[], pagina: number) => array.slice((pagina - 1) * porPagina, pagina * porPagina);

    const renderPagination = (total: number, actual: number, cambiar: (p: number) => void) => {
        const totalPaginas = Math.ceil(total / porPagina);
        return totalPaginas > 1 && (
            <div className="pagination">
                <button onClick={() => cambiar(actual - 1)} disabled={actual === 1}>Anterior</button>
                <span>Página {actual} de {totalPaginas}</span>
                <button onClick={() => cambiar(actual + 1)} disabled={actual === totalPaginas}>Siguiente</button>
            </div>
        );
    };

    const aprobarFlora = async (flora: Flora) => {
        const payload = {
            id: flora.id,
            nombre: flora.nombre,
            descripcion: flora.descripcion,
            especie: flora.especie,
            tipoHoja: flora.tipoHoja,
            salidaFlor: flora.salidaFlor,
            caidaFlor: flora.caidaFlor,
            aprobada: true,
            usuario: flora.usuario.id,
            rutas: flora.rutas.map(r => r.id),
        };
        await authFetch("http://localhost:8080/api/v3/floras/update", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
        });
        fetchAll();
    };

    const aprobarFauna = async (fauna: Fauna) => {
        const payload = {
            id: fauna.id,
            nombre: fauna.nombre,
            descripcion: fauna.descripcion,
            aprobada: true,
            usuario: fauna.usuario.id,
            rutas: fauna.rutas.map(r => r.id),
        };
        await authFetch("http://localhost:8080/api/v3/faunas/update", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
        });
        fetchAll();
    };

    const aprobarRuta = async (ruta: Ruta) => {
        const payload = {
            id: ruta.id,
            nombre: ruta.nombre,
            dificultad: ruta.dificultad,
            tiempoDuracion: ruta.tiempoDuracion,
            distanciaMetros: ruta.distanciaMetros,
            desnivel: ruta.desnivel,
            aprobada: true,
            usuario: ruta.usuario.id,
            faunas: ruta.faunas.map(f => f.id),
            floras: ruta.floras.map(f => f.id),
            coordenadas: ruta.coordenadas.map(c => c.id),
            municipios: ruta.municipios.map(m => m.id),
        };
        await authFetch("http://localhost:8080/api/v3/rutas/update", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
        });
        fetchAll();
    };

    const rechazar = async (tipo: "flora" | "fauna" | "ruta", id: number) => {
        if (!window.confirm("¿Seguro que deseas rechazar esta propuesta?")) return;
        await authFetch(`http://localhost:8080/api/v3/${tipo}s/delete/${id}`, { method: "DELETE" });
        fetchAll();
    };

    const handleLogout = () => {
        logout();
        navigate("/");
    };
    const handleVerEstadisticas = () => {
        navigate("/admin/estadisticas");
    };

    return (
        <div className="dashboard-page">
            <h1 className="dashboard-title">Panel de Administración</h1>
            <br></br>
            <div>
                <div className="dashboard-actions" style={{ display: "flex",  marginBottom: "1rem" }}>
                <button className="view-all-btn" onClick={handleVerEstadisticas}>ESTADÍSTICAS</button>
            </div>
            </div>
            

            <section className="dashboard-section">
                
                <h2>Flora pendiente</h2>
                <ul className="dashboard-list">
                    {paginar(floras, paginaFlora).map((f) => (
                        <li key={f.id} className="dashboard-item" onClick={() => toggleDetalle("flora", f.id)}>
                            <div className="dashboard-row">
                                <div className="dashboard-content-left">
                                    <span>{f.nombre} - {f.descripcion}</span>
                                </div>
                                <div className="dashboard-buttons" onClick={(e) => e.stopPropagation()}>
                                    <button className="approve-btn" onClick={() => aprobarFlora(f)}>Aprobar</button>
                                    <button className="reject-btn" onClick={() => rechazar("flora", f.id)}>Rechazar</button>
                                </div>
                            </div>
                            {detalleVisible?.tipo === "flora" && detalleVisible.id === f.id && (
                                <div className="dashboard-detail">
                                    <div className="dashboard-detail-columns">
                                        <div className="dashboard-detail-text">
                                            <p><strong>Especie:</strong> {f.especie}</p>
                                            <p><strong>Tipo de hoja:</strong> {f.tipoHoja}</p>
                                            <p><strong>Floración:</strong> {f.salidaFlor} - {f.caidaFlor}</p>
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
                {renderPagination(floras.length, paginaFlora, setPaginaFlora)}
                <div className="dashboard-actions">
                    <button className="view-all-btn" onClick={() => navigate("/admin/floras")}>Ver todas las floras</button>
                </div>
            </section>

            <section className="dashboard-section">
                <h2>Fauna pendiente</h2>
                <ul className="dashboard-list">
                    {paginar(faunas, paginaFauna).map((f) => (
                        <li key={f.id} className="dashboard-item" onClick={() => toggleDetalle("fauna", f.id)}>
                            <div className="dashboard-row">
                                <div className="dashboard-content-left">
                                    <span>{f.nombre} - {f.descripcion}</span>
                                </div>
                                <div className="dashboard-buttons" onClick={(e) => e.stopPropagation()}>
                                    <button className="approve-btn" onClick={() => aprobarFauna(f)}>Aprobar</button>
                                    <button className="reject-btn" onClick={() => rechazar("fauna", f.id)}>Rechazar</button>
                                </div>
                            </div>
                            {detalleVisible?.tipo === "fauna" && detalleVisible.id === f.id && (
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
                {renderPagination(faunas.length, paginaFauna, setPaginaFauna)}
                <div className="dashboard-actions">
                    <button className="view-all-btn" onClick={() => navigate("/admin/faunas")}>Ver toda la fauna</button>
                </div>
            </section>

            <section className="dashboard-section">
                <h2>Rutas pendientes</h2>
                <ul className="dashboard-list">
                    {paginar(rutas, paginaRuta).map((r) => (
                        <li key={r.id} className="dashboard-item" onClick={() => toggleDetalle("ruta", r.id)}>
                            <div className="dashboard-row">
                                <div className="dashboard-content-left">
                                    <span>{r.nombre}{r.descripcion ? ` - ${r.descripcion}` : ""}</span>
                                </div>
                                <div className="dashboard-buttons" onClick={(e) => e.stopPropagation()}>
                                    <button className="approve-btn" onClick={() => aprobarRuta(r)}>Aprobar</button>
                                    <button className="reject-btn" onClick={() => rechazar("ruta", r.id)}>Rechazar</button>
                                </div>
                            </div>
                            {detalleVisible?.tipo === "ruta" && detalleVisible.id === r.id && (
                                <div className="dashboard-detail">
                                    <div className="dashboard-detail-columns">
                                        <div className="dashboard-detail-text">
                                            <p><strong>Dificultad:</strong> {r.dificultad}</p>
                                            <p><strong>Duración:</strong> {r.tiempoDuracion} min</p>
                                            <p><strong>Distancia:</strong> {r.distanciaMetros} m</p>
                                            <p><strong>Desnivel:</strong> {r.desnivel} m</p>
                                            <p><strong>Usuario:</strong> {r.usuario?.nombre}</p>
                                            <p><strong>Fauna:</strong> {r.faunas.map(f => f.nombre).join(", ")}</p>
                                            <p><strong>Flora:</strong> {r.floras.map(f => f.nombre).join(", ")}</p>
                                            <p><strong>Municipios:</strong> {r.municipios.map(m => m.nombre).join(", ")}</p>
                                        </div>
                                        {r.fotos?.[0] && (
                                            <div className="dashboard-detail-image">
                                                <img src={`http://localhost:8080/api/v1/imagenes/ruta/${r.fotos[0]}`} alt={`Foto de ${r.nombre}`} />
                                            </div>
                                        )}
                                    </div>
                                </div>
                            )}
                        </li>
                    ))}
                </ul>
                {renderPagination(rutas.length, paginaRuta, setPaginaRuta)}
                <div className="dashboard-actions">
                    <button className="view-all-btn" onClick={() => navigate("/admin/rutas")}>Ver todas las rutas</button>
                </div>
            </section>

            <button className="logout-btn" onClick={handleLogout}>Cerrar sesión</button>
        </div>
    );
};

export default DashboardPage;