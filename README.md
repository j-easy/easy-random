## What is jPopulator ?

[![Join the chat at https://gitter.im/benas/jPopulator](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/benas/jPopulator?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

jPopulator is a java library that allows you to populate java beans with random data.

Populating a deep type hierarchy by hand is a tedious task and the goal of jPopulator is to make this task hassle-free.

Let's see an example, suppose you have the following classes:

![](https://github.com/benas/jPopulator/raw/master/site/person.png)

If you want to populate a `Person` bean with jPopulator, you write this:

```java
Populator populator = new PopulatorBuilder().build();
Person person = populator.populateBean(Person.class);
```

And voila! jPopulator will introspect the `Person` type hierarchy, generate an instance for each nested bean and populate it with random data.

## Documentation

jPopulator documentation can be found here : [https://github.com/benas/jPopulator/wiki](https://github.com/benas/jPopulator/wiki)

## Awesome contributors

* [Alberto Lagna](https://github.com/alagna)
* [Adriano Machado](https://github.com/ammachado)
* [Eric Taix](https://github.com/eric-taix)
* [Jose Manuel Prieto](https://github.com/prietopa)
* [Nikola Milivojevic](https://github.com/dziga)

Thank you all for your contributions!

## License
jPopulator is released under the [MIT License](http://opensource.org/licenses/mit-license.php/).
