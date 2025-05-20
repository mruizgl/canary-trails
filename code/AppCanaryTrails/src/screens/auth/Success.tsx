import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React from 'react'
import { PrincipalStackParamList } from '../../navigations/PrincipalStackNavigation'
import { NativeStackScreenProps } from '@react-navigation/native-stack'

type Props = {}

type PropsHome = NativeStackScreenProps<PrincipalStackParamList, 'Success'>

const Success = ({navigation, route}:PropsHome) => {
  return (
    <View style={{flex: 1}}>
        <View style={styles.titulo}>
            <Text>¡Tu cuenta ha sido registrada!</Text>
        </View>
        <View style={styles.descripcion}>
            <Text>Revisa tu correo para validarla y poder iniciar sesión</Text>
            <TouchableOpacity onPress={()=> navigation.replace('Login')}>
                <Text style={{color: 'blue', textAlign: 'center', margin: 20}}>Volver al Login</Text>
            </TouchableOpacity>
        </View>
    </View>
  )
}

export default Success

const styles = StyleSheet.create({
    titulo:{

        flex: 1,

        alignSelf: 'center',
        justifyContent: 'flex-end',
        //backgroundColor: 'blue'
    },

    descripcion:{
        flex: 1,

        alignSelf: 'center',
        //backgroundColor: 'red'
    }
})