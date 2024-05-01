<script setup lang="ts">
defineProps<{
  kind: string
  errors: { Code: string; Description: string }[]
  highlight?: any
}>()
</script>

<template>
  <table>
    <caption>Error Codes</caption>
    <thead>
      <tr>
        <th>Code</th>
        <th>Description</th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="(err) of errors"
        :class="{ highlight: highlight === `${kind}-${err.Code}` }"
      >
        <td :id="`${kind}-${err.Code}`" v-text="err.Code" />
        <td v-text="err.Description" />
      </tr>
    </tbody>
  </table>
</template>

<style scoped>
.highlight {
  color: var(--vp-c-brand-1);
  font-weight: bold;
}
</style>
