import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { useAppContext } from '../context/AppContext';
import { useJwt } from 'react-jwt';
import { Fauna, FaunaCreate, FaunaDetailed, tokenPlayload } from '../globals/Types';
import axios, { all } from 'axios';


const useFauna = () => {

    const context = useAppContext();
    const { decodedToken } = useJwt<tokenPlayload>(context.token);

    const [allFaunas, setAllFaunas] = useState<Array<Fauna>>()

    useEffect(() => {
      getAllFaunas();
    }, [])
    

    async function getAllFaunas(){
        
        let faunasAux : Array<Fauna> = [];

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/faunas`,
            {
                headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
                }
            }
            );

            faunasAux = response.data;
            faunasAux = faunasAux.filter((ruta: FaunaDetailed) => ruta.aprobada === true);

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }

        
        setAllFaunas(faunasAux);
        console.log("All faunas seteados");
    }

    async function crearFauna(fauna : FaunaCreate){

        let faunaAux : FaunaCreate;

        try{

            const response = await axios.post(`http://10.0.2.2:8080/api/v2/faunas/add`, fauna,
            {
                headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
                }
            }
            );

            faunaAux = response.data;

            return faunaAux;

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }
    } 

    return {
        allFaunas,
        crearFauna
    }
}

export default useFauna

const styles = StyleSheet.create({})