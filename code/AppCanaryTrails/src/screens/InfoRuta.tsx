import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { Comentario, Coordenada, Fauna, Flora, Municipio, Usuario } from '../globals/Types'

type Props = {
  id: number,
  nombre: string,
  dificultad: string,
  tiempoDuracion: number,
  distanciaMetros: number,
  desnivel: number,
  aprobada: boolean,
  usuario: Usuario,     //Usuario sin informacion adicional
  comentarios: Array<Comentario>,   //Comentario con la información del usuario que lo hizo
  faunas: Array<Fauna>,
  floras: Array<Flora>,
  coordenadas: Array<Coordenada>,
  municipios: Array<Municipio>,  //Municipio sin información adicional
  fotos: Array<string>
}

const InfoRuta = (props: Props) => {
  return (
    <View>
      <Text>InfoRuta</Text>

    </View>
  )
}

export default InfoRuta

const styles = StyleSheet.create({})