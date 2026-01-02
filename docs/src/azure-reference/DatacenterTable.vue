<script setup lang="ts">
interface Datacenter {
  Name: string;
  DisplayName: string;
}

defineProps<{
  datacenters: Datacenter[]
}>()
</script>

<template>
  <table>
    <thead>
    <tr>
      <th>Region Name</th>
      <th>Region ID (API)</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="dc in datacenters" :key="dc.Name">
      <td>{{ dc.DisplayName }}</td>
      <td><code>{{ dc.Name }}</code></td> 
    </tr>
    </tbody>
  </table>
</template>