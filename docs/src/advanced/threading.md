# Creating an AzureApi with a Custom ExecutorService

This guide will walk you through the process of creating an `AzureApiBuilder` with a custom `ExecutorService`.

## Steps

1. **Create a custom ExecutorService**

   For this example, we'll create a custom `ExecutorService` using the Virtual Threads API introduced in Java 21:

   ```java
   ExecutorService customExecutorService = Executors.newVirtualThreadPerTaskExecutor();
   ```

2. **Create an AzureApiBuilder**

   You can create an `AzureApi` by using the `AzureApiBuilder` class. Here's an example:

   ```java
   String azureKey = "<Your Azure Subscription Key>";
   String azureRegion = "<Your Azure Subscription Region>";

   AzureApi azureApi = new AzureApiBuilder()
       .setKey(azureKey)
       .region(azureRegion)
       .executorService(customExecutorService).build();
   ```
   This will create an `AzureApi` with the settings you specified in the `AzureApiBuilder`.

That's it! You've successfully created an `AzureApi` with a custom `ExecutorService`. You can now use this `AzureApi` to
make requests to the Azure API.
