import { Alert, StyleSheet, Text, TouchableHighlight, View } from 'react-native'
import React, { useState } from 'react'
import { TextInput } from 'react-native-gesture-handler';
import axios from 'axios';
import { useAppContext } from '../../context/AppContext';
import { PrincipalStackParamList } from '../../navigations/PrincipalStackNavigation';
import { NativeStackScreenProps } from '@react-navigation/native-stack';

type Props = {}

type PropsHome = NativeStackScreenProps<PrincipalStackParamList, 'Login'>

const Login = ({navigation, route}:PropsHome) => {

    const {saveToken} = useAppContext();

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
      Alert.alert(
        "Error al Iniciar Sesión",
        "Los datos son incorrectos o la cuenta no está verificada. Revisa tu correo.",
        [{ text: "Ok" }],
        { cancelable: false }
      );
    }

    return (
        <View>
            <TextInput placeholder='nombre' onChangeText={(texto) => setnombre(texto)} />
            <TextInput placeholder='password' secureTextEntry={true} onChangeText={(texto) => setpassword(texto)} />
            <View style={{borderWidth: 2, borderColor: 'blue' , margin: 10, padding: 2, alignItems: 'center'}}>
                <TouchableHighlight onPress={login}>
                    <Text>Login</Text>
                </TouchableHighlight>
            </View>

            <View>
                <TouchableHighlight onPress={()=> navigation.replace('Register')}>
                    <Text style={{color: 'blue', textAlign: 'center', margin: 20}}>No tengo cuenta</Text>
                </TouchableHighlight>
            </View>
        </View>
    )
}

export default Login

const styles = StyleSheet.create({})