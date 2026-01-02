<script setup>
import { data } from './datacenter.data.mts'
import DatacenterTable from './DatacenterTable.vue'
</script>

# Datacenter List

This page contains a list of Azure datacenters and their locations.

## Azure Datacenter Locations

<DatacenterTable :datacenters="data" />