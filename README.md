# Azure Translator 4 Java (AT4J) 
[![Maven Central](https://img.shields.io/maven-central/v/io.github.brenoepics/at4j?color=blue)](https://central.sonatype.com/artifact/io.github.brenoepics/at4j)
![Static Badge](https://img.shields.io/badge/azure--api-3.0-blue?style=flat&logo=microsoftazure&logoColor=%230080FF&color=%230080FF&link=https%3A%2F%2Flearn.microsoft.com%2Fen-us%2Fazure%2Fai-services%2Ftranslator%2Freference%2Fv3-0-reference) 
[![Static Badge](https://img.shields.io/badge/run-l?logo=postman&label=Postman&color=EF5B25)](https://www.postman.com/maintenance-astronaut-2993290/workspace/brenoepics/collection/18589822-dfe7a640-9b94-47a8-b19f-46cb9cc8843e?action=share&creator=18589822)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=brenoepics_at4j&metric=coverage)](https://sonarcloud.io/summary/new_code?id=brenoepics_at4j) 
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=brenoepics_at4j&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=brenoepics_at4j)

An unofficial Java library for translating text using Azure AI Cognitive Services.

## ✨ Features

- Text Translation: Translate text from one language (or detect) to another or to a list of languages.
- Language Detection: Detect the language of a given text.
- Profanity Handling: Options for handling profanity in translations.
- Text Type Support: We support both plain text and HTML text translation.
- And more.

## 📝 Documentation

- [VitePress Docs](https://brenoepics.github.io/at4j/)
- [JavaDoc](https://brenoepics.github.io/at4j/javadoc/)

## 🎉 Basic Usage
> [!NOTE]
> Example repository [Azure-Translator-Example](https://github.com/brenoepics/Azure-Translator-Example)

The following example translates a simple Hello World to Portuguese, Spanish and French.

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
<details>
     <summary>Expected Output</summary>

```console
pt: Olá, Mundo!
es: ¡Hola mundo!
fr: Salut tout le monde!
```
</details>

## 📦 Download / Installation

The recommended way to get AT4J is to use a build manager, like Gradle or Maven.

### [AT4J Dependency](https://central.sonatype.com/artifact/io.github.brenoepics/at4j)

<details>
  <summary>Gradle</summary>
    
```gradle
implementation group: 'io.github.brenoepics', name: 'at4j', version: '1.0.0'
```
</details>
<details>
  <summary>Maven</summary>

```xml
<dependency>
    <groupId>io.github.brenoepics</groupId>
    <artifactId>at4j</artifactId>
    <version>1.0.0</version>
</dependency>
```
</details>
<details>
  <summary>Sbt</summary>

```sbt
libraryDependencies += "io.github.brenoepics" % "at4j" % "1.0.0"
```
</details>

### Frequently Asked Questions (FAQ)

**Q:** How do I access Azure Translator Keys for my project?

**A:** You can access your Azure Translator Keys through your Azure portal. Remember to keep your keys secure and refrain from sharing them publicly. If you suspect a key has been compromised, it's crucial to regenerate it promptly. For detailed instructions on generating your own keys, refer to [this guide](https://brenoepics.github.io/at4j/guide/azure-subscription.html#azure-subscription). Additionally, you can explore the [Azure Free Tier](https://brenoepics.github.io/at4j/guide/azure-subscription.html#azure-free-tier) for more information.

Optional Logger Dependency
**Q:** Is there a recommended logger dependency for the project?

**A:** While our project is compatible with any Log4j-2-compatible logging framework, integrating one can enhance your logging experience significantly. This allows you to configure log format, log targets (console, file, database, etc.), log levels per class, and more. For further details, please visit our [Docs](https://brenoepics.github.io/at4j/guide/installation.html#logger-dependency).

## 🤝 Thank You!
- **Microsoft Azure**: Supporting our project with a generous grant of $10,000+ in Azure credits, enabling us to use virtual machines, document translation and other essential cloud resources for our development needs.
- We extend our sincere thanks to all contributors for their invaluable contributions.
  
## 🧑‍💻 Contributing

Contributions of any kind are welcome. You can start contributing to this library by creating issues, submitting pull requests or giving a star to the project.

## 📃 License

AT4J is distributed under the [Apache license version 2.0](./LICENSE).
