import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { ProfileStackParamList } from '../navigations/stack/ProfileStack'
import { TextInput } from 'react-native-gesture-handler'

type Props = {}

type PropsProfile = NativeStackScreenProps<ProfileStackParamList, 'EditPerfil'>

const EditPerfil = ({navigation, route}:PropsProfile) => {

    const {usuario} = route.params;
  
    const [rolUsuario, setrolUsuario] = useState<string>("")

    const [nombre, setnombre] = useState(usuario.nombre)
  
    useEffect(() => {
      let aux = usuario.rol.substring(usuario.rol.indexOf('_') + 1);;
  
      setrolUsuario(aux);
    }, [])

  return (
    <View style={{flex: 1, backgroundColor: '#9D8DF1'}}>
    
          <View style={styles.infoPerfil}>
    
            <View style={{alignSelf: 'center', marginBottom: 40}}>
              <Text style={{fontWeight: 'bold', fontSize: 20}}>Editar información de Usuario</Text>
            </View>
    
            <View style={styles.datos}>
              <View>
                <Text>Nombre de Usuario: </Text>
              </View>
              <View>
                <TextInput 
                  placeholder={nombre} 
                  onChangeText={(texto) => {
                      setnombre(texto);
                  }}
                />
              </View>
            </View>
    
            <View style={styles.datos}>
              <Text>Correo:</Text>
              <Text style={{fontWeight: 'bold'}}>{usuario.correo}</Text>
            </View>
    
            <View style={styles.datos}>
              <Text>Contraseña:</Text>
              <Text style={{fontWeight: 'bold'}}>********</Text>
              <Text style={{textDecorationLine: 'underline', color: 'blue', borderWidth: 2, borderRadius: 10, padding: 2, backgroundColor: '#9D8DF1', borderColor: '#9D8DF1'}}>Editar</Text>
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

export default EditPerfil

const styles = StyleSheet.create({
  infoPerfil:{
    // flex: 1,

    backgroundColor: '#B8CDF8',

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
  }
})