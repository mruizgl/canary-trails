import { FlatList, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native'
import React, { useState } from 'react'
import DropDownPicker from 'react-native-dropdown-picker';
import Icon from 'react-native-vector-icons/Ionicons';

type Props = {}

const CrearRutas = (props: Props) => {

  const [open, setOpen] = useState(false)
  const [value, setValue] = useState(null)
  const [items, setItems] = useState([
    {label: 'Fácil', value: 'Fácil'},
    {label: 'Media', value: 'Media'},
    {label: 'Difícil', value: 'Difícil'},
    {label: 'Extrema', value: 'Extrema'},
  ])

  const [titulo, setTitulo] = useState("");
  const [distancia, setdistancia] = useState(0);
  const [duracion, setduracion] = useState(0);
  const [desnivel, setdesnivel] = useState(0);

  const [municipios, setmunicipios] = useState([])
  const [faunas, setfaunas] = useState([])
  const [flora, setflora] = useState([])

  return (
    <View style={{flex: 1, backgroundColor: '#889584'}}>

        <View style={styles.rutaInfo}>

          <View style={styles.inputContainer}>
            <TextInput placeholder='nombre' onChangeText={(texto) => setTitulo(texto)} />
          </View>

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <View style={[styles.inputContainer, {width: 150}]}>
              <TextInput placeholder='distancia' onChangeText={(texto) => setdistancia(parseInt(texto))} />
            </View>

            <View style={[styles.inputContainer, {width: 150}]}>
              <TextInput placeholder='duracion' onChangeText={(texto) => setduracion(parseInt(texto))} />
            </View>
          </View>

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            
            <View style={[styles.inputContainer, {width: 200}]}>
              <DropDownPicker 
                  open={open}
                  value={value}
                  items={items}
                  setOpen={setOpen}
                  setValue={setValue}
                  setItems={setItems}
                  placeholder="Seleccione la dificultad"
                  maxHeight={100}
                />
            </View>

            <View style={[styles.inputContainer, {width: 100, height: 45}]}>
              <TextInput placeholder='desnivel' onChangeText={(texto) => setdesnivel(parseInt(texto))} />
            </View>
          </View>

          <View style={{height: 200}}>
            <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
              <View style={{marginLeft: 5, marginTop: 20}}>
                <Text style={{fontSize: 20, fontWeight: 'bold'}}>Municipios:</Text>
              </View>
              <View style={{marginVertical: 15}}>
                <TouchableOpacity>
                  <View style={styles.addButton}>
                    <Icon name={'add-outline'} size={25} color={'white'} /> 
                  </View>
                </TouchableOpacity>
              </View>
            </View>

            <View>
              <FlatList 
                data={municipios}
                renderItem={({ item, index }) => {
                    return (
                    <View style={{marginVertical: 10}}>
                        <TouchableOpacity>
                          <View>
                            <Text>Algo</Text>
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

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <View style={{marginLeft: 5, marginTop: 20}}>
              <Text style={{fontSize: 20, fontWeight: 'bold'}}>Fauna:</Text>
            </View>
            <View style={{marginVertical: 15}}>
              <TouchableOpacity>
                <View style={styles.addButton}>
                  <Icon name={'add-outline'} size={25} color={'white'} /> 
                </View>
              </TouchableOpacity>
            </View>
          </View>

          <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <View style={{marginLeft: 5, marginTop: 20}}>
              <Text style={{fontSize: 20, fontWeight: 'bold'}}>Flora:</Text>
            </View>
            <View style={{marginVertical: 15}}>
              <TouchableOpacity>
                <View style={styles.addButton}>
                  <Icon name={'add-outline'} size={25} color={'white'} /> 
                </View>
              </TouchableOpacity>
            </View>
          </View>

        </View>
    </View>
  )
}

export default CrearRutas

const styles = StyleSheet.create({

  rutaInfo:{
    flex: 1,
    
    backgroundColor: '#F3F5E8',

    margin: 30,
    //marginBottom: 0,
    padding: 15,
    borderRadius: 10,
    overflow: 'scroll',

    elevation: 20,
  },

  inputContainer:{
    marginTop: 20,

    borderWidth: 2,
    borderRadius: 10,
    borderColor: '#bab8b8',

    backgroundColor: '#EAEAEA',
    
  },

  addButton:{
    borderWidth: 2,
    borderRadius: 10,
    paddingHorizontal: 10,
    borderColor: '#33d17a',
    backgroundColor: '#57e389',

    height: 30,
    marginTop: 8,
    
  },

  input:{

  }
})