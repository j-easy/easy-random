# What is Random Beans ?

Random Beans (formerly jPopulator) is a library that generates random Java beans.
Generating random data is a common requirement in software testing, and this task can quickly become tedious when the domain model
involves many related classes. Random Beans tries to make this task easier.

# How to use it?

Random Beans provides the `Populator` API that is able to generate a random instance of given type:

```java
Populator populator = new PopulatorBuilder().build();
Person person = populator.populateBean(Person.class);
```

This snippet will generate a random instance of the `Person` type.

# Why Random Beans?

Populating a Java object with random data can look easy at first glance, unless your domain model involves many related classes.
Let's see a quick example, suppose you have the following classes:

![](https://github.com/benas/random-beans/raw/master/site/person.png)

With Random Beans, generating a random `Person` object is done with the previous snippet. The library will **recursively** populate
all the object graph. Without Random Beans, you would write something like:

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

As you can see, Random Beans can tremendously reduce the code of generating random test data than doing it by hand.

## Documentation

Random Beans documentation can be found in the project's wiki: [https://github.com/benas/random-beans/wiki](https://github.com/benas/random-beans/wiki)

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you believe you found a bug, please use the [issue tracker](https://github.com/benas/random-beans/issues).

If you have any question, suggestion, or feedback, do not hesitate to use the [Gitter channel](https://gitter.im/benas/random-beans) of the project.

## Awesome contributors

* [Alberto Lagna](https://github.com/alagna)
* [Adriano Machado](https://github.com/ammachado)
* [Eric Taix](https://github.com/eric-taix)
* [Jose Manuel Prieto](https://github.com/prietopa)
* [Nikola Milivojevic](https://github.com/dziga)
* [Rémi Alvergnat](http://www.pragmasphere.com)
* [Fred Eckertson](https://github.com/feckertson)

Thank you all for your contributions!

## License

Random Beans is released under the [MIT License](http://opensource.org/licenses/mit-license.php/):

```
The MIT License

Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
