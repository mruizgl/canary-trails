import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { RutaType, tokenPlayload, Usuario } from '../globals/Types';
import { useAppContext } from '../context/AppContext';
import { useJwt } from 'react-jwt';
import { FlatList } from 'react-native-gesture-handler';
import RutaCard from '../components/RutaCard';
import RutaCardSmall from '../components/RutaCardSmall';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { HomeStackParamList } from '../navigations/stack/HomeStack';
import useRutas from '../hooks/useRutas';

type Props = {}

type PropsHome = NativeStackScreenProps<HomeStackParamList, 'Home'>

const Home = ({navigation, route}:PropsHome) => {

  const context = useAppContext();
  const { decodedToken } = useJwt<tokenPlayload>(context.token);
  
  const {allRutas, rutasPopulares, rutasRandom} = useRutas();

  return (
    <>
      <View style={{flex: 1, backgroundColor: '#889584'}}>

        <View style={styles.titulo}>
          <Text style={{fontSize: 20}}>Canary Trails</Text>
          <View style={styles.underline}/>
        </View>

        <View style={styles.containerRandoms}>
          <FlatList 
              data={rutasRandom}
              renderItem={({ item, index }) => {
                  return (
                  <View>
                      <TouchableOpacity onPress={()=> navigation.navigate('InfoRuta', {ruta: item})}>
                          <RutaCard 
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
              keyExtractor={(item, index) => 'partida ' + index}
              horizontal={true}
              showsHorizontalScrollIndicator={false}  //Oculta la bara de deslizamiento
              contentContainerStyle={{ paddingHorizontal: 16 }}
              ItemSeparatorComponent={() => <View style={{ width: 12 }} />} //La separacion entre componentes
              snapToAlignment="start" //Que al pasar de tarjeta, se haga snap al start
              snapToInterval={392}  //Que al deslizar las tarjetas se haga snap. El numero es el ancho de la tarjeta + el separador
              decelerationRate="fast" //Se desacelera rapido
          />
        </View>
        
        <View style={styles.restoContenedor}>
          
          <View style={[styles.underline]}/>
          
          <View  style={{flex: 1}}>
            <View style={{marginLeft: 10, marginTop: 10}}>
              <Text style={{fontSize: 18}}>Rutas Recomendadas</Text>
            </View>

            <View style={{}}>
                <FlatList 
                  data={rutasPopulares}
                  renderItem={({ item, index }) => {
                      return (
                      <View style={{alignSelf: 'center', marginVertical: 10}}>
                          <TouchableOpacity onPress={()=> navigation.navigate('InfoRuta', {ruta: item})}>
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
                  keyExtractor={(item, index) => 'partida ' + index}
                />
            </View>
          </View>
        </View>
      </View>
    </>
  )
}

export default Home

const styles = StyleSheet.create({
  containerRandoms:{
    flex: 2,

    //backgroundColor:'red',

    //paddingVertical: 5,
    //marginTop: 20
  },

  restoContenedor:{
    flex: 4,
    padding: 10,
    //backgroundColor: 'green'
  },

  titulo:{
    //flex: 1,
    alignItems: 'center',
    padding: 10,
  },

  underline: {
    marginTop: 2,
    height: 2,
    width: '100%', // ancho de la raya
    backgroundColor: 'white',
    borderRadius: 2,
  },
})