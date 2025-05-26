import { ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React from 'react'
import useUsuario from '../hooks/useUsuario'
import RutaCardSmall from '../components/RutaCardSmall'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { ProfileStackParamList } from '../navigations/stack/ProfileStack'

type Props = {}

type PropsProfile = NativeStackScreenProps<ProfileStackParamList, 'Creaciones'>

const Creaciones = ({navigation, route}:PropsProfile) => {

  const {usuarioLogueado, rutasDelUsuario, faunasDelUsuario, florasDelUsuario} = useUsuario();

  return (
    <ScrollView style={{flex:1, backgroundColor: '#889584'}}>
      
      <View style={{alignSelf: 'center'}}>
        <Text style={{fontSize: 25, fontWeight: 'bold', color: 'white', marginVertical: 20}}>Creaciones</Text>
      </View>

      <View style={styles.underline}/>

      <View style={{margin: 20, marginLeft: 18,}}>
        <Text style={{fontSize: 20, color: 'white', fontWeight: 'bold'}}>Rutas no verificadas:</Text>
      </View>

      {
        (usuarioLogueado !== null) &&
        <View style={{}}>
          {rutasDelUsuario.map((ruta, index) => (
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
        <Text style={{fontSize: 20, color: 'white', fontWeight: 'bold'}}>Faunas no verificadas:</Text>
        
        {
          (usuarioLogueado !== null) &&
          <View style={{}}>
            {faunasDelUsuario.map((fauna, index) => (
              <View key={index} style={{alignSelf: 'center', marginVertical: 10}}>
                <TouchableOpacity >
                  <View style={styles.cardFaunaFlora}>
                    <Text style={{fontSize: 25, fontWeight: 'bold'}}>{fauna.nombre}</Text>
                  </View>
                </TouchableOpacity>
              </View>   
            ))}
          </View>
        }
      </View>

      <View style={{margin: 20, marginLeft: 18,}}>
        <Text style={{fontSize: 20, color: 'white', fontWeight: 'bold'}}>Flora no verificadas:</Text>

        {
          (usuarioLogueado !== null) &&
          <View style={{}}>
            {florasDelUsuario.map((flora, index) => (
              <View key={index} style={{alignSelf: 'center', marginVertical: 10}}>
                <TouchableOpacity >
                  <View style={styles.cardFaunaFlora}>
                    <Text style={{fontSize: 25, fontWeight: 'bold'}}>{flora.nombre}</Text>
                  </View>
                </TouchableOpacity>
              </View>   
            ))}
          </View>
        }
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

  cardFaunaFlora:{
    width: 380,
    height: 90,

    borderRadius: 10,

    //overflow: 'hidden',
    backgroundColor: '#F3F5E8',

    padding: 15,
    justifyContent: 'center'
  }

})