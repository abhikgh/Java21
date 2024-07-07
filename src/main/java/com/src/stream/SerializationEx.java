package com.src.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*

 Object -> byte stream -> File, DB, Memory -> byte stream -> Object

 An object is serializable only if its class/superclass implements the Serializable interface.
 If Super class is not Serialized it must have a no-arg constructor.
 Serialization performs Deep Clone of an Object.
 transient / @JsonIgnore - to not make a variable Serializable
 During Deserialization if superclass does not implement Serializable then its default constructor will run to create the object

 */
class SuperClass {

    //no-args constructor has to be present for non-serialized superclass, because it will run during deserialization
    int r = 10;
    String a = "abc";

    SuperClass(){
        r = 100;
        a = "aaaaaa";
    }

}


public class SerializationEx extends SuperClass implements Serializable {
    private String b;
    private transient String c;

    public SerializationEx(String a, String b) {
        //default :: super()
        //super() constructor (with/without arguments)  must be the first line in the child class constructor - if its there, fine, else will be added by the compiler
        super();
      this.a = a;
      this.b = b;
      this.c = "test";
    }
   // r=100, a=aaaaaa,b=bbb, c=null

    public static void main(String[] args) {
        SerializationEx serializationEx = new SerializationEx("aaa", "bbb");

        try{
            //serialize
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("a1"));
            objectOutputStream.writeObject(serializationEx);
            objectOutputStream.close();

            //de-serialize
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("a1"));
            SerializationEx serializationExDS = (SerializationEx) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println(serializationExDS.r +"::"+ serializationExDS.a + "::" + serializationExDS.b + "::" +  serializationExDS.c);
            // r=100, a=aaaaaa,b=bbb, c=null

            //if superclass implements Serializable
            // r=100, a=aaa,b=bbb, c=null


        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
