import { Alert, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native'
import React, { useState } from 'react'
import Icon from 'react-native-vector-icons/Ionicons';
import useFauna from '../hooks/useFauna';
import useUsuario from '../hooks/useUsuario';

type Props = {}

const CrearFauna = (props: Props) => {

    const [nombre, setNombre] = useState("")
    const [descripcion, setDescripcion] = useState("")
    const {crearFauna} = useFauna();
    const {usuarioLogueado} = useUsuario();

    function createFauna(){

        if(!nombre || !descripcion){
            Alert.alert(
                "Error al crear la fauna",
                "Por favor, rellene todos los campos obligatorios.",
                [{ text: "Ok" }],
                { cancelable: false }
            );
            return;
        }

        const newFauna = {
            nombre: nombre,
            descripcion: descripcion,
            aprobada: false,
            usuario: usuarioLogueado.id
        }

        const faunaCreada = crearFauna(newFauna);
        console.log(faunaCreada);

        if(faunaCreada){
            Alert.alert(
            "Se ha creado la fauna",
            "Espere a que un administrador la acepte. Podrá verla en 'Perfil > Creaciones' hasta que sea aceptada",
            [{ text: "Ok" }],
            { cancelable: false }
            );
        } else {
            Alert.alert(
            "Error al crear la fauna",
            "Inténtelo de nuevo",
            [{ text: "Ok" }],
            { cancelable: false }
            );
        }
    }

  return (
    <View style={{flex: 1, backgroundColor: '#889584'}}>

        <View style={styles.crearFauna}>

            <View>
                <Text style={{fontSize: 22, fontWeight: 'bold', alignSelf: 'center'}}>Crear Fauna</Text>
            </View>

            <Text style={{marginLeft: 5, marginTop: 25}}>Nombre: </Text>
            <View style={[styles.inputContainer]}>
                <TextInput placeholder='Nombre....' onChangeText={(texto) => setNombre(texto)} />
            </View>

            <Text style={{marginLeft: 5, marginTop: 25}}>Descripcion: </Text>
            <View style={[styles.inputContainer]}>
                <TextInput 
                    placeholder='Descripcion...' 
                    onChangeText={(texto) => setDescripcion(texto)} 
                    multiline={true}
                    numberOfLines={5}
                />
            </View>

            <TouchableOpacity onPress={createFauna}>
                <View style={styles.createRuta}>
                    <Icon name={'archive-outline'} size={25} color={'white'}/> 
                </View>
            </TouchableOpacity>
            
        </View>
    </View>
  )
}

export default CrearFauna

const styles = StyleSheet.create({

    crearFauna:{
        
        backgroundColor: '#F3F5E8',
    
        margin: 30,
        // marginBottom: 0,
        padding: 15,
        paddingBottom: 25,
        
        borderRadius: 10,
        overflow: 'scroll',
    
        elevation: 20,
    },

    inputContainer:{
        marginTop: 10,

        borderWidth: 2,
        borderRadius: 10,
        borderColor: '#e4d49c',
        backgroundColor: '#e4d49c',
    },

    createRuta:{
        alignItems: 'center', 
        alignSelf: 'center',
    
        borderWidth: 2, 
        borderRadius: 10, 
        borderColor: '#D9BF68',
        backgroundColor: '#D9BF68',
    
        width: 150,
        height: 50,
    
        paddingVertical: 10,
    
        marginTop: 30,
    
    },
})