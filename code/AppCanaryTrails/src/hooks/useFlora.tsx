import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { Flora, tokenPlayload } from '../globals/Types';
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
    
    function doCreateFlora(flora : Flora) : boolean{

        if(crearFlora(flora)){
            return true;
        } else {
            return false;
        }

    }

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

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }

        
        setAllFloras(florasAux);
        console.log("All floras seteados");
    }

    async function crearFlora(flora: Flora) : Promise<boolean>{

        let floraAux : Flora;

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

            if(floraAux !== null){
                return true;
            } else {
                return false;
            }

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
        }
    }   

    return {
        allFloras
    }
}

export default useFlora

const styles = StyleSheet.create({})