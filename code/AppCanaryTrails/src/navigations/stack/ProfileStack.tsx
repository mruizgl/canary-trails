import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Perfil from '../../screens/Perfil';
import InfoPerfil from '../../screens/InfoPerfil';
import EditPerfil from '../../screens/EditPerfil';
import RutasFavoritas from '../../screens/RutasFavoritas';

type Props = {}

export type ProfileStackParamList = {
  Perfil: undefined,
  Info: undefined,
  EditPerfil: undefined,
  RutasFavoritas: undefined,
};

const Stack = createNativeStackNavigator<ProfileStackParamList>();

const ProfileStack = (props: Props) => {
  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
      >      
      <Stack.Screen name="Perfil" component={Perfil} />
      <Stack.Screen name="Info" component={InfoPerfil} />
      <Stack.Screen name="EditPerfil" component={EditPerfil} />
      <Stack.Screen name="RutasFavoritas" component={RutasFavoritas} />

  </Stack.Navigator>
  )
}

export default ProfileStack

const styles = StyleSheet.create({})