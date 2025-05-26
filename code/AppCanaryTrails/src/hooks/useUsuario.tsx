import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { useAppContext } from '../context/AppContext';
import { useJwt } from 'react-jwt';
import { Fauna, FaunaDetailed, Flora, FloraDetailed, RutaType, tokenPlayload, Usuario } from '../globals/Types';
import axios from 'axios';


const useUsuario = () => {

    const context = useAppContext();
    const { decodedToken } = useJwt<tokenPlayload>(context.token);

    const [usuarioLogueado, setUsuarioLogueado] = useState<Usuario>()
    const [rutasFavoritasByUsuario, setRutasFavoritasByUsuario] = useState<Array<RutaType>>([])
    const [rutasDelUsuario, setRutasDelUsuario] = useState<Array<RutaType>>([])
    const [faunasDelUsuario, setFaunasDelUsuario] = useState<Array<FaunaDetailed>>([])
    const [florasDelUsuario, setFlorasDelUsuario] = useState<Array<FloraDetailed>>([])


    useEffect(() => {

        obtenerUsuario();

    }, [decodedToken])

    useEffect(() => {

        obtenerRutasFavoritasByUsuario();
        obtenerRutasCreadasByUsuario();
        obtenerFaunasCreadasByUsuario();
        obtenerFlorasCreadasByUsuario();
      
    }, [usuarioLogueado])
    

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
                correo: response.data.correo,
                password: response.data.password,
                foto: response.data.foto,
                rol: response.data.rol,
                fechaCreacion: response.data.fechaCreacion,
                faunas: response.data.faunas,
                floras: response.data.floras,
                rutas: response.data.rutas,
                comentarios: response.data.comentarios,
                rutasFavoritas: response.data.rutasFavoritas
            }

            setUsuarioLogueado(usuario);
            console.log("Setteado el usuario logueado");

        } catch (error) {
          console.log("An error has occurred aqui" +error.message);
        }

    }

    async function obtenerRutasFavoritasByUsuario(){

        if(usuarioLogueado == null){
            return;
        }

        let rutasAux : Array<RutaType> = [];

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/rutas_favoritas/${usuarioLogueado.id}`,

                {
                    headers: {
                        'Authorization': `Bearer ${context.token}`, // Token JWT
                        'Content-Type': 'application/json', // Tipo de contenido
                    }
                }
            );

            rutasAux = response.data;
            
            setRutasFavoritasByUsuario(rutasAux);
            console.log("Setteadas rutas favoritas del usuario logueado");

        } catch (error) {
          console.log("An error has occurred aqui" +error.message);
        }
    }

    function esFavoritaDeUser(idRuta : number) : boolean{

        //Some devuelve true o false directamente y se detiene cuando encuentra la coincidencia. Más optimo que el filter
        const es = rutasFavoritasByUsuario.some((ruta: RutaType) => ruta.id === idRuta);
        console.log(es);
        return es;
    }

    async function actualizarRutaFavoritas(idRuta : number){

        const idUsuario = usuarioLogueado.id;

        if(esFavoritaDeUser(idRuta)){
            deleteFavLlamadaApi();
        } else {
            addFavLlamadaApi();
        }
        
        async function addFavLlamadaApi(){

            try{

                const response = await axios.post(`http://10.0.2.2:8080/api/v2/rutas_favoritas/add`, {idUsuario, idRuta},
                    {
                        headers: {
                            'Authorization': `Bearer ${context.token}`, // Token JWT
                            'Content-Type': 'application/json', // Tipo de contenido
                        }
                    }
                );
    
                console.log("Añadida a favoritos");
    
            } catch (error) {
              console.log("An error has occurred aqui" +error.message);
            }
        }

        async function deleteFavLlamadaApi(){

            try{

                const response = await axios.delete(`http://10.0.2.2:8080/api/v2/rutas_favoritas/delete`,
                    {
                        data: {
                            idUsuario,
                            idRuta,
                        },
                        headers: {
                            'Authorization': `Bearer ${context.token}`, // Token JWT
                            'Content-Type': 'application/json', // Tipo de contenido
                        }
                    }
                );

                console.log("Eliminada de favoritos");
    
            } catch (error) {
              console.log("An error has occurred aqui" +error.message);
            }
        }

        obtenerRutasFavoritasByUsuario();
    }

    async function obtenerRutasCreadasByUsuario(){

        if(usuarioLogueado == null){
            return;
        }

        let rutasAux : Array<RutaType> = [];

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/rutas/creador/${usuarioLogueado.id}`,

                {
                    headers: {
                        'Authorization': `Bearer ${context.token}`, // Token JWT
                        'Content-Type': 'application/json', // Tipo de contenido
                    }
                }
            );

            rutasAux = response.data;
            rutasAux = rutasAux.filter((ruta: RutaType) => ruta.aprobada !== true); //Filtrado si la ruta esá aprobada se muestra
            setRutasDelUsuario(rutasAux);
            
        } catch (error) {
          console.log("An error has occurred aqui" +error.message);
        }
    }

    async function obtenerFaunasCreadasByUsuario(){

        if(usuarioLogueado == null){
            return;
        }

        let faunasAux : Array<FaunaDetailed> = [];

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/faunas/creador/${usuarioLogueado.id}`,

                {
                    headers: {
                        'Authorization': `Bearer ${context.token}`, // Token JWT
                        'Content-Type': 'application/json', // Tipo de contenido
                    }
                }
            );

            faunasAux = response.data;
            faunasAux = faunasAux.filter((fauna: FaunaDetailed) => fauna.aprobada !== true); //Filtrado si la ruta esá aprobada se muestra
            setFaunasDelUsuario(faunasAux);

        } catch (error) {
          console.log("An error has occurred aqui" +error.message);
        }
    }

    async function obtenerFlorasCreadasByUsuario(){

        if(usuarioLogueado == null){
            return;
        }

        let florasAux : Array<FloraDetailed> = [];

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/floras/creador/${usuarioLogueado.id}`,

                {
                    headers: {
                        'Authorization': `Bearer ${context.token}`, // Token JWT
                        'Content-Type': 'application/json', // Tipo de contenido
                    }
                }
            );

            florasAux = response.data;
            florasAux = florasAux.filter((flora: FloraDetailed) => flora.aprobada !== true); //Filtrado si la ruta esá aprobada se muestra
            setFlorasDelUsuario(florasAux);
            
            console.log("Setteadas rutas favoritas del usuario logueado");

        } catch (error) {
          console.log("An error has occurred aqui" +error.message);
        }
    }

    return {
        usuarioLogueado,
        rutasFavoritasByUsuario,
        rutasDelUsuario,
        florasDelUsuario,
        faunasDelUsuario,
        obtenerRutasFavoritasByUsuario,
        esFavoritaDeUser,
        actualizarRutaFavoritas,
    }
}

export default useUsuario

const styles = StyleSheet.create({})