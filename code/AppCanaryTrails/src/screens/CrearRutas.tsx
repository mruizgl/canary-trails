import { Alert, FlatList, Image, Modal, ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native'
import React, { useRef, useState } from 'react'
import { Dropdown } from 'react-native-element-dropdown';
import Icon from 'react-native-vector-icons/Ionicons';
import { Coordenada, CoordenadaCreateRuta, Fauna, Flora, Municipio, RutaType } from '../globals/Types';
import useMunicipios from '../hooks/useMunicipios';
import useFauna from '../hooks/useFauna';
import useFlora from '../hooks/useFlora';
import MapView, { Marker } from 'react-native-maps';
import useRutas from '../hooks/useRutas';
import useUsuario from '../hooks/useUsuario';

type Props = {}

const CrearRutas = (props: Props) => {

  const [valueDificultad, setValue] = useState(null);

  const data = [
    {label: 'Fácil', value: 'Fácil'},
    {label: 'Media', value: 'Media'},
    {label: 'Difícil', value: 'Difícil'},
    {label: 'Extrema', value: 'Extrema'},
  ];

  const [titulo, setTitulo] = useState("");
  const [distancia, setdistancia] = useState(0);
  const [duracion, setduracion] = useState(0);
  const [desnivel, setdesnivel] = useState(0);

  const {allMunicipios} = useMunicipios();
  const [municipios, setmunicipios] = useState<Array<Municipio>>([])
  const [municipiosModalVisible, setmunicipiosModalVisible] = useState(false)

  const {allFaunas} = useFauna();
  const [faunas, setfaunas] = useState<Array<Fauna>>([])
  const [faunasModalVisible, setFaunasModalVisible] = useState(false)

  const {allFloras} = useFlora();
  const [floras, setfloras] = useState<Array<Flora>>([])
  const [florasModalVisible, setFlorasModalVisible] = useState(false)

  const [marker, setMarker] = useState(null);
  const mapRef = useRef(null);
  
  const [allCoordenadas, setAllCoordenadas] = useState<Array<CoordenadaCreateRuta>>([])
  const [contador, setcontador] = useState(1)

  const {crearRuta} = useRutas();
  const {usuarioLogueado} = useUsuario();

  //Manda la señal de crear la ruta
  function createRuta(){

    if (!titulo || !valueDificultad || !distancia || !duracion || !desnivel || allCoordenadas.length < 2) {

      Alert.alert(
        "Error al crear la ruta",
        "Por favor, rellene todos los campos obligatorios y añada al menos dos coordenada.",
        [{ text: "Ok" }],
        { cancelable: false }
      );
      return;

    }
    
    const faunasAux = faunas.map(fauna => fauna.id);
    const florasAux = floras.map(flora => flora.id);
    const municipiosAux = municipios.map(municipio => municipio.id);

    const rutaNueva = {
      nombre: titulo,
      dificultad: valueDificultad,
      tiempoDuracion: duracion,
      distanciaMetros: distancia,
      desnivel: desnivel,
      aprobada: false,
      usuario: usuarioLogueado.id,
      faunas: faunasAux,
      floras: florasAux,
      coordenadas: allCoordenadas,
      municipios: municipiosAux
    }

    console.log(rutaNueva);
    const rutaCreada  = crearRuta(rutaNueva);
    console.log(rutaCreada);

    if(rutaCreada){
      Alert.alert(
        "Se ha creado la ruta",
        "Espere a que un administrador la acepte. Podrá verla en 'Perfil > Creaciones' hasta que sea aceptada",
        [{ text: "Ok" }],
        { cancelable: false }
      );
    } else {
      Alert.alert(
        "Error al crear la ruta",
        "Inténtelo de nuevo",
        [{ text: "Ok" }],
        { cancelable: false }
      );
    }
  }

  //Añade marcador a la lista
  function addMarker(){

    if (!marker) {
      return;
    }

    let latitude = marker.latitude.toFixed(6)
    let longitude = marker.longitude.toFixed(6)

    const coordenada : CoordenadaCreateRuta = {
      latitud: parseFloat(latitude),
      longitud: parseFloat(longitude)
    }

    // Verificar si ya existe una coordenada igual
    const yaExiste = allCoordenadas.some(coord =>
      coord.latitud === coordenada.latitud && coord.longitud === coordenada.longitud
    );

    if (yaExiste) {
      console.log('La coordenada ya existe, no se añade:', coordenada);
      return;
    }

    setAllCoordenadas([...allCoordenadas, coordenada]);
    console.log('Añadida la coordenada: ', coordenada);

    setcontador(contador+1);
  }

  //Elimina coordenada añadida al array
  function removeCoordenada(coordenadaEliminar : CoordenadaCreateRuta){

    const nuevasCoordenadas = allCoordenadas.filter(coord =>
      !(coord.latitud === coordenadaEliminar.latitud && coord.longitud === coordenadaEliminar.longitud)
    );

    setAllCoordenadas(nuevasCoordenadas);
  }

  //Controla el colocar un marcador en el mapa y aparezca la coordenada
  const handleMapPress = (event) => {
    const { coordinate } = event.nativeEvent;
    setMarker(coordinate);
  };


  //Controla opciones a añadir
  const handleOptionMunicipiosPress = (municipioNuevo) => {
    console.log('Seleccionaste:', municipioNuevo);
    if(!municipios.includes(municipioNuevo)){
      setmunicipios([...municipios, municipioNuevo])
    }
    setmunicipiosModalVisible(false);
  };

  function removeMunicipio(municipioRemove){
    const nuevosMunicipios = municipios.filter(municipio =>
      !(municipio === municipioRemove)
    );

    setmunicipios(nuevosMunicipios);
  }

  const handleOptionFaunasPress = (faunaNueva) => {
    console.log('Seleccionaste:', faunaNueva);
    if(!faunas.includes(faunaNueva)){
      setfaunas([...faunas, faunaNueva])
    }
    setFaunasModalVisible(false);
  };

  function removeFauna(faunaRemove){
    const nuevasFaunas = faunas.filter(fauna =>
      !(fauna === faunaRemove)
    );

    setfaunas(nuevasFaunas);
  }

  const handleOptionFlorasPress = (floraNueva) => {
    console.log('Seleccionaste:', floraNueva);
    if(!floras.includes(floraNueva)){
      setfloras([...floras, floraNueva])
    }
    setFlorasModalVisible(false);
  };

  function removeFlora(floraRemove){
    const nuevasFloras = floras.filter(flora =>
      !(flora === floraRemove)
    );

    setfloras(nuevasFloras);
  }


  //Item del Modal del dropdown
  const renderItem = item => {
    return (
      <View style={{backgroundColor: '', height: 50, justifyContent: 'center', marginLeft: 10}}>
        <Text style={{}}>{item.label}</Text>
      </View>
    );
  };

  return (
    <ScrollView style={{flex: 1, backgroundColor: '#889584'}}>

      <View style={styles.crearRuta}>

          <View>
            <Text style={{fontSize: 22, fontWeight: 'bold', alignSelf: 'center'}}>Crear Ruta</Text>
          </View>

          <Text style={{marginLeft: 5, marginTop: 25}}>Nombre: </Text>
          <View style={[styles.inputContainer]}>
            <TextInput placeholder='Ruta de ...' onChangeText={(texto) => setTitulo(texto)} />
          </View>

          <View style={{flexDirection: 'row'}}>
            <Text style={{marginLeft: 5, marginTop: 20, width: 167}}>Distancia (m) : </Text>
            <Text style={{marginLeft: 5, marginTop: 20}}>Duracion (min): </Text>
          </View>

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <View style={[styles.inputContainer, {width: 150}]}>
              <TextInput placeholder='Distancia' keyboardType="numeric" onChangeText={(texto) => setdistancia(parseInt(texto))} />
            </View>

            <View style={[styles.inputContainer, {width: 150}]}>
              <TextInput placeholder='Duracion' keyboardType="numeric" onChangeText={(texto) => setduracion(parseInt(texto))} />
            </View>
          </View>

          <View style={{flexDirection: 'row'}}>
            <Text style={{marginLeft: 5, marginTop: 20, width: 216}}>Dificultad: </Text>
            <Text style={{marginLeft: 5, marginTop: 20}}>Desnivel (m): </Text>
          </View>

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            
            <View style={[styles.inputContainer, {width: 200}]}>
              <Dropdown
                style={{paddingTop: 10, paddingHorizontal: 5}}
                renderItem={renderItem}
                data={data}
                maxHeight={300}
                labelField="label"
                valueField="value"
                placeholder={'Elija la dificultad'}
                value={valueDificultad}
                onChange={item => {
                  setValue(item.value);
                }}
                
              />
            </View>

            <View style={[styles.inputContainer, {width: 100, height: 45}]}>
              <TextInput placeholder='Desnivel' keyboardType="numeric" onChangeText={(texto) => setdesnivel(parseInt(texto))} />
            </View>
          </View>

          <View style={styles.container}>
            <MapView
              style={styles.map}
              ref={mapRef}
              onPress={handleMapPress}
              initialRegion={{
                latitude: 28.2789829,
                longitude: -16.5523792,
                latitudeDelta: 0.5,
                longitudeDelta: 0.5,
              }}
            >
              {marker && (
                <Marker
                  coordinate={marker}
                  title="Ubicación seleccionada"
                />
              )}
            </MapView>
              {marker && (
                <View style={styles.info}>
                  <Text>Latitud: {marker.latitude.toFixed(6)}</Text>
                  <Text>Longitud: {marker.longitude.toFixed(6)}</Text>
                </View>
              )}
          </View>

          <TouchableOpacity onPress={addMarker}>
            <View style={styles.addCoordenadas}>
              <Icon name={'add-outline'} size={25} color={'white'}/> 
            </View>
          </TouchableOpacity>

          <View style={{flexDirection: 'row', flexWrap: 'wrap', marginTop: 20}}>
            {allCoordenadas.map((coordenada, index) => (
              <View key={index} style={styles.coordAniadidas}>

                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                  <TouchableOpacity 
                    onPress={() => {

                      const region = {
                        latitude: coordenada.latitud,
                        longitude: coordenada.longitud,
                        latitudeDelta: 0.05,
                        longitudeDelta: 0.05,
                      };

                      const rememberMarker = {
                        latitude: coordenada.latitud,
                        longitude: coordenada.longitud,
                      }
                    
                      setMarker(rememberMarker);
                    
                      mapRef.current?.animateToRegion(region, 1000); //Mueve el mapa suavemente hacia la región de la coordenada
                    }}
                  >
                    <View>
                      <Text style={{color: 'white', fontSize: 16}}>{coordenada.latitud}</Text>
                      <Text style={{color: 'white', fontSize: 16}}>{coordenada.longitud}</Text>
                    </View>
                  </TouchableOpacity>
                  <TouchableOpacity onPress={()=>removeCoordenada(coordenada)}>
                    <View style={{marginLeft: 10}}>
                      <Icon name={'close-circle-outline'} size={30} color={'red'} />
                    </View>
                  </TouchableOpacity>
                </View>
                
              </View>
            ))}
          </View>

          <View>

            <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>

              <View style={{marginLeft: 5, marginTop: 25}}>
                <Text style={{fontSize: 20, fontWeight: 'bold'}}>Municipios:</Text>
              </View>

              <View style={{marginVertical: 18}}>
                <TouchableOpacity onPress={()=> setmunicipiosModalVisible(true)}>
                  <View style={styles.addButton}>
                    <Icon name={'add-outline'} size={25} color={'white'} /> 
                  </View>
                </TouchableOpacity>
              </View>

            </View>

            <Modal
              transparent
              visible={municipiosModalVisible}
              animationType="fade"
              onRequestClose={() => setmunicipiosModalVisible(false)}
            >
              <TouchableOpacity
                style={styles.overlay}
                onPress={() => setmunicipiosModalVisible(false)}
                activeOpacity={1}
              >
                <View style={styles.menu}>
                  <FlatList 
                    data={allMunicipios}
                    renderItem={({ item, index }) => {
                        return (
                          <TouchableOpacity
                            key={index}
                            onPress={() => handleOptionMunicipiosPress(item)}
                            style={styles.option}
                          >
                            <Text style={styles.optionText}>{item.nombre}</Text>
                        </TouchableOpacity>
                        )
                    }}
                    keyExtractor={(item, index) => 'municipio ' + index}
                  />
                </View>
  
              </TouchableOpacity>
            </Modal>

            <View style={{flexDirection: 'row', flexWrap: 'wrap'}}>
              {municipios.map((municipio, index) => (
                <View key={index} style={styles.elmentCard}>
                  <TouchableOpacity onPress={()=> removeMunicipio(municipio)}>
                      <Text style={{color: 'white', fontSize: 16}}>{municipio.nombre}</Text>
                  </TouchableOpacity>
                </View>
              ))}
            </View>

          </View>

        <View>

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <View style={{marginLeft: 5, marginTop: 5}}>
              <Text style={{fontSize: 20, fontWeight: 'bold'}}>Fauna:</Text>
            </View>

            <View style={{marginVertical: 0}}>
              <TouchableOpacity onPress={() => setFaunasModalVisible(true)}>
                <View style={styles.addButton}>
                  <Icon name={'add-outline'} size={25} color={'white'} /> 
                </View>
              </TouchableOpacity>
            </View>
          </View>

          <Modal
              transparent
              visible={faunasModalVisible}
              animationType="fade"
              onRequestClose={() => setFaunasModalVisible(false)}
            >
              <TouchableOpacity
                style={styles.overlay}
                onPress={() => setFaunasModalVisible(false)}
                activeOpacity={1}
              >
                <View style={styles.menu}>
                  <FlatList
                    data={allFaunas}
                    renderItem={({ item, index }) => {
                        return (
                          <TouchableOpacity
                            key={index}
                            onPress={() => handleOptionFaunasPress(item)}
                            style={styles.option}
                          >
                            <Text style={styles.optionText}>{item.nombre}</Text>
                        </TouchableOpacity>
                        )
                    }}
                    keyExtractor={(item, index) => 'faunaItem ' + index}
                  />
                </View>
  
              </TouchableOpacity>
            </Modal>

          <View>
            <FlatList 
              data={faunas}
              renderItem={({ item, index }) => {
                  return (
                  <View style={styles.cardFaunaFlora}>
                      <TouchableOpacity onPress={()=>removeFauna(item)}>
                        <Image  
                            source={{ uri: 'http://10.0.2.2:8080/api/v1/imagenes/fauna/' + item.foto }}
                            style={{ width: 100, height: 80, alignSelf: 'center', borderRadius: 5}}
                        />
                        <View>
                          <Text style={{alignSelf: 'center', marginTop: 5, color: 'white'}}>{item.nombre}</Text>
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

        <View>

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <View style={{marginLeft: 5, marginTop: 20}}>
              <Text style={{fontSize: 20, fontWeight: 'bold'}}>Flora:</Text>
            </View>

            <View style={{marginVertical: 15}}>
              <TouchableOpacity onPress={()=> setFlorasModalVisible(true)}>
                <View style={styles.addButton}>
                  <Icon name={'add-outline'} size={25} color={'white'} /> 
                </View>
              </TouchableOpacity>
            </View>
          </View>

          <Modal
              transparent
              visible={florasModalVisible}
              animationType="fade"
              onRequestClose={() => setFlorasModalVisible(false)}
            >
              <TouchableOpacity
                style={styles.overlay}
                onPress={() => setFlorasModalVisible(false)}
                activeOpacity={1}
              >
                <View style={styles.menu}>
                  <FlatList
                    data={allFloras}
                    renderItem={({ item, index }) => {
                        return (
                          <TouchableOpacity
                            key={index}
                            onPress={() => handleOptionFlorasPress(item)}
                            style={styles.option}
                          >
                            <Text style={styles.optionText}>{item.nombre}</Text>
                        </TouchableOpacity>
                        )
                    }}
                    keyExtractor={(item, index) => 'floraItem ' + index}
                  />
                </View>
  
              </TouchableOpacity>
            </Modal>

          <View>
            <FlatList 
              data={floras}
              renderItem={({ item, index }) => {
                  return (
                    <View style={styles.cardFaunaFlora}>
                      <TouchableOpacity onPress={()=>removeFlora(item)}>
                        <Image  
                          source={{ uri: 'http://10.0.2.2:8080/api/v1/imagenes/flora/' + item.foto }}
                          style={{ width: 100, height: 80, alignSelf: 'center', borderRadius: 5}}
                        />
                        <View >
                          <Text style={{alignSelf: 'center', marginTop: 5, color:'white'}}>{item.nombre}</Text>
                        </View>
                    </TouchableOpacity>
                </View>
                  )
              }}
              keyExtractor={(item, index) => 'flora ' + index}
              horizontal={true}
            />
          </View>


          <TouchableOpacity onPress={createRuta}>
            <View style={styles.createRuta}>
              <Icon name={'archive-outline'} size={25} color={'white'}/> 
            </View>
          </TouchableOpacity>

        </View>

      </View>
    </ScrollView>
  )
}

export default CrearRutas

const styles = StyleSheet.create({

  crearRuta:{
    flex: 1,
    
    backgroundColor: '#F3F5E8',

    margin: 30,
    // marginBottom: 0,
    padding: 15,
    paddingBottom: 25,
    
    borderRadius: 10,
    overflow: 'scroll',

    elevation: 20,
  },

  inputContainer:{
    marginTop: 10,

    borderWidth: 2,
    borderRadius: 10,
    borderColor: '#e4d49c',
    backgroundColor: '#e4d49c',
    
  },

  addButton:{
    borderWidth: 2,
    borderRadius: 10,
    paddingHorizontal: 10,
    // borderColor: '#00A676',
    // backgroundColor: '#00A676',
    borderColor: '#D9BF68',
    backgroundColor: '#D9BF68',

    height: 30,
    marginTop: 8,
    
  },

  //Overlay del modal
  overlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.2)',
    justifyContent: 'center',
    alignItems: 'center',
  },

  //Menú del modal
  menu: {
    backgroundColor: '#fff',
    borderRadius: 10,
    paddingVertical: 10,
    minWidth: 200,
    elevation: 5,
  },

  option: { 
    // padding: 10 ,
    marginHorizontal: 20,
    marginVertical: 10,
  },

  optionText: { 
    fontSize: 16 ,
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

  cardFaunaFlora:{
    borderWidth: 2,
    padding: 5,
    borderRadius: 5,
    borderColor: '#00A676',

    backgroundColor: '#00A676',

    marginRight: 10,
  },

  //Mapa
  container: { 
    overflow: 'hidden',
    borderWidth: 2,
    borderRadius: 10,
    marginTop: 30,
  },

  map: {
    height: 250, 
  },

  info: {
    position: 'absolute',
    bottom: 20,
    left: 10,
    backgroundColor: 'white',
    padding: 10,
    borderRadius: 8,
    elevation: 5,
  },

  addCoordenadas:{
    alignItems: 'center', 
    alignSelf: 'center',

    borderWidth: 2, 
    borderRadius: 10, 
    borderColor: '#D9BF68',
    backgroundColor: '#D9BF68',

    width: 150,

    marginTop: 15,
  },

  createRuta:{
    alignItems: 'center', 
    alignSelf: 'center',

    borderWidth: 2, 
    borderRadius: 10, 
    borderColor: '#D9BF68',
    backgroundColor: '#D9BF68',

    width: 150,
    height: 50,

    paddingVertical: 10,

    marginTop: 30,

  },

  coordAniadidas:{
    borderWidth: 2,
    borderRadius: 5, 

    marginBottom: 10,
    // alignSelf: 'flex-start',

    paddingLeft: 15,
    paddingRight: 10,
    paddingVertical: 5,
    marginRight: 15,

    borderColor: '#00A676',
    backgroundColor: '#00A676',
  }

})