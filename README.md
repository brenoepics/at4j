# Azure Translator 4 Java (AT4J) 
[![Latest version](https://shields.io/github/release/brenoepics/at4j.svg?label=Version&colorB=brightgreen&style=flat-square)](https://github.com/brenoepics/at4j/releases/latest) ![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/brenoepics/at4j/maven.yml) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=brenoepics_at4j&metric=coverage)](https://sonarcloud.io/summary/new_code?id=brenoepics_at4j) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=brenoepics_at4j&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=brenoepics_at4j) ![GitHub Repo stars](https://img.shields.io/github/stars/brenoepics/at4j)


An unofficial Java library for translating text using Azure AI Cognitive Services.

## ✨ Features

- Text Translation: Translate text from one language (or detect) to another or to a list of languages.
- Language Detection: Detect the language of a given text.
- Profanity Handling: Options for handling profanity in translations.
- Text Type Support: We support both plain text and HTML text translation.
- And more.

## 📝 Documentation

- [JavaDoc](https://brenoepics.github.io/at4j/javadoc/)

## 🎉 Basic Usage
> Example repository [Azure-Translator-Example](https://github.com/brenoepics/Azure-Translator-Example)

The following example translates a simple Hello World to Portuguese, Spanish and French.

```java
public class MyFirstTranslation {
		
    public static void main(String[] args) {
        // Insert your Azure key and region here
        String azureKey = "<Your Azure Subscription Key>";
        String azureRegion = "<Your Azure Subscription Region>";

        // Create an Azure API object with your key and region
        AzureApi api = new AzureApiBuilder().setKey(azureKey).region(azureRegion).build();

        // Set up translation parameters
        List<String> targetLanguages = List.of("pt", "es", "fr");
        TranslateParams params = new TranslateParams("Hello World!", targetLanguages).setSourceLanguage("en");

        // Translate the text
        Optional<TranslationResponse> translationResult = api.translate(params).join();

        // Print the translations
        translationResult.ifPresent(response -> {
            System.out.println("Translations:");

            // Loop through each translation and print the language code and text
            response.getTranslations().forEach(translation -> 
                    System.out.println(translation.getLanguageCode() + ": " + translation.getText()));
        });
    }	
}
```
<details>
     <summary>Expected Output</summary>

```console
Translations:
pt: Olá, Mundo!
es: ¡Hola mundo!
fr: Salut tout le monde!
```
</details>

## 📦 Download / Installation

The recommended way to get AT4J is to use a build manager, like Gradle or Maven.

### AT4J Dependency

<details>
  <summary>Gradle</summary>
    
```gradle
implementation group: 'io.github.brenoepics', name: 'at4j', version: '0.0.5'
```
</details>
<details>
  <summary>Maven</summary>

```xml
<dependency>
    <groupId>io.github.brenoepics</groupId>
    <artifactId>at4j</artifactId>
    <version>0.0.5</version>
</dependency>
```
</details>
<details>
  <summary>Sbt</summary>

```sbt
libraryDependencies += "io.github.brenoepics" % "at4j" % "0.0.5"
```
</details>

### 🔑 Azure Translator Keys
> tl;dr [Generate Here](https://portal.azure.com/#view/Microsoft_Azure_ProjectOxford/CognitiveServicesHub/~/TextTranslation).
<details>
  <summary>How to generate my own keys?</summary>

1. Sign in to the [Azure portal](https://portal.azure.com/).

2. In the left-hand menu, click on "Create a resource".

3. In the "Search the Marketplace" box, type "Translator Text" and select it from the dropdown list.

4. Click on the "Create" button.

5. Fill in the required details:
   - Name: Enter a unique name for your resource.
   - Subscription: Select the Azure subscription that you want to use.
   - Resource Group: You can create a new resource group or select an existing one.
   - Pricing tier: Select the pricing tier that suits your needs.
6. Click on the "Review + create" button.

7. Review your settings and click on the "Create" button.

8. After the deployment is complete, go to the resource you just created.

9. In the left-hand menu, click on "Keys and Endpoint".

10. You will see two keys and an endpoint. You can use either of the keys in your application.
</details>
<details>
  <summary>Azure Free Tier</summary>
    
Azure offers a free tier for its services, which is a great way to get started with Azure Translator for Java. The free tier includes a monthly allowance of 2 million characters for Translator Text. This is sufficient for testing and small projects.

To use the free tier, you need to create an Azure account and set up a Translator Text resource. Azure will require a credit card for identity verification, but you will not be charged unless you explicitly change your settings and choose to do so.

Please note that the free tier usage limits are subject to change by Azure, and it's recommended to check the current details on the Azure Pricing page.
</details>

Remember to **keep your keys secure and do not share them publicly**. If you believe that a key has been compromised, you must regenerate it in Azure's Panel.
For more information, visit the [Azure Pricing page](https://azure.microsoft.com/pricing/details/cognitive-services/translator/).

### Optional Logger Dependency

Any Log4j-2-compatible logging framework can be used to provide a more sophisticated logging experience
with being able to configure log format, log targets (console, file, database, etc.),
log levels per class, and much more.

For example, Log4j Core in Gradle
```gradle
dependencies { runtimeOnly 'org.apache.logging.log4j:log4j-core:2.19.0' }
```

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
