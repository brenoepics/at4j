---
footer: false
---

# What is the `CompletableFuture` API? {#completable-future}

> [!WARNING]
>
> This tutorial assumes that you are familiar with [lambda expressions](https://www.w3schools.com/java/java_lambda.asp). Take a look at the lambda introduction first if
> you are not!

AT4J uses Futures for asynchronous requests. To understand how AT4J works,
you must understand the concept of
[Futures](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html)
in general, as well as their most common implementation, the
[CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html).

`CompletableFuture` is a class introduced in Java 8 that represents a future result of an asynchronous computation.
It is a part of the `java.util.concurrent` package and is used for asynchronous programming in Java.

This little introduction gives you a quick overview of the basics you need to know to work with Futures.

## What is a `Future`?

A `Future` is a placeholder for a result that is not yet available. It represents the result of an asynchronous
computation.

This is useful if a method call requires some time and should not block the execution of your current code. You can see
the difference with a primitive speed comparison:

```java
long currentTime = System.currentTimeMillis();
TranslateParams params =
    new TranslateParams("Hello World!", List.of("pt", "es", "fr"));
    
azureApi.translate(params);
azureApi.translate(params);
azureApi.translate(params);
azureApi.translate(params);
azureApi.translate(params);

System.out.println((System.currentTimeMillis() - currentTime) + " ms");
```

```java
long currentTime = System.currentTimeMillis();
TranslateParams params =
    new TranslateParams("Hello World!", List.of("pt", "es", "fr"));
    
azureApi.translate(params).join();
azureApi.translate(params).join();
azureApi.translate(params).join();
azureApi.translate(params).join();
azureApi.translate(params).join();

System.out.println((System.currentTimeMillis() - currentTime) + " ms");
```

The first example will take a few milliseconds to execute, while the second example will take a few seconds to execute.
This is because the first example does not wait for the result of the `translate` method, while the second example waits
for the result of each `translate` method call.

## `CompletableFuture` Methods

### `join()`

> [!CAUTION]
> The `join` method is a blocking operation, which means that it will wait for the result of the `CompletableFuture` to
> be
> available. If the computation fails, a `CompletionException` will be thrown.
> It is recommended to use the `join` method only when you are sure that the computation will succeed.
> Avoid using the `join` method in production code, and methods which will be called by other methods, as it can lead to
> deadlocks.

The `join` method is used to wait for the result of the `CompletableFuture`. It returns the result of the computation
when it is done or throws a `CompletionException` if anything failed.

The following example demonstrates how to use the `join` method:

```java
TranslateParams params =
    new TranslateParams("Hello World!", List.of("pt", "es", "fr"));
    
Optional<TranslationResponse> translationResult = azureApi.translate(params).join();

translationResult.ifPresent(
    response -> response.getFirstResult().getTranslations().forEach(System.out::println));
```

This example translates the text "Hello World!" into Portuguese, Spanish, and French. The `join` method waits for the
result of the translation and prints the translations to the console.

> [!TIP]
> While join() can become a performance issue when you call it very frequently, it is very convenient to use and easy to
> understand. If you are new to programming and just want to get your first application working, this is a good method to start
> with.
> Once you gathered more experience, we highly advise against using `join()` as it negatively impacts the performance
> of your application.

### `thenAccept()`

The `thenAccept` method is used to perform an action when the `CompletableFuture` completes. It takes a `Consumer` as a
parameter, which is called with the result of the computation when it is done.

The following example demonstrates how to use the `thenAccept` method:

```java
TranslateParams params =
    new TranslateParams("Hello World!", List.of("pt", "es", "fr"));
    
azureApi.translate(params).thenAccept(
    response -> response.getFirstResult().getTranslations().forEach(System.out::println));
```

> [!CAUTION]
> The example above does not handle exceptions. If the translation fails, the exception will be thrown and not caught.
> This can lead to unexpected behavior in your application and make it harder to debug.
> It is recommended to use the `exceptionally` method to handle exceptions.

### `exceptionally(...)`

The `exceptionally` method is used to handle exceptions that occur during the computation of the `CompletableFuture`. It
takes a `Function` as a parameter, which is called with the exception when it occurs.

The following example demonstrates how to use the `exceptionally` method:

```java
TranslateParams params =
    new TranslateParams("Hello World!", List.of("pt", "es", "fr"));
    
azureApi.translate(params).exceptionally(
    exception -> {
        System.err.println("An error occurred: " + exception.getMessage());
        return null;
    }).thenAccept(
        response -> response.getFirstResult().getTranslations().forEach(System.out::println));
```

### `thenCompose()`

The `thenCompose` method is used to chain multiple `CompletableFuture` instances together. It takes a `Function` as a
parameter, which is called with the result of the computation when it is done.

The following example demonstrates how to use the `thenCompose` method:

```java
TranslateParams params =
    new TranslateParams("Hello World!", List.of("pt", "es", "fr"));
    
azureApi.translate(params).thenCompose(
    response -> azureApi.translate(params)).thenAccept(
        response -> response.getFirstResult().getTranslations().forEach(System.out::println));
```

## Further Reading

To learn more about `CompletableFuture`, check out the following resources:

- [Java 8 CompletableFuture Tutorial](https://www.baeldung.com/java-completablefuture)
- [Java 8 CompletableFuture API](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
- [Java 8 CompletableFuture Javadoc](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
- [Java 8 CompletableFuture Tutorial](https://www.callicoder.com/java-8-completablefuture-tutorial/)

