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

### 🔑 Azure Translator Keys
> [!WARNING]
> Remember to **keep your keys secure and do not share them publicly**. If you believe that a key has been compromised, you must regenerate it in Azure's Panel.
> For more information, visit the [Azure portal](https://portal.azure.com/).

- [How to generate my own keys?](https://brenoepics.github.io/at4j/guide/azure-subscription.html#azure-subscription)
- [Azure Free Tier](https://brenoepics.github.io/at4j/guide/azure-subscription.html#azure-free-tier)

### Optional Logger Dependency

Any Log4j-2-compatible logging framework can be used to provide a more sophisticated logging experience
with being able to configure log format, log targets (console, file, database, etc.),
log levels per class, and much more.

More info at our [Docs](https://brenoepics.github.io/at4j/guide/installation.html#logger-dependency)

## 📋 Version Numbers

The version number has a 3-digit format: `major.minor.trivial`
* `major`: Increased extremely rarely to mark a major release (usually a rewrite affecting very huge parts of the library).
* `minor`: Any backward incompatible change to the api wrapper.
* `trivial`: A backward compatible change to the **api wrapper**. This is usually an important bugfix (or a bunch of smaller ones)
 or a backwards compatible feature addition.
 
## 🔨 Deprecation Policy

A method or class that is marked as deprecated can be removed with the next minor release (but it will usually stay for
several minor releases). A minor release might remove a class or method without having it deprecated, but we will do our
best to deprecate it before removing it. We are unable to guarantee this though, because we might have to remove / replace
something due to changes made by Azure, which we are unable to control. Usually you can expect a deprecated method or
class to stay for at least 6 months before it finally gets removed, but this is not guaranteed.

## 🧑‍💻 Contributing

Contributions of any kind are welcome. You can start contributing to this library by creating issues, submitting pull requests or giving a star to the project.

## 📃 License

AT4J is distributed under the [Apache license version 2.0](./LICENSE).
