---
footer: false
---

# Getting an Azure Subscription {#azure-subscription}

:::warning Azure Keys

Remember to **keep your keys secure and do not share them publicly**. If you believe that a key has been compromised, you must regenerate it in Azure's Panel.

For more information, visit the [Azure portal](https://portal.azure.com/).
  :::

Azure Text Translation is able to translate text instantly or in batches across [more than 100 languages](https://go.microsoft.com/fwlink/?linkid=2216841),
Support a wide range of use cases, such as translation for call centers, 
multilingual conversational agents, 
or in-app communication.

## Free Tier {#azure-free-tier}

Azure offers a free tier for its services, which is a great way to get started with Azure Translator for Java.
The free tier includes a monthly allowance of two million characters for Translator Text. This is sufficient for testing and small projects.

Please note that the free tier usage limits are subject to change by Azure, and it's recommended to check the current details on the [Azure Pricing](https://azure.microsoft.com/en-us/pricing/details/cognitive-services/translator/) page.

## How to generate an Azure Translation Subscription {#generate-azure-subscription}
> [Generate Here](https://portal.azure.com/#view/Microsoft_Azure_ProjectOxford/CognitiveServicesHub/~/TextTranslation).

1. Sign in to the [Azure portal](https://portal.azure.com/).
2. In the left-hand menu, click on "Create a resource."
3. In the "Search the Marketplace" box, type "Translator Text" and select it from the dropdown list.
4. Click on the "Create" button.
5. Fill in the required details:
   * **Name:** Enter a unique name for your resource.
   * **Subscription:** Select the Azure subscription that you want to use.
   * **Resource Group:** You can create a new resource group or select an existing one. 
   * **Pricing tier:** Select the pricing tier that suits your needs.
6. Click on the "Review + create" button.
7. Review your settings and click on the "Create" button.
8. After the deployment is complete, go to the resource you just created.
9. In the left-hand menu, click on "Keys and Endpoint."
10. You will see two keys and an endpoint. You can use either of the keys in your application.
