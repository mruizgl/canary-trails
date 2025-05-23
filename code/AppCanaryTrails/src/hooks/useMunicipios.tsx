import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { useAppContext } from '../context/AppContext';
import { useJwt } from 'react-jwt';
import { Municipio, tokenPlayload } from '../globals/Types';
import axios, { all } from 'axios';


const useMunicipios = () => {

    const context = useAppContext();
    const { decodedToken } = useJwt<tokenPlayload>(context.token);

    const [allMunicipios, setAllMunicipios] = useState<Array<Municipio>>()

    useEffect(() => {

        getAllMunicipios();

    }, [])
    

    async function getAllMunicipios(){
        
        let municipiosAux : Array<Municipio> = [];

        try{

            const response = await axios.get(`http://10.0.2.2:8080/api/v2/municipios`,
            {
                headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
                }
            }
            );

            municipiosAux = response.data;

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }

        
        
        setAllMunicipios(municipiosAux);
        console.log("All municipios seteados");
    }


    return {
        allMunicipios,
    }
}

export default useMunicipios

const styles = StyleSheet.create({})