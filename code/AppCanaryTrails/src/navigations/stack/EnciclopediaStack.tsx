import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Enciclopedia from '../../screens/Enciclopedia';

type Props = {}

export type EnciclopediaStackParamList = {
    Enciclopedia: undefined,
};

const Stack = createNativeStackNavigator<EnciclopediaStackParamList>();

const EnciclopediaStack = (props: Props) => {
  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
    >      
      <Stack.Screen name="Enciclopedia" component={Enciclopedia} />
    
    </Stack.Navigator>
  )
}

export default EnciclopediaStack

const styles = StyleSheet.create({})