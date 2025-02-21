
import {DataSource} from 'typeorm';

import { EjemploEntity } from './entity/EjemploEntity';



export const dataSource = new DataSource({
  database: 'ejemplodb.db',
  entities: [
    EjemploEntity
  ],
  location: 'default',
  //logging: [],
  logging: ['error', 'query', 'schema'],
  synchronize: true,
 
  type: 'react-native',
});


export const EjemploRepository = dataSource.getRepository(EjemploEntity);









