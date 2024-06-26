<script setup>
import { data } from './languages.data.mts';
import LanguagesTable from './LanguagesTable.vue';

</script>

# Azure Available Languages

The Azure Translator API supports a wide range of languages for translation. This guide provides a list of languages
that are supported by the Azure Translator API.

## Supported Languages

> [!TIP]
> You can find the full list of supported languages in the [Azure Translator API documentation](https://docs.microsoft.com/en-us/azure/cognitive-services/translator/language-support).

<LanguagesTable :languages="data.azure_response" />
