import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { TextInput } from 'react-native-gesture-handler'

type Props = {}

const CrearFauna = (props: Props) => {
  return (
    <View style={{flex: 1, backgroundColor: '#889584'}}>

        <View style={styles.crearFauna}>

            <View>
                <Text style={{fontSize: 22, fontWeight: 'bold', alignSelf: 'center'}}>Crear Fauna</Text>
            </View>

            {/* <Text style={{marginLeft: 5, marginTop: 25}}>Nombre: </Text>
            <View style={[styles.inputContainer]}>
                <TextInput placeholder='Ruta de ...' onChangeText={(texto) => setTitulo(texto)} />
            </View>

            <View style={{flexDirection: 'row'}}>
                <Text style={{marginLeft: 5, marginTop: 20, width: 167}}>Distancia (m) : </Text>
                <Text style={{marginLeft: 5, marginTop: 20}}>Duracion (min): </Text>
            </View>

            <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
                <View style={[styles.inputContainer, {width: 150}]}>
                <TextInput placeholder='Distancia' keyboardType="numeric" onChangeText={(texto) => setdistancia(parseInt(texto))} />
                </View>

                <View style={[styles.inputContainer, {width: 150}]}>
                <TextInput placeholder='Duracion' keyboardType="numeric" onChangeText={(texto) => setduracion(parseInt(texto))} />
                </View>
            </View>

            <View style={{flexDirection: 'row'}}>
                <Text style={{marginLeft: 5, marginTop: 20, width: 216}}>Dificultad: </Text>
                <Text style={{marginLeft: 5, marginTop: 20}}>Desnivel (m): </Text>
            </View>

            <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
                
                <View style={[styles.inputContainer, {width: 200}]}>
                    <Dropdown
                        style={{paddingTop: 10, paddingHorizontal: 5}}
                        renderItem={renderItem}
                        data={data}
                        maxHeight={300}
                        labelField="label"
                        valueField="value"
                        placeholder={'Elija la dificultad'}
                        value={value}
                        onChange={item => {
                        setValue(item.value);
                        }}
                    />
                </View>

                <View style={[styles.inputContainer, {width: 100, height: 45}]}>
                    <TextInput placeholder='Desnivel' keyboardType="numeric" onChangeText={(texto) => setdesnivel(parseInt(texto))} />
                </View>
            </View> */}

        </View>
    </View>
  )
}

export default CrearFauna

const styles = StyleSheet.create({

    crearFauna:{
        flex: 1,
        
        backgroundColor: '#F3F5E8',
    
        margin: 30,
        // marginBottom: 0,
        padding: 15,
        paddingBottom: 25,
        
        borderRadius: 10,
        overflow: 'scroll',
    
        elevation: 20,
    },
})