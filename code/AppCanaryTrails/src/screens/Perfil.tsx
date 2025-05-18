import { StyleSheet, Text, TouchableHighlight, View } from 'react-native'
import React, { useEffect } from 'react'
import { tokenPlayload, useAppContext, Usuario } from '../context/AppContext'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { ProfileStackParamList } from '../navigations/stack/ProfileStack'
import axios from 'axios'
import { useJwt } from 'react-jwt'

type Props = {}

type PropsProfile = NativeStackScreenProps<ProfileStackParamList, 'Perfil'>

const Perfil = ({navigation, route}:PropsProfile) => {

  const { removeToken } = useAppContext();
  
  const context = useAppContext();
  const { decodedToken } = useJwt<tokenPlayload>(context.token);

  const {setUsuarioLogueado, usuarioLogueado} = useAppContext();

  useEffect(() => {
    if(decodedToken == null){
      return;
    }

    const nombre = decodedToken?.sub;
    async function obtenerUsuario(){

        try{
          const response = await axios.get(`http://10.0.2.2:8080/api/v2/usuarios/nombre/${nombre}`,
            {
              headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
              }
            }
          );

          const usuario : Usuario = {
            id: response.data.id,
            nombre: response.data.nombre,
            correo: response.data.correo,
            password: response.data.password,
            foto: response.data.foto
          }

          setUsuarioLogueado(usuario);

        } catch (error) {
          console.log("An error has occurred aqui" +error.message);
        }

    }

    obtenerUsuario();
  }, [decodedToken])
  

  return (
    <View style={{flex:1}}>
      <View style={styles.contenedorFoto}>
        <View style={styles.foto}>
          <Text>{usuarioLogueado?.foto}</Text>
        </View>
      </View>
      <View style={styles.opciones}>

        <View style={styles.opcion}>
          <TouchableHighlight onPress={()=> navigation.navigate('Info')}>
            <Text>Información de la Cuenta</Text>
          </TouchableHighlight>
        </View>

        <View style={styles.opcion}>
        <TouchableHighlight onPress={()=> navigation.navigate('EditPerfil')}>
            <Text>Editar Perfil</Text>
          </TouchableHighlight>
        </View>

        <View style={styles.opcion}>
        <TouchableHighlight onPress={()=> navigation.navigate('RutasFavoritas')}>
            <Text>Rutas Favoritas</Text>
          </TouchableHighlight>
        </View>

        <View style={styles.opcion}>
        {/* <TouchableHighlight onPress={}> */}
            <Text>Otro</Text>
          {/* </TouchableHighlight> */}
        </View>
      </View>
      <View style={styles.bottomSpace}>
        <View style={styles.cerrarSesion}>
          <TouchableHighlight onPress={removeToken}>
            <Text style={{textDecorationLine: 'underline', color: 'blue'}}>Cerrar Sesión</Text>
          </TouchableHighlight>
        </View>
      </View>
    </View>
  )
}

export default Perfil

const styles = StyleSheet.create({

  contenedorFoto: {
    flex: 1,
    //backgroundColor: 'blue',
    justifyContent: 'center'
  },

  foto:{

    alignSelf: 'center',
    
  },

  opciones:{

    flex: 2,
    alignSelf: 'center',
    
    //backgroundColor: 'red',

    justifyContent: 'space-around',
    width: 300,

    borderStyle:'solid',
    borderWidth: 3,
    borderRadius: 10,
  },

  opcion:{
    //backgroundColor: 'purple',
    justifyContent: 'center',

    borderStyle: 'solid',
    borderTopWidth: 2,
    borderBottomWidth: 2,
    height: 99,
    padding: 10,
  },

  bottomSpace:{
    flex: 1,
    //backgroundColor: 'yellow'
  },

  cerrarSesion:{
    alignSelf: 'center',
    justifyContent: 'center',
    height: 100
  }
})