import { StyleSheet, Text, View } from 'react-native'
import React, { createContext, useEffect, useState } from 'react'
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Usuario } from '../globals/Types';


type Props = {}

type ContextType = {

    token : string,
    saveToken: (token: string) => void,
    removeToken: () => void,
}

const Context = createContext<ContextType>({} as ContextType);

const AppContext = (props: any) => {

    const [token, settoken] = useState<string>();
    
    useEffect(() => {
        AsyncStorage.getItem("token")
        .then((storedToken) => {
            if (storedToken) {
                settoken(storedToken);
                console.log(storedToken);
                console.log("token obtenido del storage");
                //removeToken();
            }
        })
    }, [])

    const saveToken = (token: string) => {
        AsyncStorage.setItem("token", token);
        settoken(token);
        console.log("token guardado");
    }

    const removeToken = () => {
        AsyncStorage.removeItem("token");
        settoken("");
        console.log("token eliminado");
    }

    const contextValues = {
        token,
        saveToken,
        removeToken,
    }

  return (
    <Context.Provider value={contextValues}>
        {props.children}
    </Context.Provider>
  )
}

export function useAppContext() {
    return React.useContext(Context);
}

export default AppContext

const styles = StyleSheet.create({})