import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { RutaCreateType, RutaType, tokenPlayload } from '../globals/Types'
import axios from 'axios'
import { useAppContext } from '../context/AppContext'
import { useJwt } from 'react-jwt'


const useRutas = () => {

    const context = useAppContext();
    const { decodedToken } = useJwt<tokenPlayload>(context.token);

    const [allRutas, setAllRutas] = useState<Array<RutaType>>([])
    const [rutasRandom, setRutasRandom] = useState<Array<RutaType>>([])
    const [rutasPopulares, setRutasPopulares] = useState<Array<RutaType>>([])

    useEffect(() => {
        //Setea allRutas y RutasRandom
        getRutasPopulares();
        getAllRutas();
    }, [])

    async function getAllRutas(){
        
        let rutasAux : Array<RutaType> = [];

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/rutas`,
            {
                headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
                }
            }
            );

            rutasAux = response.data;

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }

        
        rutasAux = rutasAux.filter((ruta: RutaType) => ruta.aprobada === true); //Filtrado si la ruta esá aprobada se muestra
        setAllRutas(rutasAux);
        console.log("All rutas seteadas");


        let rutasRandomAux : Array<RutaType>= [];

        if (rutasAux.length >= 5) {
            const rutasMezcladas = rutasAux.sort(() => 0.5 - Math.random()); //Genera un valor random entre -0.5 y 0.5 
            rutasRandomAux = rutasMezcladas.slice(0, 5); //Mete los 5 primeros elementos en el array (del 0 al 4)
        } else {
            rutasRandomAux = rutasAux;
        }

        setRutasRandom(rutasRandomAux);
        console.log("Rutas random seteadas");
    }

    async function getRutasPopulares(){

        let rutasAux : Array<RutaType> = [];
      
        try{
  
            const response = await axios.get(`http://10.0.2.2:8080/api/v2/rutas_favoritas/populares`,
                {
                    headers: {
                        'Authorization': `Bearer ${context.token}`, // Token JWT
                        'Content-Type': 'application/json', // Tipo de contenido
                    }
                }
            );
  
          rutasAux = response.data;
  
        } catch (error) {
          console.log("An error has occurred aqui" +error.message);
        }
  
        setRutasPopulares(rutasAux);
    }

    async function crearRuta(ruta : RutaCreateType){

        let rutaAux : RutaType;

        try{

            const response = await axios.post(`http://10.0.2.2:8080/api/v2/rutas/add`, ruta,
            {
                headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
                }
            }
            );

            rutaAux = response.data;

            // if(floraAux !== null){
            //     return true;
            // } else {
            //     return false;
            // }

            return rutaAux;

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }
    }   
    
    

  return {
    allRutas,
    rutasPopulares,
    rutasRandom,
    crearRuta,
  }
}

export default useRutas

const styles = StyleSheet.create({})