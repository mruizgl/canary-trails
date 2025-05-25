import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { Flora, FloraCreate, FloraDetailed, tokenPlayload } from '../globals/Types';
import { useJwt } from 'react-jwt';
import { useAppContext } from '../context/AppContext';
import axios from 'axios';


const useFlora = () => {
    const context = useAppContext();
    const { decodedToken } = useJwt<tokenPlayload>(context.token);

    const [allFloras, setAllFloras] = useState<Array<Flora>>()

    useEffect(() => {
      getAllFloras();
    }, [])


    async function getAllFloras(){
        
        let florasAux : Array<Flora> = [];

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/floras`,
            {
                headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
                }
            }
            );

            florasAux = response.data;
            florasAux = florasAux.filter((ruta: FloraDetailed) => ruta.aprobada === true);
            

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }

        
        setAllFloras(florasAux);
        console.log("All floras seteados");
    }

    async function crearFlora(flora: FloraCreate){

        let floraAux : FloraCreate;

        try{

            const response = await axios.post(`http://10.0.2.2:8080/api/v2/floras/add`, flora,
            {
                headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
                }
            }
            );

            floraAux = response.data;
            
            return floraAux;


        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }
    }   

    return {
        allFloras,
        crearFlora
    }
}

export default useFlora

const styles = StyleSheet.create({})