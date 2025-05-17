import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Home from '../../screens/Home';
import RutasRecomendadas from '../../screens/RutasRecomendadas';

type Props = {}

export type HomeStackParamList = {
    Home: undefined,
    RutasRecomentadas: undefined,
};

const Stack = createNativeStackNavigator<HomeStackParamList>();

const HomeStack = (props: Props) => {

  /*
  useEffect(() => {
    console.log("PruebaStack");
  
    const grabarYcargar= async ()=>{

      //aquí un ejemplo de que tengamos datos ya en la ddbb previamente:
      let e = new EjemploEntity();
      e.nick = "yepa" + Math.random();
      await EjemploRepository.save(e);

      //aquí podemos hacer ya la carga de los datos que queramos poner en el contexto
      console.log("vamos a cargar ejemplos");
      try{
          const ejemplos = await EjemploRepository.find();
          
          console.log("cargados ejemplos : " +JSON.stringify(ejemplos));
          


      }catch(e){
        console.log(e.message);
      }

    }

    grabarYcargar();
  }, []);
  */

  return (
    <Stack.Navigator id={undefined}
      screenOptions={{
          headerShown: false, // Oculta la cabecera para todas las pantallas
        }}
      >      
      <Stack.Screen name="Home" component={Home} />
      <Stack.Screen name="RutasRecomentadas" component={RutasRecomendadas} />
    
    </Stack.Navigator>
  )
}

export default HomeStack

const styles = StyleSheet.create({})