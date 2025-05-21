import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import CrearRutas from '../../screens/CrearRutas';

type Props = {}

export type CrearRutasStackParamList = {
    CrearRutas: undefined,
};

const Stack = createNativeStackNavigator<CrearRutasStackParamList>();

const CrearRutasStack = (props: Props) => {
  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
    >      
      <Stack.Screen name="CrearRutas" component={CrearRutas} />
    
    </Stack.Navigator>
  )
}

export default CrearRutasStack

const styles = StyleSheet.create({})