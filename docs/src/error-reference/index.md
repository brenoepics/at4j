<script setup>
import { ref, onMounted } from 'vue';
import { data } from './errors.data.mts';
import ErrorsTable from './ErrorsTable.vue';

const highlight = ref();
onMounted(() => {
  highlight.value = location.hash.slice(1)
})
</script>

# Azure Error Code Reference {#azure-errors}

The following table maps the codes to their original full information strings.

<ErrorsTable kind="azure" :errors="data.azure_response" :highlight="highlight" />
