package com.nju.edu.serialization;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class PersonTest {

    @Test 
    public void whenSerializingAndDeserializing_ThenObjectIsTheSame() throws IOException, ClassNotFoundException { 
    Person person = new Person();
    person.setAge(20);
    person.setName("Joe");
    FileOutputStream fileOutputStream = new FileOutputStream("yourfile.txt");
    ObjectOutputStream objectOutputStream  = new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(person);
    objectOutputStream.flush();
    objectOutputStream.close();
    FileInputStream fileInputStream = new FileInputStream("yourfile.txt");
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
    Person p2 = (Person) objectInputStream.readObject();
    objectInputStream.close(); 

    assertTrue(p2.getAge() == person.getAge());
    assertTrue(p2.getName().equals(person.getName()));
}
    
}
