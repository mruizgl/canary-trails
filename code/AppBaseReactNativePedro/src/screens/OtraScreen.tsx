import { Alert, Button, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack';


import { PruebaStackParamList } from '../navigations/PruebaStack';




type PropsOtra = NativeStackScreenProps<PruebaStackParamList, 'Otra'>;
function OtraScreen({navigation,route}:PropsOtra) {

   




    
        


 



    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' ,marginTop: 36}}>
        <Text>otra screen</Text>
        

      
      </View>
    );
}


const styles = StyleSheet.create({})

export default OtraScreen;