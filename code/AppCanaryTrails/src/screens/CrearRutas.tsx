import { FlatList, Image, Modal, ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native'
import React, { useState } from 'react'
import { Dropdown } from 'react-native-element-dropdown';
import Icon from 'react-native-vector-icons/Ionicons';
import { Fauna, Flora, Municipio } from '../globals/Types';
import useMunicipios from '../hooks/useMunicipios';
import useFauna from '../hooks/useFauna';
import useFlora from '../hooks/useFlora';

type Props = {}

const CrearRutas = (props: Props) => {

  const [value, setValue] = useState(null);

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

  const renderItem = item => {
    return (
      <View style={{backgroundColor: '', height: 50, justifyContent: 'center', marginLeft: 10}}>
        <Text style={{}}>{item.label}</Text>
      </View>
    );
  };

  const handleOptionMunicipiosPress = (municipioNuevo) => {
    console.log('Seleccionaste:', municipioNuevo);
    if(!municipios.includes(municipioNuevo)){
      setmunicipios([...municipios, municipioNuevo])
    }
    setmunicipiosModalVisible(false);
  };

  const handleOptionFaunasPress = (faunaNueva) => {
    console.log('Seleccionaste:', faunaNueva);
    if(!faunas.includes(faunaNueva)){
      setfaunas([...faunas, faunaNueva])
    }
    setFaunasModalVisible(false);
  };

  const handleOptionFlorasPress = (floraNueva) => {
    console.log('Seleccionaste:', floraNueva);
    if(!floras.includes(floraNueva)){
      setfloras([...floras, floraNueva])
    }
    setFlorasModalVisible(false);
  };

  return (
    <ScrollView style={{flex: 1, backgroundColor: '#889584'}}>

      <View style={styles.rutaInfo}>

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
                value={value}
                onChange={item => {
                  setValue(item.value);
                }}
                
              />
            </View>

            <View style={[styles.inputContainer, {width: 100, height: 45}]}>
              <TextInput placeholder='Desnivel' keyboardType="numeric" onChangeText={(texto) => setdesnivel(parseInt(texto))} />
            </View>
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
                    keyExtractor={(item, index) => 'fauna ' + index}
                  />
                </View>
  
              </TouchableOpacity>
            </Modal>

            <View style={{flexDirection: 'row', flexWrap: 'wrap'}}>
              {municipios.map((option, index) => (
                <View key={index} style={styles.elmentCard}>
                  <TouchableOpacity>
                      <Text>{option.nombre}</Text>
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
                    keyExtractor={(item, index) => 'fauna ' + index}
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
                      <TouchableOpacity>
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
                    keyExtractor={(item, index) => 'fauna ' + index}
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
                      <TouchableOpacity>
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
              keyExtractor={(item, index) => 'fauna ' + index}
              horizontal={true}
            />
          </View>

        </View>

      </View>
    </ScrollView>
  )
}

export default CrearRutas

const styles = StyleSheet.create({

  rutaInfo:{
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
    borderColor: '#D9BF68',

    backgroundColor: '#D9BF68',
    
  },

  addButton:{
    borderWidth: 2,
    borderRadius: 10,
    paddingHorizontal: 10,
    borderColor: '#00A676',
    backgroundColor: '#00A676',

    height: 30,
    marginTop: 8,
    
  },

  overlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.2)',
    justifyContent: 'center',
    alignItems: 'center',
  },

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
    fontSize: 16 
  },

  elmentCard:{

    alignSelf: 'flex-start',

    marginVertical: 10,
    marginRight: 15,
    paddingHorizontal: 10,

    borderWidth: 2, 
    borderRadius: 5,

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
  }

})