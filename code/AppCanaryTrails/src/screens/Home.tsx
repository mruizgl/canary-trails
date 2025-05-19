import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { RutaType, tokenPlayload } from '../globals/Types';
import axios from 'axios';
import { useAppContext } from '../context/AppContext';
import { useJwt } from 'react-jwt';
import { FlatList } from 'react-native-gesture-handler';
import { time } from 'console';
import RutaCard from '../navigations/components/RutaCard';
import RutaCardSmall from '../navigations/components/RutaCardSmall';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { PrincipalStackParamList } from '../navigations/PrincipalStackNavigation';

type Props = {}

type PropsHome = NativeStackScreenProps<PrincipalStackParamList, 'Login'>
const Home = ({navigation, route}:PropsHome) => {

  const context = useAppContext();
  const { decodedToken } = useJwt<tokenPlayload>(context.token);
  const [listaRutasRandom, setlistaRutasRandom] = useState<Array<RutaType>>([])

  useEffect(() => {

    async function obtenerRutasRandom(){

      let contador : number = 1;
      let rutas : Array<RutaType> = [];

      try{

        const response = await axios.get(`http://10.0.2.2:8080/api/v2/rutas`,
          {
            headers: {
              'Authorization': `Bearer ${context.token}`, // Token JWT
              'Content-Type': 'application/json', // Tipo de contenido
            }
          }
        );

        rutas = response.data;

      } catch (error) {
        console.log("An error has occurred aqui" +error.message);
      }

      //let idRutaRandom = 0;
      let rutasRandom : Array<RutaType>= [];
      rutas = rutas.filter((ruta: RutaType) => ruta.aprobada === true); //Filtrado si la ruta esá aprobada se muestra

      if (rutas.length >= 5) {
        const rutasMezcladas = rutas.sort(() => 0.5 - Math.random()); //Genera un valor random entre -0.5 y 0.5 
        rutasRandom = rutasMezcladas.slice(0, 5); //Mete los 5 primeros elementos en el array (del 0 al 4)
      } else {
        rutasRandom = rutas;
      }

      setlistaRutasRandom(rutasRandom);

    }

    obtenerRutasRandom();
    
  }, [])

  return (
    <>
      <View style={{flex: 1, backgroundColor: '#9D8DF1'}}>
        <View style={styles.titulo}>
          <Text style={{fontSize: 20}}>Canary Trails</Text>
          <View style={styles.underline}/>
        </View>
        <View style={styles.containerRandoms}>
          <FlatList 
              data={listaRutasRandom}
              renderItem={({ item, index }) => {
                  return (
                  <View>
                      <TouchableOpacity>
                          <RutaCard 
                            nombre={item.nombre} 
                            distancia={item.distanciaMetros}
                            dificultad={item.dificultad}
                            tiempo={item.tiempoDuracion}
                            desnivel={item.desnivel}
                            fotos={item.fotos}
                            municipios={item.municipios}
                          />
                      </TouchableOpacity>
                  </View>
                  )
              }}
              keyExtractor={(item, index) => 'partida ' + index}
              horizontal={true}
              showsHorizontalScrollIndicator={false}  //Oculta la bara de deslizamiento
              contentContainerStyle={{ paddingHorizontal: 16 }}
              ItemSeparatorComponent={() => <View style={{ width: 12 }} />} //La separacion entre componentes
              snapToAlignment="start" //Que al pasar de tarjeta, se haga snap al start
              snapToInterval={392}  //Que al deslizar las tarjetas se haga snap. El numero es el ancho de la tarjeta + el separador
              decelerationRate="fast" //Se desacelera rapido
          />
        </View>
        
        <View style={styles.restoContenedor}>
          <View style={[styles.underline]}/>
          
          <View  style={{flex: 1}}>
            <View style={{marginLeft: 10, marginTop: 10}}>
              <Text style={{fontSize: 18}}>Rutas Recomendadas</Text>
            </View>

            <View style={{}}>
                <FlatList 
                  data={listaRutasRandom}
                  renderItem={({ item, index }) => {
                      return (
                      <View style={{alignSelf: 'center', marginVertical: 10}}>
                          <TouchableOpacity>
                              <RutaCardSmall 
                                nombre={item.nombre} 
                                distancia={item.distanciaMetros}
                                dificultad={item.dificultad}
                                tiempo={item.tiempoDuracion}
                                desnivel={item.desnivel}
                                fotos={item.fotos}
                                municipios={item.municipios}
                              />
                          </TouchableOpacity>
                      </View>
                      )
                  }}
                  keyExtractor={(item, index) => 'partida ' + index}
                />
            </View>
          </View>
        </View>
      </View>
    </>
  )
}

export default Home

const styles = StyleSheet.create({
  containerRandoms:{
    flex: 2,

    //backgroundColor:'blue',

    paddingVertical: 20,
    //marginTop: 20
  },

  restoContenedor:{
    flex: 4,
    padding: 10,
    //backgroundColor: 'green'
  },

  titulo:{
    //flex: 1,
    alignItems: 'center',
    padding: 10,
  },

  underline: {
    marginTop: 4,
    height: 2,
    width: '100%', // ancho de la raya
    backgroundColor: 'white',
    borderRadius: 2,
  },
})