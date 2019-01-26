***

<div align="center">
    <b><em>Random Beans</em></b><br>
    Because life is too short to generate random Java&trade; beans by hand..
</div>

<div align="center">

[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT)
[![Coverage Status](https://coveralls.io/repos/benas/random-beans/badge.svg?branch=master&service=github)](https://coveralls.io/github/benas/random-beans?branch=master)
[![Build Status](https://travis-ci.org/benas/random-beans.svg?branch=master)](https://travis-ci.org/benas/random-beans)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.benas/random-beans/badge.svg?style=flat)](http://repo1.maven.org/maven2/io/github/benas/random-beans/3.7.0/)
[![Javadocs](http://www.javadoc.io/badge/io.github.benas/random-beans.svg)](http://www.javadoc.io/doc/io.github.benas/random-beans)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/benas/random-beans)
[![Code Quality: Java](https://img.shields.io/lgtm/grade/java/g/benas/random-beans.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/benas/random-beans/context:java)
[![Total Alerts](https://img.shields.io/lgtm/alerts/g/benas/random-beans.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/benas/random-beans/alerts)

</div>

***

## Latest news

* 19/06/2017: Version 3.7.0 is released. Checkout what's new in the [change log](https://github.com/benas/random-beans/releases).
* 05/03/2017: Version 3.6.0 is out with new features and bug fixes. See all details in the [change log](https://github.com/benas/random-beans/releases).

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

## How can this be useful ?

Sometimes, the test fixture does not really matter to the test logic. For example, if we want to test the result of a new sorting algorithm,
 we can generate random input data and assert the output is sorted, regardless of the data itself:

```java
@org.junit.Test
public void testSortAlgorithm() {

   // Given
   int[] ints = aNewEnhancedRandom().nextObject(int[].class);

   // When
   int[] sortedInts = myAwesomeSortAlgo.sort(ints);

   // Then
   assertThat(sortedInts).isSorted(); // fake assertion

}
```

Another example is testing the persistence of a domain object, we can generate a random domain object, persist it and assert the database contains the same values:

```java
@org.junit.Test
public void testPersistPerson() throws Exception {
   // Given
   Person person = random(Person.class);

   // When
   personDao.persist(person);

   // Then
   assertThat("person_table").column("name").value().isEqualTo(person.getName()); // assretj db
}
```

There are many other uses cases where random beans can be useful, you can find a non exhaustive list in the [wiki](https://github.com/benas/random-beans/wiki/use-cases).

## Extensions

* [JUnit extension](https://glytching.github.io/junit-extensions/randomBeans): Use random beans to generate random data in JUnit tests (courtesy of [glytching](https://github.com/glytching))

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you believe you found a bug, please use the [issue tracker](https://github.com/benas/random-beans/issues).

If you have any question, suggestion, or feedback, do not hesitate to use the [Gitter channel](https://gitter.im/benas/random-beans) of the project.

## Core team and contributors

#### Core team

* [Mahmoud Ben Hassine](https://github.com/benas)
* [Pascal Schumacher](https://github.com/PascalSchumacher)

#### Awesome contributors

* [Adriano Machado](https://github.com/ammachado)
* [Alberto Lagna](https://github.com/alagna)
* [Andrew Neal](https://github.com/aeneal)
* [Dovid Kopel](https://github.com/dovidkopel)
* [Eric Taix](https://github.com/eric-taix)
* [euZebe](https://github.com/euzebe)
* [Fred Eckertson](https://github.com/feckertson)
* [huningd](https://github.com/huningd)
* [Jose Manuel Prieto](https://github.com/prietopa)
* [kermit-the-frog](https://github.com/kermit-the-frog)
* [Lucas Andersson](https://github.com/LucasAndersson)
* [Nikola Milivojevic](https://github.com/dziga)
* [Oleksandr Shcherbyna](https://github.com/sansherbina)
* [Petromir Dzhunev](https://github.com/petromir)
* [Rebecca McQuary](https://github.com/rmcquary)
* [Rémi Alvergnat](http://www.pragmasphere.com)
* [Rodrigue Alcazar](https://github.com/rodriguealcazar)
* [Ryan Dunckel](https://github.com/sparty02)
* [Sam Van Overmeire](https://github.com/VanOvermeire)
* [Valters Vingolds](https://github.com/valters)
* [Vincent Potucek](https://github.com/punkratz312)

Thank you all for your contributions!
