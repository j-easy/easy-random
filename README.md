***

<div align="center">
    <b><em>Easy Random</em></b><br>
    The simple, stupid random Java&trade; beans generator
</div>

<div align="center">

[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](http://opensource.org/licenses/MIT)
[![Coverage Status](https://coveralls.io/repos/j-easy/easy-random/badge.svg?branch=master&service=github)](https://coveralls.io/github/j-easy/easy-random?branch=master)
[![Build Status](https://github.com/j-easy/easy-random/workflows/Java%20CI/badge.svg)](https://github.com/j-easy/easy-random/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.jeasy/easy-random-core/badge.svg?style=flat)](http://repo1.maven.org/maven2/org/jeasy/easy-random-core/4.1.0/)
[![Javadocs](http://www.javadoc.io/badge/org.jeasy/easy-random-core.svg)](http://www.javadoc.io/doc/org.jeasy/easy-random-core)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/j-easy/easy-random)

</div>

***

## Latest news

* 04/11/2019: Easy Random v4.1.0 is released! See what's new in the [change log](https://github.com/j-easy/easy-random/releases).
* 05/05/2019: Random Beans has been renamed to Easy Random and is now part of [jeasy.org](http://www.jeasy.org).
Version 4.0.0 has been released! See what's new in the [change log](https://github.com/j-easy/easy-random/releases).

# What is Easy Random ?

Easy Random is a library that generates random Java beans. You can think of it as an [ObjectMother](https://martinfowler.com/bliki/ObjectMother.html) for the JVM. Let's say you have a class `Person` and you want to generate a random instance of it, here we go:

```java
EasyRandom easyRandom = new EasyRandom();
Person person = easyRandom.nextObject(Person.class);
```

The method `EasyRandom#nextObject` is able to generate random instances of any given type.

# What is this EasyRandom API ?

The `java.util.Random` API provides 7 methods to generate random data: `nextInt()`, `nextLong()`, `nextDouble()`, `nextFloat()`, `nextBytes()`, `nextBoolean()` and `nextGaussian()`.
What if you need to generate a random `String`? Or say a random instance of your domain object?
Easy Random provides the `EasyRandom` API that extends `java.util.Random` with a method called `nextObject(Class type)`.
This method is able to generate a random instance of any arbitrary Java bean.

The `EasyRandomParameters` class is the main entry point to configure `EasyRandom` instances. It allows you to set all
parameters to control how random data is generated:

```java
EasyRandomParameters parameters = new EasyRandomParameters()
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
   .ignoreRandomizationErrors(true);

EasyRandom easyRandom = new EasyRandom(parameters);
```

For more details about these parameters, please refer to the [configuration parameters](https://github.com/j-easy/easy-random/wiki/Randomization-parameters) section.

In most cases, default options are enough and you can use the default constructor of `EasyRandom`.

Easy Random allows you to control how to generate random data through the [`org.jeasy.random.api.Randomizer`](https://github.com/j-easy/easy-random/blob/master/easy-random-core/src/main/java/org/jeasy/random/api/Randomizer.java) interface and makes it easy to exclude some fields from the object graph using a `java.util.function.Predicate`:

```java
EasyRandomParameters parameters = new EasyRandomParameters()
   .randomize(String.class, () -> "foo")
   .excludeField(named("age").and(ofType(Integer.class)).and(inClass(Person.class)));

EasyRandom easyRandom = new EasyRandom(parameters);
Person person = easyRandom.nextObject(Person.class);
```

In the previous example, Easy Random will:

* Set all fields of type `String` to `foo` (using the `Randomizer` defined as a lambda expression)
* Exclude the field named `age` of type `Integer` in class `Person`.

The static methods `named`, `ofType` and `inClass` are defined in [`org.jeasy.random.FieldPredicates`](https://github.com/j-easy/easy-random/blob/master/easy-random-core/src/main/java/org/jeasy/random/FieldPredicates.java) 
which provides common predicates you can use in combination to define exactly which fields to exclude.
A similar class called [`TypePredicates`](https://github.com/j-easy/easy-random/blob/master/easy-random-core/src/main/java/org/jeasy/random/TypePredicates.java) can be used to define which types to exclude from the object graph.
You can of course use your own `java.util.function.Predicate` in combination with those predefined predicates. 

# Why Easy Random ?

Populating a Java object with random data can look easy at first glance, unless your domain model involves many related classes. In the previous example, let's suppose the `Person` type is defined as follows:

<p align="center">
    <img src="https://raw.githubusercontent.com/wiki/j-easy/easy-random/images/person.png">
</p>

**Without** Easy Random, you would write the following code in order to create an instance of the `Person` class:

```java
Street street = new Street(12, (byte) 1, "Oxford street");
Address address = new Address(street, "123456", "London", "United Kingdom");
Person person = new Person("Foo", "Bar", "foo.bar@gmail.com", Gender.MALE, address);
```

And if these classes do not provide constructors with parameters (may be some legacy beans you can't change), you would write:

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

**With** Easy Random, generating a random `Person` object is done with `new EasyRandom().nextObject(Person.class)`.
The library will **recursively** populate all the object graph. That's a big difference!

## How can this be useful ?

Sometimes, the test fixture does not really matter to the test logic. For example, if we want to test the result of a new sorting algorithm, we can generate random input data and assert the output is sorted, regardless of the data itself:

```java
@org.junit.Test
public void testSortAlgorithm() {

   // Given
   int[] ints = easyRandom.nextObject(int[].class);

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
   Person person = easyRandom.nextObject(Person.class);

   // When
   personDao.persist(person);

   // Then
   assertThat("person_table").column("name").value().isEqualTo(person.getName()); // assretj db
}
```

There are many other uses cases where Easy Random can be useful, you can find a non exhaustive list in the [wiki](https://github.com/j-easy/easy-random/wiki/use-cases).

## Extensions

* [JUnit extension](https://glytching.github.io/junit-extensions/randomBeans): Use Easy Random to generate random data in JUnit tests (courtesy of [glytching](https://github.com/glytching))
* [Vavr extension](https://github.com/xShadov/easy-random-vavr-extension): This extension adds support to randomize [Vavr](https://github.com/vavr-io/vavr) types (courtesy of [xShadov](https://github.com/xShadov))

## Articles and blog posts

* [Easy testing with ObjectMothers and EasyRandom](https://www.jworks.io/easy-testing-with-objectmothers-and-easyrandom/)
* [Quick Guide to EasyRandom in Java](https://www.baeldung.com/java-easy-random)
* [Top secrets of the efficient test data preparation](https://waverleysoftware.com/blog/test-data-preparation/)
* [Random Data Generators for API Testing in Java](https://techblog.dotdash.com/random-data-generators-for-api-testing-in-java-369c99075208)
* [EasyRandom 4.0 Released](https://www.jworks.io/easyrandom-4-0-released/)
* [Type Erasure Revisited](https://www.beyondjava.net/type-erasure-revisited)
* [Generate Random Test Data With jPopulator](https://www.beyondjava.net/newsflash-generate-random-test-data-jpopulator)

## Who is using Easy Random ?

* [Netflix](https://github.com/Netflix/AWSObjectMapper/blob/v1.11.723/build.gradle#L71)
* [JetBrains](https://github.com/JetBrains/intellij-community/blob/201.6073/plugins/gradle/tooling-extension-impl/testSources/org/jetbrains/plugins/gradle/tooling/serialization/ToolingSerializerTest.kt#L8)
* [Mulesoft](https://github.com/mulesoft/mule-wsc-connector/blob/1.5.6/src/test/java/org/mule/extension/ws/ConfigEqualsTestCase.java#L14)
* [Easy Cucumber](https://github.com/osvaldjr/easy-cucumber/blob/0.0.18/pom.xml#L122)
* [Open Network Automation Platform](https://github.com/onap/vid/blob/6.0.3/vid-app-common/src/test/java/org/onap/vid/controller/MsoControllerTest.java#L43)

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you believe you found a bug, please use the [issue tracker](https://github.com/j-easy/easy-random/issues).

If you have any question, suggestion, or feedback, do not hesitate to use the [Gitter channel](https://gitter.im/j-easy/easy-random) of the project.

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
* [Johan Kindgren](https://github.com/johkin)
* [Joren Inghelbrecht](https://github.com/joinghel)
* [Jose Manuel Prieto](https://github.com/prietopa)
* [kermit-the-frog](https://github.com/kermit-the-frog)
* [Lucas Andersson](https://github.com/LucasAndersson)
* [Michael Düsterhus](https://github.com/reitzmichnicht)
* [Nikola Milivojevic](https://github.com/dziga)
* [nrenzoni](https://github.com/nrenzoni)
* [Oleksandr Shcherbyna](https://github.com/sansherbina)
* [Petromir Dzhunev](https://github.com/petromir)
* [Rebecca McQuary](https://github.com/rmcquary)
* [Rémi Alvergnat](http://www.pragmasphere.com)
* [Rodrigue Alcazar](https://github.com/rodriguealcazar)
* [Ryan Dunckel](https://github.com/sparty02)
* [Sam Van Overmeire](https://github.com/VanOvermeire)
* [Valters Vingolds](https://github.com/valters)
* [Vincent Potucek](https://github.com/punkratz312)
* [Weronika Redlarska](https://github.com/weronika-redlarska)
* [Konstantin Lutovich](https://github.com/lutovich)
* [Steven_Van_Ophem](https://github.com/stevenvanophem)

Thank you all for your contributions!

## License

The [MIT License](http://opensource.org/licenses/MIT). See [LICENSE.txt](https://github.com/j-easy/easy-random/blob/master/LICENSE.txt).
