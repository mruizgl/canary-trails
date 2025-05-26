import { Alert, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
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

    async function register() {
        
        const datosRegister ={
            nombre: nombre, 
            correo: correo,
            password: password
        }

        try {
            const response = await axios.post(`http://10.0.2.2:8080/api/v1/auth/register`, datosRegister
            );
            
            if (response.status === 200) {
                navigation.navigate('Success');
            }

        } catch (error) {
            console.log("An error has occurred aqui" +error.message);
            mostrarAlert();
        }
        
    }

    function mostrarAlert(){
        Alert.alert(
            "Error al Registrarse",
            "Revise que su correo exista y que la contraseña tenga más de 8 caracteres",
            [{ text: "Ok" }],
            { cancelable: false }
        );
    }

    return (
        <View style={{flex:1, backgroundColor: '#889584'}}>
            <View style={styles.estiloFormulario}>

                <View style={{alignSelf: 'center'}}>
                    <Text style={{fontWeight: 'bold', fontSize: 20}}>Register</Text>
                </View>

                <Text style={[styles.label, {marginTop: 30}]}>Nombre de Usuario: </Text>
                <TextInput style={styles.input} placeholder='nombre' onChangeText={(texto) => setnombre(texto)} />
                
                <Text style={[styles.label, {marginTop: 20}]}>Correo: </Text>
                <TextInput style={styles.input} placeholder='correo' onChangeText={(texto) => setcorreo(texto)} />
                
                <Text style={[styles.label, {marginTop: 20}]}>Contraseña </Text>
                <TextInput style={styles.input} placeholder='password' secureTextEntry={true} onChangeText={(texto) => setpassword(texto)} />

                <View style={styles.button}>
                    <TouchableOpacity onPress={register}>
                        <Text style={{color: 'white'}}>Register</Text>
                    </TouchableOpacity>
                </View>

                <View>
                    <TouchableOpacity onPress={()=> navigation.navigate('Login')}>
                        <Text style={{color: 'blue', textAlign: 'center', margin: 20}}>Ya tengo cuenta</Text>
                    </TouchableOpacity>
                </View>
            </View>
        </View>

    )
}

export default Register

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