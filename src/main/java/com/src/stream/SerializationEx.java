package com.src.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*

 Object -------> Sequence of Bytes, so that the object can be easily saved / streamed.
 The byte stream can then be deserialised , to get a copy of the Original object.

 Object -> byte stream -> File, DB, Memory -> byte stream -> Object

 Rules:

 1. An object is serializable only if its class/superclass implements the Serializable interface (else NotSerializableException)

 2. If Super class is not Serialized it must have a no-arg constructor.
    All Super class' no-arg constructors are run while deserialization. However , the class' constructor does not run.

 3. Generally, Super Class variables are serialized ,
    but if the variable is in Sub Class constructor then it is serialized if Super Class implements Serializable

 4. Serialized object can contain only Serializable objects , else NotSerializableException

 5. All primitive types are serializable.

 6. TRANSIENT, STATIC, @JsonIgnore fields are not serialized.

 7. Serialization performs deep copy of an Object.

 8. If only serialize certain fields use Externalizable interface.

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
      //super()/super(x..) is the first call in a constructor
      this.a = a;
      this.b = b;
      this.c = "test";
    }
   // r=100, a=aaa,b=bbb, c=null

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
