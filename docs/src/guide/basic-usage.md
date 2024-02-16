---
footer: false
---

# Basic Usage {#basic-usage}

This section will guide you through the basic usage of AT4J.

## Simple Hello World Translation {#hello-world}
```java
import io.github.brenoepics.at4j.AzureApi;
import io.github.brenoepics.at4j.AzureApiBuilder;
import io.github.brenoepics.at4j.data.request.TranslateParams;
import io.github.brenoepics.at4j.data.response.TranslationResponse;

import java.util.List;
import java.util.Optional;

public class ExampleApi {
  private static AzureApi azureApi;
  
	public static void main(String[] args) {
		// Insert your Azure key and region here
		String azureKey = "<Your Azure Subscription Key>";
		String azureRegion = "<Your Azure Subscription Region>";
		azureApi = new AzureApiBuilder().setKey(azureKey).region(azureRegion).build();

    translate(args.join(" "), List.of("pt", "es", "fr"));
	}

  private static void translate(String inputText, List<String> targetLanguages) {
    TranslateParams params = new TranslateParams(inputText, targetLanguages);
    Optional<TranslationResponse> result = azureApi.translate(params).join();

    result.ifPresent(response ->
      response.getFirstResult().getTranslations().forEach(ExampleApi::log));
  }
  
  public static void log(Translation translation) {
    System.out.println(translation.getLanguageCode() + ": " + translation.getText());
  }
}
```

**Input**
```console
Hello, World!
```

**Result**

```console
pt: Olá, Mundo!
es: ¡Hola mundo!
fr: Salut tout le monde!
```

The above example demonstrates the core feature of AT4J:

- **AzureApi**: Serves as the primary entry point for interacting with the Azure Translator API. It provides a set of methods to perform various translation-related tasks.

You may already have questions—don't worry.
We will cover every little detail in the rest of the documentation.
For now, please read along, so you can have a high-level understanding of what AT4J offers.

:::tip Prerequisites
The rest of the documentation assumes basic familiarity with Java and Azure Portal.
If you are totally new to backend development,
it's recommended
to familiarize yourself with Java programming and the Azure Portal before proceeding with the rest of the documentation.
Here are some brief pointers to help you get started:
[Java](https://www.tutorialspoint.com/java/index.htm) and [Azure](https://azure.microsoft.com/en-us/get-started) if needed.
:::

## Still Got Questions? {#still-got-questions}

Check out our [FAQ](/about/faq).
