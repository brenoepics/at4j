---
footer: false
---

# Download/Installation {#install}

The recommended way to get AT4J is to use a build manager, like Gradle or Maven.
If you are not familiar with build managers, you can follow one of the beginner ide setup guides (see navigation) or download AT4J directly from [GitHub](https://github.com/brenoepics/at4j).

## AT4J Dependency {#at4j-dependency}

::: code-group

```groovy [Gradle]
implementation group: 'io.github.brenoepics', name: 'at4j', version: 'AT4J-VERSION'
 ```
```xml [Maven]
<dependency>
  <groupId>io.github.brenoepics</groupId>
  <artifactId>at4j</artifactId>
  <version>AT4J-VERSION</version>
</dependency>
```
```scala [Sbt]
libraryDependencies += "io.github.brenoepics" % "at4j" % "AT4J-VERSION"
```
:::

