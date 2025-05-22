import { StyleSheet, Text, View } from 'react-native'
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Icon from 'react-native-vector-icons/Ionicons';
import React from 'react'
import HomeStack from './stack/HomeStack';
import ProfileStack from './stack/ProfileStack';
import RoutesStack from './stack/SearchRoutesStack';
import EnciclopediaStack from './stack/EnciclopediaStack';
import CrearRutas from '../screens/CrearRutas';
import CrearRutasStack from './stack/CrearStack';

type Props = {

}

const Tab = createBottomTabNavigator();

function PrincipalTabNavigation (){


    return (
        <Tab.Navigator 
            id={undefined}
            screenOptions={{ 
                headerShown: false,
                tabBarStyle: {
                    backgroundColor: '#41463D',      // Color de fondo del tab
                    //borderTopWidth: 0,            // Sin borde superior
                    height: 54,                   // Altura del tab
                },
                tabBarIconStyle: {
                    flex: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                }
            }}>

            <Tab.Screen name='HomeStack' component={HomeStack} 
                options={{
                    tabBarIcon: ({focused})=> <Icon name={(focused) ? 'home' : 'home-outline'} size={30} color={'#F3F5E8'}/>, 
                    tabBarShowLabel: false
                }}
            />

            <Tab.Screen name='RutasStack' component={RoutesStack} 
                options={{
                    tabBarIcon: ({focused})=> <Icon name={(focused) ? 'map' : 'map-outline'} size={30} color={'#F3F5E8'}/> ,
                    tabBarShowLabel: false
                }}
            />

            <Tab.Screen name='CrearStack' component={CrearRutasStack}
                options={{
                    tabBarIcon: ({focused})=> <Icon name={(focused) ? 'add-circle' : 'add-circle-outline'} size={30} color={'#F3F5E8'}/> ,
                    tabBarShowLabel: false
                }}
            />

            {/* <Tab.Screen name='EnciclopediaStack' component={EnciclopediaStack} 
                options={{
                    tabBarIcon: ({focused})=> <Icon name={(focused) ? 'apps' : 'apps-outline'} size={30} color={'white'}/> ,
                    tabBarShowLabel: false
                }}
            /> */}

            <Tab.Screen name='PerfilStack' component={ProfileStack} 
                options={{
                    tabBarIcon: ({focused})=> <Icon name={(focused) ? 'person' : 'person-outline'} size={30} color={'#F3F5E8'}/> ,
                    tabBarShowLabel: false
                }}
            />
        </Tab.Navigator>
    )
}

export default PrincipalTabNavigation

const styles = StyleSheet.create({})