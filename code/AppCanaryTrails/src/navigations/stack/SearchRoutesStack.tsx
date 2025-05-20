import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import SearchRutas from '../../screens/SearchRutas';
import InfoRuta from '../../screens/InfoRuta';
import { RutaType } from '../../globals/Types';

type Props = {}

export type RoutesStackParamList = {
  SearchRoutes: undefined,
  InfoRuta: {ruta: RutaType},
};

const Stack = createNativeStackNavigator<RoutesStackParamList>();

const SearchRutasStack = (props: Props) => {
  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
      >      
      <Stack.Screen name="SearchRoutes" component={SearchRutas} />
      <Stack.Screen name="InfoRuta" component={InfoRuta} />
    
    </Stack.Navigator>
  )
}

export default SearchRutasStack

const styles = StyleSheet.create({})