import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import SearchRutas from '../../screens/SearchRutas';

type Props = {}

export type RoutesStackParamList = {
    Inicio: undefined,
    Otra: undefined,
};

const Stack = createNativeStackNavigator<RoutesStackParamList>();

const SearchRutasStack = (props: Props) => {
  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
      >      
      <Stack.Screen name="Otra" component={SearchRutas} />
    
    </Stack.Navigator>
  )
}

export default SearchRutasStack

const styles = StyleSheet.create({})