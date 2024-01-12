# Azure Translator For Java (AT4J) [![Latest version](https://shields.io/github/release/brenoepics/at4j.svg?label=Version&colorB=brightgreen&style=flat-square)](https://github.com/brenoepics/at4j/releases/latest)

An Unofficial Java library for translating text using Azure AI Cognitive Services. It is fast and easy to configure.

## Features

- Translation of text from one language to another
- Language detection
- Profanity handling
- Support for both plain text and HTML text translation
- more

## 🎉 Basic Usage

The following example translates a simple Hello World to French.

```java
import com.github.brenoepics.at4j.AzureApi;
import com.github.brenoepics.at4j.AzureApiBuilder;
import com.github.brenoepics.at4j.azure.BaseURL;
import com.github.brenoepics.at4j.data.request.TranslateParams;
import com.github.brenoepics.at4j.data.response.TranslationResponse;

public class Main {

    public static void main(String[] args) {

        // Create an instance of AzureApi
        AzureApi api = new AzureApiBuilder()
                .setBaseURL(BaseURL.GLOBAL)
                .setSubscriptionKey("<Your Azure Subscription Key>")
                .setSubscriptionRegion("<Your Azure Subscription Region>")
                .build();

        // Create translation parameters
        TranslateParams params = new TranslateParams("Hello World");
        params.setSourceLanguage("en");
        params.setTargetLanguages("fr");

        // Translate text
        api.translate(params).thenAccept(response -> {
            if (response.isPresent()) {
                TranslationResponse translationResponse = response.get();
                translationResponse.getTranslations().forEach(translation -> {
                    System.out.println("Translated Text: " + translation.getText());
                });
            }
        });
    }

}
```

### Other basic usage here [Azure-Translator-Example](https://github.com/brenoepics/Azure-Translator-Example)

## 📦 Download / Installation

The recommended way to get AT4J is to use a build manager, like Gradle or Maven.

### AT4J Dependency

#### Gradle

```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
dependencies { implementation 'com.github.brenoepics:at4j:0.0.1' }
```

#### [Maven](https://github.com/brenoepics/at4j/packages/2037740)
Add the GitHub Maven Repository to your `pom.xml`:

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.github.brenoepics</groupId>
    <artifactId>at4j</artifactId>
    <version>0.0.1</version>
</dependency>
```

To create Azure keys, you need to follow these steps:

1. Sign in to the Azure portal.

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

Remember to keep your keys secure and do not share them publicly. If you believe that a key has been compromised, you can regenerate it from this page.


## 🗄️ Azure Free Tier

Azure offers a free tier for its services, which is a great way to get started with Azure Translator for Java. The free tier includes a monthly allowance of 2 million characters for Translator Text. This is sufficient for testing and small projects.

To use the free tier, you need to create an Azure account and set up a Translator Text resource. Azure will require a credit card for identity verification, but you will not be charged unless you explicitly change your settings and choose to do so.

Please note that the free tier usage limits are subject to change by Azure, and it's recommended to check the current details on the Azure Pricing page.

For more information, visit the [Azure Pricing page](https://azure.microsoft.com/pricing/details/cognitive-services/translator/).

### Optional Logger Dependency

Any Log4j-2-compatible logging framework can be used to provide a more sophisticated logging experience
with being able to configure log format, log targets (console, file, database, Discord direct message, ...),
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

## ✨ Contributing

Contributions of any kind are welcome. You can start contributing to this library by creating issues, submitting pull requests or giving a star to the project.

## 📃 License

AT4J is distributed under the [Apache license version 2.0](./LICENSE).
