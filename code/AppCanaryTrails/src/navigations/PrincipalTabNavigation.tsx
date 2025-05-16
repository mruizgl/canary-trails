import { StyleSheet, Text, View } from 'react-native'
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Icon from 'react-native-vector-icons/Ionicons';
import React from 'react'
import HomeStack from './stack/HomeStack';
import ProfileStack from './stack/ProfileStack';
import RoutesStack from './stack/RoutesStack';
import EnciclopediaStack from './stack/EnciclopediaStack';

type Props = {}

const Tab = createBottomTabNavigator();

const PrincipalTabNavigation = (props: Props) => {


    return (
        <Tab.Navigator id={undefined}
            screenOptions={{ 
                headerShown: false
            }}>

            <Tab.Screen name='Home' component={HomeStack} 
                options={{tabBarIcon: ({focused})=> <Icon name={(focused) ? 'home' : 'home-outline'} size={30}/> }}
            />

            <Tab.Screen name='Rutas' component={RoutesStack} 
                options={{tabBarIcon: ({focused})=> <Icon name={(focused) ? 'map' : 'map-outline'} size={30}/> }}
            />

            <Tab.Screen name='Enciclopedia' component={EnciclopediaStack} 
                options={{tabBarIcon: ({focused})=> <Icon name={(focused) ? 'apps' : 'apps-outline'} size={30}/> }}
            />

            <Tab.Screen name='Perfil' component={ProfileStack} 
                options={{tabBarIcon: ({focused})=> <Icon name={(focused) ? 'person' : 'person-outline'} size={30}/> }}
            />
        </Tab.Navigator>
    )
}

export default PrincipalTabNavigation

const styles = StyleSheet.create({})