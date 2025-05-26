import { Image, StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { Municipio } from '../globals/Types'


type Props = {
  nombre: string,
  distancia: number,
  dificultad: string,
  tiempo: number,
  desnivel: number,
  fotos: Array<string>,
  municipios: Array<Municipio>
}

const RutaCardSmall = (props: Props) => {

  const [distanciaKm, setdistanciaKm] = useState<number>(0)
  const [horas, sethoras] = useState<number>(0)
  const [minutos, setminutos] = useState<number>(0)

  //const [municipio, setmunicipio] = useState<Array<Municipio> | string>()

  useEffect(() => {
    let nuevaDistancia = props.distancia/1000;
    const horasProcesadas = Math.floor(minutos / 60);
    const minutosRestantes = minutos % 60;
    
    setdistanciaKm(nuevaDistancia);
    sethoras(horas);
    setminutos(minutos);
  }, [])
  

  return (
    <View style={styles.card}>
      <View>
        <Text style={{fontSize: 25, fontWeight: 'bold'}}>{props.nombre}</Text>
        <View>
          {
            (props.municipios?.length > 1) ?
              props.municipios.map((municipio, index) => (
                <div key={index}>
                  {municipio.nombre}
                </div>
              ))
            :
            <Text>{props.municipios[0].nombre}</Text>
          }
        </View>
      </View>
    </View>
  )
}

export default RutaCardSmall

const styles = StyleSheet.create({
  card:{
    width: 380,
    height: 90,

    borderRadius: 10,

    //overflow: 'hidden',
    backgroundColor: '#F3F5E8',

    padding: 15,
    justifyContent: 'space-between'
  },
})