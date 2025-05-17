import { StyleSheet, Text, TouchableHighlight, View } from 'react-native'
import React from 'react'
import { useAppContext } from '../context/AppContext'

type Props = {}

const Perfil = (props: Props) => {

  const { removeToken } = useAppContext();

  return (
    <View style={{flex:1, backgroundColor: 'green'}}>
      <View style={styles.contenedorFoto}>
        <View style={styles.foto}>
          <Text>Foto</Text>
        </View>
      </View>
      <View style={styles.opciones}>

        <View style={styles.opcion}>
          <Text>Op</Text>
        </View>

        <View style={styles.opcion}>
          <Text>Op</Text>
        </View>

        <View style={styles.opcion}>
          <Text>Op</Text>
        </View>

        <View style={styles.opcion}>
          <Text>Op</Text>
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
    backgroundColor: 'blue'
  },

  foto:{

    alignSelf: 'center',
    justifyContent: 'center',
    
  },

  opciones:{

    flex: 2,
    alignSelf: 'center',
    backgroundColor: 'red',
    justifyContent: 'space-around',
    width: 300,

    borderStyle:'solid',
    borderWidth: 3,
    borderRadius: 10,
    //height: 100,
    //marginBottom: 50
  },

  opcion:{
    backgroundColor: 'purple',
    justifyContent: 'center',

    borderStyle: 'solid',
    borderTopWidth: 2,
    borderBottomWidth: 2,
    height: 99,
    //borderRadius: 5,
    padding: 10,
  },

  bottomSpace:{
    flex: 1,
    backgroundColor: 'yellow'
  },

  cerrarSesion:{
    alignSelf: 'center',
    justifyContent: 'center',
    height: 100
  }
})