# Classes

19 min
Previously, we saw how generics can help make our code more manageable and flexible for future needs. We created the following generic class:
public class Box <T> {
        private T data;

        public Box(T data) {
            this.data = data; 
        }

        public T getData() {
            return this.data;
        }  
}

In the example above, notice that:

The type parameter must be specified within the diamond operator (<>) after the class name.
The type parameter, T, is similar to a method parameter but instead receives a class or interface type as an argument as opposed to a reference or primitive type.
The constructor accepts a T-type parameter to initialize data.
The getter method returns the type parameter T when returning data.
Creating generic classes is great, but we also need to be able to create instantiations of them in our programs:

Box<String> myStringBox = new Box<>("Apple");

In the example above, the object myStringBox is created like a non-generic object, but differs in:

Needing the diamond operator with the class or interface type argument, <String> in this example, after the class name.
Needing the empty diamond operator before calling the constructor new Box<>("Apple").
When defining type parameters, although not being a hard requirement, it’s best practice to make them single, uppercase letters to easily distinguish them from the names of classes or variables. By convention, type parameters are E (Elements), K (Key), N (Number), T (Type), V (Value), and S (or U or V) for multiple type parameters.

One last thing to note is that before Java 7, generic references had to be created like this:

Box<String> myStringBox = new Box<String>("Apple");

In the example above, the <String> type argument also needed to be specified prior to calling the constructor. This is no longer necessary due to Java’s type inference where the compiler can infer the <String> type argument in the constructor from the context of the reference definition Box<String>.

## Wrapper Classes

4 min
Generics allow our programs to adapt to different data type needs but one issue with them is that we cannot use primitive types (int, double, boolean, etc) as arguments to generic type parameters. For example, we cannot create a Box of integers this way:

Box<int> intBox = new Box<>(56);

Fortunately, Java provides wrapper classes to allow us to create objects of primitive types and use them as type parameters. We can now create a Box of integers this way:

Box<Integer> intBox = new Box<>(56);

In the example above, the Integer wrapper class is used in place of int to work as a type argument. Also, notice that we are able to pass 56 as the argument to the constructor and this is because of autoboxing.

Autoboxing allows wrapper classes to take primitive values and convert them to their corresponding wrapper object by automatically calling the valueOf() method. For example, the following statements are equivalent when creating a Box<Integer>:

Integer a = 56;  // Autoboxing, automatic call to `valueOf()`
Box<Integer> intBox1 = new Box<>(a);
Box<Integer> intBox2 = new Box<>(56);  // Autoboxing, automatic call to `valueOf()`
Box<Integer> intBox3 = new Box<>(Integer.valueOf(56));

We can also take the wrapper object and convert it back to its primitive value using one of the wrapper object’s Value() methods. This process is also automated for us and is known as unboxing. For example:

Integer a = 56;
int aPrimitive1 = a.intValue();  // Return primitive `int` value from `Integer` object
int aPrimitive2 = a;  // Unboxing, automatic call to `intValue()`

## Interfaces

27 min
We’ve seen how to create a generic class, but we can also create a generic interface. Let’s look at an example:

public interface Replacer<T> {
  void replace(T data);
}

The generic interface Replacer is created like a generic class where the type parameter <T> must be appended to the interface name. Interface method declarations are similar to non-generic interfaces and can include non-generic methods as well.

A generic interface can be implemented by a generic class and its generic type parameter can be used as the argument to the interface type parameter. For example, let’s have our Box generic class implement the interface Replacer:

public class Box <T> implements Replacer<T> {
  private T data;
  
  @Override
  void replace(T data) {
    this.data = data; 
  }
}

In the example above, the Box type parameter <T> will be used as the type argument for the Replacer type parameter <T>.

We can also have a non-generic class implement a generic interface by specifying the type argument to the interface. For example:

public class StringBag implements Replacer<String> {
  private String data;
  
  @Override
  void replace(String data) {
    this.data = data; 
  }
}

In the example above, the StringBag is a non-generic class that implements Replacer and provides String as the argument to the type parameter. Notice that the replace() parameter data has a String type as opposed to the generic T in the previous example.

Now let’s create interface type references similarly to how we created generic class references:

Replacer<Integer> boxReplacer = new Box<>();  // Using generic `Box` implementation
Replacer<String> bagReplacer = new StringBag();  // Using non-generic `StringBag` implementation

In the example above we created two Replacer references. The Box implementation can be of any type but the StringBag implementation needs to be a <String> type because of the non-generic class implementation.
