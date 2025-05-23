import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { ProfileStackParamList } from '../navigations/stack/ProfileStack'

type Props = {}

type PropsProfile = NativeStackScreenProps<ProfileStackParamList, 'InfoPerfil'>

const InfoPerfil = ({navigation, route}:PropsProfile) => {

  const {usuario} = route.params;

  const [rolUsuario, setrolUsuario] = useState<string>("")

  useEffect(() => {
    let aux = usuario.rol.substring(usuario.rol.indexOf('_') + 1);;

    setrolUsuario(aux);
  }, [])
  
  function getRolColor(rol : string){
    switch (rol.toUpperCase()) {
      case 'USER':
        return 'blue';
      case 'ADMIN':
        return 'red';
      default:
        return 'gray'; // por si acaso
    }
  }

  return (
    <View style={{flex: 1, backgroundColor: '#889584'}}>

      <View style={styles.infoPerfil}>

        <View style={{alignSelf: 'center'}}>
          <Text style={{fontWeight: 'bold', fontSize: 20}}>Información de Usuario</Text>
        </View>

        <View style={[styles.underline, {marginBottom: 20}]}/>

        <View style={styles.datos}>
          <Text>Nombre de Usuario: </Text>
          <Text style={{fontWeight: 'bold'}}>{usuario.nombre}</Text>
        </View>

        <View style={styles.datos}>
          <Text>Correo:</Text>
          <Text style={{fontWeight: 'bold'}}>{usuario.correo}</Text>
        </View>

        <View style={styles.datos}>
          <Text>Contraseña:</Text>
          <Text style={{fontWeight: 'bold'}}>********</Text>
          {/* <Text style={{textDecorationLine: 'underline', color: 'blue', borderWidth: 2, borderRadius: 10, padding: 2, backgroundColor: '#9D8DF1', borderColor: '#9D8DF1'}}>Editar</Text> */}
        </View>

        <View style={[styles.underline, {marginTop: 20}]}/>
        
        <View style={styles.datos}>
          <Text>Rol:</Text>
          <Text style={[{fontWeight: 'bold', color: 'white', borderWidth: 2, borderRadius: 10, padding: 2}, {borderColor: getRolColor(rolUsuario), backgroundColor:getRolColor(rolUsuario)}]}>{rolUsuario}</Text>
        </View>

        {/* <View style={styles.creaciones}>
          <Text style={{fontWeight: 'bold', fontSize: 16}}>Creaciones: </Text>

          <View>

          </View>

        </View> */}
      </View>
    </View>
  )
}

export default InfoPerfil

const styles = StyleSheet.create({

  infoPerfil:{
    // flex: 1,

    backgroundColor: '#F3F5E8',

    margin: 30,
    //marginBottom: 0,
    padding: 15,
    borderRadius: 10,
    overflow: 'scroll'
  },

  datos:{
    marginVertical: 10, 
    flexDirection: 'row', 
    justifyContent: 'space-between',
    marginHorizontal: 10
  },

  creaciones:{
    marginVertical: 10, 
    marginHorizontal: 10,

  },

  underline: {
    marginTop: 5,
    marginBottom: 5,
    height: 2,
    width: '100%', // ancho de la raya
    backgroundColor: 'black',
    borderRadius: 2,
  },
})