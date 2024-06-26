---
footer: false
---

# What are `Optional` types? {#optional}

Basically, every method that might return a `null` value will return an Optional in AT4J. This is a way to
avoid `NullPointerException`s in your code. This type was introduced in Java 8 and
is a container object that may or may not contain a non-null value. If a value is present, `isPresent()` will
return `true`, and `get()` will return the value.

Old code:

```java
  TranslationResponse translation = azureApi.translate(text).join();
  if (translation != null) {
    translation.getFirstResult().getTranslations().forEach(ExampleApi::log);
  }
```

New code:

```java
  azureApi.translate(text).ifPresent(response ->
  response.getFirstResult().getTranslations().forEach(ExampleApi::log));
```

You can imagine an Optional like a box that may or may not contain a value. Before accessing this value, you have to
unpack this box first.

## Methods

### `get()`

> [!CAUTION]
> You should avoid using the `get` method as it can throw a `NoSuchElementException` if the value is not present.
> It is recommended to use the `isPresent` method to check if the value is present before calling the `get` method.
> If you are sure that the value is present, you can use the `orElse` method to provide a default value if the value is
> not present.

The `get` method returns the value if present, otherwise throws a `NoSuchElementException`.

```java
TranslationResponse translation = azureApi.translate(text).get();
translation.getFirstResult().getTranslations().forEach(ExampleApi::log);
```

### `isPresent()`

The `isPresent` method returns `true` if the `Optional` contains a non-null value, otherwise `false`.

```java
Optional<TranslationResponse> translation = azureApi.translate(text);
if (translation.isPresent()) {
  translation.get().getFirstResult().getTranslations().forEach(ExampleApi::log);
}
```

### `ifPresent(Consumer<? super T> consumer)`

The `ifPresent` method takes a `Consumer` as an argument and executes the consumer if the `Optional` contains a non-null
value.

```java
azureApi.translate(text).ifPresent(response ->
  response.getFirstResult().getTranslations().forEach(ExampleApi::log));
```

### `orElse(T other)`

The `orElse` method returns the value if present, otherwise returns the specified value.

```java
TranslationResponse translation = azureApi.translate(text).orElse(new TranslationResponse());
translation.getFirstResult().getTranslations().forEach(ExampleApi::log);
```

### `orElseGet(Supplier<? extends T> other)`

The `orElseGet` method returns the value if present, otherwise returns the result of the specified `Supplier`.

```java
TranslationResponse translation = azureApi.translate(text).orElseGet(() -> new TranslationResponse());
translation.getFirstResult().getTranslations().forEach(ExampleApi::log);
```

### `orElseThrow()`

The `orElseThrow` method returns the value if present, otherwise throws an exception.

```java
TranslationResponse translation = azureApi.translate(text).orElseThrow();
translation.getFirstResult().getTranslations().forEach(ExampleApi::log);
```

### `map(Function<? super T, ? extends U> mapper)`

The `map` method applies the given function to the value if present, otherwise returns an empty `Optional`.

```java
Optional<String> translation = azureApi.translate(text).map(response -> response.getFirstResult().getTranslations().getFirst().getText());
translation.ifPresent(System.out::println);
```

### `filter(Predicate<? super T> predicate)`

The `filter` method returns an `Optional` containing the value if present and the value matches the given predicate,

```java
Optional<Translation> translation = azureApi.translate(text).flatMap(response -> response.getFirstResult().getTranslations().stream().findFirst());
translation.filter(t -> t.getLanguageCode().equals("en")).ifPresent(ExampleApi::log);
```

### `flatMap(Function<? super T, Optional<U>> mapper)`

The `flatMap` method applies the given function to the value if present, otherwise returns an empty `Optional`.

```java
Optional<Translation> translation = azureApi.translate(text).flatMap(response -> response.getFirstResult().getTranslations().stream().findFirst());
translation.ifPresent(ExampleApi::log);
```

## Further Reading

- [Optional (Java Platform SE 8 )](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
- [Java 8 Optional Tutorial](https://www.baeldung.com/java-optional)
- [Java 8 Optional API](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
