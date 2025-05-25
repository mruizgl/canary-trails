import React, { useEffect, useState } from 'react'
import { useAppContext } from '../context/AuthContext';
import { tokenPlayload, Usuario } from '../types/UsuarioTypes';
import { jwtDecode } from 'jwt-decode';
import axios from 'axios';

const useUsuario = () => {

    const context = useAppContext();
    const decodedToken = jwtDecode<tokenPlayload>(context.token);

    const [usuarioLogueado, setUsuarioLogueado] = useState<Usuario>()

    useEffect(() => {

        obtenerUsuario();

    }, [decodedToken])
    

    async function obtenerUsuario(){

        if(decodedToken == null){
            return;
        }

        const nombre = decodedToken?.sub;

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/usuarios/nombre/${nombre}`,

                {
                    headers: {
                        'Authorization': `Bearer ${context.token}`, // Token JWT
                        'Content-Type': 'application/json', // Tipo de contenido
                    }
                }
            );

            const usuario : Usuario = {
                id: response.data.id,
                nombre: response.data.nombre,
            }

            setUsuarioLogueado(usuario);
            console.log("Setteado el usuario logueado");

        } catch (error) {
          console.log("An error has occurred");
        }

    }

    return {
        usuarioLogueado,
    }
}

export default useUsuario