import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import CrearRutas from '../../screens/CrearRutas';
import CrearFauna from '../../screens/CrearFauna';
import SelectCrear from '../../screens/SelectCrear';
import CrearFlora from '../../screens/CrearFlora';

type Props = {}

export type CrearStackParamList = {
    SelectCrear: undefined,
    CrearRutas: undefined,
    CrearFauna: undefined,
    CrearFlora: undefined
};

const Stack = createNativeStackNavigator<CrearStackParamList>();

const CrearStack = (props: Props) => {
  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
    >      
      <Stack.Screen name="SelectCrear" component={SelectCrear} />
      <Stack.Screen name="CrearRutas" component={CrearRutas} />
      <Stack.Screen name="CrearFauna" component={CrearFauna} />
      <Stack.Screen name="CrearFlora" component={CrearFlora} />

    
    </Stack.Navigator>
  )
}

export default CrearStack

const styles = StyleSheet.create({})