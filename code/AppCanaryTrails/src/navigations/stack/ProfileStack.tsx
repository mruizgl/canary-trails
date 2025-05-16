import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Perfil from '../../screens/Perfil';

type Props = {}

export type ProfileStackParamList = {
    Inicio: undefined,
    Otra: undefined,
};

const Stack = createNativeStackNavigator<ProfileStackParamList>();

const ProfileStack = (props: Props) => {
  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
      >      
      <Stack.Screen name="Otra" component={Perfil} />
  
  </Stack.Navigator>
  )
}

export default ProfileStack

const styles = StyleSheet.create({})