import 'react-native-gesture-handler';
import { createDrawerNavigator } from '@react-navigation/drawer';
import React, { useEffect, useState } from 'react';
import type {PropsWithChildren} from 'react';
import { SafeAreaView, ScrollView, StatusBar, StyleSheet, Text, useColorScheme, View } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import "reflect-metadata";
import { dataSource } from './src/data/Database';
import PrincipalTabNavigation from './src/navigations/PrincipalTabNavigation';
import AppContext from './src/context/AppContext';
import PrincipalStackNavigation from './src/navigations/PrincipalStackNavigation';
import { GestureHandlerRootView } from 'react-native-gesture-handler';


function App(): React.JSX.Element {

  const [dbInitilized, setDbInitilized] = useState(false);
  
  useEffect(() => {
  
      async function iniciarDDBB(){
        try{
          console.log("Inicializando base de datos...");
          await dataSource.initialize();
          console.log("Base de datos inicializada correctamente");
          //cargar();
          setDbInitilized(true);

        } catch(e){
          console.error("no arranca la ddbb" + e)
        }
      }
      iniciarDDBB();
          
  }, [])

 
  return (
    <>
      {
        dbInitilized? (
          <GestureHandlerRootView>
            <NavigationContainer >
              <AppContext>
                <PrincipalStackNavigation />
              </AppContext>
            </NavigationContainer>
          </GestureHandlerRootView>
        ) : (
          <View style={{flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <Text>Cargando...</Text>
          </View>
        )
      }
    </>
  );
  
}
  









export default App;
