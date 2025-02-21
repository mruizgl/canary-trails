import { Alert, Button, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack';

import { EjemploRepository } from '../data/Database';
import { EjemploEntity } from '../data/entity/EjemploEntity';
import { PruebaStackParamList } from '../navigations/PruebaStack';
import axios from 'axios';




type PropsInicio = NativeStackScreenProps<PruebaStackParamList, 'Inicio'>;
function InicioScreen({navigation,route}:PropsInicio) {

    useEffect(() => {
      async function getPokemons(uri : string){
        const respuesta = await axios.get(uri);
        const pokemons = respuesta.data.results;
        console.log("Pokemons : " + JSON.stringify(pokemons));
      }

      getPokemons("https://pokeapi.co/api/v2/pokemon/");
    }, [])
    

    const crear =  async ()=>{
      const datos = await EjemploRepository.find(  );
      console.log("se ha credado : " + JSON.stringify(datos));
    }

    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' ,marginTop: 36}}>
        <Text>inicio screen</Text>
      </View>
    );
}


const styles = StyleSheet.create({})

export default InicioScreen;