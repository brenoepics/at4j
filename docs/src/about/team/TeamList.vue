<script setup lang="ts">
import type { Member } from './Member'
import { VPTeamMembers } from 'vitepress/theme'

defineProps<{
  members: Member[]
}>()
</script>

<template>
  <section class="TeamList">
    <div class="container">
      <div class="info">
        <h2 class="title">
          <slot name="title" />
        </h2>
        <p class="lead">
          <slot name="lead" />
        </p>
      </div>

      <div class="members">
        <VPTeamMembers size="medium" :members="members" />
      </div>
    </div>
  </section>
</template>

<style scoped>
@media (min-width: 768px) {
  .TeamList {
    padding: 0 32px;
  }
}

.container {
  border-top: 1px solid var(--vp-c-divider);
  padding-top: 24px;
}

@media (min-width: 768px) {
  .container {
    margin: 0 auto;
    display: flex;
    align-items: flex-start;
    max-width: 960px;
  }
}

.info {
  flex-shrink: 0;
  padding: 0 24px;
  max-width: 512px;
}

@media (min-width: 768px) {
  .info {
    position: sticky;
    top: 32px;
    left: 0;
    padding: 0 24px 0 0;
    width: 256px;
  }

  html.banner-dismissed .info {
    top: 32px;
  }
}

@media (min-width: 960px) {
  .info {
    top: 88px;
    padding: 0 64px 0 0;
    width: 384px;
  }

  html.banner-dismissed .info {
    top: 88px;
  }
}

.title {
  font-size: 20px;
  font-weight: 500;
}

.lead {
  padding-top: 8px;
  line-height: 24px;
  font-size: 14px;
  font-weight: 500;
  color: var(--vp-c-text-2);
}

.members {
  padding-top: 24px;
}

@media (min-width: 768px) {
  .members {
    flex-grow: 1;
    padding-top: 0;
  }
}
</style>
