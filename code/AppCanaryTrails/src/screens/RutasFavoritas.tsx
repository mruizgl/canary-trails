import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React, { useCallback, useEffect, useState } from 'react'
import useRutas from '../hooks/useRutas'
import useUsuario from '../hooks/useUsuario'
import { FlatList } from 'react-native-gesture-handler'
import RutaCardSmall from '../components/RutaCardSmall'
import { ProfileStackParamList } from '../navigations/stack/ProfileStack'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { useFocusEffect } from '@react-navigation/native'

type Props = {}

type PropsProfile = NativeStackScreenProps<ProfileStackParamList, 'RutasFavoritas'>

const RutasFavoritas = ({navigation, route}:PropsProfile) => {

  const {usuarioLogueado, rutasFavoritasByUsuario, obtenerRutasFavoritasByUsuario} = useUsuario();
  

  return (
    <View style={{flex: 1, backgroundColor: '#889584'}}>
      {
        (usuarioLogueado === null) ? 
          <Text>Cargando Rutas favoritas....</Text>
          :
          <>
            <View style={styles.titulo}>
              <Text style={{fontSize: 20, fontWeight: 'bold', color: 'white'}}>RutasFavoritas</Text>
              <Text style={{fontSize: 20, fontWeight: 'bold', color: 'white'}}>{usuarioLogueado?.nombre}</Text>
            </View>

            <View style={[styles.underline]}/>
          
            <View style={{marginBottom: 70}}>
              <FlatList 
                data={rutasFavoritasByUsuario}
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
          </>
      }
    </View>
  )
}

export default RutasFavoritas

const styles = StyleSheet.create({
  titulo:{
    flexDirection: 'row',
    justifyContent: 'space-between',

    marginVertical: 10,
    marginTop: 20,
    marginHorizontal: 20
  },

  underline: {
    marginTop: 2,
    marginBottom: 6,
    marginHorizontal: 10,

    height: 2,
    backgroundColor: 'white',
    borderRadius: 2,
  },
})