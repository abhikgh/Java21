package com.src.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SerializationSubsetEx implements Externalizable {

    private String name;
    private int age;
    private String department;
    private String address;
    private double salary;
    private String nonSerializedField;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
        out.writeObject(department);
        out.writeObject(address);
        out.writeDouble(salary);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = in.readInt();
        department = (String) in.readObject();
        address = (String) in.readObject();
        salary = in.readDouble();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", nonSerializedField='" + nonSerializedField + '\'' +
                '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerializationSubsetEx serializationSubsetEx = new SerializationSubsetEx("aa",10, "testD", "testA", 100.0,"test");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("a1"));
        objectOutputStream.writeObject(serializationSubsetEx);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("a1"));
        SerializationSubsetEx serializationSubsetExD = (SerializationSubsetEx) objectInputStream.readObject();
        objectInputStream.close();

        System.out.println(serializationSubsetExD);
        //Employee{name='aa', age=10, department='testD', address='testA', salary=100.0, nonSerializedField='null'}

    }


}
