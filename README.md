# JAVA_CONTENT

Code Academy work
Notes

## Serializing Objects to a File

14 min
Now that we’ve learned about the Serializable interface and how to implement it, let’s take a look at how to serialize an object to a file. To do this we’ll need to use the helper classes, FileOutputStream, which will help us write to a file, and ObjectOutputStream, which will help us write a serializable object to an output stream.

Let’s look at how to do this with some code:

public class Person implements Serializable {
  private String name;
  private int age;
  private static final long serialVersionUID = 1L;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }  

  public static void main(String[] args) throws FileNotFoundException, IOException{
    Person michael = new Person("Michael", 26);
    Person peter = new Person("Peter", 37);

    FileOutputStream fileOutputStream = new FileOutputStream("persons.txt");
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        
    objectOutputStream.writeObject(michael);
    objectOutputStream.writeObject(peter);  
  }
}

In the example above we:

Added a constructor to the Person class we defined in the previous lesson.
Defined main() and initialized two Person objects - michael and peter.
Initialized a FileOutputStream object in main() which will create and write a stream of bytes to a file named persons.txt.
Initialized an ObjectOutputStream object in main() which will help serialize an object to a specified output stream.
Used objectOutputStream.writeObject() in main() to serialize the michael and peter objects to a file.
After the execution of the above program, the persons.txt will contain a stream of bytes that has the type and value information of the fields for the michael and peter objects respectively.

## Deserializing an Object to a File

19 min
Knowing how to serialize our objects is great, but it becomes more useful when we can turn serialized objects back into Java objects with deserialization. As the name suggests, deserialization does the opposite of serialization and transforms a stream of bytes into a Java object.

Like with serialization, we’ll make use of helper classes FileInputStream, which helps us read a file, and ObjectInputStream which helps us read a serialized stream of bytes.

Assuming we have the file persons.txt we created in the last exercise, let’s understand how to do this by looking at some code:

public class Person implements Serializable {
  public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

    FileInputStream fileInputStream = new FileInputStream("persons.txt");
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        
    Person michaelCopy = (Person) objectInputStream.readObject();
    Person peterCopy = (Person) objectInputStream.readObject();
  }
}

In the example above we:

Initialized FileInputStream and ObjectInputStream in main() which will read a file and transform a stream of bytes into a Java object.
Created a Person object named michaelCopy by using objectInputStream.readObject() to read a serialized object.
Created a Person object named peterCopy by using objectInputStream.readObject() to read a serialized object.
It’s important to note that deserialization creates a copy of the original object. This means that the deserialized object shares the same field values as the original object but is located in a different place in memory. Also, The deserialized objects will be in the order that they were serialized in and we need to explicitly typecast the readObject() return value back into the type of object we serialized.

The JVM ensures it deserializes the object using the correct class file by comparing the serialVersionUID in the class file with the one in the serialized object. If a match is not found an InvalidClassException is thrown. Also, readObject() throws a ClassNotFoundException when the class of the serialized object cannot be found.

Let’s practice deserializing objects from a file.

## Serializable Fields

10 min
When serializing objects, we often need to handle static class fields or exclude non-static fields in the serialization. Recall that the JVM defines a default way of serializing objects; this default includes ignoring static class fields, which belong to a class and not an object. The JVM also serializes all fields in an object, even those marked private and final.

Although the JVM implicitly serializes non-static fields, we can instruct the JVM to ignore them using the transient keyword. A field marked as transient will have its value ignored during serialization and instead receive the default value for that type of field.

Let’s visualize this with some code:

public class Person implements Serializable {
  private String name;
  private int age;
  private static int numberOfPeople = 10;
  private transient int yearBorn;  
}

In the example above, we defined a static field called numberOfPeople with a value of 10. Since it belongs to the class, it will not be included in the serialization process. We also defined a transient field called yearBorn of type int. The JVM will ignore the initialized value of this field and instead serialize the field with its default value for its type (0 in this case).

Let’s discuss some interesting aspects of deserializing objects with transient and static fields:

The deserialized (copy) object will not get the default value for a static field (in our example the value 0). Since a static field belongs to the class and not the object, a deserialized static field will receive the value it corresponds to in the current class.
A constructor is not called during deserialization for the deserialized type object. Object fields are set using reflection.
A constructor is only called for the first non-serializable class in the parent hierarchy of the deserialized object.
We often want to serialize all non-static fields in an object. However, sometimes we may need to serialize transient fields if a field’s value is dependent on other fields or, more importantly, if we have a reference field that is not serializable (we’ll talk about this later).

Let’s practice using different types of serializable and non-serializable fields.

## Custom Serialization

18 min
As we’ve learned about serialization, we’ve discussed how the JVM defines a default way to serialize objects when their classes implement the Serializable interface. Can we modify this default process? We can by implementing the methods readObject() and writeObject() in our class!

Let’s look at some code that implements readObject() and writeObject():

public class DateOfBirth {
  private int month;
  private int day;
  private int year;

  // constructors and getters
}

public class Person implements Serializable {
  private String name;
  private DateOfBirth dateOfBirth;

  private void writeObject(java.io.ObjectOutputStream stream) throws IOException{
    stream.writeObject(this.name);
    stream.writeInt(this.dateOfBirth.getMonth());
    stream.writeInt(this.dateOfBirth.getDay());
    stream.writeInt(this.dateOfBirth.getYear());
  }

  private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
    this.name = (String) stream.readObject();
    int month = (int) stream.readInt();
    int day = (int) stream.readInt();
    int year = (int) stream.readInt();
    this.dateOfBirth = new DateOfBirth(month, day, year);
  }
}

In the example above:

We have two classes: Person which implements Serializable and DateOfBirth which does not.
Person has a reference field of type DateOfBirth.
If we were to use the default serialization process, we would get a NotSerializableException because DateOfBirth does not implement Serializable.
We implement writeObject(), which must throw IOException, to serialize a DateOfBirth object by manually serializing all of its fields separately. We also serialize the serializable String field.
We implement readObject(), which must throw IOException and ClassNotFoundException, to deserialize a Person object by reading the int fields that are a part of DateOfBirth and creating a new DateOfBirth object with the provided constructor. This new object is used to set the dateOfBirth field in Person.
Often, the default process of serialization is enough as long as all references implement Serializable. The implementation of readObject() and writeObject() is useful when we have a reference field that does not or cannot implement Serializable. We could also potentially handle static field values if we needed to persist them. This, however, is not good practice as a static field should belong to a class and not an object.

Let’s practice implementing our custom serialization and deserialization methods.
