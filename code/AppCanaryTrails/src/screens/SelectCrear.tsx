import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React from 'react'
import Icon from 'react-native-vector-icons/Ionicons';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { CrearStackParamList } from '../navigations/stack/CrearStack';

type Props = {}

type PropsHome = NativeStackScreenProps<CrearStackParamList, 'SelectCrear'>

const SelectCrear = ({navigation, route}:PropsHome) => {
  return (
    <View style={{flex: 1, backgroundColor: '#889584'}}>

        <View style={styles.contenedorCards}>

            <View style={{marginBottom: 50, alignSelf: 'center'}}>
                <Text style={{fontSize: 25, fontWeight: 'bold'}}>Panel de Creación</Text>
            </View>

            <TouchableOpacity onPress={()=> navigation.navigate('CrearRutas')}>
                <View style={styles.cardCrear}>
                    <Text style={{fontSize: 18, fontWeight: 'bold'}}>Crear Ruta</Text>
                    <Icon name={'chevron-forward-outline'} 
                        size={30} color={'black'}
                    />
                </View>
            </TouchableOpacity>

            <TouchableOpacity onPress={()=> navigation.navigate('CrearFauna')}>
                <View style={styles.cardCrear}>
                    <Text style={{fontSize: 18, fontWeight: 'bold'}}>Crear Fauna</Text>
                    <Icon name={'chevron-forward-outline'} 
                        size={30} color={'black'}
                    />
                </View>
            </TouchableOpacity>

            <TouchableOpacity onPress={()=> navigation.navigate('CrearFlora')}>
                <View style={styles.cardCrear}>
                    <Text style={{fontSize: 18, fontWeight: 'bold'}}>Crear Flora</Text>
                    <Icon name={'chevron-forward-outline'} 
                        size={30} color={'black'}
                    />
                </View>
            </TouchableOpacity>

        </View>

    </View>
  )
}

export default SelectCrear

const styles = StyleSheet.create({
    contenedorCards:{
        flex: 1,
        padding: 40,

        justifyContent: 'center',

        // backgroundColor: 'blue'
    },

    cardCrear:{
        backgroundColor: '#F3F5E8',

        flexDirection: 'row',
        justifyContent: 'space-between',

        padding: 15,
        marginBottom: 50,

        borderRadius: 10,

        alignItems: 'center',
    },

})