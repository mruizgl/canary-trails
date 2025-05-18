import { StyleSheet, Text, View } from 'react-native'
import React, { createContext, useEffect, useState } from 'react'
import AsyncStorage from '@react-native-async-storage/async-storage';


type Props = {}

export type tokenPlayload ={
  sub: string;
  rol: string;
}

export type Usuario = {
    id: number,
    nombre: string,
    correo: string,
    password: string,
    foto: string
}

type ContextType = {
    //idPartidaActual: number,
    //saveIdPartida: (id: number) => void,
    token : string,
    saveToken: (token: string) => void,
    removeToken: () => void,
    usuarioLogueado: Usuario ,
    setUsuarioLogueado: (usuario : Usuario) => void,
}

const Context = createContext<ContextType>({} as ContextType);

const AppContext = (props: any) => {

    const [token, settoken] = useState<string>();
    const [usuarioLogueado, setusuarioLogueado] = useState<Usuario>()
    
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

    const setUsuarioLogueado = (usuario : Usuario) => {
        setusuarioLogueado(usuario);
        console.log("usuario seteado");
    }

    const contextValues = {
        token,
        saveToken,
        removeToken,
        usuarioLogueado,
        setUsuarioLogueado,
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