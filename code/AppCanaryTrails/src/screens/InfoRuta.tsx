import { Image, ScrollView, StyleSheet, Text, TouchableHighlight, TouchableOpacity, View } from 'react-native'
import React, { useCallback, useContext, useEffect, useRef, useState } from 'react'
import { Comentario, Coordenada, CoordenadaMaps, Fauna, Flora, Municipio, RutaType, tokenPlayload, Usuario } from '../globals/Types'
import { useFocusEffect, useRoute } from '@react-navigation/native'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { RoutesStackParamList } from '../navigations/stack/SearchRoutesStack'
import MapView, { Circle, Marker, Polyline } from 'react-native-maps'
import { FlatList, TextInput } from 'react-native-gesture-handler'
import Icon from 'react-native-vector-icons/Ionicons';
import axios from 'axios'
import { useAppContext } from '../context/AppContext'
import { useJwt } from 'react-jwt'
import useUsuario from '../hooks/useUsuario'


// type Props = {
//   id: number,
//   nombre: string,
//   dificultad: string,
//   tiempoDuracion: number,
//   distanciaMetros: number,
//   desnivel: number,
//   aprobada: boolean,
//   usuario: Usuario,     //Usuario sin informacion adicional
//   comentarios: Array<Comentario>,   //Comentario con la información del usuario que lo hizo
//   faunas: Array<Fauna>,
//   floras: Array<Flora>,
//   coordenadas: Array<Coordenada>,
//   municipios: Array<Municipio>,  //Municipio sin información adicional
//   fotos: Array<string>
// }

type PropsSearch = NativeStackScreenProps<RoutesStackParamList, 'InfoRuta'>

const InfoRuta = ({navigation, route}:PropsSearch) => {

  const { ruta } = route.params;

  const context = useAppContext();
  const { decodedToken } = useJwt<tokenPlayload>(context.token);

  const [distanciaKm, setdistanciaKm] = useState<number>(0)
  const [horas, sethoras] = useState<number>(0)
  const [minutos, setminutos] = useState<number>(0)

  const [coordenadasRuta, setcoordenadasRuta] = useState<Array<CoordenadaMaps>>([]);
  const {usuarioLogueado, rutasFavoritasByUsuario, esFavoritaDeUser, actualizarRutaFavoritas} = useUsuario();
  
  const [estadoActualDeFavorita, setEstadoActualDeFavorita] = useState<boolean>();
  const estadoHaCambiadoRef = useRef(false);
  
  const mapRef = useRef(null);

  useFocusEffect(
    useCallback(() => {

      if(rutasFavoritasByUsuario === null){
        return;
      }

      // Esta parte/funcion se llama al ENTRAR en la pantalla
      //console.log("Estableciendo fav o no");
      setEstadoActualDeFavorita(esFavoritaDeUser(ruta.id));

      return () => {

        // Esta función se llama al SALIR de la pantalla (blur)
        //console.log('Saliendo de InfoRuta, actualizando favoritos...');
        if(estadoHaCambiadoRef.current){
          actualizarRutaFavoritas(ruta.id);
          console.log("Estado actualizado");
          estadoHaCambiadoRef.current = false;
        }
        //console.log("Estado no actualiado");
      };
    }, [rutasFavoritasByUsuario])
  );
  

  useEffect(() => {

    let nuevaDistancia = ruta.distanciaMetros/1000;
    const horasProcesadas = Math.floor(ruta.tiempoDuracion / 60);
    const minutosRestantes = ruta.tiempoDuracion % 60;

    let arrayCoordenadasAux : Array<CoordenadaMaps> = [];

    ruta.coordenadas.forEach(coordenada => {

      let toSaveCoordenada : CoordenadaMaps = {
        latitude: coordenada.latitud,
        longitude: coordenada.longitud
      }
      arrayCoordenadasAux.push(toSaveCoordenada);

    });

    setcoordenadasRuta(arrayCoordenadasAux);
    setdistanciaKm(nuevaDistancia);
    sethoras(horasProcesadas);
    setminutos(minutosRestantes);

    const timer = setTimeout(() => {
      if (mapRef.current && arrayCoordenadasAux.length > 0) {
        //Se hace fit a las coordenadas, dejando los espacios establecidos para que se vea
        mapRef.current.fitToCoordinates(arrayCoordenadasAux, {
          edgePadding: { top: 40, right: 30, bottom: 40, left: 40 },
          animated: true,
        });
      }
    }, 100);
  
    return () => clearTimeout(timer);
  }, [])
  

  const getDificultadColor = (dificultad: string) => {

    switch (dificultad.toLowerCase()) {
      case 'fácil':
        return 'green';
      case 'media':
        return 'orange';
      case 'difícil':
        return 'red';
      default:
        return 'gray'; // por si acaso
    }

  };
  

  return (
    <ScrollView style={{flex:1, backgroundColor: '#889584'}}>
      {
        (rutasFavoritasByUsuario === null) ?
        <View>
          <Text>Cargando las Rutas...</Text>
        </View>
        :
        <View style={styles.rutaInfo}>

          <View style={styles.header}>

            <View style={{flexDirection: 'row', justifyContent: 'space-between', width: '100%'}}>

              <View style={{marginLeft: 3}}>
                <Text style={{fontSize: 25}}>{ruta.nombre}</Text>
              </View>

              <View style={{marginTop: 2}}>
                <TouchableOpacity onPress={()=> {
                      setEstadoActualDeFavorita(!estadoActualDeFavorita);
                      estadoHaCambiadoRef.current = true;
                    }}>
                  {
                    (estadoActualDeFavorita) ?
                    <Icon name={'heart'} size={30} color={'red'} /> 
                    :
                    <Icon name={'heart-outline'} size={30} color={'red'} /> 
                  }
                </TouchableOpacity>
              </View>

            </View>

            <View style={styles.underline}/>

            <View style={{flexDirection: 'row', width: '100%', justifyContent: 'space-between'}}>
              <View style={[styles.dificultad, { borderColor: getDificultadColor(ruta.dificultad), backgroundColor:getDificultadColor(ruta.dificultad)}]}>
                <Text>{ruta.dificultad}</Text>
              </View>

              <View style={styles.creador}>
                <Text>Creado por: </Text>
                <Text style={{color: 'blue'}}>{ruta.usuario.nombre}</Text>
              </View>

            </View>
          </View>

          <View style={{marginTop: 20}}>
            <Text style={{fontSize: 16, fontWeight: 'bold'}}>Detalles: </Text>

            <View style={{marginTop: 5, flexDirection: 'row', justifyContent: 'space-between'}}>
              
              <View>
                <Text>Distancia: </Text>
                {
                  (distanciaKm > 1) ?
                  <Text>{distanciaKm} km</Text>
                  :
                  <Text>{ruta.distanciaMetros} m</Text>
                }
              </View>

              <View>
                <Text>Duracion: </Text>
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
                <Text>{ruta.desnivel} m</Text>
              </View>
            </View>
          </View>

          <View style={styles.contenedorMapa}>
            <MapView
              style={styles.mapa}
              ref={mapRef}
              initialRegion={{
                latitude: 28.2789829,
                longitude: -16.5523792,
                latitudeDelta: 1.0,
                longitudeDelta: 1.0,
              }}
            >
              <Polyline
                coordinates={coordenadasRuta}
                strokeColor="#FF0000"      // Color de la línea
                strokeWidth={4}            // Grosor
              />

              {coordenadasRuta.map((coord, index) => (
                  <Circle 
                    key={index}
                    radius={15}
                    center={{ latitude: coord.latitude, longitude: coord.longitude }} 
                    fillColor="rgba(132, 0, 255, 0.5)"
                    strokeColor="transparent"
                  />
              ))}
            </MapView>
          </View>

          <View style={{flex: 1, marginTop: 20}}>

            <Text style={{fontSize: 20, fontWeight: 'bold'}}>Fauna: </Text>

            <View style={{flexDirection: 'row', flexWrap: 'wrap', marginTop: 10}}>
              {ruta.municipios.map((municipio, index) => (
                <View key={index} style={styles.elmentCard}>
                  <Text style={{color: 'white', fontSize: 16}}>{municipio.nombre}</Text>
                </View>
              ))}
            </View>
          </View>
          
          <View style={{flex: 1, marginTop: 5}}>
            <Text style={{fontSize: 20, fontWeight: 'bold'}}>Fauna: </Text>

            <View>
              <FlatList 
                data={ruta.faunas}
                renderItem={({ item, index }) => {
                    return (
                    <View style={{marginVertical: 10}}>
                        <TouchableOpacity>
                          <View style={styles.itemCargadoCard}>
                            <Image  
                              source={{ uri: 'http://10.0.2.2:8080/api/v1/imagenes/fauna/' + item.foto }}
                              style={{ width: 100, height: 80}}
                            />
                            <Text style={{color: 'white', fontSize: 16, marginTop: 2}}>{item.nombre}</Text>
                          </View>
                        </TouchableOpacity>
                    </View>
                    )
                }}
                keyExtractor={(item, index) => 'fauna ' + index}
                horizontal={true}
              />
            
            </View>
          </View>

          {
            (ruta.floras.length > 0) &&
            <View style={{flex: 1, marginTop: 10}}>
              <Text style={{fontSize: 20, fontWeight: 'bold'}}>Flora: </Text>

              <View>
                <FlatList 
                  data={ruta.floras}
                  renderItem={({ item, index }) => {
                      return (
                        <View style={{marginVertical: 10}}>
                            <TouchableOpacity>
                                <View style={styles.itemCargadoCard}>
                                  <Image  
                                    source={{ uri: 'http://10.0.2.2:8080/api/v1/imagenes/flora/' + item.foto }}
                                    style={{ width: 100, height: 80}}
                                  />
                                  <Text style={{fontSize: 16, color: 'white', marginTop: 2}}>{item.nombre}</Text>
                                </View>
                            </TouchableOpacity>
                        </View>
                      )
                  }}
                  keyExtractor={(item, index) => 'fauna ' + index}
                  horizontal={true}
                />  
              </View>
            </View>
          }
          
        </View>
      }
    </ScrollView>
  )
}

export default InfoRuta

const styles = StyleSheet.create({
  header:{
    //flex:1
    //backgroundColor: 'red',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
  },

  rutaInfo:{
    flex: 1,
    
    backgroundColor: '#F3F5E8',

    margin: 30,
    // marginBottom: 0,
    padding: 15,
    borderRadius: 10,
    overflow: 'scroll'
  },

  dificultad:{
    marginTop: 10,
    borderRadius: 10, 
    borderStyle: 'solid', 
    borderWidth: 2, 

    height: 25,

    paddingHorizontal: 5
  },

  underline: {
    marginTop: 2,
    height: 2,
    width: '100%', // ancho de la raya
    backgroundColor: 'black',
    borderRadius: 2,
  },

  creador:{
    marginTop: 10,
  },

  contenedorMapa:{
    marginTop: 20, 
    //flex: 1,
    borderStyle: 'solid',
    borderWidth: 2,
    borderRadius: 5,
    height: 200
  },

  mapa:{
    flex: 1,
    
  },

  itemCargadoCard:{
    backgroundColor: '#00A676',
    borderRadius: 10, 
    padding: 10, 
    paddingBottom: 5,
    alignItems: 'center'
  },

  elmentCard:{

    alignSelf: 'flex-start',

    marginVertical: 10,
    marginRight: 15,
    paddingHorizontal: 10,

    borderWidth: 2, 
    borderRadius: 5,
    borderColor: '#00A676',
    backgroundColor: '#00A676',

    // width: 200,
    height: 50, 

    justifyContent: 'center',
  },
})