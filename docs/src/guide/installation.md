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

## Optional Logger Dependency {#logger-dependency}

In addition to AT4J, it is also recommended to install a Log4j-2-compatible logging framework. 
A logging framework can be used to provide a more sophisticated logging experience with being able to configure log format,
log targets (console, file, database, Discord direct message, ...), log levels per class, and much more.

For example, Log4j Core:

::: code-group
```groovy [Gradle]
dependencies { runtimeOnly 'org.apache.logging.log4j:log4j-core:2.17.0' }
```

```xml [Maven]
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-core</artifactId>
  <version>2.17.0</version>
</dependency>
```

```scala [Sbt]
libraryDependencies ++= Seq("org.apache.logging.log4j" % "log4j-core" % "2.17.0")
```
:::

Or Log4j to Slf4j:

::: code-group
```groovy [Gradle]
dependencies { runtimeOnly 'org.apache.logging.log4j:log4j-to-slf4j:2.22.1' }
```

```xml [Maven]
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-to-slf4j</artifactId>
  <version>2.22.1</version>
</dependency>
```
```scala [Sbt]
libraryDependencies ++= Seq("org.apache.logging.log4j" % "log4j-to-slf4j" % "2.22.1")
```
:::

Take a look at the logger configuration doc for further information.
