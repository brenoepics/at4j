<script setup>
import { ref, onMounted } from 'vue'
import { data } from './errors.data.ts'
import ErrorsTable from './ErrorsTable.vue'

const highlight = ref()
onMounted(() => {
  highlight.value = location.hash.slice(1)
})
</script>

# Production Error Code Reference {#error-reference}

## Azure Errors {#azure-errors}

The following table maps the codes to their original full information strings.

<ErrorsTable kind="azure" :errors="data.azure_response" :highlight="highlight" />
