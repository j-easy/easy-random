# What is Random Beans ?

Random Beans is a library that generates random Java beans. Let's say you have a class `Person` and you want to generate a random instance of it, here we go:

```java
Person person = random(Person.class);
```

The static method `EnhancedRandom.random` is able to generate random instances of a given type.

Let's see another example. If you want to generate a random stream of 10 persons, you can use the following snippet:

```java
Stream<Person> persons = randomStreamOf(10, Person.class);
```

This second static method of the `EnhancedRandom` API generates a stream of random instances of a given type.

# What is this EnhancedRandom API?

The `java.util.Random` API provides 7 methods to generate random data: `nextInt()`, `nextLong()`, `nextDouble()`, `nextFloat()`, `nextBytes()`, `nextBoolean()` and `nextGaussian()`.
What if you need to generate a random `String`? Or say a random instance of your domain object?
Random Beans provides the `EnhancedRandom` API that extends (enhances) `java.util.Random` with a method called `nextObject(Class type)`.
This method is able to generate a random instance of any arbitrary Java bean:

```java
EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
Person person = enhancedRandom.nextObject(Person.class);
```

The `EnhancedRandomBuilder` is the main entry point to configure `EnhancedRandom` instances. It allows you to set all
parameters to control how random data is generated:

```java
EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
   .seed(123L)
   .objectPoolSize(100)
   .randomizationDepth(3)
   .charset(forName("UTF-8"))
   .timeRange(nine, five)
   .dateRange(today, tomorrow)
   .stringLengthRange(5, 50)
   .collectionSizeRange(1, 10)
   .scanClasspathForConcreteTypes(true)
   .overrideDefaultInitialization(false)
   .build();
```

For more details about these parameters, please refer to the [configuration parameters](https://github.com/benas/random-beans/wiki/Randomization-parameters) section.

In most cases, default options are enough and you can use a static import of the `EnhancedRandom.random(Class object)` as seen previously.

Random beans allows you to control how to generate random data through the `Randomizer` interface
 and makes it easy to exclude some fields from the object graph using the fluent `FieldDefinition` API:

```java
EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
   .randomize(String.class, (Randomizer<String>) () -> "foo")
   .exclude(field().named("age").ofType(Integer.class).inClass(Person.class).get())
   .build();

Person person = enhancedRandom.nextObject(Person.class);
```

# Why Random Beans ?

Populating a Java object with random data can look easy at first glance, unless your domain model involves many related classes.
In the previous example, let's suppose the `Person` type is defined as follows:

![](https://github.com/benas/random-beans/raw/master/site/person.png)

**Without** Random Beans, you would write the following code in order to create an instance of the `Person` class:

```java
Street street = new Street(12, (byte) 1, "Oxford street");
Address address = new Address(street, "123456", "London", "United Kingdom");
Person person = new Person("Foo", "Bar", "foo.bar@gmail.com", Gender.MALE, address);
```

And if these classes do not provide constructors with parameters (may be some legacy beans you don't have the control over), you would write:

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

With Random Beans, generating a random `Person` object is done with `random(Person.class)`. The library will **recursively** populate
all the object graph. That's a big difference!

## Quick links

|Item                  |Link                                                                                      |
|:---------------------|:-----------------------------------------------------------------------------------------|
|Documentation         | [https://github.com/benas/random-beans/wiki](https://github.com/benas/random-beans/wiki) |
|Agile Board           | [Backlog items @ waffle.io](https://waffle.io/benas/random-beans)                        |
|Continuous integration| [![Build Status](https://travis-ci.org/benas/random-beans.svg?branch=master)](https://travis-ci.org/benas/random-beans) |
|Code coverage         | [![Coverage Status](https://coveralls.io/repos/benas/random-beans/badge.svg?branch=master&service=github)](https://coveralls.io/github/benas/random-beans?branch=master) |
|Dependencies          | [![Dependency Status](https://www.versioneye.com/user/projects/56c6d7fa19f173000c237adc/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56c6d7fa19f173000c237adc) |
|Current version       | [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.benas/random-beans/badge.svg?style=flat)](http://repo1.maven.org/maven2/io/github/benas/random-beans/3.6.0/) |
|License               | [![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT) |

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you believe you found a bug, please use the [issue tracker](https://github.com/benas/random-beans/issues).

If you have any question, suggestion, or feedback, do not hesitate to use the [Gitter channel](https://gitter.im/benas/random-beans) of the project.

## Core team and contributors

#### Core team

* [Mahmoud Ben Hassine](https://github.com/benas)
* [Pascal Schumacher](https://github.com/PascalSchumacher)

#### Awesome contributors

* [Alberto Lagna](https://github.com/alagna)
* [Adriano Machado](https://github.com/ammachado)
* [Dovid Kopel](https://github.com/dovidkopel)
* [Eric Taix](https://github.com/eric-taix)
* [Fred Eckertson](https://github.com/feckertson)
* [Jose Manuel Prieto](https://github.com/prietopa)
* [kermit-the-frog](https://github.com/kermit-the-frog)
* [Nikola Milivojevic](https://github.com/dziga)
* [Oleksandr Shcherbyna](https://github.com/sansherbina)
* [Rebecca McQuary](https://github.com/rmcquary)
* [Rémi Alvergnat](http://www.pragmasphere.com)
* [Rodrigue Alcazar](https://github.com/rodriguealcazar)
* [Valters Vingolds](https://github.com/valters)
* [Vincent Potucek](https://github.com/punkratz312)
* [Lucas Andersson](https://github.com/LucasAndersson)
* [euZebe](https://github.com/euzebe)
* [Petromir Dzhunev](https://github.com/petromir)

Thank you all for your contributions!
