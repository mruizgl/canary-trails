import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { authFetch } from "../utils/authFetch";
import { jwtDecode } from "jwt-decode";

interface Usuario { id: number; nombre: string }
interface RutaSimple { id: number; nombre: string }

const CrearFloraPage: React.FC = () => {
    const navigate = useNavigate();

    const [nombre, setNombre] = useState("");
    const [especie, setEspecie] = useState("");
    const [tipoHoja, setTipoHoja] = useState("");
    const [salidaFlor, setSalidaFlor] = useState("");
    const [caidaFlor, setCaidaFlor] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [fotoArchivo, setFotoArchivo] = useState<File | null>(null);
    const [usuarioId, setUsuarioId] = useState<number | null>(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (!token) return;
        const decoded = jwtDecode<{ sub: string }>(token);
        const nombreUsuario = decoded.sub;

        const fetchUsuario = async () => {
            const res = await authFetch("http://localhost:8080/api/v3/usuarios");
            const usuarios = await res.json();
            const usuario = usuarios.find((u: Usuario) => u.nombre === nombreUsuario);
            if (usuario) setUsuarioId(usuario.id);
        };

        fetchUsuario();
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!usuarioId) return alert("Usuario no detectado");

        const payload = {
            nombre,
            especie,
            tipoHoja,
            salidaFlor,
            caidaFlor,
            descripcion,
            aprobada: true,
            usuario: usuarioId,
            rutas: []
        };

        console.log("ENVIANDO:", payload);

        const res = await authFetch("http://localhost:8080/api/v3/floras/add", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            const errorText = await res.text();
            console.error("Error al crear flora:", errorText);
            alert("Error al crear flora: " + errorText);
            return;
        }

        const data = await res.json();
        console.log("Flora creada:", data);

        if (fotoArchivo && data.id) {
            const formData = new FormData();
            formData.append("id", data.id.toString());
            formData.append("file", fotoArchivo);

            const resUpload = await authFetch(`http://localhost:8080/api/v3/floras/upload/${data.id}`, {
                method: "POST",
                body: formData
            });

            if (!resUpload.ok) {
                const errorUpload = await resUpload.text();
                console.error("Error al subir imagen:", errorUpload);
                alert("Imagen no subida: " + errorUpload);
            }
        }

        navigate("/admin/floras");
    };


    return (
        <div className="dashboard-page">
            <h1 className="dashboard-title">Crear nueva flora</h1>
            <form onSubmit={handleSubmit} className="form-card">
                <input
                    type="text"
                    placeholder="Nombre"
                    value={nombre}
                    onChange={(e) => setNombre(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Especie"
                    value={especie}
                    onChange={(e) => setEspecie(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Tipo de hoja"
                    value={tipoHoja}
                    onChange={(e) => setTipoHoja(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Salida flor"
                    value={salidaFlor}
                    onChange={(e) => setSalidaFlor(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Caída flor"
                    value={caidaFlor}
                    onChange={(e) => setCaidaFlor(e.target.value)}
                />
                <textarea
                    placeholder="Descripción"
                    value={descripcion}
                    onChange={(e) => setDescripcion(e.target.value)}
                />
                <input
                    type="file"
                    accept="image/*"
                    onChange={(e) => setFotoArchivo(e.target.files?.[0] || null)}
                />
                <div className="form-buttons">
                    <button type="submit" className="approve-btn">Guardar</button>
                    <button type="button" className="reject-btn" onClick={() => navigate("/admin/floras")}>Cancelar</button>
                </div>
            </form>
        </div>
    );
};

export default CrearFloraPage;
