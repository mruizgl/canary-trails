import { Alert, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native'
import Icon from 'react-native-vector-icons/Ionicons';
import React, { useState } from 'react'
import useFlora from '../hooks/useFlora';
import useUsuario from '../hooks/useUsuario';

type Props = {}

const CrearFlora = (props: Props) => {

    const [nombre, setNombre] = useState("")
    const [descripcion, setDescripcion] = useState("")
    const {crearFlora} = useFlora();
    const {usuarioLogueado} = useUsuario();

    function createFlora(){
    
            if(!nombre || !descripcion){
                Alert.alert(
                    "Error al crear la flora",
                    "Por favor, rellene todos los campos obligatorios.",
                    [{ text: "Ok" }],
                    { cancelable: false }
                );
                return;
            }
    
            const newFlora = {
                nombre: nombre,
                especie: " - ",
                tipoHoja: " - ",
                salidaFlor: " - ",
                caidaFlor: " - ",
                descripcion: descripcion,
                aprobada: false,
                usuario: usuarioLogueado.id
            }
    
            const floraCreada = crearFlora(newFlora);
            console.log(floraCreada);

            if(floraCreada){
                Alert.alert(
                "Se ha creado la flora",
                "Espere a que un administrador la acepte. Podrá verla en 'Perfil > Creaciones' hasta que sea aceptada",
                [{ text: "Ok" }],
                { cancelable: false }
                );
            } else {
                Alert.alert(
                "Error al crear la flora",
                "Inténtelo de nuevo",
                [{ text: "Ok" }],
                { cancelable: false }
                );
            }
        }
    

    return (
        <View style={{flex: 1, backgroundColor: '#889584'}}>

            <View style={styles.container}>

                <View>
                    <Text style={{fontSize: 22, fontWeight: 'bold', alignSelf: 'center'}}>Crear Flora</Text>
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

                <TouchableOpacity onPress={createFlora}>
                    <View style={styles.createRuta}>
                        <Icon name={'archive-outline'} size={25} color={'white'}/> 
                    </View>
                </TouchableOpacity>

            </View>
        </View>
    )
}

export default CrearFlora

const styles = StyleSheet.create({
    container:{
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