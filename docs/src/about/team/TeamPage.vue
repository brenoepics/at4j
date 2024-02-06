<script lang="ts">

</script>

<script setup lang="ts">
import membersCoreData from './members.json'
import type { Member } from './Member'
import TeamList from './TeamList.vue'
</script>

<template>
  <div class="TeamPage">
    <TeamList :members="membersCoreData as Member[]">
      <template #title>Team Members</template>
      <template #lead
        >Team members are those who are actively involved in the
        maintenance of the project.

      </template
      >
    </TeamList>
  </div>
</template>

<style scoped>
.TeamPage {
  padding-bottom: 16px;
}

@media (min-width: 768px) {
  .TeamPage {
    padding-bottom: 96px;
  }
}
</style>
