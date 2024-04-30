import { Food } from "./food";

export class User{
    id!:string;
    email!:string;
    name!:string;
    address!:string;
    token!:string;
    favFood!:[Food]
    roles!:[any] // ROLE_ADMIN
}