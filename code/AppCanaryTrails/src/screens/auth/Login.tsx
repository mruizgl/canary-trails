import { Alert, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { TextInput } from 'react-native-gesture-handler';
import axios from 'axios';
import { useAppContext } from '../../context/AppContext';
import { PrincipalStackParamList } from '../../navigations/PrincipalStackNavigation';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { tokenPlayload, Usuario } from '../../globals/Types';
import { useJwt } from 'react-jwt';

type Props = {}

type PropsHome = NativeStackScreenProps<PrincipalStackParamList, 'Login'>

const Login = ({navigation, route}:PropsHome) => {

    const {saveToken} = useAppContext();
      
    const context = useAppContext();
    const { decodedToken } = useJwt<tokenPlayload>(context.token);

    const [nombre, setnombre] = useState("")
    const [password, setpassword] = useState("")

    async function login() {

      const datosLogin ={
        nombre: nombre, 
        password: password
      }
      
      try {
          const response = await axios.post(`http://10.0.2.2:8080/api/v1/auth/login`, datosLogin);
          
          if (response.status === 200) {
              saveToken(response.data);
          }

      } catch (error: any) {
        console.log("Error inesperado: ", error.message);
        mostrarAlert();
      }
      
    }

    function mostrarAlert(){

      setTimeout(() => {
        Alert.alert(
          "Error al Iniciar Sesión",
          "Los datos son incorrectos o la cuenta no está verificada. Revisa tu correo.",
          [{ text: "Ok" }],
          { cancelable: false }
        );
      }, 100); 
    }

    return (
        <View style={{flex:1, backgroundColor: '#889584'}}>
            
            <View style={styles.estiloFormulario}>
              <View style={{alignSelf: 'center'}}>
                <Text style={{fontWeight: 'bold', fontSize: 20}}>Login</Text>
              </View>

              <Text style={[styles.label, {marginTop: 30}]}>Nombre de usuario:</Text>
              <TextInput style={[styles.input, {marginBottom: 15}]} placeholder='nombre' onChangeText={(texto) => setnombre(texto)} />
              
              <Text style={styles.label}>Contraseña: </Text>
              <TextInput style={styles.input} placeholder='password' secureTextEntry={true} onChangeText={(texto) => setpassword(texto)} />
              
              <View style={styles.button}>
                  <TouchableOpacity onPress={login}>
                      <Text style={{color: 'white'}}>Login</Text>
                  </TouchableOpacity>
              </View>

              <View>
                  <TouchableOpacity onPress={()=> navigation.replace('Register')}>
                      <Text style={{color: 'blue', textAlign: 'center', margin: 20}}>No tengo cuenta</Text>
                  </TouchableOpacity>
              </View>
            </View>
        </View>
    )
}

export default Login

const styles = StyleSheet.create({

  estiloFormulario:{
    backgroundColor: '#F3F5E8',

    margin: 30,
    //marginBottom: 0,
    padding: 15,
    borderRadius: 10,
    overflow: 'scroll'
  },

  label:{
    marginBottom: 5,
    marginLeft: 5
  },

  input:{
    borderWidth: 2,
    borderRadius: 5,

    borderColor: '#e4d49c',
    backgroundColor: '#e4d49c',
  },

  button:{
    marginTop: 30, 
    borderWidth: 2, 

    borderColor: '#00A676' , 
    backgroundColor: '#00A676',
    borderRadius: 5,

    margin: 10, 
    padding: 2, 
    alignItems: 'center'
  }
})