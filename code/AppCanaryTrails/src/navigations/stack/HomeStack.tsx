import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect } from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Home from '../../screens/Home';
import RutasRecomendadas from '../../screens/RutasRecomendadas';
import { tokenPlayload, useAppContext } from '../../context/AppContext';
import { useJwt } from 'react-jwt';
import axios from 'axios';

type Props = {}

export type HomeStackParamList = {
    Home: undefined,
    RutasRecomentadas: undefined,
};

const Stack = createNativeStackNavigator<HomeStackParamList>();

const HomeStack = (props: Props) => {

  const context = useAppContext();
  const { decodedToken } = useJwt<tokenPlayload>(context.token);

  useEffect(() => {

    async function obtenerRutasRandom(){

      let contador : number = 1;
      let maxRutas : number = 0;

      try{

        const response = await axios.get(`http://10.0.2.2:8080/api/v2/rutas`,
          {
            headers: {
              'Authorization': `Bearer ${context.token}`, // Token JWT
              'Content-Type': 'application/json', // Tipo de contenido
            }
          }
        );

        maxRutas = response.data.length;

      } catch (error) {
        console.log("An error has occurred aqui" +error.message);
      }

      let idRutaRandom = 0;

      while(contador <= 5){

        idRutaRandom = Math.floor(Math.random() * maxRutas);

        
      }

    }

    obtenerRutasRandom();
    
  }, [decodedToken])

  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
      >      
      <Stack.Screen name="Home" component={Home} />
      <Stack.Screen name="RutasRecomentadas" component={RutasRecomendadas} />
    
    </Stack.Navigator>
  )
}

export default HomeStack

const styles = StyleSheet.create({})

/*
  useEffect(() => {
    console.log("PruebaStack");
  
    const grabarYcargar= async ()=>{

      //aquí un ejemplo de que tengamos datos ya en la ddbb previamente:
      let e = new EjemploEntity();
      e.nick = "yepa" + Math.random();
      await EjemploRepository.save(e);

      //aquí podemos hacer ya la carga de los datos que queramos poner en el contexto
      console.log("vamos a cargar ejemplos");
      try{
          const ejemplos = await EjemploRepository.find();
          
          console.log("cargados ejemplos : " +JSON.stringify(ejemplos));
          


      }catch(e){
        console.log(e.message);
      }

    }

    grabarYcargar();
  }, []);
  */