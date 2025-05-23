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

const RutaCard = (props: Props) => {

  const [distanciaKm, setdistanciaKm] = useState<number>(0)
  const [horas, sethoras] = useState<number>(0)
  const [minutos, setminutos] = useState<number>(0)
  // let fotoProcesada = 'http://10.0.2.2:8080/images/'+props.fotos[1];

  //const [municipio, setmunicipio] = useState<Array<Municipio> | string>()

  useEffect(() => {
    let nuevaDistancia = props.distancia/1000;
    const horasProcesadas = Math.floor(props.tiempo / 60);
    const minutosRestantes = props.tiempo % 60;
    
    setdistanciaKm(nuevaDistancia);
    sethoras(horasProcesadas);
    setminutos(minutosRestantes);
  }, [])
  

  return (
    <View style={styles.card}>

      <View>
        <Text style={{fontSize: 25, fontWeight: 'bold'}}>{props.nombre}</Text>
        <View>
          {
            (props.municipios.length > 1) ?
              props.municipios.map((municipio, index) => (
                <p key={index}>, {municipio.nombre}</p>
              ))
            :
            <Text>{props.municipios[0].nombre}</Text>
          }
        </View>
      </View>

      <View>
        <Image  
          source={{ uri: 'http://10.0.2.2:8080/api/v1/imagenes/ruta/' + props.fotos[0] }}
          style={{ width: '100%', height: 100 }}
        />
      </View>

      <View style={styles.stats}>
        <View>
          <Text>Distancia: </Text>
          {
            (distanciaKm > 1) ?
            <Text>{distanciaKm} km</Text>
            :
            <Text>{props.distancia} m</Text>
          }
        </View>

        <View>
          <Text>Tiempo: </Text>
          {
            (horas > 0) &&
            <Text>{horas}h</Text>
          }
          {
            (minutos > 0) &&
            <Text>{minutos} min </Text>
          }
        </View>
        
        <View>
          <Text>Desnivel: </Text>
          <Text>{props.desnivel} m</Text>
        </View>

      </View>
    </View>
  )
}

export default RutaCard

const styles = StyleSheet.create({
  card:{
    width: 380,
    height: 240,

    borderRadius: 10,

    overflow: 'hidden',
    backgroundColor: '#F3F5E8',

    padding: 15,
    justifyContent: 'space-between'
  },

  stats:{
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
})