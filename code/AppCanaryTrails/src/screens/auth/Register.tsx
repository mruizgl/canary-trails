import { StyleSheet, Text, TouchableHighlight, View } from 'react-native'
import React, { useState } from 'react'
import { TextInput } from 'react-native-gesture-handler'
import { useAppContext } from '../../context/AppContext'
import axios from 'axios'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { PrincipalStackParamList } from '../../navigations/PrincipalStackNavigation'

type Props = {}

type PropsHome = NativeStackScreenProps<PrincipalStackParamList, 'Register'>

const Register = ({navigation, route}:PropsHome) => {

    const {saveToken} = useAppContext();

    const [nombre, setnombre] = useState("")
    const [password, setpassword] = useState("")
    const [correo, setcorreo] = useState("")

    async function login() {
        
        try {
            const response = await axios.post(`'http://localhost:8080/api/v1/register'`, {
                nombre, password, correo
            });
            
            if (response.status === 200) {
                saveToken(response.data);
            }
        } catch (error) {
            console.log("An error has occurred aqui" +error.message);

        }
    }
  return (
    <>
      <View>
            <TextInput placeholder='nombre' onChangeText={(texto) => setnombre(texto)} />
            <TextInput placeholder='password' secureTextEntry={true} onChangeText={(texto) => setpassword(texto)} />
            <TextInput placeholder='correo' onChangeText={(texto) => setcorreo(texto)} />

            <View style={{borderWidth: 2, borderColor: 'blue' , margin: 10, padding: 2, alignItems: 'center'}}>
                <TouchableHighlight onPress={login}>
                    <Text>Register</Text>
                </TouchableHighlight>
            </View>

            <View>
                <TouchableHighlight onPress={()=> navigation.navigate('Login')}>
                    <Text style={{color: 'blue', textAlign: 'center', margin: 20}}>No tengo cuenta</Text>
                </TouchableHighlight>
            </View>
        </View>
    </>
  )
}

export default Register

const styles = StyleSheet.create({})