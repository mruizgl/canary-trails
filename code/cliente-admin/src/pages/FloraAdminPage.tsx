import React, { useEffect, useState } from "react";
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
    const [form, setForm] = useState<Flora | null>(null);
    const [fotoArchivo, setFotoArchivo] = useState<File | null>(null);
    const [detalleId, setDetalleId] = useState<number | null>(null);

    useEffect(() => {
        fetchFloras();
    }, []);

    const fetchFloras = async () => {
        const res = await authFetch("http://localhost:8080/api/v3/floras");
        const data = await res.json();
        setFloras(data);
    };

    const handleEdit = (flora: Flora) => {
        setForm(flora);
        setFotoArchivo(null);
    };

    const handleNew = () => {
        setForm({
            id: 0,
            nombre: "",
            especie: "",
            tipoHoja: "",
            salidaFlor: "",
            caidaFlor: "",
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
            ? "http://localhost:8080/api/v3/floras/update"
            : "http://localhost:8080/api/v3/floras/add";

      
        const payload = isEdit
            ? {
                id: form.id,
                nombre: form.nombre,
                especie: form.especie,
                tipoHoja: form.tipoHoja,
                salidaFlor: form.salidaFlor,
                caidaFlor: form.caidaFlor,
                descripcion: form.descripcion,
                aprobada: form.aprobada,
                usuario: form.usuario.id,
                rutas: form.rutas.map(r => r.id),
            }
            : {
                nombre: form.nombre,
                especie: form.especie,
                tipoHoja: form.tipoHoja,
                salidaFlor: form.salidaFlor,
                caidaFlor: form.caidaFlor,
                descripcion: form.descripcion,
                aprobada: form.aprobada,
                usuario: form.usuario.id,
                rutas: form.rutas.map(r => r.id),
            };

        const res = await authFetch(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload),
        });

        if (!res.ok) {
            console.error("Error al guardar la flora:", await res.text());
            return;
        }

        const floraId = isEdit ? form.id : (await res.json()).id;

        if (fotoArchivo && floraId) {
            const formData = new FormData();
            formData.append("id", floraId.toString());
            formData.append("file", fotoArchivo);

            const resUpload = await authFetch(`http://localhost:8080/api/v3/floras/upload/${floraId}`, {
                method: "POST",
                body: formData,
            });

            if (resUpload.ok) {
                const updatedFlora = await resUpload.json();

               
                setFloras(prev => prev.map(f => (f.id === updatedFlora.id ? updatedFlora : f)));

               
                setForm(prev => (prev && prev.id === updatedFlora.id ? updatedFlora : prev));
            }
        } else {
            fetchFloras(); 
        }

        setForm(null);
        setFotoArchivo(null);
    };


    const handleDelete = async (id: number) => {
        if (!id || !window.confirm("¿Eliminar definitivamente esta flora?")) return;
        await authFetch(`http://localhost:8080/api/v3/floras/delete/${id}`, { method: "DELETE" });
        fetchFloras();
    };

    return (
        <div className="dashboard-page">
            <h1 className="dashboard-title">Gestión de Flora (CRUD completo)</h1>
            <button className="view-all-btn" onClick={handleNew}>+ Nueva Flora</button>

            <ul className="dashboard-list">
                {floras.map((f) => (
                    <li
                        key={f.id}
                        className="dashboard-item"
                        onClick={() => setDetalleId(detalleId === f.id ? null : f.id)}
                    >
                        <div className="dashboard-row">
                            <div className="dashboard-content-left">
                                <span>{f.nombre} - {f.especie} ({f.aprobada ? "Aprobada" : "No aprobada"})</span>
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
                                        <p><strong>Especie:</strong> {f.especie}</p>
                                        <p><strong>Tipo de hoja:</strong> {f.tipoHoja}</p>
                                        <p><strong>Floración:</strong> {f.salidaFlor} - {f.caidaFlor}</p>
                                        <p><strong>Descripción:</strong> {f.descripcion}</p>
                                        <p><strong>Usuario:</strong> {f.usuario?.nombre}</p>
                                    </div>
                                    {f.foto && (
                                        <div className="dashboard-detail-image">
                                            <img src={`http://localhost:8080/api/v3/fotos/flora/${f.foto}`} alt={`Foto de ${f.nombre}`} />
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
                    <h3>{form.id && form.id !== 0 ? "Editar Flora" : "Nueva Flora"}</h3>

                    <input name="nombre" placeholder="Nombre" value={form.nombre} onChange={handleChange} />
                    <input name="especie" placeholder="Especie" value={form.especie} onChange={handleChange} />
                    <input name="tipoHoja" placeholder="Tipo Hoja" value={form.tipoHoja} onChange={handleChange} />
                    <input name="salidaFlor" placeholder="Salida Flor" value={form.salidaFlor} onChange={handleChange} />
                    <input name="caidaFlor" placeholder="Caída Flor" value={form.caidaFlor} onChange={handleChange} />
                    <textarea name="descripcion" placeholder="Descripción" value={form.descripcion} onChange={handleChange} />

                    {form.foto && (
                        <div className="mb-2">
                            <img
                                src={`http://localhost:8080/api/v3/fotos/flora/${form.foto}`}
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

export default FloraAdminPage;
