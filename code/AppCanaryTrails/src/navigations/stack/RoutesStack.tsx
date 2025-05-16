import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Rutas from '../../screens/Rutas';

type Props = {}

export type RoutesStackParamList = {
    Inicio: undefined,
    Otra: undefined,
};

const Stack = createNativeStackNavigator<RoutesStackParamList>();

const RoutesStack = (props: Props) => {
  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
      >      
      <Stack.Screen name="Otra" component={Rutas} />
    
    </Stack.Navigator>
  )
}

export default RoutesStack

const styles = StyleSheet.create({})