<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { Languages } from './languages.data.mjs';

// Define the props
const props = defineProps<{
  languages: Languages
}>()


const uniqueKeys = computed(() => {
  if(!props.languages) return [];
  const allKeys = [
    ...Object.keys(props.languages.translation),
    ...Object.keys(props.languages.transliteration),
    ...Object.keys(props.languages.dictionary)
  ];
  return Array.from(new Set(allKeys));
});

function getLanguageName(key: string): string {
  if(props.languages?.translation[key]) {
    return props.languages.translation[key].name;
  }

  if(props.languages?.transliteration[key]) {
    return props.languages.transliteration[key].name;
  }

  if(props.languages?.dictionary[key]) {
    return props.languages.dictionary[key].name;
  }

  return key;
}
</script>

<template>
  <table>
    <caption></caption>
    <thead>
    <tr>
      <th>Language</th>
      <th>Translation</th>
      <th>Transliteration</th>
      <th>Dictionary</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="key in uniqueKeys" :key="key">
      <td>{{ languages?.translation[key] ? languages.translation[key].name : key }}</td>
      <td>{{ languages?.translation[key] ? '✔️' : '❌' }}</td>
      <td>{{ languages?.transliteration[key] ? '✔️' : '❌' }}</td>
      <td>{{ languages?.dictionary[key] ? '✔️' : '❌' }}</td>
    </tr>
    </tbody>
  </table>
</template>
