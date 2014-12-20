## What is jPopulator ?

jPopulator is a java tool that allows you to populate java beans with random data. This can be very useful in development/test phases when you need to generate fake data, especially when your java beans type hierarchy is deep, since it populates *recursively* all nested types of a given java bean.

Let's see an example, suppose you have the following classes:

![](https://github.com/benas/jPopulator/raw/master/site/person.png)

If you want to populate a `Person` bean with jPopulator, you write this:

```java
Populator populator = new PopulatorBuilder().build();
Person person = populator.populateBean(Person.class);
```

And voila! jPopulator will introspect the `Person` type hierarchy, generate an instance for each nested bean and populate it with random data.

## Documentation

jPopulator documentation can be found here : [https://github.com/benas/jPopulator/wiki][]

## Awesome contributors

* [Alberto Lagna](https://github.com/alagna)
* [Adriano Machado](https://github.com/ammachado)
* [Eric Taix](https://github.com/eric-taix)
* [Jose Manuel Prieto](https://github.com/prietopa)
* [Nikola Milivojevic](https://github.com/dziga)

## License
jPopulator is released under the [MIT License][].

[https://github.com/benas/jPopulator/wiki]: https://github.com/benas/jPopulator/wiki
[MIT License]: http://opensource.org/licenses/mit-license.php/
