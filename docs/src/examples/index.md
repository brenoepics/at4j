---
page: true
title: Examples
aside: false
footer: false
outline: false
---

# Examples {#examples}

This section contains examples of how to use AT4J in different scenarios.

## Translate Hello World {#translator}

```java
public class ExampleTranslator {
  public static void main(String[] args) {
    // Insert your Azure key and region here
    String azureKey = "<Your Azure Subscription Key>";
    String azureRegion = "<Your Azure Subscription Region>";
    AzureApi api = new AzureApiBuilder().setKey(azureKey).region(azureRegion).build();

    // Set up translation parameters
    List<String> targetLanguages = List.of("pt", "es", "fr");
    TranslateParams params =
        new TranslateParams("Hello World!", targetLanguages).setSourceLanguage("en");

    // Translate the text
    Optional<TranslationResponse> translationResult = api.translate(params).join();

    // Print the translations
    translationResult.ifPresent(
        response ->
            response.getFirstResult().getTranslations().forEach(ExampleTranslator::logLanguage));
  }

  public static void logLanguage(Translation translation) {
    System.out.println(translation.getLanguageCode() + ": " + translation.getText());
  }
}
```

## Language Detector {#detect}

```java
public class ExampleDetector {
  public static void main(String[] args) {
    // Insert your Azure key and region here
    String azureKey = "<Your Azure Subscription Key>";
    String azureRegion = "<Your Azure Subscription Region>";
    AzureApi api = new AzureApiBuilder().setKey(azureKey).region(azureRegion).build();

    DetectLanguageParams params =
      new DetectLanguageParams("Hello World!").addText("Bonjour le monde!");

    // Translate the text
    Optional<DetectResponse> result = api.detectLanguage(params).join();

    // Print the translations
    result.ifPresent(
      response -> response.getDetectedLanguages().forEach(ExampleDetector::logLanguage));
  }

  public static void logLanguage(DetectedLanguage detectedLanguage) {
    System.out.println(detectedLanguage.getLanguageCode() + ": " + detectedLanguage.getScore());
  }
}
```
