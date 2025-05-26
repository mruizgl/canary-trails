import { FlatList, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import MapView from 'react-native-maps'
import axios from 'axios'
import { useAppContext } from '../context/AppContext'
import { RutaType, tokenPlayload } from '../globals/Types'
import { useJwt } from 'react-jwt'
import RutaCardSmall from '../components/RutaCardSmall'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { RoutesStackParamList } from '../navigations/stack/SearchRoutesStack'
import useRutas from '../hooks/useRutas'

type Props = {}

type PropsSearch = NativeStackScreenProps<RoutesStackParamList, 'SearchRoutes'>

const SearchRutas = ({navigation, route}:PropsSearch) => {

  const context = useAppContext();
  const { decodedToken } = useJwt<tokenPlayload>(context.token);

  const [busqueda, setbusqueda] = useState<string>("");

  const [rutasBuqueda, setrutasBuqueda] = useState<Array<RutaType>>([]);

  const {allRutas} = useRutas();


  function realizarBusqueda(texto: string){

    let rutasAux : Array<RutaType>= [];

    rutasAux = allRutas.filter((ruta: RutaType) => ruta.nombre.toLowerCase().includes(texto.toLowerCase()));
    console.log(rutasAux);
    console.log(texto);
    setrutasBuqueda(rutasAux);
  }
  

  return (
    <View style={{flex:1, backgroundColor:'#889584'}}>

      
      <View style={styles.mapaContenedor}>

        <View style={styles.busqueda}>
          <TextInput placeholder='Buscar...' 
            onChangeText={(texto) => {
                setbusqueda(texto); 
                realizarBusqueda(texto);
            }} 
          />
        </View>

        <FlatList 
          data={rutasBuqueda}
          renderItem={({ item, index }) => {
              return (
              <View style={{alignSelf: 'center', marginVertical: 10}}>
                  <TouchableOpacity 
                      onPress={()=> navigation.navigate('InfoRuta', {ruta: item})}>
                      <RutaCardSmall
                        nombre={item.nombre} 
                        distancia={item.distanciaMetros}
                        dificultad={item.dificultad}
                        tiempo={item.tiempoDuracion}
                        desnivel={item.desnivel}
                        fotos={item.fotos}
                        municipios={item.municipios}
                      />
                  </TouchableOpacity>
              </View>
              )
          }}
          keyExtractor={(item, index) => 'ruta ' + index}
        />
      
      </View>
    </View>
  )
}

export default SearchRutas

const styles = StyleSheet.create({
  busqueda:{

    height: 70,
    marginTop: 240,
    marginHorizontal: 20,
    padding: 10,

    borderStyle: 'solid',
    borderWidth: 2,
    borderRadius: 50,
    borderColor: '#F3F5E8',

    elevation: 20,

    backgroundColor: '#F3F5E8',

    justifyContent: 'center',
    zIndex: 1,
  },

  mapaContenedor:{
    //backgroundColor: 'blue',
    flex: 1,
    //margin: 20,
  },

  mapa:{
    flex:1,
  
    //AIzaSyClzqiUJRIP831x2twIzfbCFh0BRRN9UEE
  }
})