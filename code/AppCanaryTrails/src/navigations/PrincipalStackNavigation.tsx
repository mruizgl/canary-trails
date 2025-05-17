import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect } from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Login from '../screens/auth/Login';
import Register from '../screens/auth/Register';
import PrincipalTabNavigation from './PrincipalTabNavigation';
import { useJwt } from 'react-jwt';
import { useAppContext } from '../context/AppContext';
import Success from '../screens/auth/Success';

type Props = {}

export type PrincipalStackParamList = {
    Login: undefined,
    Register: undefined,
    PrincipalTab: undefined,
    Success: undefined
};

type tokenPlayload ={
    sub: string;
    rol: string;
}

const Stack = createNativeStackNavigator<PrincipalStackParamList>();

const PrincipalStackNavigation = (props: Props) => {

    const context = useAppContext();
    const { decodedToken } = useJwt<tokenPlayload>(context.token);

    useEffect(() => {

    }, [])
    

    return (
        <Stack.Navigator id={undefined}
            screenOptions={{
                headerShown: false, // Oculta la cabecera para todas las pantallas
                }}
            >

        {
            (decodedToken) ?
            <>
                <Stack.Screen name="PrincipalTab" component={PrincipalTabNavigation} />
            </>
            :
            <>
                <Stack.Screen name="Register" component={Register} />
                <Stack.Screen name="Login" component={Login} />
                <Stack.Screen name="Success" component={Success} />
            </>
        }
        
        </Stack.Navigator>
    )
}

export default PrincipalStackNavigation

const styles = StyleSheet.create({})