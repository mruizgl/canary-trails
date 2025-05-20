import { Image, StyleSheet, Text, TouchableHighlight, TouchableOpacity, View } from 'react-native'
import React, { useContext, useEffect, useRef, useState } from 'react'
import { Comentario, Coordenada, CoordenadaMaps, Fauna, Flora, Municipio, RutaType, tokenPlayload, Usuario } from '../globals/Types'
import { useRoute } from '@react-navigation/native'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { RoutesStackParamList } from '../navigations/stack/SearchRoutesStack'
import MapView, { Marker, Polyline } from 'react-native-maps'
import { FlatList, TextInput } from 'react-native-gesture-handler'
import Icon from 'react-native-vector-icons/Ionicons';
import axios from 'axios'
import { useAppContext } from '../context/AppContext'
import { useJwt } from 'react-jwt'


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

  const {setUsuarioLogueado, usuarioLogueado} = useAppContext();
  const [coordenadasRuta, setcoordenadasRuta] = useState<Array<CoordenadaMaps>>([]);

  // const [rutaFavorita, setRutaFavorita] = useState<boolean>()

  // const [tituloComentario, settituloComentario] = useState("");
  // const [descripcion, setdescripcion] = useState("");

  const mapRef = useRef(null);

  useEffect(() => {
    console.log(ruta);
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

  useEffect(() => {
    if(decodedToken == null){
      return;
    }

    const nombre = decodedToken?.sub;

    async function obtenerUsuario(){

        try{
          const response = await axios.get(`http://10.0.2.2:8080/api/v2/usuarios/nombre/${nombre}`,
            {
              headers: {
                'Authorization': `Bearer ${context.token}`, // Token JWT
                'Content-Type': 'application/json', // Tipo de contenido
              }
            }
          );

          const usuario : Usuario = {
            id: response.data.id,
            nombre: response.data.nombre,
            correo: response.data.correo,
            password: response.data.password,
            foto: response.data.foto,
            rol: response.data.rol,
            fechaCreacion: response.data.fechaCreacion,
            faunas: response.data.faunas,
            floras: response.data.floras,
            rutas: response.data.rutas,
            comentarios: response.data.comentarios,
            rutasFavoritas: response.data.rutasFavoritas
          }

          setUsuarioLogueado(usuario);

        } catch (error) {
          console.log("An error has occurred aqui" +error.message);
        }

    }

    obtenerUsuario();
  }, [decodedToken])

  // useEffect(() => {

  //   if(usuarioLogueado == null){
  //     return;
  //   }

  //   async function obtenerFavoritas(){

  //     try{

  //       const response = await axios.get(`http://10.0.2.2:8080/api/v2/rutas_favoritas/${usuarioLogueado.id}`,
  //         {
  //           headers: {
  //             'Authorization': `Bearer ${context.token}`, // Token JWT
  //             'Content-Type': 'application/json', // Tipo de contenido
  //           }
  //         }
  //       );

  //       let favoritas : Array<RutaType> = response.data;
  //       let rutasAux : Array<RutaType> = []

  //       if(!(favoritas.length == 0)){
  //         rutasAux = favoritas.filter((rutaActual: RutaType) => rutaActual.id === ruta.id);
  //         if(rutasAux.length > 0){
  //           setRutaFavorita(true);
  //         }
  //       }

  //     } catch (error) {
  //       console.log("An error has occurred aqui" +error.message);
  //     }
  //   }

  //   obtenerFavoritas();
    
  // }, [usuarioLogueado])
  

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

  // function publicarComentario (){
  //   async function publicar (){
  //     try{

  //       const response = await axios.put(`http://10.0.2.2:8080/api/v2/comentarios/`, descripcion, 
  //         {
  //           headers: {
  //             'Authorization': `Bearer ${context.token}`, // Token JWT
  //             'Content-Type': 'application/json', // Tipo de contenido
  //           }
  //         }
  //       );
  
  
  //     } catch (error) {
  //       console.log("An error has occurred aqui" +error.message);
  //     }
  //   }

  //   publicar();
  // }

  // function aniadirFavoritas(){

  //   if(rutaFavorita){
        
  //   } else {
  //     aniadir();
  //   }

  //   async function aniadir (){

  //     try{

        

  //       const response = await axios.put(`http://10.0.2.2:8080/api/v2/rutas_favoritas/add`, 

  //         {idUsuario: usuarioLogueado.id,  idRuta: ruta.id} , 

  //         {
  //           headers: {
  //             'Authorization': `Bearer ${context.token}`, // Token JWT
  //             'Content-Type': 'application/json', // Tipo de contenido
  //           }
  //         }
  //       );
  
  //       if(response.data === true){
  //         //Para que se refresque el estado del icono
  //         let auxUsuario = usuarioLogueado;
  //         setUsuarioLogueado(auxUsuario);
  //         console.log("aniadido favs");
  //       }

  //     } catch (error) {
  //       console.log("An error has occurred aqui" +error.message);
  //     }
  //   }

  //   async function eliminar(){

  //   }


  // }

  

  return (
    <View style={{flex:1, backgroundColor: '#9D8DF1'}}>
      <View style={styles.rutaInfo}>

        <View style={styles.header}>
          <View style={{flexDirection: 'row', justifyContent: 'space-between', width: '100%'}}>

            <View style={{marginLeft: 3}}>
              <Text style={{fontSize: 25}}>{ruta.nombre}</Text>
            </View>
            {/* <View style={{marginTop: 2}}>
              <TouchableOpacity onPress={() => aniadirFavoritas()}>
                {
                  (rutaFavorita) ?
                  <Icon name={'heart-outline'} size={30} color={'red'} /> 
                  :
                  <Icon name={'heart'} size={30} color={'red'} /> 
                }
              </TouchableOpacity>
            </View> */}

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
              <Text>{distanciaKm} km</Text>
            </View>

            <View>
              <Text>Duracion: </Text>
              <Text>{horas}h {minutos} min </Text>
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
                    <Marker key={index} coordinate={coord} />
              ))}
          </MapView>
        </View>
        
        <View style={{flex: 1, marginTop: 20}}>
          <Text style={{fontSize: 16, fontWeight: 'bold'}}>Fauna: </Text>

          <View>
            <FlatList 
              data={ruta.faunas}
              renderItem={({ item, index }) => {
                  return (
                  <View style={{marginVertical: 10}}>
                      <TouchableOpacity>
                        <View>
                          <Image  
                            source={{ uri: 'http://10.0.2.2:8080/api/v1/imagenes/fauna/' + item.foto }}
                            style={{ width: 100, height: 80}}
                          />
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

        <View style={{flex: 1, marginTop: 30}}>
          <Text style={{fontSize: 16, fontWeight: 'bold'}}>Flora: </Text>

          <View>
            <FlatList 
              data={ruta.floras}
              renderItem={({ item, index }) => {
                  return (
                    <View style={{marginVertical: 10}}>
                        <TouchableOpacity>
                            <View>
                              <Image  
                                source={{ uri: 'http://10.0.2.2:8080/api/v1/imagenes/flora/' + item.foto }}
                                style={{ width: 100, height: 80}}
                              />
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

        {/* <View style={{flex: 1, marginTop: 30}}>
          <Text style={{fontSize: 16, fontWeight: 'bold'}}>Comentarios: </Text>

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
              <TextInput placeholder='Descripcion' 
                onChangeText={(texto) => {
                    settituloComentario(texto);
                }}
              />
              <View style={{borderWidth: 2, borderRadius: 100, height: 40, width: 40, justifyContent: 'center', alignSelf: 'center', paddingHorizontal: 10}}>
                <TouchableOpacity>
                  <Icon name={'send-outline'} size={20} color={'white'} /> 
                </TouchableOpacity>
              </View>
          </View>
          
          {
            ruta.comentarios.map((comentario, index) => (
              <View key={index}>
                <View style={{flexDirection: 'row'}}>
                  <Text style={{fontWeight: 'bold'}}>{comentario.usuario.nombre} </Text>
                  <Text>dice:</Text>
                </View>
                <Text>{comentario.descripcion}</Text>
              </View>
            ))
          }
          
        </View> */}
        
      </View>
    </View>
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
    
    backgroundColor: '#B8CDF8',

    margin: 30,
    //marginBottom: 0,
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

  comentario:{
    padding: 10,
    borderStyle: 'solid',
    borderWidth: 2,
    borderRadius: 5
  }
})