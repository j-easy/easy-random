## What is jPopulator ?

jPopulator is a java library that allows you to populate java beans with random data.

Populating a deep type hierarchy by hand is a tedious task and the goal of jPopulator is to make this task easy.

Let's see an example, suppose you have the following classes:

![](https://github.com/benas/jPopulator/raw/master/site/person.png)

If you want to populate a `Person` bean with jPopulator, you write this snippet:

```java
Populator populator = new PopulatorBuilder().build();
Person person = populator.populateBean(Person.class);
```

And voila! jPopulator will introspect the `Person` type hierarchy, generate an instance for each nested bean and populate it with random data.

**Without** jPopulator, you would write something like:
 
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
 
It's always tedious to generate test data by hand, and jPopulator makes this task easier.

## Documentation

jPopulator documentation can be found in the project's wiki:  [https://github.com/benas/jPopulator/wiki](https://github.com/benas/jPopulator/wiki)

## Contribution

You are welcome to contribute to the project with pull requests on GitHub.

If you believe you found a bug, please use the [issue tracker](https://github.com/benas/jPopulator/issues).

If you have any question, suggestion, or feedback, do not hesitate to use the [Gitter channel](https://gitter.im/benas/jPopulator) of the project.

## Awesome contributors

* [Alberto Lagna](https://github.com/alagna)
* [Adriano Machado](https://github.com/ammachado)
* [Eric Taix](https://github.com/eric-taix)
* [Jose Manuel Prieto](https://github.com/prietopa)
* [Nikola Milivojevic](https://github.com/dziga)
* [Rémi Alvergnat](http://www.pragmasphere.com)

Thank you all for your contributions!

## License
jPopulator is released under the [MIT License](http://opensource.org/licenses/mit-license.php/):

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
