import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Home from '../../screens/Home';
import {useAppContext } from '../../context/AppContext';
import { useJwt } from 'react-jwt';
import axios from 'axios';
import { RutaType } from '../../globals/Types';
import InfoRuta from '../../screens/InfoRuta';

type Props = {}

export type HomeStackParamList = {
    Home: undefined,
    InfoRuta: {ruta: RutaType},
};

const Stack = createNativeStackNavigator<HomeStackParamList>();

const HomeStack = (props: Props) => {

  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
      >      
      <Stack.Screen name="Home" component={Home} />
      <Stack.Screen name="InfoRuta" component={InfoRuta} />
    
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