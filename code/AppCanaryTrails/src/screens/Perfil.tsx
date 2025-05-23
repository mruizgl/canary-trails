import { Image, Linking, StyleSheet, Text, TouchableHighlight, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import {useAppContext} from '../context/AppContext'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { ProfileStackParamList } from '../navigations/stack/ProfileStack'
import axios from 'axios'
import { useJwt } from 'react-jwt'
import { tokenPlayload, Usuario } from '../globals/Types'
import Icon from 'react-native-vector-icons/Ionicons';
import useUsuario from '../hooks/useUsuario'

type Props = {}

type PropsProfile = NativeStackScreenProps<ProfileStackParamList, 'Perfil'>

const Perfil = ({navigation, route}:PropsProfile) => {

  const { removeToken } = useAppContext();
  
  const context = useAppContext();
  const { decodedToken } = useJwt<tokenPlayload>(context.token);

  const {usuarioLogueado, rutasFavoritasByUsuario, esFavoritaDeUser} = useUsuario();
  
  return (
    <View style={{flex:1, backgroundColor: '#889584', justifyContent: 'center'}}>

      <View style={styles.contenedorFoto}>
        <View style={styles.foto}>
          {usuarioLogueado?.foto ? (
            <Image  
              source={{ uri: `http://10.0.2.2:8080/api/v1/imagenes/usuario/${usuarioLogueado.foto}` }}
              style={{ width: 100, height: 100, overflow: 'hidden' }}
            />
          ) : (
            <Text>Cargando foto...</Text>
          )}
        </View>
      </View>

      <View style={styles.opciones}>

        <View style={styles.opcionTop}>
          <TouchableHighlight onPress={()=> navigation.navigate('InfoPerfil', {usuario: usuarioLogueado})}>
            <View style={{flexDirection: 'row'}}>
              <Icon name={'person-circle-outline'} size={30}/>
              <Text style={{marginTop: 5, marginLeft: 5}}>Información de la Cuenta</Text>
            </View>
          </TouchableHighlight>
        </View>

        {/* <View style={styles.opcionBottom}>
        <TouchableHighlight onPress={()=> navigation.navigate('EditPerfil' , {usuario: usuarioLogueado})}>
            <Text>Editar Perfil</Text>
          </TouchableHighlight>
        </View> */}

        <View style={styles.opcion}>
        <TouchableHighlight onPress={()=> navigation.navigate('RutasFavoritas')}>
            <View style={{flexDirection: 'row', marginLeft: 2}}>
              <Icon name={'heart'} size={25}/>
              <Text style={{marginTop: 4, marginLeft: 5}}>Rutas Favoritas</Text>
            </View>
          </TouchableHighlight>
        </View>

        <View style={styles.opcion}>
          <TouchableHighlight onPress={()=> navigation.navigate('Creaciones')}>
            <View style={{flexDirection: 'row', marginLeft: 2}}>
              <Icon name={'reader'} size={25}/>
              <Text style={{marginTop: 4, marginLeft: 5}}>Creaciones</Text>
            </View>
          </TouchableHighlight>
        </View>

        <View style={styles.opcionBottom}>
          <TouchableHighlight onPress={()=> Linking.openURL('https://github.com/mruizgl/canary-trails')}>
            <View style={{flexDirection: 'row'}}>
              <Icon name={'information-circle-outline'} size={30}/>
              <Text style={{marginTop: 5, marginLeft: 5}}>Info</Text>
            </View>
          </TouchableHighlight>
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

    backgroundColor: 'white',
    margin: 10,
    marginBottom: 30,

    height: 100,
    width: 100,

    justifyContent: 'center',
    alignSelf:'center',

    borderRadius: 100,
    borderStyle: 'solid',
    borderWidth: 3,

    overflow: 'hidden'
  },

  foto:{
    // flex: 1,
    alignSelf: 'center',
    
  },

  opciones:{

    // flex: 1,
    height: 300,
    alignSelf: 'center',
    
    backgroundColor: '#F3F5E8',

    justifyContent: 'space-between',
    width: 360,

    borderStyle:'solid',
    borderWidth: 3,
    borderRadius: 10,
  },

  opcion:{
    flex: 1,
    //backgroundColor: 'purple',
    justifyContent: 'center',

    borderStyle: 'solid',
    borderBottomWidth: 2,
    
    height: 60,
    padding: 10,
  },

  opcionTop:{
    flex: 1,
    //backgroundColor: 'purple',
    justifyContent: 'center',

    borderStyle: 'solid',
    borderBottomWidth: 2,
    borderTopLeftRadius: 6,
    borderTopRightRadius: 6,
    
    height: 60,
    padding: 10,
  },

  opcionBottom:{
    flex: 1,
    //backgroundColor: 'purple',
    justifyContent: 'center',

    height: 60,
    padding: 10,

    borderBottomLeftRadius: 6,
    borderBottomRightRadius: 6,
  },

  bottomSpace:{
    // flex: 2,
    //backgroundColor: 'yellow'
  },

  cerrarSesion:{
    alignSelf: 'center',
    justifyContent: 'center',
    height: 100
  }
})