## What is jPopulator ?

jPopulator is a java tool that allows you to populate java beans with random data. This can be very useful in development/test phases when you need to generate fake data.
jPopulator can be very handy when your java beans type hierarchy is deep, since it populates recursively all the nested types of a given java bean.
Let's see an example, suppose you have the following classes:

![class diagram](https://github.com/benas/jPopulator/raw/master/src/site/resources/person.png)

If you want to populate a `Person` bean "by hand", you would write the following code:

```java
Street street = new Street(12, (byte) 1, "Oxford street");
Address address = new Address(street, "123456", "London", "United Kingdom");
Person person = new Person("Foo", "Bar", "foo.bar@gmail.com" ,Gender.MALE, address);
```

If these classes do not provide constructors with parameters (may be some legacy beans you don't have the control over), you would write:

```java
Street street = new Street();
street.setNumber(12);
street.setType((byte) 1);
street.setName("Oxford street");

Address address = new Address();
address.setStreet(street);
address.setZipCode("123456");
address.setCity("London");
address.setCountry("United Kingdom");

Person person = new Person();
person.setFirstName("Foo");
person.setLastName("Bar");
person.setEmail("foo.bar@gmail.com");
person.setGender(Gender.MALE);
person.setAddress(address);
```

With jPopulator, you simply write this:

```java
Populator populator = new PopulatorBuilder().build();
Person person = (Person) populator.populateBean(Person.class);
```

And voila! jPopulator will introspect the `Person` type hierarchy, generate an instance for each nested bean and populate it with random data.
You can also generate a random or fixed number of instances using the following APIs in the `Populator` interface:

```java
public interface Populator {

    /**
     * Populate a java bean instance for the given type.
     *
     * @param type the type for which a java bean instance will be populated
     * @return a populated instance of the given type
     */
    Object populateBean(Class type);

    /**
     * Populate a random number of java bean instances for the given type.
     *
     * @param type the type for which java bean instances will be populated
     * @return a list of populated instances of the given type
     */
    List<Object> populateBeans(Class type);

    /**
     * Populate a fixed number of java bean instances for the given type.
     *
     * @param type the type for which java bean instances will be populated
     * @param size the number of instances to populate
     * @return a list of populated instances of the given type
     */
    List<Object> populateBeans(Class type, int size);

}
```

## Controlling the randomness

By default, jPopulator generates random values according to field type. For example, the `String` field named `firstName` of the `Person` class will be set to a random String, say `dfrtgyhu`,
which is completely meaningless since generated randomly.

You may want to generate random data that would be meaningful according to the field it is assigned to.
For instance, how to tell jPopulator that the `firstName` field is actually a first name and its value should be randomly generated from the list `{"John","Brad","Tommy"}` ?

This is where randomizers come to play. A randomizer is an implementation of the `Randomizer<T>` interface that controls how to generate random values for the type T :

```java
public interface Randomizer<T> {

    /**
     * Generate a random value for the given type.
     *
     * @return a random value for the given type
     */
    public T getRandomValue();

}
```

Let's continue with the previous example. The following is a custom randomizer implementation that generates first names from a resource bundle :

```java
public class FirstNameRandomizer implements Randomizer<String> {

    Random random;

    /**
     * First names array from which the random value should be generated.
     */
    private String[] firstNames;

    public FirstNameRandomizer() {
        this.random = new Random();
        this.firstNames = ResourceBundle.getBundle("net/benas/jpopulator/data/data").getString("firstNames").split(",");
    }

    @Override
    public String getRandomValue() {
        return firstNames[random.nextInt(firstNames.length)];
    }

}
```

Now, to register this custom randomizer within jPopulator, you can use the `PopulatorBuilder` API as follows:

```java
Populator populator = new PopulatorBuilder()
                        .registerRandomizer(Person.class, String.class, "firstName", new FirstNameRandomizer())
                        .build();
```

This tells jPopulator to generate random value for the `String` field named `firstName` declared in the `Person` class using the `FirstNameRandomizer`.

jPopulator comes with several built-in randomizers for commom attribures such as first name, last name, email, city, country, etc.
These randomizers can be found in the `net.benas.jpopulator.randomizers` package.
Of course, as we have just seen, you can provide your custom randomizers and use them with jPopulator.

## I18N Support for random values

jPopulator also supports internationalized values for the built-in randomizers since it loads them from the resource bundle `net/benas/jpopulator/data/data.properties`.
This means you can simply put a `data_xx_YY.properties` with your locale in the the package `net/benas/jpopulator/data` of your classpath and jPopulator will use it automatically.

## Supported types

### Java types
By default, jPopulator can generate random values for all Java built-in types (primitive and boxed) : int, long, String, Boolean, etc

Support for third party types (Joda time, Apache common, etc) is planned for a future release. Any contribution is welcome!

### Enumeration types

Enumeration types are also supported. In the example above, jPopulator generated random values for enum type `Gender`.
jPopulator will pick up a random value from the enumeration values set and set it to the field of the enumeration type.

### Arrays and Collection types
jPopulator can also handle arrays and collection types defined in the [Java Collections Framework][] (Set, List, Map, etc).

For the moment, jPopulator generates empty arrays and collections. Populating collections is also planned for a future release. Any contribution is welcome!

## Getting started

jPopulator tool is a single jar file with no dependencies. To build jPopulator from sources, you need to have maven installed and set up.

To use jPopulator, please follow these instructions :

 * $>`git clone https://github.com/benas/jPopulator.git`

 * $>`mvn package`

 * Add the generated jar `target/jpopulator-${version}.jar` to your application's classpath

If you use Maven, you should build/install the jar to your local Maven repository and add the following dependency to your pom.xml :
```xml
<dependency>
    <groupId>net.benas</groupId>
    <artifactId>jpopulator</artifactId>
    <version>${version}</version>
</dependency>
```

## License
jPopulator is released under the [MIT License][].

[Java Collections Framework]: http://docs.oracle.com/javase/tutorial/collections/
[MIT License]: http://opensource.org/licenses/mit-license.php/