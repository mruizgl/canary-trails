import { ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React from 'react'
import useUsuario from '../hooks/useUsuario'
import RutaCardSmall from '../components/RutaCardSmall'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { ProfileStackParamList } from '../navigations/stack/ProfileStack'

type Props = {}

type PropsProfile = NativeStackScreenProps<ProfileStackParamList, 'Creaciones'>

const Creaciones = ({navigation, route}:PropsProfile) => {

  const {usuarioLogueado} = useUsuario();

  return (
    <ScrollView style={{flex:1, backgroundColor: '#889584'}}>
      
      <View style={{alignSelf: 'center'}}>
        <Text style={{fontSize: 25, fontWeight: 'bold', color: 'white', marginVertical: 20}}>Creaciones</Text>
      </View>

      <View style={styles.underline}/>

      <View style={{margin: 20, marginLeft: 18,}}>
        <Text style={{fontSize: 20, color: 'white', fontWeight: 'bold'}}>Rutas:</Text>
      </View>

      {
        (usuarioLogueado !== null) &&
        <View style={{}}>
          {usuarioLogueado?.rutas?.map((ruta, index) => (
            <View key={index} style={{alignSelf: 'center', marginVertical: 10}}>
              <TouchableOpacity onPress={()=> navigation.navigate('InfoRuta', {ruta: ruta})}>
                  <RutaCardSmall
                    nombre={ruta.nombre} 
                    distancia={ruta.distanciaMetros}
                    dificultad={ruta.dificultad}
                    tiempo={ruta.tiempoDuracion}
                    desnivel={ruta.desnivel}
                    fotos={ruta.fotos}
                    municipios={ruta.municipios}
                  />
              </TouchableOpacity>
            </View>   
          ))}
        </View>
      }

      <View style={{margin: 20, marginLeft: 18,}}>
        <Text style={{fontSize: 20, color: 'white', fontWeight: 'bold'}}>Faunas:</Text>
      </View>

      <View style={{margin: 20, marginLeft: 18,}}>
        <Text style={{fontSize: 20, color: 'white', fontWeight: 'bold'}}>Flora:</Text>
      </View>
      

    </ScrollView>
  )
}

export default Creaciones

const styles = StyleSheet.create({

  underline: {
    marginBottom: 5,
    marginHorizontal: 10,

    height: 2,
    backgroundColor: 'white',
    borderRadius: 2,
  },

})