import {
    Entity,
    PrimaryGeneratedColumn,
    Column,
    ManyToMany,
    JoinTable,
    BaseEntity,
    OneToMany,
} from "typeorm"



@Entity()
export class EjemploEntity extends BaseEntity {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({type:'text',unique:true}) nick: string;


}