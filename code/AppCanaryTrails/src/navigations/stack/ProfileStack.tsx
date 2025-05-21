import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Perfil from '../../screens/Perfil';
import InfoPerfil from '../../screens/InfoPerfil';
import EditPerfil from '../../screens/EditPerfil';
import RutasFavoritas from '../../screens/RutasFavoritas';
import { RutaType, Usuario } from '../../globals/Types';
import InfoRuta from '../../screens/InfoRuta';

type Props = {}

export type ProfileStackParamList = {
  Perfil: undefined,
  InfoPerfil: {usuario: Usuario},
  EditPerfil: {usuario: Usuario},
  RutasFavoritas: undefined,
  InfoRuta: {ruta: RutaType}
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
      <Stack.Screen name="InfoPerfil" component={InfoPerfil} />
      <Stack.Screen name="EditPerfil" component={EditPerfil} />
      <Stack.Screen name="RutasFavoritas" component={RutasFavoritas} />
      <Stack.Screen name="InfoRuta" component={InfoRuta} />

  </Stack.Navigator>
  )
}

export default ProfileStack

const styles = StyleSheet.create({})